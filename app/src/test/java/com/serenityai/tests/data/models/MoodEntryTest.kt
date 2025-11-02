package com.serenityai.tests.data.models

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.util.*

class MoodEntryTest {

    @Nested
    @DisplayName("MoodEntry Creation")
    inner class MoodEntryCreation {

        @Test
        @DisplayName("should create mood entry with default values")
        fun shouldCreateMoodEntryWithDefaultValues() {
            val moodEntry = MoodEntry()
            
            assertEquals("", moodEntry.id)
            assertEquals("", moodEntry.userId)
            assertNotNull(moodEntry.date)
            assertEquals(5, moodEntry.mood)
            assertEquals(5, moodEntry.energy)
            assertEquals(5, moodEntry.stress)
            assertEquals(5, moodEntry.anxiety)
            assertEquals(5, moodEntry.sleep)
            assertEquals("", moodEntry.notes)
            assertTrue(moodEntry.tags.isEmpty())
            assertTrue(moodEntry.activities.isEmpty())
            assertNull(moodEntry.weather)
            assertNull(moodEntry.location)
        }

        @Test
        @DisplayName("should create mood entry with custom values")
        fun shouldCreateMoodEntryWithCustomValues() {
            val id = "mood123"
            val userId = "user123"
            val date = Date()
            val mood = 8
            val energy = 7
            val stress = 3
            val anxiety = 2
            val sleep = 9
            val notes = "Feeling great today!"
            val tags = listOf("happy", "productive")
            val activities = listOf("exercise", "work")
            val weather = "sunny"
            val location = "home"
            
            val moodEntry = MoodEntry(
                id = id,
                userId = userId,
                date = date,
                mood = mood,
                energy = energy,
                stress = stress,
                anxiety = anxiety,
                sleep = sleep,
                notes = notes,
                tags = tags,
                activities = activities,
                weather = weather,
                location = location
            )
            
            assertEquals(id, moodEntry.id)
            assertEquals(userId, moodEntry.userId)
            assertEquals(date, moodEntry.date)
            assertEquals(mood, moodEntry.mood)
            assertEquals(energy, moodEntry.energy)
            assertEquals(stress, moodEntry.stress)
            assertEquals(anxiety, moodEntry.anxiety)
            assertEquals(sleep, moodEntry.sleep)
            assertEquals(notes, moodEntry.notes)
            assertEquals(tags, moodEntry.tags)
            assertEquals(activities, moodEntry.activities)
            assertEquals(weather, moodEntry.weather)
            assertEquals(location, moodEntry.location)
        }
    }

    @Nested
    @DisplayName("MoodEntry Validation")
    inner class MoodEntryValidation {

        @ParameterizedTest
        @ValueSource(ints = [1, 5, 10])
        @DisplayName("should accept valid mood values")
        fun shouldAcceptValidMoodValues(mood: Int) {
            val moodEntry = MoodEntry(mood = mood)
            assertTrue(mood >= 1 && mood <= 10)
        }

        @ParameterizedTest
        @ValueSource(ints = [1, 5, 10])
        @DisplayName("should accept valid energy values")
        fun shouldAcceptValidEnergyValues(energy: Int) {
            val moodEntry = MoodEntry(energy = energy)
            assertTrue(energy >= 1 && energy <= 10)
        }

        @ParameterizedTest
        @ValueSource(ints = [1, 5, 10])
        @DisplayName("should accept valid stress values")
        fun shouldAcceptValidStressValues(stress: Int) {
            val moodEntry = MoodEntry(stress = stress)
            assertTrue(stress >= 1 && stress <= 10)
        }

        @ParameterizedTest
        @ValueSource(ints = [1, 5, 10])
        @DisplayName("should accept valid anxiety values")
        fun shouldAcceptValidAnxietyValues(anxiety: Int) {
            val moodEntry = MoodEntry(anxiety = anxiety)
            assertTrue(anxiety >= 1 && anxiety <= 10)
        }

        @ParameterizedTest
        @ValueSource(ints = [1, 5, 10])
        @DisplayName("should accept valid sleep values")
        fun shouldAcceptValidSleepValues(sleep: Int) {
            val moodEntry = MoodEntry(sleep = sleep)
            assertTrue(sleep >= 1 && sleep <= 10)
        }
    }

    @Nested
    @DisplayName("MoodEntry Equality")
    inner class MoodEntryEquality {

        @Test
        @DisplayName("should be equal when all properties match")
        fun shouldBeEqualWhenAllPropertiesMatch() {
            val date = Date()
            val moodEntry1 = MoodEntry(
                id = "mood123",
                userId = "user123",
                date = date,
                mood = 8,
                notes = "Great day!"
            )
            
            val moodEntry2 = MoodEntry(
                id = "mood123",
                userId = "user123",
                date = date,
                mood = 8,
                notes = "Great day!"
            )
            
            assertEquals(moodEntry1, moodEntry2)
            assertEquals(moodEntry1.hashCode(), moodEntry2.hashCode())
        }

        @Test
        @DisplayName("should not be equal when properties differ")
        fun shouldNotBeEqualWhenPropertiesDiffer() {
            val moodEntry1 = MoodEntry(id = "mood123", mood = 8)
            val moodEntry2 = MoodEntry(id = "mood123", mood = 7)
            
            assertNotEquals(moodEntry1, moodEntry2)
        }
    }

    @Nested
    @DisplayName("MoodEntry Copy")
    inner class MoodEntryCopy {

        @Test
        @DisplayName("should create copy with modified properties")
        fun shouldCreateCopyWithModifiedProperties() {
            val originalMoodEntry = MoodEntry(
                id = "mood123",
                mood = 5,
                notes = "Original note"
            )
            
            val modifiedMoodEntry = originalMoodEntry.copy(
                mood = 8,
                notes = "Updated note"
            )
            
            assertEquals(originalMoodEntry.id, modifiedMoodEntry.id)
            assertEquals(8, modifiedMoodEntry.mood)
            assertEquals("Updated note", modifiedMoodEntry.notes)
        }
    }
}

class MoodTrendTest {

    @Nested
    @DisplayName("MoodTrend Creation")
    inner class MoodTrendCreation {

        @Test
        @DisplayName("should create mood trend with all properties")
        fun shouldCreateMoodTrendWithAllProperties() {
            val period = "week"
            val averageMood = 7.5
            val moodHistory = listOf(
                MoodEntry(mood = 7),
                MoodEntry(mood = 8)
            )
            val insights = listOf("Improving trend", "Good sleep pattern")
            
            val moodTrend = MoodTrend(
                period = period,
                averageMood = averageMood,
                moodHistory = moodHistory,
                insights = insights
            )
            
            assertEquals(period, moodTrend.period)
            assertEquals(averageMood, moodTrend.averageMood)
            assertEquals(moodHistory, moodTrend.moodHistory)
            assertEquals(insights, moodTrend.insights)
        }

        @Test
        @DisplayName("should create mood trend with default insights")
        fun shouldCreateMoodTrendWithDefaultInsights() {
            val moodTrend = MoodTrend(
                period = "month",
                averageMood = 6.0,
                moodHistory = emptyList()
            )
            
            assertTrue(moodTrend.insights.isEmpty())
        }
    }
}

class MoodInsightTest {

    @Nested
    @DisplayName("MoodInsight Creation")
    inner class MoodInsightCreation {

        @Test
        @DisplayName("should create mood insight with all properties")
        fun shouldCreateMoodInsightWithAllProperties() {
            val type = InsightType.POSITIVE_TREND
            val message = "Your mood has been improving"
            val confidence = 0.85
            val actionable = true
            val suggestion = "Continue your current routine"
            
            val moodInsight = MoodInsight(
                type = type,
                message = message,
                confidence = confidence,
                actionable = actionable,
                suggestion = suggestion
            )
            
            assertEquals(type, moodInsight.type)
            assertEquals(message, moodInsight.message)
            assertEquals(confidence, moodInsight.confidence)
            assertEquals(actionable, moodInsight.actionable)
            assertEquals(suggestion, moodInsight.suggestion)
        }

        @Test
        @DisplayName("should create mood insight with default values")
        fun shouldCreateMoodInsightWithDefaultValues() {
            val moodInsight = MoodInsight(
                type = InsightType.PATTERN_DETECTED,
                message = "Pattern detected",
                confidence = 0.5
            )
            
            assertFalse(moodInsight.actionable)
            assertNull(moodInsight.suggestion)
        }
    }
}

class InsightTypeTest {

    @Nested
    @DisplayName("InsightType Enum")
    inner class InsightTypeEnum {

        @Test
        @DisplayName("should have all expected insight types")
        fun shouldHaveAllExpectedInsightTypes() {
            val expectedTypes = listOf(
                InsightType.POSITIVE_TREND,
                InsightType.NEGATIVE_TREND,
                InsightType.PATTERN_DETECTED,
                InsightType.RECOMMENDATION,
                InsightType.WARNING
            )
            
            assertEquals(5, InsightType.values().size)
            expectedTypes.forEach { type ->
                assertTrue(InsightType.values().contains(type))
            }
        }

        @Test
        @DisplayName("should have correct string representation")
        fun shouldHaveCorrectStringRepresentation() {
            assertEquals("POSITIVE_TREND", InsightType.POSITIVE_TREND.name)
            assertEquals("NEGATIVE_TREND", InsightType.NEGATIVE_TREND.name)
            assertEquals("PATTERN_DETECTED", InsightType.PATTERN_DETECTED.name)
            assertEquals("RECOMMENDATION", InsightType.RECOMMENDATION.name)
            assertEquals("WARNING", InsightType.WARNING.name)
        }
    }
}
