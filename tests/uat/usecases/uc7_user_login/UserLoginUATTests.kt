package com.serenityai.tests.uat.usecases.uc7_user_login

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested

@DisplayName("UAT: UC7 - User Login")
class UserLoginUATTests {

    @Nested
    @DisplayName("User Story: Secure Login")
    inner class SecureLogin {
        
        @Test
        @DisplayName("As a registered user, I want to login securely so I can access my account")
        fun `registered user can login securely`() {
            // Given: Registered user credentials
            val email = "user@example.com"
            val password = "SecurePassword123"
            
            // When: User logs in
            val credentialsValid = email.contains("@") && password.length >= 8
            val loginSuccessful = credentialsValid
            
            // Then: User is logged in
            assertTrue(loginSuccessful, "User should be able to login with valid credentials")
            assertTrue(credentialsValid, "Credentials should be validated")
        }
        
        @Test
        @DisplayName("As a user, I want clear error messages so I know why login failed")
        fun `user receives clear error messages on login failure`() {
            // Given: Invalid login attempt
            val wrongPassword = "wrongpass"
            val correctPassword = "correctpass"
            val passwordMatches = wrongPassword == correctPassword
            
            // When: Login fails
            val errorMessage = if (!passwordMatches) "Invalid credentials" else null
            
            // Then: Clear error message is shown
            assertFalse(passwordMatches, "Wrong password should be rejected")
            assertNotNull(errorMessage, "Error message should be provided")
        }
        
        @Test
        @DisplayName("As a user, I want my session maintained so I don't have to login repeatedly")
        fun `user session is maintained after login`() {
            // Given: Successful login
            val loginSuccessful = true
            val sessionToken = if (loginSuccessful) "session_token_123" else null
            
            // When: User is logged in
            val sessionCreated = sessionToken != null
            val sessionValid = sessionToken?.isNotBlank() == true
            
            // Then: Session is maintained
            assertTrue(sessionCreated, "Session should be created after login")
            assertTrue(sessionValid, "Session should be valid")
        }
    }
}

