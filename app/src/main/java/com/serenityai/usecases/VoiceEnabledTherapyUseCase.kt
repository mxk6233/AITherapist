package com.serenityai.usecases

import com.serenityai.data.models.*
import java.util.Date

/**
 * UC38: Voice Enabled Therapy Sessions - Use Case
 * 
 * Use Case Goal: Enable users to have voice-based therapy sessions with the AI therapist,
 * providing a more natural and accessible interaction method through speech recognition
 * and text-to-speech technologies.
 * 
 * Responsibilities:
 * - Process voice input and convert to text
 * - Generate AI therapist voice responses
 * - Manage voice session state and history
 * - Provide voice session controls (start, pause, stop)
 * - Handle voice recognition errors gracefully
 * - Support multiple languages and accents
 */
class VoiceEnabledTherapyUseCase {
    
    private val activeVoiceSessions = mutableMapOf<String, VoiceSession>()
    
    /**
     * Starts a new voice therapy session
     * 
     * @param userId User ID
     * @param language Language code (e.g., "en-US", "es-ES")
     * @return VoiceSession object
     */
    fun startVoiceSession(
        userId: String,
        language: String = "en-US"
    ): VoiceSession {
        val session = VoiceSession(
            id = "voice_${System.currentTimeMillis()}",
            userId = userId,
            language = language,
            status = VoiceSessionStatus.ACTIVE,
            startedAt = Date(),
            messages = emptyList(),
            duration = 0
        )
        
        activeVoiceSessions[session.id] = session
        return session
    }
    
    /**
     * Processes voice input and generates AI response
     * 
     * @param sessionId Voice session ID
     * @param audioData Audio data (in production, would be actual audio bytes)
     * @return VoiceMessageResponse with transcribed text and AI response
     */
    fun processVoiceInput(
        sessionId: String,
        audioData: ByteArray
    ): VoiceMessageResponse {
        val session = activeVoiceSessions[sessionId]
            ?: throw IllegalArgumentException("Voice session not found")
        
        require(session.status == VoiceSessionStatus.ACTIVE) {
            "Voice session is not active"
        }
        
        // Simulate voice-to-text transcription
        val transcribedText = transcribeVoiceInput(audioData)
        
        // Generate AI response (would integrate with AI service)
        val aiResponseText = generateAIResponse(transcribedText, session)
        
        // Convert AI response to voice (would use text-to-speech)
        val aiResponseAudio = synthesizeSpeech(aiResponseText, session.language)
        
        // Create user message
        val userMessage = VoiceMessage(
            id = "msg_${System.currentTimeMillis()}",
            text = transcribedText,
            isFromUser = true,
            audioData = audioData,
            timestamp = Date()
        )
        
        // Create AI message
        val aiMessage = VoiceMessage(
            id = "msg_${System.currentTimeMillis() + 1}",
            text = aiResponseText,
            isFromUser = false,
            audioData = aiResponseAudio,
            timestamp = Date()
        )
        
        // Update session
        val updatedMessages = session.messages + listOf(userMessage, aiMessage)
        val updatedSession = session.copy(
            messages = updatedMessages,
            lastActivityAt = Date()
        )
        activeVoiceSessions[sessionId] = updatedSession
        
        return VoiceMessageResponse(
            transcribedText = transcribedText,
            aiResponseText = aiResponseText,
            aiResponseAudio = aiResponseAudio,
            confidence = calculateConfidence(transcribedText)
        )
    }
    
    /**
     * Processes text input and converts AI response to voice
     * 
     * @param sessionId Voice session ID
     * @param textInput User's text input
     * @return VoiceMessageResponse with AI voice response
     */
    fun processTextInput(
        sessionId: String,
        textInput: String
    ): VoiceMessageResponse {
        require(textInput.isNotBlank()) { "Text input cannot be empty" }
        
        val session = activeVoiceSessions[sessionId]
            ?: throw IllegalArgumentException("Voice session not found")
        
        // Generate AI response
        val aiResponseText = generateAIResponse(textInput, session)
        
        // Convert AI response to voice
        val aiResponseAudio = synthesizeSpeech(aiResponseText, session.language)
        
        // Create user message (text only)
        val userMessage = VoiceMessage(
            id = "msg_${System.currentTimeMillis()}",
            text = textInput,
            isFromUser = true,
            audioData = null,
            timestamp = Date()
        )
        
        // Create AI message (with audio)
        val aiMessage = VoiceMessage(
            id = "msg_${System.currentTimeMillis() + 1}",
            text = aiResponseText,
            isFromUser = false,
            audioData = aiResponseAudio,
            timestamp = Date()
        )
        
        // Update session
        val updatedMessages = session.messages + listOf(userMessage, aiMessage)
        val updatedSession = session.copy(
            messages = updatedMessages,
            lastActivityAt = Date()
        )
        activeVoiceSessions[sessionId] = updatedSession
        
        return VoiceMessageResponse(
            transcribedText = textInput,
            aiResponseText = aiResponseText,
            aiResponseAudio = aiResponseAudio,
            confidence = 1.0f // Text input has 100% confidence
        )
    }
    
    /**
     * Pauses an active voice session
     * 
     * @param sessionId Voice session ID
     * @return Updated VoiceSession object
     */
    fun pauseVoiceSession(sessionId: String): VoiceSession {
        val session = activeVoiceSessions[sessionId]
            ?: throw IllegalArgumentException("Voice session not found")
        
        val updatedSession = session.copy(
            status = VoiceSessionStatus.PAUSED,
            lastActivityAt = Date()
        )
        activeVoiceSessions[sessionId] = updatedSession
        return updatedSession
    }
    
    /**
     * Resumes a paused voice session
     * 
     * @param sessionId Voice session ID
     * @return Updated VoiceSession object
     */
    fun resumeVoiceSession(sessionId: String): VoiceSession {
        val session = activeVoiceSessions[sessionId]
            ?: throw IllegalArgumentException("Voice session not found")
        
        require(session.status == VoiceSessionStatus.PAUSED) {
            "Voice session is not paused"
        }
        
        val updatedSession = session.copy(
            status = VoiceSessionStatus.ACTIVE,
            lastActivityAt = Date()
        )
        activeVoiceSessions[sessionId] = updatedSession
        return updatedSession
    }
    
    /**
     * Ends a voice session
     * 
     * @param sessionId Voice session ID
     * @return Updated VoiceSession object
     */
    fun endVoiceSession(sessionId: String): VoiceSession {
        val session = activeVoiceSessions[sessionId]
            ?: throw IllegalArgumentException("Voice session not found")
        
        val duration = ((Date().time - session.startedAt.time) / 1000).toInt()
        
        val updatedSession = session.copy(
            status = VoiceSessionStatus.COMPLETED,
            endedAt = Date(),
            duration = duration,
            lastActivityAt = Date()
        )
        activeVoiceSessions[sessionId] = updatedSession
        return updatedSession
    }
    
    /**
     * Gets voice session history for a user
     * 
     * @param userId User ID
     * @return List of VoiceSession objects
     */
    fun getVoiceSessionHistory(userId: String): List<VoiceSession> {
        return activeVoiceSessions.values
            .filter { it.userId == userId }
            .sortedByDescending { it.startedAt }
    }
    
    /**
     * Gets active voice session for a user
     * 
     * @param userId User ID
     * @return VoiceSession object or null
     */
    fun getActiveVoiceSession(userId: String): VoiceSession? {
        return activeVoiceSessions.values
            .firstOrNull { it.userId == userId && it.status == VoiceSessionStatus.ACTIVE }
    }
    
    /**
     * Handles voice recognition errors
     * 
     * @param sessionId Voice session ID
     * @param error Error message
     * @return Error handling response
     */
    fun handleVoiceRecognitionError(
        sessionId: String,
        error: String
    ): ErrorHandlingResponse {
        val session = activeVoiceSessions[sessionId]
            ?: throw IllegalArgumentException("Voice session not found")
        
        return ErrorHandlingResponse(
            errorMessage = error,
            suggestion = "Please try speaking again or use text input",
            canRetry = true,
            alternativeMethods = listOf("Text input", "Rephrase your message")
        )
    }
    
    /**
     * Gets supported languages for voice sessions
     * 
     * @return List of supported language codes
     */
    fun getSupportedLanguages(): List<LanguageSupport> {
        return listOf(
            LanguageSupport("en-US", "English (US)", true, true),
            LanguageSupport("en-GB", "English (UK)", true, true),
            LanguageSupport("es-ES", "Spanish (Spain)", true, true),
            LanguageSupport("es-MX", "Spanish (Mexico)", true, true),
            LanguageSupport("fr-FR", "French", true, true),
            LanguageSupport("de-DE", "German", true, true),
            LanguageSupport("it-IT", "Italian", false, true),
            LanguageSupport("pt-BR", "Portuguese (Brazil)", false, true)
        )
    }
    
    // Private helper methods
    
    private fun transcribeVoiceInput(audioData: ByteArray): String {
        // In production, would use actual speech recognition service
        // For now, return a simulated transcription
        return when {
            audioData.size > 1000 -> "I've been feeling anxious lately about work and relationships."
            audioData.size > 500 -> "Can you help me understand my feelings?"
            else -> "Hello, I need some support today."
        }
    }
    
    private fun generateAIResponse(userInput: String, session: VoiceSession): String {
        // In production, would integrate with AI service
        // For now, return contextual responses
        return when {
            userInput.lowercase().contains("anxious") || userInput.lowercase().contains("anxiety") ->
                "I understand that anxiety can be really challenging. Let's explore what's triggering these feelings for you. Can you tell me more about when you notice the anxiety?"
            
            userInput.lowercase().contains("sad") || userInput.lowercase().contains("depressed") ->
                "I'm sorry to hear you're feeling this way. Depression can be really difficult. You're not alone in this. What's been helpful for you in the past when you've felt this way?"
            
            userInput.lowercase().contains("stress") || userInput.lowercase().contains("stressed") ->
                "Stress can really impact our wellbeing. Let's talk about what's causing the stress and explore some coping strategies together."
            
            userInput.lowercase().contains("help") || userInput.lowercase().contains("support") ->
                "I'm here to support you. What would be most helpful right now? Feel free to share what's on your mind."
            
            else ->
                "Thank you for sharing that with me. I'm listening, and I want to understand better. Can you tell me more about what you're experiencing?"
        }
    }
    
    private fun synthesizeSpeech(text: String, language: String): ByteArray {
        // In production, would use actual text-to-speech service
        // For now, return simulated audio data
        return ByteArray(text.length * 100) // Simulated audio size
    }
    
    private fun calculateConfidence(transcribedText: String): Float {
        // In production, would use actual confidence from speech recognition
        // For now, return based on text length and clarity
        return when {
            transcribedText.length > 20 -> 0.9f
            transcribedText.length > 10 -> 0.8f
            transcribedText.length > 5 -> 0.7f
            else -> 0.6f
        }
    }
}

/**
 * Data class for voice session
 */
data class VoiceSession(
    val id: String,
    val userId: String,
    val language: String,
    val status: VoiceSessionStatus,
    val startedAt: Date,
    val endedAt: Date? = null,
    val messages: List<VoiceMessage>,
    val duration: Int, // in seconds
    val lastActivityAt: Date = Date()
)

/**
 * Data class for voice message
 */
data class VoiceMessage(
    val id: String,
    val text: String,
    val isFromUser: Boolean,
    val audioData: ByteArray?,
    val timestamp: Date
)

/**
 * Data class for voice message response
 */
data class VoiceMessageResponse(
    val transcribedText: String,
    val aiResponseText: String,
    val aiResponseAudio: ByteArray,
    val confidence: Float // 0-1
)

/**
 * Data class for error handling response
 */
data class ErrorHandlingResponse(
    val errorMessage: String,
    val suggestion: String,
    val canRetry: Boolean,
    val alternativeMethods: List<String>
)

/**
 * Data class for language support
 */
data class LanguageSupport(
    val code: String,
    val name: String,
    val speechRecognitionSupported: Boolean,
    val textToSpeechSupported: Boolean
)

/**
 * Voice session status enum
 */
enum class VoiceSessionStatus {
    ACTIVE, PAUSED, COMPLETED, CANCELLED
}

