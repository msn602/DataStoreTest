package com.example.msn.datastoretest.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

/**
 * Class that handles saving and retrieving layout setting preferences
 */

private const val APP_PREFERENCES_NAME = "settingsdatastoretest_preferences"

// Create a DataStore instance using the preferencesDataStore delegate, with the Context as
// receiver.
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = APP_PREFERENCES_NAME
)

class SettingsDataStore(val dataStore: DataStore<Preferences>) {
    companion object {
        val TEXT_DATA = stringPreferencesKey("text_data")
        val TEXT2_DATA = stringPreferencesKey("text2_data")
    }

    suspend fun saveToPreferencesStore(textData: String, textData2: String) {
        dataStore.edit {
            it[TEXT_DATA] = textData
            it[TEXT2_DATA] = textData2
        }
    }

    //Create flows
    val textDataFlow: Flow<String?> = dataStore.data.map {
        it[TEXT_DATA]
    }

    val text2DataFlow: Flow<String?> = dataStore.data.map {
        it[TEXT2_DATA]
    }
}
