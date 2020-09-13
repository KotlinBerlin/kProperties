@file:Suppress("unused", "NOTHING_TO_INLINE")

package de.kotlinBerlin.kProperties.collection

/** Creates a new [KObservableList] instance. */
inline fun <E> observableList(): KObservableList<E> = observableMutableList<E>().toImmutable()

/** Creates a new [KObservableMutableList] instance. */
inline fun <E> observableMutableList(): KObservableMutableList<E> = BasicKObservableMutableList(arrayListOf())

inline fun <E> observableSet(): KObservableSet<E> = observableMutableSet<E>().toImmutable()

/** Creates a new [KObservableMutableSet] instance. */
inline fun <E> observableMutableSet(): KObservableMutableSet<E> = BasicKObservableMutableSet(hashSetOf())

inline fun <K, V> observableMap(): KObservableMap<K, V> = observableMutableMap<K, V>().toImmutable()

/** Creates a new [KObservableMutableMap] instance. */
inline fun <K, V> observableMutableMap(): KObservableMutableMap<K, V> = BasicKObservableMutableMap(hashMapOf())

/**
 * Creates a new [KObservableMutableList] instance that is backed by this [MutableList].
 *
 * Note that any changes made directly to this [MutableList] will not be observed.
 * Only the returned [KObservableMutableList] should be used to make changes.
 */
inline fun <E> MutableList<E>.toObservableMutableList(): KObservableMutableList<E> = BasicKObservableMutableList(this)

/**
 * Creates a new [KObservableMutableSet] instance that is backed by this [MutableSet].
 *
 * Note that any changes made directly to this [MutableSet] will not be observed.
 * Only the returned [KObservableMutableSet] should be used to make changes.
 */
inline fun <E> MutableSet<E>.toObservableMutableSet(): KObservableMutableSet<E> = BasicKObservableMutableSet(this)

/**
 * Creates a new [KObservableMutableMap] instance that is backed by this [MutableMap].
 *
 * Note that any changes made directly to this [MutableMap] will not be observed.
 * Only the returned [KObservableMutableMap] should be used to make changes.
 */
inline fun <K, V> MutableMap<K, V>.toObservableMutableMap(): KObservableMutableMap<K, V> =
    BasicKObservableMutableMap(this)

/**
 * Creates a new [KObservableMutableSet] that contains all the elements of this [Collection] if this
 * is an instance of [Set]. Otherwise creates a new [KObservableMutableList] that contains all the
 * elements of this [Collection].
 */
inline fun <reified E> Collection<E>.copyToObservableMutableCollection(): KObservableMutableCollection<E> {
    return if (this is Set<E>) mutableSetOf(*this.toTypedArray()).toObservableMutableSet()
    else this.copyToObservableMutableList()
}

/**
 * Creates a new [KObservableMutableList] that contains all the elements of this [Collection].
 */
inline fun <reified E> Collection<E>.copyToObservableMutableList(): KObservableMutableList<E> =
    mutableListOf(*this.toTypedArray()).toObservableMutableList()

/**
 * Creates a new [KObservableMutableSet] that contains all the elements of this [Collection].
 */
inline fun <reified E> Collection<E>.copyToObservableMutableSet(): KObservableMutableSet<E> =
    mutableSetOf(*this.toTypedArray()).toObservableMutableSet()

inline fun <E> KObservableMutableCollection<E>.toImmutable(): KObservableCollection<E> =
    object : KObservableCollection<E> by this {}

inline fun <E> KObservableMutableList<E>.toImmutable(): KObservableList<E> = object : KObservableList<E> by this {}
inline fun <E> KObservableMutableSet<E>.toImmutable(): KObservableSet<E> = object : KObservableSet<E> by this {}
inline fun <K, V> KObservableMutableMap<K, V>.toImmutable(): KObservableMap<K, V> =
    object : KObservableMap<K, V> by this {
        override val keys: KObservableSet<K> by lazy { this@toImmutable.keys.toImmutable() }
        override val values: KObservableCollection<V> by lazy { this@toImmutable.values.toImmutable() }
    }
