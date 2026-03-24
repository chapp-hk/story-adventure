package com.storyadventure.app

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.storyadventure.app.data.Story
import com.storyadventure.app.data.getSampleStories
import com.storyadventure.app.ui.screens.HomeScreen
import com.storyadventure.app.ui.screens.StoryReaderScreen

enum class Screen {
    HOME,
    READER
}

@Composable
fun App() {
    var currentScreen by remember { mutableStateOf(Screen.HOME) }
    var selectedStory by remember { mutableStateOf<Story?>(null) }
    var currentChapterIndex by remember { mutableIntStateOf(0) }
    var currentSceneIndex by remember { mutableIntStateOf(0) }
    var gems by remember { mutableIntStateOf(0) }
    
    val stories = remember { getSampleStories() }
    
    when (currentScreen) {
        Screen.HOME -> {
            HomeScreen(
                stories = stories,
                onStoryClick = { story ->
                    selectedStory = story
                    currentChapterIndex = 0
                    currentSceneIndex = 0
                    currentScreen = Screen.READER
                }
            )
        }
        
        Screen.READER -> {
            selectedStory?.let { story ->
                StoryReaderScreen(
                    story = story,
                    currentChapterIndex = currentChapterIndex,
                    currentSceneIndex = currentSceneIndex,
                    gems = gems,
                    onBack = {
                        if (currentScreen == Screen.READER) {
                            currentScreen = Screen.HOME
                            selectedStory = null
                        }
                    },
                    onChoiceSelected = { choice ->
                        // Find next scene in current chapter
                        val chapter = story.chapters.getOrNull(currentChapterIndex)
                        val currentScene = chapter?.scenes?.getOrNull(currentSceneIndex)
                        val nextSceneIndex = chapter?.scenes?.indexOfFirst { it.id == choice.targetSceneId } ?: -1
                        
                        if (nextSceneIndex >= 0) {
                            currentSceneIndex = nextSceneIndex
                            gems += choice.rewardGems
                        } else {
                            // Move to next chapter or end
                            if (currentChapterIndex < story.chapters.size - 1) {
                                currentChapterIndex++
                                currentSceneIndex = 0
                            } else {
                                currentScreen = Screen.HOME
                            }
                        }
                    }
                )
            }
        }
    }
}