package personaltaste.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import personaltaste.controller.model.taste.TasteOptionCreateRequest
import personaltaste.service.TasteOptionWriteService

/**
 * 취향 Option(선택지) 목록 조회, 추가, 삭제
 *
 * @author seungmin
 */
@RestController
@RequestMapping("/api/v1/tastes/{taste_id}/options")
class TasteOptionController(
        private val tasteOptionWriteService: TasteOptionWriteService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
            @PathVariable("taste_id") tasteId: Long,
            @RequestBody tasteOptionCreateRequest: TasteOptionCreateRequest
    ) {
        tasteOptionWriteService.create(tasteId, tasteOptionCreateRequest.name)
    }

    @DeleteMapping("/{taste_option_id}")
    @ResponseStatus(HttpStatus.OK)
    fun delete(
            @PathVariable("taste_id") tasteId: Long,
            @PathVariable("taste_option_id") tasteOptionId: Long
    ) {
        tasteOptionWriteService.delete(tasteId, tasteOptionId)
    }
}
