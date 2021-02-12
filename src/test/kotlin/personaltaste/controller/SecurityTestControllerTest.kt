package personaltaste.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import personaltaste.PersonalTasteApplication
import personaltaste.controller.model.user.UserSecurityRequest
import support.test.BaseTest

/**
 * @author seungmin
 */
@AutoConfigureMockMvc
@SpringBootTest(classes = [PersonalTasteApplication::class])
class SecurityTestControllerTest : BaseTest() {

    @Autowired
    private lateinit var snakeCaseObjectMapper: ObjectMapper

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `login - ok`() {
        // given
        val email = "choising@gmail.com"
        val password = "1234"

        // when
        val result = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/security/login")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(
                                snakeCaseObjectMapper.writeValueAsString(
                                        UserSecurityRequest(email, password)
                                )
                        )
        )

        // then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `auth - ok`() {
        // given
        val email = "choising@gmail.com"
        val password = "1234"

        val response = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/security/login")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(
                                snakeCaseObjectMapper.writeValueAsString(
                                        UserSecurityRequest(email, password)
                                )
                        )
        )

        val token = response.andReturn().response.contentAsString

        // when
        val result = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/security/auth")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .header("x-auth-token", token)
        )

        // then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)

    }

    @Test
    fun `auth - fail`() {
        // given
        val invalidToken = "Hello"

        // when
        val result = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/security/auth")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .header("x-auth-token", invalidToken)
        )

        // then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden)

    }
}
