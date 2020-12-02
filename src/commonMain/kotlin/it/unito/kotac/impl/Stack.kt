package it.unito.kotac.impl

interface Stack {
    fun enter(align: Any?)
    fun exit()
    val trace: Trace
}
