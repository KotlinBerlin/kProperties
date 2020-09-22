@file:Suppress("unused", "DuplicatedCode")

package de.kotlinBerlin.kProperties.collection

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

/**
 * Creates a new [KMutableCollectionListener] instance, that calls [aCallback] whenever the [KMutableCollectionListener.onAdd] method is called.
 * The new listener is automatically added to this [KObservableMutableCollection] instance and then returned.
 */
inline fun <E> KObservableMutableCollection<E>.observeMutableAdd(crossinline aCallback: (KObservableMutableCollection<E>, Collection<E>) -> Unit): KMutableCollectionListener<E> {
    val tempListener = object : KMutableCollectionListener<E> {
        override fun onAdd(aMutableCollection: KObservableMutableCollection<E>, anAddedList: Collection<E>) {
            aCallback.invoke(aMutableCollection, anAddedList)
        }
    }
    addListener(tempListener)
    return tempListener
}

/**
 * Creates a new [KMutableCollectionListener] instance, that calls [aCallback] whenever the [KMutableCollectionListener.onRemove] method is called.
 * The new listener is automatically added to this [KObservableMutableCollection] instance and then returned.
 */
inline fun <E> KObservableMutableCollection<E>.observeMutableRemove(crossinline aCallback: (KObservableMutableCollection<E>, Collection<E>) -> Unit): KMutableCollectionListener<E> {
    val tempListener = object : KMutableCollectionListener<E> {
        override fun onRemove(aMutableCollection: KObservableMutableCollection<E>, aRemovedList: Collection<E>) {
            aCallback.invoke(aMutableCollection, aRemovedList)
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

/** Same as [observeMutableAdd]. Use this method if you do not need the [KObservableMutableCollection] in the callback. */
inline fun <E> KObservableMutableCollection<E>.simpleObserveMutableAdd(crossinline aCallback: (Collection<E>) -> Unit): KMutableCollectionListener<E> =
    this.observeMutableAdd { _, aColl -> aCallback.invoke(aColl) }

/** Same as [observeMutableRemove]. Use this method if you do not need the [KObservableMutableCollection] in the callback. */
inline fun <E> KObservableMutableCollection<E>.simpleObserveMutableRemove(crossinline aCallback: (Collection<E>) -> Unit): KMutableCollectionListener<E> =
    this.observeMutableRemove { _, aColl -> aCallback.invoke(aColl) }

//List

/**
 * Creates a new [KListListener] instance, that calls [aCallback] whenever the [KListListener.onAdd] method is called.
 * The new listener is automatically added to this [KObservableList] instance and then returned.
 */
inline fun <E> KObservableList<E>.observeListAdd(crossinline aCallback: (KObservableList<E>, Collection<E>) -> Unit): KListListener<E> {
    val tempListener = object : KListListener<E> {
        override fun onAdd(aList: KObservableList<E>, aStartIndex: Int, anAddedList: Collection<E>) {
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
inline fun <E> KObservableList<E>.observeListMove(crossinline aCallback: (KObservableList<E>, Collection<ListPermutation<E>>) -> Unit): KListListener<E> {
    val tempListener = object : KListListener<E> {
        override fun onMove(aList: KObservableList<E>, aPermutationList: Collection<ListPermutation<E>>) {
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
inline fun <E> KObservableList<E>.observeListReplace(crossinline aCallback: (KObservableCollection<E>, Collection<ListReplacement<E>>) -> Unit): KListListener<E> {
    val tempListener = object : KListListener<E> {
        override fun onReplace(
            aList: KObservableList<E>,
            aReplacementList: Collection<ListReplacement<E>>
        ) {
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

/**
 * Creates a new [KMutableListListener] instance, that calls [aCallback] whenever the [KMutableListListener.onAdd] method is called.
 * The new listener is automatically added to this [KObservableMutableList] instance and then returned.
 */
inline fun <E> KObservableMutableList<E>.observeMutableListAdd(crossinline aCallback: (KObservableMutableList<E>, Collection<E>) -> Unit): KMutableListListener<E> {
    val tempListener = object : KMutableListListener<E> {
        override fun onAdd(aMutableList: KObservableMutableList<E>, aStartIndex: Int, anAddedList: Collection<E>) {
            aCallback.invoke(aMutableList, anAddedList)
        }
    }
    addListener(tempListener)
    return tempListener
}

/**
 * Creates a new [KMutableListListener] instance, that calls [aCallback] whenever the [KMutableListListener.onMove] method is called.
 * The new listener is automatically added to this [KObservableMutableList] instance and then returned.
 */
inline fun <E> KObservableMutableList<E>.observeMutableListMove(crossinline aCallback: (KObservableMutableList<E>, Collection<ListPermutation<E>>) -> Unit): KMutableListListener<E> {
    val tempListener = object : KMutableListListener<E> {
        override fun onMove(aMutableList: KObservableMutableList<E>, aPermutationList: Collection<ListPermutation<E>>) {
            aCallback.invoke(aMutableList, aPermutationList)
        }
    }
    addListener(tempListener)
    return tempListener
}

/**
 * Creates a new [KMutableListListener] instance, that calls [aCallback] whenever the [KMutableListListener.onReplace] method is called.
 * The new listener is automatically added to this [KObservableMutableList] instance and then returned.
 */
inline fun <E> KObservableMutableList<E>.observeMutableListReplace(crossinline aCallback: (KObservableMutableCollection<E>, Collection<ListReplacement<E>>) -> Unit): KMutableListListener<E> {
    val tempListener = object : KMutableListListener<E> {
        override fun onReplace(
            aMutableList: KObservableMutableList<E>,
            aReplacementList: Collection<ListReplacement<E>>
        ) {
            aCallback.invoke(aMutableList, aReplacementList)
        }
    }
    addListener(tempListener)
    return tempListener
}

/**
 * Creates a new [KMutableListListener] instance, that calls [aCallback] whenever the [KMutableListListener.onRemove] method is called.
 * The new listener is automatically added to this [KObservableMutableList] instance and then returned.
 */
inline fun <E> KObservableMutableList<E>.observeMutableListRemove(crossinline aCallback: (KObservableMutableList<E>, Collection<E>) -> Unit): KMutableListListener<E> {
    val tempListener = object : KMutableListListener<E> {
        override fun onRemove(aMutableList: KObservableMutableList<E>, aRemovedList: Collection<E>) {
            aCallback.invoke(aMutableList, aRemovedList)
        }
    }
    this.addListener(tempListener)
    return tempListener
}

/** Same as [observeListAdd]. Use this method if you do not need the [KObservableList] in the callback. */
inline fun <E> KObservableList<E>.simpleObserveListAdd(crossinline aCallback: (Collection<E>) -> Unit): KListListener<E> =
    this.observeListAdd { _, aList -> aCallback.invoke(aList) }

/** Same as [observeListMove]. Use this method if you do not need the [KObservableList] in the callback. */
inline fun <E> KObservableList<E>.simpleObserveListMove(crossinline aCallback: (Collection<ListPermutation<E>>) -> Unit): KListListener<E> =
    this.observeListMove { _, aList -> aCallback.invoke(aList) }

/** Same as [observeListReplace]. Use this method if you do not need the [KObservableList] in the callback. */
inline fun <E> KObservableList<E>.simpleObserveListReplace(crossinline aCallback: (Collection<ListReplacement<E>>) -> Unit): KListListener<E> =
    this.observeListReplace { _, aList -> aCallback.invoke(aList) }

/** Same as [observeListRemove]. Use this method if you do not need the [KObservableList] in the callback. */
inline fun <E> KObservableList<E>.simpleObserveListRemove(crossinline aCallback: (Collection<E>) -> Unit): KListListener<E> =
    this.observeListRemove { _, aList -> aCallback.invoke(aList) }

/** Same as [observeMutableListAdd]. Use this method if you do not need the [KObservableMutableList] in the callback. */
inline fun <E> KObservableMutableList<E>.simpleObserveMutableListAdd(crossinline aCallback: (Collection<E>) -> Unit): KMutableListListener<E> =
    this.observeMutableListAdd { _, aList -> aCallback.invoke(aList) }

/** Same as [observeMutableListMove]. Use this method if you do not need the [KObservableMutableList] in the callback. */
inline fun <E> KObservableMutableList<E>.simpleObserveMutableListMove(crossinline aCallback: (Collection<ListPermutation<E>>) -> Unit): KMutableListListener<E> =
    this.observeMutableListMove { _, aList -> aCallback.invoke(aList) }

/** Same as [observeMutableListReplace]. Use this method if you do not need the [KObservableMutableList] in the callback. */
inline fun <E> KObservableMutableList<E>.simpleObserveMutableListReplace(crossinline aCallback: (Collection<ListReplacement<E>>) -> Unit): KMutableListListener<E> =
    this.observeMutableListReplace { _, aList -> aCallback.invoke(aList) }

/** Same as [observeMutableListRemove]. Use this method if you do not need the [KObservableMutableList] in the callback. */
inline fun <E> KObservableMutableList<E>.simpleObserveMutableListRemove(crossinline aCallback: (Collection<E>) -> Unit): KMutableListListener<E> =
    this.observeMutableListRemove { _, aList -> aCallback.invoke(aList) }

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

/**
 * Creates a new [KMutableSetListener] instance, that calls [aCallback] whenever the [KMutableSetListener.onAdd] method is called.
 * The new listener is automatically added to this [KObservableMutableSet] instance and then returned.
 */
inline fun <E> KObservableMutableSet<E>.observeMutableSetAdd(crossinline aCallback: (KObservableMutableSet<E>, Collection<E>) -> Unit): KMutableSetListener<E> {
    val tempListener = object : KMutableSetListener<E> {
        override fun onAdd(aMutableSet: KObservableMutableSet<E>, anAddedList: Collection<E>) {
            aCallback.invoke(aMutableSet, anAddedList)
        }
    }
    addListener(tempListener)
    return tempListener
}

/**
 * Creates a new [KMutableSetListener] instance, that calls [aCallback] whenever the [KMutableSetListener.onRemove] method is called.
 * The new listener is automatically added to this [KObservableMutableSet] instance and then returned.
 */
inline fun <E> KObservableMutableSet<E>.observeMutableSetRemove(crossinline aCallback: (KObservableMutableSet<E>, Collection<E>) -> Unit): KMutableSetListener<E> {
    val tempListener = object : KMutableSetListener<E> {
        override fun onRemove(aMutableSet: KObservableMutableSet<E>, aRemovedList: Collection<E>) {
            aCallback.invoke(aMutableSet, aRemovedList)
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

/** Same as [observeMutableSetAdd]. Use this method if you do not need the [KObservableMutableSet] in the callback. */
inline fun <E> KObservableMutableSet<E>.simpleObserveMutableSetAdd(crossinline aCallback: (Collection<E>) -> Unit): KMutableSetListener<E> =
    this.observeMutableSetAdd { _, aColl -> aCallback.invoke(aColl) }

/** Same as [observeMutableSetRemove]. Use this method if you do not need the [KObservableMutableSet] in the callback. */
inline fun <E> KObservableMutableSet<E>.simpleObserveMutableSetRemove(crossinline aCallback: (Collection<E>) -> Unit): KMutableSetListener<E> =
    this.observeMutableSetRemove { _, aColl -> aCallback.invoke(aColl) }

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
inline fun <K, V> KObservableMap<K, V>.observeMapReplace(crossinline aCallback: (KObservableMap<K, V>, Collection<MapReplacement<K, V>>) -> Unit): KMapListener<K, V> {
    val tempListener = object : KMapListener<K, V> {
        override fun onReplace(
            aMap: KObservableMap<K, V>,
            aReplacedEntries: Collection<MapReplacement<K, V>>
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

/**
 * Creates a new [KMutableMapListener] instance, that calls [aCallback] whenever the [KMutableMapListener.onAdd] method is called.
 * The new listener is automatically added to this [KObservableMutableMap] instance and then returned.
 */
inline fun <K, V> KObservableMutableMap<K, V>.observeMutableMapAdd(crossinline aCallback: (KObservableMutableMap<K, V>, Collection<Pair<K, V>>) -> Unit): KMutableMapListener<K, V> {
    val tempListener = object : KMutableMapListener<K, V> {
        override fun onAdd(aMutableMap: KObservableMutableMap<K, V>, anAddedEntries: Collection<Pair<K, V>>) {
            aCallback.invoke(aMutableMap, anAddedEntries)
        }
    }
    addListener(tempListener)
    return tempListener
}

/**
 * Creates a new [KMutableMapListener] instance, that calls [aCallback] whenever the [KMutableMapListener.onReplace] method is called.
 * The new listener is automatically added to this [KObservableMutableMap] instance and then returned.
 */
inline fun <K, V> KObservableMutableMap<K, V>.observeMutableMapReplace(crossinline aCallback: (KObservableMutableMap<K, V>, Collection<MapReplacement<K, V>>) -> Unit): KMutableMapListener<K, V> {
    val tempListener = object : KMutableMapListener<K, V> {
        override fun onReplace(
            aMutableMap: KObservableMutableMap<K, V>,
            aReplacedEntries: Collection<MapReplacement<K, V>>
        ) {
            aCallback.invoke(aMutableMap, aReplacedEntries)
        }
    }
    addListener(tempListener)
    return tempListener
}

/**
 * Creates a new [KMutableMapListener] instance, that calls [aCallback] whenever the [KMutableMapListener.onRemove] method is called.
 * The new listener is automatically added to this [KObservableMutableMap] instance and then returned.
 */
inline fun <K, V> KObservableMutableMap<K, V>.observeMutableMapRemove(crossinline aCallback: (KObservableMutableMap<K, V>, Collection<Pair<K, V>>) -> Unit): KMutableMapListener<K, V> {
    val tempListener = object : KMutableMapListener<K, V> {
        override fun onRemove(aMutableMap: KObservableMutableMap<K, V>, aRemovedEntries: Collection<Pair<K, V>>) {
            aCallback.invoke(aMutableMap, aRemovedEntries)
        }
    }
    addListener(tempListener)
    return tempListener
}

/** Same as [observeMapAdd]. Use this method if you do not need the [KObservableMap] in the callback. */
inline fun <K, V> KObservableMap<K, V>.simpleObserveMapAdd(crossinline aCallback: (Collection<Pair<K, V>>) -> Unit): KMapListener<K, V> =
    this.observeMapAdd { _, aColl -> aCallback.invoke(aColl) }

/** Same as [observeMapReplace]. Use this method if you do not need the [KObservableMap] in the callback. */
inline fun <K, V> KObservableMap<K, V>.simpleObserveMapReplace(
    crossinline aCallback: (Collection<MapReplacement<K, V>>) -> Unit
): KMapListener<K, V> = this.observeMapReplace { _, aColl -> aCallback.invoke(aColl) }

/** Same as [observeMapRemove]. Use this method if you do not need the [KObservableMap] in the callback. */
inline fun <K, V> KObservableMap<K, V>.simpleObserveMapRemove(crossinline aCallback: (Collection<Pair<K, V>>) -> Unit): KMapListener<K, V> =
    this.observeMapRemove { _, aColl -> aCallback.invoke(aColl) }

/** Same as [observeMutableMapAdd]. Use this method if you do not need the [KObservableMutableMap] in the callback. */
inline fun <K, V> KObservableMutableMap<K, V>.simpleObserveMutableMapAdd(crossinline aCallback: (Collection<Pair<K, V>>) -> Unit): KMutableMapListener<K, V> =
    this.observeMutableMapAdd { _, aColl -> aCallback.invoke(aColl) }

/** Same as [observeMutableMapReplace]. Use this method if you do not need the [KObservableMutableMap] in the callback. */
inline fun <K, V> KObservableMutableMap<K, V>.simpleObserveMutableMapReplace(
    crossinline aCallback: (Collection<MapReplacement<K, V>>) -> Unit
): KMutableMapListener<K, V> = this.observeMutableMapReplace { _, aColl -> aCallback.invoke(aColl) }

/** Same as [observeMutableMapRemove]. Use this method if you do not need the [KObservableMutableMap] in the callback. */
inline fun <K, V> KObservableMutableMap<K, V>.simpleObserveMutableMapRemove(crossinline aCallback: (Collection<Pair<K, V>>) -> Unit): KMutableMapListener<K, V> =
    this.observeMutableMapRemove { _, aColl -> aCallback.invoke(aColl) }
