package com.storyadventure.app.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

actual fun createStorage(): Storage = AndroidStorage()

private class AndroidStorage : Storage {
    private val prefs by lazy { 
        androidx.compose.ui.platform.LocalContext.current
            .getSharedPreferences("story_adventure", Context.MODE_PRIVATE)
    }

    override suspend fun save(key: String, json: String) = withContext(Dispatchers.IO) {
        prefs.edit().putString(key, json).apply()
    }

    override suspend fun load(key: String): String? = withContext(Dispatchers.IO) {
        prefs.getString(key, null)
    }

    override suspend fun remove(key: String) = withContext(Dispatchers.IO) {
        prefs.edit().remove(key).apply()
    }
}