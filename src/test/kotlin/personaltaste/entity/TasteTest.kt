package personaltaste.entity

import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import org.junit.jupiter.api.Test
import personaltaste.entity.model.taste.TasteStatus

/**
 * @author seungmin
 */
class TasteTest {

    @Test
    fun `create by constructor test`() {
        // given
        val name = "먹기"
        val priority = 1

        // when
        val result = Taste(
                name = name,
                priority = priority
        )

        // then
        result.name shouldBe name
        result.priority shouldBe priority
        result.status shouldBe TasteStatus.ACTIVE
    }

    @Test
    fun `create by factory method test`() {
        // given
        val name = "먹기"
        val priority = 1

        // when
        val result = Taste.of(
                name = name,
                priority = priority
        )

        // then
        result.name shouldBe name
        result.priority shouldBe priority
        result.status shouldBe TasteStatus.ACTIVE
    }

    @Test
    fun `changePriority test - success`() {
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

        // when
        eat.changePriority(mintChocolate)

        // then
        eat.name shouldBe eatName
        eat.priority shouldNotBe eatPriority
        eat.priority shouldBe mintChocolatePriority

        mintChocolate.name shouldBe mintChocolateName
        mintChocolate.priority shouldNotBe mintChocolatePriority
        mintChocolate.priority shouldBe eatPriority
    }

    @Test
    fun `sort test`() {
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
        val priorityList = mutableListOf(mintChocolatePriority, eatPriority)

        // when
        tasteList.sort()
        priorityList.sort()

        // then
        tasteList[0].priority = priorityList[0]
        tasteList[1].priority = priorityList[1]
    }

}
