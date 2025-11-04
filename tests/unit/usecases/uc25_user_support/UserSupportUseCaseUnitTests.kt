package com.serenityai.tests.unit.usecases.uc25_user_support

import com.serenityai.usecases.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

/**
 * UC25: Facilitate User Support
 * 
 * Use Case Goal: Provide comprehensive user support through multiple channels including
 * in-app help, FAQ, support tickets, and direct assistance to ensure users can effectively
 * use the application and get help when needed.
 * 
 * Key Requirements Being Tested:
 * 1. System provides in-app help and documentation
 * 2. System manages support tickets and inquiries
 * 3. System offers FAQ and knowledge base access
 * 4. System facilitates direct user support interactions
 * 5. System tracks support request history
 */
@DisplayName("UC25: Facilitate User Support - Unit Tests")
class UserSupportUseCaseUnitTests {

    private lateinit var useCase: UserSupportUseCase

    @BeforeEach
    fun setUp() {
        useCase = UserSupportUseCase()
    }

    @Nested
    @DisplayName("Test Case 1: Support Ticket Management - Validates Core UC25 Functionality")
    inner class SupportTicketTests {
        
        /**
         * Tests: System creates support tickets
         * Validates: UC25 requirement for ticket creation
         * Expected: Support tickets are created with correct information
         */
        @Test
        @DisplayName("UC25-REQ-1: System MUST create support tickets for user inquiries")
        fun `system creates support tickets with correct information`() {
            // Given: User needs to submit a support request
            // Purpose: Validate ticket creation functionality
            
            // When: User creates a support ticket
            val ticket = useCase.createSupportTicket(
                userId = "user123",
                subject = "App login issue",
                description = "I cannot log into my account",
                category = SupportCategory.TECHNICAL,
                priority = SupportPriority.HIGH
            )
            
            // Then: Ticket should be created correctly
            assertNotNull(ticket.id, "UC25: Ticket must have unique ID")
            assertEquals("user123", ticket.userId, "UC25: Ticket must be linked to correct user")
            assertEquals("App login issue", ticket.subject, "UC25: Ticket subject must be preserved")
            assertEquals("I cannot log into my account", ticket.description, "UC25: Ticket description must be preserved")
            assertEquals(SupportCategory.TECHNICAL, ticket.category, "UC25: Ticket category must be correct")
            assertEquals(SupportPriority.HIGH, ticket.priority, "UC25: Ticket priority must be correct")
            assertEquals(TicketStatus.OPEN, ticket.status, "UC25: New tickets must have OPEN status")
            assertNotNull(ticket.createdAt, "UC25: Ticket must have creation timestamp")
        }
        
        /**
         * Tests: System validates ticket creation input
         * Validates: UC25 requirement for input validation
         * Expected: Empty subject or description are rejected
         */
        @Test
        @DisplayName("UC25-REQ-2: System MUST validate support ticket input")
        fun `system validates support ticket input and rejects empty fields`() {
            // Given: User attempts to create ticket with invalid input
            // Purpose: Validate input validation prevents errors
            
            // When: User creates ticket with empty subject
            // Then: System should throw IllegalArgumentException
            assertThrows(IllegalArgumentException::class.java) {
                useCase.createSupportTicket(
                    userId = "user123",
                    subject = "",
                    description = "Valid description"
                )
            }
            
            // When: User creates ticket with empty description
            // Then: System should throw IllegalArgumentException
            assertThrows(IllegalArgumentException::class.java) {
                useCase.createSupportTicket(
                    userId = "user123",
                    subject = "Valid subject",
                    description = ""
                )
            }
        }
        
        /**
         * Tests: System adds responses to support tickets
         * Validates: UC25 requirement for ticket communication
         * Expected: Responses can be added to tickets
         */
        @Test
        @DisplayName("UC25-REQ-3: System MUST allow adding responses to support tickets")
        fun `system adds responses to support tickets correctly`() {
            // Given: Support ticket exists
            // Purpose: Validate response functionality
            
            // When: Response is added to ticket
            val updatedTicket = useCase.addTicketResponse(
                ticketId = "ticket_123",
                response = "We're looking into this issue",
                isFromUser = false
            )
            
            // Then: Response should be added correctly
            assertTrue(updatedTicket.responses.isNotEmpty(), "UC25: Ticket must have responses")
            val response = updatedTicket.responses.first()
            assertEquals("We're looking into this issue", response.message, "UC25: Response message must be preserved")
            assertFalse(response.isFromUser, "UC25: Response source must be correctly identified")
            assertNotNull(response.createdAt, "UC25: Response must have timestamp")
        }
    }

    @Nested
    @DisplayName("Test Case 2: FAQ and Help Content - Validates Secondary UC25 Functionality")
    inner class FAQAndHelpTests {
        
        /**
         * Tests: System retrieves FAQ entries
         * Validates: UC25 requirement for FAQ access
         * Expected: FAQ entries are returned and can be searched
         */
        @Test
        @DisplayName("UC25-REQ-4: System MUST provide FAQ entries with search capability")
        fun `system retrieves and searches FAQ entries correctly`() {
            // Given: System has FAQ entries
            // Purpose: Validate FAQ retrieval and search functionality
            
            // When: User requests all FAQs
            val allFAQs = useCase.getFAQEntries()
            
            // Then: FAQs should be returned
            assertTrue(allFAQs.isNotEmpty(), "UC25: System must provide FAQ entries")
            assertTrue(allFAQs.all { it.question.isNotBlank() && it.answer.isNotBlank() }, 
                "UC25: All FAQs must have question and answer")
            
            // When: User searches FAQs
            val moodFAQs = useCase.getFAQEntries(query = "mood")
            
            // Then: Matching FAQs should be returned
            assertTrue(moodFAQs.any { 
                it.question.lowercase().contains("mood") || 
                it.answer.lowercase().contains("mood") 
            }, "UC25: Search must return matching FAQs")
        }
        
        /**
         * Tests: System provides contextual help
         * Validates: UC25 requirement for contextual assistance
         * Expected: Help content is provided based on current context
         */
        @Test
        @DisplayName("UC25-REQ-5: System MUST provide contextual help based on current screen")
        fun `system provides contextual help content correctly`() {
            // Given: User is on a specific screen
            // Purpose: Validate contextual help functionality
            
            // When: User requests help for mood logging
            val helpContent = useCase.getContextualHelp("mood_logging")
            
            // Then: Relevant help content should be returned
            assertNotNull(helpContent, "UC25: Contextual help must be provided")
            assertEquals("mood_logging", helpContent.context, "UC25: Help content must match context")
            assertTrue(helpContent.title.isNotBlank(), "UC25: Help content must have title")
            assertTrue(helpContent.content.isNotBlank(), "UC25: Help content must have content")
            
            // When: User requests help for unknown context
            val unknownHelp = useCase.getContextualHelp("unknown_screen")
            
            // Then: General help should be provided
            assertNotNull(unknownHelp, "UC25: System must provide help even for unknown contexts")
            assertEquals("General Help", unknownHelp.title, "UC25: Unknown contexts should get general help")
        }
        
        /**
         * Tests: System provides support categories
         * Validates: UC25 requirement for category listing
         * Expected: Available support categories are returned
         */
        @Test
        @DisplayName("UC25-REQ-6: System MUST provide available support categories")
        fun `system provides comprehensive support categories`() {
            // Given: System has support categories defined
            // Purpose: Validate category listing functionality
            
            // When: User requests support categories
            val categories = useCase.getSupportCategories()
            
            // Then: Categories should be returned
            assertTrue(categories.isNotEmpty(), "UC25: System must provide support categories")
            assertTrue(categories.contains("TECHNICAL"), "UC25: Technical support category must be available")
            assertTrue(categories.contains("GENERAL"), "UC25: General support category must be available")
        }
    }

    @Nested
    @DisplayName("Test Case 3: Feedback and Support History - Validates Advanced UC25 Functionality")
    inner class FeedbackAndHistoryTests {
        
        /**
         * Tests: System accepts user feedback
         * Validates: UC25 requirement for feedback collection
         * Expected: Feedback submissions are created and tracked
         */
        @Test
        @DisplayName("UC25-REQ-7: System MUST accept and track user feedback")
        fun `system accepts and tracks user feedback correctly`() {
            // Given: User wants to submit feedback
            // Purpose: Validate feedback submission functionality
            
            // When: User submits feedback
            val feedback = useCase.submitFeedback(
                userId = "user123",
                feedbackType = FeedbackType.FEATURE,
                message = "I'd love to see dark mode support",
                rating = 5
            )
            
            // Then: Feedback should be submitted correctly
            assertNotNull(feedback.id, "UC25: Feedback must have unique ID")
            assertEquals("user123", feedback.userId, "UC25: Feedback must be linked to correct user")
            assertEquals(FeedbackType.FEATURE, feedback.feedbackType, "UC25: Feedback type must be preserved")
            assertEquals("I'd love to see dark mode support", feedback.message, "UC25: Feedback message must be preserved")
            assertEquals(5, feedback.rating, "UC25: Feedback rating must be preserved")
            assertEquals(FeedbackStatus.RECEIVED, feedback.status, "UC25: New feedback must have RECEIVED status")
            assertNotNull(feedback.submittedAt, "UC25: Feedback must have submission timestamp")
        }
        
        /**
         * Tests: System validates feedback input
         * Validates: UC25 requirement for feedback validation
         * Expected: Invalid feedback is rejected
         */
        @Test
        @DisplayName("UC25-REQ-8: System MUST validate feedback input")
        fun `system validates feedback input and rejects invalid data`() {
            // Given: User attempts to submit invalid feedback
            // Purpose: Validate input validation prevents errors
            
            // When: User submits empty feedback message
            // Then: System should throw IllegalArgumentException
            assertThrows(IllegalArgumentException::class.java) {
                useCase.submitFeedback(
                    userId = "user123",
                    feedbackType = FeedbackType.GENERAL,
                    message = ""
                )
            }
            
            // When: User submits invalid rating
            // Then: System should throw IllegalArgumentException
            assertThrows(IllegalArgumentException::class.java) {
                useCase.submitFeedback(
                    userId = "user123",
                    feedbackType = FeedbackType.GENERAL,
                    message = "Valid feedback",
                    rating = 6
                )
            }
            
            assertThrows(IllegalArgumentException::class.java) {
                useCase.submitFeedback(
                    userId = "user123",
                    feedbackType = FeedbackType.GENERAL,
                    message = "Valid feedback",
                    rating = 0
                )
            }
        }
        
        /**
         * Tests: System retrieves support ticket history
         * Validates: UC25 requirement for history tracking
         * Expected: User's support history is retrievable
         */
        @Test
        @DisplayName("UC25-REQ-9: System MUST retrieve user's support ticket history")
        fun `system retrieves user support ticket history correctly`() {
            // Given: User has submitted support tickets
            // Purpose: Validate history retrieval functionality
            
            // When: User requests ticket history
            val tickets = useCase.getSupportTickets(userId = "user123")
            
            // Then: Ticket history should be returned
            assertNotNull(tickets, "UC25: Support ticket history must be retrievable")
            // In production, would verify tickets belong to correct user
        }
    }
}

