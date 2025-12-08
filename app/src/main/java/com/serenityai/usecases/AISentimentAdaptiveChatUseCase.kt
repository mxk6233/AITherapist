package com.serenityai.usecases

import com.serenityai.data.models.*
import java.util.Date

/** UC29: AI sentiment adaptive chat system that analyzes user sentiment and adapts responses accordingly. */
class AISentimentAdaptiveChatUseCase {
    
    private val conversationHistory = mutableMapOf<String, MutableList<ChatMessage>>()
    private val sentimentHistory = mutableMapOf<String, MutableList<SentimentAnalysis>>()
    
    /** Analyzes sentiment from user message. */
    fun analyzeSentiment(message: String): SentimentAnalysis {
        val lowerMessage = message.lowercase()
        val positiveWords = listOf("great", "good", "happy", "excited", "wonderful", "amazing", "love", "thankful", "grateful", "better", "improving")
        val negativeWords = listOf("bad", "terrible", "awful", "hate", "sad", "depressed", "anxious", "worried", "stressed", "frustrated", "angry", "hopeless")
        val veryNegativeWords = listOf("suicide", "kill", "end", "worthless", "useless", "despair", "hopeless")
        
        val positiveCount = positiveWords.count { lowerMessage.contains(it) }
        val negativeCount = negativeWords.count { lowerMessage.contains(it) }
        val veryNegativeCount = veryNegativeWords.count { lowerMessage.contains(it) }
        
        val sentimentType = when {
            veryNegativeCount > 0 -> SentimentType.VERY_NEGATIVE
            negativeCount > positiveCount && negativeCount > 2 -> SentimentType.NEGATIVE
            positiveCount > negativeCount && positiveCount > 2 -> SentimentType.POSITIVE
            positiveCount > 3 -> SentimentType.VERY_POSITIVE
            negativeCount > 0 -> SentimentType.NEGATIVE
            positiveCount > 0 -> SentimentType.POSITIVE
            else -> SentimentType.NEUTRAL
        }
        
        val confidence = when (sentimentType) {
            SentimentType.VERY_POSITIVE, SentimentType.VERY_NEGATIVE -> 0.9f
            SentimentType.POSITIVE, SentimentType.NEGATIVE -> 0.75f
            SentimentType.NEUTRAL -> 0.5f
        }
        
        return SentimentAnalysis(
            type = sentimentType,
            confidence = confidence,
            keywords = extractKeywords(message),
            intensity = calculateIntensity(message, sentimentType),
            detectedAt = Date()
        )
    }
    
    /** Detects emotional intensity from message. */
    fun detectEmotionalIntensity(message: String): Float {
        val sentiment = analyzeSentiment(message)
        val exclamationCount = message.count { it == '!' }
        val capsRatio = message.count { it.isUpperCase() }.toFloat() / message.length.coerceAtLeast(1)
        
        return when (sentiment.type) {
            SentimentType.VERY_POSITIVE, SentimentType.VERY_NEGATIVE -> 0.9f + (exclamationCount * 0.05f).coerceAtMost(0.1f)
            SentimentType.POSITIVE, SentimentType.NEGATIVE -> 0.7f + (capsRatio * 0.2f).coerceAtMost(0.2f)
            SentimentType.NEUTRAL -> 0.5f
        }
    }
    
    /** Generates adaptive response based on sentiment. */
    fun generateAdaptiveResponse(message: String, sentiment: SentimentAnalysis): AITherapistResponse {
        val responseMessage = when (sentiment.type) {
            SentimentType.VERY_POSITIVE -> generatePositiveResponse(message)
            SentimentType.POSITIVE -> generateSupportiveResponse(message)
            SentimentType.NEUTRAL -> generateNeutralResponse(message)
            SentimentType.NEGATIVE -> generateEmpatheticResponse(message)
            SentimentType.VERY_NEGATIVE -> generateCrisisResponse(message)
        }
        
        return AITherapistResponse(
            id = "response_${System.currentTimeMillis()}",
            message = responseMessage,
            type = determineResponseType(sentiment),
            moodAnalysis = MoodAnalysis(
                detectedMood = sentiment.type.name,
                confidence = sentiment.confidence,
                sentiment = sentiment.type,
                keywords = sentiment.keywords
            )
        )
    }
    
    /** Builds conversation context from history. */
    fun buildConversationContext(userId: String, history: List<ChatMessage>): ConversationContext {
        val recentMessages = history.takeLast(10)
        val sentiments = recentMessages.map { analyzeSentiment(it.text) }
        val averageSentiment = calculateAverageSentiment(sentiments)
        
        return ConversationContext(
            userId = userId,
            recentMessages = recentMessages,
            sentimentTrend = detectSentimentTrend(sentiments),
            averageSentiment = averageSentiment,
            emotionalPatterns = detectEmotionalPatterns(sentiments)
        )
    }
    
    /** Generates contextual response using conversation history. */
    fun generateContextualResponse(userId: String, currentMessage: String, history: List<ChatMessage>): AITherapistResponse {
        val context = buildConversationContext(userId, history)
        val currentSentiment = analyzeSentiment(currentMessage)
        
        val response = when {
            context.sentimentTrend == SentimentTrend.DECLINING && currentSentiment.type == SentimentType.NEGATIVE -> 
                "I notice you've been feeling down lately. Let's work through this together. What's been weighing on you?"
            context.sentimentTrend == SentimentTrend.IMPROVING && currentSentiment.type == SentimentType.POSITIVE -> 
                "It's wonderful to see you're feeling better! What's been helping you feel more positive?"
            currentSentiment.type == SentimentType.VERY_NEGATIVE -> 
                generateCrisisResponse(currentMessage)
            else -> generateAdaptiveResponse(currentMessage, currentSentiment).message
        }
        
        return AITherapistResponse(
            id = "response_${System.currentTimeMillis()}",
            message = response,
            type = determineResponseType(currentSentiment),
            moodAnalysis = MoodAnalysis(
                detectedMood = currentSentiment.type.name,
                confidence = currentSentiment.confidence,
                sentiment = currentSentiment.type
            )
        )
    }
    
    /** Detects conversation patterns. */
    fun detectConversationPatterns(userId: String, history: List<ChatMessage>): List<ConversationPattern> {
        val patterns = mutableListOf<ConversationPattern>()
        val sentiments = history.map { analyzeSentiment(it.text) }
        
        if (sentiments.count { it.type == SentimentType.NEGATIVE } > sentiments.size * 0.6) {
            patterns.add(ConversationPattern.NEGATIVE_TREND)
        }
        if (sentiments.count { it.intensity > 0.8f } > sentiments.size * 0.4) {
            patterns.add(ConversationPattern.HIGH_INTENSITY)
        }
        if (sentiments.any { it.type == SentimentType.VERY_NEGATIVE }) {
            patterns.add(ConversationPattern.CRISIS_INDICATORS)
        }
        
        return patterns
    }
    
    /** Detects emotional triggers in message. */
    fun detectEmotionalTriggers(message: String): List<String> {
        val triggers = mutableListOf<String>()
        val lowerMessage = message.lowercase()
        
        val triggerKeywords = mapOf(
            "work" to "work_stress",
            "family" to "family_issues",
            "relationship" to "relationship_problems",
            "money" to "financial_stress",
            "health" to "health_concerns",
            "lonely" to "loneliness",
            "anxious" to "anxiety",
            "depressed" to "depression"
        )
        
        triggerKeywords.forEach { (keyword, trigger) ->
            if (lowerMessage.contains(keyword)) {
                triggers.add(trigger)
            }
        }
        
        return triggers
    }
    
    /** Identifies emotional needs from message. */
    fun identifyEmotionalNeeds(message: String): List<EmotionalNeed> {
        val needs = mutableListOf<EmotionalNeed>()
        val sentiment = analyzeSentiment(message)
        val triggers = detectEmotionalTriggers(message)
        
        when (sentiment.type) {
            SentimentType.VERY_NEGATIVE, SentimentType.NEGATIVE -> {
                needs.add(EmotionalNeed.SUPPORT)
                needs.add(EmotionalNeed.VALIDATION)
                if (triggers.isNotEmpty()) {
                    needs.add(EmotionalNeed.PROBLEM_SOLVING)
                }
            }
            SentimentType.POSITIVE, SentimentType.VERY_POSITIVE -> {
                needs.add(EmotionalNeed.ENCOURAGEMENT)
            }
            SentimentType.NEUTRAL -> {
                needs.add(EmotionalNeed.ENGAGEMENT)
            }
        }
        
        return needs
    }
    
    /** Provides emotional validation. */
    fun provideEmotionalValidation(message: String): String {
        val sentiment = analyzeSentiment(message)
        return when (sentiment.type) {
            SentimentType.NEGATIVE, SentimentType.VERY_NEGATIVE -> 
                "I hear you, and your feelings are completely valid. It's okay to feel this way."
            SentimentType.POSITIVE, SentimentType.VERY_POSITIVE -> 
                "I'm so glad you're feeling positive! That's wonderful to hear."
            SentimentType.NEUTRAL -> 
                "Thank you for sharing. I'm here to listen and support you."
        }
    }
    
    /** Suggests emotional regulation techniques. */
    fun suggestEmotionalRegulationTechniques(message: String): List<String> {
        val sentiment = analyzeSentiment(message)
        val intensity = detectEmotionalIntensity(message)
        
        return when {
            intensity > 0.8f && sentiment.type in listOf(SentimentType.NEGATIVE, SentimentType.VERY_NEGATIVE) -> 
                listOf("Deep breathing exercise", "Progressive muscle relaxation", "Grounding technique: 5-4-3-2-1")
            sentiment.type == SentimentType.NEGATIVE -> 
                listOf("Mindful breathing", "Journaling", "Gentle movement")
            else -> 
                listOf("Mindfulness meditation", "Gratitude practice")
        }
    }
    
    /** Personalizes response based on user profile. */
    fun personalizeResponse(userId: String, message: String, userProfile: UserProfile?): AITherapistResponse {
        val sentiment = analyzeSentiment(message)
        val baseResponse = generateAdaptiveResponse(message, sentiment)
        
        val personalizedMessage = userProfile?.let { profile ->
            when (profile.preferences?.language) {
                "es" -> "Hola, ${baseResponse.message}"
                else -> baseResponse.message
            }
        } ?: baseResponse.message
        
        return baseResponse.copy(message = personalizedMessage)
    }
    
    /** Adapts to emotional patterns. */
    fun adaptToEmotionalPatterns(userId: String, message: String, emotionalHistory: List<SentimentAnalysis>): AITherapistResponse {
        val currentSentiment = analyzeSentiment(message)
        val trend = detectSentimentTrend(emotionalHistory)
        
        val response = when {
            trend == SentimentTrend.DECLINING && currentSentiment.type == SentimentType.NEGATIVE -> 
                "I've noticed you've been struggling more lately. Let's take this step by step. What's been the hardest part?"
            trend == SentimentTrend.IMPROVING -> 
                "I'm seeing positive changes in how you're feeling. That's really encouraging!"
            else -> generateAdaptiveResponse(message, currentSentiment).message
        }
        
        return AITherapistResponse(
            id = "response_${System.currentTimeMillis()}",
            message = response,
            type = determineResponseType(currentSentiment)
        )
    }
    
    /** Adapts to user preferences. */
    fun adaptToUserPreferences(userId: String, message: String, preferences: Map<String, Any>): AITherapistResponse {
        val sentiment = analyzeSentiment(message)
        val baseResponse = generateAdaptiveResponse(message, sentiment)
        
        val responseStyle = preferences["responseStyle"] as? String ?: "empathetic"
        val adaptedMessage = when (responseStyle) {
            "direct" -> baseResponse.message.replace("I understand", "Here's what I think")
            "gentle" -> baseResponse.message.replace(".", ".")
            else -> baseResponse.message
        }
        
        return baseResponse.copy(message = adaptedMessage)
    }
    
    /** Detects crisis indicators. */
    fun detectCrisisIndicators(message: String): List<CrisisIndicator> {
        val indicators = mutableListOf<CrisisIndicator>()
        val lowerMessage = message.lowercase()
        val sentiment = analyzeSentiment(message)
        
        val crisisKeywords = listOf("suicide", "kill myself", "end it all", "no point", "worthless", "hopeless", "can't go on")
        if (crisisKeywords.any { lowerMessage.contains(it) }) {
            indicators.add(CrisisIndicator.SUICIDAL_IDEATION)
        }
        
        if (sentiment.type == SentimentType.VERY_NEGATIVE && sentiment.intensity > 0.9f) {
            indicators.add(CrisisIndicator.SEVERE_DISTRESS)
        }
        
        return indicators
    }
    
    /** Escalates crisis situation. */
    fun escalateCrisisSituation(message: String, indicators: List<CrisisIndicator>): CrisisEscalation {
        val hasSuicidalIdeation = indicators.contains(CrisisIndicator.SUICIDAL_IDEATION)
        val severity = when {
            hasSuicidalIdeation -> CrisisSeverity.CRITICAL
            indicators.isNotEmpty() -> CrisisSeverity.HIGH
            else -> CrisisSeverity.MEDIUM
        }
        
        return CrisisEscalation(
            severity = severity,
            indicators = indicators,
            immediateActions = when (severity) {
                CrisisSeverity.CRITICAL -> listOf("Contact crisis hotline", "Reach out to emergency contacts", "Seek immediate professional help")
                CrisisSeverity.HIGH -> listOf("Use coping strategies", "Contact support network", "Consider professional support")
                CrisisSeverity.MEDIUM -> listOf("Practice self-care", "Use grounding techniques")
                CrisisSeverity.LOW -> listOf("Practice self-care", "Use coping strategies")
            },
            resources = getCrisisResources(severity)
        )
    }
    
    /** Generates crisis response. */
    fun generateCrisisResponse(message: String): String {
        val indicators = detectCrisisIndicators(message)
        return when {
            indicators.contains(CrisisIndicator.SUICIDAL_IDEATION) -> 
                "I'm very concerned about what you've shared. Your life has value and there are people who care about you. Please reach out to a crisis hotline or emergency services immediately. You don't have to go through this alone."
            indicators.isNotEmpty() -> 
                "I hear that you're going through a very difficult time. Let's work through this together. Would you like to talk about what's making things so hard right now?"
            else -> 
                "I'm here for you. Let's take this one step at a time. What's on your mind?"
        }
    }
    
    /** Provides actionable advice. */
    fun provideActionableAdvice(message: String): List<String> {
        val sentiment = analyzeSentiment(message)
        val needs = identifyEmotionalNeeds(message)
        
        return when {
            needs.contains(EmotionalNeed.PROBLEM_SOLVING) -> 
                listOf("Break down the problem into smaller steps", "Identify what you can control", "Consider different perspectives")
            needs.contains(EmotionalNeed.SUPPORT) -> 
                listOf("Reach out to trusted friends or family", "Consider joining a support group", "Connect with a mental health professional")
            sentiment.type == SentimentType.NEGATIVE -> 
                listOf("Practice self-compassion", "Engage in activities you enjoy", "Focus on small positive moments")
            else -> 
                listOf("Continue your positive practices", "Share your progress with others", "Set small achievable goals")
        }
    }
    
    // Helper methods
    private fun extractKeywords(message: String): List<String> {
        val words = message.lowercase().split(" ").filter { it.length > 4 }
        return words.take(5)
    }
    
    private fun calculateIntensity(message: String, sentiment: SentimentType): Float {
        val exclamationCount = message.count { it == '!' }
        val capsRatio = message.count { it.isUpperCase() }.toFloat() / message.length.coerceAtLeast(1)
        return when (sentiment) {
            SentimentType.VERY_POSITIVE, SentimentType.VERY_NEGATIVE -> 0.9f + (exclamationCount * 0.05f).coerceAtMost(0.1f)
            SentimentType.POSITIVE, SentimentType.NEGATIVE -> 0.7f + (capsRatio * 0.2f).coerceAtMost(0.2f)
            SentimentType.NEUTRAL -> 0.5f
        }
    }
    
    private fun generatePositiveResponse(message: String): String {
        return "That's wonderful to hear! I'm so glad you're feeling positive. What's been contributing to these good feelings?"
    }
    
    private fun generateSupportiveResponse(message: String): String {
        return "I'm glad you're sharing this with me. It sounds like you're in a good place. What would you like to explore today?"
    }
    
    private fun generateNeutralResponse(message: String): String {
        return "Thank you for sharing. I'm here to listen and support you. What's on your mind today?"
    }
    
    private fun generateEmpatheticResponse(message: String): String {
        return "I hear you, and I want you to know that your feelings are valid. It sounds like you're going through a difficult time. Would you like to talk more about what's bothering you?"
    }
    
    private fun determineResponseType(sentiment: SentimentAnalysis): ResponseType {
        return when (sentiment.type) {
            SentimentType.VERY_NEGATIVE -> ResponseType.CRISIS_SUPPORT
            SentimentType.NEGATIVE -> ResponseType.THERAPEUTIC
            SentimentType.POSITIVE, SentimentType.VERY_POSITIVE -> ResponseType.CELEBRATION
            SentimentType.NEUTRAL -> ResponseType.GENERAL
        }
    }
    
    private fun calculateAverageSentiment(sentiments: List<SentimentAnalysis>): SentimentType {
        if (sentiments.isEmpty()) return SentimentType.NEUTRAL
        
        val scores = sentiments.map { 
            when (it.type) {
                SentimentType.VERY_POSITIVE -> 2
                SentimentType.POSITIVE -> 1
                SentimentType.NEUTRAL -> 0
                SentimentType.NEGATIVE -> -1
                SentimentType.VERY_NEGATIVE -> -2
            }
        }
        
        val average = scores.average()
        return when {
            average >= 1.5 -> SentimentType.VERY_POSITIVE
            average >= 0.5 -> SentimentType.POSITIVE
            average <= -1.5 -> SentimentType.VERY_NEGATIVE
            average <= -0.5 -> SentimentType.NEGATIVE
            else -> SentimentType.NEUTRAL
        }
    }
    
    private fun detectSentimentTrend(sentiments: List<SentimentAnalysis>): SentimentTrend {
        if (sentiments.size < 2) return SentimentTrend.STABLE
        
        val recent = sentiments.takeLast(3)
        val earlier = sentiments.dropLast(3).takeLast(3)
        
        if (earlier.isEmpty()) return SentimentTrend.STABLE
        
        val recentAvg = recent.map { 
            when (it.type) {
                SentimentType.VERY_POSITIVE -> 2
                SentimentType.POSITIVE -> 1
                SentimentType.NEUTRAL -> 0
                SentimentType.NEGATIVE -> -1
                SentimentType.VERY_NEGATIVE -> -2
            }
        }.average()
        
        val earlierAvg = earlier.map {
            when (it.type) {
                SentimentType.VERY_POSITIVE -> 2
                SentimentType.POSITIVE -> 1
                SentimentType.NEUTRAL -> 0
                SentimentType.NEGATIVE -> -1
                SentimentType.VERY_NEGATIVE -> -2
            }
        }.average()
        
        return when {
            recentAvg > earlierAvg + 0.5 -> SentimentTrend.IMPROVING
            recentAvg < earlierAvg - 0.5 -> SentimentTrend.DECLINING
            else -> SentimentTrend.STABLE
        }
    }
    
    private fun detectEmotionalPatterns(sentiments: List<SentimentAnalysis>): List<String> {
        val patterns = mutableListOf<String>()
        if (sentiments.count { it.type == SentimentType.NEGATIVE } > sentiments.size * 0.6) {
            patterns.add("persistent_negative")
        }
        if (sentiments.any { it.intensity > 0.8f }) {
            patterns.add("high_intensity")
        }
        return patterns
    }
    
    private fun getCrisisResources(severity: CrisisSeverity): List<String> {
        return when (severity) {
            CrisisSeverity.CRITICAL -> listOf(
                "National Suicide Prevention Lifeline: 988",
                "Crisis Text Line: Text HOME to 741741",
                "Emergency Services: 911"
            )
            CrisisSeverity.HIGH -> listOf(
                "Crisis support hotline",
                "Mental health professional directory",
                "Support group resources"
            )
            CrisisSeverity.MEDIUM -> listOf(
                "Self-help resources",
                "Coping strategies guide",
                "Community support options"
            )
            CrisisSeverity.LOW -> listOf(
                "Self-help resources",
                "Coping strategies guide"
            )
        }
    }
}

/** Data class for sentiment analysis results. */
data class SentimentAnalysis(
    val type: SentimentType,
    val confidence: Float,
    val keywords: List<String>,
    val intensity: Float,
    val detectedAt: Date
)

/** Data class for conversation context. */
data class ConversationContext(
    val userId: String,
    val recentMessages: List<ChatMessage>,
    val sentimentTrend: SentimentTrend,
    val averageSentiment: SentimentType,
    val emotionalPatterns: List<String>
)

/** Enum for sentiment trends. */
enum class SentimentTrend {
    IMPROVING, DECLINING, STABLE, VOLATILE
}

/** Enum for conversation patterns. */
enum class ConversationPattern {
    NEGATIVE_TREND, HIGH_INTENSITY, CRISIS_INDICATORS, POSITIVE_MOMENTUM
}

/** Enum for emotional needs. */
enum class EmotionalNeed {
    SUPPORT, VALIDATION, PROBLEM_SOLVING, ENCOURAGEMENT, ENGAGEMENT, CRISIS_INTERVENTION
}

/** Enum for crisis indicators. */
enum class CrisisIndicator {
    SUICIDAL_IDEATION, SEVERE_DISTRESS, SELF_HARM_RISK, ISOLATION, SUBSTANCE_ABUSE
}

/** Data class for crisis escalation. */
data class CrisisEscalation(
    val severity: CrisisSeverity,
    val indicators: List<CrisisIndicator>,
    val immediateActions: List<String>,
    val resources: List<String>
)

/** Enum for crisis severity. */
enum class CrisisSeverity {
    LOW, MEDIUM, HIGH, CRITICAL
}

