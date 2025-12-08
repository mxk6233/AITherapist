package com.serenityai.tests.integration.usecases.uc41_greedy_algorithm

import com.serenityai.usecases.*
import com.serenityai.utils.UserConstraints
import com.serenityai.ui.screens.CopingExercise
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

@DisplayName("UC41: Add Greedy Algorithm - Integration Tests")
class GreedyAlgorithmUseCaseIntegrationTests {

    @Nested
    @DisplayName("Integration Test 1: Algorithm with Exercise System")
    inner class ExerciseSystemIntegrationTests {
        
        @Test
        @DisplayName("Should integrate algorithm with exercise system")
        fun `algorithm selects exercises through exercise system integration`() {
            val useCase = GreedyAlgorithmUseCase()
            val exercises = listOf(
                CopingExercise("1", "Exercise 1", "Desc", "Category", "5 min", "Easy", listOf("Anxious"), 4.5f, true, 0),
                CopingExercise("2", "Exercise 2", "Desc", "Category", "10 min", "Medium", listOf("Stressed"), 4.8f, false, 0)
            )
            val constraints = UserConstraints(15, 7, "Anxious", 6)
            
            val selected = useCase.selectOptimalStrategies(exercises, constraints)
            
            assertTrue(selected.isNotEmpty(), "UC41 Integration: Algorithm must select exercises")
        }
    }

    @Nested
    @DisplayName("Integration Test 2: Recommendations with User Context")
    inner class UserContextIntegrationTests {
        
        @Test
        @DisplayName("Should integrate recommendations with user context")
        fun `recommendations personalized through user context integration`() {
            val useCase = GreedyAlgorithmUseCase()
            val exercises = listOf(
                CopingExercise("1", "Exercise 1", "Desc", "Category", "5 min", "Easy", listOf("Anxious"), 4.5f, true, 0)
            )
            val constraints = UserConstraints(15, 7, "Anxious", 6)
            
            val recommendations = useCase.getTopRecommendations(exercises, constraints, 3)
            
            assertTrue(recommendations.isNotEmpty(), "UC41 Integration: Recommendations must consider user context")
        }
    }

    @Nested
    @DisplayName("Integration Test 3: Statistics with Analytics")
    inner class AnalyticsIntegrationTests {
        
        @Test
        @DisplayName("Should integrate statistics with analytics system")
        fun `statistics tracked through analytics integration`() {
            val useCase = GreedyAlgorithmUseCase()
            val exercises = listOf(
                CopingExercise("1", "Exercise 1", "Desc", "Category", "5 min", "Easy", listOf("Anxious"), 4.5f, true, 0)
            )
            val constraints = UserConstraints(15, 7, "Anxious", 6)
            val selected = useCase.selectOptimalStrategies(exercises, constraints)
            
            val stats = useCase.getAlgorithmStats(exercises, constraints, selected)
            
            assertNotNull(stats, "UC41 Integration: Statistics must be tracked")
            assertEquals(exercises.size, stats.totalExercisesEvaluated, "UC41 Integration: Analytics must track evaluations")
        }
    }
}


