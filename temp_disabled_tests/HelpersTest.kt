package com.serenityai.utils

import android.content.Context
import android.content.SharedPreferences
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.*
import org.mockito.ArgumentMatchers.*
import java.util.*

class HelpersTest {

    @Mock
    private lateinit var mockContext: Context

    @Mock
    private lateinit var mockSharedPreferences: SharedPreferences

    @Mock
    private lateinit var mockEditor: SharedPreferences.Editor

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        `when`(mockContext.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE))
            .thenReturn(mockSharedPreferences)
        `when`(mockSharedPreferences.edit()).thenReturn(mockEditor)
        `when`(mockEditor.putString(any(), any())).thenReturn(mockEditor)
        `when`(mockEditor.putBoolean(any(), any())).thenReturn(mockEditor)
        `when`(mockEditor.putInt(any(), any())).thenReturn(mockEditor)
        `when`(mockEditor.apply()).thenReturn(Unit)
    }

    @Nested
    @DisplayName("Date and Time Utilities")
    inner class DateAndTimeUtilities {

        @Test
        @DisplayName("formatDate should format date correctly with default pattern")
        fun formatDate_shouldFormatDateCorrectlyWithDefaultPattern() {
            val date = Date(1640995200000L) // date-1
            val result = Helpers.formatDate(date)
            assertEquals("date-1", result)
        }

        @Test
        @DisplayName("formatDate should format date correctly with custom pattern")
        fun formatDate_shouldFormatDateCorrectlyWithCustomPattern() {
            val date = Date(1640995200000L) // date-1
            val result = Helpers.formatDate(date, "dd/MM/yyyy")
            assertEquals("date-formatted", result)
        }

        @Test
        @DisplayName("getCurrentDate should return current date")
        fun getCurrentDate_shouldReturnCurrentDate() {
            val before = Date()
            val result = Helpers.getCurrentDate()
            val after = Date()
            
            assertTrue(result.time >= before.time)
            assertTrue(result.time <= after.time)
        }

        @Test
        @DisplayName("getDateDaysAgo should return correct date")
        fun getDateDaysAgo_shouldReturnCorrectDate() {
            val result = Helpers.getDateDaysAgo(7)
            val expected = Calendar.getInstance().apply {
                add(Calendar.DAY_OF_YEAR, -7)
            }.time
            
            // Allow for small time differences
            assertTrue(Math.abs(result.time - expected.time) < 1000)
        }

        @Test
        @DisplayName("getStartOfDay should return start of day")
        fun getStartOfDay_shouldReturnStartOfDay() {
            val date = Date(1640995200000L) // date-1
            val result = Helpers.getStartOfDay(date)
            
            val calendar = Calendar.getInstance()
            calendar.time = result
            
            assertEquals(0, calendar.get(Calendar.HOUR_OF_DAY))
            assertEquals(0, calendar.get(Calendar.MINUTE))
            assertEquals(0, calendar.get(Calendar.SECOND))
            assertEquals(0, calendar.get(Calendar.MILLISECOND))
        }

        @Test
        @DisplayName("getEndOfDay should return end of day")
        fun getEndOfDay_shouldReturnEndOfDay() {
            val date = Date(1640995200000L) // date-1
            val result = Helpers.getEndOfDay(date)
            
            val calendar = Calendar.getInstance()
            calendar.time = result
            
            assertEquals(23, calendar.get(Calendar.HOUR_OF_DAY))
            assertEquals(59, calendar.get(Calendar.MINUTE))
            assertEquals(59, calendar.get(Calendar.SECOND))
            assertEquals(999, calendar.get(Calendar.MILLISECOND))
        }
    }

    @Nested
    @DisplayName("Mood Utilities")
    inner class MoodUtilities {

        @ParameterizedTest
        @ValueSource(ints = [1, 2])
        @DisplayName("getMoodDescription should return 'Very Low' for mood values 1-2")
        fun getMoodDescription_shouldReturnVeryLowForMoodValues1To2(mood: Int) {
            val result = Helpers.getMoodDescription(mood)
            assertEquals("Very Low", result)
        }

        @ParameterizedTest
        @ValueSource(ints = [3, 4])
        @DisplayName("getMoodDescription should return 'Low' for mood values 3-4")
        fun getMoodDescription_shouldReturnLowForMoodValues3To4(mood: Int) {
            val result = Helpers.getMoodDescription(mood)
            assertEquals("Low", result)
        }

        @Test
        @DisplayName("getMoodDescription should return 'Neutral' for mood value 5")
        fun getMoodDescription_shouldReturnNeutralForMoodValue5() {
            val result = Helpers.getMoodDescription(5)
            assertEquals("Neutral", result)
        }

        @ParameterizedTest
        @ValueSource(ints = [6, 7])
        @DisplayName("getMoodDescription should return 'Good' for mood values 6-7")
        fun getMoodDescription_shouldReturnGoodForMoodValues6To7(mood: Int) {
            val result = Helpers.getMoodDescription(mood)
            assertEquals("Good", result)
        }

        @ParameterizedTest
        @ValueSource(ints = [8, 9])
        @DisplayName("getMoodDescription should return 'Very Good' for mood values 8-9")
        fun getMoodDescription_shouldReturnVeryGoodForMoodValues8To9(mood: Int) {
            val result = Helpers.getMoodDescription(mood)
            assertEquals("Very Good", result)
        }

        @Test
        @DisplayName("getMoodDescription should return 'Excellent' for mood value 10")
        fun getMoodDescription_shouldReturnExcellentForMoodValue10() {
            val result = Helpers.getMoodDescription(10)
            assertEquals("Excellent", result)
        }

        @Test
        @DisplayName("getMoodDescription should return 'Unknown' for invalid mood values")
        fun getMoodDescription_shouldReturnUnknownForInvalidMoodValues() {
            val result = Helpers.getMoodDescription(0)
            assertEquals("Unknown", result)
        }

        @ParameterizedTest
        @ValueSource(ints = [1, 2])
        @DisplayName("getMoodEmoji should return crying emoji for mood values 1-2")
        fun getMoodEmoji_shouldReturnCryingEmojiForMoodValues1To2(mood: Int) {
            val result = Helpers.getMoodEmoji(mood)
            assertEquals("😢", result)
        }

        @Test
        @DisplayName("getMoodEmoji should return excellent emoji for mood value 10")
        fun getMoodEmoji_shouldReturnExcellentEmojiForMoodValue10() {
            val result = Helpers.getMoodEmoji(10)
            assertEquals("🤩", result)
        }

        @Test
        @DisplayName("calculateAverageMood should return correct average")
        fun calculateAverageMood_shouldReturnCorrectAverage() {
            val moods = listOf(5, 7, 8, 6, 9)
            val result = Helpers.calculateAverageMood(moods)
            assertEquals(7.0, result)
        }

        @Test
        @DisplayName("calculateAverageMood should return 0.0 for empty list")
        fun calculateAverageMood_shouldReturnZeroForEmptyList() {
            val result = Helpers.calculateAverageMood(emptyList())
            assertEquals(0.0, result)
        }

        @Test
        @DisplayName("getMoodTrend should return 'Improving' when recent mood is better")
        fun getMoodTrend_shouldReturnImprovingWhenRecentMoodIsBetter() {
            val moods = listOf(3, 4, 5, 6, 7, 8) // Recent average: 7, Older average: 4
            val result = Helpers.getMoodTrend(moods)
            assertEquals("Improving", result)
        }

        @Test
        @DisplayName("getMoodTrend should return 'Declining' when recent mood is worse")
        fun getMoodTrend_shouldReturnDecliningWhenRecentMoodIsWorse() {
            val moods = listOf(8, 7, 6, 5, 4, 3) // Recent average: 4, Older average: 7
            val result = Helpers.getMoodTrend(moods)
            assertEquals("Declining", result)
        }

        @Test
        @DisplayName("getMoodTrend should return 'Stable' when mood is stable")
        fun getMoodTrend_shouldReturnStableWhenMoodIsStable() {
            val moods = listOf(5, 6, 5, 6, 5, 6) // Recent average: 5.67, Older average: 5.33
            val result = Helpers.getMoodTrend(moods)
            assertEquals("Stable", result)
        }

        @Test
        @DisplayName("getMoodTrend should return 'Insufficient data' for less than 2 moods")
        fun getMoodTrend_shouldReturnInsufficientDataForLessThan2Moods() {
            val result = Helpers.getMoodTrend(listOf(5))
            assertEquals("Insufficient data", result)
        }
    }

    @Nested
    @DisplayName("Validation Utilities")
    inner class ValidationUtilities {

        @Test
        @DisplayName("isValidEmail should return true for valid email")
        fun isValidEmail_shouldReturnTrueForValidEmail() {
            val validEmails = listOf(
                "test@example.com",
                "user.name@domain.co.uk",
                "user+tag@example.org"
            )
            
            validEmails.forEach { email ->
                assertTrue(Helpers.isValidEmail(email), "Email $email should be valid")
            }
        }

        @Test
        @DisplayName("isValidEmail should return false for invalid email")
        fun isValidEmail_shouldReturnFalseForInvalidEmail() {
            val invalidEmails = listOf(
                "invalid-email",
                "@example.com",
                "test@",
                "test.example.com"
            )
            
            invalidEmails.forEach { email ->
                assertFalse(Helpers.isValidEmail(email), "Email $email should be invalid")
            }
        }

        @Test
        @DisplayName("isValidPassword should return true for valid password")
        fun isValidPassword_shouldReturnTrueForValidPassword() {
            val validPasswords = listOf("123456", "password", "abcdefgh")
            
            validPasswords.forEach { password ->
                assertTrue(Helpers.isValidPassword(password), "Password should be valid")
            }
        }

        @Test
        @DisplayName("isValidPassword should return false for invalid password")
        fun isValidPassword_shouldReturnFalseForInvalidPassword() {
            val invalidPasswords = listOf("12345", "pass", "")
            
            invalidPasswords.forEach { password ->
                assertFalse(Helpers.isValidPassword(password), "Password should be invalid")
            }
        }

        @Test
        @DisplayName("isValidMoodValue should return true for valid mood values")
        fun isValidMoodValue_shouldReturnTrueForValidMoodValues() {
            for (mood in Constants.MIN_MOOD_VALUE..Constants.MAX_MOOD_VALUE) {
                assertTrue(Helpers.isValidMoodValue(mood), "Mood value $mood should be valid")
            }
        }

        @Test
        @DisplayName("isValidMoodValue should return false for invalid mood values")
        fun isValidMoodValue_shouldReturnFalseForInvalidMoodValues() {
            val invalidMoods = listOf(0, 11, -1, 15)
            
            invalidMoods.forEach { mood ->
                assertFalse(Helpers.isValidMoodValue(mood), "Mood value $mood should be invalid")
            }
        }
    }

    @Nested
    @DisplayName("SharedPreferences Utilities")
    inner class SharedPreferencesUtilities {

        @Test
        @DisplayName("saveString should save string value")
        fun saveString_shouldSaveStringValue() {
            val key = "test_key"
            val value = "test_value"
            
            Helpers.saveString(mockContext, key, value)
            
            verify(mockEditor).putString(key, value)
            verify(mockEditor).apply()
        }

        @Test
        @DisplayName("getString should return saved string value")
        fun getString_shouldReturnSavedStringValue() {
            val key = "test_key"
            val value = "test_value"
            
            `when`(mockSharedPreferences.getString(key, "")).thenReturn(value)
            
            val result = Helpers.getString(mockContext, key)
            
            assertEquals(value, result)
        }

        @Test
        @DisplayName("getString should return default value when key not found")
        fun getString_shouldReturnDefaultValueWhenKeyNotFound() {
            val key = "nonexistent_key"
            val defaultValue = "default"
            
            `when`(mockSharedPreferences.getString(key, defaultValue)).thenReturn(null)
            
            val result = Helpers.getString(mockContext, key, defaultValue)
            
            assertEquals(defaultValue, result)
        }

        @Test
        @DisplayName("saveBoolean should save boolean value")
        fun saveBoolean_shouldSaveBooleanValue() {
            val key = "test_key"
            val value = true
            
            Helpers.saveBoolean(mockContext, key, value)
            
            verify(mockEditor).putBoolean(key, value)
            verify(mockEditor).apply()
        }

        @Test
        @DisplayName("getBoolean should return saved boolean value")
        fun getBoolean_shouldReturnSavedBooleanValue() {
            val key = "test_key"
            val value = true
            
            `when`(mockSharedPreferences.getBoolean(key, false)).thenReturn(value)
            
            val result = Helpers.getBoolean(mockContext, key)
            
            assertEquals(value, result)
        }

        @Test
        @DisplayName("saveInt should save int value")
        fun saveInt_shouldSaveIntValue() {
            val key = "test_key"
            val value = 42
            
            Helpers.saveInt(mockContext, key, value)
            
            verify(mockEditor).putInt(key, value)
            verify(mockEditor).apply()
        }

        @Test
        @DisplayName("getInt should return saved int value")
        fun getInt_shouldReturnSavedIntValue() {
            val key = "test_key"
            val value = 42
            
            `when`(mockSharedPreferences.getInt(key, 0)).thenReturn(value)
            
            val result = Helpers.getInt(mockContext, key)
            
            assertEquals(value, result)
        }
    }

    @Nested
    @DisplayName("String Utilities")
    inner class StringUtilities {

        @Test
        @DisplayName("capitalizeFirstLetter should capitalize first letter")
        fun capitalizeFirstLetter_shouldCapitalizeFirstLetter() {
            val result = Helpers.capitalizeFirstLetter("hello world")
            assertEquals("Hello world", result)
        }

        @Test
        @DisplayName("capitalizeFirstLetter should not change already capitalized text")
        fun capitalizeFirstLetter_shouldNotChangeAlreadyCapitalizedText() {
            val result = Helpers.capitalizeFirstLetter("Hello World")
            assertEquals("Hello World", result)
        }

        @Test
        @DisplayName("truncateText should return original text when within limit")
        fun truncateText_shouldReturnOriginalTextWhenWithinLimit() {
            val text = "Hello"
            val result = Helpers.truncateText(text, 10)
            assertEquals(text, result)
        }

        @Test
        @DisplayName("truncateText should truncate text when exceeding limit")
        fun truncateText_shouldTruncateTextWhenExceedingLimit() {
            val text = "Hello World"
            val result = Helpers.truncateText(text, 8)
            assertEquals("Hello...", result)
        }
    }

    @Nested
    @DisplayName("Math Utilities")
    inner class MathUtilities {

        @Test
        @DisplayName("roundToDecimalPlaces should round correctly")
        fun roundToDecimalPlaces_shouldRoundCorrectly() {
            val result = Helpers.roundToDecimalPlaces(3.14159, 2)
            assertEquals(3.14, result, 0.001)
        }

        @Test
        @DisplayName("calculatePercentage should calculate percentage correctly")
        fun calculatePercentage_shouldCalculatePercentageCorrectly() {
            val result = Helpers.calculatePercentage(25, 100)
            assertEquals(25, result)
        }

        @Test
        @DisplayName("calculatePercentage should return 0 when total is 0")
        fun calculatePercentage_shouldReturnZeroWhenTotalIsZero() {
            val result = Helpers.calculatePercentage(25, 0)
            assertEquals(0, result)
        }
    }
}
