package com.serenityai.utils

/**
 * Utility functions for the AI Therapist application.
 * Currently contains only mood-related utilities that are actively used.
 */
object Helpers {
    
    /**
     * Returns an emoji representation of a mood value (1-10 scale).
     * 
     * @param mood Mood value from 1 to 10
     * @return Emoji string representing the mood level
     */
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
}
