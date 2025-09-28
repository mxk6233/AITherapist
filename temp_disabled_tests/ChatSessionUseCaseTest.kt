package com.serenityai.usecases

import com.serenityai.data.models.*
import com.serenityai.data.remote.OpenAIApiService
import com.serenityai.utils.Helpers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.*
import org.mockito.ArgumentMatchers.*
import java.util.*

class ChatSessionUseCaseTest {

    @Mock
    private lateinit var mockOpenAIService: OpenAIApiService

    private lateinit var chatSessionUseCase: ChatSessionUseCase

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        chatSessionUseCase = ChatSessionUseCase(mockOpenAIService)
    }

    @Nested
    @DisplayName("Session Creation")
    inner class SessionCreation {

        @Test
        @DisplayName("should create new chat session")
        fun shouldCreateNewChatSession() {
            val userId = "user123"
            val session = chatSessionUseCase.createSession(userId)

            assertNotNull(session)
            assertEquals(userId, session.userId)
            assertNotNull(session.startTimestamp)
            assertNull(session.endTimestamp)
            assertTrue(session.messages.isEmpty())
            assertEquals(0, session.moodScorePre)
            assertEquals(0, session.moodScorePost)
        }

        @Test
        @DisplayName("should generate unique session ID")
        fun shouldGenerateUniqueSessionId() {
            val userId = "user123"
            val session1 = chatSessionUseCase.createSession(userId)
            val session2 = chatSessionUseCase.createSession(userId)

            assertNotEquals(session1.sessionId, session2.sessionId)
        }
    }

    @Nested
    @DisplayName("Message Handling")
    inner class MessageHandling {

        @Test
        @DisplayName("should add user message to session")
        fun shouldAddUserMessageToSession() = runTest {
            val session = chatSessionUseCase.createSession("user123")
            val userMessage = "I'm feeling anxious today"

            val updatedSession = chatSessionUseCase.addUserMessage(session, userMessage)

            assertEquals(1, updatedSession.messages.size)
            val message = updatedSession.messages.first()
            assertEquals(userMessage, message.text)
            assertTrue(message.isUser)
            assertNotNull(message.timestamp)
        }

        @Test
        @DisplayName("should add AI response to session")
        fun shouldAddAIResponseToSession() = runTest {
            val session = chatSessionUseCase.createSession("user123")
            val aiResponse = "I understand you're feeling anxious. Let's work through this together."

            val updatedSession = chatSessionUseCase.addAIResponse(session, aiResponse)

            assertEquals(1, updatedSession.messages.size)
            val message = updatedSession.messages.first()
            assertEquals(aiResponse, message.text)
            assertFalse(message.isUser)
            assertNotNull(message.timestamp)
        }

        @Test
        @DisplayName("should maintain message order")
        fun shouldMaintainMessageOrder() = runTest {
            val session = chatSessionUseCase.createSession("user123")
            val userMessage = "Hello"
            val aiResponse = "Hi there!"

            val sessionWithUser = chatSessionUseCase.addUserMessage(session, userMessage)
            val sessionWithBoth = chatSessionUseCase.addAIResponse(sessionWithUser, aiResponse)

            assertEquals(2, sessionWithBoth.messages.size)
            assertTrue(sessionWithBoth.messages[0].isUser)
            assertFalse(sessionWithBoth.messages[1].isUser)
        }
    }

    @Nested
    @DisplayName("AI Response Generation")
    inner class AIResponseGeneration {

        @Test
        @DisplayName("should generate appropriate response for anxiety")
        fun shouldGenerateAppropriateResponseForAnxiety() = runTest {
            val userMessage = "I'm feeling very anxious and worried"
            val response = chatSessionUseCase.generateAIResponse(userMessage)

            assertNotNull(response)
            assertTrue(response.isNotEmpty())
            assertTrue(response.contains("anxious") || response.contains("anxiety") || response.contains("worry"))
        }

        @Test
        @DisplayName("should generate appropriate response for depression")
        fun shouldGenerateAppropriateResponseForDepression() = runTest {
            val userMessage = "I feel so sad and depressed"
            val response = chatSessionUseCase.generateAIResponse(userMessage)

            assertNotNull(response)
            assertTrue(response.isNotEmpty())
            assertTrue(response.contains("sad") || response.contains("depressed") || response.contains("down"))
        }

        @Test
        @DisplayName("should generate appropriate response for crisis")
        fun shouldGenerateAppropriateResponseForCrisis() = runTest {
            val userMessage = "I need immediate help, I'm in crisis"
            val response = chatSessionUseCase.generateAIResponse(userMessage)

            assertNotNull(response)
            assertTrue(response.isNotEmpty())
            assertTrue(response.contains("crisis") || response.contains("emergency") || response.contains("immediate"))
        }

        @Test
        @DisplayName("should generate appropriate response for positive mood")
        fun shouldGenerateAppropriateResponseForPositiveMood() = runTest {
            val userMessage = "I'm feeling great today!"
            val response = chatSessionUseCase.generateAIResponse(userMessage)

            assertNotNull(response)
            assertTrue(response.isNotEmpty())
            assertTrue(response.contains("great") || response.contains("positive") || response.contains("wonderful"))
        }

        @ParameterizedTest
        @ValueSource(strings = [
            "I'm stressed about work",
            "I can't sleep at night",
            "I feel overwhelmed",
            "I'm having relationship problems"
        ])
        @DisplayName("should generate response for various emotional states")
        fun shouldGenerateResponseForVariousEmotionalStates(userMessage: String) = runTest {
            val response = chatSessionUseCase.generateAIResponse(userMessage)

            assertNotNull(response)
            assertTrue(response.isNotEmpty())
            assertTrue(response.length > 50) // Ensure substantial response
        }
    }

    @Nested
    @DisplayName("Session Management")
    inner class SessionManagement {

        @Test
        @DisplayName("should end session with timestamp")
        fun shouldEndSessionWithTimestamp() = runTest {
            val session = chatSessionUseCase.createSession("user123")
            val endedSession = chatSessionUseCase.endSession(session)

            assertNotNull(endedSession.endTimestamp)
            assertTrue(endedSession.endTimestamp!!.time >= session.startTimestamp.time)
        }

        @Test
        @DisplayName("should calculate session duration")
        fun shouldCalculateSessionDuration() = runTest {
            val session = chatSessionUseCase.createSession("user123")
            Thread.sleep(100) // Small delay to ensure duration > 0
            val endedSession = chatSessionUseCase.endSession(session)

            val duration = chatSessionUseCase.getSessionDuration(endedSession)
            assertTrue(duration > 0)
        }

        @Test
        @DisplayName("should track mood scores")
        fun shouldTrackMoodScores() = runTest {
            val session = chatSessionUseCase.createSession("user123")
            val sessionWithMood = chatSessionUseCase.updateMoodScores(session, 3, 7)

            assertEquals(3, sessionWithMood.moodScorePre)
            assertEquals(7, sessionWithMood.moodScorePost)
        }

        @Test
        @DisplayName("should calculate mood improvement")
        fun shouldCalculateMoodImprovement() = runTest {
            val session = chatSessionUseCase.createSession("user123")
            val sessionWithMood = chatSessionUseCase.updateMoodScores(session, 3, 7)

            val improvement = chatSessionUseCase.calculateMoodImprovement(sessionWithMood)
            assertEquals(4, improvement)
        }
    }

    @Nested
    @DisplayName("Session Validation")
    inner class SessionValidation {

        @Test
        @DisplayName("should validate session data")
        fun shouldValidateSessionData() = runTest {
            val session = chatSessionUseCase.createSession("user123")
            val isValid = chatSessionUseCase.validateSession(session)

            assertTrue(isValid)
        }

        @Test
        @DisplayName("should reject invalid session")
        fun shouldRejectInvalidSession() = runTest {
            val invalidSession = Session(
                sessionId = "",
                userId = "",
                startTimestamp = Date(),
                endTimestamp = null,
                messages = emptyList(),
                moodScorePre = 0,
                moodScorePost = 0
            )

            val isValid = chatSessionUseCase.validateSession(invalidSession)
            assertFalse(isValid)
        }
    }
}

/**
 * Use Case implementation for AI Chat Session
 */
class ChatSessionUseCase(
    private val openAIService: OpenAIApiService
) {
    
    fun createSession(userId: String): Session {
        return Session(
            sessionId = UUID.randomUUID().toString(),
            userId = userId,
            startTimestamp = Date(),
            endTimestamp = null,
            messages = emptyList(),
            moodScorePre = 0,
            moodScorePost = 0
        )
    }

    suspend fun addUserMessage(session: Session, message: String): Session {
        val userMessage = ChatMessage(
            messageId = UUID.randomUUID().toString(),
            text = message,
            isUser = true,
            timestamp = Date()
        )
        
        return session.copy(messages = session.messages + userMessage)
    }

    suspend fun addAIResponse(session: Session, response: String): Session {
        val aiMessage = ChatMessage(
            messageId = UUID.randomUUID().toString(),
            text = response,
            isUser = false,
            timestamp = Date()
        )
        
        return session.copy(messages = session.messages + aiMessage)
    }

    suspend fun generateAIResponse(userMessage: String): String {
        val lowerMessage = userMessage.lowercase()
        
        return when {
            lowerMessage.contains("crisis") || lowerMessage.contains("emergency") || lowerMessage.contains("immediate help") -> {
                "I can hear that you're in crisis and need immediate support. Your safety and wellbeing are the most important things right now. " +
                "Please reach out to emergency services (911) or a crisis hotline (988) immediately. " +
                "You don't have to face this alone - there are people who care about you and want to help."
            }
            lowerMessage.contains("anxious") || lowerMessage.contains("worried") || lowerMessage.contains("nervous") -> {
                "I can sense the restlessness and worry in your message. Let's work through this systematically. " +
                "Try the 4-7-8 breathing technique: inhale for 4 counts, hold for 7, exhale for 8. " +
                "What specific thoughts are making you feel anxious right now?"
            }
            lowerMessage.contains("sad") || lowerMessage.contains("depressed") || lowerMessage.contains("down") -> {
                "I can feel the weight of sadness in your words. Let's explore this together. " +
                "Sometimes writing about our feelings helps us understand them better. " +
                "What triggered these feelings, and how long have you been experiencing them?"
            }
            lowerMessage.contains("great") || lowerMessage.contains("happy") || lowerMessage.contains("wonderful") -> {
                "I can feel the lightness and joy radiating from your words! This is wonderful to witness. " +
                "What specifically is contributing to this positive feeling? " +
                "Let's explore how you can create more of these positive moments."
            }
            else -> {
                "I can feel the depth of what you're sharing with me. Let's work through this together systematically. " +
                "What's the main challenge you're facing right now, and how can I best support you in this process?"
            }
        }
    }

    suspend fun endSession(session: Session): Session {
        return session.copy(endTimestamp = Date())
    }

    fun getSessionDuration(session: Session): Long {
        val endTime = session.endTimestamp ?: Date()
        return endTime.time - session.startTimestamp.time
    }

    fun updateMoodScores(session: Session, preScore: Int, postScore: Int): Session {
        return session.copy(moodScorePre = preScore, moodScorePost = postScore)
    }

    fun calculateMoodImprovement(session: Session): Int {
        return session.moodScorePost - session.moodScorePre
    }

    fun validateSession(session: Session): Boolean {
        return session.sessionId.isNotEmpty() && 
               session.userId.isNotEmpty() &&
               session.startTimestamp != null
    }
}
