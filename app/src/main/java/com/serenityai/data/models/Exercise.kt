package com.serenityai.data.models

import java.util.Date

data class Exercise(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val category: ExerciseCategory = ExerciseCategory.MINDFULNESS,
    val duration: Int = 0, // in minutes
    val difficulty: ExerciseDifficulty = ExerciseDifficulty.BEGINNER,
    val instructions: List<String> = emptyList(),
    val benefits: List<String> = emptyList(),
    val audioUrl: String? = null,
    val imageUrl: String? = null,
    val isCompleted: Boolean = false,
    val completedAt: Date? = null,
    val userRating: Int? = null, // 1-5 stars
    val isFavorite: Boolean = false
)

data class ExerciseSession(
    val id: String = "",
    val userId: String = "",
    val exerciseId: String = "",
    val startTime: Date = Date(),
    val endTime: Date? = null,
    val duration: Int = 0, // actual duration in minutes
    val completed: Boolean = false,
    val rating: Int? = null,
    val notes: String = "",
    val moodBefore: Int? = null,
    val moodAfter: Int? = null
)

enum class ExerciseCategory {
    MINDFULNESS,
    BREATHING,
    MEDITATION,
    STRETCHING,
    WALKING,
    JOURNALING,
    GRATITUDE,
    RELAXATION,
    ANXIETY_RELIEF,
    SLEEP_PREP
}

enum class ExerciseDifficulty {
    BEGINNER,
    INTERMEDIATE,
    ADVANCED
}
