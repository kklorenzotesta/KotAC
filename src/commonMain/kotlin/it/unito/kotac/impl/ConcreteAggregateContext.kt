package it.unito.kotac.impl

import it.unito.kotac.AggregateContext
import it.unito.kotac.DeviceID
import it.unito.kotac.Field
import it.unito.kotac.Platform

/**
 * Standard implementation of [AggregateContext]. A new instance must be used on each round of execution.
 *
 * @param platform the execution [Platform]
 */
internal class ConcreteAggregateContext(override val platform: Platform) : AggregateContext {
    /**
     * [ValueTree] with the values for the local interactions. This tree will not be shared with neighbors, minimizing
     * the network usage.
     */
    private val localTree: ValueTree = MapBasedValueTree()

    /**
     * [ValueEnvironment] with all the [ValueTree] received from the neighbors.
     */
    private val neighboringEnvironment: ValueEnvironment = MapBasedValueEnvironment()

    /**
     * [ValueTree] with the values that will be shared with neighbors.
     */
    private val neighboringTree: ValueTree = MapBasedValueTree()

    /**
     * The [Stack] keeping track of the execution.
     */
    private val stack: Stack = HashBasedStack()

    /**
     * Provides alignment keys for the operators.
     */
    private val keyProvider: AggregateConstructsKeyProvider = AggregateConstructsKeyProvider()

    /**
     * Returns the [Field] of values from the [neighboringEnvironment] based on the current trace, with [deviceValue]
     * associated to this device.
     *
     * @param T the type of the values in the field
     * @param deviceValue the value associated to this device in the returned field
     */
    @Suppress("UNCHECKED_CAST")
    private fun <T> extractField(deviceValue: T): Field<T> =
        MapBasedField((neighboringEnvironment.readTrees(stack.trace) + Pair(platform.deviceID, deviceValue)) as Map<DeviceID, T>)

    override fun <T> share(initial: T, f: AggregateContext.(Field<T>) -> T): T = align(keyProvider.share()) {
        @Suppress("UNCHECKED_CAST") val input = if (localTree.containsTrace(stack.trace)) localTree.get(stack.trace) as T else initial
        val output = f(extractField(input))
        localTree.put(stack.trace, output)
        neighboringTree.put(stack.trace, output)
        output
    }

    override fun <K, T> align(key: K, proc: AggregateContext.(K) -> T): T {
        this.stack.enter(key)
        val output = proc(key)
        this.stack.exit()
        return output
    }

    override fun <T> nbr(f: AggregateContext.() -> T): Field<T> = align(keyProvider.nbr()) {
        val localOutput = f()
        neighboringTree.put(stack.trace, localOutput)
        extractField(localOutput)
    }

    override fun <T> rep(initial: T, f: AggregateContext.(T) -> T): T = align(keyProvider.rep()) {
        @Suppress("UNCHECKED_CAST") val input = if (localTree.containsTrace(stack.trace)) localTree.get(stack.trace) as T else initial
        val output = f(input)
        localTree.put(stack.trace, output)
        output
    }
}
