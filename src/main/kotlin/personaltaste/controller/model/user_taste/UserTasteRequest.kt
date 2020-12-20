package personaltaste.controller.model.user_taste

import com.fasterxml.jackson.annotation.JsonIgnore

data class UserTasteRequest(
    val tasteOptionIds: List<String>
) {

    @JsonIgnore
    fun getIds(): List<Long> = tasteOptionIds.map {
        it.toLong()
    }

}
