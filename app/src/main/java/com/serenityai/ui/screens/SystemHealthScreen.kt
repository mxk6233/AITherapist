package com.serenityai.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serenityai.data.models.*
import com.serenityai.usecases.SystemHealthUseCase
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

/**
 * UC22: Monitor System Health - UI Screen
 * 
 * Displays system health metrics, service status, and health reports.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SystemHealthScreen(onNavigateBack: () -> Unit) {
    val useCase = remember { SystemHealthUseCase() }
    var healthReport by remember { mutableStateOf<HealthReport?>(null) }
    var alerts by remember { mutableStateOf<List<HealthAlert>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var lastUpdated by remember { mutableStateOf<Date?>(null) }
    
    // Auto-refresh health metrics every 30 seconds
    LaunchedEffect(Unit) {
        while (true) {
            isLoading = true
            delay(500) // Simulate loading
            healthReport = useCase.generateHealthReport()
            alerts = useCase.alertOnCriticalHealthIssues(healthReport!!)
            isLoading = false
            lastUpdated = Date()
            delay(30000) // Refresh every 30 seconds
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("System Health Monitor") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                actions = {
                    IconButton(onClick = {
                        isLoading = true
                        healthReport = useCase.generateHealthReport()
                        alerts = useCase.alertOnCriticalHealthIssues(healthReport!!)
                        isLoading = false
                        lastUpdated = Date()
                    }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Overall Health Status Card
            item {
                healthReport?.let { report ->
                    OverallHealthStatusCard(report.overallStatus, lastUpdated)
                } ?: HealthLoadingCard()
            }
            
            // Critical Alerts
            item {
                if (alerts.isNotEmpty()) {
                    CriticalAlertsSection(alerts)
                }
            }
            
            // Performance Metrics
            item {
                healthReport?.let { report ->
                    PerformanceMetricsCard(report.performanceMetrics)
                }
            }
            
            // Service Status
            item {
                healthReport?.let { report ->
                    ServiceStatusCard(report.serviceStatuses)
                }
            }
            
            // Critical Issues
            item {
                healthReport?.let { report ->
                    if (report.criticalIssues.isNotEmpty()) {
                        CriticalIssuesCard(report.criticalIssues)
                    }
                }
            }
            
            // Recommendations
            item {
                healthReport?.let { report ->
                    if (report.recommendations.isNotEmpty()) {
                        RecommendationsCard(report.recommendations)
                    }
                }
            }
        }
    }
}

@Composable
fun OverallHealthStatusCard(status: HealthStatus, lastUpdated: Date?) {
    val statusColor = when (status) {
        HealthStatus.HEALTHY -> Color(0xFF4CAF50)
        HealthStatus.WARNING -> Color(0xFFFF9800)
        HealthStatus.CRITICAL -> Color(0xFFF44336)
        HealthStatus.UNKNOWN -> Color(0xFF9E9E9E)
    }
    
    val statusText = when (status) {
        HealthStatus.HEALTHY -> "Healthy"
        HealthStatus.WARNING -> "Warning"
        HealthStatus.CRITICAL -> "Critical"
        HealthStatus.UNKNOWN -> "Unknown"
    }
    
    val statusIcon = when (status) {
        HealthStatus.HEALTHY -> Icons.Default.CheckCircle
        HealthStatus.WARNING -> Icons.Default.Warning
        HealthStatus.CRITICAL -> Icons.Default.Error
        HealthStatus.UNKNOWN -> Icons.Default.Help
    }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                statusIcon,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = statusColor
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "System Status: $statusText",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = statusColor
            )
            lastUpdated?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Last updated: ${SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(it)}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun HealthLoadingCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(40.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun CriticalAlertsSection(alerts: List<HealthAlert>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFEBEE)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Error,
                    contentDescription = null,
                    tint = Color(0xFFD32F2F),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Critical Alerts (${alerts.size})",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFD32F2F)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            alerts.forEach { alert ->
                AlertCard(alert)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun AlertCard(alert: HealthAlert) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Warning,
                contentDescription = null,
                tint = Color(0xFFD32F2F),
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = alert.message,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(alert.createdAt),
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun PerformanceMetricsCard(metrics: SystemHealthMetrics) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Performance Metrics",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            
            MetricRow("CPU Usage", "${String.format("%.1f", metrics.cpuUsage)}%", metrics.cpuUsage)
            Spacer(modifier = Modifier.height(12.dp))
            MetricRow("Memory Usage", "${String.format("%.1f", metrics.memoryUsage)}%", metrics.memoryUsage)
            Spacer(modifier = Modifier.height(12.dp))
            MetricRow("Response Time", "${metrics.responseTime}ms", null, reverse = true)
            Spacer(modifier = Modifier.height(12.dp))
            MetricRow("Error Rate", "${String.format("%.2f", metrics.errorRate)}%", metrics.errorRate)
            Spacer(modifier = Modifier.height(12.dp))
            MetricRow("Active Users", "${metrics.activeUsers}", null)
            Spacer(modifier = Modifier.height(12.dp))
            MetricRow("API Calls/min", "${metrics.apiCallsPerMinute}", null)
        }
    }
}

@Composable
fun MetricRow(label: String, value: String, percentage: Double?, reverse: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
        
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            percentage?.let {
                val color = when {
                    reverse -> if (it < 200.0) Color(0xFF4CAF50) else if (it < 500.0) Color(0xFFFF9800) else Color(0xFFF44336)
                    it < 50.0 -> Color(0xFF4CAF50)
                    it < 80.0 -> Color(0xFFFF9800)
                    else -> Color(0xFFF44336)
                }
                val progressValue = (it / 100.0).coerceIn(0.0, 1.0).toFloat()
                LinearProgressIndicator(
                    progress = progressValue,
                    modifier = Modifier.width(100.dp),
                    color = color
                )
            }
            Text(
                text = value,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun ServiceStatusCard(services: List<ServiceStatus>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Service Status",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            
            services.forEach { service ->
                ServiceStatusRow(service)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun ServiceStatusRow(service: ServiceStatus) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                if (service.isAvailable) Icons.Default.CheckCircle else Icons.Default.Error,
                contentDescription = null,
                tint = if (service.isAvailable) Color(0xFF4CAF50) else Color(0xFFF44336),
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = service.serviceName,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
        
        Text(
            text = if (service.isAvailable) "Available (${service.responseTime}ms)" else "Unavailable",
            fontSize = 14.sp,
            color = if (service.isAvailable) Color(0xFF4CAF50) else Color(0xFFF44336),
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun CriticalIssuesCard(issues: List<HealthIssue>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFEBEE)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Warning,
                    contentDescription = null,
                    tint = Color(0xFFD32F2F),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Critical Issues (${issues.size})",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFD32F2F)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            
            issues.forEach { issue ->
                IssueRow(issue)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun IssueRow(issue: HealthIssue) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = issue.type.name.replace("_", " "),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = issue.severity.name,
                    fontSize = 12.sp,
                    color = Color(0xFFD32F2F),
                    fontWeight = FontWeight.Medium
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = issue.description,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun RecommendationsCard(recommendations: List<String>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Lightbulb,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Recommendations",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            
            recommendations.forEach { recommendation ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = recommendation,
                        fontSize = 14.sp,
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

