package de.kotlinBerlin.kProperties.collection

/**
 * A [Set] that can be observed for changes. The [KObservableSet] supports observation
 * of added and removed elements through the [KSetListener] interface.
 *
 * [KSetListener] instances can be added and removed with the [addListener] and [removeListener] methods.
 */
interface KObservableSet<E> : KObservableCollection<E>, MutableSet<E> {

    /**
     * Adds an [KSetListener] which will be notified whenever elements are added to or removed from the
     * [KObservableSet]. If the same listener is added more than
     * once, then it will be notified more than once.
     * <p>
     * Note that the same actual [KSetListener] instance may be
     * safely registered for different [KObservableSet] instances.
     * <p>
     * The [KObservableSet] stores a strong reference to the listener
     * which will prevent the listener from being garbage collected and may
     * result in a memory leak. It is recommended to either unregister a
     * listener by calling [removeListener] after use or to use an instance of
     * [WeakKSetListener] to avoid this situation.
     *
     * @param aListener the [KSetListener] to be registered.
     **/
    fun addListener(aListener: KSetListener<E>)

    /**
     * Removes the given listener from the list of listeners, that are notified
     * whenever elements are added to or removed from the [KObservableSet].
     * <p>
     * If the given listener has not been previously registered (i.e. it was
     * never added) then this method call is a no-onp.
     *
     * If it had been previously added then it will be removed.
     * If it had been added more than once, then only the first occurrence will be removed.
     *
     * @param aListener the [KSetListener] to be removed.
     **/
    fun removeListener(aListener: KSetListener<E>)
}