package it.unito.kotac.impl

class MapBasedValueTree : ValueTree {
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
