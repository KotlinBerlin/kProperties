package de.kotlinBerlin.kProperties.property

import de.kotlinBerlin.kProperties.value.KObservableValue

/**
 * In addition to the functionality of a [KObservableValue] a property provides information on the context it
 * is used in. It provides the [bean] it is used in and a [name].
 */
interface KReadOnlyProperty<T> : KObservableValue<T> {

    /** The bean instance this [KReadOnlyProperty] is used on */
    val bean: Any?

    /** The name of the property */
    val name: String?
}