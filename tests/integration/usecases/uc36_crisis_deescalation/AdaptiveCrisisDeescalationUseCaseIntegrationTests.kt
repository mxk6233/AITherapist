package com.serenityai.tests.integration.usecases.uc36_crisis_deescalation

import com.serenityai.usecases.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

/**
 * UC36: Adaptive Crisis Deescalation Scripts - Integration Tests
 * 
 * Integration tests verify that Crisis Deescalation integrates correctly
 * with crisis assessment service, script generation, safety planning,
 * and session management services.
 */
@DisplayName("UC36: Adaptive Crisis Deescalation Scripts - Integration Tests")
class AdaptiveCrisisDeescalationUseCaseIntegrationTests {

    @Nested
    @DisplayName("Integration Test 1: Crisis Assessment Integration")
    inner class CrisisAssessmentIntegrationTests {
        
        @Test
        @DisplayName("Should integrate crisis assessment with script generation")
        fun `crisis assessment integrates with script generation`() {
            // Given: Use case and user input
            val useCase = AdaptiveCrisisDeescalationUseCase()
            val userId = "user123"
            val userInput = "I'm in crisis"
            
            // When: System integrates assessment with script generation
            val assessment = useCase.assessCrisisLevel(userInput, userId)
            val script = useCase.generateDeescalationScript(assessment, userId)
            
            // Then: Integration works correctly
            assertNotNull(assessment, "UC36 Integration: Assessment must be performed")
            assertNotNull(script, "UC36 Integration: Script must be generated")
            assertEquals(assessment.level, script.crisisLevel,
                "UC36 Integration: Script level must match assessment level")
            assertTrue(script.steps.isNotEmpty(),
                "UC36 Integration: Script must have steps")
        }
    }

    @Nested
    @DisplayName("Integration Test 2: Script Execution Integration")
    inner class ScriptExecutionIntegrationTests {
        
        @Test
        @DisplayName("Should integrate script execution with progress monitoring")
        fun `script execution integrates with progress monitoring`() {
            // Given: Use case and crisis session
            val useCase = AdaptiveCrisisDeescalationUseCase()
            val userId = "user123"
            val session = useCase.createSession(userId, "I'm in crisis")
            val scriptId = session.script.id
            
            // When: System integrates script execution
            val stepResult = useCase.executeDeescalationStep(scriptId, 0, "I'm here")
            val progress = useCase.monitorProgress(session.id)
            
            // Then: Integration works correctly
            assertNotNull(stepResult, "UC36 Integration: Step must be executed")
            assertNotNull(progress, "UC36 Integration: Progress must be monitored")
            assertTrue(progress.stepsCompleted >= 0,
                "UC36 Integration: Steps completed must be tracked")
        }
    }

    @Nested
    @DisplayName("Integration Test 3: Safety Measures Integration")
    inner class SafetyMeasuresIntegrationTests {
        
        @Test
        @DisplayName("Should integrate crisis assessment with safety measures")
        fun `crisis assessment integrates with safety measures`() {
            // Given: Use case and critical crisis input
            val useCase = AdaptiveCrisisDeescalationUseCase()
            val userId = "user123"
            val userInput = "I want to end it all"
            
            // When: System integrates assessment with safety measures
            val assessment = useCase.assessCrisisLevel(userInput, userId)
            val safetyMeasures = useCase.provideImmediateSafetyMeasures(assessment)
            
            // Then: Integration works correctly
            assertNotNull(assessment, "UC36 Integration: Assessment must be performed")
            assertNotNull(safetyMeasures, "UC36 Integration: Safety measures must be provided")
            assertTrue(safetyMeasures.immediateActions.isNotEmpty(),
                "UC36 Integration: Immediate actions must be provided")
            assertTrue(safetyMeasures.resources.isNotEmpty(),
                "UC36 Integration: Resources must be provided")
        }
    }

    @Nested
    @DisplayName("Integration Test 4: Script Adaptation Integration")
    inner class ScriptAdaptationIntegrationTests {
        
        @Test
        @DisplayName("Should integrate script adaptation with user responses")
        fun `script adaptation integrates with user responses`() {
            // Given: Use case and crisis session
            val useCase = AdaptiveCrisisDeescalationUseCase()
            val userId = "user123"
            val session = useCase.createSession(userId, "I'm feeling stressed")
            val scriptId = session.script.id
            
            // When: System integrates script adaptation
            val escalatedResponse = "I want to end it all"
            val adaptedScript = useCase.adaptScript(scriptId, escalatedResponse)
            
            // Then: Integration works correctly
            assertNotNull(adaptedScript, "UC36 Integration: Script must be adapted")
            assertTrue(adaptedScript!!.steps.isNotEmpty(),
                "UC36 Integration: Adapted script must have steps")
        }
    }

    @Nested
    @DisplayName("Integration Test 5: End-to-End Deescalation Flow")
    inner class EndToEndFlowTests {
        
        @Test
        @DisplayName("Should integrate complete deescalation flow")
        fun `complete deescalation flow integrates correctly`() {
            // Given: Use case and crisis scenario
            val useCase = AdaptiveCrisisDeescalationUseCase()
            val userId = "user123"
            val initialInput = "I'm in crisis"
            
            // When: System processes complete flow
            val session = useCase.createSession(userId, initialInput)
            val scriptId = session.script.id
            
            // Execute steps
            val stepResults = mutableListOf<DeescalationStepResult>()
            for (i in 0 until session.script.steps.size) {
                val result = useCase.executeDeescalationStep(scriptId, i, "Response $i")
                stepResults.add(result)
            }
            
            // Monitor progress
            val progress = useCase.monitorProgress(session.id)
            
            // Complete session
            val finalAssessment = useCase.assessCrisisLevel("I'm feeling better", userId)
            val completedSession = useCase.completeSession(session.id, finalAssessment)
            
            // Then: Complete flow works correctly
            assertNotNull(session, "UC36 Integration: Session must be created")
            assertEquals(stepResults.size, session.script.steps.size,
                "UC36 Integration: All steps must be executed")
            assertNotNull(progress, "UC36 Integration: Progress must be monitored")
            assertNotNull(completedSession, "UC36 Integration: Session must be completed")
            assertEquals(DeescalationStatus.COMPLETED, completedSession.status,
                "UC36 Integration: Session must be marked as COMPLETED")
        }
    }
}

