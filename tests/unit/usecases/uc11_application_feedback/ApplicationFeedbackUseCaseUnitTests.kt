package com.serenityai.tests.unit.usecases.uc11_application_feedback

import com.serenityai.usecases.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

/**
 * UC11: Submit Application Feedback
 * 
 * Use Case Goal: Enable users to submit feedback, bug reports, feature requests,
 * complaints, and ratings to help improve the application.
 * 
 * Key Requirements Being Tested:
 * 1. System allows users to submit feedback
 * 2. System supports different feedback types (bug, feature, complaint, compliment)
 * 3. System tracks feedback status (received, reviewed, implemented, declined)
 * 4. System provides feedback analytics and statistics
 * 5. System allows searching and filtering feedback
 */
@DisplayName("UC11: Submit Application Feedback - Unit Tests")
class ApplicationFeedbackUseCaseUnitTests {

    private lateinit var useCase: ApplicationFeedbackUseCase

    @BeforeEach
    fun setUp() {
        useCase = ApplicationFeedbackUseCase()
    }

    @Nested
    @DisplayName("Test Case 1: Feedback Submission - Validates Core UC11 Functionality")
    inner class FeedbackSubmissionTests {
        
        @Test
        @DisplayName("UC11-REQ-1: System MUST allow users to submit feedback")
        fun `system allows users to submit feedback correctly`() {
            // Given: User wants to submit feedback
            val userId = "user123"
            val feedbackType = FeedbackType.GENERAL
            val message = "This app is helpful"
            
            // When: User submits feedback
            val feedback = useCase.submitFeedback(userId, feedbackType, message)
            
            // Then: Feedback should be submitted correctly
            assertNotNull(feedback, "UC11: Feedback must be submitted")
            assertTrue(feedback.id.isNotBlank(), "UC11: Feedback must have unique ID")
            assertEquals(userId, feedback.userId, "UC11: Feedback must be linked to user")
            assertEquals(feedbackType, feedback.feedbackType, "UC11: Feedback type must be set correctly")
            assertEquals(message, feedback.message, "UC11: Feedback message must be stored correctly")
            assertEquals(FeedbackStatus.RECEIVED, feedback.status,
                "UC11: New feedback must have RECEIVED status")
            assertNotNull(feedback.submittedAt, "UC11: Feedback must have submission timestamp")
        }
        
        @Test
        @DisplayName("UC11-REQ-2: System MUST allow users to submit bug reports")
        fun `system allows users to submit bug reports correctly`() {
            // Given: User wants to report a bug
            val userId = "user123"
            val feedbackType = FeedbackType.BUG
            val message = "The app crashes when I try to save my mood"
            
            // When: User submits bug report
            val feedback = useCase.submitFeedback(userId, feedbackType, message)
            
            // Then: Bug report should be submitted correctly
            assertNotNull(feedback, "UC11: Bug report must be submitted")
            assertEquals(FeedbackType.BUG, feedback.feedbackType,
                "UC11: Feedback type must be BUG")
            assertEquals(FeedbackStatus.RECEIVED, feedback.status,
                "UC11: Bug report must have RECEIVED status")
        }
        
        @Test
        @DisplayName("UC11-REQ-3: System MUST allow users to submit feature requests")
        fun `system allows users to submit feature requests correctly`() {
            // Given: User wants to request a feature
            val userId = "user123"
            val feedbackType = FeedbackType.FEATURE
            val message = "I would like to have dark mode support"
            
            // When: User submits feature request
            val feedback = useCase.submitFeedback(userId, feedbackType, message)
            
            // Then: Feature request should be submitted correctly
            assertNotNull(feedback, "UC11: Feature request must be submitted")
            assertEquals(FeedbackType.FEATURE, feedback.feedbackType,
                "UC11: Feedback type must be FEATURE")
        }
        
        @Test
        @DisplayName("UC11-REQ-4: System MUST allow users to submit complaints")
        fun `system allows users to submit complaints correctly`() {
            // Given: User wants to submit a complaint
            val userId = "user123"
            val feedbackType = FeedbackType.COMPLAINT
            val message = "The app is too slow"
            
            // When: User submits complaint
            val feedback = useCase.submitFeedback(userId, feedbackType, message)
            
            // Then: Complaint should be submitted correctly
            assertNotNull(feedback, "UC11: Complaint must be submitted")
            assertEquals(FeedbackType.COMPLAINT, feedback.feedbackType,
                "UC11: Feedback type must be COMPLAINT")
        }
        
        @Test
        @DisplayName("UC11-REQ-5: System MUST allow users to submit compliments")
        fun `system allows users to submit compliments correctly`() {
            // Given: User wants to submit a compliment
            val userId = "user123"
            val feedbackType = FeedbackType.COMPLIMENT
            val message = "This app has helped me so much!"
            
            // When: User submits compliment
            val feedback = useCase.submitFeedback(userId, feedbackType, message)
            
            // Then: Compliment should be submitted correctly
            assertNotNull(feedback, "UC11: Compliment must be submitted")
            assertEquals(FeedbackType.COMPLIMENT, feedback.feedbackType,
                "UC11: Feedback type must be COMPLIMENT")
        }
        
        @Test
        @DisplayName("UC11-REQ-6: System MUST allow users to submit ratings")
        fun `system allows users to submit ratings correctly`() {
            // Given: User wants to submit feedback with rating
            val userId = "user123"
            val feedbackType = FeedbackType.GENERAL
            val message = "Great app!"
            val rating = 5
            
            // When: User submits feedback with rating
            val feedback = useCase.submitFeedback(userId, feedbackType, message, rating)
            
            // Then: Rating should be submitted correctly
            assertNotNull(feedback, "UC11: Feedback with rating must be submitted")
            assertEquals(rating, feedback.rating, "UC11: Rating must be stored correctly")
            assertTrue(feedback.rating!! in 1..5, "UC11: Rating must be between 1 and 5")
        }
        
        @Test
        @DisplayName("UC11-REQ-7: System MUST validate feedback input")
        fun `system validates feedback input correctly`() {
            // Given: Invalid feedback input
            val userId = "user123"
            val feedbackType = FeedbackType.GENERAL
            val emptyMessage = ""
            
            // When: User tries to submit invalid feedback
            // Then: System should reject invalid input
            assertThrows(IllegalArgumentException::class.java) {
                useCase.submitFeedback(userId, feedbackType, emptyMessage)
            }
        }
        
        @Test
        @DisplayName("UC11-REQ-8: System MUST validate rating range")
        fun `system validates rating range correctly`() {
            // Given: Invalid rating
            val userId = "user123"
            val feedbackType = FeedbackType.GENERAL
            val message = "Feedback"
            val invalidRating = 10
            
            // When: User tries to submit feedback with invalid rating
            // Then: System should reject invalid rating
            assertThrows(IllegalArgumentException::class.java) {
                useCase.submitFeedback(userId, feedbackType, message, invalidRating)
            }
        }
    }

    @Nested
    @DisplayName("Test Case 2: Feedback Retrieval")
    inner class FeedbackRetrievalTests {
        
        @Test
        @DisplayName("UC11-REQ-9: System MUST retrieve user feedback")
        fun `system retrieves user feedback correctly`() {
            // Given: User has submitted feedback
            val userId = "user123"
            useCase.submitFeedback(userId, FeedbackType.GENERAL, "Feedback 1")
            useCase.submitFeedback(userId, FeedbackType.BUG, "Bug report")
            
            // When: System retrieves user feedback
            val userFeedback = useCase.getUserFeedback(userId)
            
            // Then: Feedback should be retrieved correctly
            assertNotNull(userFeedback, "UC11: User feedback must be retrieved")
            assertTrue(userFeedback.size >= 2, "UC11: All user feedback must be retrieved")
            assertTrue(userFeedback.all { it.userId == userId },
                "UC11: All feedback must belong to correct user")
        }
        
        @Test
        @DisplayName("UC11-REQ-10: System MUST retrieve feedback by ID")
        fun `system retrieves feedback by ID correctly`() {
            // Given: Feedback has been submitted
            val userId = "user123"
            val feedback = useCase.submitFeedback(userId, FeedbackType.GENERAL, "Feedback")
            
            // When: System retrieves feedback by ID
            val retrievedFeedback = useCase.getFeedback(feedback.id)
            
            // Then: Feedback should be retrieved correctly
            assertNotNull(retrievedFeedback, "UC11: Feedback must be retrieved by ID")
            assertEquals(feedback.id, retrievedFeedback?.id,
                "UC11: Retrieved feedback must match submitted feedback")
        }
        
        @Test
        @DisplayName("UC11-REQ-11: System MUST retrieve all feedback")
        fun `system retrieves all feedback correctly`() {
            // Given: Multiple feedback submissions
            useCase.submitFeedback("user1", FeedbackType.GENERAL, "Feedback 1")
            useCase.submitFeedback("user2", FeedbackType.BUG, "Bug report")
            useCase.submitFeedback("user3", FeedbackType.FEATURE, "Feature request")
            
            // When: System retrieves all feedback
            val allFeedback = useCase.getAllFeedback()
            
            // Then: All feedback should be retrieved
            assertNotNull(allFeedback, "UC11: All feedback must be retrieved")
            assertTrue(allFeedback.size >= 3, "UC11: All feedback must be retrieved")
        }
        
        @Test
        @DisplayName("UC11-REQ-12: System MUST filter feedback by type")
        fun `system filters feedback by type correctly`() {
            // Given: Feedback of different types
            useCase.submitFeedback("user1", FeedbackType.BUG, "Bug 1")
            useCase.submitFeedback("user2", FeedbackType.FEATURE, "Feature 1")
            useCase.submitFeedback("user3", FeedbackType.BUG, "Bug 2")
            
            // When: System filters by type
            val bugReports = useCase.getAllFeedback(feedbackType = FeedbackType.BUG)
            
            // Then: Feedback should be filtered correctly
            assertNotNull(bugReports, "UC11: Filtered feedback must be retrieved")
            assertTrue(bugReports.isNotEmpty(), "UC11: Bug reports must be found")
            assertTrue(bugReports.all { it.feedbackType == FeedbackType.BUG },
                "UC11: All filtered feedback must be of correct type")
        }
        
        @Test
        @DisplayName("UC11-REQ-13: System MUST filter feedback by status")
        fun `system filters feedback by status correctly`() {
            // Given: Feedback with different statuses
            val feedback1 = useCase.submitFeedback("user1", FeedbackType.GENERAL, "Feedback 1")
            val feedback2 = useCase.submitFeedback("user2", FeedbackType.GENERAL, "Feedback 2")
            useCase.updateFeedbackStatus(feedback1.id, FeedbackStatus.REVIEWED)
            
            // When: System filters by status
            val reviewedFeedback = useCase.getAllFeedback(status = FeedbackStatus.REVIEWED)
            
            // Then: Feedback should be filtered correctly
            assertNotNull(reviewedFeedback, "UC11: Filtered feedback must be retrieved")
            assertTrue(reviewedFeedback.isNotEmpty(), "UC11: Reviewed feedback must be found")
            assertTrue(reviewedFeedback.all { it.status == FeedbackStatus.REVIEWED },
                "UC11: All filtered feedback must have correct status")
        }
    }

    @Nested
    @DisplayName("Test Case 3: Feedback Status Management")
    inner class StatusManagementTests {
        
        @Test
        @DisplayName("UC11-REQ-14: System MUST update feedback status")
        fun `system updates feedback status correctly`() {
            // Given: Submitted feedback
            val userId = "user123"
            val feedback = useCase.submitFeedback(userId, FeedbackType.BUG, "Bug report")
            
            // When: System updates status
            val updatedFeedback = useCase.updateFeedbackStatus(feedback.id, FeedbackStatus.REVIEWED)
            
            // Then: Status should be updated correctly
            assertNotNull(updatedFeedback, "UC11: Feedback status must be updated")
            assertEquals(FeedbackStatus.REVIEWED, updatedFeedback?.status,
                "UC11: Status must be updated to REVIEWED")
        }
        
        @Test
        @DisplayName("UC11-REQ-15: System MUST mark feedback as implemented")
        fun `system marks feedback as implemented correctly`() {
            // Given: Reviewed feedback
            val userId = "user123"
            val feedback = useCase.submitFeedback(userId, FeedbackType.FEATURE, "Feature request")
            useCase.updateFeedbackStatus(feedback.id, FeedbackStatus.REVIEWED)
            
            // When: System marks as implemented
            val implementedFeedback = useCase.markAsImplemented(feedback.id, "Implemented in v2.0")
            
            // Then: Feedback should be marked as implemented
            assertNotNull(implementedFeedback, "UC11: Feedback must be marked as implemented")
            assertEquals(FeedbackStatus.IMPLEMENTED, implementedFeedback?.status,
                "UC11: Status must be IMPLEMENTED")
        }
        
        @Test
        @DisplayName("UC11-REQ-16: System MUST mark feedback as declined")
        fun `system marks feedback as declined correctly`() {
            // Given: Reviewed feedback
            val userId = "user123"
            val feedback = useCase.submitFeedback(userId, FeedbackType.FEATURE, "Feature request")
            useCase.updateFeedbackStatus(feedback.id, FeedbackStatus.REVIEWED)
            
            // When: System marks as declined
            val declinedFeedback = useCase.markAsDeclined(feedback.id, "Not feasible")
            
            // Then: Feedback should be marked as declined
            assertNotNull(declinedFeedback, "UC11: Feedback must be marked as declined")
            assertEquals(FeedbackStatus.DECLINED, declinedFeedback?.status,
                "UC11: Status must be DECLINED")
        }
        
        @Test
        @DisplayName("UC11-REQ-17: System MUST get pending feedback")
        fun `system gets pending feedback correctly`() {
            // Given: Feedback with different statuses
            useCase.submitFeedback("user1", FeedbackType.GENERAL, "Feedback 1")
            val feedback2 = useCase.submitFeedback("user2", FeedbackType.BUG, "Bug report")
            useCase.updateFeedbackStatus(feedback2.id, FeedbackStatus.REVIEWED)
            
            // When: System gets pending feedback
            val pendingFeedback = useCase.getPendingFeedback()
            
            // Then: Pending feedback should be retrieved
            assertNotNull(pendingFeedback, "UC11: Pending feedback must be retrieved")
            assertTrue(pendingFeedback.isNotEmpty(), "UC11: Pending feedback must be found")
            assertTrue(pendingFeedback.all { it.status == FeedbackStatus.RECEIVED },
                "UC11: All pending feedback must have RECEIVED status")
        }
    }

    @Nested
    @DisplayName("Test Case 4: Feedback Analytics")
    inner class AnalyticsTests {
        
        @Test
        @DisplayName("UC11-REQ-18: System MUST provide feedback analytics")
        fun `system provides feedback analytics correctly`() {
            // Given: Multiple feedback submissions
            useCase.submitFeedback("user1", FeedbackType.BUG, "Bug 1", 3)
            useCase.submitFeedback("user2", FeedbackType.FEATURE, "Feature 1", 5)
            useCase.submitFeedback("user3", FeedbackType.GENERAL, "Feedback 1", 4)
            
            // When: System provides analytics
            val analytics = useCase.getFeedbackAnalytics()
            
            // Then: Analytics should be provided correctly
            assertNotNull(analytics, "UC11: Analytics must be provided")
            assertTrue(analytics.totalFeedback >= 3, "UC11: Total feedback must be tracked")
            assertNotNull(analytics.feedbackByType, "UC11: Feedback by type must be tracked")
            assertNotNull(analytics.feedbackByStatus, "UC11: Feedback by status must be tracked")
        }
        
        @Test
        @DisplayName("UC11-REQ-19: System MUST calculate average rating")
        fun `system calculates average rating correctly`() {
            // Given: Feedback with ratings
            useCase.submitFeedback("user1", FeedbackType.GENERAL, "Feedback 1", 5)
            useCase.submitFeedback("user2", FeedbackType.GENERAL, "Feedback 2", 4)
            useCase.submitFeedback("user3", FeedbackType.GENERAL, "Feedback 3", 3)
            
            // When: System calculates average rating
            val analytics = useCase.getFeedbackAnalytics()
            
            // Then: Average rating should be calculated correctly
            assertNotNull(analytics, "UC11: Analytics must be provided")
            assertNotNull(analytics.averageRating, "UC11: Average rating must be calculated")
            assertTrue(analytics.averageRating!! >= 3f && analytics.averageRating!! <= 5f,
                "UC11: Average rating must be within valid range")
        }
        
        @Test
        @DisplayName("UC11-REQ-20: System MUST provide feedback statistics")
        fun `system provides feedback statistics correctly`() {
            // Given: Various feedback submissions
            useCase.submitFeedback("user1", FeedbackType.BUG, "Bug 1")
            useCase.submitFeedback("user2", FeedbackType.FEATURE, "Feature 1")
            useCase.submitFeedback("user3", FeedbackType.COMPLAINT, "Complaint 1")
            useCase.submitFeedback("user4", FeedbackType.COMPLIMENT, "Compliment 1")
            
            // When: System provides statistics
            val statistics = useCase.getFeedbackStatistics()
            
            // Then: Statistics should be provided correctly
            assertNotNull(statistics, "UC11: Statistics must be provided")
            assertTrue(statistics.totalSubmissions >= 4, "UC11: Total submissions must be tracked")
            assertTrue(statistics.bugReports >= 1, "UC11: Bug reports must be tracked")
            assertTrue(statistics.featureRequests >= 1, "UC11: Feature requests must be tracked")
            assertTrue(statistics.complaints >= 1, "UC11: Complaints must be tracked")
            assertTrue(statistics.compliments >= 1, "UC11: Compliments must be tracked")
        }
    }

    @Nested
    @DisplayName("Test Case 5: Feedback Search and Filtering")
    inner class SearchAndFilterTests {
        
        @Test
        @DisplayName("UC11-REQ-21: System MUST search feedback by keyword")
        fun `system searches feedback by keyword correctly`() {
            // Given: Feedback with different content
            useCase.submitFeedback("user1", FeedbackType.BUG, "App crashes when saving")
            useCase.submitFeedback("user2", FeedbackType.FEATURE, "Add dark mode")
            useCase.submitFeedback("user3", FeedbackType.BUG, "Saving doesn't work")
            
            // When: System searches by keyword
            val searchResults = useCase.searchFeedback("crash")
            
            // Then: Search results should be returned
            assertNotNull(searchResults, "UC11: Search results must be returned")
            assertTrue(searchResults.isNotEmpty(), "UC11: Search results must be found")
            assertTrue(searchResults.any { it.message.contains("crash", ignoreCase = true) },
                "UC11: Search results must contain keyword")
        }
        
        @Test
        @DisplayName("UC11-REQ-22: System MUST get feedback by category")
        fun `system gets feedback by category correctly`() {
            // Given: Feedback with categories
            useCase.submitFeedback("user1", FeedbackType.BUG, "Bug report", category = "UI")
            useCase.submitFeedback("user2", FeedbackType.BUG, "Another bug", category = "Performance")
            useCase.submitFeedback("user3", FeedbackType.FEATURE, "Feature request", category = "UI")
            
            // When: System gets feedback by category
            val uiFeedback = useCase.getFeedbackByCategory("UI")
            
            // Then: Feedback should be filtered by category
            assertNotNull(uiFeedback, "UC11: Category feedback must be retrieved")
            assertTrue(uiFeedback.isNotEmpty(), "UC11: Category feedback must be found")
            assertTrue(uiFeedback.all { it.category == "UI" },
                "UC11: All feedback must be in correct category")
        }
        
        @Test
        @DisplayName("UC11-REQ-23: System MUST get top rated feedback")
        fun `system gets top rated feedback correctly`() {
            // Given: Feedback with different ratings
            useCase.submitFeedback("user1", FeedbackType.GENERAL, "Feedback 1", 5)
            useCase.submitFeedback("user2", FeedbackType.GENERAL, "Feedback 2", 3)
            useCase.submitFeedback("user3", FeedbackType.GENERAL, "Feedback 3", 4)
            
            // When: System gets top rated feedback
            val topRated = useCase.getTopRatedFeedback(limit = 2)
            
            // Then: Top rated feedback should be returned
            assertNotNull(topRated, "UC11: Top rated feedback must be retrieved")
            assertTrue(topRated.size <= 2, "UC11: Top rated feedback must respect limit")
            assertTrue(topRated.isNotEmpty(), "UC11: Top rated feedback must be found")
            if (topRated.size > 1) {
                assertTrue(topRated[0].rating!! >= topRated[1].rating!!,
                    "UC11: Top rated feedback must be sorted by rating")
            }
        }
    }

    @Nested
    @DisplayName("Test Case 6: Feedback Trends")
    inner class TrendsTests {
        
        @Test
        @DisplayName("UC11-REQ-24: System MUST track feedback trends")
        fun `system tracks feedback trends correctly`() {
            // Given: Multiple feedback submissions
            useCase.submitFeedback("user1", FeedbackType.BUG, "Bug 1")
            useCase.submitFeedback("user2", FeedbackType.FEATURE, "Feature 1")
            useCase.submitFeedback("user3", FeedbackType.GENERAL, "Feedback 1", 4)
            
            // When: System tracks trends
            val trends = useCase.getFeedbackTrends(days = 30)
            
            // Then: Trends should be tracked correctly
            assertNotNull(trends, "UC11: Trends must be tracked")
            assertEquals(30, trends.periodDays, "UC11: Period must be set correctly")
            assertTrue(trends.totalFeedback >= 3, "UC11: Total feedback must be tracked")
            assertNotNull(trends.typeDistribution, "UC11: Type distribution must be tracked")
        }
    }
}

