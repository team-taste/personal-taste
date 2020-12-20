package personaltaste.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import personaltaste.entity.User
import personaltaste.entity.UserTaste

interface UserTasteRepository : JpaRepository<UserTaste, Long> {

    fun findByUserAndActiveYn(user: User, activeYn: Boolean = true): List<UserTaste>

    @Query("""
        select userTaste from UserTaste userTaste
        inner join userTaste.tasteOption tasteOption
        where userTaste.user = :user
        and tasteOption.id in :tasteOptionIds
    """)
    fun findAllByUserAndTasteOptionId(
        @Param("user") user: User,
        @Param("tasteOptionIds") tasteOptionIds: List<Long>
    ): List<UserTaste>

}
