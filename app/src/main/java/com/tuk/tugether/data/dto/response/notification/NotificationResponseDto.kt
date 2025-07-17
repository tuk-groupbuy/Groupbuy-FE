package com.tuk.tugether.data.dto.response.notification

import com.tuk.tugether.domain.model.response.notification.NotificationResponseModel

data class NotificationResponseDto(
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

fun NotificationResponseDto.toModel(): NotificationResponseModel =
    NotificationResponseModel(
        notificationId = notificationId,
        type = type,
        content = content,
        isRead = isRead,
        createdAt = createdAt,
        userId = userId,
        currentQuantity = currentQuantity,
        goalQuantity = goalQuantity,
        senderNickname = senderNickname
    )