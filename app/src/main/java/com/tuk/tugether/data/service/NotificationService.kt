package com.tuk.tugether.data.service


import com.tuk.tugether.data.dto.request.notification.NotificationApproveRequestDto
import com.tuk.tugether.data.dto.request.notification.NotificationDecisionRequestDto
import com.tuk.tugether.data.dto.response.notification.NotificationResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface NotificationService {

    // 알림 전체 목록 불러오기
    @GET("notification/user/{userId}")
    suspend fun getNotifications(
        @Path("userId") userId: Long
    ): List<NotificationResponseDto>

    // 알림 승인
    @POST("notification/approve")
    suspend fun approveNotification(
        @Body request: NotificationApproveRequestDto
    ): Response<String>

    // 알림 거절
    @POST("notification/reject")
    suspend fun rejectNotification(
        @Body request: NotificationDecisionRequestDto
    ): Response<String>
}