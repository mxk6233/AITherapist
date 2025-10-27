package com.serenityai.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Data classes for app preferences
data class AppPreferences(
    val theme: AppTheme,
    val language: AppLanguage,
    val notifications: NotificationSettings,
    val privacy: PrivacySettings,
    val dataUsage: DataUsageSettings,
    val backup: BackupSettings,
    val security: SecuritySettings
)

enum class AppTheme(val displayName: String) {
    SYSTEM("System Default"),
    LIGHT("Light"),
    DARK("Dark"),
    AUTO("Auto (Based on Time)")
}

enum class AppLanguage(val displayName: String, val code: String) {
    ENGLISH("English", "en"),
    SPANISH("Español", "es"),
    FRENCH("Français", "fr"),
    GERMAN("Deutsch", "de"),
    CHINESE("中文", "zh"),
    JAPANESE("日本語", "ja"),
    ARABIC("العربية", "ar"),
    HINDI("हिन्दी", "hi")
}

data class NotificationSettings(
    val pushNotifications: Boolean,
    val emailNotifications: Boolean,
    val reminderNotifications: Boolean,
    val crisisAlerts: Boolean,
    val moodReminders: Boolean,
    val sessionReminders: Boolean,
    val quietHours: Boolean,
    val quietStartTime: String = "22:00",
    val quietEndTime: String = "08:00"
)

data class PrivacySettings(
    val dataCollection: Boolean,
    val analytics: Boolean,
    val crashReports: Boolean,
    val personalizedAds: Boolean,
    val shareDataWithTherapists: Boolean,
    val anonymousMode: Boolean
)

data class DataUsageSettings(
    val autoSync: Boolean,
    val wifiOnlySync: Boolean,
    val imageQuality: ImageQuality,
    val cacheSize: CacheSize,
    val autoDeleteOldData: Boolean,
    val dataRetentionDays: Int = 30
)

enum class ImageQuality(val displayName: String) {
    LOW("Low (Faster)"),
    MEDIUM("Medium"),
    HIGH("High (Slower)")
}

enum class CacheSize(val displayName: String, val maxSizeMB: Int) {
    SMALL("Small (100 MB)", 100),
    MEDIUM("Medium (500 MB)", 500),
    LARGE("Large (1 GB)", 1000),
    UNLIMITED("Unlimited", -1)
}

data class BackupSettings(
    val autoBackup: Boolean,
    val backupFrequency: BackupFrequency,
    val cloudBackup: Boolean,
    val localBackup: Boolean,
    val encryptBackups: Boolean
)

enum class BackupFrequency(val displayName: String) {
    DAILY("Daily"),
    WEEKLY("Weekly"),
    MONTHLY("Monthly"),
    MANUAL("Manual Only")
}

data class SecuritySettings(
    val biometricAuth: Boolean,
    val pinProtection: Boolean,
    val sessionTimeout: SessionTimeout,
    val secureStorage: Boolean,
    val twoFactorAuth: Boolean
)

enum class SessionTimeout(val displayName: String, val minutes: Int) {
    IMMEDIATE("Immediate", 0),
    FIVE_MINUTES("5 Minutes", 5),
    FIFTEEN_MINUTES("15 Minutes", 15),
    THIRTY_MINUTES("30 Minutes", 30),
    ONE_HOUR("1 Hour", 60),
    NEVER("Never", -1)
}

// Wrapper function for backward compatibility
@Composable
fun AppPreferencesScreen(onNavigateBack: () -> Unit) {
    AppPreferencesScreenDetailed(onNavigateBack = onNavigateBack)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppPreferencesScreenDetailed(onNavigateBack: () -> Unit) {
    var currentSection by remember { mutableStateOf("General") }
    var showResetDialog by remember { mutableStateOf(false) }
    
    val sections = listOf(
        "General",
        "Notifications", 
        "Privacy",
        "Data Usage",
        "Backup",
        "Security"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("App Preferences") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { showResetDialog = true }) {
                        Icon(Icons.Default.Restore, contentDescription = "Reset to defaults")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { paddingValues ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Side navigation
            LazyColumn(
                modifier = Modifier
                    .width(200.dp)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(sections) { section ->
                    PreferenceSectionItem(
                        title = section,
                        isSelected = currentSection == section,
                        onClick = { currentSection = section }
                    )
                }
            }
            
            // Content area
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                when (currentSection) {
                    "General" -> item { GeneralPreferences() }
                    "Notifications" -> item { NotificationPreferences() }
                    "Privacy" -> item { PrivacyPreferences() }
                    "Data Usage" -> item { DataUsagePreferences() }
                    "Backup" -> item { BackupPreferences() }
                    "Security" -> item { SecurityPreferences() }
                }
            }
        }
    }
    
    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            title = { Text("Reset Preferences") },
            text = { Text("Are you sure you want to reset all preferences to their default values? This action cannot be undone.") },
            confirmButton = {
                TextButton(
                    onClick = { 
                        showResetDialog = false
                        // Reset preferences logic here
                    }
                ) {
                    Text("Reset")
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun PreferenceSectionItem(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) 
                MaterialTheme.colorScheme.primaryContainer 
            else MaterialTheme.colorScheme.surface
        )
    ) {
        Text(
            text = title,
            fontSize = 14.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            modifier = Modifier.padding(12.dp)
        )
    }
}

@Composable
fun GeneralPreferences() {
    var theme by remember { mutableStateOf(AppTheme.SYSTEM) }
    var language by remember { mutableStateOf(AppLanguage.ENGLISH) }
    
    Column {
        Text(
            text = "General Settings",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        // Theme Selection
        PreferenceGroup(
            title = "Appearance",
            icon = Icons.Default.Palette
        ) {
            ThemeSelector(
                selectedTheme = theme,
                onThemeSelected = { theme = it }
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Language Selection
        PreferenceGroup(
            title = "Language",
            icon = Icons.Default.Language
        ) {
            LanguageSelector(
                selectedLanguage = language,
                onLanguageSelected = { language = it }
            )
        }
    }
}

@Composable
fun NotificationPreferences() {
    var pushNotifications by remember { mutableStateOf(true) }
    var emailNotifications by remember { mutableStateOf(false) }
    var reminderNotifications by remember { mutableStateOf(true) }
    var crisisAlerts by remember { mutableStateOf(true) }
    var moodReminders by remember { mutableStateOf(true) }
    var sessionReminders by remember { mutableStateOf(true) }
    var quietHours by remember { mutableStateOf(false) }
    
    Column {
        Text(
            text = "Notification Settings",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        PreferenceGroup(
            title = "Notification Types",
            icon = Icons.Default.Notifications
        ) {
            PreferenceToggle(
                title = "Push Notifications",
                description = "Receive notifications on your device",
                checked = pushNotifications,
                onCheckedChange = { pushNotifications = it }
            )
            
            PreferenceToggle(
                title = "Email Notifications",
                description = "Receive notifications via email",
                checked = emailNotifications,
                onCheckedChange = { emailNotifications = it }
            )
            
            PreferenceToggle(
                title = "Reminder Notifications",
                description = "Get reminded about scheduled activities",
                checked = reminderNotifications,
                onCheckedChange = { reminderNotifications = it }
            )
            
            PreferenceToggle(
                title = "Crisis Alerts",
                description = "Emergency notifications for crisis situations",
                checked = crisisAlerts,
                onCheckedChange = { crisisAlerts = it }
            )
            
            PreferenceToggle(
                title = "Mood Reminders",
                description = "Daily reminders to log your mood",
                checked = moodReminders,
                onCheckedChange = { moodReminders = it }
            )
            
            PreferenceToggle(
                title = "Session Reminders",
                description = "Reminders for therapy sessions",
                checked = sessionReminders,
                onCheckedChange = { sessionReminders = it }
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        PreferenceGroup(
            title = "Quiet Hours",
            icon = Icons.Default.Bedtime
        ) {
            PreferenceToggle(
                title = "Enable Quiet Hours",
                description = "Pause notifications during specified hours",
                checked = quietHours,
                onCheckedChange = { quietHours = it }
            )
        }
    }
}

@Composable
fun PrivacyPreferences() {
    var dataCollection by remember { mutableStateOf(true) }
    var analytics by remember { mutableStateOf(true) }
    var crashReports by remember { mutableStateOf(true) }
    var personalizedAds by remember { mutableStateOf(false) }
    var shareDataWithTherapists by remember { mutableStateOf(false) }
    var anonymousMode by remember { mutableStateOf(false) }
    
    Column {
        Text(
            text = "Privacy Settings",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        PreferenceGroup(
            title = "Data Collection",
            icon = Icons.Default.Security
        ) {
            PreferenceToggle(
                title = "Allow Data Collection",
                description = "Help improve the app by sharing anonymous usage data",
                checked = dataCollection,
                onCheckedChange = { dataCollection = it }
            )
            
            PreferenceToggle(
                title = "Analytics",
                description = "Share app usage analytics for improvements",
                checked = analytics,
                onCheckedChange = { analytics = it }
            )
            
            PreferenceToggle(
                title = "Crash Reports",
                description = "Automatically send crash reports to help fix bugs",
                checked = crashReports,
                onCheckedChange = { crashReports = it }
            )
            
            PreferenceToggle(
                title = "Personalized Ads",
                description = "Show ads based on your interests",
                checked = personalizedAds,
                onCheckedChange = { personalizedAds = it }
            )
            
            PreferenceToggle(
                title = "Share with Therapists",
                description = "Allow your therapist to view your progress data",
                checked = shareDataWithTherapists,
                onCheckedChange = { shareDataWithTherapists = it }
            )
            
            PreferenceToggle(
                title = "Anonymous Mode",
                description = "Use the app without creating an account",
                checked = anonymousMode,
                onCheckedChange = { anonymousMode = it }
            )
        }
    }
}

@Composable
fun DataUsagePreferences() {
    var autoSync by remember { mutableStateOf(true) }
    var wifiOnlySync by remember { mutableStateOf(false) }
    var imageQuality by remember { mutableStateOf(ImageQuality.MEDIUM) }
    var cacheSize by remember { mutableStateOf(CacheSize.MEDIUM) }
    var autoDeleteOldData by remember { mutableStateOf(true) }
    
    Column {
        Text(
            text = "Data Usage Settings",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        PreferenceGroup(
            title = "Sync Settings",
            icon = Icons.Default.Sync
        ) {
            PreferenceToggle(
                title = "Auto Sync",
                description = "Automatically sync data across devices",
                checked = autoSync,
                onCheckedChange = { autoSync = it }
            )
            
            PreferenceToggle(
                title = "WiFi Only Sync",
                description = "Only sync when connected to WiFi",
                checked = wifiOnlySync,
                onCheckedChange = { wifiOnlySync = it }
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        PreferenceGroup(
            title = "Storage Settings",
            icon = Icons.Default.Storage
        ) {
            PreferenceSetting(
                title = "Image Quality",
                description = "Quality of images stored in the app"
            ) {
                ImageQualitySelector(
                    selectedQuality = imageQuality,
                    onQualitySelected = { imageQuality = it }
                )
            }
            
            PreferenceSetting(
                title = "Cache Size",
                description = "Maximum amount of data to store locally"
            ) {
                CacheSizeSelector(
                    selectedSize = cacheSize,
                    onSizeSelected = { cacheSize = it }
                )
            }
            
            PreferenceToggle(
                title = "Auto Delete Old Data",
                description = "Automatically remove data older than 30 days",
                checked = autoDeleteOldData,
                onCheckedChange = { autoDeleteOldData = it }
            )
        }
    }
}

@Composable
fun BackupPreferences() {
    var autoBackup by remember { mutableStateOf(true) }
    var backupFrequency by remember { mutableStateOf(BackupFrequency.WEEKLY) }
    var cloudBackup by remember { mutableStateOf(true) }
    var localBackup by remember { mutableStateOf(false) }
    var encryptBackups by remember { mutableStateOf(true) }
    
    Column {
        Text(
            text = "Backup Settings",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        PreferenceGroup(
            title = "Backup Options",
            icon = Icons.Default.Backup
        ) {
            PreferenceToggle(
                title = "Auto Backup",
                description = "Automatically backup your data",
                checked = autoBackup,
                onCheckedChange = { autoBackup = it }
            )
            
            PreferenceSetting(
                title = "Backup Frequency",
                description = "How often to create backups"
            ) {
                BackupFrequencySelector(
                    selectedFrequency = backupFrequency,
                    onFrequencySelected = { backupFrequency = it }
                )
            }
            
            PreferenceToggle(
                title = "Cloud Backup",
                description = "Store backups in the cloud",
                checked = cloudBackup,
                onCheckedChange = { cloudBackup = it }
            )
            
            PreferenceToggle(
                title = "Local Backup",
                description = "Create local backups on your device",
                checked = localBackup,
                onCheckedChange = { localBackup = it }
            )
            
            PreferenceToggle(
                title = "Encrypt Backups",
                description = "Encrypt backup files for security",
                checked = encryptBackups,
                onCheckedChange = { encryptBackups = it }
            )
        }
    }
}

@Composable
fun SecurityPreferences() {
    var biometricAuth by remember { mutableStateOf(false) }
    var pinProtection by remember { mutableStateOf(false) }
    var sessionTimeout by remember { mutableStateOf(SessionTimeout.FIFTEEN_MINUTES) }
    var secureStorage by remember { mutableStateOf(true) }
    var twoFactorAuth by remember { mutableStateOf(false) }
    
    Column {
        Text(
            text = "Security Settings",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        PreferenceGroup(
            title = "Authentication",
            icon = Icons.Default.Lock
        ) {
            PreferenceToggle(
                title = "Biometric Authentication",
                description = "Use fingerprint or face recognition",
                checked = biometricAuth,
                onCheckedChange = { biometricAuth = it }
            )
            
            PreferenceToggle(
                title = "PIN Protection",
                description = "Require PIN to access the app",
                checked = pinProtection,
                onCheckedChange = { pinProtection = it }
            )
            
            PreferenceToggle(
                title = "Two-Factor Authentication",
                description = "Add extra security with 2FA",
                checked = twoFactorAuth,
                onCheckedChange = { twoFactorAuth = it }
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        PreferenceGroup(
            title = "Session Management",
            icon = Icons.Default.Timer
        ) {
            PreferenceSetting(
                title = "Session Timeout",
                description = "Automatically lock the app after inactivity"
            ) {
                SessionTimeoutSelector(
                    selectedTimeout = sessionTimeout,
                    onTimeoutSelected = { sessionTimeout = it }
                )
            }
            
            PreferenceToggle(
                title = "Secure Storage",
                description = "Encrypt sensitive data on device",
                checked = secureStorage,
                onCheckedChange = { secureStorage = it }
            )
        }
    }
}

@Composable
fun PreferenceGroup(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            content()
        }
    }
}

@Composable
fun PreferenceToggle(
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = description,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

@Composable
fun PreferenceSetting(
    title: String,
    description: String,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Text(
            text = title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = description,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        content()
    }
}

@Composable
fun ThemeSelector(
    selectedTheme: AppTheme,
    onThemeSelected: (AppTheme) -> Unit
) {
    Column {
        AppTheme.values().forEach { theme ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = selectedTheme == theme,
                        onClick = { onThemeSelected(theme) }
                    )
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedTheme == theme,
                    onClick = { onThemeSelected(theme) }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = theme.displayName,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun LanguageSelector(
    selectedLanguage: AppLanguage,
    onLanguageSelected: (AppLanguage) -> Unit
) {
    Column {
        AppLanguage.values().forEach { language ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = selectedLanguage == language,
                        onClick = { onLanguageSelected(language) }
                    )
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedLanguage == language,
                    onClick = { onLanguageSelected(language) }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = language.displayName,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun ImageQualitySelector(
    selectedQuality: ImageQuality,
    onQualitySelected: (ImageQuality) -> Unit
) {
    Column {
        ImageQuality.values().forEach { quality ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = selectedQuality == quality,
                        onClick = { onQualitySelected(quality) }
                    )
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedQuality == quality,
                    onClick = { onQualitySelected(quality) }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = quality.displayName,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun CacheSizeSelector(
    selectedSize: CacheSize,
    onSizeSelected: (CacheSize) -> Unit
) {
    Column {
        CacheSize.values().forEach { size ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = selectedSize == size,
                        onClick = { onSizeSelected(size) }
                    )
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedSize == size,
                    onClick = { onSizeSelected(size) }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = size.displayName,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun BackupFrequencySelector(
    selectedFrequency: BackupFrequency,
    onFrequencySelected: (BackupFrequency) -> Unit
) {
    Column {
        BackupFrequency.values().forEach { frequency ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = selectedFrequency == frequency,
                        onClick = { onFrequencySelected(frequency) }
                    )
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedFrequency == frequency,
                    onClick = { onFrequencySelected(frequency) }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = frequency.displayName,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun SessionTimeoutSelector(
    selectedTimeout: SessionTimeout,
    onTimeoutSelected: (SessionTimeout) -> Unit
) {
    Column {
        SessionTimeout.values().forEach { timeout ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = selectedTimeout == timeout,
                        onClick = { onTimeoutSelected(timeout) }
                    )
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedTimeout == timeout,
                    onClick = { onTimeoutSelected(timeout) }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = timeout.displayName,
                    fontSize = 14.sp
                )
            }
        }
    }
}
