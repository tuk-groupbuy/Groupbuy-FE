package com.tuk.tugether.domain.model.request.post

import com.tuk.tugether.data.dto.request.post.UpdatePostRequestDto

data class UpdatePostRequestModel(
    val title: String,
    val content: String,
    val goalQuantity: Int,
    val price: Int,
    val imageUrl: String,
    val deadline: String
) {
    fun toDto(): UpdatePostRequestDto = UpdatePostRequestDto(
        title = title,
        content = content,
        goalQuantity = goalQuantity,
        price = price,
        imageUrl = imageUrl,
        deadline = deadline
    )
}
