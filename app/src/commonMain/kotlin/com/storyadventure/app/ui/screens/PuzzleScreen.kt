package com.storyadventure.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.storyadventure.app.data.Puzzle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PuzzleScreen(
    puzzle: Puzzle,
    currentGems: Int,
    onSolved: (Int) -> Unit,
    onSkip: () -> Unit,
    onBack: () -> Unit
) {
    var userAnswer by remember { mutableStateOf("") }
    var showHint by remember { mutableStateOf(false) }
    var showResult by remember { mutableStateOf(false) }
    var isCorrect by remember { mutableStateOf(false) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Brain Teaser!") },
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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Puzzle icon
            Card(
                modifier = Modifier.size(100.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "🧩",
                        style = MaterialTheme.typography.displayLarge
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Question
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Text(
                    text = puzzle.question,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(20.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Answer input
            OutlinedTextField(
                value = userAnswer,
                onValueChange = {
                    userAnswer = it
                    showResult = false
                },
                label = { Text("Your Answer") },
                modifier = Modifier.fillMaxWidth(),
                enabled = !showResult,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        isCorrect = userAnswer.lowercase().trim() == puzzle.answer.lowercase()
                        showResult = true
                        if (isCorrect) {
                            onSolved(puzzle.rewardGems)
                        }
                    }
                ),
                singleLine = true
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Hint button
            if (!showHint && !showResult) {
                TextButton(onClick = { showHint = true }) {
                    Icon(
                        Icons.Default.Info,
                        contentDescription = "Hint",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Need a hint?")
                }
            }
            
            // Hint display
            if (showHint) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Info,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.tertiary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = puzzle.hint,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Result
            if (showResult) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isCorrect) MaterialTheme.colorScheme.primaryContainer
                        else MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = if (isCorrect) "🎉 Correct! +${puzzle.rewardGems} Gems" else "❌ Not quite!",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        if (!isCorrect) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Answer: ${puzzle.answer.replaceFirstChar { it.uppercase() }}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            // Action buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (!isCorrect) {
                    OutlinedButton(
                        onClick = onSkip,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Skip")
                    }
                }
                Button(
                    onClick = {
                        if (!showResult) {
                            isCorrect = userAnswer.lowercase().trim() == puzzle.answer.lowercase()
                            showResult = true
                            if (isCorrect) {
                                onSolved(puzzle.rewardGems)
                            }
                        } else {
                            onSkip()
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(if (showResult) "Continue" else "Check Answer")
                }
            }
        }
    }
}