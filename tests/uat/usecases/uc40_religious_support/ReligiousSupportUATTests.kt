package com.serenityai.tests.uat.usecases.uc40_religious_support

import com.serenityai.usecases.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested

/** UAT: UC40 - Religious Support by Person's Religion - User Acceptance Tests validating religious support from an end-user perspective. */
@DisplayName("UAT: UC40 - Religious Support by Person's Religion")
class ReligiousSupportUATTests {

    private val useCase = ReligiousSupportUseCase()

    @Nested
    @DisplayName("User Story: Religious Preference")
    inner class ReligiousPreference {
        
        @Test
        @DisplayName("As a user, I want to set my religious preference so I can receive faith-based support")
        fun `user can set religious preference`() {
            // Given: User wants to set preference
            val userId = "user-123"
            
            // When: User sets religious preference
            useCase.setReligiousPreference(userId, Religion.CHRISTIANITY)
            val preference = useCase.getReligiousPreference(userId)
            
            // Then: Preference is set successfully
            assertNotNull(preference, "User should be able to set religious preference")
            assertEquals(Religion.CHRISTIANITY, preference, "Preference should match set value")
        }
    }

    @Nested
    @DisplayName("User Story: Spiritual Guidance")
    inner class SpiritualGuidance {
        
        @Test
        @DisplayName("As a user, I want to receive spiritual guidance so I can find comfort in my faith")
        fun `user can receive spiritual guidance`() {
            // Given: User has set religious preference
            val userId = "user-123"
            useCase.setReligiousPreference(userId, Religion.ISLAM)
            
            // When: User requests guidance
            val guidance = useCase.provideSpiritualGuidance(userId, "anxiety")
            
            // Then: User receives guidance
            assertTrue(guidance.guidance.isNotBlank(), "User should receive spiritual guidance")
            assertEquals(Religion.ISLAM, guidance.religion, "Guidance should match religion")
            assertTrue(guidance.guidance.contains("anxiety", ignoreCase = true) || 
                      guidance.guidance.contains("Allah", ignoreCase = true), 
                      "Guidance should be faith-appropriate")
        }
    }

    @Nested
    @DisplayName("User Story: Religious Resources")
    inner class ReligiousResources {
        
        @Test
        @DisplayName("As a user, I want to access religious resources so I can deepen my faith")
        fun `user can access religious resources`() {
            // Given: User has set religious preference
            val userId = "user-123"
            useCase.setReligiousPreference(userId, Religion.JUDAISM)
            
            // When: User requests resources
            val resources = useCase.getReligiousResources(userId)
            
            // Then: User receives resources
            assertTrue(resources.isNotEmpty(), "User should receive religious resources")
            assertTrue(resources.all { it.religion == Religion.JUDAISM }, 
                      "All resources should match user's religion")
        }
        
        @Test
        @DisplayName("As a user, I want to filter resources by category so I can find specific content")
        fun `user can filter resources by category`() {
            // Given: User has set religious preference
            val userId = "user-123"
            useCase.setReligiousPreference(userId, Religion.BUDDHISM)
            
            // When: User filters resources by category
            val scriptureResources = useCase.getReligiousResources(userId, ResourceCategory.SCRIPTURE)
            
            // Then: User receives filtered resources
            assertTrue(scriptureResources.isNotEmpty(), "User should receive filtered resources")
            assertTrue(scriptureResources.all { it.category == ResourceCategory.SCRIPTURE }, 
                      "All resources should match category")
        }
    }

    @Nested
    @DisplayName("User Story: Prayer Requests")
    inner class PrayerRequests {
        
        @Test
        @DisplayName("As a user, I want to submit prayer requests so I can seek spiritual support")
        fun `user can submit prayer requests`() {
            // Given: User has set religious preference
            val userId = "user-123"
            useCase.setReligiousPreference(userId, Religion.CHRISTIANITY)
            
            // When: User submits prayer request
            val prayerRequest = useCase.submitPrayerRequest(
                userId = userId,
                request = "Please pray for my anxiety",
                isPrivate = true
            )
            
            // Then: Prayer request is submitted successfully
            assertTrue(prayerRequest.id.isNotBlank(), "User should be able to submit prayer requests")
            assertEquals("Please pray for my anxiety", prayerRequest.request, "Request should be preserved")
        }
        
        @Test
        @DisplayName("As a user, I want to view my prayer requests so I can track my spiritual journey")
        fun `user can view prayer requests`() {
            // Given: User has submitted prayer requests
            val userId = "user-123"
            useCase.setReligiousPreference(userId, Religion.CHRISTIANITY)
            useCase.submitPrayerRequest(userId, "Prayer request 1")
            useCase.submitPrayerRequest(userId, "Prayer request 2")
            
            // When: User views requests
            val requests = useCase.getPrayerRequests(userId)
            
            // Then: User sees prayer requests
            assertTrue(requests.isNotEmpty(), "User should see prayer requests")
            assertEquals(2, requests.size, "All requests should be shown")
        }
    }

    @Nested
    @DisplayName("User Story: Daily Reflections and Quotes")
    inner class ReflectionsAndQuotes {
        
        @Test
        @DisplayName("As a user, I want daily reflections so I can start my day with faith")
        fun `user can receive daily reflections`() {
            // Given: User has set religious preference
            val userId = "user-123"
            useCase.setReligiousPreference(userId, Religion.HINDUISM)
            
            // When: User requests daily reflection
            val reflection = useCase.getDailyReflection(userId)
            
            // Then: User receives reflection
            assertTrue(reflection.isNotBlank(), "User should receive daily reflection")
            assertTrue(reflection.contains("Namaste", ignoreCase = true) || 
                      reflection.contains("dharma", ignoreCase = true) || 
                      reflection.contains("divine", ignoreCase = true), 
                      "Reflection should be faith-appropriate")
        }
        
        @Test
        @DisplayName("As a user, I want religious quotes so I can find inspiration")
        fun `user can receive religious quotes`() {
            // Given: User has set religious preference
            val userId = "user-123"
            useCase.setReligiousPreference(userId, Religion.SIKHISM)
            
            // When: User requests quote
            val quote = useCase.getReligiousQuote(userId)
            
            // Then: User receives quote
            assertTrue(quote.isNotBlank(), "User should receive religious quote")
            assertTrue(quote.contains("Waheguru", ignoreCase = true) || 
                      quote.contains("Guru", ignoreCase = true) || 
                      quote.contains("Sikh", ignoreCase = true), 
                      "Quote should be faith-appropriate")
        }
    }
}

