package personaltaste.entity

import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import personaltaste.entity.model.user.UserGender
import personaltaste.exception.ExceptionCode
import personaltaste.exception.PersonalTasteException

class UserTest {

    @Test
    fun `create by constructor test`() {
        // given
        val name = "김세훈"
        val email = "sechun0215@gmail.com"
        val age = 20
        val gender = UserGender.MALE
        val password = "1234"

        // when
        val result = User(
                name = name,
                email = email,
                password = password,
                age = age,
                gender = gender
        )

        // then
        assertAll(
                { result.name shouldBe name },
                { result.age shouldBe age },
                { result.password shouldBe password }
        )
    }

    @Test
    fun `create by factory method test`() {
        // given
        val name = "김세훈"
        val email = "sechun0215@gmail.com"
        val age = 20
        val gender = UserGender.MALE
        val password = "1234"

        // when
        val result = User.of(
                name = name,
                email = email,
                password = password,
                age = age,
                gender = gender
        )

        // then
        assertAll(
                { result.name shouldBe name },
                { result.age shouldBe age },
                { result.password shouldBe password }
        )

    }

    @Test
    fun `changePassword test (wrong)`() {
        // given
        val name = "김세훈"
        val email = "sechun0215@gmail.com"
        val age = 20
        val gender = UserGender.MALE
        val password = "1234"

        val user = User.of(
                name = name,
                email = email,
                password = password,
                age = age,
                gender = gender
        )

        // when
        val exception = assertThrows<PersonalTasteException> {
            user.changePassword("1111", "12314")
        }

        // then
        exception.exceptionCode shouldBe ExceptionCode.INVALID_VALUE
    }

    @Test
    fun `changePassword test(success)`() {
        // given
        val name = "김세훈"
        val email = "sechun0215@gmail.com"
        val age = 20
        val gender = UserGender.MALE
        val password = "1234"

        val user = User.of(
                name = name,
                email = email,
                password = password,
                age = age,
                gender = gender
        )

        val changePassword = "1334"

        // when
        val result = user.changePassword(password, changePassword)

        // then
        result.password shouldBe changePassword
    }

}
