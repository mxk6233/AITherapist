package com.serenityai.tests.unit.usecases.uc39_community_support

import com.serenityai.usecases.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

/**
 * UC39: Community Support Circles
 * 
 * Use Case Goal: Enable users to create and participate in community support circles for peer support and shared experiences.
 * 
 * Key Requirements Being Tested:
 * 1. System creates support circles
 * 2. System allows users to join and leave circles
 * 3. System manages circle memberships
 * 4. System facilitates peer interactions through posts and comments
 * 5. System provides privacy controls for circles
 */
@DisplayName("UC39: Community Support Circles - Unit Tests")
class CommunitySupportCirclesUseCaseUnitTests {

    private lateinit var useCase: CommunitySupportCirclesUseCase

    @BeforeEach
    fun setUp() {
        useCase = CommunitySupportCirclesUseCase()
    }

    @Nested
    @DisplayName("Test Case 1: Circle Creation - Validates Core UC39 Functionality")
    inner class CircleCreationTests {
        
        @Test
        @DisplayName("UC39-REQ-1: System MUST create support circles")
        fun `system creates support circles correctly`() {
            // Given: User wants to create a support circle
            // Purpose: Validate circle creation functionality
            
            // When: User creates a circle
            val circle = useCase.createSupportCircle(
                creatorId = "user123",
                name = "Anxiety Support Circle",
                description = "A safe space for anxiety support",
                topic = "Anxiety Management",
                isPrivate = false,
                maxMembers = 50
            )
            
            // Then: Circle should be created correctly
            assertNotNull(circle.id, "UC39: Circle must have unique ID")
            assertEquals("Anxiety Support Circle", circle.name, "UC39: Circle name must be preserved")
            assertEquals("user123", circle.creatorId, "UC39: Creator must be set correctly")
            assertEquals("Anxiety Management", circle.topic, "UC39: Topic must be preserved")
            assertEquals(50, circle.maxMembers, "UC39: Max members must be set correctly")
            assertFalse(circle.isPrivate, "UC39: Public circle should not be private")
            assertEquals(1, circle.memberCount, "UC39: Creator should be counted as member")
        }
        
        @Test
        @DisplayName("UC39-REQ-2: System MUST validate circle creation input")
        fun `system validates circle creation input and rejects invalid data`() {
            // Given: User attempts to create circle with invalid input
            // Purpose: Validate input validation prevents errors
            
            // When: User creates circle with empty name
            // Then: System should throw IllegalArgumentException
            assertThrows(IllegalArgumentException::class.java) {
                useCase.createSupportCircle(
                    creatorId = "user123",
                    name = "",
                    description = "Valid description"
                )
            }
            
            // When: User creates circle with empty description
            // Then: System should throw IllegalArgumentException
            assertThrows(IllegalArgumentException::class.java) {
                useCase.createSupportCircle(
                    creatorId = "user123",
                    name = "Valid Name",
                    description = ""
                )
            }
            
            // When: User creates circle with invalid max members
            // Then: System should throw IllegalArgumentException
            assertThrows(IllegalArgumentException::class.java) {
                useCase.createSupportCircle(
                    creatorId = "user123",
                    name = "Valid Name",
                    description = "Valid description",
                    maxMembers = 0
                )
            }
        }
    }

    @Nested
    @DisplayName("Test Case 2: Circle Membership - Validates Secondary UC39 Functionality")
    inner class CircleMembershipTests {
        
        @Test
        @DisplayName("UC39-REQ-3: System MUST allow users to join support circles")
        fun `system allows users to join support circles correctly`() {
            // Given: Support circle exists
            // Purpose: Validate join functionality
            
            // When: User creates circle
            val circle = useCase.createSupportCircle(
                creatorId = "creator123",
                name = "Support Circle",
                description = "Description"
            )
            
            // When: Another user joins
            val joinResult = useCase.joinSupportCircle(circle.id, "user456")
            
            // Then: User should be able to join
            assertTrue(joinResult, "UC39: User should be able to join public circles")
            
            val members = useCase.getCircleMembers(circle.id)
            assertTrue(members.contains("user456"), "UC39: User should be in members list")
        }
        
        @Test
        @DisplayName("UC39-REQ-4: System MUST enforce private circle invitations")
        fun `system enforces private circle invitations correctly`() {
            // Given: Private circle exists
            // Purpose: Validate invitation requirement for private circles
            
            // When: User creates private circle
            val circle = useCase.createSupportCircle(
                creatorId = "creator123",
                name = "Private Circle",
                description = "Description",
                isPrivate = true
            )
            
            // When: User tries to join without invitation
            val joinResult = useCase.joinSupportCircle(circle.id, "user456")
            
            // Then: Join should fail
            assertFalse(joinResult, "UC39: Users cannot join private circles without invitation")
            
            // When: User is invited
            val inviteResult = useCase.inviteToCircle(circle.id, "creator123", "user456")
            
            // Then: Invitation should succeed
            assertTrue(inviteResult, "UC39: Invitation should succeed")
            
            // When: User joins after invitation
            val joinAfterInvite = useCase.joinSupportCircle(circle.id, "user456")
            
            // Then: Join should succeed
            assertTrue(joinAfterInvite, "UC39: User should be able to join after invitation")
        }
        
        @Test
        @DisplayName("UC39-REQ-5: System MUST allow users to leave support circles")
        fun `system allows users to leave support circles correctly`() {
            // Given: User is member of circle
            // Purpose: Validate leave functionality
            
            // When: Circle is created and user joins
            val circle = useCase.createSupportCircle(
                creatorId = "creator123",
                name = "Support Circle",
                description = "Description"
            )
            useCase.joinSupportCircle(circle.id, "user456")
            
            // When: User leaves
            val leaveResult = useCase.leaveSupportCircle(circle.id, "user456")
            
            // Then: Leave should succeed
            assertTrue(leaveResult, "UC39: User should be able to leave circle")
            
            val members = useCase.getCircleMembers(circle.id)
            assertFalse(members.contains("user456"), "UC39: User should not be in members list")
        }
        
        @Test
        @DisplayName("UC39-REQ-6: System MUST prevent creator from leaving circle")
        fun `system prevents creator from leaving circle`() {
            // Given: Circle with creator
            // Purpose: Validate creator cannot leave
            
            // When: Circle is created
            val circle = useCase.createSupportCircle(
                creatorId = "creator123",
                name = "Support Circle",
                description = "Description"
            )
            
            // When: Creator tries to leave
            val leaveResult = useCase.leaveSupportCircle(circle.id, "creator123")
            
            // Then: Leave should fail
            assertFalse(leaveResult, "UC39: Creator should not be able to leave circle")
        }
    }

    @Nested
    @DisplayName("Test Case 3: Peer Interactions - Validates Advanced UC39 Functionality")
    inner class PeerInteractionTests {
        
        @Test
        @DisplayName("UC39-REQ-7: System MUST allow users to create posts in circles")
        fun `system allows users to create posts correctly`() {
            // Given: User is member of circle
            // Purpose: Validate post creation functionality
            
            // When: Circle is created and user joins
            val circle = useCase.createSupportCircle(
                creatorId = "creator123",
                name = "Support Circle",
                description = "Description"
            )
            useCase.joinSupportCircle(circle.id, "user456")
            
            // When: User creates post
            val post = useCase.createPost(
                circleId = circle.id,
                userId = "user456",
                content = "I'm feeling anxious today",
                isAnonymous = false
            )
            
            // Then: Post should be created correctly
            assertNotNull(post.id, "UC39: Post must have unique ID")
            assertEquals(circle.id, post.circleId, "UC39: Post must be linked to circle")
            assertEquals("user456", post.authorId, "UC39: Author must be set correctly")
            assertEquals("I'm feeling anxious today", post.content, "UC39: Content must be preserved")
            assertFalse(post.isAnonymous, "UC39: Post should not be anonymous")
            assertEquals(0, post.likeCount, "UC39: Initial like count should be 0")
        }
        
        @Test
        @DisplayName("UC39-REQ-8: System MUST validate post creation")
        fun `system validates post creation and rejects invalid data`() {
            // Given: User is member of circle
            // Purpose: Validate input validation
            
            // When: Circle is created and user joins
            val circle = useCase.createSupportCircle(
                creatorId = "creator123",
                name = "Support Circle",
                description = "Description"
            )
            useCase.joinSupportCircle(circle.id, "user456")
            
            // When: User creates post with empty content
            // Then: System should throw IllegalArgumentException
            assertThrows(IllegalArgumentException::class.java) {
                useCase.createPost(
                    circleId = circle.id,
                    userId = "user456",
                    content = ""
                )
            }
        }
        
        @Test
        @DisplayName("UC39-REQ-9: System MUST allow users to add comments to posts")
        fun `system allows users to add comments correctly`() {
            // Given: Post exists in circle
            // Purpose: Validate comment functionality
            
            // When: Circle is created, user joins, and creates post
            val circle = useCase.createSupportCircle(
                creatorId = "creator123",
                name = "Support Circle",
                description = "Description"
            )
            useCase.joinSupportCircle(circle.id, "user456")
            val post = useCase.createPost(circle.id, "user456", "Post content")
            
            // When: Another user joins and adds comment
            useCase.joinSupportCircle(circle.id, "user789")
            val comment = useCase.addComment(post.id, "user789", "Supportive comment")
            
            // Then: Comment should be created correctly
            assertNotNull(comment.id, "UC39: Comment must have unique ID")
            assertEquals(post.id, comment.postId, "UC39: Comment must be linked to post")
            assertEquals("user789", comment.authorId, "UC39: Author must be set correctly")
            assertEquals("Supportive comment", comment.content, "UC39: Content must be preserved")
        }
        
        @Test
        @DisplayName("UC39-REQ-10: System MUST allow users to like posts")
        fun `system allows users to like posts correctly`() {
            // Given: Post exists in circle
            // Purpose: Validate like functionality
            
            // When: Circle is created, user joins, and creates post
            val circle = useCase.createSupportCircle(
                creatorId = "creator123",
                name = "Support Circle",
                description = "Description"
            )
            useCase.joinSupportCircle(circle.id, "user456")
            val post = useCase.createPost(circle.id, "user456", "Post content")
            
            // When: User likes post
            val likeResult = useCase.likePost(post.id, "user456")
            
            // Then: Like should succeed
            assertTrue(likeResult, "UC39: Like should succeed")
            
            val posts = useCase.getCirclePosts(circle.id)
            val updatedPost = posts.find { it.id == post.id }
            assertNotNull(updatedPost, "UC39: Post should exist")
            assertEquals(1, updatedPost!!.likeCount, "UC39: Like count should be incremented")
        }
    }

    @Nested
    @DisplayName("Test Case 4: Circle Management - Validates Circle Retrieval Functionality")
    inner class CircleManagementTests {
        
        @Test
        @DisplayName("UC39-REQ-11: System MUST retrieve user's support circles")
        fun `system retrieves user support circles correctly`() {
            // Given: User is member of multiple circles
            // Purpose: Validate circle retrieval functionality
            
            // When: User creates and joins circles
            val circle1 = useCase.createSupportCircle(
                creatorId = "user123",
                name = "Circle 1",
                description = "Description 1"
            )
            val circle2 = useCase.createSupportCircle(
                creatorId = "user456",
                name = "Circle 2",
                description = "Description 2"
            )
            useCase.joinSupportCircle(circle2.id, "user123")
            
            // When: User requests their circles
            val userCircles = useCase.getUserSupportCircles("user123")
            
            // Then: User's circles should be returned
            assertTrue(userCircles.isNotEmpty(), "UC39: System must return user's circles")
            assertTrue(userCircles.any { it.id == circle1.id }, "UC39: Created circles must be included")
            assertTrue(userCircles.any { it.id == circle2.id }, "UC39: Joined circles must be included")
        }
        
        @Test
        @DisplayName("UC39-REQ-12: System MUST retrieve available public circles")
        fun `system retrieves available public circles correctly`() {
            // Given: Multiple public circles exist
            // Purpose: Validate public circle discovery
            
            // When: Multiple circles are created
            val publicCircle1 = useCase.createSupportCircle(
                creatorId = "user1",
                name = "Public Circle 1",
                description = "Description",
                isPrivate = false
            )
            val publicCircle2 = useCase.createSupportCircle(
                creatorId = "user2",
                name = "Public Circle 2",
                description = "Description",
                isPrivate = false
            )
            val privateCircle = useCase.createSupportCircle(
                creatorId = "user3",
                name = "Private Circle",
                description = "Description",
                isPrivate = true
            )
            
            // Verify circles were created correctly
            assertNotNull(publicCircle1.id, "UC39: Public Circle 1 must have an ID")
            assertNotNull(publicCircle2.id, "UC39: Public Circle 2 must have an ID")
            assertNotEquals(publicCircle1.id, publicCircle2.id, "UC39: Circles must have unique IDs")
            assertFalse(publicCircle1.isPrivate, "UC39: Public Circle 1 must not be private")
            assertFalse(publicCircle2.isPrivate, "UC39: Public Circle 2 must not be private")
            assertTrue(privateCircle.isPrivate, "UC39: Private circle must be private")
            
            // When: User requests available circles
            val availableCircles = useCase.getAvailableCircles()
            
            // Then: Only public circles should be returned
            // Verify the circles we created are in the available circles list
            assertTrue(availableCircles.isNotEmpty(), "UC39: System must return available circles. Found: ${availableCircles.size}")
            assertTrue(availableCircles.all { !it.isPrivate }, "UC39: Only public circles should be returned")
            
            // Check that our specific circles are included
            val circle1InList = availableCircles.find { it.id == publicCircle1.id }
            val circle2InList = availableCircles.find { it.id == publicCircle2.id }
            val privateInList = availableCircles.find { it.id == privateCircle.id }
            
            assertNotNull(circle1InList, "UC39: Public Circle 1 (${publicCircle1.id}) must be included in available circles. Available: ${availableCircles.map { it.id }}")
            assertNotNull(circle2InList, "UC39: Public Circle 2 (${publicCircle2.id}) must be included in available circles. Available: ${availableCircles.map { it.id }}")
            assertNull(privateInList, "UC39: Private circle must not be included in available circles")
        }
        
        @Test
        @DisplayName("UC39-REQ-13: System MUST filter circles by topic")
        fun `system filters circles by topic correctly`() {
            // Given: Circles with different topics exist
            // Purpose: Validate topic filtering
            
            // When: Circles with different topics are created
            val anxietyCircle = useCase.createSupportCircle(
                creatorId = "user1",
                name = "Anxiety Circle",
                description = "Description",
                topic = "Anxiety"
            )
            val depressionCircle = useCase.createSupportCircle(
                creatorId = "user2",
                name = "Depression Circle",
                description = "Description",
                topic = "Depression"
            )
            
            // When: User filters by topic
            val anxietyCircles = useCase.getAvailableCircles("Anxiety")
            
            // Then: Only matching circles should be returned
            assertTrue(anxietyCircles.isNotEmpty(), "UC39: System must return filtered circles")
            assertTrue(anxietyCircles.all { it.topic?.equals("Anxiety", ignoreCase = true) == true }, "UC39: Only matching topic circles should be returned")
            assertTrue(anxietyCircles.any { it.id == anxietyCircle.id }, "UC39: Anxiety circle must be included")
            assertFalse(anxietyCircles.any { it.id == depressionCircle.id }, "UC39: Depression circle must not be included")
        }
    }
}

