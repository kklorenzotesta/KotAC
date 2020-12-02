package it.unito.kotac.impl

interface ValueTree {
    fun get(trace: Trace): Any?
    fun containsTrace(trace: Trace): Boolean
    fun put(trace: Trace, value: Any?)
    fun remove(trace: Trace)
}
