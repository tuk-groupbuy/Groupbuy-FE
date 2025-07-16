package com.tuk.tugether.data.dto.response.chat

import com.tuk.tugether.domain.model.response.ChatMessageResponseModel

data class ChatMessageResponseDto(
    val responseChatMessageGetDTOS: List<ChatMessageDto>
) {
    data class ChatMessageDto(
        val sender: String,
        val content: String,
        val sendAt: String
    ) {
        fun toChatMessageModel() = ChatMessageResponseModel.ChatMessageModel(
            sender = sender,
            content = content,
            sendAt = sendAt
        )
    }

    fun toChatMessageResponseModel() = ChatMessageResponseModel(
        messages = responseChatMessageGetDTOS.map { it.toChatMessageModel() }
    )
}

