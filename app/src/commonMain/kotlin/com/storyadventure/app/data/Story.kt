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
    val scenes: List<Scene>
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
    val id: String,
    val question: String,
    val options: List<String>,
    val correctAnswer: Int,
    val explanation: String
)

@Serializable
data class Puzzle(
    val id: String,
    val question: String,
    val answer: String,
    val hint: String,
    val rewardGems: Int
)