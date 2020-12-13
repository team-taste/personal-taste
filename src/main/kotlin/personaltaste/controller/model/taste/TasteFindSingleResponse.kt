package personaltaste.controller.model.taste

import personaltaste.service.model.taste.TasteFind
import java.io.Serializable

class TasteFindSingleResponse(
        val taste: TasteFind
) : Serializable {
    companion object {
        fun of(taste: TasteFind): TasteFindSingleResponse {
            return TasteFindSingleResponse(
                    taste = taste
            )
        }
    }
}
