package personaltaste.service

import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.times
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.anyOrNull
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import personaltaste.entity.TasteOption
import personaltaste.entity.User
import personaltaste.entity.UserTaste
import personaltaste.repository.TasteOptionRepository
import personaltaste.repository.UserTasteRepository
import support.test.BaseTest

@SpringBootTest(classes = [UserTasteCommonService::class])
class UserTasteCommonServiceTest : BaseTest() {

    @Autowired
    private lateinit var userTasteCommonService: UserTasteCommonService

    @MockBean
    private lateinit var userTasteRepository: UserTasteRepository

    @MockBean
    private lateinit var tasteOptionRepostory: TasteOptionRepository

    @Test
    fun `list 조회 테스트`() {
        // given
        val user = mock(User::class.java)
        val cOptionName = "펩시"
        val tOptionName = "찍먹"
        val tTasteOption = mock(TasteOption::class.java)
        val cTasteOption = mock(TasteOption::class.java)
        val userTastes = listOf<UserTaste>(
            UserTaste.of(cTasteOption, user),
            UserTaste.of(tTasteOption, user)
        )

        given(tTasteOption.name).willReturn(tOptionName)
        given(cTasteOption.name).willReturn(cOptionName)
        given(userTasteRepository.findByUserAndActiveYn(user, true)).willReturn(userTastes)

        // when
        val result = userTasteCommonService.list(user)

        // then
        result[0].tasteOption.name shouldBe cOptionName
        result[1].tasteOption.name shouldBe tOptionName
    }

    @Test
    fun `{bulk create} 성공`() {
        // given
        val user = mock(User::class.java)
        val tasteOptionIds = listOf(1L, 2L, 3L, 4L)
        val tTasteOption = mock(TasteOption::class.java)
        val cTasteOption = mock(TasteOption::class.java)
        val existTastes = listOf(
            UserTaste.of(tTasteOption, user),
            UserTaste.of(cTasteOption, user)
        )
        val tasteOptions = listOf(
            mock(TasteOption::class.java),
            mock(TasteOption::class.java)
        )

        val merge = existTastes.plus(tasteOptions.map {
            UserTaste.of(it, user)
        })

        given(cTasteOption.id).willReturn(1L)
        given(tTasteOption.id).willReturn(3L)
        given(tasteOptionRepostory.findAllById(any())).willReturn(tasteOptions)
        given(userTasteRepository.findAllByUserAndTasteOptionId(any(), any())).willReturn(existTastes)
        given(userTasteRepository.saveAll(merge)).willReturn(merge)

        // when
        val result = userTasteCommonService.bulkCreate(user, tasteOptionIds)

        // then
        result shouldBe merge
        verify(userTasteRepository, times(1)).saveAll(merge)

    }

    @Test
    fun `{bulk delete} 성공`() {
        // given
        val user = mock(User::class.java)
        val tasteOptionIds = listOf(1L, 2L)
        val tTasteOption = mock(TasteOption::class.java)
        val cTasteOption = mock(TasteOption::class.java)
        val existTastes = listOf(
            UserTaste.of(tTasteOption, user),
            UserTaste.of(cTasteOption, user)
        )

        given(userTasteRepository.findAllByUserAndTasteOptionId(any(), any())).willReturn(existTastes)

        // when
        val result = userTasteCommonService.bulkDelete(user, tasteOptionIds)

        // then
        result[0].activeYn shouldBe false
        result[1].activeYn shouldBe false
    }

}
