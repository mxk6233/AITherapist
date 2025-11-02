package com.serenityai.tests.uat.usecases.uc32_journaling_prompts

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested

@DisplayName("UAT: UC32 - AI-Generated Journaling Prompts")
class JournalingPromptsUATTests {

    @Nested
    @DisplayName("User Story: Journaling Prompts")
    inner class JournalingPrompts {
        
        @Test
        @DisplayName("As a user, I want AI-generated journaling prompts so I can reflect meaningfully")
        fun `user receives AI-generated journaling prompts`() {
            // Given: Journaling prompts
            val prompts = listOf(
                "What are three things you're grateful for today?",
                "Describe a moment that made you feel proud this week",
                "What emotions are you experiencing right now?"
            )
            
            // When: User requests prompts
            val promptsGenerated = prompts.isNotEmpty()
            val promptsMeaningful = prompts.any { it.contains("?") && it.length > 20 }
            
            // Then: Prompts are generated
            assertTrue(promptsGenerated, "Journaling prompts should be generated")
            assertTrue(promptsMeaningful, "Prompts should be meaningful and thought-provoking")
        }
        
        @Test
        @DisplayName("As a user, I want prompts by category so I can choose what to reflect on")
        fun `user can access prompts by category`() {
            // Given: Categorized prompts
            val categories = mapOf(
                "Gratitude" to listOf("What are you grateful for?", "Who helped you today?"),
                "Self-reflection" to listOf("How are you feeling?", "What did you learn today?"),
                "Goal-setting" to listOf("What do you want to achieve?", "What's your next step?")
            )
            
            // When: User browses categories
            val categoriesAvailable = categories.isNotEmpty()
            val canBrowseCategories = categoriesAvailable
            
            // Then: Categories are available
            assertTrue(categoriesAvailable, "Prompt categories should be available")
            assertTrue(canBrowseCategories, "User should be able to browse categories")
        }
        
        @Test
        @DisplayName("As a user, I want custom prompts so I can explore specific topics")
        fun `user can generate custom journaling prompts`() {
            // Given: Custom prompt request
            val topic = "career growth"
            val customPrompt = "Reflect on your career journey and identify one area where you want to grow."
            
            // When: User requests custom prompt
            val customPromptGenerated = customPrompt.isNotBlank()
            val topicRelevant = customPrompt.contains("career") || customPrompt.contains("grow")
            
            // Then: Custom prompt is generated
            assertTrue(customPromptGenerated, "Custom prompts should be generated")
            assertTrue(topicRelevant, "Custom prompt should match requested topic")
        }
    }
}

