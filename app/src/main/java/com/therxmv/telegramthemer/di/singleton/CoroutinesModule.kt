package com.therxmv.telegramthemer.di.singleton

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
import javax.inject.Singleton

@Module
class CoroutinesModule {

    @Provides
    @Singleton
    @Named("IO")
    fun providesIOCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    @Named("Default")
    fun providesDefaultCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @Singleton
    @Named("Main")
    fun providesMainCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    @Singleton
    @Named("Unconfined")
    fun providesUnconfinedCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.Unconfined
}