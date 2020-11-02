package it.unito.kotac

interface Field<out T> {
    fun <R> map(f: (T) -> R): Field<R>
    fun <R> fold(initial: R, operation: (acc: R, T) -> R): R
}
