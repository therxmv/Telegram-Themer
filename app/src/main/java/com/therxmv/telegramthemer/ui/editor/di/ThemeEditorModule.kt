package com.therxmv.telegramthemer.ui.editor.di

import com.therxmv.telegramthemer.ui.editor.ThemeEditorEventProvider
import com.therxmv.telegramthemer.ui.editor.ThemeEditorEventStore
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class ThemeEditorModule {

    @Singleton
    @Binds
    abstract fun bindsThemeEditorEventStore(eventStore: ThemeEditorEventStore): ThemeEditorEventProvider
}