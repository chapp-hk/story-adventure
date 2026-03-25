package com.storyadventure.app.data

interface Storage {
    suspend fun save(key: String, json: String)
    suspend fun load(key: String): String?
    suspend fun remove(key: String)
}

expect fun createStorage(): Storage