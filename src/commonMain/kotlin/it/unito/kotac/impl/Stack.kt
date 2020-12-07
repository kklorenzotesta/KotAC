package it.unito.kotac.impl

/**
 * A Stack manage the generation of the [Trace]s for a round of computation.
 */
interface Stack {
    /**
     * Updates the [trace] by memorizing that the execution is entered in a new block of code identified by the given
     * alignment key [align].
     *
     * Every call to enter **must** be followed by a call to [exit] after the execution of the aligned block of code.
     *
     * @param align the alignment key for the block
     */
    fun enter(align: Any?)

    /**
     * Updates the [trace] by restoring it to the one before the last invocation of [enter].
     */
    fun exit()

    /**
     * The current [Trace], starting with an empty Trace before the first execution of [enter].
     */
    val trace: Trace
}
