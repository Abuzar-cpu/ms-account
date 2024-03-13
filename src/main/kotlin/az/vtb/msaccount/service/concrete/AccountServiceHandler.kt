package az.vtb.msaccount.service.concrete

import az.vtb.msaccount.dao.AccountRepository
import az.vtb.msaccount.exception.ConflictException
import az.vtb.msaccount.exception.ExceptionMessages.*
import az.vtb.msaccount.exception.InsufficientBalanceException
import az.vtb.msaccount.exception.NotFoundException
import az.vtb.msaccount.mapper.toEntity
import az.vtb.msaccount.mapper.toResponse
import az.vtb.msaccount.model.BalanceOperation.DECREASE
import az.vtb.msaccount.model.BalanceOperation.INCREASE
import az.vtb.msaccount.model.CreateAccountRequest
import az.vtb.msaccount.model.GetUserAccountsResponse
import az.vtb.msaccount.model.ModifyBalanceRequest
import az.vtb.msaccount.service.abstraction.AccountService
import org.springframework.stereotype.Service

@Service
class AccountServiceHandler(
    private val accountRepository: AccountRepository
) : AccountService {

    override fun createAccount(request: CreateAccountRequest) {
        accountRepository.findByUserIdAndCardId(request.userId, request.cardId)
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
        val account = accountRepository.findByUserIdAndCardId(request.userId, cardId)

        account ?. let {
            when(request.operation) {
                INCREASE -> account.balance += request.amount;
                DECREASE -> {
                    require(it.balance > request.amount) {throw InsufficientBalanceException(INSUFFICIENT_BALANCE_ERROR.message)}
                    account.balance -= request.amount
                }
            }
        } ?: throw NotFoundException(NOT_FOUND_ERROR.message)

        accountRepository.save(account);
    }
}
