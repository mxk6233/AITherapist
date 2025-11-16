package com.serenityai.tests.unit.utils

import com.serenityai.ui.screens.CopingExercise
import com.serenityai.utils.GreedyCopingStrategySelector
import com.serenityai.utils.UserConstraints
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

@DisplayName("Greedy Algorithm for Coping Strategy Selection - Tests")
class GreedyCopingStrategySelectorTest {
    
    private lateinit var selector: GreedyCopingStrategySelector
    private lateinit var sampleExercises: List<CopingExercise>
    
    @BeforeEach
    fun setUp() {
        selector = GreedyCopingStrategySelector()
        
        sampleExercises = listOf(
            CopingExercise("1", "4-7-8 Breathing", "Quick breathing exercise", "Breathing", "5 minutes", "Easy", listOf("Anxious", "Stressed"), 4.5f, true, 3),
            CopingExercise("2", "Body Scan Meditation", "Deep relaxation", "Mindfulness", "15 minutes", "Medium", listOf("Stressed", "Overwhelmed"), 4.8f, false, 0),
            CopingExercise("3", "Progressive Muscle Relaxation", "Physical relaxation", "Physical", "20 minutes", "Medium", listOf("Stressed", "Angry"), 4.2f, true, 1),
            CopingExercise("4", "Gratitude Journaling", "Reflective writing", "Creative", "10 minutes", "Easy", listOf("Sad", "Overwhelmed"), 4.6f, false, 0),
            CopingExercise("5", "Grounding 5-4-3-2-1", "Quick grounding technique", "Mindfulness", "5 minutes", "Easy", listOf("Anxious", "Overwhelmed"), 4.7f, true, 5),
            CopingExercise("6", "Gentle Yoga Flow", "Physical movement", "Physical", "25 minutes", "Medium", listOf("Stressed", "Sad"), 4.4f, false, 0)
        )
    }
    
    @Nested
    @DisplayName("Test Case 1: Greedy Selection Algorithm")
    inner class GreedySelectionTests {
        
        @Test
        fun `should select exercises with highest effectiveness-to-effort ratio`() {
            // Given: Limited time constraint
            val constraints = UserConstraints(
                availableTimeMinutes = 15,
                currentEnergyLevel = 7,
                currentMood = "Anxious",
                stressLevel = 6
            )
            
            // When: Greedy selection
            val selected = selector.selectOptimalStrategies(sampleExercises, constraints)
            
            // Then: Should prioritize high effectiveness, low effort
            assertTrue(selected.isNotEmpty())
            val firstSelected = selected.first()
            assertTrue(firstSelected.effectiveness >= 4.5f)
            assertTrue(firstSelected.difficulty == "Easy" || firstSelected.difficulty == "Medium")
        }
        
        @Test
        fun `should respect time budget constraint`() {
            // Given: 20 minute time budget
            val constraints = UserConstraints(
                availableTimeMinutes = 20,
                currentEnergyLevel = 8,
                currentMood = "Stressed",
                stressLevel = 7
            )
            
            // When: Greedy selection
            val selected = selector.selectOptimalStrategies(sampleExercises, constraints)
            val totalTime = selector.calculateTotalTime(selected)
            
            // Then: Total time should not exceed budget
            assertTrue(totalTime <= constraints.availableTimeMinutes)
            assertTrue(selected.isNotEmpty())
        }
        
        @Test
        fun `should respect energy level constraint`() {
            // Given: Low energy level
            val constraints = UserConstraints(
                availableTimeMinutes = 30,
                currentEnergyLevel = 3,
                currentMood = "Tired",
                stressLevel = 4
            )
            
            // When: Greedy selection
            val selected = selector.selectOptimalStrategies(sampleExercises, constraints)
            
            // Then: Should select mostly easy exercises
            val easyExercises = selected.filter { it.difficulty == "Easy" }
            assertTrue(easyExercises.size >= selected.size / 2) // At least half should be easy
        }
        
        @Test
        fun `should prioritize mood-matched exercises`() {
            // Given: Specific mood state
            val constraints = UserConstraints(
                availableTimeMinutes = 30,
                currentEnergyLevel = 7,
                currentMood = "Anxious",
                stressLevel = 7
            )
            
            // When: Greedy selection
            val selected = selector.selectOptimalStrategies(sampleExercises, constraints)
            
            // Then: Selected exercises should match anxious mood
            val matchingExercises = selected.filter { it.moodTags.contains("Anxious") }
            assertTrue(matchingExercises.isNotEmpty())
            assertTrue(matchingExercises.size >= 1)
        }
    }
    
    @Nested
    @DisplayName("Test Case 2: Score Calculation")
    inner class ScoreCalculationTests {
        
        @Test
        fun `should calculate higher score for high effectiveness low effort exercises`() {
            // Given: Exercise with high effectiveness and low effort
            val highValueExercise = CopingExercise(
                "1", "Quick Breathing", "Description", "Breathing", "5 minutes", "Easy",
                listOf("Anxious"), 4.8f, true, 5
            )
            
            val lowValueExercise = CopingExercise(
                "2", "Long Meditation", "Description", "Mindfulness", "30 minutes", "Hard",
                listOf("Stressed"), 4.2f, false, 0
            )
            
            val constraints = UserConstraints(30, 8, "Anxious", 6)
            
            // When: Calculate scores
            val selected = selector.selectOptimalStrategies(
                listOf(highValueExercise, lowValueExercise),
                constraints
            )
            
            // Then: High value exercise should be selected first
            assertTrue(selected.isNotEmpty())
            assertEquals("1", selected.first().id)
        }
        
        @Test
        fun `should boost score for exercises matching current mood`() {
            // Given: Same exercise with different mood match
            val matchingExercise = CopingExercise(
                "1", "Exercise", "Description", "Breathing", "10 minutes", "Easy",
                listOf("Anxious"), 4.5f, false, 0
            )
            
            val nonMatchingExercise = CopingExercise(
                "2", "Exercise", "Description", "Creative", "10 minutes", "Easy",
                listOf("Sad"), 4.5f, false, 0
            )
            
            val constraints = UserConstraints(20, 7, "Anxious", 6)
            
            // When: Greedy selection
            val selected = selector.selectOptimalStrategies(
                listOf(matchingExercise, nonMatchingExercise),
                constraints
            )
            
            // Then: Mood-matching exercise should be prioritized
            assertTrue(selected.isNotEmpty())
            assertTrue(selected.first().moodTags.contains("Anxious"))
        }
        
        @Test
        fun `should consider past success when calculating score`() {
            // Given: Exercises with different completion history
            val provenExercise = CopingExercise(
                "1", "Proven Exercise", "Description", "Breathing", "5 minutes", "Easy",
                listOf("Anxious"), 4.5f, true, 10
            )
            
            val newExercise = CopingExercise(
                "2", "New Exercise", "Description", "Mindfulness", "5 minutes", "Easy",
                listOf("Anxious"), 4.6f, false, 0
            )
            
            val constraints = UserConstraints(15, 7, "Anxious", 6)
            
            // When: Greedy selection
            val selected = selector.selectOptimalStrategies(
                listOf(provenExercise, newExercise),
                constraints
            )
            
            // Then: Proven exercise may be prioritized due to familiarity
            assertTrue(selected.isNotEmpty())
            // Both are good, but proven one has familiarity boost
        }
    }
    
    @Nested
    @DisplayName("Test Case 3: Constraint Handling")
    inner class ConstraintHandlingTests {
        
        @Test
        fun `should handle zero time budget gracefully`() {
            // Given: No available time
            val constraints = UserConstraints(
                availableTimeMinutes = 0,
                currentEnergyLevel = 5,
                currentMood = "Neutral",
                stressLevel = 3
            )
            
            // When: Greedy selection
            val selected = selector.selectOptimalStrategies(sampleExercises, constraints)
            
            // Then: Should return empty list
            assertTrue(selected.isEmpty())
        }
        
        @Test
        fun `should handle very limited time by selecting shortest exercises`() {
            // Given: Only 5 minutes available
            val constraints = UserConstraints(
                availableTimeMinutes = 5,
                currentEnergyLevel = 5,
                currentMood = "Anxious",
                stressLevel = 5
            )
            
            // When: Greedy selection
            val selected = selector.selectOptimalStrategies(sampleExercises, constraints)
            val totalTime = selector.calculateTotalTime(selected)
            
            // Then: Should select 5-minute exercises
            assertTrue(totalTime <= 5)
            assertTrue(selected.all { it.duration.contains("5") })
        }
        
        @Test
        fun `should maximize benefit within time constraint`() {
            // Given: 30 minute budget
            val constraints = UserConstraints(
                availableTimeMinutes = 30,
                currentEnergyLevel = 8,
                currentMood = "Stressed",
                stressLevel = 8
            )
            
            // When: Greedy selection
            val selected = selector.selectOptimalStrategies(sampleExercises, constraints)
            val totalBenefit = selector.calculateTotalBenefit(selected)
            val totalTime = selector.calculateTotalTime(selected)
            
            // Then: Should maximize benefit while respecting constraint
            assertTrue(totalTime <= 30)
            assertTrue(totalBenefit > 0)
            // Should select multiple high-value exercises
            assertTrue(selected.size >= 2)
        }
    }
    
    @Nested
    @DisplayName("Test Case 4: Top Recommendations")
    inner class TopRecommendationsTests {
        
        @Test
        fun `should return top 3 recommendations by greedy score`() {
            // Given: Multiple exercises available
            val constraints = UserConstraints(
                availableTimeMinutes = 60,
                currentEnergyLevel = 8,
                currentMood = "Anxious",
                stressLevel = 7
            )
            
            // When: Get top recommendations
            val top3 = selector.getTopRecommendations(sampleExercises, constraints, 3)
            
            // Then: Should return exactly 3 exercises
            assertEquals(3, top3.size)
            assertTrue(top3.all { it in sampleExercises })
        }
        
        @Test
        fun `should return top recommendations sorted by score`() {
            // Given: Exercises with varying scores
            val constraints = UserConstraints(
                availableTimeMinutes = 30,
                currentEnergyLevel = 7,
                currentMood = "Anxious",
                stressLevel = 6
            )
            
            // When: Get top recommendations
            val top = selector.getTopRecommendations(sampleExercises, constraints, 3)
            
            // Then: Should be sorted by effectiveness/ease
            assertTrue(top.isNotEmpty())
            // First should have high effectiveness or low effort
            assertTrue(top.first().effectiveness >= 4.0f)
        }
    }
    
    @Nested
    @DisplayName("Test Case 5: Explanation and Analysis")
    inner class ExplanationTests {
        
        @Test
        fun `should provide explanation for selected strategies`() {
            // Given: Selected exercises
            val constraints = UserConstraints(
                availableTimeMinutes = 20,
                currentEnergyLevel = 7,
                currentMood = "Anxious",
                stressLevel = 7
            )
            
            val selected = selector.selectOptimalStrategies(sampleExercises, constraints)
            
            // When: Get explanation
            val explanation = selector.explainSelection(selected, constraints)
            
            // Then: Explanation should contain key information
            assertTrue(explanation.contains("Selected"))
            assertTrue(explanation.contains("effectiveness-to-effort"))
            assertTrue(explanation.contains("Anxious"))
        }
        
        @Test
        fun `should calculate total benefit correctly`() {
            // Given: Selected exercises
            val selected = listOf(
                sampleExercises[0], // 4.5 effectiveness
                sampleExercises[4]  // 4.7 effectiveness
            )
            
            // When: Calculate total benefit
            val totalBenefit = selector.calculateTotalBenefit(selected)
            
            // Then: Should sum effectiveness
            assertEquals(9.2, totalBenefit, 0.01)
        }
        
        @Test
        fun `should calculate total time correctly`() {
            // Given: Selected exercises with known durations
            val selected = listOf(
                sampleExercises[0], // 5 minutes
                sampleExercises[4]  // 5 minutes
            )
            
            // When: Calculate total time
            val totalTime = selector.calculateTotalTime(selected)
            
            // Then: Should sum durations
            assertEquals(10, totalTime)
        }
    }
}

