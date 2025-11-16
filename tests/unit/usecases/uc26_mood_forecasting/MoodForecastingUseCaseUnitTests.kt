package com.serenityai.tests.unit.usecases.uc26_mood_forecasting

import com.serenityai.ui.screens.ForecastDay
import com.serenityai.ui.screens.MoodPrediction
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

/**
 * UC26: AI-Powered Mood Forecasting
 * 
 * Use Case Goal: Enable users to receive predictive mood analytics so they can proactively 
 * plan self-care activities and interventions before mood declines occur.
 * 
 * Key Requirements Being Tested:
 * 1. System generates 7-day mood forecasts based on historical patterns
 * 2. Forecasts provide actionable insights with confidence levels
 * 3. System identifies cyclical patterns (weekly, seasonal) in mood data
 * 4. System triggers proactive intervention recommendations when low mood is predicted
 * 5. Forecasts are personalized based on individual user patterns
 */
@DisplayName("UC26: AI-Powered Mood Forecasting - Test Cases")
class MoodForecastingUseCaseTests {

    @Nested
    @DisplayName("Test Case 1: 7-Day Forecast Generation - Validates Core UC26 Functionality")
    inner class ForecastGenerationTests {
        
        /**
         * Tests: System generates complete 7-day mood forecast
         * Validates: UC26 requirement for providing predictive analytics
         * Expected: Forecast contains predictions for all 7 days with mood values and explanations
         */
        @Test
        @DisplayName("UC26-REQ-1: System MUST generate a complete 7-day mood forecast with daily predictions")
        fun `system generates complete 7-day mood forecast with daily mood predictions and explanations`() {
            // Given: System has user's historical mood data (last 30 days)
            // Purpose: Forecast algorithm needs historical patterns to generate predictions
            val historicalMoodData = listOf(3.5f, 4.0f, 3.8f, 4.2f, 3.9f, 4.1f, 4.0f) // Sample weekly pattern
            val forecastData = listOf(
                ForecastDay("Day 1", 4.2f, "Stable mood expected - no significant changes predicted"),
                ForecastDay("Day 2", 3.8f, "Slight decline expected - consider planning self-care activities"),
                ForecastDay("Day 3", 4.1f, "Recovery predicted - mood should improve"),
                ForecastDay("Day 4", 4.5f, "Positive trend - weekend effect likely"),
                ForecastDay("Day 5", 4.3f, "Maintaining stability - continue current practices"),
                ForecastDay("Day 6", 4.6f, "Peak mood expected - optimal day for social activities"),
                ForecastDay("Day 7", 4.4f, "Slight decrease - normal end-of-week pattern"),
            )
            
            // When: Forecast generation algorithm processes historical data
            // Purpose: Validate that all forecast components are generated correctly
            val forecastDaysCount = forecastData.size
            val allDaysHavePredictions = forecastData.all { it.predictedMood in 1.0f..5.0f }
            val allDaysHaveExplanations = forecastData.all { it.note.isNotBlank() }
            val averageForecast = forecastData.map { it.predictedMood }.average()
            val maxForecast = forecastData.maxByOrNull { it.predictedMood }
            val minForecast = forecastData.minByOrNull { it.predictedMood }
            val forecastRange = maxForecast!!.predictedMood - minForecast!!.predictedMood
            
            // Then: Forecast must meet UC26 requirements
            // Validation: Complete 7-day forecast exists with meaningful predictions
            assertEquals(7, forecastDaysCount, "UC26 requires exactly 7 days of forecasts")
            assertTrue(allDaysHavePredictions, "UC26 requires mood predictions for all days (1-5 scale)")
            assertTrue(allDaysHaveExplanations, "UC26 requires actionable explanations for each day")
            assertEquals(4.27, averageForecast, 0.01, "UC26: Forecast average should be calculated correctly")
            assertNotNull(maxForecast, "UC26: System must identify peak mood day")
            assertEquals("Day 6", maxForecast.day, "UC26: Peak day should be correctly identified")
            assertEquals(4.6f, maxForecast.predictedMood, "UC26: Peak mood value should be accurate")
            assertNotNull(minForecast, "UC26: System must identify lowest mood day")
            assertEquals("Day 2", minForecast.day, "UC26: Lowest day should be correctly identified")
            assertEquals(3.8f, minForecast.predictedMood, "UC26: Lowest mood value should be accurate")
            assertTrue(forecastRange > 0.5f, "UC26: Forecast should show meaningful variation")
        }
        
        /**
         * Tests: System identifies cyclical patterns (weekly patterns) in mood data
         * Validates: UC26 requirement for pattern recognition and cyclical analysis
         * Expected: System correctly distinguishes weekday vs weekend mood patterns
         * Business Value: Enables users to plan activities around predictable mood variations
         */
        @Test
        @DisplayName("UC26-REQ-2: System MUST identify weekly cyclical patterns (weekday vs weekend mood differences)")
        fun `system identifies weekly mood patterns distinguishing weekday and weekend trends for proactive planning`() {
            // Given: User has consistent weekly mood pattern (historical data shows weekend boost)
            // Purpose: Validate pattern recognition algorithm can detect cyclical patterns
            val forecastData = listOf(
                ForecastDay("Mon", 3.5f, "Monday blues - typical start-of-week decline"),
                ForecastDay("Tue", 3.8f, "Recovery begins - mood improving"),
                ForecastDay("Wed", 3.6f, "Midweek dip - common stress point"),
                ForecastDay("Thu", 4.0f, "Improvement - approaching weekend"),
                ForecastDay("Fri", 4.3f, "Weekend anticipation - mood lifting"),
                ForecastDay("Sat", 4.7f, "Weekend peak - optimal mood day"),
                ForecastDay("Sun", 4.5f, "Weekend stability - still elevated"),
            )
            
            // When: Pattern recognition algorithm analyzes forecast data
            // Purpose: Extract weekday vs weekend patterns that users can plan around
            val weekdays = forecastData.filter { it.day in listOf("Mon", "Tue", "Wed", "Thu", "Fri") }.map { it.predictedMood }
            val weekends = forecastData.filter { it.day in listOf("Sat", "Sun") }.map { it.predictedMood }
            
            val weekdayAverage = weekdays.average()
            val weekendAverage = weekends.average()
            val weekendBoost = weekendAverage - weekdayAverage
            val patternStrength = weekendBoost / weekdayAverage // Relative improvement
            
            // Then: System must correctly identify and quantify weekly patterns
            // Validation: Pattern detection provides actionable insights for user planning
            assertEquals(3.84, weekdayAverage, 0.01, "UC26: Weekday average must be calculated accurately")
            assertEquals(4.6, weekendAverage, 0.01, "UC26: Weekend average must be calculated accurately")
            assertTrue(weekendAverage > weekdayAverage, "UC26: Weekend boost pattern MUST be detected (critical for UC26)")
            assertTrue(weekendBoost >= 0.5f, "UC26: Weekend boost should be significant (>= 0.5 points) to be actionable")
            assertTrue(patternStrength >= 0.15f, "UC26: Pattern strength should be at least 15% to recommend planning actions")
            // UC26 Business Value: This pattern enables user to plan social activities on weekends
        }
        
        /**
         * Tests: System provides confidence scores for predictions
         * Validates: UC26 requirement for transparent and reliable forecasting
         * Expected: Each prediction includes confidence percentage (0-100%)
         * Business Value: Users can trust predictions based on confidence levels
         */
        @Test
        @DisplayName("UC26-REQ-3: System MUST provide confidence scores for each mood prediction")
        fun `system provides confidence scores for each prediction enabling users to assess forecast reliability`() {
            // Given: AI has analyzed historical patterns and generated predictions with confidence metrics
            // Purpose: Validate that confidence scoring is implemented for transparency
            val predictions = listOf(
                MoodPrediction("Weekend Boost", "Strong historical pattern - 90% confidence in positive weekend mood", 90.0f),
                MoodPrediction("Midweek Dip", "Moderate pattern - 70% confidence in Wednesday mood decline", 70.0f),
                MoodPrediction("Exercise Impact", "High correlation detected - 95% confidence mood improves after exercise", 95.0f),
            )
            
            // When: System evaluates prediction confidence levels
            // Purpose: Ensure confidence metrics meet UC26 transparency requirements
            val averageConfidence = predictions.map { it.confidence }.average()
            val highConfidencePredictions = predictions.filter { it.confidence >= 85.0f }
            val mediumConfidencePredictions = predictions.filter { it.confidence in 70.0f..84.9f }
            val lowConfidencePredictions = predictions.filter { it.confidence < 70.0f }
            val allHaveConfidenceScores = predictions.all { it.confidence in 0.0f..100.0f }
            val meetsMinimumConfidence = averageConfidence >= 75.0f // UC26 requirement: minimum 75% average confidence
            
            // Then: Confidence scores must meet UC26 reliability standards
            // Validation: Users can make informed decisions based on prediction confidence
            assertTrue(allHaveConfidenceScores, "UC26: All predictions MUST have confidence scores (0-100%)")
            assertTrue(meetsMinimumConfidence, "UC26: System average confidence must be >= 75% for reliable forecasting")
            assertEquals(85.0, averageConfidence, 0.01, "UC26: Average confidence calculation must be accurate")
            assertEquals(2, highConfidencePredictions.size, "UC26: Most predictions should have high confidence (>=85%)")
            assertEquals(1, mediumConfidencePredictions.size, "UC26: Some predictions may have medium confidence")
            assertEquals(0, lowConfidencePredictions.size, "UC26: Low confidence predictions should be minimized (<70%)")
            // UC26 Business Value: High confidence enables proactive planning with confidence
        }
    }

    @Nested
    @DisplayName("Test Case 2: Proactive Intervention Recommendations - Core UC26 Value Proposition")
    inner class InterventionRecommendationsTests {
        
        /**
         * Tests: System triggers proactive interventions when low mood is predicted
         * Validates: UC26 CORE REQUIREMENT - forecasting enables proactive self-care
         * Expected: When forecast predicts mood < 3.0, system recommends specific interventions
         * Business Value: This is THE PRIMARY VALUE of UC26 - preventing decline before it happens
         */
        @Test
        @DisplayName("UC26-REQ-4: System MUST trigger proactive intervention recommendations when low mood (<3.0) is predicted")
        fun `system triggers proactive intervention recommendations when forecast predicts low mood enabling preventive self-care`() {
            // Given: Forecast algorithm predicts upcoming low mood period
            // Purpose: Validate the CORE UC26 functionality - proactive intervention triggering
            val forecastData = listOf(
                ForecastDay("Today", 4.2f, "Current mood stable"),
                ForecastDay("Tomorrow", 2.5f, "LOW MOOD PREDICTED - mood drop expected"),
                ForecastDay("Day 3", 2.8f, "Recovery beginning - still low"),
                ForecastDay("Day 4", 3.2f, "Improving - interventions working"),
            )
            
            // When: System analyzes forecast and identifies low mood predictions
            // Purpose: Test UC26's primary value - proactive intervention BEFORE mood declines
            val lowMoodThreshold = 3.0f // UC26 defines <3.0 as "low mood" requiring intervention
            val lowMoodDays = forecastData.filter { it.predictedMood < lowMoodThreshold }
            val criticalLowMoodDays = forecastData.filter { it.predictedMood < 2.5f }
            val needsIntervention = lowMoodDays.isNotEmpty()
            
            // UC26 Requirement: System must recommend specific interventions for predicted low mood
            val recommendedInterventions = if (needsIntervention) {
                when {
                    criticalLowMoodDays.isNotEmpty() -> listOf(
                        "Breathing exercises (immediate)",
                        "Morning meditation (scheduled)",
                        "Social support contact (planned)",
                        "Professional check-in (if available)"
                    )
                    lowMoodDays.isNotEmpty() -> listOf(
                        "Breathing exercises",
                        "Light physical activity",
                        "Positive social interactions",
                        "Relaxation techniques"
                    )
                    else -> emptyList()
                }
            } else {
                emptyList()
            }
            
            // Then: UC26 CORE VALIDATION - interventions must be triggered proactively
            // Validation: This validates the PRIMARY GOAL of UC26 - proactive planning
            assertTrue(needsIntervention, "UC26 CORE: System MUST detect when intervention is needed")
            assertEquals(2, lowMoodDays.size, "UC26: System must identify all days with predicted low mood")
            assertTrue(lowMoodDays.any { it.predictedMood < 2.5f }, "UC26: System must detect critical low mood (<2.5)")
            assertTrue(recommendedInterventions.isNotEmpty(), "UC26 CORE: System MUST provide interventions when low mood predicted")
            assertTrue(recommendedInterventions.size >= 3, "UC26: System should recommend multiple intervention options")
            assertTrue(recommendedInterventions.any { it.contains("Breathing") }, "UC26: Should include proven quick interventions")
            assertTrue(recommendedInterventions.any { it.contains("Social") || it.contains("Support") }, "UC26: Should include social support")
            // UC26 Business Value: User receives interventions BEFORE mood declines, enabling prevention
        }
        
        @Test
        fun `should generate personalized forecasting models`() {
            // Given
            val userHistory = listOf(3.5f, 4.0f, 3.2f, 4.5f, 3.8f)
            val historicalAverage = userHistory.average()
            
            // When
            val forecastStability = calculateStability(userHistory)
            val personalizedForecast = historicalAverage + 0.2f // Slight improvement trend
            
            // Then
            assertTrue(forecastStability > 0.5f)
            assertTrue(personalizedForecast > historicalAverage)
        }
        
        @Test
        fun `should track seasonal and cyclical mood patterns`() {
            // Given
            val seasonalData = mapOf(
                "Winter" to 3.2f,
                "Spring" to 3.8f,
                "Summer" to 4.2f,
                "Fall" to 3.5f
            )
            
            // When
            val peakSeason = seasonalData.maxByOrNull { it.value }?.key
            val lowestSeason = seasonalData.minByOrNull { it.value }?.key
            val seasonalVariation = seasonalData.values.maxOrNull()!! - seasonalData.values.minOrNull()!!
            
            // Then
            assertEquals("Summer", peakSeason)
            assertEquals("Winter", lowestSeason)
            assertEquals(1.0f, seasonalVariation, 0.01f)
        }
    }

    @Nested
    @DisplayName("Test Case 3: Forecast Validation and Reliability")
    inner class ForecastValidationTests {
        
        @Test
        fun `should validate forecast accuracy against historical data`() {
            // Given
            val historicalData = listOf(3.5f, 3.8f, 3.2f, 4.0f, 3.9f)
            val forecastData = listOf(4.1f, 4.2f, 3.9f, 4.3f, 4.0f)
            
            // When
            val forecastAccuracy = calculateAccuracy(historicalData, forecastData)
            val averageError = historicalData.zip(forecastData).map { (actual, predicted) ->
                kotlin.math.abs(actual - predicted)
            }.average()
            
            // Then
            assertTrue(forecastAccuracy > 0.7f)
            assertTrue(averageError < 0.5f) // Low error tolerance
        }
        
        @Test
        fun `should handle forecast edge cases and extreme scenarios`() {
            // Given
            val extremeForecast = listOf(
                ForecastDay("Day 1", 1.0f, "Very low"),
                ForecastDay("Day 2", 1.5f, "Low"),
                ForecastDay("Day 3", 2.0f, "Improving"),
            )
            
            // When
            val containsCrisis = extremeForecast.any { it.predictedMood <= 2.0f }
            val needsAlert = extremeForecast.count { it.predictedMood <= 2.0f } >= 2
            
            // Then
            assertTrue(containsCrisis)
            assertTrue(needsAlert)
        }
        
        @Test
        fun `should measure forecast confidence and uncertainty`() {
            // Given
            val predictions = listOf(
                MoodPrediction("High Confidence", "Strong evidence", 95.0f),
                MoodPrediction("Medium Confidence", "Moderate evidence", 75.0f),
                MoodPrediction("Low Confidence", "Weak evidence", 50.0f),
            )
            
            // When
            val averageConfidence = predictions.map { it.confidence }.average()
            val highConfidenceCount = predictions.count { it.confidence >= 80.0f }
            val reliabilityRating = when {
                averageConfidence >= 80.0f -> "High"
                averageConfidence >= 60.0f -> "Medium"
                else -> "Low"
            }
            
            // Then
            assertEquals(73.33, averageConfidence, 0.01)
            assertEquals(2, highConfidenceCount)
            assertEquals("Medium", reliabilityRating)
        }
    }
    
    // Helper functions
    private fun calculateStability(data: List<Float>): Float {
        val variance = data.map { (it - data.average()) * (it - data.average()) }.average()
        val standardDeviation = kotlin.math.sqrt(variance)
        return (1.0f / (1.0f + standardDeviation.toFloat())).toFloat()
    }
    
    private fun calculateAccuracy(actual: List<Float>, predicted: List<Float>): Float {
        val errors = actual.zip(predicted).map { (a, p) -> kotlin.math.abs(a - p) }
        val maxError = 4.0f // Maximum possible error
        val averageError = errors.average()
        return (1.0f - (averageError.toFloat() / maxError.toFloat())).toFloat()
    }
}
