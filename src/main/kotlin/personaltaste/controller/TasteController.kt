package personaltaste.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import personaltaste.controller.model.taste.TasteCreateRequest
import personaltaste.controller.model.taste.TasteFindMultiResponse
import personaltaste.service.TasteFindService
import personaltaste.service.TasteManageService

/**
 * 취향 (Taste) 목록조회, 추가, 삭제
 *
 * @author seungmin
 */
@RestController
@RequestMapping("/api/v1/tastes")
class TasteController(
        private val tasteFindService: TasteFindService,
        private val tasteManageService: TasteManageService
) {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun findAll() : TasteFindMultiResponse {
        // todo paging
        return TasteFindMultiResponse.of(tasteFindService.findAllActive())
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
            @RequestBody tasteCreateRequest: TasteCreateRequest
    ) {
        tasteManageService.create(tasteCreateRequest.taste)
    }

    @DeleteMapping("/{taste_id}")
    @ResponseStatus(HttpStatus.OK)
    fun delete(
            @PathVariable("taste_id") tasteId: Long
    ) {
        tasteManageService.delete(tasteId)
    }

}
