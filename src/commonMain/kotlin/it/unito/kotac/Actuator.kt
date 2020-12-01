package it.unito.kotac

interface Actuator<in T> {
    fun set(value: T)
}
