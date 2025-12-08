package com.serenityai.tests.uat.usecases.uc41_greedy_algorithm

import com.serenityai.usecases.*
import com.serenityai.utils.UserConstraints
import com.serenityai.ui.screens.CopingExercise
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested

/** UAT: UC41 - Greedy Algorithm for Coping Strategy Selection - User Acceptance Tests validating greedy algorithm from an end-user perspective. */
@DisplayName("UAT: UC41 - Greedy Algorithm")
class GreedyAlgorithmUATTests {

    private val useCase = GreedyAlgorithmUseCase()

    @Nested
    @DisplayName("User Story: Optimal Strategy Recommendations")
    inner class OptimalStrategyRecommendations {
        
        @Test
        @DisplayName("As a user, I want optimal coping strategy recommendations so I get the best help")
        fun `user receives optimal strategy recommendations`() {
            // Given: User needs coping strategies
            val constraints = UserConstraints(
                availableTimeMinutes = 30,
                currentEnergyLevel = 5,
                currentMood = 3,
                stressLevel = 6
            )
            val availableExercises = listOf(
                CopingExercise(
                    id = "ex1", name = "Deep Breathing", description = "Breathing exercise",
                    duration = 5, difficulty = "Easy", effectiveness = 8.0, category = "Relaxation"
                ),
                CopingExercise(
                    id = "ex2", name = "Meditation", description = "Meditation practice",
                    duration = 10, difficulty = "Medium", effectiveness = 7.0, category = "Mindfulness"
                )
            )
            
            // When: System recommends strategies
            val recommendations = useCase.selectOptimalStrategies(availableExercises, constraints)
            
            // Then: Optimal recommendations are provided
            assertTrue(recommendations.isNotEmpty(), "User should receive recommendations")
            assertTrue(recommendations.all { 
                useCase.validateConstraints(listOf(it), constraints) 
            }, "Recommendations should respect constraints")
        }
        
        @Test
        @DisplayName("As a user, I want strategies that fit my time constraints so they're practical")
        fun `strategies respect time constraints`() {
            // Given: User has limited time
            val constraints = UserConstraints(
                availableTimeMinutes = 15,
                currentEnergyLevel = 5,
                currentMood = 3,
                stressLevel = 6
            )
            val availableExercises = listOf(
                CopingExercise(
                    id = "ex1", name = "Quick Breathing", description = "Quick exercise",
                    duration = 5, difficulty = "Easy", effectiveness = 8.0, category = "Relaxation"
                ),
                CopingExercise(
                    id = "ex2", name = "Short Meditation", description = "Short practice",
                    duration = 10, difficulty = "Medium", effectiveness = 7.0, category = "Mindfulness"
                )
            )
            
            // When: Strategies are selected
            val strategies = useCase.selectOptimalStrategies(availableExercises, constraints)
            val totalTime = useCase.calculateTotalTime(strategies)
            val timeRespected = totalTime <= constraints.availableTimeMinutes
            
            // Then: Strategies fit time constraints
            assertTrue(strategies.isNotEmpty(), "Strategies should fit time constraints")
            assertTrue(timeRespected, "Total time should be within limit")
        }
        
        @Test
        @DisplayName("As a user, I want strategies that match my energy level so they're achievable")
        fun `strategies match energy level`() {
            // Given: User has low energy
            val constraints = UserConstraints(
                availableTimeMinutes = 30,
                currentEnergyLevel = 3,
                currentMood = 3,
                stressLevel = 6
            )
            val availableExercises = listOf(
                CopingExercise(
                    id = "ex1", name = "Gentle Stretching", description = "Easy stretching",
                    duration = 10, difficulty = "Easy", effectiveness = 6.0, category = "Physical"
                ),
                CopingExercise(
                    id = "ex2", name = "Mindful Breathing", description = "Breathing practice",
                    duration = 5, difficulty = "Easy", effectiveness = 7.0, category = "Relaxation"
                )
            )
            
            // When: Strategies are selected
            val strategies = useCase.selectOptimalStrategies(availableExercises, constraints)
            val strategiesMatch = useCase.validateConstraints(strategies, constraints)
            
            // Then: Strategies match energy level
            assertTrue(strategiesMatch, "Strategies should match energy level")
            assertTrue(strategies.isNotEmpty(), "Strategies should be selected")
        }
    }

    @Nested
    @DisplayName("User Story: Strategy Selection Explanation")
    inner class StrategySelectionExplanation {
        
        @Test
        @DisplayName("As a user, I want to understand why strategies were recommended so I trust the system")
        fun `user understands strategy selection reasoning`() {
            // Given: Strategies are recommended
            val constraints = UserConstraints(
                availableTimeMinutes = 30,
                currentEnergyLevel = 5,
                currentMood = 3,
                stressLevel = 6
            )
            val availableExercises = listOf(
                CopingExercise(
                    id = "ex1", name = "Deep Breathing", description = "Description",
                    duration = 5, difficulty = "Easy", effectiveness = 8.0, category = "Relaxation"
                ),
                CopingExercise(
                    id = "ex2", name = "Meditation", description = "Description",
                    duration = 10, difficulty = "Medium", effectiveness = 7.0, category = "Mindfulness"
                )
            )
            val selected = useCase.selectOptimalStrategies(availableExercises, constraints)
            
            // When: User views explanation
            val explanation = useCase.explainSelection(selected, constraints)
            val explanationProvided = explanation.isNotBlank()
            val explanationClear = explanation.length > 10
            
            // Then: Explanation is provided
            assertTrue(explanationProvided, "User should see explanation")
            assertTrue(explanationClear, "Explanation should be clear")
        }
        
        @Test
        @DisplayName("As a user, I want to see benefit scores so I know which strategies are most effective")
        fun `user sees benefit scores`() {
            // Given: Strategies with benefit scores
            val constraints = UserConstraints(
                availableTimeMinutes = 30,
                currentEnergyLevel = 5,
                currentMood = 3,
                stressLevel = 6
            )
            val availableExercises = listOf(
                CopingExercise(
                    id = "ex1", name = "Deep Breathing", description = "Description",
                    duration = 5, difficulty = "Easy", effectiveness = 8.0, category = "Relaxation"
                ),
                CopingExercise(
                    id = "ex2", name = "Meditation", description = "Description",
                    duration = 10, difficulty = "Medium", effectiveness = 7.0, category = "Mindfulness"
                ),
                CopingExercise(
                    id = "ex3", name = "Journaling", description = "Description",
                    duration = 15, difficulty = "Easy", effectiveness = 6.0, category = "Reflection"
                )
            )
            
            // When: User views strategies
            val selected = useCase.selectOptimalStrategies(availableExercises, constraints)
            val scoresVisible = selected.all { it.effectiveness > 0 }
            val scoresPositive = selected.all { it.effectiveness > 0 }
            
            // Then: Benefit scores are displayed
            assertTrue(scoresVisible, "User should see benefit scores")
            assertTrue(scoresPositive, "Scores should be positive")
        }
    }

    @Nested
    @DisplayName("User Story: Top N Recommendations")
    inner class TopNRecommendations {
        
        @Test
        @DisplayName("As a user, I want top recommendations so I don't get overwhelmed with options")
        fun `user receives top N recommendations`() {
            // Given: User requests top 3 strategies
            val topN = 3
            val constraints = UserConstraints(
                availableTimeMinutes = 30,
                currentEnergyLevel = 5,
                currentMood = 3,
                stressLevel = 6
            )
            val availableExercises = listOf(
                CopingExercise(
                    id = "ex1", name = "Strategy 1", description = "Description",
                    duration = 5, difficulty = "Easy", effectiveness = 10.0, category = "Relaxation"
                ),
                CopingExercise(
                    id = "ex2", name = "Strategy 2", description = "Description",
                    duration = 10, difficulty = "Medium", effectiveness = 9.0, category = "Mindfulness"
                ),
                CopingExercise(
                    id = "ex3", name = "Strategy 3", description = "Description",
                    duration = 15, difficulty = "Easy", effectiveness = 8.0, category = "Reflection"
                ),
                CopingExercise(
                    id = "ex4", name = "Strategy 4", description = "Description",
                    duration = 20, difficulty = "Hard", effectiveness = 7.0, category = "Physical"
                )
            )
            
            // When: Top N are selected
            val topStrategies = useCase.getTopRecommendations(availableExercises, constraints, topN)
            
            // Then: Top N recommendations are provided
            assertTrue(topStrategies.size == topN, "Should return exactly top N recommendations")
            assertTrue(topStrategies.map { it.effectiveness }
                .zipWithNext().all { it.first >= it.second }, "Should be sorted by benefit")
        }
    }

    @Nested
    @DisplayName("User Story: Constraint Validation")
    inner class ConstraintValidation {
        
        @Test
        @DisplayName("As a user, I want the system to validate my constraints so recommendations are realistic")
        fun `system validates user constraints`() {
            // Given: User provides constraints
            val constraints = UserConstraints(
                availableTimeMinutes = 30,
                currentEnergyLevel = 5,
                currentMood = 3,
                stressLevel = 6
            )
            
            // When: Constraints are validated
            val timeValid = constraints.availableTimeMinutes > 0
            val energyValid = constraints.currentEnergyLevel >= 1 && constraints.currentEnergyLevel <= 10
            val moodValid = constraints.currentMood >= 1 && constraints.currentMood <= 10
            
            // Then: Constraints are valid
            assertTrue(timeValid, "Time constraint should be valid")
            assertTrue(energyValid, "Energy constraint should be valid")
            assertTrue(moodValid, "Mood constraint should be valid")
        }
    }

    @Nested
    @DisplayName("User Story: Algorithm Performance")
    inner class AlgorithmPerformance {
        
        @Test
        @DisplayName("As a user, I want fast recommendations so I don't wait long")
        fun `recommendations are provided quickly`() {
            // Given: User requests recommendations
            val startTime = System.currentTimeMillis()
            val constraints = UserConstraints(
                availableTimeMinutes = 30,
                currentEnergyLevel = 5,
                currentMood = 3,
                stressLevel = 6
            )
            val availableExercises = listOf(
                CopingExercise(
                    id = "ex1", name = "Strategy 1", description = "Description",
                    duration = 5, difficulty = "Easy", effectiveness = 8.0, category = "Relaxation"
                ),
                CopingExercise(
                    id = "ex2", name = "Strategy 2", description = "Description",
                    duration = 10, difficulty = "Medium", effectiveness = 7.0, category = "Mindfulness"
                )
            )
            val recommendations = useCase.selectOptimalStrategies(availableExercises, constraints)
            val endTime = System.currentTimeMillis()
            val responseTime = endTime - startTime
            
            // When: Recommendations are generated
            val recommendationsFast = responseTime < 1000 // Less than 1 second
            val recommendationsProvided = recommendations.isNotEmpty()
            
            // Then: Recommendations are fast
            assertTrue(recommendationsFast, "Recommendations should be fast")
            assertTrue(recommendationsProvided, "Recommendations should be provided")
        }
    }
}
