package personaltaste.service

import com.nhaarman.mockitokotlin2.given
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import personaltaste.entity.Taste
import personaltaste.entity.TasteOption
import personaltaste.entity.User
import personaltaste.entity.model.user.UserGender
import personaltaste.entity.model.user.UserStatus
import personaltaste.repository.TasteOptionRepository
import support.test.BaseTest
import java.util.*

/**
 * @author seungmin
 */
@SpringBootTest(classes = [TasteOptionWriteService::class])
class TasteOptionWriteServiceTest: BaseTest() {

    @MockBean
    private lateinit var tasteOptionRepository: TasteOptionRepository

    @MockBean
    private lateinit var tasteFindService: TasteFindService

    @Autowired
    private lateinit var tasteOptionWriteService: TasteOptionWriteService

    @Test
    fun `create test`() {
        // given
        val tasteName = "먹기"
        val tastePriority = 1
        val tasteId = 1L

        val tasteOptionName = "찍먹"

        val taste = Taste.of(
                name = tasteName,
                priority = tastePriority
        )

        val tasteOption = TasteOption.of(taste, tasteOptionName)

        given(tasteOptionRepository.existsByTasteAndName(taste, tasteOptionName)).willReturn(false)
        given(tasteOptionRepository.save(tasteOption)).willReturn(tasteOption)
        given(tasteFindService.findOneActiveTaste(tasteId)).willReturn(taste)

        // when
        val result = tasteOptionWriteService.create(tasteId, tasteOptionName)

        // then
        result shouldBe tasteOption
    }

    @Test
    fun `delete test`() {
        // given
        val tasteName = "먹기"
        val tastePriority = 1
        val tasteId = 1L
        val tasteOptionId = 2L

        val tasteOptionName = "찍먹"

        val taste = Taste.of(
                name = tasteName,
                priority = tastePriority
        )

        val tasteOption = TasteOption.of(taste, tasteOptionName)

        given(tasteFindService.findOneActiveTaste(tasteId)).willReturn(taste)
        given(tasteOptionRepository.findByIdAndTaste(tasteOptionId, taste)).willReturn(tasteOption)

        // when
        val result = tasteOptionWriteService.delete(tasteId, tasteOptionId)

        // then
        result.activeYn shouldBe false
    }
}
