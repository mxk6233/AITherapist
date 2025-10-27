package com.serenityai.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CopingExercisesScreen(
    onNavigateBack: () -> Unit,
    onStartExercise: (String) -> Unit
) {
    var selectedCategory by remember { mutableStateOf("All") }
    var selectedMood by remember { mutableStateOf("All") }
    var showProgressDialog by remember { mutableStateOf(false) }
    
    val categories = listOf("All", "Breathing", "Mindfulness", "Physical", "Creative", "Social")
    val moods = listOf("All", "Anxious", "Sad", "Angry", "Stressed", "Overwhelmed")
    
    val exercises = remember {
        listOf(
            CopingExercise(
                id = "1",
                title = "4-7-8 Breathing",
                description = "Calm your nervous system with this simple breathing technique",
                category = "Breathing",
                duration = "5 minutes",
                difficulty = "Easy",
                moodTags = listOf("Anxious", "Stressed"),
                effectiveness = 4.5f,
                isCompleted = true,
                timesCompleted = 3
            ),
            CopingExercise(
                id = "2",
                title = "Body Scan Meditation",
                description = "Progressive relaxation to release tension throughout your body",
                category = "Mindfulness",
                duration = "15 minutes",
                difficulty = "Medium",
                moodTags = listOf("Stressed", "Overwhelmed"),
                effectiveness = 4.8f,
                isCompleted = false,
                timesCompleted = 0
            ),
            CopingExercise(
                id = "3",
                title = "Progressive Muscle Relaxation",
                description = "Systematically tense and release muscle groups for deep relaxation",
                category = "Physical",
                duration = "20 minutes",
                difficulty = "Medium",
                moodTags = listOf("Stressed", "Angry"),
                effectiveness = 4.2f,
                isCompleted = true,
                timesCompleted = 1
            ),
            CopingExercise(
                id = "4",
                title = "Gratitude Journaling",
                description = "Write down three things you're grateful for today",
                category = "Creative",
                duration = "10 minutes",
                difficulty = "Easy",
                moodTags = listOf("Sad", "Overwhelmed"),
                effectiveness = 4.6f,
                isCompleted = false,
                timesCompleted = 0
            ),
            CopingExercise(
                id = "5",
                title = "Grounding 5-4-3-2-1",
                description = "Use your senses to ground yourself in the present moment",
                category = "Mindfulness",
                duration = "5 minutes",
                difficulty = "Easy",
                moodTags = listOf("Anxious", "Overwhelmed"),
                effectiveness = 4.7f,
                isCompleted = true,
                timesCompleted = 5
            ),
            CopingExercise(
                id = "6",
                title = "Gentle Yoga Flow",
                description = "Slow, mindful movements to connect body and mind",
                category = "Physical",
                duration = "25 minutes",
                difficulty = "Medium",
                moodTags = listOf("Stressed", "Sad"),
                effectiveness = 4.4f,
                isCompleted = false,
                timesCompleted = 0
            )
        )
    }
    
    val filteredExercises = exercises.filter { exercise ->
        val matchesCategory = selectedCategory == "All" || exercise.category == selectedCategory
        val matchesMood = selectedMood == "All" || exercise.moodTags.contains(selectedMood)
        matchesCategory && matchesMood
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Coping Exercises") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { showProgressDialog = true }) {
                        Icon(Icons.Default.Analytics, contentDescription = "Progress")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Personalized Recommendations
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
                            text = "Personalized for You",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Text(
                        text = "Based on your recent mood patterns, we recommend:",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(exercises.take(3)) { exercise ->
                            RecommendationCard(
                                exercise = exercise,
                                onClick = { onStartExercise(exercise.id) }
                            )
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Filter Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Filter Exercises",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Text(
                        text = "Category:",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(categories) { category ->
                            FilterChip(
                                onClick = { selectedCategory = category },
                                label = { Text(category) },
                                selected = selectedCategory == category
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Text(
                        text = "Mood:",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(moods) { mood ->
                            FilterChip(
                                onClick = { selectedMood = mood },
                                label = { Text(mood) },
                                selected = selectedMood == mood
                            )
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Exercise Library
            Text(
                text = "Exercise Library (${filteredExercises.size})",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredExercises) { exercise ->
                    ExerciseCard(
                        exercise = exercise,
                        onClick = { onStartExercise(exercise.id) }
                    )
                }
            }
        }
    }
    
    // Progress Dialog
    if (showProgressDialog) {
        AlertDialog(
            onDismissRequest = { showProgressDialog = false },
            title = { Text("Exercise Progress") },
            text = {
                Column {
                    Text("Completed Exercises: ${exercises.count { it.isCompleted }}")
                    Text("Total Sessions: ${exercises.sumOf { it.timesCompleted }}")
                    Text("Average Effectiveness: ${exercises.map { it.effectiveness }.average().let { "%.1f".format(it) }}/5.0")
                }
            },
            confirmButton = {
                TextButton(onClick = { showProgressDialog = false }) {
                    Text("Close")
                }
            }
        )
    }
}

@Composable
fun RecommendationCard(
    exercise: CopingExercise,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(200.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = exercise.title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                maxLines = 2
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = exercise.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = exercise.duration,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
                
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = Color(0xFFFFD700)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = exercise.effectiveness.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFFFFD700)
                    )
                }
            }
        }
    }
}

@Composable
fun ExerciseCard(
    exercise: CopingExercise,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = exercise.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                
                if (exercise.isCompleted) {
                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = "Completed",
                        tint = Color(0xFF4CAF50),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = exercise.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Timer,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = exercise.duration,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = Color(0xFFFFD700)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = exercise.effectiveness.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFFFFD700)
                    )
                }
                
                DifficultyChip(difficulty = exercise.difficulty)
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(exercise.moodTags) { mood ->
                        MoodTag(mood = mood)
                    }
                }
                
                if (exercise.timesCompleted > 0) {
                    Text(
                        text = "${exercise.timesCompleted}x completed",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun DifficultyChip(difficulty: String) {
    val color = when (difficulty.lowercase()) {
        "easy" -> Color(0xFF4CAF50)
        "medium" -> Color(0xFFFF9800)
        "hard" -> Color(0xFFF44336)
        else -> MaterialTheme.colorScheme.primary
    }
    
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(color.copy(alpha = 0.1f))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = difficulty,
            style = MaterialTheme.typography.bodySmall,
            color = color,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun MoodTag(mood: String) {
    val color = when (mood.lowercase()) {
        "anxious" -> Color(0xFFFF9800)
        "sad" -> Color(0xFF2196F3)
        "angry" -> Color(0xFFF44336)
        "stressed" -> Color(0xFFFF5722)
        "overwhelmed" -> Color(0xFF9C27B0)
        else -> MaterialTheme.colorScheme.primary
    }
    
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(color.copy(alpha = 0.1f))
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Text(
            text = mood,
            style = MaterialTheme.typography.bodySmall,
            color = color,
            fontSize = 10.sp
        )
    }
}

data class CopingExercise(
    val id: String,
    val title: String,
    val description: String,
    val category: String,
    val duration: String,
    val difficulty: String,
    val moodTags: List<String>,
    val effectiveness: Float,
    val isCompleted: Boolean,
    val timesCompleted: Int
)
