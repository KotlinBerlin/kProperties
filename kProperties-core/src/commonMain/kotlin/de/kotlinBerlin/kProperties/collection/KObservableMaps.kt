package de.kotlinBerlin.kProperties.collection

/**
 * A [Map] that can be observed for changes. The [KObservableMap] supports observation
 * of added, removed and replaced key value mappings through the [KMapListener] interface.
 *
 * [KMapListener] instances can be added and removed with the [addListener] and [removeListener] methods.
 */
interface KObservableMap<K, V> : Map<K, V> {

    /** Returns a [KObservableMutableSet] of all keys in this map. */
    override val keys: KObservableSet<K>

    /** Returns a [KObservableMutableCollection] of all values in this map. Note that this collection may contain duplicate values. */
    override val values: KObservableCollection<V>

    /**
     * Adds an [KMapListener] which will be notified whenever key value mappings are added to, removed from,
     * or replaced in the [KObservableMap]. If the same listener is added more than
     * once, then it will be notified more than once.
     * <p>
     * Note that the same actual [KMapListener] instance may be
     * safely registered for different [KObservableMap] instances.
     * <p>
     * The [KObservableMap] stores a strong reference to the listener
     * which will prevent the listener from being garbage collected and may
     * result in a memory leak. It is recommended to either unregister a
     * listener by calling [removeListener] after use or to use an instance of
     * [WeakKMapListener] to avoid this situation.
     *
     * @param aListener the [KMutableListListener] to be registered.
     **/
    fun addListener(aListener: KMapListener<K, V>)

    /**
     * Removes the given listener from the list of listeners, that are notified
     * whenever elements are added to, removed from or replaced in the [KObservableMap].
     * <p>
     * If the given listener has not been previously registered (i.e. it was
     * never added) then this method call is a no-onp.
     *
     * If it had been previously added then it will be removed.
     * If it had been added more than once, then only the first occurrence will be removed.
     *
     * @param aListener the [KMapListener] to be removed.
     **/
    fun removeListener(aListener: KMapListener<K, V>)
}

/**
 * A [Map] that can be observed for changes. The [KObservableMutableMap] supports observation
 * of added, removed and replaced key value mappings through the [KMutableMapListener] interface.
 *
 * [KMutableMapListener] instances can be added and removed with the [addListener] and [removeListener] methods.
 */
interface KObservableMutableMap<K, V> : KObservableMap<K, V>, MutableMap<K, V> {

    /** Returns a [KObservableMutableSet] of all keys in this map. */
    override val keys: KObservableMutableSet<K>

    /** Returns a [KObservableMutableCollection] of all values in this map. Note that this collection may contain duplicate values. */
    override val values: KObservableMutableCollection<V>

    /**
     * Adds an [KMutableMapListener] which will be notified whenever key value mappings are added to, removed from,
     * or replaced in the [KObservableMutableMap]. If the same listener is added more than
     * once, then it will be notified more than once.
     * <p>
     * Note that the same actual [KMutableMapListener] instance may be
     * safely registered for different [KObservableMutableMap] instances.
     * <p>
     * The [KObservableMutableMap] stores a strong reference to the listener
     * which will prevent the listener from being garbage collected and may
     * result in a memory leak. It is recommended to either unregister a
     * listener by calling [removeListener] after use or to use an instance of
     * [WeakKMutableMapListener] to avoid this situation.
     *
     * @param aListener the [KMutableListListener] to be registered.
     **/
    fun addListener(aListener: KMutableMapListener<K, V>)

    /**
     * Removes the given listener from the list of listeners, that are notified
     * whenever elements are added to, removed from or replaced in the [KObservableMutableMap].
     * <p>
     * If the given listener has not been previously registered (i.e. it was
     * never added) then this method call is a no-onp.
     *
     * If it had been previously added then it will be removed.
     * If it had been added more than once, then only the first occurrence will be removed.
     *
     * @param aListener the [KMutableMapListener] to be removed.
     **/
    fun removeListener(aListener: KMutableMapListener<K, V>)
}
