package org.jrtech.ai.mychatgpt

import com.azure.ai.openai.OpenAIClient
import com.azure.ai.openai.OpenAIClientBuilder
import com.azure.ai.openai.models.*
import com.azure.core.credential.TokenCredential
import com.azure.identity.DefaultAzureCredentialBuilder
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class ChatController {
    private val openAIClient: OpenAIClient

    init {
        val credential: TokenCredential = DefaultAzureCredentialBuilder().build()
        openAIClient = OpenAIClientBuilder()
            .credential(credential)
            .endpoint(System.getenv("AZURE_OPENAI_ENDPOINT"))
            .buildClient()
    }

    @PostMapping("/chat")
    fun chat(@AuthenticationPrincipal principal: Jwt, @RequestBody request: ChatRequest): ChatMessage {
        val userId = principal.subject
        val messages = request.messages.takeLast(10) // Limit to last 10 messages

        val chatMessages = mutableListOf<ChatMessage>()
        chatMessages.add(toChatMessage(ChatRole.SYSTEM, request.systemMessage))
        chatMessages.addAll(messages.map { toChatMessage(it.role, it.content) })

        val options = ChatCompletionsOptions(chatMessages)
            .setTemperature(request.temperature)
            .setMaxTokens(request.maxTokens)

        val chatCompletions = openAIClient.getChatCompletions("gpt-4", options)

        val completion = chatCompletions.choices[0].message
        return toChatMessage(completion.role, completion.content)
    }

    private fun toChatMessage(role: ChatRole, content: String): ChatMessage {
        val chatMessage = ChatMessage(role)
        chatMessage.content = content
        return chatMessage
    }
}

data class ChatRequest(
    val messages: List<ChatMessage>,
    val systemMessage: String,
    val temperature: Double,
    val maxTokens: Int
)

