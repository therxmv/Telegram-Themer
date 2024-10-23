package com.therxmv.telegramthemer.di

import com.therxmv.telegramthemer.ui.editor.di.ThemeEditorModule
import com.therxmv.telegramthemer.ui.editor.di.ThemeValuesModule
import dagger.Module

@Module(
    includes = [
        ThemeEditorModule::class,
        CoroutinesModule::class,
        ThemeValuesModule::class,
    ]
)
class SingletonModule