package de.kotlinBerlin.kProperties.collection

/**
 * A [Collection] that can be observed for changes. The [KObservableCollection] supports observation
 * of added and removed elements through the [KCollectionListener] interface.
 *
 * [KCollectionListener] instances can be added and removed with the [addListener] and [removeListener] methods.
 */
interface KObservableCollection<E> : Collection<E> {
    /**
     * Adds an [KCollectionListener] which will be notified whenever elements are added to
     * or removed from the [KObservableCollection]. If the same listener is added more than
     * once, then it will be notified more than once.
     * <p>
     * Note that the same actual [KCollectionListener] instance may be
     * safely registered for different [KObservableCollection] instances.
     * <p>
     * The [KObservableCollection] stores a strong reference to the listener
     * which will prevent the listener from being garbage collected and may
     * result in a memory leak. It is recommended to either unregister a
     * listener by calling [removeListener] after use or to use an instance of
     * [WeakKCollectionListener] to avoid this situation.
     *
     * @param aListener the [KCollectionListener] to be registered.
     **/
    fun addListener(aListener: KCollectionListener<E>)

    /**
     * Removes the given listener from the list of listeners, that are notified
     * whenever elements are added to or removed from the [KObservableCollection].
     * <p>
     * If the given listener has not been previously registered (i.e. it was
     * never added) then this method call is a no-onp.
     *
     * If it had been previously added then it will be removed.
     * If it had been added more than once, then only the first occurrence will be removed.
     *
     * @param aListener the [KCollectionListener] to be removed.
     **/
    fun removeListener(aListener: KCollectionListener<E>)
}

/**
 * A [Collection] that can be observed for changes. The [KObservableMutableCollection] supports observation
 * of added and removed elements through the [KMutableCollectionListener] interface.
 *
 * [KMutableCollectionListener] instances can be added and removed with the [addListener] and [removeListener] methods.
 */
interface KObservableMutableCollection<E> : KObservableCollection<E>, MutableCollection<E> {

    /**
     * Adds an [KMutableCollectionListener] which will be notified whenever elements are added to
     * or removed from the [KObservableMutableCollection]. If the same listener is added more than
     * once, then it will be notified more than once.
     * <p>
     * Note that the same actual [KMutableCollectionListener] instance may be
     * safely registered for different [KObservableMutableCollection] instances.
     * <p>
     * The [KObservableMutableCollection] stores a strong reference to the listener
     * which will prevent the listener from being garbage collected and may
     * result in a memory leak. It is recommended to either unregister a
     * listener by calling [removeListener] after use or to use an instance of
     * [WeakKMutableCollectionListener] to avoid this situation.
     *
     * @param aListener the [KMutableCollectionListener] to be registered.
     **/
    fun addListener(aListener: KMutableCollectionListener<E>)

    /**
     * Removes the given listener from the list of listeners, that are notified
     * whenever elements are added to or removed from the [KObservableMutableCollection].
     * <p>
     * If the given listener has not been previously registered (i.e. it was
     * never added) then this method call is a no-onp.
     *
     * If it had been previously added then it will be removed.
     * If it had been added more than once, then only the first occurrence will be removed.
     *
     * @param aListener the [KMutableCollectionListener] to be removed.
     **/
    fun removeListener(aListener: KMutableCollectionListener<E>)
}
