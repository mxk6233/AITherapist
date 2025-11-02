package com.serenityai.usecases

import com.serenityai.data.models.*
import java.util.Date

/**
 * UC22: Monitor System Health - Use Case
 * 
 * Use Case Goal: Monitor and maintain system health metrics including performance, 
 * resource usage, and service availability.
 * 
 * Responsibilities:
 * - Monitor application performance metrics
 * - Track resource usage (memory, CPU)
 * - Detect service availability issues
 * - Generate health reports
 * - Alert on critical health issues
 */
class SystemHealthUseCase {
    
    /**
     * Collects current system health metrics
     * 
     * @return SystemHealthMetrics with current system state
     */
    fun collectHealthMetrics(): SystemHealthMetrics {
        // In a real implementation, this would collect actual system metrics
        // For now, returns simulated metrics
        return SystemHealthMetrics(
            id = System.currentTimeMillis().toString(),
            timestamp = Date(),
            cpuUsage = getCpuUsage(),
            memoryUsage = getMemoryUsage(),
            responseTime = getAverageResponseTime(),
            errorRate = getErrorRate(),
            activeUsers = getActiveUserCount(),
            apiCallsPerMinute = getApiCallsPerMinute()
        )
    }
    
    /**
     * Monitors application performance metrics
     * 
     * @return Performance metrics including response time and throughput
     */
    fun monitorPerformanceMetrics(): SystemHealthMetrics {
        return collectHealthMetrics()
    }
    
    /**
     * Tracks resource usage (memory and CPU)
     * 
     * @return SystemHealthMetrics with resource usage data
     */
    fun trackResourceUsage(): SystemHealthMetrics {
        val metrics = collectHealthMetrics()
        // Validate resource usage
        require(metrics.cpuUsage in 0.0..100.0) { "CPU usage must be between 0-100%" }
        require(metrics.memoryUsage in 0.0..100.0) { "Memory usage must be between 0-100%" }
        return metrics
    }
    
    /**
     * Detects service availability issues
     * 
     * @param services List of services to check
     * @return List of ServiceStatus objects
     */
    fun detectServiceAvailabilityIssues(services: List<String>): List<ServiceStatus> {
        return services.map { serviceName ->
            val isAvailable = checkServiceAvailability(serviceName)
            ServiceStatus(
                serviceName = serviceName,
                isAvailable = isAvailable,
                lastChecked = Date(),
                responseTime = if (isAvailable) measureServiceResponseTime(serviceName) else 0L,
                errorCount = if (isAvailable) 0 else getServiceErrorCount(serviceName)
            )
        }
    }
    
    /**
     * Generates a comprehensive health report
     * 
     * @return HealthReport with overall status and details
     */
    fun generateHealthReport(): HealthReport {
        val metrics = collectHealthMetrics()
        val services = listOf("API", "Database", "ExternalService", "Cache", "NotificationService")
        val serviceStatuses = detectServiceAvailabilityIssues(services)
        
        val overallStatus = calculateOverallHealthStatus(metrics, serviceStatuses)
        val criticalIssues = identifyCriticalIssues(metrics, serviceStatuses)
        val recommendations = generateRecommendations(metrics, serviceStatuses, criticalIssues)
        
        return HealthReport(
            id = System.currentTimeMillis().toString(),
            generatedAt = Date(),
            overallStatus = overallStatus,
            performanceMetrics = metrics,
            serviceStatuses = serviceStatuses,
            criticalIssues = criticalIssues,
            recommendations = recommendations
        )
    }
    
    /**
     * Alerts on critical health issues
     * 
     * @param healthReport Current health report
     * @return List of HealthAlert objects for critical issues
     */
    fun alertOnCriticalHealthIssues(healthReport: HealthReport): List<HealthAlert> {
        return healthReport.criticalIssues
            .filter { it.severity == IssueSeverity.CRITICAL }
            .map { issue ->
                HealthAlert(
                    id = System.currentTimeMillis().toString(),
                    issueId = issue.id,
                    message = "Critical health issue detected: ${issue.description}",
                    severity = issue.severity,
                    createdAt = Date(),
                    acknowledged = false
                )
            }
    }
    
    /**
     * Calculates overall health status based on metrics and service status
     */
    private fun calculateOverallHealthStatus(
        metrics: SystemHealthMetrics,
        services: List<ServiceStatus>
    ): HealthStatus {
        // Check for critical conditions
        if (metrics.cpuUsage > 95.0 || metrics.memoryUsage > 95.0) {
            return HealthStatus.CRITICAL
        }
        
        if (services.any { !it.isAvailable }) {
            return HealthStatus.CRITICAL
        }
        
        if (metrics.errorRate > 5.0) {
            return HealthStatus.CRITICAL
        }
        
        // Check for warning conditions
        if (metrics.cpuUsage > 80.0 || metrics.memoryUsage > 80.0) {
            return HealthStatus.WARNING
        }
        
        if (metrics.errorRate > 1.0) {
            return HealthStatus.WARNING
        }
        
        if (metrics.responseTime > 500L) {
            return HealthStatus.WARNING
        }
        
        return HealthStatus.HEALTHY
    }
    
    /**
     * Identifies critical issues from metrics and service status
     */
    private fun identifyCriticalIssues(
        metrics: SystemHealthMetrics,
        services: List<ServiceStatus>
    ): List<HealthIssue> {
        val issues = mutableListOf<HealthIssue>()
        
        // Check CPU usage
        if (metrics.cpuUsage > 90.0) {
            issues.add(
                HealthIssue(
                    id = "cpu-${System.currentTimeMillis()}",
                    type = IssueType.RESOURCE_EXHAUSTION,
                    severity = IssueSeverity.CRITICAL,
                    description = "CPU usage is critically high: ${metrics.cpuUsage}%",
                    detectedAt = Date()
                )
            )
        }
        
        // Check memory usage
        if (metrics.memoryUsage > 90.0) {
            issues.add(
                HealthIssue(
                    id = "memory-${System.currentTimeMillis()}",
                    type = IssueType.RESOURCE_EXHAUSTION,
                    severity = IssueSeverity.CRITICAL,
                    description = "Memory usage is critically high: ${metrics.memoryUsage}%",
                    detectedAt = Date()
                )
            )
        }
        
        // Check error rate
        if (metrics.errorRate > 5.0) {
            issues.add(
                HealthIssue(
                    id = "errors-${System.currentTimeMillis()}",
                    type = IssueType.HIGH_ERROR_RATE,
                    severity = IssueSeverity.CRITICAL,
                    description = "Error rate is critically high: ${metrics.errorRate}%",
                    detectedAt = Date()
                )
            )
        }
        
        // Check unavailable services
        services.filter { !it.isAvailable }.forEach { service ->
            issues.add(
                HealthIssue(
                    id = "service-${service.serviceName}-${System.currentTimeMillis()}",
                    type = IssueType.SERVICE_UNAVAILABLE,
                    severity = IssueSeverity.CRITICAL,
                    description = "Service ${service.serviceName} is unavailable",
                    detectedAt = Date(),
                    affectedServices = listOf(service.serviceName)
                )
            )
        }
        
        return issues
    }
    
    /**
     * Generates recommendations based on health status
     */
    private fun generateRecommendations(
        metrics: SystemHealthMetrics,
        services: List<ServiceStatus>,
        issues: List<HealthIssue>
    ): List<String> {
        val recommendations = mutableListOf<String>()
        
        if (metrics.cpuUsage > 80.0) {
            recommendations.add("Consider scaling up CPU resources or optimizing CPU-intensive operations")
        }
        
        if (metrics.memoryUsage > 80.0) {
            recommendations.add("Consider increasing memory allocation or investigating memory leaks")
        }
        
        if (metrics.errorRate > 1.0) {
            recommendations.add("Review error logs and investigate root causes of errors")
        }
        
        if (metrics.responseTime > 300L) {
            recommendations.add("Optimize response time by reviewing slow queries and API calls")
        }
        
        services.filter { !it.isAvailable }.forEach { service ->
            recommendations.add("Investigate and restore service: ${service.serviceName}")
        }
        
        if (recommendations.isEmpty()) {
            recommendations.add("System is healthy. Continue monitoring.")
        }
        
        return recommendations
    }
    
    // Simulated metric collection methods
    // In a real implementation, these would use actual system monitoring APIs
    private fun getCpuUsage(): Double = (Math.random() * 50 + 20).coerceIn(0.0, 100.0)
    private fun getMemoryUsage(): Double = (Math.random() * 40 + 30).coerceIn(0.0, 100.0)
    private fun getAverageResponseTime(): Long = (Math.random() * 200 + 100).toLong()
    private fun getErrorRate(): Double = (Math.random() * 0.5).coerceIn(0.0, 100.0)
    private fun getActiveUserCount(): Int = (Math.random() * 1000 + 500).toInt()
    private fun getApiCallsPerMinute(): Int = (Math.random() * 500 + 200).toInt()
    
    private fun checkServiceAvailability(serviceName: String): Boolean {
        // In real implementation, would check actual service health
        return Math.random() > 0.1 // 90% availability simulation
    }
    
    private fun measureServiceResponseTime(serviceName: String): Long {
        return (Math.random() * 150 + 50).toLong()
    }
    
    private fun getServiceErrorCount(serviceName: String): Int {
        return if (checkServiceAvailability(serviceName)) 0 else (Math.random() * 10).toInt()
    }
}

