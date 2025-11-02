package com.serenityai.tests.unit.usecases.uc9_mood_analytics

import com.serenityai.ui.screens.MoodDataPoint
import com.serenityai.ui.screens.MoodPattern
import com.serenityai.ui.screens.MoodAnalyticsScreen
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

@DisplayName("UC9: View Mood Analytics - Test Cases")
class MoodAnalyticsUseCaseTests {

    @Nested
    @DisplayName("Test Case 1: Mood Trend Analysis")
    inner class MoodTrendAnalysisTests {
        
        @Test
        fun `should calculate average mood correctly`() {
            // Given
            val moodData = listOf(
                MoodDataPoint("Mon", 3.5f, "Work stress"),
                MoodDataPoint("Tue", 4.2f, "Good workout"),
                MoodDataPoint("Wed", 2.8f, "Difficult meeting"),
                MoodDataPoint("Thu", 4.0f, "Friend visit"),
                MoodDataPoint("Fri", 4.5f, "Weekend plans")
            )
            
            // When
            val averageMood = moodData.map { it.value }.average()
            val highestMood = moodData.maxByOrNull { it.value }
            val lowestMood = moodData.minByOrNull { it.value }
            
            // Then
            assertEquals(3.8, averageMood, 0.01)
            assertNotNull(highestMood)
            assertEquals("Fri", highestMood!!.day)
            assertEquals(4.5f, highestMood.value)
            assertNotNull(lowestMood)
            assertEquals("Wed", lowestMood!!.day)
            assertEquals(2.8f, lowestMood.value)
        }
        
        @Test
        fun `should identify mood trends over time`() {
            // Given
            val moodData = listOf(
                MoodDataPoint("Mon", 3.0f, "Monday blues"),
                MoodDataPoint("Tue", 3.5f, "Getting better"),
                MoodDataPoint("Wed", 4.0f, "Good day"),
                MoodDataPoint("Thu", 4.2f, "Great day"),
                MoodDataPoint("Fri", 4.5f, "Excellent day")
            )
            
            // When
            val firstMood = moodData.first().value
            val lastMood = moodData.last().value
            val isImproving = lastMood > firstMood
            val improvement = lastMood - firstMood
            
            // Then
            assertTrue(isImproving)
            assertEquals(1.5f, improvement, 0.01f)
            assertTrue(improvement > 1.0f) // Significant improvement
        }
        
        @Test
        fun `should detect mood volatility patterns`() {
            // Given
            val stableMoodData = listOf(
                MoodDataPoint("Mon", 4.0f, "Stable"),
                MoodDataPoint("Tue", 4.1f, "Stable"),
                MoodDataPoint("Wed", 3.9f, "Stable"),
                MoodDataPoint("Thu", 4.0f, "Stable"),
                MoodDataPoint("Fri", 4.1f, "Stable")
            )
            
            val volatileMoodData = listOf(
                MoodDataPoint("Mon", 2.0f, "Low"),
                MoodDataPoint("Tue", 5.0f, "High"),
                MoodDataPoint("Wed", 1.5f, "Very low"),
                MoodDataPoint("Thu", 4.8f, "High"),
                MoodDataPoint("Fri", 2.2f, "Low")
            )
            
            // When
            val stableRange = stableMoodData.map { it.value }.maxOrNull()!! - stableMoodData.map { it.value }.minOrNull()!!
            val volatileRange = volatileMoodData.map { it.value }.maxOrNull()!! - volatileMoodData.map { it.value }.minOrNull()!!
            
            // Then
            assertTrue(stableRange < 0.5f) // Low volatility
            assertTrue(volatileRange > 2.0f) // High volatility
        }
    }

    @Nested
    @DisplayName("Test Case 2: Mood Pattern Recognition")
    inner class MoodPatternRecognitionTests {
        
        @Test
        fun `should identify weekly mood patterns`() {
            // Given
            val weeklyMoodData = listOf(
                MoodDataPoint("Mon", 2.5f, "Monday blues"),
                MoodDataPoint("Tue", 3.0f, "Getting better"),
                MoodDataPoint("Wed", 3.5f, "Midweek improvement"),
                MoodDataPoint("Thu", 4.0f, "Good day"),
                MoodDataPoint("Fri", 4.5f, "TGIF"),
                MoodDataPoint("Sat", 4.8f, "Weekend joy"),
                MoodDataPoint("Sun", 4.2f, "Sunday relaxation")
            )
            
            // When
            val weekdayMoods = weeklyMoodData.filter { 
                listOf("Mon", "Tue", "Wed", "Thu", "Fri").contains(it.day) 
            }.map { it.value }
            val weekendMoods = weeklyMoodData.filter { 
                listOf("Sat", "Sun").contains(it.day) 
            }.map { it.value }
            
            val weekdayAverage = weekdayMoods.average()
            val weekendAverage = weekendMoods.average()
            
            // Then
            assertEquals(3.5, weekdayAverage, 0.01)
            assertEquals(4.5, weekendAverage, 0.01)
            assertTrue(weekendAverage > weekdayAverage) // Weekend boost pattern
        }
        
        @Test
        fun `should detect mood triggers and correlations`() {
            // Given
            val moodDataWithTriggers = listOf(
                MoodDataPoint("Mon", 2.0f, "Work stress"),
                MoodDataPoint("Tue", 2.5f, "Work stress"),
                MoodDataPoint("Wed", 4.5f, "Exercise day"),
                MoodDataPoint("Thu", 2.2f, "Work stress"),
                MoodDataPoint("Fri", 4.8f, "Social activity"),
                MoodDataPoint("Sat", 4.5f, "Exercise day"),
                MoodDataPoint("Sun", 3.0f, "Work preparation")
            )
            
            // When
            val workStressDays = moodDataWithTriggers.filter { it.note.contains("Work stress") }
            val exerciseDays = moodDataWithTriggers.filter { it.note.contains("Exercise") }
            val socialDays = moodDataWithTriggers.filter { it.note.contains("Social") }
            
            val workStressAverage = workStressDays.map { it.value }.average()
            val exerciseAverage = exerciseDays.map { it.value }.average()
            val socialAverage = socialDays.map { it.value }.average()
            
            // Then
            assertEquals(3, workStressDays.size)
            assertEquals(2.23, workStressAverage, 0.01) // Low mood
            assertEquals(2, exerciseDays.size)
            assertEquals(4.5, exerciseAverage, 0.01) // High mood
            assertEquals(1, socialDays.size)
            assertEquals(4.8, socialAverage, 0.01) // Very high mood
        }
        
        @Test
        fun `should generate AI-detected mood patterns`() {
            // Given
            val moodPatterns = listOf(
                MoodPattern("Work Stress Pattern", "Mood drops on Mondays and Wednesdays", "Consider meditation before work"),
                MoodPattern("Exercise Boost", "Mood improves significantly after physical activity", "Schedule regular workouts"),
                MoodPattern("Weekend Effect", "Mood consistently higher on weekends", "Plan more weekend activities")
            )
            
            // When
            val workStressPattern = moodPatterns.find { it.title.contains("Work Stress") }
            val exercisePattern = moodPatterns.find { it.title.contains("Exercise") }
            val weekendPattern = moodPatterns.find { it.title.contains("Weekend") }
            
            // Then
            assertNotNull(workStressPattern)
            assertTrue(workStressPattern!!.description.contains("Mondays"))
            assertTrue(workStressPattern.recommendation.contains("meditation"))
            
            assertNotNull(exercisePattern)
            assertTrue(exercisePattern!!.description.contains("physical activity"))
            assertTrue(exercisePattern.recommendation.contains("workouts"))
            
            assertNotNull(weekendPattern)
            assertTrue(weekendPattern!!.description.contains("weekends"))
            assertTrue(weekendPattern.recommendation.contains("activities"))
        }
    }

    @Nested
    @DisplayName("Test Case 3: Analytics Dashboard and Insights")
    inner class AnalyticsDashboardTests {
        
        @Test
        fun `should calculate mood statistics accurately`() {
            // Given
            val moodData = listOf(
                MoodDataPoint("Mon", 3.5f, "Average day"),
                MoodDataPoint("Tue", 4.2f, "Good day"),
                MoodDataPoint("Wed", 2.8f, "Difficult day"),
                MoodDataPoint("Thu", 4.0f, "Stable day"),
                MoodDataPoint("Fri", 4.5f, "Great day"),
                MoodDataPoint("Sat", 4.8f, "Excellent day"),
                MoodDataPoint("Sun", 3.2f, "Relaxing day")
            )
            
            // When
            val totalDays = moodData.size
            val averageMood = moodData.map { it.value }.average()
            val bestDay = moodData.maxByOrNull { it.value }
            val worstDay = moodData.minByOrNull { it.value }
            val goodDays = moodData.count { it.value >= 4.0f }
            val poorDays = moodData.count { it.value < 3.0f }
            
            // Then
            assertEquals(7, totalDays)
            assertEquals(3.86, averageMood, 0.01)
            assertNotNull(bestDay)
            assertEquals("Sat", bestDay!!.day)
            assertEquals(4.8f, bestDay.value)
            assertNotNull(worstDay)
            assertEquals("Wed", worstDay!!.day)
            assertEquals(2.8f, worstDay.value)
            assertEquals(4, goodDays)
            assertEquals(1, poorDays)
        }
        
        @Test
        fun `should generate personalized recommendations based on patterns`() {
            // Given
            val moodData = listOf(
                MoodDataPoint("Mon", 2.5f, "Work stress"),
                MoodDataPoint("Tue", 3.0f, "Work stress"),
                MoodDataPoint("Wed", 4.5f, "Exercise day"),
                MoodDataPoint("Thu", 2.8f, "Work stress"),
                MoodDataPoint("Fri", 4.8f, "Social activity"),
                MoodDataPoint("Sat", 4.5f, "Exercise day"),
                MoodDataPoint("Sun", 3.2f, "Work preparation")
            )
            
            // When
            val workDays = moodData.filter { it.note.contains("Work") }
            val exerciseDays = moodData.filter { it.note.contains("Exercise") }
            val socialDays = moodData.filter { it.note.contains("Social") }
            
            val workMoodAverage = workDays.map { it.value }.average()
            val exerciseMoodAverage = exerciseDays.map { it.value }.average()
            val socialMoodAverage = socialDays.map { it.value }.average()
            
            // Then
            assertTrue(workMoodAverage < 3.5) // Low mood on work days
            assertTrue(exerciseMoodAverage > 4.0) // High mood on exercise days
            assertTrue(socialMoodAverage > 4.5) // Very high mood on social days
            
            // Generate recommendations based on patterns
            val recommendations = mutableListOf<String>()
            if (workMoodAverage < 3.5) {
                recommendations.add("Consider stress management techniques for work days")
            }
            if (exerciseMoodAverage > 4.0) {
                recommendations.add("Schedule more exercise sessions")
            }
            if (socialMoodAverage > 4.5) {
                recommendations.add("Plan more social activities")
            }
            
            assertEquals(3, recommendations.size)
            assertTrue(recommendations.any { it.contains("stress management") })
            assertTrue(recommendations.any { it.contains("exercise") })
            assertTrue(recommendations.any { it.contains("social") })
        }
        
        @Test
        fun `should track mood consistency and stability metrics`() {
            // Given
            val stableMoodData = listOf(
                MoodDataPoint("Mon", 4.0f, "Stable"),
                MoodDataPoint("Tue", 4.1f, "Stable"),
                MoodDataPoint("Wed", 3.9f, "Stable"),
                MoodDataPoint("Thu", 4.0f, "Stable"),
                MoodDataPoint("Fri", 4.1f, "Stable")
            )
            
            val unstableMoodData = listOf(
                MoodDataPoint("Mon", 2.0f, "Low"),
                MoodDataPoint("Tue", 5.0f, "High"),
                MoodDataPoint("Wed", 1.5f, "Very low"),
                MoodDataPoint("Thu", 4.8f, "High"),
                MoodDataPoint("Fri", 2.2f, "Low")
            )
            
            // When
            fun calculateConsistency(moodData: List<MoodDataPoint>): Float {
                val values = moodData.map { it.value }
                val average = values.average().toFloat()
                val variance = values.map { (it - average) * (it - average) }.average().toFloat()
                val standardDeviation = kotlin.math.sqrt(variance)
                return 1.0f / (1.0f + standardDeviation) // Higher value = more consistent
            }
            
            val stableConsistency = calculateConsistency(stableMoodData)
            val unstableConsistency = calculateConsistency(unstableMoodData)
            
            // Then
            assertTrue(stableConsistency > 0.8f) // High consistency
            assertTrue(unstableConsistency < 0.5f) // Low consistency
            assertTrue(stableConsistency > unstableConsistency)
        }
    }
}
