package com.serenityai.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin

// Data classes for breathing sessions
data class BreathingSession(
    val id: String,
    val name: String,
    val description: String,
    val duration: Int, // in minutes
    val inhaleTime: Int, // in seconds
    val holdTime: Int, // in seconds
    val exhaleTime: Int, // in seconds
    val color: Color,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

enum class BreathingPhase {
    INHALE, HOLD, EXHALE, REST
}

// Wrapper function for backward compatibility
@Composable
fun GuidedBreathingScreen(onNavigateBack: () -> Unit) {
    GuidedBreathingScreenDetailed(onNavigateBack = onNavigateBack)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuidedBreathingScreenDetailed(onNavigateBack: () -> Unit) {
    var selectedSession by remember { mutableStateOf<BreathingSession?>(null) }
    var isSessionActive by remember { mutableStateOf(false) }
    var currentPhase by remember { mutableStateOf(BreathingPhase.INHALE) }
    var timeRemaining by remember { mutableStateOf(0) }
    var sessionDuration by remember { mutableStateOf(0) }
    
    val breathingSessions = remember { generateBreathingSessions() }
    
    // Breathing animation
    val infiniteTransition = rememberInfiniteTransition(label = "breathing")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )
    
    // Phase timing
    LaunchedEffect(isSessionActive, selectedSession) {
        if (isSessionActive && selectedSession != null) {
            val session = selectedSession!!
            sessionDuration = session.duration * 60 // Convert to seconds
            
            while (sessionDuration > 0) {
                // Inhale phase
                currentPhase = BreathingPhase.INHALE
                timeRemaining = session.inhaleTime
                while (timeRemaining > 0) {
                    delay(1000)
                    timeRemaining--
                    sessionDuration--
                }
                
                if (sessionDuration <= 0) break
                
                // Hold phase
                currentPhase = BreathingPhase.HOLD
                timeRemaining = session.holdTime
                while (timeRemaining > 0) {
                    delay(1000)
                    timeRemaining--
                    sessionDuration--
                }
                
                if (sessionDuration <= 0) break
                
                // Exhale phase
                currentPhase = BreathingPhase.EXHALE
                timeRemaining = session.exhaleTime
                while (timeRemaining > 0) {
                    delay(1000)
                    timeRemaining--
                    sessionDuration--
                }
                
                if (sessionDuration <= 0) break
                
                // Rest phase
                currentPhase = BreathingPhase.REST
                timeRemaining = 2
                while (timeRemaining > 0) {
                    delay(1000)
                    timeRemaining--
                    sessionDuration--
                }
            }
            
            isSessionActive = false
            currentPhase = BreathingPhase.INHALE
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Guided Breathing") },
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
            if (selectedSession == null) {
                // Session selection
                SessionSelection(
                    sessions = breathingSessions,
                    onSessionSelected = { selectedSession = it }
                )
            } else if (!isSessionActive) {
                // Session preview
                SessionPreview(
                    session = selectedSession!!,
                    onStartSession = { isSessionActive = true },
                    onBackToSelection = { selectedSession = null }
                )
            } else {
                // Active breathing session
                ActiveBreathingSession(
                    session = selectedSession!!,
                    currentPhase = currentPhase,
                    timeRemaining = timeRemaining,
                    sessionDuration = sessionDuration,
                    scale = scale,
                    onStopSession = { 
                        isSessionActive = false
                        selectedSession = null
                    }
                )
            }
        }
    }
}

@Composable
fun SessionSelection(
    sessions: List<BreathingSession>,
    onSessionSelected: (BreathingSession) -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Choose a Breathing Exercise",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(sessions) { session ->
                BreathingSessionCard(
                    session = session,
                    onClick = { onSessionSelected(session) }
                )
            }
        }
    }
}

@Composable
fun BreathingSessionCard(
    session: BreathingSession,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = session.color.copy(alpha = 0.1f)
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                session.icon,
                contentDescription = null,
                tint = session.color,
                modifier = Modifier.size(40.dp)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = session.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = session.description,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "${session.duration} minutes • ${session.inhaleTime}-${session.holdTime}-${session.exhaleTime} pattern",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Icon(
                Icons.Default.ArrowForwardIos,
                contentDescription = "Start session",
                modifier = Modifier.size(20.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun SessionPreview(
    session: BreathingSession,
    onStartSession: () -> Unit,
    onBackToSelection: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = session.name,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        Text(
            text = session.description,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        
        // Breathing pattern preview
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = session.color.copy(alpha = 0.1f)
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Breathing Pattern",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    BreathingPhasePreview("Inhale", session.inhaleTime, session.color)
                    BreathingPhasePreview("Hold", session.holdTime, session.color)
                    BreathingPhasePreview("Exhale", session.exhaleTime, session.color)
                }
            }
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedButton(
                onClick = onBackToSelection,
                modifier = Modifier.weight(1f)
            ) {
                Text("Back")
            }
            
            Button(
                onClick = onStartSession,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = session.color
                )
            ) {
                Text("Start Session")
            }
        }
    }
}

@Composable
fun BreathingPhasePreview(
    phase: String,
    duration: Int,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(color.copy(alpha = 0.3f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = duration.toString(),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
        Text(
            text = phase,
            fontSize = 12.sp,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun ActiveBreathingSession(
    session: BreathingSession,
    currentPhase: BreathingPhase,
    timeRemaining: Int,
    sessionDuration: Int,
    scale: Float,
    onStopSession: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Session info
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = session.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            
            IconButton(onClick = onStopSession) {
                Icon(Icons.Default.Close, contentDescription = "Stop session")
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Breathing circle
        Box(
            modifier = Modifier.size(300.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier.fillMaxSize()
            ) {
                val center = Offset(size.width / 2, size.height / 2)
                val radius = (size.minDimension / 2) * scale
                
                drawCircle(
                    color = session.color.copy(alpha = 0.3f),
                    radius = radius,
                    center = center
                )
                
                drawCircle(
                    color = session.color,
                    radius = radius * 0.7f,
                    center = center
                )
            }
            
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = when (currentPhase) {
                        BreathingPhase.INHALE -> "Breathe In"
                        BreathingPhase.HOLD -> "Hold"
                        BreathingPhase.EXHALE -> "Breathe Out"
                        BreathingPhase.REST -> "Rest"
                    },
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = session.color
                )
                
                Text(
                    text = timeRemaining.toString(),
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = session.color
                )
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Progress
        Text(
            text = "Session Progress",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
        
        LinearProgressIndicator(
            progress = (session.duration * 60 - sessionDuration).toFloat() / (session.duration * 60),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            color = session.color
        )
        
        Text(
            text = "${sessionDuration / 60}:${String.format("%02d", sessionDuration % 60)} remaining",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.weight(1f))
        
        // Instructions
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Text(
                text = when (currentPhase) {
                    BreathingPhase.INHALE -> "Slowly inhale through your nose, filling your lungs completely."
                    BreathingPhase.HOLD -> "Hold your breath gently, feeling the stillness within."
                    BreathingPhase.EXHALE -> "Slowly exhale through your mouth, releasing all tension."
                    BreathingPhase.REST -> "Take a moment to rest and prepare for the next cycle."
                },
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

fun generateBreathingSessions(): List<BreathingSession> {
    return listOf(
        BreathingSession(
            id = "1",
            name = "4-7-8 Breathing",
            description = "Calming technique for stress relief and better sleep",
            duration = 5,
            inhaleTime = 4,
            holdTime = 7,
            exhaleTime = 8,
            color = Color(0xFF2196F3),
            icon = Icons.Default.NightsStay
        ),
        BreathingSession(
            id = "2",
            name = "Box Breathing",
            description = "Military technique for focus and concentration",
            duration = 10,
            inhaleTime = 4,
            holdTime = 4,
            exhaleTime = 4,
            color = Color(0xFF4CAF50),
            icon = Icons.Default.FitnessCenter
        ),
        BreathingSession(
            id = "3",
            name = "Deep Breathing",
            description = "Simple deep breathing for relaxation",
            duration = 5,
            inhaleTime = 4,
            holdTime = 2,
            exhaleTime = 6,
            color = Color(0xFF9C27B0),
            icon = Icons.Default.Psychology
        ),
        BreathingSession(
            id = "4",
            name = "Triangle Breathing",
            description = "Equal timing for balance and harmony",
            duration = 8,
            inhaleTime = 4,
            holdTime = 4,
            exhaleTime = 4,
            color = Color(0xFFFF9800),
            icon = Icons.Default.Psychology
        ),
        BreathingSession(
            id = "5",
            name = "Morning Energizer",
            description = "Quick energizing breath for morning routine",
            duration = 3,
            inhaleTime = 2,
            holdTime = 1,
            exhaleTime = 2,
            color = Color(0xFFE91E63),
            icon = Icons.Default.WbSunny
        ),
        BreathingSession(
            id = "6",
            name = "Anxiety Relief",
            description = "Extended exhale to activate relaxation response",
            duration = 7,
            inhaleTime = 3,
            holdTime = 2,
            exhaleTime = 6,
            color = Color(0xFF00BCD4),
            icon = Icons.Default.Favorite
        )
    )
}
