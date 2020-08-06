package it.unito.kotac

interface AggregateContext {
    fun <T> share(initial: T, f: (Field<T>) -> T): T
    fun <K, T> align(key: K, proc: (K) -> T): T
}
