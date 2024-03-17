package az.vtb.msaccount.service.concrete

import az.vtb.msaccount.dao.AccountEntity
import az.vtb.msaccount.dao.AccountRepository
import az.vtb.msaccount.exception.ConflictException
import az.vtb.msaccount.exception.ExceptionMessages.*
import az.vtb.msaccount.exception.InsufficientBalanceException
import az.vtb.msaccount.exception.NotFoundException
import az.vtb.msaccount.mapper.toEntity
import az.vtb.msaccount.mapper.toResponse
import az.vtb.msaccount.model.AccountType
import az.vtb.msaccount.model.AccountType.*
import az.vtb.msaccount.model.BalanceOperation.DECREASE
import az.vtb.msaccount.model.BalanceOperation.INCREASE
import az.vtb.msaccount.model.CreateAccountRequest
import az.vtb.msaccount.model.GetUserAccountsResponse
import az.vtb.msaccount.model.ModifyBalanceRequest
import az.vtb.msaccount.service.abstraction.AccountService
import org.springframework.stereotype.Service
import java.math.BigDecimal.ZERO

@Service
class AccountServiceHandler(
    private val accountRepository: AccountRepository
) : AccountService {

    override fun createAccount(request: CreateAccountRequest) {
        when (request.accountType) {
            CARD -> validateCardAccount(request)
            STANDALONE, CASH -> validateNonCardAccount(request, request.accountType)
        }
    }

    override fun getUserAccounts(userId: String): List<GetUserAccountsResponse> {
        return accountRepository.findAllByUserId(userId = userId)
            .takeIf { it.isNotEmpty() }
            ?.map { it.toResponse() }
            ?: throw NotFoundException(NOT_FOUND_ERROR.message)
    }

    override fun modifyBalanceByAccountId(accountNumber: String, request: ModifyBalanceRequest) {
        accountRepository.findByAccountNumber(accountNumber)?.let {
            executeModify(it, request)
        } ?: throw NotFoundException(NOT_FOUND_ERROR.message)
    }

    override fun modifyBalanceByUserIdAndCardId(
        userId: String,
        cardId: String,
        modifyBalanceRequest: ModifyBalanceRequest
    ) {
        getAccount(userId, cardId)?.let {
            executeModify(it, modifyBalanceRequest)
        } ?: throw NotFoundException(NOT_FOUND_ERROR.message)

    }

    override fun deleteAccount(accountNumber: String) {
        accountRepository.findByAccountNumber(accountNumber)?.run {
            require(this.balance.compareTo(ZERO) == 0) { throw IllegalStateException(AMOUNT_IS_NOT_ZERO_ERROR.message) }
            accountRepository.delete(this)
        }
    }

    private fun executeModify(accountEntity: AccountEntity, modifyBalanceRequest: ModifyBalanceRequest) {
        accountEntity.let {
            when (modifyBalanceRequest.operation) {
                INCREASE -> accountEntity.balance += modifyBalanceRequest.amount
                DECREASE -> {
                    require(it.balance.compareTo(modifyBalanceRequest.amount) > 0) {
                        throw InsufficientBalanceException(
                            INSUFFICIENT_BALANCE_ERROR.message
                        )
                    }
                    accountEntity.balance -= modifyBalanceRequest.amount
                }
            }
        }

        accountRepository.save(accountEntity)
    }

    private fun validateCardAccount(request: CreateAccountRequest) {
        require(request.cardId != null) { throw IllegalArgumentException(CARD_ID_IS_REQUIRED_ERROR.message) }
        getAccount(request.userId, request.cardId)?.let {
            throw ConflictException(ALREADY_EXISTS_ERROR.message)
        } ?: accountRepository.save(request.toEntity())
    }

    private fun validateNonCardAccount(request: CreateAccountRequest, type: AccountType) {
        require(request.cardId == null) { throw IllegalArgumentException(CARD_ID_SHOULD_NOT_PRESENT.message) }
        if (type == CASH) {
            getUserAccounts(request.userId)
                .find { it.accountType == CASH }
                ?.let { throw ConflictException(ALREADY_EXISTS_ERROR.message) }
        }
        accountRepository.save(request.toEntity())
    }

    private fun getAccount(userId: String, cardId: String): AccountEntity? =
        accountRepository.findByUserIdAndCardId(userId, cardId)

}
