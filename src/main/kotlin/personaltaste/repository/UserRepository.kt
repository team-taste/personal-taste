package personaltaste.repository

import org.springframework.data.jpa.repository.JpaRepository
import personaltaste.entity.User


interface UserRepository : JpaRepository<User, Long> {

}
