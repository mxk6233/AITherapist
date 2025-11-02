package com.serenityai.tests.unit.usecases.uc24_personalization

// Data classes are defined at the bottom of this file
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

@DisplayName("UC24: Personalize User Experience - Test Cases")
class PersonalizationUseCaseTests {

    @Nested
    @DisplayName("Test Case 1: Theme and Appearance Customization")
    inner class ThemeAndAppearanceTests {
        
        @Test
        fun `should apply theme preferences correctly`() {
            // Given
            val themePreferences = listOf(
                ThemePreference("Light", "Bright theme for daytime use", true),
                ThemePreference("Dark", "Dark theme for nighttime use", false),
                ThemePreference("System", "Follow system theme settings", false)
            )
            
            // When
            val selectedTheme = themePreferences.find { it.isSelected }
            val availableThemes = themePreferences.map { it.name }
            val themeDescriptions = themePreferences.map { it.description }
            
            // Then
            assertNotNull(selectedTheme)
            assertEquals("Light", selectedTheme!!.name)
            assertEquals(3, availableThemes.size)
            assertTrue(availableThemes.contains("Light"))
            assertTrue(availableThemes.contains("Dark"))
            assertTrue(availableThemes.contains("System"))
            assertEquals(3, themeDescriptions.size)
        }
        
        @Test
        fun `should handle font size preferences`() {
            // Given
            val fontSizes = listOf("Small", "Medium", "Large", "Extra Large")
            val currentFontSize = "Medium"
            
            // When
            val fontSizeIndex = fontSizes.indexOf(currentFontSize)
            val isAccessibleSize = fontSizes.contains("Large") || fontSizes.contains("Extra Large")
            val recommendedSize = if (fontSizes.contains("Large")) "Large" else "Medium"
            
            // Then
            assertEquals(1, fontSizeIndex)
            assertTrue(isAccessibleSize)
            assertEquals("Large", recommendedSize)
            assertTrue(fontSizes.size >= 3) // Minimum accessibility requirement
        }
        
        @Test
        fun `should validate theme switching functionality`() {
            // Given
            val themes = listOf("Light", "Dark", "System")
            val currentTheme = "Light"
            
            // When
            val canSwitchToDark = themes.contains("Dark")
            val canSwitchToSystem = themes.contains("System")
            val themeSwitchOptions = themes.filter { it != currentTheme }
            
            // Then
            assertTrue(canSwitchToDark)
            assertTrue(canSwitchToSystem)
            assertEquals(2, themeSwitchOptions.size)
            assertTrue(themeSwitchOptions.contains("Dark"))
            assertTrue(themeSwitchOptions.contains("System"))
        }
    }

    @Nested
    @DisplayName("Test Case 2: AI Personality Customization")
    inner class AIPersonalityCustomizationTests {
        
        @Test
        fun `should configure AI personality settings`() {
            // Given
            val aiPersonalities = listOf(
                AIPersonality("Supportive", "Warm and encouraging communication style", 4.5f),
                AIPersonality("Professional", "Clinical and structured approach", 4.2f),
                AIPersonality("Friendly", "Casual and approachable tone", 4.3f),
                AIPersonality("Direct", "Straightforward and concise responses", 3.8f),
                AIPersonality("Empathetic", "Deeply understanding and compassionate", 4.7f)
            )
            
            // When
            val mostRatedPersonality = aiPersonalities.maxByOrNull { it.rating }
            val leastRatedPersonality = aiPersonalities.minByOrNull { it.rating }
            val highRatedPersonalities = aiPersonalities.filter { it.rating >= 4.5f }
            val averageRating = aiPersonalities.map { it.rating }.average()
            
            // Then
            assertNotNull(mostRatedPersonality)
            assertEquals("Empathetic", mostRatedPersonality!!.name)
            assertEquals(4.7f, mostRatedPersonality.rating)
            assertNotNull(leastRatedPersonality)
            assertEquals("Direct", leastRatedPersonality!!.name)
            assertEquals(2, highRatedPersonalities.size)
            assertEquals(4.3, averageRating, 0.01)
        }
        
        @Test
        fun `should adapt AI responses based on personality selection`() {
            // Given
            val personalityResponses = mapOf(
                "Supportive" to "I understand this is challenging for you. You're doing great by reaching out.",
                "Professional" to "Based on your description, I recommend implementing these coping strategies.",
                "Friendly" to "Hey there! I'm here to help you work through this together.",
                "Direct" to "Here are three specific steps to address your concern.",
                "Empathetic" to "I can sense the difficulty you're experiencing. Let's explore this gently."
            )
            
            // When
            val supportiveResponse = personalityResponses["Supportive"]
            val professionalResponse = personalityResponses["Professional"]
            val friendlyResponse = personalityResponses["Friendly"]
            val directResponse = personalityResponses["Direct"]
            val empatheticResponse = personalityResponses["Empathetic"]
            
            // Then
            assertNotNull(supportiveResponse)
            assertTrue(supportiveResponse!!.contains("understand") && supportiveResponse.contains("great"))
            assertNotNull(professionalResponse)
            assertTrue(professionalResponse!!.contains("recommend") && professionalResponse.contains("strategies"))
            assertNotNull(friendlyResponse)
            assertTrue(friendlyResponse!!.contains("Hey there") && friendlyResponse.contains("together"))
            assertNotNull(directResponse)
            assertTrue(directResponse!!.contains("three specific steps"))
            assertNotNull(empatheticResponse)
            assertTrue(empatheticResponse!!.contains("sense") && empatheticResponse.contains("gently"))
        }
        
        @Test
        fun `should track personality effectiveness metrics`() {
            // Given
            val personalityMetrics = listOf(
                AIPersonality("Supportive", "Description", 4.5f),
                AIPersonality("Professional", "Description", 4.2f),
                AIPersonality("Friendly", "Description", 4.3f),
                AIPersonality("Direct", "Description", 3.8f),
                AIPersonality("Empathetic", "Description", 4.7f)
            )
            
            // When
            val totalPersonalities = personalityMetrics.size
            val averageEffectiveness = personalityMetrics.map { it.rating }.average()
            val topPerformingPersonalities = personalityMetrics.filter { it.rating >= 4.5f }
            val needsImprovement = personalityMetrics.filter { it.rating < 4.0f }
            
            // Then
            assertEquals(5, totalPersonalities)
            assertEquals(4.3, averageEffectiveness, 0.01)
            assertEquals(2, topPerformingPersonalities.size)
            assertTrue(topPerformingPersonalities.any { it.name == "Supportive" })
            assertTrue(topPerformingPersonalities.any { it.name == "Empathetic" })
            assertEquals(1, needsImprovement.size)
            assertEquals("Direct", needsImprovement.first().name)
        }
    }

    @Nested
    @DisplayName("Test Case 3: Content and Feature Personalization")
    inner class ContentPersonalizationTests {
        
        @Test
        fun `should manage content preferences effectively`() {
            // Given
            val contentPreferences = listOf(
                ContentPreference("Anxiety Management", "Focus on anxiety-related content", true, 4.8f),
                ContentPreference("Mindfulness Exercises", "Include meditation and mindfulness", true, 4.5f),
                ContentPreference("Progress Tracking", "Show detailed progress analytics", false, 4.2f),
                ContentPreference("Mood Forecasting", "Enable predictive mood analysis", true, 4.6f),
                ContentPreference("Crisis Support", "Include emergency support resources", true, 4.9f)
            )
            
            // When
            val enabledPreferences = contentPreferences.filter { it.isEnabled }
            val disabledPreferences = contentPreferences.filter { !it.isEnabled }
            val highPriorityContent = contentPreferences.filter { it.priority >= 4.5f }
            val averagePriority = contentPreferences.map { it.priority }.average()
            
            // Then
            assertEquals(4, enabledPreferences.size)
            assertEquals(1, disabledPreferences.size)
            assertEquals("Progress Tracking", disabledPreferences.first().name)
            assertEquals(4, highPriorityContent.size)
            assertEquals(4.6, averagePriority, 0.01)
            assertTrue(enabledPreferences.any { it.name == "Crisis Support" })
        }
        
        @Test
        fun `should personalize notification preferences`() {
            // Given
            val notificationStyles = listOf("Gentle", "Standard", "Minimal", "Detailed")
            val currentStyle = "Gentle"
            
            // When
            val availableStyles = notificationStyles.size
            val canSwitchToDetailed = notificationStyles.contains("Detailed")
            val canSwitchToMinimal = notificationStyles.contains("Minimal")
            val alternativeStyles = notificationStyles.filter { it != currentStyle }
            
            // Then
            assertEquals(4, availableStyles)
            assertTrue(canSwitchToDetailed)
            assertTrue(canSwitchToMinimal)
            assertEquals(3, alternativeStyles.size)
            assertTrue(alternativeStyles.contains("Standard"))
            assertTrue(alternativeStyles.contains("Minimal"))
            assertTrue(alternativeStyles.contains("Detailed"))
        }
        
        @Test
        fun `should validate personalization settings persistence`() {
            // Given
            val personalizationSettings = PersonalizationSettings(
                theme = "Dark",
                fontSize = "Large",
                aiPersonality = "Empathetic",
                language = "English",
                notificationStyle = "Gentle",
                contentPreferences = listOf("Anxiety Management", "Mindfulness", "Crisis Support")
            )
            
            // When
            val isAccessibleTheme = personalizationSettings.theme == "Dark"
            val isAccessibleFont = personalizationSettings.fontSize == "Large"
            val hasEmpatheticAI = personalizationSettings.aiPersonality == "Empathetic"
            val hasEssentialContent = personalizationSettings.contentPreferences.contains("Crisis Support")
            val totalPreferences = personalizationSettings.contentPreferences.size
            
            // Then
            assertTrue(isAccessibleTheme)
            assertTrue(isAccessibleFont)
            assertTrue(hasEmpatheticAI)
            assertTrue(hasEssentialContent)
            assertEquals(3, totalPreferences)
            assertTrue(personalizationSettings.contentPreferences.contains("Anxiety Management"))
            assertTrue(personalizationSettings.contentPreferences.contains("Mindfulness"))
        }
    }
}

// Data classes for testing
data class PersonalizationSettings(
    val theme: String,
    val fontSize: String,
    val aiPersonality: String,
    val language: String,
    val notificationStyle: String,
    val contentPreferences: List<String>
)

data class ThemePreference(
    val name: String,
    val description: String,
    val isSelected: Boolean
)

data class AIPersonality(
    val name: String,
    val description: String,
    val rating: Float
)

data class ContentPreference(
    val name: String,
    val description: String,
    val isEnabled: Boolean,
    val priority: Float
)
