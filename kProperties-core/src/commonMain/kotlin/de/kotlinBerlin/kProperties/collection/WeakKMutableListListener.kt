package de.kotlinBerlin.kProperties.collection

import de.kotlinBerlin.kProperties.util.WeakReference

/**
 * A [WeakKListListener] can be used if an [KObservableList]
 * should only maintain a weak reference to the listener. This helps to avoid
 * memory leaks that can occur if observers are not unregistered from observed
 * objects after use.
 */
class WeakKListListener<E>(aListener: KListListener<E>) : KListListener<E> {

    private val listenerRef = WeakReference(aListener)

    override fun onAdd(aList: KObservableList<E>, anAddedList: Collection<E>) {
        performIfAvailable(aList, anAddedList, KListListener<E>::onAdd)
    }

    override fun onRemove(aList: KObservableList<E>, aRemovedList: Collection<E>) {
        performIfAvailable(aList, aRemovedList, KListListener<E>::onRemove)
    }

    override fun onMove(aList: KObservableList<E>, aPermutationList: Collection<ListPermutation<E>>) {
        performIfAvailable(aList, aPermutationList, KListListener<E>::onMove)
    }

    override fun onReplace(aList: KObservableList<E>, aReplacementList: Collection<ListReplacement<E>>) {
        performIfAvailable(aList, aReplacementList, KListListener<E>::onReplace)
    }

    private inline fun <T> performIfAvailable(
        aCollection: KObservableList<E>, aParameter: T,
        anAction: KListListener<E>.(KObservableList<E>, T) -> Unit
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
 * A [WeakKMutableListListener] can be used if an [KObservableMutableList]
 * should only maintain a weak reference to the listener. This helps to avoid
 * memory leaks that can occur if observers are not unregistered from observed
 * objects after use.
 */
class WeakKMutableListListener<E>(aListener: KMutableListListener<E>) : KMutableListListener<E> {

    private val listenerRef = WeakReference(aListener)

    override fun onAdd(aMutableList: KObservableMutableList<E>, anAddedList: Collection<E>) {
        performIfAvailable(aMutableList, anAddedList, KMutableListListener<E>::onAdd)
    }

    override fun onRemove(aMutableList: KObservableMutableList<E>, aRemovedList: Collection<E>) {
        performIfAvailable(aMutableList, aRemovedList, KMutableListListener<E>::onRemove)
    }

    override fun onMove(aMutableList: KObservableMutableList<E>, aPermutationList: Collection<ListPermutation<E>>) {
        performIfAvailable(aMutableList, aPermutationList, KMutableListListener<E>::onMove)
    }

    override fun onReplace(aMutableList: KObservableMutableList<E>, aReplacementList: Collection<ListReplacement<E>>) {
        performIfAvailable(aMutableList, aReplacementList, KMutableListListener<E>::onReplace)
    }

    private inline fun <T> performIfAvailable(
        aCollection: KObservableMutableList<E>, aParameter: T,
        anAction: KMutableListListener<E>.(KObservableMutableList<E>, T) -> Unit
    ) {
        val tempListener = listenerRef.wrapped
        if (tempListener != null) {
            tempListener.anAction(aCollection, aParameter)
        } else {
            aCollection.removeListener(this)
        }
    }
}
