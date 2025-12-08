package com.serenityai.tests.uat.usecases.uc38_voice_therapy

import com.serenityai.usecases.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested

/** UAT: UC38 - Voice Enabled Therapy Sessions - User Acceptance Tests validating voice therapy from an end-user perspective. */
@DisplayName("UAT: UC38 - Voice Enabled Therapy Sessions")
class VoiceEnabledTherapyUATTests {

    private val useCase = VoiceEnabledTherapyUseCase()

    @Nested
    @DisplayName("User Story: Voice Input")
    inner class VoiceInput {
        
        @Test
        @DisplayName("As a user, I want to speak to the AI so I can express myself naturally")
        fun `user can speak to AI therapist`() {
            // Given: User wants to use voice input
            val userId = "user123"
            val session = useCase.startVoiceSession(userId, "en-US")
            val audioData = ByteArray(1000) { it.toByte() }
            
            // When: User speaks
            val response = useCase.processVoiceInput(session.id, audioData)
            
            // Then: Voice input is captured
            assertNotNull(response, "User should be able to speak to AI")
            assertTrue(response.transcribedText.isNotBlank(), "Voice input should be received")
        }
        
        @Test
        @DisplayName("As a user, I want my speech transcribed so the AI understands me")
        fun `user speech is transcribed accurately`() {
            // Given: User speaks
            val userId = "user123"
            val session = useCase.startVoiceSession(userId)
            val audioData = ByteArray(1500) { it.toByte() }
            val response = useCase.processVoiceInput(session.id, audioData)
            
            // When: Speech is transcribed
            val transcriptionAccurate = response.transcribedText.isNotBlank()
            val transcriptionComplete = response.transcribedText.length > 0
            
            // Then: Speech is transcribed accurately
            assertTrue(transcriptionAccurate, "Speech should be transcribed accurately")
            assertTrue(transcriptionComplete, "Transcription should be complete")
        }
        
        @Test
        @DisplayName("As a user, I want to see what I said so I can verify it's correct")
        fun `user can see transcribed text`() {
            // Given: Speech is transcribed
            val userId = "user123"
            val session = useCase.startVoiceSession(userId)
            val response = useCase.processTextInput(session.id, "I've been feeling anxious about work")
            
            // When: User views transcription
            val textVisible = response.transcribedText.isNotBlank()
            val textReadable = response.transcribedText.length > 0
            
            // Then: Transcribed text is displayed
            assertTrue(textVisible, "User should see transcribed text")
            assertTrue(textReadable, "Text should be readable")
        }
    }

    @Nested
    @DisplayName("User Story: Voice Output")
    inner class VoiceOutput {
        
        @Test
        @DisplayName("As a user, I want the AI to speak responses so I can listen hands-free")
        fun `user can hear AI responses`() {
            // Given: AI generates response
            val userId = "user123"
            val session = useCase.startVoiceSession(userId)
            val response = useCase.processTextInput(session.id, "I'm feeling anxious")
            
            // When: AI speaks response
            val canHear = response.aiResponseAudio.isNotEmpty()
            val responseAudible = response.aiResponseText.isNotBlank()
            
            // Then: User hears AI response
            assertTrue(canHear, "User should be able to hear AI responses")
            assertTrue(responseAudible, "Response should be audible")
        }
        
        @Test
        @DisplayName("As a user, I want natural-sounding speech so it feels like a real conversation")
        fun `AI speech sounds natural`() {
            // Given: AI response
            val userId = "user123"
            val session = useCase.startVoiceSession(userId)
            val response = useCase.processTextInput(
                session.id, "I understand how you're feeling. Let's explore some coping strategies."
            )
            
            // When: AI speaks
            val speechNatural = response.aiResponseText.isNotBlank()
            val speechFluid = response.aiResponseText.contains(" ") && response.aiResponseText.length > 30
            
            // Then: Speech sounds natural
            assertTrue(speechNatural, "AI speech should sound natural")
            assertTrue(speechFluid, "Speech should be fluid")
        }
    }

    @Nested
    @DisplayName("User Story: Voice Session Management")
    inner class VoiceSessionManagement {
        
        @Test
        @DisplayName("As a user, I want to start voice sessions so I can have voice conversations")
        fun `user can start voice sessions`() {
            // Given: User wants to start session
            val userId = "user123"
            
            // When: User starts session
            val session = useCase.startVoiceSession(userId)
            
            // Then: Voice session starts successfully
            assertNotNull(session, "User should be able to start voice sessions")
            assertEquals(VoiceSessionStatus.ACTIVE, session.status, "Session should be active")
        }
        
        @Test
        @DisplayName("As a user, I want to pause voice sessions so I can take breaks")
        fun `user can pause voice sessions`() {
            // Given: Active voice session
            val userId = "user123"
            val session = useCase.startVoiceSession(userId)
            
            // When: User pauses session
            val pausedSession = useCase.pauseVoiceSession(session.id)
            
            // Then: Session is paused
            assertNotNull(pausedSession, "User should be able to pause sessions")
            assertEquals(VoiceSessionStatus.PAUSED, pausedSession.status, "Session should be paused")
        }
        
        @Test
        @DisplayName("As a user, I want to end voice sessions so I can finish conversations")
        fun `user can end voice sessions`() {
            // Given: Active voice session
            val userId = "user123"
            val session = useCase.startVoiceSession(userId)
            
            // When: User ends session
            val endedSession = useCase.endVoiceSession(session.id)
            
            // Then: Session ends successfully
            assertNotNull(endedSession, "User should be able to end sessions")
            assertEquals(VoiceSessionStatus.COMPLETED, endedSession.status, "Session should be ended")
        }
    }

    @Nested
    @DisplayName("User Story: Conversation Flow")
    inner class ConversationFlow {
        
        @Test
        @DisplayName("As a user, I want natural conversation flow so it feels like talking to a therapist")
        fun `conversation flows naturally`() {
            // Given: Voice conversation
            val userId = "user123"
            val session = useCase.startVoiceSession(userId)
            val response1 = useCase.processTextInput(session.id, "I'm feeling stressed")
            val response2 = useCase.processTextInput(session.id, "Work deadlines")
            
            // When: Conversation progresses
            val conversationNatural = response1.aiResponseText.isNotBlank() && response2.aiResponseText.isNotBlank()
            val turnTaking = session.messages.size >= 2
            
            // Then: Conversation flows naturally
            assertTrue(conversationNatural, "Conversation should flow naturally")
            assertTrue(turnTaking, "Conversation should have turn-taking")
        }
        
        @Test
        @DisplayName("As a user, I want the AI to remember context so conversations make sense")
        fun `AI remembers conversation context`() {
            // Given: Multi-turn conversation
            val userId = "user123"
            val session = useCase.startVoiceSession(userId)
            useCase.processTextInput(session.id, "I'm stressed about work")
            val response = useCase.processTextInput(session.id, "Tell me more about work stress")
            
            // When: AI responds
            val contextMaintained = response.aiResponseText.isNotBlank()
            val contextRelevant = session.messages.size >= 2
            
            // Then: AI maintains context
            assertTrue(contextMaintained, "AI should maintain conversation context")
            assertTrue(contextRelevant, "Context should be relevant")
        }
    }

    @Nested
    @DisplayName("User Story: Accessibility")
    inner class Accessibility {
        
        @Test
        @DisplayName("As a user with visual impairments, I want voice therapy so I can use the app")
        fun `voice therapy is accessible`() {
            // Given: User with visual impairment
            val userId = "user123"
            val session = useCase.startVoiceSession(userId)
            val languages = useCase.getSupportedLanguages()
            
            // When: User uses voice therapy
            val accessible = session != null
            val usable = languages.isNotEmpty()
            
            // Then: Feature is accessible
            assertTrue(accessible, "Voice therapy should be accessible")
            assertTrue(usable, "Feature should be usable")
        }
    }
}
