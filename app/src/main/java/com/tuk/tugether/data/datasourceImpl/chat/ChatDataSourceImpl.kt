package com.tuk.tugether.data.datasourceImpl.chat

import com.tuk.tugether.data.datasource.chat.ChatDataSource
import com.tuk.tugether.data.dto.request.CommonChatRequestDto
import com.tuk.tugether.data.dto.request.CreateChatRequestDto
import com.tuk.tugether.data.dto.response.chat.ChatListResponseDto
import com.tuk.tugether.data.dto.response.chat.ChatMessageResponseDto
import com.tuk.tugether.data.dto.response.chat.ChatResponse
import com.tuk.tugether.data.dto.response.chat.CommonChatResponseDto
import com.tuk.tugether.data.dto.response.chat.CreateChatResponseDto
import com.tuk.tugether.data.dto.response.chat.ParticipantListResponseDto
import com.tuk.tugether.data.service.ChatService
import javax.inject.Inject

class ChatDataSourceImpl @Inject constructor(
    private val chatService: ChatService
): ChatDataSource {
    override suspend fun fetchChatParticipantUserList(chatRoomId: Long): ChatResponse<ParticipantListResponseDto> =
        chatService.fetchChatParticipantUserList(chatRoomId)

    override suspend fun fetchJoinChat(request: CommonChatRequestDto): ChatResponse<CommonChatResponseDto> =
        chatService.fetchJoinChat(request)

    override suspend fun fetchExitChatRoom(request: CommonChatRequestDto): ChatResponse<CommonChatResponseDto> =
        chatService.fetchExitChatRoom(request)

    override suspend fun fetchCreateChatRoom(request: CreateChatRequestDto): ChatResponse<CreateChatResponseDto> =
        chatService.fetchCreateChatRoom(request)

//    override suspend fun fetchChatRoomList(request: CreateChatRequestDto): ChatResponse<ChatListResponseDto> =
//        chatService.fetchChatRoomList(request)
    override suspend fun fetchChatRoomList(userId: Long): ChatResponse<ChatListResponseDto> =
        chatService.fetchChatRoomList(userId)

    override suspend fun fetchChatMessage(chatRoomId: Long): ChatResponse<ChatMessageResponseDto> =
        chatService.fetchChatMessage(chatRoomId)
}