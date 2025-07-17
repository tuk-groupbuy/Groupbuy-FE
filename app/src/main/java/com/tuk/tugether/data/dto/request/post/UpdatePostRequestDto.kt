package com.tuk.tugether.data.dto.request.post

data class UpdatePostRequestDto(
    val title: String,
    val content: String,
    val goalQuantity: Int,
    val price: Int,
    val imageUrl: String,
    val deadline: String
)
