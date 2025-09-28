package com.serenityai.usecases

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.serenityai.AITherpistDashboard
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MoodLoggingUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testMoodLoggingUI() {
        composeTestRule.setContent {
            AITherpistDashboard()
        }

        // Test mood logging interface
        composeTestRule.onNodeWithText("Mood Tracking").assertIsDisplayed()
        composeTestRule.onNodeWithText("Rate your mood").assertIsDisplayed()
        composeTestRule.onNodeWithText("Add notes").assertIsDisplayed()
    }

    @Test
    fun testMoodSelection() {
        composeTestRule.setContent {
            AITherpistDashboard()
        }

        // Test mood selection (1-10 scale)
        composeTestRule.onNodeWithText("Rate your mood").performClick()
        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("5").performClick()
        composeTestRule.onNodeWithText("10").performClick()
    }

    @Test
    fun testMoodNotes() {
        composeTestRule.setContent {
            AITherpistDashboard()
        }

        // Test adding mood notes
        composeTestRule.onNodeWithText("Add notes").performClick()
        composeTestRule.onNodeWithText("How are you feeling?").performTextInput("I'm feeling stressed about work")
        composeTestRule.onNodeWithText("Save").performClick()
    }

    @Test
    fun testMoodFactors() {
        composeTestRule.setContent {
            AITherpistDashboard()
        }

        // Test mood factors
        composeTestRule.onNodeWithText("Energy Level").assertIsDisplayed()
        composeTestRule.onNodeWithText("Stress Level").assertIsDisplayed()
        composeTestRule.onNodeWithText("Anxiety Level").assertIsDisplayed()
        composeTestRule.onNodeWithText("Sleep Quality").assertIsDisplayed()
    }

    @Test
    fun testMoodHistory() {
        composeTestRule.setContent {
            AITherpistDashboard()
        }

        // Test mood history view
        composeTestRule.onNodeWithText("Mood History").performClick()
        composeTestRule.onNodeWithText("Past 7 days").assertIsDisplayed()
        composeTestRule.onNodeWithText("Past 30 days").assertIsDisplayed()
    }
}
