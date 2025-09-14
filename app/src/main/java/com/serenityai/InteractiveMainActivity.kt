package com.serenityai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.serenityai.data.models.*
import com.serenityai.ui.theme.AITherapistTheme
import com.serenityai.utils.SampleData
import com.serenityai.utils.SpeechUtils
import com.serenityai.utils.rememberSpeechUtils
import java.util.Date
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.collectAsState

@OptIn(ExperimentalMaterial3Api::class)
class InteractiveMainActivity : ComponentActivity() {
    private var speechUtils: SpeechUtils? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AITherapistTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text("AI Therapist") },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer
                            )
                        )
                    }
                ) { innerPadding ->
                    AITherpistDashboard(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        speechUtils?.release()
    }
}

@Composable
fun AITherpistDashboard(modifier: Modifier = Modifier) {
    var selectedScreen by remember { mutableStateOf("home") }
    var showDialog by remember { mutableStateOf(false) }
    var dialogContent by remember { mutableStateOf("") }
    
    when (selectedScreen) {
        "home" -> HomeScreen(
            onNavigate = { selectedScreen = it },
            onShowDialog = { content -> 
                dialogContent = content
                showDialog = true 
            }
        )
        "chat" -> ChatScreen(
            onBack = { selectedScreen = "home" },
            onShowDialog = { content -> 
                dialogContent = content
                showDialog = true 
            }
        )
        "mood" -> MoodScreen(
            onBack = { selectedScreen = "home" },
            onShowDialog = { content -> 
                dialogContent = content
                showDialog = true 
            }
        )
        "journal" -> JournalScreen(
            onBack = { selectedScreen = "home" },
            onShowDialog = { content -> 
                dialogContent = content
                showDialog = true 
            }
        )
        "challenges" -> ChallengesScreen(
            onBack = { selectedScreen = "home" },
            onShowDialog = { content -> 
                dialogContent = content
                showDialog = true 
            }
        )
        "rewards" -> RewardsScreen(
            onBack = { selectedScreen = "home" },
            onShowDialog = { content -> 
                dialogContent = content
                showDialog = true 
            }
        )
        "dashboard" -> DashboardScreen(
            onBack = { selectedScreen = "home" },
            onShowDialog = { content -> 
                dialogContent = content
                showDialog = true 
            }
        )
    }
    
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Feature Preview") },
            text = { Text(dialogContent) },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("OK")
                }
            }
        )
    }
}

@Composable
fun HomeScreen(
    onNavigate: (String) -> Unit,
    onShowDialog: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        item {
            // Welcome header with modern design
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                shape = MaterialTheme.shapes.extraLarge
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Welcome to Serenity AI",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "Your AI-powered mental wellness companion",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }
        
        item {
            // Feature grid with modern cards
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.height(400.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    ModernFeatureCard(
                        title = "🤖 AI Chat",
                        description = "Talk to your AI therapist",
                        icon = Icons.Default.Chat,
                        onClick = { onNavigate("chat") },
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                }
                
                item {
                    ModernFeatureCard(
                        title = "📊 Mood",
                        description = "Track your moods",
                        icon = Icons.Default.TrendingUp,
                        onClick = { onNavigate("mood") },
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer
                    )
                }
                
                item {
                    ModernFeatureCard(
                        title = "📝 Journal",
                        description = "Express yourself",
                        icon = Icons.Default.Edit,
                        onClick = { onNavigate("journal") },
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                }
                
                item {
                    ModernFeatureCard(
                        title = "🎯 Challenges",
                        description = "Daily wellness",
                        icon = Icons.Default.Star,
                        onClick = { onNavigate("challenges") },
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                }
                
                item {
                    ModernFeatureCard(
                        title = "🏆 Rewards",
                        description = "Your achievements",
                        icon = Icons.Default.EmojiEvents,
                        onClick = { onNavigate("rewards") },
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                }
                
                item {
                    ModernFeatureCard(
                        title = "📈 Dashboard",
                        description = "Progress insights",
                        icon = Icons.Default.Analytics,
                        onClick = { onNavigate("dashboard") },
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModernFeatureCard(
    title: String,
    description: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit,
    containerColor: androidx.compose.ui.graphics.Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        shape = MaterialTheme.shapes.extraLarge,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeatureButton(
    title: String,
    description: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Navigate",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    onBack: () -> Unit,
    onShowDialog: (String) -> Unit
) {
    var message by remember { mutableStateOf("") }
    var messages by remember { mutableStateOf(listOf(
        "Hello! I'm here to listen and understand what you're experiencing.",
        "I want you to know that whatever you're feeling right now is valid and real. I'm here to help you explore your thoughts and emotions with compassion and insight."
    )) }
    
    val speechUtils = rememberSpeechUtils()
    val isSpeaking by speechUtils.isSpeaking.collectAsState()
    val isListening by speechUtils.isListening.collectAsState()
    val recognizedText by speechUtils.recognizedText.collectAsState()
    
    // Update message when speech is recognized
    LaunchedEffect(recognizedText) {
        if (recognizedText.isNotEmpty()) {
            message = recognizedText
            speechUtils.clearRecognizedText()
        }
    }
    
    fun generateAIResponse(userMessage: String): String {
        val lowerMessage = userMessage.lowercase()
        
        // Therapeutic analysis and solution-focused responses
        when {
            lowerMessage.contains("sad") || lowerMessage.contains("depressed") || lowerMessage.contains("down") -> {
                return "I can feel the weight of sadness in your words. Let's explore this together. \n\n**Understanding the Cause:**\n• What triggered these feelings? Is it a recent event or something that's been building?\n• Are there patterns you notice - does this happen at certain times or in specific situations?\n• What thoughts go through your mind when you feel this way?\n\n**Therapeutic Solutions to Consider:**\n• **Emotional Processing:** Try journaling about what you're feeling - sometimes writing helps us understand our emotions better\n• **Physical Movement:** Even a short walk can help release endorphins and shift your mood\n• **Connection:** Reach out to someone you trust - isolation often makes sadness feel heavier\n• **Professional Support:** If these feelings persist or feel overwhelming, consider speaking with a mental health professional\n\nWhat feels like the most helpful approach for you right now?"
            }
            lowerMessage.contains("anxious") || lowerMessage.contains("worried") || lowerMessage.contains("nervous") -> {
                return "I can sense the restlessness and worry in your message. Let's work through this systematically. \n\n**Identifying the Root Cause:**\n• What specific thoughts are making you feel anxious? Are they about the future, past, or present?\n• Is this a new feeling or something you've experienced before?\n• What situations or triggers seem to make it worse?\n\n**Immediate Coping Strategies:**\n• **4-7-8 Breathing:** Inhale for 4 counts, hold for 7, exhale for 8 - this activates your parasympathetic nervous system\n• **Grounding Technique:** Name 5 things you can see, 4 you can touch, 3 you can hear, 2 you can smell, 1 you can taste\n• **Challenge Your Thoughts:** Ask yourself - 'What's the worst that could happen?' and 'How likely is that really?'\n• **Progressive Muscle Relaxation:** Tense and release each muscle group from toes to head\n\n**Long-term Solutions:**\n• **Mindfulness Practice:** Regular meditation can help manage anxiety over time\n• **Lifestyle Changes:** Regular exercise, good sleep, and limiting caffeine can significantly reduce anxiety\n• **Cognitive Behavioral Techniques:** Working with a therapist to identify and change thought patterns\n\nWhich of these approaches resonates with you most?"
            }
            lowerMessage.contains("angry") || lowerMessage.contains("mad") || lowerMessage.contains("frustrated") -> {
                return "I can feel the intensity of your anger and frustration. Let's explore what's really happening here. \n\n**Understanding the Underlying Cause:**\n• What specific event or situation triggered this anger?\n• Are you feeling hurt, disrespected, or powerless in some way?\n• Is this anger masking other emotions like sadness, fear, or disappointment?\n• What need or boundary feels violated right now?\n\n**Immediate Anger Management:**\n• **Take a Pause:** Count to 10 or take 5 deep breaths before responding\n• **Physical Release:** Go for a walk, do jumping jacks, or squeeze a stress ball\n• **Express Safely:** Write down what you're feeling or talk to someone you trust\n• **Identify the Real Issue:** Ask yourself what you really need right now\n\n**Long-term Solutions:**\n• **Communication Skills:** Learn to express needs and boundaries clearly and calmly\n• **Stress Management:** Regular exercise and relaxation techniques can reduce overall irritability\n• **Address Underlying Issues:** Sometimes anger points to deeper problems that need attention\n• **Professional Help:** Anger management therapy can provide effective tools and strategies\n\nWhat do you think is really at the heart of this anger?"
            }
            lowerMessage.contains("stressed") || lowerMessage.contains("overwhelmed") -> {
                return "I can feel the weight of everything pressing down on you. Let's break this down and find solutions. \n\n**Identifying Stress Sources:**\n• What are the top 3 things causing you the most stress right now?\n• Are these external pressures, internal expectations, or both?\n• What feels most urgent vs. most important?\n• Are there any stressors you can eliminate or delegate?\n\n**Immediate Stress Relief:**\n• **Prioritize:** Make a list and tackle one thing at a time - you don't have to do everything at once\n• **Break It Down:** Take large tasks and break them into smaller, manageable steps\n• **Set Boundaries:** Learn to say 'no' to non-essential commitments\n• **Take Breaks:** Even 5-10 minutes can help reset your nervous system\n\n**Long-term Stress Management:**\n• **Time Management:** Use tools like calendars, to-do lists, and time-blocking\n• **Self-Care Routine:** Regular exercise, good sleep, and healthy eating build resilience\n• **Support System:** Don't try to handle everything alone - ask for help\n• **Professional Support:** A therapist can help you develop personalized stress management strategies\n\nWhat feels like the most overwhelming thing right now, and how can we make it more manageable?"
            }
            lowerMessage.contains("happy") || lowerMessage.contains("good") || lowerMessage.contains("great") -> {
                return "I can feel the lightness and joy radiating from your words! This is wonderful to witness. \n\n**Understanding Your Happiness:**\n• What specifically is contributing to this positive feeling?\n• Is this a new development or something that's been building?\n• What activities, people, or experiences are bringing you joy?\n• How can you create more of these positive moments?\n\n**Building on This Positive Energy:**\n• **Gratitude Practice:** Write down 3 things you're grateful for each day\n• **Share Your Joy:** Tell someone about what's making you happy - it can multiply the feeling\n• **Create Rituals:** Build routines around the things that bring you joy\n• **Document It:** Keep a happiness journal to remember what works for you\n\n**Maintaining Positive Momentum:**\n• **Mindful Appreciation:** Take time to really savor these good moments\n• **Set Positive Goals:** Use this energy to work toward things that matter to you\n• **Help Others:** Sharing your positive energy can create a ripple effect\n• **Prepare for Challenges:** Use this time to build resilience for when things get tough\n\nWhat's making your heart feel so full right now, and how can we help you maintain this positive energy?"
            }
            lowerMessage.contains("tired") || lowerMessage.contains("exhausted") -> {
                return "I can feel the deep weariness in your words. Let's address both the symptoms and the root causes. \n\n**Identifying the Type of Exhaustion:**\n• Is this physical, emotional, mental, or all three?\n• How long have you been feeling this way?\n• What's been different in your life recently?\n• Are you getting enough quality sleep?\n\n**Immediate Rest and Recovery:**\n• **Rest When You Can:** Even 15-20 minutes of rest can help recharge you\n• **Hydrate and Nourish:** Dehydration and poor nutrition can worsen fatigue\n• **Gentle Movement:** Sometimes light stretching or a short walk can boost energy\n• **Reduce Stimulation:** Limit screen time and overwhelming activities\n\n**Addressing Root Causes:**\n• **Sleep Hygiene:** Consistent bedtime, dark room, no screens before bed\n• **Stress Management:** Chronic stress is a major cause of exhaustion\n• **Boundaries:** Learn to say no to things that drain your energy\n• **Health Check:** Consider if there might be underlying health issues\n\n**Long-term Energy Management:**\n• **Regular Exercise:** Counterintuitively, regular exercise increases energy levels\n• **Balanced Lifestyle:** Work, rest, and play in healthy proportions\n• **Professional Support:** A therapist can help identify patterns and develop strategies\n\nWhat do you think might be the main cause of your exhaustion, and what would feel most restorative right now?"
            }
            lowerMessage.contains("work") -> {
                return "I can sense the complexity of your relationship with work. Let's explore this together and find solutions. \n\n**Understanding Your Work Challenges:**\n• What specific aspects of work are causing you stress or dissatisfaction?\n• Is it the workload, relationships, environment, or something else?\n• What would your ideal work situation look like?\n• Are there any patterns in when work feels most difficult?\n\n**Immediate Work Stress Relief:**\n• **Time Management:** Break large tasks into smaller chunks, use the Pomodoro technique\n• **Set Boundaries:** Define clear work hours and stick to them\n• **Take Breaks:** Regular short breaks can improve focus and reduce stress\n• **Communicate Needs:** Talk to your supervisor about workload or support needs\n\n**Long-term Work Solutions:**\n• **Skill Development:** Consider what skills might help you feel more confident\n• **Career Planning:** Evaluate if this role aligns with your values and goals\n• **Work-Life Balance:** Create clear separation between work and personal time\n• **Professional Development:** Consider coaching or mentoring opportunities\n\n**If Work Issues Persist:**\n• **Document Everything:** Keep records of problems and attempts to resolve them\n• **HR Support:** Use company resources for workplace issues\n• **Career Counseling:** A professional can help you explore options\n• **Therapy:** Work stress often has deeper roots that therapy can address\n\nWhat aspect of work is most challenging for you right now, and what would help you feel more supported?"
            }
            lowerMessage.contains("relationship") || lowerMessage.contains("partner") || lowerMessage.contains("family") -> {
                return "I can feel the depth of emotion around your relationships. Let's work through this together. \n\n**Understanding Relationship Dynamics:**\n• What specific issues are you facing in your relationships?\n• Is this about communication, boundaries, trust, or something else?\n• How are these relationships affecting your overall wellbeing?\n• What would healthy relationships look like for you?\n\n**Immediate Relationship Strategies:**\n• **Active Listening:** Focus on understanding before being understood\n• **Use 'I' Statements:** Express your feelings without blaming (e.g., 'I feel hurt when...')\n• **Take Breaks:** Sometimes stepping back can help you see things more clearly\n• **Seek Support:** Talk to trusted friends or family about what you're experiencing\n\n**Long-term Relationship Solutions:**\n• **Communication Skills:** Learn effective ways to express needs and listen to others\n• **Boundary Setting:** Understand what you will and won't accept in relationships\n• **Conflict Resolution:** Develop skills for handling disagreements constructively\n• **Self-Reflection:** Consider your own patterns and how they might contribute to issues\n\n**When to Seek Professional Help:**\n• **Couples/Family Therapy:** Can help improve communication and resolve conflicts\n• **Individual Therapy:** Can help you understand your relationship patterns\n• **Support Groups:** Connect with others facing similar relationship challenges\n\nWhat's the most important thing you'd like to change in your relationships, and what support do you need to make that happen?"
            }
            lowerMessage.contains("crisis") || lowerMessage.contains("immediate help") || lowerMessage.contains("emergency") -> {
                return "I can hear that you're in crisis and need immediate support. Your safety and wellbeing are the most important things right now. \n\n**Immediate Crisis Resources:**\n• **National Suicide Prevention Lifeline:** 988 (US) - Available 24/7\n• **Crisis Text Line:** Text HOME to 741741\n• **Emergency Services:** 911 for immediate danger\n• **Local Crisis Centers:** Search for mental health crisis services in your area\n\n**Right Now - Safety First:**\n• **Are you safe?** If you're having thoughts of self-harm, please reach out to someone immediately\n• **Don't be alone:** Stay with someone you trust or go to a public place\n• **Remove means:** If you have access to anything you might use to harm yourself, please put it away or ask someone to help you\n\n**Professional Support:**\n• **Emergency Room:** If you're in immediate danger, go to your nearest emergency room\n• **Crisis Hotlines:** Trained counselors are available 24/7 to listen and help\n• **Mobile Crisis Teams:** Many areas have teams that can come to you\n\n**Remember:** This feeling will pass, and you don't have to face this alone. There are people who care about you and want to help. \n\nAre you safe right now? What kind of support do you need most immediately?"
            }
            lowerMessage.contains("help") || lowerMessage.contains("advice") -> {
                return "I can feel your openness to support and guidance. Let me help you in a structured way. \n\n**First, let's understand what you need:**\n• What specific challenge are you facing right now?\n• What have you already tried to address this issue?\n• What would success look like for you?\n• What resources or support do you currently have?\n\n**Therapeutic Problem-Solving Approach:**\n• **Identify the Problem:** Clearly define what you're dealing with\n• **Explore Options:** Brainstorm all possible solutions, even unconventional ones\n• **Evaluate Choices:** Consider the pros and cons of each option\n• **Make a Plan:** Choose the best approach and break it into steps\n• **Take Action:** Start with small, manageable steps\n• **Review and Adjust:** Regularly assess what's working and what isn't\n\n**Types of Support Available:**\n• **Self-Help Resources:** Books, apps, online courses\n• **Peer Support:** Support groups, online communities\n• **Professional Help:** Therapists, counselors, coaches\n• **Crisis Support:** Hotlines, emergency services if needed\n\n**Remember:** You don't have to figure this out alone. What specific area would you like to focus on first?"
            }
            lowerMessage.contains("thank") -> {
                return "I can feel the warmth and gratitude in your words. Thank you for sharing that with me. \n\n**Your gratitude is a powerful tool for wellbeing:**\n• **Gratitude Practice:** Consider keeping a daily gratitude journal\n• **Express Appreciation:** Let others know when they've helped you\n• **Reflect on Growth:** Acknowledge how far you've come in your journey\n• **Pay It Forward:** Use your positive energy to help others\n\n**Building on This Positive Moment:**\n• **Celebrate Progress:** Take time to recognize your achievements, no matter how small\n• **Share Your Story:** Your experiences might help others who are struggling\n• **Continue the Work:** Use this positive energy to keep moving forward\n• **Stay Connected:** Maintain the relationships and practices that support you\n\n**Remember:** Healing and growth are ongoing processes. You're doing important work, and it's okay to celebrate that. How does it feel to express this appreciation, and what would you like to focus on next?"
            }
            else -> {
                val responses = listOf(
                    "I can feel the depth of what you're sharing with me. Let's work through this together systematically. \n\n**Understanding Your Situation:**\n• What's the main challenge you're facing right now?\n• How is this affecting your daily life and wellbeing?\n• What would you like to be different?\n• What support do you feel you need most?\n\n**Therapeutic Approach:**\n• **Explore the Root Cause:** Sometimes symptoms point to deeper issues\n• **Identify Patterns:** Look for recurring themes in your experiences\n• **Develop Coping Strategies:** Build tools for managing difficult situations\n• **Create Action Plans:** Break down solutions into manageable steps\n\nWhat feels most important to address first, and how can I best support you in this process?",
                    "I can sense the significance of what you're sharing. Let's approach this therapeutically. \n\n**Assessment Questions:**\n• What specific thoughts or feelings are most prominent right now?\n• How long have you been dealing with this situation?\n• What's been your typical way of handling similar challenges?\n• What resources or strengths do you have that might help?\n\n**Solution-Focused Approach:**\n• **Identify Exceptions:** When has this problem been less severe or absent?\n• **Build on Strengths:** What personal qualities help you cope?\n• **Set Realistic Goals:** What would meaningful progress look like?\n• **Develop Strategies:** Create specific plans for moving forward\n\nWhat aspect of this situation would you like to explore first, and what outcome are you hoping for?",
                    "I can feel the complexity of what you're navigating. Let's break this down together. \n\n**Understanding the Full Picture:**\n• What are the different factors contributing to this situation?\n• How are your thoughts, feelings, and behaviors interconnected?\n• What external pressures or internal conflicts are at play?\n• What would resolution or improvement look like for you?\n\n**Therapeutic Problem-Solving:**\n• **Mindfulness:** Practice observing your experience without judgment\n• **Cognitive Restructuring:** Challenge unhelpful thought patterns\n• **Behavioral Changes:** Identify actions that might improve the situation\n• **Emotional Processing:** Allow yourself to feel and understand your emotions\n\nWhat feels like the most pressing issue to address, and what kind of support would be most helpful right now?"
                )
                return responses.random()
            }
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text(
                text = "AI Chat Therapist",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Speech status indicator
        if (isSpeaking || isListening) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (isSpeaking) {
                        Icon(
                            imageVector = Icons.Default.VolumeUp,
                            contentDescription = "Speaking",
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "AI is speaking...",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    } else if (isListening) {
                        Icon(
                            imageVector = Icons.Default.Mic,
                            contentDescription = "Listening",
                            tint = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Listening... Speak now",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
        }
        
        // Quick action buttons - moved to bottom
        Spacer(modifier = Modifier.weight(1f))
        
        Spacer(modifier = Modifier.height(16.dp))
        
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(messages) { msg ->
                val isUser = msg.startsWith("You: ")
                val messageText = if (isUser) msg.substring(5) else msg
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
                ) {
                    Card(
                        modifier = Modifier.widthIn(max = 280.dp),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = if (isUser) 2.dp else 4.dp
                        ),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isUser) 
                                MaterialTheme.colorScheme.primary 
                            else 
                                MaterialTheme.colorScheme.surfaceVariant
                        ),
                        shape = MaterialTheme.shapes.extraLarge
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = messageText,
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (isUser) 
                                    MaterialTheme.colorScheme.onPrimary 
                                else 
                                    MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            
                            // Add read-aloud button for AI messages
                            if (!isUser) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    IconButton(
                                        onClick = { 
                                            if (isSpeaking) {
                                                speechUtils.stopSpeaking()
                                            } else {
                                                speechUtils.speak(messageText)
                                            }
                                        },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            imageVector = if (isSpeaking) Icons.Default.Stop else Icons.Default.VolumeUp,
                                            contentDescription = if (isSpeaking) "Stop reading" else "Read aloud",
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        
        // Modern input section with floating action buttons
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // Quick action chips
                Text(
                    text = "Quick Actions",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                
                LazyColumn(
                    modifier = Modifier.height(120.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            FilterChip(
                                onClick = { message = "I'm feeling sad today" },
                                label = { Text("😢 I'm sad") },
                                selected = false,
                                modifier = Modifier.weight(1f)
                            )
                            FilterChip(
                                onClick = { message = "I'm feeling anxious and worried" },
                                label = { Text("😰 I'm anxious") },
                                selected = false,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                    
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            FilterChip(
                                onClick = { message = "I'm stressed about work" },
                                label = { Text("💼 Work stress") },
                                selected = false,
                                modifier = Modifier.weight(1f)
                            )
                            FilterChip(
                                onClick = { message = "I need help with my relationships" },
                                label = { Text("💕 Relationships") },
                                selected = false,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                    
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            FilterChip(
                                onClick = { message = "I need immediate help and support" },
                                label = { Text("🚨 Crisis Support") },
                                selected = false,
                                modifier = Modifier.weight(1f),
                                colors = FilterChipDefaults.filterChipColors(
                                    containerColor = MaterialTheme.colorScheme.errorContainer
                                )
                            )
                            FilterChip(
                                onClick = { message = "I want to explore solutions to my problems" },
                                label = { Text("💡 Find Solutions") },
                                selected = false,
                                modifier = Modifier.weight(1f),
                                colors = FilterChipDefaults.filterChipColors(
                                    containerColor = MaterialTheme.colorScheme.tertiaryContainer
                                )
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Input row with modern design
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = message,
                        onValueChange = { message = it },
                        placeholder = { Text("Type your message or use voice...") },
                        modifier = Modifier.weight(1f),
                        shape = MaterialTheme.shapes.extraLarge,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline
                        )
                    )
                    
                    Spacer(modifier = Modifier.width(12.dp))
                    
                    // Modern floating action buttons
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Speech-to-Text button
                        FloatingActionButton(
                            onClick = {
                                if (isListening) {
                                    speechUtils.stopListening()
                                } else {
                                    speechUtils.startListening()
                                }
                            },
                            modifier = Modifier.size(48.dp),
                            containerColor = if (isListening) 
                                MaterialTheme.colorScheme.error 
                            else 
                                MaterialTheme.colorScheme.secondaryContainer,
                            contentColor = if (isListening) 
                                MaterialTheme.colorScheme.onError 
                            else 
                                MaterialTheme.colorScheme.onSecondaryContainer
                        ) {
                            Icon(
                                imageVector = if (isListening) Icons.Default.Stop else Icons.Default.Mic,
                                contentDescription = if (isListening) "Stop listening" else "Start voice input",
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        
                        // Send button
                        FloatingActionButton(
                            onClick = {
                                if (message.isNotBlank()) {
                                    messages = messages + "You: $message"
                                    val aiResponse = generateAIResponse(message)
                                    messages = messages + aiResponse
                                    message = ""
                                }
                            },
                            modifier = Modifier.size(48.dp),
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ) {
                            Icon(
                                imageVector = Icons.Default.Send,
                                contentDescription = "Send",
                                modifier = Modifier.size(20.dp)
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
fun MoodScreen(
    onBack: () -> Unit,
    onShowDialog: (String) -> Unit
) {
    var selectedMood by remember { mutableStateOf(5) }
    var notes by remember { mutableStateOf("") }
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        item {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Text(
                    text = "Mood Tracking",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        
        item {
            // Mood selection card
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                shape = MaterialTheme.shapes.extraLarge
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "How are you feeling today?",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    
                    Spacer(modifier = Modifier.height(20.dp))
                    
                    // Modern mood selector
                    LazyColumn(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                for (i in 1..5) {
                                    FloatingActionButton(
                                        onClick = { selectedMood = i },
                                        modifier = Modifier.size(48.dp),
                                        containerColor = if (selectedMood == i) 
                                            MaterialTheme.colorScheme.primary 
                                        else 
                                            MaterialTheme.colorScheme.surface,
                                        contentColor = if (selectedMood == i) 
                                            MaterialTheme.colorScheme.onPrimary 
                                        else 
                                            MaterialTheme.colorScheme.onSurface
                                    ) {
                                        Text("$i", style = MaterialTheme.typography.titleMedium)
                                    }
                                }
                            }
                        }
                        
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                for (i in 6..10) {
                                    FloatingActionButton(
                                        onClick = { selectedMood = i },
                                        modifier = Modifier.size(48.dp),
                                        containerColor = if (selectedMood == i) 
                                            MaterialTheme.colorScheme.primary 
                                        else 
                                            MaterialTheme.colorScheme.surface,
                                        contentColor = if (selectedMood == i) 
                                            MaterialTheme.colorScheme.onPrimary 
                                        else 
                                            MaterialTheme.colorScheme.onSurface
                                    ) {
                                        Text("$i", style = MaterialTheme.typography.titleMedium)
                                    }
                                }
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(
                        text = "Selected: $selectedMood/10",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }
        
        item {
            // Notes card
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                shape = MaterialTheme.shapes.extraLarge
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Add notes (optional)",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    OutlinedTextField(
                        value = notes,
                        onValueChange = { notes = it },
                        placeholder = { Text("What's contributing to your mood today?") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3,
                        shape = MaterialTheme.shapes.medium,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline
                        )
                    )
                }
            }
        }
        
        item {
            // Save button
            Button(
                onClick = { 
                    onShowDialog("Mood logged successfully! Your mood: $selectedMood/10")
                },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.extraLarge,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = "Save Mood Entry",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalScreen(
    onBack: () -> Unit,
    onShowDialog: (String) -> Unit
) {
    var selectedType by remember { mutableStateOf(JournalType.FREEFORM) }
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        item {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Text(
                    text = "Journal Entry",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        
        item {
            // Journal type selection card
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                shape = MaterialTheme.shapes.extraLarge
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Journal Type",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        JournalType.values().forEach { type ->
                            FilterChip(
                                onClick = { selectedType = type },
                                label = { Text(type.name.replace("_", " ")) },
                                selected = selectedType == type,
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                                )
                            )
                        }
                    }
                }
            }
        }
        
        item {
            // Title input card
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                shape = MaterialTheme.shapes.extraLarge
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Entry Title",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        placeholder = { Text("Entry title (optional)") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.medium,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline
                        )
                    )
                }
            }
        }
        
        item {
            // Content input card
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                shape = MaterialTheme.shapes.extraLarge
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Your Thoughts",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    OutlinedTextField(
                        value = content,
                        onValueChange = { content = it },
                        placeholder = { Text("Write your thoughts here...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        minLines = 8,
                        shape = MaterialTheme.shapes.medium,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline
                        )
                    )
                }
            }
        }
        
        item {
            // Save button
            Button(
                onClick = { 
                    onShowDialog("Journal entry saved successfully!")
                },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.extraLarge,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = "Save Entry",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun ChallengesScreen(
    onBack: () -> Unit,
    onShowDialog: (String) -> Unit
) {
    val challenges = SampleData.createSampleChallenges()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text(
                text = "Wellness Challenges",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(challenges) { challenge ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = challenge.title,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = challenge.description,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Difficulty: ${challenge.difficulty.name}",
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(
                                text = "XP: ${challenge.xpReward}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        Button(
                            onClick = { 
                                onShowDialog("Challenge '${challenge.title}' started! Good luck!")
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Start Challenge")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RewardsScreen(
    onBack: () -> Unit,
    onShowDialog: (String) -> Unit
) {
    val badges = SampleData.createSampleBadges()
    val affirmations = SampleData.createSampleAffirmations()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text(
                text = "Rewards & Achievements",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Your Progress",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("1,250", style = MaterialTheme.typography.headlineMedium)
                        Text("Total XP", style = MaterialTheme.typography.bodySmall)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("7", style = MaterialTheme.typography.headlineMedium)
                        Text("Day Streak", style = MaterialTheme.typography.bodySmall)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("3", style = MaterialTheme.typography.headlineMedium)
                        Text("Level", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Your Badges",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(badges) { badge ->
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = badge.icon,
                            style = MaterialTheme.typography.headlineMedium
                        )
                        
                        Spacer(modifier = Modifier.width(16.dp))
                        
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = badge.name,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = badge.description,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                        
                        Text(
                            text = "+${badge.xpValue} XP",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Daily Affirmation",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        ) {
            Text(
                text = affirmations.first().text,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun DashboardScreen(
    onBack: () -> Unit,
    onShowDialog: (String) -> Unit
) {
    val moodEntries = SampleData.createSampleMoodEntries()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text(
                text = "Dashboard",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Mood Overview",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        val avgMood = moodEntries.map { it.mood }.average()
                        Text(
                            text = "Average Mood: ${String.format("%.1f", avgMood)}/10",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        
                        Text(
                            text = "Last 3 entries: ${moodEntries.take(3).joinToString(", ") { "${it.mood}/10" }}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
            
            item {
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Recent Journal Entries",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        val journalEntries = SampleData.createSampleJournalEntries()
                        journalEntries.take(2).forEach { entry ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(12.dp)
                                ) {
                                    Text(
                                        text = entry.title,
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = entry.content.take(100) + if (entry.content.length > 100) "..." else "",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                    }
                }
            }
            
            item {
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Quick Actions",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = { onShowDialog("Mood logging started!") },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Log Mood")
                            }
                            
                            Button(
                                onClick = { onShowDialog("Journal opened!") },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Write Journal")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AITherpistDashboardPreview() {
    AITherapistTheme {
        AITherpistDashboard()
    }
}
