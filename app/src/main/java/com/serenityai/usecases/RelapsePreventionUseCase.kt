package com.serenityai.usecases

import com.serenityai.data.models.*
import java.util.Date

/**
 * UC35: Relapse Prevention Alerts - Use Case
 * 
 * Use Case Goal: Detect relapse risk factors early and trigger proactive interventions
 * to prevent mental health relapses.
 * 
 * Responsibilities:
 * - Assess risk factors from mood, sleep, stress, and social data
 * - Calculate overall risk level
 * - Detect early warning signs and patterns
 * - Trigger interventions when risk is high
 * - Maintain safety plans with emergency contacts
 */
class RelapsePreventionUseCase {
    
    /**
     * Assesses risk factors and calculates overall risk level
     * 
     * @param moodEntries Recent mood entries for pattern analysis
     * @param sleepData Recent sleep patterns (hours per night)
     * @param stressLevels Recent stress levels (1-10 scale)
     * @return RiskAssessment with overall risk level and indicators
     */
    fun assessRiskLevel(
        moodEntries: List<MoodEntry>,
        sleepData: List<SleepData> = emptyList(),
        stressLevels: List<StressData> = emptyList()
    ): RiskAssessment {
        val riskIndicators = mutableListOf<RiskIndicator>()
        
        // Assess mood patterns
        if (moodEntries.isNotEmpty()) {
            val moodRisk = assessMoodRisk(moodEntries)
            riskIndicators.add(moodRisk)
        }
        
        // Assess sleep patterns
        if (sleepData.isNotEmpty()) {
            val sleepRisk = assessSleepRisk(sleepData)
            riskIndicators.add(sleepRisk)
        }
        
        // Assess stress levels
        if (stressLevels.isNotEmpty()) {
            val stressRisk = assessStressRisk(stressLevels)
            riskIndicators.add(stressRisk)
        }
        
        // Assess social support (from mood entries - assuming low social activity correlates with isolation)
        val socialRisk = assessSocialSupportRisk(moodEntries)
        riskIndicators.add(socialRisk)
        
        // Assess trigger exposure (simplified - would check against user's known triggers)
        val triggerRisk = assessTriggerExposureRisk()
        riskIndicators.add(triggerRisk)
        
        // Calculate overall risk level
        val overallRiskLevel = calculateOverallRiskLevel(riskIndicators)
        
        // Detect early warnings
        val earlyWarnings = detectEarlyWarnings(riskIndicators, moodEntries)
        
        // Generate recommendations
        val recommendations = generateRecommendations(overallRiskLevel, riskIndicators)
        
        // Determine if intervention should be triggered
        val interventionTriggered = when (overallRiskLevel) {
            RiskLevel.HIGH, RiskLevel.CRITICAL -> true
            RiskLevel.LOW, RiskLevel.MEDIUM -> false
        }
        
        return RiskAssessment(
            id = System.currentTimeMillis().toString(),
            userId = moodEntries.firstOrNull()?.userId ?: "",
            assessedAt = Date(),
            overallRiskLevel = overallRiskLevel,
            riskIndicators = riskIndicators,
            earlyWarnings = earlyWarnings,
            recommendations = recommendations,
            interventionTriggered = interventionTriggered
        )
    }
    
    /**
     * Detects early warning signs from risk indicators and patterns
     * 
     * @param riskIndicators Current risk indicators
     * @param moodEntries Recent mood data for pattern analysis
     * @return List of EarlyWarning objects
     */
    fun detectEarlyWarnings(
        riskIndicators: List<RiskIndicator>,
        moodEntries: List<MoodEntry>
    ): List<EarlyWarning> {
        val warnings = mutableListOf<EarlyWarning>()
        
        // Check for sleep disruption patterns
        val sleepRisk = riskIndicators.find { it.category == RiskCategory.SLEEP }
        if (sleepRisk != null && sleepRisk.riskLevel >= 70) {
            warnings.add(
                EarlyWarning(
                    title = "Sleep Disruption",
                    description = "Irregular sleep patterns detected - ${sleepRisk.description}",
                    severity = if (sleepRisk.riskLevel >= 85) com.serenityai.data.models.WarningSeverity.HIGH else com.serenityai.data.models.WarningSeverity.MEDIUM,
                    recommendedAction = "Schedule sleep hygiene activities and maintain consistent bedtime",
                    category = RiskCategory.SLEEP,
                    relatedRiskIndicators = listOf(sleepRisk.id)
                )
            )
        }
        
        // Check for social isolation
        val socialRisk = riskIndicators.find { it.category == RiskCategory.SOCIAL_SUPPORT }
        if (socialRisk != null && socialRisk.riskLevel >= 60) {
            warnings.add(
                EarlyWarning(
                    title = "Social Isolation",
                    description = "Decreased social activity detected - ${socialRisk.description}",
                    severity = if (socialRisk.riskLevel >= 80) com.serenityai.data.models.WarningSeverity.HIGH else com.serenityai.data.models.WarningSeverity.MEDIUM,
                    recommendedAction = "Plan social interactions and connect with support network",
                    category = RiskCategory.SOCIAL_SUPPORT,
                    relatedRiskIndicators = listOf(socialRisk.id)
                )
            )
        }
        
        // Check for stress accumulation
        val stressRisk = riskIndicators.find { it.category == RiskCategory.STRESS }
        if (stressRisk != null && stressRisk.riskLevel >= 65) {
            warnings.add(
                EarlyWarning(
                    title = "Stress Accumulation",
                    description = "Elevated stress levels detected - ${stressRisk.description}",
                    severity = if (stressRisk.riskLevel >= 80) com.serenityai.data.models.WarningSeverity.HIGH else com.serenityai.data.models.WarningSeverity.MEDIUM,
                    recommendedAction = "Practice stress management techniques and reduce stressors",
                    category = RiskCategory.STRESS,
                    relatedRiskIndicators = listOf(stressRisk.id)
                )
            )
        }
        
        // Check for mood decline patterns
        if (moodEntries.size >= 3) {
            val recentMoods = moodEntries.takeLast(3).map { it.mood.toFloat() }
            val moodTrend = recentMoods.last() - recentMoods.first()
            if (moodTrend < -1.0f && recentMoods.last() <= 3) {
                warnings.add(
                    EarlyWarning(
                        title = "Mood Decline",
                        description = "Significant mood decline detected over recent days",
                        severity = if (recentMoods.last() <= 2) com.serenityai.data.models.WarningSeverity.HIGH else com.serenityai.data.models.WarningSeverity.MEDIUM,
                        recommendedAction = "Engage in coping strategies and contact support network",
                        category = RiskCategory.MOOD_PATTERNS,
                        relatedRiskIndicators = emptyList()
                    )
                )
            }
        }
        
        return warnings.sortedByDescending { 
            when (it.severity) {
                com.serenityai.data.models.WarningSeverity.CRITICAL -> 4
                com.serenityai.data.models.WarningSeverity.HIGH -> 3
                com.serenityai.data.models.WarningSeverity.MEDIUM -> 2
                com.serenityai.data.models.WarningSeverity.LOW -> 1
            }
        }
    }
    
    /**
     * Triggers proactive intervention when risk is high
     * 
     * @param riskAssessment Current risk assessment
     * @param safetyPlan User's safety plan
     * @return InterventionPlan with actions to take
     */
    fun triggerIntervention(
        riskAssessment: RiskAssessment,
        safetyPlan: SafetyPlan
    ): InterventionPlan {
        val actions = mutableListOf<InterventionAction>()
        
        when (riskAssessment.overallRiskLevel) {
            RiskLevel.CRITICAL -> {
                actions.add(
                    InterventionAction(
                        title = "Immediate Support Contact",
                        description = "Contact emergency support immediately",
                        priority = ActionPriority.CRITICAL,
                        deadline = Date(System.currentTimeMillis() + 3600000) // 1 hour
                    )
                )
                actions.add(
                    InterventionAction(
                        title = "Use Coping Strategies",
                        description = "Engage in immediate coping strategies from safety plan",
                        priority = ActionPriority.CRITICAL
                    )
                )
                actions.add(
                    InterventionAction(
                        title = "Schedule Professional Support",
                        description = "Contact therapist or healthcare provider within 24 hours",
                        priority = ActionPriority.HIGH,
                        deadline = Date(System.currentTimeMillis() + 86400000) // 24 hours
                    )
                )
            }
            RiskLevel.HIGH -> {
                actions.add(
                    InterventionAction(
                        title = "Increase Monitoring",
                        description = "Complete daily check-ins and mood assessments",
                        priority = ActionPriority.HIGH
                    )
                )
                actions.add(
                    InterventionAction(
                        title = "Engage Support Network",
                        description = "Contact support person and share concerns",
                        priority = ActionPriority.HIGH
                    )
                )
                actions.add(
                    InterventionAction(
                        title = "Practice Coping Strategies",
                        description = "Use planned coping strategies from safety plan",
                        priority = ActionPriority.MEDIUM
                    )
                )
            }
            RiskLevel.MEDIUM -> {
                actions.add(
                    InterventionAction(
                        title = "Regular Check-ins",
                        description = "Complete daily assessments and monitor patterns",
                        priority = ActionPriority.MEDIUM
                    )
                )
                actions.add(
                    InterventionAction(
                        title = "Preventive Activities",
                        description = "Engage in preventive self-care activities",
                        priority = ActionPriority.MEDIUM
                    )
                )
            }
            RiskLevel.LOW -> {
                actions.add(
                    InterventionAction(
                        title = "Continue Monitoring",
                        description = "Maintain regular check-ins and awareness",
                        priority = ActionPriority.LOW
                    )
                )
            }
        }
        
        // Add specific actions based on early warnings
        riskAssessment.earlyWarnings.forEach { warning ->
            if (warning.severity in listOf(com.serenityai.data.models.WarningSeverity.HIGH, com.serenityai.data.models.WarningSeverity.CRITICAL)) {
                actions.add(
                    InterventionAction(
                        title = "Address: ${warning.title}",
                        description = warning.recommendedAction,
                        priority = when (warning.severity) {
                            com.serenityai.data.models.WarningSeverity.CRITICAL -> ActionPriority.CRITICAL
                            com.serenityai.data.models.WarningSeverity.HIGH -> ActionPriority.HIGH
                            else -> ActionPriority.MEDIUM
                        }
                    )
                )
            }
        }
        
        return InterventionPlan(
            id = System.currentTimeMillis().toString(),
            userId = riskAssessment.userId,
            triggeredAt = Date(),
            triggerReason = "Risk level: ${riskAssessment.overallRiskLevel}",
            riskLevel = riskAssessment.overallRiskLevel,
            actions = actions,
            emergencyContacts = safetyPlan.emergencyContacts,
            status = InterventionStatus.ACTIVE
        )
    }
    
    /**
     * Calculates overall risk level from individual risk indicators
     */
    private fun calculateOverallRiskLevel(indicators: List<RiskIndicator>): RiskLevel {
        if (indicators.isEmpty()) return RiskLevel.LOW
        
        val averageRisk = indicators.map { it.riskLevel }.average()
        return when {
            averageRisk >= 80 -> RiskLevel.CRITICAL
            averageRisk >= 60 -> RiskLevel.HIGH
            averageRisk >= 40 -> RiskLevel.MEDIUM
            else -> RiskLevel.LOW
        }
    }
    
    /**
     * Assesses mood-related risk
     */
    private fun assessMoodRisk(moodEntries: List<MoodEntry>): RiskIndicator {
        val recentMoods = moodEntries.takeLast(7).map { it.mood.toFloat() }
        val averageMood = recentMoods.average()
        val moodDecline = recentMoods.first() - recentMoods.last()
        
        val riskLevel = when {
            averageMood <= 2.0f -> 90
            averageMood <= 3.0f && moodDecline > 1.0f -> 85
            averageMood <= 3.0f -> 75
            averageMood <= 3.5f && moodDecline > 0.5f -> 65
            averageMood <= 3.5f -> 55
            averageMood <= 4.0f && moodDecline > 0.5f -> 45
            else -> 30
        }.toInt()
        
        val description = when {
            averageMood <= 2.0f -> "Severe mood decline - immediate attention needed"
            averageMood <= 3.0f && moodDecline > 1.0f -> "Significant mood decline detected"
            averageMood <= 3.0f -> "Low mood levels detected"
            moodDecline > 1.0f -> "Rapid mood decline pattern"
            else -> "Mood patterns showing variation"
        }
        
        val trend = when {
            moodDecline > 1.0f -> RiskTrend.DECLINING
            moodDecline > 0.5f -> RiskTrend.DECLINING
            moodDecline < -0.5f -> RiskTrend.IMPROVING
            else -> RiskTrend.STABLE
        }
        
        return RiskIndicator(
            name = "Mood Patterns",
            riskLevel = riskLevel,
            description = description,
            category = RiskCategory.MOOD_PATTERNS,
            trend = trend
        )
    }
    
    /**
     * Assesses sleep-related risk
     */
    private fun assessSleepRisk(sleepData: List<SleepData>): RiskIndicator {
        if (sleepData.isEmpty()) {
            return RiskIndicator(
                name = "Sleep Pattern",
                riskLevel = 50,
                description = "Insufficient sleep data",
                category = RiskCategory.SLEEP
            )
        }
        
        val recentSleep = sleepData.takeLast(7)
        val averageHours = recentSleep.map { it.hours }.average()
        val irregularity = calculateSleepIrregularity(recentSleep)
        
        val riskLevel = when {
            averageHours < 5.0 -> 90
            averageHours < 6.0 -> 75
            averageHours < 7.0 && irregularity > 2.0 -> 70
            averageHours < 7.0 -> 60
            irregularity > 2.5 -> 65
            irregularity > 1.5 -> 50
            else -> 30
        }.toInt()
        
        val description = when {
            averageHours < 5.0 -> "Severe sleep deprivation"
            averageHours < 6.0 -> "Insufficient sleep - below recommended minimum"
            irregularity > 2.0 -> "Irregular sleep pattern increases relapse risk"
            else -> "Sleep patterns within acceptable range"
        }
        
        return RiskIndicator(
            name = "Sleep Pattern",
            riskLevel = riskLevel,
            description = description,
            category = RiskCategory.SLEEP,
            trend = if (irregularity > 2.0) RiskTrend.DECLINING else RiskTrend.STABLE
        )
    }
    
    /**
     * Assesses stress-related risk
     */
    private fun assessStressRisk(stressData: List<StressData>): RiskIndicator {
        if (stressData.isEmpty()) {
            return RiskIndicator(
                name = "Stress Level",
                riskLevel = 40,
                description = "Insufficient stress data",
                category = RiskCategory.STRESS
            )
        }
        
        val recentStress = stressData.takeLast(7).map { it.level.toFloat() }
        val averageStress = recentStress.average()
        val stressIncrease = recentStress.last() - recentStress.first()
        
        val riskLevel = when {
            averageStress >= 8.0f -> 85
            averageStress >= 7.0f -> 70
            averageStress >= 6.0f && stressIncrease > 1.0f -> 65
            averageStress >= 6.0f -> 55
            averageStress >= 5.0f && stressIncrease > 0.5f -> 50
            else -> 30
        }.toInt()
        
        val description = when {
            averageStress >= 8.0f -> "Extremely high stress levels detected"
            averageStress >= 7.0f -> "High stress detected in recent patterns"
            stressIncrease > 1.0f -> "Rapid stress accumulation"
            else -> "Stress levels manageable"
        }
        
        return RiskIndicator(
            name = "Stress Level",
            riskLevel = riskLevel,
            description = description,
            category = RiskCategory.STRESS,
            trend = if (stressIncrease > 0.5f) RiskTrend.DECLINING else RiskTrend.STABLE
        )
    }
    
    /**
     * Assesses social support risk
     */
    private fun assessSocialSupportRisk(moodEntries: List<MoodEntry>): RiskIndicator {
        // Simplified - would check actual social activity data
        // Assume low social activity if no recent mood improvement
        val recentMoods = moodEntries.takeLast(14).map { it.mood.toFloat() }
        val socialActivityScore = if (recentMoods.isEmpty()) 50.0 else {
            // Estimate social support based on mood stability/improvement
            val moodVariance = calculateVariance(recentMoods)
            val moodTrend = recentMoods.last() - recentMoods.first()
            (50.0 - moodVariance * 10.0 + moodTrend * 5.0).coerceIn(0.0, 100.0)
        }
        
        val riskLevel = (100 - socialActivityScore).toInt()
        
        val description = when {
            riskLevel >= 70 -> "Limited social interaction - increased isolation risk"
            riskLevel >= 50 -> "Social support may be limited"
            else -> "Social support appears adequate"
        }
        
        return RiskIndicator(
            name = "Social Support",
            riskLevel = riskLevel,
            description = description,
            category = RiskCategory.SOCIAL_SUPPORT
        )
    }
    
    /**
     * Assesses trigger exposure risk
     */
    private fun assessTriggerExposureRisk(): RiskIndicator {
        // Simplified - would check against user's known triggers
        val riskLevel = (Math.random() * 30 + 20).toInt() // Simulated 20-50 range
        
        val description = when {
            riskLevel >= 40 -> "Some exposure to known triggers detected"
            else -> "Low exposure to known triggers"
        }
        
        return RiskIndicator(
            name = "Trigger Exposure",
            riskLevel = riskLevel,
            description = description,
            category = RiskCategory.TRIGGER_EXPOSURE
        )
    }
    
    /**
     * Generates recommendations based on risk assessment
     */
    private fun generateRecommendations(
        riskLevel: RiskLevel,
        indicators: List<RiskIndicator>
    ): List<String> {
        val recommendations = mutableListOf<String>()
        
        when (riskLevel) {
            RiskLevel.CRITICAL -> {
                recommendations.add("Contact emergency support immediately")
                recommendations.add("Use safety plan coping strategies")
                recommendations.add("Notify support network")
            }
            RiskLevel.HIGH -> {
                recommendations.add("Increase monitoring frequency")
                recommendations.add("Contact support person")
                recommendations.add("Engage in planned coping strategies")
            }
            RiskLevel.MEDIUM -> {
                recommendations.add("Continue regular check-ins")
                recommendations.add("Monitor risk factors closely")
            }
            RiskLevel.LOW -> {
                recommendations.add("Maintain current prevention strategies")
            }
        }
        
        // Add specific recommendations based on high-risk indicators
        indicators.filter { it.riskLevel >= 70 }.forEach { indicator ->
            when (indicator.category) {
                RiskCategory.SLEEP -> recommendations.add("Prioritize sleep hygiene and consistent schedule")
                RiskCategory.STRESS -> recommendations.add("Practice stress management techniques")
                RiskCategory.SOCIAL_SUPPORT -> recommendations.add("Increase social connections")
                else -> {}
            }
        }
        
        return recommendations.distinct()
    }
    
    // Helper methods
    private fun calculateSleepIrregularity(sleepData: List<SleepData>): Double {
        if (sleepData.size < 2) return 0.0
        val hours = sleepData.map { it.hours }
        val average = hours.average()
        val variance = hours.map { (it - average) * (it - average) }.average()
        return Math.sqrt(variance)
    }
    
    private fun calculateVariance(values: List<Float>): Double {
        if (values.size < 2) return 0.0
        val average = values.average()
        val variance = values.map { (it - average) * (it - average) }.average()
        return variance
    }
}

// Helper data classes for sleep and stress (would come from repositories in real implementation)
data class SleepData(
    val date: Date,
    val hours: Double
)

data class StressData(
    val date: Date,
    val level: Int // 1-10 scale
)

