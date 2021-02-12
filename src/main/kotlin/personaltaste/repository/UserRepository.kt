package personaltaste.repository

import org.springframework.data.jpa.repository.JpaRepository
import personaltaste.entity.User


interface UserRepository : JpaRepository<User, Long> {

    fun existsByEmail(email: String): Boolean

    fun findByEmail(email: String): User?

}
