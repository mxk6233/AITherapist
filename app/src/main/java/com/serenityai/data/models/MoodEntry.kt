package com.serenityai.data.models

import java.util.Date

data class MoodEntry(
    val id: String = "",
    val userId: String = "",
    val date: Date = Date(),
    val mood: Int = 5, // 1-10 scale
    val energy: Int = 5, // 1-10 scale
    val stress: Int = 5, // 1-10 scale
    val anxiety: Int = 5, // 1-10 scale
    val sleep: Int = 5, // 1-10 scale
    val notes: String = "",
    val tags: List<String> = emptyList(),
    val activities: List<String> = emptyList(),
    val weather: String? = null,
    val location: String? = null
)

data class MoodTrend(
    val period: String, // "week", "month", "year"
    val averageMood: Double,
    val moodHistory: List<MoodEntry>,
    val insights: List<String> = emptyList()
)

data class MoodInsight(
    val type: InsightType,
    val message: String,
    val confidence: Double, // 0.0 to 1.0
    val actionable: Boolean = false,
    val suggestion: String? = null
)

enum class InsightType {
    POSITIVE_TREND,
    NEGATIVE_TREND,
    PATTERN_DETECTED,
    RECOMMENDATION,
    WARNING
}
