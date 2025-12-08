package com.serenityai.tests.unit.usecases.uc31_social_support

import com.serenityai.usecases.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

@DisplayName("UC31: Social Support Network Integration - Unit Tests")
class SocialSupportNetworkIntegrationUseCaseUnitTests {

    private lateinit var useCase: SocialSupportNetworkIntegrationUseCase

    @BeforeEach
    fun setUp() {
        useCase = SocialSupportNetworkIntegrationUseCase()
    }

    @Nested
    @DisplayName("Test Case 1: Friend Management - Validates Core UC31 Functionality")
    inner class FriendManagementTests {
        
        @Test
        @DisplayName("UC31-REQ-1: System MUST send friend requests")
        fun `system sends friend requests correctly`() {
            val result = useCase.sendFriendRequest("user1", "user2")
            
            assertTrue(result, "UC31: Friend request must be sent")
            assertTrue(useCase.getPendingFriendRequests("user2").contains("user1"), "UC31: Request must be pending")
        }
        
        @Test
        @DisplayName("UC31-REQ-2: System MUST accept friend requests")
        fun `system accepts friend requests correctly`() {
            useCase.sendFriendRequest("user1", "user2")
            
            val result = useCase.acceptFriendRequest("user2", "user1")
            
            assertTrue(result, "UC31: Friend request must be accepted")
            assertTrue(useCase.getFriends("user1").contains("user2"), "UC31: Users must be friends")
            assertTrue(useCase.getFriends("user2").contains("user1"), "UC31: Friendship must be bidirectional")
        }
        
        @Test
        @DisplayName("UC31-REQ-3: System MUST remove friends")
        fun `system removes friends correctly`() {
            useCase.sendFriendRequest("user1", "user2")
            useCase.acceptFriendRequest("user2", "user1")
            
            val result = useCase.removeFriend("user1", "user2")
            
            assertTrue(result, "UC31: Friend removal must succeed")
            assertFalse(useCase.getFriends("user1").contains("user2"), "UC31: Users must no longer be friends")
        }
    }

    @Nested
    @DisplayName("Test Case 2: Support Groups - Validates Secondary UC31 Functionality")
    inner class SupportGroupsTests {
        
        @Test
        @DisplayName("UC31-REQ-4: System MUST create support groups")
        fun `system creates support groups correctly`() {
            val group = useCase.createSupportGroup("user1", "Anxiety Support", "Group for anxiety support", false)
            
            assertNotNull(group.id, "UC31: Group must have unique ID")
            assertEquals("Anxiety Support", group.name, "UC31: Group name must be stored")
            assertEquals(1, group.memberCount, "UC31: Creator must be member")
        }
        
        @Test
        @DisplayName("UC31-REQ-5: System MUST join support groups")
        fun `system joins support groups correctly`() {
            val group = useCase.createSupportGroup("user1", "Support Group", "Description", false)
            
            val result = useCase.joinSupportGroup("user2", group.id)
            
            assertTrue(result, "UC31: Join must succeed")
            val userGroups = useCase.getUserSupportGroups("user2")
            assertTrue(userGroups.any { it.id == group.id }, "UC31: User must be in group")
        }
    }

    @Nested
    @DisplayName("Test Case 3: Progress Sharing and Encouragement - Validates Tertiary UC31 Functionality")
    inner class ProgressSharingTests {
        
        @Test
        @DisplayName("UC31-REQ-6: System MUST share progress with friends")
        fun `system shares progress with friends correctly`() {
            useCase.sendFriendRequest("user1", "user2")
            useCase.acceptFriendRequest("user2", "user1")
            
            val shared = useCase.shareProgress("user1", "Made great progress today!", true, null)
            
            assertNotNull(shared.id, "UC31: Shared progress must have ID")
            assertEquals("Made great progress today!", shared.message, "UC31: Message must be stored")
        }
        
        @Test
        @DisplayName("UC31-REQ-7: System MUST send encouragement messages")
        fun `system sends encouragement messages correctly`() {
            useCase.sendFriendRequest("user1", "user2")
            useCase.acceptFriendRequest("user2", "user1")
            
            val encouragement = useCase.sendEncouragement("user1", "user2", "You're doing great!")
            
            assertNotNull(encouragement.id, "UC31: Encouragement must have ID")
            assertEquals("You're doing great!", encouragement.message, "UC31: Message must be stored")
            
            val messages = useCase.getEncouragementMessages("user2")
            assertTrue(messages.any { it.id == encouragement.id }, "UC31: Message must be retrievable")
        }
    }
}


