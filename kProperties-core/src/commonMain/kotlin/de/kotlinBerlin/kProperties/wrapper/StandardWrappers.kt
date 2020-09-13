@file:Suppress("unused")

package de.kotlinBerlin.kProperties.wrapper

import de.kotlinBerlin.kProperties.property.BasicKBooleanProperty
import de.kotlinBerlin.kProperties.property.BasicKStringProperty
import de.kotlinBerlin.kProperties.property.KReadOnlyBooleanProperty
import de.kotlinBerlin.kProperties.property.KReadOnlyStringProperty
import de.kotlinBerlin.kProperties.value.KObservableBooleanValue
import de.kotlinBerlin.kProperties.value.KObservableStringValue

/**
 * Basic [KWrapper] implementation for [Boolean] objects
 */
open class BasicKBooleanWrapper<B : Boolean?>(bean: Any, name: String, value: B) :
        BasicKBooleanProperty<B>(bean, name, value), KWrapper<B> {
    override val readOnlyProperty: KReadOnlyBooleanProperty<B> by lazy {
        object : KReadOnlyBooleanProperty<B>, KObservableBooleanValue<B> by this {
            override val bean: Any?
                get() = this@BasicKBooleanWrapper.bean
            override val name: String?
                get() = this@BasicKBooleanWrapper.name
        }
    }
}

/**
 * Basic [KWrapper] implementation for [String] objects
 */
open class BasicKStringWrapper<S : String?>(bean: Any? = null, name: String? = null, value: S) :
        BasicKStringProperty<S>(bean, name, value), KWrapper<S> {
    override val readOnlyProperty: KReadOnlyStringProperty<S> by lazy {
        object : KReadOnlyStringProperty<S>, KObservableStringValue<S> by this {
            override val bean: Any?
                get() = this@BasicKStringWrapper.bean
            override val name: String?
                get() = this@BasicKStringWrapper.name
        }
    }
}