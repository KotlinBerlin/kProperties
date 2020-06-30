@file:Suppress("unused")

package de.kotlinBerlin.kProperties.binding

import de.kotlinBerlin.kProperties.KInvalidationListener
import de.kotlinBerlin.kProperties.KObservable
import de.kotlinBerlin.kProperties.WeakKInvalidationListener
import de.kotlinBerlin.kProperties.collection.*
import de.kotlinBerlin.kProperties.constants.toObservableConst
import de.kotlinBerlin.kProperties.value.KObservableValue

/** Returns a [KCollectionBinding] which observes this [KObservableValue] for changes. */
fun <E, C : KObservableCollection<E>?> KObservableValue<C>.toCollectionBinding(): KCollectionBinding<E, C> {
    val tempFunc: () -> C = { this.value }
    return object : BasicKCollectionBinding<E, C>(tempFunc) {

        private val listener: KInvalidationListener = KInvalidationListener { invalidate() }
        private val weakListener: WeakKInvalidationListener = WeakKInvalidationListener(listener)

        init {
            this@toCollectionBinding.addListener(weakListener)
        }

        override fun dispose() {
            this@toCollectionBinding.removeListener(weakListener)
        }
    }
}

/** Returns a [KListBinding] which observes this [KObservableValue] for changes. */
fun <E, L : KObservableList<E>?> KObservableValue<L>.toListBinding(): KListBinding<E, L> {
    val tempFunc: () -> L = { this.value }
    return object : BasicKListBinding<E, L>(tempFunc) {
        private val listener: KInvalidationListener = KInvalidationListener { invalidate() }
        private val weakListener: WeakKInvalidationListener = WeakKInvalidationListener(listener)

        init {
            this@toListBinding.addListener(weakListener)
        }

        override fun dispose() {
            this@toListBinding.removeListener(weakListener)
        }
    }
}

/** Returns a [KSetBinding] which observes this [KObservableValue] for changes. */
fun <E, S : KObservableSet<E>?> KObservableValue<S>.toSetBinding(): KSetBinding<E, S> {
    val tempFunc: () -> S = { this.value }
    return object : BasicKSetBinding<E, S>(tempFunc) {
        private val listener: KInvalidationListener = KInvalidationListener { invalidate() }
        private val weakListener: WeakKInvalidationListener = WeakKInvalidationListener(listener)

        init {
            this@toSetBinding.addListener(weakListener)
        }

        override fun dispose() {
            this@toSetBinding.removeListener(weakListener)
        }
    }
}

/**
 * Creates a [KBinding] that observes [aKeyObservable] and [this] for changes and updates its value to match the value
 * that is associated with the current value of [aKeyObservable] in the [KObservableMap]
 */
fun <K, V> KObservableMap<K, V>.observeValueAt(aKeyObservable: KObservableValue<K>): KBinding<V?> {
    return object : KObjectBinding<V?>() {

        private val listener = KInvalidationListener { invalidate() }
        private val weakListener = WeakKInvalidationListener(listener)

        private val mapListener = object : KMapListener<K, V> {
            override fun onReplace(
                    aMap: KObservableMap<K, V>,
                    aReplacedEntries: Collection<KMapListener.Replacement<K, V>>
            ) {
                if (aReplacedEntries.any { it.key == aKeyObservable.value }) invalidate()
            }

            override fun onAdd(aMap: KObservableMap<K, V>, anAddedEntries: Collection<Pair<K, V>>) {
                if (anAddedEntries.any { it.first == aKeyObservable.value }) invalidate()
            }

            override fun onRemove(aMap: KObservableMap<K, V>, aRemovedEntries: Collection<Pair<K, V>>) {
                if (aRemovedEntries.any { it.first == aKeyObservable.value }) invalidate()
            }
        }
        private val weakMapListener = WeakKMapListener(mapListener)

        init {
            this@observeValueAt.addListener(weakMapListener)
            aKeyObservable.addListener(weakListener)
            if (this@observeValueAt is KObservable) {
                this@observeValueAt.addListener(weakListener)
            }
        }

        override fun computeValue(): V? {
            return this@observeValueAt[aKeyObservable.value]
        }

        override fun dispose() {
            this@observeValueAt.removeListener(weakMapListener)
            aKeyObservable.removeListener(weakListener)
            if (this@observeValueAt is KObservable) {
                this@observeValueAt.removeListener(weakListener)
            }
        }
    }
}

/**
 * Creates a [KBinding] that observes [this] for changes and updates its value to match the value
 * that is associated with [aKey] in the [KObservableMap]
 */
fun <K, V> KObservableMap<K, V>.observeValueAt(aKey: K): KBinding<V?> = this.observeValueAt(aKey.toObservableConst())

/**
 * Creates a [KBinding] that observes [anIndexObservable] and [this] for changes and updates its value to match the value
 * that is associated with the current index of [anIndexObservable] in the [KObservableList]
 */
fun <E, L : KObservableList<E>> KObservableList<E>.observeValueAt(anIndexObservable: KObservableValue<Int>): KBinding<E> {
    return object : KObjectBinding<E>() {

        private val listener = KInvalidationListener { invalidate() }
        private val weakListener = WeakKInvalidationListener(listener)

        private val listListener = object : KListListener<E> {

            override fun onMove(aList: KObservableList<E>, aPermutationList: Collection<KListListener.Permutation<E>>) {
                if (aPermutationList.isEmpty()) return
                val tempIndexList = aPermutationList.flatMap { listOf(it.newIndex, it.oldIndex) }
                val tempMax = tempIndexList.max() ?: aList.size
                val tempMin = tempIndexList.min() ?: 0
                val tempIndex = anIndexObservable.value
                if (tempIndex in tempMin..tempMax) invalidate()
            }

            override fun onReplace(
                    aList: KObservableList<E>,
                    aReplacementList: Collection<KListListener.Replacement<E>>
            ) {
                if (aReplacementList.any { it.index == anIndexObservable.value }) invalidate()
            }
        }
        private val weakListListener = WeakKListListener(listListener)

        init {
            this@observeValueAt.addListener(weakListListener)
            anIndexObservable.addListener(weakListener)
            if (this@observeValueAt is KObservable) {
                this@observeValueAt.addListener(weakListener)
            }
        }

        override fun computeValue(): E {
            return this@observeValueAt[anIndexObservable.value]
        }

        override fun dispose() {
            this@observeValueAt.removeListener(weakListListener)
            anIndexObservable.removeListener(weakListener)
            if (this@observeValueAt is KObservable) {
                this@observeValueAt.removeListener(weakListener)
            }
        }
    }
}

/**
 * Creates a [KBinding] that observes [this] for changes and updates its value to match the value
 * that is associated with [anIndex] in the [KObservableList]
 */
fun <E> KObservableList<E>.observeValueAt(anIndex: Int): KBinding<E> = this.observeValueAt(anIndex.toObservableConst())