package com.tuk.tugether.data.dto.request.post

data class CreatePostRequestDto(
    val writerId: Long,
    val title: String,
    val content: String,
    val goalQuantity: Int,
    val minParticipants: Int,
    val price: Int,
    val deadline: String // ISO 8601 형식 ex: 2025-07-20T23:59:00
)


