@file:Suppress("unused")

package de.kotlinBerlin.kProperties.constants

import de.kotlinBerlin.kProperties.collection.KObservableMutableCollection
import de.kotlinBerlin.kProperties.collection.KObservableMutableList
import de.kotlinBerlin.kProperties.collection.KObservableMutableMap
import de.kotlinBerlin.kProperties.collection.KObservableMutableSet
import de.kotlinBerlin.kProperties.value.BasicKObservableMutableCollectionValue
import de.kotlinBerlin.kProperties.value.BasicKObservableMutableListValue
import de.kotlinBerlin.kProperties.value.BasicKObservableMutableMapValue
import de.kotlinBerlin.kProperties.value.BasicKObservableMutableSetValue

/**
 * An [de.kotlinBerlin.kProperties.value.KObservableValue] implementation for [KObservableMutableCollection] objects
 * which value can not be changed.
 */
open class KMutableCollectionConstant<E, C : KObservableMutableCollection<E>?>(override val value: C) :
        BasicKObservableMutableCollectionValue<E, C>()

/**
 * An [de.kotlinBerlin.kProperties.value.KObservableValue] implementation for [KObservableMutableList] objects
 * which value can not be changed.
 */
open class KMutableListConstant<E, L : KObservableMutableList<E>?>(override val value: L) : BasicKObservableMutableListValue<E, L>()

/**
 * An [de.kotlinBerlin.kProperties.value.KObservableValue] implementation for [KObservableMutableSet] objects
 * which value can not be changed.
 */
open class KMutableSetConstant<E, S : KObservableMutableSet<E>?>(override val value: S) : BasicKObservableMutableSetValue<E, S>()

/**
 * An [de.kotlinBerlin.kProperties.value.KObservableValue] implementation for [KObservableMutableMap] objects
 * which value can not be changed.
 */
open class KMutableMapConstant<K, V, M : KObservableMutableMap<K, V>?>(override val value: M) : BasicKObservableMutableMapValue<K, V, M>()
