package de.kotlinBerlin.kProperties.collection

import de.kotlinBerlin.kProperties.util.WeakReference

/**
 * A [WeakKSetListener] can be used if an [KObservableSet]
 * should only maintain a weak reference to the listener. This helps to avoid
 * memory leaks that can occur if observers are not unregistered from observed
 * objects after use.
 */
class WeakKSetListener<E>(aListener: KSetListener<E>) : KSetListener<E> {

    private val listenerRef = WeakReference(aListener)

    override fun onAdd(aSet: KObservableSet<E>, anAddedList: Collection<E>) {
        performIfAvailable(aSet, anAddedList, KSetListener<E>::onAdd)
    }

    override fun onRemove(aSet: KObservableSet<E>, aRemovedList: Collection<E>) {
        performIfAvailable(aSet, aRemovedList, KSetListener<E>::onRemove)
    }

    private inline fun <T> performIfAvailable(
        aCollection: KObservableSet<E>,
        aParameter: T,
        anAction: KSetListener<E>.(KObservableSet<E>, T) -> Unit
    ) {
        val tempListener = listenerRef.wrapped
        if (tempListener != null) {
            tempListener.anAction(aCollection, aParameter)
        } else {
            aCollection.removeListener(this)
        }
    }
}

/**
 * A [WeakKMutableSetListener] can be used if an [KObservableMutableSet]
 * should only maintain a weak reference to the listener. This helps to avoid
 * memory leaks that can occur if observers are not unregistered from observed
 * objects after use.
 */
class WeakKMutableSetListener<E>(aListener: KMutableSetListener<E>) : KMutableSetListener<E> {

    private val listenerRef = WeakReference(aListener)

    override fun onAdd(aMutableSet: KObservableMutableSet<E>, anAddedList: Collection<E>) {
        performIfAvailable(aMutableSet, anAddedList, KMutableSetListener<E>::onAdd)
    }

    override fun onRemove(aMutableSet: KObservableMutableSet<E>, aRemovedList: Collection<E>) {
        performIfAvailable(aMutableSet, aRemovedList, KMutableSetListener<E>::onRemove)
    }

    private inline fun <T> performIfAvailable(
        aCollection: KObservableMutableSet<E>,
        aParameter: T,
        anAction: KMutableSetListener<E>.(KObservableMutableSet<E>, T) -> Unit
    ) {
        val tempListener = listenerRef.wrapped
        if (tempListener != null) {
            tempListener.anAction(aCollection, aParameter)
        } else {
            aCollection.removeListener(this)
        }
    }
}
