package az.vtb.msaccount.service.abstraction

import az.vtb.msaccount.model.CreateAccountRequest
import az.vtb.msaccount.model.GetUserAccountsResponse
import az.vtb.msaccount.model.ModifyBalanceRequest

interface AccountService {
    fun createAccount(request: CreateAccountRequest)
    fun getUserAccounts(userId: String): List<GetUserAccountsResponse>
    fun modifyBalance(cardId: String, request: ModifyBalanceRequest)
}