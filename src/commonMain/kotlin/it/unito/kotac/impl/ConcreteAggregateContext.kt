package it.unito.kotac.impl

import it.unito.kotac.AggregateContext
import it.unito.kotac.Field

class ConcreteAggregateContext : AggregateContext {
    private val localTree: ValueTree = MapBasedValueTree()
    private val neighboringEnvironment: ValueEnvironment = MapBasedValueEnvironment()
    private val neighboringTree: ValueTree = MapBasedValueTree()

    override fun <T> share(initial: T, f: (Field<T>) -> T): T {
        TODO("Not yet implemented")
    }

    override fun <K, T> align(key: K, proc: (K) -> T): T {
        TODO("Not yet implemented")
    }

    override fun <T> nbr(f: () -> T): Field<T> {
        TODO("Not yet implemented")
    }

    override fun <T> rep(initial: T, f: (T) -> T): T {
        TODO("Not yet implemented")
    }
}
