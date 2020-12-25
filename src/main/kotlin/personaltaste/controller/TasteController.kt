package personaltaste.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import personaltaste.controller.model.taste.TasteCreateRequest
import personaltaste.controller.model.taste.TasteFindMultiResponse
import personaltaste.controller.model.taste.TasteFindSingleResponse
import personaltaste.service.TasteFindService
import personaltaste.service.TasteWriteService

/**
 * 취향 (Taste) 목록조회, 추가, 삭제
 *
 * @author seungmin
 */
@RestController
@RequestMapping("/api/v1/tastes")
class TasteController(
        private val tasteFindService: TasteFindService,
        private val tasteWriteService: TasteWriteService
) {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun findAll() : TasteFindMultiResponse {
        // todo paging
        return TasteFindMultiResponse.of(tasteFindService.findAllActive())
    }

    @GetMapping("/{taste_id}")
    @ResponseStatus(HttpStatus.OK)
    fun findOne(@PathVariable("taste_id") tasteId: Long) : TasteFindSingleResponse {
        return TasteFindSingleResponse.of(tasteFindService.findOneActive(tasteId))
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
            @RequestBody tasteCreateRequest: TasteCreateRequest
    ) {
        tasteWriteService.create(tasteCreateRequest.taste)
    }

    @DeleteMapping("/{taste_id}")
    @ResponseStatus(HttpStatus.OK)
    fun delete(
            @PathVariable("taste_id") tasteId: Long
    ) {
        tasteWriteService.delete(tasteId)
    }

}
