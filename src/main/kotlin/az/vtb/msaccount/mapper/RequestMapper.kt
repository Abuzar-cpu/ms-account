package az.vtb.msaccount.mapper

import az.vtb.msaccount.dao.AccountEntity
import az.vtb.msaccount.model.CreateAccountRequest
import java.time.LocalDateTime

fun CreateAccountRequest.toEntity(): AccountEntity{
    return AccountEntity(
        userId = userId,
        cardId = cardId,
        balance = balance,
        createdAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now(),
    )
}