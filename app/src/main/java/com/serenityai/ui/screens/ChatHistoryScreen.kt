package com.serenityai.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatHistoryScreen(
    onNavigateBack: () -> Unit,
    onNavigateToChat: (String) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("All") }
    var showExportDialog by remember { mutableStateOf(false) }
    
    val chatSessions = remember {
        listOf(
            ChatSession(
                id = "1",
                title = "Anxiety Support Session",
                lastMessage = "Thank you for the breathing techniques",
                timestamp = System.currentTimeMillis() - 86400000, // 1 day ago
                messageCount = 15,
                moodLevel = "Anxious"
            ),
            ChatSession(
                id = "2", 
                title = "Daily Check-in",
                lastMessage = "I'm feeling much better today",
                timestamp = System.currentTimeMillis() - 172800000, // 2 days ago
                messageCount = 8,
                moodLevel = "Good"
            ),
            ChatSession(
                id = "3",
                title = "Stress Management",
                lastMessage = "The meditation helped a lot",
                timestamp = System.currentTimeMillis() - 259200000, // 3 days ago
                messageCount = 22,
                moodLevel = "Stressed"
            ),
            ChatSession(
                id = "4",
                title = "Crisis Support",
                lastMessage = "I'm feeling safer now",
                timestamp = System.currentTimeMillis() - 345600000, // 4 days ago
                messageCount = 35,
                moodLevel = "Crisis"
            )
        )
    }
    
    val filteredSessions = chatSessions.filter { session ->
        val matchesSearch = session.title.contains(searchQuery, ignoreCase = true) ||
                          session.lastMessage.contains(searchQuery, ignoreCase = true)
        val matchesFilter = selectedFilter == "All" || session.moodLevel == selectedFilter
        matchesSearch && matchesFilter
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chat History") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { showExportDialog = true }) {
                        Icon(Icons.Default.Download, contentDescription = "Export")
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
            // Search and Filter Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        label = { Text("Search conversations") },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Text(
                        text = "Filter by mood:",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf("All", "Good", "Anxious", "Stressed", "Crisis").forEach { filter ->
                            FilterChip(
                                onClick = { selectedFilter = filter },
                                label = { Text(filter) },
                                selected = selectedFilter == filter
                            )
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Analytics Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Chat Analytics",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        AnalyticsCard(
                            title = "Total Sessions",
                            value = "${chatSessions.size}",
                            icon = Icons.Default.Chat,
                            color = MaterialTheme.colorScheme.primary
                        )
                        
                        AnalyticsCard(
                            title = "Total Messages",
                            value = "${chatSessions.sumOf { it.messageCount }}",
                            icon = Icons.Default.Message,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        AnalyticsCard(
                            title = "Avg. Session",
                            value = "${chatSessions.map { it.messageCount }.average().toInt()} msgs",
                            icon = Icons.Default.Timer,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                        
                        AnalyticsCard(
                            title = "This Week",
                            value = "${chatSessions.count { it.timestamp > System.currentTimeMillis() - 604800000 }}",
                            icon = Icons.Default.CalendarToday,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Chat Sessions List
            Text(
                text = "Recent Conversations (${filteredSessions.size})",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filteredSessions) { session ->
                    ChatSessionCard(
                        session = session,
                        onClick = { onNavigateToChat(session.id) }
                    )
                }
            }
        }
    }
    
    // Export Dialog
    if (showExportDialog) {
        AlertDialog(
            onDismissRequest = { showExportDialog = false },
            title = { Text("Export Chat History") },
            text = { Text("Choose export format:") },
            confirmButton = {
                TextButton(onClick = { showExportDialog = false }) {
                    Text("PDF")
                }
            },
            dismissButton = {
                TextButton(onClick = { showExportDialog = false }) {
                    Text("CSV")
                }
            }
        )
    }
}

@Composable
fun ChatSessionCard(
    session: ChatSession,
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
                    text = session.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                
                Text(
                    text = formatTimestamp(session.timestamp),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = session.lastMessage,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Message,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${session.messageCount} messages",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                
                MoodChip(moodLevel = session.moodLevel)
            }
        }
    }
}

@Composable
fun AnalyticsCard(
    title: String,
    value: String,
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
                text = value,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = color
            )
            
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun MoodChip(moodLevel: String) {
    val color = when (moodLevel.lowercase()) {
        "good" -> Color(0xFF4CAF50)
        "anxious" -> Color(0xFFFF9800)
        "stressed" -> Color(0xFFFF5722)
        "crisis" -> Color(0xFFF44336)
        else -> MaterialTheme.colorScheme.primary
    }
    
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(color.copy(alpha = 0.1f))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = moodLevel,
            style = MaterialTheme.typography.bodySmall,
            color = color,
            fontWeight = FontWeight.Medium
        )
    }
}

fun formatTimestamp(timestamp: Long): String {
    val now = System.currentTimeMillis()
    val diff = now - timestamp
    
    return when {
        diff < 3600000 -> "${diff / 60000}m ago" // Less than 1 hour
        diff < 86400000 -> "${diff / 3600000}h ago" // Less than 1 day
        diff < 604800000 -> "${diff / 86400000}d ago" // Less than 1 week
        else -> SimpleDateFormat("MMM dd", Locale.getDefault()).format(Date(timestamp))
    }
}

data class ChatSession(
    val id: String,
    val title: String,
    val lastMessage: String,
    val timestamp: Long,
    val messageCount: Int,
    val moodLevel: String
)
