package de.kotlinBerlin.kProperties.collection

/**
 * An [KCollectionListener] is notified whenever elements are added to or removed from an [KObservableCollection].
 * It can be registered and unregistered with [KObservableCollection.addListener]
 * respectively [KObservableCollection.removeListener].
 * The same instance of [KCollectionListener] can be registered to listen to multiple [KObservableCollection] instances.
 *
 * @see KObservableCollection
 */
interface KCollectionListener<E> {

    /** This method gets called whenever elements are added to the [KObservableCollection] */
    fun onAdd(aCollection: KObservableCollection<E>, anAddedList: Collection<E>) {
        //Can be overridden
    }

    /** This method gets called whenever elements are removed from the [KObservableCollection] */
    fun onRemove(aCollection: KObservableCollection<E>, aRemovedList: Collection<E>) {
        //Can be overridden
    }
}

