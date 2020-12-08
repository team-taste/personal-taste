package personaltaste.service

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import personaltaste.repository.UserRepository
import personaltaste.repository.UserTasteRepository

@SpringBootTest(classes = [UserTasteCommonService::class])
class UserTasteCommonServiceTest {

    @Autowired
    private lateinit var userTasteCommonService: UserTasteCommonService

    @MockBean
    private lateinit var userRepository: UserRepository

    @MockBean
    private lateinit var userTasteRepository: UserTasteRepository

    @Test
    fun `list 조회 테스트`() {



    }
}