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

/**
 * A [WeakKMutableCollectionListener] can be used if an [KObservableMutableCollection]
 * should only maintain a weak reference to the listener. This helps to avoid
 * memory leaks that can occur if observers are not unregistered from observed
 * objects after use.
 */
class WeakKMutableCollectionListener<E>(aListener: KMutableCollectionListener<E>) : KMutableCollectionListener<E> {

    private val listenerRef = WeakReference(aListener)

    override fun onAdd(aMutableCollection: KObservableMutableCollection<E>, anAddedList: Collection<E>) {
        performIfAvailable(aMutableCollection, anAddedList, KMutableCollectionListener<E>::onAdd)
    }

    override fun onRemove(aMutableCollection: KObservableMutableCollection<E>, aRemovedList: Collection<E>) {
        performIfAvailable(aMutableCollection, aRemovedList, KMutableCollectionListener<E>::onRemove)
    }

    private inline fun <T> performIfAvailable(
        aMutableCollection: KObservableMutableCollection<E>,
        aParameter: T,
        anAction: KMutableCollectionListener<E>.(KObservableMutableCollection<E>, T) -> Unit
    ) {
        val tempListener = listenerRef.wrapped
        if (tempListener != null) {
            tempListener.anAction(aMutableCollection, aParameter)
        } else {
            aMutableCollection.removeListener(this)
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
