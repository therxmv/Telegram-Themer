package com.therxmv.telegramthemer.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.therxmv.telegramthemer.data.source.SharedPrefsDataSource
import com.therxmv.telegramthemer.domain.source.SharedPrefsSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

private const val DATA_STORE_NAME = "themer_data_store"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = DATA_STORE_NAME,
)

@Module
class SharedPrefsModule {

    @Singleton
    @Provides
    fun providesDataStore(context: Context): DataStore<Preferences> =
        context.dataStore

    @Singleton
    @Provides
    fun providesSharedPrefsDataSource(dataStore: DataStore<Preferences>): SharedPrefsSource =
        SharedPrefsDataSource(dataStore)
}