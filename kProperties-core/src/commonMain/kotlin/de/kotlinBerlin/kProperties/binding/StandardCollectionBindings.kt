@file:Suppress("unused")

package de.kotlinBerlin.kProperties.binding

import de.kotlinBerlin.kProperties.collection.*
import de.kotlinBerlin.kProperties.value.*

/** An [KBinding] wrapping a [KObservableCollection] value. */
interface KCollectionBinding<E, C : KObservableCollection<E>?> : KBinding<C>, KObservableCollectionValue<E, C>

/** An [KBinding] wrapping a [KObservableMutableCollection] value. */
interface KMutableCollectionBinding<E, C : KObservableMutableCollection<E>?> : KCollectionBinding<E, C>,
    KObservableMutableCollectionValue<E, C>

/** An [KBinding] wrapping a [KObservableList] value. */
interface KListBinding<E, L : KObservableList<E>?> : KBinding<L>, KObservableListValue<E, L>

/** An [KBinding] wrapping a [KObservableMutableList] value. */
interface KMutableListBinding<E, L : KObservableMutableList<E>?> : KListBinding<E, L>, KObservableMutableListValue<E, L>

/** An [KBinding] wrapping a [KObservableMutableSet] value. */
interface KMutableSetBinding<E, S : KObservableMutableSet<E>?> : KBinding<S>, KObservableMutableSetValue<E, S>

/** An [KBinding] wrapping a [KObservableMap] value. */
interface KMapBinding<K, V, M : KObservableMap<K, V>?> : KBinding<M>, KObservableMapValue<K, V, M>

/** An [KBinding] wrapping a [KObservableMutableMap] value. */
interface KMutableMapBinding<K, V, M : KObservableMutableMap<K, V>?> : KMapBinding<K, V, M>,
    KObservableMutableMapValue<K, V, M>

/** Creates a [KMutableCollectionBinding] that uses the given function to calculate its value. */
fun <E, C : KObservableMutableCollection<E>?> createMutableCollectionBinding(func: () -> C): KMutableCollectionBinding<E, C> =
    BasicKMutableCollectionBinding(func)

/** Creates a [KMutableListBinding] that uses the given function to calculate its value. */
fun <E, L : KObservableMutableList<E>?> createMutableListBinding(func: () -> L): KMutableListBinding<E, L> =
    BasicKMutableListBinding(func)

/** Creates a [KMutableSetBinding] that uses the given function to calculate its value. */
fun <E, S : KObservableMutableSet<E>?> createSetBinding(func: () -> S): KMutableSetBinding<E, S> =
    BasicKMutableSetBinding(func)

/** Creates a [KMutableMapBinding] that uses the given function to calculate its value. */
fun <K, V, M : KObservableMutableMap<K, V>?> createMutableMapBinding(func: () -> M): KMutableMapBinding<K, V, M> =
    BasicKMutableMapBinding(func)
