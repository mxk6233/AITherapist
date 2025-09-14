package com.serenityai.ui.mood

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.serenityai.data.models.MoodEntry
import com.serenityai.utils.Helpers

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoodScreen(
    moodEntries: List<MoodEntry>,
    onSaveMood: (Int) -> Unit,
    isLoading: Boolean = false
) {
    var selectedMood by remember { mutableStateOf(5) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "How are you feeling today?",
            style = MaterialTheme.typography.headlineMedium
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Mood selector
        MoodSelector(
            selectedMood = selectedMood,
            onMoodSelected = { selectedMood = it }
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Save button
        Button(
            onClick = { onSaveMood(selectedMood) },
            enabled = !isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(16.dp))
            } else {
                Text("Save Mood")
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Recent mood entries
        if (moodEntries.isNotEmpty()) {
            Text(
                text = "Recent Moods",
                style = MaterialTheme.typography.titleMedium
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(moodEntries.takeLast(7)) { entry ->
                    MoodEntryCard(entry = entry)
                }
            }
        }
    }
}

@Composable
fun MoodSelector(
    selectedMood: Int,
    onMoodSelected: (Int) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items((1..10).toList()) { mood ->
            MoodButton(
                mood = mood,
                isSelected = mood == selectedMood,
                onClick = { onMoodSelected(mood) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoodButton(
    mood: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            }
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = Helpers.getMoodEmoji(mood),
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = mood.toString(),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun MoodEntryCard(entry: MoodEntry) {
    Card(
        modifier = Modifier.width(80.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = Helpers.getMoodEmoji(entry.mood),
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = entry.mood.toString(),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
