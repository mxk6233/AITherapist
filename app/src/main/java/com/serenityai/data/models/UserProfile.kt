package com.serenityai.data.models

import java.util.Date

data class UserProfile(
    val id: String = "",
    val userId: String = "",
    val name: String = "",
    val email: String = "",
    val dateOfBirth: Date? = null,
    val goals: List<WellnessGoal> = emptyList(),
    val preferences: UserPreferences = UserPreferences(),
    val createdAt: Date = Date(),
    val updatedAt: Date = Date(),
    val totalXP: Int = 0,
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,
    val badges: List<Badge> = emptyList(),
    val level: Int = 1
)

data class WellnessGoal(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val category: GoalCategory = GoalCategory.MENTAL_HEALTH,
    val targetDate: Date? = null,
    val isCompleted: Boolean = false,
    val progress: Int = 0,
    val createdAt: Date = Date()
)

enum class GoalCategory {
    MENTAL_HEALTH,
    EMOTIONAL_WELLNESS,
    PHYSICAL_HEALTH,
    RELATIONSHIPS,
    CAREER,
    PERSONAL_GROWTH,
    STRESS_MANAGEMENT,
    MINDFULNESS
}

data class UserPreferences(
    val theme: String = "light",
    val notifications: Boolean = true,
    val reminderTime: String = "09:00",
    val language: String = "en",
    val privacyLevel: PrivacyLevel = PrivacyLevel.MEDIUM
)

enum class PrivacyLevel {
    LOW,
    MEDIUM,
    HIGH
}
