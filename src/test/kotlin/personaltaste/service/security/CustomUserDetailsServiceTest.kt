package personaltaste.service.security

import com.nhaarman.mockitokotlin2.given
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.core.userdetails.UserDetails
import personaltaste.entity.User
import personaltaste.entity.model.user.UserGender
import personaltaste.exception.PersonalTasteException
import personaltaste.repository.UserRepository
import support.test.BaseTest

/**
 * @author seungmin
 */
@SpringBootTest(classes = [CustomUserDetailsService::class])
class CustomUserDetailsServiceTest : BaseTest() {

    @MockBean
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var customUserDetailsService: CustomUserDetailsService

    @Test
    fun `loadUserByUsername - exists email`() {
        // given
        val email = "choising"
        val user = User.of(
                email = email,
                name = "최싱",
                password = "1234",
                age = 30,
                gender = UserGender.MALE
        )
        given(userRepository.findByEmail(email)).willReturn(user)

        // when
        val customUserDetails: UserDetails = customUserDetailsService.loadUserByUsername(email)

        // then
        customUserDetails.username shouldBe email
    }

    @Test
    fun `loadUserByUsername - not exists email`() {
        // given
        val email = "choising"
        given(userRepository.findByEmail(email)).willReturn(null)

        // expect
        Assertions.assertThrows(PersonalTasteException::class.java) {
            customUserDetailsService.loadUserByUsername(email)
        }
    }

}
