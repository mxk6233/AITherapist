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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RelapsePreventionScreen(
    onNavigateBack: () -> Unit,
    onEmergencyContact: () -> Unit
) {
    var showRiskAssessment by remember { mutableStateOf(false) }
    var showSafetyPlan by remember { mutableStateOf(false) }
    var isMonitoringEnabled by remember { mutableStateOf(true) }
    
    val riskFactors = remember {
        listOf(
            RiskIndicator("Sleep Pattern", 75, "Irregular sleep increases relapse risk"),
            RiskIndicator("Stress Level", 60, "High stress detected in recent patterns"),
            RiskIndicator("Social Support", 40, "Limited social interaction this week"),
            RiskIndicator("Medication Adherence", 90, "Good adherence to treatment plan"),
            RiskIndicator("Trigger Exposure", 30, "Low exposure to known triggers")
        )
    }
    
    val earlyWarnings = remember {
        listOf(
            EarlyWarning(
                "Sleep Disruption",
                "3 consecutive nights of poor sleep detected",
                "High",
                "Schedule sleep hygiene activities"
            ),
            EarlyWarning(
                "Social Isolation",
                "Decreased social activity over 5 days",
                "Medium",
                "Plan social interactions this weekend"
            ),
            EarlyWarning(
                "Stress Accumulation",
                "Work stress levels increasing",
                "Medium",
                "Practice stress management techniques"
            )
        )
    }
    
    val safetyPlan = remember {
        SafetyPlan(
            emergencyContacts = listOf(
                EmergencyContact("Dr. Sarah Johnson", "Therapist", "+1-555-0123"),
                EmergencyContact("Mike Chen", "Support Person", "+1-555-0456"),
                EmergencyContact("Crisis Hotline", "24/7 Support", "988")
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
                            text = if (isMonitoringEnabled) 
                                "AI monitoring active - Risk level: LOW" 
                            else "Monitoring disabled - Manual assessment required",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        
                        if (isMonitoringEnabled) {
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = Color(0xFF4CAF50),
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Last assessment: 2 hours ago",
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
                        
                        safetyPlan.emergencyContacts.take(2).forEach { contact ->
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
                        
                        safetyPlan.copingStrategies.take(3).forEach { strategy ->
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
                    safetyPlan.emergencyContacts.forEach { contact ->
                        Text("• ${contact.name} (${contact.role}): ${contact.phone}")
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text("Warning Signs:")
                    safetyPlan.warningSigns.forEach { sign ->
                        Text("• $sign")
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text("Coping Strategies:")
                    safetyPlan.copingStrategies.forEach { strategy ->
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
