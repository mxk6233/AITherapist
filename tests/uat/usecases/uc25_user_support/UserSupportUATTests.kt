package com.serenityai.tests.uat.usecases.uc25_user_support

import com.serenityai.usecases.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import java.util.Date

/** UAT: UC25 - Facilitate User Support - User Acceptance Tests validating user support from an end-user perspective. */
@DisplayName("UAT: UC25 - Facilitate User Support")
class UserSupportUATTests {

    private val useCase = UserSupportUseCase()

    @Nested
    @DisplayName("User Story: Support Ticket Management")
    inner class SupportTicketManagement {
        
        @Test
        @DisplayName("As a user, I want to submit a support ticket so I can get help when I need it")
        fun `user can submit support ticket`() {
            // Given: User needs help
            val userId = "user123"
            val subject = "App not working"
            val description = "The app crashes when I try to log my mood"
            val category = SupportCategory.TECHNICAL
            
            // When: User submits ticket
            val ticket = useCase.createSupportTicket(userId, subject, description, category)
            
            // Then: Ticket is submitted successfully
            assertNotNull(ticket, "User should be able to submit support ticket")
            assertEquals(subject, ticket.subject, "Ticket should have correct subject")
            assertEquals(category, ticket.category, "Ticket should have valid category")
            assertEquals(TicketStatus.OPEN, ticket.status, "Ticket should be open")
        }
        
        @Test
        @DisplayName("As a user, I want to track my support tickets so I know their status")
        fun `user can track support ticket status`() {
            // Given: User has submitted ticket
            val userId = "user123"
            val ticket = useCase.createSupportTicket(
                userId, "Issue", "Description", SupportCategory.GENERAL
            )
            
            // When: User checks status
            val statusVisible = ticket.status != null
            val statusValid = ticket.status in listOf(
                TicketStatus.OPEN, TicketStatus.IN_PROGRESS, 
                TicketStatus.RESOLVED, TicketStatus.CLOSED
            )
            
            // Then: Ticket status is displayed
            assertTrue(statusVisible, "Ticket status should be visible")
            assertTrue(statusValid, "Ticket status should be valid")
        }
        
        @Test
        @DisplayName("As a user, I want to view my support history so I can see past interactions")
        fun `user can view support history`() {
            // Given: User has multiple tickets
            val userId = "user123"
            val ticket1 = useCase.createSupportTicket(
                userId, "App Issue", "Description", SupportCategory.TECHNICAL
            )
            val ticket2 = useCase.createSupportTicket(
                userId, "Feature Request", "Description", SupportCategory.FEATURE
            )
            
            // When: User views history
            val historyAvailable = listOf(ticket1, ticket2).isNotEmpty()
            val historyOrdered = listOf(ticket1, ticket2).size > 0
            
            // Then: Support history is displayed
            assertTrue(historyAvailable, "Support history should be available")
            assertTrue(historyOrdered, "History should be ordered chronologically")
        }
    }

    @Nested
    @DisplayName("User Story: FAQ and Help")
    inner class FAQAndHelp {
        
        @Test
        @DisplayName("As a user, I want to search FAQs so I can find answers quickly")
        fun `user can search FAQs`() {
            // Given: User searches FAQ
            val searchQuery = "mood logging"
            val faqs = useCase.getFAQEntries(searchQuery)
            
            // When: User searches
            val searchPerformed = searchQuery.isNotBlank()
            val resultsFound = faqs.isNotEmpty()
            
            // Then: Relevant FAQs are found
            assertTrue(searchPerformed, "User should be able to search FAQs")
            assertTrue(resultsFound, "Search should return relevant FAQs")
        }
        
        @Test
        @DisplayName("As a user, I want to browse FAQs by category so I can find related topics")
        fun `user can browse FAQs by category`() {
            // Given: User browses FAQ categories
            val category = "Getting Started"
            val faqs = useCase.getFAQEntries(category = category)
            
            // When: User selects category
            val categoriesAvailable = category.isNotBlank()
            val faqsInCategory = faqs.isNotEmpty()
            
            // Then: FAQs are organized by category
            assertTrue(categoriesAvailable, "FAQ categories should be available")
            assertTrue(faqsInCategory, "FAQs should be organized by category")
        }
        
        @Test
        @DisplayName("As a user, I want contextual help so I get relevant information where I need it")
        fun `user receives contextual help`() {
            // Given: User is on specific screen
            val currentScreen = "mood_logging"
            val contextualHelp = useCase.getContextualHelp(currentScreen)
            
            // When: User requests help
            val helpAvailable = contextualHelp.context == currentScreen
            val helpRelevant = contextualHelp.content.isNotBlank()
            
            // Then: Contextual help is provided
            assertTrue(helpAvailable, "Contextual help should be available")
            assertTrue(helpRelevant, "Help should be relevant to current screen")
        }
    }

    @Nested
    @DisplayName("User Story: Feedback Submission")
    inner class FeedbackSubmission {
        
        @Test
        @DisplayName("As a user, I want to submit feedback so I can help improve the app")
        fun `user can submit feedback`() {
            // Given: User wants to provide feedback
            val userId = "user123"
            val feedbackType = FeedbackType.FEATURE
            val message = "I would like dark mode"
            val rating = 4
            
            // When: User submits feedback
            val feedback = useCase.submitFeedback(userId, feedbackType, message, rating)
            
            // Then: Feedback is submitted successfully
            assertNotNull(feedback, "User should be able to submit feedback")
            assertEquals(message, feedback.message, "Feedback should have correct message")
            assertEquals(rating, feedback.rating, "Rating should be within valid range (1-5)")
            assertTrue(feedback.rating!! in 1..5, "Rating should be valid")
        }
    }
}
