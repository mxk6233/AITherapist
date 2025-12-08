package com.serenityai.tests.unit.usecases.uc10_user_profile

import com.serenityai.usecases.*
import com.serenityai.data.models.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import java.util.Date

/**
 * UC10: Manage User Profile - Unit Tests
 * 
 * Use Case Goal: Enable users to manage their profile information, set wellness goals,
 * configure preferences, and track achievements so they can personalize their therapy experience.
 * 
 * Key Requirements Being Tested:
 * 1. System creates and updates user profile information
 * 2. System manages wellness goals (create, update, complete, track progress)
 * 3. System configures user preferences
 * 4. System tracks achievements (XP, streaks, badges, levels)
 * 5. System validates profile data
 */
@DisplayName("UC10: Manage User Profile - Unit Tests")
class UserProfileUseCaseUnitTests {

    private lateinit var useCase: UserProfileUseCase

    @BeforeEach
    fun setUp() {
        useCase = UserProfileUseCase()
    }

    @Nested
    @DisplayName("Test Case 1: Profile Management - Validates Core UC10 Functionality")
    inner class ProfileManagementTests {
        
        @Test
        @DisplayName("UC10-REQ-1: System MUST create user profile with valid information")
        fun `system creates user profile with valid information correctly`() {
            // Given: Valid user information
            val userId = "user123"
            val name = "John Doe"
            val email = "john@example.com"
            
            // When: User creates profile
            val profile = useCase.createProfile(userId, name, email)
            
            // Then: Profile should be created successfully
            assertNotNull(profile.id, "UC10: Profile must have unique ID")
            assertEquals(userId, profile.userId, "UC10: Profile must be linked to correct user")
            assertEquals(name, profile.name, "UC10: Profile must store name correctly")
            assertEquals(email, profile.email, "UC10: Profile must store email correctly")
        }
        
        @Test
        @DisplayName("UC10-REQ-2: System MUST validate profile creation input")
        fun `system validates profile creation input correctly`() {
            // Given: Invalid input data
            // Purpose: Validate input validation
            
            // When/Then: System should reject invalid input
            assertThrows(IllegalArgumentException::class.java) {
                useCase.createProfile("", "John", "john@example.com")
            }
            
            assertThrows(IllegalArgumentException::class.java) {
                useCase.createProfile("user123", "", "john@example.com")
            }
            
            assertThrows(IllegalArgumentException::class.java) {
                useCase.createProfile("user123", "John", "invalid-email")
            }
        }
        
        @Test
        @DisplayName("UC10-REQ-3: System MUST update user profile information")
        fun `system updates user profile information correctly`() {
            // Given: Existing user profile
            val userId = "user123"
            useCase.createProfile(userId, "John Doe", "john@example.com")
            
            // When: User updates profile
            val updatedProfile = useCase.updateProfile(
                userId = userId,
                name = "John Smith",
                email = "johnsmith@example.com"
            )
            
            // Then: Profile should be updated correctly
            assertNotNull(updatedProfile, "UC10: Updated profile must be returned")
            assertEquals("John Smith", updatedProfile?.name, "UC10: Name must be updated")
            assertEquals("johnsmith@example.com", updatedProfile?.email, "UC10: Email must be updated")
        }
        
        @Test
        @DisplayName("UC10-REQ-4: System MUST retrieve user profile by ID")
        fun `system retrieves user profile by ID correctly`() {
            // Given: Existing user profile
            val userId = "user123"
            val createdProfile = useCase.createProfile(userId, "John Doe", "john@example.com")
            
            // When: User retrieves profile
            val retrievedProfile = useCase.getProfile(userId)
            
            // Then: Profile should be retrieved correctly
            assertNotNull(retrievedProfile, "UC10: Profile must be retrievable")
            assertEquals(createdProfile.id, retrievedProfile?.id, "UC10: Retrieved profile must match created profile")
            assertEquals(userId, retrievedProfile?.userId, "UC10: User ID must match")
        }
    }

    @Nested
    @DisplayName("Test Case 2: Wellness Goals Management - Validates Secondary UC10 Functionality")
    inner class WellnessGoalsTests {
        
        @Test
        @DisplayName("UC10-REQ-5: System MUST create wellness goals")
        fun `system creates wellness goals correctly`() {
            // Given: User profile exists
            val userId = "user123"
            useCase.createProfile(userId, "John Doe", "john@example.com")
            
            // When: User creates wellness goal
            val goal = useCase.createWellnessGoal(
                userId = userId,
                title = "Reduce Anxiety",
                description = "Practice mindfulness daily",
                category = GoalCategory.MENTAL_HEALTH
            )
            
            // Then: Goal should be created successfully
            assertNotNull(goal.id, "UC10: Goal must have unique ID")
            assertEquals("Reduce Anxiety", goal.title, "UC10: Goal title must be stored")
            assertEquals(GoalCategory.MENTAL_HEALTH, goal.category, "UC10: Goal category must be stored")
            assertFalse(goal.isCompleted, "UC10: New goal must not be completed")
            assertEquals(0, goal.progress, "UC10: New goal progress must be 0")
        }
        
        @Test
        @DisplayName("UC10-REQ-6: System MUST update goal progress")
        fun `system updates goal progress correctly`() {
            // Given: User profile with wellness goal
            val userId = "user123"
            useCase.createProfile(userId, "John Doe", "john@example.com")
            val goal = useCase.createWellnessGoal(userId, "Reduce Anxiety", "Practice mindfulness", GoalCategory.MENTAL_HEALTH)
            
            // When: User updates goal progress
            val updatedGoal = useCase.updateGoalProgress(userId, goal.id, 75)
            
            // Then: Goal progress should be updated
            assertNotNull(updatedGoal, "UC10: Updated goal must be returned")
            assertEquals(75, updatedGoal?.progress, "UC10: Progress must be updated to 75")
            assertFalse(updatedGoal?.isCompleted ?: true, "UC10: Goal should not be completed at 75%")
            
            // When: Progress reaches 100%
            val completedGoal = useCase.updateGoalProgress(userId, goal.id, 100)
            
            // Then: Goal should be marked as completed
            assertEquals(100, completedGoal?.progress, "UC10: Progress must be 100%")
            assertTrue(completedGoal?.isCompleted ?: false, "UC10: Goal must be marked as completed")
        }
        
        @Test
        @DisplayName("UC10-REQ-7: System MUST retrieve user's wellness goals")
        fun `system retrieves user wellness goals correctly`() {
            // Given: User profile with multiple goals
            val userId = "user123"
            useCase.createProfile(userId, "John Doe", "john@example.com")
            useCase.createWellnessGoal(userId, "Goal 1", "Description 1", GoalCategory.MENTAL_HEALTH)
            useCase.createWellnessGoal(userId, "Goal 2", "Description 2", GoalCategory.MINDFULNESS)
            
            // When: User retrieves goals
            val goals = useCase.getWellnessGoals(userId)
            
            // Then: All goals should be retrieved
            assertEquals(2, goals.size, "UC10: All goals must be retrieved")
            
            // When: Filter by completed status
            val incompleteGoals = useCase.getWellnessGoals(userId, completed = false)
            
            // Then: Only incomplete goals should be returned
            assertEquals(2, incompleteGoals.size, "UC10: Incomplete goals filter must work")
        }
    }

    @Nested
    @DisplayName("Test Case 3: Preferences and Achievements - Validates Tertiary UC10 Functionality")
    inner class PreferencesAndAchievementsTests {
        
        @Test
        @DisplayName("UC10-REQ-8: System MUST update user preferences")
        fun `system updates user preferences correctly`() {
            // Given: User profile exists
            val userId = "user123"
            useCase.createProfile(userId, "John Doe", "john@example.com")
            
            // When: User updates preferences
            val updatedProfile = useCase.updatePreferences(
                userId = userId,
                theme = "dark",
                notifications = false,
                language = "es",
                privacyLevel = PrivacyLevel.HIGH
            )
            
            // Then: Preferences should be updated
            assertNotNull(updatedProfile, "UC10: Updated profile must be returned")
            assertEquals("dark", updatedProfile?.preferences?.theme, "UC10: Theme must be updated")
            assertEquals(false, updatedProfile?.preferences?.notifications, "UC10: Notifications preference must be updated")
            assertEquals("es", updatedProfile?.preferences?.language, "UC10: Language must be updated")
            assertEquals(PrivacyLevel.HIGH, updatedProfile?.preferences?.privacyLevel, "UC10: Privacy level must be updated")
        }
        
        @Test
        @DisplayName("UC10-REQ-9: System MUST add XP and calculate level")
        fun `system adds XP and calculates level correctly`() {
            // Given: User profile exists
            val userId = "user123"
            useCase.createProfile(userId, "John Doe", "john@example.com")
            
            // When: User earns XP
            val profileAfterXP = useCase.addXP(userId, 500)
            
            // Then: XP should be added and level calculated
            assertNotNull(profileAfterXP, "UC10: Updated profile must be returned")
            assertEquals(500, profileAfterXP?.totalXP, "UC10: XP must be added")
            assertEquals(1, profileAfterXP?.level, "UC10: Level must be calculated correctly")
            
            // When: More XP is earned
            val profileAfterMoreXP = useCase.addXP(userId, 1500)
            
            // Then: Level should increase
            assertEquals(2000, profileAfterMoreXP?.totalXP, "UC10: Total XP must accumulate")
            assertEquals(3, profileAfterMoreXP?.level, "UC10: Level must increase based on XP")
        }
        
        @Test
        @DisplayName("UC10-REQ-10: System MUST update user streak")
        fun `system updates user streak correctly`() {
            // Given: User profile exists
            val userId = "user123"
            useCase.createProfile(userId, "John Doe", "john@example.com")
            
            // When: User increments streak
            val profileAfterStreak = useCase.updateStreak(userId, increment = true)
            
            // Then: Streak should be incremented
            assertNotNull(profileAfterStreak, "UC10: Updated profile must be returned")
            assertEquals(1, profileAfterStreak?.currentStreak, "UC10: Current streak must be incremented")
            assertEquals(1, profileAfterStreak?.longestStreak, "UC10: Longest streak must be updated")
            
            // When: Streak continues
            val profileAfterMoreStreak = useCase.updateStreak(userId, increment = true)
            
            // Then: Streak should continue
            assertEquals(2, profileAfterMoreStreak?.currentStreak, "UC10: Streak must continue")
            assertEquals(2, profileAfterMoreStreak?.longestStreak, "UC10: Longest streak must update")
            
            // When: Streak is reset
            val profileAfterReset = useCase.updateStreak(userId, increment = false)
            
            // Then: Current streak should reset but longest should remain
            assertEquals(0, profileAfterReset?.currentStreak, "UC10: Current streak must reset")
            assertEquals(2, profileAfterReset?.longestStreak, "UC10: Longest streak must be preserved")
        }
    }
}


