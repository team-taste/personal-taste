package personaltaste.controller.model.user_taste

import personaltaste.entity.UserTaste

/**
 * Created by dom.alpha on 2020/12/27.
 */
data class UserTasteResponse(
    val userOptionIds: List<Long>
) {

    companion object {
        fun ofUserTastes(userTastes: List<UserTaste>): UserTasteResponse {
            return UserTasteResponse(userTastes.map { it.id })
        }
    }

}