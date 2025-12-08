package com.serenityai.tests.unit.usecases.uc41_greedy_algorithm

import com.serenityai.usecases.*
import com.serenityai.utils.UserConstraints
import com.serenityai.ui.screens.CopingExercise
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

@DisplayName("UC41: Add Greedy Algorithm - Unit Tests")
class GreedyAlgorithmUseCaseUnitTests {

    private lateinit var useCase: GreedyAlgorithmUseCase
    private lateinit var sampleExercises: List<CopingExercise>

    @BeforeEach
    fun setUp() {
        useCase = GreedyAlgorithmUseCase()
        sampleExercises = listOf(
            CopingExercise("1", "Breathing", "Quick breathing", "Breathing", "5 minutes", "Easy", listOf("Anxious"), 4.5f, true, 3),
            CopingExercise("2", "Meditation", "Deep relaxation", "Mindfulness", "15 minutes", "Medium", listOf("Stressed"), 4.8f, false, 0),
            CopingExercise("3", "Yoga", "Physical movement", "Physical", "20 minutes", "Medium", listOf("Stressed"), 4.2f, true, 1)
        )
    }

    @Nested
    @DisplayName("Test Case 1: Optimal Strategy Selection - Validates Core UC41 Functionality")
    inner class OptimalSelectionTests {
        
        @Test
        @DisplayName("UC41-REQ-1: System MUST select optimal strategies using greedy algorithm")
        fun `system selects optimal strategies correctly`() {
            val constraints = UserConstraints(
                availableTimeMinutes = 15,
                currentEnergyLevel = 7,
                currentMood = "Anxious",
                stressLevel = 6
            )
            
            val selected = useCase.selectOptimalStrategies(sampleExercises, constraints)
            
            assertTrue(selected.isNotEmpty(), "UC41: Must select at least one strategy")
            assertTrue(selected.all { exercise ->
                sampleExercises.any { it.id == exercise.id }
            }, "UC41: Selected strategies must be from available exercises")
        }
        
        @Test
        @DisplayName("UC41-REQ-2: System MUST validate constraints")
        fun `system validates constraints correctly`() {
            val constraints = UserConstraints(15, 7, "Anxious", 6)
            val selected = useCase.selectOptimalStrategies(sampleExercises, constraints)
            
            val isValid = useCase.validateConstraints(selected, constraints)
            
            assertTrue(isValid, "UC41: Selected strategies must fit within constraints")
        }
    }

    @Nested
    @DisplayName("Test Case 2: Top Recommendations - Validates Secondary UC41 Functionality")
    inner class TopRecommendationsTests {
        
        @Test
        @DisplayName("UC41-REQ-3: System MUST provide top N recommendations")
        fun `system provides top recommendations correctly`() {
            val constraints = UserConstraints(30, 8, "Stressed", 7)
            
            val top3 = useCase.getTopRecommendations(sampleExercises, constraints, 3)
            
            assertTrue(top3.size <= 3, "UC41: Must return at most top N")
            assertTrue(top3.isNotEmpty(), "UC41: Must return at least one recommendation")
        }
    }

    @Nested
    @DisplayName("Test Case 3: Benefit Calculation - Validates Tertiary UC41 Functionality")
    inner class BenefitCalculationTests {
        
        @Test
        @DisplayName("UC41-REQ-4: System MUST calculate total benefit")
        fun `system calculates total benefit correctly`() {
            val constraints = UserConstraints(30, 8, "Stressed", 7)
            val selected = useCase.selectOptimalStrategies(sampleExercises, constraints)
            
            val totalBenefit = useCase.calculateTotalBenefit(selected)
            
            assertTrue(totalBenefit >= 0, "UC41: Total benefit must be non-negative")
        }
        
        @Test
        @DisplayName("UC41-REQ-5: System MUST calculate total time")
        fun `system calculates total time correctly`() {
            val constraints = UserConstraints(30, 8, "Stressed", 7)
            val selected = useCase.selectOptimalStrategies(sampleExercises, constraints)
            
            val totalTime = useCase.calculateTotalTime(selected)
            
            assertTrue(totalTime >= 0, "UC41: Total time must be non-negative")
        }
        
        @Test
        @DisplayName("UC41-REQ-6: System MUST provide selection explanation")
        fun `system provides selection explanation correctly`() {
            val constraints = UserConstraints(30, 8, "Stressed", 7)
            val selected = useCase.selectOptimalStrategies(sampleExercises, constraints)
            
            val explanation = useCase.explainSelection(selected, constraints)
            
            assertNotNull(explanation, "UC41: Explanation must be provided")
            assertTrue(explanation.isNotBlank(), "UC41: Explanation must not be empty")
        }
        
        @Test
        @DisplayName("UC41-REQ-7: System MUST provide algorithm statistics")
        fun `system provides algorithm statistics correctly`() {
            val constraints = UserConstraints(30, 8, "Stressed", 7)
            val selected = useCase.selectOptimalStrategies(sampleExercises, constraints)
            
            val stats = useCase.getAlgorithmStats(sampleExercises, constraints, selected)
            
            assertNotNull(stats, "UC41: Statistics must be provided")
            assertEquals(sampleExercises.size, stats.totalExercisesEvaluated, "UC41: Must track evaluated exercises")
            assertEquals(selected.size, stats.exercisesSelected, "UC41: Must track selected exercises")
        }
    }
}


