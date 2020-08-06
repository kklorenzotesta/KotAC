package it.unito.kotac.impl

interface ValueTree {
    fun get(trace: Trace): Any?
    fun put(trace: Trace, value: Any?)
}
