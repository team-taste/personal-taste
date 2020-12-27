package personaltaste.repository

import io.kotlintest.matchers.collections.shouldContain
import io.kotlintest.shouldBe
import mu.KotlinLogging
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import personaltaste.entity.Taste
import personaltaste.entity.TasteOption
import personaltaste.entity.User
import personaltaste.entity.UserTaste
import personaltaste.entity.model.user.UserGender

@DataJpaTest
class UserTasteRepositoryTest {

    private val logger = KotlinLogging.logger {}

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var tasteOptionRepository: TasteOptionRepository

    @Autowired
    private lateinit var tasteRepository: TasteRepository

    @Autowired
    private lateinit var userTasteRepository: UserTasteRepository

    lateinit var user: User
    lateinit var tangTaste: Taste
    lateinit var colaTaste: Taste
    lateinit var tangOption1: TasteOption
    lateinit var tangOption2: TasteOption
    lateinit var colaOption1: TasteOption
    lateinit var colaOption2: TasteOption

    @BeforeEach
    fun setUp() {
        user = userRepository.save(
            User.of(
                name = "김세훈",
                email = "sechun0215@gmail.com",
                password = "1234",
                age = 20,
                gender = UserGender.MALE
            )
        )

        tangTaste = tasteRepository.save(
            Taste.of("탕수육", 1)
        )

        colaTaste = tasteRepository.save(
            Taste.of("콜라", 1)
        )

        tangOption1 = tasteOptionRepository.save(
            TasteOption.of(tangTaste, "부먹")
        )

        tangOption2 = tasteOptionRepository.save(
            TasteOption.of(tangTaste, "찍먹")
        )

        colaOption1 = tasteOptionRepository.save(
            TasteOption.of(colaTaste, "팹시")
        )

        colaOption2 = tasteOptionRepository.save(
            TasteOption.of(colaTaste, "코")
        )

    }

    @Test
    fun `{findAllByUserAndTasteOptionId} test`() {
        // given
        val ids = listOf<Long>(
            tangOption1.id,
            tangOption2.id,
            colaOption1.id,
            colaOption2.id
        )

        val userTastes = userTasteRepository.saveAll(
            listOf(
                UserTaste.of(colaOption1, user),
                UserTaste.of(tangOption2, user)
            )
        )

        // when
        val result = userTasteRepository.findAllByUserAndTasteOptionId(user, ids)

        // then
        logger.debug("result : {}", result)

        result.map { it.tasteOption } shouldContain colaOption1
        result.map { it.tasteOption } shouldContain tangOption2
        result.size shouldBe 2
    }

}
