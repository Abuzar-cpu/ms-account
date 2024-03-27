package az.vtb.msaccount.dao

import org.springframework.data.repository.CrudRepository

interface AccountRepository : CrudRepository<AccountEntity, Long> {

    fun findAllByUserId(userId: String): List<AccountEntity>
    fun findByUserIdAndAccountNumber(userId: String, accountNumber: String): AccountEntity?
    fun findByAccountNumber(accountNumber: String): AccountEntity?
}
