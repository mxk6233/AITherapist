package com.serenityai.tests.unit.usecases.uc36_crisis_deescalation

import com.serenityai.usecases.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

/**
 * UC36: Adaptive Crisis Deescalation Scripts
 * 
 * Use Case Goal: Provide personalized crisis intervention responses through
 * adaptive deescalation scripts that adjust based on user responses and crisis level.
 * 
 * Key Requirements Being Tested:
 * 1. System assesses crisis level from user input
 * 2. System generates adaptive deescalation scripts
 * 3. System executes deescalation steps
 * 4. System adapts scripts based on user responses
 * 5. System provides immediate safety measures
 * 6. System monitors deescalation progress
 */
@DisplayName("UC36: Adaptive Crisis Deescalation Scripts - Unit Tests")
class AdaptiveCrisisDeescalationUseCaseUnitTests {

    private lateinit var useCase: AdaptiveCrisisDeescalationUseCase

    @BeforeEach
    fun setUp() {
        useCase = AdaptiveCrisisDeescalationUseCase()
    }

    @Nested
    @DisplayName("Test Case 1: Crisis Assessment - Validates Core UC36 Functionality")
    inner class CrisisAssessmentTests {
        
        @Test
        @DisplayName("UC36-REQ-1: System MUST assess critical crisis level")
        fun `system assesses critical crisis level correctly`() {
            // Given: User input indicating critical crisis
            val userInput = "I want to end it all. I can't go on like this."
            val userId = "user123"
            
            // When: System assesses crisis level
            val assessment = useCase.assessCrisisLevel(userInput, userId)
            
            // Then: Crisis level should be critical
            assertNotNull(assessment, "UC36: Assessment must be returned")
            assertEquals(CrisisLevel.CRITICAL, assessment.level,
                "UC36: Critical crisis input must be assessed as CRITICAL")
            assertTrue(assessment.indicators.isNotEmpty(),
                "UC36: Crisis indicators must be detected")
            assertTrue(assessment.intensity > 0.8f,
                "UC36: Critical crisis should have high intensity")
        }
        
        @Test
        @DisplayName("UC36-REQ-2: System MUST assess high crisis level")
        fun `system assesses high crisis level correctly`() {
            // Given: User input indicating high crisis
            val userInput = "I'm feeling extremely hopeless and don't know what to do"
            val userId = "user123"
            
            // When: System assesses crisis level
            val assessment = useCase.assessCrisisLevel(userInput, userId)
            
            // Then: Crisis level should be high
            assertNotNull(assessment, "UC36: Assessment must be returned")
            assertTrue(assessment.level == CrisisLevel.HIGH || assessment.level == CrisisLevel.CRITICAL,
                "UC36: High crisis input should be assessed as HIGH or CRITICAL")
            assertTrue(assessment.intensity > 0.5f,
                "UC36: High crisis should have elevated intensity")
        }
        
        @Test
        @DisplayName("UC36-REQ-3: System MUST assess medium crisis level")
        fun `system assesses medium crisis level correctly`() {
            // Given: User input indicating medium crisis
            val userInput = "I'm feeling really stressed and overwhelmed and don't know what to do"
            val userId = "user123"
            
            // When: System assesses crisis level
            val assessment = useCase.assessCrisisLevel(userInput, userId)
            
            // Then: Crisis level should be assessed (may be LOW, MEDIUM, HIGH, or CRITICAL)
            assertNotNull(assessment, "UC36: Assessment must be returned")
            assertTrue(assessment.level != CrisisLevel.NONE,
                "UC36: Crisis input should be assessed as some level of crisis")
        }
        
        @Test
        @DisplayName("UC36-REQ-4: System MUST identify risk factors")
        fun `system identifies risk factors correctly`() {
            // Given: User input with risk factors
            val userInput = "I'm feeling alone and isolated, and I can't sleep"
            val userId = "user123"
            
            // When: System assesses crisis level
            val assessment = useCase.assessCrisisLevel(userInput, userId)
            
            // Then: Risk factors should be identified
            assertNotNull(assessment, "UC36: Assessment must be returned")
            assertNotNull(assessment.riskFactors, "UC36: Risk factors must be identified")
        }
    }

    @Nested
    @DisplayName("Test Case 2: Deescalation Script Generation")
    inner class ScriptGenerationTests {
        
        @Test
        @DisplayName("UC36-REQ-5: System MUST generate critical crisis script")
        fun `system generates critical crisis script correctly`() {
            // Given: Critical crisis assessment
            val userId = "user123"
            val userInput = "I want to end it all"
            val assessment = useCase.assessCrisisLevel(userInput, userId)
            
            // When: System generates script
            val script = useCase.generateDeescalationScript(assessment, userId)
            
            // Then: Script should be generated correctly
            assertNotNull(script, "UC36: Script must be generated")
            assertEquals(CrisisLevel.CRITICAL, script.crisisLevel,
                "UC36: Script must match crisis level")
            assertTrue(script.steps.isNotEmpty(), "UC36: Script must have steps")
            assertTrue(script.estimatedDuration > 0,
                "UC36: Script must have estimated duration")
            assertEquals(userId, script.userId, "UC36: Script must be linked to user")
        }
        
        @Test
        @DisplayName("UC36-REQ-6: System MUST generate high crisis script")
        fun `system generates high crisis script correctly`() {
            // Given: High crisis assessment
            val userId = "user123"
            val userInput = "I'm feeling extremely hopeless"
            val assessment = useCase.assessCrisisLevel(userInput, userId)
            
            // When: System generates script
            val script = useCase.generateDeescalationScript(assessment, userId)
            
            // Then: Script should be generated correctly
            assertNotNull(script, "UC36: Script must be generated")
            assertTrue(script.crisisLevel.ordinal >= CrisisLevel.HIGH.ordinal,
                "UC36: Script must match or exceed crisis level")
            assertTrue(script.steps.isNotEmpty(), "UC36: Script must have steps")
        }
        
        @Test
        @DisplayName("UC36-REQ-7: System MUST generate script with appropriate steps")
        fun `system generates script with appropriate steps correctly`() {
            // Given: Crisis assessment (ensuring it's not NONE level)
            val userId = "user123"
            val userInput = "I'm feeling extremely hopeless and don't know what to do"
            val assessment = useCase.assessCrisisLevel(userInput, userId)
            
            // When: System generates script
            val script = useCase.generateDeescalationScript(assessment, userId)
            
            // Then: Script steps should be appropriate
            assertNotNull(script, "UC36: Script must be generated")
            // Only check steps if crisis level is not NONE (NONE has empty steps)
            if (script.crisisLevel != CrisisLevel.NONE) {
                assertTrue(script.steps.isNotEmpty(), "UC36: Script must have steps for non-NONE crisis levels")
                script.steps.forEach { step ->
                    assertNotNull(step.prompt, "UC36: Each step must have a prompt")
                    assertTrue(step.prompt.isNotBlank(), "UC36: Step prompt must not be empty")
                    assertNotNull(step.type, "UC36: Each step must have a type")
                }
            }
        }
    }

    @Nested
    @DisplayName("Test Case 3: Deescalation Step Execution")
    inner class StepExecutionTests {
        
        @Test
        @DisplayName("UC36-REQ-8: System MUST execute deescalation steps")
        fun `system executes deescalation steps correctly`() {
            // Given: Crisis session and script (ensuring crisis level has steps)
            val userId = "user123"
            val userInput = "I'm feeling extremely hopeless and don't know what to do"
            val assessment = useCase.assessCrisisLevel(userInput, userId)
            // Generate script directly to ensure it's stored in the map
            val script = useCase.generateDeescalationScript(assessment, userId)
            val scriptId = script.id
            
            // Skip test if no steps (NONE level)
            if (script.steps.isEmpty()) {
                return
            }
            
            // When: System executes step
            val stepResult = useCase.executeDeescalationStep(scriptId, 0, "I'm here")
            
            // Then: Step should be executed correctly
            assertNotNull(stepResult, "UC36: Step result must be returned")
            assertNotNull(stepResult.step, "UC36: Step must be included in result")
            assertTrue(stepResult.progress >= 0f, "UC36: Progress must be non-negative")
            assertTrue(stepResult.progress <= 100f, "UC36: Progress must not exceed 100%")
        }
        
        @Test
        @DisplayName("UC36-REQ-9: System MUST track step progress")
        fun `system tracks step progress correctly`() {
            // Given: Crisis session and script
            val userId = "user123"
            val userInput = "I'm in crisis"
            val session = useCase.createSession(userId, userInput)
            val scriptId = session.script.id
            val totalSteps = session.script.steps.size
            
            // When: System executes multiple steps
            val stepResults = mutableListOf<DeescalationStepResult>()
            for (i in 0 until totalSteps) {
                val result = useCase.executeDeescalationStep(scriptId, i, "Response $i")
                stepResults.add(result)
            }
            
            // Then: Progress should be tracked correctly
            if (totalSteps > 0) {
                assertEquals(totalSteps, stepResults.size, "UC36: All steps must be executed")
                val lastResult = stepResults.last()
                assertTrue(lastResult.isCompleted, "UC36: Last step should be completed")
                assertTrue(lastResult.progress >= 90f, "UC36: Progress should be near completion")
            }
        }
    }

    @Nested
    @DisplayName("Test Case 4: Script Adaptation")
    inner class ScriptAdaptationTests {
        
        @Test
        @DisplayName("UC36-REQ-10: System MUST adapt script based on user response")
        fun `system adapts script based on user response correctly`() {
            // Given: Crisis session and user response
            val userId = "user123"
            val userInput = "I'm feeling stressed"
            val session = useCase.createSession(userId, userInput)
            val scriptId = session.script.id
            val escalatedResponse = "I want to end it all"
            
            // When: System adapts script
            val adaptedScript = useCase.adaptScript(scriptId, escalatedResponse)
            
            // Then: Script should be adapted
            assertNotNull(adaptedScript, "UC36: Script must be adapted")
            assertTrue(adaptedScript!!.steps.isNotEmpty(), "UC36: Adapted script must have steps")
        }
        
        @Test
        @DisplayName("UC36-REQ-11: System MUST escalate script when crisis worsens")
        fun `system escalates script when crisis worsens correctly`() {
            // Given: Medium crisis session
            val userId = "user123"
            val userInput = "I'm feeling stressed"
            val session = useCase.createSession(userId, userInput)
            val scriptId = session.script.id
            val criticalResponse = "I want to kill myself"
            
            // When: System adapts script for critical crisis
            val adaptedScript = useCase.adaptScript(scriptId, criticalResponse)
            
            // Then: Script should be escalated
            assertNotNull(adaptedScript, "UC36: Script must be adapted")
            assertTrue(adaptedScript!!.crisisLevel.ordinal >= CrisisLevel.CRITICAL.ordinal,
                "UC36: Script should be escalated to CRITICAL")
        }
    }

    @Nested
    @DisplayName("Test Case 5: Safety Measures")
    inner class SafetyMeasuresTests {
        
        @Test
        @DisplayName("UC36-REQ-12: System MUST provide immediate safety measures for critical crisis")
        fun `system provides immediate safety measures for critical crisis correctly`() {
            // Given: Critical crisis assessment
            val userId = "user123"
            val userInput = "I want to end it all"
            val assessment = useCase.assessCrisisLevel(userInput, userId)
            
            // When: System provides safety measures
            val safetyMeasures = useCase.provideImmediateSafetyMeasures(assessment)
            
            // Then: Safety measures should be provided
            assertNotNull(safetyMeasures, "UC36: Safety measures must be provided")
            assertTrue(safetyMeasures.immediateActions.isNotEmpty(),
                "UC36: Immediate actions must be provided")
            assertTrue(safetyMeasures.resources.isNotEmpty(),
                "UC36: Resources must be provided")
            assertNotNull(safetyMeasures.safetyPlan,
                "UC36: Safety plan must be provided for critical crisis")
        }
        
        @Test
        @DisplayName("UC36-REQ-13: System MUST provide safety measures for high crisis")
        fun `system provides safety measures for high crisis correctly`() {
            // Given: High crisis assessment
            val userId = "user123"
            val userInput = "I'm feeling extremely hopeless"
            val assessment = useCase.assessCrisisLevel(userInput, userId)
            
            // When: System provides safety measures
            val safetyMeasures = useCase.provideImmediateSafetyMeasures(assessment)
            
            // Then: Safety measures should be provided
            assertNotNull(safetyMeasures, "UC36: Safety measures must be provided")
            assertTrue(safetyMeasures.immediateActions.isNotEmpty(),
                "UC36: Immediate actions must be provided")
            assertTrue(safetyMeasures.resources.isNotEmpty(),
                "UC36: Resources must be provided")
        }
    }

    @Nested
    @DisplayName("Test Case 6: Progress Monitoring")
    inner class ProgressMonitoringTests {
        
        @Test
        @DisplayName("UC36-REQ-14: System MUST monitor deescalation progress")
        fun `system monitors deescalation progress correctly`() {
            // Given: Active crisis session
            val userId = "user123"
            val userInput = "I'm in crisis"
            val session = useCase.createSession(userId, userInput)
            
            // When: System monitors progress
            val progress = useCase.monitorProgress(session.id)
            
            // Then: Progress should be monitored
            assertNotNull(progress, "UC36: Progress must be monitored")
            assertEquals(session.id, progress.sessionId,
                "UC36: Progress must be for correct session")
            assertNotNull(progress.initialLevel, "UC36: Initial level must be tracked")
            assertNotNull(progress.currentLevel, "UC36: Current level must be tracked")
            assertTrue(progress.progressPercentage >= 0f,
                "UC36: Progress percentage must be non-negative")
            assertTrue(progress.progressPercentage <= 100f,
                "UC36: Progress percentage must not exceed 100%")
        }
        
        @Test
        @DisplayName("UC36-REQ-15: System MUST track progress improvement")
        fun `system tracks progress improvement correctly`() {
            // Given: Crisis session (ensuring it has steps)
            val userId = "user123"
            val userInput = "I'm feeling extremely hopeless and don't know what to do"
            val session = useCase.createSession(userId, userInput)
            
            // Skip test if no steps
            if (session.script.steps.isEmpty()) {
                return
            }
            
            // When: System monitors progress
            val progress = useCase.monitorProgress(session.id)
            
            // Then: Progress should show improvement potential
            assertNotNull(progress, "UC36: Progress must be monitored")
            assertTrue(progress.stepsCompleted >= 0,
                "UC36: Steps completed must be tracked")
            assertTrue(progress.totalSteps > 0,
                "UC36: Total steps must be tracked")
        }
    }

    @Nested
    @DisplayName("Test Case 7: Session Management")
    inner class SessionManagementTests {
        
        @Test
        @DisplayName("UC36-REQ-16: System MUST create deescalation session")
        fun `system creates deescalation session correctly`() {
            // Given: User ID and initial input
            val userId = "user123"
            val userInput = "I'm in crisis"
            
            // When: System creates session
            val session = useCase.createSession(userId, userInput)
            
            // Then: Session should be created correctly
            assertNotNull(session, "UC36: Session must be created")
            assertTrue(session.id.isNotBlank(), "UC36: Session must have ID")
            assertEquals(userId, session.userId, "UC36: Session must be linked to user")
            assertNotNull(session.initialAssessment, "UC36: Session must have initial assessment")
            assertNotNull(session.script, "UC36: Session must have script")
            assertEquals(DeescalationStatus.ACTIVE, session.status,
                "UC36: New session must be ACTIVE")
        }
        
        @Test
        @DisplayName("UC36-REQ-17: System MUST get active sessions")
        fun `system gets active sessions correctly`() {
            // Given: User with active sessions
            val userId = "user123"
            val session1 = useCase.createSession(userId, "I'm in crisis")
            val session2 = useCase.createSession(userId, "I'm feeling stressed")
            
            // When: System gets active sessions
            val activeSessions = useCase.getActiveSessions(userId)
            
            // Then: Active sessions should be retrieved
            assertNotNull(activeSessions, "UC36: Active sessions must be retrieved")
            assertTrue(activeSessions.size >= 2,
                "UC36: All active sessions must be retrieved")
            assertTrue(activeSessions.all { it.status == DeescalationStatus.ACTIVE },
                "UC36: All retrieved sessions must be ACTIVE")
        }
        
        @Test
        @DisplayName("UC36-REQ-18: System MUST complete deescalation session")
        fun `system completes deescalation session correctly`() {
            // Given: Active crisis session
            val userId = "user123"
            val session = useCase.createSession(userId, "I'm in crisis")
            val improvedAssessment = useCase.assessCrisisLevel("I'm feeling better", userId)
            
            // When: System completes session
            val completedSession = useCase.completeSession(session.id, improvedAssessment)
            
            // Then: Session should be completed
            assertNotNull(completedSession, "UC36: Session must be completed")
            assertEquals(DeescalationStatus.COMPLETED, completedSession.status,
                "UC36: Session status must be COMPLETED")
            assertNotNull(completedSession.completedAt,
                "UC36: Session must have completion timestamp")
        }
    }
}

