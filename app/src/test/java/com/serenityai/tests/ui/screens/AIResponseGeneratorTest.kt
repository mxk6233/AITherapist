package com.serenityai.tests.ui.screens

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class AIResponseGeneratorTest {
    
    @Test
    fun `generateAIResponse should return a response`() {
        val response = generateAIResponse("hello")
        assertNotNull(response)
        assertTrue(response.isNotEmpty())
    }
    
    @Test
    fun `generateAIResponse should return different responses`() {
        val responses = mutableSetOf<String>()
        
        // Generate multiple responses to test randomness
        repeat(50) {
            responses.add(generateAIResponse("test message"))
        }
        
        // Should have multiple different responses due to randomness
        assertTrue(responses.size > 1, "Should generate different responses")
    }
    
    @Test
    fun `generateAIResponse should return meaningful responses`() {
        val response = generateAIResponse("I'm feeling anxious today")
        assertNotNull(response)
        assertTrue(response.isNotEmpty())
        assertTrue(response.length > 10) // Should be meaningful responses
    }
    
    @Test
    fun `generateAIResponse should handle empty input`() {
        val response = generateAIResponse("")
        assertNotNull(response)
        assertTrue(response.isNotEmpty())
    }
    
    @Test
    fun `generateAIResponse should handle any input`() {
        val testInputs = listOf(
            "hello",
            "I'm feeling sad",
            "thank you",
            "random text",
            "HELLO",
            "Hello",
            "special chars: @#$%^&*()",
            "unicode: ñáéíóú 世界 🌍"
        )
        
        testInputs.forEach { input ->
            val response = generateAIResponse(input)
            assertNotNull(response)
            assertTrue(response.isNotEmpty())
        }
    }
    
    @Test
    fun `generateAIResponse should return responses from predefined list`() {
        val expectedResponses = listOf(
            "I understand how you're feeling. Can you tell me more about what's been on your mind?",
            "That sounds challenging. It's important to acknowledge these feelings. What do you think might help you feel better?",
            "Thank you for sharing that with me. How long have you been experiencing this?",
            "I hear you. It takes courage to talk about these things. What would you like to focus on today?",
            "That's a valid concern. Let's explore this together. What's your biggest worry about this situation?",
            "I appreciate you being open with me. How does this make you feel physically?",
            "It sounds like you're going through a lot right now. What support do you have in your life?",
            "I can sense this is important to you. What would you like to achieve from our conversation today?"
        )
        
        val responses = mutableSetOf<String>()
        
        // Generate many responses to ensure we get all possible ones
        repeat(100) {
            responses.add(generateAIResponse("test"))
        }
        
        // All responses should be from the expected list
        responses.forEach { response ->
            assertTrue(expectedResponses.contains(response), "Response should be from predefined list: $response")
        }
    }
    
    @Test
    fun `generateAIResponse should provide helpful responses`() {
        val responses = listOf(
            generateAIResponse("hello"),
            generateAIResponse("I'm feeling anxious"),
            generateAIResponse("thank you"),
            generateAIResponse("random text")
        )
        
        responses.forEach { response ->
            assertNotNull(response)
            assertTrue(response.isNotEmpty())
            assertTrue(response.length > 10) // Should be meaningful responses
            assertTrue(response.contains("?")) // Should ask questions to engage
        }
    }
}
