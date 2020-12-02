package it.unito.kotac.impl

internal data class AggregateConstructsKey(private val name: String, private val index: Int)

internal class AggregateConstructsKeyProvider {
    private var counter: Int = 0

    private fun gen(name: String): AggregateConstructsKey = AggregateConstructsKey(name, counter++)

    fun nbr(): AggregateConstructsKey = gen("nbr")
    fun rep(): AggregateConstructsKey = gen("rep")
    fun share(): AggregateConstructsKey = gen("share")
}
