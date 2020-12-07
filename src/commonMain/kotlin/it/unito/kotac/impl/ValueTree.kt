package it.unito.kotac.impl

/**
 * A ValueTree represents the result of the computation of an aggregate program.
 */
interface ValueTree {
    /**
     * Returns the value computed by the block of code inside the aggregate program represented by the given [Trace],
     * or null if the [ValueTree] was not aligned with the Trace.
     *
     * Calls to this function must be preceded with a call to [containsTrace] in order to distinguish null values from
     * not aligned ValueTrees.
     *
     * @param trace a [Trace] representing a block of code inside the aggregate program.
     */
    fun get(trace: Trace): Any?

    /**
     * Returns true if the [ValueTree] is aligned with the given [Trace]. If true [get] will return the value computed
     * in the block of code represented by the Trace.
     */
    fun containsTrace(trace: Trace): Boolean

    /**
     * Updates the [ValueTree] by memorizing that the aggregate program computed [value] in the block of code
     * represented by the [Trace] [trace]
     */
    fun put(trace: Trace, value: Any?)

    /**
     * Removes the value associated with the [Trace] [trace] in this [ValueTree].
     */
    fun remove(trace: Trace)
}
