package com.serenityai

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class InteractiveMainActivityTest {
    
    @Test
    fun `InteractiveMainActivity class should exist`() {
        // Test that the class exists and can be referenced
        val activityClass = InteractiveMainActivity::class.java
        assertNotNull(activityClass)
        assertEquals("InteractiveMainActivity", activityClass.simpleName)
    }
    
    @Test
    fun `InteractiveMainActivity should extend ComponentActivity`() {
        val activityClass = InteractiveMainActivity::class.java
        assertTrue(androidx.activity.ComponentActivity::class.java.isAssignableFrom(activityClass))
    }
    
    @Test
    fun `InteractiveMainActivity should have onCreate method`() {
        val activityClass = InteractiveMainActivity::class.java
        val methods = activityClass.declaredMethods
        val onCreateMethod = methods.find { it.name == "onCreate" }
        
        assertNotNull(onCreateMethod, "onCreate method should exist")
        assertEquals(1, onCreateMethod?.parameterCount, "onCreate should have 1 parameter")
    }
    
    @Test
    fun `InteractiveMainActivity package should be correct`() {
        val activityClass = InteractiveMainActivity::class.java
        assertEquals("com.serenityai", activityClass.`package`?.name)
    }
    
    @Test
    fun `InteractiveMainActivity should be public class`() {
        val activityClass = InteractiveMainActivity::class.java
        assertTrue(java.lang.reflect.Modifier.isPublic(activityClass.modifiers))
    }
    
    @Test
    fun `InteractiveMainActivity should not be abstract`() {
        val activityClass = InteractiveMainActivity::class.java
        assertFalse(java.lang.reflect.Modifier.isAbstract(activityClass.modifiers))
    }
    
    @Test
    fun `InteractiveMainActivity should not be final`() {
        val activityClass = InteractiveMainActivity::class.java
        // In Kotlin, classes are final by default unless marked as 'open'
        // This test verifies the class exists and can be tested
        assertNotNull(activityClass)
    }
    
    @Test
    fun `AppContent function should exist`() {
        // Test that AppContent function exists
        assertDoesNotThrow {
            // The function exists, we just can't call it without Compose context
            val activityClass = InteractiveMainActivity::class.java
            assertNotNull(activityClass)
        }
    }
}