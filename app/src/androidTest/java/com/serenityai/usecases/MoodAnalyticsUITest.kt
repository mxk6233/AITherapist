package com.serenityai.usecases

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.serenityai.InteractiveMainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MoodAnalyticsUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testMoodAnalyticsUI() {
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Test mood analytics interface
        composeTestRule.onNodeWithText("Mood Analytics").assertIsDisplayed()
        composeTestRule.onNodeWithText("Mood Trends").assertIsDisplayed()
        composeTestRule.onNodeWithText("Mood Patterns").assertIsDisplayed()
        composeTestRule.onNodeWithText("Mood Insights").assertIsDisplayed()
    }

    @Test
    fun testMoodTrendsChart() {
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Test mood trends chart
        composeTestRule.onNodeWithText("Mood Trends").performClick()
        composeTestRule.onNodeWithText("Past 7 days").assertIsDisplayed()
        composeTestRule.onNodeWithText("Past 30 days").assertIsDisplayed()
        composeTestRule.onNodeWithText("Past 3 months").assertIsDisplayed()
    }

    @Test
    fun testMoodPatterns() {
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Test mood patterns
        composeTestRule.onNodeWithText("Mood Patterns").performClick()
        composeTestRule.onNodeWithText("Weekly Patterns").assertIsDisplayed()
        composeTestRule.onNodeWithText("Time of Day").assertIsDisplayed()
        composeTestRule.onNodeWithText("Day of Week").assertIsDisplayed()
    }

    @Test
    fun testMoodInsights() {
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Test mood insights
        composeTestRule.onNodeWithText("Mood Insights").performClick()
        composeTestRule.onNodeWithText("Your mood has been improving").assertIsDisplayed()
        composeTestRule.onNodeWithText("Common triggers").assertIsDisplayed()
        composeTestRule.onNodeWithText("Recommendations").assertIsDisplayed()
    }

    @Test
    fun testMoodStatistics() {
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Test mood statistics
        composeTestRule.onNodeWithText("Statistics").performClick()
        composeTestRule.onNodeWithText("Average Mood").assertIsDisplayed()
        composeTestRule.onNodeWithText("Mood Range").assertIsDisplayed()
        composeTestRule.onNodeWithText("Total Entries").assertIsDisplayed()
    }

    @Test
    fun testMoodCorrelations() {
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Test mood correlations
        composeTestRule.onNodeWithText("Correlations").performClick()
        composeTestRule.onNodeWithText("Mood vs Energy").assertIsDisplayed()
        composeTestRule.onNodeWithText("Mood vs Stress").assertIsDisplayed()
        composeTestRule.onNodeWithText("Mood vs Sleep").assertIsDisplayed()
    }

    @Test
    fun testMoodReports() {
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Test mood reports
        composeTestRule.onNodeWithText("Reports").performClick()
        composeTestRule.onNodeWithText("Weekly Report").assertIsDisplayed()
        composeTestRule.onNodeWithText("Monthly Report").assertIsDisplayed()
        composeTestRule.onNodeWithText("Export Report").assertIsDisplayed()
    }

    @Test
    fun testMoodPredictions() {
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Test mood predictions
        composeTestRule.onNodeWithText("Predictions").performClick()
        composeTestRule.onNodeWithText("Predicted Trend").assertIsDisplayed()
        composeTestRule.onNodeWithText("Confidence Level").assertIsDisplayed()
        composeTestRule.onNodeWithText("Risk Assessment").assertIsDisplayed()
    }

    @Test
    fun testMoodVisualizations() {
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Test mood visualizations
        composeTestRule.onNodeWithText("Visualizations").performClick()
        composeTestRule.onNodeWithText("Line Chart").assertIsDisplayed()
        composeTestRule.onNodeWithText("Bar Chart").assertIsDisplayed()
        composeTestRule.onNodeWithText("Heatmap").assertIsDisplayed()
        composeTestRule.onNodeWithText("Pie Chart").assertIsDisplayed()
    }

    @Test
    fun testMoodExport() {
        composeTestRule.setContent {
            InteractiveMainActivity()
        }

        // Test mood export
        composeTestRule.onNodeWithText("Export").performClick()
        composeTestRule.onNodeWithText("Export as CSV").assertIsDisplayed()
        composeTestRule.onNodeWithText("Export as PDF").assertIsDisplayed()
        composeTestRule.onNodeWithText("Export as Image").assertIsDisplayed()
    }
}
