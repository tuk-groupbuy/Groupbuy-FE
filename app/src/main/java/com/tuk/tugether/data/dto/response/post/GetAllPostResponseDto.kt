package com.tuk.tugether.data.dto.response.post

import com.tuk.tugether.domain.model.response.post.GetAllPostResponseModel

data class GetAllPostResponseDto(
    val postId: Long,
    val title: String,
    val imageUrl: String,
    val currentQuantity: Int,
    val goalQuantity: Int,
    val price: Int,
    val deadlineText: String,
    val completed: Boolean
)

fun GetAllPostResponseDto.toModel(): GetAllPostResponseModel =
    GetAllPostResponseModel(
        postId = postId,
        title = title,
        imageUrl = imageUrl,
        currentQuantity = currentQuantity,
        goalQuantity = goalQuantity,
        price = price,
        deadlineText = deadlineText,
        completed = completed
    )