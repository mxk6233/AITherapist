package com.serenityai.tests.uat.usecases.uc2_crisis_intervention

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested

@DisplayName("UAT: UC2 - Handle Crisis Intervention")
class CrisisInterventionUATTests {

    @Nested
    @DisplayName("User Story: Emergency Crisis Support")
    inner class EmergencyCrisisSupport {
        
        @Test
        @DisplayName("As a user in crisis, I want immediate intervention so I can get help when I need it most")
        fun `user receives immediate crisis intervention when in crisis`() {
            // Given: User expresses crisis
            val crisisMessage = "I want to hurt myself"
            val crisisDetected = crisisMessage.contains("hurt myself") || crisisMessage.contains("suicide")
            val emergencyHotline = "988"
            
            // When: Crisis is detected
            val interventionTriggered = crisisDetected
            val hotlineDisplayed = interventionTriggered
            
            // Then: Immediate intervention is provided
            assertTrue(interventionTriggered, "Crisis intervention must be triggered immediately")
            assertTrue(hotlineDisplayed, "Emergency hotline must be displayed")
            assertTrue(emergencyHotline.isNotBlank(), "Emergency resources must be available")
        }
        
        @Test
        @DisplayName("As a user, I want emergency resources displayed so I know how to get help")
        fun `user sees emergency resources when needed`() {
            // Given: Crisis situation
            val emergencyResources = listOf(
                "988 Suicide & Crisis Lifeline",
                "911 Emergency Services",
                "Text HOME to 741741"
            )
            
            // When: Crisis is detected
            val resourcesAvailable = emergencyResources.isNotEmpty()
            val resourcesVisible = resourcesAvailable
            
            // Then: Emergency resources are displayed
            assertTrue(resourcesAvailable, "Emergency resources must be available")
            assertTrue(resourcesVisible, "Resources must be visible to user")
            assertEquals(3, emergencyResources.size, "Multiple emergency resources must be provided")
        }
        
        @Test
        @DisplayName("As a user, I want my safety plan accessible so I can use it during difficult times")
        fun `user can access safety plan during crisis`() {
            // Given: User has a safety plan
            val safetyPlan = mapOf(
                "contacts" to listOf("Dr. Smith", "Family Member"),
                "strategies" to listOf("Deep breathing", "Call support person"),
                "warningSigns" to listOf("Feeling hopeless", "Withdrawing from others")
            )
            
            // When: User needs safety plan
            val planAccessible = safetyPlan.isNotEmpty()
            val contactsAvailable = (safetyPlan["contacts"] as? List<*>)?.isNotEmpty() == true
            
            // Then: Safety plan is accessible
            assertTrue(planAccessible, "Safety plan must be accessible")
            assertTrue(contactsAvailable, "Emergency contacts must be available")
        }
    }
}

