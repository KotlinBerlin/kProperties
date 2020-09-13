package de.kotlinBerlin.kProperties.collection

import de.kotlinBerlin.kProperties.util.WeakReference

/**
 * A [WeakKCollectionListener] can be used if an [KObservableCollection]
 * should only maintain a weak reference to the listener. This helps to avoid
 * memory leaks that can occur if observers are not unregistered from observed
 * objects after use.
 */
class WeakKCollectionListener<E>(aListener: KCollectionListener<E>) : KCollectionListener<E> {

    private val listenerRef = WeakReference(aListener)

    override fun onAdd(aCollection: KObservableCollection<E>, anAddedList: Collection<E>) {
        performIfAvailable(aCollection, anAddedList, KCollectionListener<E>::onAdd)
    }

    override fun onRemove(aCollection: KObservableCollection<E>, aRemovedList: Collection<E>) {
        performIfAvailable(aCollection, aRemovedList, KCollectionListener<E>::onRemove)
    }

    private inline fun <T> performIfAvailable(
            aCollection: KObservableCollection<E>,
            aParameter: T,
            anAction: KCollectionListener<E>.(KObservableCollection<E>, T) -> Unit
    ) {
        val tempListener = listenerRef.wrapped
        if (tempListener != null) {
            tempListener.anAction(aCollection, aParameter)
        } else {
            aCollection.removeListener(this)
        }
    }

    @Suppress("SuspiciousEqualsCombination")
    override fun equals(other: Any?): Boolean {
        return this === other || listenerRef.wrapped == other
    }

    override fun hashCode(): Int {
        return listenerRef.wrapped.hashCode()
    }
}