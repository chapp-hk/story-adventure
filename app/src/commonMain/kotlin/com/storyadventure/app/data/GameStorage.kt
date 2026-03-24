package com.storyadventure.app.data

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

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
    
    fun getState(): GameState = state
    
    fun addGems(amount: Int): GameState {
        state = state.copy(gems = state.gems + amount)
        checkAchievements()
        return state
    }
    
    fun unlockAchievement(id: String): GameState {
        if (id !in state.achievements) {
            state = state.copy(achievements = state.achievements + id)
        }
        return state
    }
    
    fun incrementQuizzes(): GameState {
        state = state.copy(quizzesCompleted = state.quizzesCompleted + 1)
        if (state.quizzesCompleted >= 5) {
            unlockAchievement("quiz_whiz")
        }
        return state
    }
    
    fun incrementPuzzles(): GameState {
        state = state.copy(puzzlesSolved = state.puzzlesSolved + 1)
        if (state.puzzlesSolved >= 3) {
            unlockAchievement("puzzle_solver")
        }
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
        return state
    }
    
    private fun checkAchievements() {
        // Gem achievements
        if (state.gems >= 10) unlockAchievement("gem_collector_10")
        if (state.gems >= 50) unlockAchievement("gem_collector_50")
        if (state.gems >= 100) unlockAchievement("gem_collector_100")
    }
    
    fun getAchievements(): List<Achievement> {
        return getAllAchievements().map { achievement ->
            achievement.copy(isUnlocked = achievement.id in state.achievements)
        }
    }
}