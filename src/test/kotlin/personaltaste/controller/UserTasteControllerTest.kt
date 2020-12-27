package personaltaste.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import mu.KotlinLogging
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import personaltaste.entity.Taste
import personaltaste.entity.TasteOption
import personaltaste.entity.User
import personaltaste.entity.UserTaste
import personaltaste.resolver.UserExtractResolver
import personaltaste.service.UserTasteCommonService

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class UserTasteControllerTest {

    private val logger = KotlinLogging.logger {}

    @Autowired
    private lateinit var snakeCaseObjectMapper: ObjectMapper

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var userTasteCommonService: UserTasteCommonService

    @MockBean
    private lateinit var userExtractResolver: UserExtractResolver

    @Test
    fun `userTaste 목록 조회 성공`() {
        // given
        val userId = 1L
        val user = mock(User::class.java)
        val tTaste = Taste.of("탕수육", 1)
        val cTaste = Taste.of("콜라", 1)
        val tTasteOption = TasteOption.of(tTaste, "찍먹")
        val cTasteOption = TasteOption.of(cTaste, "펩시")
        val userTastes = listOf<UserTaste>(
            UserTaste.of(cTasteOption, user),
            UserTaste.of(tTasteOption, user)
        )

        given(userExtractResolver.supportsParameter(any())).willCallRealMethod()
        given(userExtractResolver.resolveArgument(any(), any(), any(), any())).willReturn(user)

        given(userTasteCommonService.list(user)).willReturn(userTastes)

        // when
        val result = mockMvc.perform(
            MockMvcRequestBuilders.get("/api/v1/user-tastes")
                .header("User-Id", userId.toString())
        )

        // then
        result
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.user_tastes").isArray)
            .andExpect(jsonPath("$.user_tastes", hasSize<Any>(userTastes.size)))

        logger.debug("result : {}", result.andReturn().response)
    }


}