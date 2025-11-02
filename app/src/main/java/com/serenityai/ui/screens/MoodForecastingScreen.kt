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
import com.serenityai.usecases.MoodForecastingUseCase
import com.serenityai.data.models.*
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoodForecastingScreen(
    onNavigateBack: () -> Unit,
    onViewDetailedForecast: () -> Unit
) {
    val useCase = remember { MoodForecastingUseCase() }
    var selectedForecastPeriod by remember { mutableStateOf("7 Days") }
    var showInsightsDialog by remember { mutableStateOf(false) }
    var moodForecast by remember { mutableStateOf<MoodForecast?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    
    val forecastPeriods = listOf("7 Days", "14 Days", "30 Days", "90 Days")
    
    // Sample historical mood data - in production would come from repository
    val historicalMoodData = remember {
        // Generate sample data for demonstration
        generateHistoricalMoodData()
    }
    
    // Generate forecast when screen loads
    LaunchedEffect(Unit) {
        isLoading = true
        moodForecast = useCase.generate7DayForecast(historicalMoodData)
        isLoading = false
    }
    
    val forecastData = moodForecast?.forecastDays?.map { 
        ForecastDay(it.day, it.predictedMood, it.note) 
    } ?: emptyList()
    
    val predictions = moodForecast?.predictions?.map {
        MoodPrediction(it.title, it.description, it.confidence)
    } ?: emptyList()
    
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
                    IconButton(onClick = { showInsightsDialog = true }) {
                        Icon(Icons.Default.Psychology, contentDescription = "Insights")
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
            // Period Selector
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Forecast Period",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(forecastPeriods) { period ->
                                FilterChip(
                                    onClick = { selectedForecastPeriod = period },
                                    label = { Text(period) },
                                    selected = selectedForecastPeriod == period
                                )
                            }
                        }
                    }
                }
            }
            
            // Forecast Summary
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
                                Icons.Default.TrendingUp,
                                contentDescription = null,
                                tint = Color(0xFF4CAF50)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Forecast Summary",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            ForecastStatCard(
                                title = "Average Mood",
                                value = forecastData.map { it.predictedMood }.average().let { "%.1f".format(it) },
                                subtitle = "Expected range",
                                color = Color(0xFF2196F3)
                            )
                            
                            ForecastStatCard(
                                title = "Stability",
                                value = "72%",
                                subtitle = "Low volatility expected",
                                color = Color(0xFF4CAF50)
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            ForecastStatCard(
                                title = "Improvement Trend",
                                value = "+0.3",
                                subtitle = "vs current average",
                                color = Color(0xFF4CAF50)
                            )
                            
                            ForecastStatCard(
                                title = "Risk Level",
                                value = "Low",
                                subtitle = "No alerts expected",
                                color = Color(0xFFFF9800)
                            )
                        }
                    }
                }
            }
            
            // Forecast Chart
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "7-Day Forecast",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        ForecastChart(forecastData = forecastData)
                    }
                }
            }
            
            // Predictions
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
                                Icons.Default.Psychology,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "AI Predictions",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        predictions.forEach { prediction ->
                            PredictionCard(prediction = prediction)
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
            
            // Detailed Report Button
            item {
                Button(
                    onClick = onViewDetailedForecast,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Analytics, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("View Detailed Forecast")
                }
            }
        }
    }
    
    // Insights Dialog
    if (showInsightsDialog) {
        AlertDialog(
            onDismissRequest = { showInsightsDialog = false },
            title = { Text("Forecasting Insights") },
            text = {
                Column {
                    Text("• Your mood patterns show a positive trend over the next week")
                    Text("• Weekends typically boost your mood by 0.5 points")
                    Text("• Exercise days predict higher mood stability")
                    Text("• Consider planning activities on Wednesday to offset midweek dip")
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
fun ForecastChart(forecastData: List<ForecastDay>) {
    val maxValue = 5.0f
    val minValue = 1.0f
    
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
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
            forecastData.forEach { data ->
                val height = ((data.predictedMood - minValue) / (maxValue - minValue)) * 100
                
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .width(30.dp)
                            .height(height.dp)
                            .background(
                                when {
                                    data.predictedMood >= 4.0f -> Color(0xFF4CAF50)
                                    data.predictedMood >= 3.0f -> Color(0xFFFF9800)
                                    else -> Color(0xFFF44336)
                                },
                                RoundedCornerShape(4.dp)
                            )
                    )
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    Text(
                        text = data.day,
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 9.sp
                    )
                    
                    Text(
                        text = data.predictedMood.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 8.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun ForecastStatCard(
    title: String,
    value: String,
    subtitle: String,
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
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
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
                textAlign = TextAlign.Center,
                fontSize = 10.sp
            )
        }
    }
}

@Composable
fun PredictionCard(prediction: MoodPrediction) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Lightbulb,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = prediction.title,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Text(
                        text = "${prediction.confidence}%",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )
                }
                
                Text(
                    text = prediction.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

// Data classes moved to com.serenityai.data.models.MoodForecast
// Keeping these for backward compatibility with existing code
data class ForecastDay(
    val day: String,
    val predictedMood: Float,
    val note: String
)

data class MoodPrediction(
    val title: String,
    val description: String,
    val confidence: Float
)

// Helper function to generate sample historical data
private fun generateHistoricalMoodData(): List<com.serenityai.data.models.MoodEntry> {
    val entries = mutableListOf<com.serenityai.data.models.MoodEntry>()
    val calendar = Calendar.getInstance()
    
    // Generate 30 days of historical data
    repeat(30) { dayOffset ->
        calendar.time = Date(System.currentTimeMillis() - (30 - dayOffset) * 86400000L)
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        
        // Simulate weekend boost pattern
        val baseMood = 3.5
        val mood = when {
            dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY -> baseMood + 0.5 + Math.random() * 0.5
            dayOfWeek == Calendar.WEDNESDAY -> baseMood - 0.3 + Math.random() * 0.3
            else -> baseMood + Math.random() * 0.5 - 0.25
        }.coerceIn(1.0, 5.0).toInt()
        
        entries.add(
            com.serenityai.data.models.MoodEntry(
                id = "entry-$dayOffset",
                userId = "user-123",
                date = calendar.time,
                mood = mood,
                energy = 5,
                stress = 5,
                anxiety = 5,
                sleep = 5
            )
        )
    }
    
    return entries
}
