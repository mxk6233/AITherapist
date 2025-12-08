package com.serenityai.tests.integration.usecases.uc29_sentiment_adaptive_chat

import com.serenityai.usecases.*
import com.serenityai.data.models.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

/**
 * UC29: AI Sentiment Adaptive Chat - Integration Tests
 * 
 * Integration tests verify that Sentiment Adaptive Chat integrates correctly
 * with sentiment analysis service, AI response generation, conversation history,
 * and user profile services.
 */
@DisplayName("UC29: AI Sentiment Adaptive Chat - Integration Tests")
class AISentimentAdaptiveChatUseCaseIntegrationTests {

    @Nested
    @DisplayName("Integration Test 1: Sentiment Analysis Integration")
    inner class SentimentAnalysisIntegrationTests {
        
        @Test
        @DisplayName("Should integrate sentiment analysis with response generation")
        fun `sentiment analysis integrates with response generation`() {
            // Given: Use case and message
            val useCase = AISentimentAdaptiveChatUseCase()
            val message = "I'm feeling great today!"
            
            // When: System integrates sentiment analysis with response generation
            val sentiment = useCase.analyzeSentiment(message)
            val response = useCase.generateAdaptiveResponse(message, sentiment)
            
            // Then: Integration works correctly
            assertNotNull(sentiment, "UC29 Integration: Sentiment must be analyzed")
            assertNotNull(response, "UC29 Integration: Response must be generated")
            assertTrue(response.message.isNotBlank(), "UC29 Integration: Response message must not be empty")
            assertEquals(sentiment.type, response.moodAnalysis?.sentiment,
                "UC29 Integration: Response sentiment must match analyzed sentiment")
        }
        
        @Test
        @DisplayName("Should integrate emotional intensity with response adaptation")
        fun `emotional intensity integrates with response adaptation`() {
            // Given: Use case and high-intensity message
            val useCase = AISentimentAdaptiveChatUseCase()
            val message = "I'm SO ANGRY!!!"
            
            // When: System integrates intensity detection
            val intensity = useCase.detectEmotionalIntensity(message)
            val sentiment = useCase.analyzeSentiment(message)
            val response = useCase.generateAdaptiveResponse(message, sentiment)
            
            // Then: Integration works correctly
            assertTrue(intensity > 0.5f, "UC29 Integration: Intensity must be detected")
            assertNotNull(response, "UC29 Integration: Response must be generated")
            assertTrue(response.message.isNotBlank(), "UC29 Integration: Response must not be empty")
        }
    }

    @Nested
    @DisplayName("Integration Test 2: Conversation Context Integration")
    inner class ConversationContextIntegrationTests {
        
        @Test
        @DisplayName("Should integrate conversation history with context building")
        fun `conversation history integrates with context building`() {
            // Given: Use case, user ID, and conversation history
            val useCase = AISentimentAdaptiveChatUseCase()
            val userId = "user123"
            val history = listOf(
                ChatMessage("msg1", "I'm feeling good", true, java.util.Date()),
                ChatMessage("msg2", "I'm feeling better", true, java.util.Date())
            )
            
            // When: System integrates conversation history
            val context = useCase.buildConversationContext(userId, history)
            val currentMessage = "I'm still feeling positive"
            val response = useCase.generateContextualResponse(userId, currentMessage, history)
            
            // Then: Integration works correctly
            assertNotNull(context, "UC29 Integration: Context must be built")
            assertEquals(userId, context.userId, "UC29 Integration: Context must include user ID")
            assertNotNull(response, "UC29 Integration: Contextual response must be generated")
            assertTrue(response.message.isNotBlank(), "UC29 Integration: Response must not be empty")
        }
        
        @Test
        @DisplayName("Should integrate sentiment trends with response adaptation")
        fun `sentiment trends integrate with response adaptation`() {
            // Given: Use case and declining sentiment trend
            val useCase = AISentimentAdaptiveChatUseCase()
            val userId = "user123"
            val history = listOf(
                ChatMessage("msg1", "I'm feeling okay", true, java.util.Date()),
                ChatMessage("msg2", "I'm feeling worse", true, java.util.Date()),
                ChatMessage("msg3", "I'm feeling terrible", true, java.util.Date())
            )
            
            // When: System integrates sentiment trends
            val context = useCase.buildConversationContext(userId, history)
            val response = useCase.generateContextualResponse(userId, "I'm still feeling down", history)
            
            // Then: Integration works correctly
            assertNotNull(context, "UC29 Integration: Context must be built")
            assertNotNull(context.sentimentTrend, "UC29 Integration: Sentiment trend must be detected")
            assertNotNull(response, "UC29 Integration: Response must be generated")
        }
    }

    @Nested
    @DisplayName("Integration Test 3: Crisis Detection Integration")
    inner class CrisisDetectionIntegrationTests {
        
        @Test
        @DisplayName("Should integrate crisis detection with escalation")
        fun `crisis detection integrates with escalation`() {
            // Given: Use case and crisis message
            val useCase = AISentimentAdaptiveChatUseCase()
            val message = "I feel hopeless and want to end it all"
            
            // When: System integrates crisis detection
            val indicators = useCase.detectCrisisIndicators(message)
            val escalation = useCase.escalateCrisisSituation(message, indicators)
            val response = useCase.generateCrisisResponse(message)
            
            // Then: Integration works correctly
            assertNotNull(indicators, "UC29 Integration: Crisis indicators must be detected")
            assertTrue(indicators.isNotEmpty(), "UC29 Integration: Crisis indicators should be detected")
            assertNotNull(escalation, "UC29 Integration: Escalation must occur")
            assertTrue(escalation.immediateActions.isNotEmpty(),
                "UC29 Integration: Immediate actions must be provided")
            assertNotNull(response, "UC29 Integration: Crisis response must be generated")
        }
    }

    @Nested
    @DisplayName("Integration Test 4: Personalization Integration")
    inner class PersonalizationIntegrationTests {
        
        @Test
        @DisplayName("Should integrate user profile with response personalization")
        fun `user profile integrates with response personalization`() {
            // Given: Use case, user ID, message, and user profile
            val useCase = AISentimentAdaptiveChatUseCase()
            val userId = "user123"
            val message = "I'm feeling good"
            val userProfile = UserProfile(
                userId = userId,
                preferences = UserPreferences(language = "es")
            )
            
            // When: System integrates user profile
            val sentiment = useCase.analyzeSentiment(message)
            val response = useCase.personalizeResponse(userId, message, userProfile)
            
            // Then: Integration works correctly
            assertNotNull(sentiment, "UC29 Integration: Sentiment must be analyzed")
            assertNotNull(response, "UC29 Integration: Personalized response must be generated")
            assertTrue(response.message.isNotBlank(), "UC29 Integration: Response must not be empty")
        }
        
        @Test
        @DisplayName("Should integrate emotional history with pattern adaptation")
        fun `emotional history integrates with pattern adaptation`() {
            // Given: Use case, user ID, message, and emotional history
            val useCase = AISentimentAdaptiveChatUseCase()
            val userId = "user123"
            val message = "I'm still feeling down"
            val emotionalHistory = listOf(
                SentimentAnalysis(SentimentType.NEGATIVE, 0.8f, emptyList(), 0.7f, java.util.Date()),
                SentimentAnalysis(SentimentType.NEGATIVE, 0.75f, emptyList(), 0.75f, java.util.Date())
            )
            
            // When: System integrates emotional history
            val response = useCase.adaptToEmotionalPatterns(userId, message, emotionalHistory)
            
            // Then: Integration works correctly
            assertNotNull(response, "UC29 Integration: Adapted response must be generated")
            assertTrue(response.message.isNotBlank(), "UC29 Integration: Response must not be empty")
        }
    }

    @Nested
    @DisplayName("Integration Test 5: End-to-End Sentiment Adaptive Chat Flow")
    inner class EndToEndFlowTests {
        
        @Test
        @DisplayName("Should integrate complete sentiment adaptive chat flow")
        fun `complete sentiment adaptive chat flow integrates correctly`() {
            // Given: Use case, user ID, and conversation flow
            val useCase = AISentimentAdaptiveChatUseCase()
            val userId = "user123"
            val messages = listOf(
                "I'm feeling good today",
                "Actually, I'm feeling worse now",
                "I'm feeling terrible and hopeless"
            )
            
            // When: System processes complete flow
            val history = mutableListOf<ChatMessage>()
            val responses = mutableListOf<AITherapistResponse>()
            
            messages.forEach { message ->
                val chatMessage = ChatMessage("msg_${System.currentTimeMillis()}", message, true, java.util.Date())
                history.add(chatMessage)
                
                val sentiment = useCase.analyzeSentiment(message)
                val response = useCase.generateContextualResponse(userId, message, history)
                responses.add(response)
            }
            
            // Then: Complete flow works correctly
            assertEquals(messages.size, history.size, "UC29 Integration: All messages must be processed")
            assertEquals(messages.size, responses.size, "UC29 Integration: All responses must be generated")
            responses.forEach { response ->
                assertNotNull(response, "UC29 Integration: Each response must be generated")
                assertTrue(response.message.isNotBlank(), "UC29 Integration: Each response must not be empty")
            }
        }
    }
}

