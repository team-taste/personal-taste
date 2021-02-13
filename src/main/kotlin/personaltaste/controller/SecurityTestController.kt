package personaltaste.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import personaltaste.controller.model.user.UserSecurityRequest
import personaltaste.service.UserCommonService
import personaltaste.service.security.JwtTokenProvider
import java.util.*

/**
 * Spring security test controller
 *
 * @author seungmin
 */
@RestController
@RequestMapping("/api/v1/security")
class SecurityTestController(
        private val jwtTokenProvider: JwtTokenProvider
) {

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    fun login(
            @RequestBody userSecurityRequest: UserSecurityRequest
    ) : String {

        // todo 1. exist check
//        userCommonService.checkExists(userSecurityRequest.email)

        // todo 2. password 일치 체크 todo 암호화

        // 3. token 발급
        return jwtTokenProvider.createToken(userSecurityRequest.email, Date())
    }

    @GetMapping("/auth")
    fun test(): String {
        return "Hello Security"
    }
}
