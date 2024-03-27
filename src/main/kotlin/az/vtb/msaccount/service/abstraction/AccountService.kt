package az.vtb.msaccount.service.abstraction

import az.vtb.msaccount.model.*

interface AccountService {
    fun createAccount(request: CreateAccountRequest): CreateAccountResponse
    fun modifyBalanceByAccountId(userId: String, accountNumber: String, request: ModifyBalanceRequest)
    fun deleteAccount(accountNumber: String)
    fun deleteUserAccounts(userId: String)
    fun getAccount(accountCriteria: AccountCriteria): List<GetUserAccountResponse>
}
