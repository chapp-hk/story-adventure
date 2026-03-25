package com.storyadventure.app.data

import kotlinx.coroutines.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.coroutines.flow.*

private const val STORAGE_KEY = "game_state"

@Serializable
data class StoryProgress(
    val currentChapterIndex: Int = 0,
    val currentSceneIndex: Int = 0,
    val completedScenes: List<String> = emptyList()
)

@Serializable
data class GameState(
    val gems: Int = 0,
    val achievements: List<String> = emptyList(),
    val storyProgress: Map<String, StoryProgress> = emptyMap(),
    val quizzesCompleted: Int = 0,
    val puzzlesSolved: Int = 0,
    val storiesCompletedCount: Int = 0
)

object GameStorage {
    private var state = GameState()
    private var storage: Storage? = null
    private val json = Json { ignoreUnknownKeys = true }
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private var initialized = false
    
    // Loading state for UI
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    fun initialize(storageImpl: Storage) {
        storage = storageImpl
        if (!initialized) {
            initialized = true
            scope.launch {
                loadState()
                _isLoading.value = false
            }
        }
    }
    
    private suspend fun loadState() {
        storage?.let { s ->
            try {
                s.load(STORAGE_KEY)?.let { jsonString ->
                    json.decodeFromString<GameState>(jsonString).also { state = it }
                }
            } catch (e: Exception) {
                // Invalid data, start fresh
            }
        }
    }
    
    private fun saveState() {
        scope.launch {
            storage?.save(STORAGE_KEY, json.encodeToString(GameState.serializer(), state))
        }
    }
    
    fun getState(): GameState = state
    
    fun addGems(amount: Int): GameState {
        state = state.copy(gems = state.gems + amount)
        checkAchievements()
        saveState()
        return state
    }
    
    fun unlockAchievement(id: String): GameState {
        if (id !in state.achievements) {
            state = state.copy(achievements = state.achievements + id)
            saveState()
        }
        return state
    }
    
    fun incrementQuizzes(): GameState {
        state = state.copy(quizzesCompleted = state.quizzesCompleted + 1)
        if (state.quizzesCompleted >= 5) {
            unlockAchievement("quiz_whiz")
        }
        saveState()
        return state
    }
    
    fun incrementPuzzles(): GameState {
        state = state.copy(puzzlesSolved = state.puzzlesSolved + 1)
        if (state.puzzlesSolved >= 3) {
            unlockAchievement("puzzle_solver")
        }
        saveState()
        return state
    }
    
    fun incrementStoriesCompleted(): GameState {
        state = state.copy(storiesCompletedCount = state.storiesCompletedCount + 1)
        if (state.storiesCompletedCount >= 1) {
            unlockAchievement("first_story")
        }
        if (state.storiesCompletedCount >= 3) {
            unlockAchievement("explorer")
        }
        saveState()
        return state
    }
    
    private fun checkAchievements() {
        if (state.gems >= 10) unlockAchievement("gem_collector_10")
        if (state.gems >= 50) unlockAchievement("gem_collector_50")
        if (state.gems >= 100) unlockAchievement("gem_collector_100")
    }
    
    fun getAchievements(): List<Achievement> {
        return getAllAchievements().map { achievement ->
            achievement.copy(isUnlocked = achievement.id in state.achievements)
        }
    }
    
    fun saveStoryProgress(storyId: String, chapterIndex: Int, sceneIndex: Int, completedSceneId: String? = null) {
        val current = state.storyProgress[storyId] ?: StoryProgress()
        val updatedCompletedScenes = if (completedSceneId != null && completedSceneId !in current.completedScenes) {
            current.completedScenes + completedSceneId
        } else {
            current.completedScenes
        }
        
        state = state.copy(
            storyProgress = state.storyProgress + (
                storyId to StoryProgress(
                    currentChapterIndex = chapterIndex,
                    currentSceneIndex = sceneIndex,
                    completedScenes = updatedCompletedScenes
                )
            )
        )
        saveState()
    }
    
    fun getStoryProgress(storyId: String): StoryProgress? {
        return state.storyProgress[storyId]
    }
    
    fun clearStoryProgress(storyId: String) {
        state = state.copy(storyProgress = state.storyProgress - storyId)
        saveState()
    }
    
    fun resetAll() {
        state = GameState()
        saveState()
    }
}