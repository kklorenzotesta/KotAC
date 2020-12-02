package it.unito.kotac

interface Sensor<out T> {
    fun read(): T
}
