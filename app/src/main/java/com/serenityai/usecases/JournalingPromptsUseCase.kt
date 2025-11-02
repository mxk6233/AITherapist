package com.serenityai.usecases

import com.serenityai.data.models.*
import java.util.Date
import kotlin.random.Random

/**
 * UC32: AI-Generated Journaling Prompts - Use Case
 * 
 * Use Case Goal: Generate personalized journaling prompts using AI based on user mood,
 * preferences, and journaling history to encourage meaningful reflection.
 * 
 * Responsibilities:
 * - Generate AI-powered journaling prompts
 * - Personalize prompts based on user mood and patterns
 * - Filter prompts by category, mood, and difficulty
 * - Track prompt usage and effectiveness
 * - Provide recommendations based on user history
 */
class JournalingPromptsUseCase {
    
    /**
     * Generates personalized journaling prompts based on user request
     * 
     * @param request Prompt generation request with user preferences
     * @param recentMoodEntries Recent mood entries for personalization
     * @param journalingHistory User's journaling history for pattern analysis
     * @return List of personalized JournalingPrompt objects
     */
    fun generatePersonalizedPrompts(
        request: PromptGenerationRequest,
        recentMoodEntries: List<MoodEntry> = emptyList(),
        journalingHistory: List<JournalEntry> = emptyList()
    ): List<JournalingPrompt> {
        val prompts = mutableListOf<JournalingPrompt>()
        
        // Get base prompts from library
        val basePrompts = getPromptLibrary()
        
        // Filter and personalize
        val filteredPrompts = basePrompts.filter { prompt ->
            val matchesCategory = request.preferredCategory == null || 
                prompt.category == request.preferredCategory || 
                request.preferredCategory == PromptCategory.ALL
            
            val matchesDifficulty = request.preferredDifficulty == null || 
                prompt.difficulty == request.preferredDifficulty
            
            val matchesTime = request.maxTimeMinutes == null || 
                prompt.estimatedTime <= request.maxTimeMinutes
            
            val matchesMood = request.moodTags.isEmpty() || 
                request.moodTags.any { it in prompt.moodTags } ||
                prompt.moodTags.contains(MoodTag.ALL)
            
            val notUsed = !request.excludeUsedPrompts || prompt.timesUsed == 0
            
            matchesCategory && matchesDifficulty && matchesTime && matchesMood && notUsed
        }
        
        // Personalize prompts based on user data
        filteredPrompts.forEach { prompt ->
            val personalizedPrompt = personalizePrompt(
                prompt = prompt,
                userId = request.userId,
                currentMood = request.currentMood,
                recentMoodEntries = recentMoodEntries,
                journalingHistory = journalingHistory,
                customTheme = request.customTheme
            )
            prompts.add(personalizedPrompt)
        }
        
        // Sort by personalization score and relevance
        return prompts.sortedByDescending { 
            it.effectivenessScore + calculateRelevanceScore(it, request)
        }.take(20) // Return top 20
    }
    
    /**
     * Gets recommended prompts for the user
     * 
     * @param userId User ID
     * @param currentMood Current mood (1-10)
     * @param recentMoodEntries Recent mood entries
     * @param journalingHistory Journaling history
     * @return PromptRecommendation with top prompts and reasons
     */
    fun getRecommendedPrompts(
        userId: String,
        currentMood: Int? = null,
        recentMoodEntries: List<MoodEntry> = emptyList(),
        journalingHistory: List<JournalEntry> = emptyList()
    ): PromptRecommendation {
        val request = PromptGenerationRequest(
            userId = userId,
            currentMood = currentMood,
            moodTags = extractMoodTags(currentMood, recentMoodEntries),
            excludeUsedPrompts = false
        )
        
        val prompts = generatePersonalizedPrompts(
            request = request,
            recentMoodEntries = recentMoodEntries,
            journalingHistory = journalingHistory
        )
        
        val recommended = prompts.take(5)
        val personalizationScore = recommended.map { it.effectivenessScore }.average().toFloat()
        val reasons = generateRecommendationReasons(recommended, currentMood, recentMoodEntries)
        
        return PromptRecommendation(
            userId = userId,
            recommendedPrompts = recommended,
            reasons = reasons,
            personalizationScore = personalizationScore,
            generatedAt = Date()
        )
    }
    
    /**
     * Filters prompts by category and mood
     * 
     * @param prompts List of prompts to filter
     * @param category Category filter (null = all)
     * @param moodTags Mood tags filter (empty = all)
     * @return Filtered list of prompts
     */
    fun filterPrompts(
        prompts: List<JournalingPrompt>,
        category: PromptCategory? = null,
        moodTags: List<MoodTag> = emptyList()
    ): List<JournalingPrompt> {
        return prompts.filter { prompt ->
            val matchesCategory = category == null || 
                prompt.category == category || 
                category == PromptCategory.ALL
            
            val matchesMood = moodTags.isEmpty() || 
                moodTags.any { it in prompt.moodTags } ||
                prompt.moodTags.contains(MoodTag.ALL)
            
            matchesCategory && matchesMood
        }
    }
    
    /**
     * Tracks prompt usage and updates effectiveness
     * 
     * @param promptId Prompt ID that was used
     * @param journalEntry Created journal entry
     * @return Updated prompt with new usage count
     */
    fun trackPromptUsage(
        promptId: String,
        journalEntry: JournalEntry
    ): JournalingPrompt {
        // In production, would fetch from repository and update
        // For now, create updated version
        val basePrompt = getPromptLibrary().find { it.id == promptId }
            ?: throw IllegalArgumentException("Prompt not found: $promptId")
        
        // Calculate effectiveness based on entry quality
        val effectivenessScore = calculateEffectivenessScore(journalEntry)
        
        return basePrompt.copy(
            timesUsed = basePrompt.timesUsed + 1,
            effectivenessScore = (basePrompt.effectivenessScore * basePrompt.timesUsed + effectivenessScore) / (basePrompt.timesUsed + 1)
        )
    }
    
    // Private helper methods
    
    private fun getPromptLibrary(): List<JournalingPrompt> {
        return listOf(
            JournalingPrompt(
                id = "1",
                title = "Daily Reflection",
                prompt = "What was the most meaningful moment of your day? How did it make you feel?",
                category = PromptCategory.REFLECTION,
                moodTags = listOf(MoodTag.ALL),
                difficulty = PromptDifficulty.EASY,
                estimatedTime = 8,
                isAIGenerated = true,
                timesUsed = 3
            ),
            JournalingPrompt(
                id = "2",
                title = "Gratitude Practice",
                prompt = "Write about three things you're grateful for today. What makes each one special?",
                category = PromptCategory.GRATITUDE,
                moodTags = listOf(MoodTag.HAPPY, MoodTag.GRATEFUL),
                difficulty = PromptDifficulty.EASY,
                estimatedTime = 5,
                isAIGenerated = true,
                timesUsed = 7
            ),
            JournalingPrompt(
                id = "3",
                title = "Growth Mindset",
                prompt = "Describe a challenge you faced recently. What did you learn from it? How did it help you grow?",
                category = PromptCategory.GROWTH,
                moodTags = listOf(MoodTag.MOTIVATED, MoodTag.STRESSED),
                difficulty = PromptDifficulty.MEDIUM,
                estimatedTime = 12,
                isAIGenerated = true,
                timesUsed = 2
            ),
            JournalingPrompt(
                id = "4",
                title = "Relationship Reflection",
                prompt = "Think about a recent interaction with someone important to you. What went well? What would you do differently?",
                category = PromptCategory.RELATIONSHIPS,
                moodTags = listOf(MoodTag.ALL),
                difficulty = PromptDifficulty.MEDIUM,
                estimatedTime = 10,
                isAIGenerated = true,
                timesUsed = 1
            ),
            JournalingPrompt(
                id = "5",
                title = "Goal Setting",
                prompt = "What is one goal you want to achieve this week? What steps will you take to make it happen?",
                category = PromptCategory.GOALS,
                moodTags = listOf(MoodTag.MOTIVATED, MoodTag.HAPPY),
                difficulty = PromptDifficulty.EASY,
                estimatedTime = 8,
                isAIGenerated = true,
                timesUsed = 4
            ),
            JournalingPrompt(
                id = "6",
                title = "Emotional Processing",
                prompt = "Describe a recent emotion you experienced. What triggered it? How did you respond? What would you do differently next time?",
                category = PromptCategory.EMOTIONAL_PROCESSING,
                moodTags = listOf(MoodTag.SAD, MoodTag.ANXIOUS, MoodTag.STRESSED),
                difficulty = PromptDifficulty.MEDIUM,
                estimatedTime = 13,
                isAIGenerated = true,
                timesUsed = 2
            ),
            JournalingPrompt(
                id = "7",
                title = "Morning Intention",
                prompt = "What intention do you want to set for today? How will you embody it?",
                category = PromptCategory.DAILY_CHECK_IN,
                moodTags = listOf(MoodTag.CALM, MoodTag.MOTIVATED),
                difficulty = PromptDifficulty.EASY,
                estimatedTime = 5,
                isAIGenerated = true,
                timesUsed = 5
            ),
            JournalingPrompt(
                id = "8",
                title = "Overcoming Obstacles",
                prompt = "Think about a recent obstacle. What strategies did you use to overcome it? What strengths did you discover?",
                category = PromptCategory.CHALLENGES,
                moodTags = listOf(MoodTag.STRESSED, MoodTag.OVERWHELMED),
                difficulty = PromptDifficulty.MEDIUM,
                estimatedTime = 10,
                isAIGenerated = true,
                timesUsed = 1
            ),
            JournalingPrompt(
                id = "9",
                title = "Self-Compassion",
                prompt = "Write a compassionate letter to yourself. What do you need to hear right now?",
                category = PromptCategory.EMOTIONAL_PROCESSING,
                moodTags = listOf(MoodTag.SAD, MoodTag.ANXIOUS, MoodTag.STRESSED),
                difficulty = PromptDifficulty.MEDIUM,
                estimatedTime = 12,
                isAIGenerated = true,
                timesUsed = 0
            ),
            JournalingPrompt(
                id = "10",
                title = "Future Self",
                prompt = "Write a letter to your future self. What advice would you give? What do you hope for?",
                category = PromptCategory.REFLECTION,
                moodTags = listOf(MoodTag.MOTIVATED, MoodTag.HAPPY),
                difficulty = PromptDifficulty.HARD,
                estimatedTime = 15,
                isAIGenerated = true,
                timesUsed = 1
            )
        )
    }
    
    private fun personalizePrompt(
        prompt: JournalingPrompt,
        userId: String,
        currentMood: Int?,
        recentMoodEntries: List<MoodEntry>,
        journalingHistory: List<JournalEntry>,
        customTheme: String?
    ): JournalingPrompt {
        val factors = mutableListOf<PersonalizationFactor>()
        var effectivenessScore = prompt.effectivenessScore
        
        // Factor 1: Current mood match
        if (currentMood != null) {
            val moodMatch = calculateMoodMatch(currentMood, prompt.moodTags)
            if (moodMatch > 0) {
                effectivenessScore += 0.3f * moodMatch
                factors.add(
                    PersonalizationFactor(
                        type = FactorType.RECENT_MOOD,
                        value = "Mood score: $currentMood",
                        weight = moodMatch
                    )
                )
            }
        }
        
        // Factor 2: Journaling history pattern
        if (journalingHistory.isNotEmpty()) {
            val categoryFrequency = journalingHistory
                .groupBy { it.type }
                .mapValues { it.value.size }
            val preferredCategory = categoryFrequency.maxByOrNull { it.value }?.key
            if (preferredCategory != null && matchesCategory(prompt.category, preferredCategory)) {
                effectivenessScore += 0.2f
                factors.add(
                    PersonalizationFactor(
                        type = FactorType.JOURNALING_HISTORY,
                        value = "You often write about ${preferredCategory.name}",
                        weight = 0.2f
                    )
                )
            }
        }
        
        // Factor 3: Recent mood pattern
        if (recentMoodEntries.size >= 3) {
            val averageMood = recentMoodEntries.map { it.mood.toFloat() }.average().toFloat()
            val moodTag = determineMoodTagFromValue(averageMood.toInt())
            if (moodTag in prompt.moodTags || prompt.moodTags.contains(MoodTag.ALL)) {
                effectivenessScore += 0.2f
                factors.add(
                    PersonalizationFactor(
                        type = FactorType.MOOD_PATTERN,
                        value = "Your recent average mood aligns with this prompt",
                        weight = 0.2f
                    )
                )
            }
        }
        
        // Factor 4: Custom theme match
        if (customTheme != null && prompt.prompt.contains(customTheme, ignoreCase = true)) {
            effectivenessScore += 0.3f
            factors.add(
                PersonalizationFactor(
                    type = FactorType.USER_PREFERENCE,
                    value = "Matches your requested theme: $customTheme",
                    weight = 0.3f
                )
            )
        }
        
        // Factor 5: Usage frequency (prefer moderately used prompts)
        when {
            prompt.timesUsed == 0 -> effectivenessScore += 0.1f // Try new prompts
            prompt.timesUsed in 1..5 -> effectivenessScore += 0.2f // Well-tested prompts
            else -> effectivenessScore -= 0.1f // Overused prompts
        }
        
        return prompt.copy(
            personalizedFor = userId,
            personalizationFactors = factors,
            effectivenessScore = effectivenessScore.coerceIn(0f, 1f)
        )
    }
    
    private fun calculateMoodMatch(mood: Int, moodTags: List<MoodTag>): Float {
        if (moodTags.contains(MoodTag.ALL)) return 1.0f
        
        val moodTag = determineMoodTagFromValue(mood)
        return if (moodTag in moodTags) 1.0f else 0.0f
    }
    
    private fun determineMoodTagFromValue(mood: Int): MoodTag {
        return when {
            mood >= 8 -> MoodTag.HAPPY
            mood >= 7 -> MoodTag.GRATEFUL
            mood >= 6 -> MoodTag.CALM
            mood >= 5 -> MoodTag.MOTIVATED
            mood >= 4 -> MoodTag.STRESSED
            mood >= 3 -> MoodTag.SAD
            mood >= 2 -> MoodTag.ANXIOUS
            else -> MoodTag.OVERWHELMED
        }
    }
    
    private fun matchesCategory(promptCategory: PromptCategory, journalType: JournalType): Boolean {
        return when (journalType) {
            JournalType.GRATITUDE -> promptCategory == PromptCategory.GRATITUDE
            JournalType.REFLECTION -> promptCategory == PromptCategory.REFLECTION
            JournalType.GOAL_REVIEW -> promptCategory == PromptCategory.GOALS
            JournalType.CHALLENGE_REFLECTION -> promptCategory == PromptCategory.CHALLENGES
            else -> false
        }
    }
    
    private fun extractMoodTags(currentMood: Int?, recentMoodEntries: List<MoodEntry>): List<MoodTag> {
        val tags = mutableListOf<MoodTag>()
        
        currentMood?.let {
            tags.add(determineMoodTagFromValue(it))
        }
        
        if (recentMoodEntries.isNotEmpty()) {
            val averageMood = recentMoodEntries.map { it.mood }.average().toInt()
            tags.add(determineMoodTagFromValue(averageMood))
        }
        
        return tags.distinct()
    }
    
    private fun calculateRelevanceScore(prompt: JournalingPrompt, request: PromptGenerationRequest): Float {
        var score = 0f
        
        if (request.preferredCategory != null && prompt.category == request.preferredCategory) {
            score += 0.3f
        }
        
        if (request.preferredDifficulty != null && prompt.difficulty == request.preferredDifficulty) {
            score += 0.2f
        }
        
        if (request.moodTags.isNotEmpty() && request.moodTags.any { it in prompt.moodTags }) {
            score += 0.3f
        }
        
        return score
    }
    
    private fun calculateEffectivenessScore(journalEntry: JournalEntry): Float {
        var score = 0.5f // Base score
        
        // Longer entries suggest more engagement
        if (journalEntry.content.length > 200) score += 0.2f
        if (journalEntry.content.length > 500) score += 0.1f
        
        // Entries with mood tags suggest emotional processing
        if (journalEntry.mood != null) score += 0.1f
        
        // Entries with multiple tags suggest depth
        if (journalEntry.tags.size > 2) score += 0.1f
        
        return score.coerceIn(0f, 1f)
    }
    
    private fun generateRecommendationReasons(
        prompts: List<JournalingPrompt>,
        currentMood: Int?,
        recentMoodEntries: List<MoodEntry>
    ): List<String> {
        val reasons = mutableListOf<String>()
        
        if (currentMood != null) {
            val moodTag = determineMoodTagFromValue(currentMood)
            reasons.add("Based on your current mood, we think you'd enjoy reflecting on ${moodTag.name.lowercase()} themes")
        }
        
        if (recentMoodEntries.isNotEmpty()) {
            reasons.add("These prompts align with your recent mood patterns")
        }
        
        if (prompts.any { it.timesUsed > 0 }) {
            reasons.add("Includes prompts you've found helpful before")
        }
        
        if (prompts.any { it.timesUsed == 0 }) {
            reasons.add("Includes new prompts to explore")
        }
        
        return reasons
    }
}

