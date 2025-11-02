package com.serenityai.tests.usecases.uc32_journaling_prompts.unit

import com.serenityai.ui.screens.JournalPrompt
import com.serenityai.ui.screens.JournalingPromptsScreen
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

@DisplayName("UC32: AI-Generated Journaling Prompts - Test Cases")
class JournalingPromptsUseCaseTests {

    @Nested
    @DisplayName("Test Case 1: AI Prompt Generation and Personalization")
    inner class AIPromptGenerationTests {
        
        @Test
        fun `should generate personalized prompts based on user mood and preferences`() {
            // Given
            val journalPrompts = listOf(
                JournalPrompt("1", "Daily Reflection", "What was the most meaningful moment of your day?", "Reflection", listOf("All"), "Easy", "5-10 minutes", true, 3),
                JournalPrompt("2", "Gratitude Practice", "Write about three things you're grateful for today", "Gratitude", listOf("Happy", "Grateful"), "Easy", "5 minutes", true, 7),
                JournalPrompt("3", "Growth Mindset", "Describe a challenge you faced recently and what you learned", "Growth", listOf("Motivated", "Stressed"), "Medium", "10-15 minutes", true, 2),
                JournalPrompt("4", "Emotional Processing", "Describe a recent emotion and what triggered it", "Challenges", listOf("Sad", "Anxious", "Stressed"), "Medium", "12-15 minutes", true, 2)
            )
            
            // When
            val happyMoodPrompts = journalPrompts.filter { it.moodTags.contains("Happy") }
            val anxiousMoodPrompts = journalPrompts.filter { it.moodTags.contains("Anxious") }
            val allMoodPrompts = journalPrompts.filter { it.moodTags.contains("All") }
            val aiGeneratedPrompts = journalPrompts.filter { it.isAIGenerated }
            
            // Then
            assertEquals(1, happyMoodPrompts.size)
            assertEquals("Gratitude Practice", happyMoodPrompts.first().title)
            assertEquals(1, anxiousMoodPrompts.size)
            assertEquals("Emotional Processing", anxiousMoodPrompts.first().title)
            assertEquals(1, allMoodPrompts.size)
            assertEquals("Daily Reflection", allMoodPrompts.first().title)
            assertEquals(4, aiGeneratedPrompts.size)
            assertTrue(aiGeneratedPrompts.all { it.isAIGenerated })
        }
        
        @Test
        fun `should adapt prompt difficulty and duration based on user experience`() {
            // Given
            val journalPrompts = listOf(
                JournalPrompt("1", "Easy Prompt", "Simple question", "Reflection", listOf("All"), "Easy", "5 minutes", true, 0),
                JournalPrompt("2", "Medium Prompt", "Moderate question", "Growth", listOf("All"), "Medium", "10 minutes", true, 0),
                JournalPrompt("3", "Hard Prompt", "Complex question", "Challenges", listOf("All"), "Hard", "20 minutes", true, 0),
                JournalPrompt("4", "Experienced Prompt", "Advanced question", "Reflection", listOf("All"), "Easy", "8 minutes", true, 10)
            )
            
            // When
            val easyPrompts = journalPrompts.filter { it.difficulty == "Easy" }
            val mediumPrompts = journalPrompts.filter { it.difficulty == "Medium" }
            val hardPrompts = journalPrompts.filter { it.difficulty == "Hard" }
            val shortDurationPrompts = journalPrompts.filter { it.estimatedTime.contains("5 minutes") }
            val longDurationPrompts = journalPrompts.filter { it.estimatedTime.contains("20 minutes") }
            
            // Then
            assertEquals(2, easyPrompts.size)
            assertEquals(1, mediumPrompts.size)
            assertEquals(1, hardPrompts.size)
            assertEquals(1, shortDurationPrompts.size)
            assertEquals(1, longDurationPrompts.size)
            assertTrue(easyPrompts.any { it.title == "Easy Prompt" })
            assertTrue(mediumPrompts.any { it.title == "Medium Prompt" })
            assertTrue(hardPrompts.any { it.title == "Hard Prompt" })
        }
        
        @Test
        fun `should track prompt usage and effectiveness for continuous improvement`() {
            // Given
            val journalPrompts = listOf(
                JournalPrompt("1", "Popular Prompt", "Often used", "Reflection", listOf("All"), "Easy", "5 minutes", true, 15),
                JournalPrompt("2", "Occasional Prompt", "Sometimes used", "Gratitude", listOf("All"), "Easy", "8 minutes", true, 3),
                JournalPrompt("3", "New Prompt", "Never used", "Growth", listOf("All"), "Medium", "12 minutes", true, 0),
                JournalPrompt("4", "Effective Prompt", "High effectiveness", "Challenges", listOf("All"), "Medium", "10 minutes", true, 8)
            )
            
            // When
            val totalUsage = journalPrompts.sumOf { it.timesUsed }
            val mostUsedPrompt = journalPrompts.maxByOrNull { it.timesUsed }
            val unusedPrompts = journalPrompts.filter { it.timesUsed == 0 }
            val frequentlyUsedPrompts = journalPrompts.filter { it.timesUsed >= 5 }
            val averageUsage = journalPrompts.map { it.timesUsed }.average()
            
            // Then
            assertEquals(26, totalUsage)
            assertNotNull(mostUsedPrompt)
            assertEquals("Popular Prompt", mostUsedPrompt!!.title)
            assertEquals(15, mostUsedPrompt.timesUsed)
            assertEquals(1, unusedPrompts.size)
            assertEquals("New Prompt", unusedPrompts.first().title)
            assertEquals(2, frequentlyUsedPrompts.size)
            assertEquals(6.5, averageUsage, 0.01)
        }
    }

    @Nested
    @DisplayName("Test Case 2: Prompt Categorization and Filtering")
    inner class PromptCategorizationTests {
        
        @Test
        fun `should categorize prompts by type and theme correctly`() {
            // Given
            val journalPrompts = listOf(
                JournalPrompt("1", "Daily Reflection", "Reflect on your day", "Reflection", listOf("All"), "Easy", "5 minutes", true, 5),
                JournalPrompt("2", "Gratitude Journal", "Write about gratitude", "Gratitude", listOf("Happy"), "Easy", "8 minutes", true, 3),
                JournalPrompt("3", "Growth Challenge", "Describe growth", "Growth", listOf("Motivated"), "Medium", "12 minutes", true, 2),
                JournalPrompt("4", "Relationship Thoughts", "Think about relationships", "Relationships", listOf("All"), "Medium", "10 minutes", true, 1),
                JournalPrompt("5", "Goal Setting", "Set goals", "Goals", listOf("Motivated"), "Easy", "8 minutes", true, 4),
                JournalPrompt("6", "Emotional Processing", "Process emotions", "Challenges", listOf("Sad"), "Medium", "15 minutes", true, 2)
            )
            
            // When
            val reflectionPrompts = journalPrompts.filter { it.category == "Reflection" }
            val gratitudePrompts = journalPrompts.filter { it.category == "Gratitude" }
            val growthPrompts = journalPrompts.filter { it.category == "Growth" }
            val relationshipPrompts = journalPrompts.filter { it.category == "Relationships" }
            val goalPrompts = journalPrompts.filter { it.category == "Goals" }
            val challengePrompts = journalPrompts.filter { it.category == "Challenges" }
            
            // Then
            assertEquals(1, reflectionPrompts.size)
            assertEquals("Daily Reflection", reflectionPrompts.first().title)
            assertEquals(1, gratitudePrompts.size)
            assertEquals("Gratitude Journal", gratitudePrompts.first().title)
            assertEquals(1, growthPrompts.size)
            assertEquals("Growth Challenge", growthPrompts.first().title)
            assertEquals(1, relationshipPrompts.size)
            assertEquals("Relationship Thoughts", relationshipPrompts.first().title)
            assertEquals(1, goalPrompts.size)
            assertEquals("Goal Setting", goalPrompts.first().title)
            assertEquals(1, challengePrompts.size)
            assertEquals("Emotional Processing", challengePrompts.first().title)
        }
        
        @Test
        fun `should filter prompts by mood compatibility`() {
            // Given
            val journalPrompts = listOf(
                JournalPrompt("1", "Universal Prompt", "Works for everyone", "Reflection", listOf("All"), "Easy", "5 minutes", true, 0),
                JournalPrompt("2", "Happy Prompt", "For happy moods", "Gratitude", listOf("Happy", "Grateful"), "Easy", "8 minutes", true, 0),
                JournalPrompt("3", "Sad Prompt", "For sad moods", "Challenges", listOf("Sad", "Anxious"), "Medium", "12 minutes", true, 0),
                JournalPrompt("4", "Motivated Prompt", "For motivated moods", "Goals", listOf("Motivated", "Happy"), "Easy", "10 minutes", true, 0)
            )
            
            // When
            val happyMoodCompatible = journalPrompts.filter { 
                it.moodTags.contains("Happy") || it.moodTags.contains("All") 
            }
            val sadMoodCompatible = journalPrompts.filter { 
                it.moodTags.contains("Sad") || it.moodTags.contains("All") 
            }
            val motivatedMoodCompatible = journalPrompts.filter { 
                it.moodTags.contains("Motivated") || it.moodTags.contains("All") 
            }
            val anxiousMoodCompatible = journalPrompts.filter { 
                it.moodTags.contains("Anxious") || it.moodTags.contains("All") 
            }
            
            // Then
            assertEquals(3, happyMoodCompatible.size) // Universal + Happy + Motivated
            assertEquals(2, sadMoodCompatible.size) // Universal + Sad
            assertEquals(2, motivatedMoodCompatible.size) // Universal + Motivated
            assertEquals(2, anxiousMoodCompatible.size) // Universal + Sad
        }
        
        @Test
        fun `should prioritize prompts based on user preferences and history`() {
            // Given
            val journalPrompts = listOf(
                JournalPrompt("1", "Frequently Used", "Often chosen", "Reflection", listOf("All"), "Easy", "5 minutes", true, 10),
                JournalPrompt("2", "Never Used", "Never chosen", "Gratitude", listOf("All"), "Easy", "8 minutes", true, 0),
                JournalPrompt("3", "Occasionally Used", "Sometimes chosen", "Growth", listOf("All"), "Medium", "12 minutes", true, 3),
                JournalPrompt("4", "High Effectiveness", "Very effective", "Challenges", listOf("All"), "Medium", "10 minutes", true, 5)
            )
            
            // When
            val frequentlyUsedPrompts = journalPrompts.filter { it.timesUsed >= 5 }
            val unusedPrompts = journalPrompts.filter { it.timesUsed == 0 }
            val occasionallyUsedPrompts = journalPrompts.filter { it.timesUsed in 1..4 }
            
            // Sort by usage frequency (descending)
            val promptsByUsage = journalPrompts.sortedByDescending { it.timesUsed }
            
            // Then
            assertEquals(2, frequentlyUsedPrompts.size)
            assertTrue(frequentlyUsedPrompts.any { it.title == "Frequently Used" })
            assertTrue(frequentlyUsedPrompts.any { it.title == "High Effectiveness" })
            assertEquals(1, unusedPrompts.size)
            assertEquals("Never Used", unusedPrompts.first().title)
            assertEquals(1, occasionallyUsedPrompts.size)
            assertEquals("Occasionally Used", occasionallyUsedPrompts.first().title)
            assertEquals("Frequently Used", promptsByUsage.first().title)
            assertEquals("Never Used", promptsByUsage.last().title)
        }
    }

    @Nested
    @DisplayName("Test Case 3: Custom Prompt Generation and AI Integration")
    inner class CustomPromptGenerationTests {
        
        @Test
        fun `should generate custom prompts based on user input and context`() {
            // Given
            val userInputs = listOf(
                "I'm feeling stressed about work",
                "I want to reflect on my relationships",
                "I need help processing my emotions",
                "I want to plan for the future"
            )
            
            // When
            val customPrompts = userInputs.map { input ->
                when {
                    input.contains("stressed") -> JournalPrompt("custom1", "Work Stress Reflection", "Describe your work stress and coping strategies", "Challenges", listOf("Stressed"), "Medium", "10 minutes", true, 0)
                    input.contains("relationships") -> JournalPrompt("custom2", "Relationship Reflection", "Reflect on your important relationships", "Relationships", listOf("All"), "Easy", "8 minutes", true, 0)
                    input.contains("emotions") -> JournalPrompt("custom3", "Emotional Processing", "Process and understand your current emotions", "Challenges", listOf("All"), "Medium", "12 minutes", true, 0)
                    input.contains("future") -> JournalPrompt("custom4", "Future Planning", "Plan and visualize your future goals", "Goals", listOf("Motivated"), "Easy", "10 minutes", true, 0)
                    else -> JournalPrompt("custom5", "General Reflection", "General reflection prompt", "Reflection", listOf("All"), "Easy", "5 minutes", true, 0)
                }
            }
            
            // Then
            assertEquals(4, customPrompts.size)
            assertEquals("Work Stress Reflection", customPrompts[0].title)
            assertTrue(customPrompts[0].moodTags.contains("Stressed"))
            assertEquals("Relationship Reflection", customPrompts[1].title)
            assertEquals("Relationships", customPrompts[1].category)
            assertEquals("Emotional Processing", customPrompts[2].title)
            assertEquals("Challenges", customPrompts[2].category)
            assertEquals("Future Planning", customPrompts[3].title)
            assertEquals("Goals", customPrompts[3].category)
        }
        
        @Test
        fun `should adapt prompt complexity based on user experience level`() {
            // Given
            val userExperienceLevels = listOf("Beginner", "Intermediate", "Advanced")
            
            // When
            val promptsByExperience = userExperienceLevels.map { level ->
                when (level) {
                    "Beginner" -> listOf(
                        JournalPrompt("b1", "Simple Gratitude", "Write one thing you're grateful for", "Gratitude", listOf("All"), "Easy", "3 minutes", true, 0),
                        JournalPrompt("b2", "Daily Highlight", "What was the best part of your day?", "Reflection", listOf("All"), "Easy", "5 minutes", true, 0)
                    )
                    "Intermediate" -> listOf(
                        JournalPrompt("i1", "Emotional Check-in", "Describe your current emotions and their triggers", "Challenges", listOf("All"), "Medium", "10 minutes", true, 0),
                        JournalPrompt("i2", "Growth Reflection", "How have you grown recently?", "Growth", listOf("All"), "Medium", "12 minutes", true, 0)
                    )
                    "Advanced" -> listOf(
                        JournalPrompt("a1", "Deep Self-Analysis", "Analyze patterns in your thoughts and behaviors", "Reflection", listOf("All"), "Hard", "20 minutes", true, 0),
                        JournalPrompt("a2", "Complex Problem Solving", "Work through a complex personal challenge", "Challenges", listOf("All"), "Hard", "25 minutes", true, 0)
                    )
                    else -> emptyList()
                }
            }
            
            // Then
            assertEquals(3, promptsByExperience.size)
            assertEquals(2, promptsByExperience[0].size) // Beginner
            assertTrue(promptsByExperience[0].all { it.difficulty == "Easy" })
            assertTrue(promptsByExperience[0].all { it.estimatedTime.contains("3 minutes") || it.estimatedTime.contains("5 minutes") })
            
            assertEquals(2, promptsByExperience[1].size) // Intermediate
            assertTrue(promptsByExperience[1].all { it.difficulty == "Medium" })
            assertTrue(promptsByExperience[1].all { it.estimatedTime.contains("10 minutes") || it.estimatedTime.contains("12 minutes") })
            
            assertEquals(2, promptsByExperience[2].size) // Advanced
            assertTrue(promptsByExperience[2].all { it.difficulty == "Hard" })
            assertTrue(promptsByExperience[2].all { it.estimatedTime.contains("20 minutes") || it.estimatedTime.contains("25 minutes") })
        }
        
        @Test
        fun `should validate prompt quality and user engagement metrics`() {
            // Given
            val journalPrompts = listOf(
                JournalPrompt("1", "High Quality Prompt", "Well-crafted question", "Reflection", listOf("All"), "Easy", "5 minutes", true, 8),
                JournalPrompt("2", "Medium Quality Prompt", "Decent question", "Gratitude", listOf("All"), "Easy", "8 minutes", true, 3),
                JournalPrompt("3", "Low Quality Prompt", "Poor question", "Growth", listOf("All"), "Medium", "12 minutes", true, 1),
                JournalPrompt("4", "New High Quality", "Excellent new prompt", "Challenges", listOf("All"), "Medium", "10 minutes", true, 0)
            )
            
            // When
            val highEngagementPrompts = journalPrompts.filter { it.timesUsed >= 5 }
            val mediumEngagementPrompts = journalPrompts.filter { it.timesUsed in 2..4 }
            val lowEngagementPrompts = journalPrompts.filter { it.timesUsed < 2 }
            val newPrompts = journalPrompts.filter { it.timesUsed == 0 }
            
            val averageEngagement = journalPrompts.map { it.timesUsed }.average()
            val engagementRate = journalPrompts.count { it.timesUsed > 0 }.toFloat() / journalPrompts.size
            
            // Then
            assertEquals(1, highEngagementPrompts.size)
            assertEquals("High Quality Prompt", highEngagementPrompts.first().title)
            assertEquals(1, mediumEngagementPrompts.size)
            assertEquals("Medium Quality Prompt", mediumEngagementPrompts.first().title)
            assertEquals(2, lowEngagementPrompts.size)
            assertEquals(3.0, averageEngagement, 0.01)
            assertEquals(0.75f, engagementRate, 0.01f) // 75% engagement rate
        }
    }
}
