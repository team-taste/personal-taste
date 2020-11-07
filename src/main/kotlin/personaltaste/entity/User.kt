package personaltaste.entity

import personaltaste.entity.model.user.UserGender
import personaltaste.entity.model.user.UserStatus
import personaltaste.exception.ExceptionCode
import personaltaste.exception.PTException
import javax.persistence.*

@Entity
@Table(name = "user")
data class User(
        var userName: String,

        @Column(unique = true)
        val email: String,

        var password: String,

        val age: Int,

        val gender: UserGender,

        @Enumerated(EnumType.STRING)
        var status: UserStatus = UserStatus.ACTIVE
) : BaseEntity() {

    companion object {
        fun of(
                userName: String,
                email: String,
                password: String,
                age: Int,
                gender: UserGender
        ): User {
            return User(
                    userName = userName,
                    email = email,
                    password = password,
                    age = age,
                    gender = gender
            )
        }
    }


    fun changePassword(prePassword: String, changePassword: String): User {
        if (password != prePassword)
            throw PTException(ExceptionCode.INVALID_VALUE)

        this.password = changePassword

        return this
    }

}
