package personaltaste.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import personaltaste.controller.model.taste.TasteFindMultiResponse
import personaltaste.service.TasteFindService

/**
 * 취향 (Taste) 목록조회, 추가, 삭제
 *
 * @author seungmin
 */
@RestController
@RequestMapping("/api/v1/tastes")
class TasteController(
        private val tasteFindService: TasteFindService
) {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun findAll() : TasteFindMultiResponse {
        // todo paging
        return TasteFindMultiResponse.of(tasteFindService.findAllActive())
    }

}
