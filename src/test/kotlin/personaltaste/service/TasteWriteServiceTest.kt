package personaltaste.service

import com.nhaarman.mockitokotlin2.given
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import personaltaste.entity.Taste
import personaltaste.entity.model.taste.TasteStatus
import personaltaste.repository.TasteRepository
import personaltaste.service.model.taste.TasteCreate
import support.test.BaseTest
import java.util.*

/**
 * @author seungmin
 */
@SpringBootTest(classes = [TasteWriteService::class])
class TasteWriteServiceTest: BaseTest() {

    @MockBean
    private lateinit var tasteRepository: TasteRepository

    @Autowired
    private lateinit var tasteWriteService: TasteWriteService

    @Test
    fun `create test`() {
        // given
        val name = "먹기"
        val priority = 1
        val tasteCreate = TasteCreate(
                name = name,
                priority = priority
        )

        val taste = tasteCreate.toTaste()

        given(tasteRepository.existsByName(name)).willReturn(false)
        given(tasteRepository.save(taste)).willReturn(taste)

        // when
        val result = tasteWriteService.create(tasteCreate)

        // then
        result shouldBe taste
    }

    @Test
    fun `delete test`() {
        // given
        val eatId = 1L
        val eatName = "먹기"
        val eatPriority = 1

        val eat = Taste.of(
                name = eatName,
                priority = eatPriority
        )

        given(tasteRepository.findById(eatId)).willReturn(Optional.of(eat))

        // when
        val result = tasteWriteService.delete(eatId)

        // then
        result.status shouldBe TasteStatus.DELETE
    }
}
