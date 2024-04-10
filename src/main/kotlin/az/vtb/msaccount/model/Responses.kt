package az.vtb.msaccount.model

import java.math.BigDecimal

data class GetUserAccountResponse(
    val userId: String,
    val balance: BigDecimal,
    val accountType: AccountType,
    val accountNumber: String,
)

data class CreateAccountResponse(
    val accountNumber: String,
)
