package personaltaste.repository

import org.springframework.data.jpa.repository.JpaRepository
import personaltaste.entity.Taste
import personaltaste.entity.model.taste.TasteStatus

/**
 *
 *
 * @author seungmin
 */
interface TasteRepository : JpaRepository<Taste, Long> {

    fun findAllByStatusOrderByPriority(status: TasteStatus): List<Taste>

    fun findByIdAndStatus(id : Long, status : TasteStatus) : Taste?

    fun existsByName(name: String) : Boolean
}
