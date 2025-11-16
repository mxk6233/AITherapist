package com.serenityai.tests.ui.screens

import com.serenityai.ui.screens.formatTime
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import java.text.SimpleDateFormat
import java.util.*

class FormatTimeTest {
    
    @Test
    fun `formatTime should format timestamp correctly`() {
        val timestamp = System.currentTimeMillis()
        val formatted = formatTime(timestamp)
        
        assertNotNull(formatted)
        assertTrue(formatted.isNotEmpty())
        
        // Should be in HH:mm format
        val timePattern = "^\\d{2}:\\d{2}$".toRegex()
        assertTrue(timePattern.matches(formatted), "Time should be in HH:mm format")
    }
    
    @Test
    fun `formatTime should handle different timestamps`() {
        val testTimestamps = listOf(
            0L, // Epoch
            1000000000L, // timestamp-1
            2000000000L, // timestamp-2
            System.currentTimeMillis() // Current time
        )
        
        testTimestamps.forEach { timestamp ->
            val formatted = formatTime(timestamp)
            assertNotNull(formatted)
            assertTrue(formatted.isNotEmpty())
            
            // Should be in HH:mm format
            val timePattern = "^\\d{2}:\\d{2}$".toRegex()
            assertTrue(timePattern.matches(formatted), "Time should be in HH:mm format for timestamp $timestamp")
        }
    }
    
    @Test
    fun `formatTime should use default locale`() {
        val timestamp = System.currentTimeMillis()
        val formatted = formatTime(timestamp)
        
        // Should not be null or empty
        assertNotNull(formatted)
        assertTrue(formatted.isNotEmpty())
        
        // Should contain colon separator
        assertTrue(formatted.contains(":"))
    }
    
    @Test
    fun `formatTime should handle midnight correctly`() {
        // Create a timestamp for midnight (00:00)
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        
        val midnightTimestamp = calendar.timeInMillis
        val formatted = formatTime(midnightTimestamp)
        
        assertEquals("00:00", formatted)
    }
    
    @Test
    fun `formatTime should handle noon correctly`() {
        // Create a timestamp for noon (12:00)
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 12)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        
        val noonTimestamp = calendar.timeInMillis
        val formatted = formatTime(noonTimestamp)
        
        assertEquals("12:00", formatted)
    }
    
    @Test
    fun `formatTime should handle different minutes`() {
        val testCases = listOf(
            Pair(0, "00"),
            Pair(15, "15"),
            Pair(30, "30"),
            Pair(45, "45"),
            Pair(59, "59")
        )
        
        testCases.forEach { (minute, expectedMinute) ->
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, 10)
            calendar.set(Calendar.MINUTE, minute)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            
            val timestamp = calendar.timeInMillis
            val formatted = formatTime(timestamp)
            
            assertTrue(formatted.endsWith(expectedMinute), "Time should end with $expectedMinute for minute $minute")
        }
    }
    
    @Test
    fun `formatTime should handle different hours`() {
        val testCases = listOf(
            Pair(0, "00"),
            Pair(6, "06"),
            Pair(12, "12"),
            Pair(18, "18"),
            Pair(23, "23")
        )
        
        testCases.forEach { (hour, expectedHour) ->
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            
            val timestamp = calendar.timeInMillis
            val formatted = formatTime(timestamp)
            
            assertTrue(formatted.startsWith(expectedHour), "Time should start with $expectedHour for hour $hour")
        }
    }
    
    @Test
    fun `formatTime should be consistent for same timestamp`() {
        val timestamp = System.currentTimeMillis()
        val formatted1 = formatTime(timestamp)
        val formatted2 = formatTime(timestamp)
        
        assertEquals(formatted1, formatted2, "Same timestamp should produce same formatted time")
    }
}
