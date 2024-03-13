package az.vtb.msaccount.model

import java.math.BigDecimal

data class GetUserAccountsResponse(
    val userId: String,
    val cardId: String,
    val balance: BigDecimal,
)
