package com.serenityai.data.models

import java.util.Date

data class Session(
    val sessionId: String = "",
    val userId: String = "",
    val startTimestamp: Date = Date(),
    val endTimestamp: Date? = null,
    val messages: List<ChatMessage> = emptyList(),
    val moodScorePre: Int = 0,
    val moodScorePost: Int = 0
)

data class ChatMessage(
    val messageId: String = "",
    val text: String = "",
    val isUser: Boolean = true,
    val timestamp: Date = Date()
)
