package de.kotlinBerlin.kProperties.binding

import de.kotlinBerlin.kProperties.value.KObservableValue

/**
 * A [KBinding] calculates a value that depends on one or more sources. A binding observes
 * its dependencies for changes and updates its value automatically.
 *
 * A source can be anything, but to be able to observe it the source should implement the
 * [de.kotlinBerlin.kProperties.KObservable] or even the [KObservableValue] interface.
 * [KBinding] implements [KObservableValue] allowing to use it in another binding.
 *
 * All bindings in this library are calculated lazily. That means, if
 * a dependency changes, the result of a binding is not immediately
 * recalculated, but it is marked as invalid. Next time the value of an invalid
 * binding is requested, it is recalculated.
 */
interface KBinding<out T> : KObservableValue<T> {

    /**
     * The valid state represents whether or not the current value of the [KBinding] instance
     * is valid or should be recomputed.
     */
    val valid: Boolean

    /** Manually invalidates the [KBinding] */
    fun invalidate()

    /**
     * Disposes this binding when it is not used anymore. Typically this removes the listeners from the
     * observed sources.
     */
    fun dispose()
}