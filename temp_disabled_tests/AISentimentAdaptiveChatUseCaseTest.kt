package com.serenityai.usecases

import com.serenityai.data.models.*
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.util.*

class AISentimentAdaptiveChatUseCaseTest {

    private lateinit var aiSentimentAdaptiveChatUseCase: AISentimentAdaptiveChatUseCase

    @BeforeEach
    fun setUp() {
        aiSentimentAdaptiveChatUseCase = AISentimentAdaptiveChatUseCase()
    }

    @Nested
    @DisplayName("Sentiment Analysis")
    inner class SentimentAnalysis {

        @Test
        @DisplayName("should analyze positive sentiment")
        fun shouldAnalyzePositiveSentiment() = runTest {
            val message = "I'm feeling great today! Everything is going well."

            val sentiment = aiSentimentAdaptiveChatUseCase.analyzeSentiment(message)

            assertNotNull(sentiment)
            assertEquals(SentimentType.POSITIVE, sentiment.type)
            assertTrue(sentiment.confidence > 0.7)
        }

        @Test
        @DisplayName("should analyze negative sentiment")
        fun shouldAnalyzeNegativeSentiment() = runTest {
            val message = "I'm feeling terrible. Nothing is working out for me."

            val sentiment = aiSentimentAdaptiveChatUseCase.analyzeSentiment(message)

            assertNotNull(sentiment)
            assertEquals(SentimentType.NEGATIVE, sentiment.type)
            assertTrue(sentiment.confidence > 0.7)
        }

        @Test
        @DisplayName("should analyze neutral sentiment")
        fun shouldAnalyzeNeutralSentiment() = runTest {
            val message = "I went to the store today and bought some groceries."

            val sentiment = aiSentimentAdaptiveChatUseCase.analyzeSentiment(message)

            assertNotNull(sentiment)
            assertEquals(SentimentType.NEUTRAL, sentiment.type)
        }

        @Test
        @DisplayName("should detect emotional intensity")
        fun shouldDetectEmotionalIntensity() = runTest {
            val highIntensityMessage = "I am absolutely devastated and heartbroken!"
            val lowIntensityMessage = "I'm a bit sad today."

            val highIntensity = aiSentimentAdaptiveChatUseCase.detectEmotionalIntensity(highIntensityMessage)
            val lowIntensity = aiSentimentAdaptiveChatUseCase.detectEmotionalIntensity(lowIntensityMessage)

            assertTrue(highIntensity > lowIntensity)
            assertTrue(highIntensity >= 0.8)
            assertTrue(lowIntensity <= 0.4)
        }
    }

    @Nested
    @DisplayName("Adaptive Response Generation")
    inner class AdaptiveResponseGeneration {

        @Test
        @DisplayName("should generate empathetic response for negative sentiment")
        fun shouldGenerateEmpatheticResponseForNegativeSentiment() = runTest {
            val message = "I'm feeling really depressed and hopeless."
            val sentiment = SentimentAnalysisResult(SentimentType.NEGATIVE, 0.9, 0.8)

            val response = aiSentimentAdaptiveChatUseCase.generateAdaptiveResponse(message, sentiment)

            assertNotNull(response)
            assertTrue(response.contains("understand") || response.contains("hear"))
            assertTrue(response.contains("difficult") || response.contains("challenging"))
        }

        @Test
        @DisplayName("should generate encouraging response for positive sentiment")
        fun shouldGenerateEncouragingResponseForPositiveSentiment() = runTest {
            val message = "I'm feeling amazing today! I accomplished so much."
            val sentiment = SentimentAnalysisResult(SentimentType.POSITIVE, 0.9, 0.7)

            val response = aiSentimentAdaptiveChatUseCase.generateAdaptiveResponse(message, sentiment)

            assertNotNull(response)
            assertTrue(response.contains("wonderful") || response.contains("great"))
            assertTrue(response.contains("proud") || response.contains("celebrate"))
        }

        @Test
        @DisplayName("should generate supportive response for neutral sentiment")
        fun shouldGenerateSupportiveResponseForNeutralSentiment() = runTest {
            val message = "I had a regular day at work today."
            val sentiment = SentimentAnalysisResult(SentimentType.NEUTRAL, 0.6, 0.3)

            val response = aiSentimentAdaptiveChatUseCase.generateAdaptiveResponse(message, sentiment)

            assertNotNull(response)
            assertTrue(response.contains("routine") || response.contains("regular"))
        }

        @Test
        @DisplayName("should adapt response based on emotional intensity")
        fun shouldAdaptResponseBasedOnEmotionalIntensity() = runTest {
            val highIntensityMessage = "I am absolutely terrified and panicking!"
            val lowIntensityMessage = "I'm feeling a bit anxious."

            val highIntensitySentiment = SentimentAnalysisResult(SentimentType.NEGATIVE, 0.9, 0.9)
            val lowIntensitySentiment = SentimentAnalysisResult(SentimentType.NEGATIVE, 0.7, 0.3)

            val highIntensityResponse = aiSentimentAdaptiveChatUseCase.generateAdaptiveResponse(highIntensityMessage, highIntensitySentiment)
            val lowIntensityResponse = aiSentimentAdaptiveChatUseCase.generateAdaptiveResponse(lowIntensityMessage, lowIntensitySentiment)

            assertNotNull(highIntensityResponse)
            assertNotNull(lowIntensityResponse)
            assertTrue(highIntensityResponse.contains("calm") || highIntensityResponse.contains("breathe"))
            assertTrue(lowIntensityResponse.contains("gentle") || lowIntensityResponse.contains("support"))
        }
    }

    @Nested
    @DisplayName("Context Awareness")
    inner class ContextAwareness {

        @Test
        @DisplayName("should maintain conversation context")
        fun shouldMaintainConversationContext() = runTest {
            val userId = "user123"
            val conversationHistory = listOf(
                ChatMessage("user123", "I'm feeling anxious about my job interview", true, Date()),
                ChatMessage("ai", "I understand your anxiety about the interview. What specifically worries you?", false, Date()),
                ChatMessage("user123", "I'm worried I won't answer the questions well", true, Date())
            )

            val context = aiSentimentAdaptiveChatUseCase.buildConversationContext(userId, conversationHistory)

            assertNotNull(context)
            assertTrue(context.containsKey("recent_topics"))
            assertTrue(context.containsKey("emotional_state"))
            assertTrue(context.containsKey("concerns"))
        }

        @Test
        @DisplayName("should adapt based on conversation history")
        fun shouldAdaptBasedOnConversationHistory() = runTest {
            val userId = "user123"
            val conversationHistory = listOf(
                ChatMessage("user123", "I've been struggling with anxiety", true, Date()),
                ChatMessage("ai", "I'm here to help you with anxiety", false, Date()),
                ChatMessage("user123", "Today I tried the breathing exercise you suggested", true, Date())
            )

            val currentMessage = "It helped a little bit"
            val response = aiSentimentAdaptiveChatUseCase.generateContextualResponse(userId, currentMessage, conversationHistory)

            assertNotNull(response)
            assertTrue(response.contains("breathing") || response.contains("exercise"))
            assertTrue(response.contains("progress") || response.contains("improvement"))
        }

        @Test
        @DisplayName("should detect conversation patterns")
        fun shouldDetectConversationPatterns() = runTest {
            val userId = "user123"
            val conversationHistory = listOf(
                ChatMessage("user123", "I'm feeling sad", true, Date()),
                ChatMessage("ai", "I'm sorry to hear you're feeling sad", false, Date()),
                ChatMessage("user123", "I'm still feeling sad", true, Date()),
                ChatMessage("ai", "Let's work on this together", false, Date()),
                ChatMessage("user123", "I'm feeling sad again", true, Date())
            )

            val patterns = aiSentimentAdaptiveChatUseCase.detectConversationPatterns(userId, conversationHistory)

            assertNotNull(patterns)
            assertTrue(patterns.containsKey("recurring_themes"))
            assertTrue(patterns.containsKey("emotional_patterns"))
            assertTrue(patterns.containsKey("response_effectiveness"))
        }
    }

    @Nested
    @DisplayName("Emotional Intelligence")
    inner class EmotionalIntelligence {

        @Test
        @DisplayName("should detect emotional triggers")
        fun shouldDetectEmotionalTriggers() = runTest {
            val message = "Every time I think about my ex, I get really upset and can't stop crying."

            val triggers = aiSentimentAdaptiveChatUseCase.detectEmotionalTriggers(message)

            assertNotNull(triggers)
            assertTrue(triggers.isNotEmpty())
            assertTrue(triggers.any { it.contains("ex") })
            assertTrue(triggers.any { it.contains("upset") })
        }

        @Test
        @DisplayName("should identify emotional needs")
        fun shouldIdentifyEmotionalNeeds() = runTest {
            val message = "I feel so alone and isolated. No one understands what I'm going through."

            val needs = aiSentimentAdaptiveChatUseCase.identifyEmotionalNeeds(message)

            assertNotNull(needs)
            assertTrue(needs.isNotEmpty())
            assertTrue(needs.any { it.contains("connection") || it.contains("understanding") })
        }

        @Test
        @DisplayName("should provide emotional validation")
        fun shouldProvideEmotionalValidation() = runTest {
            val message = "I feel like I'm failing at everything and I'm worthless."

            val validation = aiSentimentAdaptiveChatUseCase.provideEmotionalValidation(message)

            assertNotNull(validation)
            assertTrue(validation.contains("valid") || validation.contains("understand"))
            assertTrue(validation.contains("worth") || validation.contains("value"))
        }

        @Test
        @DisplayName("should suggest emotional regulation techniques")
        fun shouldSuggestEmotionalRegulationTechniques() = runTest {
            val message = "I'm having a panic attack and I don't know what to do."

            val techniques = aiSentimentAdaptiveChatUseCase.suggestEmotionalRegulationTechniques(message)

            assertNotNull(techniques)
            assertTrue(techniques.isNotEmpty())
            assertTrue(techniques.any { it.contains("breathing") })
            assertTrue(techniques.any { it.contains("grounding") })
        }
    }

    @Nested
    @DisplayName("Personalization")
    inner class Personalization {

        @Test
        @DisplayName("should adapt to user's communication style")
        fun shouldAdaptToUsersCommunicationStyle() = runTest {
            val userId = "user123"
            val userProfile = UserProfile(
                userId = userId,
                firstName = "John",
                lastName = "Doe",
                dateOfBirth = Date(),
                gender = "Male",
                preferences = UserPreferences(),
                profilePictureUrl = null,
                bio = null,
                createdAt = Date(),
                updatedAt = Date()
            )

            val message = "I'm feeling down"
            val response = aiSentimentAdaptiveChatUseCase.personalizeResponse(userId, message, userProfile)

            assertNotNull(response)
            assertTrue(response.isNotEmpty())
        }

        @Test
        @DisplayName("should adapt to user's emotional patterns")
        fun shouldAdaptToUsersEmotionalPatterns() = runTest {
            val userId = "user123"
            val emotionalHistory = listOf(
                SentimentAnalysisResult(SentimentType.NEGATIVE, 0.8, 0.7),
                SentimentAnalysisResult(SentimentType.NEGATIVE, 0.7, 0.6),
                SentimentAnalysisResult(SentimentType.NEUTRAL, 0.5, 0.4)
            )

            val message = "I'm not feeling great"
            val response = aiSentimentAdaptiveChatUseCase.adaptToEmotionalPatterns(userId, message, emotionalHistory)

            assertNotNull(response)
            assertTrue(response.isNotEmpty())
        }

        @Test
        @DisplayName("should learn from user preferences")
        fun shouldLearnFromUserPreferences() = runTest {
            val userId = "user123"
            val userPreferences = UserPreferences(
                notificationsEnabled = true,
                reminderTime = "09:00",
                theme = "dark",
                language = "en"
            )

            val message = "I need some help"
            val response = aiSentimentAdaptiveChatUseCase.adaptToUserPreferences(userId, message, userPreferences)

            assertNotNull(response)
            assertTrue(response.isNotEmpty())
        }
    }

    @Nested
    @DisplayName("Crisis Detection")
    inner class CrisisDetection {

        @Test
        @DisplayName("should detect crisis indicators")
        fun shouldDetectCrisisIndicators() = runTest {
            val message = "I want to hurt myself and I don't see any point in living anymore."

            val crisisIndicators = aiSentimentAdaptiveChatUseCase.detectCrisisIndicators(message)

            assertNotNull(crisisIndicators)
            assertTrue(crisisIndicators.isNotEmpty())
            assertTrue(crisisIndicators.any { it.contains("self-harm") })
            assertTrue(crisisIndicators.any { it.contains("suicidal") })
        }

        @Test
        @DisplayName("should escalate crisis situations")
        fun shouldEscalateCrisisSituations() = runTest {
            val message = "I'm planning to end my life tonight."
            val crisisIndicators = listOf("suicidal ideation", "self-harm", "hopelessness")

            val escalation = aiSentimentAdaptiveChatUseCase.escalateCrisisSituation(message, crisisIndicators)

            assertNotNull(escalation)
            assertTrue(escalation.containsKey("urgency_level"))
            assertTrue(escalation.containsKey("intervention_needed"))
            assertTrue(escalation.containsKey("resources"))
        }

        @Test
        @DisplayName("should provide crisis response")
        fun shouldProvideCrisisResponse() = runTest {
            val message = "I can't take this anymore. I want to die."

            val crisisResponse = aiSentimentAdaptiveChatUseCase.generateCrisisResponse(message)

            assertNotNull(crisisResponse)
            assertTrue(crisisResponse.contains("crisis") || crisisResponse.contains("emergency"))
            assertTrue(crisisResponse.contains("help") || crisisResponse.contains("support"))
        }
    }

    @Nested
    @DisplayName("Response Quality")
    inner class ResponseQuality {

        @Test
        @DisplayName("should ensure response appropriateness")
        fun shouldEnsureResponseAppropriateness() = runTest {
            val message = "I'm feeling suicidal"
            val sentiment = SentimentAnalysisResult(SentimentType.NEGATIVE, 0.9, 0.9)

            val response = aiSentimentAdaptiveChatUseCase.generateAdaptiveResponse(message, sentiment)

            assertNotNull(response)
            assertFalse(response.contains("joke") || response.contains("funny"))
            assertTrue(response.contains("serious") || response.contains("concern"))
        }

        @Test
        @DisplayName("should maintain professional tone")
        fun shouldMaintainProfessionalTone() = runTest {
            val message = "I'm struggling with depression"
            val sentiment = SentimentAnalysisResult(SentimentType.NEGATIVE, 0.8, 0.6)

            val response = aiSentimentAdaptiveChatUseCase.generateAdaptiveResponse(message, sentiment)

            assertNotNull(response)
            assertFalse(response.contains("lol") || response.contains("haha"))
            assertTrue(response.contains("understand") || response.contains("support"))
        }

        @Test
        @DisplayName("should provide actionable advice")
        fun shouldProvideActionableAdvice() = runTest {
            val message = "I'm having trouble sleeping because of anxiety"

            val advice = aiSentimentAdaptiveChatUseCase.provideActionableAdvice(message)

            assertNotNull(advice)
            assertTrue(advice.isNotEmpty())
            assertTrue(advice.any { it.contains("technique") || it.contains("exercise") })
        }
    }

    // Helper methods
    private fun createMockUserProfile(userId: String): UserProfile {
        return UserProfile(
            userId = userId,
            firstName = "John",
            lastName = "Doe",
            dateOfBirth = Date(),
            gender = "Male",
            preferences = UserPreferences(),
            profilePictureUrl = null,
            bio = null,
            createdAt = Date(),
            updatedAt = Date()
        )
    }
}

/**
 * Sentiment types enum
 */
enum class SentimentType {
    POSITIVE, NEGATIVE, NEUTRAL, MIXED
}

/**
 * Sentiment analysis result data class
 */
data class SentimentAnalysisResult(
    val type: SentimentType,
    val confidence: Double,
    val intensity: Double
)

/**
 * Use Case implementation for AI Sentiment-Adaptive Chat
 */
class AISentimentAdaptiveChatUseCase {
    
    suspend fun analyzeSentiment(message: String): SentimentAnalysisResult {
        val lowerMessage = message.lowercase()
        
        val positiveWords = listOf("great", "amazing", "wonderful", "happy", "good", "excellent", "fantastic")
        val negativeWords = listOf("terrible", "awful", "horrible", "sad", "depressed", "anxious", "worried", "scared")
        
        val positiveCount = positiveWords.count { lowerMessage.contains(it) }
        val negativeCount = negativeWords.count { lowerMessage.contains(it) }
        
        val sentimentType = when {
            positiveCount > negativeCount -> SentimentType.POSITIVE
            negativeCount > positiveCount -> SentimentType.NEGATIVE
            else -> SentimentType.NEUTRAL
        }
        
        val confidence = when (sentimentType) {
            SentimentType.POSITIVE -> positiveCount.toDouble() / (positiveCount + negativeCount + 1)
            SentimentType.NEGATIVE -> negativeCount.toDouble() / (positiveCount + negativeCount + 1)
            else -> 0.5
        }
        
        val intensity = calculateEmotionalIntensity(message)
        
        return SentimentAnalysisResult(sentimentType, confidence, intensity)
    }

    suspend fun detectEmotionalIntensity(message: String): Double {
        val intensityWords = mapOf(
            "absolutely" to 0.9,
            "completely" to 0.8,
            "totally" to 0.8,
            "extremely" to 0.9,
            "very" to 0.7,
            "really" to 0.6,
            "quite" to 0.5,
            "somewhat" to 0.4,
            "a bit" to 0.3,
            "slightly" to 0.2
        )
        
        val lowerMessage = message.lowercase()
        val maxIntensity = intensityWords.entries
            .filter { lowerMessage.contains(it.key) }
            .maxOfOrNull { it.value } ?: 0.5
        
        return maxIntensity
    }

    suspend fun generateAdaptiveResponse(message: String, sentiment: SentimentAnalysisResult): String {
        return when (sentiment.type) {
            SentimentType.POSITIVE -> {
                when {
                    sentiment.intensity > 0.7 -> "That's absolutely wonderful to hear! Your positive energy is truly inspiring. What's contributing to this amazing feeling?"
                    sentiment.intensity > 0.4 -> "I'm so glad you're feeling good! It's wonderful to see you in such a positive mood. Tell me more about what's going well."
                    else -> "It's great to hear you're feeling positive! I'd love to know more about what's making you feel this way."
                }
            }
            SentimentType.NEGATIVE -> {
                when {
                    sentiment.intensity > 0.7 -> "I can hear how much pain you're in right now, and I want you to know that I'm here to support you. Your feelings are completely valid, and we can work through this together."
                    sentiment.intensity > 0.4 -> "I understand that you're going through a difficult time, and I want you to know that your feelings matter. Let's talk about what's troubling you."
                    else -> "I can sense that you're not feeling your best right now. I'm here to listen and help you work through whatever is on your mind."
                }
            }
            SentimentType.NEUTRAL -> {
                "Thank you for sharing that with me. I'm here to listen and help however I can. Is there anything specific you'd like to talk about?"
            }
            else -> {
                "I can sense there are mixed emotions in what you're sharing. That's completely normal. I'm here to help you sort through these feelings."
            }
        }
    }

    suspend fun buildConversationContext(userId: String, conversationHistory: List<ChatMessage>): Map<String, Any> {
        val context = mutableMapOf<String, Any>()
        
        val recentTopics = conversationHistory.takeLast(5).map { it.text }
        context["recent_topics"] = recentTopics
        
        val emotionalStates = conversationHistory.map { analyzeSentiment(it.text) }
        context["emotional_state"] = emotionalStates.lastOrNull()?.type?.name ?: "neutral"
        
        val concerns = conversationHistory.filter { 
            it.text.contains("worried") || it.text.contains("concerned") || it.text.contains("problem")
        }.map { it.text }
        context["concerns"] = concerns
        
        return context
    }

    suspend fun generateContextualResponse(userId: String, currentMessage: String, conversationHistory: List<ChatMessage>): String {
        val context = buildConversationContext(userId, conversationHistory)
        val sentiment = analyzeSentiment(currentMessage)
        
        val baseResponse = generateAdaptiveResponse(currentMessage, sentiment)
        
        // Add contextual elements
        val recentTopics = context["recent_topics"] as List<String>
        if (recentTopics.any { it.contains("breathing") || it.contains("exercise") }) {
            return baseResponse + " I'm glad you tried the breathing exercise I suggested. "
        }
        
        return baseResponse
    }

    suspend fun detectConversationPatterns(userId: String, conversationHistory: List<ChatMessage>): Map<String, Any> {
        val patterns = mutableMapOf<String, Any>()
        
        val sentiments = conversationHistory.map { analyzeSentiment(it.text) }
        val recurringThemes = conversationHistory.map { it.text }
            .flatMap { it.split(" ") }
            .filter { it.length > 3 }
            .groupBy { it.lowercase() }
            .filter { it.value.size >= 2 }
        
        patterns["recurring_themes"] = recurringThemes.keys.take(5)
        patterns["emotional_patterns"] = sentiments.map { it.type.name }
        patterns["response_effectiveness"] = "moderate" // Placeholder
        
        return patterns
    }

    suspend fun detectEmotionalTriggers(message: String): List<String> {
        val triggers = mutableListOf<String>()
        
        val triggerWords = mapOf(
            "ex" to "relationship issues",
            "work" to "work stress",
            "family" to "family problems",
            "money" to "financial stress",
            "health" to "health concerns"
        )
        
        val lowerMessage = message.lowercase()
        triggerWords.forEach { (word, trigger) ->
            if (lowerMessage.contains(word)) {
                triggers.add(trigger)
            }
        }
        
        return triggers
    }

    suspend fun identifyEmotionalNeeds(message: String): List<String> {
        val needs = mutableListOf<String>()
        
        val lowerMessage = message.lowercase()
        
        when {
            lowerMessage.contains("alone") || lowerMessage.contains("isolated") -> needs.add("connection")
            lowerMessage.contains("understand") || lowerMessage.contains("confused") -> needs.add("understanding")
            lowerMessage.contains("support") || lowerMessage.contains("help") -> needs.add("support")
            lowerMessage.contains("worthless") || lowerMessage.contains("failure") -> needs.add("validation")
            lowerMessage.contains("scared") || lowerMessage.contains("afraid") -> needs.add("safety")
        }
        
        return needs
    }

    suspend fun provideEmotionalValidation(message: String): String {
        return "I want you to know that your feelings are completely valid and understandable. What you're experiencing is real, and it's okay to feel this way. You're not alone in this."
    }

    suspend fun suggestEmotionalRegulationTechniques(message: String): List<String> {
        val techniques = mutableListOf<String>()
        
        val lowerMessage = message.lowercase()
        
        when {
            lowerMessage.contains("panic") || lowerMessage.contains("anxious") -> {
                techniques.add("Try the 4-7-8 breathing technique: inhale for 4, hold for 7, exhale for 8")
                techniques.add("Use the 5-4-3-2-1 grounding technique: name 5 things you can see, 4 you can touch, 3 you can hear, 2 you can smell, 1 you can taste")
            }
            lowerMessage.contains("angry") || lowerMessage.contains("frustrated") -> {
                techniques.add("Try progressive muscle relaxation")
                techniques.add("Take a short walk or do some physical activity")
            }
            lowerMessage.contains("sad") || lowerMessage.contains("depressed") -> {
                techniques.add("Practice self-compassion and gentle self-talk")
                techniques.add("Engage in activities that bring you joy")
            }
        }
        
        return techniques
    }

    suspend fun personalizeResponse(userId: String, message: String, userProfile: UserProfile): String {
        val baseResponse = generateAdaptiveResponse(message, analyzeSentiment(message))
        
        // Personalize based on user profile
        val personalizedResponse = when (userProfile.gender) {
            "Male" -> baseResponse.replace("I understand", "I understand, John")
            "Female" -> baseResponse.replace("I understand", "I understand")
            else -> baseResponse
        }
        
        return personalizedResponse
    }

    suspend fun adaptToEmotionalPatterns(userId: String, message: String, emotionalHistory: List<SentimentAnalysisResult>): String {
        val baseResponse = generateAdaptiveResponse(message, analyzeSentiment(message))
        
        // Adapt based on emotional patterns
        val recentSentiments = emotionalHistory.takeLast(3)
        val negativeCount = recentSentiments.count { it.type == SentimentType.NEGATIVE }
        
        return if (negativeCount >= 2) {
            baseResponse + " I notice you've been going through a difficult time recently. Let's work together to find some strategies that might help."
        } else {
            baseResponse
        }
    }

    suspend fun adaptToUserPreferences(userId: String, message: String, userPreferences: UserPreferences): String {
        val baseResponse = generateAdaptiveResponse(message, analyzeSentiment(message))
        
        // Adapt based on user preferences
        return when (userPreferences.theme) {
            "dark" -> baseResponse + " I'm here to provide support in whatever way works best for you."
            else -> baseResponse
        }
    }

    suspend fun detectCrisisIndicators(message: String): List<String> {
        val indicators = mutableListOf<String>()
        
        val crisisKeywords = mapOf(
            "hurt myself" to "self-harm",
            "kill myself" to "suicidal ideation",
            "end my life" to "suicidal ideation",
            "suicide" to "suicidal ideation",
            "hopeless" to "hopelessness",
            "worthless" to "low self-worth",
            "no point" to "hopelessness"
        )
        
        val lowerMessage = message.lowercase()
        crisisKeywords.forEach { (keyword, indicator) ->
            if (lowerMessage.contains(keyword)) {
                indicators.add(indicator)
            }
        }
        
        return indicators
    }

    suspend fun escalateCrisisSituation(message: String, crisisIndicators: List<String>): Map<String, Any> {
        val escalation = mutableMapOf<String, Any>()
        
        val urgencyLevel = when {
            crisisIndicators.contains("suicidal ideation") -> "critical"
            crisisIndicators.contains("self-harm") -> "high"
            crisisIndicators.contains("hopelessness") -> "medium"
            else -> "low"
        }
        
        escalation["urgency_level"] = urgencyLevel
        escalation["intervention_needed"] = urgencyLevel in listOf("critical", "high")
        escalation["resources"] = listOf("Crisis hotline: 988", "Emergency services: 911")
        
        return escalation
    }

    suspend fun generateCrisisResponse(message: String): String {
        return "I'm very concerned about what you're telling me. Your safety is the most important thing right now. " +
                "Please reach out to a crisis hotline (988) or emergency services (911) immediately. " +
                "You don't have to face this alone - there are people who care about you and want to help."
    }

    suspend fun provideActionableAdvice(message: String): List<String> {
        val advice = mutableListOf<String>()
        
        val lowerMessage = message.lowercase()
        
        when {
            lowerMessage.contains("sleep") && lowerMessage.contains("anxiety") -> {
                advice.add("Try the 4-7-8 breathing technique before bed")
                advice.add("Practice progressive muscle relaxation")
                advice.add("Create a calming bedtime routine")
            }
            lowerMessage.contains("stress") -> {
                advice.add("Take regular breaks throughout the day")
                advice.add("Practice mindfulness meditation")
                advice.add("Engage in physical activity")
            }
            lowerMessage.contains("sad") || lowerMessage.contains("depressed") -> {
                advice.add("Connect with supportive friends or family")
                advice.add("Engage in activities you used to enjoy")
                advice.add("Consider speaking with a mental health professional")
            }
        }
        
        return advice
    }

    private fun calculateEmotionalIntensity(message: String): Double {
        val intensityWords = mapOf(
            "absolutely" to 0.9,
            "completely" to 0.8,
            "totally" to 0.8,
            "extremely" to 0.9,
            "very" to 0.7,
            "really" to 0.6,
            "quite" to 0.5,
            "somewhat" to 0.4,
            "a bit" to 0.3,
            "slightly" to 0.2
        )
        
        val lowerMessage = message.lowercase()
        return intensityWords.entries
            .filter { lowerMessage.contains(it.key) }
            .maxOfOrNull { it.value } ?: 0.5
    }
}
