package com.tuk.tugether.domain.model.response.notification

import com.tuk.tugether.data.dto.response.notification.NotificationResponseDto

data class NotificationResponseModel (
    val notificationId: Long,
    val type: String,
    val content: String,
    val isRead: Boolean,
    val createdAt: String,
    val userId: Long?,
    val currentQuantity: Int,
    val goalQuantity: Int
)

fun NotificationResponseDto.toNotificationResponseModel(): NotificationResponseModel =
    NotificationResponseModel(
        notificationId = notificationId,
        type = type,
        content = content,
        isRead = isRead,
        createdAt = createdAt,
        userId = userId,
        currentQuantity = currentQuantity,
        goalQuantity = goalQuantity
    )