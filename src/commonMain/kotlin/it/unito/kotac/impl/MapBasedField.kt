package it.unito.kotac.impl

import it.unito.kotac.DeviceID
import it.unito.kotac.Field

/**
 * Internal implementation of [Field] based on a [Map]
 */
internal class MapBasedField<T>(private val elements: Map<DeviceID, T>) : Field<T> {
    override fun <R> map(f: (T) -> R): Field<R> = MapBasedField(elements.mapValues { f(it.value) })
    override fun <R> fold(initial: R, operation: (acc: R, T) -> R): R = elements.values.fold(initial, operation)
}
