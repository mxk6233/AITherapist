package com.serenityai.tests.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Emergency
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class ChatFeatureTest {
    
    @Test
    fun `ChatFeature should be created with correct properties`() {
        val feature = ChatFeature(
            title = "AI Chat Session",
            description = "Engage in a personalized AI therapy session.",
            icon = Icons.Default.Chat,
            onClick = {}
        )
        
        assertEquals("AI Chat Session", feature.title)
        assertEquals("Engage in a personalized AI therapy session.", feature.description)
        assertEquals(Icons.Default.Chat, feature.icon)
        assertNotNull(feature.onClick)
    }
    
    @Test
    fun `ChatFeature should handle different chat features`() {
        val aiChat = ChatFeature(
            title = "AI Chat Session",
            description = "Engage in a personalized AI therapy session.",
            icon = Icons.Default.Chat,
            onClick = {}
        )
        
        val chatHistory = ChatFeature(
            title = "Chat History",
            description = "Review past conversations and insights.",
            icon = Icons.Default.History,
            onClick = {}
        )
        
        val crisisIntervention = ChatFeature(
            title = "Crisis Intervention",
            description = "Access immediate support during a crisis.",
            icon = Icons.Default.Emergency,
            onClick = {}
        )
        
        assertEquals("AI Chat Session", aiChat.title)
        assertEquals("Chat History", chatHistory.title)
        assertEquals("Crisis Intervention", crisisIntervention.title)
        
        assertEquals(Icons.Default.Chat, aiChat.icon)
        assertEquals(Icons.Default.History, chatHistory.icon)
        assertEquals(Icons.Default.Emergency, crisisIntervention.icon)
    }
    
    @Test
    fun `ChatFeature should handle empty title`() {
        val feature = ChatFeature(
            title = "",
            description = "Test Description",
            icon = Icons.Default.Chat,
            onClick = {}
        )
        
        assertEquals("", feature.title)
        assertEquals("Test Description", feature.description)
    }
    
    @Test
    fun `ChatFeature should handle empty description`() {
        val feature = ChatFeature(
            title = "Test Title",
            description = "",
            icon = Icons.Default.Chat,
            onClick = {}
        )
        
        assertEquals("Test Title", feature.title)
        assertEquals("", feature.description)
    }
    
    @Test
    fun `ChatFeature should handle long text`() {
        val longTitle = "This is a very long title for a chat feature that might be used for testing"
        val longDescription = "This is a very long description that contains multiple sentences. " +
                "It should be able to handle text of any length without issues. " +
                "The description can contain special characters and emojis 🎉😊"
        
        val feature = ChatFeature(
            title = longTitle,
            description = longDescription,
            icon = Icons.Default.Chat,
            onClick = {}
        )
        
        assertEquals(longTitle, feature.title)
        assertEquals(longDescription, feature.description)
    }
    
    @Test
    fun `ChatFeature should handle special characters`() {
        val feature = ChatFeature(
            title = "Title with @#$%^&*() characters",
            description = "Description with unicode: ñáéíóú 世界 🌍",
            icon = Icons.Default.Chat,
            onClick = {}
        )
        
        assertEquals("Title with @#$%^&*() characters", feature.title)
        assertEquals("Description with unicode: ñáéíóú 世界 🌍", feature.description)
    }
    
    @Test
    fun `ChatFeature onClick should be callable`() {
        var clicked = false
        val feature = ChatFeature(
            title = "Test",
            description = "Test",
            icon = Icons.Default.Chat,
            onClick = { clicked = true }
        )
        
        feature.onClick()
        assertTrue(clicked)
    }
    
    @Test
    fun `ChatFeature onClick should be callable multiple times`() {
        var clickCount = 0
        val feature = ChatFeature(
            title = "Test",
            description = "Test",
            icon = Icons.Default.Chat,
            onClick = { clickCount++ }
        )
        
        feature.onClick()
        feature.onClick()
        feature.onClick()
        
        assertEquals(3, clickCount)
    }
    
    @Test
    fun `ChatFeature should handle different icons`() {
        val chatFeature = ChatFeature(
            title = "Chat",
            description = "Chat description",
            icon = Icons.Default.Chat,
            onClick = {}
        )
        
        val historyFeature = ChatFeature(
            title = "History",
            description = "History description",
            icon = Icons.Default.History,
            onClick = {}
        )
        
        val emergencyFeature = ChatFeature(
            title = "Emergency",
            description = "Emergency description",
            icon = Icons.Default.Emergency,
            onClick = {}
        )
        
        assertEquals(Icons.Default.Chat, chatFeature.icon)
        assertEquals(Icons.Default.History, historyFeature.icon)
        assertEquals(Icons.Default.Emergency, emergencyFeature.icon)
    }
}
