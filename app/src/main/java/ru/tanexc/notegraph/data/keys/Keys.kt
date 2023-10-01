package ru.tanexc.notegraph.data.keys

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object Keys {
    val AMOLED_MODE = booleanPreferencesKey("amoled_mode")
    val IS_DARK_MODE = booleanPreferencesKey("is_dark_mode")
    val BORDERS_ENABLED = booleanPreferencesKey("borders_enabled")
    val USE_DYNAMIC_COLORS = booleanPreferencesKey("use_dynamic_colors")
    val COLOR_TUPLE = stringPreferencesKey("color_tuple")

    val LOCAL_USER_ID = stringPreferencesKey("local_user_id")
}