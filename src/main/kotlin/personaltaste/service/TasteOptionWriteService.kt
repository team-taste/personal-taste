package personaltaste.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import personaltaste.entity.Taste
import personaltaste.entity.TasteOption
import personaltaste.exception.ExceptionCode
import personaltaste.exception.PersonalTasteException
import personaltaste.repository.TasteOptionRepository

@Service
class TasteOptionWriteService(
        private val tasteOptionRepository: TasteOptionRepository,
        private val tasteFindService: TasteFindService
) {

    @Transactional
    fun create(tasteId: Long, tasteOptionName: String): TasteOption {
        val taste = tasteFindService.findOneActiveTaste(tasteId)

        if (this.checkExists(taste, tasteOptionName))
            throw PersonalTasteException(ExceptionCode.INVALID_VALUE, "중복된 취향 선택지가 존재합니다.")

        return tasteOptionRepository.save(TasteOption.of(taste, tasteOptionName))
    }

    @Transactional
    fun delete(tasteId: Long, tasteOptionId: Long): TasteOption {
        val taste = tasteFindService.findOneActiveTaste(tasteId)

        return tasteOptionRepository.findByIdAndTaste(tasteOptionId, taste)?.delete()
                ?: throw PersonalTasteException(ExceptionCode.NOT_FOUND, "tasteId : $tasteId, tasteOptionId: $tasteOptionId 와 일치하는 취향 옵션이 존재하지 않습니다.")
    }

    private fun checkExists(taste: Taste, tasteOptionName: String): Boolean {
        // todo activeYn 을 일부로 조회 조건에 포함하지 않았는데, 논리삭제된 것과 동일한 entity 를 생성하려 할 때 어떻게 할 지 정해야 할 듯.
        return tasteOptionRepository.existsByTasteAndName(taste, tasteOptionName)
    }


}