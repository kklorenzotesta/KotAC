package it.unito.kotac.impl

import it.unito.kotac.DeviceID

/**
 * A [ValueEnvironment] associates to each neighbouring [DeviceID] its last computed [ValueTree].
 */
interface ValueEnvironment {
    /**
     * Returns the last [ValueTree] computed by the neighbouring device identified by [DeviceID] if exists and not
     * expired, null otherwise.
     *
     * @param device the [DeviceID] of a neighbour device
     */
    fun getTree(device: DeviceID): ValueTree?

    /**
     * Saves the given [tree] as the last computed [ValueTree] for the neighbouring device identified by
     * [device].
     *
     * @param device [DeviceID] for the neighbouring device
     * @param tree the new [ValueTree] for the neighbouring device
     */
    fun putTree(device: DeviceID, tree: ValueTree)

    /**
     * Returns a [Map] from [DeviceID] to [Any] associating to each neighbouring device in the [ValueEnvironment] the
     * value corresponding to the given [Trace] in its [ValueTree] or null if its not aligned.
     *
     * @param trace the [Trace] for which will be extracted the values from the [ValueTree]s
     */
    fun readTrees(trace: Trace): Map<DeviceID, Any?>
}
