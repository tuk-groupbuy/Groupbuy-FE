package com.tuk.tugether.data.datasourceImpl.chat

import com.tuk.tugether.data.datasource.chat.ChatDataSource
import com.tuk.tugether.data.dto.response.chat.ChatResponse
import com.tuk.tugether.data.service.ChatService
import javax.inject.Inject

class ChatDataSourceImpl @Inject constructor(
    private val chatService: ChatService
): ChatDataSource {
    override suspend fun fetchChatParticipantUserList(chatRoomId: Long): ChatResponse<ParticipanListResponseDto> =
        chatService.fetchChatParticipantUserList(chatRoomId)

    override suspend fun fetchJoinChat(request: JoinChatRequestDto): ChatResponse<JoinChatResponseDto> =
        chatService.fetchJoinChat(request)

    override suspend fun fetchExitChatRoom(request: ExitChatRequestDto): ChatResponse<ExitChatResponseDto> =
        chatService.fetchExitChatRoom(request)
}