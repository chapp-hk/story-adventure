package com.storyadventure.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = StoryPurple,
    secondary = StoryOrange,
    tertiary = StoryBlue
)

private val LightColorScheme = lightColorScheme(
    primary = StoryPurple,
    secondary = StoryOrange,
    tertiary = StoryBlue
)

@Composable
fun StoryAdventureTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    
    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}