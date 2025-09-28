package com.serenityai

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class IntegrationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun fullAppFlow_loginToChatToMoodToHome() {
        composeTestRule.setContent {
            AITherpistDashboard()
        }

        // Step 1: Login
        composeTestRule.onNodeWithText("Welcome to Serenity AI").assertIsDisplayed()
        composeTestRule.onNodeWithText("Email").performTextInput("test@example.com")
        composeTestRule.onNodeWithText("Password").performTextInput("password123")
        composeTestRule.onNodeWithText("Login").performClick()

        // Step 2: Accept disclaimer
        composeTestRule.onNodeWithText("Disclaimer").assertIsDisplayed()
        composeTestRule.onNodeWithText("Accept").performClick()

        // Step 3: Navigate to chat from home
        composeTestRule.onNodeWithText("Start AI Therapy Session").assertIsDisplayed()
        composeTestRule.onNodeWithText("Start AI Therapy Session").performClick()

        // Step 4: Send a message in chat
        composeTestRule.onNodeWithText("Type your message or use voice...")
            .performTextInput("I'm feeling anxious")
        composeTestRule.onNodeWithContentDescription("Send").performClick()

        // Step 5: Navigate back to home
        composeTestRule.onNodeWithContentDescription("Back").performClick()

        // Step 6: Navigate to mood tracking
        composeTestRule.onNodeWithText("Mood").performClick()

        // Step 7: Log mood
        composeTestRule.onNodeWithText("7").performClick()
        composeTestRule.onNodeWithText("What's contributing to your mood today?")
            .performTextInput("Had a good therapy session")
        composeTestRule.onNodeWithText("Save Mood Entry").performClick()

        // Step 8: Navigate back to home
        composeTestRule.onNodeWithContentDescription("Back").performClick()

        // Step 9: Verify we're back on home screen
        composeTestRule.onNodeWithText("Start AI Therapy Session").assertIsDisplayed()
    }

    @Test
    fun fullAppFlow_signUpFlow() {
        composeTestRule.setContent {
            AITherpistDashboard()
        }

        // Step 1: Start sign up process
        composeTestRule.onNodeWithText("Don't have an account? Sign up").performClick()

        // Step 2: Fill sign up form
        composeTestRule.onNodeWithText("Create Account").assertIsDisplayed()
        composeTestRule.onNodeWithText("Email").performTextInput("newuser@example.com")
        composeTestRule.onNodeWithText("Password").performTextInput("newpassword123")
        composeTestRule.onNodeWithText("Confirm Password").performTextInput("newpassword123")
        composeTestRule.onNodeWithText("Sign Up").performClick()

        // Step 3: Accept disclaimer
        composeTestRule.onNodeWithText("Disclaimer").assertIsDisplayed()
        composeTestRule.onNodeWithText("Accept").performClick()

        // Step 4: Verify we're on home screen
        composeTestRule.onNodeWithText("Start AI Therapy Session").assertIsDisplayed()
    }

    @Test
    fun fullAppFlow_journalFlow() {
        composeTestRule.setContent {
            AITherpistDashboard()
        }

        // Step 1: Login
        composeTestRule.onNodeWithText("Email").performTextInput("test@example.com")
        composeTestRule.onNodeWithText("Password").performTextInput("password123")
        composeTestRule.onNodeWithText("Login").performClick()

        // Step 2: Accept disclaimer
        composeTestRule.onNodeWithText("Accept").performClick()

        // Step 3: Navigate to journal
        composeTestRule.onNodeWithText("Journal").performClick()

        // Step 4: Create journal entry
        composeTestRule.onNodeWithText("Journal Entry").assertIsDisplayed()
        composeTestRule.onNodeWithText("Entry title (optional)")
            .performTextInput("My thoughts today")
        composeTestRule.onNodeWithText("Write your thoughts here...")
            .performTextInput("Today I learned about managing anxiety through breathing exercises.")
        composeTestRule.onNodeWithText("Save Entry").performClick()

        // Step 5: Navigate back
        composeTestRule.onNodeWithContentDescription("Back").performClick()

        // Step 6: Verify we're back on home screen
        composeTestRule.onNodeWithText("Start AI Therapy Session").assertIsDisplayed()
    }

    @Test
    fun fullAppFlow_challengesFlow() {
        composeTestRule.setContent {
            AITherpistDashboard()
        }

        // Step 1: Login
        composeTestRule.onNodeWithText("Email").performTextInput("test@example.com")
        composeTestRule.onNodeWithText("Password").performTextInput("password123")
        composeTestRule.onNodeWithText("Login").performClick()

        // Step 2: Accept disclaimer
        composeTestRule.onNodeWithText("Accept").performClick()

        // Step 3: Navigate to challenges
        composeTestRule.onNodeWithText("Challenges").performClick()

        // Step 4: Start a challenge
        composeTestRule.onNodeWithText("Wellness Challenges").assertIsDisplayed()
        composeTestRule.onNodeWithText("Start Challenge").performClick()

        // Step 5: Navigate back
        composeTestRule.onNodeWithContentDescription("Back").performClick()

        // Step 6: Verify we're back on home screen
        composeTestRule.onNodeWithText("Start AI Therapy Session").assertIsDisplayed()
    }

    @Test
    fun fullAppFlow_rewardsFlow() {
        composeTestRule.setContent {
            AITherpistDashboard()
        }

        // Step 1: Login
        composeTestRule.onNodeWithText("Email").performTextInput("test@example.com")
        composeTestRule.onNodeWithText("Password").performTextInput("password123")
        composeTestRule.onNodeWithText("Login").performClick()

        // Step 2: Accept disclaimer
        composeTestRule.onNodeWithText("Accept").performClick()

        // Step 3: Navigate to rewards
        composeTestRule.onNodeWithText("Rewards").performClick()

        // Step 4: View rewards
        composeTestRule.onNodeWithText("Rewards & Achievements").assertIsDisplayed()
        composeTestRule.onNodeWithText("Your Progress").assertIsDisplayed()
        composeTestRule.onNodeWithText("Your Badges").assertIsDisplayed()
        composeTestRule.onNodeWithText("Daily Affirmation").assertIsDisplayed()

        // Step 5: Navigate back
        composeTestRule.onNodeWithContentDescription("Back").performClick()

        // Step 6: Verify we're back on home screen
        composeTestRule.onNodeWithText("Start AI Therapy Session").assertIsDisplayed()
    }

    @Test
    fun fullAppFlow_logoutFlow() {
        composeTestRule.setContent {
            AITherpistDashboard()
        }

        // Step 1: Login
        composeTestRule.onNodeWithText("Email").performTextInput("test@example.com")
        composeTestRule.onNodeWithText("Password").performTextInput("password123")
        composeTestRule.onNodeWithText("Login").performClick()

        // Step 2: Accept disclaimer
        composeTestRule.onNodeWithText("Accept").performClick()

        // Step 3: Verify we're logged in
        composeTestRule.onNodeWithText("Start AI Therapy Session").assertIsDisplayed()

        // Step 4: Logout
        composeTestRule.onNodeWithText("Logout").performClick()

        // Step 5: Verify we're back to login screen
        composeTestRule.onNodeWithText("Welcome to Serenity AI").assertIsDisplayed()
    }

    @Test
    fun fullAppFlow_errorHandling() {
        composeTestRule.setContent {
            AITherpistDashboard()
        }

        // Step 1: Try to login with empty credentials
        composeTestRule.onNodeWithText("Login").performClick()

        // Step 2: Verify error handling (should show dialog)
        composeTestRule.onNodeWithText("Please enter both email and password").assertIsDisplayed()
        composeTestRule.onNodeWithText("OK").performClick()

        // Step 3: Try to login with invalid credentials
        composeTestRule.onNodeWithText("Email").performTextInput("invalid@email.com")
        composeTestRule.onNodeWithText("Password").performTextInput("wrongpassword")
        composeTestRule.onNodeWithText("Login").performClick()

        // Step 4: Should still show error dialog
        composeTestRule.onNodeWithText("Please enter both email and password").assertIsDisplayed()
    }
}
