package personaltaste.entity

import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

/**
 * @author seungmin
 */
class TasteOptionTest {

    @Test
    fun `create by factory method test`() {
        // given
        val name = "먹기"
        val priority = 1

        val taste = Taste.of(
                name = name,
                priority = priority
        )

        val optionName = "찍먹"

        // when
        val result = TasteOption.of(
                taste = taste,
                name = optionName
        )

        // then
        result.name shouldBe optionName
        result.taste shouldBe taste
    }
}
