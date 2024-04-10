package az.vtb.msaccount.controller

import az.vtb.msaccount.model.*
import az.vtb.msaccount.service.abstraction.AccountService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus.CREATED
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Validated
@RestController
@RequestMapping("api/v1/accounts")
class AccountController(val accountService: AccountService) {

    @GetMapping
    fun getUserAccounts(accountCriteria: AccountCriteria): List<GetUserAccountResponse> =
        accountService.getAccount(accountCriteria)

    @PostMapping
    @ResponseStatus(CREATED)
    fun createAccount(@RequestBody @Valid createAccountRequest: CreateAccountRequest): CreateAccountResponse =
        accountService.createAccount(createAccountRequest)

    @PostMapping("/{accountId}/modify")
    fun modifyBalanceByAccountId(
        @RequestHeader(name = USER_ID) userId: String,
        @PathVariable accountId: String,
        @RequestBody modifyBalanceRequest: ModifyBalanceRequest
    ) = accountService.modifyBalanceByAccountId(userId, accountId, modifyBalanceRequest)

    @DeleteMapping("/{accountId}")
    fun deleteAccount(@PathVariable accountId: String) = accountService.deleteAccount(accountId)

    @DeleteMapping("/user/{userId}")
    fun deleteUserAccounts(@PathVariable userId: String) = accountService.deleteUserAccounts(userId)

}
