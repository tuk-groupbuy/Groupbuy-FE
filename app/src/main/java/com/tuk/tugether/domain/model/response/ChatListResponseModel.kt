package com.tuk.tugether.domain.model.response

data class ChatListResponseModel(
    val success: Boolean,
    val message: String,
    val requestChatRoomSaveDTOS: List<ChatRoomModel>
) {
    data class ChatRoomModel(
        val chatRoomId: Long,
        val title: String,
        val imageUrl: String,
        val lastChatMessage: String,
        val isRead: Boolean?,
        val sendAt: String?
    )
}
