package personaltaste.service.model.user

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import personaltaste.controller.converter.UserGenderConverter
import personaltaste.entity.User
import personaltaste.entity.model.user.UserGender

data class UserCreate(
    val email: String,
    val name: String,
    val password: String,
    val age: Int,
    @JsonDeserialize(converter = UserGenderConverter::class)
    val gender: UserGender
) {

    fun toUser(): User {
        return User.of(
            name = name,
            email = email,
            password = password,
            age = age,
            gender = gender
        )
    }

}