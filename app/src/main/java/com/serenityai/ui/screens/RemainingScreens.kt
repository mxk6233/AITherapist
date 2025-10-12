package com.serenityai.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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

// Crisis Intervention Screen (UC2)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrisisInterventionScreen(onNavigateBack: () -> Unit) {
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
            Text("Crisis Intervention Content (UC2)", fontSize = 20.sp)
            // Add crisis intervention UI here
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
    onNavigateBack: () -> Unit
) {
    val supportTools = listOf(
        Pair("Coping Exercises", onNavigateToCopingExercises),
        Pair("Journal Prompts", onNavigateToJournalPrompts),
        Pair("Educational Resources", onNavigateToEducationalResources),
        Pair("User Support", onNavigateToUserSupport)
    )

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
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(supportTools) { (title, onClick) ->
                    Card(
                        onClick = onClick,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(20.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Build, // Placeholder icon
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
            Text("Educational Resources Content (UC16)", fontSize = 20.sp)
            // Add educational resources UI here
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserSupportScreen(onNavigateBack: () -> Unit) {
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("User Support Content (UC25)", fontSize = 20.sp)
            // Add user support UI here
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
    val wellnessActivities = listOf(
        Pair("Guided Breathing", onNavigateToGuidedBreathing),
        Pair("Daily Affirmations", onNavigateToDailyAffirmations)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text(
                text = "Wellness Activities",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(wellnessActivities) { (title, onClick) ->
                Card(
                    onClick = onClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Favorite, // Placeholder icon
                            contentDescription = null,
                            modifier = Modifier.size(40.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = title,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
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
    onNavigateBack: () -> Unit
) {
    val settingsItems = listOf(
        Pair("Security Protocols", onNavigateToSecurityProtocols),
        Pair("Personalization", onNavigateToPersonalization),
        Pair("Accessibility", onNavigateToAccessibility),
        Pair("Notifications", onNavigateToNotifications),
        Pair("App Preferences", onNavigateToAppPreferences)
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
                items(settingsItems) { (title, onClick) ->
                    Card(
                        onClick = onClick,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(20.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Tune, // Placeholder icon
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