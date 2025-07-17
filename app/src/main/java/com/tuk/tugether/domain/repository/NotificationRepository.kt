package com.tuk.tugether.domain.repository

import com.tuk.tugether.data.dto.request.notification.NotificationApproveRequestDto
import com.tuk.tugether.data.dto.request.notification.NotificationDecisionRequestDto
import com.tuk.tugether.domain.model.response.notification.NotificationResponseModel

interface NotificationRepository {
    suspend fun getNotifications(userId: Long): List<NotificationResponseModel>
    suspend fun approveNotification(request: NotificationApproveRequestDto): String
    suspend fun rejectNotification(request: NotificationDecisionRequestDto): String


}
