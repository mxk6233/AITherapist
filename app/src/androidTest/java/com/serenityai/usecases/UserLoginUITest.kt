package com.serenityai.usecases

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.serenityai.AITherpistDashboard
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserLoginUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testLoginUI() {
        composeTestRule.setContent {
            AITherpistDashboard()
        }

        // Test login interface
        composeTestRule.onNodeWithText("Login").assertIsDisplayed()
        composeTestRule.onNodeWithText("Email").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
        composeTestRule.onNodeWithText("Remember Me").assertIsDisplayed()
        composeTestRule.onNodeWithText("Forgot Password").assertIsDisplayed()
    }

    @Test
    fun testLoginForm() {
        composeTestRule.setContent {
            AITherpistDashboard()
        }

        // Test login form
        composeTestRule.onNodeWithText("Email").performTextInput("test@example.com")
        composeTestRule.onNodeWithText("Password").performTextInput("password123")
        composeTestRule.onNodeWithText("Remember Me").performClick()
        composeTestRule.onNodeWithText("Login").performClick()
    }

    @Test
    fun testLoginValidation() {
        composeTestRule.setContent {
            AITherpistDashboard()
        }

        // Test login validation
        composeTestRule.onNodeWithText("Email").performTextInput("invalid-email")
        composeTestRule.onNodeWithText("Password").performTextInput("123")
        composeTestRule.onNodeWithText("Login").performClick()
        composeTestRule.onNodeWithText("Please enter valid credentials").assertIsDisplayed()
    }

    @Test
    fun testForgotPassword() {
        composeTestRule.setContent {
            AITherpistDashboard()
        }

        // Test forgot password
        composeTestRule.onNodeWithText("Forgot Password").performClick()
        composeTestRule.onNodeWithText("Reset Password").assertIsDisplayed()
        composeTestRule.onNodeWithText("Enter your email").performTextInput("test@example.com")
        composeTestRule.onNodeWithText("Send Reset Link").performClick()
        composeTestRule.onNodeWithText("Reset link sent to your email").assertIsDisplayed()
    }

    @Test
    fun testBiometricLogin() {
        composeTestRule.setContent {
            AITherpistDashboard()
        }

        // Test biometric login
        composeTestRule.onNodeWithText("Use Biometric").performClick()
        composeTestRule.onNodeWithText("Touch ID").assertIsDisplayed()
        composeTestRule.onNodeWithText("Face ID").assertIsDisplayed()
        composeTestRule.onNodeWithText("Fingerprint").assertIsDisplayed()
    }

    @Test
    fun testSocialLogin() {
        composeTestRule.setContent {
            AITherpistDashboard()
        }

        // Test social login
        composeTestRule.onNodeWithText("Login with Google").assertIsDisplayed()
        composeTestRule.onNodeWithText("Login with Apple").assertIsDisplayed()
        composeTestRule.onNodeWithText("Login with Facebook").assertIsDisplayed()
    }

    @Test
    fun testLoginErrorHandling() {
        composeTestRule.setContent {
            AITherpistDashboard()
        }

        // Test login error handling
        composeTestRule.onNodeWithText("Email").performTextInput("nonexistent@example.com")
        composeTestRule.onNodeWithText("Password").performTextInput("wrongpassword")
        composeTestRule.onNodeWithText("Login").performClick()
        composeTestRule.onNodeWithText("Invalid email or password").assertIsDisplayed()
    }

    @Test
    fun testLoginSuccess() {
        composeTestRule.setContent {
            AITherpistDashboard()
        }

        // Test successful login
        composeTestRule.onNodeWithText("Email").performTextInput("test@example.com")
        composeTestRule.onNodeWithText("Password").performTextInput("password123")
        composeTestRule.onNodeWithText("Login").performClick()
        composeTestRule.onNodeWithText("Welcome back").assertIsDisplayed()
    }
}
