package com.therxmv.telegramthemer.ui.editor.di

import com.therxmv.telegramthemer.ui.editor.data.ThemeValues
import com.therxmv.telegramthemer.ui.editor.data.ThemeValuesProvider
import com.therxmv.telegramthemer.ui.editor.data.file.ThemeFileAdapter
import com.therxmv.telegramthemer.ui.editor.data.file.ThemeToFileAdapter
import com.therxmv.telegramthemer.ui.editor.data.preview.PreviewColorsAdapter
import com.therxmv.telegramthemer.ui.editor.data.preview.ThemeToPreviewAdapter
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class ThemeValuesModule {

    @Singleton
    @Binds
    abstract fun bindsThemeValuesProvider(provider: ThemeValuesProvider): ThemeValues

    @Singleton
    @Binds
    abstract fun bindsThemeToPreviewAdapter(adapter: ThemeToPreviewAdapter): PreviewColorsAdapter

    @Singleton
    @Binds
    abstract fun bindsThemeToFileAdapter(adapter: ThemeToFileAdapter): ThemeFileAdapter
}