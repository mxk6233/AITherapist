package com.serenityai.usecases

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.serenityai.InteractiveMainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CrisisInterventionUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testCrisisDetectionUI() {
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Test crisis detection elements
        composeTestRule.onNodeWithText("Crisis Support").assertIsDisplayed()
        composeTestRule.onNodeWithText("Emergency Contacts").assertIsDisplayed()
        composeTestRule.onNodeWithText("Crisis Hotline").assertIsDisplayed()
    }

    @Test
    fun testCrisisResponseFlow() {
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Test crisis response flow
        composeTestRule.onNodeWithText("I'm in crisis").performClick()
        composeTestRule.onNodeWithText("Emergency Response").assertIsDisplayed()
        composeTestRule.onNodeWithText("Call 911").assertIsDisplayed()
        composeTestRule.onNodeWithText("Call Crisis Hotline").assertIsDisplayed()
    }

    @Test
    fun testEmergencyContacts() {
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Test emergency contacts
        composeTestRule.onNodeWithText("Emergency Contacts").performClick()
        composeTestRule.onNodeWithText("911").assertIsDisplayed()
        composeTestRule.onNodeWithText("988").assertIsDisplayed()
        composeTestRule.onNodeWithText("Crisis Text Line").assertIsDisplayed()
    }

    @Test
    fun testSafetyPlanning() {
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Test safety planning
        composeTestRule.onNodeWithText("Safety Planning").performClick()
        composeTestRule.onNodeWithText("Remove means of self-harm").assertIsDisplayed()
        composeTestRule.onNodeWithText("Go to a safe place").assertIsDisplayed()
        composeTestRule.onNodeWithText("Call a trusted person").assertIsDisplayed()
    }
}
