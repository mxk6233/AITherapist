package com.serenityai.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

// Data classes for daily affirmations
data class Affirmation(
    val id: String,
    val text: String,
    val category: AffirmationCategory,
    val timestamp: Long = System.currentTimeMillis(),
    val isFavorite: Boolean = false
)

enum class AffirmationCategory(
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val color: Color
) {
    SELF_LOVE("Self-Love", Icons.Default.Favorite, Color(0xFFE91E63)),
    CONFIDENCE("Confidence", Icons.Default.Star, Color(0xFFFF9800)),
    PEACE("Peace", Icons.Default.Psychology, Color(0xFF2196F3)),
    SUCCESS("Success", Icons.Default.TrendingUp, Color(0xFF4CAF50)),
    GRATITUDE("Gratitude", Icons.Default.EmojiEmotions, Color(0xFF9C27B0)),
    STRENGTH("Strength", Icons.Default.FitnessCenter, Color(0xFFF44336)),
    HEALING("Healing", Icons.Default.LocalHospital, Color(0xFF00BCD4)),
    MINDFULNESS("Mindfulness", Icons.Default.Psychology, Color(0xFF8BC34A))
}

// Wrapper function for backward compatibility
@Composable
fun DailyAffirmationsScreen(onNavigateBack: () -> Unit) {
    DailyAffirmationsScreenDetailed(onNavigateBack = onNavigateBack)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyAffirmationsScreenDetailed(onNavigateBack: () -> Unit) {
    var selectedCategory by remember { mutableStateOf<AffirmationCategory?>(null) }
    var currentAffirmation by remember { mutableStateOf<Affirmation?>(null) }
    var showAllAffirmations by remember { mutableStateOf(false) }
    var favoriteAffirmations by remember { mutableStateOf<List<Affirmation>>(emptyList()) }
    
    val allAffirmations = remember { generateAffirmations() }
    
    // Auto-rotate affirmations
    LaunchedEffect(Unit) {
        while (true) {
            delay(10000) // Change every 10 seconds
            if (!showAllAffirmations && selectedCategory == null) {
                currentAffirmation = allAffirmations.random()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Daily Affirmations") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { showAllAffirmations = !showAllAffirmations }) {
                        Icon(
                            if (showAllAffirmations) Icons.Default.Close else Icons.Default.List,
                            contentDescription = if (showAllAffirmations) "Close" else "Show All"
                        )
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
            if (!showAllAffirmations) {
                // Category selector
                CategorySelector(
                    selectedCategory = selectedCategory,
                    onCategorySelected = { category ->
                        selectedCategory = if (selectedCategory == category) null else category
                        currentAffirmation = if (selectedCategory == category) {
                            allAffirmations.filter { it.category == category }.random()
                        } else {
                            allAffirmations.random()
                        }
                    }
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Current affirmation display
                CurrentAffirmationCard(
                    affirmation = currentAffirmation ?: allAffirmations.random(),
                    onFavoriteToggle = { affirmation ->
                        favoriteAffirmations = if (favoriteAffirmations.contains(affirmation)) {
                            favoriteAffirmations - affirmation
                        } else {
                            favoriteAffirmations + affirmation
                        }
                    },
                    isFavorite = favoriteAffirmations.contains(currentAffirmation)
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Action buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            currentAffirmation = if (selectedCategory != null) {
                                allAffirmations.filter { it.category == selectedCategory }.random()
                            } else {
                                allAffirmations.random()
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("New Affirmation")
                    }
                    
                    Spacer(modifier = Modifier.width(16.dp))
                    
                    OutlinedButton(
                        onClick = { showAllAffirmations = true },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.List, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Browse All")
                    }
                }
            } else {
                // All affirmations list
                AllAffirmationsList(
                    affirmations = allAffirmations,
                    favoriteAffirmations = favoriteAffirmations,
                    onFavoriteToggle = { affirmation ->
                        favoriteAffirmations = if (favoriteAffirmations.contains(affirmation)) {
                            favoriteAffirmations - affirmation
                        } else {
                            favoriteAffirmations + affirmation
                        }
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategorySelector(
    selectedCategory: AffirmationCategory?,
    onCategorySelected: (AffirmationCategory) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val categories = AffirmationCategory.values()
        val categoryRows = listOf(
            categories.slice(0..3),
            categories.slice(4..7)
        )
        
        categoryRows.forEach { categoryRow ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categoryRow.forEach { category ->
                    FilterChip(
                        onClick = { onCategorySelected(category) },
                        label = { 
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    category.icon,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(category.title)
                            }
                        },
                        selected = selectedCategory == category,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun CurrentAffirmationCard(
    affirmation: Affirmation,
    onFavoriteToggle: (Affirmation) -> Unit,
    isFavorite: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            affirmation.category.color.copy(alpha = 0.1f),
                            affirmation.category.color.copy(alpha = 0.05f)
                        )
                    )
                )
                .padding(24.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Category header
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Icon(
                        affirmation.category.icon,
                        contentDescription = null,
                        tint = affirmation.category.color,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = affirmation.category.title,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = affirmation.category.color
                    )
                }
                
                // Affirmation text
                Text(
                    text = affirmation.text,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    lineHeight = 28.sp,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
                
                // Favorite button
                IconButton(
                    onClick = { onFavoriteToggle(affirmation) },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Icon(
                        if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
                        tint = if (isFavorite) Color(0xFFE91E63) else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun AllAffirmationsList(
    affirmations: List<Affirmation>,
    favoriteAffirmations: List<Affirmation>,
    onFavoriteToggle: (Affirmation) -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        val groupedAffirmations = affirmations.groupBy { it.category }
        
        groupedAffirmations.forEach { (category, categoryAffirmations) ->
            item {
                Text(
                    text = category.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = category.color,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            
            items(categoryAffirmations) { affirmation ->
                AffirmationListItem(
                    affirmation = affirmation,
                    isFavorite = favoriteAffirmations.contains(affirmation),
                    onFavoriteToggle = { onFavoriteToggle(affirmation) }
                )
            }
        }
    }
}

@Composable
fun AffirmationListItem(
    affirmation: Affirmation,
    isFavorite: Boolean,
    onFavoriteToggle: (Affirmation) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                affirmation.category.icon,
                contentDescription = null,
                tint = affirmation.category.color,
                modifier = Modifier.size(24.dp)
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Text(
                text = affirmation.text,
                fontSize = 16.sp,
                modifier = Modifier.weight(1f)
            )
            
            IconButton(
                onClick = { onFavoriteToggle(affirmation) }
            ) {
                Icon(
                    if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
                    tint = if (isFavorite) Color(0xFFE91E63) else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

fun generateAffirmations(): List<Affirmation> {
    return listOf(
        // Self-Love Affirmations
        Affirmation("1", "I am worthy of love and respect.", AffirmationCategory.SELF_LOVE),
        Affirmation("2", "I accept myself completely as I am.", AffirmationCategory.SELF_LOVE),
        Affirmation("3", "I am enough, just as I am.", AffirmationCategory.SELF_LOVE),
        Affirmation("4", "I treat myself with kindness and compassion.", AffirmationCategory.SELF_LOVE),
        
        // Confidence Affirmations
        Affirmation("5", "I believe in my abilities and trust my judgment.", AffirmationCategory.CONFIDENCE),
        Affirmation("6", "I am capable of achieving my goals.", AffirmationCategory.CONFIDENCE),
        Affirmation("7", "I face challenges with courage and determination.", AffirmationCategory.CONFIDENCE),
        Affirmation("8", "I am confident in my unique gifts and talents.", AffirmationCategory.CONFIDENCE),
        
        // Peace Affirmations
        Affirmation("9", "I choose peace over worry and anxiety.", AffirmationCategory.PEACE),
        Affirmation("10", "I am calm and centered in this moment.", AffirmationCategory.PEACE),
        Affirmation("11", "I release what I cannot control and focus on what I can.", AffirmationCategory.PEACE),
        Affirmation("12", "I find peace within myself.", AffirmationCategory.PEACE),
        
        // Success Affirmations
        Affirmation("13", "I am successful in all areas of my life.", AffirmationCategory.SUCCESS),
        Affirmation("14", "I attract opportunities that align with my goals.", AffirmationCategory.SUCCESS),
        Affirmation("15", "I am persistent and never give up on my dreams.", AffirmationCategory.SUCCESS),
        Affirmation("16", "I celebrate my achievements, big and small.", AffirmationCategory.SUCCESS),
        
        // Gratitude Affirmations
        Affirmation("17", "I am grateful for all the blessings in my life.", AffirmationCategory.GRATITUDE),
        Affirmation("18", "I appreciate the small moments of joy each day.", AffirmationCategory.GRATITUDE),
        Affirmation("19", "I find something positive in every situation.", AffirmationCategory.GRATITUDE),
        Affirmation("20", "I am thankful for my growth and learning.", AffirmationCategory.GRATITUDE),
        
        // Strength Affirmations
        Affirmation("21", "I am stronger than I think and braver than I feel.", AffirmationCategory.STRENGTH),
        Affirmation("22", "I overcome obstacles with resilience and determination.", AffirmationCategory.STRENGTH),
        Affirmation("23", "I have the power to create positive change.", AffirmationCategory.STRENGTH),
        Affirmation("24", "I am resilient and bounce back from setbacks.", AffirmationCategory.STRENGTH),
        
        // Healing Affirmations
        Affirmation("25", "I am healing and growing every day.", AffirmationCategory.HEALING),
        Affirmation("26", "I release past pain and embrace my future.", AffirmationCategory.HEALING),
        Affirmation("27", "I am worthy of healing and happiness.", AffirmationCategory.HEALING),
        Affirmation("28", "I trust in my body's ability to heal.", AffirmationCategory.HEALING),
        
        // Mindfulness Affirmations
        Affirmation("29", "I am present in this moment and fully aware.", AffirmationCategory.MINDFULNESS),
        Affirmation("30", "I observe my thoughts without judgment.", AffirmationCategory.MINDFULNESS),
        Affirmation("31", "I breathe deeply and find calm within.", AffirmationCategory.MINDFULNESS),
        Affirmation("32", "I am mindful of my actions and their impact.", AffirmationCategory.MINDFULNESS)
    )
}
