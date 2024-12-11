package com.therxmv.telegramthemer.data.di

import com.therxmv.telegramthemer.data.theme.ThemeValues
import com.therxmv.telegramthemer.data.theme.ThemeValuesProvider
import com.therxmv.telegramthemer.data.theme.file.ThemeToFileAdapter
import com.therxmv.telegramthemer.data.theme.preview.ThemeToPreviewAdapter
import com.therxmv.telegramthemer.domain.adapter.PreviewColorsAdapter
import com.therxmv.telegramthemer.domain.adapter.ThemeFileAdapter
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