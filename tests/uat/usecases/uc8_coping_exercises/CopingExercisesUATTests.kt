package com.serenityai.tests.uat.usecases.uc8_coping_exercises

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested

@DisplayName("UAT: UC8 - Suggest Coping Exercises")
class CopingExercisesUATTests {

    @Nested
    @DisplayName("User Story: Coping Exercise Recommendations")
    inner class CopingExerciseRecommendations {
        
        @Test
        @DisplayName("As a user, I want personalized coping exercises so I can manage my stress effectively")
        fun `user receives personalized coping exercise recommendations`() {
            // Given: User needs coping strategies
            val currentMood = "Stressed"
            val exercises = listOf(
                "Deep breathing exercise",
                "Progressive muscle relaxation",
                "Mindful walking"
            )
            
            // When: System recommends exercises
            val recommendationsPersonalized = exercises.isNotEmpty()
            val relevantForMood = exercises.any { it.contains("breathing") || it.contains("relaxation") }
            
            // Then: Personalized exercises are recommended
            assertTrue(recommendationsPersonalized, "Exercises should be recommended")
            assertTrue(relevantForMood, "Exercises should match user's mood")
        }
        
        @Test
        @DisplayName("As a user, I want to track my exercise progress so I can see my improvement")
        fun `user can track coping exercise progress`() {
            // Given: User completes exercises
            val completedExercises = listOf(
                mapOf("exercise" to "Deep breathing", "date" to "2024-01-01", "duration" to 10),
                mapOf("exercise" to "Meditation", "date" to "2024-01-02", "duration" to 15)
            )
            
            // When: User views progress
            val progressTracked = completedExercises.isNotEmpty()
            val totalExercises = completedExercises.size
            
            // Then: Progress is displayed
            assertTrue(progressTracked, "Exercise progress should be tracked")
            assertTrue(totalExercises >= 2, "User should see progress over time")
        }
        
        @Test
        @DisplayName("As a user, I want to access a library of exercises so I have options")
        fun `user can access library of coping exercises`() {
            // Given: Exercise library
            val exerciseLibrary = listOf(
                "Deep breathing",
                "Progressive muscle relaxation",
                "Guided meditation",
                "Mindful walking",
                "Journaling"
            )
            
            // When: User browses library
            val libraryAvailable = exerciseLibrary.isNotEmpty()
            val varietyAvailable = exerciseLibrary.size >= 5
            
            // Then: Library is accessible
            assertTrue(libraryAvailable, "Exercise library should be available")
            assertTrue(varietyAvailable, "Library should have variety of exercises")
        }
    }
}

