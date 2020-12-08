package personaltaste.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import personaltaste.service.UserTasteCommonService
import personaltaste.service.model.user_taste.UserTasteListResponse

@RestController
@RequestMapping("/api/v1/user-tastes")
class UserTasteController(
    private val userTasteService: UserTasteCommonService
) {


    /**
     * - 필요해지면 paging 구현
     * - header의 user-id login 기능 구현 후에 token으로 user 가져오는 것으로 변경
     * - user의 userTaste들을 lazy로 가져올 때 문제가 될지 확인 후 아예 명시적 조회로 변경할지 말지 결정
     */
    @GetMapping("/list")
    fun list(
        @RequestHeader("user-id") userId: Long
    ): UserTasteListResponse {
        return UserTasteListResponse.ofUserTastes(userTasteService.list(userId))
    }



}