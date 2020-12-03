package personaltaste.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/user-tastes")
class UserTasteController(
    private val useTasteService:
) {


    /**
     * - 필요해지면 paging 구현
     * - header의 user-id login 기능 구현 후에 token으로 user 가져오는 것으로 변경
     */
    @GetMapping("/list")
    fun list(
        @RequestHeader("user-id") userId: String
    ) {

    }

}