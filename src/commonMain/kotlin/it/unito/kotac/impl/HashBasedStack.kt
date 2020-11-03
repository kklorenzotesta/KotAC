package it.unito.kotac.impl

internal class HashBasedStack : Stack {
    override fun enter(align: Any?) {
        trace = trace.push(align)
    }

    override fun exit() {
        trace = trace.pop()
    }

    override var trace: HashBasedTrace = EmptyHashBasedTrace
}
