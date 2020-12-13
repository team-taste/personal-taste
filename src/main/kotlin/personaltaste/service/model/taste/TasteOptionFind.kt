package personaltaste.service.model.taste

import personaltaste.entity.TasteOption
import java.io.Serializable

/**
 *
 *
 * @author seungmin
 */
data class TasteOptionFind(
        val id: Long,
        val name: String
) : Serializable {
    companion object {
        fun of(tasteOption: TasteOption): TasteOptionFind {
            return TasteOptionFind(
                    id = tasteOption.id,
                    name = tasteOption.name
            )
        }
    }
}
