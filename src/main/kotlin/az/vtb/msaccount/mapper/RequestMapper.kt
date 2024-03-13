package az.vtb.msaccount.mapper

import az.vtb.msaccount.dao.AccountEntity
import az.vtb.msaccount.model.CreateAccountRequest
import az.vtb.msaccount.model.ModifyBalanceRequest
import az.vtb.msaccount.model.ReverseTransactionRequest
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

fun ReverseTransactionRequest.toModifyRequest(): ModifyBalanceRequest {
    return ModifyBalanceRequest(
        userId = userId,
        amount = amount,
        operation = balanceOperation,
    )
}
