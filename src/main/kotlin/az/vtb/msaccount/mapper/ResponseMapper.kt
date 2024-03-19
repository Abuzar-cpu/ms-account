package az.vtb.msaccount.mapper

import az.vtb.msaccount.dao.AccountEntity
import az.vtb.msaccount.model.GetUserAccountResponse

fun AccountEntity.toResponse(): GetUserAccountResponse {
    return GetUserAccountResponse (
        userId = userId,
        cardId = cardId,
        balance = balance,
        accountType = accountType,
    )
}
