package personaltaste.exception

enum class ExceptionCode(
        val code: String,
        val message: String
) {
    NOT_FOUND("NOT_FOUND", "찾지 못했다."),
    INVALID_VALUE("INVALID_VALUE", "잘못된 값.")
}