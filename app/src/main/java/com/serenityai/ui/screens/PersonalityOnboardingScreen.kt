package com.serenityai.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Data classes for personality onboarding
data class PersonalityProfile(
    val communicationStyle: CommunicationStyle,
    val therapyPreferences: List<TherapyPreference>,
    val goals: List<WellnessGoal>,
    val challenges: List<MentalHealthChallenge>,
    val preferredActivities: List<ActivityPreference>
)

enum class CommunicationStyle(
    val title: String,
    val description: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val color: Color
) {
    DIRECT(
        "Direct & Straightforward",
        "I prefer clear, direct communication without sugar-coating",
        Icons.Default.Straighten,
        Color(0xFF2196F3)
    ),
    SUPPORTIVE(
        "Supportive & Encouraging",
        "I respond well to gentle, encouraging guidance",
        Icons.Default.Favorite,
        Color(0xFFE91E63)
    ),
    ANALYTICAL(
        "Analytical & Thoughtful",
        "I like detailed explanations and logical approaches",
        Icons.Default.Analytics,
        Color(0xFF9C27B0)
    ),
    CREATIVE(
        "Creative & Expressive",
        "I enjoy metaphors, stories, and creative approaches",
        Icons.Default.Palette,
        Color(0xFFFF9800)
    )
}

enum class TherapyPreference(val title: String, val description: String) {
    CBT("Cognitive Behavioral Therapy", "Focus on changing thought patterns"),
    MINDFULNESS("Mindfulness & Meditation", "Present-moment awareness techniques"),
    SOLUTION_FOCUSED("Solution-Focused Therapy", "Goal-oriented problem solving"),
    EMOTIONAL_SUPPORT("Emotional Support", "Validation and emotional processing"),
    SKILL_BUILDING("Skill Building", "Learning practical coping strategies")
}

enum class WellnessGoal(val title: String, val description: String) {
    ANXIETY_MANAGEMENT("Manage Anxiety", "Reduce anxiety and worry"),
    MOOD_IMPROVEMENT("Improve Mood", "Enhance overall emotional well-being"),
    STRESS_REDUCTION("Reduce Stress", "Better stress management techniques"),
    SLEEP_IMPROVEMENT("Better Sleep", "Improve sleep quality and patterns"),
    RELATIONSHIPS("Better Relationships", "Improve interpersonal connections"),
    SELF_ESTEEM("Build Self-Esteem", "Increase confidence and self-worth"),
    PRODUCTIVITY("Increase Productivity", "Improve focus and motivation"),
    MINDFULNESS("Practice Mindfulness", "Develop present-moment awareness")
}

enum class MentalHealthChallenge(val title: String, val description: String) {
    ANXIETY("Anxiety", "Excessive worry and nervousness"),
    DEPRESSION("Depression", "Persistent sadness and low mood"),
    STRESS("Stress", "Feeling overwhelmed by daily pressures"),
    SLEEP_ISSUES("Sleep Problems", "Difficulty sleeping or poor sleep quality"),
    RELATIONSHIP_ISSUES("Relationship Issues", "Challenges in personal relationships"),
    WORK_PRESSURE("Work Pressure", "Stress related to work or career"),
    SOCIAL_ANXIETY("Social Anxiety", "Fear of social situations"),
    TRAUMA("Trauma", "Dealing with past traumatic experiences")
}

enum class ActivityPreference(val title: String, val description: String) {
    READING("Reading", "Books, articles, and written content"),
    AUDIO("Audio Content", "Podcasts, music, and guided audio"),
    INTERACTIVE("Interactive Activities", "Games, quizzes, and hands-on exercises"),
    WRITING("Writing & Journaling", "Reflective writing and self-expression"),
    VISUAL("Visual Content", "Images, videos, and visual aids"),
    MOVEMENT("Movement & Exercise", "Physical activities and body-based practices")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalityOnboardingScreen(onNavigateBack: () -> Unit, onComplete: (PersonalityProfile) -> Unit) {
    var currentStep by remember { mutableStateOf(0) }
    var communicationStyle by remember { mutableStateOf<CommunicationStyle?>(null) }
    var therapyPreferences by remember { mutableStateOf<List<TherapyPreference>>(emptyList()) }
    var goals by remember { mutableStateOf<List<WellnessGoal>>(emptyList()) }
    var challenges by remember { mutableStateOf<List<MentalHealthChallenge>>(emptyList()) }
    var activityPreferences by remember { mutableStateOf<List<ActivityPreference>>(emptyList()) }
    
    val steps = listOf(
        "Communication Style",
        "Therapy Preferences", 
        "Wellness Goals",
        "Current Challenges",
        "Activity Preferences"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text("Personality Onboarding") 
                },
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
            // Progress indicator
            LinearProgressIndicator(
                progress = (currentStep + 1).toFloat() / steps.size,
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Step ${currentStep + 1} of ${steps.size}",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Text(
                text = steps[currentStep],
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            when (currentStep) {
                0 -> CommunicationStyleStep(
                    selectedStyle = communicationStyle,
                    onStyleSelected = { communicationStyle = it }
                )
                1 -> TherapyPreferencesStep(
                    selectedPreferences = therapyPreferences,
                    onPreferencesChanged = { therapyPreferences = it }
                )
                2 -> WellnessGoalsStep(
                    selectedGoals = goals,
                    onGoalsChanged = { goals = it }
                )
                3 -> ChallengesStep(
                    selectedChallenges = challenges,
                    onChallengesChanged = { challenges = it }
                )
                4 -> ActivityPreferencesStep(
                    selectedPreferences = activityPreferences,
                    onPreferencesChanged = { activityPreferences = it }
                )
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (currentStep > 0) {
                    OutlinedButton(
                        onClick = { currentStep-- }
                    ) {
                        Text("Previous")
                    }
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }
                
                Button(
                    onClick = {
                        if (currentStep < steps.size - 1) {
                            currentStep++
                        } else {
                            // Complete onboarding
                            val profile = PersonalityProfile(
                                communicationStyle = communicationStyle!!,
                                therapyPreferences = therapyPreferences,
                                goals = goals,
                                challenges = challenges,
                                preferredActivities = activityPreferences
                            )
                            onComplete(profile)
                        }
                    },
                    enabled = when (currentStep) {
                        0 -> communicationStyle != null
                        1 -> therapyPreferences.isNotEmpty()
                        2 -> goals.isNotEmpty()
                        3 -> challenges.isNotEmpty()
                        4 -> activityPreferences.isNotEmpty()
                        else -> true
                    }
                ) {
                    Text(if (currentStep == steps.size - 1) "Complete" else "Next")
                }
            }
        }
    }
}

@Composable
fun CommunicationStyleStep(
    selectedStyle: CommunicationStyle?,
    onStyleSelected: (CommunicationStyle) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(CommunicationStyle.values()) { style ->
            Card(
                onClick = { onStyleSelected(style) },
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = if (selectedStyle == style) 
                        style.color.copy(alpha = 0.1f) 
                    else MaterialTheme.colorScheme.surface
                ),
                border = if (selectedStyle == style) 
                    CardDefaults.outlinedCardBorder().copy(
                        brush = androidx.compose.foundation.BorderStroke(2.dp, style.color).brush
                    ) 
                else null
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        style.icon,
                        contentDescription = null,
                        tint = style.color,
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = style.title,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = style.description,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TherapyPreferencesStep(
    selectedPreferences: List<TherapyPreference>,
    onPreferencesChanged: (List<TherapyPreference>) -> Unit
) {
    Text(
        text = "Select the therapy approaches that resonate with you:",
        fontSize = 16.sp,
        modifier = Modifier.padding(bottom = 16.dp)
    )
    
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(TherapyPreference.values()) { preference ->
            Card(
                onClick = {
                    val newList = if (selectedPreferences.contains(preference)) {
                        selectedPreferences - preference
                    } else {
                        selectedPreferences + preference
                    }
                    onPreferencesChanged(newList)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = if (selectedPreferences.contains(preference))
                        MaterialTheme.colorScheme.primaryContainer
                    else MaterialTheme.colorScheme.surface
                )
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = selectedPreferences.contains(preference),
                        onCheckedChange = null
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = preference.title,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = preference.description,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun WellnessGoalsStep(
    selectedGoals: List<WellnessGoal>,
    onGoalsChanged: (List<WellnessGoal>) -> Unit
) {
    Text(
        text = "What are your main wellness goals?",
        fontSize = 16.sp,
        modifier = Modifier.padding(bottom = 16.dp)
    )
    
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(WellnessGoal.values()) { goal ->
            Card(
                onClick = {
                    val newList = if (selectedGoals.contains(goal)) {
                        selectedGoals - goal
                    } else {
                        selectedGoals + goal
                    }
                    onGoalsChanged(newList)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = if (selectedGoals.contains(goal))
                        MaterialTheme.colorScheme.secondaryContainer
                    else MaterialTheme.colorScheme.surface
                )
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = selectedGoals.contains(goal),
                        onCheckedChange = null
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = goal.title,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = goal.description,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ChallengesStep(
    selectedChallenges: List<MentalHealthChallenge>,
    onChallengesChanged: (List<MentalHealthChallenge>) -> Unit
) {
    Text(
        text = "What challenges are you currently facing?",
        fontSize = 16.sp,
        modifier = Modifier.padding(bottom = 16.dp)
    )
    
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(MentalHealthChallenge.values()) { challenge ->
            Card(
                onClick = {
                    val newList = if (selectedChallenges.contains(challenge)) {
                        selectedChallenges - challenge
                    } else {
                        selectedChallenges + challenge
                    }
                    onChallengesChanged(newList)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = if (selectedChallenges.contains(challenge))
                        MaterialTheme.colorScheme.tertiaryContainer
                    else MaterialTheme.colorScheme.surface
                )
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = selectedChallenges.contains(challenge),
                        onCheckedChange = null
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = challenge.title,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = challenge.description,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ActivityPreferencesStep(
    selectedPreferences: List<ActivityPreference>,
    onPreferencesChanged: (List<ActivityPreference>) -> Unit
) {
    Text(
        text = "What types of activities do you prefer?",
        fontSize = 16.sp,
        modifier = Modifier.padding(bottom = 16.dp)
    )
    
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(ActivityPreference.values()) { preference ->
            Card(
                onClick = {
                    val newList = if (selectedPreferences.contains(preference)) {
                        selectedPreferences - preference
                    } else {
                        selectedPreferences + preference
                    }
                    onPreferencesChanged(newList)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = if (selectedPreferences.contains(preference))
                        MaterialTheme.colorScheme.primaryContainer
                    else MaterialTheme.colorScheme.surface
                )
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = selectedPreferences.contains(preference),
                        onCheckedChange = null
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = preference.title,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = preference.description,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}
