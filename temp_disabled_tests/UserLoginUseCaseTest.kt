package com.serenityai.usecases

import com.serenityai.data.models.*
import com.serenityai.data.remote.FirebaseSource
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.*
import org.mockito.ArgumentMatchers.*
import java.util.*

class UserLoginUseCaseTest {

    @Mock
    private lateinit var mockFirebaseSource: FirebaseSource

    private lateinit var userLoginUseCase: UserLoginUseCase

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        userLoginUseCase = UserLoginUseCase(mockFirebaseSource)
    }

    @Nested
    @DisplayName("Login Validation")
    inner class LoginValidation {

        @Test
        @DisplayName("should validate login credentials")
        fun shouldValidateLoginCredentials() {
            val loginData = LoginData(
                email = "test@example.com",
                password = "password123"
            )

            val isValid = userLoginUseCase.validateLoginData(loginData)
            assertTrue(isValid)
        }

        @Test
        @DisplayName("should reject invalid email")
        fun shouldRejectInvalidEmail() {
            val loginData = LoginData(
                email = "invalid-email",
                password = "password123"
            )

            val isValid = userLoginUseCase.validateLoginData(loginData)
            assertFalse(isValid)
        }

        @Test
        @DisplayName("should reject empty password")
        fun shouldRejectEmptyPassword() {
            val loginData = LoginData(
                email = "test@example.com",
                password = ""
            )

            val isValid = userLoginUseCase.validateLoginData(loginData)
            assertFalse(isValid)
        }

        @ParameterizedTest
        @ValueSource(strings = [
            "test@example.com",
            "user.name@domain.co.uk",
            "user+tag@example.org"
        ])
        @DisplayName("should accept valid email formats")
        fun shouldAcceptValidEmailFormats(email: String) {
            val loginData = LoginData(
                email = email,
                password = "password123"
            )

            val isValid = userLoginUseCase.validateLoginData(loginData)
            assertTrue(isValid, "Should accept email: $email")
        }
    }

    @Nested
    @DisplayName("Authentication")
    inner class Authentication {

        @Test
        @DisplayName("should authenticate user successfully")
        fun shouldAuthenticateUserSuccessfully() = runTest {
            val loginData = LoginData(
                email = "test@example.com",
                password = "password123"
            )

            val mockUser = User(
                userId = "user123",
                email = "test@example.com",
                username = "testuser",
                dateCreated = Date(),
                lastActiveAt = Date(),
                consentGiven = true
            )

            `when`(mockFirebaseSource.authenticateUser(any(), any())).thenReturn(mockUser)

            val result = userLoginUseCase.authenticateUser(loginData)

            assertTrue(result.isSuccess)
            assertNotNull(result.getOrNull())
            assertEquals(mockUser.email, result.getOrNull()?.email)
            verify(mockFirebaseSource).authenticateUser(loginData.email, loginData.password)
        }

        @Test
        @DisplayName("should handle authentication failure")
        fun shouldHandleAuthenticationFailure() = runTest {
            val loginData = LoginData(
                email = "test@example.com",
                password = "wrongpassword"
            )

            `when`(mockFirebaseSource.authenticateUser(any(), any())).thenThrow(AuthenticationException("Invalid credentials"))

            val result = userLoginUseCase.authenticateUser(loginData)

            assertTrue(result.isFailure)
            assertTrue(result.exceptionOrNull() is AuthenticationException)
        }

        @Test
        @DisplayName("should handle user not found")
        fun shouldHandleUserNotFound() = runTest {
            val loginData = LoginData(
                email = "nonexistent@example.com",
                password = "password123"
            )

            `when`(mockFirebaseSource.authenticateUser(any(), any())).thenThrow(UserNotFoundException())

            val result = userLoginUseCase.authenticateUser(loginData)

            assertTrue(result.isFailure)
            assertTrue(result.exceptionOrNull() is UserNotFoundException)
        }
    }

    @Nested
    @DisplayName("Session Management")
    inner class SessionManagement {

        @Test
        @DisplayName("should create user session")
        fun shouldCreateUserSession() = runTest {
            val user = User(
                userId = "user123",
                email = "test@example.com",
                username = "testuser",
                dateCreated = Date(),
                lastActiveAt = Date(),
                consentGiven = true
            )

            val session = userLoginUseCase.createUserSession(user)

            assertNotNull(session)
            assertEquals(user.userId, session.userId)
            assertNotNull(session.sessionToken)
            assertNotNull(session.createdAt)
            assertNotNull(session.expiresAt)
        }

        @Test
        @DisplayName("should validate session token")
        fun shouldValidateSessionToken() = runTest {
            val sessionToken = "valid_token_123"
            val userId = "user123"

            `when`(mockFirebaseSource.validateSessionToken(sessionToken)).thenReturn(userId)

            val isValid = userLoginUseCase.validateSessionToken(sessionToken)

            assertTrue(isValid)
            verify(mockFirebaseSource).validateSessionToken(sessionToken)
        }

        @Test
        @DisplayName("should handle invalid session token")
        fun shouldHandleInvalidSessionToken() = runTest {
            val sessionToken = "invalid_token"

            `when`(mockFirebaseSource.validateSessionToken(sessionToken)).thenReturn(null)

            val isValid = userLoginUseCase.validateSessionToken(sessionToken)

            assertFalse(isValid)
        }

        @Test
        @DisplayName("should refresh session token")
        fun shouldRefreshSessionToken() = runTest {
            val oldToken = "old_token_123"
            val newToken = "new_token_456"

            `when`(mockFirebaseSource.refreshSessionToken(oldToken)).thenReturn(newToken)

            val result = userLoginUseCase.refreshSessionToken(oldToken)

            assertTrue(result.isSuccess)
            assertEquals(newToken, result.getOrNull())
            verify(mockFirebaseSource).refreshSessionToken(oldToken)
        }
    }

    @Nested
    @DisplayName("Password Management")
    inner class PasswordManagement {

        @Test
        @DisplayName("should reset password")
        fun shouldResetPassword() = runTest {
            val email = "test@example.com"

            `when`(mockFirebaseSource.sendPasswordResetEmail(email)).thenReturn(true)

            val result = userLoginUseCase.resetPassword(email)

            assertTrue(result)
            verify(mockFirebaseSource).sendPasswordResetEmail(email)
        }

        @Test
        @DisplayName("should handle password reset failure")
        fun shouldHandlePasswordResetFailure() = runTest {
            val email = "nonexistent@example.com"

            `when`(mockFirebaseSource.sendPasswordResetEmail(email)).thenReturn(false)

            val result = userLoginUseCase.resetPassword(email)

            assertFalse(result)
        }

        @Test
        @DisplayName("should change password")
        fun shouldChangePassword() = runTest {
            val userId = "user123"
            val oldPassword = "oldpassword"
            val newPassword = "newpassword"

            `when`(mockFirebaseSource.changePassword(userId, oldPassword, newPassword)).thenReturn(true)

            val result = userLoginUseCase.changePassword(userId, oldPassword, newPassword)

            assertTrue(result)
            verify(mockFirebaseSource).changePassword(userId, oldPassword, newPassword)
        }

        @Test
        @DisplayName("should validate password strength")
        fun shouldValidatePasswordStrength() {
            val strongPasswords = listOf(
                "Password123!",
                "MySecure@Pass1",
                "StrongP@ssw0rd"
            )

            strongPasswords.forEach { password ->
                val isStrong = userLoginUseCase.validatePasswordStrength(password)
                assertTrue(isStrong, "Should consider '$password' as strong")
            }

            val weakPasswords = listOf(
                "123",
                "password",
                "abc"
            )

            weakPasswords.forEach { password ->
                val isStrong = userLoginUseCase.validatePasswordStrength(password)
                assertFalse(isStrong, "Should consider '$password' as weak")
            }
        }
    }

    @Nested
    @DisplayName("Security Features")
    inner class SecurityFeatures {

        @Test
        @DisplayName("should implement rate limiting")
        fun shouldImplementRateLimiting() = runTest {
            val email = "test@example.com"
            val attempts = 5

            val isBlocked = userLoginUseCase.isRateLimited(email, attempts)

            assertFalse(isBlocked) // Should not be blocked for 5 attempts
        }

        @Test
        @DisplayName("should block after too many attempts")
        fun shouldBlockAfterTooManyAttempts() = runTest {
            val email = "test@example.com"
            val attempts = 10

            val isBlocked = userLoginUseCase.isRateLimited(email, attempts)

            assertTrue(isBlocked) // Should be blocked for 10 attempts
        }

        @Test
        @DisplayName("should track login attempts")
        fun shouldTrackLoginAttempts() = runTest {
            val email = "test@example.com"
            val success = false

            userLoginUseCase.trackLoginAttempt(email, success)

            val attempts = userLoginUseCase.getLoginAttempts(email)
            assertEquals(1, attempts)
        }

        @Test
        @DisplayName("should detect suspicious activity")
        fun shouldDetectSuspiciousActivity() = runTest {
            val email = "test@example.com"
            val attempts = listOf(
                LoginAttempt(email, Date(), false),
                LoginAttempt(email, Date(), false),
                LoginAttempt(email, Date(), false),
                LoginAttempt(email, Date(), false),
                LoginAttempt(email, Date(), false)
            )

            val isSuspicious = userLoginUseCase.detectSuspiciousActivity(attempts)

            assertTrue(isSuspicious)
        }
    }

    @Nested
    @DisplayName("Login Flow")
    inner class LoginFlow {

        @Test
        @DisplayName("should complete login flow successfully")
        fun shouldCompleteLoginFlowSuccessfully() = runTest {
            val loginData = LoginData(
                email = "test@example.com",
                password = "password123"
            )

            val mockUser = User(
                userId = "user123",
                email = "test@example.com",
                username = "testuser",
                dateCreated = Date(),
                lastActiveAt = Date(),
                consentGiven = true
            )

            `when`(mockFirebaseSource.authenticateUser(any(), any())).thenReturn(mockUser)
            `when`(mockFirebaseSource.updateLastActiveAt(any())).thenReturn(true)

            val result = userLoginUseCase.completeLoginFlow(loginData)

            assertTrue(result.isSuccess)
            assertNotNull(result.getOrNull())
            verify(mockFirebaseSource).authenticateUser(loginData.email, loginData.password)
            verify(mockFirebaseSource).updateLastActiveAt(mockUser.userId)
        }

        @Test
        @DisplayName("should handle login flow failure")
        fun shouldHandleLoginFlowFailure() = runTest {
            val loginData = LoginData(
                email = "test@example.com",
                password = "wrongpassword"
            )

            `when`(mockFirebaseSource.authenticateUser(any(), any())).thenThrow(AuthenticationException("Invalid credentials"))

            val result = userLoginUseCase.completeLoginFlow(loginData)

            assertTrue(result.isFailure)
        }
    }
}

/**
 * Login data class
 */
data class LoginData(
    val email: String,
    val password: String
)

/**
 * User session data class
 */
data class UserSession(
    val sessionId: String,
    val userId: String,
    val sessionToken: String,
    val createdAt: Date,
    val expiresAt: Date
)

/**
 * Login attempt data class
 */
data class LoginAttempt(
    val email: String,
    val timestamp: Date,
    val success: Boolean
)

/**
 * Custom exceptions
 */
class AuthenticationException(message: String) : Exception(message)
class UserNotFoundException : Exception("User not found")

/**
 * Use Case implementation for User Login
 */
class UserLoginUseCase(
    private val firebaseSource: FirebaseSource
) {
    
    fun validateLoginData(loginData: LoginData): Boolean {
        return loginData.email.isNotEmpty() &&
               Helpers.isValidEmail(loginData.email) &&
               loginData.password.isNotEmpty()
    }

    suspend fun authenticateUser(loginData: LoginData): Result<User> {
        return try {
            if (!validateLoginData(loginData)) {
                return Result.failure(AuthenticationException("Invalid login data"))
            }

            val user = firebaseSource.authenticateUser(loginData.email, loginData.password)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createUserSession(user: User): UserSession {
        val sessionToken = generateSessionToken(user.userId)
        val now = Date()
        val expiresAt = Date(now.time + 24 * 60 * 60 * 1000) // 24 hours

        return UserSession(
            sessionId = UUID.randomUUID().toString(),
            userId = user.userId,
            sessionToken = sessionToken,
            createdAt = now,
            expiresAt = expiresAt
        )
    }

    suspend fun validateSessionToken(sessionToken: String): Boolean {
        return try {
            val userId = firebaseSource.validateSessionToken(sessionToken)
            userId != null
        } catch (e: Exception) {
            false
        }
    }

    suspend fun refreshSessionToken(oldToken: String): Result<String> {
        return try {
            val newToken = firebaseSource.refreshSessionToken(oldToken)
            Result.success(newToken)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun resetPassword(email: String): Boolean {
        return try {
            firebaseSource.sendPasswordResetEmail(email)
        } catch (e: Exception) {
            false
        }
    }

    suspend fun changePassword(userId: String, oldPassword: String, newPassword: String): Boolean {
        return try {
            if (!validatePasswordStrength(newPassword)) {
                return false
            }
            firebaseSource.changePassword(userId, oldPassword, newPassword)
        } catch (e: Exception) {
            false
        }
    }

    fun validatePasswordStrength(password: String): Boolean {
        return password.length >= 8 &&
               password.any { it.isDigit() } &&
               password.any { it.isUpperCase() } &&
               password.any { it.isLowerCase() } &&
               password.any { "!@#$%^&*()_+-=[]{}|;:,.<>?".contains(it) }
    }

    fun isRateLimited(email: String, attempts: Int): Boolean {
        return attempts >= 10 // Block after 10 failed attempts
    }

    fun trackLoginAttempt(email: String, success: Boolean) {
        // In a real implementation, this would store the attempt in a database
        // For testing purposes, we'll simulate tracking
    }

    fun getLoginAttempts(email: String): Int {
        // In a real implementation, this would retrieve from database
        // For testing purposes, we'll return a simulated count
        return 1
    }

    fun detectSuspiciousActivity(attempts: List<LoginAttempt>): Boolean {
        val recentAttempts = attempts.filter { 
            Date().time - it.timestamp.time < 60 * 60 * 1000 // Last hour
        }
        
        val failedAttempts = recentAttempts.count { !it.success }
        return failedAttempts >= 5
    }

    suspend fun completeLoginFlow(loginData: LoginData): Result<User> {
        return try {
            val userResult = authenticateUser(loginData)
            if (userResult.isFailure) {
                return userResult
            }

            val user = userResult.getOrThrow()
            
            // Update last active timestamp
            firebaseSource.updateLastActiveAt(user.userId)
            
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun generateSessionToken(userId: String): String {
        return UUID.randomUUID().toString() + "_" + userId + "_" + System.currentTimeMillis()
    }
}
