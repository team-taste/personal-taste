package personaltaste.exception

enum class ExceptionCode(
        val code: String,
        val message: String
) {
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", "서버에러입니다. 다시 시도해주십시오."),
    NOT_FOUND("NOT_FOUND", "요청한 정보를 찾지 못하였습니다."),
    INVALID_VALUE("INVALID_VALUE", "유효하지 않은 값입니다.")
}