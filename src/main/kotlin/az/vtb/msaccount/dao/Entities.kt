package az.vtb.msaccount.dao

import az.vtb.msaccount.model.AccountType
import jakarta.persistence.*
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.GenerationType.IDENTITY
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "accounts")
data class AccountEntity(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    var id: Long? = null,
    var userId: String,
    var cardId: String?,
    var balance: BigDecimal,
    @Enumerated(STRING)
    var accountType: AccountType,
    var accountNumber: String,

    @field: CreationTimestamp
    var createdAt: LocalDateTime,

    @field: UpdateTimestamp
    var updatedAt: LocalDateTime,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AccountEntity

        return id == other.id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}
