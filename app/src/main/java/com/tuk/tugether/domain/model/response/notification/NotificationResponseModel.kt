package com.tuk.tugether.domain.model.response.notification

data class NotificationResponseModel (
    val postId: Long,
    val notificationId: Long,
    val type: String,
    val content: String,
    val isRead: Boolean,
    val createdAt: String,
    val userId: Long?,
    val currentQuantity: Int,
    val goalQuantity: Int,
    val senderNickname: String
)