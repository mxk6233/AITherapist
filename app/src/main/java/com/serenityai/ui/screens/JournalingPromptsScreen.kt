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

import com.serenityai.usecases.JournalingPromptsUseCase
import com.serenityai.data.models.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalingPromptsScreen(
    onNavigateBack: () -> Unit,
    onStartJournaling: (String) -> Unit
) {
    val useCase = remember { JournalingPromptsUseCase() }
    var selectedCategory by remember { mutableStateOf("All") }
    var selectedMood by remember { mutableStateOf("All") }
    var showCustomPromptDialog by remember { mutableStateOf(false) }
    var customPrompt by remember { mutableStateOf("") }
    
    // Sample data - in production would come from repositories
    val sampleMoodEntries = remember { generateSampleMoodEntries() }
    val sampleJournalHistory = remember { emptyList<com.serenityai.data.models.JournalEntry>() }
    
    val categories = listOf("All", "Reflection", "Gratitude", "Growth", "Relationships", "Goals", "Challenges")
    val moods = listOf("All", "Happy", "Sad", "Anxious", "Grateful", "Motivated", "Stressed")
    
    // Generate personalized prompts
    val promptRecommendation = remember {
        useCase.getRecommendedPrompts(
            userId = "user-123",
            currentMood = sampleMoodEntries.lastOrNull()?.mood,
            recentMoodEntries = sampleMoodEntries.takeLast(7),
            journalingHistory = sampleJournalHistory
        )
    }
    
    // Get all prompts
    val allPrompts = remember {
        useCase.generatePersonalizedPrompts(
            request = PromptGenerationRequest(
                userId = "user-123",
                currentMood = sampleMoodEntries.lastOrNull()?.mood,
                moodTags = extractMoodTagsFromMoodEntries(sampleMoodEntries)
            ),
            recentMoodEntries = sampleMoodEntries.takeLast(7),
            journalingHistory = sampleJournalHistory
        )
    }
    
    // Convert to UI model
    val aiPrompts = allPrompts.map { prompt ->
        JournalPrompt(
            id = prompt.id,
            title = prompt.title,
            prompt = prompt.prompt,
            category = prompt.category.name.replace("_", " "),
            moodTags = prompt.moodTags.map { it.name.lowercase().replaceFirstChar { it.uppercase() } },
            difficulty = prompt.difficulty.name.replaceFirstChar { it.uppercase() },
            estimatedTime = "${prompt.estimatedTime} minutes",
            isAIGenerated = prompt.isAIGenerated,
            timesUsed = prompt.timesUsed
        )
    }
    
    // Filter prompts
    val filteredPrompts = useCase.filterPrompts(
        prompts = allPrompts,
        category = if (selectedCategory == "All") null else parseCategory(selectedCategory),
        moodTags = if (selectedMood == "All") emptyList() else listOf(parseMoodTag(selectedMood))
    ).map { prompt ->
        JournalPrompt(
            id = prompt.id,
            title = prompt.title,
            prompt = prompt.prompt,
            category = prompt.category.name.replace("_", " "),
            moodTags = prompt.moodTags.map { it.name.lowercase().replaceFirstChar { it.uppercase() } },
            difficulty = prompt.difficulty.name.replaceFirstChar { it.uppercase() },
            estimatedTime = "${prompt.estimatedTime} minutes",
            isAIGenerated = prompt.isAIGenerated,
            timesUsed = prompt.timesUsed
        )
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
                            text = "Personalization: ${(promptRecommendation.personalizationScore * 100).toInt()}% accuracy",
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
                        items(promptRecommendation.recommendedPrompts.take(3).map { prompt ->
                            JournalPrompt(
                                id = prompt.id,
                                title = prompt.title,
                                prompt = prompt.prompt,
                                category = prompt.category.name.replace("_", " "),
                                moodTags = prompt.moodTags.map { it.name.lowercase().replaceFirstChar { it.uppercase() } },
                                difficulty = prompt.difficulty.name.replaceFirstChar { it.uppercase() },
                                estimatedTime = "${prompt.estimatedTime} minutes",
                                isAIGenerated = prompt.isAIGenerated,
                                timesUsed = prompt.timesUsed
                            )
                        }) { prompt ->
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

// Data class for UI - keeping for backward compatibility
// Main data model is in com.serenityai.data.models.JournalingPrompt
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

// Helper functions
private fun generateSampleMoodEntries(): List<com.serenityai.data.models.MoodEntry> {
    val entries = mutableListOf<com.serenityai.data.models.MoodEntry>()
    val calendar = Calendar.getInstance()
    
    repeat(7) { dayOffset ->
        calendar.time = Date(System.currentTimeMillis() - (7 - dayOffset) * 86400000L)
        entries.add(
            com.serenityai.data.models.MoodEntry(
                id = "mood-$dayOffset",
                userId = "user-123",
                date = calendar.time,
                mood = (3 + Math.random() * 4).toInt().coerceIn(1, 10),
                energy = 5,
                stress = 5,
                anxiety = 5,
                sleep = 6
            )
        )
    }
    
    return entries
}

private fun extractMoodTagsFromMoodEntries(entries: List<com.serenityai.data.models.MoodEntry>): List<MoodTag> {
    if (entries.isEmpty()) return emptyList()
    val averageMood = entries.map { it.mood.toDouble() }.average().toInt()
    return listOf(determineMoodTagFromValue(averageMood))
}

private fun determineMoodTagFromValue(mood: Int): MoodTag {
    return when {
        mood >= 8 -> MoodTag.HAPPY
        mood >= 7 -> MoodTag.GRATEFUL
        mood >= 6 -> MoodTag.CALM
        mood >= 5 -> MoodTag.MOTIVATED
        mood >= 4 -> MoodTag.STRESSED
        mood >= 3 -> MoodTag.SAD
        mood >= 2 -> MoodTag.ANXIOUS
        else -> MoodTag.OVERWHELMED
    }
}

private fun parseCategory(category: String): PromptCategory? {
    return when (category.lowercase()) {
        "reflection" -> PromptCategory.REFLECTION
        "gratitude" -> PromptCategory.GRATITUDE
        "growth" -> PromptCategory.GROWTH
        "relationships" -> PromptCategory.RELATIONSHIPS
        "goals" -> PromptCategory.GOALS
        "challenges" -> PromptCategory.CHALLENGES
        else -> null
    }
}

private fun parseMoodTag(mood: String): MoodTag {
    return when (mood.lowercase()) {
        "happy" -> MoodTag.HAPPY
        "sad" -> MoodTag.SAD
        "anxious" -> MoodTag.ANXIOUS
        "grateful" -> MoodTag.GRATEFUL
        "motivated" -> MoodTag.MOTIVATED
        "stressed" -> MoodTag.STRESSED
        "calm" -> MoodTag.CALM
        "overwhelmed" -> MoodTag.OVERWHELMED
        else -> MoodTag.ALL
    }
}
