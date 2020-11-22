package personaltaste.repository

import org.springframework.data.jpa.repository.JpaRepository
import personaltaste.entity.Taste

/**
 *
 *
 * @author seungmin
 */
interface TasteRepository : JpaRepository<Taste, Long> {
}
