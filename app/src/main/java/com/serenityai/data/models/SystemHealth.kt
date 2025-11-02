package com.serenityai.data.models

import java.util.Date

/**
 * UC22: Monitor System Health - Data Models
 * 
 * Represents system health metrics, performance data, and health status.
 */
data class SystemHealthMetrics(
    val id: String = "",
    val timestamp: Date = Date(),
    val cpuUsage: Double = 0.0, // Percentage 0-100
    val memoryUsage: Double = 0.0, // Percentage 0-100
    val responseTime: Long = 0L, // Milliseconds
    val errorRate: Double = 0.0, // Percentage 0-100
    val activeUsers: Int = 0,
    val apiCallsPerMinute: Int = 0
)

data class ServiceStatus(
    val serviceName: String,
    val isAvailable: Boolean,
    val lastChecked: Date = Date(),
    val responseTime: Long = 0L, // Milliseconds
    val errorCount: Int = 0
)

data class HealthReport(
    val id: String = "",
    val generatedAt: Date = Date(),
    val overallStatus: HealthStatus,
    val performanceMetrics: SystemHealthMetrics,
    val serviceStatuses: List<ServiceStatus>,
    val criticalIssues: List<HealthIssue> = emptyList(),
    val recommendations: List<String> = emptyList()
)

enum class HealthStatus {
    HEALTHY,
    WARNING,
    CRITICAL,
    UNKNOWN
}

data class HealthIssue(
    val id: String = "",
    val type: IssueType,
    val severity: IssueSeverity,
    val description: String,
    val detectedAt: Date = Date(),
    val resolvedAt: Date? = null,
    val affectedServices: List<String> = emptyList()
)

enum class IssueType {
    PERFORMANCE_DEGRADATION,
    HIGH_ERROR_RATE,
    SERVICE_UNAVAILABLE,
    RESOURCE_EXHAUSTION,
    NETWORK_ISSUE,
    DATABASE_ISSUE
}

enum class IssueSeverity {
    LOW,
    MEDIUM,
    HIGH,
    CRITICAL
}

data class HealthAlert(
    val id: String = "",
    val issueId: String,
    val message: String,
    val severity: IssueSeverity,
    val createdAt: Date = Date(),
    val acknowledged: Boolean = false,
    val acknowledgedAt: Date? = null
)

