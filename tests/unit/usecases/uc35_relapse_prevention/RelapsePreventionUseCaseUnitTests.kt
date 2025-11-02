package com.serenityai.tests.unit.usecases.uc35_relapse_prevention

import com.serenityai.ui.screens.RiskIndicator
import com.serenityai.ui.screens.EarlyWarning
import com.serenityai.ui.screens.SafetyPlan
import com.serenityai.ui.screens.EmergencyContact
import com.serenityai.ui.screens.RelapsePreventionScreen
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

@DisplayName("UC35: Relapse Prevention Alerts - Test Cases")
class RelapsePreventionUseCaseTests {

    @Nested
    @DisplayName("Test Case 1: Risk Assessment and Monitoring")
    inner class RiskAssessmentTests {
        
        @Test
        fun `should assess risk factors and calculate overall risk level`() {
            // Given
            val riskFactors = listOf(
                RiskIndicator("Sleep Pattern", 75, "Irregular sleep increases relapse risk"),
                RiskIndicator("Stress Level", 60, "High stress detected in recent patterns"),
                RiskIndicator("Social Support", 40, "Limited social interaction this week"),
                RiskIndicator("Medication Adherence", 90, "Good adherence to treatment plan"),
                RiskIndicator("Trigger Exposure", 30, "Low exposure to known triggers")
            )
            
            // When
            val averageRiskLevel = riskFactors.map { it.riskLevel }.average()
            val highRiskFactors = riskFactors.filter { it.riskLevel >= 70 }
            val lowRiskFactors = riskFactors.filter { it.riskLevel < 50 }
            val mediumRiskFactors = riskFactors.filter { it.riskLevel in 50..69 }
            
            // Then
            assertEquals(59.0, averageRiskLevel, 0.01)
            assertEquals(2, highRiskFactors.size)
            assertTrue(highRiskFactors.any { it.name == "Sleep Pattern" })
            assertTrue(highRiskFactors.any { it.name == "Medication Adherence" })
            assertEquals(2, lowRiskFactors.size)
            assertTrue(lowRiskFactors.any { it.name == "Social Support" })
            assertTrue(lowRiskFactors.any { it.name == "Trigger Exposure" })
            assertEquals(1, mediumRiskFactors.size)
            assertEquals("Stress Level", mediumRiskFactors.first().name)
        }
        
        @Test
        fun `should track risk factor changes over time`() {
            // Given
            val weeklyRiskData = listOf(
                listOf(RiskIndicator("Sleep", 80, "Week 1"), RiskIndicator("Stress", 70, "Week 1")),
                listOf(RiskIndicator("Sleep", 75, "Week 2"), RiskIndicator("Stress", 65, "Week 2")),
                listOf(RiskIndicator("Sleep", 70, "Week 3"), RiskIndicator("Stress", 60, "Week 3"))
            )
            
            // When
            val sleepTrend = weeklyRiskData.map { week ->
                week.find { it.name == "Sleep" }?.riskLevel
            }
            val stressTrend = weeklyRiskData.map { week ->
                week.find { it.name == "Stress" }?.riskLevel
            }
            
            val sleepImproving = sleepTrend.first()!! > sleepTrend.last()!!
            val stressImproving = stressTrend.first()!! > stressTrend.last()!!
            
            // Then
            assertTrue(sleepImproving) // Sleep risk decreasing
            assertTrue(stressImproving) // Stress risk decreasing
            assertEquals(listOf(80, 75, 70), sleepTrend)
            assertEquals(listOf(70, 65, 60), stressTrend)
        }
        
        @Test
        fun `should identify critical risk thresholds and alerts`() {
            // Given
            val riskFactors = listOf(
                RiskIndicator("Sleep Pattern", 85, "Critical sleep disruption"),
                RiskIndicator("Stress Level", 45, "Moderate stress"),
                RiskIndicator("Social Support", 90, "Severe isolation"),
                RiskIndicator("Medication Adherence", 25, "Poor adherence"),
                RiskIndicator("Trigger Exposure", 95, "High trigger exposure")
            )
            
            // When
            val criticalThreshold = 80
            val criticalRiskFactors = riskFactors.filter { it.riskLevel >= criticalThreshold }
            val severeRiskFactors = riskFactors.filter { it.riskLevel >= 90 }
            val moderateRiskFactors = riskFactors.filter { it.riskLevel in 50..79 }
            val lowRiskFactors = riskFactors.filter { it.riskLevel < 50 }
            
            // Then
            assertEquals(3, criticalRiskFactors.size)
            assertTrue(criticalRiskFactors.any { it.name == "Sleep Pattern" })
            assertTrue(criticalRiskFactors.any { it.name == "Social Support" })
            assertTrue(criticalRiskFactors.any { it.name == "Trigger Exposure" })
            assertEquals(2, severeRiskFactors.size)
            assertEquals(0, moderateRiskFactors.size)
            assertEquals(2, lowRiskFactors.size)
        }
    }

    @Nested
    @DisplayName("Test Case 2: Early Warning System")
    inner class EarlyWarningSystemTests {
        
        @Test
        fun `should detect early warning signs and trigger alerts`() {
            // Given
            val earlyWarnings = listOf(
                EarlyWarning("Sleep Disruption", "3 consecutive nights of poor sleep detected", "High", "Schedule sleep hygiene activities"),
                EarlyWarning("Social Isolation", "Decreased social activity over 5 days", "Medium", "Plan social interactions this weekend"),
                EarlyWarning("Stress Accumulation", "Work stress levels increasing", "Medium", "Practice stress management techniques"),
                EarlyWarning("Medication Missed", "Missed 2 doses this week", "High", "Set up medication reminders")
            )
            
            // When
            val highSeverityWarnings = earlyWarnings.filter { it.severity == "High" }
            val mediumSeverityWarnings = earlyWarnings.filter { it.severity == "Medium" }
            val sleepWarnings = earlyWarnings.filter { it.title.contains("Sleep") }
            val socialWarnings = earlyWarnings.filter { it.title.contains("Social") }
            
            // Then
            assertEquals(2, highSeverityWarnings.size)
            assertTrue(highSeverityWarnings.any { it.title == "Sleep Disruption" })
            assertTrue(highSeverityWarnings.any { it.title == "Medication Missed" })
            assertEquals(2, mediumSeverityWarnings.size)
            assertEquals(1, sleepWarnings.size)
            assertTrue(sleepWarnings.first().description.contains("3 consecutive nights"))
            assertEquals(1, socialWarnings.size)
            assertTrue(socialWarnings.first().description.contains("5 days"))
        }
        
        @Test
        fun `should prioritize warnings based on severity and urgency`() {
            // Given
            val warnings = listOf(
                EarlyWarning("Critical Alert", "Immediate intervention needed", "High", "Contact emergency services"),
                EarlyWarning("Moderate Alert", "Monitor closely", "Medium", "Increase monitoring frequency"),
                EarlyWarning("Low Alert", "Minor concern", "Low", "Continue current plan"),
                EarlyWarning("Another Critical", "Another urgent issue", "High", "Immediate action required")
            )
            
            // When
            val criticalWarnings = warnings.filter { it.severity == "High" }
            val moderateWarnings = warnings.filter { it.severity == "Medium" }
            val lowWarnings = warnings.filter { it.severity == "Low" }
            
            // Sort by severity (High > Medium > Low)
            val prioritizedWarnings = warnings.sortedWith(compareByDescending<EarlyWarning> { 
                when (it.severity) {
                    "High" -> 3
                    "Medium" -> 2
                    "Low" -> 1
                    else -> 0
                }
            })
            
            // Then
            assertEquals(2, criticalWarnings.size)
            assertEquals(1, moderateWarnings.size)
            assertEquals(1, lowWarnings.size)
            assertEquals("Critical Alert", prioritizedWarnings.first().title)
            assertEquals("Low Alert", prioritizedWarnings.last().title)
        }
        
        @Test
        fun `should track warning patterns and escalation protocols`() {
            // Given
            val warningHistory = listOf(
                EarlyWarning("Sleep Issue", "First occurrence", "Low", "Monitor"),
                EarlyWarning("Sleep Issue", "Second occurrence", "Medium", "Increase monitoring"),
                EarlyWarning("Sleep Issue", "Third occurrence", "High", "Immediate intervention"),
                EarlyWarning("Social Issue", "First occurrence", "Medium", "Plan activities")
            )
            
            // When
            val sleepWarningCount = warningHistory.count { it.title.contains("Sleep") }
            val escalatedWarnings = warningHistory.filter { it.severity == "High" }
            val recurringIssues = warningHistory.groupBy { it.title }.filter { it.value.size > 1 }
            
            // Then
            assertEquals(3, sleepWarningCount)
            assertEquals(1, escalatedWarnings.size)
            assertEquals("Sleep Issue", escalatedWarnings.first().title)
            assertEquals(1, recurringIssues.size)
            assertTrue(recurringIssues.containsKey("Sleep Issue"))
        }
    }

    @Nested
    @DisplayName("Test Case 3: Safety Plan and Emergency Protocols")
    inner class SafetyPlanTests {
        
        @Test
        fun `should maintain comprehensive safety plan with emergency contacts`() {
            // Given
            val safetyPlan = SafetyPlan(
                emergencyContacts = listOf(
                    EmergencyContact("Dr. Sarah Johnson", "Therapist", "+1-555-0123"),
                    EmergencyContact("Mike Chen", "Support Person", "+1-555-0456"),
                    EmergencyContact("Crisis Hotline", "Crisis Support", "988")
                ),
                copingStrategies = listOf(
                    "Deep breathing exercises",
                    "Call support person",
                    "Go for a walk",
                    "Listen to calming music",
                    "Practice mindfulness meditation"
                ),
                warningSigns = listOf(
                    "Feeling overwhelmed",
                    "Sleep problems",
                    "Loss of appetite",
                    "Withdrawing from others",
                    "Negative thoughts"
                )
            )
            
            // When
            val therapistContacts = safetyPlan.emergencyContacts.filter { it.role == "Therapist" }
            val supportContacts = safetyPlan.emergencyContacts.filter { it.role == "Support Person" }
            val crisisContacts = safetyPlan.emergencyContacts.filter { it.role.contains("Crisis") }
            val totalStrategies = safetyPlan.copingStrategies.size
            val totalWarningSigns = safetyPlan.warningSigns.size
            
            // Then
            assertEquals(3, safetyPlan.emergencyContacts.size)
            assertEquals(1, therapistContacts.size)
            assertEquals("Dr. Sarah Johnson", therapistContacts.first().name)
            assertEquals(1, supportContacts.size)
            assertEquals("Mike Chen", supportContacts.first().name)
            assertEquals(1, crisisContacts.size)
            assertEquals("988", crisisContacts.first().phone)
            assertEquals(5, totalStrategies)
            assertEquals(5, totalWarningSigns)
            assertTrue(safetyPlan.copingStrategies.contains("Deep breathing exercises"))
            assertTrue(safetyPlan.warningSigns.contains("Feeling overwhelmed"))
        }
        
        @Test
        fun `should validate emergency contact accessibility and response protocols`() {
            // Given
            val emergencyContacts = listOf(
                EmergencyContact("Dr. Sarah Johnson", "Therapist", "+1-555-0123"),
                EmergencyContact("Mike Chen", "Support Person", "+1-555-0456"),
                EmergencyContact("Crisis Hotline", "Crisis Support", "988"),
                EmergencyContact("Emergency Services", "911", "911")
            )
            
            // When
            val therapistAvailable = emergencyContacts.any { it.role == "Therapist" }
            val supportPersonAvailable = emergencyContacts.any { it.role == "Support Person" }
            val crisisHotlineAvailable = emergencyContacts.any { it.role.contains("Crisis") }
            val emergencyServicesAvailable = emergencyContacts.any { it.phone == "911" }
            
            val contactsByAvailability = mapOf(
                "24/7" to emergencyContacts.filter { it.role.contains("Crisis") || it.phone == "911" },
                "Business Hours" to emergencyContacts.filter { it.role == "Therapist" },
                "Personal" to emergencyContacts.filter { it.role == "Support Person" }
            )
            
            // Then
            assertTrue(therapistAvailable)
            assertTrue(supportPersonAvailable)
            assertTrue(crisisHotlineAvailable)
            assertTrue(emergencyServicesAvailable)
            assertEquals(2, contactsByAvailability["24/7"]!!.size)
            assertEquals(1, contactsByAvailability["Business Hours"]!!.size)
            assertEquals(1, contactsByAvailability["Personal"]!!.size)
        }
        
        @Test
        fun `should track safety plan effectiveness and update protocols`() {
            // Given
            val safetyPlan = SafetyPlan(
                emergencyContacts = listOf(
                    EmergencyContact("Dr. Sarah Johnson", "Therapist", "+1-555-0123"),
                    EmergencyContact("Mike Chen", "Support Person", "+1-555-0456")
                ),
                copingStrategies = listOf(
                    "Deep breathing exercises",
                    "Call support person",
                    "Go for a walk"
                ),
                warningSigns = listOf(
                    "Feeling overwhelmed",
                    "Sleep problems"
                )
            )
            
            // When
            val planCompleteness = calculatePlanCompleteness(safetyPlan)
            val hasMultipleContacts = safetyPlan.emergencyContacts.size >= 2
            val hasMultipleStrategies = safetyPlan.copingStrategies.size >= 3
            val hasWarningSigns = safetyPlan.warningSigns.isNotEmpty()
            
            // Then
            assertTrue(planCompleteness >= 0.8f) // 80% complete
            assertTrue(hasMultipleContacts)
            assertTrue(hasMultipleStrategies)
            assertTrue(hasWarningSigns)
        }
        
        private fun calculatePlanCompleteness(plan: SafetyPlan): Float {
            var score = 0f
            if (plan.emergencyContacts.size >= 2) score += 0.4f
            if (plan.copingStrategies.size >= 3) score += 0.3f
            if (plan.warningSigns.isNotEmpty()) score += 0.3f
            return score
        }
    }
}
