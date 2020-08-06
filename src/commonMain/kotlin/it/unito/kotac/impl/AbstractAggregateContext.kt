package it.unito.kotac.impl

import it.unito.kotac.AggregateContext
import it.unito.kotac.Field

abstract class AbstractAggregateContext : AggregateContext {
    protected abstract val localEnvironment: ValueEnvironment
    protected abstract val localTree: ValueTree
    protected abstract val neighboringEnvironment: ValueEnvironment
    protected abstract val neighboringTree: ValueTree

    override fun <T> share(initial: T, f: (Field<T>) -> T): T {
        TODO("Not yet implemented")
    }

    override fun <K, T> align(key: K, proc: (K) -> T): T {
        TODO("Not yet implemented")
    }
}
