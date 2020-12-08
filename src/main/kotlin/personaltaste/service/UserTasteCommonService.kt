package personaltaste.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import personaltaste.entity.UserTaste
import personaltaste.exception.ExceptionCode
import personaltaste.exception.PersonalTasteException
import personaltaste.repository.UserRepository
import personaltaste.repository.UserTasteRepository

@Service
class UserTasteCommonService(
    private val userRepository: UserRepository,
    private val userTasteRepository: UserTasteRepository
) {

    fun list(userId: Long): List<UserTaste> {
        val user = userRepository.findByIdOrNull(userId)
            ?: throw PersonalTasteException(ExceptionCode.NOT_FOUND, "user를 찾지못하였습니다.")

        return userTasteRepository.findByUser(user)
    }


}