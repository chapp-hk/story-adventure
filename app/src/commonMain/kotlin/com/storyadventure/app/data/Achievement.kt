package com.storyadventure.app.data

import kotlinx.serialization.Serializable

@Serializable
data class Achievement(
    val id: String,
    val title: String,
    val description: String,
    val icon: String,
    val isUnlocked: Boolean = false
)

@Serializable
data class StoryProgress(
    val storyId: String,
    val currentChapter: Int = 0,
    val currentScene: Int = 0,
    val completedChapters: List<String> = emptyList(),
    val endingsReached: List<String> = emptyList()
)

@Serializable
data class UserProgress(
    val totalGems: Int = 0,
    val storiesCompleted: List<StoryProgress> = emptyList(),
    val achievementsUnlocked: List<String> = emptyList(),
    val totalStoriesRead: Int = 0,
    val totalQuizzesPassed: Int = 0,
    val totalPuzzlesSolved: Int = 0
)

fun getAllAchievements(): List<Achievement> = listOf(
    Achievement("first_story", "First Steps", "Complete your first story", "📖"),
    Achievement("gem_collector_10", "Gem Collector", "Collect 10 gems", "💎"),
    Achievement("gem_collector_50", "Gem Master", "Collect 50 gems", "💰"),
    Achievement("gem_collector_100", "Gem Legend", "Collect 100 gems", "👑"),
    Achievement("quiz_whiz", "Quiz Whiz", "Answer 5 quizzes correctly", "🧠"),
    Achievement("puzzle_solver", "Puzzle Solver", "Solve 3 puzzles", "🧩"),
    Achievement("explorer", "Explorer", "Read 3 different stories", "🗺️"),
    Achievement("adventurer", "Adventurer", "Reach 5 different endings", "🏆")
)