package com.tuk.tugether.data.service

import com.tuk.tugether.data.dto.request.CommonChatRequestDto
import com.tuk.tugether.data.dto.request.CreateChatRequestDto
import com.tuk.tugether.data.dto.response.chat.ChatListResponseDto
import com.tuk.tugether.data.dto.response.chat.ChatMessageResponseDto
import com.tuk.tugether.data.dto.response.chat.ChatResponse
import com.tuk.tugether.data.dto.response.chat.CommonChatResponseDto
import com.tuk.tugether.data.dto.response.chat.CreateChatResponseDto
import com.tuk.tugether.data.dto.response.chat.ParticipantListResponseDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ChatService {
    // 채팅방 참여자 조회
    @GET("chat/room/user/all/{chatRoomId}")
    suspend fun fetchChatParticipantUserList(
        @Path("chatRoomId") chatRoomId: Long
    ): ChatResponse<ParticipantListResponseDto>

    // 채팅방 참여
    @POST("chat/room/user/join")
    suspend fun fetchJoinChat(
        @Body request: CommonChatRequestDto
    ): ChatResponse<CommonChatResponseDto>

    // 채팅방 나가기
    @DELETE("chat/room/user")
    suspend fun fetchExitChatRoom(
        @Body request: CommonChatRequestDto
        ): ChatResponse<CommonChatResponseDto>

    // 채팅방 생성
    @POST("chat/room")
    suspend fun fetchCreateChatRoom(
        @Body request: CreateChatRequestDto
    ): ChatResponse<CreateChatResponseDto>

    // 채팅방 목록 조회
    @GET("chat/room/all")
    suspend fun fetchChatRoomList(
        @Body request: CreateChatRequestDto
    ): ChatResponse<ChatListResponseDto>

    // 채팅방 메세지 조회
    @GET("chat/message/{chatRoomId}")
    suspend fun fetchChatMessage(): ChatResponse<ChatMessageResponseDto>

}