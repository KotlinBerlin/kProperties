@file:Suppress("unused")

package de.kotlinBerlin.kProperties.constants

import de.kotlinBerlin.kProperties.collection.KObservableCollection
import de.kotlinBerlin.kProperties.collection.KObservableList
import de.kotlinBerlin.kProperties.collection.KObservableMap
import de.kotlinBerlin.kProperties.collection.KObservableSet
import de.kotlinBerlin.kProperties.value.BasicKObservableCollectionValue
import de.kotlinBerlin.kProperties.value.BasicKObservableListValue
import de.kotlinBerlin.kProperties.value.BasicKObservableMapValue
import de.kotlinBerlin.kProperties.value.BasicKObservableSetValue

/**
 * An [de.kotlinBerlin.kProperties.value.KObservableValue] implementation for [KObservableCollection] objects
 * which value can not be changed.
 */
open class KCollectionConstant<E, C : KObservableCollection<E>?>(override val value: C) :
        BasicKObservableCollectionValue<E, C>()

/**
 * An [de.kotlinBerlin.kProperties.value.KObservableValue] implementation for [KObservableList] objects
 * which value can not be changed.
 */
open class KListConstant<E, L : KObservableList<E>?>(override val value: L) : BasicKObservableListValue<E, L>()

/**
 * An [de.kotlinBerlin.kProperties.value.KObservableValue] implementation for [KObservableSet] objects
 * which value can not be changed.
 */
open class KSetConstant<E, S : KObservableSet<E>?>(override val value: S) : BasicKObservableSetValue<E, S>()

/**
 * An [de.kotlinBerlin.kProperties.value.KObservableValue] implementation for [KObservableMap] objects
 * which value can not be changed.
 */
open class KMapConstant<K, V, M : KObservableMap<K, V>?>(override val value: M) : BasicKObservableMapValue<K, V, M>()