package it.unito.kotac.impl

import it.unito.kotac.AggregateContext
import it.unito.kotac.DeviceID
import it.unito.kotac.Field
import it.unito.kotac.Platform

internal class ConcreteAggregateContext(override val platform: Platform) : AggregateContext {
    private val localTree: ValueTree = MapBasedValueTree()
    private val neighboringEnvironment: ValueEnvironment = MapBasedValueEnvironment()
    private val neighboringTree: ValueTree = MapBasedValueTree()
    private val stack: Stack = HashBasedStack()
    private val keyProvider: AggregateConstructsKeyProvider = AggregateConstructsKeyProvider()

    @Suppress("UNCHECKED_CAST")
    private fun <T> extractField(deviceValue: T): Field<T> =
        MapBasedField((neighboringEnvironment.readTrees(stack.trace) + Pair(platform.deviceID, deviceValue)) as Map<DeviceID, T>)

    override fun <T> share(initial: T, f: (Field<T>) -> T): T = align(keyProvider.share()) {
        val lastValue = localTree.get(stack.trace)
        @Suppress("UNCHECKED_CAST") val output = f(extractField(if (lastValue != null) lastValue.value as T else initial))
        localTree.put(stack.trace, ValueTreeValue(output))
        neighboringTree.put(stack.trace, ValueTreeValue(output))
        output
    }

    override fun <K, T> align(key: K, proc: (K) -> T): T {
        this.stack.enter(key)
        val output = proc(key)
        this.stack.exit()
        return output
    }

    override fun <T> nbr(f: () -> T): Field<T> = align(keyProvider.nbr()) {
        val localOutput = f()
        neighboringTree.put(stack.trace, ValueTreeValue(localOutput))
        extractField(localOutput)
    }

    override fun <T> rep(initial: T, f: (T) -> T): T = align(keyProvider.rep()) {
        val lastValue = localTree.get(stack.trace)
        @Suppress("UNCHECKED_CAST") val output = f(if (lastValue != null) lastValue.value as T else initial)
        localTree.put(stack.trace, ValueTreeValue(output))
        output
    }
}
