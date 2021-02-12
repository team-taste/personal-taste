package personaltaste.service.security

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import personaltaste.exception.ExceptionCode
import personaltaste.exception.PersonalTasteException
import personaltaste.repository.UserRepository
import personaltaste.service.model.user.CustomUserDetails

/**
 * Spring security UserDetailService 구현체
 *
 * @author seungmin
 */
@Service
class CustomUserDetailsService(
        private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        return userRepository.findByEmail(username)
                ?.let { CustomUserDetails.of(it) }
                ?: throw PersonalTasteException(ExceptionCode.NOT_FOUND, "${username}에 맞는 User가 존재하지 않습니다.")
    }
}
