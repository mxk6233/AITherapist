package com.serenityai.usecases

import com.serenityai.data.models.*
import java.util.Date

/** UC28: Therapist portal integration system enabling therapists to access patient data, monitor progress, and manage therapeutic relationships. */
class TherapistPortalIntegrationUseCase {
    
    private val therapistPatients = mutableMapOf<String, List<String>>() // therapistId -> patientIds
    private val therapistNotes = mutableMapOf<String, MutableList<TherapistNote>>() // patientId -> notes
    private val therapistAssignments = mutableMapOf<String, String>() // patientId -> therapistId
    
    /**
     * Assigns a patient to a therapist
     * 
     * @param therapistId Therapist ID
     * @param patientId Patient ID
     * @return True if assignment successful
     */
    fun assignPatientToTherapist(
        therapistId: String,
        patientId: String
    ): Boolean {
        require(therapistId.isNotBlank()) { "Therapist ID cannot be empty" }
        require(patientId.isNotBlank()) { "Patient ID cannot be empty" }
        
        // Check if patient is already assigned
        if (therapistAssignments.containsKey(patientId)) {
            return false
        }
        
        therapistAssignments[patientId] = therapistId
        val currentPatients = therapistPatients[therapistId] ?: emptyList()
        therapistPatients[therapistId] = currentPatients + patientId
        
        return true
    }
    
    /**
     * Unassigns a patient from a therapist
     * 
     * @param therapistId Therapist ID
     * @param patientId Patient ID
     * @return True if unassignment successful
     */
    fun unassignPatientFromTherapist(
        therapistId: String,
        patientId: String
    ): Boolean {
        if (therapistAssignments[patientId] != therapistId) {
            return false
        }
        
        therapistAssignments.remove(patientId)
        val currentPatients = therapistPatients[therapistId] ?: emptyList()
        therapistPatients[therapistId] = currentPatients.filter { it != patientId }
        
        return true
    }
    
    /**
     * Gets list of patients assigned to a therapist
     * 
     * @param therapistId Therapist ID
     * @return List of patient IDs
     */
    fun getTherapistPatients(therapistId: String): List<String> {
        return therapistPatients[therapistId] ?: emptyList()
    }
    
    /**
     * Gets therapist assigned to a patient
     * 
     * @param patientId Patient ID
     * @return Therapist ID or null if not assigned
     */
    fun getPatientTherapist(patientId: String): String? {
        return therapistAssignments[patientId]
    }
    
    /**
     * Adds a therapist note for a patient
     * 
     * @param therapistId Therapist ID
     * @param patientId Patient ID
     * @param noteContent Note content
     * @param noteType Note type (observation, recommendation, etc.)
     * @return TherapistNote object
     */
    fun addTherapistNote(
        therapistId: String,
        patientId: String,
        noteContent: String,
        noteType: String = "observation"
    ): TherapistNote {
        require(noteContent.isNotBlank()) { "Note content cannot be empty" }
        
        // Verify therapist has access to patient
        if (therapistAssignments[patientId] != therapistId) {
            throw IllegalArgumentException("Therapist does not have access to this patient")
        }
        
        val note = TherapistNote(
            id = "note_${System.currentTimeMillis()}",
            therapistId = therapistId,
            patientId = patientId,
            content = noteContent,
            noteType = noteType,
            createdAt = Date()
        )
        
        val notes = therapistNotes[patientId] ?: mutableListOf()
        notes.add(note)
        therapistNotes[patientId] = notes
        
        return note
    }
    
    /**
     * Gets therapist notes for a patient
     * 
     * @param therapistId Therapist ID
     * @param patientId Patient ID
     * @return List of TherapistNote objects
     */
    fun getTherapistNotes(
        therapistId: String,
        patientId: String
    ): List<TherapistNote> {
        // Verify therapist has access to patient
        if (therapistAssignments[patientId] != therapistId) {
            return emptyList()
        }
        
        return therapistNotes[patientId]?.filter { it.therapistId == therapistId } ?: emptyList()
    }
    
    /**
     * Gets patient mood history for therapist review
     * 
     * @param therapistId Therapist ID
     * @param patientId Patient ID
     * @param startDate Start date for history range
     * @param endDate End date for history range
     * @return List of MoodEntry objects
     */
    fun getPatientMoodHistory(
        therapistId: String,
        patientId: String,
        startDate: Date,
        endDate: Date
    ): List<MoodEntry> {
        // Verify therapist has access to patient
        if (therapistAssignments[patientId] != therapistId) {
            return emptyList()
        }
        
        // In a real implementation, this would fetch from database
        // For now, return empty list as placeholder
        return emptyList()
    }
    
    /**
     * Generates patient progress report
     * 
     * @param therapistId Therapist ID
     * @param patientId Patient ID
     * @param startDate Start date for report period
     * @param endDate End date for report period
     * @return PatientProgressReport object
     */
    fun generatePatientProgressReport(
        therapistId: String,
        patientId: String,
        startDate: Date,
        endDate: Date
    ): PatientProgressReport {
        // Verify therapist has access to patient
        if (therapistAssignments[patientId] != therapistId) {
            throw IllegalArgumentException("Therapist does not have access to this patient")
        }
        
        val moodHistory = getPatientMoodHistory(therapistId, patientId, startDate, endDate)
        val notes = getTherapistNotes(therapistId, patientId)
        
        val averageMood = if (moodHistory.isNotEmpty()) {
            moodHistory.map { it.mood }.average().toFloat()
        } else {
            0f
        }
        
        return PatientProgressReport(
            patientId = patientId,
            therapistId = therapistId,
            startDate = startDate,
            endDate = endDate,
            averageMood = averageMood,
            totalMoodEntries = moodHistory.size,
            totalTherapistNotes = notes.size,
            generatedAt = Date()
        )
    }
}

/**
 * Data class for therapist notes
 */
data class TherapistNote(
    val id: String,
    val therapistId: String,
    val patientId: String,
    val content: String,
    val noteType: String,
    val createdAt: Date
)

/**
 * Data class for patient progress report
 */
data class PatientProgressReport(
    val patientId: String,
    val therapistId: String,
    val startDate: Date,
    val endDate: Date,
    val averageMood: Float,
    val totalMoodEntries: Int,
    val totalTherapistNotes: Int,
    val generatedAt: Date
)

