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
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalingPromptsScreen(
    onNavigateBack: () -> Unit,
    onStartJournaling: (String) -> Unit
) {
    var selectedCategory by remember { mutableStateOf("All") }
    var selectedMood by remember { mutableStateOf("All") }
    var showCustomPromptDialog by remember { mutableStateOf(false) }
    var customPrompt by remember { mutableStateOf("") }
    
    val categories = listOf("All", "Reflection", "Gratitude", "Growth", "Relationships", "Goals", "Challenges")
    val moods = listOf("All", "Happy", "Sad", "Anxious", "Grateful", "Motivated", "Stressed")
    
    val aiPrompts = remember {
        listOf(
            JournalPrompt(
                id = "1",
                title = "Daily Reflection",
                prompt = "What was the most meaningful moment of your day? How did it make you feel?",
                category = "Reflection",
                moodTags = listOf("All"),
                difficulty = "Easy",
                estimatedTime = "5-10 minutes",
                isAIGenerated = true,
                timesUsed = 3
            ),
            JournalPrompt(
                id = "2",
                title = "Gratitude Practice",
                prompt = "Write about three things you're grateful for today. What makes each one special?",
                category = "Gratitude",
                moodTags = listOf("Happy", "Grateful"),
                difficulty = "Easy",
                estimatedTime = "5 minutes",
                isAIGenerated = true,
                timesUsed = 7
            ),
            JournalPrompt(
                id = "3",
                title = "Growth Mindset",
                prompt = "Describe a challenge you faced recently. What did you learn from it? How did it help you grow?",
                category = "Growth",
                moodTags = listOf("Motivated", "Stressed"),
                difficulty = "Medium",
                estimatedTime = "10-15 minutes",
                isAIGenerated = true,
                timesUsed = 2
            ),
            JournalPrompt(
                id = "4",
                title = "Relationship Reflection",
                prompt = "Think about a recent interaction with someone important to you. What went well? What would you do differently?",
                category = "Relationships",
                moodTags = listOf("All"),
                difficulty = "Medium",
                estimatedTime = "10 minutes",
                isAIGenerated = true,
                timesUsed = 1
            ),
            JournalPrompt(
                id = "5",
                title = "Goal Setting",
                prompt = "What is one goal you want to achieve this week? What steps will you take to make it happen?",
                category = "Goals",
                moodTags = listOf("Motivated", "Happy"),
                difficulty = "Easy",
                estimatedTime = "8 minutes",
                isAIGenerated = true,
                timesUsed = 4
            ),
            JournalPrompt(
                id = "6",
                title = "Emotional Processing",
                prompt = "Describe a recent emotion you experienced. What triggered it? How did you respond? What would you do differently next time?",
                category = "Challenges",
                moodTags = listOf("Sad", "Anxious", "Stressed"),
                difficulty = "Medium",
                estimatedTime = "12-15 minutes",
                isAIGenerated = true,
                timesUsed = 2
            )
        )
    }
    
    val filteredPrompts = aiPrompts.filter { prompt ->
        val matchesCategory = selectedCategory == "All" || prompt.category == selectedCategory
        val matchesMood = selectedMood == "All" || prompt.moodTags.contains(selectedMood) || prompt.moodTags.contains("All")
        matchesCategory && matchesMood
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("AI Journaling Prompts") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { showCustomPromptDialog = true }) {
                        Icon(Icons.Default.Add, contentDescription = "Custom Prompt")
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
            // AI Status and Personalization
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
                            text = "AI-Powered Prompts",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "Prompts are personalized based on your mood patterns, writing history, and preferences",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.TrendingUp,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = Color(0xFF4CAF50)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Personalization: 92% accuracy",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF4CAF50)
                        )
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
                        text = "Filter Prompts",
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
                            text = "Recommended for You",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Text(
                        text = "Based on your recent mood and journaling patterns",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(aiPrompts.take(3)) { prompt ->
                            RecommendationCard(
                                prompt = prompt,
                                onClick = { onStartJournaling(prompt.id) }
                            )
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Prompt Library
            Text(
                text = "Prompt Library (${filteredPrompts.size})",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredPrompts) { prompt ->
                    PromptCard(
                        prompt = prompt,
                        onClick = { onStartJournaling(prompt.id) }
                    )
                }
            }
        }
    }
    
    // Custom Prompt Dialog
    if (showCustomPromptDialog) {
        AlertDialog(
            onDismissRequest = { showCustomPromptDialog = false },
            title = { Text("Generate Custom Prompt") },
            text = {
                Column {
                    Text("Describe what you'd like to write about:")
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = customPrompt,
                        onValueChange = { customPrompt = it },
                        label = { Text("Topic or theme") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 3
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = { 
                        showCustomPromptDialog = false
                        customPrompt = ""
                    }
                ) {
                    Text("Generate")
                }
            },
            dismissButton = {
                TextButton(onClick = { showCustomPromptDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun RecommendationCard(
    prompt: JournalPrompt,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(250.dp)
            .clickable { onClick() },
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
                    text = prompt.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                
                Icon(
                    Icons.Default.Psychology,
                    contentDescription = "AI Generated",
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = prompt.prompt,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 3
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = prompt.estimatedTime,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
                
                DifficultyChip(difficulty = prompt.difficulty)
            }
        }
    }
}

@Composable
fun PromptCard(
    prompt: JournalPrompt,
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
                    text = prompt.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                
                if (prompt.isAIGenerated) {
                    Icon(
                        Icons.Default.Psychology,
                        contentDescription = "AI Generated",
                        modifier = Modifier.size(20.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = prompt.prompt,
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
                        text = prompt.estimatedTime,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                
                DifficultyChip(difficulty = prompt.difficulty)
                
                if (prompt.timesUsed > 0) {
                    Text(
                        text = "${prompt.timesUsed}x used",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
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
                    items(prompt.moodTags.filter { it != "All" }) { mood ->
                        MoodTag(mood = mood)
                    }
                }
                
                CategoryChip(category = prompt.category)
            }
        }
    }
}

@Composable
fun CategoryChip(category: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = category,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Medium
        )
    }
}

data class JournalPrompt(
    val id: String,
    val title: String,
    val prompt: String,
    val category: String,
    val moodTags: List<String>,
    val difficulty: String,
    val estimatedTime: String,
    val isAIGenerated: Boolean,
    val timesUsed: Int
)
