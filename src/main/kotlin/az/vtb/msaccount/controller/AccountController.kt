package az.vtb.msaccount.controller

import az.vtb.msaccount.model.CreateAccountRequest
import az.vtb.msaccount.model.GetUserAccountsResponse
import az.vtb.msaccount.model.ModifyBalanceRequest
import az.vtb.msaccount.service.abstraction.AccountService
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/accounts")
class AccountController(val accountService: AccountService) {

    @GetMapping
    fun getUserAccounts(@RequestParam userId: String): List<GetUserAccountsResponse> =
        accountService.getUserAccounts(userId)

    @PostMapping
    @ResponseStatus(CREATED)
    fun createAccount(@RequestBody createAccountRequest: CreateAccountRequest) =
        accountService.createAccount(createAccountRequest)


    @PostMapping("/{cardId}")
    @ResponseStatus(NO_CONTENT)
    fun modifyBalance(@PathVariable cardId: String, @RequestBody modifyBalanceRequest: ModifyBalanceRequest) =
        accountService.modifyBalance(cardId, request = modifyBalanceRequest)
}
