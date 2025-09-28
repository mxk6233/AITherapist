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

class MoodForecastingUseCaseTest {

    private lateinit var moodForecastingUseCase: MoodForecastingUseCase

    @BeforeEach
    fun setUp() {
        moodForecastingUseCase = MoodForecastingUseCase()
    }

    @Nested
    @DisplayName("Mood Data Analysis")
    inner class MoodDataAnalysis {

        @Test
        @DisplayName("should analyze historical mood patterns")
        fun shouldAnalyzeHistoricalMoodPatterns() = runTest {
            val userId = "user123"
            val moodEntries = createMockMoodHistory(userId, 30)

            val patterns = moodForecastingUseCase.analyzeHistoricalPatterns(userId, moodEntries)

            assertNotNull(patterns)
            assertTrue(patterns.containsKey("trend"))
            assertTrue(patterns.containsKey("seasonality"))
            assertTrue(patterns.containsKey("volatility"))
        }

        @Test
        @DisplayName("should identify mood cycles")
        fun shouldIdentifyMoodCycles() = runTest {
            val userId = "user123"
            val moodEntries = createCyclicalMoodData(userId)

            val cycles = moodForecastingUseCase.identifyMoodCycles(userId, moodEntries)

            assertNotNull(cycles)
            assertTrue(cycles.containsKey("cycle_length"))
            assertTrue(cycles.containsKey("cycle_amplitude"))
            assertTrue(cycles.containsKey("cycle_phase"))
        }

        @Test
        @DisplayName("should detect mood triggers")
        fun shouldDetectMoodTriggers() = runTest {
            val userId = "user123"
            val moodEntries = createMoodWithTriggers(userId)

            val triggers = moodForecastingUseCase.detectMoodTriggers(userId, moodEntries)

            assertNotNull(triggers)
            assertTrue(triggers.isNotEmpty())
            assertTrue(triggers.any { it.contains("work") })
            assertTrue(triggers.any { it.contains("weather") })
        }
    }

    @Nested
    @DisplayName("Mood Prediction")
    inner class MoodPrediction {

        @Test
        @DisplayName("should predict short-term mood")
        fun shouldPredictShortTermMood() = runTest {
            val userId = "user123"
            val moodEntries = createMockMoodHistory(userId, 30)

            val prediction = moodForecastingUseCase.predictShortTermMood(userId, moodEntries, 7)

            assertNotNull(prediction)
            assertTrue(prediction.containsKey("predicted_mood"))
            assertTrue(prediction.containsKey("confidence"))
            assertTrue(prediction.containsKey("risk_factors"))
        }

        @Test
        @DisplayName("should predict long-term mood trends")
        fun shouldPredictLongTermMoodTrends() = runTest {
            val userId = "user123"
            val moodEntries = createMockMoodHistory(userId, 90)

            val prediction = moodForecastingUseCase.predictLongTermTrends(userId, moodEntries, 30)

            assertNotNull(prediction)
            assertTrue(prediction.containsKey("trend_direction"))
            assertTrue(prediction.containsKey("trend_strength"))
            assertTrue(prediction.containsKey("prediction_accuracy"))
        }

        @Test
        @DisplayName("should predict mood based on external factors")
        fun shouldPredictMoodBasedOnExternalFactors() = runTest {
            val userId = "user123"
            val moodEntries = createMockMoodHistory(userId, 30)
            val externalFactors = ExternalFactors(
                weather = "sunny",
                season = "spring",
                dayOfWeek = "monday",
                timeOfDay = "morning"
            )

            val prediction = moodForecastingUseCase.predictMoodWithExternalFactors(userId, moodEntries, externalFactors)

            assertNotNull(prediction)
            assertTrue(prediction.containsKey("predicted_mood"))
            assertTrue(prediction.containsKey("factor_impact"))
        }

        @ParameterizedTest
        @ValueSource(ints = [1, 3, 7, 14, 30])
        @DisplayName("should predict mood for different time horizons")
        fun shouldPredictMoodForDifferentTimeHorizons(days: Int) = runTest {
            val userId = "user123"
            val moodEntries = createMockMoodHistory(userId, 60)

            val prediction = moodForecastingUseCase.predictMoodForHorizon(userId, moodEntries, days)

            assertNotNull(prediction)
            assertTrue(prediction.containsKey("predicted_mood"))
            assertTrue(prediction.containsKey("confidence"))
        }
    }

    @Nested
    @DisplayName("Risk Assessment")
    inner class RiskAssessment {

        @Test
        @DisplayName("should assess mood decline risk")
        fun shouldAssessMoodDeclineRisk() = runTest {
            val userId = "user123"
            val moodEntries = createDecliningMoodData(userId)

            val riskAssessment = moodForecastingUseCase.assessMoodDeclineRisk(userId, moodEntries)

            assertNotNull(riskAssessment)
            assertTrue(riskAssessment.containsKey("risk_level"))
            assertTrue(riskAssessment.containsKey("risk_factors"))
            assertTrue(riskAssessment.containsKey("intervention_recommendations"))
        }

        @Test
        @DisplayName("should detect early warning signs")
        fun shouldDetectEarlyWarningSigns() = runTest {
            val userId = "user123"
            val moodEntries = createEarlyWarningMoodData(userId)

            val warningSigns = moodForecastingUseCase.detectEarlyWarningSigns(userId, moodEntries)

            assertNotNull(warningSigns)
            assertTrue(warningSigns.isNotEmpty())
            assertTrue(warningSigns.any { it.contains("declining") })
        }

        @Test
        @DisplayName("should predict crisis risk")
        fun shouldPredictCrisisRisk() = runTest {
            val userId = "user123"
            val moodEntries = createCrisisRiskMoodData(userId)

            val crisisRisk = moodForecastingUseCase.predictCrisisRisk(userId, moodEntries)

            assertNotNull(crisisRisk)
            assertTrue(crisisRisk.containsKey("crisis_probability"))
            assertTrue(crisisRisk.containsKey("time_to_crisis"))
            assertTrue(crisisRisk.containsKey("intervention_urgency"))
        }
    }

    @Nested
    @DisplayName("Intervention Recommendations")
    inner class InterventionRecommendations {

        @Test
        @DisplayName("should recommend preventive interventions")
        fun shouldRecommendPreventiveInterventions() = runTest {
            val userId = "user123"
            val moodEntries = createMockMoodHistory(userId, 30)
            val prediction = moodForecastingUseCase.predictShortTermMood(userId, moodEntries, 7)

            val interventions = moodForecastingUseCase.recommendPreventiveInterventions(userId, prediction)

            assertNotNull(interventions)
            assertTrue(interventions.isNotEmpty())
            assertTrue(interventions.any { it.category == "exercise" })
            assertTrue(interventions.any { it.category == "social" })
        }

        @Test
        @DisplayName("should recommend crisis interventions")
        fun shouldRecommendCrisisInterventions() = runTest {
            val userId = "user123"
            val moodEntries = createCrisisRiskMoodData(userId)
            val crisisRisk = moodForecastingUseCase.predictCrisisRisk(userId, moodEntries)

            val interventions = moodForecastingUseCase.recommendCrisisInterventions(userId, crisisRisk)

            assertNotNull(interventions)
            assertTrue(interventions.isNotEmpty())
            assertTrue(interventions.any { it.category == "immediate" })
            assertTrue(interventions.any { it.category == "professional" })
        }

        @Test
        @DisplayName("should personalize intervention recommendations")
        fun shouldPersonalizeInterventionRecommendations() = runTest {
            val userId = "user123"
            val userProfile = createMockUserProfile(userId)
            val moodEntries = createMockMoodHistory(userId, 30)

            val personalizedInterventions = moodForecastingUseCase.personalizeInterventions(userId, userProfile, moodEntries)

            assertNotNull(personalizedInterventions)
            assertTrue(personalizedInterventions.isNotEmpty())
        }
    }

    @Nested
    @DisplayName("Forecasting Accuracy")
    inner class ForecastingAccuracy {

        @Test
        @DisplayName("should calculate prediction accuracy")
        fun shouldCalculatePredictionAccuracy() = runTest {
            val userId = "user123"
            val historicalData = createMockMoodHistory(userId, 60)
            val actualData = createMockMoodHistory(userId, 7)

            val accuracy = moodForecastingUseCase.calculatePredictionAccuracy(userId, historicalData, actualData)

            assertNotNull(accuracy)
            assertTrue(accuracy.containsKey("accuracy_score"))
            assertTrue(accuracy.containsKey("mean_absolute_error"))
            assertTrue(accuracy.containsKey("root_mean_square_error"))
        }

        @Test
        @DisplayName("should validate prediction models")
        fun shouldValidatePredictionModels() = runTest {
            val userId = "user123"
            val moodEntries = createMockMoodHistory(userId, 90)

            val modelValidation = moodForecastingUseCase.validatePredictionModels(userId, moodEntries)

            assertNotNull(modelValidation)
            assertTrue(modelValidation.containsKey("model_performance"))
            assertTrue(modelValidation.containsKey("best_model"))
            assertTrue(modelValidation.containsKey("confidence_interval"))
        }
    }

    @Nested
    @DisplayName("Forecasting Visualization")
    inner class ForecastingVisualization {

        @Test
        @DisplayName("should generate mood forecast chart data")
        fun shouldGenerateMoodForecastChartData() = runTest {
            val userId = "user123"
            val moodEntries = createMockMoodHistory(userId, 30)

            val chartData = moodForecastingUseCase.generateForecastChartData(userId, moodEntries)

            assertNotNull(chartData)
            assertTrue(chartData.containsKey("historical_data"))
            assertTrue(chartData.containsKey("predicted_data"))
            assertTrue(chartData.containsKey("confidence_bands"))
        }

        @Test
        @DisplayName("should generate risk visualization data")
        fun shouldGenerateRiskVisualizationData() = runTest {
            val userId = "user123"
            val moodEntries = createMockMoodHistory(userId, 30)

            val riskData = moodForecastingUseCase.generateRiskVisualizationData(userId, moodEntries)

            assertNotNull(riskData)
            assertTrue(riskData.containsKey("risk_levels"))
            assertTrue(riskData.containsKey("risk_timeline"))
            assertTrue(riskData.containsKey("intervention_points"))
        }
    }

    // Helper methods
    private fun createMockMoodHistory(userId: String, days: Int): List<MoodEntry> {
        val entries = mutableListOf<MoodEntry>()
        val now = Date()
        
        for (i in 0 until days) {
            val date = Date(now.time - i * 24 * 60 * 60 * 1000L)
            entries.add(MoodEntry(
                id = UUID.randomUUID().toString(),
                userId = userId,
                date = date,
                mood = (3..8).random(),
                energy = (3..8).random(),
                stress = (2..7).random(),
                anxiety = (2..7).random(),
                sleep = (4..9).random(),
                notes = "",
                tags = emptyList(),
                activities = emptyList(),
                weather = null,
                location = null
            ))
        }
        
        return entries
    }

    private fun createCyclicalMoodData(userId: String): List<MoodEntry> {
        val entries = mutableListOf<MoodEntry>()
        val now = Date()
        
        for (i in 0..13) { // 2 weeks
            val date = Date(now.time - i * 24 * 60 * 60 * 1000L)
            val mood = 5 + 3 * Math.sin(i * Math.PI / 7).toInt() // Weekly cycle
            entries.add(MoodEntry(
                id = UUID.randomUUID().toString(),
                userId = userId,
                date = date,
                mood = mood,
                energy = mood + 1,
                stress = 8 - mood,
                anxiety = 8 - mood,
                sleep = mood + 2,
                notes = "",
                tags = emptyList(),
                activities = emptyList(),
                weather = null,
                location = null
            ))
        }
        
        return entries
    }

    private fun createMoodWithTriggers(userId: String): List<MoodEntry> {
        val entries = mutableListOf<MoodEntry>()
        val now = Date()
        
        val triggers = listOf("work stress", "sunny weather", "weekend", "exercise", "social")
        
        for (i in 0..29) {
            val date = Date(now.time - i * 24 * 60 * 60 * 1000L)
            val trigger = triggers[i % triggers.size]
            val mood = when (trigger) {
                "work stress" -> 3
                "sunny weather" -> 8
                "weekend" -> 7
                "exercise" -> 6
                "social" -> 7
                else -> 5
            }
            
            entries.add(MoodEntry(
                id = UUID.randomUUID().toString(),
                userId = userId,
                date = date,
                mood = mood,
                energy = mood + 1,
                stress = 8 - mood,
                anxiety = 8 - mood,
                sleep = mood + 2,
                notes = trigger,
                tags = emptyList(),
                activities = emptyList(),
                weather = null,
                location = null
            ))
        }
        
        return entries
    }

    private fun createDecliningMoodData(userId: String): List<MoodEntry> {
        val entries = mutableListOf<MoodEntry>()
        val now = Date()
        
        for (i in 0..14) {
            val date = Date(now.time - i * 24 * 60 * 60 * 1000L)
            val mood = 8 - i // Declining mood
            entries.add(MoodEntry(
                id = UUID.randomUUID().toString(),
                userId = userId,
                date = date,
                mood = mood,
                energy = mood - 1,
                stress = 10 - mood,
                anxiety = 10 - mood,
                sleep = mood - 2,
                notes = "feeling worse",
                tags = emptyList(),
                activities = emptyList(),
                weather = null,
                location = null
            ))
        }
        
        return entries
    }

    private fun createEarlyWarningMoodData(userId: String): List<MoodEntry> {
        val entries = mutableListOf<MoodEntry>()
        val now = Date()
        
        for (i in 0..9) {
            val date = Date(now.time - i * 24 * 60 * 60 * 1000L)
            val mood = 7 - i * 0.3 // Gradual decline
            entries.add(MoodEntry(
                id = UUID.randomUUID().toString(),
                userId = userId,
                date = date,
                mood = mood.toInt(),
                energy = mood.toInt() - 1,
                stress = 8 - mood.toInt(),
                anxiety = 8 - mood.toInt(),
                sleep = mood.toInt() - 1,
                notes = "feeling off",
                tags = emptyList(),
                activities = emptyList(),
                weather = null,
                location = null
            ))
        }
        
        return entries
    }

    private fun createCrisisRiskMoodData(userId: String): List<MoodEntry> {
        val entries = mutableListOf<MoodEntry>()
        val now = Date()
        
        for (i in 0..6) {
            val date = Date(now.time - i * 24 * 60 * 60 * 1000L)
            val mood = 2 + i * 0.5 // Very low mood
            entries.add(MoodEntry(
                id = UUID.randomUUID().toString(),
                userId = userId,
                date = date,
                mood = mood.toInt(),
                energy = 1,
                stress = 9,
                anxiety = 9,
                sleep = 2,
                notes = "crisis",
                tags = emptyList(),
                activities = emptyList(),
                weather = null,
                location = null
            ))
        }
        
        return entries
    }

    private fun createMockUserProfile(userId: String): UserProfile {
        return UserProfile(
            userId = userId,
            firstName = "John",
            lastName = "Doe",
            dateOfBirth = Date(),
            gender = "Male",
            preferences = UserPreferences(),
            profilePictureUrl = null,
            bio = null,
            createdAt = Date(),
            updatedAt = Date()
        )
    }
}

/**
 * External factors data class
 */
data class ExternalFactors(
    val weather: String,
    val season: String,
    val dayOfWeek: String,
    val timeOfDay: String,
    val socialActivity: String? = null,
    val workStress: Int? = null,
    val sleepQuality: Int? = null
)

/**
 * Intervention recommendation data class
 */
data class InterventionRecommendation(
    val category: String,
    val title: String,
    val description: String,
    val priority: Int,
    val effectiveness: Double,
    val personalized: Boolean = false
)

/**
 * Use Case implementation for AI-Powered Mood Forecasting
 */
class MoodForecastingUseCase {
    
    suspend fun analyzeHistoricalPatterns(userId: String, moodEntries: List<MoodEntry>): Map<String, Any> {
        val userEntries = moodEntries.filter { it.userId == userId }
        val patterns = mutableMapOf<String, Any>()
        
        if (userEntries.size >= 7) {
            val moods = userEntries.map { it.mood }
            
            // Calculate trend
            val trend = calculateTrend(moods)
            patterns["trend"] = trend
            
            // Calculate seasonality (weekly patterns)
            val seasonality = calculateSeasonality(userEntries)
            patterns["seasonality"] = seasonality
            
            // Calculate volatility
            val volatility = calculateVolatility(moods)
            patterns["volatility"] = volatility
        }
        
        return patterns
    }

    suspend fun identifyMoodCycles(userId: String, moodEntries: List<MoodEntry>): Map<String, Any> {
        val userEntries = moodEntries.filter { it.userId == userId }
        val cycles = mutableMapOf<String, Any>()
        
        if (userEntries.size >= 14) {
            val moods = userEntries.map { it.mood }
            
            // Detect cycle length (in days)
            val cycleLength = detectCycleLength(moods)
            cycles["cycle_length"] = cycleLength
            
            // Calculate cycle amplitude
            val amplitude = calculateCycleAmplitude(moods, cycleLength)
            cycles["cycle_amplitude"] = amplitude
            
            // Calculate current phase
            val phase = calculateCyclePhase(moods, cycleLength)
            cycles["cycle_phase"] = phase
        }
        
        return cycles
    }

    suspend fun detectMoodTriggers(userId: String, moodEntries: List<MoodEntry>): List<String> {
        val userEntries = moodEntries.filter { it.userId == userId }
        val triggers = mutableListOf<String>()
        
        // Analyze notes for common triggers
        val lowMoodEntries = userEntries.filter { it.mood <= 4 }
        val highMoodEntries = userEntries.filter { it.mood >= 7 }
        
        val lowMoodWords = lowMoodEntries.flatMap { it.notes.split(" ") }
        val highMoodWords = highMoodEntries.flatMap { it.notes.split(" ") }
        
        val lowMoodTriggers = lowMoodWords.groupBy { it.lowercase() }.mapValues { it.value.size }
        val highMoodTriggers = highMoodWords.groupBy { it.lowercase() }.mapValues { it.value.size }
        
        lowMoodTriggers.filter { it.value >= 2 }.forEach { (word, count) ->
            triggers.add("'$word' associated with low mood ($count times)")
        }
        
        highMoodTriggers.filter { it.value >= 2 }.forEach { (word, count) ->
            triggers.add("'$word' associated with high mood ($count times)")
        }
        
        return triggers
    }

    suspend fun predictShortTermMood(userId: String, moodEntries: List<MoodEntry>, days: Int): Map<String, Any> {
        val userEntries = moodEntries.filter { it.userId == userId }
        val prediction = mutableMapOf<String, Any>()
        
        if (userEntries.size >= 7) {
            val recentMoods = userEntries.takeLast(7).map { it.mood }
            val averageMood = recentMoods.average()
            val trend = calculateTrend(recentMoods)
            
            val predictedMood = averageMood + (trend * days)
            prediction["predicted_mood"] = predictedMood.coerceIn(1.0, 10.0)
            prediction["confidence"] = calculateConfidence(userEntries.size, days)
            prediction["risk_factors"] = identifyRiskFactors(userEntries)
        }
        
        return prediction
    }

    suspend fun predictLongTermTrends(userId: String, moodEntries: List<MoodEntry>, days: Int): Map<String, Any> {
        val userEntries = moodEntries.filter { it.userId == userId }
        val prediction = mutableMapOf<String, Any>()
        
        if (userEntries.size >= 30) {
            val moods = userEntries.map { it.mood }
            val trend = calculateTrend(moods)
            val seasonality = calculateSeasonality(userEntries)
            
            prediction["trend_direction"] = if (trend > 0) "improving" else if (trend < 0) "declining" else "stable"
            prediction["trend_strength"] = Math.abs(trend)
            prediction["prediction_accuracy"] = calculateLongTermAccuracy(userEntries)
        }
        
        return prediction
    }

    suspend fun predictMoodWithExternalFactors(userId: String, moodEntries: List<MoodEntry>, factors: ExternalFactors): Map<String, Any> {
        val userEntries = moodEntries.filter { it.userId == userId }
        val prediction = mutableMapOf<String, Any>()
        
        val baseMood = userEntries.takeLast(7).map { it.mood }.average()
        val factorImpact = calculateFactorImpact(factors)
        val predictedMood = baseMood + factorImpact
        
        prediction["predicted_mood"] = predictedMood.coerceIn(1.0, 10.0)
        prediction["factor_impact"] = factorImpact
        
        return prediction
    }

    suspend fun predictMoodForHorizon(userId: String, moodEntries: List<MoodEntry>, days: Int): Map<String, Any> {
        val userEntries = moodEntries.filter { it.userId == userId }
        val prediction = mutableMapOf<String, Any>()
        
        if (userEntries.size >= 7) {
            val recentMoods = userEntries.takeLast(7).map { it.mood }
            val averageMood = recentMoods.average()
            val trend = calculateTrend(recentMoods)
            
            val predictedMood = averageMood + (trend * days)
            prediction["predicted_mood"] = predictedMood.coerceIn(1.0, 10.0)
            prediction["confidence"] = calculateConfidence(userEntries.size, days)
        }
        
        return prediction
    }

    suspend fun assessMoodDeclineRisk(userId: String, moodEntries: List<MoodEntry>): Map<String, Any> {
        val userEntries = moodEntries.filter { it.userId == userId }
        val riskAssessment = mutableMapOf<String, Any>()
        
        if (userEntries.size >= 7) {
            val recentMoods = userEntries.takeLast(7).map { it.mood }
            val trend = calculateTrend(recentMoods)
            val volatility = calculateVolatility(recentMoods)
            
            val riskLevel = when {
                trend < -0.5 && volatility > 2.0 -> "high"
                trend < -0.2 && volatility > 1.5 -> "medium"
                trend < 0 -> "low"
                else -> "minimal"
            }
            
            riskAssessment["risk_level"] = riskLevel
            riskAssessment["risk_factors"] = listOf("declining trend", "high volatility")
            riskAssessment["intervention_recommendations"] = generateInterventionRecommendations(riskLevel)
        }
        
        return riskAssessment
    }

    suspend fun detectEarlyWarningSigns(userId: String, moodEntries: List<MoodEntry>): List<String> {
        val userEntries = moodEntries.filter { it.userId == userId }
        val warningSigns = mutableListOf<String>()
        
        if (userEntries.size >= 7) {
            val recentMoods = userEntries.takeLast(7).map { it.mood }
            val trend = calculateTrend(recentMoods)
            
            if (trend < -0.3) {
                warningSigns.add("Mood declining over past week")
            }
            
            if (recentMoods.any { it <= 3 }) {
                warningSigns.add("Very low mood episodes detected")
            }
            
            if (recentMoods.count { it <= 4 } >= 3) {
                warningSigns.add("Multiple low mood days in recent period")
            }
        }
        
        return warningSigns
    }

    suspend fun predictCrisisRisk(userId: String, moodEntries: List<MoodEntry>): Map<String, Any> {
        val userEntries = moodEntries.filter { it.userId == userId }
        val crisisRisk = mutableMapOf<String, Any>()
        
        if (userEntries.size >= 7) {
            val recentMoods = userEntries.takeLast(7).map { it.mood }
            val averageMood = recentMoods.average()
            val trend = calculateTrend(recentMoods)
            
            val crisisProbability = when {
                averageMood <= 2 && trend < -0.5 -> 0.9
                averageMood <= 3 && trend < -0.3 -> 0.7
                averageMood <= 4 && trend < -0.2 -> 0.5
                else -> 0.1
            }
            
            crisisRisk["crisis_probability"] = crisisProbability
            crisisRisk["time_to_crisis"] = estimateTimeToCrisis(trend, averageMood)
            crisisRisk["intervention_urgency"] = if (crisisProbability > 0.7) "immediate" else "monitor"
        }
        
        return crisisRisk
    }

    suspend fun recommendPreventiveInterventions(userId: String, prediction: Map<String, Any>): List<InterventionRecommendation> {
        val interventions = mutableListOf<InterventionRecommendation>()
        
        val predictedMood = prediction["predicted_mood"] as? Double ?: 5.0
        
        when {
            predictedMood < 4 -> {
                interventions.add(InterventionRecommendation(
                    "exercise", "Daily Exercise", "Engage in 30 minutes of physical activity", 1, 0.8
                ))
                interventions.add(InterventionRecommendation(
                    "social", "Social Connection", "Reach out to friends or family", 2, 0.7
                ))
            }
            predictedMood < 6 -> {
                interventions.add(InterventionRecommendation(
                    "mindfulness", "Mindfulness Practice", "Practice meditation or deep breathing", 2, 0.6
                ))
                interventions.add(InterventionRecommendation(
                    "sleep", "Sleep Hygiene", "Maintain regular sleep schedule", 3, 0.5
                ))
            }
        }
        
        return interventions
    }

    suspend fun recommendCrisisInterventions(userId: String, crisisRisk: Map<String, Any>): List<InterventionRecommendation> {
        val interventions = mutableListOf<InterventionRecommendation>()
        
        val crisisProbability = crisisRisk["crisis_probability"] as? Double ?: 0.0
        
        when {
            crisisProbability > 0.7 -> {
                interventions.add(InterventionRecommendation(
                    "immediate", "Emergency Contact", "Contact crisis hotline or emergency services", 1, 1.0
                ))
                interventions.add(InterventionRecommendation(
                    "professional", "Professional Help", "Schedule immediate appointment with therapist", 1, 0.9
                ))
            }
            crisisProbability > 0.5 -> {
                interventions.add(InterventionRecommendation(
                    "professional", "Therapy Session", "Schedule therapy session within 24 hours", 1, 0.8
                ))
                interventions.add(InterventionRecommendation(
                    "support", "Support Network", "Contact trusted friend or family member", 2, 0.7
                ))
            }
        }
        
        return interventions
    }

    suspend fun personalizeInterventions(userId: String, userProfile: UserProfile, moodEntries: List<MoodEntry>): List<InterventionRecommendation> {
        val interventions = mutableListOf<InterventionRecommendation>()
        
        // Base interventions
        interventions.addAll(recommendPreventiveInterventions(userId, mapOf("predicted_mood" to 5.0)))
        
        // Personalize based on user profile
        if (userProfile.preferences.notificationsEnabled) {
            interventions.add(InterventionRecommendation(
                "reminder", "Daily Check-in", "Set daily mood check-in reminders", 3, 0.6, true
            ))
        }
        
        return interventions
    }

    suspend fun calculatePredictionAccuracy(userId: String, historicalData: List<MoodEntry>, actualData: List<MoodEntry>): Map<String, Any> {
        val accuracy = mutableMapOf<String, Any>()
        
        if (historicalData.size >= 7 && actualData.size >= 7) {
            val historicalMoods = historicalData.takeLast(7).map { it.mood }
            val actualMoods = actualData.takeLast(7).map { it.mood }
            
            val mae = calculateMAE(historicalMoods, actualMoods)
            val rmse = calculateRMSE(historicalMoods, actualMoods)
            val accuracyScore = 1.0 - (mae / 10.0) // Normalize by mood scale (1-10)
            
            accuracy["accuracy_score"] = accuracyScore.coerceIn(0.0, 1.0)
            accuracy["mean_absolute_error"] = mae
            accuracy["root_mean_square_error"] = rmse
        }
        
        return accuracy
    }

    suspend fun validatePredictionModels(userId: String, moodEntries: List<MoodEntry>): Map<String, Any> {
        val validation = mutableMapOf<String, Any>()
        
        if (moodEntries.size >= 30) {
            val moods = moodEntries.map { it.mood }
            
            // Simple model validation
            val trendModel = calculateTrend(moods)
            val seasonalModel = calculateSeasonality(moodEntries)
            
            validation["model_performance"] = mapOf(
                "trend_model" to Math.abs(trendModel),
                "seasonal_model" to seasonalModel
            )
            validation["best_model"] = if (Math.abs(trendModel) > seasonalModel) "trend" else "seasonal"
            validation["confidence_interval"] = 0.7 // Placeholder
        }
        
        return validation
    }

    suspend fun generateForecastChartData(userId: String, moodEntries: List<MoodEntry>): Map<String, Any> {
        val chartData = mutableMapOf<String, Any>()
        
        val userEntries = moodEntries.filter { it.userId == userId }
        if (userEntries.size >= 7) {
            val historicalData = userEntries.map { entry ->
                mapOf("date" to entry.date, "mood" to entry.mood)
            }
            
            val predictedData = (0..6).map { i ->
                val date = Date(System.currentTimeMillis() + i * 24 * 60 * 60 * 1000L)
                val predictedMood = 5.0 + i * 0.1 // Simple prediction
                mapOf("date" to date, "mood" to predictedMood)
            }
            
            val confidenceBands = (0..6).map { i ->
                val date = Date(System.currentTimeMillis() + i * 24 * 60 * 60 * 1000L)
                mapOf("date" to date, "upper" to 7.0, "lower" to 3.0)
            }
            
            chartData["historical_data"] = historicalData
            chartData["predicted_data"] = predictedData
            chartData["confidence_bands"] = confidenceBands
        }
        
        return chartData
    }

    suspend fun generateRiskVisualizationData(userId: String, moodEntries: List<MoodEntry>): Map<String, Any> {
        val riskData = mutableMapOf<String, Any>()
        
        val userEntries = moodEntries.filter { it.userId == userId }
        if (userEntries.size >= 7) {
            val riskLevels = userEntries.map { entry ->
                val risk = when {
                    entry.mood <= 3 -> "high"
                    entry.mood <= 5 -> "medium"
                    else -> "low"
                }
                mapOf("date" to entry.date, "risk" to risk)
            }
            
            val riskTimeline = (0..6).map { i ->
                val date = Date(System.currentTimeMillis() + i * 24 * 60 * 60 * 1000L)
                val risk = if (i < 3) "medium" else "high"
                mapOf("date" to date, "risk" to risk)
            }
            
            val interventionPoints = listOf(
                mapOf("date" to Date(), "type" to "check-in"),
                mapOf("date" to Date(System.currentTimeMillis() + 3 * 24 * 60 * 60 * 1000L), "type" to "intervention")
            )
            
            riskData["risk_levels"] = riskLevels
            riskData["risk_timeline"] = riskTimeline
            riskData["intervention_points"] = interventionPoints
        }
        
        return riskData
    }

    // Helper methods
    private fun calculateTrend(moods: List<Int>): Double {
        if (moods.size < 2) return 0.0
        
        val n = moods.size
        val sumX = (0 until n).sum()
        val sumY = moods.sum()
        val sumXY = (0 until n).zip(moods).sumOf { it.first * it.second }
        val sumX2 = (0 until n).sumOf { it * it }
        
        return (n * sumXY - sumX * sumY).toDouble() / (n * sumX2 - sumX * sumX)
    }

    private fun calculateSeasonality(moodEntries: List<MoodEntry>): Double {
        val dayOfWeekMoods = moodEntries.groupBy { 
            java.util.Calendar.getInstance().apply { time = it.date }.get(java.util.Calendar.DAY_OF_WEEK)
        }
        
        val dayAverages = dayOfWeekMoods.mapValues { (_, entries) -> entries.map { it.mood }.average() }
        val overallAverage = moodEntries.map { it.mood }.average()
        
        return dayAverages.values.map { Math.abs(it - overallAverage) }.average()
    }

    private fun calculateVolatility(moods: List<Int>): Double {
        if (moods.size < 2) return 0.0
        
        val mean = moods.average()
        val variance = moods.map { (it - mean) * (it - mean) }.average()
        return Math.sqrt(variance)
    }

    private fun detectCycleLength(moods: List<Int>): Int {
        // Simple cycle detection - look for repeating patterns
        for (cycleLength in 3..7) {
            if (moods.size >= cycleLength * 2) {
                val firstCycle = moods.take(cycleLength)
                val secondCycle = moods.drop(cycleLength).take(cycleLength)
                val correlation = calculateCorrelation(firstCycle.map { it.toDouble() }, secondCycle.map { it.toDouble() })
                if (correlation > 0.7) {
                    return cycleLength
                }
            }
        }
        return 7 // Default to weekly cycle
    }

    private fun calculateCycleAmplitude(moods: List<Int>, cycleLength: Int): Double {
        if (moods.size < cycleLength) return 0.0
        
        val cycleMoods = moods.take(cycleLength)
        return cycleMoods.maxOrNull()!! - cycleMoods.minOrNull()!!
    }

    private fun calculateCyclePhase(moods: List<Int>, cycleLength: Int): Int {
        if (moods.size < cycleLength) return 0
        
        val currentMood = moods.last()
        val cycleMoods = moods.takeLast(cycleLength)
        return cycleMoods.indexOf(currentMood)
    }

    private fun calculateConfidence(dataSize: Int, predictionDays: Int): Double {
        val baseConfidence = 0.8
        val dataFactor = Math.min(dataSize / 30.0, 1.0)
        val timeFactor = Math.max(1.0 - predictionDays / 30.0, 0.3)
        return baseConfidence * dataFactor * timeFactor
    }

    private fun identifyRiskFactors(moodEntries: List<MoodEntry>): List<String> {
        val riskFactors = mutableListOf<String>()
        
        val recentMoods = moodEntries.takeLast(7).map { it.mood }
        if (recentMoods.any { it <= 3 }) {
            riskFactors.add("Very low mood episodes")
        }
        
        if (recentMoods.count { it <= 4 } >= 3) {
            riskFactors.add("Multiple low mood days")
        }
        
        val trend = calculateTrend(recentMoods)
        if (trend < -0.3) {
            riskFactors.add("Declining mood trend")
        }
        
        return riskFactors
    }

    private fun calculateFactorImpact(factors: ExternalFactors): Double {
        var impact = 0.0
        
        when (factors.weather) {
            "sunny" -> impact += 0.5
            "rainy" -> impact -= 0.3
            "cloudy" -> impact -= 0.1
        }
        
        when (factors.season) {
            "spring" -> impact += 0.3
            "summer" -> impact += 0.2
            "autumn" -> impact -= 0.1
            "winter" -> impact -= 0.4
        }
        
        when (factors.dayOfWeek) {
            "monday" -> impact -= 0.2
            "friday" -> impact += 0.3
            "saturday", "sunday" -> impact += 0.4
        }
        
        return impact
    }

    private fun generateInterventionRecommendations(riskLevel: String): List<String> {
        return when (riskLevel) {
            "high" -> listOf("Immediate professional help", "Crisis hotline contact", "Safety planning")
            "medium" -> listOf("Therapy session", "Support network contact", "Self-care activities")
            "low" -> listOf("Mood monitoring", "Preventive activities", "Regular check-ins")
            else -> listOf("Continue current routine", "Regular monitoring")
        }
    }

    private fun estimateTimeToCrisis(trend: Double, currentMood: Double): String {
        val daysToCrisis = (currentMood - 2) / Math.abs(trend)
        return when {
            daysToCrisis <= 1 -> "immediate"
            daysToCrisis <= 3 -> "within 3 days"
            daysToCrisis <= 7 -> "within a week"
            else -> "more than a week"
        }
    }

    private fun calculateMAE(predicted: List<Int>, actual: List<Int>): Double {
        return predicted.zip(actual).map { Math.abs(it.first - it.second) }.average()
    }

    private fun calculateRMSE(predicted: List<Int>, actual: List<Int>): Double {
        val mse = predicted.zip(actual).map { (it.first - it.second) * (it.first - it.second) }.average()
        return Math.sqrt(mse)
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

    private fun calculateLongTermAccuracy(moodEntries: List<MoodEntry>): Double {
        // Simple accuracy calculation based on data consistency
        val moods = moodEntries.map { it.mood }
        val volatility = calculateVolatility(moods)
        return Math.max(0.5, 1.0 - volatility / 10.0)
    }
}
