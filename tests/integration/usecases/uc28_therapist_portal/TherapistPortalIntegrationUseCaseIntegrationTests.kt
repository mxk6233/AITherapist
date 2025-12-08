package com.serenityai.tests.integration.usecases.uc28_therapist_portal

import com.serenityai.usecases.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

@DisplayName("UC28: Therapist Portal Integration - Integration Tests")
class TherapistPortalIntegrationUseCaseIntegrationTests {

    @Nested
    @DisplayName("Integration Test 1: Portal with Patient Data")
    inner class PatientDataIntegrationTests {
        
        @Test
        @DisplayName("Should integrate portal with patient data access")
        fun `patient data accessed through portal integration`() {
            val useCase = TherapistPortalIntegrationUseCase()
            useCase.assignPatientToTherapist("therapist123", "patient123")
            
            val patients = useCase.getTherapistPatients("therapist123")
            
            assertTrue(patients.contains("patient123"), "UC28 Integration: Patient data must be accessible")
        }
    }

    @Nested
    @DisplayName("Integration Test 2: Notes with Storage")
    inner class StorageIntegrationTests {
        
        @Test
        @DisplayName("Should integrate notes with storage system")
        fun `notes persisted through storage integration`() {
            val useCase = TherapistPortalIntegrationUseCase()
            useCase.assignPatientToTherapist("therapist123", "patient123")
            val note = useCase.addTherapistNote("therapist123", "patient123", "Patient improving", "observation")
            
            val notes = useCase.getTherapistNotes("therapist123", "patient123")
            
            assertTrue(notes.isNotEmpty(), "UC28 Integration: Notes must be persisted")
            assertEquals(note.id, notes.first().id, "UC28 Integration: Notes must be retrievable")
        }
    }

    @Nested
    @DisplayName("Integration Test 3: Reports with Analytics")
    inner class AnalyticsIntegrationTests {
        
        @Test
        @DisplayName("Should integrate reports with analytics system")
        fun `reports generated through analytics integration`() {
            val useCase = TherapistPortalIntegrationUseCase()
            useCase.assignPatientToTherapist("therapist123", "patient123")
            useCase.addTherapistNote("therapist123", "patient123", "Note", "observation")
            
            val report = useCase.generatePatientProgressReport("therapist123", "patient123", java.util.Date(), java.util.Date())
            
            assertNotNull(report, "UC28 Integration: Report must be generated")
            assertEquals("patient123", report.patientId, "UC28 Integration: Report must be for correct patient")
        }
    }
}


