package com.tuk.tugether.data.dto.response.notification

import com.tuk.tugether.domain.model.response.notification.NotificationResponseModel

data class NotificationResponseDto(
    val postId: Long,
    val notificationId: Long,
    val type: String,
    val content: String,
    val isRead: Boolean,
    val createdAt: String,
    val userId: Long?,
    val currentQuantity: Int,
    val goalQuantity: Int,
    val senderNickname: String,
    val writerId: Long,
    val chatRoomId: Long
)

fun NotificationResponseDto.toModel(): NotificationResponseModel =
    NotificationResponseModel(
        postId = postId,
        notificationId = notificationId,
        type = type,
        content = content,
        isRead = isRead,
        createdAt = createdAt,
        userId = userId,
        currentQuantity = currentQuantity,
        goalQuantity = goalQuantity,
        senderNickname = senderNickname,
        writerId,
        chatRoomId
    )