package personaltaste.repository

import org.springframework.data.jpa.repository.JpaRepository
import personaltaste.entity.User
import personaltaste.entity.UserTaste

interface UserTasteRepository : JpaRepository<UserTaste, Long> {

    fun findByUser(user: User): List<UserTaste>

}