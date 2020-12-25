package personaltaste.controller.model.taste

import personaltaste.service.model.taste.TasteFind
import java.io.Serializable

/**
 *
 *
 * @author seungmin
 */
data class TasteFindMultiResponse(
        val tastes: List<TasteFind>
) : Serializable {
    companion object {
        fun of(tastes: List<TasteFind>): TasteFindMultiResponse {
            return TasteFindMultiResponse(
                    tastes = tastes
            )
        }
    }
}
