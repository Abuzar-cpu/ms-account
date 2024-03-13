package az.vtb.msaccount.exception

class ConflictException(override val message: String) : RuntimeException(message)
class NotFoundException(override val message: String) : RuntimeException(message)
class InsufficientBalanceException(override val message: String) : RuntimeException(message)
