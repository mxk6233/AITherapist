package com.serenityai.tests.unit.usecases.uc29_sentiment_adaptive_chat

import com.serenityai.usecases.*
import com.serenityai.data.models.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

/**
 * UC29: AI Sentiment Adaptive Chat
 * 
 * Use Case Goal: Analyze user sentiment and adapt chat responses accordingly
 * to provide personalized and emotionally appropriate therapeutic interactions.
 * 
 * Key Requirements Being Tested:
 * 1. System analyzes sentiment from user messages
 * 2. System detects emotional intensity
 * 3. System generates adaptive responses based on sentiment
 * 4. System builds conversation context
 * 5. System detects emotional patterns and triggers
 * 6. System provides emotional validation and regulation techniques
 */
@DisplayName("UC29: AI Sentiment Adaptive Chat - Unit Tests")
class AISentimentAdaptiveChatUseCaseUnitTests {

    private lateinit var useCase: AISentimentAdaptiveChatUseCase

    @BeforeEach
    fun setUp() {
        useCase = AISentimentAdaptiveChatUseCase()
    }

    @Nested
    @DisplayName("Test Case 1: Sentiment Analysis - Validates Core UC29 Functionality")
    inner class SentimentAnalysisTests {
        
        @Test
        @DisplayName("UC29-REQ-1: System MUST analyze positive sentiment")
        fun `system analyzes positive sentiment correctly`() {
            // Given: User message with positive sentiment
            val message = "I'm feeling great today! Everything is going well."
            
            // When: System analyzes sentiment
            val sentiment = useCase.analyzeSentiment(message)
            
            // Then: Sentiment should be positive
            assertNotNull(sentiment, "UC29: Sentiment analysis must return result")
            assertTrue(sentiment.type == SentimentType.POSITIVE || sentiment.type == SentimentType.VERY_POSITIVE,
                "UC29: Positive message must be detected as positive sentiment")
            assertTrue(sentiment.confidence > 0.5f, "UC29: Confidence must be above threshold")
            assertTrue(sentiment.intensity in 0f..1f, "UC29: Intensity must be between 0 and 1")
        }
        
        @Test
        @DisplayName("UC29-REQ-2: System MUST analyze negative sentiment")
        fun `system analyzes negative sentiment correctly`() {
            // Given: User message with negative sentiment
            val message = "I'm feeling terrible. Nothing is working out for me."
            
            // When: System analyzes sentiment
            val sentiment = useCase.analyzeSentiment(message)
            
            // Then: Sentiment should be negative
            assertNotNull(sentiment, "UC29: Sentiment analysis must return result")
            assertTrue(sentiment.type == SentimentType.NEGATIVE || sentiment.type == SentimentType.VERY_NEGATIVE,
                "UC29: Negative message must be detected as negative sentiment")
            assertTrue(sentiment.confidence > 0.5f, "UC29: Confidence must be above threshold")
        }
        
        @Test
        @DisplayName("UC29-REQ-3: System MUST analyze neutral sentiment")
        fun `system analyzes neutral sentiment correctly`() {
            // Given: User message with neutral sentiment
            val message = "I went to the store today and bought some groceries."
            
            // When: System analyzes sentiment
            val sentiment = useCase.analyzeSentiment(message)
            
            // Then: Sentiment should be neutral
            assertNotNull(sentiment, "UC29: Sentiment analysis must return result")
            assertEquals(SentimentType.NEUTRAL, sentiment.type, "UC29: Neutral message must be detected as neutral")
        }
        
        @Test
        @DisplayName("UC29-REQ-4: System MUST detect very negative sentiment")
        fun `system detects very negative sentiment correctly`() {
            // Given: User message with very negative sentiment
            val message = "I feel hopeless and worthless. I can't go on like this."
            
            // When: System analyzes sentiment
            val sentiment = useCase.analyzeSentiment(message)
            
            // Then: Sentiment should be very negative
            assertNotNull(sentiment, "UC29: Sentiment analysis must return result")
            assertEquals(SentimentType.VERY_NEGATIVE, sentiment.type,
                "UC29: Very negative message must be detected as VERY_NEGATIVE")
            assertTrue(sentiment.confidence > 0.7f, "UC29: Very negative sentiment should have high confidence")
        }
    }

    @Nested
    @DisplayName("Test Case 2: Emotional Intensity Detection")
    inner class EmotionalIntensityTests {
        
        @Test
        @DisplayName("UC29-REQ-5: System MUST detect high emotional intensity")
        fun `system detects high emotional intensity correctly`() {
            // Given: Message with high emotional intensity
            val message = "I'm SO ANGRY!!! This is TERRIBLE!!!"
            
            // When: System detects intensity
            val intensity = useCase.detectEmotionalIntensity(message)
            
            // Then: Intensity should be high
            assertTrue(intensity > 0.7f, "UC29: High intensity message must have intensity > 0.7")
            assertTrue(intensity <= 1.0f, "UC29: Intensity must not exceed 1.0")
        }
        
        @Test
        @DisplayName("UC29-REQ-6: System MUST detect low emotional intensity")
        fun `system detects low emotional intensity correctly`() {
            // Given: Message with low emotional intensity
            val message = "I went to the store today."
            
            // When: System detects intensity
            val intensity = useCase.detectEmotionalIntensity(message)
            
            // Then: Intensity should be low to moderate
            assertTrue(intensity <= 0.7f, "UC29: Low intensity message should have intensity <= 0.7")
        }
    }

    @Nested
    @DisplayName("Test Case 3: Adaptive Response Generation")
    inner class AdaptiveResponseTests {
        
        @Test
        @DisplayName("UC29-REQ-7: System MUST generate adaptive response for positive sentiment")
        fun `system generates adaptive response for positive sentiment`() {
            // Given: Positive sentiment
            val message = "I'm feeling great today!"
            val sentiment = useCase.analyzeSentiment(message)
            
            // When: System generates adaptive response
            val response = useCase.generateAdaptiveResponse(message, sentiment)
            
            // Then: Response should be appropriate
            assertNotNull(response, "UC29: Response must be generated")
            assertTrue(response.message.isNotBlank(), "UC29: Response message must not be empty")
            assertNotNull(response.moodAnalysis, "UC29: Response must include mood analysis")
            assertEquals(sentiment.type, response.moodAnalysis?.sentiment,
                "UC29: Response sentiment must match analyzed sentiment")
        }
        
        @Test
        @DisplayName("UC29-REQ-8: System MUST generate adaptive response for negative sentiment")
        fun `system generates adaptive response for negative sentiment`() {
            // Given: Negative sentiment
            val message = "I'm feeling terrible today."
            val sentiment = useCase.analyzeSentiment(message)
            
            // When: System generates adaptive response
            val response = useCase.generateAdaptiveResponse(message, sentiment)
            
            // Then: Response should be empathetic
            assertNotNull(response, "UC29: Response must be generated")
            assertTrue(response.message.isNotBlank(), "UC29: Response message must not be empty")
            assertEquals(ResponseType.THERAPEUTIC, response.type,
                "UC29: Negative sentiment should generate therapeutic response")
        }
        
        @Test
        @DisplayName("UC29-REQ-9: System MUST generate crisis response for very negative sentiment")
        fun `system generates crisis response for very negative sentiment`() {
            // Given: Very negative sentiment
            val message = "I feel hopeless and worthless."
            val sentiment = useCase.analyzeSentiment(message)
            
            // When: System generates adaptive response
            val response = useCase.generateAdaptiveResponse(message, sentiment)
            
            // Then: Response should be crisis-focused
            assertNotNull(response, "UC29: Response must be generated")
            assertEquals(ResponseType.CRISIS_SUPPORT, response.type,
                "UC29: Very negative sentiment should generate crisis support response")
        }
    }

    @Nested
    @DisplayName("Test Case 4: Conversation Context Building")
    inner class ConversationContextTests {
        
        @Test
        @DisplayName("UC29-REQ-10: System MUST build conversation context")
        fun `system builds conversation context correctly`() {
            // Given: User ID and conversation history
            val userId = "user123"
            val history = listOf(
                ChatMessage("msg1", "I'm feeling good", true, java.util.Date()),
                ChatMessage("msg2", "I'm feeling better", true, java.util.Date())
            )
            
            // When: System builds context
            val context = useCase.buildConversationContext(userId, history)
            
            // Then: Context should be built correctly
            assertNotNull(context, "UC29: Context must be built")
            assertEquals(userId, context.userId, "UC29: Context must include correct user ID")
            assertEquals(history.size, context.recentMessages.size,
                "UC29: Context must include all recent messages")
            assertNotNull(context.sentimentTrend, "UC29: Context must include sentiment trend")
        }
        
        @Test
        @DisplayName("UC29-REQ-11: System MUST generate contextual response")
        fun `system generates contextual response correctly`() {
            // Given: User ID, current message, and history
            val userId = "user123"
            val currentMessage = "I'm still feeling down"
            val history = listOf(
                ChatMessage("msg1", "I'm feeling terrible", true, java.util.Date()),
                ChatMessage("msg2", "I'm feeling worse", true, java.util.Date())
            )
            
            // When: System generates contextual response
            val response = useCase.generateContextualResponse(userId, currentMessage, history)
            
            // Then: Response should be contextual
            assertNotNull(response, "UC29: Contextual response must be generated")
            assertTrue(response.message.isNotBlank(), "UC29: Response message must not be empty")
            assertNotNull(response.moodAnalysis, "UC29: Response must include mood analysis")
        }
    }

    @Nested
    @DisplayName("Test Case 5: Emotional Pattern Detection")
    inner class PatternDetectionTests {
        
        @Test
        @DisplayName("UC29-REQ-12: System MUST detect conversation patterns")
        fun `system detects conversation patterns correctly`() {
            // Given: User ID and conversation history
            val userId = "user123"
            val history = listOf(
                ChatMessage("msg1", "I'm feeling terrible", true, java.util.Date()),
                ChatMessage("msg2", "I'm feeling worse", true, java.util.Date()),
                ChatMessage("msg3", "I'm feeling awful", true, java.util.Date())
            )
            
            // When: System detects patterns
            val patterns = useCase.detectConversationPatterns(userId, history)
            
            // Then: Patterns should be detected
            assertNotNull(patterns, "UC29: Patterns must be detected")
            assertTrue(patterns.isNotEmpty(), "UC29: At least one pattern should be detected for negative trend")
        }
        
        @Test
        @DisplayName("UC29-REQ-13: System MUST detect emotional triggers")
        fun `system detects emotional triggers correctly`() {
            // Given: Message with emotional triggers
            val message = "I'm stressed about work and my relationship is falling apart"
            
            // When: System detects triggers
            val triggers = useCase.detectEmotionalTriggers(message)
            
            // Then: Triggers should be detected
            assertNotNull(triggers, "UC29: Triggers must be detected")
            assertTrue(triggers.isNotEmpty(), "UC29: Triggers should be detected in message")
        }
        
        @Test
        @DisplayName("UC29-REQ-14: System MUST identify emotional needs")
        fun `system identifies emotional needs correctly`() {
            // Given: Message with emotional content
            val message = "I'm feeling terrible and don't know what to do"
            
            // When: System identifies needs
            val needs = useCase.identifyEmotionalNeeds(message)
            
            // Then: Needs should be identified
            assertNotNull(needs, "UC29: Emotional needs must be identified")
            assertTrue(needs.isNotEmpty(), "UC29: At least one emotional need should be identified")
        }
    }

    @Nested
    @DisplayName("Test Case 6: Emotional Validation and Regulation")
    inner class ValidationAndRegulationTests {
        
        @Test
        @DisplayName("UC29-REQ-15: System MUST provide emotional validation")
        fun `system provides emotional validation correctly`() {
            // Given: Message with negative sentiment
            val message = "I'm feeling terrible"
            
            // When: System provides validation
            val validation = useCase.provideEmotionalValidation(message)
            
            // Then: Validation should be provided
            assertNotNull(validation, "UC29: Validation must be provided")
            assertTrue(validation.isNotBlank(), "UC29: Validation message must not be empty")
        }
        
        @Test
        @DisplayName("UC29-REQ-16: System MUST suggest emotional regulation techniques")
        fun `system suggests emotional regulation techniques correctly`() {
            // Given: Message with high emotional intensity
            val message = "I'm SO ANGRY and STRESSED!!!"
            
            // When: System suggests techniques
            val techniques = useCase.suggestEmotionalRegulationTechniques(message)
            
            // Then: Techniques should be suggested
            assertNotNull(techniques, "UC29: Techniques must be suggested")
            assertTrue(techniques.isNotEmpty(), "UC29: At least one technique should be suggested")
        }
    }

    @Nested
    @DisplayName("Test Case 7: Crisis Detection")
    inner class CrisisDetectionTests {
        
        @Test
        @DisplayName("UC29-REQ-17: System MUST detect crisis indicators")
        fun `system detects crisis indicators correctly`() {
            // Given: Message with crisis indicators
            val message = "I feel hopeless and worthless. I can't go on."
            
            // When: System detects indicators
            val indicators = useCase.detectCrisisIndicators(message)
            
            // Then: Indicators should be detected
            assertNotNull(indicators, "UC29: Crisis indicators must be detected")
            assertTrue(indicators.isNotEmpty(), "UC29: Crisis indicators should be detected")
        }
        
        @Test
        @DisplayName("UC29-REQ-18: System MUST escalate crisis situations")
        fun `system escalates crisis situations correctly`() {
            // Given: Message with crisis indicators
            val message = "I want to end it all"
            val indicators = useCase.detectCrisisIndicators(message)
            
            // When: System escalates
            val escalation = useCase.escalateCrisisSituation(message, indicators)
            
            // Then: Escalation should occur
            assertNotNull(escalation, "UC29: Escalation must occur")
            assertTrue(escalation.severity.ordinal >= CrisisSeverity.HIGH.ordinal,
                "UC29: Crisis situation should be escalated to HIGH or CRITICAL")
            assertTrue(escalation.immediateActions.isNotEmpty(),
                "UC29: Immediate actions must be provided")
            assertTrue(escalation.resources.isNotEmpty(), "UC29: Resources must be provided")
        }
        
        @Test
        @DisplayName("UC29-REQ-19: System MUST generate crisis response")
        fun `system generates crisis response correctly`() {
            // Given: Message with crisis indicators
            val message = "I feel hopeless and want to end it all"
            
            // When: System generates crisis response
            val response = useCase.generateCrisisResponse(message)
            
            // Then: Crisis response should be generated
            assertNotNull(response, "UC29: Crisis response must be generated")
            assertTrue(response.isNotBlank(), "UC29: Crisis response must not be empty")
            assertTrue(response.contains("crisis") || response.contains("help") || response.contains("support"),
                "UC29: Crisis response should mention support or help")
        }
    }

    @Nested
    @DisplayName("Test Case 8: Personalization")
    inner class PersonalizationTests {
        
        @Test
        @DisplayName("UC29-REQ-20: System MUST personalize responses based on user profile")
        fun `system personalizes responses based on user profile`() {
            // Given: User ID, message, and user profile
            val userId = "user123"
            val message = "I'm feeling good"
            val userProfile = UserProfile(
                userId = userId,
                preferences = UserPreferences(language = "es")
            )
            
            // When: System personalizes response
            val response = useCase.personalizeResponse(userId, message, userProfile)
            
            // Then: Response should be personalized
            assertNotNull(response, "UC29: Personalized response must be generated")
            assertTrue(response.message.isNotBlank(), "UC29: Response message must not be empty")
        }
        
        @Test
        @DisplayName("UC29-REQ-21: System MUST adapt to emotional patterns")
        fun `system adapts to emotional patterns correctly`() {
            // Given: User ID, message, and emotional history
            val userId = "user123"
            val message = "I'm still feeling down"
            val emotionalHistory = listOf(
                SentimentAnalysis(SentimentType.NEGATIVE, 0.8f, emptyList(), 0.7f, java.util.Date()),
                SentimentAnalysis(SentimentType.NEGATIVE, 0.75f, emptyList(), 0.75f, java.util.Date())
            )
            
            // When: System adapts to patterns
            val response = useCase.adaptToEmotionalPatterns(userId, message, emotionalHistory)
            
            // Then: Response should be adapted
            assertNotNull(response, "UC29: Adapted response must be generated")
            assertTrue(response.message.isNotBlank(), "UC29: Response message must not be empty")
        }
        
        @Test
        @DisplayName("UC29-REQ-22: System MUST adapt to user preferences")
        fun `system adapts to user preferences correctly`() {
            // Given: User ID, message, and preferences
            val userId = "user123"
            val message = "I'm feeling okay"
            val preferences = mapOf("responseStyle" to "direct")
            
            // When: System adapts to preferences
            val response = useCase.adaptToUserPreferences(userId, message, preferences)
            
            // Then: Response should be adapted
            assertNotNull(response, "UC29: Adapted response must be generated")
            assertTrue(response.message.isNotBlank(), "UC29: Response message must not be empty")
        }
    }

    @Nested
    @DisplayName("Test Case 9: Actionable Advice")
    inner class ActionableAdviceTests {
        
        @Test
        @DisplayName("UC29-REQ-23: System MUST provide actionable advice")
        fun `system provides actionable advice correctly`() {
            // Given: Message requiring advice
            val message = "I'm stressed about work and don't know how to handle it"
            
            // When: System provides advice
            val advice = useCase.provideActionableAdvice(message)
            
            // Then: Advice should be provided
            assertNotNull(advice, "UC29: Advice must be provided")
            assertTrue(advice.isNotEmpty(), "UC29: At least one piece of advice should be provided")
        }
    }
}

