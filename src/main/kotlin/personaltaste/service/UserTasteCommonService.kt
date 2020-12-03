package personaltaste.service

import org.springframework.stereotype.Service
import personaltaste.repository.UserRepository
import personaltaste.repository.UserTasteRepository

@Service
class UserTasteCommonService(
    private val userRepository: UserRepository,
    private val userTasteRepository: UserTasteRepository
) {



}