package com.therxmv.telegramthemer.di

import com.therxmv.telegramthemer.ui.editor.di.ThemeEditorModule
import dagger.Module

@Module(
    includes = [
        ThemeEditorModule::class,
        CoroutinesModule::class,
    ]
)
class SingletonModule