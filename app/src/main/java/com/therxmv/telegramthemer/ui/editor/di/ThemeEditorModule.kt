package com.therxmv.telegramthemer.ui.editor.di

import com.therxmv.telegramthemer.ui.editor.ThemeEditorContract
import com.therxmv.telegramthemer.ui.editor.ThemeEditorPresenter
import dagger.Binds
import dagger.Module
import dagger.hilt.android.scopes.ActivityScoped

@Module
abstract class ThemeEditorModule {

    @ActivityScoped
    @Binds
    abstract fun bindsThemeEditorPresenter(presenter: ThemeEditorPresenter): ThemeEditorContract.Presenter
}