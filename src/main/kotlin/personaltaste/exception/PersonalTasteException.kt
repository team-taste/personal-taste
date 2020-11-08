package personaltaste.exception

import java.lang.RuntimeException

class PersonalTasteException(
        val exceptionCode: ExceptionCode,
        private val additionalMessage: String? = null
) : RuntimeException(
        exceptionCode.message.plus(
                additionalMessage?.let { " ".plus(it.trim()) } ?: ""
        )
) {

}