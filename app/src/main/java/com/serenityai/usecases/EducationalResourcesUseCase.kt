package com.serenityai.usecases

import com.serenityai.data.models.*
import java.util.Date

/**
 * UC16: Access Educational Resources - Use Case
 * 
 * Use Case Goal: Enable users to access comprehensive educational resources about mental health,
 * therapy techniques, and self-care strategies so they can learn and apply evidence-based practices.
 * 
 * Responsibilities:
 * - Provide categorized educational content (articles, videos, guides)
 * - Personalize content recommendations based on user needs
 * - Track learning progress and completion
 * - Provide search and filtering capabilities
 * - Support multiple content formats (text, video, audio)
 */
class EducationalResourcesUseCase {
    
    /**
     * Retrieves educational resources based on category and user preferences
     * 
     * @param category Category of resources (e.g., "anxiety", "depression", "mindfulness")
     * @param userId User ID for personalization
     * @param format Content format preference (text, video, audio, all)
     * @return List of EducationalResource objects
     */
    fun getEducationalResources(
        category: String? = null,
        userId: String? = null,
        format: ContentFormat = ContentFormat.ALL
    ): List<EducationalResource> {
        val allResources = generateEducationalResources()
        
        // Filter by category
        val filteredByCategory = if (category != null) {
            allResources.filter { it.category.equals(category, ignoreCase = true) }
        } else {
            allResources
        }
        
        // Filter by format
        val filteredByFormat = when (format) {
            ContentFormat.TEXT -> filteredByCategory.filter { it.format == ContentFormat.TEXT }
            ContentFormat.VIDEO -> filteredByCategory.filter { it.format == ContentFormat.VIDEO }
            ContentFormat.AUDIO -> filteredByCategory.filter { it.format == ContentFormat.AUDIO }
            ContentFormat.ALL -> filteredByCategory
        }
        
        // Personalize based on user needs (if userId provided)
        val personalizedResources = if (userId != null) {
            personalizeResources(filteredByFormat, userId)
        } else {
            filteredByFormat
        }
        
        return personalizedResources.sortedByDescending { it.relevanceScore }
    }
    
    /**
     * Searches educational resources by keyword
     * 
     * @param query Search query string
     * @param userId User ID for personalization
     * @return List of matching EducationalResource objects
     */
    fun searchEducationalResources(
        query: String,
        userId: String? = null
    ): List<EducationalResource> {
        require(query.isNotBlank()) { "Search query cannot be empty" }
        
        val allResources = generateEducationalResources()
        val lowerQuery = query.lowercase()
        
        // Search in title, description, and tags
        val matchingResources = allResources.filter { resource ->
            resource.title.lowercase().contains(lowerQuery) ||
            resource.description.lowercase().contains(lowerQuery) ||
            resource.tags.any { it.lowercase().contains(lowerQuery) }
        }
        
        // Personalize results if userId provided
        return if (userId != null) {
            personalizeResources(matchingResources, userId)
        } else {
            matchingResources
        }.sortedByDescending { it.relevanceScore }
    }
    
    /**
     * Gets recommended resources for a user based on their profile and history
     * 
     * @param userId User ID
     * @param limit Maximum number of recommendations
     * @return List of recommended EducationalResource objects
     */
    fun getRecommendedResources(
        userId: String,
        limit: Int = 10
    ): List<EducationalResource> {
        val allResources = generateEducationalResources()
        
        // Personalize based on user profile
        val personalized = personalizeResources(allResources, userId)
        
        // Sort by relevance and return top N
        return personalized
            .sortedByDescending { it.relevanceScore }
            .take(limit)
    }
    
    /**
     * Marks a resource as completed by the user
     * 
     * @param resourceId Resource ID
     * @param userId User ID
     * @return LearningProgress object tracking completion
     */
    fun markResourceAsCompleted(
        resourceId: String,
        userId: String
    ): LearningProgress {
        return LearningProgress(
            id = System.currentTimeMillis().toString(),
            userId = userId,
            resourceId = resourceId,
            completedAt = Date(),
            progress = 100,
            timeSpent = estimateTimeSpent(resourceId),
            notes = null
        )
    }
    
    /**
     * Tracks learning progress for a resource
     * 
     * @param resourceId Resource ID
     * @param userId User ID
     * @param progress Progress percentage (0-100)
     * @return LearningProgress object
     */
    fun trackLearningProgress(
        resourceId: String,
        userId: String,
        progress: Int
    ): LearningProgress {
        require(progress in 0..100) { "Progress must be between 0 and 100" }
        
        return LearningProgress(
            id = System.currentTimeMillis().toString(),
            userId = userId,
            resourceId = resourceId,
            completedAt = if (progress == 100) Date() else null,
            progress = progress,
            timeSpent = estimateTimeSpent(resourceId) * (progress / 100f),
            notes = null
        )
    }
    
    /**
     * Gets user's learning history
     * 
     * @param userId User ID
     * @return List of LearningProgress objects
     */
    fun getLearningHistory(userId: String): List<LearningProgress> {
        // In production, would fetch from database
        return emptyList()
    }
    
    /**
     * Gets available resource categories
     * 
     * @return List of category names
     */
    fun getResourceCategories(): List<String> {
        return listOf(
            "Anxiety Management",
            "Depression Support",
            "Stress Relief",
            "Mindfulness",
            "Cognitive Behavioral Therapy",
            "Self-Care",
            "Coping Strategies",
            "Relationships",
            "Sleep",
            "Mindset & Growth"
        )
    }
    
    // Private helper methods
    
    private fun generateEducationalResources(): List<EducationalResource> {
        return listOf(
            EducationalResource(
                id = "res_001",
                title = "Understanding Anxiety: A Comprehensive Guide",
                description = "Learn about anxiety disorders, symptoms, and evidence-based treatment approaches.",
                category = "Anxiety Management",
                format = ContentFormat.TEXT,
                url = "https://example.com/anxiety-guide",
                duration = 15,
                tags = listOf("anxiety", "mental health", "education", "guide"),
                difficulty = DifficultyLevel.BEGINNER,
                relevanceScore = 85f,
                createdAt = Date()
            ),
            EducationalResource(
                id = "res_002",
                title = "Mindfulness Meditation for Beginners",
                description = "Step-by-step video guide to starting your mindfulness practice.",
                category = "Mindfulness",
                format = ContentFormat.VIDEO,
                url = "https://example.com/mindfulness-video",
                duration = 20,
                tags = listOf("mindfulness", "meditation", "video", "beginners"),
                difficulty = DifficultyLevel.BEGINNER,
                relevanceScore = 90f,
                createdAt = Date()
            ),
            EducationalResource(
                id = "res_003",
                title = "CBT Techniques for Negative Thoughts",
                description = "Learn cognitive behavioral therapy techniques to challenge and reframe negative thinking patterns.",
                category = "Cognitive Behavioral Therapy",
                format = ContentFormat.TEXT,
                url = "https://example.com/cbt-techniques",
                duration = 25,
                tags = listOf("CBT", "cognitive therapy", "negative thoughts", "reframing"),
                difficulty = DifficultyLevel.INTERMEDIATE,
                relevanceScore = 80f,
                createdAt = Date()
            ),
            EducationalResource(
                id = "res_004",
                title = "Guided Breathing Exercise",
                description = "Audio-guided breathing exercise for stress relief and relaxation.",
                category = "Stress Relief",
                format = ContentFormat.AUDIO,
                url = "https://example.com/breathing-audio",
                duration = 10,
                tags = listOf("breathing", "stress relief", "relaxation", "audio"),
                difficulty = DifficultyLevel.BEGINNER,
                relevanceScore = 88f,
                createdAt = Date()
            ),
            EducationalResource(
                id = "res_005",
                title = "Building a Self-Care Routine",
                description = "Practical guide to creating and maintaining a sustainable self-care practice.",
                category = "Self-Care",
                format = ContentFormat.TEXT,
                url = "https://example.com/self-care",
                duration = 12,
                tags = listOf("self-care", "routine", "wellness", "habits"),
                difficulty = DifficultyLevel.BEGINNER,
                relevanceScore = 75f,
                createdAt = Date()
            )
        )
    }
    
    private fun personalizeResources(
        resources: List<EducationalResource>,
        userId: String
    ): List<EducationalResource> {
        // In production, would analyze user profile, mood history, preferences
        // For now, return resources with adjusted relevance scores
        return resources.map { resource ->
            resource.copy(relevanceScore = resource.relevanceScore * 1.1f) // Slight boost for personalization
        }
    }
    
    private fun estimateTimeSpent(resourceId: String): Float {
        // In production, would calculate based on resource duration and format
        return when {
            resourceId.contains("video") -> 1200f // 20 minutes in seconds
            resourceId.contains("audio") -> 600f // 10 minutes
            else -> 900f // 15 minutes for text
        }
    }
}

/**
 * Data class for educational resources
 */
data class EducationalResource(
    val id: String,
    val title: String,
    val description: String,
    val category: String,
    val format: ContentFormat,
    val url: String,
    val duration: Int, // in minutes
    val tags: List<String>,
    val difficulty: DifficultyLevel,
    val relevanceScore: Float, // 0-100
    val createdAt: Date
)

/**
 * Data class for learning progress
 */
data class LearningProgress(
    val id: String,
    val userId: String,
    val resourceId: String,
    val completedAt: Date?,
    val progress: Int, // 0-100
    val timeSpent: Float, // in seconds
    val notes: String?
)

/**
 * Content format enum
 */
enum class ContentFormat {
    TEXT, VIDEO, AUDIO, ALL
}

/**
 * Difficulty level enum
 */
enum class DifficultyLevel {
    BEGINNER, INTERMEDIATE, ADVANCED
}

