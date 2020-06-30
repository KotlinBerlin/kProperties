package de.kotlinBerlin.kProperties.collection

/**
 * An [KListListener] is notified whenever elements are added to, removed from, replaced or moved in an [KObservableList].
 * It can be registered and unregistered with [KObservableList.addListener]
 * respectively [KObservableList.removeListener].
 * The same instance of [KListListener] can be registered to listen to multiple [KObservableList] instances.
 *
 * @see KObservableList
 */
interface KListListener<E> {

    /** This method gets called whenever elements are added to the [KObservableList] */
    fun onAdd(aList: KObservableList<E>, anAddedList: Collection<E>) {
        //Can be overridden
    }

    /** This method gets called whenever elements are removed from the [KObservableList] */
    fun onRemove(aList: KObservableList<E>, aRemovedList: Collection<E>) {
        //Can be overridden
    }

    /** This method gets called whenever elements are moved inside the [KObservableList] */
    fun onMove(aList: KObservableList<E>, aPermutationList: Collection<Permutation<E>>) {
        //Can be overridden
    }

    /** This method gets called whenever elements are replaced in the [KObservableList] */
    fun onReplace(aList: KObservableList<E>, aReplacementList: Collection<Replacement<E>>) {
        //Can be overridden
    }

    /** Represents a move of the [element] from an [oldIndex] to the [newIndex] */
    data class Permutation<E>(
            /** The element that was moved. */
            val element: E,
            /** The old index of the element. */
            val oldIndex: Int,
            /** The new index of the element. */
            val newIndex: Int
    )

    /** Represents a replacement of the [oldElement] with the [newElement] at a specified [index] */
    data class Replacement<E>(
            /** The element that was replaced */
            val oldElement: E,
            /** The element that replaced it */
            val newElement: E,
            /** The index of the old and the new element */
            val index: Int
    )
}