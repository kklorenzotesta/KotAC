package it.unito.kotac.impl

/**
 * Internal implementation of [Trace] based on a sequence of hashcodes from the alignment objects.
 */
internal sealed class HashBasedTrace : Trace {
    companion object {
        /**
         * Returns a new [HashBasedTrace] representing an enter on [obj] from an empty [Trace].
         */
        fun from(obj: Any?): HashBasedTrace = HashBasedTraceElement(obj.hashCode(), EmptyHashBasedTrace)
    }

    /**
     * Returns a new [HashBasedTrace] representing this Trace followed by and enter on [obj].
     */
    abstract fun push(obj: Any?): HashBasedTrace

    /**
     * Returns the [HashBasedTrace] before the last enter call.
     */
    abstract fun pop(): HashBasedTrace
}

/**
 * Represents an empty [HashBasedTrace].
 */
internal object EmptyHashBasedTrace : HashBasedTrace() {
    override fun push(obj: Any?): HashBasedTrace = from(obj)

    override fun pop(): HashBasedTrace = this
}

/**
 * Represents the result of calling enter on [previousHash] with and object with [hash] hashcode.
 */
internal data class HashBasedTraceElement(private val hash: Int, private val previousHash: HashBasedTrace) :
    HashBasedTrace() {
    override fun push(obj: Any?): HashBasedTrace = HashBasedTraceElement(obj.hashCode(), this)

    override fun pop(): HashBasedTrace = this.previousHash
}
