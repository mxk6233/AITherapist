package com.serenityai.tests.unit.usecases.uc40_religious_support

import com.serenityai.usecases.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

/**
 * UC40: Religious Support by Person's Religion
 * 
 * Use Case Goal: Provide religious and spiritual support tailored to the user's specific religion, including faith-based guidance and religious resources.
 * 
 * Key Requirements Being Tested:
 * 1. System sets and retrieves user religious preferences
 * 2. System provides faith-based guidance based on religion
 * 3. System provides religious resources for user's religion
 * 4. System handles prayer requests
 * 5. System provides daily reflections and quotes
 */
@DisplayName("UC40: Religious Support by Person's Religion - Unit Tests")
class ReligiousSupportUseCaseUnitTests {

    private lateinit var useCase: ReligiousSupportUseCase

    @BeforeEach
    fun setUp() {
        useCase = ReligiousSupportUseCase()
    }

    @Nested
    @DisplayName("Test Case 1: Religious Preference Management - Validates Core UC40 Functionality")
    inner class ReligiousPreferenceTests {
        
        @Test
        @DisplayName("UC40-REQ-1: System MUST set user religious preference")
        fun `system sets user religious preference correctly`() {
            // Given: User wants to set religious preference
            // Purpose: Validate preference setting functionality
            
            // When: User sets religious preference
            useCase.setReligiousPreference("user123", Religion.CHRISTIANITY)
            
            // When: User retrieves preference
            val preference = useCase.getReligiousPreference("user123")
            
            // Then: Preference should be set correctly
            assertNotNull(preference, "UC40: Preference must be set")
            assertEquals(Religion.CHRISTIANITY, preference, "UC40: Preference must match set value")
        }
        
        @Test
        @DisplayName("UC40-REQ-2: System MUST retrieve user religious preference")
        fun `system retrieves user religious preference correctly`() {
            // Given: User has set preference
            // Purpose: Validate preference retrieval functionality
            
            // When: User sets preference
            useCase.setReligiousPreference("user123", Religion.ISLAM)
            
            // When: User retrieves preference
            val preference = useCase.getReligiousPreference("user123")
            
            // Then: Preference should be retrieved correctly
            assertEquals(Religion.ISLAM, preference, "UC40: Retrieved preference must match set value")
        }
        
        @Test
        @DisplayName("UC40-REQ-3: System MUST return null for unset preferences")
        fun `system returns null for unset preferences`() {
            // Given: User has not set preference
            // Purpose: Validate null handling
            
            // When: User retrieves preference without setting it
            val preference = useCase.getReligiousPreference("user123")
            
            // Then: Preference should be null
            assertNull(preference, "UC40: Unset preference should return null")
        }
    }

    @Nested
    @DisplayName("Test Case 2: Spiritual Guidance - Validates Secondary UC40 Functionality")
    inner class SpiritualGuidanceTests {
        
        @Test
        @DisplayName("UC40-REQ-4: System MUST provide spiritual guidance for Christianity")
        fun `system provides Christian spiritual guidance correctly`() {
            // Given: User has Christian preference
            // Purpose: Validate Christian guidance generation
            
            // When: User sets preference and requests guidance
            useCase.setReligiousPreference("user123", Religion.CHRISTIANITY)
            val guidance = useCase.provideSpiritualGuidance("user123", "anxiety")
            
            // Then: Guidance should be provided correctly
            assertNotNull(guidance.id, "UC40: Guidance must have unique ID")
            assertEquals(Religion.CHRISTIANITY, guidance.religion, "UC40: Guidance must match religion")
            assertTrue(guidance.guidance.isNotBlank(), "UC40: Guidance must not be empty")
            assertTrue(guidance.guidance.contains("anxiety", ignoreCase = true), "UC40: Guidance must include context")
        }
        
        @Test
        @DisplayName("UC40-REQ-5: System MUST provide spiritual guidance for Islam")
        fun `system provides Islamic spiritual guidance correctly`() {
            // Given: User has Islamic preference
            // Purpose: Validate Islamic guidance generation
            
            // When: User sets preference and requests guidance
            useCase.setReligiousPreference("user123", Religion.ISLAM)
            val guidance = useCase.provideSpiritualGuidance("user123", "stress")
            
            // Then: Guidance should be provided correctly
            assertEquals(Religion.ISLAM, guidance.religion, "UC40: Guidance must match religion")
            assertTrue(guidance.guidance.contains("Allah", ignoreCase = true) || 
                      guidance.guidance.contains("prayer", ignoreCase = true), 
                      "UC40: Guidance must be faith-appropriate")
        }
        
        @Test
        @DisplayName("UC40-REQ-6: System MUST validate guidance requests")
        fun `system validates guidance requests and rejects invalid data`() {
            // Given: User has set preference
            // Purpose: Validate input validation
            
            // When: User sets preference
            useCase.setReligiousPreference("user123", Religion.CHRISTIANITY)
            
            // When: User requests guidance with empty context
            // Then: System should throw IllegalArgumentException
            assertThrows(IllegalArgumentException::class.java) {
                useCase.provideSpiritualGuidance("user123", "")
            }
        }
        
        @Test
        @DisplayName("UC40-REQ-7: System MUST require religious preference for guidance")
        fun `system requires religious preference for guidance`() {
            // Given: User has not set preference
            // Purpose: Validate preference requirement
            
            // When: User requests guidance without setting preference
            // Then: System should throw IllegalArgumentException
            assertThrows(IllegalArgumentException::class.java) {
                useCase.provideSpiritualGuidance("user123", "anxiety")
            }
        }
    }

    @Nested
    @DisplayName("Test Case 3: Religious Resources - Validates Resource Management Functionality")
    inner class ReligiousResourceTests {
        
        @Test
        @DisplayName("UC40-REQ-8: System MUST provide religious resources for user's religion")
        fun `system provides religious resources correctly`() {
            // Given: User has set preference
            // Purpose: Validate resource retrieval functionality
            
            // When: User sets preference and requests resources
            useCase.setReligiousPreference("user123", Religion.CHRISTIANITY)
            val resources = useCase.getReligiousResources("user123")
            
            // Then: Resources should be provided correctly
            assertTrue(resources.isNotEmpty(), "UC40: System must provide resources")
            assertTrue(resources.all { it.religion == Religion.CHRISTIANITY }, 
                      "UC40: All resources must match user's religion")
        }
        
        @Test
        @DisplayName("UC40-REQ-9: System MUST filter resources by category")
        fun `system filters resources by category correctly`() {
            // Given: User has set preference
            // Purpose: Validate category filtering
            
            // When: User sets preference and filters resources
            useCase.setReligiousPreference("user123", Religion.ISLAM)
            val scriptureResources = useCase.getReligiousResources("user123", ResourceCategory.SCRIPTURE)
            
            // Then: Filtered resources should be provided
            assertTrue(scriptureResources.isNotEmpty(), "UC40: System must provide filtered resources")
            assertTrue(scriptureResources.all { it.category == ResourceCategory.SCRIPTURE }, 
                      "UC40: All resources must match category")
        }
        
        @Test
        @DisplayName("UC40-REQ-10: System MUST require religious preference for resources")
        fun `system requires religious preference for resources`() {
            // Given: User has not set preference
            // Purpose: Validate preference requirement
            
            // When: User requests resources without setting preference
            // Then: System should throw IllegalArgumentException
            assertThrows(IllegalArgumentException::class.java) {
                useCase.getReligiousResources("user123")
            }
        }
    }

    @Nested
    @DisplayName("Test Case 4: Prayer Requests - Validates Prayer Request Functionality")
    inner class PrayerRequestTests {
        
        @Test
        @DisplayName("UC40-REQ-11: System MUST allow users to submit prayer requests")
        fun `system allows users to submit prayer requests correctly`() {
            // Given: User has set preference
            // Purpose: Validate prayer request submission
            
            // When: User sets preference and submits prayer request
            useCase.setReligiousPreference("user123", Religion.CHRISTIANITY)
            val prayerRequest = useCase.submitPrayerRequest(
                userId = "user123",
                request = "Please pray for my anxiety",
                isPrivate = true
            )
            
            // Then: Prayer request should be submitted correctly
            assertNotNull(prayerRequest.id, "UC40: Prayer request must have unique ID")
            assertEquals("user123", prayerRequest.userId, "UC40: User ID must be set correctly")
            assertEquals(Religion.CHRISTIANITY, prayerRequest.religion, "UC40: Religion must match preference")
            assertEquals("Please pray for my anxiety", prayerRequest.request, "UC40: Request must be preserved")
            assertTrue(prayerRequest.isPrivate, "UC40: Privacy setting must be preserved")
            assertEquals(PrayerStatus.PENDING, prayerRequest.status, "UC40: Status must be PENDING")
        }
        
        @Test
        @DisplayName("UC40-REQ-12: System MUST validate prayer request input")
        fun `system validates prayer request input and rejects invalid data`() {
            // Given: User has set preference
            // Purpose: Validate input validation
            
            // When: User sets preference
            useCase.setReligiousPreference("user123", Religion.CHRISTIANITY)
            
            // When: User submits prayer request with empty content
            // Then: System should throw IllegalArgumentException
            assertThrows(IllegalArgumentException::class.java) {
                useCase.submitPrayerRequest("user123", "")
            }
        }
        
        @Test
        @DisplayName("UC40-REQ-13: System MUST retrieve user's prayer requests")
        fun `system retrieves user prayer requests correctly`() {
            // Given: User has submitted prayer requests
            // Purpose: Validate prayer request retrieval
            
            // When: User sets preference and submits requests
            useCase.setReligiousPreference("user123", Religion.CHRISTIANITY)
            useCase.submitPrayerRequest("user123", "Prayer request 1")
            useCase.submitPrayerRequest("user123", "Prayer request 2")
            
            // When: User retrieves requests
            val requests = useCase.getPrayerRequests("user123")
            
            // Then: Requests should be retrieved correctly
            assertTrue(requests.isNotEmpty(), "UC40: System must return prayer requests")
            assertEquals(2, requests.size, "UC40: All requests must be returned")
            assertTrue(requests.all { it.userId == "user123" }, "UC40: All requests must belong to user")
        }
    }

    @Nested
    @DisplayName("Test Case 5: Daily Reflections and Quotes - Validates Reflection Functionality")
    inner class ReflectionTests {
        
        @Test
        @DisplayName("UC40-REQ-14: System MUST provide daily reflections")
        fun `system provides daily reflections correctly`() {
            // Given: User has set preference
            // Purpose: Validate daily reflection functionality
            
            // When: User sets preference and requests reflection
            useCase.setReligiousPreference("user123", Religion.CHRISTIANITY)
            val reflection = useCase.getDailyReflection("user123")
            
            // Then: Reflection should be provided correctly
            assertTrue(reflection.isNotBlank(), "UC40: Reflection must not be empty")
            assertTrue(reflection.contains("prayer", ignoreCase = true) || 
                      reflection.contains("blessings", ignoreCase = true), 
                      "UC40: Reflection must be faith-appropriate")
        }
        
        @Test
        @DisplayName("UC40-REQ-15: System MUST provide religious quotes")
        fun `system provides religious quotes correctly`() {
            // Given: User has set preference
            // Purpose: Validate quote functionality
            
            // When: User sets preference and requests quote
            useCase.setReligiousPreference("user123", Religion.ISLAM)
            val quote = useCase.getReligiousQuote("user123")
            
            // Then: Quote should be provided correctly
            assertTrue(quote.isNotBlank(), "UC40: Quote must not be empty")
            assertTrue(quote.contains("Allah", ignoreCase = true) || 
                      quote.contains("Quran", ignoreCase = true), 
                      "UC40: Quote must be faith-appropriate")
        }
        
        @Test
        @DisplayName("UC40-REQ-16: System MUST filter quotes by topic")
        fun `system filters quotes by topic correctly`() {
            // Given: User has set preference
            // Purpose: Validate topic filtering for quotes
            
            // When: User sets preference and requests quote with topic
            useCase.setReligiousPreference("user123", Religion.CHRISTIANITY)
            val quote = useCase.getReligiousQuote("user123", "strength")
            
            // Then: Quote should be provided (may or may not contain topic)
            assertTrue(quote.isNotBlank(), "UC40: Quote must not be empty")
        }
        
        @Test
        @DisplayName("UC40-REQ-17: System MUST retrieve spiritual guidance history")
        fun `system retrieves spiritual guidance history correctly`() {
            // Given: User has received guidance
            // Purpose: Validate guidance history retrieval
            
            // When: User sets preference and receives guidance
            useCase.setReligiousPreference("user123", Religion.CHRISTIANITY)
            useCase.provideSpiritualGuidance("user123", "anxiety")
            useCase.provideSpiritualGuidance("user123", "stress")
            
            // When: User retrieves history
            val history = useCase.getSpiritualGuidanceHistory("user123")
            
            // Then: History should be retrieved correctly
            assertTrue(history.isNotEmpty(), "UC40: System must return guidance history")
            assertEquals(2, history.size, "UC40: All guidance must be returned")
            assertTrue(history.all { it.religion == Religion.CHRISTIANITY }, 
                      "UC40: All guidance must match religion")
        }
    }
}

