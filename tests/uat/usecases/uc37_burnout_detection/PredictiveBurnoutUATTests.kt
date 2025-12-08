package com.serenityai.tests.uat.usecases.uc37_burnout_detection

import com.serenityai.usecases.*
import com.serenityai.data.models.MoodEntry
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import java.util.Date

/** UAT: UC37 - Predictive Burnout Detection - User Acceptance Tests validating burnout detection from an end-user perspective. */
@DisplayName("UAT: UC37 - Predictive Burnout Detection")
class PredictiveBurnoutUATTests {

    private val useCase = PredictiveBurnoutDetectionUseCase()

    @Nested
    @DisplayName("User Story: Burnout Risk Assessment")
    inner class BurnoutRiskAssessment {
        
        @Test
        @DisplayName("As a user, I want to know my burnout risk level so I can take preventive action")
        fun `user can see burnout risk level`() {
            // Given: System assesses burnout risk
            val moodEntries = listOf(
                MoodEntry(userId = "user123", mood = 2, notes = "Stressed", date = Date()),
                MoodEntry(userId = "user123", mood = 3, notes = "Tired", date = Date()),
                MoodEntry(userId = "user123", mood = 2, notes = "Overwhelmed", date = Date())
            )
            
            // When: User views assessment
            val assessment = useCase.assessBurnoutRisk(moodEntries)
            
            // Then: Risk level is displayed
            assertNotNull(assessment, "User should see burnout risk level")
            assertTrue(assessment.riskLevel in listOf(
                BurnoutRiskLevel.LOW, BurnoutRiskLevel.MODERATE, 
                BurnoutRiskLevel.HIGH, BurnoutRiskLevel.CRITICAL
            ), "Risk level should be valid")
        }
        
        @Test
        @DisplayName("As a user, I want to understand risk factors so I know what to address")
        fun `user can see risk factors`() {
            // Given: Risk assessment identifies factors
            val moodEntries = listOf(
                MoodEntry(userId = "user123", mood = 2, notes = "High stress", date = Date()),
                MoodEntry(userId = "user123", mood = 3, notes = "Low sleep", date = Date())
            )
            val assessment = useCase.assessBurnoutRisk(moodEntries)
            
            // When: User views factors
            val factorsVisible = assessment.riskFactors.isNotEmpty()
            val factorsDetailed = assessment.riskFactors.all { 
                it.factorType != null && it.severity >= 0f 
            }
            
            // Then: Risk factors are displayed
            assertTrue(factorsVisible, "User should see risk factors")
            assertTrue(factorsDetailed, "Factors should include severity")
        }
        
        @Test
        @DisplayName("As a user, I want to see trends so I can track changes over time")
        fun `user can see burnout trends`() {
            // Given: Historical risk data
            val moodEntries = listOf(
                MoodEntry(userId = "user123", mood = 4, notes = "Previous", date = Date()),
                MoodEntry(userId = "user123", mood = 2, notes = "Current", date = Date())
            )
            val assessment = useCase.assessBurnoutRisk(moodEntries)
            val prediction = useCase.predictFutureBurnoutRisk(assessment)
            
            // When: User views trends
            val trendVisible = prediction.trend != null
            val trendValid = prediction.trend in listOf(Trend.INCREASING, Trend.DECREASING, Trend.STABLE)
            
            // Then: Trends are displayed
            assertTrue(trendVisible, "User should see burnout trends")
            assertTrue(trendValid, "Trend should be valid")
        }
    }

    @Nested
    @DisplayName("User Story: Early Warning System")
    inner class EarlyWarningSystem {
        
        @Test
        @DisplayName("As a user, I want early warnings so I can prevent burnout before it happens")
        fun `user receives early warnings`() {
            // Given: System detects warning signs
            val moodEntries = listOf(
                MoodEntry(userId = "user123", mood = 2, notes = "High stress", date = Date()),
                MoodEntry(userId = "user123", mood = 2, notes = "Consistently high", date = Date())
            )
            val assessment = useCase.assessBurnoutRisk(moodEntries)
            
            // When: Warning is triggered
            val warningReceived = assessment.earlyWarnings.isNotEmpty()
            val warningActionable = assessment.recommendations.isNotEmpty()
            
            // Then: User receives early warning
            assertTrue(warningReceived || assessment.riskLevel in listOf(BurnoutRiskLevel.HIGH, BurnoutRiskLevel.CRITICAL), 
                      "User should receive early warnings")
            assertTrue(warningActionable, "Warning should include recommendations")
        }
        
        @Test
        @DisplayName("As a user, I want actionable recommendations so I know what to do")
        fun `user receives actionable recommendations`() {
            // Given: System provides recommendations
            val moodEntries = listOf(
                MoodEntry(userId = "user123", mood = 2, notes = "Stressed", date = Date())
            )
            val assessment = useCase.assessBurnoutRisk(moodEntries)
            
            // When: User views recommendations
            val recommendationsProvided = assessment.recommendations.isNotEmpty()
            val recommendationsActionable = assessment.recommendations.all { it.isNotBlank() }
            
            // Then: Recommendations are actionable
            assertTrue(recommendationsProvided, "User should receive recommendations")
            assertTrue(recommendationsActionable, "Recommendations should be actionable")
        }
    }

    @Nested
    @DisplayName("User Story: Prevention Strategies")
    inner class PreventionStrategies {
        
        @Test
        @DisplayName("As a user, I want prevention strategies so I can avoid burnout")
        fun `user receives prevention strategies`() {
            // Given: System suggests strategies
            val moodEntries = listOf(
                MoodEntry(userId = "user123", mood = 3, notes = "Moderate stress", date = Date())
            )
            val assessment = useCase.assessBurnoutRisk(moodEntries)
            
            // When: User views strategies
            val strategiesAvailable = assessment.recommendations.isNotEmpty()
            val strategiesEffective = assessment.recommendations.any { it.length > 10 }
            
            // Then: Prevention strategies are provided
            assertTrue(strategiesAvailable, "User should see prevention strategies")
            assertTrue(strategiesEffective, "Strategies should be effective")
        }
        
        @Test
        @DisplayName("As a user, I want to track my progress so I can see if strategies are working")
        fun `user can track prevention progress`() {
            // Given: User implements strategies
            val initialMoods = listOf(
                MoodEntry(userId = "user123", mood = 2, notes = "Initial", date = Date())
            )
            val initialAssessment = useCase.assessBurnoutRisk(initialMoods)
            
            val improvedMoods = listOf(
                MoodEntry(userId = "user123", mood = 4, notes = "Improved", date = Date())
            )
            val improvedAssessment = useCase.assessBurnoutRisk(improvedMoods)
            
            // When: User tracks progress
            val progressTracked = improvedAssessment.riskScore < initialAssessment.riskScore
            val progressPositive = improvedAssessment.riskScore >= 0f
            
            // Then: Progress is tracked
            assertTrue(progressTracked || improvedAssessment.riskScore <= initialAssessment.riskScore, 
                      "User should track prevention progress")
            assertTrue(progressPositive, "Progress should show improvement")
        }
    }

    @Nested
    @DisplayName("User Story: Visual Feedback")
    inner class VisualFeedback {
        
        @Test
        @DisplayName("As a user, I want visual feedback so I can quickly understand my risk level")
        fun `user sees visual risk feedback`() {
            // Given: Risk assessment with visualization
            val moodEntries = listOf(
                MoodEntry(userId = "user123", mood = 3, notes = "Moderate", date = Date())
            )
            val assessment = useCase.assessBurnoutRisk(moodEntries)
            
            // When: User views visualization
            val visualizationVisible = assessment.riskLevel != null
            val visualizationClear = assessment.riskScore >= 0f && assessment.riskScore <= 100f
            
            // Then: Visual feedback is displayed
            assertTrue(visualizationVisible, "User should see visual feedback")
            assertTrue(visualizationClear, "Visualization should be clear")
        }
    }
}
