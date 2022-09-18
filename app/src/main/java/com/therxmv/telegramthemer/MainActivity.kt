package com.therxmv.telegramthemer

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.google.android.material.textfield.TextInputLayout
import com.therxmv.telegramthemer.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val thedayTemplateFileName = "theday_template.attheme"
    private val thedayFileName = "theday.attheme"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val input = binding.tfHexInput
        val createButton = binding.btnCreateTheme
        val previewButton = binding.btnPreviewTheme
        val shareButton = binding.btnShareTheme

        // unfocus textfield and hide keyboard on pressed done
        input.editText?.setOnEditorActionListener(OnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                input.error = null
                input.clearFocus()

                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(v.windowToken, 0)

                return@OnEditorActionListener true
            }
            false
        })

        // create default theme
        createButton.setOnClickListener {
            input.clearFocus()

            if (!checkInput(input)) {
                val templateFile =
                    applicationContext.assets.open(thedayTemplateFileName).bufferedReader()
                        .readText()

                // creating new theme from template
                val fileString =
                    createTheme(templateFile, input.editText!!.text.toString())

                File(
                    applicationContext.filesDir,
                    thedayFileName
                ).writeText(fileString)

                //previewButton.isEnabled = true
                shareButton.isEnabled = true
            }
        }

        // share theme
        shareButton.setOnClickListener {
            val themeFile = File(applicationContext.filesDir, thedayFileName)

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

            val chooser = Intent.createChooser(intent, thedayFileName)

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
        } else if (inputText.contains(Regex("[G-Zg-z]"))) {
            isError = true
            input.error = "Invalid hex number!"
        } else {
            isError = false
            input.error = null
        }

        return isError
    }
}