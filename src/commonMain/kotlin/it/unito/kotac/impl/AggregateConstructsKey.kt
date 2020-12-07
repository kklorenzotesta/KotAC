package it.unito.kotac.impl

/**
 * An alignment key used in the implementation of an aggregate construct.
 *
 * @param name the name of the construct which call is represented by this key
 * @param index an index identifying a precise call of the construct in the user code
 */
internal data class AggregateConstructsKey(private val name: String, private val index: Int)

/**
 * AggregateConstructsKeyProvider generates unique alignment keys for each call to an aggregate construct,
 * as [AggregateConstructsKey]s.
 *
 * A new instance must be used for each round of execution in order to get the right key for each construct invocation.
 */
internal class AggregateConstructsKeyProvider {
    /**
     * Counts how many aggregate constructs have been called, in order to distinguish two sibling call of the same ù
     * construct.
     */
    private var counter: Int = 0

    /**
     * Returns an [AggregateConstructsKey] identifying a construct with named [name].
     */
    private fun gen(name: String): AggregateConstructsKey = AggregateConstructsKey(name, counter++)

    /**
     * Returns an [AggregateConstructsKey] identifying an invocation of the nbr construct
     */
    fun nbr(): AggregateConstructsKey = gen("nbr")

    /**
     * Returns an [AggregateConstructsKey] identifying an invocation of the rep construct
     */
    fun rep(): AggregateConstructsKey = gen("rep")

    /**
     * Returns an [AggregateConstructsKey] identifying an invocation of the share construct
     */
    fun share(): AggregateConstructsKey = gen("share")
}
