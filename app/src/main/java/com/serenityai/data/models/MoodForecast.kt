package com.serenityai.data.models

import java.util.Date

/**
 * UC26: AI-Powered Mood Forecasting - Data Models
 * 
 * Represents mood forecasting data, predictions, and patterns.
 */
data class ForecastDay(
    val day: String,
    val date: Date = Date(),
    val predictedMood: Float, // 1.0-5.0 scale
    val note: String,
    val confidence: Float = 75.0f, // 0-100%
    val factors: List<ForecastFactor> = emptyList()
)

data class MoodPrediction(
    val id: String = "",
    val title: String,
    val description: String,
    val confidence: Float, // 0-100%
    val patternType: PatternType = PatternType.WEEKLY,
    val detectedAt: Date = Date()
)

enum class PatternType {
    WEEKLY,
    WEEKEND_BOOST,
    MIDWEEK_DIP,
    SEASONAL,
    EXERCISE_IMPACT,
    SOCIAL_IMPACT,
    WORK_RELATED,
    UNKNOWN
}

data class ForecastFactor(
    val name: String,
    val impact: Float, // Positive or negative impact on mood
    val confidence: Float // Confidence in this factor's influence
)

data class MoodForecast(
    val id: String = "",
    val userId: String = "",
    val generatedAt: Date = Date(),
    val forecastDays: List<ForecastDay>, // Must be 7 days
    val predictions: List<MoodPrediction> = emptyList(),
    val averagePredictedMood: Float = 0f,
    val peakDay: ForecastDay? = null,
    val lowestDay: ForecastDay? = null,
    val overallTrend: ForecastTrend = ForecastTrend.STABLE,
    val riskLevel: RiskLevel = RiskLevel.LOW,
    val recommendations: List<String> = emptyList()
)

enum class ForecastTrend {
    IMPROVING,
    DECLINING,
    STABLE,
    VOLATILE
}

// RiskLevel enum moved to RelapsePrevention.kt to avoid duplication
// Using com.serenityai.data.models.RiskLevel from RelapsePrevention.kt

data class WeeklyPattern(
    val weekdayAverage: Float,
    val weekendAverage: Float,
    val weekendBoost: Float,
    val patternStrength: Float, // Percentage difference (>=15% is actionable)
    val confidence: Float
)

