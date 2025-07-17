package com.tuk.tugether.domain.model.response

data class CommonChatResponseModel(
    val success: Boolean,
    val message: String,
    val chatRoomUserId: Long
)
