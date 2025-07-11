package  com.tuk.tugether.data.dto.response

import com.tuk.tugether.domain.model.TestModel

data class TestResponse (
    val body: String
){
    fun toTestModel() = TestModel(body)
}