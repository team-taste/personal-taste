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
import personaltaste.controller.model.taste.TasteCreateRequest
import personaltaste.entity.Taste
import personaltaste.exception.ExceptionCode
import personaltaste.exception.PersonalTasteException
import personaltaste.service.TasteFindService
import personaltaste.service.TasteWriteService
import personaltaste.service.model.taste.TasteCreate
import personaltaste.service.model.taste.TasteFind
import support.test.BaseTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class TasteControllerTest : BaseTest() {

    @Autowired
    private lateinit var snakeCaseObjectMapper: ObjectMapper

    @MockBean
    private lateinit var tasteFindService: TasteFindService

    @MockBean
    private lateinit var tasteWriteService: TasteWriteService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `taste 목록 조회 성공`() {
        // given
        val eatName = "먹기"
        val eatPriority = 1

        val mintChocolateName = "민초"
        val mintChocolatePriority = 2

        val eat = Taste.of(
                name = eatName,
                priority = eatPriority
        )

        val mintChocolate = Taste.of(
                name = mintChocolateName,
                priority = mintChocolatePriority
        )

        val tasteFindList = mutableListOf(TasteFind.ofOnlyActiveOption(mintChocolate), TasteFind.ofOnlyActiveOption(eat))

        given(tasteFindService.findAllActive()).willReturn(tasteFindList)

        // when
        val result = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/tastes")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        )

        // then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk)
                .andExpect(jsonPath("tastes[0].name").value(mintChocolateName))
                .andExpect(jsonPath("tastes[1].name").value(eatName))
    }

    @Test
    fun `taste 생성 성공`() {
        // given
        val name = "먹기"
        val priority = 1
        val tasteCreate = TasteCreate(
                name = name,
                priority = priority
        )

        given(tasteWriteService.create(tasteCreate)).willReturn(tasteCreate.toTaste())

        // when
        val result = mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/tastes")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(
                    snakeCaseObjectMapper.writeValueAsString(
                        TasteCreateRequest(tasteCreate)
                    )
                )
        )

        // then
        result
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isCreated)
    }

    @Test
    fun `taste 생성 실패 (이미 name 등록 되어 있는 경우)`() {
        // given
        val name = "먹기"
        val priority = 1
        val tasteCreate = TasteCreate(
                name = name,
                priority = priority
        )

        given(tasteWriteService.create(tasteCreate)).willThrow(PersonalTasteException(ExceptionCode.INVALID_VALUE, "중복된 name 존재"))

        // when
        val result = mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/tastes")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(
                    snakeCaseObjectMapper.writeValueAsString(
                        TasteCreateRequest(tasteCreate)
                    )
                )
        )

        // then
        result
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.error_code", equalTo("INVALID_VALUE")))
            .andExpect(jsonPath("$.error_message", equalTo("${ExceptionCode.INVALID_VALUE.message} 중복된 name 존재") ))
    }

    @Test
    fun `taste 삭제 성공`() {
        // given
        val eatId = 1L
        val eatName = "먹기"
        val eatPriority = 1

        val eat = Taste.of(
                name = eatName,
                priority = eatPriority
        )

        given(tasteWriteService.delete(eatId)).willReturn(eat.delete())

        // when
        val result = mockMvc.perform(
            MockMvcRequestBuilders.delete("/api/v1/tastes/{taste_id}", eatId)
        )

        // then
        result
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk)
    }

    @Test
    fun `taste 삭제 실패`() {
        // given
        val eatId = 1L
        given(tasteWriteService.delete(eatId)).willThrow(PersonalTasteException(ExceptionCode.NOT_FOUND, "일치하는 Taste 가 존재하지 않습니다."))

        // when
        val result = mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/v1/tastes/{taste_id}", eatId)
        )

        // then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound)
                .andExpect(jsonPath("$.error_code", equalTo("NOT_FOUND")))
                .andExpect(jsonPath("$.error_message", equalTo("${ExceptionCode.NOT_FOUND.message} 일치하는 Taste 가 존재하지 않습니다.") ))
    }

}
