package com.serenityai.data.models

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import java.util.*

class SessionTest {

    @Nested
    @DisplayName("Session Creation")
    inner class SessionCreation {

        @Test
        @DisplayName("should create session with default values")
        fun shouldCreateSessionWithDefaultValues() {
            val session = Session()
            
            assertEquals("", session.sessionId)
            assertEquals("", session.userId)
            assertNotNull(session.startTimestamp)
            assertNull(session.endTimestamp)
            assertTrue(session.messages.isEmpty())
            assertEquals(0, session.moodScorePre)
            assertEquals(0, session.moodScorePost)
        }

        @Test
        @DisplayName("should create session with custom values")
        fun shouldCreateSessionWithCustomValues() {
            val sessionId = "session123"
            val userId = "user123"
            val startTimestamp = Date()
            val endTimestamp = Date()
            val messages = listOf(
                ChatMessage(text = "Hello", isUser = true),
                ChatMessage(text = "Hi there!", isUser = false)
            )
            val moodScorePre = 5
            val moodScorePost = 7
            
            val session = Session(
                sessionId = sessionId,
                userId = userId,
                startTimestamp = startTimestamp,
                endTimestamp = endTimestamp,
                messages = messages,
                moodScorePre = moodScorePre,
                moodScorePost = moodScorePost
            )
            
            assertEquals(sessionId, session.sessionId)
            assertEquals(userId, session.userId)
            assertEquals(startTimestamp, session.startTimestamp)
            assertEquals(endTimestamp, session.endTimestamp)
            assertEquals(messages, session.messages)
            assertEquals(moodScorePre, session.moodScorePre)
            assertEquals(moodScorePost, session.moodScorePost)
        }
    }

    @Nested
    @DisplayName("Session Duration")
    inner class SessionDuration {

        @Test
        @DisplayName("should calculate session duration when ended")
        fun shouldCalculateSessionDurationWhenEnded() {
            val startTime = Date()
            val endTime = Date(startTime.time + 30000) // 30 seconds later
            
            val session = Session(
                startTimestamp = startTime,
                endTimestamp = endTime
            )
            
            assertNotNull(session.endTimestamp)
            assertTrue(session.endTimestamp!!.time > session.startTimestamp.time)
        }

        @Test
        @DisplayName("should handle ongoing session")
        fun shouldHandleOngoingSession() {
            val session = Session(startTimestamp = Date())
            
            assertNull(session.endTimestamp)
        }
    }

    @Nested
    @DisplayName("Session Mood Scores")
    inner class SessionMoodScores {

        @Test
        @DisplayName("should track mood improvement")
        fun shouldTrackMoodImprovement() {
            val session = Session(
                moodScorePre = 3,
                moodScorePost = 8
            )
            
            val improvement = session.moodScorePost - session.moodScorePre
            assertEquals(5, improvement)
            assertTrue(improvement > 0)
        }

        @Test
        @DisplayName("should track mood decline")
        fun shouldTrackMoodDecline() {
            val session = Session(
                moodScorePre = 8,
                moodScorePost = 4
            )
            
            val decline = session.moodScorePre - session.moodScorePost
            assertEquals(4, decline)
            assertTrue(decline > 0)
        }

        @Test
        @DisplayName("should track stable mood")
        fun shouldTrackStableMood() {
            val session = Session(
                moodScorePre = 6,
                moodScorePost = 6
            )
            
            val change = session.moodScorePost - session.moodScorePre
            assertEquals(0, change)
        }
    }

    @Nested
    @DisplayName("Session Equality")
    inner class SessionEquality {

        @Test
        @DisplayName("should be equal when all properties match")
        fun shouldBeEqualWhenAllPropertiesMatch() {
            val startTime = Date()
            val endTime = Date()
            val messages = listOf(ChatMessage(text = "Test"))
            
            val session1 = Session(
                sessionId = "session123",
                userId = "user123",
                startTimestamp = startTime,
                endTimestamp = endTime,
                messages = messages,
                moodScorePre = 5,
                moodScorePost = 7
            )
            
            val session2 = Session(
                sessionId = "session123",
                userId = "user123",
                startTimestamp = startTime,
                endTimestamp = endTime,
                messages = messages,
                moodScorePre = 5,
                moodScorePost = 7
            )
            
            assertEquals(session1, session2)
            assertEquals(session1.hashCode(), session2.hashCode())
        }

        @Test
        @DisplayName("should not be equal when properties differ")
        fun shouldNotBeEqualWhenPropertiesDiffer() {
            val session1 = Session(sessionId = "session123", moodScorePre = 5)
            val session2 = Session(sessionId = "session123", moodScorePre = 6)
            
            assertNotEquals(session1, session2)
        }
    }

    @Nested
    @DisplayName("Session Copy")
    inner class SessionCopy {

        @Test
        @DisplayName("should create copy with modified properties")
        fun shouldCreateCopyWithModifiedProperties() {
            val originalSession = Session(
                sessionId = "session123",
                moodScorePre = 5,
                moodScorePost = 5
            )
            
            val modifiedSession = originalSession.copy(
                moodScorePost = 8,
                endTimestamp = Date()
            )
            
            assertEquals(originalSession.sessionId, modifiedSession.sessionId)
            assertEquals(originalSession.moodScorePre, modifiedSession.moodScorePre)
            assertEquals(8, modifiedSession.moodScorePost)
            assertNotNull(modifiedSession.endTimestamp)
        }
    }
}

class ChatMessageTest {

    @Nested
    @DisplayName("ChatMessage Creation")
    inner class ChatMessageCreation {

        @Test
        @DisplayName("should create chat message with default values")
        fun shouldCreateChatMessageWithDefaultValues() {
            val message = ChatMessage()
            
            assertEquals("", message.messageId)
            assertEquals("", message.text)
            assertTrue(message.isUser)
            assertNotNull(message.timestamp)
        }

        @Test
        @DisplayName("should create chat message with custom values")
        fun shouldCreateChatMessageWithCustomValues() {
            val messageId = "msg123"
            val text = "Hello, how are you?"
            val isUser = false
            val timestamp = Date()
            
            val message = ChatMessage(
                messageId = messageId,
                text = text,
                isUser = isUser,
                timestamp = timestamp
            )
            
            assertEquals(messageId, message.messageId)
            assertEquals(text, message.text)
            assertEquals(isUser, message.isUser)
            assertEquals(timestamp, message.timestamp)
        }
    }

    @Nested
    @DisplayName("ChatMessage Types")
    inner class ChatMessageTypes {

        @Test
        @DisplayName("should identify user message")
        fun shouldIdentifyUserMessage() {
            val userMessage = ChatMessage(text = "Hello", isUser = true)
            assertTrue(userMessage.isUser)
        }

        @Test
        @DisplayName("should identify AI message")
        fun shouldIdentifyAIMessage() {
            val aiMessage = ChatMessage(text = "Hi there!", isUser = false)
            assertFalse(aiMessage.isUser)
        }
    }

    @Nested
    @DisplayName("ChatMessage Content")
    inner class ChatMessageContent {

        @Test
        @DisplayName("should handle empty message")
        fun shouldHandleEmptyMessage() {
            val message = ChatMessage(text = "")
            assertTrue(message.text.isEmpty())
        }

        @Test
        @DisplayName("should handle long message")
        fun shouldHandleLongMessage() {
            val longText = "This is a very long message that contains multiple sentences. " +
                    "It should be handled properly by the ChatMessage data class. " +
                    "The message can contain any kind of text content."
            
            val message = ChatMessage(text = longText)
            assertEquals(longText, message.text)
        }

        @Test
        @DisplayName("should handle special characters")
        fun shouldHandleSpecialCharacters() {
            val specialText = "Hello! 😊 How are you? @#$%^&*()"
            val message = ChatMessage(text = specialText)
            assertEquals(specialText, message.text)
        }
    }

    @Nested
    @DisplayName("ChatMessage Equality")
    inner class ChatMessageEquality {

        @Test
        @DisplayName("should be equal when all properties match")
        fun shouldBeEqualWhenAllPropertiesMatch() {
            val timestamp = Date()
            val message1 = ChatMessage(
                messageId = "msg123",
                text = "Hello",
                isUser = true,
                timestamp = timestamp
            )
            
            val message2 = ChatMessage(
                messageId = "msg123",
                text = "Hello",
                isUser = true,
                timestamp = timestamp
            )
            
            assertEquals(message1, message2)
            assertEquals(message1.hashCode(), message2.hashCode())
        }

        @Test
        @DisplayName("should not be equal when properties differ")
        fun shouldNotBeEqualWhenPropertiesDiffer() {
            val message1 = ChatMessage(messageId = "msg123", text = "Hello")
            val message2 = ChatMessage(messageId = "msg123", text = "Hi")
            
            assertNotEquals(message1, message2)
        }
    }

    @Nested
    @DisplayName("ChatMessage Copy")
    inner class ChatMessageCopy {

        @Test
        @DisplayName("should create copy with modified properties")
        fun shouldCreateCopyWithModifiedProperties() {
            val originalMessage = ChatMessage(
                messageId = "msg123",
                text = "Original text",
                isUser = true
            )
            
            val modifiedMessage = originalMessage.copy(
                text = "Modified text",
                isUser = false
            )
            
            assertEquals(originalMessage.messageId, modifiedMessage.messageId)
            assertEquals("Modified text", modifiedMessage.text)
            assertFalse(modifiedMessage.isUser)
        }
    }
}
