package it.unito.kotac.impl

import it.unito.kotac.DeviceID

/**
 * A [DeviceID] based on an unique [Int].
 *
 * @param id the unique id of a device
 */
data class IntDeviceID(val id: Int) : DeviceID
