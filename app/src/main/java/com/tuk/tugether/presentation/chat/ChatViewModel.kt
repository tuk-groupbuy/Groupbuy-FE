package com.tuk.tugether.presentation.chat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuk.tugether.domain.model.request.CommonChatRequestModel
import com.tuk.tugether.domain.model.request.CreateChatRequestModel
import com.tuk.tugether.domain.model.response.ChatListResponseModel
import com.tuk.tugether.domain.model.response.ChatMessageResponseModel
import com.tuk.tugether.domain.model.response.CommonChatResponseModel
import com.tuk.tugether.domain.model.response.ParticipantListResponseModel
import com.tuk.tugether.domain.repository.chat.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository
): ViewModel() {
    private val _participantList = MutableLiveData<List<ParticipantListResponseModel.ChatRoomUserModel>>()
    val participantList: LiveData<List<ParticipantListResponseModel.ChatRoomUserModel>> = _participantList

    private val _joinResult = MutableLiveData<CommonChatResponseModel>()
    val joinResult: LiveData<CommonChatResponseModel> = _joinResult

    private val _exitResult = MutableLiveData<CommonChatResponseModel>()
    val exitResult: LiveData<CommonChatResponseModel> = _exitResult

    private val _chatRoomList = MutableLiveData<List<ChatListResponseModel.ChatRoomModel>>()
    val chatRoomList: LiveData<List<ChatListResponseModel.ChatRoomModel>> = _chatRoomList

    private val _chatMessages = MutableLiveData<List<ChatMessageResponseModel.ChatMessageModel>>()
    val chatMessages: LiveData<List<ChatMessageResponseModel.ChatMessageModel>> = _chatMessages

    private val _chatRoomId = MutableLiveData<Long>()
    val chatRoomId: LiveData<Long> get() = _chatRoomId


    fun fetchParticipantList(chatRoomId: Long) {
        viewModelScope.launch {
            chatRepository.fetchChatParticipantUserList(chatRoomId)
                .onSuccess { response ->
                    Log.d("ChatViewModel", "채팅 참여자 목록 성공: $response")
                    _participantList.value = response.userList
                }
                .onFailure {
                    Log.e("ChatViewModel", "채팅 참여자 목록 실패: ${it.message}")
                }
        }
    }

    fun fetchJoinChat(request: CommonChatRequestModel) {
        viewModelScope.launch {
            chatRepository.fetchJoinChat(request)
                .onSuccess { response ->
                    Log.d("ChatViewModel", "채팅 참여 성공: $response")
                    _joinResult.value = response
                }
                .onFailure {
                    Log.e("ChatViewModel", "채팅 참여 실패: ${it.message}")
                }
        }
    }

    fun fetchExitChatRoom(request: CommonChatRequestModel) {
        viewModelScope.launch {
            chatRepository.fetchExitChatRoom(request)
                .onSuccess { response ->
                    Log.d("ChatViewModel", "채팅 나가기 성공: $response")
                    _exitResult.value = response
                }
                .onFailure {
                    Log.e("ChatViewModel", "채팅 나가기 실패: ${it.message}")
                }
        }
    }

//    fun fetchChatRoomList(request: CreateChatRequestModel) {
//        viewModelScope.launch {
//            chatRepository.fetchChatRoomList(request)
//                .onSuccess { response ->
//                    Log.d("ChatViewModel", "채팅방 목록 성공: $response")
//                    _chatRoomList.value = response.requestChatRoomSaveDTOS
//                }
//                .onFailure {
//                    Log.e("ChatViewModel", "채팅방 목록 실패: ${it.message}")
//                }
//        }
//    }

    fun fetchChatRoomList(userId: Long) {
        viewModelScope.launch {
            chatRepository.fetchChatRoomList(userId)
                .onSuccess { response ->
                    Log.d("ChatViewModel", "채팅방 목록 성공: $response")
                    _chatRoomList.value = response.requestChatRoomSaveDTOS
                }
                .onFailure {
                    Log.e("ChatViewModel", "채팅방 목록 실패: ${it.message}")
                }
        }
    }

    fun fetchChatMessage(chatRoomId: Long) {
        viewModelScope.launch {
            chatRepository.fetchChatMessage(chatRoomId)
                .onSuccess { response ->
                    Log.d("ChatViewModel", "채팅 메시지 조회 성공: $response")
                    _chatMessages.value = response.messages
                }
                .onFailure {
                    Log.e("ChatViewModel", "채팅 메시지 조회 실패: ${it.message}")
                }
        }
    }

    fun setChatRoomId(id: Long) {
        Log.d("ChatViewModel", "채팅방 ID : $id")
        _chatRoomId.value = id
    }
}