package az.vtb.msaccount.service.abstraction

import az.vtb.msaccount.model.CreateAccountRequest
import az.vtb.msaccount.model.CreateAccountResponse
import az.vtb.msaccount.model.GetUserAccountResponse
import az.vtb.msaccount.model.ModifyBalanceRequest

interface AccountService {
    fun createAccount(request: CreateAccountRequest): CreateAccountResponse
    fun getUserAccounts(userId: String): List<GetUserAccountResponse>
    fun modifyBalanceByAccountId(accountNumber: String, request: ModifyBalanceRequest)
    fun modifyBalanceByUserIdAndCardId(userId: String, cardId: String, modifyBalanceRequest: ModifyBalanceRequest)
    fun deleteAccount(accountNumber: String)
    fun getAccountByCardId(cardId: String): GetUserAccountResponse
}
