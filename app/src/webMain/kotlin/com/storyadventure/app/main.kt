package com.storyadventure.app

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.Window
import androidx.compose.ui.window.application
import com.storyadventure.app.data.GameStorage
import com.storyadventure.app.data.createStorage
import com.storyadventure.app.ui.theme.StoryAdventureTheme

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    GameStorage.initialize(createStorage())
    
    application {
        Window(
            title = "Story Adventure",
            onCloseRequest = ::exitApplication
        ) {
            StoryAdventureTheme {
                App()
            }
        }
    }
}