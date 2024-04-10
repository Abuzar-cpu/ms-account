package az.vtb.msaccount.mapper

import az.vtb.msaccount.dao.AccountEntity
import az.vtb.msaccount.model.CreateAccountRequest
import az.vtb.msaccount.util.generateAccountNumber
import java.time.LocalDateTime

fun CreateAccountRequest.toEntity(): AccountEntity {
    return AccountEntity(
        userId = userId,
        balance = balance,
        accountType = accountType,
        accountNumber = generateAccountNumber(currency = currency.name),
        currency = currency,
        friendlyName = friendlyName,
        createdAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now(),
    )
}
