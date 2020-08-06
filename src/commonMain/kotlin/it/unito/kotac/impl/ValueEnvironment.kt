package it.unito.kotac.impl

import it.unito.kotac.DeviceID

interface ValueEnvironment {
    fun getTree(device: DeviceID): ValueTree
    fun putTree(device: DeviceID, tree: ValueTree)
}
