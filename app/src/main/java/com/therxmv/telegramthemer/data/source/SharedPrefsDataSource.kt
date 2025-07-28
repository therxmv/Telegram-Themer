package com.therxmv.telegramthemer.data.source

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.therxmv.telegramthemer.domain.model.ThemeState
import com.therxmv.telegramthemer.domain.source.SharedPrefsSource
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class SharedPrefsDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : SharedPrefsSource {

    private val themeStateKey = stringPreferencesKey("ThemeStateKey")

    override fun saveThemeState(themeState: ThemeState) {
        runBlocking {
            themeStateKey.saveValue(themeState.toJson())
        }
    }

    override fun getThemeState(): ThemeState =
        runBlocking {
            themeStateKey.getValue()?.toObject<ThemeState>() ?: ThemeState()
        }

    private suspend fun <T> Preferences.Key<T>.saveValue(value: T) {
        dataStore.edit { prefs ->
            prefs[this] = value
        }
    }

    private suspend fun <T> Preferences.Key<T>.getValue(): T? =
        dataStore.data.map { it[this] }.firstOrNull()

    private inline fun <reified T> T.toJson(): String = Json.encodeToString(this)

    private inline fun <reified T> String.toObject(): T? =
        try {
            Json.decodeFromString<T>(this)
        } catch (e: Exception) {
            null
        }
}