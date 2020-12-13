package personaltaste.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.nhaarman.mockitokotlin2.given
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import personaltaste.controller.model.user.UserCreateRequest
import personaltaste.entity.User
import personaltaste.entity.model.user.UserGender
import personaltaste.exception.ExceptionCode
import personaltaste.exception.PersonalTasteException
import personaltaste.service.UserCommonService
import personaltaste.service.model.user.UserCreate
import support.test.BaseTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class UserControllerTest : BaseTest() {

    @Autowired
    private lateinit var snakeCaseObjectMapper: ObjectMapper

    @MockBean
    private lateinit var userCommonService: UserCommonService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `user 생성 성공`() {
        // given
        val userCreate = UserCreate(
            email = "sechun0215@gmail.com",
            name = "김세훈",
            password = "1234",
            age = 20,
            gender = UserGender.MALE
        )

        given(userCommonService.create(userCreate)).willReturn(userCreate.toUser())

        // when
        val result = mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(
                    snakeCaseObjectMapper.writeValueAsString(
                        UserCreateRequest(userCreate)
                    )
                )
        )

        // then
        result
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isCreated)
    }

    @Test
    fun `user 생성 실패 (이미 email 등록 되어 있는 경우)`() {
        // given
        val userCreate = UserCreate(
            email = "sechun0215@gmail.com",
            name = "김세훈",
            password = "1234",
            age = 20,
            gender = UserGender.MALE
        )

        given(userCommonService.create(userCreate)).willThrow(PersonalTasteException(ExceptionCode.INVALID_VALUE, "중복된 email 존재"))

        // when
        val result = mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(
                    snakeCaseObjectMapper.writeValueAsString(
                        UserCreateRequest(userCreate)
                    )
                )
        )

        // then
        result
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.error_code", equalTo("INVALID_VALUE")))
            .andExpect(jsonPath("$.error_message", equalTo("${ExceptionCode.INVALID_VALUE.message} 중복된 email 존재") ))
    }

    @Test
    fun `user 삭제 성공`() {
        // given
        val userId = 1L

        val user = User.of(
            email = "sechun0215@gmail.com",
            name = "김세훈",
            password = "1234",
            age = 20,
            gender = UserGender.MALE
        )

        given(userCommonService.delete(userId)).willReturn(user.delete())

        // when
        val result = mockMvc.perform(
            MockMvcRequestBuilders.delete("/api/v1/users/{user_id}", 1)
        )

        // then
        result
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk)
    }

}
