package personaltaste.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import personaltaste.entity.User
import personaltaste.exception.ExceptionCode
import personaltaste.exception.PersonalTasteException
import personaltaste.repository.UserRepository
import personaltaste.service.model.user.UserCreate

@Service
class UserCommonService(
    private val userRepository: UserRepository
) {

    @Transactional
    fun create(userCreate: UserCreate): User {
        if (checkExists(userCreate.email))
            throw PersonalTasteException(ExceptionCode.INVALID_VALUE, "중복된 email 주소가 존재합니다.")

        return userRepository.save(userCreate.toUser())
    }

    fun checkExists(email: String): Boolean {
        return userRepository.existsByEmail(email)
    }

    @Transactional
    fun delete(userId: Long): User {
        return userRepository.findByIdOrNull(userId)?.delete()
            ?: throw PersonalTasteException(ExceptionCode.NOT_FOUND, "${userId}에 맞는 User가 존재하지 않습니다.")
    }

}
