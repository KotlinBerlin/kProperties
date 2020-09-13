package de.kotlinBerlin.kProperties.property

import de.kotlinBerlin.kProperties.collection.*
import de.kotlinBerlin.kProperties.value.*

/** An [KReadOnlyProperty] wrapping a [KObservableCollection] value. */
interface KReadOnlyCollectionProperty<E, C : KObservableCollection<E>?> : KReadOnlyProperty<C>,
    KObservableCollectionValue<E, C>

/** An [KReadOnlyProperty] wrapping a [KObservableMutableCollection] value. */
interface KReadOnlyMutableCollectionProperty<E, C : KObservableMutableCollection<E>?> :
    KReadOnlyCollectionProperty<E, C>, KObservableMutableCollectionValue<E, C>

/** An [KReadOnlyProperty] wrapping a [KObservableList] value. */
interface KReadOnlyListProperty<E, L : KObservableList<E>?> : KReadOnlyProperty<L>, KObservableListValue<E, L>

/** An [KReadOnlyProperty] wrapping a [KObservableMutableList] value. */
interface KReadOnlyMutableListProperty<E, L : KObservableMutableList<E>?> : KReadOnlyListProperty<E, L>,
    KObservableMutableListValue<E, L>

/** An [KReadOnlyProperty] wrapping a [KObservableMutableSet] value. */
interface KReadOnlyMutableSetProperty<E, S : KObservableMutableSet<E>?> : KReadOnlyProperty<S>, KObservableMutableSetValue<E, S>


/** An [KReadOnlyProperty] wrapping a [KObservableMap] value. */
interface KReadOnlyMapProperty<K, V, M : KObservableMap<K, V>?> : KReadOnlyProperty<M>, KObservableMapValue<K, V, M>

/** An [KReadOnlyProperty] wrapping a [KObservableMutableMap] value. */
interface KReadOnlyMutableMapProperty<K, V, M : KObservableMutableMap<K, V>?> : KReadOnlyMapProperty<K, V, M>,
    KObservableMutableMapValue<K, V, M>
