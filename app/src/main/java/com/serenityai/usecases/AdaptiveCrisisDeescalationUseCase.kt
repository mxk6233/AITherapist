package com.serenityai.usecases

import com.serenityai.data.models.*
import java.util.Date

/** UC36: Adaptive crisis deescalation scripts system that provides personalized crisis intervention responses. */
class AdaptiveCrisisDeescalationUseCase {
    
    private val crisisScripts = mutableMapOf<String, CrisisScript>()
    private val deescalationHistory = mutableMapOf<String, MutableList<DeescalationSession>>()
    
    /** Assesses crisis level from user input. */
    fun assessCrisisLevel(userInput: String, userId: String): CrisisAssessment {
        val sentiment = analyzeSentiment(userInput)
        val indicators = detectCrisisIndicators(userInput)
        val intensity = calculateCrisisIntensity(userInput, indicators)
        
        val level = when {
            indicators.contains(CrisisIndicator.SUICIDAL_IDEATION) -> CrisisLevel.CRITICAL
            intensity > 0.9f && sentiment.type == SentimentType.VERY_NEGATIVE -> CrisisLevel.HIGH
            intensity > 0.7f && sentiment.type == SentimentType.NEGATIVE -> CrisisLevel.MEDIUM
            intensity > 0.5f -> CrisisLevel.LOW
            else -> CrisisLevel.NONE
        }
        
        return CrisisAssessment(
            level = level,
            indicators = indicators,
            intensity = intensity,
            riskFactors = identifyRiskFactors(userInput, userId),
            assessedAt = Date()
        )
    }
    
    /** Generates adaptive deescalation script. */
    fun generateDeescalationScript(assessment: CrisisAssessment, userId: String): CrisisScript {
        val scriptId = "script_${System.currentTimeMillis()}"
        val steps = when (assessment.level) {
            CrisisLevel.CRITICAL -> generateCriticalScript(assessment)
            CrisisLevel.HIGH -> generateHighScript(assessment)
            CrisisLevel.MEDIUM -> generateMediumScript(assessment)
            CrisisLevel.LOW -> generateLowScript(assessment)
            CrisisLevel.NONE -> emptyList()
        }
        
        val script = CrisisScript(
            id = scriptId,
            userId = userId,
            crisisLevel = assessment.level,
            steps = steps,
            estimatedDuration = calculateDuration(assessment.level),
            createdAt = Date()
        )
        
        crisisScripts[scriptId] = script
        return script
    }
    
    /** Executes deescalation step. */
    fun executeDeescalationStep(scriptId: String, stepIndex: Int, userResponse: String?): DeescalationStepResult {
        val script = crisisScripts[scriptId] ?: throw IllegalArgumentException("Script not found")
        require(stepIndex < script.steps.size) { "Step index out of bounds" }
        
        val step = script.steps[stepIndex]
        val response = userResponse ?: ""
        val newAssessment = if (response.isNotEmpty()) {
            assessCrisisLevel(response, script.userId)
        } else {
            null
        }
        
        val isCompleted = stepIndex >= script.steps.size - 1
        val nextStep = if (!isCompleted) script.steps[stepIndex + 1] else null
        
        return DeescalationStepResult(
            step = step,
            userResponse = response,
            newAssessment = newAssessment,
            nextStep = nextStep,
            isCompleted = isCompleted,
            progress = ((stepIndex + 1).toFloat() / script.steps.size) * 100f
        )
    }
    
    /** Adapts script based on user response. */
    fun adaptScript(scriptId: String, userResponse: String): CrisisScript? {
        val script = crisisScripts[scriptId] ?: return null
        val assessment = assessCrisisLevel(userResponse, script.userId)
        
        return when {
            assessment.level == CrisisLevel.CRITICAL && script.crisisLevel != CrisisLevel.CRITICAL -> {
                val newScript = generateDeescalationScript(assessment, script.userId)
                crisisScripts[scriptId] = newScript.copy(id = scriptId)
                newScript.copy(id = scriptId)
            }
            assessment.level.ordinal < script.crisisLevel.ordinal -> {
                val improvedSteps = generateImprovedSteps(assessment)
                script.copy(steps = improvedSteps)
            }
            else -> script
        }
    }
    
    /** Provides immediate safety measures. */
    fun provideImmediateSafetyMeasures(assessment: CrisisAssessment): SafetyMeasures {
        return when (assessment.level) {
            CrisisLevel.CRITICAL -> SafetyMeasures(
                immediateActions = listOf(
                    "Contact emergency services: 911",
                    "Reach out to crisis hotline: 988",
                    "Contact trusted emergency contact",
                    "Remove access to harmful means"
                ),
                safetyPlan = createEmergencySafetyPlan(assessment),
                resources = getCriticalResources()
            )
            CrisisLevel.HIGH -> SafetyMeasures(
                immediateActions = listOf(
                    "Use grounding techniques",
                    "Contact support network",
                    "Access crisis resources",
                    "Practice breathing exercises"
                ),
                safetyPlan = createSafetyPlan(assessment),
                resources = getHighRiskResources()
            )
            else -> SafetyMeasures(
                immediateActions = listOf(
                    "Practice self-care",
                    "Use coping strategies",
                    "Reach out for support"
                ),
                safetyPlan = null,
                resources = getGeneralResources()
            )
        }
    }
    
    /** Monitors deescalation progress. */
    fun monitorProgress(sessionId: String): DeescalationProgress {
        val session = deescalationHistory.values.flatten().find { it.id == sessionId }
            ?: throw IllegalArgumentException("Session not found")
        
        val initialLevel = session.initialAssessment.level
        val currentLevel = session.currentAssessment?.level ?: initialLevel
        val progress = calculateProgress(initialLevel, currentLevel)
        
        return DeescalationProgress(
            sessionId = sessionId,
            initialLevel = initialLevel,
            currentLevel = currentLevel,
            progressPercentage = progress,
            stepsCompleted = session.completedSteps.size,
            totalSteps = session.script.steps.size,
            timeElapsed = Date().time - session.startedAt.time,
            isStable = currentLevel.ordinal <= CrisisLevel.LOW.ordinal
        )
    }
    
    /** Creates deescalation session. */
    fun createSession(userId: String, initialInput: String): DeescalationSession {
        val assessment = assessCrisisLevel(initialInput, userId)
        val script = generateDeescalationScript(assessment, userId)
        
        val session = DeescalationSession(
            id = "session_${System.currentTimeMillis()}",
            userId = userId,
            initialAssessment = assessment,
            currentAssessment = assessment,
            script = script,
            completedSteps = emptyList(),
            startedAt = Date(),
            status = DeescalationStatus.ACTIVE
        )
        
        deescalationHistory.getOrPut(userId) { mutableListOf() }.add(session)
        return session
    }
    
    /** Gets active sessions for user. */
    fun getActiveSessions(userId: String): List<DeescalationSession> {
        return deescalationHistory[userId]?.filter { it.status == DeescalationStatus.ACTIVE } ?: emptyList()
    }
    
    /** Completes deescalation session. */
    fun completeSession(sessionId: String, finalAssessment: CrisisAssessment): DeescalationSession {
        val session = deescalationHistory.values.flatten().find { it.id == sessionId }
            ?: throw IllegalArgumentException("Session not found")
        
        val updatedSession = session.copy(
            currentAssessment = finalAssessment,
            status = DeescalationStatus.COMPLETED,
            completedAt = Date()
        )
        
        deescalationHistory[session.userId]?.replaceAll { if (it.id == sessionId) updatedSession else it }
        return updatedSession
    }
    
    // Helper methods
    private fun analyzeSentiment(message: String): SentimentAnalysis {
        val lowerMessage = message.lowercase()
        val negativeWords = listOf("terrible", "awful", "hate", "sad", "depressed", "anxious", "worried", "stressed", "frustrated", "angry", "hopeless")
        val veryNegativeWords = listOf("suicide", "kill", "end", "worthless", "useless", "despair")
        
        val negativeCount = negativeWords.count { lowerMessage.contains(it) }
        val veryNegativeCount = veryNegativeWords.count { lowerMessage.contains(it) }
        
        val sentimentType = when {
            veryNegativeCount > 0 -> SentimentType.VERY_NEGATIVE
            negativeCount > 2 -> SentimentType.NEGATIVE
            negativeCount > 0 -> SentimentType.NEGATIVE
            else -> SentimentType.NEUTRAL
        }
        
        return SentimentAnalysis(
            type = sentimentType,
            confidence = if (veryNegativeCount > 0) 0.9f else 0.7f,
            keywords = emptyList(),
            intensity = if (veryNegativeCount > 0) 0.95f else 0.7f,
            detectedAt = Date()
        )
    }
    
    private fun detectCrisisIndicators(message: String): List<CrisisIndicator> {
        val indicators = mutableListOf<CrisisIndicator>()
        val lowerMessage = message.lowercase()
        
        val crisisKeywords = listOf("suicide", "kill myself", "end it all", "no point", "worthless", "hopeless", "can't go on")
        if (crisisKeywords.any { lowerMessage.contains(it) }) {
            indicators.add(CrisisIndicator.SUICIDAL_IDEATION)
        }
        
        if (lowerMessage.contains("hurt myself") || lowerMessage.contains("self harm")) {
            indicators.add(CrisisIndicator.SELF_HARM_RISK)
        }
        
        return indicators
    }
    
    private fun calculateCrisisIntensity(message: String, indicators: List<CrisisIndicator>): Float {
        val baseIntensity = when {
            indicators.contains(CrisisIndicator.SUICIDAL_IDEATION) -> 0.95f
            indicators.contains(CrisisIndicator.SELF_HARM_RISK) -> 0.85f
            indicators.isNotEmpty() -> 0.75f
            else -> 0.5f
        }
        
        val exclamationCount = message.count { it == '!' }
        return (baseIntensity + (exclamationCount * 0.02f)).coerceAtMost(1.0f)
    }
    
    private fun identifyRiskFactors(message: String, userId: String): List<String> {
        val factors = mutableListOf<String>()
        val lowerMessage = message.lowercase()
        
        if (lowerMessage.contains("alone") || lowerMessage.contains("isolated")) {
            factors.add("social_isolation")
        }
        if (lowerMessage.contains("sleep") && lowerMessage.contains("can't")) {
            factors.add("sleep_disturbance")
        }
        if (lowerMessage.contains("substance") || lowerMessage.contains("alcohol") || lowerMessage.contains("drug")) {
            factors.add("substance_use")
        }
        
        return factors
    }
    
    private fun generateCriticalScript(assessment: CrisisAssessment): List<DeescalationStep> {
        return listOf(
            DeescalationStep(
                order = 1,
                type = StepType.IMMEDIATE_SAFETY,
                prompt = "I'm very concerned about your safety. Your life has value. Can you tell me where you are right now?",
                expectedResponse = "location_or_safety_confirmation"
            ),
            DeescalationStep(
                order = 2,
                type = StepType.CRISIS_HOTLINE,
                prompt = "I want to make sure you get immediate support. Can we call a crisis hotline together? The number is 988.",
                expectedResponse = "agreement_or_alternative"
            ),
            DeescalationStep(
                order = 3,
                type = StepType.SAFETY_PLANNING,
                prompt = "Let's create a safety plan together. Who can you reach out to right now for support?",
                expectedResponse = "support_contacts"
            ),
            DeescalationStep(
                order = 4,
                type = StepType.GROUNDING,
                prompt = "Let's do a grounding exercise together. Can you name 5 things you can see around you?",
                expectedResponse = "grounding_response"
            )
        )
    }
    
    private fun generateHighScript(assessment: CrisisAssessment): List<DeescalationStep> {
        return listOf(
            DeescalationStep(
                order = 1,
                type = StepType.VALIDATION,
                prompt = "I hear that you're going through an extremely difficult time. Your feelings are valid. Can you tell me what's making things so hard right now?",
                expectedResponse = "emotional_expression"
            ),
            DeescalationStep(
                order = 2,
                type = StepType.COPING_STRATEGIES,
                prompt = "Let's work through this together. What coping strategies have helped you in the past?",
                expectedResponse = "coping_identification"
            ),
            DeescalationStep(
                order = 3,
                type = StepType.SUPPORT_NETWORK,
                prompt = "Who in your life can you reach out to for support right now?",
                expectedResponse = "support_identification"
            ),
            DeescalationStep(
                order = 4,
                type = StepType.RESOURCE_PROVISION,
                prompt = "I want to make sure you have resources available. Would you like information about crisis support services?",
                expectedResponse = "resource_acceptance"
            )
        )
    }
    
    private fun generateMediumScript(assessment: CrisisAssessment): List<DeescalationStep> {
        return listOf(
            DeescalationStep(
                order = 1,
                type = StepType.VALIDATION,
                prompt = "It sounds like you're having a really tough time. I'm here to listen. What's been weighing on you?",
                expectedResponse = "emotional_expression"
            ),
            DeescalationStep(
                order = 2,
                type = StepType.EXPLORATION,
                prompt = "Can you tell me more about what's contributing to these difficult feelings?",
                expectedResponse = "detailed_expression"
            ),
            DeescalationStep(
                order = 3,
                type = StepType.COPING_STRATEGIES,
                prompt = "What are some things that have helped you feel better in similar situations?",
                expectedResponse = "coping_identification"
            )
        )
    }
    
    private fun generateLowScript(assessment: CrisisAssessment): List<DeescalationStep> {
        return listOf(
            DeescalationStep(
                order = 1,
                type = StepType.VALIDATION,
                prompt = "Thank you for sharing. I'm here to support you. What would be most helpful right now?",
                expectedResponse = "support_request"
            ),
            DeescalationStep(
                order = 2,
                type = StepType.COPING_STRATEGIES,
                prompt = "Let's explore some coping strategies that might help. What activities usually help you feel better?",
                expectedResponse = "activity_identification"
            )
        )
    }
    
    private fun generateImprovedSteps(assessment: CrisisAssessment): List<DeescalationStep> {
        return when (assessment.level) {
            CrisisLevel.HIGH -> generateHighScript(assessment)
            CrisisLevel.MEDIUM -> generateMediumScript(assessment)
            CrisisLevel.LOW -> generateLowScript(assessment)
            else -> emptyList()
        }
    }
    
    private fun calculateDuration(level: CrisisLevel): Int {
        return when (level) {
            CrisisLevel.CRITICAL -> 60
            CrisisLevel.HIGH -> 45
            CrisisLevel.MEDIUM -> 30
            CrisisLevel.LOW -> 15
            CrisisLevel.NONE -> 0
        }
    }
    
    private fun calculateProgress(initial: CrisisLevel, current: CrisisLevel): Float {
        val maxLevel = CrisisLevel.values().size - 1
        val initialScore = maxLevel - initial.ordinal
        val currentScore = maxLevel - current.ordinal
        return ((currentScore.toFloat() / initialScore.toFloat()) * 100f).coerceAtMost(100f)
    }
    
    private fun createEmergencySafetyPlan(assessment: CrisisAssessment): SafetyPlan {
        return SafetyPlan(
            id = "plan_${System.currentTimeMillis()}",
            userId = "",
            emergencyContacts = listOf(
                EmergencyContact(id = "1", name = "Crisis Hotline", role = "Crisis Support", phone = "988", isPrimary = true),
                EmergencyContact(id = "2", name = "Emergency Services", role = "Emergency", phone = "911", isPrimary = true)
            ),
            copingStrategies = listOf(
                "Deep breathing exercises",
                "Grounding techniques (5-4-3-2-1)",
                "Contact support network",
                "Remove access to harmful means"
            ),
            warningSigns = assessment.indicators.map { it.name },
            personalizedTriggers = assessment.riskFactors
        )
    }
    
    private fun createSafetyPlan(assessment: CrisisAssessment): SafetyPlan {
        return SafetyPlan(
            id = "plan_${System.currentTimeMillis()}",
            userId = "",
            emergencyContacts = emptyList(),
            copingStrategies = listOf(
                "Mindful breathing",
                "Progressive muscle relaxation",
                "Reach out to support network",
                "Use crisis resources"
            ),
            warningSigns = assessment.indicators.map { it.name },
            personalizedTriggers = assessment.riskFactors
        )
    }
    
    private fun getCriticalResources(): List<String> {
        return listOf(
            "National Suicide Prevention Lifeline: 988",
            "Crisis Text Line: Text HOME to 741741",
            "Emergency Services: 911",
            "988 Suicide & Crisis Lifeline website"
        )
    }
    
    private fun getHighRiskResources(): List<String> {
        return listOf(
            "Crisis support hotline",
            "Mental health professional directory",
            "Support group resources",
            "Crisis intervention services"
        )
    }
    
    private fun getGeneralResources(): List<String> {
        return listOf(
            "Self-help resources",
            "Coping strategies guide",
            "Community support options",
            "Mental health information"
        )
    }
}

/** Data class for crisis assessment. */
data class CrisisAssessment(
    val level: CrisisLevel,
    val indicators: List<CrisisIndicator>,
    val intensity: Float,
    val riskFactors: List<String>,
    val assessedAt: Date
)

/** Enum for crisis levels. */
enum class CrisisLevel {
    NONE, LOW, MEDIUM, HIGH, CRITICAL
}

/** Data class for crisis script. */
data class CrisisScript(
    val id: String,
    val userId: String,
    val crisisLevel: CrisisLevel,
    val steps: List<DeescalationStep>,
    val estimatedDuration: Int,
    val createdAt: Date
)

/** Data class for deescalation step. */
data class DeescalationStep(
    val order: Int,
    val type: StepType,
    val prompt: String,
    val expectedResponse: String
)

/** Enum for step types. */
enum class StepType {
    IMMEDIATE_SAFETY, CRISIS_HOTLINE, SAFETY_PLANNING, VALIDATION, EXPLORATION, 
    COPING_STRATEGIES, SUPPORT_NETWORK, RESOURCE_PROVISION, GROUNDING, FOLLOW_UP
}

/** Data class for deescalation step result. */
data class DeescalationStepResult(
    val step: DeescalationStep,
    val userResponse: String,
    val newAssessment: CrisisAssessment?,
    val nextStep: DeescalationStep?,
    val isCompleted: Boolean,
    val progress: Float
)

/** Data class for safety measures. */
data class SafetyMeasures(
    val immediateActions: List<String>,
    val safetyPlan: SafetyPlan?,
    val resources: List<String>
)

/** Data class for deescalation session. */
data class DeescalationSession(
    val id: String,
    val userId: String,
    val initialAssessment: CrisisAssessment,
    val currentAssessment: CrisisAssessment?,
    val script: CrisisScript,
    val completedSteps: List<DeescalationStepResult>,
    val startedAt: Date,
    val completedAt: Date? = null,
    val status: DeescalationStatus
)

/** Enum for deescalation status. */
enum class DeescalationStatus {
    ACTIVE, COMPLETED, ESCALATED, CANCELLED
}

/** Data class for deescalation progress. */
data class DeescalationProgress(
    val sessionId: String,
    val initialLevel: CrisisLevel,
    val currentLevel: CrisisLevel,
    val progressPercentage: Float,
    val stepsCompleted: Int,
    val totalSteps: Int,
    val timeElapsed: Long,
    val isStable: Boolean
)

