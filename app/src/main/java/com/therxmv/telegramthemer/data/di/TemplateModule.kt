package com.therxmv.telegramthemer.data.di

import com.therxmv.telegramthemer.data.source.TemplateLocalDataSource
import com.therxmv.telegramthemer.data.source.TemplateRemoteDataSource
import com.therxmv.telegramthemer.domain.source.TemplateLocalSource
import com.therxmv.telegramthemer.domain.source.TemplateRemoteSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
abstract class TemplateModule {

    @Singleton
    @Binds
    abstract fun bindsTemplateRemoteSource(source: TemplateRemoteDataSource): TemplateRemoteSource

    @Singleton
    @Binds
    abstract fun bindsTemplateLocalSource(source: TemplateLocalDataSource): TemplateLocalSource

    companion object {
        @Provides
        @Singleton
        fun providesOkHttpClient(): OkHttpClient =
            OkHttpClient.Builder()
                .connectTimeout(8, TimeUnit.SECONDS)
                .readTimeout(8, TimeUnit.SECONDS)
                .build()
    }
}
