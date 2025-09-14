package com.serenityai.data.models

import java.util.Date

data class Challenge(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val category: ChallengeCategory = ChallengeCategory.MINDFULNESS,
    val difficulty: ChallengeDifficulty = ChallengeDifficulty.EASY,
    val duration: Int = 1, // days
    val xpReward: Int = 10,
    val requirements: List<String> = emptyList(),
    val instructions: List<String> = emptyList(),
    val isActive: Boolean = true,
    val createdAt: Date = Date()
)

enum class ChallengeCategory {
    MINDFULNESS,
    EXERCISE,
    GRATITUDE,
    SOCIAL,
    LEARNING,
    CREATIVITY,
    SLEEP,
    NUTRITION,
    STRESS_RELIEF
}

enum class ChallengeDifficulty {
    EASY,
    MEDIUM,
    HARD,
    EXPERT
}

data class UserChallenge(
    val id: String = "",
    val userId: String = "",
    val challengeId: String = "",
    val status: ChallengeStatus = ChallengeStatus.ASSIGNED,
    val progress: Int = 0,
    val startedAt: Date = Date(),
    val completedAt: Date? = null,
    val xpEarned: Int = 0
)

enum class ChallengeStatus {
    ASSIGNED,
    IN_PROGRESS,
    COMPLETED,
    SKIPPED,
    EXPIRED
}
