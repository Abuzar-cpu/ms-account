package az.vtb.msaccount.model

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal
import java.math.BigDecimal.ZERO

data class CreateAccountRequest(
    @field: NotBlank
    @field: NotNull
    val userId: String,
    val accountType: AccountType,
    val balance: BigDecimal = ZERO,
    val friendlyName: String,
    val currency: Currency,
)

data class ModifyBalanceRequest(
    @field: NotNull
    val amount: BigDecimal,
    val operation: BalanceOperation,
)
