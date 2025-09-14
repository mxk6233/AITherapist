package com.serenityai.utils

object Constants {
    // API Configuration
    const val OPENAI_BASE_URL = "https://api.openai.com/"
    const val OPENAI_API_KEY_PREF = "openai_api_key"
    
    // Firebase Collections
    const val USERS_COLLECTION = "users"
    const val SESSIONS_COLLECTION = "sessions"
    const val MOOD_ENTRIES_COLLECTION = "moodEntries"
    const val EXERCISES_COLLECTION = "exercises"
    const val EXERCISE_SESSIONS_COLLECTION = "exerciseSessions"
    
    // SharedPreferences Keys
    const val PREFS_NAME = "serenity_ai_prefs"
    const val USER_ID_KEY = "user_id"
    const val IS_FIRST_LAUNCH_KEY = "is_first_launch"
    const val THEME_KEY = "theme"
    const val NOTIFICATIONS_ENABLED_KEY = "notifications_enabled"
    const val REMINDER_TIME_KEY = "reminder_time"
    
    // Mood Scale
    const val MIN_MOOD_VALUE = 1
    const val MAX_MOOD_VALUE = 10
    const val DEFAULT_MOOD_VALUE = 5
    
    // Session Configuration
    const val MAX_SESSION_DURATION_MINUTES = 60
    const val SESSION_TIMEOUT_MINUTES = 30
    
    // Exercise Configuration
    const val DEFAULT_EXERCISE_DURATION_MINUTES = 10
    const val MAX_EXERCISE_DURATION_MINUTES = 60
    
    // Notification Configuration
    const val MOOD_REMINDER_CHANNEL_ID = "mood_reminder_channel"
    const val MOOD_REMINDER_NOTIFICATION_ID = 1001
    const val EXERCISE_REMINDER_CHANNEL_ID = "exercise_reminder_channel"
    const val EXERCISE_REMINDER_NOTIFICATION_ID = 1002
    
    // Error Messages
    const val ERROR_NETWORK = "Network error. Please check your connection."
    const val ERROR_AUTH_FAILED = "Authentication failed. Please try again."
    const val ERROR_INVALID_INPUT = "Invalid input. Please check your data."
    const val ERROR_UNKNOWN = "An unknown error occurred. Please try again."
    
    // Success Messages
    const val SUCCESS_MOOD_SAVED = "Mood entry saved successfully"
    const val SUCCESS_SESSION_STARTED = "Session started successfully"
    const val SUCCESS_EXERCISE_COMPLETED = "Exercise completed successfully"
    
    // Date Formats
    const val DATE_FORMAT = "yyyy-MM-dd"
    const val DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss"
    const val TIME_FORMAT = "HH:mm"
    
    // File Extensions
    const val AUDIO_EXTENSION = ".mp3"
    const val IMAGE_EXTENSION = ".jpg"
    const val EXPORT_EXTENSION = ".csv"
}
