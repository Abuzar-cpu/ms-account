package az.vtb.msaccount.controller

import az.vtb.msaccount.model.CreateAccountRequest
import az.vtb.msaccount.model.CreateAccountResponse
import az.vtb.msaccount.model.GetUserAccountResponse
import az.vtb.msaccount.model.ModifyBalanceRequest
import az.vtb.msaccount.model.USER_ID
import az.vtb.msaccount.service.abstraction.AccountService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus.CREATED
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@Validated
@RestController
@RequestMapping("api/v1/accounts")
class AccountController(val accountService: AccountService) {

    @GetMapping("/user/{userId}")
    fun getUserAccounts(@PathVariable userId: String): List<GetUserAccountResponse> =
        accountService.getUserAccounts(userId)

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
