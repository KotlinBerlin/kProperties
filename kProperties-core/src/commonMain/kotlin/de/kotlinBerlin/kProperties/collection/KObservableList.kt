package de.kotlinBerlin.kProperties.collection

/**
 * A [List] that can be observed for changes. The [KObservableList] supports observation
 * of added and removed elements as well as replacements and movements of elements
 * through the [KListListener] interface.
 *
 * [KListListener] instances can be added and removed with the [addListener] and [removeListener] methods.
 */
interface KObservableList<E> : KObservableCollection<E>, MutableList<E> {

    /**
     * Adds an [KListListener] which will be notified whenever elements are added to, removed from,
     * replaced or moved in the [KObservableList]. If the same listener is added more than
     * once, then it will be notified more than once.
     * <p>
     * Note that the same actual [KListListener] instance may be
     * safely registered for different [KObservableList] instances.
     * <p>
     * The [KObservableList] stores a strong reference to the listener
     * which will prevent the listener from being garbage collected and may
     * result in a memory leak. It is recommended to either unregister a
     * listener by calling [removeListener] after use or to use an instance of
     * [WeakKListListener] to avoid this situation.
     *
     * @param aListener the [KListListener] to be registered.
     **/
    fun addListener(aListener: KListListener<E>)

    /**
     * Removes the given listener from the list of listeners, that are notified
     * whenever elements are added to, removed from, replaced or moved in the [KObservableList].
     * <p>
     * If the given listener has not been previously registered (i.e. it was
     * never added) then this method call is a no-onp.
     *
     * If it had been previously added then it will be removed.
     * If it had been added more than once, then only the first occurrence will be removed.
     *
     * @param aListener the [KListListener] to be removed.
     **/
    fun removeListener(aListener: KListListener<E>)

    /**
     * When using the implementations of this library, then he returned list is currently
     * not observable and doesn't trigger any change events on the main list if any modifications
     * are made through the sub list. This may change in the future.
     */
    override fun subList(fromIndex: Int, toIndex: Int): MutableList<E>
}
