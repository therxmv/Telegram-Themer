package com.therxmv.telegramthemer

import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.forEach
import com.github.dhaval2404.colorpicker.ColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape
import com.google.android.material.textfield.TextInputLayout
import com.therxmv.telegramthemer.databinding.ActivityMainBinding
import java.io.File
import java.lang.Exception
import kotlin.collections.set

class MainActivity : AppCompatActivity() {
    private val STYLE_PREFERENCES = "styleSettings"
    private val STYLE_PREFERENCES_INPUT = "input"
    private val STYLE_PREFERENCES_DEFAULT_RD = "defaultRB"

    private lateinit var binding: ActivityMainBinding

    private var mThemeTemplateFileName = "theday_template.attheme"
    private var mThemeFileName = "theday.attheme"

    private var mThemeProps = mutableMapOf(
        "default" to true,
        "isDark" to false,
        "isAmoled" to false,
        "isMonet" to false,
        "isGradient" to false,
    )

    private var mDefaultColor = "#299fe9"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // add action for custom appbar
        setSupportActionBar(binding.toolbar)

        val input = binding.tfHexInput
        val createButton = binding.btnCreateTheme
        val radioGroupStyle = binding.settings.rgStyle

        val darkCheckBox = binding.settings.cbDarkTheme
        val checkBoxMap = mapOf<String, CheckBox>(
            "isAmoled" to binding.settings.cbAmoledTheme,
            "isMonet" to binding.settings.cbMonet,
            "isGradient" to binding.settings.cbGradient,
        )

        // monet
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            checkBoxMap.getValue("isMonet").isEnabled = true
        }

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
                }
                .show()
        }

        darkCheckBox.setOnClickListener {
            checkBoxMap.getValue("isAmoled").isEnabled = darkCheckBox.isChecked
            checkBoxMap.getValue("isAmoled").isChecked = false

            mThemeProps["isDark"] = darkCheckBox.isChecked
        }

        checkBoxMap.forEach {
            checkBoxHandler(it.value, it.key)
        }

        //set click listener for each button
        radioGroupStyle.forEach { rb ->
            rb.setOnClickListener{
                changeActiveRadio(it as RadioButton, radioGroupStyle)
            }
        }

        // unfocus textfield and hide keyboard on pressed done
        input.editText?.setOnEditorActionListener(OnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                input.error = null
                input.clearFocus()

                val imm = getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(v.windowToken, 0)

                try {
                    input.setEndIconTintList(ColorStateList.valueOf(Color.parseColor("#" + input.editText?.text.toString())))
                    mDefaultColor = "#" + input.editText?.text.toString()
                } catch (e: Exception) {

                }

                return@OnEditorActionListener true
            }
            false
        })

        // create theme
        createButton.setOnClickListener {
            input.clearFocus()
            val imm = getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(it.windowToken, 0)

            if (!checkInput(input)) {
                setFilesNames()

                val templateFile =
                    applicationContext.assets.open(mThemeTemplateFileName).bufferedReader()
                        .readText()

                // creating new theme from template
                val color = if(mThemeProps["isMonet"]!!) Integer.toHexString(ContextCompat.getColor(this, R.color.theme_accent)).drop(2) else input.editText!!.text.toString()
                val fileString =
                    createTheme(templateFile, color, mThemeProps)

                File(
                    applicationContext.filesDir,
                    mThemeFileName
                ).writeText(fileString)

                shareTheme()
            }
        }
    }

    private fun checkBoxHandler(checkBox: CheckBox, value: String) {
        checkBox.setOnClickListener {
            mThemeProps[value] = checkBox.isChecked
        }
    }

    // set values in mThemeProps
    private fun setFilesNames() {
        if(mThemeProps["default"]!!) {
            if(mThemeProps["isDark"]!!) {
                mThemeFileName = if (mThemeProps["isAmoled"]!!) "TheAmoled.attheme" else "TheNight.attheme"
                mThemeTemplateFileName = "theday_dark_template.attheme"
            }
            else {
                mThemeFileName = "TheDay.attheme"
                mThemeTemplateFileName = "theday_template.attheme"
            }
        }
        else {
            if(mThemeProps["isDark"]!!) {
                mThemeFileName = if (mThemeProps["isAmoled"]!!) "Soza Amoled.attheme" else "Soza Night.attheme"
                mThemeTemplateFileName = "thesoza_dark_template.attheme"
            }
            else {
                mThemeFileName = "Soza Day.attheme"
                mThemeTemplateFileName = "thesoza_template.attheme"
            }
        }
    }

    private fun changeActiveRadio(current: RadioButton, all: RadioGroup) {
        all.clearCheck()
        current.isChecked = true
        mThemeProps["default"] = current.tag.toString() == "default"
    }

    private fun shareTheme() {
        val themeFile = File(applicationContext.filesDir, mThemeFileName)

        val uri = FileProvider.getUriForFile(
            applicationContext,
            BuildConfig.APPLICATION_ID + ".provider",
            themeFile
        )

        val intent = Intent(Intent.ACTION_SEND)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.type = "*/*"
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

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

        if(mThemeProps["isMonet"]!!) return false

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

    private fun putData() {
        val sharedPreferences: SharedPreferences = getSharedPreferences(STYLE_PREFERENCES, MODE_PRIVATE)
        val sharedPreferencesEditor: SharedPreferences.Editor = sharedPreferences.edit()

        val inputText = binding.tfHexInput.editText!!.text.toString()

        sharedPreferencesEditor.putString(STYLE_PREFERENCES_INPUT, inputText)
        sharedPreferencesEditor.putBoolean(STYLE_PREFERENCES_DEFAULT_RD, mThemeProps["default"]!!)

        mThemeProps.forEach {
            sharedPreferencesEditor.putBoolean(it.key, it.value)
        }

        sharedPreferencesEditor.apply()
    }

    private fun getData() {
        val sharedPreferences: SharedPreferences = getSharedPreferences(STYLE_PREFERENCES, MODE_PRIVATE)

        binding.tfHexInput.editText!!.setText(sharedPreferences.getString(STYLE_PREFERENCES_INPUT, ""))
        mThemeProps["default"] = sharedPreferences.getBoolean(STYLE_PREFERENCES_DEFAULT_RD, false)

        mThemeProps.forEach {
            if(it.key == "isAmoled") {
                mThemeProps[it.key] = if(mThemeProps["isDark"]!!) sharedPreferences.getBoolean(it.key, false) else false
            }
            else {
                mThemeProps[it.key] = sharedPreferences.getBoolean(it.key, false)
            }
        }
    }

    private fun setStyle() {
        getData()

        val radioGroupStyle = binding.settings.rgStyle
        val checkBoxMap = mapOf<String, CheckBox>(
            "isDark" to binding.settings.cbDarkTheme,
            "isAmoled" to binding.settings.cbAmoledTheme,
            "isMonet" to binding.settings.cbMonet,
            "isGradient" to binding.settings.cbGradient,
        )

        radioGroupStyle.forEach {
            it as RadioButton
            when (it.tag.toString()) {
                "default" -> it.isChecked = mThemeProps["default"]!!
                "soza" -> it.isChecked = !mThemeProps["default"]!!
            }
        }

        if (checkBoxMap["isDark"]!!.isChecked) checkBoxMap["isAmoled"]!!.isEnabled = true
        checkBoxMap.forEach {
            it.value.isChecked = mThemeProps[it.key]!!
        }
    }

    override fun onResume() {
        super.onResume()
        setStyle()
    }

    override fun onPause() {
        super.onPause()
        putData()
    }

    // create button in appbar
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