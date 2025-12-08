package com.serenityai.usecases

import com.serenityai.data.models.*
import java.util.Date

// FeedbackType, FeedbackStatus, and FeedbackSubmission are defined in UserSupportUseCase.kt
// as top-level types in this package, so they're accessible without import

/** UC11: Application feedback submission system for users to submit feedback, bug reports, feature requests, and ratings. */
class ApplicationFeedbackUseCase {
    
    private val feedbackSubmissions = mutableMapOf<String, FeedbackSubmission>()
    private val feedbackAnalytics = mutableMapOf<String, FeedbackAnalytics>()
    
    /** Submits application feedback. */
    fun submitFeedback(
        userId: String,
        feedbackType: FeedbackType,
        message: String,
        rating: Int? = null,
        category: String? = null,
        attachments: List<String> = emptyList()
    ): FeedbackSubmission {
        require(message.isNotBlank()) { "Feedback message cannot be empty" }
        require(rating == null || rating in 1..5) { "Rating must be between 1 and 5" }
        
        val feedback = FeedbackSubmission(
            id = "feedback_${System.currentTimeMillis()}",
            userId = userId,
            feedbackType = feedbackType,
            message = message,
            rating = rating,
            category = category,
            attachments = attachments,
            submittedAt = Date(),
            status = FeedbackStatus.RECEIVED
        )
        
        feedbackSubmissions[feedback.id] = feedback
        updateAnalytics(userId, feedback)
        
        return feedback
    }
    
    /** Gets feedback submissions for a user. */
    fun getUserFeedback(userId: String): List<FeedbackSubmission> {
        return feedbackSubmissions.values.filter { it.userId == userId }
            .sortedByDescending { it.submittedAt }
    }
    
    /** Gets feedback by ID. */
    fun getFeedback(feedbackId: String): FeedbackSubmission? {
        return feedbackSubmissions[feedbackId]
    }
    
    /** Gets all feedback submissions. */
    fun getAllFeedback(feedbackType: FeedbackType? = null, status: FeedbackStatus? = null): List<FeedbackSubmission> {
        return feedbackSubmissions.values
            .filter { feedbackType == null || it.feedbackType == feedbackType }
            .filter { status == null || it.status == status }
            .sortedByDescending { it.submittedAt }
    }
    
    /** Updates feedback status. */
    fun updateFeedbackStatus(feedbackId: String, status: FeedbackStatus, adminNotes: String? = null): FeedbackSubmission? {
        val feedback = feedbackSubmissions[feedbackId] ?: return null
        
        val updated = feedback.copy(
            status = status,
            adminNotes = adminNotes,
            updatedAt = Date()
        )
        
        feedbackSubmissions[feedbackId] = updated
        return updated
    }
    
    /** Gets feedback analytics. */
    fun getFeedbackAnalytics(userId: String? = null): FeedbackAnalytics {
        val relevantFeedback = if (userId != null) {
            feedbackSubmissions.values.filter { it.userId == userId }
        } else {
            feedbackSubmissions.values
        }
        
        val totalFeedback = relevantFeedback.size
        val byType = relevantFeedback.groupBy { it.feedbackType }.mapValues { it.value.size }
        val byStatus = relevantFeedback.groupBy { it.status }.mapValues { it.value.size }
        val averageRating = relevantFeedback.mapNotNull { it.rating }.average().let { 
            if (it.isNaN()) null else it.toFloat()
        }
        
        return FeedbackAnalytics(
            totalFeedback = totalFeedback,
            feedbackByType = byType,
            feedbackByStatus = byStatus,
            averageRating = averageRating,
            recentFeedback = relevantFeedback.take(10)
        )
    }
    
    /** Searches feedback by keyword. */
    fun searchFeedback(query: String): List<FeedbackSubmission> {
        val lowerQuery = query.lowercase()
        return feedbackSubmissions.values.filter {
            it.message.lowercase().contains(lowerQuery) ||
            it.category?.lowercase()?.contains(lowerQuery) == true
        }.sortedByDescending { it.submittedAt }
    }
    
    /** Gets feedback by category. */
    fun getFeedbackByCategory(category: String): List<FeedbackSubmission> {
        return feedbackSubmissions.values
            .filter { it.category == category }
            .sortedByDescending { it.submittedAt }
    }
    
    /** Gets feedback statistics. */
    fun getFeedbackStatistics(): FeedbackStatistics {
        val allFeedback = feedbackSubmissions.values
        val total = allFeedback.size
        val bugReports = allFeedback.count { it.feedbackType == FeedbackType.BUG }
        val featureRequests = allFeedback.count { it.feedbackType == FeedbackType.FEATURE }
        val complaints = allFeedback.count { it.feedbackType == FeedbackType.COMPLAINT }
        val compliments = allFeedback.count { it.feedbackType == FeedbackType.COMPLIMENT }
        val averageRating = allFeedback.mapNotNull { it.rating }.average().let {
            if (it.isNaN()) null else it.toFloat()
        }
        
        return FeedbackStatistics(
            totalSubmissions = total,
            bugReports = bugReports,
            featureRequests = featureRequests,
            complaints = complaints,
            compliments = compliments,
            averageRating = averageRating,
            pendingReview = allFeedback.count { it.status == FeedbackStatus.RECEIVED },
            implemented = allFeedback.count { it.status == FeedbackStatus.IMPLEMENTED }
        )
    }
    
    /** Marks feedback as implemented. */
    fun markAsImplemented(feedbackId: String, implementationNotes: String? = null): FeedbackSubmission? {
        return updateFeedbackStatus(feedbackId, FeedbackStatus.IMPLEMENTED, implementationNotes)
    }
    
    /** Marks feedback as declined. */
    fun markAsDeclined(feedbackId: String, declineReason: String? = null): FeedbackSubmission? {
        return updateFeedbackStatus(feedbackId, FeedbackStatus.DECLINED, declineReason)
    }
    
    /** Gets pending feedback for review. */
    fun getPendingFeedback(): List<FeedbackSubmission> {
        return feedbackSubmissions.values
            .filter { it.status == FeedbackStatus.RECEIVED }
            .sortedBy { it.submittedAt }
    }
    
    /** Gets top feedback by rating. */
    fun getTopRatedFeedback(limit: Int = 10): List<FeedbackSubmission> {
        return feedbackSubmissions.values
            .filter { it.rating != null }
            .sortedByDescending { it.rating }
            .take(limit)
    }
    
    /** Gets feedback trends over time. */
    fun getFeedbackTrends(days: Int = 30): FeedbackTrends {
        val cutoffDate = Date(System.currentTimeMillis() - (days * 24 * 60 * 60 * 1000L))
        val recentFeedback = feedbackSubmissions.values.filter { it.submittedAt.after(cutoffDate) }
        
        val dailyCounts = recentFeedback.groupBy {
            it.submittedAt.toString().substring(0, 10)
        }.mapValues { it.value.size }
        
        return FeedbackTrends(
            periodDays = days,
            totalFeedback = recentFeedback.size,
            dailyCounts = dailyCounts,
            typeDistribution = recentFeedback.groupBy { it.feedbackType }.mapValues { it.value.size },
            averageRating = recentFeedback.mapNotNull { it.rating }.average().let {
                if (it.isNaN()) null else it.toFloat()
            }
        )
    }
    
    // Helper methods
    private fun updateAnalytics(userId: String, feedback: FeedbackSubmission) {
        val analytics = feedbackAnalytics.getOrPut(userId) {
            FeedbackAnalytics(
                totalFeedback = 0,
                feedbackByType = emptyMap(),
                feedbackByStatus = emptyMap(),
                averageRating = null,
                recentFeedback = emptyList()
            )
        }
        
        val updatedFeedback = getUserFeedback(userId)
        feedbackAnalytics[userId] = getFeedbackAnalytics(userId)
    }
}

/** Data class for feedback analytics. */
data class FeedbackAnalytics(
    val totalFeedback: Int,
    val feedbackByType: Map<FeedbackType, Int>,
    val feedbackByStatus: Map<FeedbackStatus, Int>,
    val averageRating: Float?,
    val recentFeedback: List<FeedbackSubmission>
)

/** Data class for feedback statistics. */
data class FeedbackStatistics(
    val totalSubmissions: Int,
    val bugReports: Int,
    val featureRequests: Int,
    val complaints: Int,
    val compliments: Int,
    val averageRating: Float?,
    val pendingReview: Int,
    val implemented: Int
)

/** Data class for feedback trends. */
data class FeedbackTrends(
    val periodDays: Int,
    val totalFeedback: Int,
    val dailyCounts: Map<String, Int>,
    val typeDistribution: Map<FeedbackType, Int>,
    val averageRating: Float?
)

