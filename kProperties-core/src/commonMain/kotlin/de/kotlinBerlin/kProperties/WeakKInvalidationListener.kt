package de.kotlinBerlin.kProperties

import de.kotlinBerlin.kProperties.util.WeakReference

/**
 * A [WeakKInvalidationListener] can be used if an [KObservable]
 * should only maintain a weak reference to the listener. This helps to avoid
 * memory leaks that can occur if observers are not unregistered from observed
 * objects after use.
 */
class WeakKInvalidationListener(aListener: KInvalidationListener) : KInvalidationListener {

    private val listenerRef = WeakReference(aListener)

    override fun invalidated(anObservable: KObservable) {
        val tempListener = listenerRef.wrapped
        if (tempListener != null) {
            tempListener.invalidated(anObservable)
        } else {
            anObservable.removeListener(this)
        }
    }
}