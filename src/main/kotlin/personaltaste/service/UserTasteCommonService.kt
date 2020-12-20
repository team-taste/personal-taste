package personaltaste.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import personaltaste.entity.User
import personaltaste.entity.UserTaste
import personaltaste.repository.TasteOptionRepository
import personaltaste.repository.UserTasteRepository

@Service
class UserTasteCommonService(
    private val userTasteRepository: UserTasteRepository,
    private val tasteOptionRepository: TasteOptionRepository
) {

    fun list(user: User): List<UserTaste> {
        return userTasteRepository.findByUserAndActiveYn(user)
    }

    @Transactional
    fun bulkCreate(user: User, ids: List<Long>): List<UserTaste> {
        val exist = userTasteRepository.findAllByUserAndTasteOptionId(user, ids).map {
            it.active()
        }

        val nonExist = tasteOptionRepository.findAllById(ids.subtract(exist.map { it.tasteOption.id })).map {
            UserTaste.of(it, user)
        }

        return userTasteRepository.saveAll(
            exist.plus(nonExist)
        )
    }

    @Transactional
    fun bulkDelete(user: User, ids: List<Long>): List<UserTaste> {
        return userTasteRepository.findAllByUserAndTasteOptionId(user, ids).map {
            it.delete()
        }
    }

}