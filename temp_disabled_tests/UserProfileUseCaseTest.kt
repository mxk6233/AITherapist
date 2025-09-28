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

class UserProfileUseCaseTest {

    @Mock
    private lateinit var mockFirebaseSource: FirebaseSource

    private lateinit var userProfileUseCase: UserProfileUseCase

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        userProfileUseCase = UserProfileUseCase(mockFirebaseSource)
    }

    @Nested
    @DisplayName("Profile Creation")
    inner class ProfileCreation {

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

            val result = userProfileUseCase.createUserProfile(userId, profileData)

            assertTrue(result)
            verify(mockFirebaseSource).createUserProfile(any())
        }

        @Test
        @DisplayName("should handle profile creation failure")
        fun shouldHandleProfileCreationFailure() = runTest {
            val userId = "user123"
            val profileData = ProfileData(
                firstName = "John",
                lastName = "Doe",
                dateOfBirth = Date(),
                gender = "Male",
                preferences = UserPreferences()
            )

            `when`(mockFirebaseSource.createUserProfile(any())).thenReturn(false)

            val result = userProfileUseCase.createUserProfile(userId, profileData)

            assertFalse(result)
        }

        @Test
        @DisplayName("should validate profile data")
        fun shouldValidateProfileData() = runTest {
            val profileData = ProfileData(
                firstName = "John",
                lastName = "Doe",
                dateOfBirth = Date(),
                gender = "Male",
                preferences = UserPreferences()
            )

            val isValid = userProfileUseCase.validateProfileData(profileData)

            assertTrue(isValid)
        }

        @Test
        @DisplayName("should reject invalid profile data")
        fun shouldRejectInvalidProfileData() = runTest {
            val profileData = ProfileData(
                firstName = "",
                lastName = "Doe",
                dateOfBirth = Date(),
                gender = "Male",
                preferences = UserPreferences()
            )

            val isValid = userProfileUseCase.validateProfileData(profileData)

            assertFalse(isValid)
        }
    }

    @Nested
    @DisplayName("Profile Retrieval")
    inner class ProfileRetrieval {

        @Test
        @DisplayName("should retrieve user profile")
        fun shouldRetrieveUserProfile() = runTest {
            val userId = "user123"
            val mockProfile = UserProfile(
                userId = userId,
                firstName = "John",
                lastName = "Doe",
                dateOfBirth = Date(),
                gender = "Male",
                preferences = UserPreferences()
            )

            `when`(mockFirebaseSource.getUserProfile(userId)).thenReturn(mockProfile)

            val profile = userProfileUseCase.getUserProfile(userId)

            assertNotNull(profile)
            assertEquals(userId, profile?.userId)
            assertEquals("John", profile?.firstName)
            verify(mockFirebaseSource).getUserProfile(userId)
        }

        @Test
        @DisplayName("should handle profile not found")
        fun shouldHandleProfileNotFound() = runTest {
            val userId = "nonexistent"

            `when`(mockFirebaseSource.getUserProfile(userId)).thenReturn(null)

            val profile = userProfileUseCase.getUserProfile(userId)

            assertNull(profile)
        }
    }

    @Nested
    @DisplayName("Profile Updates")
    inner class ProfileUpdates {

        @Test
        @DisplayName("should update profile information")
        fun shouldUpdateProfileInformation() = runTest {
            val userId = "user123"
            val updatedData = ProfileData(
                firstName = "Jane",
                lastName = "Smith",
                dateOfBirth = Date(),
                gender = "Female",
                preferences = UserPreferences()
            )

            `when`(mockFirebaseSource.updateUserProfile(userId, any())).thenReturn(true)

            val result = userProfileUseCase.updateProfileInformation(userId, updatedData)

            assertTrue(result)
            verify(mockFirebaseSource).updateUserProfile(userId, any())
        }

        @Test
        @DisplayName("should update user preferences")
        fun shouldUpdateUserPreferences() = runTest {
            val userId = "user123"
            val preferences = UserPreferences(
                notificationsEnabled = true,
                reminderTime = "09:00",
                theme = "dark",
                language = "en"
            )

            `when`(mockFirebaseSource.updateUserPreferences(userId, preferences)).thenReturn(true)

            val result = userProfileUseCase.updateUserPreferences(userId, preferences)

            assertTrue(result)
            verify(mockFirebaseSource).updateUserPreferences(userId, preferences)
        }

        @Test
        @DisplayName("should update profile picture")
        fun shouldUpdateProfilePicture() = runTest {
            val userId = "user123"
            val imageUrl = "https://example.com/profile.jpg"

            `when`(mockFirebaseSource.updateProfilePicture(userId, imageUrl)).thenReturn(true)

            val result = userProfileUseCase.updateProfilePicture(userId, imageUrl)

            assertTrue(result)
            verify(mockFirebaseSource).updateProfilePicture(userId, imageUrl)
        }

        @Test
        @DisplayName("should handle update failure")
        fun shouldHandleUpdateFailure() = runTest {
            val userId = "user123"
            val updatedData = ProfileData(
                firstName = "Jane",
                lastName = "Smith",
                dateOfBirth = Date(),
                gender = "Female",
                preferences = UserPreferences()
            )

            `when`(mockFirebaseSource.updateUserProfile(userId, any())).thenReturn(false)

            val result = userProfileUseCase.updateProfileInformation(userId, updatedData)

            assertFalse(result)
        }
    }

    @Nested
    @DisplayName("Profile Validation")
    inner class ProfileValidation {

        @Test
        @DisplayName("should validate first name")
        fun shouldValidateFirstName() = runTest {
            val validNames = listOf("John", "Jane", "Mary", "Robert")
            val invalidNames = listOf("", "A", "123", "John@")

            validNames.forEach { name ->
                val isValid = userProfileUseCase.validateFirstName(name)
                assertTrue(isValid, "Should accept name: $name")
            }

            invalidNames.forEach { name ->
                val isValid = userProfileUseCase.validateFirstName(name)
                assertFalse(isValid, "Should reject name: $name")
            }
        }

        @Test
        @DisplayName("should validate last name")
        fun shouldValidateLastName() = runTest {
            val validNames = listOf("Smith", "Johnson", "Williams", "Brown")
            val invalidNames = listOf("", "A", "123", "Smith@")

            validNames.forEach { name ->
                val isValid = userProfileUseCase.validateLastName(name)
                assertTrue(isValid, "Should accept name: $name")
            }

            invalidNames.forEach { name ->
                val isValid = userProfileUseCase.validateLastName(name)
                assertFalse(isValid, "Should reject name: $name")
            }
        }

        @Test
        @DisplayName("should validate date of birth")
        fun shouldValidateDateOfBirth() = runTest {
            val validDate = Date(System.currentTimeMillis() - 25 * 365 * 24 * 60 * 60 * 1000L) // 25 years ago
            val invalidDate = Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000L) // Tomorrow

            val isValidValid = userProfileUseCase.validateDateOfBirth(validDate)
            val isValidInvalid = userProfileUseCase.validateDateOfBirth(invalidDate)

            assertTrue(isValidValid)
            assertFalse(isValidInvalid)
        }

        @Test
        @DisplayName("should validate gender")
        fun shouldValidateGender() = runTest {
            val validGenders = listOf("Male", "Female", "Non-binary", "Other")
            val invalidGenders = listOf("", "Invalid", "123")

            validGenders.forEach { gender ->
                val isValid = userProfileUseCase.validateGender(gender)
                assertTrue(isValid, "Should accept gender: $gender")
            }

            invalidGenders.forEach { gender ->
                val isValid = userProfileUseCase.validateGender(gender)
                assertFalse(isValid, "Should reject gender: $gender")
            }
        }
    }

    @Nested
    @DisplayName("Profile Preferences")
    inner class ProfilePreferences {

        @Test
        @DisplayName("should get default preferences")
        fun shouldGetDefaultPreferences() = runTest {
            val preferences = userProfileUseCase.getDefaultPreferences()

            assertNotNull(preferences)
            assertTrue(preferences.notificationsEnabled)
            assertEquals("09:00", preferences.reminderTime)
            assertEquals("light", preferences.theme)
            assertEquals("en", preferences.language)
        }

        @Test
        @DisplayName("should validate notification preferences")
        fun shouldValidateNotificationPreferences() = runTest {
            val validPreferences = UserPreferences(
                notificationsEnabled = true,
                reminderTime = "09:00",
                theme = "light",
                language = "en"
            )

            val isValid = userProfileUseCase.validateNotificationPreferences(validPreferences)

            assertTrue(isValid)
        }

        @Test
        @DisplayName("should validate theme preferences")
        fun shouldValidateThemePreferences() = runTest {
            val validThemes = listOf("light", "dark", "auto")
            val invalidThemes = listOf("", "invalid", "123")

            validThemes.forEach { theme ->
                val isValid = userProfileUseCase.validateThemePreference(theme)
                assertTrue(isValid, "Should accept theme: $theme")
            }

            invalidThemes.forEach { theme ->
                val isValid = userProfileUseCase.validateThemePreference(theme)
                assertFalse(isValid, "Should reject theme: $theme")
            }
        }

        @Test
        @DisplayName("should validate language preferences")
        fun shouldValidateLanguagePreferences() = runTest {
            val validLanguages = listOf("en", "es", "fr", "de")
            val invalidLanguages = listOf("", "invalid", "123")

            validLanguages.forEach { language ->
                val isValid = userProfileUseCase.validateLanguagePreference(language)
                assertTrue(isValid, "Should accept language: $language")
            }

            invalidLanguages.forEach { language ->
                val isValid = userProfileUseCase.validateLanguagePreference(language)
                assertFalse(isValid, "Should reject language: $language")
            }
        }
    }

    @Nested
    @DisplayName("Profile Security")
    inner class ProfileSecurity {

        @Test
        @DisplayName("should validate profile access")
        fun shouldValidateProfileAccess() = runTest {
            val userId = "user123"
            val requestingUserId = "user123"

            val hasAccess = userProfileUseCase.validateProfileAccess(userId, requestingUserId)

            assertTrue(hasAccess)
        }

        @Test
        @DisplayName("should deny access to other users")
        fun shouldDenyAccessToOtherUsers() = runTest {
            val userId = "user123"
            val requestingUserId = "user456"

            val hasAccess = userProfileUseCase.validateProfileAccess(userId, requestingUserId)

            assertFalse(hasAccess)
        }

        @Test
        @DisplayName("should log profile access")
        fun shouldLogProfileAccess() = runTest {
            val userId = "user123"
            val action = "profile_view"

            userProfileUseCase.logProfileAccess(userId, action)

            // In a real implementation, this would log to a database
            // For testing purposes, we'll just verify the method doesn't throw
            assertTrue(true)
        }
    }

    @Nested
    @DisplayName("Profile Analytics")
    inner class ProfileAnalytics {

        @Test
        @DisplayName("should calculate profile completeness")
        fun shouldCalculateProfileCompleteness() = runTest {
            val profile = UserProfile(
                userId = "user123",
                firstName = "John",
                lastName = "Doe",
                dateOfBirth = Date(),
                gender = "Male",
                preferences = UserPreferences()
            )

            val completeness = userProfileUseCase.calculateProfileCompleteness(profile)

            assertTrue(completeness >= 0.0 && completeness <= 1.0)
            assertTrue(completeness > 0.5) // Should be mostly complete
        }

        @Test
        @DisplayName("should generate profile insights")
        fun shouldGenerateProfileInsights() = runTest {
            val profile = UserProfile(
                userId = "user123",
                firstName = "John",
                lastName = "Doe",
                dateOfBirth = Date(),
                gender = "Male",
                preferences = UserPreferences()
            )

            val insights = userProfileUseCase.generateProfileInsights(profile)

            assertNotNull(insights)
            assertTrue(insights.isNotEmpty())
        }

        @Test
        @DisplayName("should track profile changes")
        fun shouldTrackProfileChanges() = runTest {
            val userId = "user123"
            val oldProfile = UserProfile(
                userId = userId,
                firstName = "John",
                lastName = "Doe",
                dateOfBirth = Date(),
                gender = "Male",
                preferences = UserPreferences()
            )

            val newProfile = oldProfile.copy(firstName = "Jane")

            val changes = userProfileUseCase.trackProfileChanges(oldProfile, newProfile)

            assertNotNull(changes)
            assertTrue(changes.isNotEmpty())
            assertTrue(changes.containsKey("firstName"))
        }
    }

    @Nested
    @DisplayName("Profile Export")
    inner class ProfileExport {

        @Test
        @DisplayName("should export profile data")
        fun shouldExportProfileData() = runTest {
            val profile = UserProfile(
                userId = "user123",
                firstName = "John",
                lastName = "Doe",
                dateOfBirth = Date(),
                gender = "Male",
                preferences = UserPreferences()
            )

            val exportedData = userProfileUseCase.exportProfileData(profile)

            assertNotNull(exportedData)
            assertTrue(exportedData.containsKey("userId"))
            assertTrue(exportedData.containsKey("firstName"))
            assertTrue(exportedData.containsKey("lastName"))
        }

        @Test
        @DisplayName("should generate profile summary")
        fun shouldGenerateProfileSummary() = runTest {
            val profile = UserProfile(
                userId = "user123",
                firstName = "John",
                lastName = "Doe",
                dateOfBirth = Date(),
                gender = "Male",
                preferences = UserPreferences()
            )

            val summary = userProfileUseCase.generateProfileSummary(profile)

            assertNotNull(summary)
            assertTrue(summary.contains("John"))
            assertTrue(summary.contains("Doe"))
        }
    }
}

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
 * User profile data class
 */
data class UserProfile(
    val userId: String,
    val firstName: String,
    val lastName: String,
    val dateOfBirth: Date,
    val gender: String,
    val preferences: UserPreferences,
    val profilePictureUrl: String? = null,
    val bio: String? = null,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)

/**
 * Use Case implementation for User Profile Management
 */
class UserProfileUseCase(
    private val firebaseSource: FirebaseSource
) {
    
    suspend fun createUserProfile(userId: String, profileData: ProfileData): Boolean {
        return try {
            if (!validateProfileData(profileData)) {
                return false
            }

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

    suspend fun getUserProfile(userId: String): UserProfile? {
        return try {
            firebaseSource.getUserProfile(userId)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun updateProfileInformation(userId: String, updatedData: ProfileData): Boolean {
        return try {
            if (!validateProfileData(updatedData)) {
                return false
            }

            val updatedProfile = UserProfile(
                userId = userId,
                firstName = updatedData.firstName,
                lastName = updatedData.lastName,
                dateOfBirth = updatedData.dateOfBirth,
                gender = updatedData.gender,
                preferences = updatedData.preferences,
                updatedAt = Date()
            )

            firebaseSource.updateUserProfile(userId, updatedProfile)
        } catch (e: Exception) {
            false
        }
    }

    suspend fun updateUserPreferences(userId: String, preferences: UserPreferences): Boolean {
        return try {
            if (!validateNotificationPreferences(preferences)) {
                return false
            }

            firebaseSource.updateUserPreferences(userId, preferences)
        } catch (e: Exception) {
            false
        }
    }

    suspend fun updateProfilePicture(userId: String, imageUrl: String): Boolean {
        return try {
            firebaseSource.updateProfilePicture(userId, imageUrl)
        } catch (e: Exception) {
            false
        }
    }

    fun validateProfileData(profileData: ProfileData): Boolean {
        return validateFirstName(profileData.firstName) &&
               validateLastName(profileData.lastName) &&
               validateDateOfBirth(profileData.dateOfBirth) &&
               validateGender(profileData.gender) &&
               validateNotificationPreferences(profileData.preferences)
    }

    fun validateFirstName(firstName: String): Boolean {
        return firstName.isNotEmpty() &&
               firstName.length >= 2 &&
               firstName.matches(Regex("[a-zA-Z\\s]+"))
    }

    fun validateLastName(lastName: String): Boolean {
        return lastName.isNotEmpty() &&
               lastName.length >= 2 &&
               lastName.matches(Regex("[a-zA-Z\\s]+"))
    }

    fun validateDateOfBirth(dateOfBirth: Date): Boolean {
        val now = Date()
        val age = (now.time - dateOfBirth.time) / (365.25 * 24 * 60 * 60 * 1000)
        return age >= 13 && age <= 120
    }

    fun validateGender(gender: String): Boolean {
        val validGenders = listOf("Male", "Female", "Non-binary", "Other", "Prefer not to say")
        return validGenders.contains(gender)
    }

    fun validateNotificationPreferences(preferences: UserPreferences): Boolean {
        return validateThemePreference(preferences.theme) &&
               validateLanguagePreference(preferences.language) &&
               validateReminderTime(preferences.reminderTime)
    }

    fun validateThemePreference(theme: String): Boolean {
        val validThemes = listOf("light", "dark", "auto")
        return validThemes.contains(theme)
    }

    fun validateLanguagePreference(language: String): Boolean {
        val validLanguages = listOf("en", "es", "fr", "de", "it", "pt", "ru", "zh", "ja", "ko")
        return validLanguages.contains(language)
    }

    fun validateReminderTime(reminderTime: String): Boolean {
        return reminderTime.matches(Regex("^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$"))
    }

    fun getDefaultPreferences(): UserPreferences {
        return UserPreferences(
            notificationsEnabled = true,
            reminderTime = "09:00",
            theme = "light",
            language = "en"
        )
    }

    fun validateProfileAccess(userId: String, requestingUserId: String): Boolean {
        return userId == requestingUserId
    }

    fun logProfileAccess(userId: String, action: String) {
        // In a real implementation, this would log to a database
        // For testing purposes, we'll simulate logging
    }

    fun calculateProfileCompleteness(profile: UserProfile): Double {
        var completeness = 0.0
        var totalFields = 0.0

        // Check required fields
        totalFields += 1
        if (profile.firstName.isNotEmpty()) completeness += 1

        totalFields += 1
        if (profile.lastName.isNotEmpty()) completeness += 1

        totalFields += 1
        if (profile.gender.isNotEmpty()) completeness += 1

        totalFields += 1
        if (profile.dateOfBirth != null) completeness += 1

        // Check optional fields
        totalFields += 1
        if (profile.bio != null && profile.bio.isNotEmpty()) completeness += 1

        totalFields += 1
        if (profile.profilePictureUrl != null) completeness += 1

        return if (totalFields > 0) completeness / totalFields else 0.0
    }

    fun generateProfileInsights(profile: UserProfile): List<String> {
        val insights = mutableListOf<String>()

        val completeness = calculateProfileCompleteness(profile)
        when {
            completeness >= 0.9 -> insights.add("Your profile is complete!")
            completeness >= 0.7 -> insights.add("Your profile is mostly complete")
            completeness >= 0.5 -> insights.add("Consider adding more information to your profile")
            else -> insights.add("Your profile needs more information")
        }

        if (profile.bio == null || profile.bio.isEmpty()) {
            insights.add("Add a bio to tell others about yourself")
        }

        if (profile.profilePictureUrl == null) {
            insights.add("Add a profile picture to personalize your account")
        }

        return insights
    }

    fun trackProfileChanges(oldProfile: UserProfile, newProfile: UserProfile): Map<String, Any> {
        val changes = mutableMapOf<String, Any>()

        if (oldProfile.firstName != newProfile.firstName) {
            changes["firstName"] = mapOf("old" to oldProfile.firstName, "new" to newProfile.firstName)
        }

        if (oldProfile.lastName != newProfile.lastName) {
            changes["lastName"] = mapOf("old" to oldProfile.lastName, "new" to newProfile.lastName)
        }

        if (oldProfile.gender != newProfile.gender) {
            changes["gender"] = mapOf("old" to oldProfile.gender, "new" to newProfile.gender)
        }

        if (oldProfile.preferences != newProfile.preferences) {
            changes["preferences"] = mapOf("old" to oldProfile.preferences, "new" to newProfile.preferences)
        }

        return changes
    }

    suspend fun exportProfileData(profile: UserProfile): Map<String, Any> {
        return mapOf(
            "userId" to profile.userId,
            "firstName" to profile.firstName,
            "lastName" to profile.lastName,
            "dateOfBirth" to profile.dateOfBirth,
            "gender" to profile.gender,
            "preferences" to profile.preferences,
            "profilePictureUrl" to profile.profilePictureUrl,
            "bio" to profile.bio,
            "createdAt" to profile.createdAt,
            "updatedAt" to profile.updatedAt
        )
    }

    suspend fun generateProfileSummary(profile: UserProfile): String {
        return """
            Profile Summary for ${profile.firstName} ${profile.lastName}
            ================================================
            User ID: ${profile.userId}
            Gender: ${profile.gender}
            Date of Birth: ${profile.dateOfBirth}
            Profile Completeness: ${String.format("%.1f", calculateProfileCompleteness(profile) * 100)}%
            
            Preferences:
            - Notifications: ${if (profile.preferences.notificationsEnabled) "Enabled" else "Disabled"}
            - Reminder Time: ${profile.preferences.reminderTime}
            - Theme: ${profile.preferences.theme}
            - Language: ${profile.preferences.language}
            
            ${if (profile.bio != null) "Bio: ${profile.bio}" else "No bio provided"}
        """.trimIndent()
    }
}
