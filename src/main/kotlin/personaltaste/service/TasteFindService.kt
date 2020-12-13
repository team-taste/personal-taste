package personaltaste.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import personaltaste.entity.model.taste.TasteStatus
import personaltaste.exception.ExceptionCode
import personaltaste.exception.PersonalTasteException
import personaltaste.repository.TasteRepository
import personaltaste.service.model.taste.TasteFind

/**
 * 취향 (Taste) Entity 를 조회하는 서비스.
 * 클래스 자체를 read Only Transaction 으로 묶었다.
 *
 * @author seungmin
 */
@Service
@Transactional(readOnly = true)
class TasteFindService(
        private val tasteRepository: TasteRepository
) {

    fun findAllActive(): List<TasteFind> {
        val tastes = tasteRepository.findAllByStatusOrderByPriority(TasteStatus.ACTIVE)

        return tastes.map(TasteFind.Companion::ofOnlyActiveOption)
    }

    fun findOneActive(tasteId : Long) : TasteFind {
        val taste = tasteRepository.findByIdAndStatus(tasteId, TasteStatus.ACTIVE)
                ?: throw PersonalTasteException(ExceptionCode.NOT_FOUND, "${tasteId}에 맞는 취향이 존재하지 않습니다.")

        return TasteFind.ofOnlyActiveOption(taste)
    }
}
