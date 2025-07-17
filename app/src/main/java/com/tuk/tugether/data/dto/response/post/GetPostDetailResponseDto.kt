package com.tuk.tugether.data.dto.response.post

import com.tuk.tugether.domain.model.response.post.GetPostDetailResponseModel

data class GetPostDetailResponseDto(
    val postId: Long,
    val title: String,
    val content: String,
    val goalQuantity: Int,
    val price: Int,
    val imageUrl: String,
    val currentQuantity: Int,
    val createdAt: String,
    val writerName: String,
    val writerId: Long,
    val deadlineText: String,
    val isWriter: Boolean,
    val participationStatus: String,
    val completed: Boolean
)

fun GetPostDetailResponseDto.toModel(): GetPostDetailResponseModel =
    GetPostDetailResponseModel(
        postId = postId,
        title = title,
        content = content,
        goalQuantity = goalQuantity,
        price = price,
        imageUrl = imageUrl,
        currentQuantity = currentQuantity,
        createdAt = createdAt,
        writerName = writerName,
        writerId = writerId,
        deadlineText = deadlineText,
        isWriter = isWriter,
        participationStatus = participationStatus,
        completed = completed
    )
