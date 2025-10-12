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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.ExperimentalMaterial3Api

@OptIn(ExperimentalMaterial3Api::class)
data class MoodFeature(
    val title: String,
    val description: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val onClick: () -> Unit
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoodAnalyticsScreen(
    onNavigateToMoodLogging: () -> Unit,
    onNavigateToMoodAnalytics: () -> Unit,
    onNavigateToMoodForecasting: () -> Unit,
    onNavigateToBurnoutDetection: () -> Unit,
    onNavigateToRelapsePrevention: () -> Unit,
    onNavigateBack: () -> Unit
) {
    val moodFeatures = listOf(
        MoodFeature(
            title = "Log Daily Mood",
            description = "Track your daily emotional state",
            icon = Icons.Default.Mood,
            onClick = onNavigateToMoodLogging
        ),
        MoodFeature(
            title = "Mood Analytics",
            description = "View insights and patterns",
            icon = Icons.Default.Analytics,
            onClick = onNavigateToMoodAnalytics
        ),
        MoodFeature(
            title = "Mood Forecasting",
            description = "Predict future mood patterns",
            icon = Icons.Default.TrendingUp,
            onClick = onNavigateToMoodForecasting
        ),
        MoodFeature(
            title = "Burnout Detection",
            description = "Identify signs of burnout",
            icon = Icons.Default.Warning,
            onClick = onNavigateToBurnoutDetection
        ),
        MoodFeature(
            title = "Relapse Prevention",
            description = "Prevent mental health relapses",
            icon = Icons.Default.Shield,
            onClick = onNavigateToRelapsePrevention
        )
    )
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text(
                text = "Mood & Analytics",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Current Mood Status
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
                Text(
                    text = "Today's Mood",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    repeat(5) { index ->
                        Icon(
                            Icons.Default.SentimentSatisfied,
                            contentDescription = null,
                            tint = if (index < 3) 
                                MaterialTheme.colorScheme.primary 
                            else 
                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Good",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Features List
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(moodFeatures) { feature ->
                MoodFeatureCard(feature = feature)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoodFeatureCard(feature: MoodFeature) {
    Card(
        onClick = feature.onClick,
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                feature.icon,
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = feature.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = feature.description,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
            Icon(
                Icons.Default.ArrowForward,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        }
    }
}

// LogDailyMoodScreen is now implemented in MoodLoggingScreen.kt
// This function is kept for backward compatibility but redirects to the new implementation
@Composable
fun LogDailyMoodScreen(onNavigateBack: () -> Unit) {
    // Use the comprehensive MoodLoggingScreen from MoodLoggingScreen.kt
    LogDailyMoodScreenDetailed(onNavigateBack = onNavigateBack)
}

@Composable
fun MoodAnalyticsViewScreen(onNavigateBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text(
                text = "Mood Analytics",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Mood Trends",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "📈 Chart visualization would go here",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Your mood has been improving over the past week!",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun MoodForecastingScreen(onNavigateBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text(
                text = "Mood Forecasting",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Predicted Mood Trends",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "🔮 AI prediction model would analyze patterns here",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Based on your patterns, you might feel more energetic tomorrow!",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BurnoutDetectionScreen(onNavigateBack: () -> Unit) {
    var currentRiskLevel by remember { mutableStateOf("Low") }
    var showAssessmentDialog by remember { mutableStateOf(false) }
    var assessmentResults by remember { mutableStateOf<List<BurnoutIndicator>>(emptyList()) }
    
    val burnoutIndicators = listOf(
        BurnoutIndicator("Work Overload", "Feeling overwhelmed by work demands", 3),
        BurnoutIndicator("Emotional Exhaustion", "Feeling drained and emotionally depleted", 2),
        BurnoutIndicator("Reduced Performance", "Decreased productivity and effectiveness", 1),
        BurnoutIndicator("Cynicism", "Negative or detached attitude toward work", 2),
        BurnoutIndicator("Sleep Issues", "Difficulty sleeping or poor sleep quality", 3),
        BurnoutIndicator("Physical Symptoms", "Headaches, muscle tension, or other stress symptoms", 1)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Burnout Risk Assessment") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
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
            item {
                RiskLevelCard(currentRiskLevel)
            }
            
            item {
                Button(
                    onClick = { showAssessmentDialog = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Take Burnout Assessment")
                }
            }
            
            item {
                Text(
                    text = "Burnout Prevention Tips",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            
            item {
                PreventionTipsCard()
            }
            
            item {
                Text(
                    text = "Recent Patterns",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            
            item {
                BurnoutPatternsCard()
            }
        }
    }
    
    if (showAssessmentDialog) {
        BurnoutAssessmentDialog(
            indicators = burnoutIndicators,
            onAssessmentComplete = { results ->
                assessmentResults = results
                val totalScore = results.sumOf { it.severity }
                currentRiskLevel = when {
                    totalScore >= 15 -> "High"
                    totalScore >= 10 -> "Medium"
                    else -> "Low"
                }
                showAssessmentDialog = false
            },
            onDismiss = { showAssessmentDialog = false }
        )
    }
}

@Composable
fun RiskLevelCard(riskLevel: String) {
    val color = when (riskLevel) {
        "High" -> MaterialTheme.colorScheme.errorContainer
        "Medium" -> MaterialTheme.colorScheme.tertiaryContainer
        else -> MaterialTheme.colorScheme.primaryContainer
    }
    
    val textColor = when (riskLevel) {
        "High" -> MaterialTheme.colorScheme.onErrorContainer
        "Medium" -> MaterialTheme.colorScheme.onTertiaryContainer
        else -> MaterialTheme.colorScheme.onPrimaryContainer
    }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                when (riskLevel) {
                    "High" -> Icons.Default.Warning
                    "Medium" -> Icons.Default.Info
                    else -> Icons.Default.CheckCircle
                },
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = textColor
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Current Risk Level: $riskLevel",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = when (riskLevel) {
                    "High" -> "Immediate attention recommended. Consider professional support."
                    "Medium" -> "Monitor closely. Implement stress management strategies."
                    else -> "Good! Continue current self-care practices."
                },
                style = MaterialTheme.typography.bodyMedium,
                color = textColor,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun PreventionTipsCard() {
    val tips = listOf(
        "Set clear work-life boundaries",
        "Take regular breaks throughout the day",
        "Practice stress-reduction techniques",
        "Maintain a healthy sleep schedule",
        "Stay connected with supportive people",
        "Engage in activities you enjoy"
    )
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            tips.forEach { tip ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = tip,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun BurnoutPatternsCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Weekly Burnout Indicators",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            
            // Placeholder for chart
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                        RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Burnout Pattern Chart\n(Last 7 days)",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BurnoutAssessmentDialog(
    indicators: List<BurnoutIndicator>,
    onAssessmentComplete: (List<BurnoutIndicator>) -> Unit,
    onDismiss: () -> Unit
) {
    var currentIndicatorIndex by remember { mutableStateOf(0) }
    var responses by remember { mutableStateOf<List<BurnoutIndicator>>(emptyList()) }
    
    if (currentIndicatorIndex < indicators.size) {
        val currentIndicator = indicators[currentIndicatorIndex]
        
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { 
                Text("Burnout Assessment") 
            },
            text = {
                Column {
                    Text(
                        text = "Question ${currentIndicatorIndex + 1} of ${indicators.size}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = currentIndicator.description,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Rate your experience (1-5):",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        (1..5).forEach { rating ->
                            FilterChip(
                                selected = currentIndicator.severity == rating,
                                onClick = { 
                                    val updatedIndicator = currentIndicator.copy(severity = rating)
                                    responses = responses + updatedIndicator
                                    currentIndicatorIndex++
                                },
                                label = { Text(rating.toString()) }
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (currentIndicatorIndex >= indicators.size) {
                            onAssessmentComplete(responses)
                        }
                    }
                ) {
                    Text("Next")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Cancel")
                }
            }
        )
    } else {
        // Assessment complete
        LaunchedEffect(Unit) {
            onAssessmentComplete(responses)
        }
    }
}

data class BurnoutIndicator(
    val name: String,
    val description: String,
    var severity: Int // 1-5 scale
)

@Composable
fun RelapsePreventionScreen(onNavigateBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text(
                text = "Relapse Prevention",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    Icons.Default.Shield,
                    contentDescription = null,
                    modifier = Modifier.size(48.dp),
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Prevention Strategies",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "🛡️ AI would monitor for relapse warning signs here",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Stay strong! You're doing great with your recovery",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
