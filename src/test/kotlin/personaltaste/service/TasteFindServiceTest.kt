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
import personaltaste.service.model.taste.TasteFind
import support.test.BaseTest

/**
 * @author seungmin
 */
@SpringBootTest(classes = [TasteFindService::class])
class TasteFindServiceTest: BaseTest() {

    @MockBean
    private lateinit var tasteRepository: TasteRepository

    @Autowired
    private lateinit var tasteFindService: TasteFindService

    @Test
    fun `findAll test`() {
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

        val tasteList = mutableListOf(mintChocolate, eat)

        val tasteFindEat = TasteFind.ofOnlyActiveOption(eat)
        val tasteFindMintChocolate = TasteFind.ofOnlyActiveOption(mintChocolate)

        val tasteFindList = mutableListOf(tasteFindMintChocolate, tasteFindEat)

        given(tasteRepository.findAllByStatusOrderByPriority(TasteStatus.ACTIVE)).willReturn(tasteList)

        // when
        val result = tasteFindService.findAllActive()

        // then
        result shouldBe tasteFindList
    }
}
