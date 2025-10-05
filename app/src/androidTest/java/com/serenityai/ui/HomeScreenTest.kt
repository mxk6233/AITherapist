package com.serenityai.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.serenityai.InteractiveMainActivity

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun homeScreen_displaysCorrectElements() {
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Check if main home screen elements are displayed
        composeTestRule.onNodeWithText("Start AI Therapy Session").assertIsDisplayed()
        composeTestRule.onNodeWithText("Begin your mental health journey").assertIsDisplayed()
        composeTestRule.onNodeWithText("Mood").assertIsDisplayed()
        composeTestRule.onNodeWithText("Track emotions").assertIsDisplayed()
        composeTestRule.onNodeWithText("Journal").assertIsDisplayed()
        composeTestRule.onNodeWithText("Reflect & write").assertIsDisplayed()
        composeTestRule.onNodeWithText("Challenges").assertIsDisplayed()
        composeTestRule.onNodeWithText("Wellness goals").assertIsDisplayed()
        composeTestRule.onNodeWithText("Rewards").assertIsDisplayed()
        composeTestRule.onNodeWithText("Track progress").assertIsDisplayed()
    }

    @Test
    fun homeScreen_navigatesToChatScreen() {
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Click on AI Therapy Session button
        composeTestRule.onNodeWithText("Start AI Therapy Session").performClick()
        
        // Verify navigation to chat screen
        composeTestRule.onNodeWithText("Type your message or use voice...").assertIsDisplayed()
    }

    @Test
    fun homeScreen_navigatesToMoodScreen() {
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Click on Mood button
        composeTestRule.onNodeWithText("Mood").performClick()
        
        // Verify navigation to mood screen
        composeTestRule.onNodeWithText("Mood Tracking").assertIsDisplayed()
    }

    @Test
    fun homeScreen_navigatesToJournalScreen() {
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Click on Journal button
        composeTestRule.onNodeWithText("Journal").performClick()
        
        // Verify navigation to journal screen
        composeTestRule.onNodeWithText("Journal Entry").assertIsDisplayed()
    }

    @Test
    fun homeScreen_navigatesToChallengesScreen() {
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Click on Challenges button
        composeTestRule.onNodeWithText("Challenges").performClick()
        
        // Verify navigation to challenges screen
        composeTestRule.onNodeWithText("Wellness Challenges").assertIsDisplayed()
    }

    @Test
    fun homeScreen_navigatesToRewardsScreen() {
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Click on Rewards button
        composeTestRule.onNodeWithText("Rewards").performClick()
        
        // Verify navigation to rewards screen
        composeTestRule.onNodeWithText("Rewards & Achievements").assertIsDisplayed()
    }

    @Test
    fun homeScreen_displaysLogoutButton() {
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Check if logout button is displayed
        composeTestRule.onNodeWithText("Logout").assertIsDisplayed()
    }

    @Test
    fun homeScreen_logoutFunctionality() {
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Click logout button
        composeTestRule.onNodeWithText("Logout").performClick()
        
        // Verify we're back to login screen
        composeTestRule.onNodeWithText("Welcome to Serenity AI").assertIsDisplayed()
    }

    @Test
    fun homeScreen_displaysCorrectIcons() {
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Check if icons are displayed (using content descriptions)
        composeTestRule.onNodeWithContentDescription("AI Chat").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Navigate").assertIsDisplayed()
    }

    @Test
    fun homeScreen_hasProperLayout() {
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Verify the main therapy session button is prominent
        composeTestRule.onNodeWithText("Start AI Therapy Session")
            .assertIsDisplayed()
            .assertHasClickAction()
        
        // Verify secondary features are displayed in grid
        composeTestRule.onNodeWithText("Mood").assertIsDisplayed()
        composeTestRule.onNodeWithText("Journal").assertIsDisplayed()
        composeTestRule.onNodeWithText("Challenges").assertIsDisplayed()
        composeTestRule.onNodeWithText("Rewards").assertIsDisplayed()
    }
}
