package com.therxmv.telegramthemer.ui.editor.data.file

import com.therxmv.telegramthemer.ui.editor.data.ThemeState
import java.io.File

interface ThemeFileAdapter {
    fun createThemeFile(themeState: ThemeState): File
}