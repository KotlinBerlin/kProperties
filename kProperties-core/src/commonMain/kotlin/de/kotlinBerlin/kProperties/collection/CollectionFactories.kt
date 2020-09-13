@file:Suppress("unused")

package de.kotlinBerlin.kProperties.collection

/**
 * Creates a new [KObservableList] that contains all the elements of this [Collection].
 */
inline fun <reified E> Collection<E>.copyToObservableList(): KObservableList<E> =
        mutableListOf(*this.toTypedArray()).toObservableList()

/**
 * Creates a new [KObservableSet] that contains all the elements of this [Collection].
 */
inline fun <reified E> Collection<E>.copyToObservableSet(): KObservableSet<E> =
        mutableSetOf(*this.toTypedArray()).toObservableSet()

/**
 * Creates a new [KObservableSet] that contains all the elements of this [Collection] if this
 * is an instance of [Set]. Otherwise creates a new [KObservableList] that contains all the
 * elements of this [Collection].
 */
inline fun <reified E> Collection<E>.copyToObservableCollection(): KObservableCollection<E> {
    return if (this is Set<E>) mutableSetOf(*this.toTypedArray()).toObservableSet()
    else this.copyToObservableList()
}

/** Creates a new [KObservableList] instance. */
fun <E> observableList(): KObservableList<E> = BasicKObservableList(arrayListOf())

/**
 * Creates a new [KObservableList] instance that is backed by this [MutableList].
 *
 * Note that any changes made directly to this [MutableList] will not be observed.
 * Only the returned [KObservableList] should be used to make changes.
 */
fun <E> MutableList<E>.toObservableList(): KObservableList<E> = BasicKObservableList(this)

/** Creates a new [KObservableSet] instance. */
fun <E> observableSet(): KObservableSet<E> = BasicKObservableSet(hashSetOf())

/**
 * Creates a new [KObservableSet] instance that is backed by this [MutableSet].
 *
 * Note that any changes made directly to this [MutableSet] will not be observed.
 * Only the returned [KObservableSet] should be used to make changes.
 */
fun <E> MutableSet<E>.toObservableSet(): KObservableSet<E> = BasicKObservableSet(this)

/** Creates a new [KObservableMap] instance. */
fun <K, V> observableMap(): KObservableMap<K, V> = BasicKObservableMap(hashMapOf())

/**
 * Creates a new [KObservableMap] instance that is backed by this [MutableMap].
 *
 * Note that any changes made directly to this [MutableMap] will not be observed.
 * Only the returned [KObservableMap] should be used to make changes.
 */
fun <K, V> MutableMap<K, V>.toObservableMap(): KObservableMap<K, V> = BasicKObservableMap(this)