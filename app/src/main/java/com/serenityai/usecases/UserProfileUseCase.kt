package com.serenityai.usecases

import com.serenityai.data.models.*
import java.util.Date

/** UC10: User profile management system for creating, updating, and managing personal information, wellness goals, preferences, and achievements. */
class UserProfileUseCase {
    
    private val profiles = mutableMapOf<String, UserProfile>()
    
    /** Creates a new user profile. */
    fun createProfile(
        userId: String,
        name: String,
        email: String,
        dateOfBirth: Date? = null
    ): UserProfile {
        require(userId.isNotBlank()) { "User ID cannot be empty" }
        require(name.isNotBlank()) { "Name cannot be empty" }
        require(email.isNotBlank() && email.contains("@")) { "Valid email is required" }
        
        val profile = UserProfile(
            id = "profile_${System.currentTimeMillis()}",
            userId = userId,
            name = name,
            email = email,
            dateOfBirth = dateOfBirth,
            createdAt = Date(),
            updatedAt = Date()
        )
        
        profiles[userId] = profile
        return profile
    }
    
    /** Updates user profile information. */
    fun updateProfile(
        userId: String,
        name: String? = null,
        email: String? = null,
        dateOfBirth: Date? = null
    ): UserProfile? {
        val profile = profiles[userId] ?: return null
        
        val updatedProfile = profile.copy(
            name = name ?: profile.name,
            email = email ?: profile.email,
            dateOfBirth = dateOfBirth ?: profile.dateOfBirth,
            updatedAt = Date()
        )
        
        if (email != null) {
            require(email.isNotBlank() && email.contains("@")) { "Valid email is required" }
        }
        
        profiles[userId] = updatedProfile
        return updatedProfile
    }
    
    /** Gets user profile by user ID. */
    fun getProfile(userId: String): UserProfile? {
        return profiles[userId]
    }
    
    /** Creates a new wellness goal. */
    fun createWellnessGoal(
        userId: String,
        title: String,
        description: String,
        category: GoalCategory = GoalCategory.MENTAL_HEALTH,
        targetDate: Date? = null
    ): WellnessGoal {
        require(title.isNotBlank()) { "Goal title cannot be empty" }
        require(description.isNotBlank()) { "Goal description cannot be empty" }
        
        val goal = WellnessGoal(
            id = "goal_${System.currentTimeMillis()}",
            title = title,
            description = description,
            category = category,
            targetDate = targetDate,
            createdAt = Date()
        )
        
        val profile = profiles[userId] ?: throw IllegalArgumentException("Profile not found")
        val updatedGoals = profile.goals + goal
        profiles[userId] = profile.copy(goals = updatedGoals)
        
        return goal
    }
    
    /** Updates wellness goal progress. */
    fun updateGoalProgress(
        userId: String,
        goalId: String,
        progress: Int
    ): WellnessGoal? {
        require(progress in 0..100) { "Progress must be between 0 and 100" }
        
        val profile = profiles[userId] ?: return null
        val goalIndex = profile.goals.indexOfFirst { it.id == goalId }
        if (goalIndex == -1) return null
        
        val goal = profile.goals[goalIndex]
        val updatedGoal = goal.copy(
            progress = progress,
            isCompleted = progress >= 100
        )
        
        val updatedGoals = profile.goals.toMutableList()
        updatedGoals[goalIndex] = updatedGoal
        profiles[userId] = profile.copy(goals = updatedGoals)
        
        return updatedGoal
    }
    
    /** Updates user preferences. */
    fun updatePreferences(
        userId: String,
        theme: String? = null,
        notifications: Boolean? = null,
        language: String? = null,
        privacyLevel: PrivacyLevel? = null
    ): UserProfile? {
        val profile = profiles[userId] ?: return null
        
        val updatedPreferences = profile.preferences.copy(
            theme = theme ?: profile.preferences.theme,
            notifications = notifications ?: profile.preferences.notifications,
            language = language ?: profile.preferences.language,
            privacyLevel = privacyLevel ?: profile.preferences.privacyLevel
        )
        
        val updatedProfile = profile.copy(
            preferences = updatedPreferences,
            updatedAt = Date()
        )
        
        profiles[userId] = updatedProfile
        return updatedProfile
    }
    
    /** Adds XP to user profile. */
    fun addXP(userId: String, xpAmount: Int): UserProfile? {
        require(xpAmount > 0) { "XP amount must be positive" }
        
        val profile = profiles[userId] ?: return null
        val newTotalXP = profile.totalXP + xpAmount
        val newLevel = calculateLevel(newTotalXP)
        
        val updatedProfile = profile.copy(
            totalXP = newTotalXP,
            level = newLevel,
            updatedAt = Date()
        )
        
        profiles[userId] = updatedProfile
        return updatedProfile
    }
    
    /** Updates user streak. */
    fun updateStreak(userId: String, increment: Boolean): UserProfile? {
        val profile = profiles[userId] ?: return null
        
        val newCurrentStreak = if (increment) {
            profile.currentStreak + 1
        } else {
            0
        }
        
        val newLongestStreak = maxOf(profile.longestStreak, newCurrentStreak)
        
        val updatedProfile = profile.copy(
            currentStreak = newCurrentStreak,
            longestStreak = newLongestStreak,
            updatedAt = Date()
        )
        
        profiles[userId] = updatedProfile
        return updatedProfile
    }
    
    /** Gets user's wellness goals. */
    fun getWellnessGoals(
        userId: String,
        completed: Boolean? = null
    ): List<WellnessGoal> {
        val profile = profiles[userId] ?: return emptyList()
        
        return if (completed != null) {
            profile.goals.filter { it.isCompleted == completed }
        } else {
            profile.goals
        }
    }
    
    /** Calculates user level based on total XP. */
    private fun calculateLevel(totalXP: Int): Int {
        return (totalXP / 1000) + 1
    }
}


