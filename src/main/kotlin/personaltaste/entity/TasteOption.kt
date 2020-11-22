package personaltaste.entity

import javax.persistence.*

/**
 * 취향의 선택값 엔티티
 * e.g.) 찍먹, 부먹
 *
 * @author seungmin
 */
@Entity
@Table(name = "taste_option")
data class TasteOption(

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "taste_id")
        var taste: Taste,

        var name: String,

        var activeYn: Boolean = true

) : BaseEntity() {

    companion object {
        fun of(
                taste: Taste,
                name: String
        ): TasteOption {
            return TasteOption(
                    taste = taste,
                    name = name
            )
        }
    }

    fun delete() {
        this.activeYn = false
    }
}
