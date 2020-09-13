package de.kotlinBerlin.kProperties.value

import de.kotlinBerlin.kProperties.KInvalidationListener
import de.kotlinBerlin.kProperties.KObservable

/**
 * An [KObservableValue] wraps a value and allows to observe the value for changes.
 *
 * The value can be requested by the [value] property.
 *
 * An implementation may support lazy evaluation of the [value] property.
 *
 * There are 2 types of events generated by [KObservableValue] instances. Change events and invalidation events.
 * Change events can be observed by [KChangeListener] instances that are registered by the [addListener] method.
 * They signal, that the value of this [KObservableValue] has changed.
 * Invalidation events can be observed by [de.kotlinBerlin.kProperties.KInvalidationListener] instances that are
 * registered by the [addListener] method. They signal that the value of this [KObservableValue] is not valid anymore.
 * Note that does not automatically mean that the value has changed. To know  the value has actually changed
 * the value must be recomputed first.
 *
 * When using [KChangeListener]s even the lazy implementations are forced to compute the new value eagerly.
 *
 * @see KObservable
 *
 * @see KObservableBooleanValue
 * @see KObservableStringValue
 * @see KObservableNumberValue
 * @see KObservableDoubleValue
 * @see KObservableFloatValue
 * @see KObservableLongValue
 * @see KObservableIntValue
 * @see KObservableShortValue
 * @see KObservableByteValue
 *
 * @see KObservableMutableCollectionValue
 * @see KObservableMutableListValue
 * @see KObservableMutableSetValue
 * @see KObservableMutableMapValue
 */
interface KObservableValue<out T> : KObservable {

    /** This property represents the current value of this [KObservableValue]. */
    val value: T

    /**
     * Adds an [KInvalidationListener] which will be notified whenever the
     * value of the [KObservableValue] changes.
     * If the same listener is added more than once, then it will be notified more than once.
     * <p>
     * Note that the same actual [KChangeListener] instance may be
     * safely registered for different [KObservableValue] instances.
     * <p>
     * The [KObservableValue] stores a strong reference to the listener
     * which will prevent the listener from being garbage collected and may
     * result in a memory leak. It is recommended to either unregister a
     * listener by calling [removeListener] after use or to use an instance of
     * [WeakKChangeListener] to avoid this situation.
     *
     * @param aListener the [KChangeListener] to be registered.
     **/
    fun addListener(aListener: KChangeListener<T>)

    /**
     * Removes the given listener from the list of listeners, that are notified
     * whenever the value of the [KObservableValue] changes.
     * <p>
     * If the given listener has not been previously registered (i.e. it was
     * never added) then this method call is a no-op.
     *
     * If it had been previously added then it will be removed.
     * If it had been added more than once, then only the first occurrence will be removed.
     *
     * @param aListener the [KChangeListener] to be removed.
     **/
    fun removeListener(aListener: KChangeListener<T>)
}
