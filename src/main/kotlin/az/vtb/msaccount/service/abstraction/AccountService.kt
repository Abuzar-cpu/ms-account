package az.vtb.msaccount.service.abstraction

import az.vtb.msaccount.model.CreateAccountRequest
import az.vtb.msaccount.model.GetUserAccountsResponse
import az.vtb.msaccount.model.ModifyBalanceRequest

interface AccountService {
    fun createAccount(request: CreateAccountRequest)
    fun getUserAccounts(userId: String): List<GetUserAccountsResponse>
    fun modifyBalanceByAccountId(accountNumber: String, request: ModifyBalanceRequest)
    fun modifyBalanceByUserIdAndCardId(userId: String, cardId: String, modifyBalanceRequest: ModifyBalanceRequest)
    fun deleteAccount(accountNumber: String)
}
