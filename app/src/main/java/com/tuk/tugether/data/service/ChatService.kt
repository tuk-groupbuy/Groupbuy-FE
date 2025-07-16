package com.tuk.tugether.data.service

import com.tuk.tugether.data.dto.response.chat.ChatResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ChatService {
    @GET("chat/room/user/all/{chatRoomId}")
    suspend fun fetchChatParticipantUserList(
        @Path("chatRoomId") chatRoomId: Long
    ): ChatResponse<ParticipanListResponseDto>

    @POST("chat/room/user/join")
    suspend fun fetchJoinChat(
        @Body request: JoinChatRequestDto
    ): ChatResponse<JoinChatResponseDto>

    @DELETE("chat/room/user")
    suspend fun fetchExitChatRoom(
        @Body request: ExitChatRequestDto
    ): ChatResponse<ExitChatResponseDto>
}