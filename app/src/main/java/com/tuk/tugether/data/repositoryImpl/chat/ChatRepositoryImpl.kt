package com.tuk.tugether.data.repositoryImpl.chat

import android.content.SharedPreferences
import com.tuk.tugether.data.datasource.chat.ChatDataSource
import com.tuk.tugether.domain.model.request.CommonChatRequestModel
import com.tuk.tugether.domain.model.request.CreateChatRequestModel
import com.tuk.tugether.domain.model.response.ChatListResponseModel
import com.tuk.tugether.domain.model.response.ChatMessageResponseModel
import com.tuk.tugether.domain.model.response.CommonChatResponseModel
import com.tuk.tugether.domain.model.response.CreateChatResponseModel
import com.tuk.tugether.domain.model.response.ParticipantListResponseModel
import com.tuk.tugether.domain.repository.chat.ChatRepository
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatDataSource: ChatDataSource,
    private val spf: SharedPreferences
): ChatRepository {
    override suspend fun fetchChatParticipantUserList(chatRoomId: Long): Result<ParticipantListResponseModel> = runCatching {
        chatDataSource.fetchChatParticipantUserList(chatRoomId).toParticipantListResponseModel()
    }

    override suspend fun fetchJoinChat(request: CommonChatRequestModel): Result<CommonChatResponseModel> = runCatching {
        chatDataSource.fetchJoinChat(request.toCommonChatRequestDto()).toJoinChatResponseModel()
    }

    override suspend fun fetchExitChatRoom(request: CommonChatRequestModel): Result<CommonChatResponseModel> = runCatching {
        chatDataSource.fetchExitChatRoom(request.toCommonChatRequestDto()).toJoinChatResponseModel()
    }

    override suspend fun fetchCreateChatRoom(request: CreateChatRequestModel): Result<CreateChatResponseModel> = runCatching {
        chatDataSource.fetchCreateChatRoom(request.toCreateChatRequestDto()).toCreateChatResponseModel()
    }

//    override suspend fun fetchChatRoomList(request: CreateChatRequestModel): Result<ChatListResponseModel> = runCatching {
//        chatDataSource.fetchChatRoomList(request.toCreateChatRequestDto()).data.toChatListResponseModel()
//    }

    override suspend fun fetchChatRoomList(userId: Long): Result<ChatListResponseModel> = runCatching {
        chatDataSource.fetchChatRoomList(userId).toChatListResponseModel()
    }


    override suspend fun fetchChatMessage(chatRoomId: Long, page: Int, size: Int): Result<ChatMessageResponseModel> = runCatching {
        chatDataSource.fetchChatMessage(chatRoomId, page, size).toChatMessageResponseModel(spf.getString("user_nickname", "")?:"")
    }

}