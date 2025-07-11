package com.tuk.tugether.domain.repository

import com.tuk.tugether.data.dto.request.TestRequest
import com.tuk.tugether.domain.model.TestModel
import com.tuk.tugether.util.network.NetworkResult

interface TestRepository {
    suspend fun fetchTest(request: TestRequest): NetworkResult<TestModel>
}