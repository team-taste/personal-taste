package personaltaste.repository

import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import personaltaste.entity.Taste

@DataJpaTest
class TasteRepositoryTest {

    @Autowired
    private lateinit var tasteRepository: TasteRepository

    @Test
    fun `create entity`() {
        // given
        val taste = Taste.of(
                name = "먹기",
                priority = 1
        )

        // when
        val result = tasteRepository.save(taste)

        // then
        result.name shouldBe taste.name
        result.priority shouldBe taste.priority

        println(result)
    }

}
