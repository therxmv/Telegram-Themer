package com.therxmv.telegramthemer.ui.editor

import com.therxmv.telegramthemer.domain.model.ThemeState

interface ThemeStateListener {
    fun onStateChange(themeState: ThemeState)

    /** The custom color picker sheet (see [ThemeEditorEvent.OpenColorPicker]) was just dismissed. */
    fun onColorPickerClosed() {}
}