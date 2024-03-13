package az.vtb.msaccount.service.abstraction

import az.vtb.msaccount.model.CreateAccountRequest
import az.vtb.msaccount.model.GetUserAccountsResponse
import az.vtb.msaccount.model.ModifyBalanceRequest
import az.vtb.msaccount.model.ReverseTransactionRequest

interface AccountService {
    fun createAccount(request: CreateAccountRequest)
    fun getUserAccounts(userId: String): List<GetUserAccountsResponse>
    fun modifyBalance(cardId: String, request: ModifyBalanceRequest)
    fun deleteAccount(userId: String, cardId: String)
    fun reverseTransaction(reverseTransactionRequest: ReverseTransactionRequest)
}
