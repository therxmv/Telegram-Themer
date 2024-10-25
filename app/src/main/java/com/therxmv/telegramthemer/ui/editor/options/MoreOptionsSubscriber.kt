package com.therxmv.telegramthemer.ui.editor.options

import com.therxmv.telegramthemer.domain.model.ThemeState

interface MoreOptionsSubscriber {
    fun onPropertyChange(themeState: ThemeState)
}