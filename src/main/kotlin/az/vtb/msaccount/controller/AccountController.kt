package az.vtb.msaccount.controller

import az.vtb.msaccount.model.CreateAccountRequest
import az.vtb.msaccount.model.GetUserAccountsResponse
import az.vtb.msaccount.model.ModifyBalanceRequest
import az.vtb.msaccount.service.abstraction.AccountService
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.springframework.http.HttpStatus.CREATED
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/accounts")
@Validated
class AccountController(val accountService: AccountService) {

    @GetMapping
    fun getUserAccounts(@RequestParam @Valid @NotBlank @NotNull userId: String): List<GetUserAccountsResponse> =
        accountService.getUserAccounts(userId)

    @PostMapping
    @ResponseStatus(CREATED)
    fun createAccount(@RequestBody @Valid createAccountRequest: CreateAccountRequest) =
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
