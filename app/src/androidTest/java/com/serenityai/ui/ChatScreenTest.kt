package com.serenityai.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.serenityai.InteractiveMainActivity

@RunWith(AndroidJUnit4::class)
class ChatScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun chatScreen_displaysCorrectElements() {
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Navigate to chat screen
        composeTestRule.onNodeWithText("Start AI Therapy Session").performClick()
        
        // Check if chat screen elements are displayed
        composeTestRule.onNodeWithText("Type your message or use voice...").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Start voice input").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Send").assertIsDisplayed()
        
        // Check for quick action buttons
        composeTestRule.onNodeWithText("Sad").assertIsDisplayed()
        composeTestRule.onNodeWithText("Anxious").assertIsDisplayed()
        composeTestRule.onNodeWithText("Work").assertIsDisplayed()
        composeTestRule.onNodeWithText("Crisis").assertIsDisplayed()
    }

    @Test
    fun chatScreen_allowsMessageInput() {
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Navigate to chat screen
        composeTestRule.onNodeWithText("Start AI Therapy Session").performClick()
        
        // Enter a message
        val messageField = composeTestRule.onNodeWithText("Type your message or use voice...")
        messageField.performTextInput("I'm feeling anxious today")
        messageField.assertTextContains("I'm feeling anxious today")
    }

    @Test
    fun chatScreen_sendsMessage() {
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Navigate to chat screen
        composeTestRule.onNodeWithText("Start AI Therapy Session").performClick()
        
        // Enter a message
        composeTestRule.onNodeWithText("Type your message or use voice...")
            .performTextInput("Hello, I need help")
        
        // Click send button
        composeTestRule.onNodeWithContentDescription("Send").performClick()
        
        // Check if message appears in chat
        composeTestRule.onNodeWithText("Hello, I need help").assertIsDisplayed()
        
        // Check if AI response appears (should contain therapeutic content)
        composeTestRule.onNodeWithText("I can feel").assertIsDisplayed()
    }

    @Test
    fun chatScreen_quickActionsWork() {
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Navigate to chat screen
        composeTestRule.onNodeWithText("Start AI Therapy Session").performClick()
        
        // Test Sad quick action
        composeTestRule.onNodeWithText("Sad").performClick()
        composeTestRule.onNodeWithText("I'm feeling sad today").assertIsDisplayed()
        
        // Test Anxious quick action
        composeTestRule.onNodeWithText("Anxious").performClick()
        composeTestRule.onNodeWithText("I'm feeling anxious and worried").assertIsDisplayed()
        
        // Test Work quick action
        composeTestRule.onNodeWithText("Work").performClick()
        composeTestRule.onNodeWithText("I'm stressed about work").assertIsDisplayed()
        
        // Test Crisis quick action
        composeTestRule.onNodeWithText("Crisis").performClick()
        composeTestRule.onNodeWithText("I need immediate help and support").assertIsDisplayed()
    }

    @Test
    fun chatScreen_handlesVoiceInput() {
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Navigate to chat screen
        composeTestRule.onNodeWithText("Start AI Therapy Session").performClick()
        
        // Click voice input button
        composeTestRule.onNodeWithContentDescription("Start voice input").performClick()
        
        // Check if listening state is indicated
        composeTestRule.onNodeWithText("Listening... Speak now").assertIsDisplayed()
        
        // Click stop listening
        composeTestRule.onNodeWithContentDescription("Stop listening").performClick()
    }

    @Test
    fun chatScreen_displaysInitialMessages() {
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Navigate to chat screen
        composeTestRule.onNodeWithText("Start AI Therapy Session").performClick()
        
        // Check if initial AI messages are displayed
        composeTestRule.onNodeWithText("Hi there! I'm your AI mental health companion").assertIsDisplayed()
        composeTestRule.onNodeWithText("Feel free to share what's on your mind").assertIsDisplayed()
    }

    @Test
    fun chatScreen_navigatesBackCorrectly() {
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Navigate to chat screen
        composeTestRule.onNodeWithText("Start AI Therapy Session").performClick()
        
        // Verify we're on chat screen
        composeTestRule.onNodeWithText("Type your message or use voice...").assertIsDisplayed()
        
        // Click back button
        composeTestRule.onNodeWithContentDescription("Back").performClick()
        
        // Verify we're back on home screen
        composeTestRule.onNodeWithText("Start AI Therapy Session").assertIsDisplayed()
    }

    @Test
    fun chatScreen_handlesEmptyMessage() {
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Navigate to chat screen
        composeTestRule.onNodeWithText("Start AI Therapy Session").performClick()
        
        // Try to send empty message
        composeTestRule.onNodeWithContentDescription("Send").performClick()
        
        // Should not add any new messages
        // The initial messages should still be there
        composeTestRule.onNodeWithText("Hi there! I'm your AI mental health companion").assertIsDisplayed()
    }
}
