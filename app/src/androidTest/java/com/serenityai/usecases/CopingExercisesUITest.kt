package com.serenityai.usecases

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.serenityai.AITherpistDashboard
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CopingExercisesUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testCopingExercisesUI() {
        composeTestRule.setContent {
            AITherpistDashboard()
        }

        // Test coping exercises interface
        composeTestRule.onNodeWithText("Coping Exercises").assertIsDisplayed()
        composeTestRule.onNodeWithText("Stress Relief").assertIsDisplayed()
        composeTestRule.onNodeWithText("Anxiety Management").assertIsDisplayed()
        composeTestRule.onNodeWithText("Mood Enhancement").assertIsDisplayed()
    }

    @Test
    fun testExerciseCategories() {
        composeTestRule.setContent {
            AITherpistDashboard()
        }

        // Test exercise categories
        composeTestRule.onNodeWithText("Stress Relief").performClick()
        composeTestRule.onNodeWithText("Deep Breathing").assertIsDisplayed()
        composeTestRule.onNodeWithText("Progressive Muscle Relaxation").assertIsDisplayed()
        composeTestRule.onNodeWithText("Mindful Walking").assertIsDisplayed()
    }

    @Test
    fun testExerciseSelection() {
        composeTestRule.setContent {
            AITherpistDashboard()
        }

        // Test exercise selection
        composeTestRule.onNodeWithText("Deep Breathing").performClick()
        composeTestRule.onNodeWithText("Start Exercise").assertIsDisplayed()
        composeTestRule.onNodeWithText("Duration: 5 minutes").assertIsDisplayed()
        composeTestRule.onNodeWithText("Difficulty: Easy").assertIsDisplayed()
    }

    @Test
    fun testExerciseExecution() {
        composeTestRule.setContent {
            AITherpistDashboard()
        }

        // Test exercise execution
        composeTestRule.onNodeWithText("Deep Breathing").performClick()
        composeTestRule.onNodeWithText("Start Exercise").performClick()
        composeTestRule.onNodeWithText("Inhale for 4 counts").assertIsDisplayed()
        composeTestRule.onNodeWithText("Hold for 7 counts").assertIsDisplayed()
        composeTestRule.onNodeWithText("Exhale for 8 counts").assertIsDisplayed()
    }

    @Test
    fun testExerciseProgress() {
        composeTestRule.setContent {
            AITherpistDashboard()
        }

        // Test exercise progress
        composeTestRule.onNodeWithText("Deep Breathing").performClick()
        composeTestRule.onNodeWithText("Start Exercise").performClick()
        composeTestRule.onNodeWithText("Progress: 0%").assertIsDisplayed()
        composeTestRule.onNodeWithText("Time remaining: 5:00").assertIsDisplayed()
    }

    @Test
    fun testExerciseCompletion() {
        composeTestRule.setContent {
            AITherpistDashboard()
        }

        // Test exercise completion
        composeTestRule.onNodeWithText("Deep Breathing").performClick()
        composeTestRule.onNodeWithText("Start Exercise").performClick()
        composeTestRule.onNodeWithText("Complete").performClick()
        composeTestRule.onNodeWithText("Exercise completed").assertIsDisplayed()
        composeTestRule.onNodeWithText("Rate your mood").assertIsDisplayed()
    }

    @Test
    fun testPersonalizedRecommendations() {
        composeTestRule.setContent {
            AITherpistDashboard()
        }

        // Test personalized recommendations
        composeTestRule.onNodeWithText("Recommended for You").performClick()
        composeTestRule.onNodeWithText("Based on your mood").assertIsDisplayed()
        composeTestRule.onNodeWithText("Based on your stress level").assertIsDisplayed()
        composeTestRule.onNodeWithText("Based on your anxiety level").assertIsDisplayed()
    }

    @Test
    fun testExerciseHistory() {
        composeTestRule.setContent {
            AITherpistDashboard()
        }

        // Test exercise history
        composeTestRule.onNodeWithText("Exercise History").performClick()
        composeTestRule.onNodeWithText("Completed Exercises").assertIsDisplayed()
        composeTestRule.onNodeWithText("Total Time").assertIsDisplayed()
        composeTestRule.onNodeWithText("Streak").assertIsDisplayed()
    }

    @Test
    fun testExerciseReminders() {
        composeTestRule.setContent {
            AITherpistDashboard()
        }

        // Test exercise reminders
        composeTestRule.onNodeWithText("Set Reminder").performClick()
        composeTestRule.onNodeWithText("Daily Reminder").assertIsDisplayed()
        composeTestRule.onNodeWithText("Time").assertIsDisplayed()
        composeTestRule.onNodeWithText("Exercise Type").assertIsDisplayed()
    }
}
