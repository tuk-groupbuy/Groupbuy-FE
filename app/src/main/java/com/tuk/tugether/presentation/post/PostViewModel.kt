package com.tuk.tugether.presentation.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuk.tugether.domain.model.request.post.DeleteJoinPostRequestModel
import com.tuk.tugether.domain.model.request.post.JoinPostRequestModel
import com.tuk.tugether.domain.model.response.post.GetPostDetailResponseModel
import com.tuk.tugether.domain.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val postRepository: PostRepository
) : ViewModel() {

    private val _postDetail = MutableStateFlow<GetPostDetailResponseModel?>(null)
    val postDetail: StateFlow<GetPostDetailResponseModel?> = _postDetail

    private val _joinPostResult = MutableStateFlow<String?>(null)
    val joinPostResult: StateFlow<String?> = _joinPostResult

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun fetchPostDetail(postId: Long, requesterId: Long) {
        viewModelScope.launch {
            postRepository.getPostDetail(postId, requesterId)
                .onSuccess { response ->
                    _postDetail.value = response
                }.onFailure { throwable ->
                    _errorMessage.value = throwable.message
                }
        }
    }

    fun joinPost(postId: Long, userId: Long) {
        viewModelScope.launch {
            val result = postRepository.joinPost(JoinPostRequestModel(postId, userId))
            result.fold(
                onSuccess = { response ->
                    _joinPostResult.value = response
                    fetchPostDetail(postId, userId)
                },
                onFailure = { throwable ->
                    _errorMessage.value = throwable.message ?: "참여 요청 실패"
                }
            )
        }
    }

    fun deleteJoinPost(postId: Long, userId: Long) {
        viewModelScope.launch {
            val result = postRepository.deleteJoinPost(DeleteJoinPostRequestModel(postId, userId))
            result.fold(
                onSuccess = { response ->
                    _joinPostResult.value = response
                    fetchPostDetail(postId, userId)
                },
                onFailure = { throwable ->
                    _errorMessage.value = throwable.message ?: "참여 취소 요청 실패"
                }
            )
        }
    }

}
