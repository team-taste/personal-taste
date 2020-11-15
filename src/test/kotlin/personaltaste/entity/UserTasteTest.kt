package personaltaste.entity

import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test
import personaltaste.entity.model.user.UserGender

/**
 * @author seungmin
 */
class UserTasteTest {

    @Test
    fun `create by factory method test`() {
        // given
        val user = createMockUser()
        val taste = createMockTaste()
        val tasteOption = createMockTasteOption(taste)

        // when
        val result = UserTaste.of(
                user = user,
                tasteOption = tasteOption
        )

        // then
        result.user shouldBe user
        result.tasteOption shouldBe tasteOption

        println(result)
    }

    private fun createMockUser(): User {
        val name = "김세훈"
        val email = "sechun0215@gmail.com"
        val age = 20
        val gender = UserGender.MALE
        val password = "1234"

        return User.of(
                name = name,
                email = email,
                password = password,
                age = age,
                gender = gender
        )
    }

    private fun createMockTaste(): Taste {
        val name = "먹기"
        val priority = 1

        return Taste.of(
                name = name,
                priority = priority
        )
    }

    private fun createMockTasteOption(taste: Taste): TasteOption {
        val name = "찍먹"

        return TasteOption.of(
                taste = taste,
                name = name
        )
    }
}
