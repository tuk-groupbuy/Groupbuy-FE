package com.tuk.tugether.domain.repository

import com.tuk.tugether.domain.model.response.notification.NotificationResponseModel

interface NotificationRepository {
    suspend fun getNotifications(userId: Long): List<NotificationResponseModel>
}
