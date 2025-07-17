package com.tuk.tugether.data.datasourceImpl.chat

import com.tuk.tugether.data.datasource.chat.ChatDataSource
import com.tuk.tugether.data.dto.request.chat.CommonChatRequestDto
import com.tuk.tugether.data.dto.request.chat.CreateChatRequestDto
import com.tuk.tugether.data.dto.response.chat.ChatListResponseDto
import com.tuk.tugether.data.dto.response.chat.ChatMessageResponseDto
import com.tuk.tugether.data.dto.response.chat.CommonChatResponseDto
import com.tuk.tugether.data.dto.response.chat.CreateChatResponseDto
import com.tuk.tugether.data.dto.response.chat.ParticipantListResponseDto
import com.tuk.tugether.data.service.ChatService
import javax.inject.Inject

class ChatDataSourceImpl @Inject constructor(
    private val chatService: ChatService
): ChatDataSource {
    override suspend fun fetchChatParticipantUserList(chatRoomId: Long): ParticipantListResponseDto =
        chatService.fetchChatParticipantUserList(chatRoomId)

    override suspend fun fetchJoinChat(request: CommonChatRequestDto): CommonChatResponseDto =
        chatService.fetchJoinChat(request)

    override suspend fun fetchExitChatRoom(request: CommonChatRequestDto): CommonChatResponseDto =
        chatService.fetchExitChatRoom(request)

    override suspend fun fetchCreateChatRoom(request: CreateChatRequestDto): CreateChatResponseDto =
        chatService.fetchCreateChatRoom(request)

//    override suspend fun fetchChatRoomList(request: CreateChatRequestDto): ChatListResponseDto =
//        chatService.fetchChatRoomList(request)
    override suspend fun fetchChatRoomList(userId: Long): ChatListResponseDto =
        chatService.fetchChatRoomList(userId)

    override suspend fun fetchChatMessage(chatRoomId: Long, page: Int, size: Int): ChatMessageResponseDto =
        chatService.fetchChatMessage(chatRoomId, page, size)
}