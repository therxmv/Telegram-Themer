package com.therxmv.telegramthemer.ui.editor.di

import com.therxmv.telegramthemer.ui.editor.advanced.AdvancedThemeEditContract
import com.therxmv.telegramthemer.ui.editor.advanced.AdvancedThemeEditPresenter
import dagger.Binds
import dagger.Module

@Module
abstract class AdvancedThemeEditModule {

    @Binds
    abstract fun bindsAdvancedThemeEditPresenter(presenter: AdvancedThemeEditPresenter): AdvancedThemeEditContract.Presenter
}