package com.serenityai.data.models

import java.util.Date

/**
 * UC35: Relapse Prevention Alerts - Data Models
 * 
 * Represents relapse risk assessment, early warnings, and safety plans.
 */
data class RiskIndicator(
    val id: String = "",
    val name: String,
    val riskLevel: Int, // 0-100
    val description: String,
    val category: RiskCategory = RiskCategory.GENERAL,
    val detectedAt: Date = Date(),
    val trend: RiskTrend = RiskTrend.STABLE
)

enum class RiskCategory {
    SLEEP,
    STRESS,
    SOCIAL_SUPPORT,
    MEDICATION_ADHERENCE,
    TRIGGER_EXPOSURE,
    MOOD_PATTERNS,
    GENERAL
}

enum class RiskTrend {
    IMPROVING,
    DECLINING,
    STABLE,
    VOLATILE
}

data class EarlyWarning(
    val id: String = "",
    val title: String,
    val description: String,
    val severity: WarningSeverity,
    val recommendedAction: String,
    val detectedAt: Date = Date(),
    val category: RiskCategory = RiskCategory.GENERAL,
    val relatedRiskIndicators: List<String> = emptyList()
)

enum class WarningSeverity {
    LOW,
    MEDIUM,
    HIGH,
    CRITICAL
}

data class SafetyPlan(
    val id: String = "",
    val userId: String = "",
    val emergencyContacts: List<EmergencyContact>,
    val copingStrategies: List<String>,
    val warningSigns: List<String>,
    val personalizedTriggers: List<String> = emptyList(),
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)

data class EmergencyContact(
    val id: String = "",
    val name: String,
    val role: String,
    val phone: String,
    val email: String? = null,
    val relationship: String? = null,
    val availability: ContactAvailability = ContactAvailability.ON_DEMAND,
    val isPrimary: Boolean = false
)

enum class ContactAvailability {
    TWENTY_FOUR_SEVEN,
    BUSINESS_HOURS,
    ON_DEMAND,
    SCHEDULED
}

data class RiskAssessment(
    val id: String = "",
    val userId: String = "",
    val assessedAt: Date = Date(),
    val overallRiskLevel: RiskLevel,
    val riskIndicators: List<RiskIndicator>,
    val earlyWarnings: List<EarlyWarning> = emptyList(),
    val recommendations: List<String> = emptyList(),
    val interventionTriggered: Boolean = false
)

enum class RiskLevel {
    LOW,     // 0-39
    MEDIUM,  // 40-59
    HIGH,    // 60-79
    CRITICAL // 80-100
}

data class InterventionPlan(
    val id: String = "",
    val userId: String = "",
    val triggeredAt: Date = Date(),
    val triggerReason: String,
    val riskLevel: RiskLevel,
    val actions: List<InterventionAction>,
    val emergencyContacts: List<EmergencyContact>,
    val status: InterventionStatus = InterventionStatus.ACTIVE
)

enum class InterventionStatus {
    ACTIVE,
    COMPLETED,
    CANCELLED
}

data class InterventionAction(
    val id: String = "",
    val title: String,
    val description: String,
    val priority: ActionPriority,
    val deadline: Date? = null,
    val isCompleted: Boolean = false,
    val completedAt: Date? = null
)

enum class ActionPriority {
    LOW,
    MEDIUM,
    HIGH,
    CRITICAL
}

