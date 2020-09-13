package de.kotlinBerlin.kProperties

/**
 * An [KObservable] wraps content and allows to observe the content for invalidation's.
 */
interface KObservable {

    /**
     * Adds an [KInvalidationListener] which will be notified whenever the
     * [KObservable] becomes invalid. If the same listener is added more than
     * once, then it will be notified more than once.
     * <p>
     * Note that the same actual [KInvalidationListener] instance may be
     * safely registered for different [KObservable] instances.
     * <p>
     * The [KObservable] stores a strong reference to the listener
     * which will prevent the listener from being garbage collected and may
     * result in a memory leak. It is recommended to either unregister a
     * listener by calling [removeListener] after use or to use an instance of
     * [WeakKInvalidationListener] to avoid this situation.
     *
     * @param aListener the [KInvalidationListener] to be registered.
     **/
    fun addListener(aListener: KInvalidationListener)

    /**
     * Removes the given listener from the list of listeners, that are notified
     * whenever the value of the [KObservable] becomes invalid.
     * <p>
     * If the given listener has not been previously registered (i.e. it was
     * never added) then this method call is a no-op.
     *
     * If it had been previously added then it will be removed.
     * If it had been added more than once, then only the first occurrence will be removed.
     *
     * @param aListener the [KInvalidationListener] to be removed.
     **/
    fun removeListener(aListener: KInvalidationListener)
}
