package com.therxmv.telegramthemer.ui.editor

import android.util.Log
import javax.inject.Inject

class ThemeEditorPresenter @Inject constructor(

) : ThemeEditorContract.Presenter() {

    init {
        Log.d("rozmi_init", this.toString())
    }

    override fun attachView(view: ThemeEditorContract.View) {
        super.attachView(view)
    }

    override fun detachView() {
        super.detachView()
    }

    override fun openColorPicker() {
        view.openColorPicker()
    }
}