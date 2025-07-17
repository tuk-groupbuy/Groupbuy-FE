package com.tuk.tugether.data.datasource

import com.tuk.tugether.data.dto.request.notification.NotificationApproveRequestDto
import com.tuk.tugether.data.dto.request.notification.NotificationDecisionRequestDto
import com.tuk.tugether.data.dto.response.notification.NotificationResponseDto

interface NotificationDataSource {
    suspend fun getNotifications(userId: Long): List<NotificationResponseDto>
    suspend fun approveNotification(request: NotificationApproveRequestDto): String
    suspend fun rejectNotification(request: NotificationDecisionRequestDto): String


}
