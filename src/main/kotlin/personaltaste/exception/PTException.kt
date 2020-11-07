package personaltaste.exception

import java.lang.RuntimeException

class PTException(
        val exceptionCode: ExceptionCode,
        val additionalMessage: String? = null
): RuntimeException(exceptionCode.message.plus(" " + additionalMessage?.trim())) {

}