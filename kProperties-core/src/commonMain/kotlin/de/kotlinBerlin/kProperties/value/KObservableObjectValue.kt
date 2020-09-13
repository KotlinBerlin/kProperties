package de.kotlinBerlin.kProperties.value

import de.kotlinBerlin.kProperties.KInvalidationListener

/**
 * The base class for implementations of [KObservableValue] which adds support for the registration if
 * [KInvalidationListener] and [KChangeListener] instances.
 */
abstract class KObservableObjectValue<out T> : KObservableValue<T> {

    private var helper: KExpressionHelper<T>? = null

    final override fun addListener(aListener: KChangeListener<T>) {
        helper = addListener(helper, this, aListener)
    }

    final override fun addListener(aListener: KInvalidationListener) {
        helper = addListener(helper, this, aListener)
    }

    final override fun removeListener(aListener: KInvalidationListener) {
        helper = helper?.removeListener(aListener)
    }

    final override fun removeListener(aListener: KChangeListener<T>) {
        helper = helper?.removeListener(aListener)
    }

    /** Fires events to all registered [KInvalidationListener] and [KChangeListener] instances. */
    protected fun fireValueChangedEvent() {
        helper?.fireValueChangedEvent()
    }
}