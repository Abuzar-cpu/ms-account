package az.vtb.msaccount.model

data class AccountCriteria(
    var userId: String? = null,
    var accountType: AccountType? = null,
    var currency: Currency? = null,
)
