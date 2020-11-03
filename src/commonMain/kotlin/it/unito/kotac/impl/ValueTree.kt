package it.unito.kotac.impl

interface ValueTree {
    fun get(trace: Trace): ValueTreeValue?
    fun put(trace: Trace, value: ValueTreeValue)
    fun remove(trace: Trace)
}
