package com.therxmv.telegramthemer.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.TextView.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import com.github.dhaval2404.colorpicker.ColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputLayout
import com.therxmv.telegramthemer.BuildConfig
import com.therxmv.telegramthemer.R
import com.therxmv.telegramthemer.createTheme
import com.therxmv.telegramthemer.data.models.ThemeModel
import com.therxmv.telegramthemer.databinding.ActivityMainBinding
import com.therxmv.telegramthemer.utils.*
import kotlinx.coroutines.flow.collectLatest
import java.io.File
import kotlin.collections.set

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val vm: MainViewModel by viewModels { MainViewModel.Factory(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // add action for custom appbar
        setSupportActionBar(binding.toolbar)

        val styles = resources.getStringArray(R.array.styles)

        val inputLayout = binding.inputLayout

        val dropdownInput = binding.settings.styleDropdown
        val dropdownItems = binding.settings.styleDropdownItems

        val darkCheckBox = binding.settings.cbDarkTheme
        val monetCheckBox = binding.settings.cbMonet
        val amoledCheckBox = binding.settings.cbAmoledTheme
        val gradientCheckBox = binding.settings.cbGradient

        val createButton = binding.btnCreateTheme

        var colorPickerColor = DEFAULT_COLOR

        // SHARE THEME
        createButton.setOnClickListener {
            shareTheme()
        }

        // LISTENERS
        lifecycleScope.launchWhenCreated {
            vm.themeProps.collectLatest {
                inputLayout.isEnabled = !it.isMonet
                inputLayout.editText?.setText(it.color)
                try {
                    inputLayout.setEndIconTintList(ColorStateList.valueOf(Color.parseColor("#" + it.color)))
                }
                catch (_: Exception){}
                colorPickerColor = it.color

                dropdownItems.setText(if (it.isDefault) styles[0] else styles[1])
                setupDropdown(styles, dropdownItems)

                darkCheckBox.isChecked = it.isDark
                monetCheckBox.isChecked = it.isMonet
                gradientCheckBox.isChecked = it.isGradient

                amoledCheckBox.isChecked = it.isAmoled
                amoledCheckBox.isEnabled = it.isDark

                if (!checkInput(inputLayout)) {
                    createThemeFile(it)
                }
            }
        }

        // DROPDOWN
        dropdownItems.setOnDismissListener {
            dropdownInput.clearFocus()
        }

        dropdownItems.setOnItemClickListener { _, _, _, _ ->
            vm.setThemeStyle(dropdownInput.editText?.text.toString() == styles[0])
        }

        // CHECKBOXES
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            monetCheckBox.isEnabled = true
        }

        monetCheckBox.setOnClickListener {
            it as CheckBox
            vm.setThemeMonet(it.isChecked)
            vm.setThemeColor(
                Integer.toHexString(
                    ContextCompat.getColor(
                        this,
                        R.color.theme_accent1
                    )
                ).drop(2)
            )
        }

        gradientCheckBox.setOnClickListener {
            it as CheckBox
            vm.setThemeGradient(it.isChecked)
        }

        amoledCheckBox.setOnClickListener {
            it as CheckBox
            vm.setThemeAmoled(it.isChecked)
        }

        darkCheckBox.setOnClickListener {
            it as CheckBox
            vm.setThemeMode(it.isChecked)
        }

        // HEX INPUT
        // unfocus textfield and hide keyboard on pressed done
        inputLayout.editText?.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                inputLayout.error = null

                inputLayout.clearFocus()
                hideKeyboard(v)

                vm.setThemeColor(inputLayout.editText?.text.toString())

                return@OnEditorActionListener true
            }
            false
        })

        // Color picker
        inputLayout.setEndIconOnClickListener {
            val dialog = ColorPickerDialog
                .Builder(this)
                .setTitle(getString(R.string.colorPickerTitle))
                .setColorShape(ColorShape.CIRCLE)
                .setDefaultColor("#$colorPickerColor") // TODO color changing
                .setColorListener { color, colorHex ->
                    inputLayout.setEndIconTintList(ColorStateList.valueOf(color))
                    inputLayout.error = null
                    vm.setThemeColor(colorHex.drop(1))
                }
                .show()
        }
    }

    private fun shareTheme() {
        val fileName = vm.getFilesNames()[0]

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
                "Theme made via play.google.com/store/apps/details?id=com.therxmv.telegramthemer\n\n@therxmv_channel\n${
                    if (fileName.contains("Soza")) "@soza_themes" else "@BlandoThemes"
                }"
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

        startActivity(chooser)
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

    override fun onResume() {
        super.onResume()
        vm.loadFromSharedPrefs()
    }

    override fun onPause() {
        super.onPause()
        vm.saveToSharedPrefs()
    }

    // create back button in appbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_about, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_about -> {
            startActivity(Intent(this, AboutActivity::class.java))
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }
}