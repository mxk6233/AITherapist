package com.serenityai.usecases

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.serenityai.InteractiveMainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserRegistrationUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testRegistrationUI() {
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Test registration interface
        composeTestRule.onNodeWithText("Sign Up").assertIsDisplayed()
        composeTestRule.onNodeWithText("Email").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
        composeTestRule.onNodeWithText("Confirm Password").assertIsDisplayed()
        composeTestRule.onNodeWithText("Username").assertIsDisplayed()
    }

    @Test
    fun testRegistrationForm() {
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Test registration form
        composeTestRule.onNodeWithText("Email").performTextInput("test@example.com")
        composeTestRule.onNodeWithText("Password").performTextInput("password123")
        composeTestRule.onNodeWithText("Confirm Password").performTextInput("password123")
        composeTestRule.onNodeWithText("Username").performTextInput("testuser")
        composeTestRule.onNodeWithText("I agree to the terms").performClick()
        composeTestRule.onNodeWithText("Sign Up").performClick()
    }

    @Test
    fun testEmailValidation() {
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Test email validation
        composeTestRule.onNodeWithText("Email").performTextInput("invalid-email")
        composeTestRule.onNodeWithText("Sign Up").performClick()
        composeTestRule.onNodeWithText("Please enter a valid email").assertIsDisplayed()
    }

    @Test
    fun testPasswordValidation() {
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Test password validation
        composeTestRule.onNodeWithText("Password").performTextInput("123")
        composeTestRule.onNodeWithText("Sign Up").performClick()
        composeTestRule.onNodeWithText("Password must be at least 6 characters").assertIsDisplayed()
    }

    @Test
    fun testPasswordMismatch() {
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Test password mismatch
        composeTestRule.onNodeWithText("Password").performTextInput("password123")
        composeTestRule.onNodeWithText("Confirm Password").performTextInput("different123")
        composeTestRule.onNodeWithText("Sign Up").performClick()
        composeTestRule.onNodeWithText("Passwords do not match").assertIsDisplayed()
    }

    @Test
    fun testTermsAndConditions() {
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Test terms and conditions
        composeTestRule.onNodeWithText("Terms and Conditions").performClick()
        composeTestRule.onNodeWithText("Privacy Policy").assertIsDisplayed()
        composeTestRule.onNodeWithText("User Agreement").assertIsDisplayed()
    }
}
