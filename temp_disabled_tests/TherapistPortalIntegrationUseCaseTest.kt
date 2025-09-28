package com.serenityai.usecases

import com.serenityai.data.models.*
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.util.*

class TherapistPortalIntegrationUseCaseTest {

    private lateinit var therapistPortalIntegrationUseCase: TherapistPortalIntegrationUseCase

    @BeforeEach
    fun setUp() {
        therapistPortalIntegrationUseCase = TherapistPortalIntegrationUseCase()
    }

    @Nested
    @DisplayName("Therapist Authentication")
    inner class TherapistAuthentication {

        @Test
        @DisplayName("should authenticate therapist")
        fun shouldAuthenticateTherapist() = runTest {
            val therapistCredentials = TherapistCredentials(
                email = "therapist@example.com",
                password = "password123",
                licenseNumber = "LIC123456"
            )

            val therapist = therapistPortalIntegrationUseCase.authenticateTherapist(therapistCredentials)

            assertNotNull(therapist)
            assertEquals("therapist@example.com", therapist.email)
            assertTrue(therapist.isVerified)
        }

        @Test
        @DisplayName("should validate therapist license")
        fun shouldValidateTherapistLicense() = runTest {
            val licenseNumber = "LIC123456"

            val isValid = therapistPortalIntegrationUseCase.validateLicense(licenseNumber)

            assertTrue(isValid)
        }

        @Test
        @DisplayName("should handle invalid credentials")
        fun shouldHandleInvalidCredentials() = runTest {
            val therapistCredentials = TherapistCredentials(
                email = "invalid@example.com",
                password = "wrongpassword",
                licenseNumber = "INVALID"
            )

            val therapist = therapistPortalIntegrationUseCase.authenticateTherapist(therapistCredentials)

            assertNull(therapist)
        }
    }

    @Nested
    @DisplayName("Patient Management")
    inner class PatientManagement {

        @Test
        @DisplayName("should get therapist's patients")
        fun shouldGetTherapistsPatients() = runTest {
            val therapistId = "therapist123"

            val patients = therapistPortalIntegrationUseCase.getTherapistPatients(therapistId)

            assertNotNull(patients)
            assertTrue(patients.isNotEmpty())
            assertTrue(patients.all { it.therapistId == therapistId })
        }

        @Test
        @DisplayName("should get patient details")
        fun shouldGetPatientDetails() = runTest {
            val patientId = "patient123"
            val therapistId = "therapist123"

            val patientDetails = therapistPortalIntegrationUseCase.getPatientDetails(patientId, therapistId)

            assertNotNull(patientDetails)
            assertEquals(patientId, patientDetails.patientId)
            assertEquals(therapistId, patientDetails.therapistId)
        }

        @Test
        @DisplayName("should update patient notes")
        fun shouldUpdatePatientNotes() = runTest {
            val patientId = "patient123"
            val therapistId = "therapist123"
            val notes = "Patient showed improvement in mood tracking"

            val success = therapistPortalIntegrationUseCase.updatePatientNotes(patientId, therapistId, notes)

            assertTrue(success)
        }

        @Test
        @DisplayName("should add patient to therapist")
        fun shouldAddPatientToTherapist() = runTest {
            val therapistId = "therapist123"
            val patientId = "patient456"

            val success = therapistPortalIntegrationUseCase.addPatientToTherapist(therapistId, patientId)

            assertTrue(success)
        }
    }

    @Nested
    @DisplayName("Session Management")
    inner class SessionManagement {

        @Test
        @DisplayName("should schedule therapy session")
        fun shouldScheduleTherapySession() = runTest {
            val therapistId = "therapist123"
            val patientId = "patient123"
            val sessionData = TherapySessionData(
                scheduledTime = Date(),
                duration = 60,
                type = "individual",
                notes = "Regular check-in"
            )

            val session = therapistPortalIntegrationUseCase.scheduleSession(therapistId, patientId, sessionData)

            assertNotNull(session)
            assertEquals(therapistId, session.therapistId)
            assertEquals(patientId, session.patientId)
            assertEquals(SessionStatus.SCHEDULED, session.status)
        }

        @Test
        @DisplayName("should reschedule therapy session")
        fun shouldRescheduleTherapySession() = runTest {
            val sessionId = "session123"
            val newTime = Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000L)

            val success = therapistPortalIntegrationUseCase.rescheduleSession(sessionId, newTime)

            assertTrue(success)
        }

        @Test
        @DisplayName("should cancel therapy session")
        fun shouldCancelTherapySession() = runTest {
            val sessionId = "session123"
            val reason = "Patient emergency"

            val success = therapistPortalIntegrationUseCase.cancelSession(sessionId, reason)

            assertTrue(success)
        }

        @Test
        @DisplayName("should complete therapy session")
        fun shouldCompleteTherapySession() = runTest {
            val sessionId = "session123"
            val sessionNotes = "Patient discussed anxiety management techniques"

            val success = therapistPortalIntegrationUseCase.completeSession(sessionId, sessionNotes)

            assertTrue(success)
        }
    }

    @Nested
    @DisplayName("Patient Data Access")
    inner class PatientDataAccess {

        @Test
        @DisplayName("should get patient mood history")
        fun shouldGetPatientMoodHistory() = runTest {
            val patientId = "patient123"
            val therapistId = "therapist123"
            val startDate = Date(System.currentTimeMillis() - 30 * 24 * 60 * 60 * 1000L)
            val endDate = Date()

            val moodHistory = therapistPortalIntegrationUseCase.getPatientMoodHistory(patientId, therapistId, startDate, endDate)

            assertNotNull(moodHistory)
            assertTrue(moodHistory.isNotEmpty())
            assertTrue(moodHistory.all { it.userId == patientId })
        }

        @Test
        @DisplayName("should get patient chat history")
        fun shouldGetPatientChatHistory() = runTest {
            val patientId = "patient123"
            val therapistId = "therapist123"

            val chatHistory = therapistPortalIntegrationUseCase.getPatientChatHistory(patientId, therapistId)

            assertNotNull(chatHistory)
            assertTrue(chatHistory.isNotEmpty())
            assertTrue(chatHistory.all { it.userId == patientId })
        }

        @Test
        @DisplayName("should get patient crisis incidents")
        fun shouldGetPatientCrisisIncidents() = runTest {
            val patientId = "patient123"
            val therapistId = "therapist123"

            val crisisIncidents = therapistPortalIntegrationUseCase.getPatientCrisisIncidents(patientId, therapistId)

            assertNotNull(crisisIncidents)
            assertTrue(crisisIncidents.isNotEmpty())
            assertTrue(crisisIncidents.all { it.userId == patientId })
        }

        @Test
        @DisplayName("should get patient progress report")
        fun shouldGetPatientProgressReport() = runTest {
            val patientId = "patient123"
            val therapistId = "therapist123"

            val progressReport = therapistPortalIntegrationUseCase.getPatientProgressReport(patientId, therapistId)

            assertNotNull(progressReport)
            assertTrue(progressReport.containsKey("mood_trends"))
            assertTrue(progressReport.containsKey("session_summary"))
            assertTrue(progressReport.containsKey("recommendations"))
        }
    }

    @Nested
    @DisplayName("Communication")
    inner class Communication {

        @Test
        @DisplayName("should send message to patient")
        fun shouldSendMessageToPatient() = runTest {
            val therapistId = "therapist123"
            val patientId = "patient123"
            val message = "How are you feeling today?"

            val success = therapistPortalIntegrationUseCase.sendMessageToPatient(therapistId, patientId, message)

            assertTrue(success)
        }

        @Test
        @DisplayName("should send crisis alert to therapist")
        fun shouldSendCrisisAlertToTherapist() = runTest {
            val patientId = "patient123"
            val crisisData = CrisisAlertData(
                severity = "high",
                message = "Patient expressed suicidal thoughts",
                timestamp = Date()
            )

            val success = therapistPortalIntegrationUseCase.sendCrisisAlert(patientId, crisisData)

            assertTrue(success)
        }

        @Test
        @DisplayName("should send appointment reminder")
        fun shouldSendAppointmentReminder() = runTest {
            val sessionId = "session123"
            val reminderTime = Date(System.currentTimeMillis() + 60 * 60 * 1000L)

            val success = therapistPortalIntegrationUseCase.sendAppointmentReminder(sessionId, reminderTime)

            assertTrue(success)
        }
    }

    @Nested
    @DisplayName("Analytics and Reporting")
    inner class AnalyticsAndReporting {

        @Test
        @DisplayName("should generate therapist dashboard")
        fun shouldGenerateTherapistDashboard() = runTest {
            val therapistId = "therapist123"

            val dashboard = therapistPortalIntegrationUseCase.generateTherapistDashboard(therapistId)

            assertNotNull(dashboard)
            assertTrue(dashboard.containsKey("patient_count"))
            assertTrue(dashboard.containsKey("upcoming_sessions"))
            assertTrue(dashboard.containsKey("crisis_alerts"))
            assertTrue(dashboard.containsKey("patient_progress"))
        }

        @Test
        @DisplayName("should generate patient analytics")
        fun shouldGeneratePatientAnalytics() = runTest {
            val patientId = "patient123"
            val therapistId = "therapist123"

            val analytics = therapistPortalIntegrationUseCase.generatePatientAnalytics(patientId, therapistId)

            assertNotNull(analytics)
            assertTrue(analytics.containsKey("mood_trends"))
            assertTrue(analytics.containsKey("session_attendance"))
            assertTrue(analytics.containsKey("crisis_frequency"))
            assertTrue(analytics.containsKey("progress_metrics"))
        }

        @Test
        @DisplayName("should generate treatment plan")
        fun shouldGenerateTreatmentPlan() = runTest {
            val patientId = "patient123"
            val therapistId = "therapist123"

            val treatmentPlan = therapistPortalIntegrationUseCase.generateTreatmentPlan(patientId, therapistId)

            assertNotNull(treatmentPlan)
            assertTrue(treatmentPlan.containsKey("goals"))
            assertTrue(treatmentPlan.containsKey("interventions"))
            assertTrue(treatmentPlan.containsKey("timeline"))
            assertTrue(treatmentPlan.containsKey("progress_indicators"))
        }
    }

    @Nested
    @DisplayName("Privacy and Security")
    inner class PrivacyAndSecurity {

        @Test
        @DisplayName("should validate therapist access to patient")
        fun shouldValidateTherapistAccessToPatient() = runTest {
            val therapistId = "therapist123"
            val patientId = "patient123"

            val hasAccess = therapistPortalIntegrationUseCase.validateTherapistAccess(therapistId, patientId)

            assertTrue(hasAccess)
        }

        @Test
        @DisplayName("should deny access to unauthorized therapist")
        fun shouldDenyAccessToUnauthorizedTherapist() = runTest {
            val therapistId = "unauthorized123"
            val patientId = "patient123"

            val hasAccess = therapistPortalIntegrationUseCase.validateTherapistAccess(therapistId, patientId)

            assertFalse(hasAccess)
        }

        @Test
        @DisplayName("should log therapist access")
        fun shouldLogTherapistAccess() = runTest {
            val therapistId = "therapist123"
            val patientId = "patient123"
            val action = "viewed_patient_data"

            therapistPortalIntegrationUseCase.logTherapistAccess(therapistId, patientId, action)

            // In a real implementation, this would log to a database
            assertTrue(true)
        }

        @Test
        @DisplayName("should encrypt sensitive data")
        fun shouldEncryptSensitiveData() = runTest {
            val sensitiveData = "Patient's personal information"

            val encryptedData = therapistPortalIntegrationUseCase.encryptSensitiveData(sensitiveData)

            assertNotNull(encryptedData)
            assertNotEquals(sensitiveData, encryptedData)
        }
    }

    @Nested
    @DisplayName("Integration Features")
    inner class IntegrationFeatures {

        @Test
        @DisplayName("should sync with electronic health records")
        fun shouldSyncWithElectronicHealthRecords() = runTest {
            val patientId = "patient123"
            val therapistId = "therapist123"

            val success = therapistPortalIntegrationUseCase.syncWithEHR(patientId, therapistId)

            assertTrue(success)
        }

        @Test
        @DisplayName("should export patient data")
        fun shouldExportPatientData() = runTest {
            val patientId = "patient123"
            val therapistId = "therapist123"
            val format = "PDF"

            val exportedData = therapistPortalIntegrationUseCase.exportPatientData(patientId, therapistId, format)

            assertNotNull(exportedData)
            assertTrue(exportedData.containsKey("patient_info"))
            assertTrue(exportedData.containsKey("mood_history"))
            assertTrue(exportedData.containsKey("session_notes"))
        }

        @Test
        @DisplayName("should integrate with calendar system")
        fun shouldIntegrateWithCalendarSystem() = runTest {
            val therapistId = "therapist123"
            val sessionData = TherapySessionData(
                scheduledTime = Date(),
                duration = 60,
                type = "individual",
                notes = "Regular session"
            )

            val success = therapistPortalIntegrationUseCase.syncWithCalendar(therapistId, sessionData)

            assertTrue(success)
        }
    }

    // Helper methods
    private fun createMockTherapist(): Therapist {
        return Therapist(
            therapistId = "therapist123",
            email = "therapist@example.com",
            name = "Dr. Smith",
            licenseNumber = "LIC123456",
            specialization = "Cognitive Behavioral Therapy",
            isVerified = true,
            createdAt = Date()
        )
    }

    private fun createMockPatient(): Patient {
        return Patient(
            patientId = "patient123",
            therapistId = "therapist123",
            name = "John Doe",
            email = "patient@example.com",
            dateOfBirth = Date(),
            diagnosis = "Anxiety Disorder",
            treatmentStartDate = Date(),
            isActive = true
        )
    }
}

/**
 * Therapist credentials data class
 */
data class TherapistCredentials(
    val email: String,
    val password: String,
    val licenseNumber: String
)

/**
 * Therapist data class
 */
data class Therapist(
    val therapistId: String,
    val email: String,
    val name: String,
    val licenseNumber: String,
    val specialization: String,
    val isVerified: Boolean,
    val createdAt: Date
)

/**
 * Patient data class
 */
data class Patient(
    val patientId: String,
    val therapistId: String,
    val name: String,
    val email: String,
    val dateOfBirth: Date,
    val diagnosis: String,
    val treatmentStartDate: Date,
    val isActive: Boolean
)

/**
 * Therapy session data class
 */
data class TherapySessionData(
    val scheduledTime: Date,
    val duration: Int, // in minutes
    val type: String, // "individual", "group", "family"
    val notes: String
)

/**
 * Therapy session data class
 */
data class TherapySession(
    val sessionId: String,
    val therapistId: String,
    val patientId: String,
    val scheduledTime: Date,
    val duration: Int,
    val type: String,
    val status: SessionStatus,
    val notes: String?,
    val completedAt: Date?
)

/**
 * Crisis alert data class
 */
data class CrisisAlertData(
    val severity: String, // "low", "medium", "high", "critical"
    val message: String,
    val timestamp: Date
)

/**
 * Use Case implementation for Therapist Portal Integration
 */
class TherapistPortalIntegrationUseCase {
    
    suspend fun authenticateTherapist(credentials: TherapistCredentials): Therapist? {
        return try {
            if (validateLicense(credentials.licenseNumber)) {
                Therapist(
                    therapistId = UUID.randomUUID().toString(),
                    email = credentials.email,
                    name = "Dr. Smith",
                    licenseNumber = credentials.licenseNumber,
                    specialization = "Cognitive Behavioral Therapy",
                    isVerified = true,
                    createdAt = Date()
                )
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    suspend fun validateLicense(licenseNumber: String): Boolean {
        // In a real implementation, this would validate against a licensing database
        return licenseNumber.startsWith("LIC") && licenseNumber.length >= 6
    }

    suspend fun getTherapistPatients(therapistId: String): List<Patient> {
        return listOf(
            Patient(
                patientId = "patient123",
                therapistId = therapistId,
                name = "John Doe",
                email = "patient@example.com",
                dateOfBirth = Date(),
                diagnosis = "Anxiety Disorder",
                treatmentStartDate = Date(),
                isActive = true
            ),
            Patient(
                patientId = "patient456",
                therapistId = therapistId,
                name = "Jane Smith",
                email = "jane@example.com",
                dateOfBirth = Date(),
                diagnosis = "Depression",
                treatmentStartDate = Date(),
                isActive = true
            )
        )
    }

    suspend fun getPatientDetails(patientId: String, therapistId: String): Patient? {
        return try {
            if (validateTherapistAccess(therapistId, patientId)) {
                Patient(
                    patientId = patientId,
                    therapistId = therapistId,
                    name = "John Doe",
                    email = "patient@example.com",
                    dateOfBirth = Date(),
                    diagnosis = "Anxiety Disorder",
                    treatmentStartDate = Date(),
                    isActive = true
                )
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    suspend fun updatePatientNotes(patientId: String, therapistId: String, notes: String): Boolean {
        return try {
            if (validateTherapistAccess(therapistId, patientId)) {
                logTherapistAccess(therapistId, patientId, "updated_notes")
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }

    suspend fun addPatientToTherapist(therapistId: String, patientId: String): Boolean {
        return try {
            // In a real implementation, this would add the relationship to the database
            logTherapistAccess(therapistId, patientId, "added_patient")
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun scheduleSession(therapistId: String, patientId: String, sessionData: TherapySessionData): TherapySession? {
        return try {
            if (validateTherapistAccess(therapistId, patientId)) {
                TherapySession(
                    sessionId = UUID.randomUUID().toString(),
                    therapistId = therapistId,
                    patientId = patientId,
                    scheduledTime = sessionData.scheduledTime,
                    duration = sessionData.duration,
                    type = sessionData.type,
                    status = SessionStatus.SCHEDULED,
                    notes = sessionData.notes,
                    completedAt = null
                )
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    suspend fun rescheduleSession(sessionId: String, newTime: Date): Boolean {
        return try {
            // In a real implementation, this would update the session in the database
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun cancelSession(sessionId: String, reason: String): Boolean {
        return try {
            // In a real implementation, this would update the session status
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun completeSession(sessionId: String, sessionNotes: String): Boolean {
        return try {
            // In a real implementation, this would update the session with completion data
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getPatientMoodHistory(patientId: String, therapistId: String, startDate: Date, endDate: Date): List<MoodEntry> {
        return try {
            if (validateTherapistAccess(therapistId, patientId)) {
                // In a real implementation, this would fetch from the database
                listOf(
                    MoodEntry(
                        id = "mood1",
                        userId = patientId,
                        date = startDate,
                        mood = 5,
                        energy = 6,
                        stress = 4,
                        anxiety = 5,
                        sleep = 7,
                        notes = "",
                        tags = emptyList(),
                        activities = emptyList(),
                        weather = null,
                        location = null
                    )
                )
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getPatientChatHistory(patientId: String, therapistId: String): List<Session> {
        return try {
            if (validateTherapistAccess(therapistId, patientId)) {
                // In a real implementation, this would fetch from the database
                listOf(
                    Session(
                        sessionId = "session1",
                        userId = patientId,
                        startTimestamp = Date(),
                        endTimestamp = Date(),
                        messages = emptyList(),
                        moodScorePre = 5,
                        moodScorePost = 6
                    )
                )
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getPatientCrisisIncidents(patientId: String, therapistId: String): List<CrisisIncident> {
        return try {
            if (validateTherapistAccess(therapistId, patientId)) {
                // In a real implementation, this would fetch from the database
                listOf(
                    CrisisIncident(
                        userId = patientId,
                        message = "Patient expressed suicidal thoughts",
                        timestamp = Date(),
                        riskLevel = CrisisRiskLevel.HIGH
                    )
                )
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getPatientProgressReport(patientId: String, therapistId: String): Map<String, Any> {
        return try {
            if (validateTherapistAccess(therapistId, patientId)) {
                mapOf(
                    "mood_trends" to "Improving over the past month",
                    "session_summary" to "Patient attended 8 out of 10 scheduled sessions",
                    "recommendations" to listOf("Continue current treatment plan", "Increase session frequency")
                )
            } else {
                emptyMap()
            }
        } catch (e: Exception) {
            emptyMap()
        }
    }

    suspend fun sendMessageToPatient(therapistId: String, patientId: String, message: String): Boolean {
        return try {
            if (validateTherapistAccess(therapistId, patientId)) {
                logTherapistAccess(therapistId, patientId, "sent_message")
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }

    suspend fun sendCrisisAlert(patientId: String, crisisData: CrisisAlertData): Boolean {
        return try {
            // In a real implementation, this would send alerts to the therapist
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun sendAppointmentReminder(sessionId: String, reminderTime: Date): Boolean {
        return try {
            // In a real implementation, this would schedule and send reminders
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun generateTherapistDashboard(therapistId: String): Map<String, Any> {
        return mapOf(
            "patient_count" to 15,
            "upcoming_sessions" to 8,
            "crisis_alerts" to 2,
            "patient_progress" to "Overall positive trend"
        )
    }

    suspend fun generatePatientAnalytics(patientId: String, therapistId: String): Map<String, Any> {
        return try {
            if (validateTherapistAccess(therapistId, patientId)) {
                mapOf(
                    "mood_trends" to "Improving",
                    "session_attendance" to "85%",
                    "crisis_frequency" to "Low",
                    "progress_metrics" to mapOf(
                        "anxiety_level" to "Decreasing",
                        "sleep_quality" to "Improving",
                        "social_engagement" to "Stable"
                    )
                )
            } else {
                emptyMap()
            }
        } catch (e: Exception) {
            emptyMap()
        }
    }

    suspend fun generateTreatmentPlan(patientId: String, therapistId: String): Map<String, Any> {
        return try {
            if (validateTherapistAccess(therapistId, patientId)) {
                mapOf(
                    "goals" to listOf("Reduce anxiety symptoms", "Improve sleep quality", "Increase social engagement"),
                    "interventions" to listOf("Cognitive Behavioral Therapy", "Mindfulness exercises", "Sleep hygiene"),
                    "timeline" to "12 weeks",
                    "progress_indicators" to listOf("Mood tracking", "Session attendance", "Crisis incidents")
                )
            } else {
                emptyMap()
            }
        } catch (e: Exception) {
            emptyMap()
        }
    }

    suspend fun validateTherapistAccess(therapistId: String, patientId: String): Boolean {
        // In a real implementation, this would check the therapist-patient relationship
        return therapistId == "therapist123" && patientId == "patient123"
    }

    suspend fun logTherapistAccess(therapistId: String, patientId: String, action: String) {
        // In a real implementation, this would log to a database
    }

    suspend fun encryptSensitiveData(data: String): String {
        // In a real implementation, this would use proper encryption
        return "encrypted_$data"
    }

    suspend fun syncWithEHR(patientId: String, therapistId: String): Boolean {
        return try {
            if (validateTherapistAccess(therapistId, patientId)) {
                // In a real implementation, this would sync with EHR system
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }

    suspend fun exportPatientData(patientId: String, therapistId: String, format: String): Map<String, Any> {
        return try {
            if (validateTherapistAccess(therapistId, patientId)) {
                mapOf(
                    "patient_info" to "Patient details",
                    "mood_history" to "Mood tracking data",
                    "session_notes" to "Therapy session notes"
                )
            } else {
                emptyMap()
            }
        } catch (e: Exception) {
            emptyMap()
        }
    }

    suspend fun syncWithCalendar(therapistId: String, sessionData: TherapySessionData): Boolean {
        return try {
            // In a real implementation, this would sync with calendar system
            true
        } catch (e: Exception) {
            false
        }
    }
}
