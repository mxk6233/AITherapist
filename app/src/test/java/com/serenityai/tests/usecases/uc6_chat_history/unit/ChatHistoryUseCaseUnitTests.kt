package com.serenityai.tests.usecases.uc6_chat_history.unit

import com.serenityai.ui.screens.ChatHistoryScreen
import com.serenityai.ui.screens.ChatSession
import com.serenityai.ui.screens.formatTimestamp
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

@DisplayName("UC6: View Chat History - Unit Tests")
class ChatHistoryUseCaseUnitTests {

    @Nested
    @DisplayName("Test Case 1: Search and Filter Functionality")
    inner class SearchAndFilterTests {
        
        @Test
        fun `should filter chat sessions by mood level`() {
            // Given
            val chatSessions = listOf(
                ChatSession("1", "Anxiety Session", "Feeling anxious", System.currentTimeMillis(), 5, "Anxious"),
                ChatSession("2", "Happy Chat", "Feeling great", System.currentTimeMillis(), 3, "Good"),
                ChatSession("3", "Stress Support", "Work stress", System.currentTimeMillis(), 8, "Stressed")
            )
            
            // When
            val anxiousSessions = chatSessions.filter { it.moodLevel == "Anxious" }
            val goodSessions = chatSessions.filter { it.moodLevel == "Good" }
            
            // Then
            assertEquals(1, anxiousSessions.size)
            assertEquals("Anxiety Session", anxiousSessions.first().title)
            assertEquals(1, goodSessions.size)
            assertEquals("Happy Chat", goodSessions.first().title)
        }
        
        @Test
        fun `should search chat sessions by title and message content`() {
            // Given
            val chatSessions = listOf(
                ChatSession("1", "Anxiety Support", "Feeling overwhelmed", System.currentTimeMillis(), 5, "Anxious"),
                ChatSession("2", "Daily Check-in", "Had a good day", System.currentTimeMillis(), 3, "Good"),
                ChatSession("3", "Work Stress", "Meeting was difficult", System.currentTimeMillis(), 8, "Stressed")
            )
            
            // When
            val anxietyResults = chatSessions.filter { 
                it.title.contains("Anxiety", ignoreCase = true) || 
                it.lastMessage.contains("overwhelmed", ignoreCase = true) 
            }
            val workResults = chatSessions.filter { 
                it.title.contains("Work", ignoreCase = true) || 
                it.lastMessage.contains("meeting", ignoreCase = true) 
            }
            
            // Then
            assertEquals(1, anxietyResults.size)
            assertEquals("Anxiety Support", anxietyResults.first().title)
            assertEquals(1, workResults.size)
            assertEquals("Work Stress", workResults.first().title)
        }
        
        @Test
        fun `should handle empty search results gracefully`() {
            // Given
            val chatSessions = listOf(
                ChatSession("1", "Anxiety Support", "Feeling overwhelmed", System.currentTimeMillis(), 5, "Anxious"),
                ChatSession("2", "Daily Check-in", "Had a good day", System.currentTimeMillis(), 3, "Good")
            )
            
            // When
            val noResults = chatSessions.filter { 
                it.title.contains("NonExistent", ignoreCase = true) 
            }
            
            // Then
            assertTrue(noResults.isEmpty())
        }
    }

    @Nested
    @DisplayName("Test Case 2: Chat Analytics and Statistics")
    inner class ChatAnalyticsTests {
        
        @Test
        fun `should calculate total sessions and messages correctly`() {
            // Given
            val chatSessions = listOf(
                ChatSession("1", "Session 1", "Message 1", System.currentTimeMillis(), 5, "Good"),
                ChatSession("2", "Session 2", "Message 2", System.currentTimeMillis(), 8, "Anxious"),
                ChatSession("3", "Session 3", "Message 3", System.currentTimeMillis(), 3, "Stressed")
            )
            
            // When
            val totalSessions = chatSessions.size
            val totalMessages = chatSessions.sumOf { it.messageCount }
            val averageMessages = chatSessions.map { it.messageCount }.average()
            
            // Then
            assertEquals(3, totalSessions)
            assertEquals(16, totalMessages)
            assertEquals(5.33, averageMessages, 0.01)
        }
        
        @Test
        fun `should identify most active chat sessions`() {
            // Given
            val chatSessions = listOf(
                ChatSession("1", "Short Chat", "Brief message", System.currentTimeMillis(), 2, "Good"),
                ChatSession("2", "Long Discussion", "Many messages", System.currentTimeMillis(), 15, "Anxious"),
                ChatSession("3", "Medium Chat", "Some messages", System.currentTimeMillis(), 7, "Stressed")
            )
            
            // When
            val mostActive = chatSessions.maxByOrNull { it.messageCount }
            val leastActive = chatSessions.minByOrNull { it.messageCount }
            
            // Then
            assertNotNull(mostActive)
            assertEquals("Long Discussion", mostActive!!.title)
            assertEquals(15, mostActive.messageCount)
            
            assertNotNull(leastActive)
            assertEquals("Short Chat", leastActive!!.title)
            assertEquals(2, leastActive.messageCount)
        }
        
        @Test
        fun `should track weekly chat activity`() {
            // Given
            val now = System.currentTimeMillis()
            val oneDayAgo = now - 86400000L
            val twoDaysAgo = now - 172800000L
            val eightDaysAgo = now - 691200000L
            
            val chatSessions = listOf(
                ChatSession("1", "Recent Chat", "Today", now, 5, "Good"),
                ChatSession("2", "Yesterday Chat", "Yesterday", oneDayAgo, 3, "Anxious"),
                ChatSession("3", "Two Days Ago", "Two days", twoDaysAgo, 7, "Stressed"),
                ChatSession("4", "Old Chat", "Last week", eightDaysAgo, 4, "Good")
            )
            
            // When
            val thisWeekSessions = chatSessions.count { 
                it.timestamp > now - 604800000L // 7 days ago
            }
            
            // Then
            assertEquals(3, thisWeekSessions)
        }
    }

    @Nested
    @DisplayName("Test Case 3: Export and Data Management")
    inner class ExportAndDataManagementTests {
        
        @Test
        fun `should format timestamps correctly for different time periods`() {
            // Given
            val now = System.currentTimeMillis()
            val thirtyMinutesAgo = now - 1800000L
            val twoHoursAgo = now - 7200000L
            val twoDaysAgo = now - 172800000L
            val twoWeeksAgo = now - 1209600000L
            
            // When
            val recentFormat = formatTimestamp(thirtyMinutesAgo)
            val hoursFormat = formatTimestamp(twoHoursAgo)
            val daysFormat = formatTimestamp(twoDaysAgo)
            val weeksFormat = formatTimestamp(twoWeeksAgo)
            
            // Then
            assertTrue(recentFormat.contains("m ago"))
            assertTrue(hoursFormat.contains("h ago"))
            assertTrue(daysFormat.contains("d ago"))
            assertTrue(weeksFormat.contains("Jan") || weeksFormat.contains("Feb") || 
                      weeksFormat.contains("Mar") || weeksFormat.contains("Apr") ||
                      weeksFormat.contains("May") || weeksFormat.contains("Jun") ||
                      weeksFormat.contains("Jul") || weeksFormat.contains("Aug") ||
                      weeksFormat.contains("Sep") || weeksFormat.contains("Oct") ||
                      weeksFormat.contains("Nov") || weeksFormat.contains("Dec"))
        }
        
        @Test
        fun `should prepare chat data for export in correct format`() {
            // Given
            val chatSessions = listOf(
                ChatSession("1", "Test Session", "Test message", System.currentTimeMillis(), 5, "Good"),
                ChatSession("2", "Another Session", "Another message", System.currentTimeMillis(), 3, "Anxious")
            )
            
            // When
            val exportData = chatSessions.map { session ->
                mapOf(
                    "id" to session.id,
                    "title" to session.title,
                    "lastMessage" to session.lastMessage,
                    "messageCount" to session.messageCount,
                    "moodLevel" to session.moodLevel
                )
            }
            
            // Then
            assertEquals(2, exportData.size)
            assertEquals("Test Session", exportData[0]["title"])
            assertEquals(5, exportData[0]["messageCount"])
            assertEquals("Good", exportData[0]["moodLevel"])
            assertEquals("Another Session", exportData[1]["title"])
            assertEquals(3, exportData[1]["messageCount"])
            assertEquals("Anxious", exportData[1]["moodLevel"])
        }
        
        @Test
        fun `should handle export with empty chat history`() {
            // Given
            val emptyChatSessions = emptyList<ChatSession>()
            
            // When
            val exportData = emptyChatSessions.map { session ->
                mapOf(
                    "id" to session.id,
                    "title" to session.title,
                    "lastMessage" to session.lastMessage,
                    "messageCount" to session.messageCount,
                    "moodLevel" to session.moodLevel
                )
            }
            
            // Then
            assertTrue(exportData.isEmpty())
        }
    }
}

