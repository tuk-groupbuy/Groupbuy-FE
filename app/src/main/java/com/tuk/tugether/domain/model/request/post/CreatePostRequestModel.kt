package com.tuk.tugether.domain.model.request.post

import com.tuk.tugether.data.dto.request.post.CreatePostRequestDto

data class CreatePostRequestModel(
    val writerId: Long,
    val title: String,
    val content: String,
    val goalQuantity: Int,
    val minParticipants: Int,
    val price: Int,
    val deadline: String
) {
    fun toCreatePostRequestDto() =
        CreatePostRequestDto(writerId, title, content, goalQuantity, minParticipants, price, deadline)
}

