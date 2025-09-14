package com.serenityai.data.remote

import retrofit2.http.Body
import retrofit2.http.POST

data class OpenAIRequest(
    val model: String = "gpt-3.5-turbo",
    val messages: List<OpenAIMessage>,
    val max_tokens: Int = 500,
    val temperature: Double = 0.7
)

data class OpenAIResponse(
    val choices: List<OpenAIChoice>
)

data class OpenAIChoice(
    val message: OpenAIMessage
)

data class OpenAIMessage(
    val role: String, // "user", "assistant", "system"
    val content: String
)

interface OpenAIApiService {
    @POST("v1/chat/completions")
    suspend fun getResponse(
        @Body request: OpenAIRequest
    ): OpenAIResponse
    
    companion object {
        const val BASE_URL = "https://api.openai.com/"
    }
}

class OpenAIRepository(private val apiService: OpenAIApiService) {
    suspend fun generateTherapistResponse(
        userMessage: String,
        context: String = "",
        apiKey: String
    ): Result<String> {
        return try {
            val systemPrompt = """
                You are a compassionate AI therapist. Your role is to:
                - Listen actively and empathetically
                - Provide supportive and non-judgmental responses
                - Ask thoughtful questions to help users explore their feelings
                - Offer gentle guidance and coping strategies
                - Maintain professional boundaries
                - Encourage professional help when appropriate
                
                Context: $context
            """.trimIndent()

            val messages = listOf(
                OpenAIMessage(role = "system", content = systemPrompt),
                OpenAIMessage(role = "user", content = userMessage)
            )

            val request = OpenAIRequest(
                model = "gpt-3.5-turbo",
                messages = messages,
                max_tokens = 500,
                temperature = 0.7
            )

            val response = apiService.getResponse(request)
            val aiResponse = response.choices.firstOrNull()?.message?.content
            if (aiResponse != null) {
                Result.success(aiResponse)
            } else {
                Result.failure(Exception("Empty response from AI"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}