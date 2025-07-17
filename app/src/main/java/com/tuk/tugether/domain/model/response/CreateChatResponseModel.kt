package com.tuk.tugether.domain.model.response

data class CreateChatResponseModel(
    val success: Boolean,
    val message: String,
    val chatRoomId: Long
)
