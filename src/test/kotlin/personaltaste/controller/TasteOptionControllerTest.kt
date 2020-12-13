package personaltaste.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.nhaarman.mockitokotlin2.given
import org.hamcrest.Matchers
import org.junit.Assert.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import personaltaste.controller.model.taste.TasteCreateRequest
import personaltaste.controller.model.taste.TasteOptionCreateRequest
import personaltaste.entity.Taste
import personaltaste.entity.TasteOption
import personaltaste.exception.ExceptionCode
import personaltaste.exception.PersonalTasteException
import personaltaste.service.TasteOptionWriteService
import personaltaste.service.model.taste.TasteCreate
import support.test.BaseTest

/**
 * @author seungmin
 */
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TasteOptionControllerTest : BaseTest() {

    @Autowired
    private lateinit var snakeCaseObjectMapper: ObjectMapper

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var tasteOptionWriteService: TasteOptionWriteService

    @Test
    fun `taste option 생성 성공`() {
        // given
        val tasteName = "먹기"
        val tastePriority = 1
        val tasteId = 1L

        val taste = Taste.of(
                name = tasteName,
                priority = tastePriority
        )

        val tasteOptionName = "찍먹"

        given(tasteOptionWriteService.create(tasteId, tasteOptionName)).willReturn(TasteOption.of(taste, tasteOptionName))

        // when
        val result = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/tastes/{taste_id}/options", tasteId)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(
                                snakeCaseObjectMapper.writeValueAsString(
                                        TasteOptionCreateRequest(tasteOptionName)
                                )
                        )
        )

        // then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated)
    }

    @Test
    fun `taste option 생성 실패`() {
        // given
        val tasteId = 1L
        val tasteOptionName = "찍먹"

        given(tasteOptionWriteService.create(tasteId, tasteOptionName)).willThrow(PersonalTasteException(ExceptionCode.INVALID_VALUE, "중복된 취향 선택지가 존재합니다."))

        // when
        val result = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/tastes/{taste_id}/options", tasteId)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(
                                snakeCaseObjectMapper.writeValueAsString(
                                        TasteOptionCreateRequest(tasteOptionName)
                                )
                        )
        )

        // then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
                .andExpect(MockMvcResultMatchers.jsonPath("$.error_code", Matchers.equalTo("INVALID_VALUE")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error_message", Matchers.equalTo("${ExceptionCode.INVALID_VALUE.message} 중복된 취향 선택지가 존재합니다.")))
    }

    @Test
    fun `taste option 삭제 성공`() {
        // given
        val tasteName = "먹기"
        val tastePriority = 1
        val tasteId = 1L
        val tasteOptionId = 1L

        val taste = Taste.of(
                name = tasteName,
                priority = tastePriority
        )

        val tasteOptionName = "찍먹"

        given(tasteOptionWriteService.delete(tasteId, tasteOptionId)).willReturn(TasteOption.of(taste, tasteOptionName).delete())

        // when
        val result = mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/v1/tastes/{taste_id}/options/{taste_option_id}", tasteId, tasteOptionId)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        )

        // then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `taste option 삭제 실패`() {
        // given
        val tasteId = 1L
        val tasteOptionId = 1L

        given(tasteOptionWriteService.delete(tasteId, tasteOptionId)).willThrow(PersonalTasteException(ExceptionCode.NOT_FOUND, "일치하는 취향 옵션이 존재하지 않습니다."))

        // when
        val result = mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/v1/tastes/{taste_id}/options/{taste_option_id}", tasteId, tasteOptionId)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        )

        // then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound)
                .andExpect(MockMvcResultMatchers.jsonPath("$.error_code", Matchers.equalTo("NOT_FOUND")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error_message", Matchers.equalTo("${ExceptionCode.NOT_FOUND.message} 일치하는 취향 옵션이 존재하지 않습니다.")))
    }
}
