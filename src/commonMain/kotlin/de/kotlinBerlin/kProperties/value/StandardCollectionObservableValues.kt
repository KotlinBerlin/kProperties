package de.kotlinBerlin.kProperties.value

import de.kotlinBerlin.kProperties.collection.*
import de.kotlinBerlin.kProperties.collection.KListListener.Permutation
import de.kotlinBerlin.kProperties.collection.KListListener.Replacement
import kotlin.collections.MutableMap.MutableEntry

/**
 * An observable reference to an [KObservableCollection].
 *
 * @see KObservableCollection
 * @see KObservableValue
 */
interface KObservableCollectionValue<E, C : KObservableCollection<E>?> : KObservableValue<C>, KObservableCollection<E>

/**
 * An observable reference to an [KObservableList].
 *
 * @see KObservableList
 * @see KObservableValue
 */
interface KObservableListValue<E, L : KObservableList<E>?> : KObservableValue<L>, KObservableList<E>

/**
 * An observable reference to an [KObservableSet].
 *
 * @see KObservableSet
 * @see KObservableValue
 */
interface KObservableSetValue<E, S : KObservableSet<E>?> : KObservableValue<S>, KObservableSet<E>

/**
 * An observable reference to an [KObservableMap].
 *
 * @see KObservableMap
 * @see KObservableValue
 */
interface KObservableMapValue<K, V, M : KObservableMap<K, V>?> : KObservableValue<M>, KObservableMap<K, V>

/**
 * Base class for all [KObservableCollectionValue] implementations that implements all the methods of
 * an [MutableCollection] and delegates them to the wrapped value. If the wrapped value is null then
 * all methods behave as if applied to an immutable empty collection. All methods of a mutable collection
 * throw an exception in this case.
 */
abstract class BasicKObservableCollectionValue<E, C : KObservableCollection<E>?> :
        KObservableObjectValue<C>(),
        KObservableCollectionValue<E, C>,
        KCollectionListener<E> {

    private val listeners = mutableListOf<KCollectionListener<E>>()

    override val size: Int
        get() = performOrDelegate(Collection<E>::size)

    override fun onAdd(aCollection: KObservableCollection<E>, anAddedList: Collection<E>) {
        listeners.forEach { it.onAdd(aCollection, anAddedList) }
    }

    override fun onRemove(aCollection: KObservableCollection<E>, aRemovedList: Collection<E>) {
        listeners.forEach { it.onRemove(aCollection, aRemovedList) }
    }

    override fun addListener(aListener: KCollectionListener<E>) {
        listeners.add(aListener)
    }

    override fun removeListener(aListener: KCollectionListener<E>) {
        listeners.remove(aListener)
    }

    override fun contains(element: E): Boolean = performOrDelegate(element, Collection<E>::contains)

    override fun containsAll(elements: Collection<E>): Boolean = performOrDelegate(elements, Collection<E>::containsAll)

    override fun isEmpty(): Boolean = performOrDelegate(Collection<E>::isEmpty)

    override fun add(element: E): Boolean = performOrThrow(element, KObservableCollection<E>::add)

    override fun addAll(elements: Collection<E>): Boolean = performOrThrow(elements, KObservableCollection<E>::addAll)

    override fun clear() {
        performOrThrow(KObservableCollection<E>::clear)
    }

    override fun iterator(): MutableIterator<E> = value?.iterator() ?: EMPTY_MUTABLE_ITERATOR

    override fun remove(element: E): Boolean = performOrThrow(element, KObservableCollection<E>::remove)

    override fun removeAll(elements: Collection<E>): Boolean =
            performOrThrow(elements, KObservableCollection<E>::removeAll)

    override fun retainAll(elements: Collection<E>): Boolean =
            performOrThrow(elements, KObservableCollection<E>::retainAll)

    private fun <P, R> performOrThrow(aParameter: P, anAction: KObservableCollection<E>.(P) -> R): R {
        return value?.anAction(aParameter)
                ?: throw UnsupportedOperationException(ERROR_MESSAGE)
    }

    private fun <R> performOrThrow(anAction: KObservableCollection<E>.() -> R): R {
        return value?.anAction()
                ?: throw UnsupportedOperationException(ERROR_MESSAGE)
    }

    @Suppress("UNCHECKED_CAST")
    private fun <P, R> performOrDelegate(aParameter: P, anAction: Collection<E>.(P) -> R): R {
        val tempValue = value
        return if (tempValue == null) {
            anAction.invoke(EMPTY_LIST, aParameter)
        } else {
            anAction.invoke(tempValue as Collection<E>, aParameter)
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <R> performOrDelegate(anAction: Collection<E>.() -> R): R {
        val tempValue = value
        return if (tempValue == null) {
            anAction.invoke(EMPTY_LIST)
        } else {
            anAction.invoke(tempValue as Collection<E>)
        }
    }

    private companion object {
        private const val ERROR_MESSAGE =
                "This KObservableCollectionValue currently has no value and can not perform the operation"

        val EMPTY_ITERATOR = emptyList<Nothing>().iterator()

        val EMPTY_MUTABLE_ITERATOR = object : MutableIterator<Nothing> {
            override fun hasNext(): Boolean = EMPTY_ITERATOR.hasNext()

            override fun next(): Nothing = EMPTY_ITERATOR.next()

            override fun remove() = throw UnsupportedOperationException(ERROR_MESSAGE)
        }

        val EMPTY_LIST = emptyList<Nothing>()
    }
}

/**
 * Base class for all [KObservableListValue] implementations that implements all the methods of
 * an [MutableList] and delegates them to the wrapped value. If the wrapped value is null then
 * all methods behave as if applied to an immutable empty list. All methods of a mutable list
 * throw an exception in this case.
 */
abstract class BasicKObservableListValue<E, L : KObservableList<E>?> : KObservableObjectValue<L>(),
        KObservableListValue<E, L>,
        KListListener<E> {

    private val listeners = mutableListOf<Any>()

    override val size: Int
        get() = performOrDelegate(List<E>::size)

    override fun addListener(aListener: KListListener<E>) {
        listeners.add(aListener)
    }

    override fun addListener(aListener: KCollectionListener<E>) {
        listeners.add(aListener)
    }

    override fun removeListener(aListener: KListListener<E>) {
        listeners.remove(aListener)
    }

    override fun removeListener(aListener: KCollectionListener<E>) {
        listeners.remove(aListener)
    }

    override fun onAdd(aList: KObservableList<E>, anAddedList: Collection<E>) {
        performListeners(anAddedList, KListListener<E>::onAdd, KCollectionListener<E>::onAdd, listeners)
    }

    override fun onRemove(aList: KObservableList<E>, aRemovedList: Collection<E>) {
        performListeners(aRemovedList, KListListener<E>::onRemove, KCollectionListener<E>::onRemove, listeners)
    }

    override fun onMove(aList: KObservableList<E>, aPermutationList: Collection<Permutation<E>>) {
        performListeners(aPermutationList, KListListener<E>::onMove, listeners = listeners)
    }

    override fun onReplace(aList: KObservableList<E>, aReplacementList: Collection<Replacement<E>>) {
        performListeners(aReplacementList, KListListener<E>::onReplace, listeners = listeners)
    }

    override fun contains(element: E): Boolean = performOrDelegate(element, List<E>::contains)

    override fun containsAll(elements: Collection<E>): Boolean = performOrDelegate(elements, List<E>::containsAll)

    override fun isEmpty(): Boolean = performOrDelegate(List<E>::isEmpty)

    override fun get(index: Int): E = performOrDelegate(index, List<E>::get)

    override fun indexOf(element: E): Int = performOrDelegate(element, List<E>::indexOf)

    override fun lastIndexOf(element: E): Int = performOrDelegate(element, List<E>::lastIndexOf)

    override fun add(element: E): Boolean = performOrThrow(element, KObservableList<E>::add)

    override fun addAll(elements: Collection<E>): Boolean {
        return performOrThrow(elements, KObservableList<E>::addAll)
    }

    override fun clear() {
        performOrThrow(KObservableList<E>::clear)
    }

    override fun iterator(): MutableIterator<E> = value?.iterator() ?: EMPTY_MUTABLE_LIST_ITERATOR

    override fun remove(element: E): Boolean = performOrThrow(element, KObservableList<E>::remove)

    override fun removeAll(elements: Collection<E>): Boolean = performOrThrow(elements, KObservableList<E>::removeAll)

    override fun retainAll(elements: Collection<E>): Boolean = performOrThrow(elements, KObservableList<E>::retainAll)

    override fun add(index: Int, element: E) {
        performOrThrow(index, element, KObservableList<E>::add)
    }

    override fun addAll(index: Int, elements: Collection<E>): Boolean =
            performOrThrow(index, elements, KObservableList<E>::addAll)

    @Suppress("UNCHECKED_CAST")
    override fun listIterator(): MutableListIterator<E> =
            value?.listIterator() ?: EMPTY_MUTABLE_LIST_ITERATOR as MutableListIterator<E>

    @Suppress("UNCHECKED_CAST")
    override fun listIterator(index: Int): MutableListIterator<E> =
            value?.listIterator(index)
                    ?: if (index == 0) EMPTY_MUTABLE_LIST_ITERATOR as MutableListIterator<E> else throw IndexOutOfBoundsException()

    override fun removeAt(index: Int): E = performOrThrow(index, KObservableList<E>::removeAt)

    override fun set(index: Int, element: E): E = performOrThrow(index, element, KObservableList<E>::set)

    override fun subList(fromIndex: Int, toIndex: Int): MutableList<E> =
            performOrDelegate(fromIndex, toIndex, List<E>::subList) as MutableList<E>

    private fun <R> performOrThrow(anAction: KObservableList<E>.() -> R): R {
        return value?.anAction()
                ?: throw UnsupportedOperationException(ERROR_MESSAGE)
    }

    private fun <P, R> performOrThrow(aParameter: P, anAction: KObservableList<E>.(P) -> R): R {
        return value?.anAction(aParameter)
                ?: throw UnsupportedOperationException(ERROR_MESSAGE)
    }

    private fun <P, P1, R> performOrThrow(
            aParameter: P,
            aParameter1: P1,
            anAction: KObservableList<E>.(P, P1) -> R
    ): R {
        return value?.anAction(aParameter, aParameter1)
                ?: throw UnsupportedOperationException(ERROR_MESSAGE)
    }

    @Suppress("UNCHECKED_CAST")
    private fun <R> performOrDelegate(anAction: List<E>.() -> R): R {
        val tempValue = value
        return if (tempValue == null) {
            anAction.invoke(EMPTY_LIST)
        } else {
            anAction.invoke(tempValue as List<E>)
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <P, R> performOrDelegate(aParameter: P, anAction: List<E>.(P) -> R): R {
        val tempValue = value
        return if (tempValue == null) {
            anAction.invoke(EMPTY_LIST, aParameter)
        } else {
            anAction.invoke(tempValue as List<E>, aParameter)
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <P, P1, R> performOrDelegate(aParameter: P, aParameter1: P1, anAction: List<E>.(P, P1) -> R): R {
        val tempValue = value
        return if (tempValue == null) {
            anAction.invoke(EMPTY_LIST, aParameter, aParameter1)
        } else {
            anAction.invoke(tempValue as List<E>, aParameter, aParameter1)
        }
    }

    private companion object {
        private const val ERROR_MESSAGE =
                "This KObservableListValue currently has no value and can not perform the operation"

        val EMPTY_LIST_ITERATOR = emptyList<Nothing>().listIterator()

        val EMPTY_MUTABLE_LIST_ITERATOR = object : MutableListIterator<Nothing> {
            override fun hasNext(): Boolean = EMPTY_LIST_ITERATOR.hasNext()

            override fun next(): Nothing = EMPTY_LIST_ITERATOR.next()

            override fun remove() = throw UnsupportedOperationException(ERROR_MESSAGE)

            override fun hasPrevious(): Boolean = EMPTY_LIST_ITERATOR.hasPrevious()

            override fun nextIndex(): Int = EMPTY_LIST_ITERATOR.nextIndex()

            override fun previous(): Nothing = EMPTY_LIST_ITERATOR.previous()

            override fun previousIndex(): Int = EMPTY_LIST_ITERATOR.previousIndex()

            override fun add(element: Nothing) = throw UnsupportedOperationException(ERROR_MESSAGE)

            override fun set(element: Nothing) = throw UnsupportedOperationException(ERROR_MESSAGE)
        }

        val EMPTY_LIST = emptyList<Nothing>()
    }
}

/**
 * Base class for all [KObservableSetValue] implementations that implements all the methods of
 * an [MutableSet] and delegates them to the wrapped value. If the wrapped value is null then
 * all methods behave as if applied to an immutable empty set. All methods of a mutable set
 * throw an exception in this case.
 */
abstract class BasicKObservableSetValue<E, S : KObservableSet<E>?> :
        KObservableObjectValue<S>(),
        KObservableSetValue<E, S>,
        KSetListener<E> {

    private val listeners = mutableListOf<Any>()

    override val size: Int
        get() = performOrDelegate(Collection<E>::size)

    override fun onAdd(aSet: KObservableSet<E>, anAddedList: Collection<E>) {
        performListeners(aSet, KSetListener<E>::onAdd, KCollectionListener<E>::onAdd, listeners)
    }

    override fun onRemove(aSet: KObservableSet<E>, aRemovedList: Collection<E>) {
        performListeners(aSet, KSetListener<E>::onRemove, KCollectionListener<E>::onRemove, listeners)
    }

    override fun addListener(aListener: KCollectionListener<E>) {
        listeners.add(aListener)
    }

    override fun addListener(aListener: KSetListener<E>) {
        listeners.add(aListener)
    }

    override fun removeListener(aListener: KCollectionListener<E>) {
        listeners.remove(aListener)
    }

    override fun removeListener(aListener: KSetListener<E>) {
        listeners.remove(aListener)
    }

    override fun contains(element: E): Boolean = performOrDelegate(element, Set<E>::contains)

    override fun containsAll(elements: Collection<E>): Boolean = performOrDelegate(elements, Set<E>::containsAll)

    override fun isEmpty(): Boolean = performOrDelegate(Set<E>::isEmpty)

    override fun add(element: E): Boolean = performOrThrow(element, KObservableSet<E>::add)

    override fun addAll(elements: Collection<E>): Boolean = performOrThrow(elements, KObservableSet<E>::addAll)

    override fun clear() {
        performOrThrow(KObservableSet<E>::clear)
    }

    override fun iterator(): MutableIterator<E> = value?.iterator() ?: EMPTY_MUTABLE_ITERATOR

    override fun remove(element: E): Boolean = performOrThrow(element, KObservableSet<E>::remove)

    override fun removeAll(elements: Collection<E>): Boolean =
            performOrThrow(elements, KObservableSet<E>::removeAll)

    override fun retainAll(elements: Collection<E>): Boolean =
            performOrThrow(elements, KObservableSet<E>::retainAll)

    private fun <P, R> performOrThrow(aParameter: P, anAction: KObservableSet<E>.(P) -> R): R {
        return value?.anAction(aParameter)
                ?: throw UnsupportedOperationException(ERROR_MESSAGE)
    }

    private fun <R> performOrThrow(anAction: KObservableSet<E>.() -> R): R {
        return value?.anAction()
                ?: throw UnsupportedOperationException(ERROR_MESSAGE)
    }

    @Suppress("UNCHECKED_CAST")
    private fun <P, R> performOrDelegate(aParameter: P, anAction: Set<E>.(P) -> R): R {
        val tempValue = value
        return if (tempValue == null) {
            anAction.invoke(EMPTY_SET, aParameter)
        } else {
            anAction.invoke(tempValue as Set<E>, aParameter)
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <R> performOrDelegate(anAction: Set<E>.() -> R): R {
        val tempValue = value
        return if (tempValue == null) {
            anAction.invoke(EMPTY_SET)
        } else {
            anAction.invoke(tempValue as Set<E>)
        }
    }

    private companion object {
        private const val ERROR_MESSAGE =
                "This KObservableSetValue currently has no value and can not perform the operation"

        val EMPTY_ITERATOR = emptySet<Nothing>().iterator()

        val EMPTY_MUTABLE_ITERATOR = object : MutableIterator<Nothing> {
            override fun hasNext(): Boolean = EMPTY_ITERATOR.hasNext()

            override fun next(): Nothing = EMPTY_ITERATOR.next()

            override fun remove() = throw UnsupportedOperationException(ERROR_MESSAGE)
        }

        val EMPTY_SET = emptySet<Nothing>()
    }
}

/**
 * Base class for all [KObservableMapValue] implementations that implements all the methods of
 * an [MutableMap] and delegates them to the wrapped value. If the wrapped value is null then
 * all methods behave as if applied to an immutable empty map. All methods of a mutable map
 * throw an exception in this case.
 */
abstract class BasicKObservableMapValue<K, V, M : KObservableMap<K, V>?> :
        KObservableObjectValue<M>(),
        KObservableMapValue<K, V, M>,
        KMapListener<K, V> {

    private val listeners = mutableListOf<KMapListener<K, V>>()

    override fun addListener(aListener: KMapListener<K, V>) {
        listeners.add(aListener)
    }

    override fun onAdd(aMap: KObservableMap<K, V>, anAddedEntries: Collection<Pair<K, V>>) {
        listeners.forEach { it.onAdd(aMap, anAddedEntries) }
    }

    override fun onRemove(aMap: KObservableMap<K, V>, aRemovedEntries: Collection<Pair<K, V>>) {
        listeners.forEach { it.onRemove(aMap, aRemovedEntries) }
    }

    override fun onReplace(aMap: KObservableMap<K, V>, aReplacedEntries: Collection<KMapListener.Replacement<K, V>>) {
        listeners.forEach { it.onReplace(aMap, aReplacedEntries) }
    }

    override val size: Int
        get() = performOrDelegate(Map<K, V>::size)

    @Suppress("UNCHECKED_CAST")
    override val entries: MutableSet<MutableEntry<K, V>>
        get() = value?.entries ?: EMPTY_MUTABLE_SET as MutableSet<MutableEntry<K, V>>

    @Suppress("UNCHECKED_CAST")
    override val keys: KObservableSet<K>
        get() = value?.keys ?: EMPTY_MUTABLE_SET as KObservableSet<K>

    @Suppress("UNCHECKED_CAST")
    override val values: KObservableCollection<V>
        get() = value?.values ?: EMPTY_MUTABLE_COLLECTION as KObservableCollection<V>

    override fun removeListener(aListener: KMapListener<K, V>) {
        listeners.remove(aListener)
    }

    override fun containsKey(key: K): Boolean = performOrDelegate(key, Map<K, V>::containsKey)

    override fun containsValue(value: V): Boolean = performOrDelegate(value, Map<K, V>::containsValue)

    override fun get(key: K): V? = performOrDelegate(key, Map<K, V>::get)

    override fun isEmpty(): Boolean = performOrDelegate(Map<K, V>::isEmpty)

    override fun clear() {
        performOrThrow(KObservableMap<K, V>::clear)
    }

    override fun put(key: K, value: V): V? = performOrThrow(key, value, KObservableMap<K, V>::put)

    override fun putAll(from: Map<out K, V>) {
        performOrThrow(from, KObservableMap<K, V>::putAll)
    }

    override fun remove(key: K): V? = performOrThrow(key, KObservableMap<K, V>::remove)

    private fun <R> performOrThrow(anAction: KObservableMap<K, V>.() -> R): R {
        return value?.anAction()
                ?: throw UnsupportedOperationException(ERROR_MESSAGE)
    }

    private fun <P, R> performOrThrow(aParameter: P, anAction: KObservableMap<K, V>.(P) -> R): R {
        return value?.anAction(aParameter)
                ?: throw UnsupportedOperationException(ERROR_MESSAGE)
    }

    private fun <P, P1, R> performOrThrow(
            aParameter: P,
            aParameter1: P1,
            anAction: KObservableMap<K, V>.(P, P1) -> R
    ): R {
        return value?.anAction(aParameter, aParameter1)
                ?: throw UnsupportedOperationException(ERROR_MESSAGE)
    }

    @Suppress("UNCHECKED_CAST")
    private fun <R> performOrDelegate(anAction: Map<K, V>.() -> R): R {
        val tempValue = value
        return if (tempValue == null) {
            anAction.invoke(EMPTY_MAP as Map<K, V>)
        } else {
            anAction.invoke(tempValue as Map<K, V>)
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <P, R> performOrDelegate(aParameter: P, anAction: Map<K, V>.(P) -> R): R {
        val tempValue = value
        return if (tempValue == null) {
            anAction.invoke(EMPTY_MAP as Map<K, V>, aParameter)
        } else {
            anAction.invoke(tempValue as Map<K, V>, aParameter)
        }
    }

    private companion object {
        private const val ERROR_MESSAGE =
                "This KObservableMapValue currently has no value and can not perform the operation"

        val EMPTY_ITERATOR = emptyList<Nothing>().iterator()

        val EMPTY_MUTABLE_ITERATOR = object : MutableIterator<Nothing> {
            override fun hasNext(): Boolean = EMPTY_ITERATOR.hasNext()

            override fun next(): Nothing = EMPTY_ITERATOR.next()

            override fun remove() = throw UnsupportedOperationException(ERROR_MESSAGE)
        }

        val EMPTY_SET = emptySet<Nothing>()

        val EMPTY_MUTABLE_SET: MutableSet<Nothing> = object : KObservableSet<Nothing>, Set<Nothing> by EMPTY_SET {
            override fun add(element: Nothing): Boolean = throw UnsupportedOperationException(ERROR_MESSAGE)

            override fun iterator(): MutableIterator<Nothing> = EMPTY_MUTABLE_ITERATOR

            override fun addAll(elements: Collection<Nothing>): Boolean =
                    throw UnsupportedOperationException(ERROR_MESSAGE)

            override fun clear() = throw UnsupportedOperationException(ERROR_MESSAGE)

            override fun remove(element: Nothing): Boolean = throw UnsupportedOperationException(ERROR_MESSAGE)

            override fun removeAll(elements: Collection<Nothing>): Boolean =
                    throw UnsupportedOperationException(ERROR_MESSAGE)

            override fun retainAll(elements: Collection<Nothing>): Boolean =
                    throw UnsupportedOperationException(ERROR_MESSAGE)

            override fun addListener(aListener: KSetListener<Nothing>) =
                    throw UnsupportedOperationException(ERROR_MESSAGE)

            override fun addListener(aListener: KCollectionListener<Nothing>) =
                    throw UnsupportedOperationException(ERROR_MESSAGE)

            override fun removeListener(aListener: KSetListener<Nothing>) =
                    throw UnsupportedOperationException(ERROR_MESSAGE)

            override fun removeListener(aListener: KCollectionListener<Nothing>) =
                    throw UnsupportedOperationException(ERROR_MESSAGE)
        }

        val EMPTY_COLLECTION = emptyList<Nothing>()

        val EMPTY_MUTABLE_COLLECTION: MutableCollection<Nothing> =
                object : MutableCollection<Nothing>, KObservableCollection<Nothing>,
                        Collection<Nothing> by EMPTY_COLLECTION {
                    override fun add(element: Nothing): Boolean = throw UnsupportedOperationException(ERROR_MESSAGE)

                    override fun iterator(): MutableIterator<Nothing> = EMPTY_MUTABLE_ITERATOR

                    override fun addAll(elements: Collection<Nothing>): Boolean =
                            throw UnsupportedOperationException(ERROR_MESSAGE)

                    override fun clear() = throw UnsupportedOperationException(ERROR_MESSAGE)

                    override fun remove(element: Nothing): Boolean = throw UnsupportedOperationException(ERROR_MESSAGE)

                    override fun removeAll(elements: Collection<Nothing>): Boolean =
                            throw UnsupportedOperationException(ERROR_MESSAGE)

                    override fun retainAll(elements: Collection<Nothing>): Boolean =
                            throw UnsupportedOperationException(ERROR_MESSAGE)

                    override fun addListener(aListener: KCollectionListener<Nothing>) =
                            throw UnsupportedOperationException(ERROR_MESSAGE)

                    override fun removeListener(aListener: KCollectionListener<Nothing>) =
                            throw UnsupportedOperationException(ERROR_MESSAGE)
                }

        val EMPTY_MAP = emptyMap<Nothing, Nothing>()
    }
}