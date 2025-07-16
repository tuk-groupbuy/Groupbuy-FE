package com.tuk.tugether.data.datasource.chat

import com.tuk.tugether.data.dto.request.CommonChatRequestDto
import com.tuk.tugether.data.dto.request.CreateChatRequestDto
import com.tuk.tugether.data.dto.response.chat.ChatListResponseDto
import com.tuk.tugether.data.dto.response.chat.ChatMessageResponseDto
import com.tuk.tugether.data.dto.response.chat.ChatResponse
import com.tuk.tugether.data.dto.response.chat.CommonChatResponseDto
import com.tuk.tugether.data.dto.response.chat.CreateChatResponseDto
import com.tuk.tugether.data.dto.response.chat.ParticipantListResponseDto

interface ChatDataSource {
    suspend fun fetchChatParticipantUserList(chatRoomId: Long): ChatResponse<ParticipantListResponseDto>
    suspend fun fetchJoinChat(request: CommonChatRequestDto): ChatResponse<CommonChatResponseDto>
    suspend fun fetchExitChatRoom(request: CommonChatRequestDto): ChatResponse<CommonChatResponseDto>
    suspend fun fetchCreateChatRoom(request: CreateChatRequestDto): ChatResponse<CreateChatResponseDto>
//    suspend fun fetchChatRoomList(request: CreateChatRequestDto): ChatResponse<ChatListResponseDto>
    suspend fun fetchChatRoomList(userId: Long): ChatResponse<ChatListResponseDto>
    suspend fun fetchChatMessage(chatRoomId: Long): ChatResponse<ChatMessageResponseDto>
}