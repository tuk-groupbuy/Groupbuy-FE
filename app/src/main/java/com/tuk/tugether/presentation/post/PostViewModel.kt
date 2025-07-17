package com.tuk.tugether.presentation.post

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuk.tugether.domain.model.request.post.DeleteJoinPostRequestModel
import com.tuk.tugether.domain.model.request.post.JoinPostRequestModel
import com.tuk.tugether.domain.model.request.post.UpdatePostRequestModel
import com.tuk.tugether.domain.model.response.post.GetPostDetailResponseModel
import com.tuk.tugether.domain.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    private val _createPostResult = MutableLiveData<Long?>()
    val createPostResult: LiveData<Long?> = _createPostResult

    private val _updatePostResult = MutableLiveData<Boolean>()
    val updatePostResult: LiveData<Boolean> = _updatePostResult

    private val _deletePostResult = MutableLiveData<Boolean>()
    val deletePostResult: LiveData<Boolean> = _deletePostResult


    fun createPost(dto: RequestBody, file: MultipartBody.Part) {
        viewModelScope.launch {
            val result = postRepository.createPost(dto, file)
            val message = result.getOrNull()
            val postId = extractPostIdFromMessage(message)
            _createPostResult.value = postId
            Log.d("PostViewModel", "게시글 생성 성공: $postId")
        }
    }

    private fun extractPostIdFromMessage(message: String?): Long? {
        if (message == null) return null
        val regex = Regex("""ID:\s*(\d+)""")
        val match = regex.find(message)
        return match?.groups?.get(1)?.value?.toLongOrNull()
    }

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

    fun deletePost(postId: Long) {
        viewModelScope.launch {
            val result = postRepository.deletePost(postId)
            result.fold(
                onSuccess = {
                    _deletePostResult.value = true
                },
                onFailure = {
                    _deletePostResult.value = false
                    _errorMessage.value = it.message ?: "게시글 삭제 실패"
                }
            )
        }
    }

    fun updatePost(postId: Long, model: UpdatePostRequestModel) {
        viewModelScope.launch {
            val result = postRepository.updatePost(postId, model)
            _updatePostResult.value = result.isSuccess
            if (result.isFailure) {
                _errorMessage.value = result.exceptionOrNull()?.message ?: "게시글 수정 실패"
            }
        }
    }

}
