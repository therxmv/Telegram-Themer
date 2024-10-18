package com.therxmv.telegramthemer.ui.editor

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.therxmv.telegramthemer.databinding.ActivityThemeEditorBinding
import com.therxmv.telegramthemer.ui.base.BaseBindingActivity
import com.therxmv.telegramthemer.ui.editor.picker.ColorPickerFragment
import com.therxmv.telegramthemer.ui.editor.picker.ColorPickerSubscriber
import javax.inject.Inject

class ThemeEditorActivity : BaseBindingActivity<ActivityThemeEditorBinding>(),
    ThemeEditorContract.View,
    ColorPickerSubscriber {

    @Inject
    lateinit var presenter: ThemeEditorContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityThemeEditorBinding::inflate)
        presenter.attachView(this@ThemeEditorActivity, lifecycleScope)
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun openColorPicker() {
        ColorPickerFragment.createInstance().show(supportFragmentManager, "ColorPickerFragment")
    }

    override fun onColorChanged(color: Int) {
        presenter.onColorChanged(color)
    }
}