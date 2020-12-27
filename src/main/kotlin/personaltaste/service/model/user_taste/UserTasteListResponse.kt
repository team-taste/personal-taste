package personaltaste.service.model.user_taste

import personaltaste.entity.UserTaste

data class UserTasteListResponse(
    val userTastes: List<UserTasteData>
) {

    data class UserTasteData(
        val tasteName: String,
        val optionName: String,
        val activeYn: Boolean
    ) {

        companion object {
            fun ofUserTaste(userTaste: UserTaste): UserTasteData {
                return UserTasteData(
                    tasteName = userTaste.tasteOption.taste.name,
                    optionName = userTaste.tasteOption.name,
                    activeYn = userTaste.activeYn
                )
            }
        }

    }

    companion object {
        fun ofUserTastes(userTastes: List<UserTaste>): UserTasteListResponse {
            return UserTasteListResponse(
                userTastes.map {
                    UserTasteData.ofUserTaste(it)
                }
            )
        }
    }

}