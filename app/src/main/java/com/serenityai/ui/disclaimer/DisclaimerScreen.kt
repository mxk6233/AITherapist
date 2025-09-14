package com.serenityai.ui.disclaimer

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun DisclaimerScreen(
    onAccept: () -> Unit,
    onDecline: () -> Unit
) {
    var isAccepted by remember { mutableStateOf(false) }
    var currentSection by remember { mutableStateOf(0) }
    val totalSections = 6
    
    // Debug logging
    LaunchedEffect(currentSection) {
        println("Disclaimer: Current section changed to $currentSection")
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.errorContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "⚠️ Important Disclaimer",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = "Section ${currentSection + 1} of $totalSections",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Progress indicator
        LinearProgressIndicator(
            progress = (currentSection + 1).toFloat() / totalSections.toFloat(),
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Disclaimer content with scrolling
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    text = "AI Therapist Disclaimer",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "Please read and understand the following before using this application:",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                when (currentSection) {
                    0 -> DisclaimerSection(
                        title = "1. Not a Replacement for Professional Care",
                        content = "This AI therapist is designed to provide general mental health support and guidance. It is NOT a replacement for professional medical advice, diagnosis, or treatment from qualified healthcare providers.\n\nThis means:\n• The AI cannot diagnose mental health conditions\n• It cannot provide personalized medical treatment\n• Always consult healthcare professionals for serious concerns\n• Use this as a supplement, not a replacement for professional care"
                    )
                    1 -> DisclaimerSection(
                        title = "2. Emergency Situations",
                        content = "If you are experiencing a mental health emergency, having thoughts of self-harm, or are in immediate danger, please:\n\n**Immediate Actions:**\n• Call emergency services (911) immediately\n• Contact the National Suicide Prevention Lifeline at 988\n• Go to your nearest emergency room\n• Text HOME to 741741 for Crisis Text Line\n\n**Remember:** This AI cannot provide emergency support. Always seek immediate professional help in crisis situations."
                    )
                    2 -> DisclaimerSection(
                        title = "3. Privacy and Data",
                        content = "Your conversations may be stored for improving the service. While we take privacy seriously, please avoid sharing highly sensitive personal information that you wouldn't want stored.\n\n**Data Usage:**\n• Conversations may be analyzed to improve AI responses\n• Personal information should be kept minimal\n• We follow privacy best practices\n• You can request data deletion if needed\n\n**Recommendation:** Avoid sharing full names, addresses, or other identifying information."
                    )
                    3 -> DisclaimerSection(
                        title = "4. Limitations",
                        content = "This AI cannot provide personalized medical advice, diagnose mental health conditions, or replace human therapeutic relationships. Responses are generated based on general therapeutic principles.\n\n**What the AI CAN do:**\n• Provide general mental health information\n• Offer coping strategies and techniques\n• Listen and provide emotional support\n• Suggest when to seek professional help\n\n**What the AI CANNOT do:**\n• Diagnose mental health conditions\n• Provide personalized treatment plans\n• Replace human therapeutic relationships\n• Handle emergency situations"
                    )
                    4 -> DisclaimerSection(
                        title = "5. User Responsibility",
                        content = "You are responsible for your own mental health decisions. If you feel the AI's responses are not helpful or appropriate, please seek professional help.\n\n**Your Responsibilities:**\n• Use the AI as a tool, not a sole source of help\n• Seek professional help when needed\n• Trust your instincts about your mental health\n• Don't rely solely on AI for serious concerns\n\n**When to Seek Professional Help:**\n• Persistent feelings of hopelessness\n• Thoughts of self-harm\n• Severe anxiety or depression\n• Any mental health crisis"
                    )
                    5 -> DisclaimerSection(
                        title = "6. Age Restrictions",
                        content = "This application is intended for users 18 years and older. If you are under 18, please seek guidance from a parent, guardian, or appropriate adult.\n\n**Age Requirements:**\n• Must be 18+ to use this application\n• Designed for adult mental health support\n• Parental guidance recommended for younger users\n\n**For Younger Users:**\n• Ask a parent or guardian for permission\n• Consider age-appropriate mental health resources\n• Seek guidance from school counselors or trusted adults"
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Navigation buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Previous button
            if (currentSection > 0) {
                OutlinedButton(
                    onClick = { 
                        if (currentSection > 0) {
                            currentSection--
                        }
                    },
                    modifier = Modifier.weight(1f),
                    enabled = currentSection > 0
                ) {
                    Text("Previous")
                }
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
            
            // Next button
            Button(
                onClick = { 
                    println("Next button clicked! Current: $currentSection, Total: $totalSections")
                    if (currentSection < totalSections - 1) {
                        currentSection++
                        println("Section incremented to: $currentSection")
                    }
                },
                modifier = Modifier.weight(1f),
                enabled = currentSection < totalSections - 1
            ) {
                Text("Next")
            }
        }
        
        // Acceptance checkbox (only show on last section)
        if (currentSection == totalSections - 1) {
            Spacer(modifier = Modifier.height(8.dp))
            
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = isAccepted,
                            onCheckedChange = { isAccepted = it }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "I have read and understand all sections of the disclaimer above",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Final action buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = onDecline,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Decline")
                        }
                        
                        Button(
                            onClick = onAccept,
                            enabled = isAccepted,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Accept & Continue")
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DisclaimerSection(
    title: String,
    content: String
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.height(6.dp))
        
        Text(
            text = content,
            style = MaterialTheme.typography.bodySmall,
            lineHeight = MaterialTheme.typography.bodySmall.lineHeight * 1.3
        )
        
        Spacer(modifier = Modifier.height(12.dp))
    }
}
