package com.serenityai.usecases

import com.serenityai.data.models.*
import com.serenityai.utils.Helpers
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.util.*

class MoodLoggingUseCaseTest {

    private lateinit var moodLoggingUseCase: MoodLoggingUseCase

    @BeforeEach
    fun setUp() {
        moodLoggingUseCase = MoodLoggingUseCase()
    }

    @Nested
    @DisplayName("Mood Entry Creation")
    inner class MoodEntryCreation {

        @Test
        @DisplayName("should create mood entry with valid data")
        fun shouldCreateMoodEntryWithValidData() = runTest {
            val userId = "user123"
            val mood = 7
            val energy = 6
            val stress = 4
            val anxiety = 3
            val sleep = 8
            val notes = "Feeling good today"

            val moodEntry = moodLoggingUseCase.createMoodEntry(
                userId = userId,
                mood = mood,
                energy = energy,
                stress = stress,
                anxiety = anxiety,
                sleep = sleep,
                notes = notes
            )

            assertNotNull(moodEntry)
            assertEquals(userId, moodEntry.userId)
            assertEquals(mood, moodEntry.mood)
            assertEquals(energy, moodEntry.energy)
            assertEquals(stress, moodEntry.stress)
            assertEquals(anxiety, moodEntry.anxiety)
            assertEquals(sleep, moodEntry.sleep)
            assertEquals(notes, moodEntry.notes)
            assertNotNull(moodEntry.date)
        }

        @Test
        @DisplayName("should create mood entry with default values")
        fun shouldCreateMoodEntryWithDefaultValues() = runTest {
            val userId = "user123"
            val moodEntry = moodLoggingUseCase.createMoodEntry(userId = userId, mood = 5)

            assertNotNull(moodEntry)
            assertEquals(userId, moodEntry.userId)
            assertEquals(5, moodEntry.mood)
            assertEquals(5, moodEntry.energy) // Default value
            assertEquals(5, moodEntry.stress) // Default value
            assertEquals(5, moodEntry.anxiety) // Default value
            assertEquals(5, moodEntry.sleep) // Default value
            assertEquals("", moodEntry.notes) // Default value
        }

        @ParameterizedTest
        @ValueSource(ints = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10])
        @DisplayName("should accept valid mood values")
        fun shouldAcceptValidMoodValues(mood: Int) = runTest {
            val moodEntry = moodLoggingUseCase.createMoodEntry("user123", mood)
            assertTrue(moodEntry.mood in 1..10)
        }
    }

    @Nested
    @DisplayName("Mood Validation")
    inner class MoodValidation {

        @Test
        @DisplayName("should validate mood entry data")
        fun shouldValidateMoodEntryData() = runTest {
            val moodEntry = moodLoggingUseCase.createMoodEntry("user123", 7)
            val isValid = moodLoggingUseCase.validateMoodEntry(moodEntry)

            assertTrue(isValid)
        }

        @Test
        @DisplayName("should reject invalid mood values")
        fun shouldRejectInvalidMoodValues() = runTest {
            val invalidMoodEntry = MoodEntry(
                id = "test123",
                userId = "user123",
                date = Date(),
                mood = 15, // Invalid mood value
                energy = 5,
                stress = 5,
                anxiety = 5,
                sleep = 5,
                notes = "Test"
            )

            val isValid = moodLoggingUseCase.validateMoodEntry(invalidMoodEntry)
            assertFalse(isValid)
        }

        @Test
        @DisplayName("should reject empty user ID")
        fun shouldRejectEmptyUserId() = runTest {
            val invalidMoodEntry = MoodEntry(
                id = "test123",
                userId = "", // Empty user ID
                date = Date(),
                mood = 7,
                energy = 5,
                stress = 5,
                anxiety = 5,
                sleep = 5,
                notes = "Test"
            )

            val isValid = moodLoggingUseCase.validateMoodEntry(invalidMoodEntry)
            assertFalse(isValid)
        }
    }

    @Nested
    @DisplayName("Mood Analytics")
    inner class MoodAnalytics {

        @Test
        @DisplayName("should calculate average mood")
        fun shouldCalculateAverageMood() = runTest {
            val moodEntries = listOf(
                moodLoggingUseCase.createMoodEntry("user123", 7),
                moodLoggingUseCase.createMoodEntry("user123", 8),
                moodLoggingUseCase.createMoodEntry("user123", 6)
            )

            val averageMood = moodLoggingUseCase.calculateAverageMood(moodEntries)
            assertEquals(7.0, averageMood, 0.1)
        }

        @Test
        @DisplayName("should identify mood trends")
        fun shouldIdentifyMoodTrends() = runTest {
            val moodEntries = listOf(
                moodLoggingUseCase.createMoodEntry("user123", 3),
                moodLoggingUseCase.createMoodEntry("user123", 4),
                moodLoggingUseCase.createMoodEntry("user123", 5),
                moodLoggingUseCase.createMoodEntry("user123", 6),
                moodLoggingUseCase.createMoodEntry("user123", 7)
            )

            val trend = moodLoggingUseCase.identifyMoodTrend(moodEntries)
            assertEquals("Improving", trend)
        }

        @Test
        @DisplayName("should detect declining mood trend")
        fun shouldDetectDecliningMoodTrend() = runTest {
            val moodEntries = listOf(
                moodLoggingUseCase.createMoodEntry("user123", 8),
                moodLoggingUseCase.createMoodEntry("user123", 7),
                moodLoggingUseCase.createMoodEntry("user123", 6),
                moodLoggingUseCase.createMoodEntry("user123", 5),
                moodLoggingUseCase.createMoodEntry("user123", 4)
            )

            val trend = moodLoggingUseCase.identifyMoodTrend(moodEntries)
            assertEquals("Declining", trend)
        }

        @Test
        @DisplayName("should detect stable mood trend")
        fun shouldDetectStableMoodTrend() = runTest {
            val moodEntries = listOf(
                moodLoggingUseCase.createMoodEntry("user123", 6),
                moodLoggingUseCase.createMoodEntry("user123", 5),
                moodLoggingUseCase.createMoodEntry("user123", 6),
                moodLoggingUseCase.createMoodEntry("user123", 5),
                moodLoggingUseCase.createMoodEntry("user123", 6)
            )

            val trend = moodLoggingUseCase.identifyMoodTrend(moodEntries)
            assertEquals("Stable", trend)
        }

        @Test
        @DisplayName("should generate mood insights")
        fun shouldGenerateMoodInsights() = runTest {
            val moodEntries = listOf(
                moodLoggingUseCase.createMoodEntry("user123", 3),
                moodLoggingUseCase.createMoodEntry("user123", 4),
                moodLoggingUseCase.createMoodEntry("user123", 5),
                moodLoggingUseCase.createMoodEntry("user123", 6),
                moodLoggingUseCase.createMoodEntry("user123", 7)
            )

            val insights = moodLoggingUseCase.generateMoodInsights(moodEntries)
            assertNotNull(insights)
            assertTrue(insights.isNotEmpty())
            assertTrue(insights.any { it.type == InsightType.POSITIVE_TREND })
        }
    }

    @Nested
    @DisplayName("Mood Patterns")
    inner class MoodPatterns {

        @Test
        @DisplayName("should identify mood patterns by day of week")
        fun shouldIdentifyMoodPatternsByDayOfWeek() = runTest {
            val moodEntries = listOf(
                moodLoggingUseCase.createMoodEntry("user123", 8), // Monday
                moodLoggingUseCase.createMoodEntry("user123", 7), // Tuesday
                moodLoggingUseCase.createMoodEntry("user123", 6), // Wednesday
                moodLoggingUseCase.createMoodEntry("user123", 5), // Thursday
                moodLoggingUseCase.createMoodEntry("user123", 9), // Friday
                moodLoggingUseCase.createMoodEntry("user123", 8), // Saturday
                moodLoggingUseCase.createMoodEntry("user123", 7)  // Sunday
            )

            val patterns = moodLoggingUseCase.identifyMoodPatterns(moodEntries)
            assertNotNull(patterns)
            assertTrue(patterns.isNotEmpty())
        }

        @Test
        @DisplayName("should detect mood correlations")
        fun shouldDetectMoodCorrelations() = runTest {
            val moodEntries = listOf(
                moodLoggingUseCase.createMoodEntry("user123", 8, energy = 7, stress = 2),
                moodLoggingUseCase.createMoodEntry("user123", 6, energy = 5, stress = 4),
                moodLoggingUseCase.createMoodEntry("user123", 4, energy = 3, stress = 6),
                moodLoggingUseCase.createMoodEntry("user123", 2, energy = 2, stress = 8)
            )

            val correlations = moodLoggingUseCase.detectMoodCorrelations(moodEntries)
            assertNotNull(correlations)
            assertTrue(correlations.isNotEmpty())
        }

        @Test
        @DisplayName("should identify triggers")
        fun shouldIdentifyTriggers() = runTest {
            val moodEntries = listOf(
                moodLoggingUseCase.createMoodEntry("user123", 3, notes = "Work stress"),
                moodLoggingUseCase.createMoodEntry("user123", 2, notes = "Work stress"),
                moodLoggingUseCase.createMoodEntry("user123", 4, notes = "Work stress"),
                moodLoggingUseCase.createMoodEntry("user123", 8, notes = "Weekend"),
                moodLoggingUseCase.createMoodEntry("user123", 7, notes = "Weekend")
            )

            val triggers = moodLoggingUseCase.identifyTriggers(moodEntries)
            assertNotNull(triggers)
            assertTrue(triggers.isNotEmpty())
        }
    }

    @Nested
    @DisplayName("Mood Recommendations")
    inner class MoodRecommendations {

        @Test
        @DisplayName("should provide mood improvement suggestions")
        fun shouldProvideMoodImprovementSuggestions() = runTest {
            val moodEntries = listOf(
                moodLoggingUseCase.createMoodEntry("user123", 3),
                moodLoggingUseCase.createMoodEntry("user123", 4),
                moodLoggingUseCase.createMoodEntry("user123", 3)
            )

            val suggestions = moodLoggingUseCase.getMoodImprovementSuggestions(moodEntries)
            assertNotNull(suggestions)
            assertTrue(suggestions.isNotEmpty())
        }

        @Test
        @DisplayName("should suggest activities based on mood")
        fun shouldSuggestActivitiesBasedOnMood() = runTest {
            val lowMoodEntry = moodLoggingUseCase.createMoodEntry("user123", 3)
            val activities = moodLoggingUseCase.suggestActivities(lowMoodEntry)

            assertNotNull(activities)
            assertTrue(activities.isNotEmpty())
            assertTrue(activities.any { it.contains("exercise") || it.contains("walk") })
        }

        @Test
        @DisplayName("should provide coping strategies")
        fun shouldProvideCopingStrategies() = runTest {
            val stressEntry = moodLoggingUseCase.createMoodEntry("user123", 5, stress = 8)
            val strategies = moodLoggingUseCase.getCopingStrategies(stressEntry)

            assertNotNull(strategies)
            assertTrue(strategies.isNotEmpty())
            assertTrue(strategies.any { it.contains("breathing") || it.contains("relaxation") })
        }
    }

    @Nested
    @DisplayName("Mood History")
    inner class MoodHistory {

        @Test
        @DisplayName("should retrieve mood history")
        fun shouldRetrieveMoodHistory() = runTest {
            val userId = "user123"
            val moodEntries = listOf(
                moodLoggingUseCase.createMoodEntry(userId, 7),
                moodLoggingUseCase.createMoodEntry(userId, 8),
                moodLoggingUseCase.createMoodEntry(userId, 6)
            )

            val history = moodLoggingUseCase.getMoodHistory(userId, moodEntries)
            assertNotNull(history)
            assertEquals(3, history.size)
        }

        @Test
        @DisplayName("should filter mood history by date range")
        fun shouldFilterMoodHistoryByDateRange() = runTest {
            val userId = "user123"
            val now = Date()
            val weekAgo = Date(now.time - 7 * 24 * 60 * 60 * 1000)
            val twoWeeksAgo = Date(now.time - 14 * 24 * 60 * 60 * 1000)

            val moodEntries = listOf(
                MoodEntry(userId = userId, date = twoWeeksAgo, mood = 5),
                MoodEntry(userId = userId, date = weekAgo, mood = 7),
                MoodEntry(userId = userId, date = now, mood = 8)
            )

            val recentHistory = moodLoggingUseCase.getMoodHistoryByDateRange(userId, moodEntries, weekAgo, now)
            assertEquals(2, recentHistory.size)
        }

        @Test
        @DisplayName("should generate mood report")
        fun shouldGenerateMoodReport() = runTest {
            val userId = "user123"
            val moodEntries = listOf(
                moodLoggingUseCase.createMoodEntry(userId, 7),
                moodLoggingUseCase.createMoodEntry(userId, 8),
                moodLoggingUseCase.createMoodEntry(userId, 6),
                moodLoggingUseCase.createMoodEntry(userId, 7),
                moodLoggingUseCase.createMoodEntry(userId, 8)
            )

            val report = moodLoggingUseCase.generateMoodReport(userId, moodEntries)
            assertNotNull(report)
            assertTrue(report.contains("Average Mood"))
            assertTrue(report.contains("Trend"))
        }
    }
}

/**
 * Use Case implementation for Mood Logging
 */
class MoodLoggingUseCase {
    
    suspend fun createMoodEntry(
        userId: String,
        mood: Int,
        energy: Int = 5,
        stress: Int = 5,
        anxiety: Int = 5,
        sleep: Int = 5,
        notes: String = "",
        tags: List<String> = emptyList(),
        activities: List<String> = emptyList(),
        weather: String? = null,
        location: String? = null
    ): MoodEntry {
        return MoodEntry(
            id = UUID.randomUUID().toString(),
            userId = userId,
            date = Date(),
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
    }

    fun validateMoodEntry(moodEntry: MoodEntry): Boolean {
        return moodEntry.userId.isNotEmpty() &&
               moodEntry.mood in 1..10 &&
               moodEntry.energy in 1..10 &&
               moodEntry.stress in 1..10 &&
               moodEntry.anxiety in 1..10 &&
               moodEntry.sleep in 1..10
    }

    fun calculateAverageMood(moodEntries: List<MoodEntry>): Double {
        return if (moodEntries.isNotEmpty()) {
            moodEntries.map { it.mood }.average()
        } else {
            0.0
        }
    }

    fun identifyMoodTrend(moodEntries: List<MoodEntry>): String {
        if (moodEntries.size < 2) return "Insufficient data"
        
        val moods = moodEntries.map { it.mood }
        val recent = moods.takeLast(3).average()
        val older = moods.dropLast(3).takeLast(3).average()
        
        return when {
            recent > older + 0.5 -> "Improving"
            recent < older - 0.5 -> "Declining"
            else -> "Stable"
        }
    }

    fun generateMoodInsights(moodEntries: List<MoodEntry>): List<MoodInsight> {
        val insights = mutableListOf<MoodInsight>()
        
        if (moodEntries.size >= 5) {
            val trend = identifyMoodTrend(moodEntries)
            when (trend) {
                "Improving" -> insights.add(
                    MoodInsight(
                        type = InsightType.POSITIVE_TREND,
                        message = "Your mood has been improving over time!",
                        confidence = 0.8,
                        actionable = true,
                        suggestion = "Continue the activities that are helping you feel better."
                    )
                )
                "Declining" -> insights.add(
                    MoodInsight(
                        type = InsightType.NEGATIVE_TREND,
                        message = "Your mood has been declining recently.",
                        confidence = 0.8,
                        actionable = true,
                        suggestion = "Consider reaching out for support or trying new coping strategies."
                    )
                )
            }
        }
        
        return insights
    }

    fun identifyMoodPatterns(moodEntries: List<MoodEntry>): List<String> {
        val patterns = mutableListOf<String>()
        
        // Analyze by day of week
        val dayOfWeekMoods = moodEntries.groupBy { 
            java.util.Calendar.getInstance().apply { time = it.date }.get(java.util.Calendar.DAY_OF_WEEK)
        }
        
        dayOfWeekMoods.forEach { (day, entries) ->
            val avgMood = entries.map { it.mood }.average()
            if (avgMood < 4) {
                patterns.add("Lower mood on ${getDayName(day)}")
            } else if (avgMood > 7) {
                patterns.add("Higher mood on ${getDayName(day)}")
            }
        }
        
        return patterns
    }

    fun detectMoodCorrelations(moodEntries: List<MoodEntry>): List<String> {
        val correlations = mutableListOf<String>()
        
        // Check correlation between mood and energy
        val moodEnergyCorrelation = calculateCorrelation(
            moodEntries.map { it.mood.toDouble() },
            moodEntries.map { it.energy.toDouble() }
        )
        
        if (moodEnergyCorrelation > 0.7) {
            correlations.add("Strong positive correlation between mood and energy")
        }
        
        // Check correlation between mood and stress
        val moodStressCorrelation = calculateCorrelation(
            moodEntries.map { it.mood.toDouble() },
            moodEntries.map { it.stress.toDouble() }
        )
        
        if (moodStressCorrelation < -0.7) {
            correlations.add("Strong negative correlation between mood and stress")
        }
        
        return correlations
    }

    fun identifyTriggers(moodEntries: List<MoodEntry>): List<String> {
        val triggers = mutableListOf<String>()
        
        // Analyze notes for common triggers
        val lowMoodEntries = moodEntries.filter { it.mood <= 4 }
        val commonWords = lowMoodEntries
            .flatMap { it.notes.split(" ") }
            .filter { it.length > 3 }
            .groupBy { it }
            .mapValues { it.value.size }
            .filter { it.value >= 2 }
        
        commonWords.forEach { (word, count) ->
            triggers.add("'$word' appears in $count low mood entries")
        }
        
        return triggers
    }

    fun getMoodImprovementSuggestions(moodEntries: List<MoodEntry>): List<String> {
        val suggestions = mutableListOf<String>()
        val avgMood = calculateAverageMood(moodEntries)
        
        when {
            avgMood < 4 -> {
                suggestions.add("Consider reaching out to a mental health professional")
                suggestions.add("Try gentle physical activity like walking")
                suggestions.add("Practice mindfulness or meditation")
                suggestions.add("Connect with supportive friends or family")
            }
            avgMood < 6 -> {
                suggestions.add("Engage in activities you enjoy")
                suggestions.add("Maintain a regular sleep schedule")
                suggestions.add("Practice stress management techniques")
                suggestions.add("Consider journaling about your feelings")
            }
            else -> {
                suggestions.add("Continue your current positive habits")
                suggestions.add("Share your positive energy with others")
                suggestions.add("Set new personal goals")
                suggestions.add("Help others who might be struggling")
            }
        }
        
        return suggestions
    }

    fun suggestActivities(moodEntry: MoodEntry): List<String> {
        val activities = mutableListOf<String>()
        
        when {
            moodEntry.mood <= 3 -> {
                activities.add("Take a gentle walk outside")
                activities.add("Listen to calming music")
                activities.add("Practice deep breathing")
                activities.add("Call a trusted friend")
            }
            moodEntry.mood <= 5 -> {
                activities.add("Do light exercise")
                activities.add("Read a book")
                activities.add("Take a warm bath")
                activities.add("Practice gratitude")
            }
            else -> {
                activities.add("Engage in creative activities")
                activities.add("Try something new")
                activities.add("Exercise or play sports")
                activities.add("Socialize with friends")
            }
        }
        
        return activities
    }

    fun getCopingStrategies(moodEntry: MoodEntry): List<String> {
        val strategies = mutableListOf<String>()
        
        if (moodEntry.stress >= 7) {
            strategies.add("Practice progressive muscle relaxation")
            strategies.add("Try the 4-7-8 breathing technique")
            strategies.add("Take breaks throughout the day")
            strategies.add("Delegate tasks when possible")
        }
        
        if (moodEntry.anxiety >= 7) {
            strategies.add("Use grounding techniques")
            strategies.add("Practice mindfulness meditation")
            strategies.add("Challenge anxious thoughts")
            strategies.add("Limit caffeine intake")
        }
        
        if (moodEntry.sleep <= 4) {
            strategies.add("Establish a bedtime routine")
            strategies.add("Avoid screens before bed")
            strategies.add("Keep bedroom cool and dark")
            strategies.add("Limit naps during the day")
        }
        
        return strategies
    }

    fun getMoodHistory(userId: String, moodEntries: List<MoodEntry>): List<MoodEntry> {
        return moodEntries.filter { it.userId == userId }
    }

    fun getMoodHistoryByDateRange(
        userId: String, 
        moodEntries: List<MoodEntry>, 
        startDate: Date, 
        endDate: Date
    ): List<MoodEntry> {
        return moodEntries.filter { 
            it.userId == userId && 
            it.date >= startDate && 
            it.date <= endDate 
        }
    }

    fun generateMoodReport(userId: String, moodEntries: List<MoodEntry>): String {
        val userEntries = getMoodHistory(userId, moodEntries)
        val avgMood = calculateAverageMood(userEntries)
        val trend = identifyMoodTrend(userEntries)
        
        return """
            Mood Report for User: $userId
            ================================
            Total Entries: ${userEntries.size}
            Average Mood: ${String.format("%.1f", avgMood)}/10
            Trend: $trend
            Date Range: ${userEntries.minByOrNull { it.date }?.date} to ${userEntries.maxByOrNull { it.date }?.date}
            
            Recent Insights:
            ${generateMoodInsights(userEntries).joinToString("\n") { "- ${it.message}" }}
        """.trimIndent()
    }

    private fun getDayName(dayOfWeek: Int): String {
        return when (dayOfWeek) {
            java.util.Calendar.SUNDAY -> "Sunday"
            java.util.Calendar.MONDAY -> "Monday"
            java.util.Calendar.TUESDAY -> "Tuesday"
            java.util.Calendar.WEDNESDAY -> "Wednesday"
            java.util.Calendar.THURSDAY -> "Thursday"
            java.util.Calendar.FRIDAY -> "Friday"
            java.util.Calendar.SATURDAY -> "Saturday"
            else -> "Unknown"
        }
    }

    private fun calculateCorrelation(x: List<Double>, y: List<Double>): Double {
        if (x.size != y.size || x.size < 2) return 0.0
        
        val n = x.size
        val sumX = x.sum()
        val sumY = y.sum()
        val sumXY = x.zip(y).sumOf { it.first * it.second }
        val sumX2 = x.sumOf { it * it }
        val sumY2 = y.sumOf { it * it }
        
        val numerator = n * sumXY - sumX * sumY
        val denominator = Math.sqrt((n * sumX2 - sumX * sumX) * (n * sumY2 - sumY * sumY))
        
        return if (denominator != 0.0) numerator / denominator else 0.0
    }
}
