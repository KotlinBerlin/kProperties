@file:Suppress("unused")

package de.kotlinBerlin.kProperties.wrapper

import de.kotlinBerlin.kProperties.collection.KObservableMutableCollection
import de.kotlinBerlin.kProperties.collection.KObservableMutableList
import de.kotlinBerlin.kProperties.collection.KObservableMutableMap
import de.kotlinBerlin.kProperties.collection.KObservableMutableSet
import de.kotlinBerlin.kProperties.property.*
import de.kotlinBerlin.kProperties.value.KObservableMutableCollectionValue
import de.kotlinBerlin.kProperties.value.KObservableMutableListValue
import de.kotlinBerlin.kProperties.value.KObservableMutableMapValue
import de.kotlinBerlin.kProperties.value.KObservableMutableSetValue

/**
 * Basic [KWrapper] implementation for [KObservableMutableCollection] objects
 */
open class BasicKMutableCollectionWrapper<E, C : KObservableMutableCollection<E>?>(
    bean: Any?, name: String?, aValue: C
) :
    BasicKMutableCollectionProperty<E, C>(bean, name, aValue), KWrapper<C> {
    override val readOnlyProperty: KReadOnlyMutableCollectionProperty<E, C> by lazy {
        object : KReadOnlyMutableCollectionProperty<E, C>, KObservableMutableCollectionValue<E, C> by this {
            override val bean: Any?
                get() = this@BasicKMutableCollectionWrapper.bean
            override val name: String?
                get() = this@BasicKMutableCollectionWrapper.name
        }
    }
}

/**
 * Basic [KWrapper] implementation for [KObservableMutableList] objects
 */
open class BasicKMutableListWrapper<E, L : KObservableMutableList<E>?>(bean: Any?, name: String?, aValue: L) :
    BasicKMutableListProperty<E, L>(bean, name, aValue), KWrapper<L> {
    override val readOnlyProperty: KReadOnlyMutableListProperty<E, L> by lazy {
        object : KReadOnlyMutableListProperty<E, L>, KObservableMutableListValue<E, L> by this {
            override val bean: Any?
                get() = this@BasicKMutableListWrapper.bean
            override val name: String?
                get() = this@BasicKMutableListWrapper.name
        }
    }
}

/**
 * Basic [KWrapper] implementation for [KObservableMutableSet] objects
 */
open class BasicKMutableSetWrapper<E, S : KObservableMutableSet<E>?>(bean: Any?, name: String?, aValue: S) :
    BasicKMutableSetProperty<E, S>(bean, name, aValue), KWrapper<S> {
    override val readOnlyProperty: KReadOnlyMutableSetProperty<E, S> by lazy {
        object : KReadOnlyMutableSetProperty<E, S>, KObservableMutableSetValue<E, S> by this {
            override val bean: Any?
                get() = this@BasicKMutableSetWrapper.bean
            override val name: String?
                get() = this@BasicKMutableSetWrapper.name
        }
    }
}

/**
 * Basic [KWrapper] implementation for [KObservableMutableMap] objects
 */
open class BasicKMutableMapWrapper<K, V, M : KObservableMutableMap<K, V>?>(bean: Any?, name: String?, aValue: M) :
    BasicKMutableMapProperty<K, V, M>(bean, name, aValue), KWrapper<M> {
    override val readOnlyProperty: KReadOnlyMutableMapProperty<K, V, M> by lazy {
        object : KReadOnlyMutableMapProperty<K, V, M>, KObservableMutableMapValue<K, V, M> by this {
            override val bean: Any?
                get() = this@BasicKMutableMapWrapper.bean
            override val name: String?
                get() = this@BasicKMutableMapWrapper.name
        }
    }
}
