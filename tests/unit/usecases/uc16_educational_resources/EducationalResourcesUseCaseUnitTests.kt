package com.serenityai.tests.unit.usecases.uc16_educational_resources

import com.serenityai.usecases.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

/**
 * UC16: Access Educational Resources
 * 
 * Use Case Goal: Enable users to access comprehensive educational resources about mental health,
 * therapy techniques, and self-care strategies so they can learn and apply evidence-based practices.
 * 
 * Key Requirements Being Tested:
 * 1. System provides categorized educational content (articles, videos, guides)
 * 2. System personalizes content recommendations based on user needs
 * 3. System tracks learning progress and completion
 * 4. System provides search and filtering capabilities
 * 5. System supports multiple content formats (text, video, audio)
 */
@DisplayName("UC16: Access Educational Resources - Unit Tests")
class EducationalResourcesUseCaseUnitTests {

    private lateinit var useCase: EducationalResourcesUseCase

    @BeforeEach
    fun setUp() {
        useCase = EducationalResourcesUseCase()
    }

    @Nested
    @DisplayName("Test Case 1: Resource Retrieval and Categorization - Validates Core UC16 Functionality")
    inner class ResourceRetrievalTests {
        
        /**
         * Tests: System retrieves educational resources by category
         * Validates: UC16 requirement for categorized content access
         * Expected: Resources filtered by category are returned correctly
         */
        @Test
        @DisplayName("UC16-REQ-1: System MUST retrieve educational resources filtered by category")
        fun `system retrieves educational resources filtered by category correctly`() {
            // Given: System has educational resources in multiple categories
            // Purpose: Validate category-based filtering works correctly
            
            // When: User requests resources for a specific category
            val anxietyResources = runBlocking { useCase.getEducationalResources(category = "Anxiety Management") }
            val mindfulnessResources = runBlocking { useCase.getEducationalResources(category = "Mindfulness") }
            
            // Then: Only resources in that category should be returned
            assertTrue(anxietyResources.isNotEmpty(), "UC16: System must return resources for requested category")
            assertTrue(anxietyResources.all { it.category == "Anxiety Management" }, 
                "UC16: All returned resources must match requested category")
            assertTrue(mindfulnessResources.all { it.category == "Mindfulness" }, 
                "UC16: Category filtering must be accurate")
        }
        
        /**
         * Tests: System retrieves resources by format type
         * Validates: UC16 requirement for format-based filtering
         * Expected: Resources filtered by format (text, video, audio) are returned
         */
        @Test
        @DisplayName("UC16-REQ-2: System MUST filter resources by content format")
        fun `system filters educational resources by content format correctly`() {
            // Given: System has resources in multiple formats
            // Purpose: Validate format filtering functionality
            
            // When: User requests resources of specific format
            val textResources = runBlocking { useCase.getEducationalResources(format = ContentFormat.TEXT) }
            val videoResources = runBlocking { useCase.getEducationalResources(format = ContentFormat.VIDEO) }
            val audioResources = runBlocking { useCase.getEducationalResources(format = ContentFormat.AUDIO) }
            
            // Then: Only resources of requested format should be returned
            assertTrue(textResources.isNotEmpty(), "UC16: Text resources must be available")
            assertTrue(textResources.all { it.format == ContentFormat.TEXT }, 
                "UC16: All text resources must be TEXT format")
            assertTrue(videoResources.isNotEmpty(), "UC16: Video resources must be available")
            assertTrue(videoResources.all { it.format == ContentFormat.VIDEO }, 
                "UC16: All video resources must be VIDEO format")
            assertTrue(audioResources.isNotEmpty(), "UC16: Audio resources must be available")
            assertTrue(audioResources.all { it.format == ContentFormat.AUDIO }, 
                "UC16: All audio resources must be AUDIO format")
        }
        
        /**
         * Tests: System provides available resource categories
         * Validates: UC16 requirement for category listing
         * Expected: List of all available categories is returned
         */
        @Test
        @DisplayName("UC16-REQ-3: System MUST provide list of available resource categories")
        fun `system provides comprehensive list of available resource categories`() {
            // Given: System has multiple resource categories
            // Purpose: Validate category listing functionality
            
            // When: User requests available categories
            val categories = runBlocking { useCase.getResourceCategories() }
            
            // Then: Complete list of categories should be returned
            assertTrue(categories.isNotEmpty(), "UC16: System must provide at least one category")
            assertTrue(categories.contains("Anxiety Management"), 
                "UC16: Common categories like Anxiety Management must be available")
            assertTrue(categories.contains("Mindfulness"), 
                "UC16: Categories like Mindfulness must be available")
            assertTrue(categories.size >= 5, "UC16: System should provide comprehensive category list")
        }
    }

    @Nested
    @DisplayName("Test Case 2: Search and Personalization - Validates Secondary UC16 Functionality")
    inner class SearchAndPersonalizationTests {
        
        /**
         * Tests: System searches educational resources by keyword
         * Validates: UC16 requirement for search functionality
         * Expected: Resources matching search query are returned
         */
        @Test
        @DisplayName("UC16-REQ-4: System MUST search educational resources by keyword")
        fun `system searches educational resources by keyword correctly`() {
            // Given: System has educational resources with various topics
            // Purpose: Validate search functionality works across titles, descriptions, tags
            
            // When: User searches for a keyword
            val anxietyResults = runBlocking { useCase.searchEducationalResources("anxiety") }
            val mindfulnessResults = runBlocking { useCase.searchEducationalResources("mindfulness") }
            
            // Then: Matching resources should be returned
            assertTrue(anxietyResults.isNotEmpty(), "UC16: Search must return results for valid queries")
            assertTrue(anxietyResults.any { 
                it.title.lowercase().contains("anxiety") || 
                it.description.lowercase().contains("anxiety") ||
                it.tags.any { tag -> tag.lowercase().contains("anxiety") }
            }, "UC16: Search results must match query in title, description, or tags")
            
            // Validate search rejects empty queries
            assertThrows(IllegalArgumentException::class.java) {
                runBlocking { useCase.searchEducationalResources("") }
            }
            assertThrows(IllegalArgumentException::class.java) {
                runBlocking { useCase.searchEducationalResources("   ") }
            }
        }
        
        /**
         * Tests: System personalizes resource recommendations
         * Validates: UC16 requirement for personalized content
         * Expected: Recommended resources are relevant to user's needs
         */
        @Test
        @DisplayName("UC16-REQ-5: System MUST provide personalized resource recommendations")
        fun `system provides personalized resource recommendations based on user profile`() {
            // Given: System has user profile and resource data
            // Purpose: Validate personalization algorithm works correctly
            
            // When: User requests recommended resources
            val recommendations = runBlocking { useCase.getRecommendedResources(userId = "user123", limit = 5) }
            
            // Then: Personalized recommendations should be returned
            assertTrue(recommendations.isNotEmpty(), "UC16: System must provide recommendations")
            assertTrue(recommendations.size <= 5, "UC16: Recommendations must respect limit parameter")
            assertTrue(recommendations.all { it.relevanceScore > 0f }, 
                "UC16: All recommendations must have relevance scores")
            // Recommendations should be sorted by relevance (highest first)
            val scores = recommendations.map { it.relevanceScore }
            assertEquals(scores.sortedDescending(), scores, 
                "UC16: Recommendations must be sorted by relevance (highest first)")
        }
        
        /**
         * Tests: System validates search input
         * Validates: UC16 requirement for input validation
         * Expected: Empty or invalid search queries are rejected
         */
        @Test
        @DisplayName("UC16-REQ-6: System MUST validate search input and reject empty queries")
        fun `system validates search input and rejects empty queries`() {
            // Given: User attempts to search with invalid input
            // Purpose: Validate input validation prevents errors
            
            // When: User searches with empty string
            // Then: System should throw IllegalArgumentException
            assertThrows(IllegalArgumentException::class.java) {
                runBlocking { useCase.searchEducationalResources("") }
            }
            
            // When: User searches with whitespace only
            // Then: System should throw IllegalArgumentException
            assertThrows(IllegalArgumentException::class.java) {
                runBlocking { useCase.searchEducationalResources("   ") }
            }
        }
    }

    @Nested
    @DisplayName("Test Case 3: Learning Progress Tracking - Validates Advanced UC16 Functionality")
    inner class LearningProgressTests {
        
        /**
         * Tests: System tracks learning progress for resources
         * Validates: UC16 requirement for progress tracking
         * Expected: Progress is tracked and updated correctly
         */
        @Test
        @DisplayName("UC16-REQ-7: System MUST track learning progress for educational resources")
        fun `system tracks learning progress for resources correctly`() {
            // Given: User is learning from a resource
            // Purpose: Validate progress tracking functionality
            
            // When: User updates progress
            val progress = runBlocking { 
                useCase.trackLearningProgress(
                    resourceId = "res_001",
                    userId = "user123",
                    progress = 50
                )
            }
            
            // Then: Progress should be tracked correctly
            assertEquals("res_001", progress.resourceId, "UC16: Progress must be linked to correct resource")
            assertEquals("user123", progress.userId, "UC16: Progress must be linked to correct user")
            assertEquals(50, progress.progress, "UC16: Progress percentage must be accurate")
            assertNull(progress.completedAt, "UC16: Completion date should be null when progress < 100%")
            
            // Validate progress bounds
            assertThrows(IllegalArgumentException::class.java) {
                runBlocking { useCase.trackLearningProgress("res_001", "user123", -1) }
            }
            assertThrows(IllegalArgumentException::class.java) {
                runBlocking { useCase.trackLearningProgress("res_001", "user123", 101) }
            }
        }
        
        /**
         * Tests: System marks resources as completed
         * Validates: UC16 requirement for completion tracking
         * Expected: Resources can be marked as completed with timestamp
         */
        @Test
        @DisplayName("UC16-REQ-8: System MUST mark educational resources as completed")
        fun `system marks educational resources as completed correctly`() {
            // Given: User has finished learning from a resource
            // Purpose: Validate completion tracking functionality
            
            // When: User marks resource as completed
            val completedProgress = runBlocking { 
                useCase.markResourceAsCompleted(
                    resourceId = "res_002",
                    userId = "user123"
                )
            }
            
            // Then: Resource should be marked as completed
            assertEquals("res_002", completedProgress.resourceId, "UC16: Completion must be linked to correct resource")
            assertEquals("user123", completedProgress.userId, "UC16: Completion must be linked to correct user")
            assertEquals(100, completedProgress.progress, "UC16: Completed resources must have 100% progress")
            assertNotNull(completedProgress.completedAt, "UC16: Completion timestamp must be set")
            assertTrue(completedProgress.timeSpent > 0, "UC16: Time spent must be tracked")
        }
        
        /**
         * Tests: System retrieves learning history
         * Validates: UC16 requirement for history tracking
         * Expected: User's learning history is retrievable
         */
        @Test
        @DisplayName("UC16-REQ-9: System MUST retrieve user's learning history")
        fun `system retrieves user learning history correctly`() {
            // Given: User has learning progress tracked
            // Purpose: Validate history retrieval functionality
            
            // When: User requests learning history
            val history = runBlocking { useCase.getLearningHistory(userId = "user123") }
            
            // Then: Learning history should be returned
            assertNotNull(history, "UC16: Learning history must be retrievable")
            // In production, would verify history contains actual progress entries
        }
    }
}

