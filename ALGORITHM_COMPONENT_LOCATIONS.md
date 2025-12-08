# Algorithm Component Locations in Code

This document identifies where algorithm components are located in the codebase for the last 3 use cases (UC34, UC39, UC40) and the Greedy Algorithm (UC41).

---

## 1. UC41: Greedy Algorithm Component

### Primary Algorithm Implementation

**File**: `app/src/main/java/com/serenityai/utils/GreedyCopingStrategySelector.kt`

**Core Algorithm Location**: Lines 39-76

```kotlin
fun selectOptimalStrategies(
    availableExercises: List<CopingExercise>,
    constraints: UserConstraints
): List<CopingExercise> {
    // Step 1: Score all exercises using greedy criteria
    val scoredExercises = availableExercises.map { exercise ->
        calculateGreedyScore(exercise, constraints)  // Line 47
    }
    
    // Step 2: Sort by score (descending) - Greedy choice
    val sortedExercises = scoredExercises.sortedByDescending { it.score }  // Line 51
    
    // Step 3: Greedy selection - select exercises until budget exhausted
    // Lines 54-73
}
```

**Key Algorithm Functions**:

1. **Greedy Score Calculation** (Lines 82-108)
   - File: `app/src/main/java/com/serenityai/utils/GreedyCopingStrategySelector.kt`
   - Function: `calculateGreedyScore()`
   - Formula: `score = (effectiveness × matchQuality) / (effort × timeCost)`

2. **Match Quality Calculation** (Lines 139-166)
   - File: `app/src/main/java/com/serenityai/utils/GreedyCopingStrategySelector.kt`
   - Function: `calculateMatchQuality()`
   - Algorithm: Contextual matching based on mood, stress, past success

3. **Effort Calculation** (Lines 114-121)
   - File: `app/src/main/java/com/serenityai/utils/GreedyCopingStrategySelector.kt`
   - Function: `calculateEffort()`
   - Maps difficulty to effort score

### Use Case Wrapper

**File**: `app/src/main/java/com/serenityai/usecases/GreedyAlgorithmUseCase.kt`

**Purpose**: High-level API wrapper that delegates to the algorithm implementation

**Key Methods**:
- `selectOptimalStrategies()` - Line 14-24 (delegates to `greedySelector.selectOptimalStrategies()`)
- `getTopRecommendations()` - Line 27-36
- `calculateTotalBenefit()` - Line 39-41
- `getAlgorithmStats()` - Line 76-101

---

## 2. UC34: Group Therapy Simulation Mode

### Algorithm Components

**File**: `app/src/main/java/com/serenityai/usecases/GroupTherapySimulationModeUseCase.kt`

#### Virtual Participant Generation Algorithm

**Location**: Lines 113-135

```kotlin
fun createVirtualParticipants(
    sessionId: String,
    participantCount: Int = 5
): List<VirtualParticipant> {
    val personalities = listOf("supportive", "analytical", "empathetic", "encouraging", "practical")
    val experiences = listOf("experienced", "moderate", "beginner")
    val responseStyles = listOf("concise", "detailed", "storytelling")
    
    // Algorithm: Round-robin distribution of personality types
    val participants = (1..participantCount).map { index ->
        VirtualParticipant(
            personality = personalities[index % personalities.size],  // Line 127
            experience = experiences[index % experiences.size],      // Line 128
            responseStyle = responseStyles[index % responseStyles.size]  // Line 129
        )
    }
}
```

**Algorithm Type**: Round-robin distribution algorithm for balanced personality assignment

#### Group Dynamics Simulation Algorithm

**Location**: Lines 214-237

```kotlin
fun simulateGroupDynamics(sessionId: String, currentTopic: String): Map<String, Any> {
    // Algorithm: Calculate participation level based on group size
    val participationLevel = when {
        session?.participants?.size ?: 0 >= 5 -> "high"      // Line 219
        session?.participants?.size ?: 0 >= 3 -> "moderate"   // Line 220
        else -> "low"                                         // Line 221
    }
    
    // Algorithm: Calculate group cohesion based on virtual participants
    val groupCohesion = when {
        participants.size >= 4 -> 0.85f                      // Line 225
        participants.size >= 2 -> 0.70f                      // Line 226
        else -> 0.50f                                        // Line 227
    }
    
    // Algorithm: Calculate engagement score
    val engagementScore = (participationLevel.length * 15 + groupCohesion * 50).toInt()  // Line 235
}
```

**Algorithm Type**: Threshold-based classification algorithm with weighted scoring

#### Response Generation Algorithm

**Location**: Lines 193-205

```kotlin
fun generateParticipantResponses(sessionId: String, topic: String): List<String> {
    val participants = virtualParticipants[sessionId] ?: emptyList()
    
    // Algorithm: Personality-based response generation
    return participants.mapIndexed { index, participant ->
        when (participant.personality) {  // Line 197
            "supportive" -> "I understand what you're going through..."
            "analytical" -> "Let's think about $topic from different perspectives..."
            "empathetic" -> "That sounds really difficult..."
            "encouraging" -> "You're making progress..."
            else -> "Thank you for sharing..."
        }
    }
}
```

**Algorithm Type**: Rule-based response generation based on personality mapping

---

## 3. UC39: Community Support Circles

### Algorithm Components

**File**: `app/src/main/java/com/serenityai/usecases/CommunitySupportCirclesUseCase.kt`

#### Unique ID Generation Algorithm

**Location**: Line 41

```kotlin
fun createSupportCircle(...): SupportCircle {
    val circle = SupportCircle(
        id = "circle_${System.currentTimeMillis()}_${++circleIdCounter}",  // Line 41
        // ...
    )
}
```

**Algorithm Type**: Timestamp + counter-based unique ID generation
**Purpose**: Ensures unique circle IDs even when created in rapid succession

#### Circle Filtering Algorithm

**Location**: Lines 318-328

```kotlin
fun getAvailableCircles(topic: String? = null): List<SupportCircle> {
    // Algorithm: Filter public circles
    val publicCircles = supportCircles.values.filter { 
        !it.isPrivate && it.status == CircleStatus.ACTIVE   // Line 320
    }
    
    // Algorithm: Optional topic-based filtering
    return if (topic != null) {
        publicCircles.filter { 
            it.topic?.equals(topic, ignoreCase = true) == true  // Line 324
        }
    } else {
        publicCircles
    }
}
```

**Algorithm Type**: Filtering algorithm with optional topic matching

#### Membership Management Algorithm

**Location**: Lines 75-116

```kotlin
fun joinSupportCircle(circleId: String, userId: String): Boolean {
    val circle = supportCircles[circleId] ?: return false
    
    // Algorithm: Check circle status
    if (circle.status != CircleStatus.ACTIVE) {  // Line 78
        return false
    }
    
    // Algorithm: Private circle invitation check
    if (circle.isPrivate) {  // Line 82
        val invitations = circleInvitations[userId] ?: return false
        if (!invitations.contains(circleId)) {  // Line 85
            return false
        }
        invitations.remove(circleId)  // Line 88
    }
    
    // Algorithm: Capacity check
    val members = circleMembers[circleId] ?: mutableSetOf()
    if (members.size >= circle.maxMembers) {  // Line 92
        return false
    }
    
    // Algorithm: Duplicate check
    if (members.contains(userId)) {  // Line 96
        return true
    }
    
    // Algorithm: Add member and update counts
    members.add(userId)  // Line 100
    // ... update logic
}
```

**Algorithm Type**: Multi-step validation and state management algorithm

#### Post/Comment Sorting Algorithm

**Location**: Lines 346-348

```kotlin
fun getCirclePosts(circleId: String): List<CirclePost> {
    // Algorithm: Sort posts by creation date (newest first)
    return circlePosts[circleId]?.sortedByDescending { it.createdAt } ?: emptyList()  // Line 347
}
```

**Location**: Lines 356-358

```kotlin
fun getPostComments(postId: String): List<CircleComment> {
    // Algorithm: Sort comments by creation date (oldest first)
    return circleComments[postId]?.sortedBy { it.createdAt } ?: emptyList()  // Line 357
}
```

**Algorithm Type**: Sorting algorithm (descending for posts, ascending for comments)

---

## 4. UC40: Religious Support by Person's Religion

### Algorithm Components

**File**: `app/src/main/java/com/serenityai/usecases/ReligiousSupportUseCase.kt`

#### Religious Guidance Generation Algorithm

**Location**: Lines 177-196

```kotlin
private fun generateGuidance(religion: Religion, context: String): SpiritualGuidance {
    // Algorithm: Religion-specific guidance generation
    val guidanceText = when (religion) {  // Line 178
        Religion.CHRISTIANITY -> generateChristianGuidance(context)
        Religion.ISLAM -> generateIslamicGuidance(context)
        Religion.JUDAISM -> generateJewishGuidance(context)
        Religion.BUDDHISM -> generateBuddhistGuidance(context)
        Religion.HINDUISM -> generateHinduGuidance(context)
        Religion.SIKHISM -> generateSikhGuidance(context)
        Religion.OTHER -> generateGenericGuidance(context)
        Religion.NONE -> throw IllegalArgumentException("Invalid religion")
    }
    
    return SpiritualGuidance(
        id = "guidance_${System.currentTimeMillis()}",  // Line 190
        // ...
    )
}
```

**Algorithm Type**: Rule-based template generation with religion-specific logic

#### Daily Reflection Selection Algorithm

**Location**: Lines 143-157

```kotlin
fun getDailyReflection(userId: String): String {
    val religion = userReligiousPreferences[userId] 
        ?: throw IllegalArgumentException("User religious preference not set")
    
    // Algorithm: Religion-specific reflection selection
    return when (religion) {  // Line 147
        Religion.CHRISTIANITY -> "Today, remember that you are loved..."
        Religion.ISLAM -> "As-Salamu Alaykum. Today, seek peace..."
        Religion.JUDAISM -> "Shalom. Today, reflect on gratitude..."
        Religion.BUDDHISM -> "Today, practice mindfulness..."
        Religion.HINDUISM -> "Namaste. Today, honor the divine..."
        Religion.SIKHISM -> "Sat Sri Akal. Today, remember Waheguru's..."
        Religion.OTHER -> "Today, connect with your spiritual beliefs..."
        Religion.NONE -> throw IllegalArgumentException("User has no religious preference set")
    }
}
```

**Algorithm Type**: Lookup table algorithm with religion-based selection

#### Quote Selection Algorithm

**Location**: Lines 166-172

```kotlin
fun getReligiousQuote(userId: String, topic: String? = null): String {
    val religion = userReligiousPreferences[userId] 
        ?: throw IllegalArgumentException("User religious preference not set")
    
    val quotes = getQuotesForReligion(religion, topic)  // Line 170
    return quotes.random()  // Line 171 - Random selection algorithm
}
```

**Location**: Lines 240-285

```kotlin
private fun getQuotesForReligion(religion: Religion, topic: String?): List<String> {
    val quotes = when (religion) {  // Line 241
        Religion.CHRISTIANITY -> listOf("For I know the plans...", ...)
        Religion.ISLAM -> listOf("And whoever relies upon Allah...", ...)
        // ... more religions
    }
    
    // Algorithm: Optional topic-based filtering
    return if (topic != null) {
        quotes.filter { it.contains(topic, ignoreCase = true) }  // Line 281
    } else {
        quotes
    }
}
```

**Algorithm Type**: Random selection with optional topic filtering

#### Resource Filtering Algorithm

**Location**: Lines 69-80

```kotlin
fun getReligiousResources(userId: String, category: ResourceCategory? = null): List<ReligiousResource> {
    val religion = userReligiousPreferences[userId] 
        ?: throw IllegalArgumentException("User religious preference not set")
    
    val resources = religiousResources[religion] ?: emptyList()  // Line 73
    
    // Algorithm: Optional category-based filtering
    return if (category != null) {
        resources.filter { it.category == category }  // Line 76
    } else {
        resources
    }
}
```

**Algorithm Type**: Filtering algorithm with optional category matching

---

## Summary Table

| Use Case | Algorithm Component | File Location | Lines | Algorithm Type |
|----------|-------------------|---------------|-------|----------------|
| **UC41** | Greedy Selection | `utils/GreedyCopingStrategySelector.kt` | 39-76 | Greedy Algorithm |
| **UC41** | Score Calculation | `utils/GreedyCopingStrategySelector.kt` | 82-108 | Scoring Algorithm |
| **UC41** | Match Quality | `utils/GreedyCopingStrategySelector.kt` | 139-166 | Matching Algorithm |
| **UC34** | Virtual Participant Generation | `usecases/GroupTherapySimulationModeUseCase.kt` | 113-135 | Round-robin Distribution |
| **UC34** | Group Dynamics Simulation | `usecases/GroupTherapySimulationModeUseCase.kt` | 214-237 | Threshold-based Classification |
| **UC34** | Response Generation | `usecases/GroupTherapySimulationModeUseCase.kt` | 193-205 | Rule-based Generation |
| **UC39** | Unique ID Generation | `usecases/CommunitySupportCirclesUseCase.kt` | 41 | Timestamp + Counter |
| **UC39** | Circle Filtering | `usecases/CommunitySupportCirclesUseCase.kt` | 318-328 | Filtering Algorithm |
| **UC39** | Membership Management | `usecases/CommunitySupportCirclesUseCase.kt` | 75-116 | Multi-step Validation |
| **UC39** | Post/Comment Sorting | `usecases/CommunitySupportCirclesUseCase.kt` | 346-358 | Sorting Algorithm |
| **UC40** | Guidance Generation | `usecases/ReligiousSupportUseCase.kt` | 177-196 | Rule-based Template |
| **UC40** | Daily Reflection | `usecases/ReligiousSupportUseCase.kt` | 143-157 | Lookup Table |
| **UC40** | Quote Selection | `usecases/ReligiousSupportUseCase.kt` | 166-172, 240-285 | Random Selection + Filtering |
| **UC40** | Resource Filtering | `usecases/ReligiousSupportUseCase.kt` | 69-80 | Filtering Algorithm |

---

## Key Algorithm Files

### Primary Algorithm Files

1. **`app/src/main/java/com/serenityai/utils/GreedyCopingStrategySelector.kt`**
   - **Purpose**: Core greedy algorithm implementation
   - **Lines**: 1-234
   - **Main Algorithm**: Lines 39-76 (greedy selection)

2. **`app/src/main/java/com/serenityai/usecases/GreedyAlgorithmUseCase.kt`**
   - **Purpose**: Use case wrapper for greedy algorithm
   - **Lines**: 1-113
   - **Delegates to**: `GreedyCopingStrategySelector`

### Use Case Files with Embedded Algorithms

3. **`app/src/main/java/com/serenityai/usecases/GroupTherapySimulationModeUseCase.kt`**
   - **Algorithms**: Virtual participant generation, group dynamics, response generation
   - **Total Lines**: 386

4. **`app/src/main/java/com/serenityai/usecases/CommunitySupportCirclesUseCase.kt`**
   - **Algorithms**: ID generation, filtering, membership management, sorting
   - **Total Lines**: 420

5. **`app/src/main/java/com/serenityai/usecases/ReligiousSupportUseCase.kt`**
   - **Algorithms**: Guidance generation, reflection selection, quote selection, filtering
   - **Total Lines**: 470

---

## Algorithm Complexity Analysis

| Algorithm | Time Complexity | Space Complexity | Notes |
|-----------|----------------|------------------|-------|
| Greedy Selection (UC41) | O(n log n) | O(n) | Dominated by sorting |
| Virtual Participant Generation (UC34) | O(n) | O(n) | Linear generation |
| Group Dynamics Simulation (UC34) | O(1) | O(1) | Constant time calculation |
| Circle Filtering (UC39) | O(n) | O(n) | Linear filtering |
| Membership Management (UC39) | O(1) | O(1) | Hash map lookups |
| Guidance Generation (UC40) | O(1) | O(1) | Template lookup |
| Quote Selection (UC40) | O(n) | O(n) | Filtering + random selection |

---

**Document Generated**: December 2024  
**Last Updated**: December 2024  
**Status**: Complete


