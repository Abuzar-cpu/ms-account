package az.vtb.msaccount.dao

import org.springframework.data.repository.CrudRepository

interface AccountRepository : CrudRepository<AccountEntity, Long> {

    fun findByUserIdAndCardId(userId: String, cardId: String): AccountEntity?
    fun findAllByUserId(userId: String): List<AccountEntity>
    fun findByAccountNumber(accountNumber: String): AccountEntity?
}
