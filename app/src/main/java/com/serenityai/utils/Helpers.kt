package com.serenityai.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

object Helpers {
    
    // Date and Time Utilities
    fun formatDate(date: Date, pattern: String = Constants.DATE_FORMAT): String {
        val formatter = SimpleDateFormat(pattern, Locale.getDefault())
        return formatter.format(date)
    }
    
    fun getCurrentDate(): Date = Date()
    
    fun getDateDaysAgo(days: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -days)
        return calendar.time
    }
    
    fun getStartOfDay(date: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.time
    }
    
    fun getEndOfDay(date: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        return calendar.time
    }
    
    // Mood Utilities
    fun getMoodDescription(mood: Int): String {
        return when (mood) {
            in 1..2 -> "Very Low"
            in 3..4 -> "Low"
            5 -> "Neutral"
            in 6..7 -> "Good"
            in 8..9 -> "Very Good"
            10 -> "Excellent"
            else -> "Unknown"
        }
    }
    
    fun getMoodEmoji(mood: Int): String {
        return when (mood) {
            in 1..2 -> "😢"
            in 3..4 -> "😔"
            5 -> "😐"
            in 6..7 -> "😊"
            in 8..9 -> "😄"
            10 -> "🤩"
            else -> "❓"
        }
    }
    
    fun calculateAverageMood(moods: List<Int>): Double {
        return if (moods.isNotEmpty()) {
            moods.average()
        } else {
            0.0
        }
    }
    
    fun getMoodTrend(moods: List<Int>): String {
        if (moods.size < 2) return "Insufficient data"
        
        val recent = moods.takeLast(3).average()
        val older = moods.dropLast(3).takeLast(3).average()
        
        return when {
            recent > older + 0.5 -> "Improving"
            recent < older - 0.5 -> "Declining"
            else -> "Stable"
        }
    }
    
    // Validation Utilities
    fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"
        return email.matches(emailPattern.toRegex())
    }
    
    fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }
    
    fun isValidMoodValue(mood: Int): Boolean {
        return mood in Constants.MIN_MOOD_VALUE..Constants.MAX_MOOD_VALUE
    }
    
    // SharedPreferences Utilities
    fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE)
    }
    
    fun saveString(context: Context, key: String, value: String) {
        getSharedPreferences(context).edit().putString(key, value).apply()
    }
    
    fun getString(context: Context, key: String, defaultValue: String = ""): String {
        return getSharedPreferences(context).getString(key, defaultValue) ?: defaultValue
    }
    
    fun saveBoolean(context: Context, key: String, value: Boolean) {
        getSharedPreferences(context).edit().putBoolean(key, value).apply()
    }
    
    fun getBoolean(context: Context, key: String, defaultValue: Boolean = false): Boolean {
        return getSharedPreferences(context).getBoolean(key, defaultValue)
    }
    
    fun saveInt(context: Context, key: String, value: Int) {
        getSharedPreferences(context).edit().putInt(key, value).apply()
    }
    
    fun getInt(context: Context, key: String, defaultValue: Int = 0): Int {
        return getSharedPreferences(context).getInt(key, defaultValue)
    }
    
    // Logging Utilities
    fun logError(tag: String, message: String, throwable: Throwable? = null) {
        if (throwable != null) {
            Log.e(tag, message, throwable)
        } else {
            Log.e(tag, message)
        }
    }
    
    fun logInfo(tag: String, message: String) {
        Log.i(tag, message)
    }
    
    fun logDebug(tag: String, message: String) {
        Log.d(tag, message)
    }
    
    // String Utilities
    fun capitalizeFirstLetter(text: String): String {
        return text.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
    }
    
    fun truncateText(text: String, maxLength: Int): String {
        return if (text.length <= maxLength) {
            text
        } else {
            "${text.take(maxLength - 3)}..."
        }
    }
    
    // Math Utilities
    fun roundToDecimalPlaces(value: Double, decimalPlaces: Int): Double {
        val multiplier = Math.pow(10.0, decimalPlaces.toDouble())
        return (value * multiplier).roundToInt() / multiplier
    }
    
    fun calculatePercentage(part: Int, total: Int): Int {
        return if (total > 0) {
            ((part.toDouble() / total) * 100).roundToInt()
        } else {
            0
        }
    }
}
