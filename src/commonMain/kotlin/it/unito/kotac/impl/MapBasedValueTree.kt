package it.unito.kotac.impl

class MapBasedValueTree : ValueTree {
    private val map: MutableMap<Trace, ValueTreeValue> = HashMap()

    override fun get(trace: Trace): ValueTreeValue? = this.map[trace]

    override fun put(trace: Trace, value: ValueTreeValue) {
        this.map[trace] = value
    }

    override fun remove(trace: Trace) {
        this.map.remove(trace)
    }
}
