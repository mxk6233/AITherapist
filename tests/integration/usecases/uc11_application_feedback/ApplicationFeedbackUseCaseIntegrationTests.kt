package com.serenityai.tests.integration.usecases.uc11_application_feedback

import com.serenityai.usecases.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

/**
 * UC11: Submit Application Feedback - Integration Tests
 * 
 * Integration tests verify that Application Feedback integrates correctly
 * with feedback storage, analytics service, search service, and status management.
 */
@DisplayName("UC11: Submit Application Feedback - Integration Tests")
class ApplicationFeedbackUseCaseIntegrationTests {

    @Nested
    @DisplayName("Integration Test 1: Feedback Submission Integration")
    inner class FeedbackSubmissionIntegrationTests {
        
        @Test
        @DisplayName("Should integrate feedback submission with storage")
        fun `feedback submission integrates with storage`() {
            // Given: Use case and feedback data
            val useCase = ApplicationFeedbackUseCase()
            val userId = "user123"
            val feedbackType = FeedbackType.BUG
            val message = "App crashes when saving"
            
            // When: System integrates feedback submission
            val feedback = useCase.submitFeedback(userId, feedbackType, message)
            val retrievedFeedback = useCase.getFeedback(feedback.id)
            
            // Then: Integration works correctly
            assertNotNull(feedback, "UC11 Integration: Feedback must be submitted")
            assertNotNull(retrievedFeedback, "UC11 Integration: Feedback must be stored")
            assertEquals(feedback.id, retrievedFeedback?.id,
                "UC11 Integration: Stored feedback must match submitted feedback")
        }
        
        @Test
        @DisplayName("Should integrate feedback submission with analytics")
        fun `feedback submission integrates with analytics`() {
            // Given: Use case and feedback submissions
            val useCase = ApplicationFeedbackUseCase()
            useCase.submitFeedback("user1", FeedbackType.BUG, "Bug 1")
            useCase.submitFeedback("user2", FeedbackType.FEATURE, "Feature 1")
            
            // When: System integrates with analytics
            val analytics = useCase.getFeedbackAnalytics()
            
            // Then: Integration works correctly
            assertNotNull(analytics, "UC11 Integration: Analytics must be provided")
            assertTrue(analytics.totalFeedback >= 2,
                "UC11 Integration: Analytics must track total feedback")
            assertNotNull(analytics.feedbackByType,
                "UC11 Integration: Analytics must track feedback by type")
        }
    }

    @Nested
    @DisplayName("Integration Test 2: Status Management Integration")
    inner class StatusManagementIntegrationTests {
        
        @Test
        @DisplayName("Should integrate status updates with feedback tracking")
        fun `status updates integrate with feedback tracking`() {
            // Given: Use case and submitted feedback
            val useCase = ApplicationFeedbackUseCase()
            val feedback = useCase.submitFeedback("user123", FeedbackType.BUG, "Bug report")
            
            // When: System integrates status updates
            val updatedFeedback = useCase.updateFeedbackStatus(feedback.id, FeedbackStatus.REVIEWED)
            val reviewedFeedback = useCase.getAllFeedback(status = FeedbackStatus.REVIEWED)
            
            // Then: Integration works correctly
            assertNotNull(updatedFeedback, "UC11 Integration: Status must be updated")
            assertEquals(FeedbackStatus.REVIEWED, updatedFeedback?.status,
                "UC11 Integration: Status must be updated correctly")
            assertTrue(reviewedFeedback.any { it.id == feedback.id },
                "UC11 Integration: Updated feedback must be retrievable by status")
        }
        
        @Test
        @DisplayName("Should integrate implementation tracking with status")
        fun `implementation tracking integrates with status`() {
            // Given: Use case and reviewed feedback
            val useCase = ApplicationFeedbackUseCase()
            val feedback = useCase.submitFeedback("user123", FeedbackType.FEATURE, "Feature request")
            useCase.updateFeedbackStatus(feedback.id, FeedbackStatus.REVIEWED)
            
            // When: System integrates implementation
            val implementedFeedback = useCase.markAsImplemented(feedback.id, "Implemented")
            val statistics = useCase.getFeedbackStatistics()
            
            // Then: Integration works correctly
            assertNotNull(implementedFeedback, "UC11 Integration: Feedback must be marked as implemented")
            assertEquals(FeedbackStatus.IMPLEMENTED, implementedFeedback?.status,
                "UC11 Integration: Status must be IMPLEMENTED")
            assertTrue(statistics.implemented >= 1,
                "UC11 Integration: Statistics must track implemented feedback")
        }
    }

    @Nested
    @DisplayName("Integration Test 3: Search Integration")
    inner class SearchIntegrationTests {
        
        @Test
        @DisplayName("Should integrate search with feedback retrieval")
        fun `search integrates with feedback retrieval`() {
            // Given: Use case and feedback with different content
            val useCase = ApplicationFeedbackUseCase()
            useCase.submitFeedback("user1", FeedbackType.BUG, "App crashes when saving")
            useCase.submitFeedback("user2", FeedbackType.FEATURE, "Add dark mode")
            useCase.submitFeedback("user3", FeedbackType.BUG, "Saving doesn't work")
            
            // When: System integrates search
            val searchResults = useCase.searchFeedback("crash")
            
            // Then: Integration works correctly
            assertNotNull(searchResults, "UC11 Integration: Search results must be returned")
            assertTrue(searchResults.isNotEmpty(),
                "UC11 Integration: Search results must be found")
            assertTrue(searchResults.any { it.message.contains("crash", ignoreCase = true) },
                "UC11 Integration: Search results must contain keyword")
        }
    }

    @Nested
    @DisplayName("Integration Test 4: Analytics Integration")
    inner class AnalyticsIntegrationTests {
        
        @Test
        @DisplayName("Should integrate analytics with feedback data")
        fun `analytics integrates with feedback data`() {
            // Given: Use case and various feedback submissions
            val useCase = ApplicationFeedbackUseCase()
            useCase.submitFeedback("user1", FeedbackType.BUG, "Bug 1", 3)
            useCase.submitFeedback("user2", FeedbackType.FEATURE, "Feature 1", 5)
            useCase.submitFeedback("user3", FeedbackType.GENERAL, "Feedback 1", 4)
            
            // When: System integrates analytics
            val analytics = useCase.getFeedbackAnalytics()
            val statistics = useCase.getFeedbackStatistics()
            val trends = useCase.getFeedbackTrends(days = 30)
            
            // Then: Integration works correctly
            assertNotNull(analytics, "UC11 Integration: Analytics must be provided")
            assertNotNull(statistics, "UC11 Integration: Statistics must be provided")
            assertNotNull(trends, "UC11 Integration: Trends must be provided")
            assertTrue(analytics.totalFeedback >= 3,
                "UC11 Integration: Analytics must track total feedback")
            assertNotNull(analytics.averageRating,
                "UC11 Integration: Analytics must calculate average rating")
        }
    }

    @Nested
    @DisplayName("Integration Test 5: End-to-End Feedback Flow")
    inner class EndToEndFlowTests {
        
        @Test
        @DisplayName("Should integrate complete feedback submission and management flow")
        fun `complete feedback flow integrates correctly`() {
            // Given: Use case and feedback scenario
            val useCase = ApplicationFeedbackUseCase()
            val userId = "user123"
            
            // Submit feedback
            val feedback = useCase.submitFeedback(userId, FeedbackType.FEATURE, "Add dark mode", 5)
            
            // Update status
            val reviewedFeedback = useCase.updateFeedbackStatus(feedback.id, FeedbackStatus.REVIEWED)
            
            // Mark as implemented
            val implementedFeedback = useCase.markAsImplemented(feedback.id, "Implemented in v2.0")
            
            // Retrieve user feedback
            val userFeedback = useCase.getUserFeedback(userId)
            
            // Get analytics
            val analytics = useCase.getFeedbackAnalytics(userId)
            
            // Then: Complete flow works correctly
            assertNotNull(feedback, "UC11 Integration: Feedback must be submitted")
            assertNotNull(reviewedFeedback, "UC11 Integration: Status must be updated")
            assertNotNull(implementedFeedback, "UC11 Integration: Feedback must be marked as implemented")
            assertTrue(userFeedback.isNotEmpty(),
                "UC11 Integration: User feedback must be retrievable")
            assertNotNull(analytics, "UC11 Integration: Analytics must be provided")
            assertTrue(analytics.totalFeedback >= 1,
                "UC11 Integration: Analytics must track user feedback")
        }
    }
}

