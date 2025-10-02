package com.syriantrivia.data.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Singleton
class PreferencesManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val sharedPrefs: SharedPreferences =
        context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    private val languageKey = stringPreferencesKey("language")

    val languageFlow: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[languageKey] ?: "en"
        }

    suspend fun saveLanguage(languageCode: String) {
        // Save to both DataStore and SharedPreferences
        context.dataStore.edit { preferences ->
            preferences[languageKey] = languageCode
        }

        sharedPrefs.edit().putString("language", languageCode).apply()
    }
}
