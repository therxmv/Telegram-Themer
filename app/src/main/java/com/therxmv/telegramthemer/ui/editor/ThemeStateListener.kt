package com.therxmv.telegramthemer.ui.editor

import com.therxmv.telegramthemer.domain.model.ThemeState

interface ThemeStateListener {
    fun onStateChange(themeState: ThemeState)
}