package com.serenityai.usecases

import com.serenityai.data.models.*
import java.util.Calendar
import java.util.Date

/**
 * UC26: AI-Powered Mood Forecasting - Use Case
 * 
 * Use Case Goal: Enable users to receive predictive mood analytics so they can proactively 
 * plan self-care activities and interventions before mood declines occur.
 * 
 * Responsibilities:
 * - Generate 7-day mood forecasts based on historical patterns
 * - Identify cyclical patterns (weekly, seasonal)
 * - Provide confidence scores for predictions
 * - Trigger proactive intervention recommendations
 * - Personalize forecasts based on user patterns
 */
class MoodForecastingUseCase {
    
    /**
     * Generates a complete 7-day mood forecast
     * 
     * @param historicalMoodData Historical mood entries (ideally 30+ days)
     * @return MoodForecast with 7 days of predictions
     */
    fun generate7DayForecast(historicalMoodData: List<MoodEntry>): MoodForecast {
        require(historicalMoodData.isNotEmpty()) { "Historical data required for forecasting" }
        
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
            id = System.currentTimeMillis().toString(),
            userId = historicalMoodData.firstOrNull()?.userId ?: "",
            generatedAt = Date(),
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
    
    /**
     * Identifies weekly cyclical patterns (weekday vs weekend)
     * 
     * @param historicalData Historical mood entries
     * @return WeeklyPattern with weekday/weekend analysis
     */
    fun identifyWeeklyPatterns(historicalData: List<MoodEntry>): WeeklyPattern {
        require(historicalData.isNotEmpty()) { "Historical data required for pattern analysis" }
        
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
            patternStrength = patternStrength * 100f, // Convert to percentage
            confidence = confidence
        )
    }
    
    /**
     * Provides confidence scores for predictions
     * 
     * @param historicalDataSize Number of historical data points
     * @param dayOffset Days into future (0 = today, 6 = 6 days ahead)
     * @return Confidence score (0-100%)
     */
    fun calculateConfidence(historicalDataSize: Int, dayOffset: Int): Float {
        // More historical data = higher confidence
        // Farther into future = lower confidence
        val dataConfidence = when {
            historicalDataSize >= 60 -> 100f
            historicalDataSize >= 30 -> 90f
            historicalDataSize >= 14 -> 75f
            historicalDataSize >= 7 -> 60f
            else -> 50f
        }
        
        val timeDecay = when {
            dayOffset <= 1 -> 1.0f
            dayOffset <= 3 -> 0.9f
            dayOffset <= 5 -> 0.8f
            else -> 0.7f
        }
        
        return (dataConfidence * timeDecay).coerceIn(0f, 100f)
    }
    
    /**
     * Triggers proactive intervention recommendations when low mood is predicted
     * 
     * @param forecastDays List of forecast days
     * @return List of intervention recommendations
     */
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
    
    // Private helper methods
    
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
            weeklyPattern.weekendBoost * 0.5f // Moderate effect
        } else if (!isWeekend) {
            -weeklyPattern.weekendBoost * 0.3f // Slight weekday decline
        } else {
            0f
        }
        
        // Add slight random variation for realism
        val variation = (Math.random() * 0.4 - 0.2).toFloat()
        
        return (historicalAverage + adjustment + variation).coerceIn(1.0f, 5.0f)
    }
    
    private fun generateForecastNote(predictedMood: Float, dayOffset: Int): String {
        return when {
            predictedMood >= 4.5f -> when (dayOffset) {
                0 -> "Excellent mood expected - perfect day for important activities"
                6 -> "Weekend peak predicted - plan social activities"
                else -> "Peak mood expected - optimal day for social activities"
            }
            predictedMood >= 4.0f -> "Stable positive mood - continue current practices"
            predictedMood >= 3.5f -> "Mild improvement expected - maintain self-care routines"
            predictedMood >= 3.0f -> "Stable mood expected - no significant changes predicted"
            predictedMood >= 2.5f -> "Slight decline expected - consider planning self-care activities"
            else -> "LOW MOOD PREDICTED - proactive interventions recommended"
        }
    }
    
    private fun calculateTrend(forecastDays: List<ForecastDay>): ForecastTrend {
        val moods = forecastDays.map { it.predictedMood }
        val firstHalf = moods.take(moods.size / 2).average()
        val secondHalf = moods.drop(moods.size / 2).average()
        
        val difference = secondHalf - firstHalf
        return when {
            difference > 0.3f -> ForecastTrend.IMPROVING
            difference < -0.3f -> ForecastTrend.DECLINING
            moods.maxOrNull()!! - moods.minOrNull()!! > 1.0f -> ForecastTrend.VOLATILE
            else -> ForecastTrend.STABLE
        }
    }
    
    private fun calculateRiskLevel(forecastDays: List<ForecastDay>): RiskLevel {
        val lowMoodDays = forecastDays.count { it.predictedMood < 3.0f }
        val criticalDays = forecastDays.count { it.predictedMood < 2.5f }
        
        return when {
            criticalDays >= 2 -> RiskLevel.CRITICAL
            criticalDays >= 1 || lowMoodDays >= 3 -> RiskLevel.HIGH
            lowMoodDays >= 1 -> RiskLevel.MEDIUM
            else -> RiskLevel.LOW
        }
    }
    
    private fun generateRecommendations(forecastDays: List<ForecastDay>, riskLevel: RiskLevel): List<String> {
        val recommendations = mutableListOf<String>()
        
        when (riskLevel) {
            RiskLevel.CRITICAL -> {
                recommendations.add("CRITICAL: Multiple low mood days predicted - schedule immediate support")
                recommendations.add("Plan intensive self-care activities before predicted decline")
                recommendations.add("Consider reaching out to support network proactively")
            }
            RiskLevel.HIGH -> {
                recommendations.add("Plan self-care activities before mood decline")
                recommendations.add("Schedule preferred activities during higher mood days")
            }
            RiskLevel.MEDIUM -> {
                recommendations.add("Monitor mood closely and plan self-care activities")
            }
            RiskLevel.LOW -> {
                recommendations.add("Forecast shows stable mood - continue current practices")
            }
        }
        
        // Add specific day recommendations
        forecastDays.forEach { day ->
            when {
                day.predictedMood >= 4.5f -> recommendations.add("${day.day}: Peak mood - optimal for social activities")
                day.predictedMood < 3.0f -> recommendations.add("${day.day}: Low mood predicted - plan self-care")
            }
        }
        
        return recommendations.distinct()
    }
    
    private fun identifyPatterns(historicalData: List<MoodEntry>, forecastDays: List<ForecastDay>): List<MoodPrediction> {
        val predictions = mutableListOf<MoodPrediction>()
        
        // Identify weekend boost pattern
        val weeklyPattern = identifyWeeklyPatterns(historicalData)
        if (weeklyPattern.patternStrength >= 15f && weeklyPattern.weekendBoost > 0) {
            predictions.add(
                MoodPrediction(
                    title = "Weekend Boost",
                    description = "Mood typically improves on weekends by ${String.format("%.1f", weeklyPattern.weekendBoost)} points",
                    confidence = weeklyPattern.confidence,
                    patternType = PatternType.WEEKEND_BOOST
                )
            )
        }
        
        // Identify midweek dip
        val weekdayForecasts = forecastDays.filter { 
            !it.day.contains("Sat") && !it.day.contains("Sun") && it.day != "Today"
        }
        if (weekdayForecasts.isNotEmpty()) {
            val midweekMood = weekdayForecasts.map { it.predictedMood }.average()
            val weekendMood = forecastDays.filter { 
                it.day.contains("Sat") || it.day.contains("Sun") 
            }.map { it.predictedMood }.average()
            
            if (midweekMood < weekendMood - 0.3f) {
                predictions.add(
                    MoodPrediction(
                        title = "Midweek Dip",
                        description = "Slight decline on weekdays - common stress pattern",
                        confidence = 70f,
                        patternType = PatternType.MIDWEEK_DIP
                    )
                )
            }
        }
        
        // Identify exercise impact (if historical data suggests it)
        // In production, would analyze correlation with exercise entries
        
        return predictions
    }
    
    private fun identifyForecastFactors(historicalData: List<MoodEntry>, dayOffset: Int): List<ForecastFactor> {
        val factors = mutableListOf<ForecastFactor>()
        
        // Weekend factor
        val calendar = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, dayOffset) }
        if (calendar.get(Calendar.DAY_OF_WEEK) in listOf(Calendar.SATURDAY, Calendar.SUNDAY)) {
            factors.add(ForecastFactor("Weekend Effect", 0.3f, 75f))
        }
        
        // Historical trend factor
        if (historicalData.size >= 7) {
            factors.add(ForecastFactor("Historical Trend", 0.2f, 80f))
        }
        
        return factors
    }
    
    private fun calculatePatternConfidence(weekdayCount: Int, weekendCount: Int): Float {
        val totalSamples = weekdayCount + weekendCount
        return when {
            totalSamples >= 20 && weekdayCount >= 10 && weekendCount >= 5 -> 90f
            totalSamples >= 10 && weekdayCount >= 5 && weekendCount >= 2 -> 75f
            totalSamples >= 5 -> 60f
            else -> 50f
        }
    }
    
    private fun getDateForDayOffset(dayOffset: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, dayOffset)
        return calendar.time
    }
    
    private fun getDayName(dayOffset: Int): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, dayOffset)
        return when (dayOffset) {
            0 -> "Today"
            1 -> "Tomorrow"
            else -> {
                val dayNames = arrayOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
                dayNames[calendar.get(Calendar.DAY_OF_WEEK) - 1]
            }
        }
    }
}

