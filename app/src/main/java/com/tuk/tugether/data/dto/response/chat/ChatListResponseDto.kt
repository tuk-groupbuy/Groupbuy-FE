package com.tuk.tugether.data.dto.response.chat

import com.tuk.tugether.domain.model.response.ChatListResponseModel

data class ChatListResponseDto(
    val success: Boolean,
    val message: String,
    val requestChatRoomSaveDTOS: List<ChatRoomDto>
) {
    data class ChatRoomDto(
        val chatRoomId: Long,
        val title: String,
        val imageUrl: String,
        val lastChatMessage: String,
        val isRead: Boolean?,
        val sendAt: String?
    ) {
        fun toChatRoomPreviewModel() = ChatListResponseModel.ChatRoomModel(
            chatRoomId = chatRoomId,
            title = title,
            imageUrl = imageUrl,
            lastChatMessage = lastChatMessage,
            isRead = isRead,
            sendAt = sendAt
        )
    }

    fun toChatListResponseModel() = ChatListResponseModel(
        success,
        message,
        requestChatRoomSaveDTOS = requestChatRoomSaveDTOS.map { it.toChatRoomPreviewModel() }
    )
}
