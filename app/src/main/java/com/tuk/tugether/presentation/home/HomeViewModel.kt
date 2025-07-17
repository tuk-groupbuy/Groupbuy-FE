package com.tuk.tugether.presentation.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuk.tugether.domain.model.response.post.GetAllPostResponseModel
import com.tuk.tugether.domain.repository.PostRepository
import com.tuk.tugether.presentation.home.adapter.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val postRepository: PostRepository
) : ViewModel() {

    private val _postList = MutableLiveData<List<GetAllPostResponseModel>>()
    val postList: LiveData<List<GetAllPostResponseModel>> get() = _postList

    fun getAllPosts() {
        viewModelScope.launch {
            postRepository.getAllPosts()
                .onSuccess { result ->
                    Log.d("HomeViewModel", "게시물 목록 불러오기 성공: $result")
                    _postList.value = result.reversed()
                }
                .onFailure { exception ->
                    Log.e("HomeViewModel", "게시물 목록 불러오기 실패", exception)
                    _postList.value = emptyList()
                }
        }
    }

    fun searchPost(keyword: String) {
        viewModelScope.launch {
            postRepository.searchPosts(keyword)
                .onSuccess { result ->
                    _postList.value = result.reversed()
                }
                .onFailure { exception ->
                    Log.e("HomeViewModel", "검색 실패", exception)
                    _postList.value = emptyList()
                }
        }
    }

}

