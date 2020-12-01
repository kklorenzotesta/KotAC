package it.unito.kotac

/**
 * AggregateContext provides the field calculus API. Aggregate functions are written as extension functions of AggregateContext
 */
interface AggregateContext {
    val platform: Platform

    fun <T> share(initial: T, f: AggregateContext.(Field<T>) -> T): T
    fun <K, T> align(key: K, proc: AggregateContext.(K) -> T): T
    fun <T> nbr(f: AggregateContext.() -> T): Field<T>
    fun <T> rep(initial: T, f: AggregateContext.(T) -> T): T
}
