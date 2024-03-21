package az.vtb.msaccount.controller

import az.vtb.msaccount.model.CreateAccountRequest
import az.vtb.msaccount.model.CreateAccountResponse
import az.vtb.msaccount.model.GetUserAccountResponse
import az.vtb.msaccount.model.ModifyBalanceRequest
import az.vtb.msaccount.service.abstraction.AccountService
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.springframework.http.HttpStatus.CREATED
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@Validated
@RestController
@RequestMapping("api/v1/accounts")
class AccountController(val accountService: AccountService) {

    @GetMapping
    fun getUserAccounts(@RequestParam @Valid @NotBlank @NotNull userId: String): List<GetUserAccountResponse> =
        accountService.getUserAccounts(userId)

    @GetMapping("/card/{cardId}")
    fun getAccountByCardId(@PathVariable cardId: String): GetUserAccountResponse =
        accountService.getAccountByCardId(cardId)

    @PostMapping
    @ResponseStatus(CREATED)
    fun createAccount(@RequestBody @Valid createAccountRequest: CreateAccountRequest): CreateAccountResponse =
        accountService.createAccount(createAccountRequest)

    @PostMapping("/{accountId}/modify")
    fun modifyBalanceByAccountId(
        @PathVariable accountId: String,
        @RequestBody modifyBalanceRequest: ModifyBalanceRequest
    ) = accountService.modifyBalanceByAccountId(accountId, modifyBalanceRequest)

    @PostMapping("/{userId}/{cardId}/modify")
    fun modifyBalanceByUserIdAndCardId(
        @PathVariable userId: String,
        @PathVariable cardId: String,
        @RequestBody modifyBalanceRequest: ModifyBalanceRequest
    ) = accountService.modifyBalanceByUserIdAndCardId(userId, cardId, modifyBalanceRequest)

    @DeleteMapping("/{accountId}")
    fun deleteAccount(@PathVariable accountId: String) = accountService.deleteAccount(accountId)

}
