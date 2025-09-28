package com.serenityai.usecases

import com.serenityai.data.models.*
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import java.util.*

class ChatHistoryUseCaseTest {

    private lateinit var chatHistoryUseCase: ChatHistoryUseCase

    @BeforeEach
    fun setUp() {
        chatHistoryUseCase = ChatHistoryUseCase()
    }

    @Nested
    @DisplayName("Chat History Retrieval")
    inner class ChatHistoryRetrieval {

        @Test
        @DisplayName("should retrieve chat history for user")
        fun shouldRetrieveChatHistoryForUser() = runTest {
            val userId = "user123"
            val sessions = listOf(
                createMockSession(userId, "session1"),
                createMockSession(userId, "session2"),
                createMockSession(userId, "session3")
            )

            val history = chatHistoryUseCase.getChatHistory(userId, sessions)

            assertNotNull(history)
            assertEquals(3, history.size)
            assertTrue(history.all { it.userId == userId })
        }

        @Test
        @DisplayName("should filter chat history by date range")
        fun shouldFilterChatHistoryByDateRange() = runTest {
            val userId = "user123"
            val now = Date()
            val weekAgo = Date(now.time - 7 * 24 * 60 * 60 * 1000)
            val twoWeeksAgo = Date(now.time - 14 * 24 * 60 * 60 * 1000)

            val sessions = listOf(
                createMockSession(userId, "session1", twoWeeksAgo),
                createMockSession(userId, "session2", weekAgo),
                createMockSession(userId, "session3", now)
            )

            val recentHistory = chatHistoryUseCase.getChatHistoryByDateRange(userId, sessions, weekAgo, now)

            assertEquals(2, recentHistory.size)
            assertTrue(recentHistory.all { it.startTimestamp >= weekAgo })
        }

        @Test
        @DisplayName("should sort chat history by date")
        fun shouldSortChatHistoryByDate() = runTest {
            val userId = "user123"
            val now = Date()
            val yesterday = Date(now.time - 24 * 60 * 60 * 1000)
            val twoDaysAgo = Date(now.time - 2 * 24 * 60 * 60 * 1000)

            val sessions = listOf(
                createMockSession(userId, "session1", twoDaysAgo),
                createMockSession(userId, "session2", now),
                createMockSession(userId, "session3", yesterday)
            )

            val sortedHistory = chatHistoryUseCase.getChatHistorySorted(userId, sessions)

            assertEquals(3, sortedHistory.size)
            assertTrue(sortedHistory[0].startTimestamp >= sortedHistory[1].startTimestamp)
            assertTrue(sortedHistory[1].startTimestamp >= sortedHistory[2].startTimestamp)
        }
    }

    @Nested
    @DisplayName("Chat History Analysis")
    inner class ChatHistoryAnalysis {

        @Test
        @DisplayName("should analyze chat patterns")
        fun shouldAnalyzeChatPatterns() = runTest {
            val userId = "user123"
            val sessions = listOf(
                createMockSessionWithMessages(userId, "session1", listOf("I'm feeling anxious", "Help me")),
                createMockSessionWithMessages(userId, "session2", listOf("I'm still anxious", "What can I do")),
                createMockSessionWithMessages(userId, "session3", listOf("I'm feeling better", "Thank you"))
            )

            val patterns = chatHistoryUseCase.analyzeChatPatterns(userId, sessions)

            assertNotNull(patterns)
            assertTrue(patterns.isNotEmpty())
            assertTrue(patterns.containsKey("common_themes"))
            assertTrue(patterns.containsKey("mood_trends"))
        }

        @Test
        @DisplayName("should identify common themes")
        fun shouldIdentifyCommonThemes() = runTest {
            val userId = "user123"
            val sessions = listOf(
                createMockSessionWithMessages(userId, "session1", listOf("I'm anxious about work", "Work stress")),
                createMockSessionWithMessages(userId, "session2", listOf("Work is overwhelming", "I hate my job")),
                createMockSessionWithMessages(userId, "session3", listOf("Work is better now", "Still some work issues"))
            )

            val themes = chatHistoryUseCase.identifyCommonThemes(userId, sessions)

            assertNotNull(themes)
            assertTrue(themes.isNotEmpty())
            assertTrue(themes.any { it.contains("work") })
        }

        @Test
        @DisplayName("should track mood progression")
        fun shouldTrackMoodProgression() = runTest {
            val userId = "user123"
            val sessions = listOf(
                createMockSessionWithMood(userId, "session1", 3, 5),
                createMockSessionWithMood(userId, "session2", 4, 6),
                createMockSessionWithMood(userId, "session3", 5, 7)
            )

            val progression = chatHistoryUseCase.trackMoodProgression(userId, sessions)

            assertNotNull(progression)
            assertTrue(progression.containsKey("overall_trend"))
            assertTrue(progression.containsKey("improvement_rate"))
        }
    }

    @Nested
    @DisplayName("Chat History Search")
    inner class ChatHistorySearch {

        @Test
        @DisplayName("should search chat history by keyword")
        fun shouldSearchChatHistoryByKeyword() = runTest {
            val userId = "user123"
            val sessions = listOf(
                createMockSessionWithMessages(userId, "session1", listOf("I'm feeling anxious", "Help me")),
                createMockSessionWithMessages(userId, "session2", listOf("I'm feeling depressed", "I need help")),
                createMockSessionWithMessages(userId, "session3", listOf("I'm feeling better", "Thank you"))
            )

            val searchResults = chatHistoryUseCase.searchChatHistory(userId, sessions, "anxious")

            assertNotNull(searchResults)
            assertTrue(searchResults.isNotEmpty())
            assertTrue(searchResults.any { it.sessionId == "session1" })
        }

        @Test
        @DisplayName("should search chat history by date")
        fun shouldSearchChatHistoryByDate() = runTest {
            val userId = "user123"
            val now = Date()
            val yesterday = Date(now.time - 24 * 60 * 60 * 1000)

            val sessions = listOf(
                createMockSession(userId, "session1", yesterday),
                createMockSession(userId, "session2", now)
            )

            val searchResults = chatHistoryUseCase.searchChatHistoryByDate(userId, sessions, yesterday)

            assertNotNull(searchResults)
            assertTrue(searchResults.isNotEmpty())
            assertTrue(searchResults.any { it.sessionId == "session1" })
        }

        @Test
        @DisplayName("should search chat history by mood range")
        fun shouldSearchChatHistoryByMoodRange() = runTest {
            val userId = "user123"
            val sessions = listOf(
                createMockSessionWithMood(userId, "session1", 2, 4),
                createMockSessionWithMood(userId, "session2", 6, 8),
                createMockSessionWithMood(userId, "session3", 3, 5)
            )

            val searchResults = chatHistoryUseCase.searchChatHistoryByMoodRange(userId, sessions, 1, 4)

            assertNotNull(searchResults)
            assertTrue(searchResults.isNotEmpty())
            assertTrue(searchResults.any { it.sessionId == "session1" })
            assertTrue(searchResults.any { it.sessionId == "session3" })
        }
    }

    @Nested
    @DisplayName("Chat History Statistics")
    inner class ChatHistoryStatistics {

        @Test
        @DisplayName("should calculate chat statistics")
        fun shouldCalculateChatStatistics() = runTest {
            val userId = "user123"
            val sessions = listOf(
                createMockSessionWithMessages(userId, "session1", listOf("Hello", "How are you")),
                createMockSessionWithMessages(userId, "session2", listOf("I'm fine", "Thank you")),
                createMockSessionWithMessages(userId, "session3", listOf("Goodbye"))
            )

            val statistics = chatHistoryUseCase.calculateChatStatistics(userId, sessions)

            assertNotNull(statistics)
            assertTrue(statistics.containsKey("total_sessions"))
            assertTrue(statistics.containsKey("total_messages"))
            assertTrue(statistics.containsKey("average_session_duration"))
            assertEquals(3, statistics["total_sessions"])
        }

        @Test
        @DisplayName("should calculate engagement metrics")
        fun shouldCalculateEngagementMetrics() = runTest {
            val userId = "user123"
            val sessions = listOf(
                createMockSessionWithMessages(userId, "session1", listOf("Hello", "How are you", "I'm fine")),
                createMockSessionWithMessages(userId, "session2", listOf("I'm fine", "Thank you")),
                createMockSessionWithMessages(userId, "session3", listOf("Goodbye"))
            )

            val metrics = chatHistoryUseCase.calculateEngagementMetrics(userId, sessions)

            assertNotNull(metrics)
            assertTrue(metrics.containsKey("messages_per_session"))
            assertTrue(metrics.containsKey("session_frequency"))
            assertTrue(metrics.containsKey("engagement_score"))
        }
    }

    @Nested
    @DisplayName("Chat History Export")
    inner class ChatHistoryExport {

        @Test
        @DisplayName("should export chat history to text")
        fun shouldExportChatHistoryToText() = runTest {
            val userId = "user123"
            val sessions = listOf(
                createMockSessionWithMessages(userId, "session1", listOf("Hello", "How are you")),
                createMockSessionWithMessages(userId, "session2", listOf("I'm fine", "Thank you"))
            )

            val exportedText = chatHistoryUseCase.exportChatHistoryToText(userId, sessions)

            assertNotNull(exportedText)
            assertTrue(exportedText.contains("Hello"))
            assertTrue(exportedText.contains("How are you"))
            assertTrue(exportedText.contains("I'm fine"))
        }

        @Test
        @DisplayName("should export chat history to JSON")
        fun shouldExportChatHistoryToJSON() = runTest {
            val userId = "user123"
            val sessions = listOf(
                createMockSessionWithMessages(userId, "session1", listOf("Hello", "How are you"))
            )

            val exportedJSON = chatHistoryUseCase.exportChatHistoryToJSON(userId, sessions)

            assertNotNull(exportedJSON)
            assertTrue(exportedJSON.contains("userId"))
            assertTrue(exportedJSON.contains("sessions"))
            assertTrue(exportedJSON.contains("Hello"))
        }
    }

    // Helper methods
    private fun createMockSession(userId: String, sessionId: String, date: Date = Date()): Session {
        return Session(
            sessionId = sessionId,
            userId = userId,
            startTimestamp = date,
            endTimestamp = Date(date.time + 30 * 60 * 1000), // 30 minutes later
            messages = emptyList(),
            moodScorePre = 5,
            moodScorePost = 6
        )
    }

    private fun createMockSessionWithMessages(userId: String, sessionId: String, messages: List<String>): Session {
        val chatMessages = messages.map { text ->
            ChatMessage(
                messageId = UUID.randomUUID().toString(),
                text = text,
                isUser = true,
                timestamp = Date()
            )
        }
        
        return Session(
            sessionId = sessionId,
            userId = userId,
            startTimestamp = Date(),
            endTimestamp = Date(),
            messages = chatMessages,
            moodScorePre = 5,
            moodScorePost = 6
        )
    }

    private fun createMockSessionWithMood(userId: String, sessionId: String, preMood: Int, postMood: Int): Session {
        return Session(
            sessionId = sessionId,
            userId = userId,
            startTimestamp = Date(),
            endTimestamp = Date(),
            messages = emptyList(),
            moodScorePre = preMood,
            moodScorePost = postMood
        )
    }
}

/**
 * Use Case implementation for Chat History
 */
class ChatHistoryUseCase {
    
    suspend fun getChatHistory(userId: String, sessions: List<Session>): List<Session> {
        return sessions.filter { it.userId == userId }
    }

    suspend fun getChatHistoryByDateRange(
        userId: String, 
        sessions: List<Session>, 
        startDate: Date, 
        endDate: Date
    ): List<Session> {
        return sessions.filter { 
            it.userId == userId && 
            it.startTimestamp >= startDate && 
            it.startTimestamp <= endDate 
        }
    }

    suspend fun getChatHistorySorted(userId: String, sessions: List<Session>): List<Session> {
        return getChatHistory(userId, sessions)
            .sortedByDescending { it.startTimestamp }
    }

    suspend fun analyzeChatPatterns(userId: String, sessions: List<Session>): Map<String, Any> {
        val patterns = mutableMapOf<String, Any>()
        
        val userSessions = getChatHistory(userId, sessions)
        val allMessages = userSessions.flatMap { it.messages }
        
        // Analyze common themes
        val themes = identifyCommonThemes(userId, sessions)
        patterns["common_themes"] = themes
        
        // Analyze mood trends
        val moodTrends = trackMoodProgression(userId, sessions)
        patterns["mood_trends"] = moodTrends
        
        // Analyze session frequency
        val sessionFrequency = calculateSessionFrequency(userSessions)
        patterns["session_frequency"] = sessionFrequency
        
        return patterns
    }

    suspend fun identifyCommonThemes(userId: String, sessions: List<Session>): List<String> {
        val userSessions = getChatHistory(userId, sessions)
        val allMessages = userSessions.flatMap { it.messages }
        
        val words = allMessages
            .flatMap { it.text.split(" ") }
            .filter { it.length > 3 }
            .map { it.lowercase() }
        
        val wordCounts = words.groupBy { it }.mapValues { it.value.size }
        
        return wordCounts
            .filter { it.value >= 2 }
            .toList()
            .sortedByDescending { it.second }
            .take(10)
            .map { it.first }
    }

    suspend fun trackMoodProgression(userId: String, sessions: List<Session>): Map<String, Any> {
        val userSessions = getChatHistory(userId, sessions)
        val moodScores = userSessions.map { it.moodScorePost }
        
        val progression = mutableMapOf<String, Any>()
        
        if (moodScores.size >= 2) {
            val firstMood = moodScores.first()
            val lastMood = moodScores.last()
            val improvement = lastMood - firstMood
            
            progression["overall_trend"] = if (improvement > 0) "improving" else if (improvement < 0) "declining" else "stable"
            progression["improvement_rate"] = improvement
            progression["average_mood"] = moodScores.average()
        }
        
        return progression
    }

    suspend fun searchChatHistory(userId: String, sessions: List<Session>, keyword: String): List<Session> {
        val userSessions = getChatHistory(userId, sessions)
        
        return userSessions.filter { session ->
            session.messages.any { message ->
                message.text.contains(keyword, ignoreCase = true)
            }
        }
    }

    suspend fun searchChatHistoryByDate(userId: String, sessions: List<Session>, date: Date): List<Session> {
        val userSessions = getChatHistory(userId, sessions)
        
        return userSessions.filter { session ->
            val sessionDate = session.startTimestamp
            sessionDate.year == date.year &&
            sessionDate.month == date.month &&
            sessionDate.date == date.date
        }
    }

    suspend fun searchChatHistoryByMoodRange(
        userId: String, 
        sessions: List<Session>, 
        minMood: Int, 
        maxMood: Int
    ): List<Session> {
        val userSessions = getChatHistory(userId, sessions)
        
        return userSessions.filter { session ->
            session.moodScorePost in minMood..maxMood
        }
    }

    suspend fun calculateChatStatistics(userId: String, sessions: List<Session>): Map<String, Any> {
        val userSessions = getChatHistory(userId, sessions)
        val statistics = mutableMapOf<String, Any>()
        
        statistics["total_sessions"] = userSessions.size
        statistics["total_messages"] = userSessions.sumOf { it.messages.size }
        
        val totalDuration = userSessions.sumOf { session ->
            val endTime = session.endTimestamp ?: Date()
            endTime.time - session.startTimestamp.time
        }
        statistics["total_duration"] = totalDuration
        
        if (userSessions.isNotEmpty()) {
            statistics["average_session_duration"] = totalDuration / userSessions.size
            statistics["average_messages_per_session"] = userSessions.sumOf { it.messages.size } / userSessions.size
        }
        
        return statistics
    }

    suspend fun calculateEngagementMetrics(userId: String, sessions: List<Session>): Map<String, Any> {
        val userSessions = getChatHistory(userId, sessions)
        val metrics = mutableMapOf<String, Any>()
        
        if (userSessions.isNotEmpty()) {
            metrics["messages_per_session"] = userSessions.sumOf { it.messages.size } / userSessions.size
            metrics["session_frequency"] = calculateSessionFrequency(userSessions)
            
            val totalMessages = userSessions.sumOf { it.messages.size }
            val totalDuration = userSessions.sumOf { session ->
                val endTime = session.endTimestamp ?: Date()
                endTime.time - session.startTimestamp.time
            }
            
            metrics["engagement_score"] = if (totalDuration > 0) {
                totalMessages.toDouble() / (totalDuration / 1000.0 / 60.0) // messages per minute
            } else {
                0.0
            }
        }
        
        return metrics
    }

    suspend fun exportChatHistoryToText(userId: String, sessions: List<Session>): String {
        val userSessions = getChatHistory(userId, sessions)
        val export = StringBuilder()
        
        export.appendLine("Chat History for User: $userId")
        export.appendLine("=" * 50)
        export.appendLine()
        
        userSessions.forEach { session ->
            export.appendLine("Session: ${session.sessionId}")
            export.appendLine("Date: ${session.startTimestamp}")
            export.appendLine("Mood Before: ${session.moodScorePre}, Mood After: ${session.moodScorePost}")
            export.appendLine()
            
            session.messages.forEach { message ->
                val sender = if (message.isUser) "You" else "AI"
                export.appendLine("$sender: ${message.text}")
            }
            
            export.appendLine()
            export.appendLine("-" * 30)
            export.appendLine()
        }
        
        return export.toString()
    }

    suspend fun exportChatHistoryToJSON(userId: String, sessions: List<Session>): String {
        val userSessions = getChatHistory(userId, sessions)
        
        val json = StringBuilder()
        json.append("{")
        json.append("\"userId\":\"$userId\",")
        json.append("\"exportDate\":\"${Date()}\",")
        json.append("\"sessions\":[")
        
        userSessions.forEachIndexed { index, session ->
            if (index > 0) json.append(",")
            json.append("{")
            json.append("\"sessionId\":\"${session.sessionId}\",")
            json.append("\"startTimestamp\":\"${session.startTimestamp}\",")
            json.append("\"endTimestamp\":\"${session.endTimestamp}\",")
            json.append("\"moodScorePre\":${session.moodScorePre},")
            json.append("\"moodScorePost\":${session.moodScorePost},")
            json.append("\"messages\":[")
            
            session.messages.forEachIndexed { msgIndex, message ->
                if (msgIndex > 0) json.append(",")
                json.append("{")
                json.append("\"messageId\":\"${message.messageId}\",")
                json.append("\"text\":\"${message.text}\",")
                json.append("\"isUser\":${message.isUser},")
                json.append("\"timestamp\":\"${message.timestamp}\"")
                json.append("}")
            }
            
            json.append("]")
            json.append("}")
        }
        
        json.append("]")
        json.append("}")
        
        return json.toString()
    }

    private fun calculateSessionFrequency(sessions: List<Session>): String {
        if (sessions.isEmpty()) return "No sessions"
        
        val now = Date()
        val weekAgo = Date(now.time - 7 * 24 * 60 * 60 * 1000)
        val recentSessions = sessions.count { it.startTimestamp >= weekAgo }
        
        return when {
            recentSessions >= 7 -> "Daily"
            recentSessions >= 3 -> "Frequent"
            recentSessions >= 1 -> "Occasional"
            else -> "Rare"
        }
    }
}
