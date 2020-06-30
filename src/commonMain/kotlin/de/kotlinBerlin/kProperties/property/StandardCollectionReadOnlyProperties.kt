package de.kotlinBerlin.kProperties.property

import de.kotlinBerlin.kProperties.collection.KObservableCollection
import de.kotlinBerlin.kProperties.collection.KObservableList
import de.kotlinBerlin.kProperties.collection.KObservableMap
import de.kotlinBerlin.kProperties.collection.KObservableSet
import de.kotlinBerlin.kProperties.value.KObservableCollectionValue
import de.kotlinBerlin.kProperties.value.KObservableListValue
import de.kotlinBerlin.kProperties.value.KObservableMapValue
import de.kotlinBerlin.kProperties.value.KObservableSetValue

/** An [KReadOnlyProperty] wrapping a [KObservableCollection] value. */
interface KReadOnlyCollectionProperty<E, C : KObservableCollection<E>?> : KReadOnlyProperty<C>,
        KObservableCollectionValue<E, C>

/** An [KReadOnlyProperty] wrapping a [KObservableList] value. */
interface KReadOnlyListProperty<E, L : KObservableList<E>?> : KReadOnlyProperty<L>, KObservableListValue<E, L>

/** An [KReadOnlyProperty] wrapping a [KObservableSet] value. */
interface KReadOnlySetProperty<E, S : KObservableSet<E>?> : KReadOnlyProperty<S>, KObservableSetValue<E, S>

/** An [KReadOnlyProperty] wrapping a [KObservableMap] value. */
interface KReadOnlyMapProperty<K, V, M : KObservableMap<K, V>?> : KReadOnlyProperty<M>, KObservableMapValue<K, V, M>