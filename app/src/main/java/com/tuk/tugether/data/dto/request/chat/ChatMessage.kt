package com.tuk.tugether.data.dto.request.chat

data class ChatMessage(
    val type: String,
    val sender: String,
    val chatRoomId: Long,
    val content: String,
)