package personaltaste.service.model.user

import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test
import personaltaste.entity.model.user.UserGender
import support.test.BaseTest

class UserCreateTest: BaseTest() {

    @Test
    fun `to User test`() {
        // given
        val userCreate = UserCreate(
            email = "sechun0215@gmail.com",
            name = "김세훈",
            password = "1234",
            age = 20,
            gender = UserGender.MALE
        )

        // when
        val result = userCreate.toUser()

        // then
        result.email shouldBe userCreate.email
    }

}