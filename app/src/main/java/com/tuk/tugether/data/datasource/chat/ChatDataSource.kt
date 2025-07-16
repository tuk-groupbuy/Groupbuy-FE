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
    suspend fun fetchChatParticipantUserList(chatRoomId: Long): ParticipantListResponseDto
    suspend fun fetchJoinChat(request: CommonChatRequestDto): CommonChatResponseDto
    suspend fun fetchExitChatRoom(request: CommonChatRequestDto): CommonChatResponseDto
    suspend fun fetchCreateChatRoom(request: CreateChatRequestDto): CreateChatResponseDto
//    suspend fun fetchChatRoomList(request: CreateChatRequestDto): ChatListResponseDto
    suspend fun fetchChatRoomList(userId: Long): ChatListResponseDto
    suspend fun fetchChatMessage(chatRoomId: Long): ChatMessageResponseDto
}