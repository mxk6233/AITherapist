package com.serenityai.usecases

import com.serenityai.data.models.*
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.util.*

class CopingExercisesUseCaseTest {

    private lateinit var copingExercisesUseCase: CopingExercisesUseCase

    @BeforeEach
    fun setUp() {
        copingExercisesUseCase = CopingExercisesUseCase()
    }

    @Nested
    @DisplayName("Exercise Recommendation")
    inner class ExerciseRecommendation {

        @Test
        @DisplayName("should recommend exercises based on mood")
        fun shouldRecommendExercisesBasedOnMood() = runTest {
            val moodEntry = MoodEntry(
                id = "mood1",
                userId = "user123",
                date = Date(),
                mood = 3,
                energy = 4,
                stress = 8,
                anxiety = 7,
                sleep = 5,
                notes = "Feeling overwhelmed"
            )

            val exercises = copingExercisesUseCase.recommendExercises(moodEntry)

            assertNotNull(exercises)
            assertTrue(exercises.isNotEmpty())
            assertTrue(exercises.any { it.category == "Stress Relief" })
            assertTrue(exercises.any { it.category == "Anxiety Management" })
        }

        @Test
        @DisplayName("should recommend exercises for high stress")
        fun shouldRecommendExercisesForHighStress() = runTest {
            val moodEntry = MoodEntry(
                id = "mood1",
                userId = "user123",
                date = Date(),
                mood = 4,
                energy = 6,
                stress = 9,
                anxiety = 5,
                sleep = 6,
                notes = "Work stress"
            )

            val exercises = copingExercisesUseCase.recommendExercises(moodEntry)

            assertNotNull(exercises)
            assertTrue(exercises.isNotEmpty())
            assertTrue(exercises.any { it.category == "Stress Relief" })
            assertTrue(exercises.any { it.name.contains("breathing") || it.name.contains("relaxation") })
        }

        @Test
        @DisplayName("should recommend exercises for low energy")
        fun shouldRecommendExercisesForLowEnergy() = runTest {
            val moodEntry = MoodEntry(
                id = "mood1",
                userId = "user123",
                date = Date(),
                mood = 5,
                energy = 2,
                stress = 4,
                anxiety = 3,
                sleep = 3,
                notes = "Feeling tired"
            )

            val exercises = copingExercisesUseCase.recommendExercises(moodEntry)

            assertNotNull(exercises)
            assertTrue(exercises.isNotEmpty())
            assertTrue(exercises.any { it.category == "Energy Boost" })
            assertTrue(exercises.any { it.category == "Sleep Improvement" })
        }

        @Test
        @DisplayName("should recommend exercises for anxiety")
        fun shouldRecommendExercisesForAnxiety() = runTest {
            val moodEntry = MoodEntry(
                id = "mood1",
                userId = "user123",
                date = Date(),
                mood = 4,
                energy = 5,
                stress = 6,
                anxiety = 9,
                sleep = 4,
                notes = "Feeling anxious"
            )

            val exercises = copingExercisesUseCase.recommendExercises(moodEntry)

            assertNotNull(exercises)
            assertTrue(exercises.isNotEmpty())
            assertTrue(exercises.any { it.category == "Anxiety Management" })
            assertTrue(exercises.any { it.name.contains("grounding") || it.name.contains("mindfulness") })
        }
    }

    @Nested
    @DisplayName("Exercise Categories")
    inner class ExerciseCategories {

        @Test
        @DisplayName("should get all exercise categories")
        fun shouldGetAllExerciseCategories() = runTest {
            val categories = copingExercisesUseCase.getExerciseCategories()

            assertNotNull(categories)
            assertTrue(categories.isNotEmpty())
            assertTrue(categories.contains("Stress Relief"))
            assertTrue(categories.contains("Anxiety Management"))
            assertTrue(categories.contains("Mood Enhancement"))
            assertTrue(categories.contains("Energy Boost"))
            assertTrue(categories.contains("Sleep Improvement"))
        }

        @Test
        @DisplayName("should get exercises by category")
        fun shouldGetExercisesByCategory() = runTest {
            val category = "Stress Relief"
            val exercises = copingExercisesUseCase.getExercisesByCategory(category)

            assertNotNull(exercises)
            assertTrue(exercises.isNotEmpty())
            assertTrue(exercises.all { it.category == category })
        }

        @Test
        @DisplayName("should handle invalid category")
        fun shouldHandleInvalidCategory() = runTest {
            val category = "Invalid Category"
            val exercises = copingExercisesUseCase.getExercisesByCategory(category)

            assertNotNull(exercises)
            assertTrue(exercises.isEmpty())
        }
    }

    @Nested
    @DisplayName("Exercise Execution")
    inner class ExerciseExecution {

        @Test
        @DisplayName("should start exercise session")
        fun shouldStartExerciseSession() = runTest {
            val exercise = CopingExercise(
                id = "exercise1",
                name = "Deep Breathing",
                description = "Practice deep breathing for 5 minutes",
                category = "Stress Relief",
                duration = 5,
                difficulty = "Easy"
            )

            val session = copingExercisesUseCase.startExerciseSession("user123", exercise)

            assertNotNull(session)
            assertEquals("user123", session.userId)
            assertEquals(exercise.id, session.exerciseId)
            assertNotNull(session.startTime)
            assertNull(session.endTime)
            assertEquals(ExerciseStatus.IN_PROGRESS, session.status)
        }

        @Test
        @DisplayName("should complete exercise session")
        fun shouldCompleteExerciseSession() = runTest {
            val exercise = CopingExercise(
                id = "exercise1",
                name = "Deep Breathing",
                description = "Practice deep breathing for 5 minutes",
                category = "Stress Relief",
                duration = 5,
                difficulty = "Easy"
            )

            val session = copingExercisesUseCase.startExerciseSession("user123", exercise)
            val completedSession = copingExercisesUseCase.completeExerciseSession(session)

            assertNotNull(completedSession)
            assertEquals(ExerciseStatus.COMPLETED, completedSession.status)
            assertNotNull(completedSession.endTime)
            assertTrue(completedSession.endTime!!.time >= completedSession.startTime.time)
        }

        @Test
        @DisplayName("should track exercise progress")
        fun shouldTrackExerciseProgress() = runTest {
            val userId = "user123"
            val sessions = listOf(
                createMockExerciseSession(userId, "exercise1", ExerciseStatus.COMPLETED),
                createMockExerciseSession(userId, "exercise2", ExerciseStatus.COMPLETED),
                createMockExerciseSession(userId, "exercise3", ExerciseStatus.IN_PROGRESS)
            )

            val progress = copingExercisesUseCase.trackExerciseProgress(userId, sessions)

            assertNotNull(progress)
            assertTrue(progress.containsKey("total_completed"))
            assertTrue(progress.containsKey("completion_rate"))
            assertEquals(2, progress["total_completed"])
        }
    }

    @Nested
    @DisplayName("Personalized Recommendations")
    inner class PersonalizedRecommendations {

        @Test
        @DisplayName("should personalize recommendations based on history")
        fun shouldPersonalizeRecommendationsBasedOnHistory() = runTest {
            val userId = "user123"
            val exerciseHistory = listOf(
                createMockExerciseSession(userId, "exercise1", ExerciseStatus.COMPLETED),
                createMockExerciseSession(userId, "exercise2", ExerciseStatus.COMPLETED),
                createMockExerciseSession(userId, "exercise3", ExerciseStatus.COMPLETED)
            )

            val personalizedExercises = copingExercisesUseCase.getPersonalizedRecommendations(userId, exerciseHistory)

            assertNotNull(personalizedExercises)
            assertTrue(personalizedExercises.isNotEmpty())
        }

        @Test
        @DisplayName("should suggest new exercises")
        fun shouldSuggestNewExercises() = runTest {
            val userId = "user123"
            val completedExercises = listOf("exercise1", "exercise2")
            val newExercises = copingExercisesUseCase.suggestNewExercises(userId, completedExercises)

            assertNotNull(newExercises)
            assertTrue(newExercises.isNotEmpty())
            assertTrue(newExercises.none { it.id in completedExercises })
        }

        @Test
        @DisplayName("should recommend based on time of day")
        fun shouldRecommendBasedOnTimeOfDay() = runTest {
            val morningTime = Date()
            val eveningTime = Date(morningTime.time + 12 * 60 * 60 * 1000) // 12 hours later

            val morningExercises = copingExercisesUseCase.getTimeBasedRecommendations(morningTime)
            val eveningExercises = copingExercisesUseCase.getTimeBasedRecommendations(eveningTime)

            assertNotNull(morningExercises)
            assertNotNull(eveningExercises)
            assertTrue(morningExercises.isNotEmpty())
            assertTrue(eveningExercises.isNotEmpty())
        }
    }

    @Nested
    @DisplayName("Exercise Effectiveness")
    inner class ExerciseEffectiveness {

        @Test
        @DisplayName("should measure exercise effectiveness")
        fun shouldMeasureExerciseEffectiveness() = runTest {
            val userId = "user123"
            val sessions = listOf(
                createMockExerciseSessionWithMood(userId, "exercise1", 3, 6),
                createMockExerciseSessionWithMood(userId, "exercise2", 4, 7),
                createMockExerciseSessionWithMood(userId, "exercise3", 2, 5)
            )

            val effectiveness = copingExercisesUseCase.measureExerciseEffectiveness(userId, sessions)

            assertNotNull(effectiveness)
            assertTrue(effectiveness.containsKey("average_mood_improvement"))
            assertTrue(effectiveness.containsKey("most_effective_exercise"))
        }

        @Test
        @DisplayName("should track mood improvement")
        fun shouldTrackMoodImprovement() = runTest {
            val userId = "user123"
            val sessions = listOf(
                createMockExerciseSessionWithMood(userId, "exercise1", 3, 6),
                createMockExerciseSessionWithMood(userId, "exercise2", 4, 7),
                createMockExerciseSessionWithMood(userId, "exercise3", 2, 5)
            )

            val moodImprovement = copingExercisesUseCase.trackMoodImprovement(userId, sessions)

            assertNotNull(moodImprovement)
            assertTrue(moodImprovement.containsKey("overall_improvement"))
            assertTrue(moodImprovement.containsKey("improvement_trend"))
        }
    }

    @Nested
    @DisplayName("Exercise Scheduling")
    inner class ExerciseScheduling {

        @Test
        @DisplayName("should schedule daily exercises")
        fun shouldScheduleDailyExercises() = runTest {
            val userId = "user123"
            val schedule = copingExercisesUseCase.scheduleDailyExercises(userId)

            assertNotNull(schedule)
            assertTrue(schedule.isNotEmpty())
            assertTrue(schedule.any { it.type == "morning" })
            assertTrue(schedule.any { it.type == "evening" })
        }

        @Test
        @DisplayName("should create exercise reminders")
        fun shouldCreateExerciseReminders() = runTest {
            val userId = "user123"
            val exercise = CopingExercise(
                id = "exercise1",
                name = "Deep Breathing",
                description = "Practice deep breathing",
                category = "Stress Relief",
                duration = 5,
                difficulty = "Easy"
            )

            val reminder = copingExercisesUseCase.createExerciseReminder(userId, exercise)

            assertNotNull(reminder)
            assertEquals(userId, reminder.userId)
            assertEquals(exercise.id, reminder.exerciseId)
            assertNotNull(reminder.reminderTime)
        }

        @Test
        @DisplayName("should adjust schedule based on preferences")
        fun shouldAdjustScheduleBasedOnPreferences() = runTest {
            val userId = "user123"
            val preferences = ExercisePreferences(
                preferredTime = "morning",
                duration = 10,
                difficulty = "Easy",
                categories = listOf("Stress Relief", "Mood Enhancement")
            )

            val adjustedSchedule = copingExercisesUseCase.adjustScheduleBasedOnPreferences(userId, preferences)

            assertNotNull(adjustedSchedule)
            assertTrue(adjustedSchedule.isNotEmpty())
        }
    }

    // Helper methods
    private fun createMockExerciseSession(userId: String, exerciseId: String, status: ExerciseStatus): ExerciseSession {
        return ExerciseSession(
            sessionId = UUID.randomUUID().toString(),
            userId = userId,
            exerciseId = exerciseId,
            startTime = Date(),
            endTime = if (status == ExerciseStatus.COMPLETED) Date() else null,
            status = status,
            moodBefore = 5,
            moodAfter = 6
        )
    }

    private fun createMockExerciseSessionWithMood(userId: String, exerciseId: String, moodBefore: Int, moodAfter: Int): ExerciseSession {
        return ExerciseSession(
            sessionId = UUID.randomUUID().toString(),
            userId = userId,
            exerciseId = exerciseId,
            startTime = Date(),
            endTime = Date(),
            status = ExerciseStatus.COMPLETED,
            moodBefore = moodBefore,
            moodAfter = moodAfter
        )
    }
}

/**
 * Coping exercise data class
 */
data class CopingExercise(
    val id: String,
    val name: String,
    val description: String,
    val category: String,
    val duration: Int, // in minutes
    val difficulty: String,
    val instructions: List<String> = emptyList(),
    val benefits: List<String> = emptyList()
)

/**
 * Exercise session data class
 */
data class ExerciseSession(
    val sessionId: String,
    val userId: String,
    val exerciseId: String,
    val startTime: Date,
    val endTime: Date?,
    val status: ExerciseStatus,
    val moodBefore: Int,
    val moodAfter: Int
)

/**
 * Exercise status enum
 */
enum class ExerciseStatus {
    NOT_STARTED, IN_PROGRESS, COMPLETED, CANCELLED
}

/**
 * Exercise reminder data class
 */
data class ExerciseReminder(
    val reminderId: String,
    val userId: String,
    val exerciseId: String,
    val reminderTime: Date,
    val message: String
)

/**
 * Exercise preferences data class
 */
data class ExercisePreferences(
    val preferredTime: String,
    val duration: Int,
    val difficulty: String,
    val categories: List<String>
)

/**
 * Exercise schedule data class
 */
data class ExerciseSchedule(
    val scheduleId: String,
    val userId: String,
    val exerciseId: String,
    val scheduledTime: Date,
    val type: String, // morning, afternoon, evening
    val frequency: String // daily, weekly, etc.
)

/**
 * Use Case implementation for Coping Exercises
 */
class CopingExercisesUseCase {
    
    suspend fun recommendExercises(moodEntry: MoodEntry): List<CopingExercise> {
        val exercises = mutableListOf<CopingExercise>()
        
        // Recommend based on stress level
        if (moodEntry.stress >= 7) {
            exercises.addAll(getStressReliefExercises())
        }
        
        // Recommend based on anxiety level
        if (moodEntry.anxiety >= 7) {
            exercises.addAll(getAnxietyManagementExercises())
        }
        
        // Recommend based on energy level
        if (moodEntry.energy <= 4) {
            exercises.addAll(getEnergyBoostExercises())
        }
        
        // Recommend based on sleep quality
        if (moodEntry.sleep <= 4) {
            exercises.addAll(getSleepImprovementExercises())
        }
        
        // Recommend based on mood
        if (moodEntry.mood <= 4) {
            exercises.addAll(getMoodEnhancementExercises())
        }
        
        return exercises.distinctBy { it.id }
    }

    suspend fun getExerciseCategories(): List<String> {
        return listOf(
            "Stress Relief",
            "Anxiety Management",
            "Mood Enhancement",
            "Energy Boost",
            "Sleep Improvement"
        )
    }

    suspend fun getExercisesByCategory(category: String): List<CopingExercise> {
        return when (category) {
            "Stress Relief" -> getStressReliefExercises()
            "Anxiety Management" -> getAnxietyManagementExercises()
            "Mood Enhancement" -> getMoodEnhancementExercises()
            "Energy Boost" -> getEnergyBoostExercises()
            "Sleep Improvement" -> getSleepImprovementExercises()
            else -> emptyList()
        }
    }

    suspend fun startExerciseSession(userId: String, exercise: CopingExercise): ExerciseSession {
        return ExerciseSession(
            sessionId = UUID.randomUUID().toString(),
            userId = userId,
            exerciseId = exercise.id,
            startTime = Date(),
            endTime = null,
            status = ExerciseStatus.IN_PROGRESS,
            moodBefore = 5, // Default mood before
            moodAfter = 5   // Will be updated after completion
        )
    }

    suspend fun completeExerciseSession(session: ExerciseSession): ExerciseSession {
        return session.copy(
            endTime = Date(),
            status = ExerciseStatus.COMPLETED
        )
    }

    suspend fun trackExerciseProgress(userId: String, sessions: List<ExerciseSession>): Map<String, Any> {
        val userSessions = sessions.filter { it.userId == userId }
        val completedSessions = userSessions.filter { it.status == ExerciseStatus.COMPLETED }
        
        return mapOf(
            "total_sessions" to userSessions.size,
            "total_completed" to completedSessions.size,
            "completion_rate" to if (userSessions.isNotEmpty()) completedSessions.size.toDouble() / userSessions.size else 0.0
        )
    }

    suspend fun getPersonalizedRecommendations(userId: String, exerciseHistory: List<ExerciseSession>): List<CopingExercise> {
        val completedExercises = exerciseHistory
            .filter { it.status == ExerciseStatus.COMPLETED }
            .map { it.exerciseId }
            .distinct()
        
        return suggestNewExercises(userId, completedExercises)
    }

    suspend fun suggestNewExercises(userId: String, completedExercises: List<String>): List<CopingExercise> {
        val allExercises = getAllExercises()
        return allExercises.filter { it.id !in completedExercises }
    }

    suspend fun getTimeBasedRecommendations(time: Date): List<CopingExercise> {
        val hour = time.hours
        
        return when {
            hour in 6..11 -> getMorningExercises()
            hour in 12..17 -> getAfternoonExercises()
            hour in 18..22 -> getEveningExercises()
            else -> getRelaxationExercises()
        }
    }

    suspend fun measureExerciseEffectiveness(userId: String, sessions: List<ExerciseSession>): Map<String, Any> {
        val userSessions = sessions.filter { it.userId == userId && it.status == ExerciseStatus.COMPLETED }
        
        if (userSessions.isEmpty()) {
            return mapOf("average_mood_improvement" to 0.0, "most_effective_exercise" to "N/A")
        }
        
        val averageImprovement = userSessions.map { it.moodAfter - it.moodBefore }.average()
        val mostEffectiveExercise = userSessions
            .groupBy { it.exerciseId }
            .mapValues { (_, sessions) -> sessions.map { it.moodAfter - it.moodBefore }.average() }
            .maxByOrNull { it.value }?.key ?: "N/A"
        
        return mapOf(
            "average_mood_improvement" to averageImprovement,
            "most_effective_exercise" to mostEffectiveExercise
        )
    }

    suspend fun trackMoodImprovement(userId: String, sessions: List<ExerciseSession>): Map<String, Any> {
        val userSessions = sessions.filter { it.userId == userId && it.status == ExerciseStatus.COMPLETED }
        
        if (userSessions.isEmpty()) {
            return mapOf("overall_improvement" to 0.0, "improvement_trend" to "stable")
        }
        
        val improvements = userSessions.map { it.moodAfter - it.moodBefore }
        val overallImprovement = improvements.average()
        
        val trend = when {
            improvements.size >= 3 -> {
                val recent = improvements.takeLast(3).average()
                val older = improvements.dropLast(3).takeLast(3).average()
                when {
                    recent > older + 0.5 -> "improving"
                    recent < older - 0.5 -> "declining"
                    else -> "stable"
                }
            }
            else -> "stable"
        }
        
        return mapOf(
            "overall_improvement" to overallImprovement,
            "improvement_trend" to trend
        )
    }

    suspend fun scheduleDailyExercises(userId: String): List<ExerciseSchedule> {
        val schedules = mutableListOf<ExerciseSchedule>()
        val now = Date()
        
        // Morning exercise
        val morningTime = Date(now.time + 8 * 60 * 60 * 1000) // 8 AM
        schedules.add(ExerciseSchedule(
            scheduleId = UUID.randomUUID().toString(),
            userId = userId,
            exerciseId = "morning_exercise",
            scheduledTime = morningTime,
            type = "morning",
            frequency = "daily"
        ))
        
        // Evening exercise
        val eveningTime = Date(now.time + 20 * 60 * 60 * 1000) // 8 PM
        schedules.add(ExerciseSchedule(
            scheduleId = UUID.randomUUID().toString(),
            userId = userId,
            exerciseId = "evening_exercise",
            scheduledTime = eveningTime,
            type = "evening",
            frequency = "daily"
        ))
        
        return schedules
    }

    suspend fun createExerciseReminder(userId: String, exercise: CopingExercise): ExerciseReminder {
        val reminderTime = Date(System.currentTimeMillis() + 60 * 60 * 1000) // 1 hour from now
        
        return ExerciseReminder(
            reminderId = UUID.randomUUID().toString(),
            userId = userId,
            exerciseId = exercise.id,
            reminderTime = reminderTime,
            message = "Time for your ${exercise.name} exercise!"
        )
    }

    suspend fun adjustScheduleBasedOnPreferences(userId: String, preferences: ExercisePreferences): List<ExerciseSchedule> {
        val schedules = mutableListOf<ExerciseSchedule>()
        val now = Date()
        
        val scheduledTime = when (preferences.preferredTime) {
            "morning" -> Date(now.time + 8 * 60 * 60 * 1000)
            "afternoon" -> Date(now.time + 14 * 60 * 60 * 1000)
            "evening" -> Date(now.time + 20 * 60 * 60 * 1000)
            else -> Date(now.time + 12 * 60 * 60 * 1000)
        }
        
        schedules.add(ExerciseSchedule(
            scheduleId = UUID.randomUUID().toString(),
            userId = userId,
            exerciseId = "personalized_exercise",
            scheduledTime = scheduledTime,
            type = preferences.preferredTime,
            frequency = "daily"
        ))
        
        return schedules
    }

    private fun getStressReliefExercises(): List<CopingExercise> {
        return listOf(
            CopingExercise("stress1", "Deep Breathing", "Practice deep breathing for 5 minutes", "Stress Relief", 5, "Easy"),
            CopingExercise("stress2", "Progressive Muscle Relaxation", "Tense and release each muscle group", "Stress Relief", 10, "Medium"),
            CopingExercise("stress3", "Mindful Walking", "Take a mindful walk for 15 minutes", "Stress Relief", 15, "Easy")
        )
    }

    private fun getAnxietyManagementExercises(): List<CopingExercise> {
        return listOf(
            CopingExercise("anxiety1", "Grounding Technique", "Use 5-4-3-2-1 grounding method", "Anxiety Management", 5, "Easy"),
            CopingExercise("anxiety2", "Box Breathing", "Practice 4-4-4-4 breathing pattern", "Anxiety Management", 5, "Easy"),
            CopingExercise("anxiety3", "Mindfulness Meditation", "Practice mindfulness for 10 minutes", "Anxiety Management", 10, "Medium")
        )
    }

    private fun getMoodEnhancementExercises(): List<CopingExercise> {
        return listOf(
            CopingExercise("mood1", "Gratitude Journaling", "Write down 3 things you're grateful for", "Mood Enhancement", 10, "Easy"),
            CopingExercise("mood2", "Positive Affirmations", "Repeat positive affirmations", "Mood Enhancement", 5, "Easy"),
            CopingExercise("mood3", "Music Therapy", "Listen to uplifting music", "Mood Enhancement", 15, "Easy")
        )
    }

    private fun getEnergyBoostExercises(): List<CopingExercise> {
        return listOf(
            CopingExercise("energy1", "Quick Stretching", "Do 5 minutes of stretching", "Energy Boost", 5, "Easy"),
            CopingExercise("energy2", "Power Poses", "Practice power poses for confidence", "Energy Boost", 3, "Easy"),
            CopingExercise("energy3", "Energizing Breathing", "Practice energizing breathing exercises", "Energy Boost", 5, "Easy")
        )
    }

    private fun getSleepImprovementExercises(): List<CopingExercise> {
        return listOf(
            CopingExercise("sleep1", "Bedtime Relaxation", "Practice relaxation before bed", "Sleep Improvement", 10, "Easy"),
            CopingExercise("sleep2", "Sleep Story", "Listen to a calming sleep story", "Sleep Improvement", 20, "Easy"),
            CopingExercise("sleep3", "Body Scan", "Practice body scan meditation", "Sleep Improvement", 15, "Medium")
        )
    }

    private fun getMorningExercises(): List<CopingExercise> {
        return listOf(
            CopingExercise("morning1", "Morning Stretch", "Start your day with gentle stretching", "Energy Boost", 10, "Easy"),
            CopingExercise("morning2", "Morning Meditation", "Begin with 5 minutes of meditation", "Mood Enhancement", 5, "Easy")
        )
    }

    private fun getAfternoonExercises(): List<CopingExercise> {
        return listOf(
            CopingExercise("afternoon1", "Midday Break", "Take a 5-minute mindful break", "Stress Relief", 5, "Easy"),
            CopingExercise("afternoon2", "Energy Boost", "Quick energizing exercises", "Energy Boost", 5, "Easy")
        )
    }

    private fun getEveningExercises(): List<CopingExercise> {
        return listOf(
            CopingExercise("evening1", "Evening Reflection", "Reflect on your day", "Mood Enhancement", 10, "Easy"),
            CopingExercise("evening2", "Relaxation Routine", "Wind down with relaxation", "Sleep Improvement", 15, "Easy")
        )
    }

    private fun getRelaxationExercises(): List<CopingExercise> {
        return listOf(
            CopingExercise("relax1", "Deep Breathing", "Practice deep breathing", "Stress Relief", 5, "Easy"),
            CopingExercise("relax2", "Guided Relaxation", "Follow a guided relaxation", "Sleep Improvement", 10, "Easy")
        )
    }

    private fun getAllExercises(): List<CopingExercise> {
        return getStressReliefExercises() +
                getAnxietyManagementExercises() +
                getMoodEnhancementExercises() +
                getEnergyBoostExercises() +
                getSleepImprovementExercises()
    }
}
