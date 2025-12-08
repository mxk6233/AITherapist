package com.serenityai.tests.integration.usecases.uc31_social_support

import com.serenityai.usecases.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

@DisplayName("UC31: Social Support Network Integration - Integration Tests")
class SocialSupportNetworkIntegrationUseCaseIntegrationTests {

    @Nested
    @DisplayName("Integration Test 1: Friends with User System")
    inner class UserSystemIntegrationTests {
        
        @Test
        @DisplayName("Should integrate friends with user system")
        fun `friends managed through user system integration`() {
            val useCase = SocialSupportNetworkIntegrationUseCase()
            useCase.sendFriendRequest("user1", "user2")
            useCase.acceptFriendRequest("user2", "user1")
            
            val friends = useCase.getFriends("user1")
            
            assertTrue(friends.contains("user2"), "UC31 Integration: Friends must be managed")
        }
    }

    @Nested
    @DisplayName("Integration Test 2: Groups with Community")
    inner class CommunityIntegrationTests {
        
        @Test
        @DisplayName("Should integrate groups with community system")
        fun `groups managed through community integration`() {
            val useCase = SocialSupportNetworkIntegrationUseCase()
            val group = useCase.createSupportGroup("user1", "Support Group", "Description", false)
            useCase.joinSupportGroup("user2", group.id)
            
            val userGroups = useCase.getUserSupportGroups("user2")
            
            assertTrue(userGroups.any { it.id == group.id }, "UC31 Integration: Groups must be managed")
        }
    }

    @Nested
    @DisplayName("Integration Test 3: Sharing with Privacy")
    inner class PrivacyIntegrationTests {
        
        @Test
        @DisplayName("Should integrate sharing with privacy system")
        fun `sharing controlled through privacy integration`() {
            val useCase = SocialSupportNetworkIntegrationUseCase()
            useCase.sendFriendRequest("user1", "user2")
            useCase.acceptFriendRequest("user2", "user1")
            
            val shared = useCase.shareProgress("user1", "Progress message", true, null)
            
            assertNotNull(shared.id, "UC31 Integration: Sharing must be controlled")
            assertTrue(shared.shareWithFriends, "UC31 Integration: Privacy settings must be respected")
        }
    }
}


