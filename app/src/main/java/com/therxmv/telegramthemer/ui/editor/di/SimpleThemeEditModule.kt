package com.therxmv.telegramthemer.ui.editor.di

import com.therxmv.telegramthemer.ui.editor.simple.SimpleThemeEditContract
import com.therxmv.telegramthemer.ui.editor.simple.SimpleThemeEditPresenter
import dagger.Binds
import dagger.Module

@Module
abstract class SimpleThemeEditModule {

    @Binds
    abstract fun bindsSimpleThemeEditPresenter(presenter: SimpleThemeEditPresenter): SimpleThemeEditContract.Presenter
}