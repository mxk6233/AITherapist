package com.serenityai

import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.assertIsDisplayed
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.runner.RunWith

/**
 * Base test configuration for instrumented tests
 * Provides common setup and utilities for UI testing
 */
@RunWith(AndroidJUnit4::class)
abstract class TestConfiguration {
    
    @get:Rule
    val activityRule = ActivityScenarioRule(InteractiveMainActivity::class.java)
    
    /**
     * Wait for the app to be fully loaded
     */
    fun waitForAppToLoad() {
        // Add any necessary waiting logic here
        Thread.sleep(1000) // Simple wait for now
    }
    
    /**
     * Navigate to login screen
     */
    fun navigateToLogin(composeTestRule: ComposeTestRule) {
        composeTestRule.onNodeWithText("Welcome to Serenity AI").assertIsDisplayed()
    }
    
    /**
     * Perform login with test credentials
     */
    fun performLogin(composeTestRule: ComposeTestRule, email: String = "test@example.com", password: String = "password123") {
        composeTestRule.onNodeWithText("Email").performTextInput(email)
        composeTestRule.onNodeWithText("Password").performTextInput(password)
        composeTestRule.onNodeWithText("Login").performClick()
    }
    
    /**
     * Accept disclaimer
     */
    fun acceptDisclaimer(composeTestRule: ComposeTestRule) {
        composeTestRule.onNodeWithText("Accept").performClick()
    }
    
    /**
     * Navigate to home screen
     */
    fun navigateToHome(composeTestRule: ComposeTestRule) {
        composeTestRule.onNodeWithText("Start AI Therapy Session").assertIsDisplayed()
    }
    
    /**
     * Complete full login flow
     */
    fun completeLoginFlow(composeTestRule: ComposeTestRule) {
        navigateToLogin(composeTestRule)
        performLogin(composeTestRule)
        acceptDisclaimer(composeTestRule)
        navigateToHome(composeTestRule)
    }
}
