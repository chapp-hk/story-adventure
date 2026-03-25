package com.storyadventure.app.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import platform.Foundation.NSUserDefaults

actual fun createStorage(): Storage = IosStorage()

private class IosStorage : Storage {
    private val defaults = NSUserDefaults.standardUserDefaults

    override suspend fun save(key: String, json: String) = withContext(Dispatchers.Main) {
        defaults.setObject(json, forKey = key)
    }

    override suspend fun load(key: String): String? = withContext(Dispatchers.Main) {
        defaults.stringForKey(key)
    }

    override suspend fun remove(key: String) = withContext(Dispatchers.Main) {
        defaults.removeObjectForKey(key)
    }
}