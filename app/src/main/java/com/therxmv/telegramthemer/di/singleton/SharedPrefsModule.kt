package com.therxmv.telegramthemer.di.singleton

import com.tencent.mmkv.MMKV
import com.therxmv.telegramthemer.data.source.SharedPrefsDataSource
import com.therxmv.telegramthemer.data.source.SharedPrefsSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SharedPrefsModule {

    @Singleton
    @Provides
    fun providesMmkv(): MMKV = MMKV.defaultMMKV()

    @Singleton
    @Provides
    fun providesSharedPrefsDataSource(mmkv: MMKV): SharedPrefsSource = SharedPrefsDataSource(mmkv)
}