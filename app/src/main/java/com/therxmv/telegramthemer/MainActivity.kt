package com.therxmv.telegramthemer

import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.forEach
import com.google.android.material.textfield.TextInputLayout
import com.therxmv.telegramthemer.databinding.ActivityMainBinding
import java.io.File
import kotlin.collections.set

class MainActivity : AppCompatActivity() {
    private val STYLE_PREFERENCES = "styleSettings"
    private val STYLE_PREFERENCES_INPUT = "input"
    private val STYLE_PREFERENCES_DEFAULT_RD = "defaultRd"
    private val STYLE_PREFERENCES_DARK_CB = "darkCb"
    private val STYLE_PREFERENCES_AMOLED_CB = "amoledCb"
    private val STYLE_PREFERENCES_GRADIENT_CB = "gradientCb"

    private lateinit var binding: ActivityMainBinding

    private var themeTemplateFileName = "theday_template.attheme"
    private var themeFileName = "theday.attheme"

    private var themeProps = mutableMapOf<String, Boolean>(
        "default" to true,
        "isDark" to false,
        "isAmoled" to false,
        "isGradient" to false,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setStyle()
        // add action for custom appbar
        setSupportActionBar(binding.toolbar)

        val input = binding.tfHexInput
        val createButton = binding.btnCreateTheme
        val radioGroupStyle = binding.settings.rgStyle
        val darkCheckBox = binding.settings.cbDarkTheme
        val amoledCheckBox = binding.settings.cbAmoledTheme
        val gradientCheckBox = binding.settings.cbGradient

        darkCheckBox.setOnClickListener {
            amoledCheckBox.isEnabled = darkCheckBox.isChecked
            amoledCheckBox.isChecked = false

            themeProps["isDark"] = darkCheckBox.isChecked
        }

        amoledCheckBox.setOnClickListener {
            themeProps["isAmoled"] = amoledCheckBox.isChecked
        }

        gradientCheckBox.setOnClickListener {
            themeProps["isGradient"] = gradientCheckBox.isChecked
        }

        //set click listener for each button
        radioGroupStyle.forEach {
            it.setOnClickListener{
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

                return@OnEditorActionListener true
            }
            false
        })

        // create default theme
        createButton.setOnClickListener {
            input.clearFocus()
            val imm = getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(it.windowToken, 0)

            if (!checkInput(input)) {
                setFilesNames()

                val templateFile =
                    applicationContext.assets.open(themeTemplateFileName).bufferedReader()
                        .readText()

                // creating new theme from template
                val fileString =
                    createTheme(templateFile, input.editText!!.text.toString(), themeProps)

                File(
                    applicationContext.filesDir,
                    themeFileName
                ).writeText(fileString)

                shareTheme()
            }
        }
    }

    // set values in themeProps
    private fun setFilesNames() {
        if(themeProps["default"]!!) {
            if(themeProps["isDark"]!!) {
                themeFileName = if (themeProps["isAmoled"]!!) "TheAmoled.attheme" else "TheNight.attheme"
                themeTemplateFileName = "theday_dark_template.attheme"
            }
            else {
                themeFileName = "TheDay.attheme"
                themeTemplateFileName = "theday_template.attheme"
            }
        }
        else {
            if(themeProps["isDark"]!!) {
                themeFileName = if (themeProps["isAmoled"]!!) "Soza Amoled.attheme" else "Soza Night.attheme"
                themeTemplateFileName = "thesoza_dark_template.attheme"
            }
            else {
                themeFileName = "Soza Day.attheme"
                themeTemplateFileName = "thesoza_template.attheme"
            }
        }
    }

    private fun changeActiveRadio(current: RadioButton, all: RadioGroup) {
        all.clearCheck()
        current.isChecked = true
        themeProps["default"] = current.tag.toString() == "default"
    }

    private fun shareTheme() {
        val themeFile = File(applicationContext.filesDir, themeFileName)

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

        val chooser = Intent.createChooser(intent, themeFileName)

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
        var isError = false;
        val inputText = input.editText?.text.toString()

        if (inputText.isEmpty()) {
            isError = true
            input.error = "Please enter something!"
        } else if (inputText.length != 6) {
            isError = true
            input.error = "You must enter six characters!"
        } else if (inputText.contains(Regex("[^0-9a-fA-f]"))) {
            isError = true
            input.error = "Invalid hex number!"
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
        sharedPreferencesEditor.putBoolean(STYLE_PREFERENCES_DEFAULT_RD, themeProps["default"]!!)
        sharedPreferencesEditor.putBoolean(STYLE_PREFERENCES_DARK_CB, themeProps["isDark"]!!)
        sharedPreferencesEditor.putBoolean(STYLE_PREFERENCES_AMOLED_CB, themeProps["isAmoled"]!!)
        sharedPreferencesEditor.putBoolean(STYLE_PREFERENCES_GRADIENT_CB, themeProps["isGradient"]!!)

        sharedPreferencesEditor.apply()
    }

    private fun getData() {
        val sharedPreferences: SharedPreferences = getSharedPreferences(STYLE_PREFERENCES, MODE_PRIVATE)

        binding.tfHexInput.editText!!.setText(sharedPreferences.getString(STYLE_PREFERENCES_INPUT, ""))

        themeProps["default"] = sharedPreferences.getBoolean(STYLE_PREFERENCES_DEFAULT_RD, false)
        themeProps["isDark"] = sharedPreferences.getBoolean(STYLE_PREFERENCES_DARK_CB, false)
        themeProps["isAmoled"] = sharedPreferences.getBoolean(STYLE_PREFERENCES_AMOLED_CB, false)
        themeProps["isGradient"] = sharedPreferences.getBoolean(STYLE_PREFERENCES_GRADIENT_CB, true)
    }

    private fun setStyle() {
        getData()

        val radioGroupStyle = binding.settings.rgStyle
        val darkCheckBox = binding.settings.cbDarkTheme
        val amoledCheckBox = binding.settings.cbAmoledTheme
        val gradientCheckBox = binding.settings.cbGradient

        radioGroupStyle.forEach {
            it as RadioButton;
            when (it.tag.toString()) {
                "default" -> it.isChecked = themeProps["default"]!!
                "soza" -> it.isChecked = !themeProps["default"]!!
            }
        }

        darkCheckBox.isChecked = themeProps["isDark"]!!;
        amoledCheckBox.isChecked = themeProps["isAmoled"]!!;
        gradientCheckBox.isChecked = themeProps["isGradient"]!!;
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