package com.therxmv.telegramthemer.data.di

import com.therxmv.telegramthemer.data.adapter.ThemeToFileAdapter
import com.therxmv.telegramthemer.data.adapter.ThemeToPreviewAdapter
import com.therxmv.telegramthemer.data.values.ThemeValuesProvider
import com.therxmv.telegramthemer.domain.adapter.PreviewColorsAdapter
import com.therxmv.telegramthemer.domain.adapter.ThemeFileAdapter
import com.therxmv.telegramthemer.domain.values.ThemeValues
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