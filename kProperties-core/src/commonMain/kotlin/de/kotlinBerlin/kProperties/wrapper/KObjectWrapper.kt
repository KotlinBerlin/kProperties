package de.kotlinBerlin.kProperties.wrapper

import de.kotlinBerlin.kProperties.property.KObjectProperty
import de.kotlinBerlin.kProperties.property.KReadOnlyProperty
import de.kotlinBerlin.kProperties.value.KObservableValue

/**
 * Basic [KWrapper] implementation for [Any] objects
 */
@Suppress("unused")
open class KObjectWrapper<T>(bean: Any, name: String, value: T) : KObjectProperty<T>(bean, name, value), KWrapper<T> {

    override val readOnlyProperty: KReadOnlyProperty<T> by lazy {
        object : KReadOnlyProperty<T>, KObservableValue<T> by this {
            override val bean: Any?
                get() = this@KObjectWrapper.bean
            override val name: String?
                get() = this@KObjectWrapper.name
        }
    }
}