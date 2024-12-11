package com.therxmv.telegramthemer.domain.adapter

import com.therxmv.telegramthemer.domain.model.ThemeState
import java.io.File

interface ThemeFileAdapter {
    fun createThemeFile(themeState: ThemeState): File
}