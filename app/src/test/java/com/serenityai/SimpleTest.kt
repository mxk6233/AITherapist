package com.serenityai

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class SimpleTest {
    
    @Test
    fun `simple addition test`() {
        assertEquals(4, 2 + 2)
    }
    
    @Test
    fun `simple string test`() {
        val message = "Hello World"
        assertTrue(message.contains("Hello"))
        assertTrue(message.contains("World"))
    }
    
    @Test
    fun `simple boolean test`() {
        assertTrue(true)
        assertFalse(false)
    }
}
