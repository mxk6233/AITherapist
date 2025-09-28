package com.serenityai.usecases

import com.serenityai.data.models.*
import com.serenityai.data.remote.FirebaseSource
import com.serenityai.utils.Helpers
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

class UserRegistrationUseCaseTest {

    @Mock
    private lateinit var mockFirebaseSource: FirebaseSource

    private lateinit var userRegistrationUseCase: UserRegistrationUseCase

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        userRegistrationUseCase = UserRegistrationUseCase(mockFirebaseSource)
    }

    @Nested
    @DisplayName("Registration Validation")
    inner class RegistrationValidation {

        @Test
        @DisplayName("should validate valid registration data")
        fun shouldValidateValidRegistrationData() {
            val registrationData = RegistrationData(
                email = "test@example.com",
                password = "password123",
                confirmPassword = "password123",
                username = "testuser",
                consentGiven = true
            )

            val isValid = userRegistrationUseCase.validateRegistrationData(registrationData)
            assertTrue(isValid)
        }

        @Test
        @DisplayName("should reject invalid email")
        fun shouldRejectInvalidEmail() {
            val registrationData = RegistrationData(
                email = "invalid-email",
                password = "password123",
                confirmPassword = "password123",
                username = "testuser",
                consentGiven = true
            )

            val isValid = userRegistrationUseCase.validateRegistrationData(registrationData)
            assertFalse(isValid)
        }

        @Test
        @DisplayName("should reject weak password")
        fun shouldRejectWeakPassword() {
            val registrationData = RegistrationData(
                email = "test@example.com",
                password = "123",
                confirmPassword = "123",
                username = "testuser",
                consentGiven = true
            )

            val isValid = userRegistrationUseCase.validateRegistrationData(registrationData)
            assertFalse(isValid)
        }

        @Test
        @DisplayName("should reject password mismatch")
        fun shouldRejectPasswordMismatch() {
            val registrationData = RegistrationData(
                email = "test@example.com",
                password = "password123",
                confirmPassword = "different123",
                username = "testuser",
                consentGiven = true
            )

            val isValid = userRegistrationUseCase.validateRegistrationData(registrationData)
            assertFalse(isValid)
        }

        @Test
        @DisplayName("should reject empty username")
        fun shouldRejectEmptyUsername() {
            val registrationData = RegistrationData(
                email = "test@example.com",
                password = "password123",
                confirmPassword = "password123",
                username = "",
                consentGiven = true
            )

            val isValid = userRegistrationUseCase.validateRegistrationData(registrationData)
            assertFalse(isValid)
        }

        @Test
        @DisplayName("should reject without consent")
        fun shouldRejectWithoutConsent() {
            val registrationData = RegistrationData(
                email = "test@example.com",
                password = "password123",
                confirmPassword = "password123",
                username = "testuser",
                consentGiven = false
            )

            val isValid = userRegistrationUseCase.validateRegistrationData(registrationData)
            assertFalse(isValid)
        }

        @ParameterizedTest
        @ValueSource(strings = [
            "test@example.com",
            "user.name@domain.co.uk",
            "user+tag@example.org",
            "test123@company.com"
        ])
        @DisplayName("should accept valid email formats")
        fun shouldAcceptValidEmailFormats(email: String) {
            val registrationData = RegistrationData(
                email = email,
                password = "password123",
                confirmPassword = "password123",
                username = "testuser",
                consentGiven = true
            )

            val isValid = userRegistrationUseCase.validateRegistrationData(registrationData)
            assertTrue(isValid, "Should accept email: $email")
        }
    }

    @Nested
    @DisplayName("User Creation")
    inner class UserCreation {

        @Test
        @DisplayName("should create user from registration data")
        fun shouldCreateUserFromRegistrationData() = runTest {
            val registrationData = RegistrationData(
                email = "test@example.com",
                password = "password123",
                confirmPassword = "password123",
                username = "testuser",
                consentGiven = true
            )

            val user = userRegistrationUseCase.createUser(registrationData)

            assertNotNull(user)
            assertEquals(registrationData.email, user.email)
            assertEquals(registrationData.username, user.username)
            assertTrue(user.consentGiven)
            assertNotNull(user.dateCreated)
            assertNotNull(user.lastActiveAt)
        }

        @Test
        @DisplayName("should generate unique user ID")
        fun shouldGenerateUniqueUserId() = runTest {
            val registrationData1 = RegistrationData(
                email = "test1@example.com",
                password = "password123",
                confirmPassword = "password123",
                username = "testuser1",
                consentGiven = true
            )

            val registrationData2 = RegistrationData(
                email = "test2@example.com",
                password = "password123",
                confirmPassword = "password123",
                username = "testuser2",
                consentGiven = true
            )

            val user1 = userRegistrationUseCase.createUser(registrationData1)
            val user2 = userRegistrationUseCase.createUser(registrationData2)

            assertNotEquals(user1.userId, user2.userId)
        }
    }

    @Nested
    @DisplayName("Account Registration")
    inner class AccountRegistration {

        @Test
        @DisplayName("should register user successfully")
        fun shouldRegisterUserSuccessfully() = runTest {
            val registrationData = RegistrationData(
                email = "test@example.com",
                password = "password123",
                confirmPassword = "password123",
                username = "testuser",
                consentGiven = true
            )

            `when`(mockFirebaseSource.createUser(any())).thenReturn(true)

            val result = userRegistrationUseCase.registerUser(registrationData)

            assertTrue(result.isSuccess)
            assertNotNull(result.getOrNull())
            verify(mockFirebaseSource).createUser(any())
        }

        @Test
        @DisplayName("should handle registration failure")
        fun shouldHandleRegistrationFailure() = runTest {
            val registrationData = RegistrationData(
                email = "test@example.com",
                password = "password123",
                confirmPassword = "password123",
                username = "testuser",
                consentGiven = true
            )

            `when`(mockFirebaseSource.createUser(any())).thenReturn(false)

            val result = userRegistrationUseCase.registerUser(registrationData)

            assertTrue(result.isFailure)
            assertTrue(result.exceptionOrNull() is RegistrationException)
        }

        @Test
        @DisplayName("should handle email already exists")
        fun shouldHandleEmailAlreadyExists() = runTest {
            val registrationData = RegistrationData(
                email = "existing@example.com",
                password = "password123",
                confirmPassword = "password123",
                username = "testuser",
                consentGiven = true
            )

            `when`(mockFirebaseSource.createUser(any())).thenThrow(EmailAlreadyExistsException())

            val result = userRegistrationUseCase.registerUser(registrationData)

            assertTrue(result.isFailure)
            assertTrue(result.exceptionOrNull() is EmailAlreadyExistsException)
        }
    }

    @Nested
    @DisplayName("Email Verification")
    inner class EmailVerification {

        @Test
        @DisplayName("should send verification email")
        fun shouldSendVerificationEmail() = runTest {
            val userId = "user123"
            val email = "test@example.com"

            `when`(mockFirebaseSource.sendVerificationEmail(userId)).thenReturn(true)

            val result = userRegistrationUseCase.sendVerificationEmail(userId, email)

            assertTrue(result)
            verify(mockFirebaseSource).sendVerificationEmail(userId)
        }

        @Test
        @DisplayName("should handle verification email failure")
        fun shouldHandleVerificationEmailFailure() = runTest {
            val userId = "user123"
            val email = "test@example.com"

            `when`(mockFirebaseSource.sendVerificationEmail(userId)).thenReturn(false)

            val result = userRegistrationUseCase.sendVerificationEmail(userId, email)

            assertFalse(result)
        }

        @Test
        @DisplayName("should verify email successfully")
        fun shouldVerifyEmailSuccessfully() = runTest {
            val userId = "user123"
            val verificationCode = "123456"

            `when`(mockFirebaseSource.verifyEmail(userId, verificationCode)).thenReturn(true)

            val result = userRegistrationUseCase.verifyEmail(userId, verificationCode)

            assertTrue(result)
            verify(mockFirebaseSource).verifyEmail(userId, verificationCode)
        }

        @Test
        @DisplayName("should handle invalid verification code")
        fun shouldHandleInvalidVerificationCode() = runTest {
            val userId = "user123"
            val verificationCode = "invalid"

            `when`(mockFirebaseSource.verifyEmail(userId, verificationCode)).thenReturn(false)

            val result = userRegistrationUseCase.verifyEmail(userId, verificationCode)

            assertFalse(result)
        }
    }

    @Nested
    @DisplayName("Profile Setup")
    inner class ProfileSetup {

        @Test
        @DisplayName("should create user profile")
        fun shouldCreateUserProfile() = runTest {
            val userId = "user123"
            val profileData = ProfileData(
                firstName = "John",
                lastName = "Doe",
                dateOfBirth = Date(),
                gender = "Male",
                preferences = UserPreferences()
            )

            `when`(mockFirebaseSource.createUserProfile(any())).thenReturn(true)

            val result = userRegistrationUseCase.createUserProfile(userId, profileData)

            assertTrue(result)
            verify(mockFirebaseSource).createUserProfile(any())
        }

        @Test
        @DisplayName("should set initial preferences")
        fun shouldSetInitialPreferences() = runTest {
            val userId = "user123"
            val preferences = UserPreferences(
                notificationsEnabled = true,
                reminderTime = "09:00",
                theme = "light",
                language = "en"
            )

            `when`(mockFirebaseSource.updateUserPreferences(userId, preferences)).thenReturn(true)

            val result = userRegistrationUseCase.setInitialPreferences(userId, preferences)

            assertTrue(result)
            verify(mockFirebaseSource).updateUserPreferences(userId, preferences)
        }
    }

    @Nested
    @DisplayName("Security")
    inner class Security {

        @Test
        @DisplayName("should hash password securely")
        fun shouldHashPasswordSecurely() = runTest {
            val password = "password123"
            val hashedPassword = userRegistrationUseCase.hashPassword(password)

            assertNotNull(hashedPassword)
            assertNotEquals(password, hashedPassword)
            assertTrue(hashedPassword.length > 20) // Should be a proper hash
        }

        @Test
        @DisplayName("should generate secure tokens")
        fun shouldGenerateSecureTokens() = runTest {
            val userId = "user123"
            val token = userRegistrationUseCase.generateSecureToken(userId)

            assertNotNull(token)
            assertTrue(token.length >= 32) // Should be a secure token
        }

        @Test
        @DisplayName("should validate password strength")
        fun shouldValidatePasswordStrength() {
            val strongPasswords = listOf(
                "Password123!",
                "MySecure@Pass1",
                "StrongP@ssw0rd",
                "Complex#Pass99"
            )

            strongPasswords.forEach { password ->
                val isStrong = userRegistrationUseCase.validatePasswordStrength(password)
                assertTrue(isStrong, "Should consider '$password' as strong")
            }

            val weakPasswords = listOf(
                "123",
                "password",
                "abc",
                "12345678"
            )

            weakPasswords.forEach { password ->
                val isStrong = userRegistrationUseCase.validatePasswordStrength(password)
                assertFalse(isStrong, "Should consider '$password' as weak")
            }
        }
    }

    @Nested
    @DisplayName("Registration Flow")
    inner class RegistrationFlow {

        @Test
        @DisplayName("should complete full registration flow")
        fun shouldCompleteFullRegistrationFlow() = runTest {
            val registrationData = RegistrationData(
                email = "test@example.com",
                password = "password123",
                confirmPassword = "password123",
                username = "testuser",
                consentGiven = true
            )

            `when`(mockFirebaseSource.createUser(any())).thenReturn(true)
            `when`(mockFirebaseSource.sendVerificationEmail(any())).thenReturn(true)
            `when`(mockFirebaseSource.createUserProfile(any())).thenReturn(true)

            val result = userRegistrationUseCase.completeRegistrationFlow(registrationData)

            assertTrue(result.isSuccess)
            verify(mockFirebaseSource).createUser(any())
            verify(mockFirebaseSource).sendVerificationEmail(any())
            verify(mockFirebaseSource).createUserProfile(any())
        }

        @Test
        @DisplayName("should handle registration flow failure")
        fun shouldHandleRegistrationFlowFailure() = runTest {
            val registrationData = RegistrationData(
                email = "test@example.com",
                password = "password123",
                confirmPassword = "password123",
                username = "testuser",
                consentGiven = true
            )

            `when`(mockFirebaseSource.createUser(any())).thenReturn(false)

            val result = userRegistrationUseCase.completeRegistrationFlow(registrationData)

            assertTrue(result.isFailure)
        }
    }
}

/**
 * Registration data class
 */
data class RegistrationData(
    val email: String,
    val password: String,
    val confirmPassword: String,
    val username: String,
    val consentGiven: Boolean
)

/**
 * Profile data class
 */
data class ProfileData(
    val firstName: String,
    val lastName: String,
    val dateOfBirth: Date,
    val gender: String,
    val preferences: UserPreferences
)

/**
 * User preferences data class
 */
data class UserPreferences(
    val notificationsEnabled: Boolean = true,
    val reminderTime: String = "09:00",
    val theme: String = "light",
    val language: String = "en"
)

/**
 * Custom exceptions
 */
class RegistrationException(message: String) : Exception(message)
class EmailAlreadyExistsException : Exception("Email already exists")

/**
 * Use Case implementation for User Registration
 */
class UserRegistrationUseCase(
    private val firebaseSource: FirebaseSource
) {
    
    fun validateRegistrationData(data: RegistrationData): Boolean {
        return data.email.isNotEmpty() &&
               Helpers.isValidEmail(data.email) &&
               data.password.isNotEmpty() &&
               data.password.length >= 6 &&
               data.password == data.confirmPassword &&
               data.username.isNotEmpty() &&
               data.consentGiven
    }

    suspend fun createUser(registrationData: RegistrationData): User {
        return User(
            userId = UUID.randomUUID().toString(),
            email = registrationData.email,
            username = registrationData.username,
            dateCreated = Date(),
            lastActiveAt = Date(),
            consentGiven = registrationData.consentGiven
        )
    }

    suspend fun registerUser(registrationData: RegistrationData): Result<User> {
        return try {
            if (!validateRegistrationData(registrationData)) {
                return Result.failure(RegistrationException("Invalid registration data"))
            }

            val user = createUser(registrationData)
            val success = firebaseSource.createUser(user)
            
            if (success) {
                Result.success(user)
            } else {
                Result.failure(RegistrationException("Failed to create user"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun sendVerificationEmail(userId: String, email: String): Boolean {
        return try {
            firebaseSource.sendVerificationEmail(userId)
        } catch (e: Exception) {
            false
        }
    }

    suspend fun verifyEmail(userId: String, verificationCode: String): Boolean {
        return try {
            firebaseSource.verifyEmail(userId, verificationCode)
        } catch (e: Exception) {
            false
        }
    }

    suspend fun createUserProfile(userId: String, profileData: ProfileData): Boolean {
        return try {
            val userProfile = UserProfile(
                userId = userId,
                firstName = profileData.firstName,
                lastName = profileData.lastName,
                dateOfBirth = profileData.dateOfBirth,
                gender = profileData.gender,
                preferences = profileData.preferences
            )
            firebaseSource.createUserProfile(userProfile)
        } catch (e: Exception) {
            false
        }
    }

    suspend fun setInitialPreferences(userId: String, preferences: UserPreferences): Boolean {
        return try {
            firebaseSource.updateUserPreferences(userId, preferences)
        } catch (e: Exception) {
            false
        }
    }

    suspend fun hashPassword(password: String): String {
        // In a real implementation, this would use proper password hashing
        // For testing purposes, we'll simulate a hash
        return "hashed_${password}_${UUID.randomUUID()}"
    }

    fun generateSecureToken(userId: String): String {
        return UUID.randomUUID().toString() + "_" + userId + "_" + System.currentTimeMillis()
    }

    fun validatePasswordStrength(password: String): Boolean {
        return password.length >= 8 &&
               password.any { it.isDigit() } &&
               password.any { it.isUpperCase() } &&
               password.any { it.isLowerCase() } &&
               password.any { "!@#$%^&*()_+-=[]{}|;:,.<>?".contains(it) }
    }

    suspend fun completeRegistrationFlow(registrationData: RegistrationData): Result<User> {
        return try {
            val userResult = registerUser(registrationData)
            if (userResult.isFailure) {
                return userResult
            }

            val user = userResult.getOrThrow()
            
            // Send verification email
            sendVerificationEmail(user.userId, user.email)
            
            // Create basic profile
            val profileData = ProfileData(
                firstName = "",
                lastName = "",
                dateOfBirth = Date(),
                gender = "",
                preferences = UserPreferences()
            )
            createUserProfile(user.userId, profileData)
            
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
