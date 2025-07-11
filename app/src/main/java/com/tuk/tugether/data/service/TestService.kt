package com.tuk.tugether.data.service


import com.tuk.tugether.data.dto.BaseResponse
import com.tuk.tugether.data.dto.request.TestRequest
import com.tuk.tugether.data.dto.response.TestResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET

interface TestService {
    // api 명세서의 api 주소 기입
    @GET("주소")
    suspend fun fetchTest(@Body request: TestRequest): Response<BaseResponse<TestResponse>>
}