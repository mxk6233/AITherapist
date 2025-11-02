package com.serenityai.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.*
import com.serenityai.usecases.RelapsePreventionUseCase
import com.serenityai.data.models.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RelapsePreventionScreen(
    onNavigateBack: () -> Unit,
    onEmergencyContact: () -> Unit
) {
    val useCase = remember { RelapsePreventionUseCase() }
    var showRiskAssessment by remember { mutableStateOf(false) }
    var showSafetyPlan by remember { mutableStateOf(false) }
    var isMonitoringEnabled by remember { mutableStateOf(true) }
    var riskAssessment by remember { mutableStateOf<RiskAssessment?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    
    // Sample historical data - in production would come from repository
    val sampleMoodEntries = remember {
        generateSampleMoodEntries()
    }
    
    // Generate safety plan - in production would come from repository
    val safetyPlan = remember {
        com.serenityai.data.models.SafetyPlan(
            userId = "user-123",
            emergencyContacts = listOf(
                com.serenityai.data.models.EmergencyContact(
                    name = "Dr. Sarah Johnson",
                    role = "Therapist",
                    phone = "+1-555-0123"
                ),
                com.serenityai.data.models.EmergencyContact(
                    name = "Mike Chen",
                    role = "Support Person",
                    phone = "+1-555-0456"
                ),
                com.serenityai.data.models.EmergencyContact(
                    name = "Crisis Hotline",
                    role = "24/7 Support",
                    phone = "988",
                    availability = ContactAvailability.TWENTY_FOUR_SEVEN
                )
            ),
            copingStrategies = listOf(
                "Deep breathing exercises",
                "Call support person",
                "Go for a walk",
                "Listen to calming music",
                "Practice mindfulness meditation"
            ),
            warningSigns = listOf(
                "Feeling overwhelmed",
                "Sleep problems",
                "Loss of appetite",
                "Withdrawing from others",
                "Negative thoughts"
            )
        )
    }
    
    // Perform risk assessment when screen loads
    LaunchedEffect(Unit) {
        if (isMonitoringEnabled) {
            isLoading = true
            riskAssessment = useCase.assessRiskLevel(
                moodEntries = sampleMoodEntries,
                sleepData = generateSampleSleepData(),
                stressLevels = generateSampleStressData()
            )
            isLoading = false
        }
    }
    
    // Refresh assessment periodically
    LaunchedEffect(isMonitoringEnabled) {
        if (isMonitoringEnabled) {
            while (true) {
                kotlinx.coroutines.delay(300000) // Every 5 minutes
                riskAssessment = useCase.assessRiskLevel(
                    moodEntries = sampleMoodEntries,
                    sleepData = generateSampleSleepData(),
                    stressLevels = generateSampleStressData()
                )
            }
        }
    }
    
    // Convert data models to UI models for backward compatibility
    val riskFactors = riskAssessment?.riskIndicators?.map {
        RiskIndicator(it.name, it.riskLevel, it.description)
    } ?: emptyList()
    
    val earlyWarnings = riskAssessment?.earlyWarnings?.map {
        EarlyWarning(
            it.title,
            it.description,
            it.severity.name,
            it.recommendedAction
        )
    } ?: emptyList()
    
    // Convert SafetyPlan to UI model (UI data class)
    val uiSafetyPlan = com.serenityai.ui.screens.SafetyPlan(
        emergencyContacts = safetyPlan.emergencyContacts.map {
            com.serenityai.ui.screens.EmergencyContact(it.name, it.role, it.phone)
        },
        copingStrategies = safetyPlan.copingStrategies,
        warningSigns = safetyPlan.warningSigns
    )
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Relapse Prevention") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = onEmergencyContact) {
                        Icon(Icons.Default.Emergency, contentDescription = "Emergency")
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
            // Risk Assessment Status
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.Security,
                                    contentDescription = null,
                                    tint = Color(0xFF4CAF50)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Risk Assessment",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            
                            Switch(
                                checked = isMonitoringEnabled,
                                onCheckedChange = { isMonitoringEnabled = it }
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = if (isMonitoringEnabled) {
                                val riskLevelText = riskAssessment?.overallRiskLevel?.name ?: "ASSESSING"
                                "AI monitoring active - Risk level: $riskLevelText"
                            } else {
                                "Monitoring disabled - Manual assessment required"
                            },
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        
                        if (isMonitoringEnabled) {
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (isLoading) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(16.dp),
                                        strokeWidth = 2.dp
                                    )
                                } else {
                                    Icon(
                                        Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = Color(0xFF4CAF50),
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = riskAssessment?.let {
                                        "Last assessment: ${SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault()).format(it.assessedAt)}"
                                    } ?: "Assessing...",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }
            
            // Risk Factors Dashboard
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Risk Factors Dashboard",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = "Current risk indicators and their impact levels",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        riskFactors.forEach { factor ->
                            RiskFactorCard(factor = factor)
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
            
            // Early Warning System
            item {
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
                                Icons.Default.Warning,
                                contentDescription = null,
                                tint = Color(0xFFFF9800)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Early Warning System",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = "AI-detected patterns that may indicate increased risk",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        earlyWarnings.forEach { warning ->
                            EarlyWarningCard(warning = warning)
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
            
            // Safety Plan
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.Shield,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Safety Plan",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            
                            TextButton(onClick = { showSafetyPlan = true }) {
                                Text("View Full Plan")
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        // Emergency Contacts Preview
                        Text(
                            text = "Emergency Contacts:",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Medium
                        )
                        
                        Spacer(modifier = Modifier.height(4.dp))
                        
                        uiSafetyPlan.emergencyContacts.take(2).forEach { contact ->
                            ContactPreview(contact = contact)
                        }
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        // Quick Coping Strategies
                        Text(
                            text = "Quick Coping Strategies:",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Medium
                        )
                        
                        Spacer(modifier = Modifier.height(4.dp))
                        
                        uiSafetyPlan.copingStrategies.take(3).forEach { strategy ->
                            Text(
                                text = "• $strategy",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
            
            // Prevention Strategies
            item {
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
                                Icons.Default.Recommend,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Prevention Strategies",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        PreventionStrategyCard(
                            title = "Daily Check-ins",
                            description = "Complete daily mood and risk assessments",
                            priority = "High",
                            isCompleted = true
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        PreventionStrategyCard(
                            title = "Medication Reminders",
                            description = "Set up automated medication reminders",
                            priority = "High",
                            isCompleted = true
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        PreventionStrategyCard(
                            title = "Support Group Meetings",
                            description = "Attend weekly support group sessions",
                            priority = "Medium",
                            isCompleted = false
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        PreventionStrategyCard(
                            title = "Therapy Sessions",
                            description = "Regular therapy appointments",
                            priority = "High",
                            isCompleted = true
                        )
                    }
                }
            }
            
            // Emergency Actions
            item {
                Button(
                    onClick = onEmergencyContact,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF44336)
                    )
                ) {
                    Icon(Icons.Default.Emergency, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Emergency Contact")
                }
            }
        }
    }
    
    // Safety Plan Dialog
    if (showSafetyPlan) {
        AlertDialog(
            onDismissRequest = { showSafetyPlan = false },
            title = { Text("Complete Safety Plan") },
            text = {
                Column {
                    Text("Emergency Contacts:")
                    uiSafetyPlan.emergencyContacts.forEach { contact ->
                        Text("• ${contact.name} (${contact.role}): ${contact.phone}")
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text("Warning Signs:")
                    uiSafetyPlan.warningSigns.forEach { sign ->
                        Text("• $sign")
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text("Coping Strategies:")
                    uiSafetyPlan.copingStrategies.forEach { strategy ->
                        Text("• $strategy")
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showSafetyPlan = false }) {
                    Text("Close")
                }
            }
        )
    }
}

@Composable
fun RiskFactorCard(factor: RiskIndicator) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val riskColor = when {
                factor.riskLevel >= 80 -> Color(0xFFF44336)
                factor.riskLevel >= 60 -> Color(0xFFFF9800)
                factor.riskLevel >= 40 -> Color(0xFFFFC107)
                else -> Color(0xFF4CAF50)
            }
            
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(riskColor, RoundedCornerShape(4.dp))
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = factor.name,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium
                )
                
                Text(
                    text = factor.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Text(
                text = "${factor.riskLevel}%",
                style = MaterialTheme.typography.bodySmall,
                color = riskColor,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun EarlyWarningCard(warning: EarlyWarning) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = warning.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                
                val severityColor = when (warning.severity.lowercase()) {
                    "high" -> Color(0xFFF44336)
                    "medium" -> Color(0xFFFF9800)
                    "low" -> Color(0xFF4CAF50)
                    else -> MaterialTheme.colorScheme.primary
                }
                
                Text(
                    text = warning.severity,
                    style = MaterialTheme.typography.bodySmall,
                    color = severityColor,
                    fontWeight = FontWeight.Medium
                )
            }
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = warning.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "💡 Recommended Action: ${warning.recommendedAction}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun ContactPreview(contact: EmergencyContact) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.Default.Person,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.width(8.dp))
        
        Text(
            text = "${contact.name} (${contact.role})",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun PreventionStrategyCard(
    title: String,
    description: String,
    priority: String,
    isCompleted: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isCompleted) {
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = "Completed",
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier.size(20.dp)
                )
            } else {
                Icon(
                    Icons.Default.RadioButtonUnchecked,
                    contentDescription = "Not completed",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium
                )
                
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            val priorityColor = when (priority.lowercase()) {
                "high" -> Color(0xFFF44336)
                "medium" -> Color(0xFFFF9800)
                "low" -> Color(0xFF4CAF50)
                else -> MaterialTheme.colorScheme.primary
            }
            
            Text(
                text = priority,
                style = MaterialTheme.typography.bodySmall,
                color = priorityColor,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

// Data classes moved to com.serenityai.data.models.RelapsePrevention
// Keeping these for backward compatibility with existing code
data class RiskIndicator(
    val name: String,
    val riskLevel: Int,
    val description: String
)

data class EarlyWarning(
    val title: String,
    val description: String,
    val severity: String,
    val recommendedAction: String
)

data class SafetyPlan(
    val emergencyContacts: List<EmergencyContact>,
    val copingStrategies: List<String>,
    val warningSigns: List<String>
)

data class EmergencyContact(
    val name: String,
    val role: String,
    val phone: String
)

// Helper functions to generate sample data
private fun generateSampleMoodEntries(): List<com.serenityai.data.models.MoodEntry> {
    val entries = mutableListOf<com.serenityai.data.models.MoodEntry>()
    val calendar = Calendar.getInstance()
    
    // Generate 14 days of mood data with some decline pattern
    repeat(14) { dayOffset ->
        calendar.time = Date(System.currentTimeMillis() - (14 - dayOffset) * 86400000L)
        
        // Simulate mood decline pattern
        val baseMood = 4.0
        val moodAdjustment = when {
            dayOffset >= 10 -> -1.5 // Recent decline
            dayOffset >= 7 -> -0.5  // Moderate decline
            else -> 0.0
        }
        val mood = (baseMood + moodAdjustment + Math.random() * 0.5 - 0.25).coerceIn(1.0, 5.0).toInt()
        
        entries.add(
            com.serenityai.data.models.MoodEntry(
                id = "entry-$dayOffset",
                userId = "user-123",
                date = calendar.time,
                mood = mood,
                energy = 5,
                stress = if (dayOffset >= 10) 7 else 5,
                anxiety = if (dayOffset >= 10) 6 else 5,
                sleep = if (dayOffset >= 10) 4 else 6
            )
        )
    }
    
    return entries
}

private fun generateSampleSleepData(): List<com.serenityai.usecases.SleepData> {
    val calendar = Calendar.getInstance()
    return (0..6).map { dayOffset ->
        calendar.time = Date(System.currentTimeMillis() - (7 - dayOffset) * 86400000L)
        com.serenityai.usecases.SleepData(
            date = calendar.time,
            hours = if (dayOffset >= 4) 5.5 + Math.random() * 1.0 else 7.0 + Math.random() * 1.0
        )
    }
}

private fun generateSampleStressData(): List<com.serenityai.usecases.StressData> {
    val calendar = Calendar.getInstance()
    return (0..6).map { dayOffset ->
        calendar.time = Date(System.currentTimeMillis() - (7 - dayOffset) * 86400000L)
        com.serenityai.usecases.StressData(
            date = calendar.time,
            level = if (dayOffset >= 4) (6 + Math.random() * 2).toInt() else (4 + Math.random() * 2).toInt()
        )
    }
}
