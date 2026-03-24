package com.storyadventure.app.data

val sampleQuizzes = listOf(
    Quiz(
        id = "q1",
        question = "What do plants need to grow?",
        options = listOf("Only water", "Sunlight, water, and soil", "Just soil", "Nothing"),
        correctAnswer = 1,
        explanation = "Plants need sunlight, water, and soil to grow healthy!"
    ),
    Quiz(
        id = "q2",
        question = "Which animal is a mammal?",
        options = listOf("Fish", "Frog", "Dog", "Eagle"),
        correctAnswer = 2,
        explanation = "Dogs are mammals! Mammals are warm-blooded and have fur or hair."
    ),
    Quiz(
        id = "q3",
        question = "What is the closest planet to the Sun?",
        options = listOf("Venus", "Mars", "Mercury", "Jupiter"),
        correctAnswer = 2,
        explanation = "Mercury is the closest planet to the Sun!"
    )
)

val samplePuzzles = listOf(
    Puzzle(
        id = "p1",
        question = "I have a face but no eyes. I have hands but no arms. What am I?",
        answer = "clock",
        hint = "You might have one on your wall",
        rewardGems = 15
    ),
    Puzzle(
        id = "p2",
        question = "What comes once in a minute, twice in a moment, but never in a thousand years?",
        answer = "m",
        hint = "It's a letter!",
        rewardGems = 20
    )
)