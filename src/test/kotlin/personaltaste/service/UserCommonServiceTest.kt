package personaltaste.service

import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.any
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.repository.findByIdOrNull
import personaltaste.entity.User
import personaltaste.entity.model.user.UserGender
import personaltaste.entity.model.user.UserStatus
import personaltaste.repository.UserRepository
import personaltaste.service.model.user.UserCreate
import support.test.BaseTest
import java.util.*

@SpringBootTest(classes = [UserCommonService::class])
class UserCommonServiceTest: BaseTest() {

    @MockBean
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var userCommonService: UserCommonService

    @Test
    fun `create test`() {
        // given
        val userCreate = UserCreate(
            email = "sechun0215@gmail.com",
            name = "김세훈",
            password = "1234",
            age = 20,
            gender = UserGender.MALE
        )

        val user = userCreate.toUser()

        given(userRepository.existsByEmail(any())).willReturn(false)
        given(userRepository.save(user)).willReturn(user)

        // when
        val result = userCommonService.create(userCreate)

        // then
        result shouldBe user
    }

    @Test
    fun `delete test`() {
        // given
        val userId = 1L

        val user = User.of(
            email = "sechun0215@gmail.com",
            name = "김세훈",
            password = "1234",
            age = 20,
            gender = UserGender.MALE
        )

        given(userRepository.findById(userId)).willReturn(Optional.of(user))

        // when
        val result = userCommonService.delete(userId)

        // then
        result.status shouldBe UserStatus.DELETE
    }

}