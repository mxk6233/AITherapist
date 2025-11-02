package com.serenityai.tests.usecases.uc8_coping_exercises.unit

import com.serenityai.ui.screens.CopingExercise
import com.serenityai.ui.screens.CopingExercisesScreen
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

@DisplayName("UC8: Suggest Coping Exercises - Test Cases")
class CopingExercisesUseCaseTests {

    @Nested
    @DisplayName("Test Case 1: Personalized Exercise Recommendations")
    inner class PersonalizedRecommendationsTests {
        
        @Test
        fun `should recommend exercises based on user mood`() {
            // Given
            val exercises = listOf(
                CopingExercise("1", "4-7-8 Breathing", "Calm your nervous system", "Breathing", "5 minutes", "Easy", listOf("Anxious", "Stressed"), 4.5f, true, 3),
                CopingExercise("2", "Body Scan Meditation", "Progressive relaxation", "Mindfulness", "15 minutes", "Medium", listOf("Stressed", "Overwhelmed"), 4.8f, false, 0),
                CopingExercise("3", "Gratitude Journaling", "Write down three things", "Creative", "10 minutes", "Easy", listOf("Sad", "Overwhelmed"), 4.6f, false, 0)
            )
            
            // When
            val anxiousRecommendations = exercises.filter { exercise ->
                exercise.moodTags.contains("Anxious")
            }
            val stressedRecommendations = exercises.filter { exercise ->
                exercise.moodTags.contains("Stressed")
            }
            
            // Then
            assertEquals(1, anxiousRecommendations.size)
            assertEquals("4-7-8 Breathing", anxiousRecommendations.first().title)
            assertEquals(2, stressedRecommendations.size)
            assertTrue(stressedRecommendations.any { it.title.contains("Breathing") })
            assertTrue(stressedRecommendations.any { it.title.contains("Meditation") })
        }
        
        @Test
        fun `should prioritize high-effectiveness exercises for recommendations`() {
            // Given
            val exercises = listOf(
                CopingExercise("1", "Low Impact Exercise", "Basic exercise", "Physical", "10 minutes", "Easy", listOf("All"), 3.2f, false, 0),
                CopingExercise("2", "High Impact Exercise", "Very effective", "Physical", "15 minutes", "Medium", listOf("All"), 4.8f, false, 0),
                CopingExercise("3", "Medium Impact Exercise", "Moderately effective", "Physical", "12 minutes", "Easy", listOf("All"), 4.1f, false, 0)
            )
            
            // When
            val topRecommendations = exercises.sortedByDescending { it.effectiveness }.take(2)
            
            // Then
            assertEquals(2, topRecommendations.size)
            assertEquals("High Impact Exercise", topRecommendations[0].title)
            assertEquals(4.8f, topRecommendations[0].effectiveness)
            assertEquals("Medium Impact Exercise", topRecommendations[1].title)
            assertEquals(4.1f, topRecommendations[1].effectiveness)
        }
        
        @Test
        fun `should consider user completion history for recommendations`() {
            // Given
            val exercises = listOf(
                CopingExercise("1", "Completed Exercise", "Already done", "Breathing", "5 minutes", "Easy", listOf("Anxious"), 4.5f, true, 5),
                CopingExercise("2", "New Exercise", "Never tried", "Mindfulness", "10 minutes", "Medium", listOf("Anxious"), 4.7f, false, 0),
                CopingExercise("3", "Occasional Exercise", "Sometimes done", "Physical", "8 minutes", "Easy", listOf("Anxious"), 4.3f, true, 2)
            )
            
            // When
            val newExercises = exercises.filter { !it.isCompleted }
            val completedExercises = exercises.filter { it.isCompleted }
            val frequentlyUsed = exercises.filter { it.timesCompleted >= 3 }
            
            // Then
            assertEquals(1, newExercises.size)
            assertEquals("New Exercise", newExercises.first().title)
            assertEquals(2, completedExercises.size)
            assertEquals(1, frequentlyUsed.size)
            assertEquals("Completed Exercise", frequentlyUsed.first().title)
        }
    }

    @Nested
    @DisplayName("Test Case 2: Exercise Library Management")
    inner class ExerciseLibraryManagementTests {
        
        @Test
        fun `should filter exercises by category correctly`() {
            // Given
            val exercises = listOf(
                CopingExercise("1", "Deep Breathing", "Breathing exercise", "Breathing", "5 minutes", "Easy", listOf("Anxious"), 4.5f, false, 0),
                CopingExercise("2", "Mindfulness Meditation", "Meditation exercise", "Mindfulness", "15 minutes", "Medium", listOf("Stressed"), 4.8f, false, 0),
                CopingExercise("3", "Yoga Flow", "Physical exercise", "Physical", "25 minutes", "Medium", listOf("Stressed"), 4.4f, false, 0),
                CopingExercise("4", "Gratitude Writing", "Creative exercise", "Creative", "10 minutes", "Easy", listOf("Sad"), 4.6f, false, 0)
            )
            
            // When
            val breathingExercises = exercises.filter { it.category == "Breathing" }
            val mindfulnessExercises = exercises.filter { it.category == "Mindfulness" }
            val physicalExercises = exercises.filter { it.category == "Physical" }
            val creativeExercises = exercises.filter { it.category == "Creative" }
            
            // Then
            assertEquals(1, breathingExercises.size)
            assertEquals("Deep Breathing", breathingExercises.first().title)
            assertEquals(1, mindfulnessExercises.size)
            assertEquals("Mindfulness Meditation", mindfulnessExercises.first().title)
            assertEquals(1, physicalExercises.size)
            assertEquals("Yoga Flow", physicalExercises.first().title)
            assertEquals(1, creativeExercises.size)
            assertEquals("Gratitude Writing", creativeExercises.first().title)
        }
        
        @Test
        fun `should filter exercises by difficulty level`() {
            // Given
            val exercises = listOf(
                CopingExercise("1", "Easy Exercise 1", "Simple exercise", "Breathing", "5 minutes", "Easy", listOf("All"), 4.0f, false, 0),
                CopingExercise("2", "Easy Exercise 2", "Another simple exercise", "Mindfulness", "8 minutes", "Easy", listOf("All"), 4.2f, false, 0),
                CopingExercise("3", "Medium Exercise", "Moderate exercise", "Physical", "15 minutes", "Medium", listOf("All"), 4.5f, false, 0),
                CopingExercise("4", "Hard Exercise", "Challenging exercise", "Physical", "30 minutes", "Hard", listOf("All"), 4.8f, false, 0)
            )
            
            // When
            val easyExercises = exercises.filter { it.difficulty == "Easy" }
            val mediumExercises = exercises.filter { it.difficulty == "Medium" }
            val hardExercises = exercises.filter { it.difficulty == "Hard" }
            
            // Then
            assertEquals(2, easyExercises.size)
            assertEquals(1, mediumExercises.size)
            assertEquals("Medium Exercise", mediumExercises.first().title)
            assertEquals(1, hardExercises.size)
            assertEquals("Hard Exercise", hardExercises.first().title)
        }
        
        @Test
        fun `should track exercise completion and usage statistics`() {
            // Given
            val exercises = listOf(
                CopingExercise("1", "Popular Exercise", "Often used", "Breathing", "5 minutes", "Easy", listOf("All"), 4.5f, true, 10),
                CopingExercise("2", "Occasional Exercise", "Sometimes used", "Mindfulness", "10 minutes", "Medium", listOf("All"), 4.3f, true, 3),
                CopingExercise("3", "New Exercise", "Never used", "Physical", "15 minutes", "Easy", listOf("All"), 4.7f, false, 0)
            )
            
            // When
            val totalCompletions = exercises.sumOf { it.timesCompleted }
            val completedExercises = exercises.count { it.isCompleted }
            val averageUsage = exercises.map { it.timesCompleted }.average()
            val mostUsed = exercises.maxByOrNull { it.timesCompleted }
            
            // Then
            assertEquals(13, totalCompletions)
            assertEquals(2, completedExercises)
            assertEquals(4.33, averageUsage, 0.01)
            assertNotNull(mostUsed)
            assertEquals("Popular Exercise", mostUsed!!.title)
            assertEquals(10, mostUsed.timesCompleted)
        }
    }

    @Nested
    @DisplayName("Test Case 3: Progress Tracking and Effectiveness")
    inner class ProgressTrackingTests {
        
        @Test
        fun `should calculate exercise effectiveness metrics`() {
            // Given
            val exercises = listOf(
                CopingExercise("1", "Exercise A", "Description", "Breathing", "5 minutes", "Easy", listOf("Anxious"), 4.8f, true, 5),
                CopingExercise("2", "Exercise B", "Description", "Mindfulness", "10 minutes", "Medium", listOf("Stressed"), 3.2f, true, 2),
                CopingExercise("3", "Exercise C", "Description", "Physical", "15 minutes", "Easy", listOf("Sad"), 4.5f, true, 8)
            )
            
            // When
            val averageEffectiveness = exercises.map { it.effectiveness }.average()
            val highEffectivenessExercises = exercises.filter { it.effectiveness >= 4.5f }
            val lowEffectivenessExercises = exercises.filter { it.effectiveness < 4.0f }
            
            // Then
            assertEquals(4.17, averageEffectiveness, 0.01)
            assertEquals(2, highEffectivenessExercises.size)
            assertTrue(highEffectivenessExercises.any { it.title == "Exercise A" })
            assertTrue(highEffectivenessExercises.any { it.title == "Exercise C" })
            assertEquals(1, lowEffectivenessExercises.size)
            assertEquals("Exercise B", lowEffectivenessExercises.first().title)
        }
        
        @Test
        fun `should track user progress over time`() {
            // Given
            val exercises = listOf(
                CopingExercise("1", "Exercise 1", "Description", "Breathing", "5 minutes", "Easy", listOf("All"), 4.5f, true, 3),
                CopingExercise("2", "Exercise 2", "Description", "Mindfulness", "10 minutes", "Medium", listOf("All"), 4.3f, true, 1),
                CopingExercise("3", "Exercise 3", "Description", "Physical", "15 minutes", "Easy", listOf("All"), 4.7f, false, 0)
            )
            
            // When
            val totalSessions = exercises.sumOf { it.timesCompleted }
            val completionRate = exercises.count { it.isCompleted }.toFloat() / exercises.size
            val averageSessionsPerExercise = exercises.map { it.timesCompleted }.average()
            
            // Then
            assertEquals(4, totalSessions)
            assertEquals(0.67f, completionRate, 0.01f)
            assertEquals(1.33, averageSessionsPerExercise, 0.01)
        }
        
        @Test
        fun `should identify improvement opportunities`() {
            // Given
            val exercises = listOf(
                CopingExercise("1", "Well Used Exercise", "High usage", "Breathing", "5 minutes", "Easy", listOf("Anxious"), 4.8f, true, 15),
                CopingExercise("2", "Underused Exercise", "Low usage", "Mindfulness", "10 minutes", "Medium", listOf("Stressed"), 4.6f, true, 1),
                CopingExercise("3", "Untried Exercise", "Never used", "Physical", "15 minutes", "Easy", listOf("Sad"), 4.7f, false, 0)
            )
            
            // When
            val underusedExercises = exercises.filter { it.isCompleted && it.timesCompleted < 3 }
            val untriedExercises = exercises.filter { !it.isCompleted }
            val highPotentialExercises = exercises.filter { !it.isCompleted && it.effectiveness >= 4.5f }
            
            // Then
            assertEquals(1, underusedExercises.size)
            assertEquals("Underused Exercise", underusedExercises.first().title)
            assertEquals(1, untriedExercises.size)
            assertEquals("Untried Exercise", untriedExercises.first().title)
            assertEquals(1, highPotentialExercises.size)
            assertEquals("Untried Exercise", highPotentialExercises.first().title)
        }
    }
}
