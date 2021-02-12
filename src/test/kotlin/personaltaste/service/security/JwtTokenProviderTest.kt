package personaltaste.service.security

import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.core.userdetails.UserDetailsService
import support.test.BaseTest
import java.util.*

/**
 * @author seungmin
 */
@SpringBootTest(classes = [JwtTokenProvider::class])
class JwtTokenProviderTest : BaseTest() {

    @MockBean
    private lateinit var userDetailsService: UserDetailsService

    @Autowired
    private lateinit var jwtTokenProvider: JwtTokenProvider

    @Test
    fun `createToken test`() {
        // given
        val userEmail = "choising"
        val now = Date()

        // when
        val token = jwtTokenProvider.createToken(userEmail, now)
        println(token)

        // then
        token shouldNotBe null
    }

    @Test
    fun `validateToken true`() {
        // given
        val userEmail = "choising"
        val now = Date()

        // 29분 전
        val date = Date(now.time - 29 * 60 * 1000L)
        val token = jwtTokenProvider.createToken(userEmail, date)

        // when
        val result = jwtTokenProvider.validateToken(token)

        // then
        result shouldBe true
    }

    @Test
    fun `validateToken false`() {
        // given
        val userEmail = "choising"
        val now = Date()

        // 31분 전
        val date = Date(now.time - 31 * 60 * 1000L)
        val token = jwtTokenProvider.createToken(userEmail, date)

        // when
        val result = jwtTokenProvider.validateToken(token)

        // then
        result shouldBe false
    }
}
