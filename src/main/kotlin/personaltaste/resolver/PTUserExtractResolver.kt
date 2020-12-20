package personaltaste.resolver

import org.springframework.core.MethodParameter
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import personaltaste.annotation.PTUserParam
import personaltaste.entity.User
import personaltaste.exception.ExceptionCode
import personaltaste.exception.PersonalTasteException
import personaltaste.repository.UserRepository

@Component
class PTUserExtractResolver(
    private val userRepository: UserRepository
) : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(PTUserParam::class.java)
    }

    // TODO : security 사용시 시큐리티 토큰으로 user 검증하는 로직으로 바꿔야함
    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): User {
        val userId = webRequest.getHeader("User-Id")?.toLong()
            ?: throw PersonalTasteException(ExceptionCode.INVALID_VALUE, "header에 user 검증값이 없습니다.")

        return userRepository.findByIdOrNull(userId)
            ?: throw PersonalTasteException(ExceptionCode.NOT_FOUND, "user 정보를 찾을 수 없습니다.")
    }

}
