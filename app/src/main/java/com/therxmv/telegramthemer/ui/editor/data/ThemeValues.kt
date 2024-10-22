package com.therxmv.telegramthemer.ui.editor.data

interface ThemeValues {
    fun getAdvancedColorSchema(state: ThemeState): Map<String, String>
}