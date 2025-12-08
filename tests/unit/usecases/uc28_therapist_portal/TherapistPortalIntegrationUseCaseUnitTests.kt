package com.serenityai.tests.unit.usecases.uc28_therapist_portal

import com.serenityai.usecases.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import java.util.Date

@DisplayName("UC28: Therapist Portal Integration - Unit Tests")
class TherapistPortalIntegrationUseCaseUnitTests {

    private lateinit var useCase: TherapistPortalIntegrationUseCase

    @BeforeEach
    fun setUp() {
        useCase = TherapistPortalIntegrationUseCase()
    }

    @Nested
    @DisplayName("Test Case 1: Patient Assignment - Validates Core UC28 Functionality")
    inner class PatientAssignmentTests {
        
        @Test
        @DisplayName("UC28-REQ-1: System MUST assign patient to therapist")
        fun `system assigns patient to therapist correctly`() {
            val therapistId = "therapist123"
            val patientId = "patient123"
            
            val result = useCase.assignPatientToTherapist(therapistId, patientId)
            
            assertTrue(result, "UC28: Patient assignment must succeed")
            assertEquals(therapistId, useCase.getPatientTherapist(patientId), "UC28: Patient must be assigned to therapist")
        }
        
        @Test
        @DisplayName("UC28-REQ-2: System MUST prevent duplicate assignments")
        fun `system prevents duplicate patient assignments`() {
            val therapistId = "therapist123"
            val patientId = "patient123"
            
            useCase.assignPatientToTherapist(therapistId, patientId)
            val duplicateResult = useCase.assignPatientToTherapist(therapistId, patientId)
            
            assertFalse(duplicateResult, "UC28: Duplicate assignment must be prevented")
        }
        
        @Test
        @DisplayName("UC28-REQ-3: System MUST retrieve therapist's patients")
        fun `system retrieves therapist patients correctly`() {
            val therapistId = "therapist123"
            useCase.assignPatientToTherapist(therapistId, "patient1")
            useCase.assignPatientToTherapist(therapistId, "patient2")
            
            val patients = useCase.getTherapistPatients(therapistId)
            
            assertEquals(2, patients.size, "UC28: All patients must be retrieved")
            assertTrue(patients.contains("patient1"), "UC28: Patient1 must be in list")
            assertTrue(patients.contains("patient2"), "UC28: Patient2 must be in list")
        }
    }

    @Nested
    @DisplayName("Test Case 2: Therapist Notes - Validates Secondary UC28 Functionality")
    inner class TherapistNotesTests {
        
        @Test
        @DisplayName("UC28-REQ-4: System MUST add therapist notes")
        fun `system adds therapist notes correctly`() {
            val therapistId = "therapist123"
            val patientId = "patient123"
            useCase.assignPatientToTherapist(therapistId, patientId)
            
            val note = useCase.addTherapistNote(therapistId, patientId, "Patient showing improvement", "observation")
            
            assertNotNull(note.id, "UC28: Note must have unique ID")
            assertEquals("Patient showing improvement", note.content, "UC28: Note content must be stored")
        }
        
        @Test
        @DisplayName("UC28-REQ-5: System MUST retrieve therapist notes")
        fun `system retrieves therapist notes correctly`() {
            val therapistId = "therapist123"
            val patientId = "patient123"
            useCase.assignPatientToTherapist(therapistId, patientId)
            useCase.addTherapistNote(therapistId, patientId, "Note 1", "observation")
            useCase.addTherapistNote(therapistId, patientId, "Note 2", "recommendation")
            
            val notes = useCase.getTherapistNotes(therapistId, patientId)
            
            assertEquals(2, notes.size, "UC28: All notes must be retrieved")
        }
    }

    @Nested
    @DisplayName("Test Case 3: Progress Reports - Validates Tertiary UC28 Functionality")
    inner class ProgressReportsTests {
        
        @Test
        @DisplayName("UC28-REQ-6: System MUST generate patient progress report")
        fun `system generates patient progress report correctly`() {
            val therapistId = "therapist123"
            val patientId = "patient123"
            useCase.assignPatientToTherapist(therapistId, patientId)
            useCase.addTherapistNote(therapistId, patientId, "Note 1", "observation")
            
            val report = useCase.generatePatientProgressReport(
                therapistId, patientId, Date(), Date()
            )
            
            assertNotNull(report, "UC28: Report must be generated")
            assertEquals(patientId, report.patientId, "UC28: Report must be for correct patient")
            assertEquals(1, report.totalTherapistNotes, "UC28: Report must include note count")
        }
    }
}


