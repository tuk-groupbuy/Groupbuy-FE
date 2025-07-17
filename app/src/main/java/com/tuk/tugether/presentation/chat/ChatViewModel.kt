package com.tuk.tugether.presentation.chat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuk.tugether.domain.model.request.CommonChatRequestModel
import com.tuk.tugether.domain.model.response.ChatListResponseModel
import com.tuk.tugether.domain.model.response.ChatMessageResponseModel
import com.tuk.tugether.domain.model.response.CommonChatResponseModel
import com.tuk.tugether.domain.model.response.CreateChatResponseModel
import com.tuk.tugether.domain.model.response.ParticipantListResponseModel
import com.tuk.tugether.domain.repository.chat.ChatRepository
import com.tuk.tugether.presentation.chat.adapter.WebSocketResource
import com.tuk.tugether.util.network.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
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

    private val _chatRoomId = MutableStateFlow<Long>(-1L)
    val chatRoomId: StateFlow<Long> get() = _chatRoomId

    private val isPageLast = MutableStateFlow<Boolean>(false)
    private val page = MutableStateFlow<Int>(0)
    private val temp = MutableStateFlow<List<ChatMessageResponseModel.ChatMessageModel>>(emptyList())

    private val _chatEvent =
        MutableStateFlow<UiState<ChatEvent>>(UiState.Empty)
    val chatEvent: StateFlow<UiState<ChatEvent>> get() = _chatEvent

    private val _webSocketEvent = MutableSharedFlow<WebSocketResource>()
    val webSocketEvent: SharedFlow<WebSocketResource> get() = _webSocketEvent

    sealed class ChatEvent {
        data class CreateChatRoom(val result: CreateChatResponseModel) : ChatEvent()
        data class FetchChatLog(val chatLog: ChatMessageResponseModel) : ChatEvent()

        data class RefreshChatLog(val chatLog: ChatMessageResponseModel) : ChatEvent()
    }

    fun eventWebsocket(event: WebSocketResource) {
        viewModelScope.launch {
            _webSocketEvent.emit(event)
        }
    }

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

    fun fetchChatMessage() {
        if (!isPageLast.value) {
            viewModelScope.launch {
                _chatEvent.value = UiState.Loading
                delay(200)
                chatRepository.fetchChatMessage(chatRoomId.value, page.value, 20)
                    .onSuccess { response ->
                        Log.d("fetchViewingPartyChatLog", "fetchViewingPartyChatLog $response.toString()")
                        temp.value += response.messages.filter { !it.content.contains("입장했습니다.") }
                        isPageLast.value = response.isLast
                        page.value += 1

                        val list = if (response.isLast) {
                            temp.value.toMutableList().apply {
                                if(this.isNotEmpty()) {
                                    this[lastIndex].isLastIndex = true
                                }
                            }
                        } else {
                            temp.value
                        }

                        _chatEvent.value =
                            UiState.Success(
                                ChatEvent.FetchChatLog(
                                    response.copy(messages = list)
                                )
                            )
                    }.onFailure {
                        Log.d("createViewingPartyChatRoom error", "$it.stackTraceToString()")
                    }
            }
        }
    }

    fun refreshChatLog() {
        viewModelScope.launch {
            _chatEvent.value = UiState.Loading
            chatRepository.fetchChatMessage(chatRoomId.value, 0, 1)
                .onSuccess { response ->
                    Log.d("fetchViewingPartyChatLog", "$response.toString()")
                    temp.value = response.messages + temp.value

                    val list = if (response.isLast) {
                        temp.value.toMutableList().apply {
                            if(this.isNotEmpty()) {
                                this[lastIndex].isLastIndex = true
                            }
                        }
                    } else {
                        temp.value
                    }

                    _chatEvent.value =
                        UiState.Success(
                            ChatEvent.FetchChatLog(
                                response.copy(messages = list)
                            )
                        )

                }.onFailure {
                    Log.d("createViewingPartyChatRoom error", "$it.stackTraceToString()")
                }
        }
    }

    fun setChatRoomId(id: Long) {
        Log.d("ChatViewModel", "채팅방 ID : $id")
        _chatRoomId.value = id
    }
}