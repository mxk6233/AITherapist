package com.serenityai.tests.unit.usecases.uc38_voice_therapy

import com.serenityai.usecases.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

/**
 * UC38: Voice Enabled Therapy Sessions
 * 
 * Use Case Goal: Enable users to have voice-based therapy sessions with the AI therapist,
 * providing a more natural and accessible interaction method through speech recognition
 * and text-to-speech technologies.
 * 
 * Key Requirements Being Tested:
 * 1. System processes voice input and converts to text
 * 2. System generates AI therapist voice responses
 * 3. System manages voice session state and history
 * 4. System provides voice session controls (start, pause, stop)
 * 5. System handles voice recognition errors gracefully
 */
@DisplayName("UC38: Voice Enabled Therapy Sessions - Unit Tests")
class VoiceEnabledTherapyUseCaseUnitTests {

    private lateinit var useCase: VoiceEnabledTherapyUseCase

    @BeforeEach
    fun setUp() {
        useCase = VoiceEnabledTherapyUseCase()
    }

    @Nested
    @DisplayName("Test Case 1: Voice Session Management - Validates Core UC38 Functionality")
    inner class SessionManagementTests {
        
        /**
         * Tests: System starts voice therapy sessions
         * Validates: UC38 requirement for session creation
         * Expected: Voice sessions are created with correct configuration
         */
        @Test
        @DisplayName("UC38-REQ-1: System MUST start voice therapy sessions")
        fun `system starts voice therapy sessions correctly`() {
            // Given: User wants to start voice therapy session
            // Purpose: Validate session creation functionality
            
            // When: User starts voice session
            val session = useCase.startVoiceSession(
                userId = "user123",
                language = "en-US"
            )
            
            // Then: Session should be created correctly
            assertNotNull(session.id, "UC38: Session must have unique ID")
            assertEquals("user123", session.userId, "UC38: Session must be linked to correct user")
            assertEquals("en-US", session.language, "UC38: Session language must be set correctly")
            assertEquals(VoiceSessionStatus.ACTIVE, session.status, "UC38: New sessions must be ACTIVE")
            assertNotNull(session.startedAt, "UC38: Session must have start timestamp")
            assertTrue(session.messages.isEmpty(), "UC38: New session should have no messages")
            assertEquals(0, session.duration, "UC38: New session should have zero duration")
        }
        
        /**
         * Tests: System processes voice input
         * Validates: UC38 requirement for voice-to-text processing
         * Expected: Voice input is transcribed and AI response is generated
         */
        @Test
        @DisplayName("UC38-REQ-2: System MUST process voice input and generate responses")
        fun `system processes voice input and generates AI responses correctly`() {
            // Given: Active voice session exists
            // Purpose: Validate voice processing functionality
            
            // When: User starts session and provides voice input
            val session = useCase.startVoiceSession("user123")
            val audioData = ByteArray(1000) // Simulated audio data
            val response = useCase.processVoiceInput(session.id, audioData)
            
            // Then: Voice should be processed correctly
            assertTrue(response.transcribedText.isNotBlank(), 
                "UC38: Voice input must be transcribed to text")
            assertTrue(response.aiResponseText.isNotBlank(), 
                "UC38: AI must generate response text")
            assertTrue(response.aiResponseAudio.isNotEmpty(), 
                "UC38: AI response must be converted to audio")
            assertTrue(response.confidence in 0f..1f, 
                "UC38: Transcription confidence must be between 0 and 1")
        }
        
        /**
         * Tests: System processes text input and converts to voice
         * Validates: UC38 requirement for text-to-speech
         * Expected: Text input generates voice response
         */
        @Test
        @DisplayName("UC38-REQ-3: System MUST process text input and convert AI response to voice")
        fun `system processes text input and converts response to voice correctly`() {
            // Given: Active voice session exists
            // Purpose: Validate text input with voice output functionality
            
            // When: User provides text input
            val session = useCase.startVoiceSession("user123")
            val response = useCase.processTextInput(session.id, "I'm feeling anxious today")
            
            // Then: Response should be generated and converted to voice
            assertEquals("I'm feeling anxious today", response.transcribedText, 
                "UC38: Text input must be preserved")
            assertTrue(response.aiResponseText.isNotBlank(), 
                "UC38: AI must generate response")
            assertTrue(response.aiResponseAudio.isNotEmpty(), 
                "UC38: Response must be converted to audio")
            assertEquals(1.0f, response.confidence, 
                "UC38: Text input should have 100% confidence")
        }
    }

    @Nested
    @DisplayName("Test Case 2: Session Controls - Validates Secondary UC38 Functionality")
    inner class SessionControlTests {
        
        /**
         * Tests: System pauses voice sessions
         * Validates: UC38 requirement for session pause functionality
         * Expected: Sessions can be paused and resumed
         */
        @Test
        @DisplayName("UC38-REQ-4: System MUST pause and resume voice sessions")
        fun `system pauses and resumes voice sessions correctly`() {
            // Given: Active voice session exists
            // Purpose: Validate pause/resume functionality
            
            // When: User starts session
            val session = useCase.startVoiceSession("user123")
            
            // When: User pauses session
            val pausedSession = useCase.pauseVoiceSession(session.id)
            
            // Then: Session should be paused
            assertEquals(VoiceSessionStatus.PAUSED, pausedSession.status, 
                "UC38: Session must be paused")
            
            // When: User resumes session
            val resumedSession = useCase.resumeVoiceSession(session.id)
            
            // Then: Session should be active again
            assertEquals(VoiceSessionStatus.ACTIVE, resumedSession.status, 
                "UC38: Session must be resumed to ACTIVE")
        }
        
        /**
         * Tests: System ends voice sessions
         * Validates: UC38 requirement for session termination
         * Expected: Sessions can be ended with duration tracking
         */
        @Test
        @DisplayName("UC38-REQ-5: System MUST end voice sessions and track duration")
        fun `system ends voice sessions and tracks duration correctly`() {
            // Given: Active voice session exists
            // Purpose: Validate session termination and duration tracking
            
            // When: User starts and ends session
            val session = useCase.startVoiceSession("user123")
            
            // Wait a moment (simulated)
            Thread.sleep(100)
            
            val endedSession = useCase.endVoiceSession(session.id)
            
            // Then: Session should be ended
            assertEquals(VoiceSessionStatus.COMPLETED, endedSession.status, 
                "UC38: Session must be COMPLETED")
            assertNotNull(endedSession.endedAt, "UC38: Session must have end timestamp")
            assertTrue(endedSession.duration >= 0, "UC38: Session duration must be tracked")
        }
        
        /**
         * Tests: System validates session operations
         * Validates: UC38 requirement for input validation
         * Expected: Invalid operations are rejected
         */
        @Test
        @DisplayName("UC38-REQ-6: System MUST validate session operations")
        fun `system validates session operations and rejects invalid requests`() {
            // Given: User attempts invalid operations
            // Purpose: Validate error handling
            
            // When: User tries to process input for non-existent session
            // Then: System should throw IllegalArgumentException
            assertThrows(IllegalArgumentException::class.java) {
                useCase.processVoiceInput("nonexistent_session", ByteArray(100))
            }
            
            // When: User tries to resume non-paused session
            val session = useCase.startVoiceSession("user123")
            assertThrows(IllegalArgumentException::class.java) {
                useCase.resumeVoiceSession(session.id) // Already active, not paused
            }
            
            // When: User tries to process text input with empty text
            assertThrows(IllegalArgumentException::class.java) {
                useCase.processTextInput(session.id, "")
            }
        }
    }

    @Nested
    @DisplayName("Test Case 3: History and Error Handling - Validates Advanced UC38 Functionality")
    inner class HistoryAndErrorTests {
        
        /**
         * Tests: System retrieves voice session history
         * Validates: UC38 requirement for history tracking
         * Expected: User's voice session history is retrievable
         */
        @Test
        @DisplayName("UC38-REQ-7: System MUST retrieve voice session history")
        fun `system retrieves voice session history correctly`() {
            // Given: User has multiple voice sessions
            // Purpose: Validate history retrieval functionality
            
            // When: User creates multiple sessions
            val session1 = useCase.startVoiceSession("user123")
            useCase.endVoiceSession(session1.id)
            
            val session2 = useCase.startVoiceSession("user123")
            useCase.endVoiceSession(session2.id)
            
            // When: User requests session history
            val history = useCase.getVoiceSessionHistory("user123")
            
            // Then: History should be returned
            assertTrue(history.isNotEmpty(), "UC38: System must return session history")
            assertTrue(history.any { it.id == session1.id }, 
                "UC38: History must include user's sessions")
            assertTrue(history.any { it.id == session2.id }, 
                "UC38: History must include all user's sessions")
        }
        
        /**
         * Tests: System gets active voice session
         * Validates: UC38 requirement for active session retrieval
         * Expected: Active session for user is returned
         */
        @Test
        @DisplayName("UC38-REQ-8: System MUST retrieve active voice session for user")
        fun `system retrieves active voice session correctly`() {
            // Given: User has active voice session
            // Purpose: Validate active session retrieval
            
            // When: User starts session
            val session = useCase.startVoiceSession("user123")
            
            // When: User requests active session
            val activeSession = useCase.getActiveVoiceSession("user123")
            
            // Then: Active session should be returned
            assertNotNull(activeSession, "UC38: System must return active session")
            assertEquals(session.id, activeSession?.id, "UC38: Returned session must match active session")
            assertEquals(VoiceSessionStatus.ACTIVE, activeSession?.status, 
                "UC38: Returned session must be ACTIVE")
        }
        
        /**
         * Tests: System handles voice recognition errors
         * Validates: UC38 requirement for graceful error handling
         * Expected: Errors are handled with helpful suggestions
         */
        @Test
        @DisplayName("UC38-REQ-9: System MUST handle voice recognition errors gracefully")
        fun `system handles voice recognition errors gracefully`() {
            // Given: Voice session exists and error occurs
            // Purpose: Validate error handling functionality
            
            // When: Error occurs during voice recognition
            val session = useCase.startVoiceSession("user123")
            val errorResponse = useCase.handleVoiceRecognitionError(
                sessionId = session.id,
                error = "Could not recognize speech"
            )
            
            // Then: Error should be handled gracefully
            assertTrue(errorResponse.errorMessage.isNotBlank(), 
                "UC38: Error message must be provided")
            assertTrue(errorResponse.suggestion.isNotBlank(), 
                "UC38: Helpful suggestion must be provided")
            assertTrue(errorResponse.canRetry, "UC38: System should allow retry")
            assertTrue(errorResponse.alternativeMethods.isNotEmpty(), 
                "UC38: Alternative methods must be suggested")
        }
        
        /**
         * Tests: System provides supported languages
         * Validates: UC38 requirement for language support
         * Expected: List of supported languages is returned
         */
        @Test
        @DisplayName("UC38-REQ-10: System MUST provide supported languages")
        fun `system provides supported languages correctly`() {
            // Given: System supports multiple languages
            // Purpose: Validate language support listing
            
            // When: User requests supported languages
            val languages = useCase.getSupportedLanguages()
            
            // Then: Supported languages should be returned
            assertTrue(languages.isNotEmpty(), "UC38: System must support multiple languages")
            assertTrue(languages.any { it.code == "en-US" }, 
                "UC38: English (US) must be supported")
            assertTrue(languages.all { it.name.isNotBlank() }, 
                "UC38: All languages must have names")
            assertTrue(languages.all { it.speechRecognitionSupported || it.textToSpeechSupported }, 
                "UC38: Languages must support at least one feature")
        }
    }
}

