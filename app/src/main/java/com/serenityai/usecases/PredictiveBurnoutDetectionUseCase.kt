package com.serenityai.usecases

import com.serenityai.data.models.*
import java.util.Date

/**
 * UC37: Predictive Burnout Detection - Use Case
 * 
 * Use Case Goal: Detect early signs of burnout risk through analysis of user behavior patterns,
 * mood trends, and activity levels to enable proactive intervention and prevention.
 * 
 * Responsibilities:
 * - Analyze user activity patterns for burnout indicators
 * - Monitor mood trends and stress levels
 * - Detect early warning signs of burnout
 * - Calculate burnout risk scores
 * - Provide personalized prevention recommendations
 * - Trigger proactive interventions when risk is high
 */
class PredictiveBurnoutDetectionUseCase {
    
    /**
     * Assesses burnout risk based on multiple factors
     * 
     * @param moodEntries Recent mood entries for trend analysis
     * @param activityLevels Recent activity levels (app usage, engagement)
     * @param stressIndicators Recent stress indicators
     * @param sleepQuality Recent sleep quality data
     * @return BurnoutRiskAssessment object
     */
    fun assessBurnoutRisk(
        moodEntries: List<MoodEntry>,
        activityLevels: List<ActivityLevel> = emptyList(),
        stressIndicators: List<StressIndicator> = emptyList(),
        sleepQuality: List<SleepQuality> = emptyList()
    ): BurnoutRiskAssessment {
        val riskFactors = mutableListOf<BurnoutRiskFactor>()
        
        // Analyze mood trends for burnout patterns
        if (moodEntries.isNotEmpty()) {
            val moodFactor = analyzeMoodTrends(moodEntries)
            riskFactors.add(moodFactor)
        }
        
        // Analyze activity levels for disengagement patterns
        if (activityLevels.isNotEmpty()) {
            val activityFactor = analyzeActivityLevels(activityLevels)
            riskFactors.add(activityFactor)
        }
        
        // Analyze stress indicators
        if (stressIndicators.isNotEmpty()) {
            val stressFactor = analyzeStressIndicators(stressIndicators)
            riskFactors.add(stressFactor)
        }
        
        // Analyze sleep quality
        if (sleepQuality.isNotEmpty()) {
            val sleepFactor = analyzeSleepQuality(sleepQuality)
            riskFactors.add(sleepFactor)
        }
        
        // Calculate overall burnout risk score
        val riskScore = calculateBurnoutRiskScore(riskFactors)
        
        // Determine risk level
        val riskLevel = determineRiskLevel(riskScore)
        
        // Detect early warning signs
        val earlyWarnings = detectBurnoutWarnings(riskFactors, moodEntries)
        
        // Generate prevention recommendations
        val recommendations = generatePreventionRecommendations(riskLevel, riskFactors)
        
        // Determine if intervention should be triggered
        val interventionTriggered = riskLevel in listOf(BurnoutRiskLevel.HIGH, BurnoutRiskLevel.CRITICAL)
        
        return BurnoutRiskAssessment(
            id = System.currentTimeMillis().toString(),
            userId = moodEntries.firstOrNull()?.userId ?: "",
            assessedAt = Date(),
            riskScore = riskScore,
            riskLevel = riskLevel,
            riskFactors = riskFactors,
            earlyWarnings = earlyWarnings,
            recommendations = recommendations,
            interventionTriggered = interventionTriggered
        )
    }
    
    /**
     * Detects early warning signs of burnout
     * 
     * @param riskFactors Current risk factors
     * @param moodEntries Recent mood data
     * @return List of BurnoutWarning objects
     */
    fun detectBurnoutWarnings(
        riskFactors: List<BurnoutRiskFactor>,
        moodEntries: List<MoodEntry>
    ): List<BurnoutWarning> {
        val warnings = mutableListOf<BurnoutWarning>()
        
        // Check for mood decline pattern
        if (moodEntries.size >= 7) {
            val recentMoods = moodEntries.takeLast(7).map { it.mood.toFloat() }
            val earlierMoods = moodEntries.dropLast(7).takeLast(7).map { it.mood.toFloat() }
            
            if (earlierMoods.isNotEmpty() && recentMoods.average().toFloat() < earlierMoods.average().toFloat() - 0.5f) {
                warnings.add(
                    BurnoutWarning(
                        type = WarningType.MOOD_DECLINE,
                        severity = WarningSeverity.MODERATE,
                        message = "Sustained mood decline detected over the past week",
                        detectedAt = Date()
                    )
                )
            }
        }
        
        // Check for activity drop
        val activityFactor = riskFactors.find { it.factorType == BurnoutFactorType.ACTIVITY_DECLINE }
        if (activityFactor != null && activityFactor.severity >= 0.7f) {
            warnings.add(
                BurnoutWarning(
                    type = WarningType.ACTIVITY_DECLINE,
                    severity = WarningSeverity.HIGH,
                    message = "Significant decrease in app engagement detected",
                    detectedAt = Date()
                )
            )
        }
        
        // Check for stress accumulation
        val stressFactor = riskFactors.find { it.factorType == BurnoutFactorType.STRESS_ACCUMULATION }
        if (stressFactor != null && stressFactor.severity >= 0.8f) {
            warnings.add(
                BurnoutWarning(
                    type = WarningType.STRESS_ACCUMULATION,
                    severity = WarningSeverity.HIGH,
                    message = "High stress levels detected consistently",
                    detectedAt = Date()
                )
            )
        }
        
        // Check for sleep disruption
        val sleepFactor = riskFactors.find { it.factorType == BurnoutFactorType.SLEEP_DISRUPTION }
        if (sleepFactor != null && sleepFactor.severity >= 0.7f) {
            warnings.add(
                BurnoutWarning(
                    type = WarningType.SLEEP_DISRUPTION,
                    severity = WarningSeverity.MODERATE,
                    message = "Sleep quality issues detected",
                    detectedAt = Date()
                )
            )
        }
        
        return warnings
    }
    
    /**
     * Generates personalized prevention recommendations
     * 
     * @param riskLevel Current burnout risk level
     * @param riskFactors Identified risk factors
     * @return List of prevention recommendations
     */
    fun generatePreventionRecommendations(
        riskLevel: BurnoutRiskLevel,
        riskFactors: List<BurnoutRiskFactor>
    ): List<String> {
        val recommendations = mutableListOf<String>()
        
        when (riskLevel) {
            BurnoutRiskLevel.CRITICAL -> {
                recommendations.add("URGENT: Consider taking a break or reducing workload")
                recommendations.add("Prioritize rest and recovery activities")
                recommendations.add("Consider speaking with a mental health professional")
                recommendations.add("Implement stress reduction techniques immediately")
            }
            BurnoutRiskLevel.HIGH -> {
                recommendations.add("Take proactive steps to prevent burnout")
                recommendations.add("Increase self-care activities and rest periods")
                recommendations.add("Practice stress management techniques daily")
                recommendations.add("Consider work-life balance adjustments")
            }
            BurnoutRiskLevel.MODERATE -> {
                recommendations.add("Monitor your stress levels closely")
                recommendations.add("Maintain regular self-care routines")
                recommendations.add("Take regular breaks throughout the day")
                recommendations.add("Practice mindfulness and relaxation techniques")
            }
            BurnoutRiskLevel.LOW -> {
                recommendations.add("Continue current self-care practices")
                recommendations.add("Maintain healthy work-life balance")
                recommendations.add("Stay engaged with supportive activities")
            }
        }
        
        // Add specific recommendations based on risk factors
        riskFactors.forEach { factor ->
            when (factor.factorType) {
                BurnoutFactorType.MOOD_DECLINE -> {
                    recommendations.add("Focus on mood improvement activities")
                    recommendations.add("Engage in activities that bring joy")
                }
                BurnoutFactorType.ACTIVITY_DECLINE -> {
                    recommendations.add("Gradually increase engagement with supportive activities")
                    recommendations.add("Set small, achievable goals")
                }
                BurnoutFactorType.STRESS_ACCUMULATION -> {
                    recommendations.add("Practice daily stress reduction techniques")
                    recommendations.add("Identify and address sources of stress")
                }
                BurnoutFactorType.SLEEP_DISRUPTION -> {
                    recommendations.add("Improve sleep hygiene practices")
                    recommendations.add("Maintain consistent sleep schedule")
                }
            }
        }
        
        return recommendations.distinct()
    }
    
    /**
     * Predicts future burnout risk based on current trends
     * 
     * @param currentAssessment Current burnout risk assessment
     * @param daysAhead Number of days to predict (default 7)
     * @return BurnoutPrediction object
     */
    fun predictFutureBurnoutRisk(
        currentAssessment: BurnoutRiskAssessment,
        daysAhead: Int = 7
    ): BurnoutPrediction {
        val currentScore = currentAssessment.riskScore
        val trend = analyzeTrend(currentAssessment.riskFactors)
        
        // Project future risk based on trend
        val projectedScore = when (trend) {
            Trend.INCREASING -> (currentScore * 1.15f).coerceIn(0f, 100f)
            Trend.DECREASING -> (currentScore * 0.85f).coerceIn(0f, 100f)
            Trend.STABLE -> currentScore
        }
        
        val projectedRiskLevel = determineRiskLevel(projectedScore)
        
        return BurnoutPrediction(
            id = System.currentTimeMillis().toString(),
            userId = currentAssessment.userId,
            predictedAt = Date(),
            daysAhead = daysAhead,
            projectedRiskScore = projectedScore,
            projectedRiskLevel = projectedRiskLevel,
            trend = trend,
            confidence = calculatePredictionConfidence(currentAssessment)
        )
    }
    
    // Private helper methods
    
    private fun analyzeMoodTrends(moodEntries: List<MoodEntry>): BurnoutRiskFactor {
        if (moodEntries.isEmpty()) {
            return BurnoutRiskFactor(
                factorType = BurnoutFactorType.MOOD_DECLINE,
                severity = 0f,
                description = "Insufficient mood data",
                detectedAt = Date()
            )
        }
        
        val moods = moodEntries.map { it.mood.toFloat() }
        val averageMood = moods.average().toFloat()
        val trend = if (moodEntries.size >= 2) {
            val recent = moods.takeLast(moodEntries.size / 2).average().toFloat()
            val earlier = moods.take(moodEntries.size / 2).average().toFloat()
            if (recent < earlier - 0.3f) Trend.DECREASING else if (recent > earlier + 0.3f) Trend.INCREASING else Trend.STABLE
        } else Trend.STABLE
        
        val severity = when {
            averageMood < 2.5f && trend == Trend.DECREASING -> 0.9f
            averageMood < 3.0f && trend == Trend.DECREASING -> 0.7f
            averageMood < 3.5f && trend == Trend.DECREASING -> 0.5f
            averageMood < 3.0f -> 0.6f
            else -> 0.3f
        }
        
        return BurnoutRiskFactor(
            factorType = BurnoutFactorType.MOOD_DECLINE,
            severity = severity,
            description = "Mood trend analysis: ${trend.name.lowercase()}, average mood: ${String.format("%.1f", averageMood)}",
            detectedAt = Date()
        )
    }
    
    private fun analyzeActivityLevels(activityLevels: List<ActivityLevel>): BurnoutRiskFactor {
        if (activityLevels.isEmpty()) {
            return BurnoutRiskFactor(
                factorType = BurnoutFactorType.ACTIVITY_DECLINE,
                severity = 0f,
                description = "Insufficient activity data",
                detectedAt = Date()
            )
        }
        
        val activities = activityLevels.map { it.level }
        val averageActivity = activities.average().toFloat()
        val recentActivity = activities.takeLast(activities.size / 2).average().toFloat()
        val earlierActivity = activities.take(activities.size / 2).average().toFloat()
        
        val decline = if (earlierActivity > 0f) (earlierActivity - recentActivity) / earlierActivity else 0f
        
        val severity = when {
            decline > 0.5f -> 0.9f
            decline > 0.3f -> 0.7f
            decline > 0.15f -> 0.5f
            averageActivity < 0.3f -> 0.6f
            else -> 0.3f
        }
        
        return BurnoutRiskFactor(
            factorType = BurnoutFactorType.ACTIVITY_DECLINE,
            severity = severity,
            description = "Activity decline: ${String.format("%.1f", decline * 100)}% decrease",
            detectedAt = Date()
        )
    }
    
    private fun analyzeStressIndicators(stressIndicators: List<StressIndicator>): BurnoutRiskFactor {
        if (stressIndicators.isEmpty()) {
            return BurnoutRiskFactor(
                factorType = BurnoutFactorType.STRESS_ACCUMULATION,
                severity = 0f,
                description = "Insufficient stress data",
                detectedAt = Date()
            )
        }
        
        val stressLevels = stressIndicators.map { it.level }
        val averageStress = stressLevels.average().toFloat()
        val highStressDays = stressIndicators.count { it.level >= 7 }
        val totalDays = stressIndicators.size
        
        val severity = when {
            averageStress >= 8.0f && highStressDays >= totalDays * 0.7f -> 0.9f
            averageStress >= 7.0f && highStressDays >= totalDays * 0.5f -> 0.8f
            averageStress >= 6.0f -> 0.6f
            averageStress >= 5.0f -> 0.4f
            else -> 0.2f
        }
        
        return BurnoutRiskFactor(
            factorType = BurnoutFactorType.STRESS_ACCUMULATION,
            severity = severity,
            description = "Average stress level: ${String.format("%.1f", averageStress)}/10",
            detectedAt = Date()
        )
    }
    
    private fun analyzeSleepQuality(sleepQuality: List<SleepQuality>): BurnoutRiskFactor {
        if (sleepQuality.isEmpty()) {
            return BurnoutRiskFactor(
                factorType = BurnoutFactorType.SLEEP_DISRUPTION,
                severity = 0f,
                description = "Insufficient sleep data",
                detectedAt = Date()
            )
        }
        
        val qualityScores = sleepQuality.map { it.quality }
        val averageQuality = qualityScores.average().toFloat()
        val poorSleepDays = sleepQuality.count { it.quality <= 3 }
        val totalDays = sleepQuality.size
        
        val severity = when {
            averageQuality <= 2.0f && poorSleepDays >= totalDays * 0.7f -> 0.9f
            averageQuality <= 3.0f && poorSleepDays >= totalDays * 0.5f -> 0.7f
            averageQuality <= 4.0f -> 0.5f
            averageQuality <= 5.0f -> 0.3f
            else -> 0.1f
        }
        
        return BurnoutRiskFactor(
            factorType = BurnoutFactorType.SLEEP_DISRUPTION,
            severity = severity,
            description = "Average sleep quality: ${String.format("%.1f", averageQuality)}/10",
            detectedAt = Date()
        )
    }
    
    private fun calculateBurnoutRiskScore(riskFactors: List<BurnoutRiskFactor>): Float {
        if (riskFactors.isEmpty()) return 0f
        
        // Weighted average of risk factor severities
        val weightedSum = riskFactors.sumOf { it.severity.toDouble() }
        val average = (weightedSum / riskFactors.size).toFloat()
        
        // Convert to 0-100 scale
        return (average * 100f).coerceIn(0f, 100f)
    }
    
    private fun determineRiskLevel(riskScore: Float): BurnoutRiskLevel {
        return when {
            riskScore >= 75f -> BurnoutRiskLevel.CRITICAL
            riskScore >= 50f -> BurnoutRiskLevel.HIGH
            riskScore >= 25f -> BurnoutRiskLevel.MODERATE
            else -> BurnoutRiskLevel.LOW
        }
    }
    
    private fun analyzeTrend(riskFactors: List<BurnoutRiskFactor>): Trend {
        // Simplified trend analysis
        val highSeverityCount = riskFactors.count { it.severity >= 0.7f }
        return when {
            highSeverityCount >= riskFactors.size * 0.7f -> Trend.INCREASING
            highSeverityCount <= riskFactors.size * 0.3f -> Trend.DECREASING
            else -> Trend.STABLE
        }
    }
    
    private fun calculatePredictionConfidence(assessment: BurnoutRiskAssessment): Float {
        // Confidence based on number of risk factors and data quality
        val factorCount = assessment.riskFactors.size
        val avgSeverity = assessment.riskFactors.map { it.severity }.average().toFloat()
        
        return when {
            factorCount >= 4 && avgSeverity > 0.5f -> 85f
            factorCount >= 3 -> 75f
            factorCount >= 2 -> 65f
            else -> 50f
        }
    }
}

/**
 * Data class for burnout risk assessment
 */
data class BurnoutRiskAssessment(
    val id: String,
    val userId: String,
    val assessedAt: Date,
    val riskScore: Float, // 0-100
    val riskLevel: BurnoutRiskLevel,
    val riskFactors: List<BurnoutRiskFactor>,
    val earlyWarnings: List<BurnoutWarning>,
    val recommendations: List<String>,
    val interventionTriggered: Boolean
)

/**
 * Data class for burnout risk factor
 */
data class BurnoutRiskFactor(
    val factorType: BurnoutFactorType,
    val severity: Float, // 0-1
    val description: String,
    val detectedAt: Date
)

/**
 * Data class for burnout warning
 */
data class BurnoutWarning(
    val type: WarningType,
    val severity: WarningSeverity,
    val message: String,
    val detectedAt: Date
)

/**
 * Data class for burnout prediction
 */
data class BurnoutPrediction(
    val id: String,
    val userId: String,
    val predictedAt: Date,
    val daysAhead: Int,
    val projectedRiskScore: Float,
    val projectedRiskLevel: BurnoutRiskLevel,
    val trend: Trend,
    val confidence: Float // 0-100
)

/**
 * Data class for activity level
 */
data class ActivityLevel(
    val date: Date,
    val level: Float // 0-1
)

/**
 * Data class for stress indicator
 */
data class StressIndicator(
    val date: Date,
    val level: Float // 0-10
)

/**
 * Data class for sleep quality
 */
data class SleepQuality(
    val date: Date,
    val quality: Float // 0-10
)

/**
 * Burnout risk level enum
 */
enum class BurnoutRiskLevel {
    LOW, MODERATE, HIGH, CRITICAL
}

/**
 * Burnout factor type enum
 */
enum class BurnoutFactorType {
    MOOD_DECLINE, ACTIVITY_DECLINE, STRESS_ACCUMULATION, SLEEP_DISRUPTION
}

/**
 * Warning type enum
 */
enum class WarningType {
    MOOD_DECLINE, ACTIVITY_DECLINE, STRESS_ACCUMULATION, SLEEP_DISRUPTION
}

/**
 * Warning severity enum
 */
enum class WarningSeverity {
    LOW, MODERATE, HIGH, CRITICAL
}

/**
 * Trend enum
 */
enum class Trend {
    INCREASING, DECREASING, STABLE
}

