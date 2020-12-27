package personaltaste.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import personaltaste.annotation.UserParam
import personaltaste.controller.model.user_taste.UserTasteRequest
import personaltaste.controller.model.user_taste.UserTasteResponse
import personaltaste.entity.User
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
    @GetMapping
    fun list(
        @UserParam user: User
    ): UserTasteListResponse {
        return UserTasteListResponse.ofUserTastes(userTasteService.list(user))
    }

    @PostMapping
    fun create(
        @UserParam user: User,
        @RequestBody userTasteRequest: UserTasteRequest
    ): UserTasteResponse {
        return UserTasteResponse.ofUserTastes(
            userTasteService.bulkCreate(user, userTasteRequest.tasteOptionIds)
        )
    }

    @DeleteMapping
    fun delete(
        @UserParam user: User,
        @RequestBody userTasteRequest: UserTasteRequest
    ): UserTasteResponse {
        return UserTasteResponse.ofUserTastes(
            userTasteService.bulkDelete(user, userTasteRequest.tasteOptionIds)
        )
    }

}