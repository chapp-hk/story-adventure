package com.storyadventure.app.data

import kotlinx.serialization.Serializable

@Serializable
data class Story(
    val id: String,
    val title: String,
    val description: String,
    val ageMin: Int,
    val ageMax: Int,
    val coverImage: String? = null,
    val chapters: List<Chapter>,
    val totalEndings: Int,
    val isFree: Boolean = true
)

@Serializable
data class Chapter(
    val id: String,
    val title: String,
    val scenes: List<Scene>,
    val quiz: Quiz? = null,
    val puzzle: Puzzle? = null
)

@Serializable
data class Scene(
    val id: String,
    val text: String,
    val imageUrl: String? = null,
    val audioUrl: String? = null,
    val choices: List<Choice> = emptyList()
)

@Serializable
data class Choice(
    val id: String,
    val text: String,
    val targetSceneId: String,
    val rewardGems: Int = 0
)

@Serializable
data class Quiz(
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
)

@Serializable
data class Puzzle(
    val type: String, // "pattern", "sequence", "riddle"
    val question: String,
    val answer: String,
    val hint: String? = null
)

@Serializable
data class StoryProgress(
    val storyId: String,
    val currentChapterId: String,
    val currentSceneId: String,
    val choicesMade: List<String> = emptyList(),
    val gems: Int = 0,
    val unlockedEndings: List<String> = emptyList(),
    val completedChapters: List<String> = emptyList()
)