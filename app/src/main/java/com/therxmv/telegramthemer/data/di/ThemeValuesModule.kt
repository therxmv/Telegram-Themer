package com.therxmv.telegramthemer.data.di

import com.therxmv.telegramthemer.data.adapter.PlatformThemeFileAdapter
import com.therxmv.telegramthemer.data.adapter.ThemeToPreviewAdapter
import com.therxmv.telegramthemer.data.values.PlatformThemeValuesProvider
import com.therxmv.telegramthemer.data.values.ThemeColorsProvider
import com.therxmv.telegramthemer.domain.adapter.PreviewColorsAdapter
import com.therxmv.telegramthemer.domain.adapter.ThemeFileAdapter
import com.therxmv.telegramthemer.domain.values.ThemeColors
import com.therxmv.telegramthemer.domain.values.ThemeValues
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class ThemeValuesModule {

    @Singleton
    @Binds
    abstract fun bindsThemeColorsProvider(provider: ThemeColorsProvider): ThemeColors

    @Singleton
    @Binds
    abstract fun bindsThemeValuesProvider(provider: PlatformThemeValuesProvider): ThemeValues

    @Singleton
    @Binds
    abstract fun bindsThemeToPreviewAdapter(adapter: ThemeToPreviewAdapter): PreviewColorsAdapter

    @Singleton
    @Binds
    abstract fun bindsThemeToFileAdapter(adapter: PlatformThemeFileAdapter): ThemeFileAdapter
}