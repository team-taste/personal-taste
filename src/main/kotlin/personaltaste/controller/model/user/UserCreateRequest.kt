package personaltaste.controller.model.user

import personaltaste.service.model.user.UserCreate

data class UserCreateRequest(
    val user: UserCreate
)