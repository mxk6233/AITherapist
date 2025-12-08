package com.serenityai.tests.uat.usecases.uc39_community_support

import com.serenityai.usecases.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested

/** UAT: UC39 - Community Support Circles - User Acceptance Tests validating community support circles from an end-user perspective. */
@DisplayName("UAT: UC39 - Community Support Circles")
class CommunitySupportCirclesUATTests {

    private val useCase = CommunitySupportCirclesUseCase()

    @Nested
    @DisplayName("User Story: Circle Creation and Joining")
    inner class CircleCreationAndJoining {
        
        @Test
        @DisplayName("As a user, I want to create support circles so I can build a community")
        fun `user can create support circles`() {
            // Given: User wants to create a circle
            val creatorId = "user-123"
            val circle = useCase.createSupportCircle(
                creatorId = creatorId,
                name = "Anxiety Support Circle",
                description = "A safe space for anxiety support",
                topic = "Anxiety Management"
            )
            
            // When: Circle is created
            val circleCreated = circle.id.isNotBlank()
            val creatorIsMember = useCase.getCircleMembers(circle.id).contains(creatorId)
            
            // Then: User creates circle successfully
            assertTrue(circleCreated, "User should be able to create circles")
            assertTrue(creatorIsMember, "Creator should be a member")
            assertEquals("Anxiety Support Circle", circle.name, "Circle name should be preserved")
        }
        
        @Test
        @DisplayName("As a user, I want to join support circles so I can connect with others")
        fun `user can join support circles`() {
            // Given: Public circle exists
            val creatorId = "creator-123"
            val userId = "user-123"
            val circle = useCase.createSupportCircle(
                creatorId = creatorId,
                name = "Public Circle",
                description = "Description",
                isPrivate = false
            )
            
            // When: User joins circle
            val canJoin = useCase.joinSupportCircle(circle.id, userId)
            
            // Then: User joins circle successfully
            assertTrue(canJoin, "User should be able to join public circles")
            assertTrue(useCase.getCircleMembers(circle.id).contains(userId), "User should be in members list")
        }
        
        @Test
        @DisplayName("As a user, I want to see available circles so I can find relevant communities")
        fun `user can see available circles`() {
            // Given: Multiple circles exist
            useCase.createSupportCircle("creator1", "Circle 1", "Description", isPrivate = false)
            useCase.createSupportCircle("creator2", "Circle 2", "Description", isPrivate = false)
            useCase.createSupportCircle("creator3", "Private Circle", "Description", isPrivate = true)
            
            // When: User browses available circles
            val availableCircles = useCase.getAvailableCircles()
            
            // Then: User sees public circles
            assertTrue(availableCircles.isNotEmpty(), "User should see available circles")
            assertTrue(availableCircles.all { !it.isPrivate }, "Only public circles should be shown")
        }
    }

    @Nested
    @DisplayName("User Story: Peer Interactions")
    inner class PeerInteractions {
        
        @Test
        @DisplayName("As a user, I want to post in circles so I can share my experiences")
        fun `user can post in circles`() {
            // Given: User is member of circle
            val creatorId = "creator-123"
            val userId = "user-123"
            val circle = useCase.createSupportCircle(creatorId, "Circle", "Description")
            useCase.joinSupportCircle(circle.id, userId)
            
            // When: User creates post
            val post = useCase.createPost(circle.id, userId, "I'm feeling anxious today")
            
            // Then: User posts successfully
            assertTrue(post.id.isNotBlank(), "User should be able to create posts")
            assertEquals("I'm feeling anxious today", post.content, "Post content should be preserved")
        }
        
        @Test
        @DisplayName("As a user, I want to comment on posts so I can provide support")
        fun `user can comment on posts`() {
            // Given: Post exists in circle
            val creatorId = "creator-123"
            val userId = "user-123"
            val circle = useCase.createSupportCircle(creatorId, "Circle", "Description")
            useCase.joinSupportCircle(circle.id, userId)
            val post = useCase.createPost(circle.id, userId, "Post content")
            
            // When: User adds comment
            val comment = useCase.addComment(post.id, userId, "Supportive comment")
            
            // Then: User comments successfully
            assertTrue(comment.id.isNotBlank(), "User should be able to add comments")
            assertEquals("Supportive comment", comment.content, "Comment content should be preserved")
        }
        
        @Test
        @DisplayName("As a user, I want to like posts so I can show support")
        fun `user can like posts`() {
            // Given: Post exists in circle
            val creatorId = "creator-123"
            val userId = "user-123"
            val circle = useCase.createSupportCircle(creatorId, "Circle", "Description")
            useCase.joinSupportCircle(circle.id, userId)
            val post = useCase.createPost(circle.id, userId, "Post content")
            
            // When: User likes post
            val likeResult = useCase.likePost(post.id, userId)
            
            // Then: User likes post successfully
            assertTrue(likeResult, "User should be able to like posts")
            val posts = useCase.getCirclePosts(circle.id)
            val updatedPost = posts.find { it.id == post.id }
            assertTrue(updatedPost!!.likeCount > 0, "Like count should be incremented")
        }
    }

    @Nested
    @DisplayName("User Story: Privacy and Invitations")
    inner class PrivacyAndInvitations {
        
        @Test
        @DisplayName("As a user, I want to create private circles so I can control access")
        fun `user can create private circles`() {
            // Given: User wants to create private circle
            val creatorId = "creator-123"
            val circle = useCase.createSupportCircle(
                creatorId = creatorId,
                name = "Private Circle",
                description = "Description",
                isPrivate = true
            )
            
            // When: Circle is created
            val isPrivate = circle.isPrivate
            
            // Then: Private circle is created
            assertTrue(isPrivate, "User should be able to create private circles")
        }
        
        @Test
        @DisplayName("As a user, I want to invite others to private circles so I can build my community")
        fun `user can invite others to private circles`() {
            // Given: Private circle exists
            val creatorId = "creator-123"
            val inviteeId = "invitee-123"
            val circle = useCase.createSupportCircle(
                creatorId = creatorId,
                name = "Private Circle",
                description = "Description",
                isPrivate = true
            )
            
            // When: Creator invites user
            val inviteResult = useCase.inviteToCircle(circle.id, creatorId, inviteeId)
            
            // Then: Invitation is sent successfully
            assertTrue(inviteResult, "User should be able to send invitations")
            val invitations = useCase.getPendingInvitations(inviteeId)
            assertTrue(invitations.any { it.id == circle.id }, "Invitee should see invitation")
        }
    }
}

