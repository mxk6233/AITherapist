package com.serenityai.tests.unit.ui.screens

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class ChatMessageTest {
    
    @Test
    fun `ChatMessage should be created with correct properties`() {
        val message = ChatMessage(
            id = "123",
            text = "Hello world",
            isFromUser = true
        )
        
        assertEquals("123", message.id)
        assertEquals("Hello world", message.text)
        assertTrue(message.isFromUser)
        assertTrue(message.timestamp > 0)
    }
    
    @Test
    fun `ChatMessage should have default timestamp`() {
        val message1 = ChatMessage(
            id = "1",
            text = "Test",
            isFromUser = true
        )
        
        val message2 = ChatMessage(
            id = "2",
            text = "Test",
            isFromUser = false
        )
        
        assertTrue(message1.timestamp > 0)
        assertTrue(message2.timestamp > 0)
        assertTrue(message1.timestamp <= message2.timestamp)
    }
    
    @Test
    fun `ChatMessage should handle empty text`() {
        val message = ChatMessage(
            id = "123",
            text = "",
            isFromUser = true
        )
        
        assertEquals("", message.text)
        assertTrue(message.isFromUser)
    }
    
    @Test
    fun `ChatMessage should handle long text`() {
        val longText = "This is a very long message that contains multiple sentences. " +
                "It should be able to handle text of any length without issues. " +
                "The message can contain special characters like @#$%^&*() and emojis 🎉😊"
        
        val message = ChatMessage(
            id = "123",
            text = longText,
            isFromUser = false
        )
        
        assertEquals(longText, message.text)
        assertFalse(message.isFromUser)
    }
    
    @Test
    fun `ChatMessage should handle special characters in id`() {
        val message = ChatMessage(
            id = "msg-123_abc@test",
            text = "Test message",
            isFromUser = true
        )
        
        assertEquals("msg-123_abc@test", message.id)
    }
    
    @Test
    fun `ChatMessage should be created for both user and AI`() {
        val userMessage = ChatMessage(
            id = "user-1",
            text = "Hello AI",
            isFromUser = true
        )
        
        val aiMessage = ChatMessage(
            id = "ai-1",
            text = "Hello! How can I help you?",
            isFromUser = false
        )
        
        assertTrue(userMessage.isFromUser)
        assertFalse(aiMessage.isFromUser)
        assertEquals("Hello AI", userMessage.text)
        assertEquals("Hello! How can I help you?", aiMessage.text)
    }
    
    @Test
    fun `ChatMessage should handle unicode characters`() {
        val message = ChatMessage(
            id = "unicode-test",
            text = "Hello 世界! 🌍 This is a test with unicode characters: ñáéíóú",
            isFromUser = true
        )
        
        assertEquals("Hello 世界! 🌍 This is a test with unicode characters: ñáéíóú", message.text)
    }
    
    @Test
    fun `ChatMessage should have consistent timestamp ordering`() {
        val message1 = ChatMessage(
            id = "1",
            text = "First message",
            isFromUser = true
        )
        
        // Small delay to ensure different timestamps
        Thread.sleep(1)
        
        val message2 = ChatMessage(
            id = "2",
            text = "Second message",
            isFromUser = false
        )
        
        assertTrue(message1.timestamp < message2.timestamp)
    }
}
