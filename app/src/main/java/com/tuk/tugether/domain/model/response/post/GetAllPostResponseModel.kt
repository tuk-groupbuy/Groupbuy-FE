package com.tuk.tugether.domain.model.response.post

data class GetAllPostResponseModel(
    val postId: Long,
    val title: String,
    val imageUrl: String,
    val currentQuantity: Int,
    val goalQuantity: Int,
    val price: Int,
    val deadlineText: String,
    val completed: Boolean
)
