@file:Suppress("unused")

package de.kotlinBerlin.kProperties.binding

import de.kotlinBerlin.kProperties.collection.KObservableCollection
import de.kotlinBerlin.kProperties.collection.KObservableList
import de.kotlinBerlin.kProperties.collection.KObservableMap
import de.kotlinBerlin.kProperties.collection.KObservableSet
import de.kotlinBerlin.kProperties.value.KObservableCollectionValue
import de.kotlinBerlin.kProperties.value.KObservableListValue
import de.kotlinBerlin.kProperties.value.KObservableMapValue
import de.kotlinBerlin.kProperties.value.KObservableSetValue

/** An [KBinding] wrapping a [Double] value. */
interface KCollectionBinding<E, C : KObservableCollection<E>?> : KBinding<C>, KObservableCollectionValue<E, C>

/** An [KBinding] wrapping a [Double] value. */
interface KListBinding<E, L : KObservableList<E>?> : KBinding<L>, KObservableListValue<E, L>

/** An [KBinding] wrapping a [Double] value. */
interface KSetBinding<E, S : KObservableSet<E>?> : KBinding<S>, KObservableSetValue<E, S>

/** An [KBinding] wrapping a [Double] value. */
interface KMapBinding<K, V, M : KObservableMap<K, V>?> : KBinding<M>, KObservableMapValue<K, V, M>

/** Creates a [KCollectionBinding] that uses the given function to calculate its value. */
fun <E, C : KObservableCollection<E>?> createCollectionBinding(func: () -> C): KCollectionBinding<E, C> =
        BasicKCollectionBinding(func)

/** Creates a [KListBinding] that uses the given function to calculate its value. */
fun <E, L : KObservableList<E>?> createListBinding(func: () -> L): KListBinding<E, L> =
        BasicKListBinding(func)

/** Creates a [KSetBinding] that uses the given function to calculate its value. */
fun <E, S : KObservableSet<E>?> createSetBinding(func: () -> S): KSetBinding<E, S> =
        BasicKSetBinding(func)

/** Creates a [KMapBinding] that uses the given function to calculate its value. */
fun <K, V, M : KObservableMap<K, V>?> createMapBinding(func: () -> M): KMapBinding<K, V, M> =
        BasicKMapBinding(func)
