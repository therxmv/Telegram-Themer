package com.therxmv.telegramthemer.ui.editor.options

import com.therxmv.telegramthemer.ui.editor.data.ThemeState

interface MoreOptionsSubscriber {
    fun onPropertyChange(themeState: ThemeState)
}