package com.storyadventure.app.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.storyadventure.app.data.Quiz

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    quiz: Quiz,
    currentGems: Int,
    onCorrect: (Int) -> Unit,
    onWrong: () -> Unit,
    onSkip: () -> Unit,
    onBack: () -> Unit
) {
    var selectedAnswer by remember { mutableStateOf<Int?>(null) }
    var showResult by remember { mutableStateOf(false) }
    
    val isCorrect = selectedAnswer == quiz.correctAnswer
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Quiz Time!") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                },
                actions = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Icon(
                            Icons.Default.Star,
                            contentDescription = "Gems",
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "$currentGems",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Progress indicator
            LinearProgressIndicator(
                progress = { if (showResult) 1f else 0.5f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Question
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Text(
                    text = quiz.question,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(20.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Options
            quiz.options.forEachIndexed { index, option ->
                val backgroundColor by animateColorAsState(
                    targetValue = when {
                        showResult && index == quiz.correctAnswer -> MaterialTheme.colorScheme.primaryContainer
                        showResult && selectedAnswer == index && !isCorrect -> MaterialTheme.colorScheme.errorContainer
                        selectedAnswer == index -> MaterialTheme.colorScheme.secondaryContainer
                        else -> MaterialTheme.colorScheme.surface
                    },
                    label = "optionColor"
                )
                
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable(enabled = !showResult) {
                            selectedAnswer = index
                            showResult = true
                            if (isCorrect) {
                                onCorrect(10)
                            } else {
                                onWrong()
                            }
                        },
                    colors = CardDefaults.cardColors(containerColor = backgroundColor)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = option,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.weight(1f)
                        )
                        if (showResult && index == quiz.correctAnswer) {
                            Text("✓", color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Result/Explanation
            if (showResult) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isCorrect) MaterialTheme.colorScheme.tertiaryContainer
                        else MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = if (isCorrect) "🎉 Correct! +10 Gems" else "❌ Not quite!",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = quiz.explanation,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (!isCorrect) {
                        OutlinedButton(
                            onClick = onSkip,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Continue Anyway")
                        }
                    }
                    Button(
                        onClick = onSkip,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(if (isCorrect) "Great!" else "Next Question")
                    }
                }
            }
        }
    }
}