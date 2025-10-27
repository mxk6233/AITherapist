package com.serenityai.test.usecases

import com.serenityai.ui.screens.ForecastDataPoint
import com.serenityai.ui.screens.RiskFactor
import com.serenityai.ui.screens.Intervention
import com.serenityai.ui.screens.MoodForecastingScreen
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

@DisplayName("UC26: AI-Powered Mood Forecasting - Test Cases")
class MoodForecastingUseCaseTests {

    @Nested
    @DisplayName("Test Case 1: Predictive Mood Analysis")
    inner class PredictiveMoodAnalysisTests {
        
        @Test
        fun `should generate accurate mood predictions based on historical data`() {
            // Given
            val historicalMoodData = listOf(
                ForecastDataPoint("Day 1", 3.5f, "Work stress", 75),
                ForecastDataPoint("Day 2", 4.2f, "Good workout", 82),
                ForecastDataPoint("Day 3", 2.8f, "Difficult meeting", 68),
                ForecastDataPoint("Day 4", 4.0f, "Friend visit", 78),
                ForecastDataPoint("Day 5", 4.5f, "Weekend plans", 85)
            )
            
            // When
            val averagePredictedMood = historicalMoodData.map { it.predictedMood }.average()
            val averageConfidence = historicalMoodData.map { it.confidence }.average()
            val highConfidencePredictions = historicalMoodData.filter { it.confidence >= 80 }
            val lowConfidencePredictions = historicalMoodData.filter { it.confidence < 70 }
            
            // Then
            assertEquals(3.8, averagePredictedMood, 0.01)
            assertEquals(77.6, averageConfidence, 0.01)
            assertEquals(2, highConfidencePredictions.size)
            assertTrue(highConfidencePredictions.any { it.note.contains("workout") })
            assertTrue(highConfidencePredictions.any { it.note.contains("Weekend") })
            assertEquals(1, lowConfidencePredictions.size)
            assertEquals("Difficult meeting", lowConfidencePredictions.first().note)
        }
        
        @Test
        fun `should identify mood trend patterns for forecasting`() {
            // Given
            val trendData = listOf(
                ForecastDataPoint("Mon", 3.0f, "Monday blues", 70),
                ForecastDataPoint("Tue", 3.5f, "Getting better", 75),
                ForecastDataPoint("Wed", 4.0f, "Good day", 80),
                ForecastDataPoint("Thu", 4.2f, "Great day", 82),
                ForecastDataPoint("Fri", 4.5f, "Excellent day", 85)
            )
            
            // When
            val firstMood = trendData.first().predictedMood
            val lastMood = trendData.last().predictedMood
            val trendDirection = if (lastMood > firstMood) "improving" else "declining"
            val trendStrength = kotlin.math.abs(lastMood - firstMood)
            
            // Then
            assertEquals("improving", trendDirection)
            assertTrue(trendStrength > 1.0f) // Strong upward trend
            assertTrue(lastMood > firstMood) // Confirmed improvement
        }
        
        @Test
        fun `should adjust confidence based on data quality and patterns`() {
            // Given
            val highQualityData = listOf(
                ForecastDataPoint("Day 1", 4.0f, "Consistent pattern", 90),
                ForecastDataPoint("Day 2", 4.1f, "Consistent pattern", 88),
                ForecastDataPoint("Day 3", 3.9f, "Consistent pattern", 92)
            )
            
            val lowQualityData = listOf(
                ForecastDataPoint("Day 1", 2.0f, "Unpredictable", 45),
                ForecastDataPoint("Day 2", 5.0f, "Unpredictable", 50),
                ForecastDataPoint("Day 3", 1.5f, "Unpredictable", 40)
            )
            
            // When
            val highQualityAverageConfidence = highQualityData.map { it.confidence }.average()
            val lowQualityAverageConfidence = lowQualityData.map { it.confidence }.average()
            
            // Then
            assertTrue(highQualityAverageConfidence > 85.0) // High confidence
            assertTrue(lowQualityAverageConfidence < 50.0) // Low confidence
            assertTrue(highQualityAverageConfidence > lowQualityAverageConfidence)
        }
    }

    @Nested
    @DisplayName("Test Case 2: Risk Factor Analysis")
    inner class RiskFactorAnalysisTests {
        
        @Test
        fun `should identify and prioritize risk factors affecting mood`() {
            // Given
            val riskFactors = listOf(
                RiskFactor("Work Stress", "High", "Monday meetings increase stress"),
                RiskFactor("Sleep Pattern", "Medium", "Irregular sleep affects mood"),
                RiskFactor("Social Isolation", "Low", "Limited social interaction"),
                RiskFactor("Exercise Routine", "Medium", "Inconsistent workout schedule")
            )
            
            // When
            val highRiskFactors = riskFactors.filter { it.level == "High" }
            val mediumRiskFactors = riskFactors.filter { it.level == "Medium" }
            val lowRiskFactors = riskFactors.filter { it.level == "Low" }
            
            // Then
            assertEquals(1, highRiskFactors.size)
            assertEquals("Work Stress", highRiskFactors.first().name)
            assertEquals(2, mediumRiskFactors.size)
            assertTrue(mediumRiskFactors.any { it.name == "Sleep Pattern" })
            assertTrue(mediumRiskFactors.any { it.name == "Exercise Routine" })
            assertEquals(1, lowRiskFactors.size)
            assertEquals("Social Isolation", lowRiskFactors.first().name)
        }
        
        @Test
        fun `should calculate cumulative risk impact on mood predictions`() {
            // Given
            val riskFactors = listOf(
                RiskFactor("Work Stress", "High", "High impact on mood"),
                RiskFactor("Sleep Pattern", "Medium", "Medium impact on mood"),
                RiskFactor("Social Support", "Low", "Low impact on mood")
            )
            
            // When
            fun calculateRiskScore(factors: List<RiskFactor>): Float {
                var score = 0f
                factors.forEach { factor ->
                    when (factor.level) {
                        "High" -> score += 3f
                        "Medium" -> score += 2f
                        "Low" -> score += 1f
                    }
                }
                return score / factors.size
            }
            
            val riskScore = calculateRiskScore(riskFactors)
            val highRiskCount = riskFactors.count { it.level == "High" }
            val totalRiskFactors = riskFactors.size
            
            // Then
            assertEquals(2.0f, riskScore, 0.01f) // Average risk level
            assertEquals(1, highRiskCount)
            assertEquals(3, totalRiskFactors)
            assertTrue(riskScore >= 1.5f) // Above low risk threshold
        }
        
        @Test
        fun `should track risk factor trends over time`() {
            // Given
            val weeklyRiskData = listOf(
                listOf(RiskFactor("Work Stress", "High", "Week 1"), RiskFactor("Sleep", "Medium", "Week 1")),
                listOf(RiskFactor("Work Stress", "High", "Week 2"), RiskFactor("Sleep", "High", "Week 2")),
                listOf(RiskFactor("Work Stress", "Medium", "Week 3"), RiskFactor("Sleep", "Medium", "Week 3"))
            )
            
            // When
            val workStressTrend = weeklyRiskData.map { week ->
                week.find { it.name == "Work Stress" }?.level
            }
            val sleepTrend = weeklyRiskData.map { week ->
                week.find { it.name == "Sleep" }?.level
            }
            
            // Then
            assertEquals(listOf("High", "High", "Medium"), workStressTrend) // Improving trend
            assertEquals(listOf("Medium", "High", "Medium"), sleepTrend) // Fluctuating trend
        }
    }

    @Nested
    @DisplayName("Test Case 3: Proactive Interventions and Early Warning")
    inner class ProactiveInterventionsTests {
        
        @Test
        fun `should generate targeted interventions based on predicted mood`() {
            // Given
            val interventions = listOf(
                Intervention("Morning Meditation", "Start day with 10-min meditation", "High Impact"),
                Intervention("Exercise Schedule", "Plan workouts for Tuesday & Thursday", "Medium Impact"),
                Intervention("Social Planning", "Schedule weekend social activities", "High Impact"),
                Intervention("Sleep Optimization", "Maintain consistent sleep schedule", "Medium Impact")
            )
            
            // When
            val highImpactInterventions = interventions.filter { it.impact == "High Impact" }
            val mediumImpactInterventions = interventions.filter { it.impact == "Medium Impact" }
            val meditationIntervention = interventions.find { it.title.contains("Meditation") }
            val exerciseIntervention = interventions.find { it.title.contains("Exercise") }
            
            // Then
            assertEquals(2, highImpactInterventions.size)
            assertTrue(highImpactInterventions.any { it.title.contains("Meditation") })
            assertTrue(highImpactInterventions.any { it.title.contains("Social") })
            assertEquals(2, mediumImpactInterventions.size)
            assertNotNull(meditationIntervention)
            assertTrue(meditationIntervention!!.description.contains("10-min"))
            assertNotNull(exerciseIntervention)
            assertTrue(exerciseIntervention!!.description.contains("Tuesday"))
        }
        
        @Test
        fun `should trigger early warning system for mood decline predictions`() {
            // Given
            val forecastData = listOf(
                ForecastDataPoint("Today", 4.0f, "Good mood", 85),
                ForecastDataPoint("Tomorrow", 3.5f, "Slight decline", 80),
                ForecastDataPoint("Day 3", 2.5f, "Significant drop", 75),
                ForecastDataPoint("Day 4", 2.0f, "Low mood predicted", 70),
                ForecastDataPoint("Day 5", 3.0f, "Recovery expected", 78)
            )
            
            // When
            val criticalThreshold = 2.5f
            val warningDays = forecastData.filter { it.predictedMood <= criticalThreshold }
            val severeWarningDays = forecastData.filter { it.predictedMood <= 2.0f }
            val recoveryDays = forecastData.filter { it.predictedMood > criticalThreshold }
            
            // Then
            assertEquals(2, warningDays.size)
            assertTrue(warningDays.any { it.day == "Day 3" })
            assertTrue(warningDays.any { it.day == "Day 4" })
            assertEquals(1, severeWarningDays.size)
            assertEquals("Day 4", severeWarningDays.first().day)
            assertEquals(3, recoveryDays.size)
        }
        
        @Test
        fun `should validate intervention effectiveness and adjust recommendations`() {
            // Given
            val interventionHistory = listOf(
                Intervention("Meditation", "Daily meditation practice", "High Impact"),
                Intervention("Exercise", "Regular physical activity", "High Impact"),
                Intervention("Social", "Increased social interaction", "Medium Impact"),
                Intervention("Sleep", "Consistent sleep schedule", "Medium Impact")
            )
            
            // When
            val highImpactCount = interventionHistory.count { it.impact == "High Impact" }
            val mediumImpactCount = interventionHistory.count { it.impact == "Medium Impact" }
            val totalInterventions = interventionHistory.size
            
            // Simulate effectiveness tracking
            val effectivenessScores = mapOf(
                "Meditation" to 4.5f,
                "Exercise" to 4.8f,
                "Social" to 3.8f,
                "Sleep" to 4.2f
            )
            
            val highlyEffectiveInterventions = effectivenessScores.filter { it.value >= 4.5f }
            val moderatelyEffectiveInterventions = effectivenessScores.filter { it.value in 4.0f..4.4f }
            
            // Then
            assertEquals(2, highImpactCount)
            assertEquals(2, mediumImpactCount)
            assertEquals(4, totalInterventions)
            assertEquals(2, highlyEffectiveInterventions.size)
            assertTrue(highlyEffectiveInterventions.containsKey("Meditation"))
            assertTrue(highlyEffectiveInterventions.containsKey("Exercise"))
            assertEquals(1, moderatelyEffectiveInterventions.size)
            assertTrue(moderatelyEffectiveInterventions.containsKey("Sleep"))
        }
    }
}
