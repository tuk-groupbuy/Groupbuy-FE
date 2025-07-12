package com.tuk.tugether.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tuk.tugether.presentation.home.adapter.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _postList = MutableLiveData<List<Post>>()
    val postList: LiveData<List<Post>> = _postList

    init {
        loadDummyPosts()
    }

    private fun loadDummyPosts() {
        _postList.value = listOf(
            Post("텀블러", "마감 3일 전", "₩12,000", "3/5", "https://via.placeholder.com/150/FF0000/FFFFFF?text=텀블러"),
            Post("문구세트", "마감 5일 전", "₩5,000", "1/4", "https://via.placeholder.com/150/00FF00/000000?text=문구"),
            Post("슬리퍼", "마감 1일 전", "₩9,900", "5/6", "https://via.placeholder.com/150/0000FF/FFFFFF?text=슬리퍼"),
            Post("텀블러 세척솔", "마감 2일 전", "₩3,000", "2/3", "https://via.placeholder.com/150/FFFF00/000000?text=세척솔"),
            Post("보조배터리", "마감 4일 전", "₩20,000", "4/4", "https://via.placeholder.com/150/00FFFF/000000?text=배터리"),
            Post("에코백", "마감 6일 전", "₩7,000", "1/2", "https://via.placeholder.com/150/FF00FF/FFFFFF?text=에코백"),
            Post("USB 메모리", "마감 2일 전", "₩11,000", "0/3", "https://via.placeholder.com/150/888888/FFFFFF?text=USB"),
            Post("전공책", "마감 7일 전", "₩15,000", "2/5", "https://via.placeholder.com/150/444444/FFFFFF?text=전공책"),
            Post("인형", "마감 1일 전", "₩8,500", "3/3", "https://via.placeholder.com/150/FFA500/000000?text=인형"),
            Post("LED 스탠드", "마감 오늘", "₩25,000", "1/1", "https://via.placeholder.com/150/008000/FFFFFF?text=스탠드")
        )
    }
}

