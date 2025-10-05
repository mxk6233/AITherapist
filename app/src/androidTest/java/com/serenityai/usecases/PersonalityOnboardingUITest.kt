package com.serenityai.usecases

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.serenityai.InteractiveMainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PersonalityOnboardingUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testPersonalityAssessmentUI() {
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Test personality assessment interface
        composeTestRule.onNodeWithText("Personality Assessment").assertIsDisplayed()
        composeTestRule.onNodeWithText("Question 1 of 10").assertIsDisplayed()
        composeTestRule.onNodeWithText("Strongly Agree").assertIsDisplayed()
        composeTestRule.onNodeWithText("Strongly Disagree").assertIsDisplayed()
    }

    @Test
    fun testPersonalityQuestions() {
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Test personality questions
        composeTestRule.onNodeWithText("I prefer quiet environments").assertIsDisplayed()
        composeTestRule.onNodeWithText("Strongly Agree").performClick()
        composeTestRule.onNodeWithText("Next").performClick()
        
        composeTestRule.onNodeWithText("I enjoy trying new experiences").assertIsDisplayed()
        composeTestRule.onNodeWithText("Agree").performClick()
        composeTestRule.onNodeWithText("Next").performClick()
    }

    @Test
    fun testAssessmentProgress() {
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Test assessment progress
        composeTestRule.onNodeWithText("Question 1 of 10").assertIsDisplayed()
        composeTestRule.onNodeWithText("Progress: 10%").assertIsDisplayed()
        
        composeTestRule.onNodeWithText("Strongly Agree").performClick()
        composeTestRule.onNodeWithText("Next").performClick()
        
        composeTestRule.onNodeWithText("Question 2 of 10").assertIsDisplayed()
        composeTestRule.onNodeWithText("Progress: 20%").assertIsDisplayed()
    }

    @Test
    fun testPersonalityResults() {
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Test personality results
        composeTestRule.onNodeWithText("Complete Assessment").performClick()
        composeTestRule.onNodeWithText("Your Personality Profile").assertIsDisplayed()
        composeTestRule.onNodeWithText("Introversion").assertIsDisplayed()
        composeTestRule.onNodeWithText("Openness").assertIsDisplayed()
        composeTestRule.onNodeWithText("Conscientiousness").assertIsDisplayed()
    }

    @Test
    fun testPersonalizedRecommendations() {
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Test personalized recommendations
        composeTestRule.onNodeWithText("Personalized Recommendations").performClick()
        composeTestRule.onNodeWithText("Communication Style").assertIsDisplayed()
        composeTestRule.onNodeWithText("Therapy Approach").assertIsDisplayed()
        composeTestRule.onNodeWithText("Coping Strategies").assertIsDisplayed()
    }

    @Test
    fun testUIPreferences() {
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Test UI preferences
        composeTestRule.onNodeWithText("Customize Your Experience").performClick()
        composeTestRule.onNodeWithText("Theme").assertIsDisplayed()
        composeTestRule.onNodeWithText("Light").performClick()
        composeTestRule.onNodeWithText("Dark").performClick()
        composeTestRule.onNodeWithText("Auto").performClick()
    }
}
