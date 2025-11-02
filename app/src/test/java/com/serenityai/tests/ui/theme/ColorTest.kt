package com.serenityai.tests.ui.theme

import androidx.compose.ui.graphics.Color
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class ColorTest {
    
    @Test
    fun `color values should not be null`() {
        assertNotNull(Purple40)
        assertNotNull(Purple80)
        assertNotNull(PurpleGrey40)
        assertNotNull(PurpleGrey80)
        assertNotNull(Pink40)
        assertNotNull(Pink80)
    }
    
    @Test
    fun `color values should be Color objects`() {
        assertTrue(Purple40 is Color)
        assertTrue(Purple80 is Color)
        assertTrue(PurpleGrey40 is Color)
        assertTrue(PurpleGrey80 is Color)
        assertTrue(Pink40 is Color)
        assertTrue(Pink80 is Color)
    }
    
    @Test
    fun `color values should be different from each other`() {
        val colors = listOf(Purple40, Purple80, PurpleGrey40, PurpleGrey80, Pink40, Pink80)
        val uniqueColors = colors.toSet()
        
        assertEquals(colors.size, uniqueColors.size, "All colors should be unique")
    }
    
    @Test
    fun `color values should have valid alpha values`() {
        // All colors should have alpha values between 0 and 1
        assertTrue(Purple40.alpha >= 0f && Purple40.alpha <= 1f)
        assertTrue(Purple80.alpha >= 0f && Purple80.alpha <= 1f)
        assertTrue(PurpleGrey40.alpha >= 0f && PurpleGrey40.alpha <= 1f)
        assertTrue(PurpleGrey80.alpha >= 0f && PurpleGrey80.alpha <= 1f)
        assertTrue(Pink40.alpha >= 0f && Pink40.alpha <= 1f)
        assertTrue(Pink80.alpha >= 0f && Pink80.alpha <= 1f)
    }
    
    @Test
    fun `color values should have valid red values`() {
        // All colors should have red values between 0 and 1
        assertTrue(Purple40.red >= 0f && Purple40.red <= 1f)
        assertTrue(Purple80.red >= 0f && Purple80.red <= 1f)
        assertTrue(PurpleGrey40.red >= 0f && PurpleGrey40.red <= 1f)
        assertTrue(PurpleGrey80.red >= 0f && PurpleGrey80.red <= 1f)
        assertTrue(Pink40.red >= 0f && Pink40.red <= 1f)
        assertTrue(Pink80.red >= 0f && Pink80.red <= 1f)
    }
    
    @Test
    fun `color values should have valid green values`() {
        // All colors should have green values between 0 and 1
        assertTrue(Purple40.green >= 0f && Purple40.green <= 1f)
        assertTrue(Purple80.green >= 0f && Purple80.green <= 1f)
        assertTrue(PurpleGrey40.green >= 0f && PurpleGrey40.green <= 1f)
        assertTrue(PurpleGrey80.green >= 0f && PurpleGrey80.green <= 1f)
        assertTrue(Pink40.green >= 0f && Pink40.green <= 1f)
        assertTrue(Pink80.green >= 0f && Pink80.green <= 1f)
    }
    
    @Test
    fun `color values should have valid blue values`() {
        // All colors should have blue values between 0 and 1
        assertTrue(Purple40.blue >= 0f && Purple40.blue <= 1f)
        assertTrue(Purple80.blue >= 0f && Purple80.blue <= 1f)
        assertTrue(PurpleGrey40.blue >= 0f && PurpleGrey40.blue <= 1f)
        assertTrue(PurpleGrey80.blue >= 0f && PurpleGrey80.blue <= 1f)
        assertTrue(Pink40.blue >= 0f && Pink40.blue <= 1f)
        assertTrue(Pink80.blue >= 0f && Pink80.blue <= 1f)
    }
}