package it.unito.kotac

/**
 * A Field represents an immutable neighboring field of T values.
 *
 * @param T the type of the values in the field
 */
interface Field<out T> {
    /**
     * Returns a new [Field] containing the result of applying the given [transform] function to each value in
     * the original Field.
     *
     * @param transform function called on each value of the field to modify it
     */
    fun <R> map(transform: (T) -> R): Field<R>

    /**
     * Accumulates value starting with [initial] value and applying the [operation] in any order to current
     * accumulator value and each value of the field.
     *
     * @param initial the initial value for the accumulator
     * @param operation the function combining the current accumulator with each value of the field.
     */
    fun <R> fold(initial: R, operation: (acc: R, T) -> R): R
}
