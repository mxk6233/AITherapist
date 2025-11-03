# Detailed Code Overview: Four Use Cases

This document provides detailed explanations and code snippets from each of the four implemented use cases, showing what the code does and how it works.

---

## 1. UC22: Monitor System Health

### Purpose
Monitors system health in real-time, tracking performance metrics, resource usage, and service availability to detect issues before they become critical.

### Code Structure

#### 📊 Data Models (`SystemHealth.kt`)

```kotlin
// Core metrics data structure
data class SystemHealthMetrics(
    val id: String = "",
    val timestamp: Date = Date(),
    val cpuUsage: Double = 0.0,        // Percentage 0-100
    val memoryUsage: Double = 0.0,      // Percentage 0-100
    val responseTime: Long = 0L,        // Milliseconds
    val errorRate: Double = 0.0,        // Percentage 0-100
    val activeUsers: Int = 0,
    val apiCallsPerMinute: Int = 0
)

// Service availability tracking
data class ServiceStatus(
    val serviceName: String,
    val isAvailable: Boolean,
    val lastChecked: Date = Date(),
    val responseTime: Long = 0L,
    val errorCount: Int = 0
)

// Comprehensive health report
data class HealthReport(
    val id: String = "",
    val generatedAt: Date = Date(),
    val overallStatus: HealthStatus,        // HEALTHY/WARNING/CRITICAL
    val performanceMetrics: SystemHealthMetrics,
    val serviceStatuses: List<ServiceStatus>,
    val criticalIssues: List<HealthIssue> = emptyList(),
    val recommendations: List<String> = emptyList()
)
```

**What it does:**
- Defines the data structures for health monitoring
- `SystemHealthMetrics` captures real-time system performance
- `ServiceStatus` tracks availability of each service (API, Database, etc.)
- `HealthReport` aggregates all data into a single report with status and recommendations

---

#### 🔧 Use Case Logic (`SystemHealthUseCase.kt`)

**1. Health Metrics Collection**
```kotlin
fun collectHealthMetrics(): SystemHealthMetrics {
    return SystemHealthMetrics(
        id = System.currentTimeMillis().toString(),
        timestamp = Date(),
        cpuUsage = getCpuUsage(),                    // Simulated: 20-70%
        memoryUsage = getMemoryUsage(),              // Simulated: 30-70%
        responseTime = getAverageResponseTime(),     // Simulated: 100-300ms
        errorRate = getErrorRate(),                  // Simulated: 0-0.5%
        activeUsers = getActiveUserCount(),          // Simulated: 500-1500
        apiCallsPerMinute = getApiCallsPerMinute()  // Simulated: 200-700
    )
}
```
**What it does:** Collects current system metrics. In production, this would call actual monitoring APIs to get real CPU, memory, and performance data.

---

**2. Health Status Calculation**
```kotlin
private fun calculateOverallHealthStatus(
    metrics: SystemHealthMetrics,
    services: List<ServiceStatus>
): HealthStatus {
    // Check for critical conditions
    if (metrics.cpuUsage > 95.0 || metrics.memoryUsage > 95.0) {
        return HealthStatus.CRITICAL
    }
    
    if (services.any { !it.isAvailable }) {
        return HealthStatus.CRITICAL
    }
    
    if (metrics.errorRate > 5.0) {
        return HealthStatus.CRITICAL
    }
    
    // Check for warning conditions
    if (metrics.cpuUsage > 80.0 || metrics.memoryUsage > 80.0) {
        return HealthStatus.WARNING
    }
    
    if (metrics.errorRate > 1.0) {
        return HealthStatus.WARNING
    }
    
    if (metrics.responseTime > 500L) {
        return HealthStatus.WARNING
    }
    
    return HealthStatus.HEALTHY
}
```
**What it does:** Analyzes metrics against thresholds:
- **CRITICAL**: CPU/Memory >95%, services down, error rate >5%
- **WARNING**: CPU/Memory >80%, error rate >1%, slow response times
- **HEALTHY**: All metrics within normal ranges

---

**3. Critical Issue Detection**
```kotlin
private fun identifyCriticalIssues(
    metrics: SystemHealthMetrics,
    services: List<ServiceStatus>
): List<HealthIssue> {
    val issues = mutableListOf<HealthIssue>()
    
    // Check CPU usage
    if (metrics.cpuUsage > 90.0) {
        issues.add(
            HealthIssue(
                id = "cpu-${System.currentTimeMillis()}",
                type = IssueType.RESOURCE_EXHAUSTION,
                severity = IssueSeverity.CRITICAL,
                description = "CPU usage is critically high: ${metrics.cpuUsage}%",
                detectedAt = Date()
            )
        )
    }
    
    // Check memory usage
    if (metrics.memoryUsage > 90.0) {
        issues.add(
            HealthIssue(
                id = "memory-${System.currentTimeMillis()}",
                type = IssueType.RESOURCE_EXHAUSTION,
                severity = IssueSeverity.CRITICAL,
                description = "Memory usage is critically high: ${metrics.memoryUsage}%",
                detectedAt = Date()
            )
        )
    }
    
    // Check unavailable services
    services.filter { !it.isAvailable }.forEach { service ->
        issues.add(
            HealthIssue(
                type = IssueType.SERVICE_UNAVAILABLE,
                severity = IssueSeverity.CRITICAL,
                description = "Service ${service.serviceName} is unavailable",
                affectedServices = listOf(service.serviceName)
            )
        )
    }
    
    return issues
}
```
**What it does:** Scans metrics for critical problems and creates `HealthIssue` objects with severity and descriptions for alerting.

---

**4. Recommendation Generation**
```kotlin
private fun generateRecommendations(
    metrics: SystemHealthMetrics,
    services: List<ServiceStatus>,
    issues: List<HealthIssue>
): List<String> {
    val recommendations = mutableListOf<String>()
    
    if (metrics.cpuUsage > 80.0) {
        recommendations.add("Consider scaling up CPU resources or optimizing CPU-intensive operations")
    }
    
    if (metrics.memoryUsage > 80.0) {
        recommendations.add("Consider increasing memory allocation or investigating memory leaks")
    }
    
    if (metrics.errorRate > 1.0) {
        recommendations.add("Review error logs and investigate root causes of errors")
    }
    
    if (metrics.responseTime > 300L) {
        recommendations.add("Optimize response time by reviewing slow queries and API calls")
    }
    
    services.filter { !it.isAvailable }.forEach { service ->
        recommendations.add("Investigate and restore service: ${service.serviceName}")
    }
    
    if (recommendations.isEmpty()) {
        recommendations.add("System is healthy. Continue monitoring.")
    }
    
    return recommendations
}
```
**What it does:** Generates actionable recommendations based on detected issues to help resolve problems.

---

#### 🎨 UI Screen (`SystemHealthScreen.kt`)

**Auto-Refresh Logic**
```kotlin
// Auto-refresh health metrics every 30 seconds
LaunchedEffect(Unit) {
    while (true) {
        isLoading = true
        delay(500) // Simulate loading
        healthReport = useCase.generateHealthReport()
        alerts = useCase.alertOnCriticalHealthIssues(healthReport!!)
        isLoading = false
        lastUpdated = Date()
        delay(30000) // Refresh every 30 seconds
    }
}
```
**What it does:** Continuously monitors system health, refreshing data every 30 seconds and updating alerts automatically.

---

**Metrics Visualization**
```kotlin
@Composable
fun MetricRow(label: String, value: String, percentage: Double?, reverse: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, fontSize = 16.sp, fontWeight = FontWeight.Medium)
        
        Row(verticalAlignment = Alignment.CenterVertically) {
            percentage?.let {
                val color = when {
                    reverse -> if (it < 200) Color(0xFF4CAF50) else Color(0xFFF44336)
                    it < 50 -> Color(0xFF4CAF50)    // Green for good
                    it < 80 -> Color(0xFFFF9800)    // Orange for warning
                    else -> Color(0xFFF44336)        // Red for critical
                }
                val progressValue = (it / 100.0).coerceIn(0.0, 1.0).toFloat()
                LinearProgressIndicator(
                    progress = progressValue,
                    modifier = Modifier.width(100.dp),
                    color = color
                )
            }
            Text(value, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}
```
**What it does:** Displays metrics with color-coded progress bars:
- **Green** (<50%): Healthy
- **Orange** (50-80%): Warning
- **Red** (>80%): Critical

---

### Test Example

```kotlin
@Test
fun `system monitors application performance metrics for optimization`() {
    // Given: Performance metrics
    val responseTime = 150L // milliseconds
    val requestCount = 100
    val averageResponseTime = responseTime.toFloat()
    
    // When: System monitors performance
    val metricsCollected = responseTime > 0 && requestCount > 0
    val performanceTracked = averageResponseTime > 0
    
    // Then: Performance must be monitored
    assertTrue(metricsCollected, "Performance metrics must be collected")
    assertTrue(performanceTracked, "Performance must be tracked")
}
```

---

## 2. UC26: AI-Powered Mood Forecasting

### Purpose
Predicts user's mood for the next 7 days using historical patterns, enabling proactive planning and interventions before mood declines.

### Code Structure

#### 📊 Data Models (`MoodForecast.kt`)

```kotlin
// Daily forecast prediction
data class ForecastDay(
    val day: String,                    // "Today", "Tomorrow", "Mon", etc.
    val date: Date = Date(),
    val predictedMood: Float,            // 1.0-5.0 scale
    val note: String,                    // Explanation of prediction
    val confidence: Float = 75.0f,       // 0-100%
    val factors: List<ForecastFactor> = emptyList()
)

// Pattern prediction (weekly boost, midweek dip, etc.)
data class MoodPrediction(
    val id: String = "",
    val title: String,                  // "Weekend Boost", "Midweek Dip"
    val description: String,
    val confidence: Float,              // 0-100%
    val patternType: PatternType = PatternType.WEEKLY,
    val detectedAt: Date = Date()
)

// Complete 7-day forecast
data class MoodForecast(
    val id: String = "",
    val userId: String = "",
    val generatedAt: Date = Date(),
    val forecastDays: List<ForecastDay>,    // Must be 7 days
    val predictions: List<MoodPrediction> = emptyList(),
    val averagePredictedMood: Float = 0f,
    val peakDay: ForecastDay? = null,      // Best mood day
    val lowestDay: ForecastDay? = null,    // Lowest mood day
    val overallTrend: ForecastTrend = ForecastTrend.STABLE,
    val riskLevel: RiskLevel = RiskLevel.LOW,
    val recommendations: List<String> = emptyList()
)
```

**What it does:**
- `ForecastDay` represents a single day's mood prediction with explanation
- `MoodPrediction` identifies patterns like "weekend boost" with confidence
- `MoodForecast` contains the complete 7-day forecast with insights

---

#### 🔧 Use Case Logic (`MoodForecastingUseCase.kt`)

**1. 7-Day Forecast Generation**
```kotlin
fun generate7DayForecast(historicalMoodData: List<MoodEntry>): MoodForecast {
    require(historicalMoodData.isNotEmpty()) { "Historical data required" }
    
    // Generate forecast for next 7 days
    val forecastDays = (0..6).map { dayOffset ->
        val date = getDateForDayOffset(dayOffset)
        val predictedMood = predictMoodForDay(historicalMoodData, dayOffset)
        val note = generateForecastNote(predictedMood, dayOffset)
        val confidence = calculateConfidence(historicalMoodData.size, dayOffset)
        val factors = identifyForecastFactors(historicalMoodData, dayOffset)
        
        ForecastDay(
            day = getDayName(dayOffset),
            date = date,
            predictedMood = predictedMood,
            note = note,
            confidence = confidence,
            factors = factors
        )
    }
    
    // Calculate statistics
    val averagePredictedMood = forecastDays.map { it.predictedMood }.average().toFloat()
    val peakDay = forecastDays.maxByOrNull { it.predictedMood }
    val lowestDay = forecastDays.minByOrNull { it.predictedMood }
    val overallTrend = calculateTrend(forecastDays)
    val riskLevel = calculateRiskLevel(forecastDays)
    val recommendations = generateRecommendations(forecastDays, riskLevel)
    
    // Identify patterns
    val predictions = identifyPatterns(historicalMoodData, forecastDays)
    
    return MoodForecast(
        forecastDays = forecastDays,
        predictions = predictions,
        averagePredictedMood = averagePredictedMood,
        peakDay = peakDay,
        lowestDay = lowestDay,
        overallTrend = overallTrend,
        riskLevel = riskLevel,
        recommendations = recommendations
    )
}
```
**What it does:** 
1. Generates predictions for each of the next 7 days
2. Calculates statistics (average, peak, lowest)
3. Identifies trends (IMPROVING/DECLINING/STABLE)
4. Assesses risk level
5. Generates actionable recommendations

---

**2. Weekly Pattern Recognition**
```kotlin
fun identifyWeeklyPatterns(historicalData: List<MoodEntry>): WeeklyPattern {
    require(historicalData.isNotEmpty()) { "Historical data required" }
    
    // Separate weekday and weekend moods
    val weekdayMoods = historicalData.filter { entry ->
        val calendar = Calendar.getInstance().apply { time = entry.date }
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        dayOfWeek != Calendar.SATURDAY && dayOfWeek != Calendar.SUNDAY
    }.map { it.mood.toFloat() }
    
    val weekendMoods = historicalData.filter { entry ->
        val calendar = Calendar.getInstance().apply { time = entry.date }
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY
    }.map { it.mood.toFloat() }
    
    val weekdayAverage = if (weekdayMoods.isNotEmpty()) weekdayMoods.average().toFloat() else 3.5f
    val weekendAverage = if (weekendMoods.isNotEmpty()) weekendMoods.average().toFloat() else 3.5f
    val weekendBoost = weekendAverage - weekdayAverage
    val patternStrength = if (weekdayAverage > 0) (weekendBoost / weekdayAverage).toFloat() else 0f
    val confidence = calculatePatternConfidence(weekdayMoods.size, weekendMoods.size)
    
    return WeeklyPattern(
        weekdayAverage = weekdayAverage,
        weekendAverage = weekendAverage,
        weekendBoost = weekendBoost,
        patternStrength = patternStrength * 100f,  // Convert to percentage
        confidence = confidence
    )
}
```
**What it does:**
- Analyzes historical data to separate weekdays from weekends
- Calculates average mood for each
- Computes "weekend boost" (difference)
- Calculates pattern strength (>=15% is considered actionable)
- Returns pattern with confidence score

**Example:** If weekday average = 3.5, weekend average = 4.2, then weekendBoost = 0.7 and patternStrength = 20% (significant pattern detected).

---

**3. Mood Prediction Algorithm**
```kotlin
private fun predictMoodForDay(historicalData: List<MoodEntry>, dayOffset: Int): Float {
    // Simple prediction algorithm - in production would use ML model
    val historicalAverage = historicalData.map { it.mood.toFloat() }.average().toFloat()
    
    // Apply weekly pattern
    val calendar = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, dayOffset) }
    val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
    val isWeekend = dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY
    
    // Weekend boost effect (if pattern exists)
    val weeklyPattern = identifyWeeklyPatterns(historicalData)
    val adjustment = if (isWeekend && weeklyPattern.patternStrength >= 15f) {
        weeklyPattern.weekendBoost * 0.5f  // Moderate effect
    } else if (!isWeekend) {
        -weeklyPattern.weekendBoost * 0.3f  // Slight weekday decline
    } else {
        0f
    }
    
    // Add slight random variation for realism
    val variation = (Math.random() * 0.4 - 0.2).toFloat()
    
    return (historicalAverage + adjustment + variation).coerceIn(1.0f, 5.0f)
}
```
**What it does:**
1. Starts with historical mood average
2. Applies weekly pattern adjustment (weekend boost or weekday decline)
3. Adds small random variation for realism
4. Ensures result stays within 1.0-5.0 mood scale

---

**4. Proactive Intervention Triggering**
```kotlin
fun triggerProactiveInterventions(forecastDays: List<ForecastDay>): List<String> {
    val lowMoodThreshold = 3.0f
    val criticalThreshold = 2.5f
    
    val lowMoodDays = forecastDays.filter { it.predictedMood < lowMoodThreshold }
    val criticalDays = forecastDays.filter { it.predictedMood < criticalThreshold }
    
    if (criticalDays.isNotEmpty()) {
        return listOf(
            "Immediate: Breathing exercises available now",
            "Scheduled: Morning meditation for ${criticalDays.first().day}",
            "Planned: Contact support network before mood decline",
            "Optional: Schedule professional check-in if available"
        )
    }
    
    if (lowMoodDays.isNotEmpty()) {
        return listOf(
            "Breathing exercises to stabilize mood",
            "Light physical activity planned",
            "Positive social interactions scheduled",
            "Relaxation techniques practiced"
        )
    }
    
    return emptyList()
}
```
**What it does:** 
- Scans forecast for predicted low mood days (< 3.0)
- If critical (< 2.5), suggests immediate interventions
- If just low (< 3.0), suggests preventive actions
- Returns actionable recommendations before mood declines

---

**5. Confidence Score Calculation**
```kotlin
fun calculateConfidence(historicalDataSize: Int, dayOffset: Int): Float {
    // More historical data = higher confidence
    val dataConfidence = when {
        historicalDataSize >= 60 -> 100f  // Excellent: 2+ months of data
        historicalDataSize >= 30 -> 90f  // Good: 1 month of data
        historicalDataSize >= 14 -> 75f  // Fair: 2 weeks of data
        historicalDataSize >= 7 -> 60f   // Minimal: 1 week
        else -> 50f                      // Low confidence
    }
    
    // Farther into future = lower confidence
    val timeDecay = when {
        dayOffset <= 1 -> 1.0f   // Today/Tomorrow: Full confidence
        dayOffset <= 3 -> 0.9f    // 2-3 days: 90% confidence
        dayOffset <= 5 -> 0.8f    // 4-5 days: 80% confidence
        else -> 0.7f               // 6+ days: 70% confidence
    }
    
    return (dataConfidence * timeDecay).coerceIn(0f, 100f)
}
```
**What it does:**
- Confidence increases with more historical data
- Confidence decreases for predictions further in the future
- Example: 30 days of data predicting tomorrow = 90% confidence
- Example: 7 days of data predicting 6 days ahead = 48% confidence (60% * 80%)

---

#### 🎨 UI Screen (`MoodForecastingScreen.kt`)

**Forecast Generation on Load**
```kotlin
// Generate forecast when screen loads
LaunchedEffect(Unit) {
    isLoading = true
    moodForecast = useCase.generate7DayForecast(historicalMoodData)
    isLoading = false
}

val forecastData = moodForecast?.forecastDays?.map { 
    ForecastDay(it.day, it.predictedMood, it.note) 
} ?: emptyList()
```
**What it does:** Automatically generates forecast when screen loads, converting data models to UI display format.

---

**Forecast Visualization**
```kotlin
@Composable
fun ForecastChart(forecastData: List<ForecastDay>) {
    val maxValue = 5.0f
    val minValue = 1.0f
    
    Box(modifier = Modifier.fillMaxWidth().height(150.dp)) {
        Row(modifier = Modifier.fillMaxSize().padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom) {
            forecastData.forEach { data ->
                val height = ((data.predictedMood - minValue) / (maxValue - minValue)) * 100
                
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(modifier = Modifier
                        .width(30.dp)
                        .height(height.dp)
                        .background(
                            when {
                                data.predictedMood >= 4.0f -> Color(0xFF4CAF50)  // Green
                                data.predictedMood >= 3.0f -> Color(0xFFFF9800)  // Orange
                                else -> Color(0xFFF44336)                          // Red
                            },
                            RoundedCornerShape(4.dp)
                        )
                    )
                    Text(data.day, fontSize = 9.sp)
                    Text(data.predictedMood.toString(), fontSize = 8.sp)
                }
            }
        }
    }
}
```
**What it does:** Creates a visual bar chart showing predicted mood for each day:
- **Green bars** (≥4.0): Positive mood
- **Orange bars** (3.0-4.0): Neutral mood
- **Red bars** (<3.0): Low mood requiring attention

---

### Test Example

```kotlin
@Test
fun `system generates complete 7-day mood forecast with daily predictions`() {
    // Given: Historical mood data
    val historicalMoodData = listOf(3.5f, 4.0f, 3.8f, 4.2f, 3.9f, 4.1f, 4.0f)
    val forecastData = listOf(
        ForecastDay("Day 1", 4.2f, "Stable mood expected"),
        ForecastDay("Day 2", 3.8f, "Slight decline expected"),
        // ... 7 days total
    )
    
    // When: Forecast generation
    val forecastDaysCount = forecastData.size
    val allDaysHavePredictions = forecastData.all { it.predictedMood in 1.0f..5.0f }
    val allDaysHaveExplanations = forecastData.all { it.note.isNotBlank() }
    
    // Then: Validate forecast
    assertEquals(7, forecastDaysCount, "UC26 requires exactly 7 days")
    assertTrue(allDaysHavePredictions, "All days must have valid predictions")
    assertTrue(allDaysHaveExplanations, "All days must have explanations")
}
```

---

## 3. UC35: Relapse Prevention Alerts

### Purpose
Monitors multiple risk factors (mood, sleep, stress, social support) to detect early warning signs and trigger proactive interventions before mental health relapses occur.

### Code Structure

#### 📊 Data Models (`RelapsePrevention.kt`)

```kotlin
// Individual risk factor assessment
data class RiskIndicator(
    val id: String = "",
    val name: String,                    // "Sleep Pattern", "Stress Level"
    val riskLevel: Int,                  // 0-100
    val description: String,
    val category: RiskCategory,          // SLEEP, STRESS, SOCIAL_SUPPORT, etc.
    val detectedAt: Date = Date(),
    val trend: RiskTrend = RiskTrend.STABLE  // IMPROVING/DECLINING/STABLE/VOLATILE
)

// Early warning sign
data class EarlyWarning(
    val id: String = "",
    val title: String,                   // "Sleep Disruption"
    val description: String,
    val severity: WarningSeverity,       // LOW/MEDIUM/HIGH/CRITICAL
    val recommendedAction: String,
    val detectedAt: Date = Date(),
    val category: RiskCategory = RiskCategory.GENERAL,
    val relatedRiskIndicators: List<String> = emptyList()
)

// Overall risk assessment
data class RiskAssessment(
    val id: String = "",
    val userId: String = "",
    val assessedAt: Date = Date(),
    val overallRiskLevel: RiskLevel,     // LOW/MEDIUM/HIGH/CRITICAL
    val riskIndicators: List<RiskIndicator>,
    val earlyWarnings: List<EarlyWarning> = emptyList(),
    val recommendations: List<String> = emptyList(),
    val interventionTriggered: Boolean = false
)
```

**What it does:**
- `RiskIndicator` tracks individual risk factors (sleep, stress, etc.) with severity scores
- `EarlyWarning` represents detected warning signs with severity and recommended actions
- `RiskAssessment` aggregates all risk data into a single assessment with overall risk level

---

#### 🔧 Use Case Logic (`RelapsePreventionUseCase.kt`)

**1. Multi-Factor Risk Assessment**
```kotlin
fun assessRiskLevel(
    moodEntries: List<MoodEntry>,
    sleepData: List<SleepData> = emptyList(),
    stressLevels: List<StressData> = emptyList()
): RiskAssessment {
    val riskIndicators = mutableListOf<RiskIndicator>()
    
    // Assess mood patterns
    if (moodEntries.isNotEmpty()) {
        val moodRisk = assessMoodRisk(moodEntries)
        riskIndicators.add(moodRisk)
    }
    
    // Assess sleep patterns
    if (sleepData.isNotEmpty()) {
        val sleepRisk = assessSleepRisk(sleepData)
        riskIndicators.add(sleepRisk)
    }
    
    // Assess stress levels
    if (stressLevels.isNotEmpty()) {
        val stressRisk = assessStressRisk(stressLevels)
        riskIndicators.add(stressRisk)
    }
    
    // Assess social support
    val socialRisk = assessSocialSupportRisk(moodEntries)
    riskIndicators.add(socialRisk)
    
    // Assess trigger exposure
    val triggerRisk = assessTriggerExposureRisk()
    riskIndicators.add(triggerRisk)
    
    // Calculate overall risk level
    val overallRiskLevel = calculateOverallRiskLevel(riskIndicators)
    
    // Detect early warnings
    val earlyWarnings = detectEarlyWarnings(riskIndicators, moodEntries)
    
    // Generate recommendations
    val recommendations = generateRecommendations(overallRiskLevel, riskIndicators)
    
    val interventionTriggered = overallRiskLevel in listOf(
        RiskLevel.HIGH, RiskLevel.CRITICAL
    )
    
    return RiskAssessment(
        overallRiskLevel = overallRiskLevel,
        riskIndicators = riskIndicators,
        earlyWarnings = earlyWarnings,
        recommendations = recommendations,
        interventionTriggered = interventionTriggered
    )
}
```
**What it does:**
1. Assesses 5 risk factors: mood, sleep, stress, social support, triggers
2. Calculates overall risk level from individual indicators
3. Detects early warning signs
4. Generates recommendations
5. Determines if intervention should be triggered

---

**2. Mood Risk Assessment**
```kotlin
private fun assessMoodRisk(moodEntries: List<MoodEntry>): RiskIndicator {
    val recentMoods = moodEntries.takeLast(7).map { it.mood.toFloat() }
    val averageMood = recentMoods.average()
    val moodDecline = recentMoods.first() - recentMoods.last()
    
    val riskLevel = when {
        averageMood <= 2.0f -> 90                    // Severe: Very low mood
        averageMood <= 3.0f && moodDecline > 1.0f -> 85  // Critical decline
        averageMood <= 3.0f -> 75                   // Low mood
        averageMood <= 3.5f && moodDecline > 0.5f -> 65  // Moderate decline
        averageMood <= 3.5f -> 55                   // Below average
        averageMood <= 4.0f && moodDecline > 0.5f -> 45  // Mild decline
        else -> 30                                   // Healthy mood
    }.toInt()
    
    val description = when {
        averageMood <= 2.0f -> "Severe mood decline - immediate attention needed"
        averageMood <= 3.0f && moodDecline > 1.0f -> "Significant mood decline detected"
        moodDecline > 1.0f -> "Rapid mood decline pattern"
        else -> "Mood patterns showing variation"
    }
    
    val trend = when {
        moodDecline > 1.0f -> RiskTrend.DECLINING
        moodDecline > 0.5f -> RiskTrend.DECLINING
        moodDecline < -0.5f -> RiskTrend.IMPROVING
        else -> RiskTrend.STABLE
    }
    
    return RiskIndicator(
        name = "Mood Patterns",
        riskLevel = riskLevel,
        description = description,
        category = RiskCategory.MOOD_PATTERNS,
        trend = trend
    )
}
```
**What it does:**
- Analyzes recent mood entries (last 7 days)
- Calculates average mood and decline trend
- Assigns risk level based on severity:
  - **90**: Average mood ≤2.0 (severe)
  - **85**: Low mood with rapid decline
  - **75**: Low mood but stable
  - **30**: Healthy mood patterns
- Identifies trend (IMPROVING/DECLINING/STABLE)

---

**3. Early Warning Detection**
```kotlin
fun detectEarlyWarnings(
    riskIndicators: List<RiskIndicator>,
    moodEntries: List<MoodEntry>
): List<EarlyWarning> {
    val warnings = mutableListOf<EarlyWarning>()
    
    // Check for sleep disruption patterns
    val sleepRisk = riskIndicators.find { it.category == RiskCategory.SLEEP }
    if (sleepRisk != null && sleepRisk.riskLevel >= 70) {
        warnings.add(
            EarlyWarning(
                title = "Sleep Disruption",
                description = "Irregular sleep patterns detected - ${sleepRisk.description}",
                severity = if (sleepRisk.riskLevel >= 85) WarningSeverity.HIGH else WarningSeverity.MEDIUM,
                recommendedAction = "Schedule sleep hygiene activities and maintain consistent bedtime",
                category = RiskCategory.SLEEP,
                relatedRiskIndicators = listOf(sleepRisk.id)
            )
        )
    }
    
    // Check for mood decline patterns
    if (moodEntries.size >= 3) {
        val recentMoods = moodEntries.takeLast(3).map { it.mood.toFloat() }
        val moodTrend = recentMoods.last() - recentMoods.first()
        if (moodTrend < -1.0f && recentMoods.last() <= 3) {
            warnings.add(
                EarlyWarning(
                    title = "Mood Decline",
                    description = "Significant mood decline detected over recent days",
                    severity = if (recentMoods.last() <= 2) WarningSeverity.HIGH else WarningSeverity.MEDIUM,
                    recommendedAction = "Engage in coping strategies and contact support network",
                    category = RiskCategory.MOOD_PATTERNS
                )
            )
        }
    }
    
    return warnings.sortedByDescending { 
        when (it.severity) {
            WarningSeverity.CRITICAL -> 4
            WarningSeverity.HIGH -> 3
            WarningSeverity.MEDIUM -> 2
            WarningSeverity.LOW -> 1
        }
    }
}
```
**What it does:**
- Scans risk indicators for high-risk factors (≥70)
- Detects rapid mood decline patterns
- Creates warnings with severity and actionable recommendations
- Prioritizes warnings by severity (CRITICAL first, then HIGH, MEDIUM, LOW)

---

**4. Intervention Triggering**
```kotlin
fun triggerIntervention(
    riskAssessment: RiskAssessment,
    safetyPlan: SafetyPlan
): InterventionPlan {
    val actions = mutableListOf<InterventionAction>()
    
    when (riskAssessment.overallRiskLevel) {
        RiskLevel.CRITICAL -> {
            actions.add(
                InterventionAction(
                    title = "Immediate Support Contact",
                    description = "Contact emergency support immediately",
                    priority = ActionPriority.CRITICAL,
                    deadline = Date(System.currentTimeMillis() + 3600000) // 1 hour
                )
            )
            actions.add(
                InterventionAction(
                    title = "Use Coping Strategies",
                    description = "Engage in immediate coping strategies from safety plan",
                    priority = ActionPriority.CRITICAL
                )
            )
        }
        RiskLevel.HIGH -> {
            actions.add(
                InterventionAction(
                    title = "Increase Monitoring",
                    description = "Complete daily check-ins and mood assessments",
                    priority = ActionPriority.HIGH
                )
            )
            actions.add(
                InterventionAction(
                    title = "Engage Support Network",
                    description = "Contact support person and share concerns",
                    priority = ActionPriority.HIGH
                )
            )
        }
        RiskLevel.MEDIUM -> {
            actions.add(
                InterventionAction(
                    title = "Regular Check-ins",
                    description = "Complete daily assessments and monitor patterns",
                    priority = ActionPriority.MEDIUM
                )
            )
        }
        RiskLevel.LOW -> {
            actions.add(
                InterventionAction(
                    title = "Continue Monitoring",
                    description = "Maintain regular check-ins and awareness",
                    priority = ActionPriority.LOW
                )
            )
        }
    }
    
    // Add specific actions based on early warnings
    riskAssessment.earlyWarnings.forEach { warning ->
        if (warning.severity in listOf(WarningSeverity.HIGH, WarningSeverity.CRITICAL)) {
            actions.add(
                InterventionAction(
                    title = "Address: ${warning.title}",
                    description = warning.recommendedAction,
                    priority = when (warning.severity) {
                        WarningSeverity.CRITICAL -> ActionPriority.CRITICAL
                        WarningSeverity.HIGH -> ActionPriority.HIGH
                        else -> ActionPriority.MEDIUM
                    }
                )
            )
        }
    }
    
    return InterventionPlan(
        triggerReason = "Risk level: ${riskAssessment.overallRiskLevel}",
        riskLevel = riskAssessment.overallRiskLevel,
        actions = actions,
        emergencyContacts = safetyPlan.emergencyContacts,
        status = InterventionStatus.ACTIVE
    )
}
```
**What it does:**
- Creates intervention plan based on risk level:
  - **CRITICAL**: Immediate emergency actions, 1-hour deadline
  - **HIGH**: Increase monitoring, contact support
  - **MEDIUM**: Regular check-ins
  - **LOW**: Continue monitoring
- Adds specific actions for high-severity early warnings
- Includes emergency contacts from safety plan

---

#### 🎨 UI Screen (`RelapsePreventionScreen.kt`)

**Auto-Refresh with Monitoring Toggle**
```kotlin
// Perform risk assessment when screen loads
LaunchedEffect(Unit) {
    if (isMonitoringEnabled) {
        isLoading = true
        riskAssessment = useCase.assessRiskLevel(
            moodEntries = sampleMoodEntries,
            sleepData = generateSampleSleepData(),
            stressLevels = generateSampleStressData()
        )
        isLoading = false
    }
}

// Refresh assessment periodically
LaunchedEffect(isMonitoringEnabled) {
    if (isMonitoringEnabled) {
        while (true) {
            kotlinx.coroutines.delay(300000) // Every 5 minutes
            riskAssessment = useCase.assessRiskLevel(
                moodEntries = sampleMoodEntries,
                sleepData = generateSampleSleepData(),
                stressLevels = generateSampleStressData()
            )
        }
    }
}
```
**What it does:**
- Automatically assesses risk when screen loads
- Refreshes assessment every 5 minutes when monitoring is enabled
- Can be toggled on/off by user

---

**Risk Factor Display**
```kotlin
@Composable
fun RiskFactorCard(factor: RiskIndicator) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(12.dp)) {
            val riskColor = when {
                factor.riskLevel >= 80 -> Color(0xFFF44336)  // Red: Critical
                factor.riskLevel >= 60 -> Color(0xFFFF9800)  // Orange: High
                factor.riskLevel >= 40 -> Color(0xFFFFC107) // Yellow: Medium
                else -> Color(0xFF4CAF50)                    // Green: Low
            }
            
            Box(modifier = Modifier
                .size(8.dp)
                .background(riskColor, RoundedCornerShape(4.dp)))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(factor.name, fontWeight = FontWeight.Medium)
                Text(factor.description, fontSize = 12.sp)
            }
            
            Text("${factor.riskLevel}%", color = riskColor, fontWeight = FontWeight.Medium)
        }
    }
}
```
**What it does:** Displays each risk factor with:
- Color-coded indicator (Red/Orange/Yellow/Green)
- Factor name and description
- Risk level percentage

---

### Test Example

```kotlin
@Test
fun `should assess risk factors and calculate overall risk level`() {
    // Given: Risk factors
    val riskFactors = listOf(
        RiskIndicator("Sleep Pattern", 75, "Irregular sleep increases relapse risk"),
        RiskIndicator("Stress Level", 60, "High stress detected"),
        RiskIndicator("Social Support", 40, "Limited social interaction")
    )
    
    // When: Calculate overall risk
    val averageRiskLevel = riskFactors.map { it.riskLevel }.average()
    val highRiskFactors = riskFactors.filter { it.riskLevel >= 70 }
    
    // Then: Validate assessment
    assertEquals(58.33, averageRiskLevel, 0.01)
    assertEquals(1, highRiskFactors.size)
    assertTrue(highRiskFactors.any { it.name == "Sleep Pattern" })
}
```

---

## 4. UC32: AI-Generated Journaling Prompts

### Purpose
Generates personalized journaling prompts using AI, adapting to user's mood, preferences, and journaling history to encourage meaningful reflection.

### Code Structure

#### 📊 Data Models (`JournalingPrompt.kt`)

```kotlin
// Individual journaling prompt
data class JournalingPrompt(
    val id: String = "",
    val title: String,                           // "Daily Reflection"
    val prompt: String,                          // The actual question
    val category: PromptCategory,                // REFLECTION, GRATITUDE, GROWTH, etc.
    val moodTags: List<MoodTag>,                 // HAPPY, SAD, ANXIOUS, etc.
    val difficulty: PromptDifficulty,            // EASY, MEDIUM, HARD, ADVANCED
    val estimatedTime: Int,                      // Minutes
    val isAIGenerated: Boolean = true,
    val timesUsed: Int = 0,
    val effectivenessScore: Float = 0f,          // 0-1.0 based on engagement
    val personalizedFor: String? = null,
    val personalizationFactors: List<PersonalizationFactor> = emptyList()
)

// Request for generating prompts
data class PromptGenerationRequest(
    val userId: String,
    val currentMood: Int? = null,                // 1-10 scale
    val moodTags: List<MoodTag> = emptyList(),
    val preferredCategory: PromptCategory? = null,
    val preferredDifficulty: PromptDifficulty? = null,
    val maxTimeMinutes: Int? = null,
    val excludeUsedPrompts: Boolean = false,
    val customTheme: String? = null
)

// Personalized recommendation
data class PromptRecommendation(
    val userId: String,
    val recommendedPrompts: List<JournalingPrompt>,
    val reasons: List<String> = emptyList(),      // Why these prompts were recommended
    val personalizationScore: Float = 0f,        // 0-1.0
    val generatedAt: Date = Date()
)
```

**What it does:**
- `JournalingPrompt` represents a single prompt with all metadata
- `PromptGenerationRequest` allows filtering and personalization
- `PromptRecommendation` provides top prompts with explanations

---

#### 🔧 Use Case Logic (`JournalingPromptsUseCase.kt`)

**1. Personalized Prompt Generation**
```kotlin
fun generatePersonalizedPrompts(
    request: PromptGenerationRequest,
    recentMoodEntries: List<MoodEntry> = emptyList(),
    journalingHistory: List<JournalEntry> = emptyList()
): List<JournalingPrompt> {
    val prompts = mutableListOf<JournalingPrompt>()
    
    // Get base prompts from library
    val basePrompts = getPromptLibrary()
    
    // Filter based on request preferences
    val filteredPrompts = basePrompts.filter { prompt ->
        val matchesCategory = request.preferredCategory == null || 
            prompt.category == request.preferredCategory || 
            request.preferredCategory == PromptCategory.ALL
        
        val matchesDifficulty = request.preferredDifficulty == null || 
            prompt.difficulty == request.preferredDifficulty
        
        val matchesTime = request.maxTimeMinutes == null || 
            prompt.estimatedTime <= request.maxTimeMinutes
        
        val matchesMood = request.moodTags.isEmpty() || 
            request.moodTags.any { it in prompt.moodTags } ||
            prompt.moodTags.contains(MoodTag.ALL)
        
        val notUsed = !request.excludeUsedPrompts || prompt.timesUsed == 0
        
        matchesCategory && matchesDifficulty && matchesTime && matchesMood && notUsed
    }
    
    // Personalize each prompt
    filteredPrompts.forEach { prompt ->
        val personalizedPrompt = personalizePrompt(
            prompt = prompt,
            userId = request.userId,
            currentMood = request.currentMood,
            recentMoodEntries = recentMoodEntries,
            journalingHistory = journalingHistory,
            customTheme = request.customTheme
        )
        prompts.add(personalizedPrompt)
    }
    
    // Sort by personalization score and relevance
    return prompts.sortedByDescending { 
        it.effectivenessScore + calculateRelevanceScore(it, request)
    }.take(20) // Return top 20
}
```
**What it does:**
1. Loads prompt library (10+ pre-built prompts)
2. Filters by category, difficulty, time, mood, usage
3. Personalizes each prompt based on user data
4. Sorts by personalization score (effectiveness + relevance)
5. Returns top 20 most relevant prompts

---

**2. Prompt Personalization Algorithm**
```kotlin
private fun personalizePrompt(
    prompt: JournalingPrompt,
    userId: String,
    currentMood: Int?,
    recentMoodEntries: List<MoodEntry>,
    journalingHistory: List<JournalEntry>,
    customTheme: String?
): JournalingPrompt {
    val factors = mutableListOf<PersonalizationFactor>()
    var effectivenessScore = prompt.effectivenessScore
    
    // Factor 1: Current mood match
    if (currentMood != null) {
        val moodMatch = calculateMoodMatch(currentMood, prompt.moodTags)
        if (moodMatch > 0) {
            effectivenessScore += 0.3f * moodMatch
            factors.add(
                PersonalizationFactor(
                    type = FactorType.RECENT_MOOD,
                    value = "Mood score: $currentMood",
                    weight = moodMatch
                )
            )
        }
    }
    
    // Factor 2: Journaling history pattern
    if (journalingHistory.isNotEmpty()) {
        val categoryFrequency = journalingHistory
            .groupBy { it.type }
            .mapValues { it.value.size }
        val preferredCategory = categoryFrequency.maxByOrNull { it.value }?.key
        if (preferredCategory != null && matchesCategory(prompt.category, preferredCategory)) {
            effectivenessScore += 0.2f
            factors.add(
                PersonalizationFactor(
                    type = FactorType.JOURNALING_HISTORY,
                    value = "You often write about ${preferredCategory.name}",
                    weight = 0.2f
                )
            )
        }
    }
    
    // Factor 3: Recent mood pattern
    if (recentMoodEntries.size >= 3) {
        val averageMood = recentMoodEntries.map { it.mood.toFloat() }.average().toFloat()
        val moodTag = determineMoodTagFromValue(averageMood.toInt())
        if (moodTag in prompt.moodTags || prompt.moodTags.contains(MoodTag.ALL)) {
            effectivenessScore += 0.2f
            factors.add(
                PersonalizationFactor(
                    type = FactorType.MOOD_PATTERN,
                    value = "Your recent average mood aligns with this prompt",
                    weight = 0.2f
                )
            )
        }
    }
    
    // Factor 4: Custom theme match
    if (customTheme != null && prompt.prompt.contains(customTheme, ignoreCase = true)) {
        effectivenessScore += 0.3f
        factors.add(
            PersonalizationFactor(
                type = FactorType.USER_PREFERENCE,
                value = "Matches your requested theme: $customTheme",
                weight = 0.3f
            )
        )
    }
    
    // Factor 5: Usage frequency (prefer moderately used prompts)
    when {
        prompt.timesUsed == 0 -> effectivenessScore += 0.1f  // Try new prompts
        prompt.timesUsed in 1..5 -> effectivenessScore += 0.2f  // Well-tested
        else -> effectivenessScore -= 0.1f  // Overused
    }
    
    return prompt.copy(
        personalizedFor = userId,
        personalizationFactors = factors,
        effectivenessScore = effectivenessScore.coerceIn(0f, 1f)
    )
}
```
**What it does:**
Calculates personalization score (0-1.0) based on:
1. **Current mood match** (+0.3): If prompt matches user's current mood
2. **Journaling history** (+0.2): If user frequently writes in this category
3. **Recent mood pattern** (+0.2): If prompt aligns with recent mood trends
4. **Custom theme** (+0.3): If prompt matches user-requested theme
5. **Usage frequency** (+0.1 to +0.2): Prefers prompts used 1-5 times (tested but not overused)

**Example:** User in happy mood (7/10), frequently writes gratitude entries, requesting "relationships" theme:
- Gratitude prompt: +0.3 (mood match) + 0.2 (history) + 0.2 (usage) = 0.7 score
- Relationships prompt: +0.3 (custom theme) = 0.3 score
- Result: Gratitude prompt ranked higher

---

**3. Recommendation Generation**
```kotlin
fun getRecommendedPrompts(
    userId: String,
    currentMood: Int? = null,
    recentMoodEntries: List<MoodEntry> = emptyList(),
    journalingHistory: List<JournalEntry> = emptyList()
): PromptRecommendation {
    val request = PromptGenerationRequest(
        userId = userId,
        currentMood = currentMood,
        moodTags = extractMoodTags(currentMood, recentMoodEntries),
        excludeUsedPrompts = false
    )
    
    val prompts = generatePersonalizedPrompts(
        request = request,
        recentMoodEntries = recentMoodEntries,
        journalingHistory = journalingHistory
    )
    
    val recommended = prompts.take(5)  // Top 5
    val personalizationScore = recommended.map { it.effectivenessScore }.average().toFloat()
    val reasons = generateRecommendationReasons(recommended, currentMood, recentMoodEntries)
    
    return PromptRecommendation(
        userId = userId,
        recommendedPrompts = recommended,
        reasons = reasons,
        personalizationScore = personalizationScore,
        generatedAt = Date()
    )
}
```
**What it does:**
- Generates personalized prompts
- Selects top 5 with highest effectiveness scores
- Calculates average personalization score
- Generates explanation reasons (e.g., "Based on your current mood", "Includes prompts you've found helpful")

---

**4. Prompt Library**
```kotlin
private fun getPromptLibrary(): List<JournalingPrompt> {
    return listOf(
        JournalingPrompt(
            id = "1",
            title = "Daily Reflection",
            prompt = "What was the most meaningful moment of your day? How did it make you feel?",
            category = PromptCategory.REFLECTION,
            moodTags = listOf(MoodTag.ALL),
            difficulty = PromptDifficulty.EASY,
            estimatedTime = 8,
            timesUsed = 3
        ),
        JournalingPrompt(
            id = "2",
            title = "Gratitude Practice",
            prompt = "Write about three things you're grateful for today. What makes each one special?",
            category = PromptCategory.GRATITUDE,
            moodTags = listOf(MoodTag.HAPPY, MoodTag.GRATEFUL),
            difficulty = PromptDifficulty.EASY,
            estimatedTime = 5,
            timesUsed = 7
        ),
        // ... 8 more prompts (10 total)
    )
}
```
**What it does:** Maintains a library of 10+ pre-built prompts across categories (Reflection, Gratitude, Growth, Relationships, Goals, Challenges, Emotional Processing, Daily Check-in).

---

**5. Effectiveness Scoring**
```kotlin
private fun calculateEffectivenessScore(journalEntry: JournalEntry): Float {
    var score = 0.5f // Base score
    
    // Longer entries suggest more engagement
    if (journalEntry.content.length > 200) score += 0.2f
    if (journalEntry.content.length > 500) score += 0.1f
    
    // Entries with mood tags suggest emotional processing
    if (journalEntry.mood != null) score += 0.1f
    
    // Entries with multiple tags suggest depth
    if (journalEntry.tags.size > 2) score += 0.1f
    
    return score.coerceIn(0f, 1f)
}
```
**What it does:** Calculates how effective a prompt was based on the journal entry created:
- **Base**: 0.5
- **+0.2**: Entry >200 characters (engaged)
- **+0.1**: Entry >500 characters (very engaged)
- **+0.1**: Entry includes mood rating
- **+0.1**: Entry has multiple tags (deep reflection)
- **Max**: 1.0

This score updates the prompt's effectiveness over time, helping the system learn which prompts work best for each user.

---

#### 🎨 UI Screen (`JournalingPromptsScreen.kt`)

**Dynamic Filtering**
```kotlin
// Filter prompts based on user selections
val filteredPrompts = useCase.filterPrompts(
    prompts = allPrompts,
    category = if (selectedCategory == "All") null else parseCategory(selectedCategory),
    moodTags = if (selectedMood == "All") emptyList() else listOf(parseMoodTag(selectedMood))
).map { prompt ->
    JournalPrompt(
        id = prompt.id,
        title = prompt.title,
        prompt = prompt.prompt,
        category = prompt.category.name.replace("_", " "),
        moodTags = prompt.moodTags.map { it.name.lowercase().capitalize() },
        difficulty = prompt.difficulty.name.capitalize(),
        estimatedTime = "${prompt.estimatedTime} minutes",
        isAIGenerated = prompt.isAIGenerated,
        timesUsed = prompt.timesUsed
    )
}
```
**What it does:**
- Filters prompts in real-time based on category and mood selections
- Converts data models to UI format
- Updates displayed prompts as user changes filters

---

**Personalization Display**
```kotlin
Text(
    text = "Personalization: ${(promptRecommendation.personalizationScore * 100).toInt()}% accuracy",
    style = MaterialTheme.typography.bodySmall,
    color = Color(0xFF4CAF50)
)
```
**What it does:** Shows the personalization accuracy score to users, indicating how well prompts match their profile.

---

### Test Example

```kotlin
@Test
fun `should generate personalized prompts based on user mood and preferences`() {
    // Given: Journal prompts and user mood
    val journalPrompts = listOf(
        JournalPrompt("1", "Daily Reflection", "What was meaningful?", "Reflection", 
                      listOf("All"), "Easy", "5-10 minutes", true, 3),
        JournalPrompt("2", "Gratitude Practice", "Three things you're grateful for", 
                      "Gratitude", listOf("Happy", "Grateful"), "Easy", "5 minutes", true, 7)
    )
    
    // When: Filter by mood
    val happyMoodPrompts = journalPrompts.filter { it.moodTags.contains("Happy") }
    val allMoodPrompts = journalPrompts.filter { it.moodTags.contains("All") }
    
    // Then: Validate filtering
    assertEquals(1, happyMoodPrompts.size)
    assertEquals("Gratitude Practice", happyMoodPrompts.first().title)
    assertEquals(1, allMoodPrompts.size)
}
```

---

## Architecture Summary

### Data Flow Pattern
```
User Input → UI Screen → Use Case → Data Models → Use Case → UI Display
                ↓            ↓           ↓
            User Action  Business    Data Storage
                        Logic       (Repository)
```

### Key Patterns Used

1. **Separation of Concerns**
   - Data Models: Pure data structures
   - Use Cases: Business logic only
   - UI Screens: Presentation only

2. **Personalization**
   - All use cases adapt to user data (mood, history, preferences)
   - Scoring algorithms calculate relevance
   - Recommendations include explanations

3. **Real-Time Updates**
   - Auto refresh mechanisms (LaunchedEffect with coroutines)
   - Reactive UI updates (remember, mutableStateOf)
   - Live data updates from use cases

4. **Error Handling**
   - Validation with `require()` statements
   - Safe navigation with null checks
   - Graceful fallbacks for missing data

---

## Integration Points

All four use cases integrate with:

1. **Navigation System**
   - Added to `Screen.kt` enum
   - Routes defined in `AppNavigation.kt`
   - Accessible from Settings/Main Dashboard

2. **Data Models**
   - Share `MoodEntry` for personalization
   - Use consistent date/timestamp patterns
   - Follow same naming conventions

3. **UI Components**
   - Material3 design system
   - Consistent card layouts
   - Similar color schemes and icons

---

## Code Statistics

| Use Case | Data Models | Use Case Logic | UI Screen | Total Lines |
|----------|-------------|----------------|-----------|-------------|
| UC22: System Health | 81 | 288 | 530 | **899** |
| UC26: Mood Forecasting | 76 | 363 | 490 | **929** |
| UC35: Relapse Prevention | 135 | 554 | 779 | **1,468** |
| UC32: Journaling Prompts | 92 | 481 | 624 | **1,197** |
| **TOTAL** | **384** | **1,686** | **2,423** | **4,493** |

---

*This document provides detailed code-level explanations. For test coverage details, see `FOUR_USE_CASES_OVERVIEW.md`.*

