package it.unito.kotac

interface AggregateContext {
    fun <T> share(initial: T, f: (Field<T>) -> T): Field<T>
    fun <T> align(on: () -> T): T
}
