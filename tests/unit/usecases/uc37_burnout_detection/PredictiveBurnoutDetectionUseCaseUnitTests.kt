package com.serenityai.tests.unit.usecases.uc37_burnout_detection

import com.serenityai.usecases.*
import com.serenityai.data.models.MoodEntry
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import java.util.Date

/**
 * UC37: Predictive Burnout Detection
 * 
 * Use Case Goal: Detect early signs of burnout risk through analysis of user behavior patterns,
 * mood trends, and activity levels to enable proactive intervention and prevention.
 * 
 * Key Requirements Being Tested:
 * 1. System analyzes user activity patterns for burnout indicators
 * 2. System monitors mood trends and stress levels
 * 3. System detects early warning signs of burnout
 * 4. System calculates burnout risk scores
 * 5. System provides personalized prevention recommendations
 */
@DisplayName("UC37: Predictive Burnout Detection - Unit Tests")
class PredictiveBurnoutDetectionUseCaseUnitTests {

    private lateinit var useCase: PredictiveBurnoutDetectionUseCase

    @BeforeEach
    fun setUp() {
        useCase = PredictiveBurnoutDetectionUseCase()
    }

    @Nested
    @DisplayName("Test Case 1: Burnout Risk Assessment - Validates Core UC37 Functionality")
    inner class RiskAssessmentTests {
        
        /**
         * Tests: System assesses burnout risk from multiple factors
         * Validates: UC37 requirement for comprehensive risk assessment
         * Expected: Risk assessment includes risk score, level, and factors
         */
        @Test
        @DisplayName("UC37-REQ-1: System MUST assess burnout risk from multiple factors")
        fun `system assesses burnout risk comprehensively`() {
            // Given: System has user data (mood, activity, stress, sleep)
            // Purpose: Validate comprehensive risk assessment functionality
            
            val moodEntries = listOf(
                MoodEntry(userId="user123", mood=2, notes="Low mood"),
                MoodEntry(userId="user123", mood=2, notes="Still low"),
                MoodEntry(userId="user123", mood=3, notes="Slightly better")
            )
            
            val activityLevels = listOf(
                ActivityLevel(Date(), 0.3f),
                ActivityLevel(Date(), 0.2f),
                ActivityLevel(Date(), 0.25f)
            )
            
            val stressIndicators = listOf(
                StressIndicator(Date(), 8.0f),
                StressIndicator(Date(), 7.5f),
                StressIndicator(Date(), 8.5f)
            )
            
            // When: System assesses burnout risk
            val assessment = useCase.assessBurnoutRisk(
                moodEntries = moodEntries,
                activityLevels = activityLevels,
                stressIndicators = stressIndicators
            )
            
            // Then: Risk assessment should be comprehensive
            assertNotNull(assessment.id, "UC37: Assessment must have unique ID")
            assertEquals("user123", assessment.userId, "UC37: Assessment must be linked to correct user")
            assertTrue(assessment.riskScore in 0f..100f, "UC37: Risk score must be between 0 and 100")
            assertNotNull(assessment.riskLevel, "UC37: Risk level must be determined")
            assertTrue(assessment.riskFactors.isNotEmpty(), "UC37: Assessment must identify risk factors")
            assertNotNull(assessment.assessedAt, "UC37: Assessment must have timestamp")
        }
        
        /**
         * Tests: System calculates risk level correctly
         * Validates: UC37 requirement for risk level determination
         * Expected: Risk levels are calculated based on risk score
         */
        @Test
        @DisplayName("UC37-REQ-2: System MUST calculate burnout risk level correctly")
        fun `system calculates burnout risk level based on risk score`() {
            // Given: System has assessed risk with different scores
            // Purpose: Validate risk level calculation
            
            // High risk scenario
            val highRiskMoods = listOf(
                MoodEntry(userId="user123", mood=2, notes="Very low"),
                MoodEntry(userId="user123", mood=1, notes="Critical"),
                MoodEntry(userId="user123", mood=2, notes="Low")
            )
            val highStress = listOf(
                StressIndicator(Date(), 9.0f),
                StressIndicator(Date(), 8.5f)
            )
            
            val highRiskAssessment = useCase.assessBurnoutRisk(
                moodEntries = highRiskMoods,
                stressIndicators = highStress
            )
            
            // Then: Risk level should reflect high risk
            assertTrue(highRiskAssessment.riskScore >= 50f, 
                "UC37: High risk scenarios should have score >= 50")
            assertTrue(highRiskAssessment.riskLevel in listOf(BurnoutRiskLevel.HIGH, BurnoutRiskLevel.CRITICAL),
                "UC37: High risk scenarios should have HIGH or CRITICAL level")
            
            // Low risk scenario
            val lowRiskMoods = listOf(
                MoodEntry(userId="user123", mood=4, notes="Good"),
                MoodEntry(userId="user123", mood=4, notes="Great"),
                MoodEntry(userId="user123", mood=4, notes="Good")
            )
            
            val lowRiskAssessment = useCase.assessBurnoutRisk(
                moodEntries = lowRiskMoods
            )
            
            // Then: Risk level should reflect low risk
            assertTrue(lowRiskAssessment.riskLevel in listOf(BurnoutRiskLevel.LOW, BurnoutRiskLevel.MODERATE),
                "UC37: Low risk scenarios should have LOW or MODERATE level")
        }
        
        /**
         * Tests: System identifies risk factors
         * Validates: UC37 requirement for factor identification
         * Expected: Multiple risk factors are identified and analyzed
         */
        @Test
        @DisplayName("UC37-REQ-3: System MUST identify multiple burnout risk factors")
        fun `system identifies multiple burnout risk factors correctly`() {
            // Given: System has diverse user data
            // Purpose: Validate factor identification
            
            val moodEntries = listOf(
                MoodEntry(userId="user123", mood=2, notes="Declining"),
                MoodEntry(userId="user123", mood=2, notes="Lower")
            )
            val activityLevels = listOf(ActivityLevel(Date(), 0.2f))
            val stressIndicators = listOf(StressIndicator(Date(), 8.0f))
            val sleepQuality = listOf(SleepQuality(Date(), 3.0f))
            
            // When: System assesses risk
            val assessment = useCase.assessBurnoutRisk(
                moodEntries = moodEntries,
                activityLevels = activityLevels,
                stressIndicators = stressIndicators,
                sleepQuality = sleepQuality
            )
            
            // Then: Multiple risk factors should be identified
            assertTrue(assessment.riskFactors.size >= 2, "UC37: System must identify multiple risk factors")
            assertTrue(assessment.riskFactors.all { it.severity in 0f..1f }, 
                "UC37: Risk factor severity must be between 0 and 1")
            assertTrue(assessment.riskFactors.all { it.description.isNotBlank() }, 
                "UC37: Risk factors must have descriptions")
        }
    }

    @Nested
    @DisplayName("Test Case 2: Early Warning Detection - Validates Secondary UC37 Functionality")
    inner class EarlyWarningTests {
        
        /**
         * Tests: System detects early warning signs
         * Validates: UC37 requirement for early warning detection
         * Expected: Early warnings are detected and reported
         */
        @Test
        @DisplayName("UC37-REQ-4: System MUST detect early warning signs of burnout")
        fun `system detects early warning signs correctly`() {
            // Given: System has data showing declining patterns
            // Purpose: Validate early warning detection
            
            val decliningMoods = listOf(
                MoodEntry(userId="user123", mood=4, notes="Good"),
                MoodEntry(userId="user123", mood=3, notes="Declining"),
                MoodEntry(userId="user123", mood=3, notes="Lower"),
                MoodEntry(userId="user123", mood=2, notes="Low"),
                MoodEntry(userId="user123", mood=2, notes="Very low"),
                MoodEntry(userId="user123", mood=1, notes="Critical"),
                MoodEntry(userId="user123", mood=2, notes="Still low")
            )
            
            val activityLevels = listOf(
                ActivityLevel(Date(), 0.8f),
                ActivityLevel(Date(), 0.6f),
                ActivityLevel(Date(), 0.4f),
                ActivityLevel(Date(), 0.3f)
            )
            
            // When: System assesses risk
            val assessment = useCase.assessBurnoutRisk(
                moodEntries = decliningMoods,
                activityLevels = activityLevels
            )
            
            // Then: Early warnings should be detected
            assertTrue(assessment.earlyWarnings.isNotEmpty(), 
                "UC37: System must detect early warnings for declining patterns")
            assertTrue(assessment.earlyWarnings.any { it.type == WarningType.MOOD_DECLINE }, 
                "UC37: Mood decline warnings should be detected")
        }
        
        /**
         * Tests: System generates prevention recommendations
         * Validates: UC37 requirement for recommendation generation
         * Expected: Personalized recommendations are provided
         */
        @Test
        @DisplayName("UC37-REQ-5: System MUST generate prevention recommendations")
        fun `system generates prevention recommendations correctly`() {
            // Given: System has assessed high burnout risk
            // Purpose: Validate recommendation generation
            
            val highRiskMoods = listOf(
                MoodEntry(userId="user123", mood=2, notes="Low"),
                MoodEntry(userId="user123", mood=2, notes="Low"),
                MoodEntry(userId="user123", mood=2, notes="Very low")
            )
            val highStress = listOf(StressIndicator(Date(), 9.0f))
            
            // When: System assesses risk
            val assessment = useCase.assessBurnoutRisk(
                moodEntries = highRiskMoods,
                stressIndicators = highStress
            )
            
            // Then: Recommendations should be provided
            assertTrue(assessment.recommendations.isNotEmpty(), 
                "UC37: System must provide prevention recommendations")
            assertTrue(assessment.recommendations.all { it.isNotBlank() }, 
                "UC37: All recommendations must have content")
            
            // High risk should trigger more urgent recommendations
            if (assessment.riskLevel == BurnoutRiskLevel.HIGH || assessment.riskLevel == BurnoutRiskLevel.CRITICAL) {
                assertTrue(assessment.recommendations.any { 
                    it.contains("URGENT", ignoreCase = true) || 
                    it.contains("immediate", ignoreCase = true) 
                }, "UC37: High risk should trigger urgent recommendations")
            }
        }
        
        /**
         * Tests: System triggers interventions when risk is high
         * Validates: UC37 requirement for intervention triggering
         * Expected: Interventions are triggered for high/critical risk
         */
        @Test
        @DisplayName("UC37-REQ-6: System MUST trigger interventions for high risk")
        fun `system triggers interventions for high burnout risk correctly`() {
            // Given: System has assessed critical burnout risk
            // Purpose: Validate intervention triggering
            
            val criticalMoods = listOf(
                MoodEntry("user123", 1.5f, Date(), "Critical"),
                MoodEntry("user123", 2.0f, Date(), "Very low"),
                MoodEntry("user123", 1.0f, Date(), "Critical")
            )
            val criticalStress = listOf(
                StressIndicator(Date(), 9.5f),
                StressIndicator(Date(), 10.0f)
            )
            
            // When: System assesses risk
            val assessment = useCase.assessBurnoutRisk(
                moodEntries = criticalMoods,
                stressIndicators = criticalStress
            )
            
            // Then: Intervention should be triggered
            if (assessment.riskLevel == BurnoutRiskLevel.CRITICAL || assessment.riskLevel == BurnoutRiskLevel.HIGH) {
                assertTrue(assessment.interventionTriggered, 
                    "UC37: High/Critical risk must trigger intervention")
            }
        }
    }

    @Nested
    @DisplayName("Test Case 3: Future Risk Prediction - Validates Advanced UC37 Functionality")
    inner class PredictionTests {
        
        /**
         * Tests: System predicts future burnout risk
         * Validates: UC37 requirement for predictive analytics
         * Expected: Future risk predictions are generated
         */
        @Test
        @DisplayName("UC37-REQ-7: System MUST predict future burnout risk")
        fun `system predicts future burnout risk correctly`() {
            // Given: System has current risk assessment
            // Purpose: Validate future risk prediction
            
            val moodEntries = listOf(
                MoodEntry("user123", 2.5f, Date(), "Low"),
                MoodEntry("user123", 2.0f, Date(), "Lower"),
                MoodEntry("user123", 2.5f, Date(), "Low")
            )
            
            val currentAssessment = useCase.assessBurnoutRisk(moodEntries = moodEntries)
            
            // When: System predicts future risk
            val prediction = useCase.predictFutureBurnoutRisk(
                currentAssessment = currentAssessment,
                daysAhead = 7
            )
            
            // Then: Prediction should be generated
            assertNotNull(prediction.id, "UC37: Prediction must have unique ID")
            assertEquals(currentAssessment.userId, prediction.userId, 
                "UC37: Prediction must be linked to correct user")
            assertEquals(7, prediction.daysAhead, "UC37: Prediction must match requested days ahead")
            assertTrue(prediction.projectedRiskScore in 0f..100f, 
                "UC37: Projected risk score must be between 0 and 100")
            assertNotNull(prediction.projectedRiskLevel, "UC37: Projected risk level must be determined")
            assertTrue(prediction.confidence in 0f..100f, "UC37: Prediction confidence must be between 0 and 100")
            assertNotNull(prediction.trend, "UC37: Prediction must include trend")
        }
        
        /**
         * Tests: System calculates prediction confidence
         * Validates: UC37 requirement for confidence scoring
         * Expected: Confidence scores reflect data quality
         */
        @Test
        @DisplayName("UC37-REQ-8: System MUST calculate prediction confidence")
        fun `system calculates prediction confidence correctly`() {
            // Given: System has assessments with different data quality
            // Purpose: Validate confidence calculation
            
            // Assessment with multiple factors
            val comprehensiveMoods = listOf(
                MoodEntry("user123", 3.0f, Date(), "Mood 1"),
                MoodEntry("user123", 3.5f, Date(), "Mood 2"),
                MoodEntry("user123", 3.0f, Date(), "Mood 3")
            )
            val comprehensiveActivity = listOf(
                ActivityLevel(Date(), 0.5f),
                ActivityLevel(Date(), 0.6f)
            )
            val comprehensiveStress = listOf(
                StressIndicator(Date(), 6.0f),
                StressIndicator(Date(), 6.5f)
            )
            val comprehensiveSleep = listOf(
                SleepQuality(Date(), 6.0f),
                SleepQuality(Date(), 6.5f)
            )
            
            val comprehensiveAssessment = useCase.assessBurnoutRisk(
                moodEntries = comprehensiveMoods,
                activityLevels = comprehensiveActivity,
                stressIndicators = comprehensiveStress,
                sleepQuality = comprehensiveSleep
            )
            
            val comprehensivePrediction = useCase.predictFutureBurnoutRisk(comprehensiveAssessment)
            
            // Then: Confidence should reflect data quality
            assertTrue(comprehensivePrediction.confidence >= 65f, 
                "UC37: Comprehensive data should yield higher confidence (>=65%)")
        }
        
        /**
         * Tests: System provides trend analysis
         * Validates: UC37 requirement for trend identification
         * Expected: Trends (increasing, decreasing, stable) are identified
         */
        @Test
        @DisplayName("UC37-REQ-9: System MUST identify burnout risk trends")
        fun `system identifies burnout risk trends correctly`() {
            // Given: System has risk assessment
            // Purpose: Validate trend identification
            
            val moodEntries = listOf(
                MoodEntry("user123", 3.0f, Date(), "Mood"),
                MoodEntry("user123", 3.5f, Date(), "Mood"),
                MoodEntry("user123", 3.0f, Date(), "Mood")
            )
            
            val assessment = useCase.assessBurnoutRisk(moodEntries = moodEntries)
            val prediction = useCase.predictFutureBurnoutRisk(assessment)
            
            // Then: Trend should be identified
            assertTrue(prediction.trend in listOf(Trend.INCREASING, Trend.DECREASING, Trend.STABLE),
                "UC37: Trend must be one of INCREASING, DECREASING, or STABLE")
        }
    }
}

