package de.kotlinBerlin.kProperties.wrapper

import de.kotlinBerlin.kProperties.property.KProperty
import de.kotlinBerlin.kProperties.property.KReadOnlyProperty

/**
 * This interface defines a convenient way of defining a property that
 * is a [KProperty] internally but can be passed to others as a [KReadOnlyProperty].
 */
interface KWrapper<T> : KProperty<T> {

    /**
     * Returns the [KReadOnlyProperty].
     */
    val readOnlyProperty: KReadOnlyProperty<T>
}