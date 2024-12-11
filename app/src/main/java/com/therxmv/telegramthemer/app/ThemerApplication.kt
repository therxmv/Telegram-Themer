package com.therxmv.telegramthemer.app

import com.tencent.mmkv.MMKV
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

open class ThemerApplication: DaggerApplication() {

    private lateinit var injector: Injector

    override fun onCreate() {
        super.onCreate()
        MMKV.initialize(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        injector = Injector(this@ThemerApplication)
        return injector.getUIDependencies()
    }
}