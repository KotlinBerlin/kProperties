@file:Suppress("unused")

package de.kotlinBerlin.kProperties.wrapper

import de.kotlinBerlin.kProperties.collection.KObservableCollection
import de.kotlinBerlin.kProperties.collection.KObservableList
import de.kotlinBerlin.kProperties.collection.KObservableMap
import de.kotlinBerlin.kProperties.collection.KObservableSet
import de.kotlinBerlin.kProperties.property.*
import de.kotlinBerlin.kProperties.value.KObservableCollectionValue
import de.kotlinBerlin.kProperties.value.KObservableListValue
import de.kotlinBerlin.kProperties.value.KObservableMapValue
import de.kotlinBerlin.kProperties.value.KObservableSetValue

/**
 * Basic [KWrapper] implementation for [KObservableCollection] objects
 */
open class BasicKCollectionWrapper<E, C : KObservableCollection<E>?>(bean: Any?, name: String?, aValue: C) :
        BasicKCollectionProperty<E, C>(bean, name, aValue), KWrapper<C> {
    override val readOnlyProperty: KReadOnlyCollectionProperty<E, C> by lazy {
        object : KReadOnlyCollectionProperty<E, C>, KObservableCollectionValue<E, C> by this {
            override val bean: Any?
                get() = this@BasicKCollectionWrapper.bean
            override val name: String?
                get() = this@BasicKCollectionWrapper.name
        }
    }
}

/**
 * Basic [KWrapper] implementation for [KObservableList] objects
 */
open class BasicKListWrapper<E, L : KObservableList<E>?>(bean: Any?, name: String?, aValue: L) :
        BasicKListProperty<E, L>(bean, name, aValue), KWrapper<L> {
    override val readOnlyProperty: KReadOnlyListProperty<E, L> by lazy {
        object : KReadOnlyListProperty<E, L>, KObservableListValue<E, L> by this {
            override val bean: Any?
                get() = this@BasicKListWrapper.bean
            override val name: String?
                get() = this@BasicKListWrapper.name
        }
    }
}

/**
 * Basic [KWrapper] implementation for [KObservableSet] objects
 */
open class BasicKSetWrapper<E, S : KObservableSet<E>?>(bean: Any?, name: String?, aValue: S) :
        BasicKSetProperty<E, S>(bean, name, aValue), KWrapper<S> {
    override val readOnlyProperty: KReadOnlySetProperty<E, S> by lazy {
        object : KReadOnlySetProperty<E, S>, KObservableSetValue<E, S> by this {
            override val bean: Any?
                get() = this@BasicKSetWrapper.bean
            override val name: String?
                get() = this@BasicKSetWrapper.name
        }
    }
}

/**
 * Basic [KWrapper] implementation for [KObservableMap] objects
 */
open class BasicKMapWrapper<K, V, M : KObservableMap<K, V>?>(bean: Any?, name: String?, aValue: M) :
        BasicKMapProperty<K, V, M>(bean, name, aValue), KWrapper<M> {
    override val readOnlyProperty: KReadOnlyMapProperty<K, V, M> by lazy {
        object : KReadOnlyMapProperty<K, V, M>, KObservableMapValue<K, V, M> by this {
            override val bean: Any?
                get() = this@BasicKMapWrapper.bean
            override val name: String?
                get() = this@BasicKMapWrapper.name
        }
    }
}