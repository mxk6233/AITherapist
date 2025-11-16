package com.serenityai.usecases

import com.serenityai.data.models.*
import com.serenityai.data.remote.FirebaseSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Date

/** UC16: Educational resource library with search and filtering capabilities that provides access to mental health educational content from Firebase. */
class EducationalResourcesUseCase(
    private val firebaseSource: FirebaseSource = FirebaseSource()
) {
    
    /**
     * Retrieves educational resources based on category and user preferences
     * 
     * @param category Category of resources (e.g., "anxiety", "depression", "mindfulness")
     * @param userId User ID for personalization
     * @param format Content format preference (text, video, audio, all)
     * @return List of EducationalResource objects
     */
    suspend fun getEducationalResources(
        category: String? = null,
        userId: String? = null,
        format: ContentFormat = ContentFormat.ALL
    ): List<EducationalResource> = withContext(Dispatchers.IO) {
        try {
            // Try to fetch from Firebase
            val formatString = if (format != ContentFormat.ALL) format.name else null
            val result = firebaseSource.getEducationalResources(category, formatString)
            
            // Log Firebase connection status
            if (result.isSuccess) {
                val firebaseResources = result.getOrNull() ?: emptyList()
                android.util.Log.d("EducationalResources", "Firebase connection: SUCCESS - Found ${firebaseResources.size} resources")
                if (firebaseResources.isEmpty()) {
                    android.util.Log.d("EducationalResources", "Firebase collection is empty, using fallback data")
                }
            } else {
                android.util.Log.w("EducationalResources", "Firebase connection: FAILED - ${result.exceptionOrNull()?.message}")
                android.util.Log.d("EducationalResources", "Using fallback hardcoded data")
            }
            
            // Use Firebase data if available and not empty, otherwise use fallback
            val allResources = result.getOrNull()?.takeIf { it.isNotEmpty() } 
                ?: generateEducationalResources()
            
            // Filter by category (if not already filtered by Firebase)
            val filteredByCategory = if (category != null && (result.isFailure || result.getOrNull()?.isEmpty() == true)) {
                allResources.filter { it.category.equals(category, ignoreCase = true) }
            } else {
                allResources
            }
            
            // Filter by format (if not already filtered by Firebase)
            val filteredByFormat = if (format != ContentFormat.ALL && (result.isFailure || result.getOrNull()?.isEmpty() == true)) {
                when (format) {
                    ContentFormat.TEXT -> filteredByCategory.filter { it.format == ContentFormat.TEXT }
                    ContentFormat.VIDEO -> filteredByCategory.filter { it.format == ContentFormat.VIDEO }
                    ContentFormat.AUDIO -> filteredByCategory.filter { it.format == ContentFormat.AUDIO }
                    ContentFormat.ALL -> filteredByCategory
                }
            } else {
                filteredByCategory
            }
            
            // Personalize based on user needs (if userId provided)
            val personalizedResources = if (userId != null) {
                personalizeResources(filteredByFormat, userId)
            } else {
                filteredByFormat
            }
            
            personalizedResources.sortedByDescending { it.relevanceScore }
        } catch (e: Exception) {
            // Fallback to hardcoded data on any error
            val allResources = generateEducationalResources()
            val filteredByCategory = if (category != null) {
                allResources.filter { it.category.equals(category, ignoreCase = true) }
            } else {
                allResources
            }
            
            val filteredByFormat = when (format) {
                ContentFormat.TEXT -> filteredByCategory.filter { it.format == ContentFormat.TEXT }
                ContentFormat.VIDEO -> filteredByCategory.filter { it.format == ContentFormat.VIDEO }
                ContentFormat.AUDIO -> filteredByCategory.filter { it.format == ContentFormat.AUDIO }
                ContentFormat.ALL -> filteredByCategory
            }
            
            if (userId != null) {
                personalizeResources(filteredByFormat, userId).sortedByDescending { it.relevanceScore }
            } else {
                filteredByFormat.sortedByDescending { it.relevanceScore }
            }
        }
    }
    
    /**
     * Searches educational resources by keyword
     * 
     * @param query Search query string
     * @param userId User ID for personalization
     * @return List of matching EducationalResource objects
     */
    suspend fun searchEducationalResources(
        query: String,
        userId: String? = null
    ): List<EducationalResource> = withContext(Dispatchers.IO) {
        require(query.isNotBlank()) { "Search query cannot be empty" }
        
        try {
            // Try to search in Firebase
            val result = firebaseSource.searchEducationalResources(query)
            // Use Firebase results if available and not empty, otherwise search fallback data
            val matchingResources = result.getOrNull()?.takeIf { it.isNotEmpty() } ?: run {
                val allResources = generateEducationalResources()
                val lowerQuery = query.lowercase()
                allResources.filter { resource ->
                    resource.title.lowercase().contains(lowerQuery) ||
                    resource.description.lowercase().contains(lowerQuery) ||
                    resource.tags.any { it.lowercase().contains(lowerQuery) }
                }
            }
            
            // Personalize results if userId provided
            if (userId != null) {
                personalizeResources(matchingResources, userId).sortedByDescending { it.relevanceScore }
            } else {
                matchingResources.sortedByDescending { it.relevanceScore }
            }
        } catch (e: Exception) {
            // Fallback to hardcoded data search on any error
            val allResources = generateEducationalResources()
            val lowerQuery = query.lowercase()
            val matchingResources = allResources.filter { resource ->
                resource.title.lowercase().contains(lowerQuery) ||
                resource.description.lowercase().contains(lowerQuery) ||
                resource.tags.any { it.lowercase().contains(lowerQuery) }
            }
            
            if (userId != null) {
                personalizeResources(matchingResources, userId).sortedByDescending { it.relevanceScore }
            } else {
                matchingResources.sortedByDescending { it.relevanceScore }
            }
        }
    }
    
    /**
     * Gets recommended resources for a user based on their profile and history
     * 
     * @param userId User ID
     * @param limit Maximum number of recommendations
     * @return List of recommended EducationalResource objects
     */
    suspend fun getRecommendedResources(
        userId: String,
        limit: Int = 10
    ): List<EducationalResource> = withContext(Dispatchers.IO) {
        try {
            // Try to fetch from Firebase
            val result = firebaseSource.getEducationalResources()
            val allResources = result.getOrElse {
                // Fallback to hardcoded data if Firebase fails
                generateEducationalResources()
            }
            
            // Personalize based on user profile
            val personalized = personalizeResources(allResources, userId)
            
            // Sort by relevance and return top N
            personalized
                .sortedByDescending { it.relevanceScore }
                .take(limit)
        } catch (e: Exception) {
            // Fallback to hardcoded data on any error
            val allResources = generateEducationalResources()
            val personalized = personalizeResources(allResources, userId)
            personalized
                .sortedByDescending { it.relevanceScore }
                .take(limit)
        }
    }
    
    /**
     * Marks a resource as completed by the user
     * 
     * @param resourceId Resource ID
     * @param userId User ID
     * @return LearningProgress object tracking completion
     */
    suspend fun markResourceAsCompleted(
        resourceId: String,
        userId: String
    ): LearningProgress = withContext(Dispatchers.IO) {
        val progress = LearningProgress(
            id = System.currentTimeMillis().toString(),
            userId = userId,
            resourceId = resourceId,
            completedAt = Date(),
            progress = 100,
            timeSpent = estimateTimeSpent(resourceId),
            notes = null
        )
        
        // Save to Firebase
        firebaseSource.saveLearningProgress(progress).getOrElse {
            // If Firebase save fails, still return the progress object
            progress
        }
    }
    
    /**
     * Tracks learning progress for a resource
     * 
     * @param resourceId Resource ID
     * @param userId User ID
     * @param progress Progress percentage (0-100)
     * @return LearningProgress object
     */
    suspend fun trackLearningProgress(
        resourceId: String,
        userId: String,
        progress: Int
    ): LearningProgress = withContext(Dispatchers.IO) {
        require(progress in 0..100) { "Progress must be between 0 and 100" }
        
        // Check if progress already exists
        val existingProgressResult = firebaseSource.getLearningProgress(userId, resourceId)
        val existingProgress = existingProgressResult.getOrNull()
        
        val progressObj = if (existingProgress != null) {
            // Update existing progress
            existingProgress.copy(
                progress = progress,
                completedAt = if (progress == 100) Date() else existingProgress.completedAt,
                timeSpent = estimateTimeSpent(resourceId) * (progress / 100f)
            )
        } else {
            // Create new progress
            LearningProgress(
                id = "",
                userId = userId,
                resourceId = resourceId,
                completedAt = if (progress == 100) Date() else null,
                progress = progress,
                timeSpent = estimateTimeSpent(resourceId) * (progress / 100f),
                notes = null
            )
        }
        
        // Save to Firebase
        firebaseSource.saveLearningProgress(progressObj).getOrElse {
            // If Firebase save fails, still return the progress object
            progressObj
        }
    }
    
    /**
     * Gets user's learning history
     * 
     * @param userId User ID
     * @return List of LearningProgress objects
     */
    suspend fun getLearningHistory(userId: String): List<LearningProgress> = withContext(Dispatchers.IO) {
        try {
            firebaseSource.getLearningHistory(userId).getOrElse {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    /**
     * Gets available resource categories
     * 
     * @return List of category names
     */
    suspend fun getResourceCategories(): List<String> = withContext(Dispatchers.IO) {
        try {
            // Try to fetch categories from Firebase
            firebaseSource.getResourceCategories().getOrElse {
                // Fallback to hardcoded categories if Firebase fails
                listOf(
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
        } catch (e: Exception) {
            // Fallback to hardcoded categories on any error
            listOf(
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
    }
    
    // Private helper methods
    
    /**
     * Builds the in-memory catalog of educational resources used for testing and preview scenarios.
     *
     * @return Seeded list of `EducationalResource` entries spanning multiple categories and formats.
     */
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
    
    /**
     * Applies lightweight personalization to the supplied resources for the given user.
     *
     * @param resources Candidate resources before personalization.
     * @param userId Identifier for the user requesting content.
     * @return List of resources with adjusted relevance scores for the user.
     */
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
    
    /**
     * Estimates the amount of time a user typically spends on a resource based on its format.
     *
     * @param resourceId Identifier for the resource whose engagement time is being estimated.
     * @return Estimated consumption time in seconds.
     */
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

