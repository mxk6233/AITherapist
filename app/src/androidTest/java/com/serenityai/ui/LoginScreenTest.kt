package com.serenityai.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.serenityai.ui.auth.LoginScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loginScreen_displaysCorrectElements() {
        composeTestRule.setContent {
            LoginScreen(
                onLoginClick = { _, _ -> },
                onSignUpClick = { },
                isLoading = false,
                error = null
            )
        }

        // Check if main elements are displayed
        composeTestRule.onNodeWithText("Welcome to Serenity AI").assertIsDisplayed()
        composeTestRule.onNodeWithText("Email").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
        composeTestRule.onNodeWithText("Login").assertIsDisplayed()
        composeTestRule.onNodeWithText("Don't have an account? Sign up").assertIsDisplayed()
    }

    @Test
    fun loginScreen_allowsEmailInput() {
        composeTestRule.setContent {
            LoginScreen(
                onLoginClick = { _, _ -> },
                onSignUpClick = { },
                isLoading = false,
                error = null
            )
        }

        val emailField = composeTestRule.onNodeWithText("Email")
        emailField.assertIsDisplayed()
        emailField.performTextInput("test@example.com")
        emailField.assertTextContains("test@example.com")
    }

    @Test
    fun loginScreen_allowsPasswordInput() {
        composeTestRule.setContent {
            LoginScreen(
                onLoginClick = { _, _ -> },
                onSignUpClick = { },
                isLoading = false,
                error = null
            )
        }

        val passwordField = composeTestRule.onNodeWithText("Password")
        passwordField.assertIsDisplayed()
        passwordField.performTextInput("password123")
        passwordField.assertTextContains("password123")
    }

    @Test
    fun loginScreen_showsLoadingState() {
        composeTestRule.setContent {
            LoginScreen(
                onLoginClick = { _, _ -> },
                onSignUpClick = { },
                isLoading = true,
                error = null
            )
        }

        // Check if loading indicator is displayed
        composeTestRule.onNodeWithContentDescription("Loading").assertIsDisplayed()
        // Login button should be disabled during loading
        composeTestRule.onNodeWithText("Login").assertIsNotEnabled()
    }

    @Test
    fun loginScreen_showsErrorWhenProvided() {
        val errorMessage = "Invalid credentials"
        
        composeTestRule.setContent {
            LoginScreen(
                onLoginClick = { _, _ -> },
                onSignUpClick = { },
                isLoading = false,
                error = errorMessage
            )
        }

        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
    }

    @Test
    fun loginScreen_callsOnLoginClickWithCorrectParameters() {
        var capturedEmail = ""
        var capturedPassword = ""
        
        composeTestRule.setContent {
            LoginScreen(
                onLoginClick = { email, password ->
                    capturedEmail = email
                    capturedPassword = password
                },
                onSignUpClick = { },
                isLoading = false,
                error = null
            )
        }

        // Enter credentials
        composeTestRule.onNodeWithText("Email").performTextInput("test@example.com")
        composeTestRule.onNodeWithText("Password").performTextInput("password123")
        
        // Click login button
        composeTestRule.onNodeWithText("Login").performClick()
        
        // Verify the callback was called with correct parameters
        assert(capturedEmail == "test@example.com")
        assert(capturedPassword == "password123")
    }

    @Test
    fun loginScreen_callsOnSignUpClickWhenSignUpButtonClicked() {
        var signUpClicked = false
        
        composeTestRule.setContent {
            LoginScreen(
                onLoginClick = { _, _ -> },
                onSignUpClick = { signUpClicked = true },
                isLoading = false,
                error = null
            )
        }

        composeTestRule.onNodeWithText("Don't have an account? Sign up").performClick()
        assert(signUpClicked)
    }

    @Test
    fun loginScreen_handlesEmptyInputs() {
        var loginClicked = false
        
        composeTestRule.setContent {
            LoginScreen(
                onLoginClick = { _, _ -> loginClicked = true },
                onSignUpClick = { },
                isLoading = false,
                error = null
            )
        }

        // Click login without entering any text
        composeTestRule.onNodeWithText("Login").performClick()
        
        // Verify login was attempted (the actual validation happens in the parent component)
        assert(loginClicked)
    }
}
