package com.therxmv.telegramthemer.ui.editor.di

import com.therxmv.telegramthemer.ui.editor.data.PreviewColorsAdapter
import com.therxmv.telegramthemer.ui.editor.data.ThemeToPreviewAdapter
import com.therxmv.telegramthemer.ui.editor.data.ThemeValues
import com.therxmv.telegramthemer.ui.editor.data.ThemeValuesProvider
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
}