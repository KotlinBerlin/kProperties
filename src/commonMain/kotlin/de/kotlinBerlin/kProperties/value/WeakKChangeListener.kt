package de.kotlinBerlin.kProperties.value

import de.kotlinBerlin.kProperties.util.WeakReference

/**
 * A [WeakKChangeListener] can be used if an [KObservableValue]
 * should only maintain a weak reference to the listener. This helps to avoid
 * memory leaks that can occur if observers are not unregistered from observed
 * objects after use.
 */
class WeakKChangeListener<T>(aListener: KChangeListener<T>) : KChangeListener<T> {

    private val listenerRef = WeakReference(aListener)

    override fun onChange(anObservable: KObservableValue<T>, anOldValue: T, aNewValue: T) {
        val tempListener = listenerRef.wrapped
        if (tempListener != null) {
            tempListener.onChange(anObservable, anOldValue, aNewValue)
        } else {
            anObservable.removeListener(this)
        }
    }
}