package de.kotlinBerlin.kProperties.collection

/** Represents a replacement of the [oldValue] with the [newValue] at a specified [key] */
data class MapReplacement<K, V>(
    /** The key of the old and the new value*/
    val key: K,
    /** The value that was replaced */
    val oldValue: V,
    /** The value that replaced it */
    val newValue: V
)

/**
 * An [KMapListener] is notified whenever elements are added to, removed from or replaced in an [KObservableMap].
 * It can be registered and unregistered with [KObservableMap.addListener]
 * respectively [KObservableMap.removeListener].
 * The same instance of [KMapListener] can be registered to listen to multiple [KObservableMap] instances.
 *
 * @see KObservableMap
 */
interface KMapListener<K, V> {
    /** This method gets called whenever elements are added to the [KObservableMap] */
    fun onAdd(aMap: KObservableMap<K, V>, anAddedEntries: Collection<Pair<K, V>>) {
        //Can be overridden
    }

    /** This method gets called whenever elements are removed from the [KObservableMap] */
    fun onRemove(aMap: KObservableMap<K, V>, aRemovedEntries: Collection<Pair<K, V>>) {
        //Can be overridden
    }

    /** This method gets called whenever elements are replaced in the [KObservableMap] */
    fun onReplace(aMap: KObservableMap<K, V>, aReplacedEntries: Collection<MapReplacement<K, V>>) {
        //Can be overridden
    }
}

/**
 * An [KMutableMapListener] is notified whenever elements are added to, removed from or replaced in an [KObservableMutableMap].
 * It can be registered and unregistered with [KObservableMutableMap.addListener]
 * respectively [KObservableMutableMap.removeListener].
 * The same instance of [KMutableMapListener] can be registered to listen to multiple [KObservableMutableMap] instances.
 *
 * @see KObservableMutableMap
 */
interface KMutableMapListener<K, V> {

    /** This method gets called whenever elements are added to the [KObservableMutableMap] */
    fun onAdd(aMutableMap: KObservableMutableMap<K, V>, anAddedEntries: Collection<Pair<K, V>>) {
        //Can be overridden
    }

    /** This method gets called whenever elements are removed from the [KObservableMutableMap] */
    fun onRemove(aMutableMap: KObservableMutableMap<K, V>, aRemovedEntries: Collection<Pair<K, V>>) {
        //Can be overridden
    }

    /** This method gets called whenever elements are replaced in the [KObservableMutableMap] */
    fun onReplace(aMutableMap: KObservableMutableMap<K, V>, aReplacedEntries: Collection<MapReplacement<K, V>>) {
        //Can be overridden
    }
}
