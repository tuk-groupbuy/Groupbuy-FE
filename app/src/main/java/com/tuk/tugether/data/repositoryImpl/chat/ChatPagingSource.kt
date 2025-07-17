package com.tuk.tugether.data.repositoryImpl.chat

import android.content.SharedPreferences
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tuk.tugether.data.service.ChatService
import com.tuk.tugether.domain.model.response.ChatMessageResponseModel
import kotlinx.coroutines.delay
import javax.inject.Inject

class ChatPagingSource @Inject constructor(
    private val chatService: ChatService,
    private val chatRoomId: Long,
    private val spf: SharedPreferences
) : PagingSource<Int, ChatMessageResponseModel.ChatMessageModel>() {
    override fun getRefreshKey(state: PagingState<Int, ChatMessageResponseModel.ChatMessageModel>): Int? {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ChatMessageResponseModel.ChatMessageModel> {
        val page = params.key ?: 0
        if(page != 0) delay(500)
        runCatching {
            chatService.fetchChatMessage(chatRoomId, page, 10).toChatMessageResponseModel(spf.getString("user_nickname", "")?:"")
        }.fold(
            onSuccess = { response ->
                return LoadResult.Page(
                    data = response.messages,
                    prevKey = if (page == 0) null else page - 1,
                    nextKey = if (response.isLast) null else page + 1
                )
            }, onFailure = {
                Log.d("Paging 3 Load Error", it.stackTraceToString())
                return LoadResult.Error(it)
            }
        )
    }
}