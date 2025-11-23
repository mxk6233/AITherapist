package com.serenityai.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.ExperimentalMaterial3Api

// Chat History Screen (UC6)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatHistoryScreen(onNavigateBack: () -> Unit) {
    val chatSessions = listOf(
        "Yesterday - Evening Session",
        "Monday - Morning Check-in",
        "Sunday - Crisis Support",
        "Saturday - Weekly Review"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chat History") },
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
                .padding(16.dp)
        ) {
            Text(
                text = "Your Past Conversations",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(chatSessions) { session ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Text(
                            text = session,
                            modifier = Modifier.padding(12.dp),
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}

// Crisis Intervention Screen (UC36)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrisisInterventionScreen(onNavigateBack: () -> Unit) {
    val useCase = remember { com.serenityai.usecases.AdaptiveCrisisDeescalationUseCase() }
    val userId = "user123" // In production, get from auth
    
    var userInput by remember { mutableStateOf("") }
    var currentSession by remember { mutableStateOf<com.serenityai.usecases.DeescalationSession?>(null) }
    var currentStepIndex by remember { mutableStateOf(0) }
    var stepResponse by remember { mutableStateOf("") }
    var showSafetyMeasures by remember { mutableStateOf(false) }
    var safetyMeasures by remember { mutableStateOf<com.serenityai.usecases.SafetyMeasures?>(null) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Crisis Intervention") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
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
            if (currentSession == null) {
                // Initial Crisis Assessment
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp)
                        ) {
                            Icon(
                                Icons.Default.Emergency,
                                contentDescription = null,
                                modifier = Modifier.size(48.dp),
                                tint = MaterialTheme.colorScheme.error
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Crisis Support",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onErrorContainer
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "If you're in immediate danger, please call 911 or your local emergency services.",
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onErrorContainer.copy(alpha = 0.9f)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "National Suicide Prevention Lifeline: 988",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
                
                item {
                    Text(
                        text = "Tell us what's happening",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                
                item {
                    OutlinedTextField(
                        value = userInput,
                        onValueChange = { userInput = it },
                        label = { Text("Describe your situation") },
                        placeholder = { Text("I'm feeling...") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 4,
                        maxLines = 8
                    )
                }
                
                item {
                    Button(
                        onClick = {
                            if (userInput.isNotBlank()) {
                                val session = useCase.createSession(userId, userInput)
                                currentSession = session
                                currentStepIndex = 0
                                
                                // Get safety measures
                                val assessment = session.initialAssessment
                                safetyMeasures = useCase.provideImmediateSafetyMeasures(assessment)
                                showSafetyMeasures = assessment.level.ordinal >= com.serenityai.usecases.CrisisLevel.HIGH.ordinal
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = userInput.isNotBlank(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Text("Get Help", color = MaterialTheme.colorScheme.onError)
                    }
                }
            } else {
                // Active Session - Show Steps
                val session = currentSession!!
                val script = session.script
                
                // Safety Measures Banner
                if (showSafetyMeasures && safetyMeasures != null) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.errorContainer
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = "Immediate Safety Actions",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onErrorContainer
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                safetyMeasures!!.immediateActions.forEach { action ->
                                    Text(
                                        text = "• $action",
                                        fontSize = 14.sp,
                                        color = MaterialTheme.colorScheme.onErrorContainer,
                                        modifier = Modifier.padding(vertical = 4.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Resources:",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.onErrorContainer
                                )
                                safetyMeasures!!.resources.forEach { resource ->
                                    Text(
                                        text = "• $resource",
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onErrorContainer,
                                        modifier = Modifier.padding(vertical = 2.dp)
                                    )
                                }
                            }
                        }
                    }
                }
                
                // Crisis Level Indicator
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = when (script.crisisLevel) {
                                com.serenityai.usecases.CrisisLevel.CRITICAL -> MaterialTheme.colorScheme.errorContainer
                                com.serenityai.usecases.CrisisLevel.HIGH -> MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.7f)
                                com.serenityai.usecases.CrisisLevel.MEDIUM -> MaterialTheme.colorScheme.tertiaryContainer
                                com.serenityai.usecases.CrisisLevel.LOW -> MaterialTheme.colorScheme.primaryContainer
                                com.serenityai.usecases.CrisisLevel.NONE -> MaterialTheme.colorScheme.surfaceVariant
                            }
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Info,
                                contentDescription = null,
                                tint = when (script.crisisLevel) {
                                    com.serenityai.usecases.CrisisLevel.CRITICAL -> MaterialTheme.colorScheme.error
                                    com.serenityai.usecases.CrisisLevel.HIGH -> MaterialTheme.colorScheme.error
                                    else -> MaterialTheme.colorScheme.primary
                                }
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "Crisis Level: ${script.crisisLevel.name}",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Estimated Duration: ${script.estimatedDuration} minutes",
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
                
                // Progress Indicator
                if (script.steps.isNotEmpty()) {
                    item {
                        val progress = ((currentStepIndex + 1).toFloat() / script.steps.size) * 100f
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Progress",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = "${currentStepIndex + 1} / ${script.steps.size}",
                                    fontSize = 14.sp
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            LinearProgressIndicator(
                                progress = progress / 100f,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
                
                // Current Step
                if (currentStepIndex < script.steps.size) {
                    val currentStep = script.steps[currentStepIndex]
                    
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(20.dp)
                            ) {
                                Text(
                                    text = "Step ${currentStep.order}",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = currentStep.prompt,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                
                                OutlinedTextField(
                                    value = stepResponse,
                                    onValueChange = { stepResponse = it },
                                    label = { Text("Your response") },
                                    placeholder = { Text("Type your response...") },
                                    modifier = Modifier.fillMaxWidth(),
                                    minLines = 3,
                                    maxLines = 6
                                )
                                
                                Spacer(modifier = Modifier.height(16.dp))
                                
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    if (currentStepIndex > 0) {
                                        OutlinedButton(
                                            onClick = {
                                                currentStepIndex--
                                                stepResponse = ""
                                            },
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Text("Previous")
                                        }
                                    }
                                    
                                    Button(
                                        onClick = {
                                            try {
                                                val result = useCase.executeDeescalationStep(
                                                    script.id,
                                                    currentStepIndex,
                                                    stepResponse
                                                )
                                                
                                                if (result.isCompleted) {
                                                    // Session completed
                                                    val finalAssessment = result.newAssessment 
                                                        ?: session.initialAssessment
                                                    val completedSession = useCase.completeSession(
                                                        session.id,
                                                        finalAssessment
                                                    )
                                                    currentSession = completedSession
                                                } else {
                                                    // Adapt script if needed (before clearing response)
                                                    val responseToAdapt = stepResponse
                                                    if (responseToAdapt.isNotBlank()) {
                                                        val adaptedScript = useCase.adaptScript(
                                                            script.id,
                                                            responseToAdapt
                                                        )
                                                        if (adaptedScript != null) {
                                                            currentSession = session.copy(script = adaptedScript)
                                                        }
                                                    }
                                                    
                                                    // Move to next step
                                                    currentStepIndex++
                                                    stepResponse = ""
                                                }
                                            } catch (e: Exception) {
                                                // Handle error
                                            }
                                        },
                                        modifier = Modifier.weight(1f),
                                        enabled = stepResponse.isNotBlank() || currentStep.type == com.serenityai.usecases.StepType.GROUNDING
                                    ) {
                                        Text(if (currentStepIndex >= script.steps.size - 1) "Complete" else "Next")
                                    }
                                }
                            }
                        }
                    }
                } else {
                    // Session Completed
                    item {
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
                                Icon(
                                    Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    modifier = Modifier.size(64.dp),
                                    tint = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = "Session Completed",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "You've completed the deescalation steps. Remember, help is always available.",
                                    fontSize = 14.sp,
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(24.dp))
                                Button(
                                    onClick = {
                                        currentSession = null
                                        currentStepIndex = 0
                                        stepResponse = ""
                                        userInput = ""
                                        showSafetyMeasures = false
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text("Start New Session")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// Support Tools Screens
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupportToolsScreen(
    onNavigateToCopingExercises: () -> Unit,
    onNavigateToJournalPrompts: () -> Unit,
    onNavigateToEducationalResources: () -> Unit,
    onNavigateToUserSupport: () -> Unit,
    onNavigateToGroupTherapy: () -> Unit,
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Support Tools") },
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
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Debug: Verify screen is rendering
            Text(
                text = "Support Tools",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            // Coping Exercises
            Card(
                onClick = onNavigateToCopingExercises,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    modifier = Modifier.padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.FitnessCenter,
                        contentDescription = null,
                        modifier = Modifier.size(40.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "Coping Exercises",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            
            // Journal Prompts
            Card(
                onClick = onNavigateToJournalPrompts,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    modifier = Modifier.padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = null,
                        modifier = Modifier.size(40.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "Journal Prompts",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            
            // Educational Resources
            Card(
                onClick = onNavigateToEducationalResources,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    modifier = Modifier.padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Book,
                        contentDescription = null,
                        modifier = Modifier.size(40.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "Educational Resources",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            
            // User Support
            Card(
                onClick = onNavigateToUserSupport,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    modifier = Modifier.padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Support,
                        contentDescription = null,
                        modifier = Modifier.size(40.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "User Support",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            
            // Group Therapy Simulation Mode (UC34)
            Card(
                onClick = onNavigateToGroupTherapy,
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    modifier = Modifier.padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.People,
                        contentDescription = null,
                        modifier = Modifier.size(40.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "Group Therapy Simulation",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CopingExercisesScreen(onNavigateBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Coping Exercises") },
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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Coping Exercises Content (UC8)", fontSize = 20.sp)
            // Add coping exercises UI here
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalPromptsScreen(onNavigateBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Journal Prompts") },
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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Journal Prompts Content (UC32)", fontSize = 20.sp)
            // Add journal prompts UI here
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EducationalResourcesScreen(onNavigateBack: () -> Unit) {
    val context = LocalContext.current
    val useCase = remember { com.serenityai.usecases.EducationalResourcesUseCase() }
    var resources by remember { mutableStateOf<List<com.serenityai.usecases.EducationalResource>>(emptyList()) }
    var categories by remember { mutableStateOf<List<String>>(emptyList()) }
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var searchQuery by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(true) }
    var selectedFormat by remember { mutableStateOf<com.serenityai.usecases.ContentFormat>(com.serenityai.usecases.ContentFormat.ALL) }
    var dataSource by remember { mutableStateOf<String>("Loading...") } // Track data source
    
    // Function to open URL in browser
    fun openResourceUrl(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        } catch (e: Exception) {
            // If URL is invalid or no browser available, show error
            android.widget.Toast.makeText(
                context,
                "Unable to open resource: ${e.message}",
                android.widget.Toast.LENGTH_SHORT
            ).show()
        }
    }
    
    // Load resources and categories when screen loads
    LaunchedEffect(Unit) {
        isLoading = true
        try {
            val loadedResources = useCase.getEducationalResources(
                category = selectedCategory,
                format = selectedFormat
            )
            resources = loadedResources
            categories = useCase.getResourceCategories()
            
            // Check if data came from Firebase (check Logcat for details)
            // For now, assume Firebase if we have more than 5 resources (Firebase might have more)
            dataSource = if (loadedResources.size > 5) "Firebase" else "Fallback Data"
        } catch (e: Exception) {
            android.util.Log.e("EducationalResources", "Error loading resources: ${e.message}", e)
            dataSource = "Error - Using Fallback"
            // Error handling - try to get fallback data
            try {
                resources = useCase.getEducationalResources(
                    category = null,
                    format = com.serenityai.usecases.ContentFormat.ALL
                )
                categories = useCase.getResourceCategories()
            } catch (e2: Exception) {
                // If even fallback fails, show empty state
                resources = emptyList()
                categories = emptyList()
            }
        }
        isLoading = false
    }
    
    // Reload when filters change
    LaunchedEffect(selectedCategory, selectedFormat) {
        isLoading = true
        try {
            resources = useCase.getEducationalResources(
                category = selectedCategory,
                format = selectedFormat
            )
        } catch (e: Exception) {
            // Error handling
        }
        isLoading = false
    }
    
    // Search functionality
    LaunchedEffect(searchQuery) {
        if (searchQuery.isNotBlank()) {
            isLoading = true
            try {
                resources = useCase.searchEducationalResources(searchQuery)
            } catch (e: Exception) {
                // Error handling
            }
            isLoading = false
        } else if (searchQuery.isEmpty()) {
            // Reload all resources when search is cleared
            isLoading = true
            try {
                resources = useCase.getEducationalResources(
                    category = selectedCategory,
                    format = selectedFormat
                )
            } catch (e: Exception) {
                // Error handling
            }
            isLoading = false
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Educational Resources") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                actions = {
                    // Show data source indicator (for debugging)
                    Text(
                        text = dataSource,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Search resources...") },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                },
                trailingIcon = {
                    if (searchQuery.isNotBlank()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(Icons.Default.Clear, contentDescription = "Clear")
                        }
                    }
                },
                singleLine = true
            )
            
            // Format Filter
            LazyRow(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    FilterChip(
                        selected = selectedFormat == com.serenityai.usecases.ContentFormat.ALL,
                        onClick = { selectedFormat = com.serenityai.usecases.ContentFormat.ALL },
                        label = { Text("All") }
                    )
                }
                item {
                    FilterChip(
                        selected = selectedFormat == com.serenityai.usecases.ContentFormat.TEXT,
                        onClick = { selectedFormat = com.serenityai.usecases.ContentFormat.TEXT },
                        label = { Text("Text") }
                    )
                }
                item {
                    FilterChip(
                        selected = selectedFormat == com.serenityai.usecases.ContentFormat.VIDEO,
                        onClick = { selectedFormat = com.serenityai.usecases.ContentFormat.VIDEO },
                        label = { Text("Video") }
                    )
                }
                item {
                    FilterChip(
                        selected = selectedFormat == com.serenityai.usecases.ContentFormat.AUDIO,
                        onClick = { selectedFormat = com.serenityai.usecases.ContentFormat.AUDIO },
                        label = { Text("Audio") }
                    )
                }
            }
            
            // Category Filter
            if (categories.isNotEmpty()) {
                LazyRow(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        FilterChip(
                            selected = selectedCategory == null,
                            onClick = { selectedCategory = null },
                            label = { Text("All Categories") }
                        )
                    }
                    items(categories) { category ->
                        FilterChip(
                            selected = selectedCategory == category,
                            onClick = { 
                                selectedCategory = if (selectedCategory == category) null else category
                            },
                            label = { Text(category) }
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Resources List
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (resources.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            Icons.Default.Book,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            "No resources found",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        if (searchQuery.isNotBlank()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                "Try a different search term",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        Text(
                            "Found ${resources.size} resource${if (resources.size != 1) "s" else ""}",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                    items(resources) { resource ->
                        EducationalResourceCard(
                            resource = resource,
                            onClick = { openResourceUrl(resource.url) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EducationalResourceCard(
    resource: com.serenityai.usecases.EducationalResource,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = resource.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = resource.category,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                // Format icon
                Icon(
                    when (resource.format) {
                        com.serenityai.usecases.ContentFormat.TEXT -> Icons.Default.Article
                        com.serenityai.usecases.ContentFormat.VIDEO -> Icons.Default.PlayCircle
                        com.serenityai.usecases.ContentFormat.AUDIO -> Icons.Default.Headphones
                        else -> Icons.Default.Book
                    },
                    contentDescription = resource.format.name,
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = resource.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 3
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
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${resource.duration} min",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    // Difficulty badge
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(
                                when (resource.difficulty) {
                                    com.serenityai.usecases.DifficultyLevel.BEGINNER -> Color(0xFF4CAF50).copy(alpha = 0.1f)
                                    com.serenityai.usecases.DifficultyLevel.INTERMEDIATE -> Color(0xFFFF9800).copy(alpha = 0.1f)
                                    com.serenityai.usecases.DifficultyLevel.ADVANCED -> Color(0xFFF44336).copy(alpha = 0.1f)
                                }
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = resource.difficulty.name,
                            style = MaterialTheme.typography.bodySmall,
                            color = when (resource.difficulty) {
                                com.serenityai.usecases.DifficultyLevel.BEGINNER -> Color(0xFF4CAF50)
                                com.serenityai.usecases.DifficultyLevel.INTERMEDIATE -> Color(0xFFFF9800)
                                com.serenityai.usecases.DifficultyLevel.ADVANCED -> Color(0xFFF44336)
                            },
                            fontSize = 10.sp
                        )
                    }
                }
                
                // Relevance score
                if (resource.relevanceScore > 0) {
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
                            text = "${resource.relevanceScore.toInt()}%",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFFFFD700),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
            
            // Tags
            if (resource.tags.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(resource.tags.take(5)) { tag ->
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(MaterialTheme.colorScheme.secondaryContainer)
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = tag,
                                style = MaterialTheme.typography.bodySmall,
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    }
                }
            }
            
            // Open button indicator (card is already clickable)
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        when (resource.format) {
                            com.serenityai.usecases.ContentFormat.VIDEO -> Icons.Default.PlayArrow
                            com.serenityai.usecases.ContentFormat.AUDIO -> Icons.Default.PlayArrow
                            else -> Icons.Default.OpenInNew
                        },
                        contentDescription = "Open",
                        modifier = Modifier.size(18.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = when (resource.format) {
                            com.serenityai.usecases.ContentFormat.VIDEO -> "Watch"
                            com.serenityai.usecases.ContentFormat.AUDIO -> "Listen"
                            else -> "Read"
                        },
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserSupportScreen(
    onNavigateToApplicationFeedback: () -> Unit,
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("User Support") },
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
                    text = "Support & Feedback",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            item {
                Card(
                    onClick = onNavigateToApplicationFeedback,
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Feedback,
                            contentDescription = null,
                            modifier = Modifier.size(48.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Submit Feedback",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Share your thoughts, report bugs, or request features",
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
            
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = "Need Help?",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "If you need immediate assistance or have questions, please reach out through the feedback system or contact our support team.",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                        )
                    }
                }
            }
        }
    }
}

// Group Therapy Screen (UC34)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupTherapyScreen(onNavigateBack: () -> Unit) {
    val useCase = remember { com.serenityai.usecases.GroupTherapySimulationModeUseCase() }
    var sessionName by remember { mutableStateOf("") }
    var topic by remember { mutableStateOf("") }
    var maxParticipants by remember { mutableStateOf(8) }
    var currentSession by remember { mutableStateOf<com.serenityai.usecases.GroupSession?>(null) }
    var virtualParticipants by remember { mutableStateOf<List<com.serenityai.usecases.VirtualParticipant>>(emptyList()) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Group Therapy Simulation") },
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
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            if (currentSession == null) {
                // Create Session Section
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = "Create Group Session",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        
                        OutlinedTextField(
                            value = sessionName,
                            onValueChange = { sessionName = it },
                            label = { Text("Session Name") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        OutlinedTextField(
                            value = topic,
                            onValueChange = { topic = it },
                            label = { Text("Topic (Optional)") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        OutlinedTextField(
                            value = maxParticipants.toString(),
                            onValueChange = { 
                                maxParticipants = it.toIntOrNull() ?: 8
                            },
                            label = { Text("Max Participants") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Button(
                            onClick = {
                                if (sessionName.isNotBlank()) {
                                    val session = useCase.createGroupSession(
                                        sessionName = sessionName,
                                        facilitatorId = "user123", // In production, get from auth
                                        maxParticipants = maxParticipants,
                                        topic = topic.ifBlank { null }
                                    )
                                    currentSession = session
                                    virtualParticipants = useCase.createVirtualParticipants(session.id, 5)
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = sessionName.isNotBlank()
                        ) {
                            Text("Create Session")
                        }
                    }
                }
            } else {
                // Active Session View
                val session = currentSession!!
                
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = session.name,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        
                        if (session.topic != null) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Topic: ${session.topic}",
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Participants: ${session.participants.size}/${session.maxParticipants}",
                            fontSize = 14.sp
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        if (virtualParticipants.isNotEmpty()) {
                            Text(
                                text = "Virtual Participants:",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            virtualParticipants.forEach { participant ->
                                Text(
                                    text = "• ${participant.name} (${participant.personality})",
                                    fontSize = 14.sp,
                                    modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
                                )
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Button(
                            onClick = {
                                currentSession = null
                                virtualParticipants = emptyList()
                                sessionName = ""
                                topic = ""
                                maxParticipants = 8
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("End Session")
                        }
                    }
                }
            }
        }
    }
}

// Voice Therapy Screen (UC38)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VoiceTherapyScreen(onNavigateBack: () -> Unit) {
    val useCase = remember { com.serenityai.usecases.VoiceEnabledTherapyUseCase() }
    var currentSession by remember { mutableStateOf<com.serenityai.usecases.VoiceSession?>(null) }
    var isRecording by remember { mutableStateOf(false) }
    var language by remember { mutableStateOf("en-US") }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Voice Therapy") },
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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (currentSession == null) {
                // Start Session
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            Icons.Default.Mic,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Voice Therapy Session",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Have a voice conversation with your AI therapist",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Button(
                            onClick = {
                                val session = useCase.startVoiceSession(
                                    userId = "user123", // In production, get from auth
                                    language = language
                                )
                                currentSession = session
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Start Voice Session")
                        }
                    }
                }
            } else {
                // Active Session
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Voice Session Active",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        Icon(
                            if (isRecording) Icons.Default.Mic else Icons.Default.MicOff,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = if (isRecording) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = if (isRecording) "Listening..." else "Tap to speak",
                            fontSize = 16.sp
                        )
                        
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Button(
                                onClick = { isRecording = !isRecording }
                            ) {
                                Text(if (isRecording) "Stop Recording" else "Start Recording")
                            }
                            Button(
                                onClick = {
                                    currentSession = null
                                    isRecording = false
                                }
                            ) {
                                Text("End Session")
                            }
                        }
                    }
                }
            }
        }
    }
}

// Wellness Activities Screens
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WellnessActivitiesScreen(
    onNavigateToGuidedBreathing: () -> Unit,
    onNavigateToDailyAffirmations: () -> Unit,
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Wellness Activities") },
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
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Guided Breathing
            Card(
                onClick = onNavigateToGuidedBreathing,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    modifier = Modifier.padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Air,
                        contentDescription = null,
                        modifier = Modifier.size(40.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "Guided Breathing",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            
            // Daily Affirmations
            Card(
                onClick = onNavigateToDailyAffirmations,
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    modifier = Modifier.padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Favorite,
                        contentDescription = null,
                        modifier = Modifier.size(40.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "Daily Affirmations",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

// Settings Screens
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateToSecurityProtocols: () -> Unit,
    onNavigateToPersonalization: () -> Unit,
    onNavigateToAccessibility: () -> Unit,
    onNavigateToNotifications: () -> Unit,
    onNavigateToAppPreferences: () -> Unit,
    onNavigateToSystemHealth: () -> Unit,
    onNavigateToApplicationFeedback: () -> Unit,
    onNavigateBack: () -> Unit
) {
    val settingsItems = listOf(
        Triple("Security Protocols", Icons.Default.Lock, onNavigateToSecurityProtocols),
        Triple("Personalization", Icons.Default.Person, onNavigateToPersonalization),
        Triple("Accessibility", Icons.Default.Accessibility, onNavigateToAccessibility),
        Triple("Notifications", Icons.Default.Notifications, onNavigateToNotifications),
        Triple("App Preferences", Icons.Default.Settings, onNavigateToAppPreferences),
        Triple("System Health", Icons.Default.HealthAndSafety, onNavigateToSystemHealth),
        Triple("Application Feedback", Icons.Default.Feedback, onNavigateToApplicationFeedback)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings & Personalization") },
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
                .padding(16.dp)
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(settingsItems) { item ->
                    val (title, icon, onClick) = item
                    Card(
                        onClick = onClick,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(20.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                icon,
                                contentDescription = null,
                                modifier = Modifier.size(40.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = title,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecurityProtocolsScreen(onNavigateBack: () -> Unit) {
    var biometricEnabled by remember { mutableStateOf(false) }
    var twoFactorEnabled by remember { mutableStateOf(true) }
    var dataEncryptionEnabled by remember { mutableStateOf(true) }
    var sessionTimeout by remember { mutableStateOf("30 minutes") }
    var showPasswordDialog by remember { mutableStateOf(false) }
    
    val timeoutOptions = listOf("15 minutes", "30 minutes", "1 hour", "2 hours", "Never")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Security Protocols") },
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
                SecurityOverviewCard()
            }
            
            item {
                Text(
                    text = "Authentication Settings",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            
            item {
                SecuritySettingCard(
                    title = "Biometric Authentication",
                    description = "Use fingerprint or face recognition to unlock the app",
                    icon = Icons.Default.Fingerprint,
                    isEnabled = biometricEnabled,
                    onToggle = { biometricEnabled = it }
                )
            }
            
            item {
                SecuritySettingCard(
                    title = "Two-Factor Authentication",
                    description = "Require additional verification for sensitive actions",
                    icon = Icons.Default.Security,
                    isEnabled = twoFactorEnabled,
                    onToggle = { twoFactorEnabled = it }
                )
            }
            
            item {
                SecuritySettingCard(
                    title = "Data Encryption",
                    description = "Encrypt all stored data for maximum security",
                    icon = Icons.Default.Lock,
                    isEnabled = dataEncryptionEnabled,
                    onToggle = { dataEncryptionEnabled = it }
                )
            }
            
            item {
                Text(
                    text = "Session Management",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            
            item {
                SessionTimeoutCard(
                    currentTimeout = sessionTimeout,
                    options = timeoutOptions,
                    onTimeoutSelected = { sessionTimeout = it }
                )
            }
            
            item {
                Text(
                    text = "Privacy Controls",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            
            item {
                PrivacyControlsCard()
            }
            
            item {
                Button(
                    onClick = { showPasswordDialog = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Change Password")
                }
            }
        }
    }
    
    if (showPasswordDialog) {
        ChangePasswordDialog(
            onDismiss = { showPasswordDialog = false },
            onPasswordChanged = { showPasswordDialog = false }
        )
    }
}

@Composable
fun SecurityOverviewCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                Icons.Default.Security,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Security Status: Active",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Your data is protected with enterprise-grade security",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun SecuritySettingCard(
    title: String,
    description: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isEnabled: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Switch(
                checked = isEnabled,
                onCheckedChange = onToggle
            )
        }
    }
}

@Composable
fun SessionTimeoutCard(
    currentTimeout: String,
    options: List<String>,
    onTimeoutSelected: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Session Timeout",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Automatically log out after inactivity",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(12.dp))
            
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(options) { option ->
                    FilterChip(
                        selected = currentTimeout == option,
                        onClick = { onTimeoutSelected(option) },
                        label = { Text(option) }
                    )
                }
            }
        }
    }
}

@Composable
fun PrivacyControlsCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Data Privacy",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            
            val privacyFeatures = listOf(
                "End-to-end encryption for all conversations",
                "Local data storage with optional cloud sync",
                "No data sharing with third parties",
                "GDPR compliant data handling",
                "Automatic data deletion after 2 years"
            )
            
            privacyFeatures.forEach { feature ->
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
                        text = feature,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePasswordDialog(
    onDismiss: () -> Unit,
    onPasswordChanged: () -> Unit
) {
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var showPasswords by remember { mutableStateOf(false) }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Change Password") },
        text = {
            Column {
                OutlinedTextField(
                    value = currentPassword,
                    onValueChange = { currentPassword = it },
                    label = { Text("Current Password") },
                    visualTransformation = if (showPasswords) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { showPasswords = !showPasswords }) {
                            Icon(
                                if (showPasswords) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = if (showPasswords) "Hide password" else "Show password"
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    label = { Text("New Password") },
                    visualTransformation = if (showPasswords) VisualTransformation.None else PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirm New Password") },
                    visualTransformation = if (showPasswords) VisualTransformation.None else PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (newPassword == confirmPassword && newPassword.isNotEmpty()) {
                        onPasswordChanged()
                    }
                },
                enabled = newPassword == confirmPassword && newPassword.isNotEmpty()
            ) {
                Text("Change Password")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalizationScreen(onNavigateBack: () -> Unit) {
    var selectedTheme by remember { mutableStateOf("System") }
    var selectedLanguage by remember { mutableStateOf("English") }
    var fontSize by remember { mutableStateOf("Medium") }
    var aiPersonality by remember { mutableStateOf("Supportive") }
    var notificationStyle by remember { mutableStateOf("Gentle") }
    var showThemeDialog by remember { mutableStateOf(false) }
    
    val themes = listOf("Light", "Dark", "System")
    val languages = listOf("English", "Spanish", "French", "German", "Chinese")
    val fontSizes = listOf("Small", "Medium", "Large", "Extra Large")
    val aiPersonalities = listOf("Supportive", "Professional", "Friendly", "Direct", "Empathetic")
    val notificationStyles = listOf("Gentle", "Standard", "Minimal", "Detailed")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Personalization") },
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
                PersonalizationOverviewCard()
            }
            
            item {
                Text(
                    text = "Appearance",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            
            item {
                PersonalizationSettingCard(
                    title = "Theme",
                    description = "Choose your preferred app theme",
                    icon = Icons.Default.Palette,
                    currentValue = selectedTheme,
                    onValueClick = { showThemeDialog = true }
                )
            }
            
            item {
                PersonalizationSettingCard(
                    title = "Font Size",
                    description = "Adjust text size for better readability",
                    icon = Icons.Default.TextFields,
                    currentValue = fontSize,
                    onValueClick = { /* Show font size picker */ }
                )
            }
            
            item {
                Text(
                    text = "AI Interaction",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            
            item {
                PersonalizationSettingCard(
                    title = "AI Personality",
                    description = "Customize how the AI therapist communicates",
                    icon = Icons.Default.Psychology,
                    currentValue = aiPersonality,
                    onValueClick = { /* Show personality picker */ }
                )
            }
            
            item {
                Text(
                    text = "Content Preferences",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            
            item {
                ContentPreferencesCard()
            }
            
            item {
                Text(
                    text = "Language & Region",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            
            item {
                PersonalizationSettingCard(
                    title = "Language",
                    description = "Select your preferred language",
                    icon = Icons.Default.Language,
                    currentValue = selectedLanguage,
                    onValueClick = { /* Show language picker */ }
                )
            }
            
            item {
                Text(
                    text = "Notifications",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            
            item {
                PersonalizationSettingCard(
                    title = "Notification Style",
                    description = "Choose how notifications appear",
                    icon = Icons.Default.Notifications,
                    currentValue = notificationStyle,
                    onValueClick = { /* Show notification style picker */ }
                )
            }
            
            item {
                Button(
                    onClick = { /* Save preferences */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save Preferences")
                }
            }
        }
    }
    
    if (showThemeDialog) {
        ThemeSelectionDialog(
            currentTheme = selectedTheme,
            themes = themes,
            onThemeSelected = { 
                selectedTheme = it
                showThemeDialog = false
            },
            onDismiss = { showThemeDialog = false }
        )
    }
}

@Composable
fun PersonalizationOverviewCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Personalized Experience",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Customize your AI therapist experience to match your preferences",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun PersonalizationSettingCard(
    title: String,
    description: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    currentValue: String,
    onValueClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onValueClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                text = currentValue,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                Icons.Default.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun ContentPreferencesCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Content Customization",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            
            val preferences = listOf(
                "Focus on anxiety management",
                "Include mindfulness exercises",
                "Show progress tracking",
                "Enable mood forecasting",
                "Include crisis support resources"
            )
            
            preferences.forEach { preference ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = true, // This would be stateful in real implementation
                        onCheckedChange = { /* Handle preference toggle */ }
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = preference,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeSelectionDialog(
    currentTheme: String,
    themes: List<String>,
    onThemeSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select Theme") },
        text = {
            Column {
                themes.forEach { theme ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onThemeSelected(theme) }
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = currentTheme == theme,
                            onClick = { onThemeSelected(theme) }
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = theme,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Done")
            }
        }
    )
}