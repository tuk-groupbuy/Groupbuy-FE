package com.tuk.tugether.domain.repository.chat

import com.tuk.tugether.domain.model.request.CommonChatRequestModel
import com.tuk.tugether.domain.model.request.CreateChatRequestModel
import com.tuk.tugether.domain.model.response.ChatListResponseModel
import com.tuk.tugether.domain.model.response.ChatMessageResponseModel
import com.tuk.tugether.domain.model.response.CommonChatResponseModel
import com.tuk.tugether.domain.model.response.CreateChatResponseModel
import com.tuk.tugether.domain.model.response.ParticipantListResponseModel

interface ChatRepository {
    suspend fun fetchChatParticipantUserList(chatRoomId: Long): Result<ParticipantListResponseModel>
    suspend fun fetchJoinChat(request: CommonChatRequestModel): Result<CommonChatResponseModel>
    suspend fun fetchExitChatRoom(request: CommonChatRequestModel): Result<CommonChatResponseModel>
    suspend fun fetchCreateChatRoom(request: CreateChatRequestModel): Result<CreateChatResponseModel>
//    suspend fun fetchChatRoomList(request: CreateChatRequestModel): Result<ChatListResponseModel>
    suspend fun fetchChatRoomList(userId: Long): Result<ChatListResponseModel>
    suspend fun fetchChatMessage(chatRoomId: Long, page: Int, size: Int): Result<ChatMessageResponseModel>
}