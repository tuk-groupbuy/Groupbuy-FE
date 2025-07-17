package com.tuk.tugether.data.datasource

import com.tuk.tugether.data.dto.request.notification.NotificationApproveRequestDto
import com.tuk.tugether.data.dto.request.notification.NotificationDecisionRequestDto
import com.tuk.tugether.data.dto.response.notification.NotificationResponseDto
import retrofit2.Response

interface NotificationDataSource {
    suspend fun getNotifications(userId: Long): List<NotificationResponseDto>
    suspend fun approveNotification(request: NotificationApproveRequestDto): Response<String>
    suspend fun rejectNotification(request: NotificationDecisionRequestDto): Response<String>
}
