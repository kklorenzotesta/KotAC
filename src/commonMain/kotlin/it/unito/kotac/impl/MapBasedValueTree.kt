package it.unito.kotac.impl

/**
 * Internal implementation of [ValueTree] based on a [MutableMap].
 */
internal class MapBasedValueTree : ValueTree {
    /** backing [MutableMap] */
    private val map: MutableMap<Trace, Any?> = HashMap()

    override fun get(trace: Trace): Any? = this.map[trace]

    override fun containsTrace(trace: Trace): Boolean = map.containsKey(trace)

    override fun put(trace: Trace, value: Any?) {
        this.map[trace] = value
    }

    override fun remove(trace: Trace) {
        this.map.remove(trace)
    }
}
