package com.tuk.tugether.presentation.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
}
