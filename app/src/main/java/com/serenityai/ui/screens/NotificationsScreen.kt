package com.serenityai.ui.screens

import androidx.compose.foundation.background
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
import java.text.SimpleDateFormat
import java.util.*

// Data classes for notifications
data class NotificationItem(
    val id: String,
    val title: String,
    val message: String,
    val timestamp: Long,
    val type: NotificationType,
    val isRead: Boolean = false,
    val priority: NotificationPriority = NotificationPriority.NORMAL,
    val actionRequired: Boolean = false
)

enum class NotificationType(
    val displayName: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val color: Color
) {
    MOOD_REMINDER("Mood Reminder", Icons.Default.SentimentSatisfiedAlt, Color(0xFF4CAF50)),
    SESSION_REMINDER("Session Reminder", Icons.Default.Schedule, Color(0xFF2196F3)),
    CRISIS_ALERT("Crisis Alert", Icons.Default.Emergency, Color(0xFFF44336)),
    ACHIEVEMENT("Achievement", Icons.Default.EmojiEvents, Color(0xFFFF9800)),
    SYSTEM_UPDATE("System Update", Icons.Default.SystemUpdate, Color(0xFF9C27B0)),
    THERAPIST_MESSAGE("Therapist Message", Icons.Default.Message, Color(0xFF00BCD4)),
    BACKUP_COMPLETE("Backup Complete", Icons.Default.Backup, Color(0xFF8BC34A)),
    SECURITY_ALERT("Security Alert", Icons.Default.Security, Color(0xFFE91E63))
}

enum class NotificationPriority(val displayName: String) {
    LOW("Low"),
    NORMAL("Normal"),
    HIGH("High"),
    URGENT("Urgent")
}

// NotificationSettings is already defined in AppPreferencesScreen.kt
// Using the existing definition to avoid conflicts

// Wrapper function for backward compatibility
@Composable
fun NotificationsScreen(onNavigateBack: () -> Unit) {
    NotificationsScreenDetailed(onNavigateBack = onNavigateBack)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreenDetailed(onNavigateBack: () -> Unit) {
    var currentTab by remember { mutableStateOf("All") }
    var showSettings by remember { mutableStateOf(false) }
    var notifications by remember { mutableStateOf(generateSampleNotifications()) }
    
    val tabs = listOf("All", "Unread", "Important", "Settings")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notifications") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { 
                        notifications = notifications.map { it.copy(isRead = true) }
                    }) {
                        Icon(Icons.Default.DoneAll, contentDescription = "Mark all as read")
                    }
                    IconButton(onClick = { showSettings = true }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
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
            // Tab selector
            TabRow(
                selectedTabIndex = tabs.indexOf(currentTab),
                modifier = Modifier.fillMaxWidth()
            ) {
                tabs.forEachIndexed { index, tab ->
                    Tab(
                        selected = currentTab == tab,
                        onClick = { currentTab = tab },
                        text = { Text(tab) }
                    )
                }
            }
            
            // Content based on selected tab
            when (currentTab) {
                "All" -> NotificationList(
                    notifications = notifications,
                    onNotificationClick = { notification ->
                        notifications = notifications.map { 
                            if (it.id == notification.id) it.copy(isRead = true) else it 
                        }
                    },
                    onNotificationDelete = { notification ->
                        notifications = notifications.filter { it.id != notification.id }
                    }
                )
                "Unread" -> NotificationList(
                    notifications = notifications.filter { !it.isRead },
                    onNotificationClick = { notification ->
                        notifications = notifications.map { 
                            if (it.id == notification.id) it.copy(isRead = true) else it 
                        }
                    },
                    onNotificationDelete = { notification ->
                        notifications = notifications.filter { it.id != notification.id }
                    }
                )
                "Important" -> NotificationList(
                    notifications = notifications.filter { 
                        it.priority == NotificationPriority.HIGH || 
                        it.priority == NotificationPriority.URGENT ||
                        it.actionRequired 
                    },
                    onNotificationClick = { notification ->
                        notifications = notifications.map { 
                            if (it.id == notification.id) it.copy(isRead = true) else it 
                        }
                    },
                    onNotificationDelete = { notification ->
                        notifications = notifications.filter { it.id != notification.id }
                    }
                )
                "Settings" -> NotificationSettingsContent()
            }
        }
    }
    
    if (showSettings) {
        NotificationSettingsDialog(
            onDismiss = { showSettings = false }
        )
    }
}

@Composable
fun NotificationList(
    notifications: List<NotificationItem>,
    onNotificationClick: (NotificationItem) -> Unit,
    onNotificationDelete: (NotificationItem) -> Unit
) {
    if (notifications.isEmpty()) {
        EmptyNotificationsState()
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(notifications) { notification ->
                NotificationCard(
                    notification = notification,
                    onClick = { onNotificationClick(notification) },
                    onDelete = { onNotificationDelete(notification) }
                )
            }
        }
    }
}

@Composable
fun NotificationCard(
    notification: NotificationItem,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (notification.isRead) 
                MaterialTheme.colorScheme.surface 
            else notification.type.color.copy(alpha = 0.1f)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (notification.isRead) 1.dp else 4.dp
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Notification icon
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        notification.type.color.copy(alpha = 0.2f),
                        shape = androidx.compose.foundation.shape.CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    notification.type.icon,
                    contentDescription = null,
                    tint = notification.type.color,
                    modifier = Modifier.size(20.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            // Notification content
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = notification.title,
                        fontSize = 16.sp,
                        fontWeight = if (notification.isRead) FontWeight.Normal else FontWeight.Bold
                    )
                    
                    Text(
                        text = formatTime(notification.timestamp),
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Text(
                    text = notification.message,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                
                // Priority and action indicators
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (notification.priority == NotificationPriority.HIGH || 
                        notification.priority == NotificationPriority.URGENT) {
                        Icon(
                            Icons.Default.PriorityHigh,
                            contentDescription = "High priority",
                            tint = Color(0xFFF44336),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                    
                    if (notification.actionRequired) {
                        Text(
                            text = "Action Required",
                            fontSize = 12.sp,
                            color = Color(0xFFF44336),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
            
            // Delete button
            IconButton(onClick = onDelete) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Delete notification",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun EmptyNotificationsState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            Icons.Default.NotificationsOff,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "No Notifications",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Text(
            text = "You're all caught up! New notifications will appear here.",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun NotificationSettingsContent() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Notification Settings",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
        
        item {
            NotificationTypeSettings()
        }
        
        item {
            QuietHoursSettings()
        }
        
        item {
            NotificationBehaviorSettings()
        }
    }
}

@Composable
fun NotificationTypeSettings() {
    var moodReminders by remember { mutableStateOf(true) }
    var sessionReminders by remember { mutableStateOf(true) }
    var crisisAlerts by remember { mutableStateOf(true) }
    var achievements by remember { mutableStateOf(true) }
    var systemUpdates by remember { mutableStateOf(false) }
    var therapistMessages by remember { mutableStateOf(true) }
    var backupNotifications by remember { mutableStateOf(false) }
    var securityAlerts by remember { mutableStateOf(true) }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Notification Types",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            NotificationTypeToggle(
                title = "Mood Reminders",
                description = "Daily reminders to log your mood",
                checked = moodReminders,
                onCheckedChange = { moodReminders = it },
                type = NotificationType.MOOD_REMINDER
            )
            
            NotificationTypeToggle(
                title = "Session Reminders",
                description = "Reminders for therapy sessions",
                checked = sessionReminders,
                onCheckedChange = { sessionReminders = it },
                type = NotificationType.SESSION_REMINDER
            )
            
            NotificationTypeToggle(
                title = "Crisis Alerts",
                description = "Emergency notifications for crisis situations",
                checked = crisisAlerts,
                onCheckedChange = { crisisAlerts = it },
                type = NotificationType.CRISIS_ALERT
            )
            
            NotificationTypeToggle(
                title = "Achievements",
                description = "Celebrate your progress and milestones",
                checked = achievements,
                onCheckedChange = { achievements = it },
                type = NotificationType.ACHIEVEMENT
            )
            
            NotificationTypeToggle(
                title = "System Updates",
                description = "App updates and maintenance notifications",
                checked = systemUpdates,
                onCheckedChange = { systemUpdates = it },
                type = NotificationType.SYSTEM_UPDATE
            )
            
            NotificationTypeToggle(
                title = "Therapist Messages",
                description = "Messages from your therapist",
                checked = therapistMessages,
                onCheckedChange = { therapistMessages = it },
                type = NotificationType.THERAPIST_MESSAGE
            )
            
            NotificationTypeToggle(
                title = "Backup Notifications",
                description = "Backup completion and status updates",
                checked = backupNotifications,
                onCheckedChange = { backupNotifications = it },
                type = NotificationType.BACKUP_COMPLETE
            )
            
            NotificationTypeToggle(
                title = "Security Alerts",
                description = "Important security notifications",
                checked = securityAlerts,
                onCheckedChange = { securityAlerts = it },
                type = NotificationType.SECURITY_ALERT
            )
        }
    }
}

@Composable
fun NotificationTypeToggle(
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    type: NotificationType
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            type.icon,
            contentDescription = null,
            tint = type.color,
            modifier = Modifier.size(20.dp)
        )
        
        Spacer(modifier = Modifier.width(12.dp))
        
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
fun QuietHoursSettings() {
    var quietHoursEnabled by remember { mutableStateOf(false) }
    var startTime by remember { mutableStateOf("22:00") }
    var endTime by remember { mutableStateOf("08:00") }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Quiet Hours",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Enable Quiet Hours",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "Pause notifications during specified hours",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Switch(
                    checked = quietHoursEnabled,
                    onCheckedChange = { quietHoursEnabled = it }
                )
            }
            
            if (quietHoursEnabled) {
                Spacer(modifier = Modifier.height(16.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Start Time",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        OutlinedTextField(
                            value = startTime,
                            onValueChange = { startTime = it },
                            modifier = Modifier.width(100.dp)
                        )
                    }
                    
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "End Time",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        OutlinedTextField(
                            value = endTime,
                            onValueChange = { endTime = it },
                            modifier = Modifier.width(100.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NotificationBehaviorSettings() {
    var vibrationEnabled by remember { mutableStateOf(true) }
    var soundEnabled by remember { mutableStateOf(true) }
    var ledEnabled by remember { mutableStateOf(false) }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Notification Behavior",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Vibration",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "Vibrate when notifications arrive",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Switch(
                    checked = vibrationEnabled,
                    onCheckedChange = { vibrationEnabled = it }
                )
            }
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Sound",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "Play sound for notifications",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Switch(
                    checked = soundEnabled,
                    onCheckedChange = { soundEnabled = it }
                )
            }
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "LED Light",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "Flash LED for notifications",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Switch(
                    checked = ledEnabled,
                    onCheckedChange = { ledEnabled = it }
                )
            }
        }
    }
}

@Composable
fun NotificationSettingsDialog(
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Quick Settings") },
        text = {
            Column {
                Text(
                    text = "Quick notification settings",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                
                Button(
                    onClick = { /* Enable all notifications */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Enable All Notifications")
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                OutlinedButton(
                    onClick = { /* Disable all notifications */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Disable All Notifications")
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                OutlinedButton(
                    onClick = { /* Clear all notifications */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Clear All Notifications")
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}

// formatTime function is already defined in AIChatSessionScreen.kt
// Using the existing definition to avoid conflicts

fun generateSampleNotifications(): List<NotificationItem> {
    return listOf(
        NotificationItem(
            id = "1",
            title = "Daily Mood Check-in",
            message = "How are you feeling today? Take a moment to log your mood.",
            timestamp = System.currentTimeMillis() - 300000, // 5 minutes ago
            type = NotificationType.MOOD_REMINDER,
            isRead = false,
            priority = NotificationPriority.NORMAL
        ),
        NotificationItem(
            id = "2",
            title = "Therapy Session Tomorrow",
            message = "You have a therapy session scheduled for tomorrow at 2:00 PM.",
            timestamp = System.currentTimeMillis() - 3600000, // 1 hour ago
            type = NotificationType.SESSION_REMINDER,
            isRead = true,
            priority = NotificationPriority.HIGH
        ),
        NotificationItem(
            id = "3",
            title = "Achievement Unlocked!",
            message = "Congratulations! You've logged your mood for 7 consecutive days.",
            timestamp = System.currentTimeMillis() - 7200000, // 2 hours ago
            type = NotificationType.ACHIEVEMENT,
            isRead = true,
            priority = NotificationPriority.NORMAL
        ),
        NotificationItem(
            id = "4",
            title = "Security Alert",
            message = "New login detected from an unknown device. Please verify this was you.",
            timestamp = System.currentTimeMillis() - 86400000, // 1 day ago
            type = NotificationType.SECURITY_ALERT,
            isRead = false,
            priority = NotificationPriority.URGENT,
            actionRequired = true
        ),
        NotificationItem(
            id = "5",
            title = "Message from Dr. Smith",
            message = "I've reviewed your progress this week. Great job on the breathing exercises!",
            timestamp = System.currentTimeMillis() - 172800000, // 2 days ago
            type = NotificationType.THERAPIST_MESSAGE,
            isRead = true,
            priority = NotificationPriority.HIGH
        )
    )
}
