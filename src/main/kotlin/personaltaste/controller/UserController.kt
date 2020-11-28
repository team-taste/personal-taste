package personaltaste.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import personaltaste.controller.model.user.UserCreateRequest
import personaltaste.service.UserCommonService

/**
 * 기본적인 crud성 api
 * User resource의 수정 삭제 권한관리 기능 필수 개발
 *
 * fe 개발 시 수정될 수 있음
 */
@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userCommonService: UserCommonService
) {

    // fe 개발 후 유저 생성(가입)시 화면을 어떻게 진입하느냐에 따라 응답값이 달라질 수 있음
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @RequestBody userCreateRequest: UserCreateRequest
    ) {
        userCommonService.create(userCreateRequest.user)
    }

}