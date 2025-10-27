package com.serenityai.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
fun MoodForecastingScreen(
    onNavigateBack: () -> Unit,
    onViewDetailedForecast: () -> Unit
) {
    var selectedForecastRange by remember { mutableStateOf("7 Days") }
    var showSettingsDialog by remember { mutableStateOf(false) }
    var isForecastingEnabled by remember { mutableStateOf(true) }
    
    val forecastRanges = listOf("3 Days", "7 Days", "14 Days", "30 Days")
    
    val forecastData = remember {
        listOf(
            ForecastDataPoint("Today", 3.8f, "Work stress expected", 75),
            ForecastDataPoint("Tomorrow", 4.2f, "Weekend approaching", 82),
            ForecastDataPoint("Day 3", 3.5f, "Monday blues", 68),
            ForecastDataPoint("Day 4", 4.0f, "Exercise planned", 78),
            ForecastDataPoint("Day 5", 4.5f, "Social activity", 85),
            ForecastDataPoint("Day 6", 4.3f, "Relaxation day", 80),
            ForecastDataPoint("Day 7", 3.9f, "Work preparation", 72)
        )
    }
    
    val riskFactors = remember {
        listOf(
            RiskFactor("Work Stress", "High", "Monday meetings increase stress"),
            RiskFactor("Sleep Pattern", "Medium", "Irregular sleep affects mood"),
            RiskFactor("Social Isolation", "Low", "Limited social interaction"),
            RiskFactor("Exercise Routine", "Medium", "Inconsistent workout schedule")
        )
    }
    
    val interventions = remember {
        listOf(
            Intervention("Morning Meditation", "Start day with 10-min meditation", "High Impact"),
            Intervention("Exercise Schedule", "Plan workouts for Tuesday & Thursday", "Medium Impact"),
            Intervention("Social Planning", "Schedule weekend social activities", "High Impact"),
            Intervention("Sleep Optimization", "Maintain consistent sleep schedule", "Medium Impact")
        )
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mood Forecasting") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { showSettingsDialog = true }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
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
            // AI Status and Controls
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
                                    Icons.Default.Psychology,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "AI Mood Forecasting",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            
                            Switch(
                                checked = isForecastingEnabled,
                                onCheckedChange = { isForecastingEnabled = it }
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = if (isForecastingEnabled) 
                                "AI is analyzing your patterns to predict future mood trends" 
                            else "Mood forecasting is currently disabled",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        
                        if (isForecastingEnabled) {
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(16.dp),
                                    strokeWidth = 2.dp
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Model accuracy: 87%",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }
            
            // Forecast Range Selector
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Forecast Range",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(forecastRanges) { range ->
                                FilterChip(
                                    onClick = { selectedForecastRange = range },
                                    label = { Text(range) },
                                    selected = selectedForecastRange == range
                                )
                            }
                        }
                    }
                }
            }
            
            // Mood Forecast Chart
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Predicted Mood Trend",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        ForecastChart(forecastData = forecastData)
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Avg Prediction: ${forecastData.map { it.predictedMood }.average().let { "%.1f".format(it) }}/5.0",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium
                            )
                            
                            Text(
                                text = "Confidence: ${forecastData.map { it.confidence }.average().toInt()}%",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
            
            // Risk Factors Analysis
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Risk Factors Analysis",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = "Factors that may impact your mood in the coming days",
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
            
            // Proactive Interventions
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
                                text = "Proactive Interventions",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = "AI-suggested actions to improve predicted mood",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        interventions.forEach { intervention ->
                            InterventionCard(intervention = intervention)
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
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        WarningCard(
                            title = "Mood Dip Predicted",
                            message = "AI predicts a potential mood decline on Day 3 (Monday)",
                            severity = "Medium",
                            action = "Schedule supportive activities"
                        )
                    }
                }
            }
            
            // Detailed Forecast Button
            item {
                Button(
                    onClick = onViewDetailedForecast,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Timeline, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("View Detailed Forecast")
                }
            }
        }
    }
    
    // Settings Dialog
    if (showSettingsDialog) {
        AlertDialog(
            onDismissRequest = { showSettingsDialog = false },
            title = { Text("Forecasting Settings") },
            text = {
                Column {
                    Text("• Model Training: Uses 30 days of mood data")
                    Text("• Update Frequency: Daily at 6 AM")
                    Text("• Privacy: All data processed locally")
                    Text("• Accuracy: 87% based on historical data")
                }
            },
            confirmButton = {
                TextButton(onClick = { showSettingsDialog = false }) {
                    Text("Close")
                }
            }
        )
    }
}

@Composable
fun ForecastChart(forecastData: List<ForecastDataPoint>) {
    val maxValue = 5.0f
    val minValue = 1.0f
    
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(
                    Color.LightGray.copy(alpha = 0.1f),
                    RoundedCornerShape(8.dp)
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {
                forecastData.forEach { dataPoint ->
                    val height = ((dataPoint.predictedMood - minValue) / (maxValue - minValue)) * 100
                    
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .width(20.dp)
                                .height(height.dp)
                                .background(
                                    when {
                                        dataPoint.predictedMood >= 4.0f -> Color(0xFF4CAF50)
                                        dataPoint.predictedMood >= 3.0f -> Color(0xFFFF9800)
                                        else -> Color(0xFFF44336)
                                    },
                                    RoundedCornerShape(4.dp)
                                )
                        )
                        
                        Spacer(modifier = Modifier.height(4.dp))
                        
                        Text(
                            text = dataPoint.day,
                            style = MaterialTheme.typography.bodySmall,
                            fontSize = 10.sp
                        )
                        
                        Text(
                            text = dataPoint.predictedMood.toString(),
                            style = MaterialTheme.typography.bodySmall,
                            fontSize = 8.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        
                        Text(
                            text = "${dataPoint.confidence}%",
                            style = MaterialTheme.typography.bodySmall,
                            fontSize = 8.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RiskFactorCard(factor: RiskFactor) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val riskColor = when (factor.level.lowercase()) {
                "high" -> Color(0xFFF44336)
                "medium" -> Color(0xFFFF9800)
                "low" -> Color(0xFF4CAF50)
                else -> MaterialTheme.colorScheme.primary
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
                text = factor.level,
                style = MaterialTheme.typography.bodySmall,
                color = riskColor,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun InterventionCard(intervention: Intervention) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Lightbulb,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = intervention.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium
                )
                
                Text(
                    text = intervention.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            val impactColor = when (intervention.impact.lowercase()) {
                "high impact" -> Color(0xFF4CAF50)
                "medium impact" -> Color(0xFFFF9800)
                "low impact" -> Color(0xFF2196F3)
                else -> MaterialTheme.colorScheme.primary
            }
            
            Text(
                text = intervention.impact,
                style = MaterialTheme.typography.bodySmall,
                color = impactColor,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun WarningCard(
    title: String,
    message: String,
    severity: String,
    action: String
) {
    val severityColor = when (severity.lowercase()) {
        "high" -> Color(0xFFF44336)
        "medium" -> Color(0xFFFF9800)
        "low" -> Color(0xFF4CAF50)
        else -> MaterialTheme.colorScheme.primary
    }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = severityColor
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "💡 Recommended Action: $action",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

data class ForecastDataPoint(
    val day: String,
    val predictedMood: Float,
    val note: String,
    val confidence: Int
)

data class RiskFactor(
    val name: String,
    val level: String,
    val description: String
)

data class Intervention(
    val title: String,
    val description: String,
    val impact: String
)
