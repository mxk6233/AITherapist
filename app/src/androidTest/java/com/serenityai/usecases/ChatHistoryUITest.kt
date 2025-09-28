package com.serenityai.usecases

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.serenityai.AITherpistDashboard
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChatHistoryUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testChatHistoryUI() {
        composeTestRule.setContent {
            AITherpistDashboard()
        }

        // Test chat history interface
        composeTestRule.onNodeWithText("Chat History").assertIsDisplayed()
        composeTestRule.onNodeWithText("Search").assertIsDisplayed()
        composeTestRule.onNodeWithText("Filter").assertIsDisplayed()
        composeTestRule.onNodeWithText("Export").assertIsDisplayed()
    }

    @Test
    fun testChatHistoryList() {
        composeTestRule.setContent {
            AITherpistDashboard()
        }

        // Test chat history list
        composeTestRule.onNodeWithText("Session 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Session 2").assertIsDisplayed()
        composeTestRule.onNodeWithText("Session 3").assertIsDisplayed()
    }

    @Test
    fun testChatHistorySearch() {
        composeTestRule.setContent {
            AITherpistDashboard()
        }

        // Test chat history search
        composeTestRule.onNodeWithText("Search").performClick()
        composeTestRule.onNodeWithText("Search messages...").performTextInput("anxiety")
        composeTestRule.onNodeWithText("Search").performClick()
        composeTestRule.onNodeWithText("Results for 'anxiety'").assertIsDisplayed()
    }

    @Test
    fun testChatHistoryFilter() {
        composeTestRule.setContent {
            AITherpistDashboard()
        }

        // Test chat history filter
        composeTestRule.onNodeWithText("Filter").performClick()
        composeTestRule.onNodeWithText("Date Range").assertIsDisplayed()
        composeTestRule.onNodeWithText("Past 7 days").performClick()
        composeTestRule.onNodeWithText("Past 30 days").performClick()
        composeTestRule.onNodeWithText("All time").performClick()
    }

    @Test
    fun testChatHistoryExport() {
        composeTestRule.setContent {
            AITherpistDashboard()
        }

        // Test chat history export
        composeTestRule.onNodeWithText("Export").performClick()
        composeTestRule.onNodeWithText("Export as Text").assertIsDisplayed()
        composeTestRule.onNodeWithText("Export as JSON").assertIsDisplayed()
        composeTestRule.onNodeWithText("Export as PDF").assertIsDisplayed()
    }

    @Test
    fun testChatSessionDetails() {
        composeTestRule.setContent {
            AITherpistDashboard()
        }

        // Test chat session details
        composeTestRule.onNodeWithText("Session 1").performClick()
        composeTestRule.onNodeWithText("Session Details").assertIsDisplayed()
        composeTestRule.onNodeWithText("Duration").assertIsDisplayed()
        composeTestRule.onNodeWithText("Messages").assertIsDisplayed()
        composeTestRule.onNodeWithText("Mood Before").assertIsDisplayed()
        composeTestRule.onNodeWithText("Mood After").assertIsDisplayed()
    }

    @Test
    fun testChatHistoryAnalytics() {
        composeTestRule.setContent {
            AITherpistDashboard()
        }

        // Test chat history analytics
        composeTestRule.onNodeWithText("Analytics").performClick()
        composeTestRule.onNodeWithText("Total Sessions").assertIsDisplayed()
        composeTestRule.onNodeWithText("Average Duration").assertIsDisplayed()
        composeTestRule.onNodeWithText("Common Themes").assertIsDisplayed()
        composeTestRule.onNodeWithText("Mood Trends").assertIsDisplayed()
    }
}
