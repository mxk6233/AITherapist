package com.serenityai.usecases

import com.serenityai.utils.GreedyCopingStrategySelector
import com.serenityai.utils.UserConstraints
import com.serenityai.ui.screens.CopingExercise
import java.util.Date

/** UC41: Greedy algorithm for optimal coping strategy selection that maximizes therapeutic benefit while respecting user constraints. */
class GreedyAlgorithmUseCase {
    
    private val greedySelector = GreedyCopingStrategySelector()
    
    /** Selects optimal coping strategies using greedy algorithm. */
    fun selectOptimalStrategies(
        availableExercises: List<CopingExercise>,
        constraints: UserConstraints
    ): List<CopingExercise> {
        require(availableExercises.isNotEmpty()) { "Available exercises cannot be empty" }
        require(constraints.availableTimeMinutes > 0) { "Available time must be positive" }
        require(constraints.currentEnergyLevel in 1..10) { "Energy level must be between 1 and 10" }
        require(constraints.stressLevel in 1..10) { "Stress level must be between 1 and 10" }
        
        return greedySelector.selectOptimalStrategies(availableExercises, constraints)
    }
    
    /** Gets top N recommendations using greedy algorithm. */
    fun getTopRecommendations(
        availableExercises: List<CopingExercise>,
        constraints: UserConstraints,
        topN: Int = 3
    ): List<CopingExercise> {
        require(availableExercises.isNotEmpty()) { "Available exercises cannot be empty" }
        require(topN > 0) { "Top N must be positive" }
        
        return greedySelector.getTopRecommendations(availableExercises, constraints, topN)
    }
    
    /** Calculates total expected benefit from selected strategies. */
    fun calculateTotalBenefit(selectedExercises: List<CopingExercise>): Double {
        return greedySelector.calculateTotalBenefit(selectedExercises)
    }
    
    /** Calculates total time investment for selected strategies. */
    fun calculateTotalTime(selectedExercises: List<CopingExercise>): Int {
        return greedySelector.calculateTotalTime(selectedExercises)
    }
    
    /** Gets explanation for why strategies were selected. */
    fun explainSelection(
        selectedExercises: List<CopingExercise>,
        constraints: UserConstraints
    ): String {
        return greedySelector.explainSelection(selectedExercises, constraints)
    }
    
    /** Validates if selected strategies fit within constraints. */
    fun validateConstraints(
        selectedExercises: List<CopingExercise>,
        constraints: UserConstraints
    ): Boolean {
        val totalTime = calculateTotalTime(selectedExercises)
        val totalEnergy = selectedExercises.map { exercise ->
            when (exercise.difficulty.lowercase()) {
                "easy" -> 1
                "medium" -> 2
                "hard" -> 3
                else -> 2
            }
        }.sum()
        
        return totalTime <= constraints.availableTimeMinutes &&
               totalEnergy <= constraints.currentEnergyLevel
    }
    
    /** Gets greedy algorithm statistics. */
    fun getAlgorithmStats(
        availableExercises: List<CopingExercise>,
        constraints: UserConstraints,
        selectedExercises: List<CopingExercise>
    ): GreedyAlgorithmStats {
        val totalBenefit = calculateTotalBenefit(selectedExercises)
        val totalTime = calculateTotalTime(selectedExercises)
        val avgEffectiveness = if (selectedExercises.isNotEmpty()) {
            selectedExercises.map { it.effectiveness }.average()
        } else {
            0.0
        }
        
        return GreedyAlgorithmStats(
            totalExercisesEvaluated = availableExercises.size,
            exercisesSelected = selectedExercises.size,
            totalExpectedBenefit = totalBenefit,
            totalTimeInvestment = totalTime,
            averageEffectiveness = avgEffectiveness,
            constraintUtilization = if (constraints.availableTimeMinutes > 0) {
                (totalTime.toDouble() / constraints.availableTimeMinutes * 100).coerceAtMost(100.0)
            } else {
                0.0
            }
        )
    }
}

/** Data class for greedy algorithm statistics. */
data class GreedyAlgorithmStats(
    val totalExercisesEvaluated: Int,
    val exercisesSelected: Int,
    val totalExpectedBenefit: Double,
    val totalTimeInvestment: Int,
    val averageEffectiveness: Double,
    val constraintUtilization: Double // Percentage of available time used
)

