package com.serenityai.data.models

import java.util.Date

data class AITherapistResponse(
    val id: String = "",
    val sessionId: String = "",
    val message: String = "",
    val type: ResponseType = ResponseType.GENERAL,
    val suggestions: List<String> = emptyList(),
    val affirmations: List<String> = emptyList(),
    val resources: List<Resource> = emptyList(),
    val moodAnalysis: MoodAnalysis? = null,
    val createdAt: Date = Date()
)

enum class ResponseType {
    GENERAL,
    THERAPEUTIC,
    AFFIRMATION,
    RESOURCE,
    CHALLENGE_SUGGESTION,
    CRISIS_SUPPORT,
    CELEBRATION
}

data class MoodAnalysis(
    val detectedMood: String = "",
    val confidence: Float = 0.0f,
    val sentiment: SentimentType = SentimentType.NEUTRAL,
    val keywords: List<String> = emptyList(),
    val suggestedActions: List<String> = emptyList()
)

enum class SentimentType {
    VERY_POSITIVE,
    POSITIVE,
    NEUTRAL,
    NEGATIVE,
    VERY_NEGATIVE
}

data class Resource(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val type: ResourceType = ResourceType.ARTICLE,
    val url: String = "",
    val category: String = "",
    val isRecommended: Boolean = false
)

enum class ResourceType {
    ARTICLE,
    VIDEO,
    AUDIO,
    EXERCISE,
    BOOK,
    APP,
    WEBSITE,
    HOTLINE
}

data class TherapySession(
    val id: String = "",
    val userId: String = "",
    val startTime: Date = Date(),
    val endTime: Date? = null,
    val duration: Long = 0, // in minutes
    val messages: List<ChatMessage> = emptyList(),
    val summary: String = "",
    val insights: List<String> = emptyList(),
    val moodTrend: List<MoodEntry> = emptyList(),
    val isActive: Boolean = true
)
