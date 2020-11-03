package it.unito.kotac.impl

import it.unito.kotac.DeviceID

class MapBasedValueEnvironment : ValueEnvironment {
    private val map: MutableMap<DeviceID, ValueTree> = HashMap()

    override fun getTree(device: DeviceID): ValueTree? = this.map[device]

    override fun putTree(device: DeviceID, tree: ValueTree) {
        this.map[device] = tree
    }

    override fun readTrees(trace: Trace): Map<DeviceID, Any?> = this.map.mapValues { it.value.get(trace) }
        .filter { it.value != null }.mapValues { it.value!!.value }
}
