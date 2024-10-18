package com.therxmv.telegramthemer.di

import com.therxmv.telegramthemer.ui.editor.ThemeEditorActivity
import com.therxmv.telegramthemer.ui.editor.di.ThemeEditorPresenterModule
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.hilt.android.scopes.ActivityScoped

@Module
abstract class ActivityModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [ThemeEditorPresenterModule::class])
    abstract fun contributesThemeEditorActivity(): ThemeEditorActivity
}