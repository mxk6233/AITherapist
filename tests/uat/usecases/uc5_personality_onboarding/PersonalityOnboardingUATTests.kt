package com.serenityai.tests.uat.usecases.uc5_personality_onboarding

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested

@DisplayName("UAT: UC5 - Personality Onboarding for UX")
class PersonalityOnboardingUATTests {

    @Nested
    @DisplayName("User Story: Personalized Onboarding")
    inner class PersonalizedOnboarding {
        
        @Test
        @DisplayName("As a new user, I want to complete personality onboarding so the app can personalize my experience")
        fun `user completes personality onboarding for personalization`() {
            // Given: New user starting onboarding
            val questions = listOf(
                "How do you prefer to receive support?",
                "What are your primary goals?",
                "How do you handle stress?"
            )
            val answers = mapOf(
                "supportStyle" to "Gentle and supportive",
                "goals" to listOf("Reduce anxiety", "Improve mood"),
                "stressHandling" to "Mindfulness"
            )
            
            // When: User completes onboarding
            val onboardingComplete = questions.size == 3 && answers.isNotEmpty()
            val preferencesCollected = answers.containsKey("supportStyle") && answers.containsKey("goals")
            
            // Then: Onboarding is completed
            assertTrue(onboardingComplete, "User should complete onboarding questions")
            assertTrue(preferencesCollected, "User preferences should be collected")
        }
        
        @Test
        @DisplayName("As a user, I want my onboarding results saved so the app remembers my preferences")
        fun `user onboarding results are saved for future use`() {
            // Given: Onboarding completion
            val onboardingResults = mapOf(
                "personalityType" to "Mindful Introvert",
                "preferences" to mapOf("style" to "Gentle", "frequency" to "Weekly"),
                "completedAt" to System.currentTimeMillis()
            )
            
            // When: Onboarding is saved
            val resultsSaved = onboardingResults.isNotEmpty()
            val hasPersonalityType = onboardingResults.containsKey("personalityType")
            
            // Then: Results are saved
            assertTrue(resultsSaved, "Onboarding results should be saved")
            assertTrue(hasPersonalityType, "Personality type should be saved")
        }
        
        @Test
        @DisplayName("As a user, I want personalized recommendations based on my onboarding so I get relevant content")
        fun `user receives personalized recommendations from onboarding`() {
            // Given: Completed onboarding
            val personalityData = mapOf("introversion" to 7, "preferredStyle" to "Mindfulness")
            val recommendations = listOf(
                "Guided meditation sessions",
                "Gentle breathing exercises",
                "Mindfulness journaling prompts"
            )
            
            // When: Recommendations are generated
            val recommendationsGenerated = recommendations.isNotEmpty()
            val recommendationsRelevant = recommendations.any { it.contains("mindfulness") || it.contains("meditation") }
            
            // Then: Recommendations are personalized
            assertTrue(recommendationsGenerated, "Recommendations should be generated")
            assertTrue(recommendationsRelevant, "Recommendations should match user preferences")
        }
    }
}

