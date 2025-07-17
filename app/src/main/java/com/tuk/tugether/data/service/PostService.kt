package com.tuk.tugether.data.service


import com.tuk.tugether.data.dto.BaseResponse
import com.tuk.tugether.data.dto.request.post.DeleteJoinPostRequestDto
import com.tuk.tugether.data.dto.request.post.JoinPostRequestDto
import com.tuk.tugether.data.dto.request.post.UpdatePostRequestDto
import com.tuk.tugether.data.dto.response.post.GetAllPostResponseDto
import com.tuk.tugether.data.dto.response.post.GetPostDetailResponseDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface PostService {

    // 게시물 작성
    @Multipart
    @POST("post")
    suspend fun createPost(
        @Part("dto") dto: RequestBody,
        @Part file: MultipartBody.Part
    ): BaseResponse<String>

    // 전체 게시물 목록 불러오기
    @GET("post/all")
    suspend fun getAllPosts(): BaseResponse<List<GetAllPostResponseDto>>

    // 게시물 상세 불러오기
    @GET("post/{postId}")
    suspend fun getPostDetail(
        @Path("postId") postId: Long,
        @Query("requesterId") requesterId: Long
    ): BaseResponse<GetPostDetailResponseDto>

    // 게시물 수정하기
    @PUT("post/{postId}")
    suspend fun updatePost(
        @Path("postId") postId: Long,
        @Body body: UpdatePostRequestDto
    ): BaseResponse<String>

    // 게시물 삭제
    @DELETE("post/{postId}")
    suspend fun deletePost(
        @Path("postId") postId: Long
    ): BaseResponse<String>

    // 참여
    @POST("post/join")
    suspend fun joinPost(
        @Body body: JoinPostRequestDto
    ): BaseResponse<String>

    // 참여 취소
    @HTTP(method = "DELETE", path = "post/join", hasBody = true)
    suspend fun deleteJoinPost(
        @Body body: DeleteJoinPostRequestDto
    ): BaseResponse<String>

}
