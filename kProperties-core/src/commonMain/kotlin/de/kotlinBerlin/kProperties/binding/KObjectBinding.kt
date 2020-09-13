package de.kotlinBerlin.kProperties.binding

import de.kotlinBerlin.kProperties.value.KObservableObjectValue

/** An [KBinding] implementation for [Any] objects. */
abstract class KObjectBinding<out T> : KObservableObjectValue<T>(), KBinding<T> {

    final override var valid: Boolean = false
        private set

    private var internalValue: T? = null

    @Suppress("UNCHECKED_CAST")
    override val value: T
        get() {
            if (!valid) {
                internalValue = computeValue()
                valid = true
            }
            return internalValue as T
        }

    /** This method should compute the new value of this [KBinding]. */
    abstract fun computeValue(): T

    /**
     * May be overridden by subclasses to perform additional logic whenever the value of this
     * [KObjectBinding] gets invalid. It gets executed before any of the listeners are called.
     */
    protected open fun onInvalidating() {
        //Empty default implementation. Can be overridden to perform additional actions!
    }

    override fun invalidate() {
        if (valid) {
            valid = false
            onInvalidating()
            fireValueChangedEvent()
        }
    }

    override fun dispose() {
        //Nothing to do by default!
    }
}