package com.serenityai.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.serenityai.usecases.ApplicationFeedbackUseCase
import com.serenityai.usecases.UserSupportUseCase
import com.serenityai.usecases.FeedbackType
import com.serenityai.usecases.FeedbackStatus
import com.serenityai.usecases.FeedbackSubmission
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationFeedbackScreen(onNavigateBack: () -> Unit) {
    val useCase = remember { ApplicationFeedbackUseCase() }
    val userId = "user123" // In production, get from auth
    
    var selectedTab by remember { mutableStateOf(0) }
    var feedbackType by remember { mutableStateOf<FeedbackType?>(null) }
    var message by remember { mutableStateOf("") }
    var rating by remember { mutableStateOf<Int?>(null) }
    var category by remember { mutableStateOf("") }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var userFeedback by remember { mutableStateOf<List<FeedbackSubmission>>(emptyList()) }
    
    // Load user feedback
    LaunchedEffect(Unit) {
        userFeedback = useCase.getUserFeedback(userId)
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Application Feedback") },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Tabs
            TabRow(selectedTabIndex = selectedTab) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text("Submit Feedback") }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("My Feedback") }
                )
            }
            
            when (selectedTab) {
                0 -> SubmitFeedbackTab(
                    feedbackType = feedbackType,
                    onFeedbackTypeChange = { feedbackType = it },
                    message = message,
                    onMessageChange = { message = it },
                    rating = rating,
                    onRatingChange = { rating = it },
                    category = category,
                    onCategoryChange = { category = it },
                    onSubmit = {
                        try {
                            useCase.submitFeedback(
                                userId = userId,
                                feedbackType = feedbackType ?: FeedbackType.GENERAL,
                                message = message,
                                rating = rating,
                                category = category.ifBlank { null }
                            )
                            showSuccessDialog = true
                            message = ""
                            rating = null
                            category = ""
                            feedbackType = null
                            userFeedback = useCase.getUserFeedback(userId)
                        } catch (e: Exception) {
                            // Handle error
                        }
                    }
                )
                1 -> MyFeedbackTab(feedback = userFeedback)
            }
        }
    }
    
    // Success Dialog
    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { showSuccessDialog = false },
            title = { Text("Feedback Submitted") },
            text = { Text("Thank you for your feedback! We'll review it and get back to you if needed.") },
            confirmButton = {
                TextButton(onClick = { showSuccessDialog = false }) {
                    Text("OK")
                }
            }
        )
    }
}

@Composable
fun SubmitFeedbackTab(
    feedbackType: FeedbackType?,
    onFeedbackTypeChange: (FeedbackType?) -> Unit,
    message: String,
    onMessageChange: (String) -> Unit,
    rating: Int?,
    onRatingChange: (Int?) -> Unit,
    category: String,
    onCategoryChange: (String) -> Unit,
    onSubmit: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Help us improve!",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
        
        item {
            Text(
                text = "Feedback Type",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FeedbackType.values().forEach { type ->
                    FilterChip(
                        selected = feedbackType == type,
                        onClick = { onFeedbackTypeChange(type) },
                        label = { Text(type.name.replace("_", " ")) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
        
        item {
            OutlinedTextField(
                value = message,
                onValueChange = onMessageChange,
                label = { Text("Your Feedback") },
                placeholder = { Text("Tell us what you think...") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 4,
                maxLines = 8
            )
        }
        
        item {
            OutlinedTextField(
                value = category,
                onValueChange = onCategoryChange,
                label = { Text("Category (Optional)") },
                placeholder = { Text("e.g., UI, Performance, Feature") },
                modifier = Modifier.fillMaxWidth()
            )
        }
        
        item {
            Text(
                text = "Rating (Optional)",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                (1..5).forEach { value ->
                    FilterChip(
                        selected = rating == value,
                        onClick = { onRatingChange(if (rating == value) null else value) },
                        label = { Text("$value") },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
        
        item {
            Button(
                onClick = onSubmit,
                modifier = Modifier.fillMaxWidth(),
                enabled = message.isNotBlank() && feedbackType != null
            ) {
                Text("Submit Feedback")
            }
        }
    }
}

@Composable
fun MyFeedbackTab(feedback: List<FeedbackSubmission>) {
    if (feedback.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                Icons.Default.Feedback,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "No feedback submitted yet",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(feedback) { item ->
                FeedbackItemCard(feedback = item)
            }
        }
    }
}

@Composable
fun FeedbackItemCard(feedback: FeedbackSubmission) {
    Card(
        modifier = Modifier.fillMaxWidth(),
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
                    text = feedback.feedbackType.name.replace("_", " "),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = feedback.status.name.replace("_", " "),
                    fontSize = 12.sp,
                    color = when (feedback.status) {
                        FeedbackStatus.RECEIVED -> MaterialTheme.colorScheme.primary
                        FeedbackStatus.REVIEWED -> MaterialTheme.colorScheme.secondary
                        FeedbackStatus.IMPLEMENTED -> MaterialTheme.colorScheme.tertiary
                        FeedbackStatus.DECLINED -> MaterialTheme.colorScheme.error
                    }
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = feedback.message,
                fontSize = 14.sp
            )
            
            if (feedback.rating != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Rating: ", fontSize = 12.sp)
                    repeat(feedback.rating) {
                        Icon(
                            Icons.Default.Star,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
            
            if (feedback.category != null) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Category: ${feedback.category}",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
                    .format(feedback.submittedAt),
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

