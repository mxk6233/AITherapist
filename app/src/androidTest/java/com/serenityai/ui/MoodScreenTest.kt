package com.serenityai.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.serenityai.InteractiveMainActivity

@RunWith(AndroidJUnit4::class)
class MoodScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun moodScreen_displaysCorrectElements() {
        composeTestRule.setContent {
            // We need to import the MoodScreen from the main activity
            // For now, we'll test the main activity which contains the mood screen
            InteractiveMainActivity()
        }

        // Navigate to mood screen first
        composeTestRule.onNodeWithText("Mood").performClick()
        
        // Check if mood screen elements are displayed
        composeTestRule.onNodeWithText("Mood Tracking").assertIsDisplayed()
        composeTestRule.onNodeWithText("How are you feeling today?").assertIsDisplayed()
        composeTestRule.onNodeWithText("Add notes (optional)").assertIsDisplayed()
        composeTestRule.onNodeWithText("Save Mood Entry").assertIsDisplayed()
    }

    @Test
    fun moodScreen_allowsMoodSelection() {
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Navigate to mood screen
        composeTestRule.onNodeWithText("Mood").performClick()
        
        // Test mood selection buttons (1-10)
        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("Selected: 1/10").assertIsDisplayed()
        
        composeTestRule.onNodeWithText("5").performClick()
        composeTestRule.onNodeWithText("Selected: 5/10").assertIsDisplayed()
        
        composeTestRule.onNodeWithText("10").performClick()
        composeTestRule.onNodeWithText("Selected: 10/10").assertIsDisplayed()
    }

    @Test
    fun moodScreen_allowsNotesInput() {
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Navigate to mood screen
        composeTestRule.onNodeWithText("Mood").performClick()
        
        // Find the notes field and enter text
        val notesField = composeTestRule.onNodeWithText("What's contributing to your mood today?")
        notesField.performTextInput("Feeling great today!")
        notesField.assertTextContains("Feeling great today!")
    }

    @Test
    fun moodScreen_savesMoodEntry() {
        var dialogShown = false
        
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Navigate to mood screen
        composeTestRule.onNodeWithText("Mood").performClick()
        
        // Select a mood
        composeTestRule.onNodeWithText("8").performClick()
        
        // Add notes
        composeTestRule.onNodeWithText("What's contributing to your mood today?")
            .performTextInput("Had a productive day")
        
        // Click save button
        composeTestRule.onNodeWithText("Save Mood Entry").performClick()
        
        // Check if success dialog appears
        composeTestRule.onNodeWithText("Mood logged successfully! Your mood: 8/10").assertIsDisplayed()
    }

    @Test
    fun moodScreen_navigatesBackCorrectly() {
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Navigate to mood screen
        composeTestRule.onNodeWithText("Mood").performClick()
        
        // Verify we're on mood screen
        composeTestRule.onNodeWithText("Mood Tracking").assertIsDisplayed()
        
        // Click back button
        composeTestRule.onNodeWithContentDescription("Back").performClick()
        
        // Verify we're back on home screen
        composeTestRule.onNodeWithText("Start AI Therapy Session").assertIsDisplayed()
    }
}
