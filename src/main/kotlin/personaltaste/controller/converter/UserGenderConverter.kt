package personaltaste.controller.converter

import com.fasterxml.jackson.databind.util.StdConverter
import personaltaste.entity.model.user.UserGender

class UserGenderConverter: StdConverter<String, UserGender>() {
    override fun convert(value: String): UserGender {
        return UserGender.valueOf(value)
    }

}