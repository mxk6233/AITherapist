package com.serenityai.tests.integration.usecases.uc10_user_profile

import com.serenityai.usecases.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

@DisplayName("UC10: Manage User Profile - Integration Tests")
class UserProfileUseCaseIntegrationTests {

    @Nested
    @DisplayName("Integration Test 1: Profile with Database")
    inner class DatabaseIntegrationTests {
        
        @Test
        @DisplayName("Should integrate profile with database storage")
        fun `profile persisted through database integration`() {
            val useCase = UserProfileUseCase()
            val profile = useCase.createProfile("user123", "John Doe", "john@example.com")
            
            val retrieved = useCase.getProfile("user123")
            
            assertTrue(retrieved != null, "UC10 Integration: Profile must be persisted")
            assertEquals(profile.id, retrieved?.id, "UC10 Integration: Profile must be retrievable")
        }
    }

    @Nested
    @DisplayName("Integration Test 2: Goals with Analytics")
    inner class AnalyticsIntegrationTests {
        
        @Test
        @DisplayName("Should integrate goals with analytics system")
        fun `goals tracked through analytics integration`() {
            val useCase = UserProfileUseCase()
            useCase.createProfile("user123", "John Doe", "john@example.com")
            val goal = useCase.createWellnessGoal("user123", "Reduce Anxiety", "Description", com.serenityai.data.models.GoalCategory.MENTAL_HEALTH)
            
            useCase.updateGoalProgress("user123", goal.id, 50)
            val goals = useCase.getWellnessGoals("user123")
            
            assertTrue(goals.isNotEmpty(), "UC10 Integration: Goals must be tracked")
            assertEquals(50, goals.first().progress, "UC10 Integration: Progress must be tracked")
        }
    }

    @Nested
    @DisplayName("Integration Test 3: Preferences with Settings")
    inner class SettingsIntegrationTests {
        
        @Test
        @DisplayName("Should integrate preferences with settings system")
        fun `preferences synchronized through settings integration`() {
            val useCase = UserProfileUseCase()
            useCase.createProfile("user123", "John Doe", "john@example.com")
            
            val updated = useCase.updatePreferences("user123", theme = "dark", notifications = false)
            
            assertTrue(updated != null, "UC10 Integration: Preferences must be updated")
            assertEquals("dark", updated?.preferences?.theme, "UC10 Integration: Theme must be synchronized")
        }
    }
}


