package com.tuk.tugether.data.datasource.chat

import com.tuk.tugether.data.dto.response.chat.ChatResponse

interface ChatDataSource {
    suspend fun fetchChatParticipantUserList(chatRoomId: Long): ChatResponse<ParticipanListResponseDto>
    suspend fun fetchJoinChat(request: JoinChatRequestDto): ChatResponse<JoinChatResponseDto>
    suspend fun fetchExitChatRoom(request: ExitChatRequestDto): ChatResponse<ExitChatResponseDto>
}