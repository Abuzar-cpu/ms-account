package az.vtb.msaccount.exception

enum class ExceptionMessages(val message: String) {
    ALREADY_EXISTS_ERROR("Account already exists"),
    NOT_FOUND_ERROR("No account found"),
    UNEXPECTED_ERROR("Unexpected error occurred"),
    INSUFFICIENT_BALANCE_ERROR("Insufficient balance"),
    METHOD_NOT_ALLOWED_ERROR("Method not supported"),
    AMOUNT_IS_NOT_ZERO_ERROR("Account contains an amount of money"),
}
