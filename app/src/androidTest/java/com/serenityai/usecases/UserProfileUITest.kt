package com.serenityai.usecases

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.serenityai.InteractiveMainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserProfileUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testUserProfileUI() {
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Test user profile interface
        composeTestRule.onNodeWithText("Profile").assertIsDisplayed()
        composeTestRule.onNodeWithText("Edit Profile").assertIsDisplayed()
        composeTestRule.onNodeWithText("Settings").assertIsDisplayed()
        composeTestRule.onNodeWithText("Logout").assertIsDisplayed()
    }

    @Test
    fun testProfileInformation() {
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Test profile information
        composeTestRule.onNodeWithText("First Name").assertIsDisplayed()
        composeTestRule.onNodeWithText("Last Name").assertIsDisplayed()
        composeTestRule.onNodeWithText("Email").assertIsDisplayed()
        composeTestRule.onNodeWithText("Date of Birth").assertIsDisplayed()
        composeTestRule.onNodeWithText("Gender").assertIsDisplayed()
    }

    @Test
    fun testEditProfile() {
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Test edit profile
        composeTestRule.onNodeWithText("Edit Profile").performClick()
        composeTestRule.onNodeWithText("First Name").performTextInput("John")
        composeTestRule.onNodeWithText("Last Name").performTextInput("Doe")
        composeTestRule.onNodeWithText("Save Changes").performClick()
        composeTestRule.onNodeWithText("Profile updated successfully").assertIsDisplayed()
    }

    @Test
    fun testProfilePicture() {
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Test profile picture
        composeTestRule.onNodeWithText("Profile Picture").performClick()
        composeTestRule.onNodeWithText("Take Photo").assertIsDisplayed()
        composeTestRule.onNodeWithText("Choose from Gallery").assertIsDisplayed()
        composeTestRule.onNodeWithText("Remove Photo").assertIsDisplayed()
    }

    @Test
    fun testProfileSettings() {
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Test profile settings
        composeTestRule.onNodeWithText("Settings").performClick()
        composeTestRule.onNodeWithText("Notifications").assertIsDisplayed()
        composeTestRule.onNodeWithText("Privacy").assertIsDisplayed()
        composeTestRule.onNodeWithText("Security").assertIsDisplayed()
        composeTestRule.onNodeWithText("Preferences").assertIsDisplayed()
    }

    @Test
    fun testNotificationSettings() {
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Test notification settings
        composeTestRule.onNodeWithText("Notifications").performClick()
        composeTestRule.onNodeWithText("Push Notifications").assertIsDisplayed()
        composeTestRule.onNodeWithText("Email Notifications").assertIsDisplayed()
        composeTestRule.onNodeWithText("Reminder Time").assertIsDisplayed()
        composeTestRule.onNodeWithText("Frequency").assertIsDisplayed()
    }

    @Test
    fun testPrivacySettings() {
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Test privacy settings
        composeTestRule.onNodeWithText("Privacy").performClick()
        composeTestRule.onNodeWithText("Data Sharing").assertIsDisplayed()
        composeTestRule.onNodeWithText("Analytics").assertIsDisplayed()
        composeTestRule.onNodeWithText("Profile Visibility").assertIsDisplayed()
    }

    @Test
    fun testSecuritySettings() {
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Test security settings
        composeTestRule.onNodeWithText("Security").performClick()
        composeTestRule.onNodeWithText("Change Password").assertIsDisplayed()
        composeTestRule.onNodeWithText("Two-Factor Authentication").assertIsDisplayed()
        composeTestRule.onNodeWithText("Biometric Login").assertIsDisplayed()
    }

    @Test
    fun testPreferencesSettings() {
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Test preferences settings
        composeTestRule.onNodeWithText("Preferences").performClick()
        composeTestRule.onNodeWithText("Theme").assertIsDisplayed()
        composeTestRule.onNodeWithText("Language").assertIsDisplayed()
        composeTestRule.onNodeWithText("Time Zone").assertIsDisplayed()
        composeTestRule.onNodeWithText("Units").assertIsDisplayed()
    }

    @Test
    fun testProfileExport() {
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Test profile export
        composeTestRule.onNodeWithText("Export Data").performClick()
        composeTestRule.onNodeWithText("Export Profile").assertIsDisplayed()
        composeTestRule.onNodeWithText("Export All Data").assertIsDisplayed()
        composeTestRule.onNodeWithText("Export Mood Data").assertIsDisplayed()
    }

    @Test
    fun testProfileDeletion() {
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Test profile deletion
        composeTestRule.onNodeWithText("Delete Account").performClick()
        composeTestRule.onNodeWithText("Are you sure?").assertIsDisplayed()
        composeTestRule.onNodeWithText("This action cannot be undone").assertIsDisplayed()
        composeTestRule.onNodeWithText("Confirm Delete").assertIsDisplayed()
        composeTestRule.onNodeWithText("Cancel").assertIsDisplayed()
    }
}
