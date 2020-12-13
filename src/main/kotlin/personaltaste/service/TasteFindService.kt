package personaltaste.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import personaltaste.entity.model.taste.TasteStatus
import personaltaste.repository.TasteRepository
import personaltaste.service.model.taste.TasteFind

/**
 *
 *
 * @author seungmin
 */
@Service
class TasteFindService(
        private val tasteRepository: TasteRepository
) {
    @Transactional(readOnly = true)
    fun findAllActive(): List<TasteFind> {
        val tastes = tasteRepository.findAllByStatusOrderByPriority(TasteStatus.ACTIVE)

        return tastes.map(TasteFind.Companion::ofOnlyActiveOption)
    }
}
