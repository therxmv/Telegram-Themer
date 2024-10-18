package com.therxmv.telegramthemer.app

import com.therxmv.telegramthemer.di.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        AndroidInjectionModule::class,
    ]
)
interface ThemerAppComponent : UiDependencies {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: ThemerApplication): ThemerAppComponent
    }
}