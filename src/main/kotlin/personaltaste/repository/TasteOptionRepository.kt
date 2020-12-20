package personaltaste.repository

import org.springframework.data.jpa.repository.JpaRepository
import personaltaste.entity.TasteOption

interface TasteOptionRepository: JpaRepository<TasteOption, Long> {

}