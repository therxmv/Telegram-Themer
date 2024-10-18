package com.therxmv.telegramthemer.app

class Injector(application: ThemerApplication) {

    companion object {
        @JvmStatic
        lateinit var uiDependencies: UiDependencies
    }

    init {
        getUIDependencies(application)
    }

    fun getUIDependencies() = uiDependencies

    private fun getUIDependencies(application: ThemerApplication) {
        uiDependencies = DaggerThemerAppComponent
            .factory()
            .create(application)
    }
}