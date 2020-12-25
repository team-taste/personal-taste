package personaltaste.repository

import org.springframework.data.jpa.repository.JpaRepository
import personaltaste.entity.Taste
import personaltaste.entity.TasteOption

/**
 *
 *
 * @author seungmin
 */
interface TasteOptionRepository : JpaRepository<TasteOption, Long> {

    fun existsByTasteAndName(taste: Taste, name: String): Boolean

    fun findByIdAndTaste(id : Long, taste: Taste) : TasteOption?
}
