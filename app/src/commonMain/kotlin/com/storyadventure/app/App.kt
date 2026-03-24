package com.storyadventure.app

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.storyadventure.app.data.*
import com.storyadventure.app.ui.screens.*

enum class Screen {
    HOME,
    READER,
    QUIZ,
    PUZZLE,
    ACHIEVEMENTS
}

@Composable
fun App() {
    var currentScreen by remember { mutableStateOf(Screen.HOME) }
    var selectedStory by remember { mutableStateOf<Story?>(null) }
    var currentChapterIndex by remember { mutableIntStateOf(0) }
    var currentSceneIndex by remember { mutableIntStateOf(0) }
    var currentQuizIndex by remember { mutableIntStateOf(0) }
    var currentPuzzleIndex by remember { mutableIntStateOf(0) }
    var gems by remember { mutableIntStateOf(0) }
    var quizCompleted by remember { mutableStateOf(false) }
    var puzzleCompleted by remember { mutableStateOf(false) }
    
    val stories = remember { getSampleStories() }
    val quizzes = remember { sampleQuizzes }
    val puzzles = remember { samplePuzzles }
    val achievements = remember { GameStorage.getAchievements() }
    
    fun addGems(amount: Int) {
        gems += amount
        GameStorage.addGems(amount)
    }
    
    when (currentScreen) {
        Screen.HOME -> {
            HomeScreen(
                stories = stories,
                gems = gems,
                achievementsCount = achievements.count { it.isUnlocked },
                onStoryClick = { story ->
                    selectedStory = story
                    currentChapterIndex = 0
                    currentSceneIndex = 0
                    currentScreen = Screen.READER
                },
                onAchievementsClick = {
                    currentScreen = Screen.ACHIEVEMENTS
                }
            )
        }
        
        Screen.READER -> {
            selectedStory?.let { story ->
                val chapter = story.chapters.getOrNull(currentChapterIndex)
                val scene = chapter?.scenes?.getOrNull(currentSceneIndex)
                
                // Trigger quiz or puzzle after completing a scene (30% chance)
                LaunchedEffect(scene) {
                    if (!quizCompleted && !puzzleCompleted && scene != null && scene.choices.isEmpty() == false) {
                        if ((0..10).random() < 3) {
                            if (currentQuizIndex < quizzes.size) {
                                currentScreen = Screen.QUIZ
                            } else if (currentPuzzleIndex < puzzles.size) {
                                currentScreen = Screen.PUZZLE
                            }
                        }
                    }
                }
                
                StoryReaderScreen(
                    story = story,
                    currentChapterIndex = currentChapterIndex,
                    currentSceneIndex = currentSceneIndex,
                    gems = gems,
                    onBack = {
                        currentScreen = Screen.HOME
                        selectedStory = null
                    },
                    onChoiceSelected = { choice ->
                        val nextSceneIndex = chapter?.scenes?.indexOfFirst { it.id == choice.targetSceneId } ?: -1
                        
                        if (nextSceneIndex >= 0) {
                            currentSceneIndex = nextSceneIndex
                            addGems(choice.rewardGems)
                        } else {
                            // End of current chapter, move to next or finish
                            if (currentChapterIndex < story.chapters.size - 1) {
                                quizCompleted = false
                                puzzleCompleted = false
                                currentChapterIndex++
                                currentSceneIndex = 0
                            } else {
                                // Story completed
                                GameStorage.incrementStoriesCompleted()
                                currentScreen = Screen.HOME
                                selectedStory = null
                            }
                        }
                    }
                )
            }
        }
        
        Screen.QUIZ -> {
            val quiz = quizzes.getOrNull(currentQuizIndex)
            if (quiz != null) {
                QuizScreen(
                    quiz = quiz,
                    currentGems = gems,
                    onCorrect = { reward ->
                        addGems(reward)
                        GameStorage.incrementQuizzes()
                        quizCompleted = true
                        currentScreen = Screen.READER
                    },
                    onWrong = {
                        quizCompleted = true
                        currentScreen = Screen.READER
                    },
                    onSkip = {
                        currentQuizIndex++
                        if (currentQuizIndex >= quizzes.size) {
                            currentScreen = Screen.READER
                        }
                    },
                    onBack = { currentScreen = Screen.READER }
                )
            } else {
                currentScreen = Screen.READER
            }
        }
        
        Screen.PUZZLE -> {
            val puzzle = puzzles.getOrNull(currentPuzzleIndex)
            if (puzzle != null) {
                PuzzleScreen(
                    puzzle = puzzle,
                    currentGems = gems,
                    onSolved = { reward ->
                        addGems(reward)
                        GameStorage.incrementPuzzles()
                        puzzleCompleted = true
                        currentScreen = Screen.READER
                    },
                    onSkip = {
                        currentPuzzleIndex++
                        if (currentPuzzleIndex >= puzzles.size) {
                            currentScreen = Screen.READER
                        }
                    },
                    onBack = { currentScreen = Screen.READER }
                )
            } else {
                currentScreen = Screen.READER
            }
        }
        
        Screen.ACHIEVEMENTS -> {
            AchievementsScreen(
                achievements = achievements,
                onBack = { currentScreen = Screen.HOME }
            )
        }
    }
}