package it.unito.kotac

interface AggregateContext {
    fun <T> share(initial: T, f: (Field<T>) -> T): T
    fun <K, T> align(key: K, proc: (K) -> T): T
    fun <T> nbr(f: () -> T): Field<T>
    fun <T> rep(initial: T, f: (T) -> T): T
}
