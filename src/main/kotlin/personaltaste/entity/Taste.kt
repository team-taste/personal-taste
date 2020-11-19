package personaltaste.entity

import personaltaste.entity.model.taste.TasteStatus
import javax.persistence.*

/**
 * 취향 엔티티
 *
 * @author seungmin
 */
@Entity
@Table(name = "taste")
data class Taste(

        var name: String,

        var priority: Int,

        @Enumerated(EnumType.STRING)
        var status: TasteStatus = TasteStatus.ACTIVE,

        @OneToMany(mappedBy = "taste", fetch = FetchType.LAZY)
        var tasteOptions: MutableList<TasteOption> = mutableListOf()

) : BaseEntity(), Comparable<Taste> {

    companion object {
        fun of(
                name: String,
                priority: Int
        ): Taste {
            return Taste(
                    name = name,
                    priority = priority
            )
        }
    }

    override fun compareTo(other: Taste): Int {
        return this.priority - other.priority
    }

    fun changePriority(other: Taste) {
        val temp = this.priority
        this.priority = other.priority
        other.priority = temp
    }
}
