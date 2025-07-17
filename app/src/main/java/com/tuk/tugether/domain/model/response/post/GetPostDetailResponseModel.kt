package com.tuk.tugether.domain.model.response.post

data class GetPostDetailResponseModel(
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
