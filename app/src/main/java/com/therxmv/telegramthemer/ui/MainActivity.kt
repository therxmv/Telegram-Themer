package com.therxmv.telegramthemer.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import com.therxmv.telegramthemer.BuildConfig
import com.therxmv.telegramthemer.R
import com.therxmv.telegramthemer.data.models.ThemeModel
import com.therxmv.telegramthemer.databinding.ActivityMainBinding
import com.therxmv.telegramthemer.utils.DEFAULT_COLOR
import com.therxmv.telegramthemer.utils.ThemeUtils
import com.therxmv.telegramthemer.utils.checkVersionForMonet
import com.therxmv.telegramthemer.utils.toVisibility
import kotlinx.coroutines.flow.collectLatest
import top.defaults.colorpicker.ColorPickerView
import java.io.File

class MainActivity : AppCompatActivity() {

    companion object {
        private const val SHARE_TEXT = "Theme made via play.google.com/store/apps/details?id=com.therxmv.telegramthemer"
        private const val THERXMV_MENTION = "@therxmv_channel"
        private const val BLANDO_MENTION = "@BlandoThemes"
        private const val SOZA_MENTION = "@soza_themes"
    }

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val vm: MainViewModel by viewModels { MainViewModel.Factory() }

    private var colorPickerColor = DEFAULT_COLOR
    private val styles by lazy { resources.getStringArray(R.array.styles) }

    private val inputLayout by lazy { binding.inputLayout }

    private val dropdownInput by lazy { binding.settings.styleDropdown }
    private val dropdownItems by lazy { binding.settings.styleDropdownItems }

    private val darkCheckBox by lazy { binding.settings.cbDarkTheme }
    private val monetCheckBox by lazy { binding.settings.cbMonet }
    private val monetBgCheckBox by lazy { binding.settings.cbMonetBg }
    private val amoledCheckBox by lazy { binding.settings.cbAmoledTheme }
    private val gradientCheckBox by lazy { binding.settings.cbGradient }

    private val createButton by lazy { binding.btnCreateTheme }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // add action for custom appbar
        setSupportActionBar(binding.toolbar)

        initListeners()
        initCheckBoxListeners()
        initInputListeners()
    }

    override fun onResume() {
        super.onResume()
        vm.loadFromSharedPrefs()
    }

    override fun onPause() {
        super.onPause()
        vm.saveToSharedPrefs()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_about, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_about -> {
            startActivity(AboutActivity.createIntent(this))
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun initListeners() {
        lifecycleScope.launchWhenCreated {
            vm.themePropsState.collectLatest {
                inputLayout.isEnabled = !it.isMonet
                inputLayout.editText?.setText(it.color)

                if (!checkInput(inputLayout)) {
                    inputLayout.setEndIconTintList(ColorStateList.valueOf(Color.parseColor("#" + it.color)))
                    colorPickerColor = it.color
                }

                dropdownItems.setText(if (it.isDefault) styles[0] else styles[1])
                setupDropdown(styles, dropdownItems)

                darkCheckBox.isChecked = it.isDark
                gradientCheckBox.isChecked = it.isGradient

                amoledCheckBox.isChecked = it.isAmoled
                amoledCheckBox.isEnabled = it.isDark

                monetCheckBox.isChecked = it.isMonet
                monetBgCheckBox.isChecked = it.isMonetBackground

                checkVersionForMonet().toVisibility().also { isVisible ->
                    monetCheckBox.visibility = isVisible
                    monetBgCheckBox.visibility = isVisible
                }

                if (!checkInput(inputLayout)) {
                    createThemeFile(it)
                }
            }
        }

        createButton.setOnClickListener {
            shareTheme()
        }

        dropdownItems.setOnDismissListener {
            dropdownInput.clearFocus()
        }

        dropdownItems.setOnItemClickListener { _, _, _, _ ->
            vm.setThemeProperties(default = dropdownInput.editText?.text.toString() == styles.first())
        }
    }

    private fun initCheckBoxListeners() {
        monetCheckBox.setOnClickListener {
            it as CheckBox
            vm.setThemeProperties(
                hex = Integer.toHexString(
                    ContextCompat.getColor(
                        this,
                        R.color.theme_accent1_200
                    )
                ).drop(2),
                monet = it.isChecked,
            )
        }

        monetBgCheckBox.setOnClickListener {
            it as CheckBox
            vm.setThemeProperties(monetBg = it.isChecked)
        }

        gradientCheckBox.setOnClickListener {
            it as CheckBox
            vm.setThemeProperties(gradient = it.isChecked)
        }

        amoledCheckBox.setOnClickListener {
            it as CheckBox
            vm.setThemeProperties(amoled = it.isChecked)
        }

        darkCheckBox.setOnClickListener {
            it as CheckBox
            vm.setThemeProperties(dark = it.isChecked)
        }
    }

    private fun initInputListeners() {
        // unfocus textfield and hide keyboard on pressed done
        inputLayout.editText?.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                inputLayout.error = null

                inputLayout.clearFocus()
                hideKeyboard(v)

                vm.setThemeProperties(hex = inputLayout.editText?.text.toString())

                return@OnEditorActionListener true
            }
            false
        })

        inputLayout.setEndIconOnClickListener {
            setUpColorPickerDialog(inputLayout, colorPickerColor)
        }
    }

    private fun setUpColorPickerDialog(inputLayout: TextInputLayout, initColor: String) {
        val view = layoutInflater.inflate(R.layout.color_picker_layout, null) as ColorPickerView
        view.setInitialColor(Color.parseColor("#$initColor"))

        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.colorPickerTitle)
            .setView(view)
            .setPositiveButton(
                R.string.colorPickerConfirm
            ) { dialog, _ ->
                val color = view.color
                val hex = String.format("#%06X", 0xFFFFFF and color)

                inputLayout.setEndIconTintList(ColorStateList.valueOf(color))
                inputLayout.error = null
                vm.setThemeProperties(hex = hex.drop(1))

                dialog.dismiss()
            }
            .setNegativeButton(
                R.string.colorPickerCancel
            ) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun shareTheme() {
        val fileName = vm.getFilesNames().first()

        val themeFile = File(applicationContext.filesDir, fileName)

        val uri = FileProvider.getUriForFile(
            applicationContext,
            BuildConfig.APPLICATION_ID + ".provider",
            themeFile
        )

        val intent = Intent(Intent.ACTION_SEND).apply {
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            type = "*/*"
            putExtra(Intent.EXTRA_STREAM, uri)
            putExtra(
                Intent.EXTRA_TEXT,
                "$SHARE_TEXT\n\n$THERXMV_MENTION\n${if (fileName.contains("Soza")) SOZA_MENTION else BLANDO_MENTION}"
            )
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }

        val chooser = Intent.createChooser(intent, fileName)

        // fix for "Permission Denial" error
        val resInfoList = this.packageManager.queryIntentActivities(
            chooser,
            PackageManager.MATCH_DEFAULT_ONLY
        )
        for (resolveInfo in resInfoList) {
            val packageName = resolveInfo.activityInfo.packageName
            grantUriPermission(
                packageName,
                uri,
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
        }

        try {
            startActivity(chooser)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show()
        }
    }

    private fun createThemeFile(themeProps: ThemeModel) {
        val (fileName, templateFileName) = vm.getFilesNames()

        val templateFile =
            applicationContext.assets.open(templateFileName).bufferedReader().readText()

        // creating new theme from template
        val fileString = ThemeUtils.createTheme(applicationContext, templateFile, themeProps, binding.themePreview)

        File(
            applicationContext.filesDir,
            fileName
        ).writeText(fileString)
    }

    private fun setupDropdown(styles: Array<String>, dropdownItems: AutoCompleteTextView) {
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, styles)
        dropdownItems.setAdapter(arrayAdapter)
    }

    private fun hideKeyboard(view: View) {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun checkInput(input: TextInputLayout): Boolean {
        val inputText = input.editText?.text.toString()
        input.errorIconDrawable = null

        return if (inputText.isEmpty()) {
            input.error = getString(R.string.empty_error)
            true
        } else if (inputText.length != 6) {
            input.error = getString(R.string.length_error)
            true
        } else if (inputText.toIntOrNull(16) == null) {
            input.error = getString(R.string.invalid_error)
            true
        } else {
            input.error = null
            false
        }
    }
}