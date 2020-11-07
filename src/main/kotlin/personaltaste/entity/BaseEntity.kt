package personaltaste.entity

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
open class BaseEntity(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long = 0L,

        @CreatedBy
        var createdBy: String = DEFAULT_CREATED_BY,

        @CreatedDate
        var createdAt: LocalDateTime = LocalDateTime.now(),

        @LastModifiedBy
        var updatedBy: String = DEFAULT_CREATED_BY,

        @LastModifiedDate
        var updatedAt: LocalDateTime = LocalDateTime.now()

) : Serializable {

    companion object {
        const val DEFAULT_CREATED_BY: String = "SYSTEM"
    }
}