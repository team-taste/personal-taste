package personaltaste.service.model.taste

import personaltaste.entity.Taste
import personaltaste.entity.TasteOption
import java.io.Serializable

/**
 *
 *
 * @author seungmin
 */
data class TasteFind(
        val id: Long,
        val name: String,
        val tasteOptions: List<TasteOptionFind>
) : Serializable {
    companion object {
        fun ofOnlyActiveOption(taste: Taste): TasteFind {
            return TasteFind(
                    id = taste.id,
                    name = taste.name,
                    tasteOptions = taste.tasteOptions
                            .filter(TasteOption::activeYn)
                            .map(TasteOptionFind.Companion::of)
            )
        }
    }
}
