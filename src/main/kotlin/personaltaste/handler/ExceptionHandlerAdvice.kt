package personaltaste.handler

import mu.KotlinLogging
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import personaltaste.exception.ExceptionCode
import personaltaste.exception.PersonalTasteException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@ControllerAdvice
@RestController
class ExceptionHandlerAdvisor : ErrorController {

    private val logger = KotlinLogging.logger {}

    companion object {
        private const val PATH = "/error"
    }

    @ExceptionHandler(PersonalTasteException::class)
    fun personalTasteExceptionHandle(e: PersonalTasteException): ResponseEntity<ErrorResponse> {
        val responseStatus = when (e.exceptionCode) {
            ExceptionCode.NOT_FOUND -> HttpStatus.NOT_FOUND
            ExceptionCode.INVALID_VALUE -> HttpStatus.BAD_REQUEST
            else -> HttpStatus.INTERNAL_SERVER_ERROR
        }

        printLog(responseStatus, e)
        return ResponseEntity.status(responseStatus)
            .body(ErrorResponse(e.exceptionCode.name, e.message ?: ""))
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = [IllegalArgumentException::class, MethodArgumentTypeMismatchException::class])
    fun illegalArgumentHandle(e: Throwable): ErrorResponse {
        logger.error("{}", e)
        return ErrorResponse(ExceptionCode.INVALID_VALUE.name, ExceptionCode.INVALID_VALUE.message)
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable::class)
    fun throwableHandle(e: Throwable): ErrorResponse {
        printLog(HttpStatus.INTERNAL_SERVER_ERROR, e)
        val exceptionCode = ExceptionCode.INTERNAL_SERVER_ERROR
        return ErrorResponse(exceptionCode.name, exceptionCode.message)
    }

    // error 기본 응답값
    @RequestMapping(PATH)
    fun getError(request: HttpServletRequest, response: HttpServletResponse): ErrorResponse {
        val exceptionCode = when (response.status) {
            HttpStatus.NOT_FOUND.value() -> ExceptionCode.NOT_FOUND
            else -> ExceptionCode.INTERNAL_SERVER_ERROR
        }
        return ErrorResponse(exceptionCode.name, exceptionCode.message)
    }

    private fun printLog(httpStatus: HttpStatus, e: Throwable) {
        if (httpStatus.is5xxServerError) {
            logger.error("${e.message}", e)
        } else {
            logger.warn(e.message, e)
        }
    }

    override fun getErrorPath(): String {
        return PATH
    }

}

data class ErrorResponse(val errorCode: String, val errorMessage: String)