package az.vtb.msaccount.util

import jakarta.persistence.criteria.Predicate

class PredicateUtil private constructor() {

    private val predicates: MutableList<Predicate> = mutableListOf()

    companion object {
        fun builder(): PredicateUtil {
            return PredicateUtil()
        }

        fun applyLikePattern(key: String): String {
            return "%$key%"
        }
    }

    fun <T> add(`object`: T, function: (T) -> Predicate): PredicateUtil {
        predicates.add(function(`object`))
        return this
    }

    fun <T> addNullSafe(`object`: T?, function: (T) -> Predicate): PredicateUtil {
        `object`?.let {
            predicates.add(function(it))
        }
        return this
    }

    fun build(): Array<Predicate> {
        return predicates.toTypedArray()
    }
}
