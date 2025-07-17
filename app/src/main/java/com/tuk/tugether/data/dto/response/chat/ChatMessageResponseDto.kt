package com.tuk.tugether.data.dto.response.chat

import com.google.gson.annotations.SerializedName
import com.tuk.tugether.domain.model.response.ChatMessageResponseModel
import com.tuk.tugether.presentation.chat.adapter.ChatRVA.Companion.RECEIVER
import com.tuk.tugether.presentation.chat.adapter.ChatRVA.Companion.SENDER

data class ChatMessagePageResponseDto(
    @SerializedName("responseChatMessageGetDTOS")
    val responseChatMessageGetDTOS: List<ChatMessageResponseDto.ChatMessageDto>,
    @SerializedName("last")
    val isLast: Boolean
)

data class ChatMessageResponseDto(
    val success: Boolean,
    val message: String,
    @SerializedName("responseChatMessagePageGetDTO")
    val responseChatMessagePageGetDTO: ChatMessagePageResponseDto
) {
    data class ChatMessageDto(
        val sender: String,
        val content: String,
        val sendAt: String
    ) {
        fun toChatMessageModel(nickname: String) = ChatMessageResponseModel.ChatMessageModel(
            sender = sender,
            content = content,
            if (nickname == sender) SENDER else RECEIVER,
            sendAt.slice(0..15)
        )
    }

    fun toChatMessageResponseModel(nickname: String): ChatMessageResponseModel {
        return ChatMessageResponseModel(
            success = success,
            message = message,
            messages = responseChatMessagePageGetDTO.responseChatMessageGetDTOS.map { it.toChatMessageModel(nickname) },
            isLast = responseChatMessagePageGetDTO.isLast
        )
    }
}
