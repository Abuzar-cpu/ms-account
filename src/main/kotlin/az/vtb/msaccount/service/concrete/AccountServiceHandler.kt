package az.vtb.msaccount.service.concrete

import az.vtb.msaccount.dao.AccountEntity
import az.vtb.msaccount.dao.AccountRepository
import az.vtb.msaccount.exception.ConflictException
import az.vtb.msaccount.exception.ExceptionMessages.*
import az.vtb.msaccount.exception.InsufficientBalanceException
import az.vtb.msaccount.exception.NotFoundException
import az.vtb.msaccount.mapper.toEntity
import az.vtb.msaccount.mapper.toModifyRequest
import az.vtb.msaccount.mapper.toResponse
import az.vtb.msaccount.model.BalanceOperation.DECREASE
import az.vtb.msaccount.model.BalanceOperation.INCREASE
import az.vtb.msaccount.model.CreateAccountRequest
import az.vtb.msaccount.model.GetUserAccountsResponse
import az.vtb.msaccount.model.ModifyBalanceRequest
import az.vtb.msaccount.model.ReverseTransactionRequest
import az.vtb.msaccount.service.abstraction.AccountService
import org.springframework.stereotype.Service
import java.math.BigDecimal.ZERO

@Service
class AccountServiceHandler(
    private val accountRepository: AccountRepository
) : AccountService {

    override fun createAccount(request: CreateAccountRequest) {
        getAccount(request.userId, request.cardId)
            ?.run { throw ConflictException(ALREADY_EXISTS_ERROR.message) }
            ?: accountRepository.save(request.toEntity())
    }

    override fun getUserAccounts(userId: String): List<GetUserAccountsResponse> {
        return accountRepository.findAllByUserId(userId = userId)
            .takeIf { it.isNotEmpty() }
            ?.map { it.toResponse() }
            ?: throw NotFoundException(NOT_FOUND_ERROR.message)
    }

    override fun modifyBalance(cardId: String, request: ModifyBalanceRequest) {
        val account = getAccount(request.userId, cardId)

        account?.let {
            when (request.operation) {
                INCREASE -> account.balance += request.amount
                DECREASE -> {
                    require(it.balance > request.amount) { throw InsufficientBalanceException(INSUFFICIENT_BALANCE_ERROR.message) }
                    account.balance -= request.amount
                }
            }
        } ?: throw NotFoundException(NOT_FOUND_ERROR.message)

        accountRepository.save(account)
    }

    override fun deleteAccount(userId: String, cardId: String) {
        getAccount(userId, cardId)?.run {
            require(this.balance.compareTo(ZERO) == 0) { throw IllegalStateException(AMOUNT_IS_NOT_ZERO_ERROR.message) }
            accountRepository.delete(this)
        }
    }

    override fun reverseTransaction(reverseTransactionRequest: ReverseTransactionRequest) {
        val account = getAccount(reverseTransactionRequest.userId, reverseTransactionRequest.cardId)
        account?.let {
            modifyBalance(
                cardId = reverseTransactionRequest.cardId,
                request = reverseTransactionRequest.toModifyRequest()
            )
        } ?: throw NotFoundException(NOT_FOUND_ERROR.message)

    }

    private fun getAccount(userId: String, cardId: String): AccountEntity? =
        accountRepository.findByUserIdAndCardId(userId, cardId)

}
