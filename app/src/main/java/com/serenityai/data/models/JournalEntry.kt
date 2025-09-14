package com.serenityai.data.models

import java.util.Date

data class JournalEntry(
    val id: String = "",
    val userId: String = "",
    val title: String = "",
    val content: String = "",
    val type: JournalType = JournalType.FREEFORM,
    val prompt: String? = null,
    val mood: Int? = null, // Mood rating 1-10
    val tags: List<String> = emptyList(),
    val isPrivate: Boolean = true,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)

enum class JournalType {
    FREEFORM,
    GUIDED,
    GRATITUDE,
    REFLECTION,
    GOAL_REVIEW,
    CHALLENGE_REFLECTION
}

data class JournalPrompt(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val category: JournalType = JournalType.GUIDED,
    val questions: List<String> = emptyList(),
    val isActive: Boolean = true
)
