package com.serenityai.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Settings
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class DashboardItemTest {
    
    @Test
    fun `DashboardItem should be created with correct properties`() {
        val item = DashboardItem(
            title = "Test Title",
            description = "Test Description",
            icon = Icons.Default.Chat,
            onClick = {}
        )
        
        assertEquals("Test Title", item.title)
        assertEquals("Test Description", item.description)
        assertEquals(Icons.Default.Chat, item.icon)
        assertNotNull(item.onClick)
    }
    
    @Test
    fun `DashboardItem should handle empty title`() {
        val item = DashboardItem(
            title = "",
            description = "Test Description",
            icon = Icons.Default.Settings,
            onClick = {}
        )
        
        assertEquals("", item.title)
        assertEquals("Test Description", item.description)
    }
    
    @Test
    fun `DashboardItem should handle empty description`() {
        val item = DashboardItem(
            title = "Test Title",
            description = "",
            icon = Icons.Default.Chat,
            onClick = {}
        )
        
        assertEquals("Test Title", item.title)
        assertEquals("", item.description)
    }
    
    @Test
    fun `DashboardItem should handle long text`() {
        val longTitle = "This is a very long title that might be used for testing purposes"
        val longDescription = "This is a very long description that contains multiple sentences. " +
                "It should be able to handle text of any length without issues. " +
                "The description can contain special characters and emojis 🎉😊"
        
        val item = DashboardItem(
            title = longTitle,
            description = longDescription,
            icon = Icons.Default.Settings,
            onClick = {}
        )
        
        assertEquals(longTitle, item.title)
        assertEquals(longDescription, item.description)
    }
    
    @Test
    fun `DashboardItem should handle special characters`() {
        val item = DashboardItem(
            title = "Title with @#$%^&*() characters",
            description = "Description with unicode: ñáéíóú 世界 🌍",
            icon = Icons.Default.Chat,
            onClick = {}
        )
        
        assertEquals("Title with @#$%^&*() characters", item.title)
        assertEquals("Description with unicode: ñáéíóú 世界 🌍", item.description)
    }
    
    @Test
    fun `DashboardItem should handle different icons`() {
        val chatItem = DashboardItem(
            title = "Chat",
            description = "Chat description",
            icon = Icons.Default.Chat,
            onClick = {}
        )
        
        val settingsItem = DashboardItem(
            title = "Settings",
            description = "Settings description",
            icon = Icons.Default.Settings,
            onClick = {}
        )
        
        assertEquals(Icons.Default.Chat, chatItem.icon)
        assertEquals(Icons.Default.Settings, settingsItem.icon)
    }
    
    @Test
    fun `DashboardItem onClick should be callable`() {
        var clicked = false
        val item = DashboardItem(
            title = "Test",
            description = "Test",
            icon = Icons.Default.Chat,
            onClick = { clicked = true }
        )
        
        item.onClick()
        assertTrue(clicked)
    }
    
    @Test
    fun `DashboardItem onClick should be callable multiple times`() {
        var clickCount = 0
        val item = DashboardItem(
            title = "Test",
            description = "Test",
            icon = Icons.Default.Chat,
            onClick = { clickCount++ }
        )
        
        item.onClick()
        item.onClick()
        item.onClick()
        
        assertEquals(3, clickCount)
    }
    
    @Test
    fun `DashboardItem should handle null onClick gracefully`() {
        val item = DashboardItem(
            title = "Test",
            description = "Test",
            icon = Icons.Default.Chat,
            onClick = {}
        )
        
        // Should not throw exception
        assertDoesNotThrow { item.onClick() }
    }
}
