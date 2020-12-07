package it.unito.kotac.impl

import it.unito.kotac.DeviceID

/**
 * A [DeviceID] based on an unique [String].
 *
 * @param id the unique id of a device
 */
data class StringDeviceID(val id: String) : DeviceID
