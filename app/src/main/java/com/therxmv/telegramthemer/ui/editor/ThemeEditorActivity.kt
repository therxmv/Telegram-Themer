package com.therxmv.telegramthemer.ui.editor

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import com.therxmv.telegramthemer.BuildConfig
import com.therxmv.telegramthemer.R
import com.therxmv.telegramthemer.databinding.ActivityThemeEditorBinding
import com.therxmv.telegramthemer.domain.model.ThemeState
import com.therxmv.telegramthemer.ui.base.BaseBindingActivity
import com.therxmv.telegramthemer.ui.editor.help.HelpDialogFragment
import com.therxmv.telegramthemer.ui.editor.options.MoreOptionsBottomSheetFragment
import com.therxmv.telegramthemer.ui.editor.options.MoreOptionsSubscriber
import com.therxmv.telegramthemer.ui.editor.picker.ColorPickerBottomSheetFragment
import com.therxmv.telegramthemer.ui.editor.picker.ColorPickerSubscriber
import java.io.File
import javax.inject.Inject

class ThemeEditorActivity : BaseBindingActivity<ActivityThemeEditorBinding>(),
    ThemeEditorContract.View,
    ColorPickerSubscriber,
    MoreOptionsSubscriber {

    companion object {
        private const val TELEGRAM_PACKAGE = "org.telegram.messenger"
        private const val TELEGRAM_WEB_PACKAGE = "org.telegram.messenger.web"
    }

    @Inject
    lateinit var presenter: ThemeEditorContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityThemeEditorBinding::inflate)
        presenter.attachView(this@ThemeEditorActivity, lifecycleScope)
        setSupportActionBar(binding.mainToolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_bar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_help -> {
            HelpDialogFragment
                .createInstance()
                .show(supportFragmentManager, "HelpDialogFragment")

            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun openColorPicker(currentColor: Int) {
        ColorPickerBottomSheetFragment
            .createInstance(currentColor)
            .show(supportFragmentManager, "ColorPickerBottomSheetFragment")
    }

    override fun openMoreOptions(themeState: ThemeState) {
        MoreOptionsBottomSheetFragment
            .createInstance(themeState)
            .show(supportFragmentManager, "MoreOptionsBottomSheetFragment")
    }

    override fun onColorChanged(color: Int) {
        presenter.onColorChanged(color)
    }

    override fun onPropertyChange(themeState: ThemeState) {
        presenter.onPropertyChange(themeState)
    }

    override fun shareThemeFile(file: File) {
        val themeUri = FileProvider.getUriForFile(
            applicationContext,
            BuildConfig.APPLICATION_ID + ".provider",
            file,
        )
        val telegramPackage = getTelegramPackageIfExists()

        val intent = Intent(Intent.ACTION_SEND).apply {
            telegramPackage?.let { `package` = it }

            type = "*/*"
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            putExtra(Intent.EXTRA_STREAM, themeUri)
            putExtra(
                Intent.EXTRA_TEXT,
                presenter.getShareDescription()
            )
        }
        val intentToStart = intent.takeIf { telegramPackage != null }
            ?: Intent.createChooser(intent, file.name)

        intentToStart.grantUriPermissions(themeUri)

        try {
            startActivity(intentToStart)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show()
        }
    }

    private fun getTelegramPackageIfExists(): String? {
        val takeIfInstalled: String.() -> String? = {
            this.takeIf {
                packageManager.getLaunchIntentForPackage(it) != null
            }
        }

        return TELEGRAM_PACKAGE.takeIfInstalled() ?: TELEGRAM_WEB_PACKAGE.takeIfInstalled()
    }

    private fun Intent.grantUriPermissions(uri: Uri) {
        val intentHandlers = packageManager.queryIntentActivities(
            this,
            PackageManager.MATCH_DEFAULT_ONLY,
        )
        for (handler in intentHandlers) {
            val packageName = handler.activityInfo.packageName
            grantUriPermission(
                packageName,
                uri,
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION,
            )
        }
    }
}