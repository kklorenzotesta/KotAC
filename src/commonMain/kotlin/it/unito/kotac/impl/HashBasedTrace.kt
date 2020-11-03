package it.unito.kotac.impl

internal sealed class HashBasedTrace : Trace {
    companion object {
        fun from(obj: Any?): HashBasedTrace = HashBasedTraceElement(obj.hashCode(), EmptyHashBasedTrace)
    }

    abstract fun push(obj: Any?): HashBasedTrace

    abstract fun pop(): HashBasedTrace
}

internal object EmptyHashBasedTrace : HashBasedTrace() {
    override fun push(obj: Any?): HashBasedTrace = from(obj)

    override fun pop(): HashBasedTrace = this
}

internal data class HashBasedTraceElement(private val hash: Int, private val previousHash: HashBasedTrace) : HashBasedTrace() {
    override fun push(obj: Any?): HashBasedTrace = HashBasedTraceElement(obj.hashCode(), this)

    override fun pop(): HashBasedTrace = this.previousHash
}
