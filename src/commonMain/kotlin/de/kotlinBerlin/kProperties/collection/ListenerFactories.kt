@file:Suppress("unused", "DuplicatedCode")

package de.kotlinBerlin.kProperties.collection

import de.kotlinBerlin.kProperties.collection.KListListener.Permutation
import de.kotlinBerlin.kProperties.collection.KListListener.Replacement

//Collection

/**
 * Creates a new [KCollectionListener] instance, that calls [aCallback] whenever the [KCollectionListener.onAdd] method is called.
 * The new listener is automatically added to this [KObservableCollection] instance and then returned.
 */
inline fun <E> KObservableCollection<E>.observeAdd(crossinline aCallback: (KObservableCollection<E>, Collection<E>) -> Unit): KCollectionListener<E> {
    val tempListener = object : KCollectionListener<E> {
        override fun onAdd(aCollection: KObservableCollection<E>, anAddedList: Collection<E>) {
            aCallback.invoke(aCollection, anAddedList)
        }
    }
    addListener(tempListener)
    return tempListener
}

/**
 * Creates a new [KCollectionListener] instance, that calls [aCallback] whenever the [KCollectionListener.onRemove] method is called.
 * The new listener is automatically added to this [KObservableCollection] instance and then returned.
 */
inline fun <E> KObservableCollection<E>.observeRemove(crossinline aCallback: (KObservableCollection<E>, Collection<E>) -> Unit): KCollectionListener<E> {
    val tempListener = object : KCollectionListener<E> {
        override fun onRemove(aCollection: KObservableCollection<E>, aRemovedList: Collection<E>) {
            aCallback.invoke(aCollection, aRemovedList)
        }
    }
    addListener(tempListener)
    return tempListener
}

/** Same as [observeAdd]. Use this method if you do not need the [KObservableCollection] in the callback. */
inline fun <E> KObservableCollection<E>.simpleObserveAdd(crossinline aCallback: (Collection<E>) -> Unit): KCollectionListener<E> =
        this.observeAdd { _, aColl -> aCallback.invoke(aColl) }

/** Same as [observeRemove]. Use this method if you do not need the [KObservableCollection] in the callback. */
inline fun <E> KObservableCollection<E>.simpleObserveRemove(crossinline aCallback: (Collection<E>) -> Unit): KCollectionListener<E> =
        this.observeRemove { _, aColl -> aCallback.invoke(aColl) }

//List

/**
 * Creates a new [KListListener] instance, that calls [aCallback] whenever the [KListListener.onAdd] method is called.
 * The new listener is automatically added to this [KObservableList] instance and then returned.
 */
inline fun <E> KObservableList<E>.observeListAdd(crossinline aCallback: (KObservableList<E>, Collection<E>) -> Unit): KListListener<E> {
    val tempListener = object : KListListener<E> {
        override fun onAdd(aList: KObservableList<E>, anAddedList: Collection<E>) {
            aCallback.invoke(aList, anAddedList)
        }
    }
    addListener(tempListener)
    return tempListener
}

/**
 * Creates a new [KListListener] instance, that calls [aCallback] whenever the [KListListener.onMove] method is called.
 * The new listener is automatically added to this [KObservableList] instance and then returned.
 */
inline fun <E> KObservableList<E>.observeListMove(crossinline aCallback: (KObservableList<E>, Collection<Permutation<E>>) -> Unit): KListListener<E> {
    val tempListener = object : KListListener<E> {
        override fun onMove(aList: KObservableList<E>, aPermutationList: Collection<Permutation<E>>) {
            aCallback.invoke(aList, aPermutationList)
        }
    }
    addListener(tempListener)
    return tempListener
}

/**
 * Creates a new [KListListener] instance, that calls [aCallback] whenever the [KListListener.onReplace] method is called.
 * The new listener is automatically added to this [KObservableList] instance and then returned.
 */
inline fun <E> KObservableList<E>.observeListReplace(crossinline aCallback: (KObservableCollection<E>, Collection<Replacement<E>>) -> Unit): KListListener<E> {
    val tempListener = object : KListListener<E> {
        override fun onReplace(aList: KObservableList<E>, aReplacementList: Collection<Replacement<E>>) {
            aCallback.invoke(aList, aReplacementList)
        }
    }
    addListener(tempListener)
    return tempListener
}

/**
 * Creates a new [KListListener] instance, that calls [aCallback] whenever the [KListListener.onRemove] method is called.
 * The new listener is automatically added to this [KObservableList] instance and then returned.
 */
inline fun <E> KObservableList<E>.observeListRemove(crossinline aCallback: (KObservableList<E>, Collection<E>) -> Unit): KListListener<E> {
    val tempListener = object : KListListener<E> {
        override fun onRemove(aList: KObservableList<E>, aRemovedList: Collection<E>) {
            aCallback.invoke(aList, aRemovedList)
        }
    }
    this.addListener(tempListener)
    return tempListener
}

/** Same as [observeListAdd]. Use this method if you do not need the [KObservableList] in the callback. */
inline fun <E> KObservableList<E>.simpleObserveListAdd(crossinline aCallback: (Collection<E>) -> Unit): KListListener<E> =
        this.observeListAdd { _, aList -> aCallback.invoke(aList) }

/** Same as [observeListMove]. Use this method if you do not need the [KObservableList] in the callback. */
inline fun <E> KObservableList<E>.simpleObserveListMove(crossinline aCallback: (Collection<Permutation<E>>) -> Unit): KListListener<E> =
        this.observeListMove { _, aList -> aCallback.invoke(aList) }

/** Same as [observeListReplace]. Use this method if you do not need the [KObservableList] in the callback. */
inline fun <E> KObservableList<E>.simpleObserveListReplace(crossinline aCallback: (Collection<Replacement<E>>) -> Unit): KListListener<E> =
        this.observeListReplace { _, aList -> aCallback.invoke(aList) }

/** Same as [observeListRemove]. Use this method if you do not need the [KObservableList] in the callback. */
inline fun <E> KObservableList<E>.simpleObserveListRemove(crossinline aCallback: (Collection<E>) -> Unit): KListListener<E> =
        this.observeListRemove { _, aList -> aCallback.invoke(aList) }

//Set

/**
 * Creates a new [KSetListener] instance, that calls [aCallback] whenever the [KSetListener.onAdd] method is called.
 * The new listener is automatically added to this [KObservableSet] instance and then returned.
 */
inline fun <E> KObservableSet<E>.observeSetAdd(crossinline aCallback: (KObservableSet<E>, Collection<E>) -> Unit): KSetListener<E> {
    val tempListener = object : KSetListener<E> {
        override fun onAdd(aSet: KObservableSet<E>, anAddedList: Collection<E>) {
            aCallback.invoke(aSet, anAddedList)
        }
    }
    addListener(tempListener)
    return tempListener
}

/**
 * Creates a new [KSetListener] instance, that calls [aCallback] whenever the [KSetListener.onRemove] method is called.
 * The new listener is automatically added to this [KObservableSet] instance and then returned.
 */
inline fun <E> KObservableSet<E>.observeSetRemove(crossinline aCallback: (KObservableSet<E>, Collection<E>) -> Unit): KSetListener<E> {
    val tempListener = object : KSetListener<E> {
        override fun onRemove(aSet: KObservableSet<E>, aRemovedList: Collection<E>) {
            aCallback.invoke(aSet, aRemovedList)
        }
    }
    addListener(tempListener)
    return tempListener
}

/** Same as [observeSetAdd]. Use this method if you do not need the [KObservableSet] in the callback. */
inline fun <E> KObservableSet<E>.simpleObserveSetAdd(crossinline aCallback: (Collection<E>) -> Unit): KSetListener<E> =
        this.observeSetAdd { _, aColl -> aCallback.invoke(aColl) }

/** Same as [observeSetRemove]. Use this method if you do not need the [KObservableSet] in the callback. */
inline fun <E> KObservableSet<E>.simpleObserveSetRemove(crossinline aCallback: (Collection<E>) -> Unit): KSetListener<E> =
        this.observeSetRemove { _, aColl -> aCallback.invoke(aColl) }

//Map

/**
 * Creates a new [KMapListener] instance, that calls [aCallback] whenever the [KMapListener.onAdd] method is called.
 * The new listener is automatically added to this [KObservableMap] instance and then returned.
 */
inline fun <K, V> KObservableMap<K, V>.observeMapAdd(crossinline aCallback: (KObservableMap<K, V>, Collection<Pair<K, V>>) -> Unit): KMapListener<K, V> {
    val tempListener = object : KMapListener<K, V> {
        override fun onAdd(aMap: KObservableMap<K, V>, anAddedEntries: Collection<Pair<K, V>>) {
            aCallback.invoke(aMap, anAddedEntries)
        }
    }
    addListener(tempListener)
    return tempListener
}

/**
 * Creates a new [KMapListener] instance, that calls [aCallback] whenever the [KMapListener.onReplace] method is called.
 * The new listener is automatically added to this [KObservableMap] instance and then returned.
 */
inline fun <K, V> KObservableMap<K, V>.observeMapReplace(crossinline aCallback: (KObservableMap<K, V>, Collection<KMapListener.Replacement<K, V>>) -> Unit): KMapListener<K, V> {
    val tempListener = object : KMapListener<K, V> {
        override fun onReplace(
                aMap: KObservableMap<K, V>,
                aReplacedEntries: Collection<KMapListener.Replacement<K, V>>
        ) {
            aCallback.invoke(aMap, aReplacedEntries)
        }
    }
    addListener(tempListener)
    return tempListener
}

/**
 * Creates a new [KMapListener] instance, that calls [aCallback] whenever the [KMapListener.onRemove] method is called.
 * The new listener is automatically added to this [KObservableMap] instance and then returned.
 */
inline fun <K, V> KObservableMap<K, V>.observeMapRemove(crossinline aCallback: (KObservableMap<K, V>, Collection<Pair<K, V>>) -> Unit): KMapListener<K, V> {
    val tempListener = object : KMapListener<K, V> {
        override fun onRemove(aMap: KObservableMap<K, V>, aRemovedEntries: Collection<Pair<K, V>>) {
            aCallback.invoke(aMap, aRemovedEntries)
        }
    }
    addListener(tempListener)
    return tempListener
}

/** Same as [observeMapAdd]. Use this method if you do not need the [KObservableMap] in the callback. */
inline fun <K, V> KObservableMap<K, V>.simpleObserveMapAdd(crossinline aCallback: (Collection<Pair<K, V>>) -> Unit): KMapListener<K, V> =
        this.observeMapAdd { _, aColl -> aCallback.invoke(aColl) }

/** Same as [observeMapReplace]. Use this method if you do not need the [KObservableMap] in the callback. */
inline fun <K, V> KObservableMap<K, V>.simpleObserveMapReplace(crossinline aCallback: (Collection<KMapListener.Replacement<K, V>>) -> Unit): KMapListener<K, V> =
        this.observeMapReplace { _, aColl -> aCallback.invoke(aColl) }

/** Same as [observeMapRemove]. Use this method if you do not need the [KObservableMap] in the callback. */
inline fun <K, V> KObservableMap<K, V>.simpleObserveMapRemove(crossinline aCallback: (Collection<Pair<K, V>>) -> Unit): KMapListener<K, V> =
        this.observeMapRemove { _, aColl -> aCallback.invoke(aColl) }