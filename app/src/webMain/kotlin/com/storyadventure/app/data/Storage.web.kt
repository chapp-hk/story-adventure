package com.storyadventure.app.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

actual fun createStorage(): Storage = WebStorage()

private class WebStorage : Storage {
    override suspend fun save(key: String, json: String) = withContext(Dispatchers.Main) {
        js("localStorage.setItem(key, json)")
    }

    override suspend fun load(key: String): String? = withContext(Dispatchers.Main) {
        js("localStorage.getItem(key)") as? String
    }

    override suspend fun remove(key: String) = withContext(Dispatchers.Main) {
        js("localStorage.removeItem(key)")
    }
}