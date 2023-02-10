package com.therxmv.telegramthemer

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
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.github.dhaval2404.colorpicker.ColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape
import com.google.android.material.textfield.TextInputLayout
import com.therxmv.telegramthemer.databinding.ActivityMainBinding
import java.io.File
import kotlin.collections.set

const val STYLE_PREFERENCES = "styleSettings"
const val STYLE_PREFERENCES_INPUT = "input"
const val STYLE_PREFERENCES_DEFAULT = "default"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences

    private var mThemeTemplateFileName = "theday_template.attheme"
    private var mThemeFileName = "theday.attheme"

    companion object {
        var mThemeProps = mutableMapOf(
            "default" to true,
            "isDark" to false,
            "isAmoled" to false,
            "isMonet" to false,
            "isGradient" to false,
        )
    }

    private var mDefaultColor = "#299fe9"

    private lateinit var input: TextInputLayout
    private lateinit var preview: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences(STYLE_PREFERENCES, Context.MODE_PRIVATE)

        // add action for custom appbar
        setSupportActionBar(binding.toolbar)

        input = binding.tfHexInput

        val textInputStyle = binding.settings.tilStyle
        val autoCompleteStyle = binding.settings.acStyle

        val darkCheckBox = binding.settings.cbDarkTheme
        val monetCheckBox = binding.settings.cbMonet
        val checkBoxMap = mapOf<String, CheckBox>(
            "isAmoled" to binding.settings.cbAmoledTheme,
            "isGradient" to binding.settings.cbGradient,
        )

        preview = binding.ivPreview
        val createButton = binding.btnCreateTheme

        // set up dropdown
        val styles = resources.getStringArray(R.array.styles)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, styles)
        autoCompleteStyle.setAdapter(arrayAdapter)

        inputHandlers(input)

        // dropdown for styles
        autoCompleteStyle.setOnDismissListener {
            textInputStyle.clearFocus()
        }

        autoCompleteStyle.setOnItemClickListener { _, _, _, _ ->
            mThemeProps["default"] = textInputStyle.editText?.text.toString() == styles[0]
            createThemeFile()
        }

        // enable monet checkbox
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            monetCheckBox.isEnabled = true
        }

        // checkboxes
        darkCheckBox.setOnClickListener {
            checkBoxMap.getValue("isAmoled").isEnabled = darkCheckBox.isChecked
            checkBoxMap.getValue("isAmoled").isChecked = false

            mThemeProps["isDark"] = darkCheckBox.isChecked
            createThemeFile()
        }
        monetCheckBox.setOnClickListener {
            mThemeProps["isMonet"] = monetCheckBox.isChecked

            input.isEnabled = !monetCheckBox.isChecked

            input.error = null
            input.editText?.setText(Integer.toHexString(
                ContextCompat.getColor(
                    this,
                    R.color.theme_accent1
                )
            ).drop(2))

            setColorPickerIconColor(input)

            createThemeFile()
        }
        checkBoxMap.forEach {
            checkBoxHandler(it.value, it.key)
        }

        createButton.setOnClickListener {
            unfocusInput(input, it)

            if (createThemeFile()) shareTheme()
        }
    }

    private fun inputHandlers(input: TextInputLayout) {
        // unfocus textfield and hide keyboard on pressed done
        input.editText?.setOnEditorActionListener(OnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                input.error = null

                unfocusInput(input, v)

                setColorPickerIconColor(input)

                createThemeFile()

                return@OnEditorActionListener true
            }
            false
        })

        // Color picker
        input.setEndIconOnClickListener {
            ColorPickerDialog
                .Builder(this)
                .setTitle("Pick color")
                .setColorShape(ColorShape.CIRCLE)
                .setDefaultColor(mDefaultColor)
                .setColorListener { color, colorHex ->
                    mDefaultColor = colorHex
                    input.editText?.setText(colorHex.drop(1))
                    input.setEndIconTintList(ColorStateList.valueOf(color))
                    input.error = null
                    createThemeFile()
                }
                .show()
        }
    }

    // some useful functions
    private fun unfocusInput(input: TextInputLayout, view: View) {
        input.clearFocus()
        val imm = getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun setColorPickerIconColor(input: TextInputLayout) {
        try {
            input.setEndIconTintList(ColorStateList.valueOf(Color.parseColor("#" + input.editText?.text.toString())))
            mDefaultColor = "#" + input.editText?.text.toString()
        }
        catch (e: Exception){}
    }

    private fun checkBoxHandler(checkBox: CheckBox, value: String) {
        checkBox.setOnClickListener {
            mThemeProps[value] = checkBox.isChecked
            createThemeFile()
        }
    }

    // set up theme file and preview
    private fun createThemeFile(): Boolean {
        if (!checkInput(input)) {
            setFilesNames()

            val templateFile =
                applicationContext.assets.open(mThemeTemplateFileName).bufferedReader()
                    .readText()

            // creating new theme from template
            val fileString =
                createTheme(applicationContext, templateFile, input.editText!!.text.toString(), preview)

            File(
                applicationContext.filesDir,
                mThemeFileName
            ).writeText(fileString)

            return true
        }

        return false
    }

    // set values in mThemeProps
    private fun setFilesNames() {
        if (mThemeProps["default"]!!) {
            if (mThemeProps["isDark"]!!) {
                mThemeFileName =
                    if (mThemeProps["isAmoled"]!!) "TheAmoled.attheme" else "TheNight.attheme"
                mThemeTemplateFileName = "theday_dark_template.attheme"
            } else {
                mThemeFileName = "TheDay.attheme"
                mThemeTemplateFileName = "theday_template.attheme"
            }
        } else {
            if (mThemeProps["isDark"]!!) {
                mThemeFileName =
                    if (mThemeProps["isAmoled"]!!) "Soza Amoled.attheme" else "Soza Night.attheme"
                mThemeTemplateFileName = "thesoza_dark_template.attheme"
            } else {
                mThemeFileName = "Soza Day.attheme"
                mThemeTemplateFileName = "thesoza_template.attheme"
            }
        }
    }

    // share theme file
    private fun shareTheme() {
        val themeFile = File(applicationContext.filesDir, mThemeFileName)

        val uri = FileProvider.getUriForFile(
            applicationContext,
            BuildConfig.APPLICATION_ID + ".provider",
            themeFile
        )

        val intent = Intent(Intent.ACTION_SEND).apply {
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            type = "*/*"
            putExtra(Intent.EXTRA_STREAM, uri)
            putExtra(Intent.EXTRA_TEXT, "Theme made via play.google.com/store/apps/details?id=com.therxmv.telegramthemer\n\n@therxmv_channel\n${if(mThemeFileName.contains("Soza")) "@soza_themes" else "@BlandoThemes"}")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }

        val chooser = Intent.createChooser(intent, mThemeFileName)

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

    // validation for input
    private fun checkInput(input: TextInputLayout): Boolean {
        val isError: Boolean
        val inputText = input.editText?.text.toString()
        input.errorIconDrawable = null

        if (mThemeProps["isMonet"]!!) return false

        if (inputText.isEmpty()) {
            isError = true
            input.error = getString(R.string.empty_error)
        } else if (inputText.length != 6) {
            isError = true
            input.error = getString(R.string.length_error)
        } else if (inputText.contains(Regex("[^0-9a-fA-f]"))) {
            isError = true
            input.error = getString(R.string.invalid_error)
        } else {
            isError = false
            input.error = null
        }

        return isError
    }

    // save props
    private fun putData() {
        val inputText = input.editText!!.text.toString()

        sharedPreferences.edit().apply {
            putString(STYLE_PREFERENCES_INPUT, inputText)
            putBoolean(STYLE_PREFERENCES_DEFAULT, mThemeProps["default"]!!)

            mThemeProps.forEach {
                putBoolean(it.key, it.value)
            }

            apply()
        }
    }

    private fun getData() {
        input.editText!!.setText(
            sharedPreferences.getString(
                STYLE_PREFERENCES_INPUT,
                ""
            )
        )

        mThemeProps["default"] = sharedPreferences.getBoolean(STYLE_PREFERENCES_DEFAULT, true)

        mThemeProps.forEach {
            if (it.key == "isAmoled") {
                mThemeProps[it.key] = if (mThemeProps["isDark"]!!) sharedPreferences.getBoolean(
                    it.key,
                    false
                ) else false
            } else {
                mThemeProps[it.key] = sharedPreferences.getBoolean(it.key, false)
            }
        }
    }

    private fun setStyle() {
        getData()

        val autoCompleteStyle = binding.settings.acStyle
        val checkBoxMap = mapOf<String, CheckBox>(
            "isDark" to binding.settings.cbDarkTheme,
            "isAmoled" to binding.settings.cbAmoledTheme,
            "isMonet" to binding.settings.cbMonet,
            "isGradient" to binding.settings.cbGradient,
        )

        val styles = resources.getStringArray(R.array.styles)
        autoCompleteStyle.setText(if(mThemeProps["default"]!!) styles[0] else styles[1])

        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, styles)
        autoCompleteStyle.setAdapter(arrayAdapter)

        checkBoxMap.forEach {
            it.value.isChecked = mThemeProps[it.key]!!
        }
        checkBoxMap["isAmoled"]!!.isEnabled = checkBoxMap["isDark"]!!.isChecked

        input.isEnabled = !checkBoxMap["isMonet"]!!.isChecked
        setColorPickerIconColor(input)

        if(input.editText?.text.toString().isNotEmpty()) createThemeFile()
    }

    override fun onResume() {
        super.onResume()
        setStyle()
    }

    override fun onPause() {
        super.onPause()
        putData()
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