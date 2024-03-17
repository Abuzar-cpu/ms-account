package az.vtb.msaccount.mapper

import az.vtb.msaccount.dao.AccountEntity
import az.vtb.msaccount.model.GetUserAccountsResponse

fun AccountEntity.toResponse(): GetUserAccountsResponse {
    return GetUserAccountsResponse (
        userId = userId,
        cardId = cardId,
        balance = balance,
        accountType = accountType,
    )
}
