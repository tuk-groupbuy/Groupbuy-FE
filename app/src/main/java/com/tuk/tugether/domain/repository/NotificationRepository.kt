package com.tuk.tugether.domain.repository

import com.tuk.tugether.domain.model.request.notification.NotificationApproveRequestModel
import com.tuk.tugether.domain.model.request.notification.NotificationDecisionRequestModel
import com.tuk.tugether.domain.model.response.notification.NotificationResponseModel

interface NotificationRepository {
    suspend fun getNotifications(userId: Long): Result<List<NotificationResponseModel>>
    suspend fun approveNotification(request: NotificationApproveRequestModel): Result<String>
    suspend fun rejectNotification(request: NotificationDecisionRequestModel): Result<String>
}