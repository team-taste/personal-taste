package personaltaste.service.model.taste

import personaltaste.entity.Taste

/**
 *
 *
 * @author seungmin
 */
data class TasteCreate(
        val name: String,
        val priority: Int
) {
    fun toTaste(): Taste {
        return Taste.of(
                name = name,
                priority = priority
        )
    }
}
