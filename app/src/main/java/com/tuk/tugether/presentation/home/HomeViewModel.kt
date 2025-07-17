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

    private val _isSearchMode = MutableLiveData(false)
    val isSearchMode: LiveData<Boolean> get() = _isSearchMode

    fun setSearchMode(value: Boolean) {
        _isSearchMode.value = value
    }

    var cachedSearchResults: List<GetAllPostResponseModel>? = null

    fun getAllPosts() {
        viewModelScope.launch {
            postRepository.getAllPosts()
                .onSuccess { result ->
                    _postList.value = result.reversed()
                    setSearchMode(false) // 전체 목록이므로 검색모드 해제
                }
                .onFailure {
                    _postList.value = emptyList()
                    setSearchMode(false)
                }
        }
    }

    fun searchPosts(keyword: String) {
        viewModelScope.launch {
            postRepository.searchPosts(keyword)
                .onSuccess { results ->
                    cachedSearchResults = results
                    _postList.value = results
                    setSearchMode(true)
                }
                .onFailure {
                    _postList.value = emptyList()
                    setSearchMode(false)
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

