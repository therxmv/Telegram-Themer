package com.therxmv.telegramthemer.di

import com.therxmv.telegramthemer.ui.editor.di.SimpleThemeEditModule
import com.therxmv.telegramthemer.ui.editor.simple.SimpleThemeEditFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector(modules = [SimpleThemeEditModule::class])
    abstract fun contributesSimpleThemeEditFragment(): SimpleThemeEditFragment
}