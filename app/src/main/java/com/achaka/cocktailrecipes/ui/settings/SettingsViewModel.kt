package com.achaka.cocktailrecipes.ui.settings

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.ViewModel

const val RECENT_CAPACITY_PREFS_KEY = "RECENT_CAP"

class SettingsViewModel(private val sharedPreferences: SharedPreferences) : ViewModel() {

    fun setRecentCapacity(capacity: Int) {
        sharedPreferences.edit {
            putInt(RECENT_CAPACITY_PREFS_KEY, capacity)
            .apply()
        }
    }

}
