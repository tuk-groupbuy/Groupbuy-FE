package com.tuk.tugether.data.repositoryImpl.chat

import com.tuk.tugether.data.datasource.chat.ChatDataSource
import com.tuk.tugether.domain.repository.chat.ChatRepository
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatDataSource: ChatDataSource
): ChatRepository {
    override suspend fun fetchChatParticipantUserList(chatRoomId: Long): Result<ParticipanListResponseModel> = runCatching {
        chatDataSource.fetchChatParticipantUserList(chatRoomId).data.
    }

    override suspend fun fetchJoinChat(request: JoinChatRequestModel): Result<JoinChatResponseModel> = runCatching {
        chatDataSource.fetchJoinChat(request.).data.
    }

    override suspend fun fetchExitChatRoom(request: ExitChatRequestModel): Result<ExitChatResponseModel> = runCatching {
        chatDataSource.fetchExitChatRoom(request.).data.
    }

}