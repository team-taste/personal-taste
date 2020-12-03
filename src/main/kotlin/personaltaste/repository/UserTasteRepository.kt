package personaltaste.repository

import org.springframework.data.jpa.repository.JpaRepository
import personaltaste.entity.UserTaste

interface UserTasteRepository : JpaRepository<UserTaste, Long> {
}