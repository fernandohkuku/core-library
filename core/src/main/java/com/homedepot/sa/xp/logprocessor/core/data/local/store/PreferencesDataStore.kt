package com.homedepot.sa.xp.logprocessor.core.data.local.store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.Preferences
import javax.inject.Inject

private const val PREFS_FILENAME = "log_processor_app_prefs"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFS_FILENAME)

class PreferencesDataStore @Inject constructor(
    private val context: Context
) {

    private val dataStore = context.dataStore

}