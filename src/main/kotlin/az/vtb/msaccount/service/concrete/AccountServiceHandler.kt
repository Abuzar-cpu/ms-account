package az.vtb.msaccount.service.concrete

import az.vtb.msaccount.annotation.Log
import az.vtb.msaccount.dao.AccountEntity
import az.vtb.msaccount.dao.AccountRepository
import az.vtb.msaccount.exception.ConflictException
import az.vtb.msaccount.exception.ExceptionMessages.*
import az.vtb.msaccount.exception.InsufficientBalanceException
import az.vtb.msaccount.exception.NotFoundException
import az.vtb.msaccount.mapper.toEntity
import az.vtb.msaccount.mapper.toResponse
import az.vtb.msaccount.model.*
import az.vtb.msaccount.model.AccountType.CASH
import az.vtb.msaccount.model.BalanceOperation.DECREASE
import az.vtb.msaccount.model.BalanceOperation.INCREASE
import az.vtb.msaccount.service.abstraction.AccountService
import az.vtb.msaccount.service.specification.AccountSpecification
import org.springframework.stereotype.Service
import java.math.BigDecimal.ZERO

@Log
@Service
class AccountServiceHandler(
    private val accountRepository: AccountRepository
) : AccountService {

    override fun createAccount(request: CreateAccountRequest): CreateAccountResponse {
        if (request.accountType == CASH) {
            validateNonCardAccount(request, request.accountType)
        }

        val entity = accountRepository.save(request.toEntity())
        return CreateAccountResponse(
            accountNumber = entity.accountNumber
        )
    }

    override fun modifyBalanceByAccountId(userId: String, accountNumber: String, request: ModifyBalanceRequest) {
        accountRepository.findByUserIdAndAccountNumber(userId, accountNumber)?.let {
            executeModify(it, request)
        } ?: throw NotFoundException(NOT_FOUND_ERROR.message)
    }

    override fun deleteAccount(accountNumber: String) {
        accountRepository.findByAccountNumber(accountNumber)?.run {
            require(this.balance.compareTo(ZERO) == 0) { throw IllegalStateException(AMOUNT_IS_NOT_ZERO_ERROR.message) }
            accountRepository.delete(this)
        }
    }

    override fun deleteUserAccounts(userId: String) {
        val accounts = accountRepository.findAllByUserId(userId)
        accountRepository.deleteAll(accounts)
    }

    override fun getAccount(accountCriteria: AccountCriteria): List<GetUserAccountResponse> {
        return accountRepository.findAll(AccountSpecification(accountCriteria))
            .takeIf { it.isNotEmpty() }
            ?.map {
                it.toResponse()
            }
            ?: throw NotFoundException(NOT_FOUND_ERROR.message)
    }

    private fun executeModify(accountEntity: AccountEntity, modifyBalanceRequest: ModifyBalanceRequest) {
        accountEntity.let {
            when (modifyBalanceRequest.operation) {
                INCREASE -> {
                    accountEntity.balance += modifyBalanceRequest.amount
                }
                DECREASE -> {
                    require(it.balance.compareTo(modifyBalanceRequest.amount) >= 0) {
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

    private fun validateNonCardAccount(request: CreateAccountRequest, type: AccountType) {
        if (type == CASH) {
            accountRepository.findAllByUserId(request.userId)
                .find { it.accountType == CASH }
                ?.let { throw ConflictException(ALREADY_EXISTS_ERROR.message) }
        }
    }

}
