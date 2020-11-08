package personaltaste.repository

import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import personaltaste.entity.User
import personaltaste.entity.model.user.UserGender

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Test
    fun `create entity`() {
        // given
        val user = User.of(
                name = "김세훈",
                email = "sechun0215@gmail.com",
                password = "1234",
                age = 20,
                gender = UserGender.MALE
        )

        // when
        val result = userRepository.save(user)

        // then
        result.name shouldBe user.name
    }

}