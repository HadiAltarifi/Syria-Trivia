package com.syriantrivia.ui.settings

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syriantrivia.data.preferences.PreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    val savedLanguage: StateFlow<String> = preferencesManager.languageFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = "en"
        )

    fun saveLanguageAndApply(languageCode: String, activity: Activity) {
        viewModelScope.launch {
            preferencesManager.saveLanguage(languageCode)

            // Apply language immediately
            val locale = Locale(languageCode)
            Locale.setDefault(locale)

            val config = activity.resources.configuration
            config.setLocale(locale)
            config.setLayoutDirection(locale)

            @Suppress("DEPRECATION")
            activity.resources.updateConfiguration(config, activity.resources.displayMetrics)

            // Recreate activity to apply changes
            activity.recreate()
        }
    }
}
