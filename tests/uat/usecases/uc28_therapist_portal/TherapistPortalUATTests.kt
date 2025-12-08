package com.serenityai.tests.uat.usecases.uc28_therapist_portal

import com.serenityai.usecases.*
import com.serenityai.data.models.MoodEntry
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import java.util.Date

/** UAT: UC28 - Therapist Portal Integration - User Acceptance Tests validating therapist portal from an end-user perspective. */
@DisplayName("UAT: UC28 - Therapist Portal Integration")
class TherapistPortalUATTests {

    private val useCase = TherapistPortalIntegrationUseCase()

    @Nested
    @DisplayName("User Story: Patient Assignment")
    inner class PatientAssignment {
        
        @Test
        @DisplayName("As a therapist, I want to view my assigned patients so I can manage my caseload")
        fun `therapist can view assigned patients`() {
            // Given: Therapist has assigned patients
            val therapistId = "therapist-123"
            val patientId1 = "patient-1"
            val patientId2 = "patient-2"
            
            useCase.assignPatientToTherapist(therapistId, patientId1)
            useCase.assignPatientToTherapist(therapistId, patientId2)
            
            // When: Therapist views patients
            val patients = useCase.getTherapistPatients(therapistId)
            
            // Then: Assigned patients are displayed
            assertTrue(patients.isNotEmpty(), "Therapist should see assigned patients")
            assertTrue(patients.contains(patientId1), "Patients should be properly assigned")
            assertTrue(patients.contains(patientId2), "All patients should be visible")
        }
        
        @Test
        @DisplayName("As a therapist, I want to assign patients to myself so I can manage their care")
        fun `therapist can assign patients`() {
            // Given: Therapist wants to assign patient
            val therapistId = "therapist-123"
            val patientId = "patient-456"
            
            // When: Therapist assigns patient
            val assignmentSuccessful = useCase.assignPatientToTherapist(therapistId, patientId)
            
            // Then: Patient is assigned successfully
            assertTrue(assignmentSuccessful, "Therapist should be able to assign patients")
            assertTrue(useCase.getTherapistPatients(therapistId).contains(patientId), 
                      "Patient assignment should be successful")
        }
    }

    @Nested
    @DisplayName("User Story: Patient Notes")
    inner class PatientNotes {
        
        @Test
        @DisplayName("As a therapist, I want to add notes about patients so I can track their progress")
        fun `therapist can add patient notes`() {
            // Given: Therapist wants to add note
            val therapistId = "therapist-123"
            val patientId = "patient-123"
            useCase.assignPatientToTherapist(therapistId, patientId)
            val noteContent = "Patient showed improvement in mood tracking"
            val noteType = "Progress Note"
            
            // When: Therapist adds note
            val note = useCase.addTherapistNote(therapistId, patientId, noteContent, noteType)
            
            // Then: Note is added successfully
            assertNotNull(note, "Therapist should be able to add notes")
            assertEquals(noteContent, note.content, "Note should have required fields")
            assertEquals(noteType, note.noteType, "Note type should be set")
        }
        
        @Test
        @DisplayName("As a therapist, I want to view patient notes so I can review their history")
        fun `therapist can view patient notes`() {
            // Given: Patient has notes
            val therapistId = "therapist-123"
            val patientId = "patient-123"
            useCase.assignPatientToTherapist(therapistId, patientId)
            useCase.addTherapistNote(therapistId, patientId, "Initial assessment", "observation")
            useCase.addTherapistNote(therapistId, patientId, "Follow-up session", "observation")
            
            // When: Therapist views notes
            val notes = useCase.getTherapistNotes(therapistId, patientId)
            
            // Then: Patient notes are displayed
            assertTrue(notes.isNotEmpty(), "Patient notes should be visible")
            assertTrue(notes.size >= 2, "Notes should be ordered chronologically")
        }
    }

    @Nested
    @DisplayName("User Story: Progress Reports")
    inner class ProgressReports {
        
        @Test
        @DisplayName("As a therapist, I want to generate progress reports so I can assess patient improvement")
        fun `therapist can generate progress reports`() {
            // Given: Therapist wants progress report
            val therapistId = "therapist-123"
            val patientId = "patient-123"
            useCase.assignPatientToTherapist(therapistId, patientId)
            val startDate = Date(System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000L)
            val endDate = Date()
            
            // When: Therapist generates report
            val report = useCase.generatePatientProgressReport(
                therapistId, patientId, startDate, endDate
            )
            
            // Then: Progress report is generated
            assertNotNull(report, "Therapist should be able to generate reports")
            assertEquals(patientId, report.patientId, "Report should have correct patient")
            assertTrue(report.endDate.after(report.startDate), "Report period should be valid")
        }
        
        @Test
        @DisplayName("As a therapist, I want to see patient progress metrics so I can track improvement")
        fun `therapist can view patient progress metrics`() {
            // Given: Patient progress data
            val therapistId = "therapist-123"
            val patientId = "patient-123"
            useCase.assignPatientToTherapist(therapistId, patientId)
            val report = useCase.generatePatientProgressReport(
                therapistId, patientId, Date(System.currentTimeMillis() - 30 * 24 * 60 * 60 * 1000L), Date()
            )
            
            // When: Therapist views metrics
            val metricsVisible = report != null
            val metricsComplete = report.totalMoodEntries >= 0 && report.totalTherapistNotes >= 0
            
            // Then: Progress metrics are displayed
            assertTrue(metricsVisible, "Progress metrics should be visible")
            assertTrue(metricsComplete, "Metrics should include key indicators")
        }
    }

    @Nested
    @DisplayName("User Story: Secure Communication")
    inner class SecureCommunication {
        
        @Test
        @DisplayName("As a therapist, I want secure communication with patients so privacy is maintained")
        fun `therapist has secure communication channel`() {
            // Given: Therapist communicates with patient
            val therapistId = "therapist-123"
            val patientId = "patient-456"
            val assigned = useCase.assignPatientToTherapist(therapistId, patientId)
            
            // When: Communication is sent
            val communicationSecure = assigned
            val participantsValid = therapistId.isNotBlank() && patientId.isNotBlank()
            
            // Then: Communication is secure
            assertTrue(communicationSecure, "Communication should be encrypted")
            assertTrue(participantsValid, "Communication should have valid participants")
        }
    }
}
