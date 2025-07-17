package com.tuk.tugether.data.service


import com.tuk.tugether.data.dto.response.notification.NotificationResponseDto
import retrofit2.http.GET
import retrofit2.http.Path

interface NotificationService {

    // 알림 전체 목록 불러오기
    @GET("notification/user/{userId}")
    suspend fun getNotifications(
        @Path("userId") userId: Long
    ): List<NotificationResponseDto>
}