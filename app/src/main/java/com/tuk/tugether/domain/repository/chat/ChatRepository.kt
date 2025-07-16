package com.tuk.tugether.domain.repository.chat

import com.tuk.tugether.data.dto.response.chat.ChatResponse

interface ChatRepository {
    suspend fun fetchChatParticipantUserList(chatRoomId: Long): Result<ParticipanListResponseModel>
    suspend fun fetchJoinChat(request: JoinChatRequestModel): Result<JoinChatResponseModel>
    suspend fun fetchExitChatRoom(request: ExitChatRequestModel): Result<ExitChatResponseModel>
}