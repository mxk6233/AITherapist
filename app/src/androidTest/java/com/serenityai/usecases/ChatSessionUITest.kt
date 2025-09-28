package com.serenityai.usecases

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.serenityai.AITherpistDashboard
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChatSessionUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testChatSessionUI() {
        composeTestRule.setContent {
            AITherpistDashboard()
        }

        // Test chat interface elements
        composeTestRule.onNodeWithText("Chat").assertIsDisplayed()
        composeTestRule.onNodeWithText("Send Message").assertIsDisplayed()
        composeTestRule.onNodeWithText("Voice Input").assertIsDisplayed()
    }

    @Test
    fun testSendMessageFlow() {
        composeTestRule.setContent {
            AITherpistDashboard()
        }

        // Test sending a message
        composeTestRule.onNodeWithText("Send Message").performClick()
        composeTestRule.onNodeWithText("Type your message...").performTextInput("I'm feeling anxious today")
        composeTestRule.onNodeWithText("Send").performClick()
        
        // Verify message was sent
        composeTestRule.onNodeWithText("I'm feeling anxious today").assertIsDisplayed()
    }

    @Test
    fun testVoiceInputFlow() {
        composeTestRule.setContent {
            AITherpistDashboard()
        }

        // Test voice input
        composeTestRule.onNodeWithText("Voice Input").performClick()
        composeTestRule.onNodeWithText("Recording...").assertIsDisplayed()
    }

    @Test
    fun testQuickActions() {
        composeTestRule.setContent {
            AITherpistDashboard()
        }

        // Test quick action buttons
        composeTestRule.onNodeWithText("I'm feeling anxious").performClick()
        composeTestRule.onNodeWithText("I need help").performClick()
        composeTestRule.onNodeWithText("I'm having a crisis").performClick()
    }
}
