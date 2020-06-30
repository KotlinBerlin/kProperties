package de.kotlinBerlin.kProperties.collection

import de.kotlinBerlin.kProperties.util.WeakReference

/**
 * A [WeakKMapListener] can be used if an [KObservableMap]
 * should only maintain a weak reference to the listener. This helps to avoid
 * memory leaks that can occur if observers are not unregistered from observed
 * objects after use.
 */
class WeakKMapListener<K, V>(aListener: KMapListener<K, V>) : KMapListener<K, V> {

    private val listenerRef = WeakReference(aListener)

    override fun onAdd(aMap: KObservableMap<K, V>, anAddedEntries: Collection<Pair<K, V>>) {
        performIfAvailable(aMap, anAddedEntries, KMapListener<K, V>::onAdd)
    }

    override fun onRemove(aMap: KObservableMap<K, V>, aRemovedEntries: Collection<Pair<K, V>>) {
        performIfAvailable(aMap, aRemovedEntries, KMapListener<K, V>::onRemove)
    }

    override fun onReplace(aMap: KObservableMap<K, V>, aReplacedEntries: Collection<KMapListener.Replacement<K, V>>) {
        performIfAvailable(aMap, aReplacedEntries, KMapListener<K, V>::onReplace)
    }

    private inline fun <T> performIfAvailable(
            aCollection: KObservableMap<K, V>, aParameter: T,
            anAction: KMapListener<K, V>.(KObservableMap<K, V>, T) -> Unit
    ) {
        val tempListener = listenerRef.wrapped
        if (tempListener != null) {
            tempListener.anAction(aCollection, aParameter)
        } else {
            aCollection.removeListener(this)
        }
    }
}