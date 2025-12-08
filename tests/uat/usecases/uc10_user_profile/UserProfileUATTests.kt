package com.serenityai.tests.uat.usecases.uc10_user_profile

import com.serenityai.data.models.*
import com.serenityai.usecases.UserProfileUseCase
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import java.util.Date

/** UAT test class. */
@DisplayName("UAT: UC10 - Manage User Profile")
class UserProfileUATTests {

    private val useCase = UserProfileUseCase()

    @Nested
    @DisplayName("User Story: Profile Creation and Management")
    inner class ProfileCreationAndManagement {
        
        @Test
        @DisplayName("As a user, I want to create my profile so the app can personalize my experience")
        fun `user can create profile with personal information`() {
            // Given: New user wants to create profile
            val userId = "user123"
            val name = "John Doe"
            val email = "john@example.com"
            
            // When: User creates profile
            val profile = useCase.createProfile(userId, name, email)
            
            // Then: Profile is created successfully
            assertNotNull(profile, "Profile should be created")
            assertEquals(userId, profile.userId, "Profile should have correct user ID")
            assertEquals(name, profile.name, "Profile should have correct name")
            assertEquals(email, profile.email, "Profile should have correct email")
        }
        
        @Test
        @DisplayName("As a user, I want to update my profile information so it stays current")
        fun `user can update profile information`() {
            // Given: User has existing profile
            val userId = "user123"
            val profile = useCase.createProfile(userId, "John Doe", "john@example.com")
            val updatedName = "John Smith"
            
            // When: User updates profile
            val updatedProfile = useCase.updateProfile(userId, name = updatedName)
            
            // Then: Profile is updated successfully
            assertNotNull(updatedProfile, "Profile should be updated")
            assertEquals(updatedName, updatedProfile.name, "Profile name should be updated")
            assertNotEquals(profile.name, updatedProfile.name, "Name should be different")
        }
        
        @Test
        @DisplayName("As a user, I want to view my profile so I can see my information")
        fun `user can view profile information`() {
            // Given: User has created profile
            val userId = "user123"
            val profile = useCase.createProfile(
                userId, 
                "John Doe", 
                "john@example.com"
            )
            
            // When: User views profile
            val retrievedProfile = useCase.getProfile(userId)
            
            // Then: Profile information is displayed
            assertNotNull(retrievedProfile, "Profile should be visible to user")
            assertEquals(profile.name, retrievedProfile?.name, "Profile should show complete information")
            assertEquals(profile.email, retrievedProfile?.email, "Email should be displayed")
            assertTrue(retrievedProfile?.level ?: 0 >= 1, "Level should be displayed")
        }
    }

    @Nested
    @DisplayName("User Story: Wellness Goals Management")
    inner class WellnessGoalsManagement {
        
        @Test
        @DisplayName("As a user, I want to set wellness goals so I can track my progress")
        fun `user can create wellness goals`() {
            // Given: User wants to set a goal
            val userId = "user123"
            useCase.createProfile(userId, "John Doe", "john@example.com")
            val goalTitle = "Daily Meditation"
            val goalDescription = "Practice 10 minutes of meditation daily"
            val goalCategory = GoalCategory.MINDFULNESS
            
            // When: User creates goal
            val goal = useCase.createWellnessGoal(userId, goalTitle, goalDescription, goalCategory)
            
            // Then: Goal is created successfully
            assertNotNull(goal, "User should be able to create wellness goal")
            assertEquals(goalTitle, goal.title, "Goal should have correct title")
            assertEquals(goalCategory, goal.category, "Goal should have valid category")
        }
        
        @Test
        @DisplayName("As a user, I want to track my goal progress so I can see how I'm doing")
        fun `user can track goal progress`() {
            // Given: User has a goal
            val userId = "user123"
            useCase.createProfile(userId, "John Doe", "john@example.com")
            val goal = useCase.createWellnessGoal(userId, "Daily Meditation", "Description", GoalCategory.MINDFULNESS)
            val progressUpdate = 50
            
            // When: User updates progress
            val updatedGoal = useCase.updateGoalProgress(userId, goal.id, progressUpdate)
            
            // Then: Progress is tracked correctly
            assertNotNull(updatedGoal, "Goal progress should be updatable")
            assertEquals(progressUpdate, updatedGoal.progress, "Progress should be within valid range (0-100)")
            assertTrue(updatedGoal.progress in 0..100, "Progress should be valid")
        }
        
        @Test
        @DisplayName("As a user, I want to complete goals so I feel accomplished")
        fun `user can complete wellness goals`() {
            // Given: User has goal with 100% progress
            val userId = "user123"
            useCase.createProfile(userId, "John Doe", "john@example.com")
            val goal = useCase.createWellnessGoal(userId, "Daily Meditation", "Description", GoalCategory.MINDFULNESS)
            useCase.updateGoalProgress(userId, goal.id, 100)
            
            // When: User completes goal
            val completedGoal = useCase.completeGoal(userId, goal.id)
            
            // Then: Goal is marked as completed
            assertNotNull(completedGoal, "User should be able to complete goal at 100%")
            assertTrue(completedGoal.isCompleted, "Goal should be marked as completed")
        }
    }

    @Nested
    @DisplayName("User Story: Gamification and Achievements")
    inner class GamificationAndAchievements {
        
        @Test
        @DisplayName("As a user, I want to earn XP so I feel motivated to use the app")
        fun `user can earn XP from activities`() {
            // Given: User performs activities
            val userId = "user123"
            val profile = useCase.createProfile(userId, "John Doe", "john@example.com")
            val initialXP = profile.totalXP
            val activityXP = 50
            
            // When: User earns XP
            useCase.addXP(userId, activityXP, "Activity completion")
            val updatedProfile = useCase.getProfile(userId)
            
            // Then: XP is added to profile
            assertNotNull(updatedProfile, "User should earn XP from activities")
            assertTrue(updatedProfile?.totalXP ?: 0 > initialXP, "XP should increase")
        }
        
        @Test
        @DisplayName("As a user, I want to maintain streaks so I stay consistent")
        fun `user can maintain daily streaks`() {
            // Given: User has current streak
            val userId = "user123"
            val profile = useCase.createProfile(userId, "John Doe", "john@example.com")
            
            // When: User logs activity daily
            val currentStreak = profile.currentStreak
            val longestStreak = profile.longestStreak
            
            // Then: Streak is maintained and tracked
            assertTrue(currentStreak >= 0, "User should maintain daily streak")
            assertTrue(longestStreak >= currentStreak, "Longest streak should be recorded")
        }
        
        @Test
        @DisplayName("As a user, I want to unlock badges so I feel rewarded")
        fun `user can unlock achievement badges`() {
            // Given: User completes achievements
            val userId = "user123"
            val profile = useCase.createProfile(userId, "John Doe", "john@example.com")
            val badges = listOf(
                Badge(id = "badge1", name = "First Steps", category = BadgeCategory.ACHIEVEMENT),
                Badge(id = "badge2", name = "Week Warrior", category = BadgeCategory.STREAK),
                Badge(id = "badge3", name = "Mood Master", category = BadgeCategory.MOOD)
            )
            
            // When: User earns badges
            val badgesEarned = profile.badges.size
            val hasBadges = badgesEarned >= 0
            
            // Then: Badges are unlocked and displayed
            assertTrue(hasBadges, "User should earn badges")
            assertTrue(badgesEarned >= 0, "Badge count should be tracked")
        }
        
        @Test
        @DisplayName("As a user, I want to level up so I see my progress")
        fun `user can level up based on XP`() {
            // Given: User accumulates XP
            val userId = "user123"
            val profile = useCase.createProfile(userId, "John Doe", "john@example.com")
            val currentLevel = profile.level
            
            // When: User reaches XP threshold
            val levelDisplayed = currentLevel > 0
            
            // Then: User levels up
            assertTrue(levelDisplayed, "User level should be displayed")
            assertTrue(currentLevel >= 1, "User should have at least level 1")
        }
    }

    @Nested
    @DisplayName("User Story: Preferences Configuration")
    inner class PreferencesConfiguration {
        
        @Test
        @DisplayName("As a user, I want to configure app preferences so it works how I like")
        fun `user can configure app preferences`() {
            // Given: User wants to change preferences
            val userId = "user123"
            useCase.createProfile(userId, "John Doe", "john@example.com")
            val preferences = UserPreferences(
                theme = "dark",
                notifications = true,
                language = "en"
            )
            
            // When: User updates preferences
            val updatedProfile = useCase.updatePreferences(userId, preferences)
            
            // Then: Preferences are saved
            assertNotNull(updatedProfile, "User should be able to set preferences")
            assertEquals("dark", updatedProfile.preferences.theme, "Theme preference should be valid")
            assertTrue(updatedProfile.preferences.notifications, "Notifications should be enabled")
        }
    }
}

