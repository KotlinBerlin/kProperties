package de.kotlinBerlin.kProperties.value

/**
 * An [KChangeListener] is notified whenever the value of an
 * [KObservableValue] changes. It can be registered and
 * unregistered with [KObservableValue.addListener] respectively [KObservableValue.removeListener].
 * The same instance of [KChangeListener] can be registered to listen to multiple [KObservableValue] instances.
 *
 * @see KObservableValue
 */
fun interface KChangeListener<in T> {

    /**
     * This method is called if the value of an [KObservableValue] changes.
     *
     * @param anObservable the [KObservableValue] which value changed.
     * @param anOldValue the old value.
     * @param aNewValue the new value.
     */
    fun onChange(anObservable: KObservableValue<T>, anOldValue: T, aNewValue: T)
}