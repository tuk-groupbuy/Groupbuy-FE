package com.tuk.tugether.data.repositoryImpl

import com.tuk.tugether.data.dto.BaseResponse
import com.tuk.tugether.data.dto.request.TestRequest
import com.tuk.tugether.data.dto.response.TestResponse
import com.tuk.tugether.data.service.TestService
import com.tuk.tugether.domain.model.TestModel
import com.tuk.tugether.domain.repository.TestRepository
import com.tuk.tugether.util.network.NetworkResult
import com.tuk.tugether.util.network.handleApi
import javax.inject.Inject

class TestRepositoryImpl @Inject constructor(
    private val testService: TestService
) : TestRepository {
    override suspend fun fetchTest(request: TestRequest): NetworkResult<TestModel> {
        return handleApi({testService.fetchTest(request)}) {response: BaseResponse<TestResponse> -> response.data.toTestModel()} // mapper를 통해 api 결과를 TestModel로 매핑
    }

}