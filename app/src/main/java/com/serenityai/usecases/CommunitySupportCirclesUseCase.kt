package com.serenityai.usecases

import com.serenityai.data.models.*
import java.util.Date

/** UC39: Community support circles enabling users to create and participate in peer support circles for shared experiences and mutual support. */
class CommunitySupportCirclesUseCase {
    
    private val supportCircles = mutableMapOf<String, SupportCircle>()
    private val circleMemberships = mutableMapOf<String, MutableSet<String>>() // userId -> circleIds
    private val circleMembers = mutableMapOf<String, MutableSet<String>>() // circleId -> userIds
    private val circlePosts = mutableMapOf<String, MutableList<CirclePost>>() // circleId -> posts
    private val circleComments = mutableMapOf<String, MutableList<CircleComment>>() // postId -> comments
    private val circleInvitations = mutableMapOf<String, MutableSet<String>>() // userId -> pending circleIds
    private var circleIdCounter = 0L
    
    /**
     * Creates a new support circle
     * 
     * @param creatorId User ID of the circle creator
     * @param name Circle name
     * @param description Circle description
     * @param topic Optional topic/focus area
     * @param isPrivate Whether the circle is private (invite-only)
     * @param maxMembers Maximum number of members (default: 50)
     * @return SupportCircle object
     */
    fun createSupportCircle(
        creatorId: String,
        name: String,
        description: String,
        topic: String? = null,
        isPrivate: Boolean = false,
        maxMembers: Int = 50
    ): SupportCircle {
        require(name.isNotBlank()) { "Circle name cannot be empty" }
        require(description.isNotBlank()) { "Circle description cannot be empty" }
        require(maxMembers > 0) { "Max members must be greater than 0" }
        
        val circle = SupportCircle(
            id = "circle_${System.currentTimeMillis()}_${++circleIdCounter}",
            name = name,
            description = description,
            creatorId = creatorId,
            topic = topic,
            isPrivate = isPrivate,
            maxMembers = maxMembers,
            memberCount = 1,
            status = CircleStatus.ACTIVE,
            createdAt = Date(),
            updatedAt = Date()
        )
        
        supportCircles[circle.id] = circle
        
        // Add creator as member
        val members = mutableSetOf(creatorId)
        circleMembers[circle.id] = members
        
        // Add circle to creator's memberships
        val userCircles = circleMemberships[creatorId] ?: mutableSetOf()
        userCircles.add(circle.id)
        circleMemberships[creatorId] = userCircles
        
        return circle
    }
    
    /**
     * Joins a user to a support circle
     * 
     * @param circleId Circle ID
     * @param userId User ID joining the circle
     * @return Boolean indicating success
     */
    fun joinSupportCircle(circleId: String, userId: String): Boolean {
        val circle = supportCircles[circleId] ?: return false
        
        if (circle.status != CircleStatus.ACTIVE) {
            return false
        }
        
        if (circle.isPrivate) {
            // Check if user has invitation
            val invitations = circleInvitations[userId] ?: return false
            if (!invitations.contains(circleId)) {
                return false
            }
            invitations.remove(circleId)
        }
        
        val members = circleMembers[circleId] ?: mutableSetOf()
        if (members.size >= circle.maxMembers) {
            return false // Circle is full
        }
        
        if (members.contains(userId)) {
            return true // Already a member
        }
        
        members.add(userId)
        circleMembers[circleId] = members
        
        // Update circle member count
        val updatedCircle = circle.copy(
            memberCount = members.size,
            updatedAt = Date()
        )
        supportCircles[circleId] = updatedCircle
        
        // Add circle to user's memberships
        val userCircles = circleMemberships[userId] ?: mutableSetOf()
        userCircles.add(circleId)
        circleMemberships[userId] = userCircles
        
        return true
    }
    
    /**
     * Leaves a support circle
     * 
     * @param circleId Circle ID
     * @param userId User ID leaving the circle
     * @return Boolean indicating success
     */
    fun leaveSupportCircle(circleId: String, userId: String): Boolean {
        val circle = supportCircles[circleId] ?: return false
        
        if (userId == circle.creatorId) {
            return false // Creator cannot leave
        }
        
        val members = circleMembers[circleId] ?: return false
        if (!members.contains(userId)) {
            return false
        }
        
        members.remove(userId)
        circleMembers[circleId] = members
        
        // Update circle member count
        val updatedCircle = circle.copy(
            memberCount = members.size,
            updatedAt = Date()
        )
        supportCircles[circleId] = updatedCircle
        
        // Remove circle from user's memberships
        circleMemberships[userId]?.remove(circleId)
        
        return true
    }
    
    /**
     * Invites a user to a private circle
     * 
     * @param circleId Circle ID
     * @param inviterId User ID sending the invitation
     * @param inviteeId User ID being invited
     * @return Boolean indicating success
     */
    fun inviteToCircle(circleId: String, inviterId: String, inviteeId: String): Boolean {
        val circle = supportCircles[circleId] ?: return false
        
        if (!circle.isPrivate) {
            return false // Only private circles require invitations
        }
        
        val members = circleMembers[circleId] ?: return false
        if (!members.contains(inviterId)) {
            return false // Inviter must be a member
        }
        
        if (members.contains(inviteeId)) {
            return false // Already a member
        }
        
        val invitations = circleInvitations[inviteeId] ?: mutableSetOf()
        invitations.add(circleId)
        circleInvitations[inviteeId] = invitations
        
        return true
    }
    
    /**
     * Creates a post in a support circle
     * 
     * @param circleId Circle ID
     * @param userId User ID creating the post
     * @param content Post content
     * @param isAnonymous Whether the post is anonymous
     * @return CirclePost object
     */
    fun createPost(
        circleId: String,
        userId: String,
        content: String,
        isAnonymous: Boolean = false
    ): CirclePost {
        require(content.isNotBlank()) { "Post content cannot be empty" }
        
        val circle = supportCircles[circleId] ?: throw IllegalArgumentException("Circle not found")
        val members = circleMembers[circleId] ?: throw IllegalArgumentException("Circle has no members")
        
        if (!members.contains(userId)) {
            throw IllegalArgumentException("User is not a member of this circle")
        }
        
        val post = CirclePost(
            id = "post_${System.currentTimeMillis()}",
            circleId = circleId,
            authorId = userId,
            content = content,
            isAnonymous = isAnonymous,
            likeCount = 0,
            commentCount = 0,
            createdAt = Date()
        )
        
        val posts = circlePosts[circleId] ?: mutableListOf()
        posts.add(post)
        circlePosts[circleId] = posts
        
        return post
    }
    
    /**
     * Adds a comment to a post
     * 
     * @param postId Post ID
     * @param userId User ID adding the comment
     * @param content Comment content
     * @return CircleComment object
     */
    fun addComment(postId: String, userId: String, content: String): CircleComment {
        require(content.isNotBlank()) { "Comment content cannot be empty" }
        
        // Find the post
        val post = circlePosts.values.flatten().find { it.id == postId }
            ?: throw IllegalArgumentException("Post not found")
        
        val circle = supportCircles[post.circleId] ?: throw IllegalArgumentException("Circle not found")
        val members = circleMembers[post.circleId] ?: throw IllegalArgumentException("Circle has no members")
        
        if (!members.contains(userId)) {
            throw IllegalArgumentException("User is not a member of this circle")
        }
        
        val comment = CircleComment(
            id = "comment_${System.currentTimeMillis()}",
            postId = postId,
            authorId = userId,
            content = content,
            createdAt = Date()
        )
        
        val comments = circleComments[postId] ?: mutableListOf()
        comments.add(comment)
        circleComments[postId] = comments
        
        // Update post comment count
        val posts = circlePosts[post.circleId] ?: mutableListOf()
        val postIndex = posts.indexOfFirst { it.id == postId }
        if (postIndex >= 0) {
            val updatedPost = posts[postIndex].copy(commentCount = comments.size)
            posts[postIndex] = updatedPost
            circlePosts[post.circleId] = posts
        }
        
        return comment
    }
    
    /**
     * Likes a post
     * 
     * @param postId Post ID
     * @param userId User ID liking the post
     * @return Boolean indicating success
     */
    fun likePost(postId: String, userId: String): Boolean {
        val post = circlePosts.values.flatten().find { it.id == postId } ?: return false
        
        val circle = supportCircles[post.circleId] ?: return false
        val members = circleMembers[post.circleId] ?: return false
        
        if (!members.contains(userId)) {
            return false
        }
        
        val posts = circlePosts[post.circleId] ?: return false
        val postIndex = posts.indexOfFirst { it.id == postId }
        if (postIndex >= 0) {
            val updatedPost = posts[postIndex].copy(likeCount = posts[postIndex].likeCount + 1)
            posts[postIndex] = updatedPost
            circlePosts[post.circleId] = posts
            return true
        }
        
        return false
    }
    
    /**
     * Gets user's support circles
     * 
     * @param userId User ID
     * @return List of SupportCircle objects
     */
    fun getUserSupportCircles(userId: String): List<SupportCircle> {
        val circleIds = circleMemberships[userId] ?: return emptyList()
        return circleIds.mapNotNull { supportCircles[it] }
    }
    
    /**
     * Gets available public circles
     * 
     * @param topic Optional topic filter
     * @return List of SupportCircle objects
     */
    fun getAvailableCircles(topic: String? = null): List<SupportCircle> {
        val publicCircles = supportCircles.values.filter { 
            !it.isPrivate && it.status == CircleStatus.ACTIVE 
        }
        
        return if (topic != null) {
            publicCircles.filter { it.topic?.equals(topic, ignoreCase = true) == true }
        } else {
            publicCircles
        }
    }
    
    /**
     * Gets circle members
     * 
     * @param circleId Circle ID
     * @return List of user IDs
     */
    fun getCircleMembers(circleId: String): List<String> {
        return circleMembers[circleId]?.toList() ?: emptyList()
    }
    
    /**
     * Gets posts for a circle
     * 
     * @param circleId Circle ID
     * @return List of CirclePost objects
     */
    fun getCirclePosts(circleId: String): List<CirclePost> {
        return circlePosts[circleId]?.sortedByDescending { it.createdAt } ?: emptyList()
    }
    
    /**
     * Gets comments for a post
     * 
     * @param postId Post ID
     * @return List of CircleComment objects
     */
    fun getPostComments(postId: String): List<CircleComment> {
        return circleComments[postId]?.sortedBy { it.createdAt } ?: emptyList()
    }
    
    /**
     * Gets pending invitations for a user
     * 
     * @param userId User ID
     * @return List of SupportCircle objects
     */
    fun getPendingInvitations(userId: String): List<SupportCircle> {
        val circleIds = circleInvitations[userId] ?: return emptyList()
        return circleIds.mapNotNull { supportCircles[it] }
    }
}

/**
 * Data class for support circles
 */
data class SupportCircle(
    val id: String,
    val name: String,
    val description: String,
    val creatorId: String,
    val topic: String? = null,
    val isPrivate: Boolean = false,
    val maxMembers: Int = 50,
    val memberCount: Int = 0,
    val status: CircleStatus = CircleStatus.ACTIVE,
    val createdAt: Date,
    val updatedAt: Date
)

enum class CircleStatus {
    ACTIVE,
    ARCHIVED,
    CLOSED
}

/**
 * Data class for circle posts
 */
data class CirclePost(
    val id: String,
    val circleId: String,
    val authorId: String,
    val content: String,
    val isAnonymous: Boolean = false,
    val likeCount: Int = 0,
    val commentCount: Int = 0,
    val createdAt: Date
)

/**
 * Data class for circle comments
 */
data class CircleComment(
    val id: String,
    val postId: String,
    val authorId: String,
    val content: String,
    val createdAt: Date
)

