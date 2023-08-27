package com.therxmv.telegramthemer

import android.app.Application
import com.tencent.mmkv.MMKV

class ThemerApp: Application() {
    override fun onCreate() {
        super.onCreate()
        MMKV.initialize(this)
    }
}