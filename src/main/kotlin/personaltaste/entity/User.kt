package personaltaste.entity

import personaltaste.entity.model.user.UserGender
import personaltaste.entity.model.user.UserStatus
import personaltaste.exception.ExceptionCode
import personaltaste.exception.PersonalTasteException
import javax.persistence.*

@Entity
@Table(name = "user")
data class User(
        var name: String,

        @Column(unique = true)
        val email: String,

        var password: String,

        val age: Int,

        @Enumerated(EnumType.STRING)
        val gender: UserGender,

        @Enumerated(EnumType.STRING)
        var status: UserStatus = UserStatus.ACTIVE,

        @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
        var userTastes: MutableList<UserTaste> = mutableListOf()

) : BaseEntity() {

    companion object {
        fun of(
                name: String,
                email: String,
                password: String,
                age: Int,
                gender: UserGender
        ): User {
            return User(
                    name = name,
                    email = email,
                    password = password,
                    age = age,
                    gender = gender
            )
        }
    }

    fun changePassword(prePassword: String, changePassword: String): User {
        if (password != prePassword)
            throw PersonalTasteException(ExceptionCode.INVALID_VALUE)

        this.password = changePassword

        return this
    }

    fun delete(): User {
        this.status = UserStatus.DELETE

        return this
    }

}
