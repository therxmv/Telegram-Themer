package com.therxmv.telegramthemer.di.singleton

import com.therxmv.telegramthemer.ui.editor.di.ThemeEditorModule
import com.therxmv.telegramthemer.ui.editor.di.ThemeValuesModule
import dagger.Module

@Module(
    includes = [
        ThemeEditorModule::class,
        CoroutinesModule::class,
        ThemeValuesModule::class,
        SharedPrefsModule::class,
    ]
)
class SingletonModule