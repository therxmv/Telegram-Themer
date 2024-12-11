package com.therxmv.telegramthemer.di

import android.app.Application
import android.content.Context
import com.therxmv.telegramthemer.app.ThemerApplication
import dagger.Module
import dagger.Provides

@Module(
    includes = [
        ApplicationModule::class,
        ActivityModule::class,
        FragmentModule::class,
    ]
)
class AppModule

@Module
class ApplicationModule {

    @Provides
    fun providesApplication(application: ThemerApplication): Application = application

    @Provides
    fun providesContext(application: Application): Context = application
}