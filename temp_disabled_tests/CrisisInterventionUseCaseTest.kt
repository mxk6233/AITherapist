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

class CrisisInterventionUseCaseTest {

    private lateinit var crisisInterventionUseCase: CrisisInterventionUseCase

    @BeforeEach
    fun setUp() {
        crisisInterventionUseCase = CrisisInterventionUseCase()
    }

    @Nested
    @DisplayName("Crisis Detection")
    inner class CrisisDetection {

        @Test
        @DisplayName("should detect crisis keywords")
        fun shouldDetectCrisisKeywords() {
            val crisisMessages = listOf(
                "I want to hurt myself",
                "I'm thinking about suicide",
                "I can't go on anymore",
                "I want to end it all",
                "I'm in immediate danger"
            )

            crisisMessages.forEach { message ->
                val isCrisis = crisisInterventionUseCase.detectCrisis(message)
                assertTrue(isCrisis, "Should detect crisis in: $message")
            }
        }

        @Test
        @DisplayName("should not detect false positives")
        fun shouldNotDetectFalsePositives() {
            val nonCrisisMessages = listOf(
                "I'm feeling sad today",
                "Work is stressful",
                "I had a bad day",
                "I'm worried about my health",
                "I need help with my homework"
            )

            nonCrisisMessages.forEach { message ->
                val isCrisis = crisisInterventionUseCase.detectCrisis(message)
                assertFalse(isCrisis, "Should not detect crisis in: $message")
            }
        }

        @ParameterizedTest
        @ValueSource(strings = [
            "I want to kill myself",
            "I'm going to end my life",
            "I can't take it anymore",
            "I want to die",
            "I'm planning to hurt myself",
            "I have a plan to end it all"
        ])
        @DisplayName("should detect high-risk crisis indicators")
        fun shouldDetectHighRiskCrisisIndicators(message: String) {
            val riskLevel = crisisInterventionUseCase.assessCrisisRisk(message)
            assertTrue(riskLevel >= CrisisRiskLevel.HIGH, "Should assess high risk for: $message")
        }
    }

    @Nested
    @DisplayName("Risk Assessment")
    inner class RiskAssessment {

        @Test
        @DisplayName("should assess high risk correctly")
        fun shouldAssessHighRiskCorrectly() {
            val highRiskMessage = "I have a plan to kill myself tonight"
            val riskLevel = crisisInterventionUseCase.assessCrisisRisk(highRiskMessage)
            
            assertEquals(CrisisRiskLevel.CRITICAL, riskLevel)
        }

        @Test
        @DisplayName("should assess medium risk correctly")
        fun shouldAssessMediumRiskCorrectly() {
            val mediumRiskMessage = "I'm thinking about hurting myself"
            val riskLevel = crisisInterventionUseCase.assessCrisisRisk(mediumRiskMessage)
            
            assertTrue(riskLevel >= CrisisRiskLevel.MEDIUM)
        }

        @Test
        @DisplayName("should assess low risk correctly")
        fun shouldAssessLowRiskCorrectly() {
            val lowRiskMessage = "I'm feeling really down and hopeless"
            val riskLevel = crisisInterventionUseCase.assessCrisisRisk(lowRiskMessage)
            
            assertTrue(riskLevel <= CrisisRiskLevel.MEDIUM)
        }
    }

    @Nested
    @DisplayName("Crisis Response")
    inner class CrisisResponse {

        @Test
        @DisplayName("should provide immediate crisis response")
        fun shouldProvideImmediateCrisisResponse() = runTest {
            val crisisMessage = "I want to kill myself"
            val response = crisisInterventionUseCase.generateCrisisResponse(crisisMessage)
            
            assertNotNull(response)
            assertTrue(response.contains("emergency") || response.contains("911") || response.contains("988"))
            assertTrue(response.contains("immediate") || response.contains("right now"))
        }

        @Test
        @DisplayName("should include crisis hotline numbers")
        fun shouldIncludeCrisisHotlineNumbers() = runTest {
            val crisisMessage = "I'm in crisis"
            val response = crisisInterventionUseCase.generateCrisisResponse(crisisMessage)
            
            assertTrue(response.contains("988") || response.contains("911"))
        }

        @Test
        @DisplayName("should provide safety planning")
        fun shouldProvideSafetyPlanning() = runTest {
            val crisisMessage = "I'm thinking about suicide"
            val response = crisisInterventionUseCase.generateCrisisResponse(crisisMessage)
            
            assertTrue(response.contains("safety") || response.contains("safe"))
        }

        @Test
        @DisplayName("should offer immediate support")
        fun shouldOfferImmediateSupport() = runTest {
            val crisisMessage = "I need help now"
            val response = crisisInterventionUseCase.generateCrisisResponse(crisisMessage)
            
            assertTrue(response.contains("support") || response.contains("help"))
        }
    }

    @Nested
    @DisplayName("Emergency Resources")
    inner class EmergencyResources {

        @Test
        @DisplayName("should provide emergency contacts")
        fun shouldProvideEmergencyContacts() {
            val contacts = crisisInterventionUseCase.getEmergencyContacts()
            
            assertNotNull(contacts)
            assertTrue(contacts.isNotEmpty())
            assertTrue(contacts.any { it.contains("988") })
            assertTrue(contacts.any { it.contains("911") })
        }

        @Test
        @DisplayName("should provide crisis hotlines")
        fun shouldProvideCrisisHotlines() {
            val hotlines = crisisInterventionUseCase.getCrisisHotlines()
            
            assertNotNull(hotlines)
            assertTrue(hotlines.isNotEmpty())
            assertTrue(hotlines.any { it.contains("Suicide Prevention") })
        }

        @Test
        @DisplayName("should provide local resources")
        fun shouldProvideLocalResources() {
            val resources = crisisInterventionUseCase.getLocalCrisisResources()
            
            assertNotNull(resources)
            assertTrue(resources.isNotEmpty())
        }
    }

    @Nested
    @DisplayName("Crisis Logging")
    inner class CrisisLogging {

        @Test
        @DisplayName("should log crisis incident")
        fun shouldLogCrisisIncident() = runTest {
            val userId = "user123"
            val crisisMessage = "I'm in crisis"
            val incident = crisisInterventionUseCase.logCrisisIncident(userId, crisisMessage)
            
            assertNotNull(incident)
            assertEquals(userId, incident.userId)
            assertEquals(crisisMessage, incident.message)
            assertNotNull(incident.timestamp)
        }

        @Test
        @DisplayName("should track crisis frequency")
        fun shouldTrackCrisisFrequency() = runTest {
            val userId = "user123"
            val incidents = listOf(
                crisisInterventionUseCase.logCrisisIncident(userId, "Crisis 1"),
                crisisInterventionUseCase.logCrisisIncident(userId, "Crisis 2"),
                crisisInterventionUseCase.logCrisisIncident(userId, "Crisis 3")
            )
            
            val frequency = crisisInterventionUseCase.getCrisisFrequency(userId, incidents)
            assertEquals(3, frequency)
        }

        @Test
        @DisplayName("should identify crisis patterns")
        fun shouldIdentifyCrisisPatterns() = runTest {
            val userId = "user123"
            val incidents = listOf(
                CrisisIncident(userId, "I want to hurt myself", Date()),
                CrisisIncident(userId, "I'm thinking about suicide", Date()),
                CrisisIncident(userId, "I can't go on", Date())
            )
            
            val patterns = crisisInterventionUseCase.identifyCrisisPatterns(incidents)
            assertNotNull(patterns)
            assertTrue(patterns.isNotEmpty())
        }
    }

    @Nested
    @DisplayName("Follow-up Care")
    inner class FollowUpCare {

        @Test
        @DisplayName("should schedule follow-up")
        fun shouldScheduleFollowUp() = runTest {
            val userId = "user123"
            val followUp = crisisInterventionUseCase.scheduleFollowUp(userId)
            
            assertNotNull(followUp)
            assertEquals(userId, followUp.userId)
            assertNotNull(followUp.scheduledTime)
        }

        @Test
        @DisplayName("should provide coping strategies")
        fun shouldProvideCopingStrategies() = runTest {
            val strategies = crisisInterventionUseCase.getCrisisCopingStrategies()
            
            assertNotNull(strategies)
            assertTrue(strategies.isNotEmpty())
            assertTrue(strategies.any { it.contains("breathing") })
            assertTrue(strategies.any { it.contains("grounding") })
        }

        @Test
        @DisplayName("should recommend professional help")
        fun shouldRecommendProfessionalHelp() = runTest {
            val recommendations = crisisInterventionUseCase.getProfessionalHelpRecommendations()
            
            assertNotNull(recommendations)
            assertTrue(recommendations.isNotEmpty())
            assertTrue(recommendations.any { it.contains("therapist") })
            assertTrue(recommendations.any { it.contains("psychiatrist") })
        }
    }
}

/**
 * Crisis Risk Levels
 */
enum class CrisisRiskLevel {
    LOW, MEDIUM, HIGH, CRITICAL
}

/**
 * Crisis Incident data class
 */
data class CrisisIncident(
    val userId: String,
    val message: String,
    val timestamp: Date,
    val riskLevel: CrisisRiskLevel = CrisisRiskLevel.MEDIUM
)

/**
 * Follow-up care data class
 */
data class FollowUpCare(
    val userId: String,
    val scheduledTime: Date,
    val type: String = "crisis_follow_up",
    val completed: Boolean = false
)

/**
 * Use Case implementation for Crisis Intervention
 */
class CrisisInterventionUseCase {
    
    fun detectCrisis(message: String): Boolean {
        val crisisKeywords = listOf(
            "suicide", "kill myself", "end my life", "hurt myself", "die",
            "can't go on", "end it all", "want to die", "planning to hurt",
            "immediate danger", "crisis", "emergency", "help now"
        )
        
        val lowerMessage = message.lowercase()
        return crisisKeywords.any { keyword -> lowerMessage.contains(keyword) }
    }

    fun assessCrisisRisk(message: String): CrisisRiskLevel {
        val criticalKeywords = listOf("kill myself", "end my life", "plan to hurt", "tonight", "today")
        val highKeywords = listOf("suicide", "hurt myself", "want to die", "can't go on")
        val mediumKeywords = listOf("crisis", "emergency", "help now", "danger")
        
        val lowerMessage = message.lowercase()
        
        return when {
            criticalKeywords.any { keyword -> lowerMessage.contains(keyword) } -> CrisisRiskLevel.CRITICAL
            highKeywords.any { keyword -> lowerMessage.contains(keyword) } -> CrisisRiskLevel.HIGH
            mediumKeywords.any { keyword -> lowerMessage.contains(keyword) } -> CrisisRiskLevel.MEDIUM
            else -> CrisisRiskLevel.LOW
        }
    }

    suspend fun generateCrisisResponse(message: String): String {
        val riskLevel = assessCrisisRisk(message)
        
        return when (riskLevel) {
            CrisisRiskLevel.CRITICAL -> {
                "🚨 IMMEDIATE CRISIS RESPONSE 🚨\n\n" +
                "Your safety is the most important thing right now. Please:\n\n" +
                "1. Call 911 immediately if you're in immediate danger\n" +
                "2. Call the National Suicide Prevention Lifeline: 988\n" +
                "3. Go to your nearest emergency room\n" +
                "4. Stay with someone you trust\n\n" +
                "You don't have to face this alone. There are people who care about you and want to help."
            }
            CrisisRiskLevel.HIGH -> {
                "I'm very concerned about your safety. Please reach out for immediate help:\n\n" +
                "• National Suicide Prevention Lifeline: 988 (available 24/7)\n" +
                "• Crisis Text Line: Text HOME to 741741\n" +
                "• Emergency Services: 911\n\n" +
                "Your life has value, and there are people who want to help you through this difficult time."
            }
            CrisisRiskLevel.MEDIUM -> {
                "I can hear that you're going through a very difficult time. Let's get you connected with support:\n\n" +
                "• Crisis Hotline: 988\n" +
                "• Crisis Text Line: Text HOME to 741741\n" +
                "• Local Crisis Center: Check your area for 24/7 support\n\n" +
                "You're not alone in this. There are trained professionals ready to help you."
            }
            CrisisRiskLevel.LOW -> {
                "I can sense you're struggling right now. It's important to reach out for support:\n\n" +
                "• Talk to a trusted friend or family member\n" +
                "• Consider speaking with a mental health professional\n" +
                "• Crisis Hotline: 988 (available 24/7)\n\n" +
                "Remember, it's okay to ask for help when you need it."
            }
        }
    }

    fun getEmergencyContacts(): List<String> {
        return listOf(
            "Emergency Services: 911",
            "National Suicide Prevention Lifeline: 988",
            "Crisis Text Line: Text HOME to 741741",
            "National Domestic Violence Hotline: 1-800-799-7233"
        )
    }

    fun getCrisisHotlines(): List<String> {
        return listOf(
            "National Suicide Prevention Lifeline: 988",
            "Crisis Text Line: Text HOME to 741741",
            "Veterans Crisis Line: 1-800-273-8255",
            "LGBT National Hotline: 1-888-843-4564"
        )
    }

    fun getLocalCrisisResources(): List<String> {
        return listOf(
            "Local Emergency Room",
            "Community Mental Health Center",
            "Local Crisis Intervention Team",
            "Mobile Crisis Unit"
        )
    }

    suspend fun logCrisisIncident(userId: String, message: String): CrisisIncident {
        val riskLevel = assessCrisisRisk(message)
        return CrisisIncident(
            userId = userId,
            message = message,
            timestamp = Date(),
            riskLevel = riskLevel
        )
    }

    fun getCrisisFrequency(userId: String, incidents: List<CrisisIncident>): Int {
        return incidents.count { it.userId == userId }
    }

    fun identifyCrisisPatterns(incidents: List<CrisisIncident>): List<String> {
        val patterns = mutableListOf<String>()
        
        if (incidents.size >= 3) {
            patterns.add("Multiple crisis incidents detected")
        }
        
        val highRiskIncidents = incidents.count { it.riskLevel >= CrisisRiskLevel.HIGH }
        if (highRiskIncidents > 0) {
            patterns.add("High-risk crisis incidents present")
        }
        
        return patterns
    }

    suspend fun scheduleFollowUp(userId: String): FollowUpCare {
        val scheduledTime = Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000) // 24 hours from now
        return FollowUpCare(
            userId = userId,
            scheduledTime = scheduledTime
        )
    }

    fun getCrisisCopingStrategies(): List<String> {
        return listOf(
            "4-7-8 Breathing: Inhale for 4, hold for 7, exhale for 8",
            "Grounding Technique: Name 5 things you can see, 4 you can touch, 3 you can hear, 2 you can smell, 1 you can taste",
            "Progressive Muscle Relaxation: Tense and release each muscle group",
            "Call a trusted friend or family member",
            "Go to a safe, public place",
            "Remove any means of self-harm from your immediate area"
        )
    }

    fun getProfessionalHelpRecommendations(): List<String> {
        return listOf(
            "Schedule an appointment with a licensed therapist",
            "Consider seeing a psychiatrist for medication evaluation",
            "Look into intensive outpatient programs",
            "Research local support groups",
            "Ask your primary care doctor for mental health referrals",
            "Consider teletherapy options for immediate support"
        )
    }
}
