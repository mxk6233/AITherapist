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

class AIGeneratedJournalingPromptsUseCaseTest {

    private lateinit var aiGeneratedJournalingPromptsUseCase: AIGeneratedJournalingPromptsUseCase

    @BeforeEach
    fun setUp() {
        aiGeneratedJournalingPromptsUseCase = AIGeneratedJournalingPromptsUseCase()
    }

    @Nested
    @DisplayName("Prompt Generation")
    inner class PromptGeneration {

        @Test
        @DisplayName("should generate mood-based prompts")
        fun shouldGenerateMoodBasedPrompts() = runTest {
            val mood = 3
            val context = "feeling anxious"

            val prompts = aiGeneratedJournalingPromptsUseCase.generateMoodBasedPrompts(mood, context)

            assertNotNull(prompts)
            assertTrue(prompts.isNotEmpty())
            assertTrue(prompts.any { it.contains("anxiety") || it.contains("anxious") })
        }

        @Test
        @DisplayName("should generate goal-oriented prompts")
        fun shouldGenerateGoalOrientedPrompts() = runTest {
            val goal = "reduce stress"
            val timeframe = "weekly"

            val prompts = aiGeneratedJournalingPromptsUseCase.generateGoalOrientedPrompts(goal, timeframe)

            assertNotNull(prompts)
            assertTrue(prompts.isNotEmpty())
            assertTrue(prompts.any { it.contains("stress") || it.contains("goal") })
        }

        @Test
        @DisplayName("should generate reflection prompts")
        fun shouldGenerateReflectionPrompts() = runTest {
            val reflectionType = "daily"
            val focus = "gratitude"

            val prompts = aiGeneratedJournalingPromptsUseCase.generateReflectionPrompts(reflectionType, focus)

            assertNotNull(prompts)
            assertTrue(prompts.isNotEmpty())
            assertTrue(prompts.any { it.contains("gratitude") || it.contains("thankful") })
        }

        @Test
        @DisplayName("should generate therapeutic prompts")
        fun shouldGenerateTherapeuticPrompts() = runTest {
            val therapyType = "CBT"
            val sessionFocus = "negative thoughts"

            val prompts = aiGeneratedJournalingPromptsUseCase.generateTherapeuticPrompts(therapyType, sessionFocus)

            assertNotNull(prompts)
            assertTrue(prompts.isNotEmpty())
            assertTrue(prompts.any { it.contains("thought") || it.contains("CBT") })
        }

        @Test
        @DisplayName("should generate creative prompts")
        fun shouldGenerateCreativePrompts() = runTest {
            val creativityLevel = "high"
            val theme = "self-discovery"

            val prompts = aiGeneratedJournalingPromptsUseCase.generateCreativePrompts(creativityLevel, theme)

            assertNotNull(prompts)
            assertTrue(prompts.isNotEmpty())
            assertTrue(prompts.any { it.contains("creative") || it.contains("imagine") })
        }
    }

    @Nested
    @DisplayName("Personalization")
    inner class Personalization {

        @Test
        @DisplayName("should personalize prompts based on user profile")
        fun shouldPersonalizePromptsBasedOnUserProfile() = runTest {
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

            val prompts = aiGeneratedJournalingPromptsUseCase.personalizePrompts(userId, userProfile)

            assertNotNull(prompts)
            assertTrue(prompts.isNotEmpty())
        }

        @Test
        @DisplayName("should adapt prompts based on writing history")
        fun shouldAdaptPromptsBasedOnWritingHistory() = runTest {
            val userId = "user123"
            val writingHistory = listOf(
                JournalEntry(
                    id = "entry1",
                    userId = userId,
                    prompt = "How are you feeling today?",
                    content = "I'm feeling anxious",
                    mood = 3,
                    tags = listOf("anxiety"),
                    createdAt = Date()
                )
            )

            val prompts = aiGeneratedJournalingPromptsUseCase.adaptPromptsBasedOnHistory(userId, writingHistory)

            assertNotNull(prompts)
            assertTrue(prompts.isNotEmpty())
        }

        @Test
        @DisplayName("should customize prompts based on preferences")
        fun shouldCustomizePromptsBasedOnPreferences() = runTest {
            val userId = "user123"
            val preferences = JournalingPreferences(
                preferredLength = "medium",
                writingStyle = "reflective",
                topics = listOf("mental_health", "personal_growth"),
                difficultyLevel = "intermediate"
            )

            val prompts = aiGeneratedJournalingPromptsUseCase.customizePromptsBasedOnPreferences(userId, preferences)

            assertNotNull(prompts)
            assertTrue(prompts.isNotEmpty())
        }

        @Test
        @DisplayName("should adjust prompts based on time of day")
        fun shouldAdjustPromptsBasedOnTimeOfDay() = runTest {
            val timeOfDay = "morning"
            val mood = 5

            val prompts = aiGeneratedJournalingPromptsUseCase.adjustPromptsForTimeOfDay(timeOfDay, mood)

            assertNotNull(prompts)
            assertTrue(prompts.isNotEmpty())
            assertTrue(prompts.any { it.contains("morning") || it.contains("today") })
        }
    }

    @Nested
    @DisplayName("Prompt Categories")
    inner class PromptCategories {

        @Test
        @DisplayName("should generate gratitude prompts")
        fun shouldGenerateGratitudePrompts() = runTest {
            val prompts = aiGeneratedJournalingPromptsUseCase.generateGratitudePrompts()

            assertNotNull(prompts)
            assertTrue(prompts.isNotEmpty())
            assertTrue(prompts.any { it.contains("grateful") || it.contains("thankful") })
        }

        @Test
        @DisplayName("should generate self-reflection prompts")
        fun shouldGenerateSelfReflectionPrompts() = runTest {
            val prompts = aiGeneratedJournalingPromptsUseCase.generateSelfReflectionPrompts()

            assertNotNull(prompts)
            assertTrue(prompts.isNotEmpty())
            assertTrue(prompts.any { it.contains("reflect") || it.contains("think") })
        }

        @Test
        @DisplayName("should generate emotional processing prompts")
        fun shouldGenerateEmotionalProcessingPrompts() = runTest {
            val prompts = aiGeneratedJournalingPromptsUseCase.generateEmotionalProcessingPrompts()

            assertNotNull(prompts)
            assertTrue(prompts.isNotEmpty())
            assertTrue(prompts.any { it.contains("emotion") || it.contains("feel") })
        }

        @Test
        @DisplayName("should generate problem-solving prompts")
        fun shouldGenerateProblemSolvingPrompts() = runTest {
            val prompts = aiGeneratedJournalingPromptsUseCase.generateProblemSolvingPrompts()

            assertNotNull(prompts)
            assertTrue(prompts.isNotEmpty())
            assertTrue(prompts.any { it.contains("problem") || it.contains("solution") })
        }

        @Test
        @DisplayName("should generate future planning prompts")
        fun shouldGenerateFuturePlanningPrompts() = runTest {
            val prompts = aiGeneratedJournalingPromptsUseCase.generateFuturePlanningPrompts()

            assertNotNull(prompts)
            assertTrue(prompts.isNotEmpty())
            assertTrue(prompts.any { it.contains("future") || it.contains("plan") })
        }
    }

    @Nested
    @DisplayName("Prompt Difficulty and Length")
    inner class PromptDifficultyAndLength {

        @Test
        @DisplayName("should generate beginner-level prompts")
        fun shouldGenerateBeginnerLevelPrompts() = runTest {
            val difficulty = "beginner"

            val prompts = aiGeneratedJournalingPromptsUseCase.generatePromptsByDifficulty(difficulty)

            assertNotNull(prompts)
            assertTrue(prompts.isNotEmpty())
            assertTrue(prompts.all { it.length < 100 })
        }

        @Test
        @DisplayName("should generate intermediate-level prompts")
        fun shouldGenerateIntermediateLevelPrompts() = runTest {
            val difficulty = "intermediate"

            val prompts = aiGeneratedJournalingPromptsUseCase.generatePromptsByDifficulty(difficulty)

            assertNotNull(prompts)
            assertTrue(prompts.isNotEmpty())
            assertTrue(prompts.all { it.length >= 100 && it.length < 200 })
        }

        @Test
        @DisplayName("should generate advanced-level prompts")
        fun shouldGenerateAdvancedLevelPrompts() = runTest {
            val difficulty = "advanced"

            val prompts = aiGeneratedJournalingPromptsUseCase.generatePromptsByDifficulty(difficulty)

            assertNotNull(prompts)
            assertTrue(prompts.isNotEmpty())
            assertTrue(prompts.all { it.length >= 200 })
        }

        @Test
        @DisplayName("should generate short prompts")
        fun shouldGenerateShortPrompts() = runTest {
            val length = "short"

            val prompts = aiGeneratedJournalingPromptsUseCase.generatePromptsByLength(length)

            assertNotNull(prompts)
            assertTrue(prompts.isNotEmpty())
            assertTrue(prompts.all { it.length < 50 })
        }

        @Test
        @DisplayName("should generate medium prompts")
        fun shouldGenerateMediumPrompts() = runTest {
            val length = "medium"

            val prompts = aiGeneratedJournalingPromptsUseCase.generatePromptsByLength(length)

            assertNotNull(prompts)
            assertTrue(prompts.isNotEmpty())
            assertTrue(prompts.all { it.length >= 50 && it.length < 150 })
        }

        @Test
        @DisplayName("should generate long prompts")
        fun shouldGenerateLongPrompts() = runTest {
            val length = "long"

            val prompts = aiGeneratedJournalingPromptsUseCase.generatePromptsByLength(length)

            assertNotNull(prompts)
            assertTrue(prompts.isNotEmpty())
            assertTrue(prompts.all { it.length >= 150 })
        }
    }

    @Nested
    @DisplayName("Contextual Awareness")
    inner class ContextualAwareness {

        @Test
        @DisplayName("should generate context-aware prompts")
        fun shouldGenerateContextAwarePrompts() = runTest {
            val context = mapOf(
                "recent_events" to "job interview",
                "current_mood" to "nervous",
                "time_of_day" to "evening"
            )

            val prompts = aiGeneratedJournalingPromptsUseCase.generateContextAwarePrompts(context)

            assertNotNull(prompts)
            assertTrue(prompts.isNotEmpty())
            assertTrue(prompts.any { it.contains("interview") || it.contains("nervous") })
        }

        @Test
        @DisplayName("should adapt prompts based on recent entries")
        fun shouldAdaptPromptsBasedOnRecentEntries() = runTest {
            val userId = "user123"
            val recentEntries = listOf(
                JournalEntry(
                    id = "entry1",
                    userId = userId,
                    prompt = "How was your day?",
                    content = "I had a difficult day at work",
                    mood = 2,
                    tags = listOf("work", "stress"),
                    createdAt = Date()
                )
            )

            val prompts = aiGeneratedJournalingPromptsUseCase.adaptPromptsBasedOnRecentEntries(userId, recentEntries)

            assertNotNull(prompts)
            assertTrue(prompts.isNotEmpty())
        }

        @Test
        @DisplayName("should generate seasonal prompts")
        fun shouldGenerateSeasonalPrompts() = runTest {
            val season = "winter"
            val mood = 4

            val prompts = aiGeneratedJournalingPromptsUseCase.generateSeasonalPrompts(season, mood)

            assertNotNull(prompts)
            assertTrue(prompts.isNotEmpty())
            assertTrue(prompts.any { it.contains("winter") || it.contains("cold") })
        }

        @Test
        @DisplayName("should generate holiday-specific prompts")
        fun shouldGenerateHolidaySpecificPrompts() = runTest {
            val holiday = "Christmas"
            val mood = 6

            val prompts = aiGeneratedJournalingPromptsUseCase.generateHolidaySpecificPrompts(holiday, mood)

            assertNotNull(prompts)
            assertTrue(prompts.isNotEmpty())
            assertTrue(prompts.any { it.contains("Christmas") || it.contains("holiday") })
        }
    }

    @Nested
    @DisplayName("Prompt Quality and Safety")
    inner class PromptQualityAndSafety {

        @Test
        @DisplayName("should ensure prompts are appropriate")
        fun shouldEnsurePromptsAreAppropriate() = runTest {
            val prompts = aiGeneratedJournalingPromptsUseCase.generateMoodBasedPrompts(2, "feeling down")

            assertNotNull(prompts)
            assertTrue(prompts.isNotEmpty())
            assertTrue(prompts.all { !it.contains("harmful") && !it.contains("dangerous") })
        }

        @Test
        @DisplayName("should validate prompt content")
        fun shouldValidatePromptContent() = runTest {
            val prompt = "Write about your feelings today"

            val isValid = aiGeneratedJournalingPromptsUseCase.validatePromptContent(prompt)

            assertTrue(isValid)
        }

        @Test
        @DisplayName("should filter inappropriate prompts")
        fun shouldFilterInappropriatePrompts() = runTest {
            val prompts = listOf(
                "Write about your feelings",
                "Describe your day",
                "Inappropriate prompt"
            )

            val filteredPrompts = aiGeneratedJournalingPromptsUseCase.filterInappropriatePrompts(prompts)

            assertNotNull(filteredPrompts)
            assertTrue(filteredPrompts.size < prompts.size)
            assertFalse(filteredPrompts.contains("Inappropriate prompt"))
        }

        @Test
        @DisplayName("should ensure prompts are supportive")
        fun shouldEnsurePromptsAreSupportive() = runTest {
            val prompts = aiGeneratedJournalingPromptsUseCase.generateMoodBasedPrompts(1, "feeling very low")

            assertNotNull(prompts)
            assertTrue(prompts.isNotEmpty())
            assertTrue(prompts.any { it.contains("support") || it.contains("help") })
        }
    }

    @Nested
    @DisplayName("Integration with AI Therapist")
    inner class IntegrationWithAITherapist {

        @Test
        @DisplayName("should integrate with AI therapist recommendations")
        fun shouldIntegrateWithAITherapistRecommendations() = runTest {
            val userId = "user123"
            val aiRecommendation = "Try journaling about your anxiety"

            val integratedPrompts = aiGeneratedJournalingPromptsUseCase.integrateWithAIRecommendations(userId, aiRecommendation)

            assertNotNull(integratedPrompts)
            assertTrue(integratedPrompts.isNotEmpty())
            assertTrue(integratedPrompts.any { it.contains("anxiety") })
        }

        @Test
        @DisplayName("should adapt prompts based on therapy progress")
        fun shouldAdaptPromptsBasedOnTherapyProgress() = runTest {
            val userId = "user123"
            val therapyProgress = mapOf(
                "sessions_completed" to 5,
                "goals_achieved" to 2,
                "current_focus" to "anxiety management"
            )

            val prompts = aiGeneratedJournalingPromptsUseCase.adaptPromptsBasedOnTherapyProgress(userId, therapyProgress)

            assertNotNull(prompts)
            assertTrue(prompts.isNotEmpty())
            assertTrue(prompts.any { it.contains("anxiety") || it.contains("management") })
        }

        @Test
        @DisplayName("should generate prompts for specific therapy techniques")
        fun shouldGeneratePromptsForSpecificTherapyTechniques() = runTest {
            val technique = "CBT"
            val focus = "negative thoughts"

            val prompts = aiGeneratedJournalingPromptsUseCase.generatePromptsForTherapyTechnique(technique, focus)

            assertNotNull(prompts)
            assertTrue(prompts.isNotEmpty())
            assertTrue(prompts.any { it.contains("thought") || it.contains("CBT") })
        }
    }

    // Helper methods
    private fun createMockJournalEntry(): JournalEntry {
        return JournalEntry(
            id = "entry123",
            userId = "user123",
            prompt = "How are you feeling today?",
            content = "I'm feeling anxious",
            mood = 3,
            tags = listOf("anxiety"),
            createdAt = Date()
        )
    }

    private fun createMockJournalingPreferences(): JournalingPreferences {
        return JournalingPreferences(
            preferredLength = "medium",
            writingStyle = "reflective",
            topics = listOf("mental_health", "personal_growth"),
            difficultyLevel = "intermediate"
        )
    }
}

/**
 * Journal entry data class
 */
data class JournalEntry(
    val id: String,
    val userId: String,
    val prompt: String,
    val content: String,
    val mood: Int,
    val tags: List<String>,
    val createdAt: Date
)

/**
 * Journaling preferences data class
 */
data class JournalingPreferences(
    val preferredLength: String,
    val writingStyle: String,
    val topics: List<String>,
    val difficultyLevel: String
)

/**
 * Use Case implementation for AI-Generated Journaling Prompts
 */
class AIGeneratedJournalingPromptsUseCase {
    
    suspend fun generateMoodBasedPrompts(mood: Int, context: String): List<String> {
        return when {
            mood <= 3 -> listOf(
                "What's contributing to your current feelings?",
                "Describe three things that might help you feel better",
                "Write about a time when you overcame a similar challenge",
                "What would you tell a friend who was feeling this way?"
            )
            mood <= 6 -> listOf(
                "What's been going well in your life recently?",
                "Describe something you're looking forward to",
                "Write about a person who brings you joy",
                "What are you grateful for today?"
            )
            else -> listOf(
                "What's making you feel so positive today?",
                "Describe how you can share this positive energy with others",
                "Write about a goal you'd like to achieve",
                "What advice would you give to someone having a difficult day?"
            )
        }
    }

    suspend fun generateGoalOrientedPrompts(goal: String, timeframe: String): List<String> {
        return listOf(
            "What steps can you take this $timeframe to work toward your goal of $goal?",
            "What obstacles might you face in achieving this goal?",
            "How will you know when you've achieved your goal?",
            "What support do you need to reach this goal?"
        )
    }

    suspend fun generateReflectionPrompts(reflectionType: String, focus: String): List<String> {
        return when (focus) {
            "gratitude" -> listOf(
                "What are three things you're grateful for today?",
                "Write about someone who has made a positive impact on your life",
                "Describe a moment today that brought you joy",
                "What simple pleasures are you thankful for?"
            )
            "growth" -> listOf(
                "What have you learned about yourself recently?",
                "How have you grown or changed in the past month?",
                "What challenges have helped you become stronger?",
                "What would you like to improve about yourself?"
            )
            else -> listOf(
                "How are you feeling right now?",
                "What's on your mind today?",
                "What would you like to focus on?",
                "How can you take care of yourself today?"
            )
        }
    }

    suspend fun generateTherapeuticPrompts(therapyType: String, sessionFocus: String): List<String> {
        return when (therapyType) {
            "CBT" -> listOf(
                "What negative thoughts are you having right now?",
                "What evidence supports or contradicts these thoughts?",
                "How would you advise a friend in this situation?",
                "What's a more balanced way to think about this?"
            )
            "DBT" -> listOf(
                "What emotions are you experiencing right now?",
                "What skills can you use to manage these emotions?",
                "How can you practice mindfulness today?",
                "What would be most effective for you right now?"
            )
            else -> listOf(
                "What's on your mind today?",
                "How are you feeling?",
                "What would you like to work on?",
                "What support do you need?"
            )
        }
    }

    suspend fun generateCreativePrompts(creativityLevel: String, theme: String): List<String> {
        return when (creativityLevel) {
            "high" -> listOf(
                "Imagine you're writing a letter to your future self",
                "Create a story about a character who overcomes a challenge",
                "Write a poem about your current emotions",
                "Describe your ideal day in detail"
            )
            "medium" -> listOf(
                "Write about a place that makes you feel peaceful",
                "Describe your perfect weekend",
                "Create a list of things that make you smile",
                "Write about a dream you had recently"
            )
            else -> listOf(
                "What's your favorite color and why?",
                "Describe your ideal meal",
                "Write about a hobby you enjoy",
                "What's something you're curious about?"
            )
        }
    }

    suspend fun personalizePrompts(userId: String, userProfile: UserProfile): List<String> {
        val basePrompts = generateMoodBasedPrompts(5, "general")
        
        // Personalize based on user profile
        return basePrompts.map { prompt ->
            when (userProfile.gender) {
                "Male" -> prompt.replace("you", "you, ${userProfile.firstName}")
                "Female" -> prompt.replace("you", "you, ${userProfile.firstName}")
                else -> prompt
            }
        }
    }

    suspend fun adaptPromptsBasedOnHistory(userId: String, writingHistory: List<JournalEntry>): List<String> {
        val recentTopics = writingHistory.flatMap { it.tags }.distinct()
        
        return when {
            recentTopics.contains("anxiety") -> listOf(
                "What anxiety management techniques have worked for you?",
                "Describe a time when you successfully managed your anxiety",
                "What triggers your anxiety and how can you address them?",
                "Write about your anxiety as if you're explaining it to a friend"
            )
            recentTopics.contains("depression") -> listOf(
                "What small things bring you joy even on difficult days?",
                "Describe your support system and how they help you",
                "What activities help you feel more like yourself?",
                "Write about a time when you felt hopeful"
            )
            else -> generateMoodBasedPrompts(5, "general")
        }
    }

    suspend fun customizePromptsBasedOnPreferences(userId: String, preferences: JournalingPreferences): List<String> {
        val basePrompts = generateMoodBasedPrompts(5, "general")
        
        return when (preferences.preferredLength) {
            "short" -> basePrompts.map { it.take(50) }
            "long" -> basePrompts.map { it + " Please provide detailed examples and specific details." }
            else -> basePrompts
        }
    }

    suspend fun adjustPromptsForTimeOfDay(timeOfDay: String, mood: Int): List<String> {
        return when (timeOfDay) {
            "morning" -> listOf(
                "What are your intentions for today?",
                "How do you want to feel by the end of the day?",
                "What's one thing you can do today to take care of yourself?",
                "What are you looking forward to today?"
            )
            "evening" -> listOf(
                "What went well today?",
                "What challenges did you face and how did you handle them?",
                "What are you grateful for from today?",
                "How do you want to feel tomorrow?"
            )
            else -> generateMoodBasedPrompts(mood, "general")
        }
    }

    suspend fun generateGratitudePrompts(): List<String> {
        return listOf(
            "What are three things you're grateful for today?",
            "Write about someone who has made a positive impact on your life",
            "Describe a moment today that brought you joy",
            "What simple pleasures are you thankful for?",
            "Who in your life are you grateful for and why?"
        )
    }

    suspend fun generateSelfReflectionPrompts(): List<String> {
        return listOf(
            "What have you learned about yourself recently?",
            "How have you grown or changed in the past month?",
            "What challenges have helped you become stronger?",
            "What would you like to improve about yourself?",
            "What are your core values and how do they guide your decisions?"
        )
    }

    suspend fun generateEmotionalProcessingPrompts(): List<String> {
        return listOf(
            "What emotions are you experiencing right now?",
            "Describe a recent situation that triggered strong emotions",
            "How do you typically handle difficult emotions?",
            "What emotions do you find most challenging to process?",
            "Write about a time when you successfully managed a difficult emotion"
        )
    }

    suspend fun generateProblemSolvingPrompts(): List<String> {
        return listOf(
            "What's a current challenge you're facing?",
            "What are three possible solutions to this problem?",
            "What resources do you have to help solve this issue?",
            "What would you advise a friend in this situation?",
            "What's the first step you can take to address this challenge?"
        )
    }

    suspend fun generateFuturePlanningPrompts(): List<String> {
        return listOf(
            "What are your goals for the next month?",
            "Where do you see yourself in one year?",
            "What steps can you take to achieve your long-term goals?",
            "What would you like to accomplish this year?",
            "How do you want to grow and develop in the future?"
        )
    }

    suspend fun generatePromptsByDifficulty(difficulty: String): List<String> {
        return when (difficulty) {
            "beginner" -> listOf(
                "How are you feeling?",
                "What did you do today?",
                "What made you smile?",
                "What are you grateful for?"
            )
            "intermediate" -> listOf(
                "What emotions are you experiencing and why?",
                "Describe a recent challenge and how you handled it",
                "What patterns do you notice in your thoughts and feelings?",
                "How can you practice self-compassion today?"
            )
            "advanced" -> listOf(
                "Explore the root causes of your current emotional state",
                "Analyze how your past experiences influence your present reactions",
                "Examine the relationship between your thoughts, emotions, and behaviors",
                "Reflect on how you can integrate your insights into meaningful change"
            )
            else -> generateMoodBasedPrompts(5, "general")
        }
    }

    suspend fun generatePromptsByLength(length: String): List<String> {
        return when (length) {
            "short" -> listOf(
                "How are you?",
                "What's on your mind?",
                "What are you grateful for?",
                "How can you take care of yourself?"
            )
            "medium" -> listOf(
                "What emotions are you experiencing right now and what might be causing them?",
                "Describe a recent situation that triggered strong emotions and how you handled it",
                "What are three things you're grateful for today and why?",
                "How can you practice self-compassion and kindness toward yourself?"
            )
            "long" -> listOf(
                "Explore the root causes of your current emotional state, considering both internal and external factors",
                "Describe a recent challenge in detail, including your initial reaction, the steps you took to address it, and what you learned from the experience",
                "Reflect on your personal growth over the past month, identifying specific changes, insights, and areas for continued development",
                "Analyze the relationship between your thoughts, emotions, and behaviors in a recent situation, and consider how you might approach similar circumstances differently in the future"
            )
            else -> generateMoodBasedPrompts(5, "general")
        }
    }

    suspend fun generateContextAwarePrompts(context: Map<String, String>): List<String> {
        val recentEvents = context["recent_events"] ?: ""
        val currentMood = context["current_mood"] ?: ""
        val timeOfDay = context["time_of_day"] ?: ""
        
        return listOf(
            "How are you feeling about $recentEvents?",
            "What's contributing to your $currentMood mood?",
            "What would help you feel better this $timeOfDay?",
            "How can you take care of yourself right now?"
        )
    }

    suspend fun adaptPromptsBasedOnRecentEntries(userId: String, recentEntries: List<JournalEntry>): List<String> {
        val recentTopics = recentEntries.flatMap { it.tags }.distinct()
        
        return when {
            recentTopics.contains("work") -> listOf(
                "How is work affecting your overall well-being?",
                "What work-related stressors can you address?",
                "What aspects of your job bring you satisfaction?",
                "How can you create better work-life balance?"
            )
            recentTopics.contains("stress") -> listOf(
                "What stress management techniques have you tried?",
                "What are your main sources of stress right now?",
                "How can you reduce stress in your daily life?",
                "What activities help you feel more relaxed?"
            )
            else -> generateMoodBasedPrompts(5, "general")
        }
    }

    suspend fun generateSeasonalPrompts(season: String, mood: Int): List<String> {
        return when (season) {
            "winter" -> listOf(
                "How does the winter season affect your mood?",
                "What winter activities bring you joy?",
                "How do you stay positive during the darker months?",
                "What are you looking forward to in the spring?"
            )
            "spring" -> listOf(
                "What new beginnings are you excited about?",
                "How does the spring season inspire you?",
                "What growth do you want to focus on?",
                "What are you planting for your future?"
            )
            "summer" -> listOf(
                "What summer activities energize you?",
                "How do you make the most of the longer days?",
                "What adventures are you planning?",
                "What brings you joy in the summer?"
            )
            "fall" -> listOf(
                "What are you grateful for as the year winds down?",
                "How do you embrace change and transition?",
                "What lessons have you learned this year?",
                "What are you looking forward to in the coming months?"
            )
            else -> generateMoodBasedPrompts(mood, "general")
        }
    }

    suspend fun generateHolidaySpecificPrompts(holiday: String, mood: Int): List<String> {
        return when (holiday) {
            "Christmas" -> listOf(
                "What does Christmas mean to you?",
                "What holiday traditions bring you joy?",
                "How do you handle holiday stress?",
                "What are you grateful for this holiday season?"
            )
            "New Year" -> listOf(
                "What are your hopes for the new year?",
                "What would you like to leave behind?",
                "What new habits do you want to develop?",
                "How do you want to grow in the coming year?"
            )
            else -> generateMoodBasedPrompts(mood, "general")
        }
    }

    suspend fun validatePromptContent(prompt: String): Boolean {
        // Basic validation - check for inappropriate content
        val inappropriateWords = listOf("harmful", "dangerous", "inappropriate")
        return !inappropriateWords.any { prompt.lowercase().contains(it) }
    }

    suspend fun filterInappropriatePrompts(prompts: List<String>): List<String> {
        return prompts.filter { validatePromptContent(it) }
    }

    suspend fun integrateWithAIRecommendations(userId: String, aiRecommendation: String): List<String> {
        return when {
            aiRecommendation.contains("anxiety") -> listOf(
                "What anxiety management techniques have worked for you?",
                "Describe a time when you successfully managed your anxiety",
                "What triggers your anxiety and how can you address them?",
                "Write about your anxiety as if you're explaining it to a friend"
            )
            aiRecommendation.contains("depression") -> listOf(
                "What small things bring you joy even on difficult days?",
                "Describe your support system and how they help you",
                "What activities help you feel more like yourself?",
                "Write about a time when you felt hopeful"
            )
            else -> generateMoodBasedPrompts(5, "general")
        }
    }

    suspend fun adaptPromptsBasedOnTherapyProgress(userId: String, therapyProgress: Map<String, Any>): List<String> {
        val sessionsCompleted = therapyProgress["sessions_completed"] as? Int ?: 0
        val currentFocus = therapyProgress["current_focus"] as? String ?: ""
        
        return when {
            sessionsCompleted < 3 -> listOf(
                "What brings you to therapy?",
                "What would you like to work on?",
                "How are you feeling about starting therapy?",
                "What support do you need right now?"
            )
            currentFocus.contains("anxiety") -> listOf(
                "What anxiety management techniques have you learned?",
                "How can you apply your therapy skills in daily life?",
                "What progress have you made in managing anxiety?",
                "What challenges are you still facing?"
            )
            else -> generateMoodBasedPrompts(5, "general")
        }
    }

    suspend fun generatePromptsForTherapyTechnique(technique: String, focus: String): List<String> {
        return when (technique) {
            "CBT" -> listOf(
                "What negative thoughts are you having about $focus?",
                "What evidence supports or contradicts these thoughts?",
                "How would you advise a friend in this situation?",
                "What's a more balanced way to think about $focus?"
            )
            "DBT" -> listOf(
                "What emotions are you experiencing related to $focus?",
                "What DBT skills can you use to manage these emotions?",
                "How can you practice mindfulness with $focus?",
                "What would be most effective for you right now?"
            )
            else -> listOf(
                "What's on your mind about $focus?",
                "How are you feeling about $focus?",
                "What would you like to work on with $focus?",
                "What support do you need with $focus?"
            )
        }
    }
}
