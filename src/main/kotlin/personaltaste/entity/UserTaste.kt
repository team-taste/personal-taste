package personaltaste.entity

import javax.persistence.*

/**
 * 사용자가 선택한 TasteOption 정보를 들고 있는 엔티티
 *
 * @author seungmin
 */
@Entity
@Table(name = "user_taste")
data class UserTaste(

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "taste_option_id")
        var tasteOption: TasteOption,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        var user: User,

        var activeYn: Boolean = true

) : BaseEntity() {
    companion object {
        fun of(
                tasteOption: TasteOption,
                user: User
        ): UserTaste {
            return UserTaste(
                    tasteOption = tasteOption,
                    user = user
            )
        }
    }

    fun active(): UserTaste {
        this.activeYn = true

        return this
    }

    fun delete(): UserTaste {
        this.activeYn = false

        return this
    }
}
