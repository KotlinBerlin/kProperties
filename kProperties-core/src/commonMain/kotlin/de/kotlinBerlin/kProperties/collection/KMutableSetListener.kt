package de.kotlinBerlin.kProperties.collection

/**
 * An [KSetListener] is notified whenever elements are added to or removed from an [KObservableSet].
 * It can be registered and unregistered with [KObservableSet.addListener]
 * respectively [KObservableSet.removeListener].
 * The same instance of [KSetListener] can be registered to listen to multiple [KObservableSet] instances.
 *
 * @see KObservableSet
 */
interface KSetListener<E> {

    /** This method gets called whenever elements are added to the [KObservableSet] */
    fun onAdd(aSet: KObservableSet<E>, anAddedList: Collection<E>) {
        //Can be overridden
    }

    /** This method gets called whenever elements are removed from the [KObservableSet] */
    fun onRemove(aSet: KObservableSet<E>, aRemovedList: Collection<E>) {
        //Can be overridden
    }
}

/**
 * An [KMutableSetListener] is notified whenever elements are added to or removed from an [KObservableMutableSet].
 * It can be registered and unregistered with [KObservableMutableSet.addListener]
 * respectively [KObservableMutableSet.removeListener].
 * The same instance of [KMutableSetListener] can be registered to listen to multiple [KObservableMutableSet] instances.
 *
 * @see KObservableMutableSet
 */
interface KMutableSetListener<E> {

    /** This method gets called whenever elements are added to the [KObservableMutableSet] */
    fun onAdd(aMutableSet: KObservableMutableSet<E>, anAddedList: Collection<E>) {
        //Can be overridden
    }

    /** This method gets called whenever elements are removed from the [KObservableMutableSet] */
    fun onRemove(aMutableSet: KObservableMutableSet<E>, aRemovedList: Collection<E>) {
        //Can be overridden
    }
}
