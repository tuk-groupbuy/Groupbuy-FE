package com.tuk.tugether.data.datasource.post

import com.tuk.tugether.data.dto.BaseResponse
import com.tuk.tugether.data.dto.request.post.DeleteJoinPostRequestDto
import com.tuk.tugether.data.dto.request.post.JoinPostRequestDto
import com.tuk.tugether.data.dto.request.post.UpdatePostRequestDto
import com.tuk.tugether.data.dto.response.post.GetAllPostResponseDto
import com.tuk.tugether.data.dto.response.post.GetPostDetailResponseDto
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface PostDataSource {
    suspend fun createPost(dto: RequestBody, file: MultipartBody.Part): BaseResponse<String>
    suspend fun getAllPosts(): BaseResponse<List<GetAllPostResponseDto>>
    suspend fun getPostDetail(postId: Long, requesterId: Long): BaseResponse<GetPostDetailResponseDto>
    suspend fun updatePost(postId: Long, body: UpdatePostRequestDto): BaseResponse<String>
    suspend fun deletePost(postId: Long): BaseResponse<String>
    suspend fun joinPost(body: JoinPostRequestDto): BaseResponse<String>
    suspend fun deleteJoinPost(body: DeleteJoinPostRequestDto): BaseResponse<String>

}
