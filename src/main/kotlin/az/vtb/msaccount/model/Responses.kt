package az.vtb.msaccount.model

import java.math.BigDecimal

data class GetUserAccountResponse(
    val userId: String,
    val cardId: String?,
    val balance: BigDecimal,
    val accountType: AccountType,
)

data class CreateAccountResponse(
    val accountNumber: String,
)
