package com.serenityai.usecases

import com.serenityai.data.models.*
import java.util.Date

/** UC25: AI-powered user support system providing assistance through in-app help, FAQ, support tickets, and contextual assistance. */
class UserSupportUseCase {
    
    /**
     * Creates a new support ticket
     * 
     * @param userId User ID
     * @param subject Ticket subject
     * @param description Detailed description of the issue
     * @param category Support category (technical, feature, billing, etc.)
     * @param priority Priority level (low, medium, high, urgent)
     * @return SupportTicket object
     */
    fun createSupportTicket(
        userId: String,
        subject: String,
        description: String,
        category: SupportCategory = SupportCategory.GENERAL,
        priority: SupportPriority = SupportPriority.MEDIUM
    ): SupportTicket {
        require(subject.isNotBlank()) { "Subject cannot be empty" }
        require(description.isNotBlank()) { "Description cannot be empty" }
        
        return SupportTicket(
            id = "ticket_${System.currentTimeMillis()}",
            userId = userId,
            subject = subject,
            description = description,
            category = category,
            priority = priority,
            status = TicketStatus.OPEN,
            createdAt = Date(),
            updatedAt = Date(),
            responses = emptyList()
        )
    }
    
    /**
     * Gets user's support ticket history
     * 
     * @param userId User ID
     * @param status Filter by status (optional)
     * @return List of SupportTicket objects
     */
    fun getSupportTickets(
        userId: String,
        status: TicketStatus? = null
    ): List<SupportTicket> {
        // In production, would fetch from database
        return emptyList()
    }
    
    /**
     * Gets FAQ entries based on search query
     * 
     * @param query Search query (optional)
     * @param category Filter by category (optional)
     * @return List of FAQEntry objects
     */
    fun getFAQEntries(
        query: String? = null,
        category: String? = null
    ): List<FAQEntry> {
        val allFAQs = generateFAQEntries()
        
        // Filter by query
        val filteredByQuery = if (query != null && query.isNotBlank()) {
            val lowerQuery = query.lowercase()
            allFAQs.filter { faq ->
                faq.question.lowercase().contains(lowerQuery) ||
                faq.answer.lowercase().contains(lowerQuery) ||
                faq.tags.any { it.lowercase().contains(lowerQuery) }
            }
        } else {
            allFAQs
        }
        
        // Filter by category
        return if (category != null) {
            filteredByQuery.filter { it.category.equals(category, ignoreCase = true) }
        } else {
            filteredByQuery
        }
    }
    
    /**
     * Gets contextual help based on current screen/feature
     * 
     * @param context Context identifier (screen name, feature name, etc.)
     * @return HelpContent object with relevant information
     */
    fun getContextualHelp(context: String): HelpContent {
        val helpContents = generateHelpContents()
        
        return helpContents.find { it.context.equals(context, ignoreCase = true) }
            ?: HelpContent(
                context = context,
                title = "General Help",
                content = "For assistance with this feature, please refer to our FAQ or contact support.",
                links = emptyList()
            )
    }
    
    /**
     * Submits feedback about the application
     * 
     * @param userId User ID
     * @param feedbackType Type of feedback (bug, feature, general)
     * @param message Feedback message
     * @param rating Optional rating (1-5)
     * @return FeedbackSubmission object
     */
    fun submitFeedback(
        userId: String,
        feedbackType: FeedbackType,
        message: String,
        rating: Int? = null
    ): FeedbackSubmission {
        require(message.isNotBlank()) { "Feedback message cannot be empty" }
        if (rating != null) {
            require(rating in 1..5) { "Rating must be between 1 and 5" }
        }
        
        return FeedbackSubmission(
            id = "feedback_${System.currentTimeMillis()}",
            userId = userId,
            feedbackType = feedbackType,
            message = message,
            rating = rating,
            submittedAt = Date(),
            status = FeedbackStatus.RECEIVED
        )
    }
    
    /**
     * Gets available support categories
     * 
     * @return List of support category names
     */
    fun getSupportCategories(): List<String> {
        return SupportCategory.values().map { it.name.replace("_", " ") }
    }
    
    /**
     * Adds a response to a support ticket
     * 
     * @param ticketId Ticket ID
     * @param response Response message
     * @param isFromUser Whether response is from user (true) or support team (false)
     * @return Updated SupportTicket object
     */
    fun addTicketResponse(
        ticketId: String,
        response: String,
        isFromUser: Boolean
    ): SupportTicket {
        require(response.isNotBlank()) { "Response cannot be empty" }
        
        // In production, would fetch ticket, add response, and update
        val ticket = SupportTicket(
            id = ticketId,
            userId = "user123",
            subject = "Sample Ticket",
            description = "Sample description",
            category = SupportCategory.GENERAL,
            priority = SupportPriority.MEDIUM,
            status = TicketStatus.OPEN,
            createdAt = Date(),
            updatedAt = Date(),
            responses = listOf(
                TicketResponse(
                    id = "response_${System.currentTimeMillis()}",
                    message = response,
                    isFromUser = isFromUser,
                    createdAt = Date()
                )
            )
        )
        
        return ticket
    }
    
    // Private helper methods
    
    /**
     * Generates the canonical FAQ entries leveraged for mocks, previews, and offline testing.
     *
     * @return Collection of `FAQEntry` items representing common support questions.
     */
    private fun generateFAQEntries(): List<FAQEntry> {
        return listOf(
            FAQEntry(
                id = "faq_001",
                question = "How do I log my mood?",
                answer = "Navigate to the Mood screen and select your current mood level (1-5). You can add notes and tags to provide more context.",
                category = "Getting Started",
                tags = listOf("mood", "logging", "basics"),
                helpfulCount = 245,
                createdAt = Date()
            ),
            FAQEntry(
                id = "faq_002",
                question = "How does the AI therapist work?",
                answer = "Our AI therapist uses advanced natural language processing to provide supportive, empathetic responses. It's designed to complement professional therapy, not replace it.",
                category = "AI Features",
                tags = listOf("AI", "chat", "therapy"),
                helpfulCount = 189,
                createdAt = Date()
            ),
            FAQEntry(
                id = "faq_003",
                question = "Can I export my data?",
                answer = "Yes, you can export your mood data, chat history, and journal entries from the Settings screen. Data is exported in JSON format.",
                category = "Data & Privacy",
                tags = listOf("export", "data", "privacy"),
                helpfulCount = 156,
                createdAt = Date()
            ),
            FAQEntry(
                id = "faq_004",
                question = "What should I do in a crisis?",
                answer = "If you're experiencing a crisis, please contact emergency services immediately. The app includes crisis intervention features, but these are not a substitute for professional emergency care.",
                category = "Safety",
                tags = listOf("crisis", "emergency", "safety"),
                helpfulCount = 312,
                createdAt = Date()
            ),
            FAQEntry(
                id = "faq_005",
                question = "How do I reset my password?",
                answer = "Go to the Login screen and tap 'Forgot Password'. You'll receive an email with instructions to reset your password.",
                category = "Account",
                tags = listOf("password", "account", "login"),
                helpfulCount = 203,
                createdAt = Date()
            )
        )
    }
    
    /**
     * Creates contextual help snippets used to surface just-in-time guidance across the app.
     *
     * @return List of `HelpContent` definitions keyed by experience context identifiers.
     */
    private fun generateHelpContents(): List<HelpContent> {
        return listOf(
            HelpContent(
                context = "mood_logging",
                title = "Mood Logging Help",
                content = "Log your mood daily to track patterns over time. Select a mood level and optionally add notes about what's affecting your mood.",
                links = listOf("FAQ: How do I log my mood?", "Video Tutorial: Mood Tracking")
            ),
            HelpContent(
                context = "ai_chat",
                title = "AI Chat Help",
                content = "Chat with our AI therapist for immediate support. Be specific about your feelings and concerns for the best responses.",
                links = listOf("FAQ: How does the AI therapist work?", "Best Practices: Chat Tips")
            ),
            HelpContent(
                context = "exercises",
                title = "Coping Exercises Help",
                content = "Access evidence-based coping exercises tailored to your needs. Complete exercises regularly for best results.",
                links = listOf("Exercise Library", "FAQ: Using Exercises")
            )
        )
    }
}

/**
 * Data class for support tickets
 */
data class SupportTicket(
    val id: String,
    val userId: String,
    val subject: String,
    val description: String,
    val category: SupportCategory,
    val priority: SupportPriority,
    val status: TicketStatus,
    val createdAt: Date,
    val updatedAt: Date,
    val responses: List<TicketResponse>
)

/**
 * Data class for ticket responses
 */
data class TicketResponse(
    val id: String,
    val message: String,
    val isFromUser: Boolean,
    val createdAt: Date
)

/**
 * Data class for FAQ entries
 */
data class FAQEntry(
    val id: String,
    val question: String,
    val answer: String,
    val category: String,
    val tags: List<String>,
    val helpfulCount: Int,
    val createdAt: Date
)

/**
 * Data class for help content
 */
data class HelpContent(
    val context: String,
    val title: String,
    val content: String,
    val links: List<String>
)

/**
 * Data class for feedback submissions
 */
data class FeedbackSubmission(
    val id: String,
    val userId: String,
    val feedbackType: FeedbackType,
    val message: String,
    val rating: Int?,
    val category: String? = null,
    val attachments: List<String> = emptyList(),
    val adminNotes: String? = null,
    val submittedAt: Date,
    val updatedAt: Date? = null,
    val status: FeedbackStatus
)

/**
 * Support category enum
 */
enum class SupportCategory {
    GENERAL, TECHNICAL, FEATURE, BILLING, ACCOUNT, SAFETY
}

/**
 * Support priority enum
 */
enum class SupportPriority {
    LOW, MEDIUM, HIGH, URGENT
}

/**
 * Ticket status enum
 */
enum class TicketStatus {
    OPEN, IN_PROGRESS, RESOLVED, CLOSED
}

/**
 * Feedback type enum
 */
enum class FeedbackType {
    BUG, FEATURE, GENERAL, COMPLIMENT, COMPLAINT
}

/**
 * Feedback status enum
 */
enum class FeedbackStatus {
    RECEIVED, REVIEWED, IMPLEMENTED, DECLINED
}

