package com.serenityai.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import kotlinx.coroutines.delay
import androidx.compose.material3.ExperimentalMaterial3Api

@OptIn(ExperimentalMaterial3Api::class)
data class ChatMessage(
    val id: String,
    val text: String,
    val isFromUser: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AIChatSessionScreen(
    onNavigateBack: () -> Unit
) {
    var messageText by remember { mutableStateOf("") }
    var messages by remember { mutableStateOf<List<ChatMessage>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var pendingResponse by remember { mutableStateOf<String?>(null) }
    val listState = rememberLazyListState()
    
    // Handle AI response with LaunchedEffect
    LaunchedEffect(pendingResponse) {
        pendingResponse?.let { userMessage ->
            delay(1500)
            val aiResponse = ChatMessage(
                id = (System.currentTimeMillis() + 1).toString(),
                text = generateAIResponse(userMessage),
                isFromUser = false
            )
            messages = messages + aiResponse
            isLoading = false
            pendingResponse = null
        }
    }
    
    // Initial welcome message
    LaunchedEffect(Unit) {
        if (messages.isEmpty()) {
            messages = listOf(
                ChatMessage(
                    id = "welcome",
                    text = "Hello! I'm your AI therapist. I'm here to listen and help you work through your thoughts and feelings. What's on your mind today?",
                    isFromUser = false
                )
            )
        }
    }
    
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Header
        TopAppBar(
            title = { Text("AI Chat Session") },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            actions = {
                IconButton(onClick = { /* Voice input */ }) {
                    Icon(Icons.Default.Mic, contentDescription = "Voice Input")
                }
                IconButton(onClick = { /* Settings */ }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "More")
                }
            }
        )
        
        // Messages List
        LazyColumn(
            state = listState,
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(messages) { message ->
                ChatMessageBubble(message = message)
            }
            
            if (isLoading) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    strokeWidth = 2.dp
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("AI is typing...")
                            }
                        }
                    }
                }
            }
        }
        
        // Input Section
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                OutlinedTextField(
                    value = messageText,
                    onValueChange = { messageText = it },
                    placeholder = { Text("Type your message...") },
                    modifier = Modifier.weight(1f),
                    maxLines = 3,
                    enabled = !isLoading
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                FloatingActionButton(
                    onClick = {
                        if (messageText.isNotBlank() && !isLoading) {
                            val userMessage = ChatMessage(
                                id = System.currentTimeMillis().toString(),
                                text = messageText,
                                isFromUser = true
                            )
                            messages = messages + userMessage
                            messageText = ""
                            isLoading = true
                            pendingResponse = userMessage.text
                        }
                    },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        Icons.Default.Send,
                        contentDescription = "Send",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ChatMessageBubble(message: ChatMessage) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.isFromUser) Arrangement.End else Arrangement.Start
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = if (message.isFromUser) 
                    MaterialTheme.colorScheme.primary 
                else 
                    MaterialTheme.colorScheme.surfaceVariant
            ),
            modifier = Modifier.widthIn(max = 280.dp)
        ) {
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    text = message.text,
                    color = if (message.isFromUser) 
                        MaterialTheme.colorScheme.onPrimary 
                    else 
                        MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = formatTime(message.timestamp),
                    fontSize = 10.sp,
                    color = if (message.isFromUser) 
                        MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                    else 
                        MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                )
            }
        }
    }
}

fun generateAIResponse(userMessage: String): String {
    val responses = listOf(
        "I understand how you're feeling. Can you tell me more about what's been on your mind?",
        "That sounds challenging. It's important to acknowledge these feelings. What do you think might help you feel better?",
        "Thank you for sharing that with me. How long have you been experiencing this?",
        "I hear you. It takes courage to talk about these things. What would you like to focus on today?",
        "That's a valid concern. Let's explore this together. What's your biggest worry about this situation?",
        "I appreciate you being open with me. How does this make you feel physically?",
        "It sounds like you're going through a lot right now. What support do you have in your life?",
        "I can sense this is important to you. What would you like to achieve from our conversation today?"
    )
    return responses.random()
}

fun formatTime(timestamp: Long): String {
    val time = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault())
    return time.format(java.util.Date(timestamp))
}
