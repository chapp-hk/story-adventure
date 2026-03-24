package com.storyadventure.app.data

import kotlinx.serialization.Serializable

@Serializable
data class StoryLibrary(
    val stories: List<Story>
)

fun getSampleStories(): List<Story> = listOf(
    Story(
        id = "enchanted-forest",
        title = "The Enchanted Forest",
        description = "A magical adventure awaits! Find your way through a mysterious forest filled with talking animals and hidden treasures.",
        ageMin = 6,
        ageMax = 10,
        totalEndings = 4,
        isFree = true,
        chapters = listOf(
            Chapter(
                id = "ch1",
                title = "The Forest Entrance",
                scenes = listOf(
                    Scene(
                        id = "s1",
                        text = "You stand at the edge of a mysterious forest. The trees seem to whisper secrets, and a warm breeze invites you in. Two paths lie before you: one covered in colorful flowers, the other shrouded in shadows.",
                        choices = listOf(
                            Choice("c1", "Take the flower path", "s2", rewardGems = 5),
                            Choice("c2", "Take the shadow path", "s3", rewardGems = 10)
                        )
                    ),
                    Scene(
                        id = "s2",
                        text = "The flower path leads you to a clearing where friendly rabbits are playing. They offer to guide you deeper into the forest.",
                        choices = listOf(
                            Choice("c3", "Follow the rabbits", "s4", rewardGems = 5),
                            Choice("c4", "Thank them and continue alone", "s5", rewardGems = 0)
                        )
                    ),
                    Scene(
                        id = "s3",
                        text = "The shadow path is tricky! You find a hidden key under a mushroom. A wise owl watches from above.",
                        choices = listOf(
                            Choice("c5", "Ask the owl about the key", "s6", rewardGems = 15),
                            Choice("c6", "Keep the key and move on", "s7", rewardGems = 10)
                        )
                    ),
                    Scene(
                        id = "s4",
                        text = "The rabbits lead you to a magical spring. The water glows with a soft blue light.",
                        choices = listOf(
                            Choice("c7", "Drink from the spring", "s_end_good", rewardGems = 20),
                            Choice("c8", "Just admire the view", "s_end_neutral", rewardGems = 5)
                        )
                    ),
                    Scene(
                        id = "s5",
                        text = "Walking alone, you find a treasure chest! But it's locked...",
                        choices = listOf(
                            Choice("c9", "Try to open it", "s_end_neutral", rewardGems = 10)
                        )
                    ),
                    Scene(
                        id = "s6",
                        text = "The owl tells you the key opens the Treasure Cave! You're getting close to an amazing adventure.",
                        choices = listOf(
                            Choice("c10", "Go to the Treasure Cave", "s_end_good", rewardGems = 25)
                        )
                    ),
                    Scene(
                        id = "s7",
                        text = "You continue through the forest and find a peaceful clearing where you can rest.",
                        choices = listOf(
                            Choice("c11", "Rest and enjoy the peace", "s_end_neutral", rewardGems = 5)
                        )
                    ),
                    Scene(
                        id = "s_end_good",
                        text = "Congratulations! You've completed your adventure in the Enchanted Forest! 🌟",
                        choices = emptyList()
                    ),
                    Scene(
                        id = "s_end_neutral",
                        text = "Your adventure in the Enchanted Forest has come to an end. You've learned something new today!",
                        choices = emptyList()
                    )
                )
            )
        )
    ),
    Story(
        id = "space-explorer",
        title = "Space Explorer",
        description = " Blast off to the stars! Explore distant planets and meet alien friends in this cosmic adventure.",
        ageMin = 8,
        ageMax = 12,
        totalEndings = 3,
        isFree = false,
        chapters = listOf(
            Chapter(
                id = "ch1",
                title = "Launch Day",
                scenes = listOf(
                    Scene(
                        id = "s1",
                        text = "You're inside your spaceship, ready for launch! Mission Control counts down: 3... 2... 1... BLAST OFF!",
                        choices = listOf(
                            Choice("c1", "Head to Mars first", "s2", rewardGems = 5),
                            Choice("c2", "Visit the Moon", "s3", rewardGems = 5)
                        )
                    ),
                    Scene(
                        id = "s2",
                        text = "You arrive at Mars! The red planet is dusty and mysterious. You spot something shiny in a crater.",
                        choices = listOf(
                            Choice("c3", "Land and investigate", "s_end_good", rewardGems = 20),
                            Choice("c4", "Orbit and take photos", "s_end_neutral", rewardGems = 10)
                        )
                    ),
                    Scene(
                        id = "s3",
                        text = "The Moon is gray and quiet. Suddenly, your radar detects movement!",
                        choices = listOf(
                            Choice("c5", "Investigate the signal", "s_end_alien", rewardGems = 25),
                            Choice("c6", "Return to Earth", "s_end_early", rewardGems = 5)
                        )
                    ),
                    Scene(
                        id = "s_end_good",
                        text = "Amazing! You discovered alien crystals on Mars! You're a hero! 🚀",
                        choices = emptyList()
                    ),
                    Scene(
                        id = "s_end_neutral",
                        text = "You collected great photos of Mars. Ready for your next space mission!",
                        choices = emptyList()
                    ),
                    Scene(
                        id = "s_end_alien",
                        text = "You met friendly Moon aliens! They gave you a gift from their planet! 🎁",
                        choices = emptyList()
                    ),
                    Scene(
                        id = "s_end_early",
                        text = "You decided to play it safe and return home. There's always next time!",
                        choices = emptyList()
                    )
                )
            )
        )
    )
)