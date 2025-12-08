package com.serenityai.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serenityai.usecases.*
import java.util.Date

/** UC40: Religious Support by Person's Religion - UI Screen */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReligiousSupportScreen(onNavigateBack: () -> Unit) {
    val useCase = remember { ReligiousSupportUseCase() }
    val userId = "user123" // In production, get from auth
    
    var selectedReligion by remember { mutableStateOf<Religion?>(null) }
    var showReligionSelector by remember { mutableStateOf(false) }
    var currentGuidance by remember { mutableStateOf<SpiritualGuidance?>(null) }
    var prayerRequestText by remember { mutableStateOf("") }
    var showPrayerRequestDialog by remember { mutableStateOf(false) }
    var dailyReflection by remember { mutableStateOf<String?>(null) }
    var currentQuote by remember { mutableStateOf<String?>(null) }
    var resources by remember { mutableStateOf<List<ReligiousResource>>(emptyList()) }
    var prayerRequests by remember { mutableStateOf<List<PrayerRequest>>(emptyList()) }
    
    // Load user's religious preference
    LaunchedEffect(Unit) {
        selectedReligion = useCase.getReligiousPreference(userId)
        if (selectedReligion != null) {
            resources = useCase.getReligiousResources(userId)
            prayerRequests = useCase.getPrayerRequests(userId)
            try {
                dailyReflection = useCase.getDailyReflection(userId)
                currentQuote = useCase.getReligiousQuote(userId)
            } catch (e: Exception) {
                // User hasn't set preference yet
            }
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Religious Support") },
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
            // Religion Selection
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Your Religious Preference",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        if (selectedReligion == null) {
                            Text(
                                text = "Please select your religion to receive personalized spiritual support",
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(bottom = 12.dp)
                            )
                            Button(
                                onClick = { showReligionSelector = true },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Select Religion")
                            }
                        } else {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = selectedReligion!!.name,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                TextButton(onClick = { showReligionSelector = true }) {
                                    Text("Change")
                                }
                            }
                        }
                    }
                }
            }
            
            // Daily Reflection
            if (selectedReligion != null && dailyReflection != null) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(bottom = 8.dp)
                            ) {
                                Icon(
                                    Icons.Default.Lightbulb,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Daily Reflection",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Text(
                                text = dailyReflection!!,
                                fontSize = 14.sp,
                                lineHeight = 20.sp
                            )
                        }
                    }
                }
            }
            
            // Religious Quote
            if (selectedReligion != null && currentQuote != null) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(bottom = 8.dp)
                            ) {
                                Icon(
                                    Icons.Default.FormatQuote,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Inspirational Quote",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Text(
                                text = currentQuote!!,
                                fontSize = 14.sp,
                                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                                lineHeight = 20.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            TextButton(
                                onClick = {
                                    currentQuote = useCase.getReligiousQuote(userId)
                                }
                            ) {
                                Text("Get Another Quote")
                            }
                        }
                    }
                }
            }
            
            // Spiritual Guidance
            if (selectedReligion != null) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Spiritual Guidance",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 12.dp)
                            )
                            var guidanceContext by remember { mutableStateOf("") }
                            OutlinedTextField(
                                value = guidanceContext,
                                onValueChange = { guidanceContext = it },
                                label = { Text("What do you need guidance on?") },
                                placeholder = { Text("e.g., anxiety, stress, relationships") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(
                                onClick = {
                                    if (guidanceContext.isNotBlank()) {
                                        try {
                                            currentGuidance = useCase.provideSpiritualGuidance(userId, guidanceContext)
                                            guidanceContext = ""
                                        } catch (e: Exception) {
                                            // Handle error
                                        }
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                enabled = guidanceContext.isNotBlank()
                            ) {
                                Text("Get Guidance")
                            }
                            if (currentGuidance != null) {
                                Spacer(modifier = Modifier.height(12.dp))
                                Card(
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                                    )
                                ) {
                                    Text(
                                        text = currentGuidance!!.guidance,
                                        modifier = Modifier.padding(12.dp),
                                        fontSize = 14.sp,
                                        lineHeight = 20.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
            
            // Religious Resources
            if (selectedReligion != null && resources.isNotEmpty()) {
                item {
                    Text(
                        text = "Religious Resources",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                items(resources) { resource ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Book,
                                contentDescription = null,
                                modifier = Modifier.size(32.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = resource.title,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = resource.description,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
            
            // Prayer Requests
            if (selectedReligion != null) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Prayer Requests",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        FloatingActionButton(
                            onClick = { showPrayerRequestDialog = true },
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Add Prayer Request")
                        }
                    }
                }
                items(prayerRequests) { request ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = request.request,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                            Text(
                                text = "Status: ${request.status.name}",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
    
    // Religion Selection Dialog
    if (showReligionSelector) {
        AlertDialog(
            onDismissRequest = { showReligionSelector = false },
            title = { Text("Select Your Religion") },
            text = {
                Column {
                    Religion.values().filter { it != Religion.NONE }.forEach { religion ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .clickable {
                                    selectedReligion = religion
                                    useCase.setReligiousPreference(userId, religion)
                                    resources = useCase.getReligiousResources(userId)
                                    try {
                                        dailyReflection = useCase.getDailyReflection(userId)
                                        currentQuote = useCase.getReligiousQuote(userId)
                                    } catch (e: Exception) {
                                        // Handle error
                                    }
                                    showReligionSelector = false
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedReligion == religion,
                                onClick = {
                                    selectedReligion = religion
                                    useCase.setReligiousPreference(userId, religion)
                                    resources = useCase.getReligiousResources(userId)
                                    try {
                                        dailyReflection = useCase.getDailyReflection(userId)
                                        currentQuote = useCase.getReligiousQuote(userId)
                                    } catch (e: Exception) {
                                        // Handle error
                                    }
                                    showReligionSelector = false
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = religion.name.replace("_", " "),
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showReligionSelector = false }) {
                    Text("Close")
                }
            }
        )
    }
    
    // Prayer Request Dialog
    if (showPrayerRequestDialog) {
        AlertDialog(
            onDismissRequest = { showPrayerRequestDialog = false },
            title = { Text("Submit Prayer Request") },
            text = {
                Column {
                    OutlinedTextField(
                        value = prayerRequestText,
                        onValueChange = { prayerRequestText = it },
                        label = { Text("Your Prayer Request") },
                        placeholder = { Text("Enter your prayer request...") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3,
                        maxLines = 5
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (prayerRequestText.isNotBlank() && selectedReligion != null) {
                            try {
                                useCase.submitPrayerRequest(userId, prayerRequestText, isPrivate = true)
                                prayerRequests = useCase.getPrayerRequests(userId)
                                prayerRequestText = ""
                                showPrayerRequestDialog = false
                            } catch (e: Exception) {
                                // Handle error
                            }
                        }
                    },
                    enabled = prayerRequestText.isNotBlank()
                ) {
                    Text("Submit")
                }
            },
            dismissButton = {
                TextButton(onClick = { showPrayerRequestDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

