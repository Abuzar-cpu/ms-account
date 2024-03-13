package az.vtb.msaccount.model

import java.math.BigDecimal
import java.math.BigDecimal.ZERO

data class CreateAccountRequest(
    val userId: String,
    val cardId: String,
    val balance: BigDecimal = ZERO,
)

data class ModifyBalanceRequest (
    val userId: String,
    val amount: BigDecimal,
    val operation: BalanceOperation,
)