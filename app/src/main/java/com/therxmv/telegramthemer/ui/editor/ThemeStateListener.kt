package com.therxmv.telegramthemer.ui.editor

import com.therxmv.telegramthemer.ui.editor.data.ThemeState

interface ThemeStateListener {
    fun onStateChange(themeState: ThemeState)
}