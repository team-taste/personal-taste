package personaltaste.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import personaltaste.repository.TasteRepository
import org.springframework.transaction.annotation.Transactional
import personaltaste.entity.Taste
import personaltaste.exception.ExceptionCode
import personaltaste.exception.PersonalTasteException
import personaltaste.service.model.taste.TasteCreate

/**
 * 취향(taste) Entity 생성/삭제 서비스
 *
 * @author seungmin
 */
@Service
class TasteWriteService(
        private val tasteRepository: TasteRepository
) {

    @Transactional
    fun create(tasteCreate: TasteCreate): Taste {
        if (this.checkExists(tasteCreate.name))
            throw PersonalTasteException(ExceptionCode.INVALID_VALUE, "동일한 취향이 이미 생성되어 있습니다.")

        return tasteRepository.save(tasteCreate.toTaste())
    }

    @Transactional
    fun delete(tasteId: Long): Taste {
        return tasteRepository.findByIdOrNull(tasteId)?.delete()
                ?: throw PersonalTasteException(ExceptionCode.NOT_FOUND, "${tasteId}와 일치하는 Taste 가 존재하지 않습니다.")
    }

    private fun checkExists(name: String): Boolean {
        return tasteRepository.existsByName(name)
    }


}
