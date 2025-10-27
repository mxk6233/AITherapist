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
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoodAnalyticsScreen(
    onNavigateBack: () -> Unit,
    onViewDetailedReport: () -> Unit
) {
    var selectedTimeRange by remember { mutableStateOf("Week") }
    var showInsightsDialog by remember { mutableStateOf(false) }
    
    val timeRanges = listOf("Week", "Month", "3 Months", "Year")
    
    val moodData = remember {
        listOf(
            MoodDataPoint("Mon", 3.5f, "Work stress"),
            MoodDataPoint("Tue", 4.2f, "Good workout"),
            MoodDataPoint("Wed", 2.8f, "Difficult meeting"),
            MoodDataPoint("Thu", 4.0f, "Friend visit"),
            MoodDataPoint("Fri", 4.5f, "Weekend plans"),
            MoodDataPoint("Sat", 4.8f, "Relaxing day"),
            MoodDataPoint("Sun", 3.2f, "Sunday blues")
        )
    }
    
    val moodPatterns = remember {
        listOf(
            MoodPattern("Work Stress", "High stress on Mondays and Wednesdays", "Consider meditation before work"),
            MoodPattern("Weekend Boost", "Mood improves significantly on weekends", "Plan more weekend activities"),
            MoodPattern("Exercise Impact", "Mood improves after physical activity", "Schedule regular workouts")
        )
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mood Analytics") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { showInsightsDialog = true }) {
                        Icon(Icons.Default.Insights, contentDescription = "Insights")
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
            // Time Range Selector
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Time Range",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(timeRanges) { range ->
                                FilterChip(
                                    onClick = { selectedTimeRange = range },
                                    label = { Text(range) },
                                    selected = selectedTimeRange == range
                                )
                            }
                        }
                    }
                }
            }
            
            // Mood Trend Chart
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Mood Trend",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        MoodChart(moodData = moodData)
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Average: ${moodData.map { it.value }.average().let { "%.1f".format(it) }}/5.0",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium
                            )
                            
                            Text(
                                text = "Trend: ${if (moodData.last().value > moodData.first().value) "↗ Improving" else "↘ Declining"}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (moodData.last().value > moodData.first().value) Color(0xFF4CAF50) else Color(0xFFF44336)
                            )
                        }
                    }
                }
            }
            
            // Mood Statistics
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Mood Statistics",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            StatCard(
                                title = "Best Day",
                                value = "Saturday",
                                subtitle = "4.8/5.0",
                                icon = Icons.Default.TrendingUp,
                                color = Color(0xFF4CAF50)
                            )
                            
                            StatCard(
                                title = "Challenging Day",
                                value = "Wednesday",
                                subtitle = "2.8/5.0",
                                icon = Icons.Default.TrendingDown,
                                color = Color(0xFFF44336)
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            StatCard(
                                title = "Consistency",
                                value = "75%",
                                subtitle = "Stable mood",
                                icon = Icons.Default.Straighten,
                                color = Color(0xFF2196F3)
                            )
                            
                            StatCard(
                                title = "Improvement",
                                value = "+0.7",
                                subtitle = "vs last week",
                                icon = Icons.Default.ShowChart,
                                color = Color(0xFF4CAF50)
                            )
                        }
                    }
                }
            }
            
            // Mood Patterns
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Mood Patterns",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = "AI-detected patterns in your mood data",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        moodPatterns.forEach { pattern ->
                            PatternCard(pattern = pattern)
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
            
            // Recommendations
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
                                text = "Personalized Recommendations",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        RecommendationItem(
                            title = "Morning Meditation",
                            description = "Start your day with 10 minutes of meditation to improve Monday mood",
                            priority = "High"
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        RecommendationItem(
                            title = "Exercise Schedule",
                            description = "Plan workouts on Tuesday and Thursday to maintain positive mood",
                            priority = "Medium"
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        RecommendationItem(
                            title = "Weekend Planning",
                            description = "Schedule more social activities on weekends to boost mood",
                            priority = "Low"
                        )
                    }
                }
            }
            
            // Detailed Report Button
            item {
                Button(
                    onClick = onViewDetailedReport,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Assessment, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("View Detailed Report")
                }
            }
        }
    }
    
    // Insights Dialog
    if (showInsightsDialog) {
        AlertDialog(
            onDismissRequest = { showInsightsDialog = false },
            title = { Text("Mood Insights") },
            text = {
                Column {
                    Text("• Your mood is most stable on weekends")
                    Text("• Work stress affects Monday and Wednesday moods")
                    Text("• Exercise has a positive impact on your mood")
                    Text("• Consider meditation to improve morning mood")
                }
            },
            confirmButton = {
                TextButton(onClick = { showInsightsDialog = false }) {
                    Text("Close")
                }
            }
        )
    }
}

@Composable
fun MoodChart(moodData: List<MoodDataPoint>) {
    val maxValue = 5.0f
    val minValue = 1.0f
    
    Column {
        // Chart Area
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(
                    Color.LightGray.copy(alpha = 0.1f),
                    RoundedCornerShape(8.dp)
                )
        ) {
            // Simple bar chart representation
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {
                moodData.forEach { dataPoint ->
                    val height = ((dataPoint.value - minValue) / (maxValue - minValue)) * 100
                    
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .width(20.dp)
                                .height(height.dp)
                                .background(
                                    when {
                                        dataPoint.value >= 4.0f -> Color(0xFF4CAF50)
                                        dataPoint.value >= 3.0f -> Color(0xFFFF9800)
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
                            text = dataPoint.value.toString(),
                            style = MaterialTheme.typography.bodySmall,
                            fontSize = 8.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(24.dp)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
            
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = color,
                textAlign = TextAlign.Center
            )
            
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun PatternCard(pattern: MoodPattern) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = pattern.title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = pattern.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = "💡 ${pattern.recommendation}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun RecommendationItem(
    title: String,
    description: String,
    priority: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val priorityColor = when (priority.lowercase()) {
            "high" -> Color(0xFFF44336)
            "medium" -> Color(0xFFFF9800)
            "low" -> Color(0xFF4CAF50)
            else -> MaterialTheme.colorScheme.primary
        }
        
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(priorityColor, RoundedCornerShape(4.dp))
        )
        
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
    }
}

data class MoodDataPoint(
    val day: String,
    val value: Float,
    val note: String
)

data class MoodPattern(
    val title: String,
    val description: String,
    val recommendation: String
)
