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
            Post("휴지 30개입 x2", "마감 3일 전", "₩12,000", "3/5", "https://via.placeholder.com/150/FF0000/FFFFFF?text=텀블러"),
            Post("코카콜라 190ml 30개", "마감 5일 전", "₩5,000", "1/4", "https://via.placeholder.com/150/00FF00/000000?text=문구"),
            Post("햇반 곤약밥 4종 150g", "마감 1일 전", "₩9,900", "5/6", "https://via.placeholder.com/150/0000FF/FFFFFF?text=슬리퍼"),
            Post("세제 2.5L 4개", "마감 2일 전", "₩3,000", "2/3", "https://via.placeholder.com/150/FFFF00/000000?text=세척솔"),
            Post("핸드워시 파우더향 250ml 2개", "마감 4일 전", "₩20,000", "2/4", "https://via.placeholder.com/150/00FFFF/000000?text=배터리"),
            Post("습기제거제 옷장용 300g 8개", "마감 6일 전", "₩7,000", "1/2", "https://via.placeholder.com/150/FF00FF/FFFFFF?text=에코백"),
            Post("코카콜라 체리 페트500ml 24개입", "마감 2일 전", "₩11,000", "0/3", "https://via.placeholder.com/150/888888/FFFFFF?text=USB"),
            Post("핫식스더킹 에너지드링크 355ml 20개 ", "마감 7일 전", "₩15,000", "2/5", "https://via.placeholder.com/150/444444/FFFFFF?text=전공책"),
            Post("콜라 300ml 20개", "마감 1일 전", "₩8,500", "3/3", "https://via.placeholder.com/150/FFA500/000000?text=인형"),
            Post("LED 스탠드", "마감 오늘", "₩25,000", "1/2", "https://via.placeholder.com/150/008000/FFFFFF?text=스탠드")
        )
    }
}

