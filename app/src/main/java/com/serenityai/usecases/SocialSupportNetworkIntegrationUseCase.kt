package com.serenityai.usecases

import com.serenityai.data.models.*
import java.util.Date

/** UC31: Social support network integration enabling users to connect with friends, join support groups, and share progress with privacy controls. */
class SocialSupportNetworkIntegrationUseCase {
    
    private val friendships = mutableMapOf<String, MutableSet<String>>() // userId -> friendIds
    private val friendRequests = mutableMapOf<String, MutableSet<String>>() // userId -> pending request senderIds
    private val supportGroups = mutableMapOf<String, SupportGroup>() // groupId -> group
    private val groupMemberships = mutableMapOf<String, MutableSet<String>>() // userId -> groupIds
    private val groupMembers = mutableMapOf<String, MutableSet<String>>() // groupId -> userIds
    private val sharedProgress = mutableMapOf<String, MutableList<SharedProgress>>() // userId -> shared progress entries
    private val encouragementMessages = mutableMapOf<String, MutableList<EncouragementMessage>>() // userId -> messages
    
    /**
     * Sends a friend request
     * 
     * @param fromUserId Sender user ID
     * @param toUserId Recipient user ID
     * @return True if request sent successfully
     */
    fun sendFriendRequest(fromUserId: String, toUserId: String): Boolean {
        require(fromUserId != toUserId) { "Cannot send friend request to yourself" }
        
        // Check if already friends
        if (friendships[fromUserId]?.contains(toUserId) == true) {
            return false
        }
        
        // Check if request already exists
        val pendingRequests = friendRequests[toUserId] ?: mutableSetOf()
        if (pendingRequests.contains(fromUserId)) {
            return false
        }
        
        pendingRequests.add(fromUserId)
        friendRequests[toUserId] = pendingRequests
        
        return true
    }
    
    /**
     * Accepts a friend request
     * 
     * @param userId User ID accepting the request
     * @param fromUserId Sender user ID
     * @return True if request accepted successfully
     */
    fun acceptFriendRequest(userId: String, fromUserId: String): Boolean {
        val pendingRequests = friendRequests[userId] ?: return false
        
        if (!pendingRequests.contains(fromUserId)) {
            return false
        }
        
        // Remove from pending requests
        pendingRequests.remove(fromUserId)
        friendRequests[userId] = pendingRequests
        
        // Add to friendships (bidirectional)
        val userFriends = friendships[userId] ?: mutableSetOf()
        userFriends.add(fromUserId)
        friendships[userId] = userFriends
        
        val fromUserFriends = friendships[fromUserId] ?: mutableSetOf()
        fromUserFriends.add(userId)
        friendships[fromUserId] = fromUserFriends
        
        return true
    }
    
    /**
     * Removes a friend
     * 
     * @param userId User ID
     * @param friendId Friend ID to remove
     * @return True if friend removed successfully
     */
    fun removeFriend(userId: String, friendId: String): Boolean {
        friendships[userId]?.remove(friendId)
        friendships[friendId]?.remove(userId)
        return true
    }
    
    /**
     * Gets user's friends
     * 
     * @param userId User ID
     * @return List of friend user IDs
     */
    fun getFriends(userId: String): List<String> {
        return friendships[userId]?.toList() ?: emptyList()
    }
    
    /**
     * Gets pending friend requests for a user
     * 
     * @param userId User ID
     * @return List of user IDs who sent friend requests
     */
    fun getPendingFriendRequests(userId: String): List<String> {
        return friendRequests[userId]?.toList() ?: emptyList()
    }
    
    /**
     * Creates a support group
     * 
     * @param creatorId Creator user ID
     * @param name Group name
     * @param description Group description
     * @param isPrivate Whether group is private
     * @return SupportGroup object
     */
    fun createSupportGroup(
        creatorId: String,
        name: String,
        description: String,
        isPrivate: Boolean = false
    ): SupportGroup {
        require(name.isNotBlank()) { "Group name cannot be empty" }
        require(description.isNotBlank()) { "Group description cannot be empty" }
        
        val group = SupportGroup(
            id = "group_${System.currentTimeMillis()}",
            name = name,
            description = description,
            creatorId = creatorId,
            isPrivate = isPrivate,
            createdAt = Date(),
            memberCount = 1
        )
        
        supportGroups[group.id] = group
        
        // Add creator as member
        val userGroups = groupMemberships[creatorId] ?: mutableSetOf()
        userGroups.add(group.id)
        groupMemberships[creatorId] = userGroups
        
        val groupMembersSet = groupMembers[group.id] ?: mutableSetOf()
        groupMembersSet.add(creatorId)
        groupMembers[group.id] = groupMembersSet
        
        return group
    }
    
    /**
     * Joins a support group
     * 
     * @param userId User ID
     * @param groupId Group ID
     * @return True if joined successfully
     */
    fun joinSupportGroup(userId: String, groupId: String): Boolean {
        val group = supportGroups[groupId] ?: return false
        
        // Check if already a member
        if (groupMembers[groupId]?.contains(userId) == true) {
            return false
        }
        
        // Add to group memberships
        val userGroups = groupMemberships[userId] ?: mutableSetOf()
        userGroups.add(groupId)
        groupMemberships[userId] = userGroups
        
        // Add to group members
        val groupMembersSet = groupMembers[groupId] ?: mutableSetOf()
        groupMembersSet.add(userId)
        groupMembers[groupId] = groupMembersSet
        
        // Update member count
        val updatedGroup = group.copy(memberCount = groupMembersSet.size)
        supportGroups[groupId] = updatedGroup
        
        return true
    }
    
    /**
     * Leaves a support group
     * 
     * @param userId User ID
     * @param groupId Group ID
     * @return True if left successfully
     */
    fun leaveSupportGroup(userId: String, groupId: String): Boolean {
        groupMemberships[userId]?.remove(groupId)
        groupMembers[groupId]?.remove(userId)
        
        // Update member count
        val group = supportGroups[groupId] ?: return false
        val updatedGroup = group.copy(memberCount = groupMembers[groupId]?.size ?: 0)
        supportGroups[groupId] = updatedGroup
        
        return true
    }
    
    /**
     * Gets user's support groups
     * 
     * @param userId User ID
     * @return List of SupportGroup objects
     */
    fun getUserSupportGroups(userId: String): List<SupportGroup> {
        val groupIds = groupMemberships[userId] ?: return emptyList()
        return groupIds.mapNotNull { supportGroups[it] }
    }
    
    /**
     * Shares progress with friends
     * 
     * @param userId User ID
     * @param progressMessage Progress message
     * @param shareWithFriends Whether to share with all friends
     * @param friendIds Specific friend IDs to share with (if not sharing with all)
     * @return SharedProgress object
     */
    fun shareProgress(
        userId: String,
        progressMessage: String,
        shareWithFriends: Boolean = true,
        friendIds: List<String>? = null
    ): SharedProgress {
        require(progressMessage.isNotBlank()) { "Progress message cannot be empty" }
        
        val shared = SharedProgress(
            id = "share_${System.currentTimeMillis()}",
            userId = userId,
            message = progressMessage,
            sharedAt = Date(),
            shareWithFriends = shareWithFriends,
            specificFriendIds = friendIds ?: emptyList()
        )
        
        val shares = sharedProgress[userId] ?: mutableListOf()
        shares.add(shared)
        sharedProgress[userId] = shares
        
        return shared
    }
    
    /**
     * Sends an encouragement message
     * 
     * @param fromUserId Sender user ID
     * @param toUserId Recipient user ID
     * @param message Encouragement message
     * @return EncouragementMessage object
     */
    fun sendEncouragement(
        fromUserId: String,
        toUserId: String,
        message: String
    ): EncouragementMessage {
        require(message.isNotBlank()) { "Message cannot be empty" }
        
        // Verify they are friends
        if (friendships[fromUserId]?.contains(toUserId) != true) {
            throw IllegalArgumentException("Can only send encouragement to friends")
        }
        
        val encouragement = EncouragementMessage(
            id = "encourage_${System.currentTimeMillis()}",
            fromUserId = fromUserId,
            toUserId = toUserId,
            message = message,
            sentAt = Date()
        )
        
        val messages = encouragementMessages[toUserId] ?: mutableListOf()
        messages.add(encouragement)
        encouragementMessages[toUserId] = messages
        
        return encouragement
    }
    
    /**
     * Gets encouragement messages for a user
     * 
     * @param userId User ID
     * @return List of EncouragementMessage objects
     */
    fun getEncouragementMessages(userId: String): List<EncouragementMessage> {
        return encouragementMessages[userId] ?: emptyList()
    }
}

/**
 * Data class for support group
 */
data class SupportGroup(
    val id: String,
    val name: String,
    val description: String,
    val creatorId: String,
    val isPrivate: Boolean,
    val createdAt: Date,
    val memberCount: Int
)

/**
 * Data class for shared progress
 */
data class SharedProgress(
    val id: String,
    val userId: String,
    val message: String,
    val sharedAt: Date,
    val shareWithFriends: Boolean,
    val specificFriendIds: List<String>
)

/**
 * Data class for encouragement message
 */
data class EncouragementMessage(
    val id: String,
    val fromUserId: String,
    val toUserId: String,
    val message: String,
    val sentAt: Date
)


