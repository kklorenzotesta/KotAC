package it.unito.kotac

/**
 * AggregateContext provides the field calculus API. Aggregate functions are written as extension functions of AggregateContext
 */
interface AggregateContext {
    /**
     * The [Platform] on which the execution is happening
     */
    val platform: Platform

    /**
     * The *share* operator returns the result of applying the aggregate function [f] to a [Field] containing the result
     * of the last invocation of [f] from all aligned neighbouring devices, associating [initial] to the current device
     * if its the first execution for this device.
     *
     * This operator allows a device to save a value for the following round of execution, share it to all
     * neighbours and read all the received values.
     *
     * @param T the type of the value computed in by the operator
     * @param initial the value associated to the current device in the field on the first round of execution
     * @param f function computing a value based on the field of the last executions by the neighbours
     */
    fun <T> share(initial: T, f: AggregateContext.(Field<T>) -> T): T

    /**
     * Returns the result of applying the aggregate function [proc] to the given [key] considering as neighbours only
     * the devices aligned on the same key.
     *
     * @param K the type of the key
     * @param T the type of the value computed by the function
     * @param key the key based on which the devices will be partitioned across the network
     * @param proc the function computing the result based on the aligned devices
     */
    fun <K, T> align(key: K, proc: AggregateContext.(K) -> T): T

    /**
     * The *nbr* operator evaluates the aggregate function [f] and returns the [Field] of the last computed values of [f]
     * by all neighbours, including this device.
     *
     * This operator allows a device to share a value with all neighbours and read all the received values.
     *
     * @param T the type of the values in the returned [Field]
     * @param f function computing a value that will be shared with all neighbours
     */
    fun <T> nbr(f: AggregateContext.() -> T): Field<T>

    /**
     * The *rep* operator returns the result of applying the aggregate function [f] to the result of its last
     * evaluation, using [initial] if its the first evaluation.
     *
     * This operator allows a device to save a value for the following round of execution.
     *
     * @param T the type of the value computed in by the operator
     * @param initial the value passed to [f] on the first round of execution
     * @param f function computing the new value to be stored for the following round based on the value from the
     * previous round
     */
    fun <T> rep(initial: T, f: AggregateContext.(T) -> T): T
}
