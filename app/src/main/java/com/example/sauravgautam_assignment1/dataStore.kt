package com.example.sauravgautam_assignment1

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class dataStoreST (private val context: Context) {

    companion object {
        private val Context.dataStoreSt: DataStore<Preferences> by preferencesDataStore("StudentInfo")

        val stUsernameKey = stringPreferencesKey("student_username")
        val stEmailKey = stringPreferencesKey("student_email")
        val stIdKey = stringPreferencesKey("student_id")
    }

    val getUsername: Flow<String?> = context.dataStoreSt.data
        .map { preferences ->
            preferences[stUsernameKey] ?: ""
        }

    val getEmail: Flow<String?> = context.dataStoreSt.data
        .map { preferences ->
            preferences[stEmailKey] ?: ""
        }

    val getID: Flow<String?> = context.dataStoreSt.data
        .map { preferences ->
            preferences[stIdKey] ?: ""
        }

    suspend fun saveInfo(username: String, email: String, id: String) {
        context.dataStoreSt.edit { preferences ->
            preferences[stUsernameKey] = username
            preferences[stEmailKey] = email
            preferences[stIdKey] = id
        }
    }

    suspend fun clearData() {
        context.dataStoreSt.edit { preferences ->
            preferences.clear()
        }
    }
}
