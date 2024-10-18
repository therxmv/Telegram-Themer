package com.therxmv.telegramthemer.ui.editor.simple

import com.therxmv.telegramthemer.ui.base.BasePresenter

interface SimpleThemeEditContract {

    interface View {
        fun setUpColorPickerButton(onClick: () -> Unit)
    }

    abstract class Presenter : BasePresenter<View>() {

    }
}