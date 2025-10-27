package com.serenityai.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import java.util.UUID

// Data classes for mood logging
data class MoodEntry(
    val id: String,
    val mood: MoodLevel,
    val timestamp: Long,
    val notes: String = "",
    val activities: List<String> = emptyList(),
    val triggers: List<String> = emptyList()
)

enum class MoodLevel(val emoji: String, val color: Color, val description: String) {
    VERY_HAPPY("😄", Color(0xFF4CAF50), "Very Happy"),
    HAPPY("😊", Color(0xFF8BC34A), "Happy"),
    NEUTRAL("😐", Color(0xFFFFC107), "Neutral"),
    SAD("😢", Color(0xFFFF9800), "Sad"),
    VERY_SAD("😭", Color(0xFFF44336), "Very Sad"),
    ANXIOUS("😰", Color(0xFF9C27B0), "Anxious"),
    STRESSED("😤", Color(0xFFE91E63), "Stressed"),
    CALM("😌", Color(0xFF2196F3), "Calm")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogDailyMoodScreenDetailed(onNavigateBack: () -> Unit) {
    var selectedMood by remember { mutableStateOf<MoodLevel?>(null) }
    var moodNotes by remember { mutableStateOf("") }
    var selectedActivities by remember { mutableStateOf<List<String>>(emptyList()) }
    var selectedTriggers by remember { mutableStateOf<List<String>>(emptyList()) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    
    val moodActivities = listOf(
        "Exercise", "Work", "Socializing", "Reading", "Music",
        "Cooking", "Walking", "Meditation", "Sleep", "Family Time"
    )
    
    val moodTriggers = listOf(
        "Work Stress", "Relationship Issues", "Health Concerns",
        "Financial Worries", "Social Anxiety", "Weather",
        "Sleep Issues", "News", "Social Media", "None"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Log Daily Mood") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
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
                Text(
                    text = "How are you feeling today?",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            
            item {
                MoodSelector(
                    selectedMood = selectedMood,
                    onMoodSelected = { selectedMood = it }
                )
            }
            
            item {
                Text(
                    text = "What activities did you do today?",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            
            item {
                ActivitySelector(
                    activities = moodActivities,
                    selectedActivities = selectedActivities,
                    onActivityToggle = { activity ->
                        selectedActivities = if (selectedActivities.contains(activity)) {
                            selectedActivities - activity
                        } else {
                            selectedActivities + activity
                        }
                    }
                )
            }
            
            item {
                Text(
                    text = "What might have influenced your mood?",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            
            item {
                TriggerSelector(
                    triggers = moodTriggers,
                    selectedTriggers = selectedTriggers,
                    onTriggerToggle = { trigger ->
                        selectedTriggers = if (selectedTriggers.contains(trigger)) {
                            selectedTriggers - trigger
                        } else {
                            selectedTriggers + trigger
                        }
                    }
                )
            }
            
            item {
                Text(
                    text = "Additional Notes (Optional)",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            
            item {
                OutlinedTextField(
                    value = moodNotes,
                    onValueChange = { moodNotes = it },
                    label = { Text("How are you feeling? Any thoughts?") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3,
                    maxLines = 5
                )
            }
            
            item {
                Button(
                    onClick = {
                        if (selectedMood != null) {
                            val moodEntry = MoodEntry(
                                id = UUID.randomUUID().toString(),
                                mood = selectedMood!!,
                                timestamp = System.currentTimeMillis(),
                                notes = moodNotes,
                                activities = selectedActivities,
                                triggers = selectedTriggers
                            )
                            // Here you would save the mood entry
                            showSuccessDialog = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = selectedMood != null
                ) {
                    Text("Save Mood Entry", fontSize = 16.sp)
                }
            }
        }
    }
    
    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { showSuccessDialog = false },
            title = { Text("Mood Logged Successfully!") },
            text = { Text("Your mood has been saved. Keep tracking to see patterns over time.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showSuccessDialog = false
                        onNavigateBack()
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }
}

@Composable
fun MoodSelector(
    selectedMood: MoodLevel?,
    onMoodSelected: (MoodLevel) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val moods = MoodLevel.values()
        val moodRows = listOf(
            moods.slice(0..3),
            moods.slice(4..7)
        )
        
        moodRows.forEach { moodRow ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                moodRow.forEach { mood ->
                    MoodButton(
                        mood = mood,
                        isSelected = selectedMood == mood,
                        onClick = { onMoodSelected(mood) }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoodButton(
    mood: MoodLevel,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.size(80.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) mood.color.copy(alpha = 0.3f) else MaterialTheme.colorScheme.surface
        ),
        border = if (isSelected) CardDefaults.outlinedCardBorder().copy(
            brush = androidx.compose.foundation.BorderStroke(2.dp, mood.color).brush
        ) else null
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = mood.emoji,
                fontSize = 32.sp
            )
            Text(
                text = mood.description,
                fontSize = 10.sp,
                textAlign = TextAlign.Center,
                maxLines = 2
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivitySelector(
    activities: List<String>,
    selectedActivities: List<String>,
    onActivityToggle: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val activityRows = listOf(
            activities.slice(0..4),
            activities.slice(5..9)
        )
        
        activityRows.forEach { activityRow ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                activityRow.forEach { activity ->
                    FilterChip(
                        onClick = { onActivityToggle(activity) },
                        label = { Text(activity) },
                        selected = selectedActivities.contains(activity),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TriggerSelector(
    triggers: List<String>,
    selectedTriggers: List<String>,
    onTriggerToggle: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val triggerRows = listOf(
            triggers.slice(0..4),
            triggers.slice(5..9)
        )
        
        triggerRows.forEach { triggerRow ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                triggerRow.forEach { trigger ->
                    FilterChip(
                        onClick = { onTriggerToggle(trigger) },
                        label = { Text(trigger) },
                        selected = selectedTriggers.contains(trigger),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}
