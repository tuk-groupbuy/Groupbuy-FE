package com.tuk.tugether.data.service

import com.tuk.tugether.data.dto.request.post.DeleteJoinPostRequestDto
import com.tuk.tugether.data.dto.request.post.JoinPostRequestDto
import com.tuk.tugether.data.dto.request.post.UpdatePostRequestDto
import com.tuk.tugether.data.dto.response.post.GetAllPostResponseDto
import com.tuk.tugether.data.dto.response.post.GetPostDetailResponseDto
import com.tuk.tugether.data.dto.response.post.SearchPostResponseDto
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
    @Multipart
    @POST("post")
    suspend fun createPost(
        @Part("dto") dto: RequestBody,
        @Part file: MultipartBody.Part
    ): String

    @GET("post/all")
    suspend fun getAllPosts(): List<GetAllPostResponseDto>

    @GET("post/{postId}")
    suspend fun getPostDetail(
        @Path("postId") postId: Long,
        @Query("requesterId") requesterId: Long
    ): GetPostDetailResponseDto

    @PUT("post/{postId}")
    suspend fun updatePost(
        @Path("postId") postId: Long,
        @Body body: UpdatePostRequestDto
    ): String

    @DELETE("post/{postId}")
    suspend fun deletePost(
        @Path("postId") postId: Long
    ): String

    @POST("post/join")
    suspend fun joinPost(
        @Body body: JoinPostRequestDto
    ): String

    @HTTP(method = "DELETE", path = "post/join", hasBody = true)
    suspend fun deleteJoinPost(
        @Body body: DeleteJoinPostRequestDto
    ): String

    @GET("/post/search")
    suspend fun searchPosts(
        @Query("keyword") keyword: String
    ): SearchPostResponseDto

}
