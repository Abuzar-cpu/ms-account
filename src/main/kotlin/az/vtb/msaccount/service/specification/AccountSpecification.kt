package az.vtb.msaccount.service.specification

import az.vtb.msaccount.dao.AccountEntity
import az.vtb.msaccount.model.AccountCriteria
import az.vtb.msaccount.model.AccountType
import az.vtb.msaccount.model.Currency
import az.vtb.msaccount.util.PredicateUtil
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import org.springframework.data.jpa.domain.Specification

data class AccountSpecification(
    private val accountCriteria: AccountCriteria
) : Specification<AccountEntity> {

    override fun toPredicate(
        root: Root<AccountEntity>,
        query: CriteriaQuery<*>,
        cb: CriteriaBuilder
    ): Predicate {
        val predicates = PredicateUtil.builder()
            .addNullSafe(accountCriteria.userId) { userId ->
                cb.like(root.get("userId"), PredicateUtil.applyLikePattern(userId))
            }
            .addNullSafe(accountCriteria.accountType) { accountType ->
                cb.equal(root.get<AccountType>("accountType"), accountType)
            }
            .addNullSafe(accountCriteria.currency) { currency ->
                cb.equal(root.get<Currency>("currency"), currency)
            }
            .build()

        return cb.and(*predicates)
    }
}
