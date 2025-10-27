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

// Data classes for accessibility settings
data class AccessibilitySettings(
    val fontSize: FontSize,
    val highContrast: Boolean,
    val screenReader: Boolean,
    val voiceOver: Boolean,
    val reducedMotion: Boolean,
    val colorBlindSupport: ColorBlindSupport,
    val keyboardNavigation: Boolean,
    val hapticFeedback: Boolean,
    val audioDescriptions: Boolean,
    val subtitles: Boolean
)

enum class FontSize(val multiplier: Float, val description: String) {
    SMALL(0.8f, "Small"),
    NORMAL(1.0f, "Normal"),
    LARGE(1.2f, "Large"),
    EXTRA_LARGE(1.4f, "Extra Large")
}

enum class ColorBlindSupport(val description: String) {
    NONE("None"),
    PROTANOPIA("Protanopia (Red-blind)"),
    DEUTERANOPIA("Deuteranopia (Green-blind)"),
    TRITANOPIA("Tritanopia (Blue-blind)")
}

// Wrapper function for backward compatibility
@Composable
fun AccessibilityScreen(onNavigateBack: () -> Unit) {
    AccessibilityScreenDetailed(onNavigateBack = onNavigateBack)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccessibilityScreenDetailed(onNavigateBack: () -> Unit) {
    var fontSize by remember { mutableStateOf(FontSize.NORMAL) }
    var highContrast by remember { mutableStateOf(false) }
    var screenReader by remember { mutableStateOf(false) }
    var voiceOver by remember { mutableStateOf(false) }
    var reducedMotion by remember { mutableStateOf(false) }
    var colorBlindSupport by remember { mutableStateOf(ColorBlindSupport.NONE) }
    var keyboardNavigation by remember { mutableStateOf(false) }
    var hapticFeedback by remember { mutableStateOf(true) }
    var audioDescriptions by remember { mutableStateOf(false) }
    var subtitles by remember { mutableStateOf(false) }
    
    var showPreview by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Accessibility Features") },
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
                    text = "Make the app more accessible for everyone",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            // Visual Accessibility
            item {
                AccessibilitySection(
                    title = "Visual Accessibility",
                    icon = Icons.Default.Visibility
                ) {
                    // Font Size
                    AccessibilitySetting(
                        title = "Font Size",
                        description = "Adjust text size for better readability"
                    ) {
                        FontSizeSelector(
                            selectedSize = fontSize,
                            onSizeSelected = { fontSize = it }
                        )
                    }
                    
                    // High Contrast
                    AccessibilityToggle(
                        title = "High Contrast Mode",
                        description = "Increase contrast for better visibility",
                        checked = highContrast,
                        onCheckedChange = { highContrast = it }
                    )
                    
                    // Color Blind Support
                    AccessibilitySetting(
                        title = "Color Blind Support",
                        description = "Adjust colors for color vision differences"
                    ) {
                        ColorBlindSelector(
                            selectedSupport = colorBlindSupport,
                            onSupportSelected = { colorBlindSupport = it }
                        )
                    }
                }
            }
            
            // Motor Accessibility
            item {
                AccessibilitySection(
                    title = "Motor Accessibility",
                    icon = Icons.Default.TouchApp
                ) {
                    // Haptic Feedback
                    AccessibilityToggle(
                        title = "Haptic Feedback",
                        description = "Vibrate on touch for tactile feedback",
                        checked = hapticFeedback,
                        onCheckedChange = { hapticFeedback = it }
                    )
                    
                    // Reduced Motion
                    AccessibilityToggle(
                        title = "Reduce Motion",
                        description = "Minimize animations and transitions",
                        checked = reducedMotion,
                        onCheckedChange = { reducedMotion = it }
                    )
                    
                    // Keyboard Navigation
                    AccessibilityToggle(
                        title = "Keyboard Navigation",
                        description = "Enable keyboard shortcuts and navigation",
                        checked = keyboardNavigation,
                        onCheckedChange = { keyboardNavigation = it }
                    )
                }
            }
            
            // Audio Accessibility
            item {
                AccessibilitySection(
                    title = "Audio Accessibility",
                    icon = Icons.Default.VolumeUp
                ) {
                    // Screen Reader
                    AccessibilityToggle(
                        title = "Screen Reader Support",
                        description = "Optimize for screen reading software",
                        checked = screenReader,
                        onCheckedChange = { screenReader = it }
                    )
                    
                    // Voice Over
                    AccessibilityToggle(
                        title = "Voice Over",
                        description = "Enable voice descriptions for UI elements",
                        checked = voiceOver,
                        onCheckedChange = { voiceOver = it }
                    )
                    
                    // Audio Descriptions
                    AccessibilityToggle(
                        title = "Audio Descriptions",
                        description = "Provide audio descriptions for visual content",
                        checked = audioDescriptions,
                        onCheckedChange = { audioDescriptions = it }
                    )
                    
                    // Subtitles
                    AccessibilityToggle(
                        title = "Subtitles",
                        description = "Show text captions for audio content",
                        checked = subtitles,
                        onCheckedChange = { subtitles = it }
                    )
                }
            }
            
            // Preview Section
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Preview Changes",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        
                        Text(
                            text = "See how your accessibility settings affect the app",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        
                        Button(
                            onClick = { showPreview = true },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.Preview, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Preview Settings")
                        }
                    }
                }
            }
            
            // Accessibility Tips
            item {
                AccessibilityTipsCard()
            }
        }
    }
    
    if (showPreview) {
        AccessibilityPreviewDialog(
            fontSize = fontSize,
            highContrast = highContrast,
            colorBlindSupport = colorBlindSupport,
            onDismiss = { showPreview = false }
        )
    }
}

@Composable
fun AccessibilitySection(
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
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            content()
        }
    }
}

@Composable
fun AccessibilitySetting(
    title: String,
    description: String,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = description,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        content()
    }
}

@Composable
fun AccessibilityToggle(
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
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = description,
                fontSize = 14.sp,
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
fun FontSizeSelector(
    selectedSize: FontSize,
    onSizeSelected: (FontSize) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        FontSize.values().forEach { size ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.selectable(
                    selected = selectedSize == size,
                    onClick = { onSizeSelected(size) }
                )
            ) {
                Text(
                    text = "Aa",
                    fontSize = (16 * size.multiplier).sp,
                    fontWeight = FontWeight.Bold,
                    color = if (selectedSize == size) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = size.description,
                    fontSize = 12.sp,
                    color = if (selectedSize == size) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun ColorBlindSelector(
    selectedSupport: ColorBlindSupport,
    onSupportSelected: (ColorBlindSupport) -> Unit
) {
    Column {
        ColorBlindSupport.values().forEach { support ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = selectedSupport == support,
                        onClick = { onSupportSelected(support) }
                    )
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedSupport == support,
                    onClick = { onSupportSelected(support) }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = support.description,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun AccessibilityPreviewDialog(
    fontSize: FontSize,
    highContrast: Boolean,
    colorBlindSupport: ColorBlindSupport,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Accessibility Preview") },
        text = {
            Column {
                Text(
                    text = "This is how text will appear with your settings:",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = if (highContrast) 
                            MaterialTheme.colorScheme.surfaceVariant 
                        else MaterialTheme.colorScheme.surface
                    )
                ) {
                    Text(
                        text = "Sample text with ${fontSize.description} font size",
                        fontSize = (16 * fontSize.multiplier).sp,
                        modifier = Modifier.padding(16.dp),
                        color = if (highContrast) 
                            MaterialTheme.colorScheme.onSurfaceVariant 
                        else MaterialTheme.colorScheme.onSurface
                    )
                }
                
                if (colorBlindSupport != ColorBlindSupport.NONE) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Color blind support: ${colorBlindSupport.description}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("OK")
            }
        }
    )
}

@Composable
fun AccessibilityTipsCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                Icon(
                    Icons.Default.Lightbulb,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onTertiaryContainer,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Accessibility Tips",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
            }
            
            val tips = listOf(
                "Use high contrast mode in bright environments",
                "Enable screen reader for hands-free navigation",
                "Increase font size if you have vision difficulties",
                "Use keyboard navigation for faster interaction",
                "Enable haptic feedback for better touch confirmation"
            )
            
            tips.forEach { tip ->
                Row(
                    modifier = Modifier.padding(vertical = 2.dp)
                ) {
                    Text(
                        text = "• ",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                    Text(
                        text = tip,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }
            }
        }
    }
}
