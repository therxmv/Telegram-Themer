package com.therxmv.telegramthemer.di.singleton

import com.therxmv.telegramthemer.data.di.SharedPrefsModule
import com.therxmv.telegramthemer.data.di.ThemeValuesModule
import com.therxmv.telegramthemer.ui.editor.di.ThemeEditorModule
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