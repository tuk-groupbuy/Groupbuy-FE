package com.tuk.tugether.data.dto.response.notification

data class NotificationResponseDto(
    val notificationId: Long,
    val type: String,
    val content: String,
    val isRead: Boolean,
    val createdAt: String,
    val userId: Long?,  // null 가능
    val currentQuantity: Int,
    val goalQuantity: Int
)
