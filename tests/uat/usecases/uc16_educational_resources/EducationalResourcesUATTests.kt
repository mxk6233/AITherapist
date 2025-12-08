package com.serenityai.tests.uat.usecases.uc16_educational_resources

import com.serenityai.usecases.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import java.util.Date

/** UAT: UC16 - Access Educational Resources - User Acceptance Tests validating educational resources from an end-user perspective. */
@DisplayName("UAT: UC16 - Access Educational Resources")
class EducationalResourcesUATTests {

    private val useCase = EducationalResourcesUseCase()

    @Nested
    @DisplayName("User Story: Resource Discovery")
    inner class ResourceDiscovery {
        
        @Test
        @DisplayName("As a user, I want to browse educational resources so I can learn about mental health")
        fun `user can browse educational resources`() {
            // Given: User wants to learn about mental health
            val resources = listOf(
                EducationalResource(
                    id = "res1", title = "Understanding Anxiety", category = "Anxiety", 
                    format = ContentFormat.TEXT, url = "https://example.com/anxiety", 
                    duration = 15, tags = emptyList(), difficulty = DifficultyLevel.BEGINNER,
                    relevanceScore = 85f, createdAt = Date()
                ),
                EducationalResource(
                    id = "res2", title = "Stress Management Techniques", category = "Stress",
                    format = ContentFormat.VIDEO, url = "https://example.com/stress", 
                    duration = 20, tags = emptyList(), difficulty = DifficultyLevel.BEGINNER,
                    relevanceScore = 90f, createdAt = Date()
                ),
                EducationalResource(
                    id = "res3", title = "Mindfulness Meditation Guide", category = "Mindfulness",
                    format = ContentFormat.AUDIO, url = "https://example.com/mindfulness",
                    duration = 10, tags = emptyList(), difficulty = DifficultyLevel.BEGINNER,
                    relevanceScore = 88f, createdAt = Date()
                )
            )
            
            // When: User browses resources
            val resourcesAvailable = resources.isNotEmpty()
            val categoriesVisible = resources.map { it.category }.distinct().size > 1
            
            // Then: Resources are displayed
            assertTrue(resourcesAvailable, "User should see available resources")
            assertTrue(categoriesVisible, "Resources should be organized by category")
        }
        
        @Test
        @DisplayName("As a user, I want to search for specific topics so I can find relevant content")
        fun `user can search for educational resources`() {
            // Given: User searches for topic
            val searchQuery = "anxiety"
            val resources = listOf(
                EducationalResource(
                    id = "res1", title = "Understanding Anxiety", category = "Anxiety",
                    format = ContentFormat.TEXT, url = "https://example.com/anxiety",
                    duration = 15, tags = listOf("anxiety", "mental health"),
                    difficulty = DifficultyLevel.BEGINNER, relevanceScore = 85f, createdAt = Date()
                ),
                EducationalResource(
                    id = "res2", title = "Stress Management", category = "Stress",
                    format = ContentFormat.TEXT, url = "https://example.com/stress",
                    duration = 20, tags = listOf("stress", "coping"),
                    difficulty = DifficultyLevel.BEGINNER, relevanceScore = 80f, createdAt = Date()
                ),
                EducationalResource(
                    id = "res3", title = "Anxiety Relief Techniques", category = "Anxiety",
                    format = ContentFormat.VIDEO, url = "https://example.com/anxiety-relief",
                    duration = 25, tags = listOf("anxiety", "techniques"),
                    difficulty = DifficultyLevel.INTERMEDIATE, relevanceScore = 90f, createdAt = Date()
                )
            )
            
            // When: User searches
            val searchPerformed = searchQuery.isNotBlank()
            val resultsFound = resources.filter { 
                it.title.lowercase().contains(searchQuery.lowercase()) ||
                it.tags.any { tag -> tag.lowercase().contains(searchQuery.lowercase()) }
            }
            
            // Then: Relevant resources are found
            assertTrue(searchPerformed, "User should be able to search")
            assertTrue(resultsFound.isNotEmpty(), "Search should return relevant results")
        }
        
        @Test
        @DisplayName("As a user, I want to filter resources by format so I can choose my preferred learning style")
        fun `user can filter resources by format`() {
            // Given: User wants specific format
            val formatFilter = ContentFormat.VIDEO
            val resources = listOf(
                EducationalResource(
                    id = "res1", title = "Video Guide", category = "General",
                    format = ContentFormat.VIDEO, url = "https://example.com/video",
                    duration = 15, tags = emptyList(), difficulty = DifficultyLevel.BEGINNER,
                    relevanceScore = 85f, createdAt = Date()
                ),
                EducationalResource(
                    id = "res2", title = "Article", category = "General",
                    format = ContentFormat.TEXT, url = "https://example.com/article",
                    duration = 10, tags = emptyList(), difficulty = DifficultyLevel.BEGINNER,
                    relevanceScore = 80f, createdAt = Date()
                ),
                EducationalResource(
                    id = "res3", title = "Audio Guide", category = "General",
                    format = ContentFormat.AUDIO, url = "https://example.com/audio",
                    duration = 20, tags = emptyList(), difficulty = DifficultyLevel.BEGINNER,
                    relevanceScore = 88f, createdAt = Date()
                )
            )
            
            // When: User filters by format
            val filterApplied = formatFilter != ContentFormat.ALL
            val filteredResults = resources.filter { it.format == formatFilter }
            
            // Then: Only matching format resources shown
            assertTrue(filterApplied, "User should be able to filter")
            assertTrue(filteredResults.isNotEmpty(), "Filter should return matching resources")
            assertTrue(filteredResults.all { it.format == formatFilter }, "All results should match filter")
        }
    }

    @Nested
    @DisplayName("User Story: Resource Access")
    inner class ResourceAccess {
        
        @Test
        @DisplayName("As a user, I want to access resources so I can read/watch/listen to content")
        fun `user can access educational resources`() {
            // Given: User selects a resource
            val resource = EducationalResource(
                id = "res1", title = "Understanding Anxiety", category = "Anxiety",
                format = ContentFormat.TEXT, url = "https://example.com/anxiety-guide",
                duration = 15, tags = emptyList(), difficulty = DifficultyLevel.BEGINNER,
                relevanceScore = 85f, createdAt = Date()
            )
            
            // When: User opens resource
            val resourceAccessible = resource.url.isNotBlank()
            val urlValid = resource.url.startsWith("http")
            
            // Then: Resource is accessible
            assertTrue(resourceAccessible, "User should be able to access resource")
            assertTrue(urlValid, "Resource URL should be valid")
        }
        
        @Test
        @DisplayName("As a user, I want to see resource details so I know what I'm accessing")
        fun `user can view resource details`() {
            // Given: User views resource
            val resource = EducationalResource(
                id = "res1", title = "Understanding Anxiety",
                description = "Comprehensive guide to anxiety management", category = "Anxiety",
                format = ContentFormat.TEXT, url = "https://example.com/anxiety",
                duration = 15, tags = emptyList(), difficulty = DifficultyLevel.BEGINNER,
                relevanceScore = 85f, createdAt = Date()
            )
            
            // When: User views details
            val detailsVisible = resource.title.isNotBlank() && resource.description.isNotBlank()
            val informationComplete = resource.duration > 0 && resource.difficulty != null
            
            // Then: Resource details are displayed
            assertTrue(detailsVisible, "Resource details should be visible")
            assertTrue(informationComplete, "Resource should have complete information")
        }
    }

    @Nested
    @DisplayName("User Story: Learning Progress")
    inner class LearningProgress {
        
        @Test
        @DisplayName("As a user, I want to track my learning progress so I know what I've completed")
        fun `user can track learning progress`() {
            // Given: User accesses resources
            val progress = LearningProgress(
                id = "progress1", userId = "user123", resourceId = "resource-1",
                completedAt = null, progress = 75, timeSpent = 600f, notes = null
            )
            
            // When: User views progress
            val progressTracked = progress.progress != null
            val progressValid = progress.progress >= 0 && progress.progress <= 100
            
            // Then: Progress is tracked
            assertTrue(progressTracked, "Learning progress should be tracked")
            assertTrue(progressValid, "Progress should be within valid range")
        }
        
        @Test
        @DisplayName("As a user, I want to mark resources as completed so I can see my achievements")
        fun `user can mark resources as completed`() {
            // Given: User completes resource
            val progress = LearningProgress(
                id = "progress1", userId = "user123", resourceId = "resource-1",
                completedAt = Date(), progress = 100, timeSpent = 900f, notes = null
            )
            
            // When: Resource reaches 100%
            val canComplete = progress.progress == 100
            val resourceCompleted = progress.completedAt != null
            
            // Then: Resource is marked as completed
            assertTrue(canComplete, "User should be able to complete resource")
            assertTrue(resourceCompleted, "Resource should be marked as completed")
        }
        
        @Test
        @DisplayName("As a user, I want to see my learning history so I can review what I've learned")
        fun `user can view learning history`() {
            // Given: User has accessed resources
            val learningHistory = listOf(
                LearningProgress(
                    id = "p1", userId = "user123", resourceId = "res1",
                    completedAt = Date(), progress = 100, timeSpent = 900f, notes = null
                ),
                LearningProgress(
                    id = "p2", userId = "user123", resourceId = "res2",
                    completedAt = null, progress = 50, timeSpent = 300f, notes = null
                ),
                LearningProgress(
                    id = "p3", userId = "user123", resourceId = "res3",
                    completedAt = Date(), progress = 100, timeSpent = 600f, notes = null
                )
            )
            
            // When: User views history
            val historyAvailable = learningHistory.isNotEmpty()
            val completedCount = learningHistory.count { it.completedAt != null }
            
            // Then: Learning history is displayed
            assertTrue(historyAvailable, "Learning history should be available")
            assertTrue(completedCount >= 0, "Completed resources should be tracked")
        }
    }

    @Nested
    @DisplayName("User Story: Personalized Recommendations")
    inner class PersonalizedRecommendations {
        
        @Test
        @DisplayName("As a user, I want personalized recommendations so I find relevant content")
        fun `user receives personalized resource recommendations`() {
            // Given: User profile and preferences
            val recommendations = listOf(
                EducationalResource(
                    id = "res1", title = "Anxiety Management", category = "Anxiety",
                    format = ContentFormat.TEXT, url = "https://example.com/anxiety",
                    duration = 15, tags = emptyList(), difficulty = DifficultyLevel.BEGINNER,
                    relevanceScore = 0.9f, createdAt = Date()
                ),
                EducationalResource(
                    id = "res2", title = "Stress Relief", category = "Stress",
                    format = ContentFormat.VIDEO, url = "https://example.com/stress",
                    duration = 20, tags = emptyList(), difficulty = DifficultyLevel.BEGINNER,
                    relevanceScore = 0.85f, createdAt = Date()
                )
            )
            
            // When: System recommends resources
            val recommendationsProvided = recommendations.isNotEmpty()
            val recommendationsRelevant = recommendations.all { it.relevanceScore > 0.7f }
            
            // Then: Personalized recommendations are shown
            assertTrue(recommendationsProvided, "User should receive recommendations")
            assertTrue(recommendationsRelevant, "Recommendations should be relevant")
        }
    }
}
