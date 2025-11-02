package com.serenityai.utils

import com.serenityai.ui.screens.CopingExercise

/**
 * Greedy Algorithm for Selecting Emotional Coping Strategies
 * 
 * Strategy: Selects coping exercises with the best effectiveness-to-effort ratio
 * that fit within user's available time and energy constraints.
 * 
 * Time Complexity: O(n log n) - dominated by sorting
 * Space Complexity: O(n) - for storing scored exercises
 */
data class UserConstraints(
    val availableTimeMinutes: Int,
    val currentEnergyLevel: Int, // 1-10 scale
    val currentMood: String,
    val stressLevel: Int // 1-10 scale
)

data class ScoredExercise(
    val exercise: CopingExercise,
    val score: Double,
    val effectivenessRatio: Double,
    val effortScore: Double,
    val timeCost: Int,
    val matchQuality: Double
)

class GreedyCopingStrategySelector {
    
    /**
     * Greedy Selection Algorithm:
     * 1. Calculate score for each exercise (effectiveness/effort ratio)
     * 2. Sort by score (descending) - greedy choice
     * 3. Select exercises until time/energy budget exhausted
     * 4. Return optimal set of strategies
     */
    fun selectOptimalStrategies(
        availableExercises: List<CopingExercise>,
        constraints: UserConstraints
    ): List<CopingExercise> {
        if (availableExercises.isEmpty()) return emptyList()
        
        // Step 1: Score all exercises using greedy criteria
        val scoredExercises = availableExercises.map { exercise ->
            calculateGreedyScore(exercise, constraints)
        }
        
        // Step 2: Sort by score (descending) - Greedy choice: always pick best available
        val sortedExercises = scoredExercises.sortedByDescending { it.score }
        
        // Step 3: Greedy selection - select exercises until budget exhausted
        val selectedExercises = mutableListOf<CopingExercise>()
        var remainingTime = constraints.availableTimeMinutes
        var remainingEnergy = constraints.currentEnergyLevel
        
        for (scoredExercise in sortedExercises) {
            val timeCost = scoredExercise.timeCost
            val energyCost = calculateEnergyCost(scoredExercise.exercise.difficulty)
            
            // Check if exercise fits within constraints (greedy feasibility check)
            if (timeCost <= remainingTime && energyCost <= remainingEnergy) {
                selectedExercises.add(scoredExercise.exercise)
                remainingTime -= timeCost
                remainingEnergy -= energyCost
                
                // Stop if we've exhausted resources
                if (remainingTime <= 0 || remainingEnergy <= 0) {
                    break
                }
            }
        }
        
        return selectedExercises
    }
    
    /**
     * Calculate greedy score: effectiveness / effort ratio
     * Higher score = better choice (greedy heuristic)
     */
    private fun calculateGreedyScore(
        exercise: CopingExercise,
        constraints: UserConstraints
    ): ScoredExercise {
        val effectiveness = exercise.effectiveness.toDouble()
        val effort = calculateEffort(exercise)
        val effectivenessRatio = if (effort > 0) effectiveness / effort else effectiveness
        
        // Match quality: how well exercise matches current mood
        val matchQuality = calculateMatchQuality(exercise, constraints)
        
        // Time cost
        val timeCost = parseDurationToMinutes(exercise.duration)
        
        // Greedy score formula: maximize benefit while minimizing cost
        // Score = (Effectiveness * MatchQuality) / (Effort * TimeCost)
        val score = (effectiveness * matchQuality) / (effort * timeCost.coerceAtLeast(1))
        
        return ScoredExercise(
            exercise = exercise,
            score = score,
            effectivenessRatio = effectivenessRatio,
            effortScore = effort,
            timeCost = timeCost,
            matchQuality = matchQuality
        )
    }
    
    /**
     * Calculate effort score based on difficulty
     * Lower effort = higher score (easier to do)
     */
    private fun calculateEffort(exercise: CopingExercise): Double {
        return when (exercise.difficulty.lowercase()) {
            "easy" -> 1.0
            "medium" -> 2.0
            "hard" -> 3.0
            else -> 2.0
        }
    }
    
    /**
     * Calculate energy cost based on difficulty
     */
    private fun calculateEnergyCost(difficulty: String): Int {
        return when (difficulty.lowercase()) {
            "easy" -> 1
            "medium" -> 2
            "hard" -> 3
            else -> 2
        }
    }
    
    /**
     * Calculate how well exercise matches user's current mood
     * Higher match = better fit for current emotional state
     */
    private fun calculateMatchQuality(
        exercise: CopingExercise,
        constraints: UserConstraints
    ): Double {
        var quality = 1.0
        
        // Boost if exercise matches current mood
        if (exercise.moodTags.contains(constraints.currentMood)) {
            quality += 0.5
        }
        
        // Boost if exercise is effective for high stress
        if (constraints.stressLevel >= 7 && exercise.moodTags.contains("Stressed")) {
            quality += 0.3
        }
        
        // Boost based on past success (times completed)
        if (exercise.timesCompleted > 0) {
            quality += (exercise.timesCompleted * 0.1).coerceAtMost(0.5)
        }
        
        // Slight boost for completed exercises (user familiarity)
        if (exercise.isCompleted) {
            quality += 0.2
        }
        
        return quality
    }
    
    /**
     * Parse duration string to minutes
     */
    private fun parseDurationToMinutes(duration: String): Int {
        return try {
            val parts = duration.split(" ")
            val number = parts[0].toIntOrNull() ?: 5
            number
        } catch (e: Exception) {
            5 // Default to 5 minutes
        }
    }
    
    /**
     * Get top N highest-scoring exercises (quick greedy recommendation)
     */
    fun getTopRecommendations(
        availableExercises: List<CopingExercise>,
        constraints: UserConstraints,
        topN: Int = 3
    ): List<CopingExercise> {
        val scored = availableExercises.map { exercise ->
            calculateGreedyScore(exercise, constraints)
        }.sortedByDescending { it.score }
        
        return scored.take(topN).map { it.exercise }
    }
    
    /**
     * Calculate total expected benefit from selected strategies
     */
    fun calculateTotalBenefit(selectedExercises: List<CopingExercise>): Double {
        return selectedExercises.sumOf { it.effectiveness.toDouble() }
    }
    
    /**
     * Calculate total time investment
     */
    fun calculateTotalTime(selectedExercises: List<CopingExercise>): Int {
        return selectedExercises.sumOf { parseDurationToMinutes(it.duration) }
    }
    
    /**
     * Get strategy selection explanation (why these were chosen)
     */
    fun explainSelection(
        selectedExercises: List<CopingExercise>,
        constraints: UserConstraints
    ): String {
        if (selectedExercises.isEmpty()) {
            return "No exercises selected - constraints may be too restrictive"
        }
        
        val totalTime = calculateTotalTime(selectedExercises)
        val avgEffectiveness = selectedExercises.map { it.effectiveness }.average()
        
        return buildString {
            append("Selected ${selectedExercises.size} exercises based on:\n")
            append("• Highest effectiveness-to-effort ratio\n")
            append("• Best match for ${constraints.currentMood} mood\n")
            append("• Total time: $totalTime minutes\n")
            append("• Average effectiveness: ${String.format("%.1f", avgEffectiveness)}/5.0")
        }
    }
}

