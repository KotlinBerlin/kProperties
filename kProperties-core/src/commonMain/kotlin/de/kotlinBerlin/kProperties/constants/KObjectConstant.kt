package de.kotlinBerlin.kProperties.constants

import de.kotlinBerlin.kProperties.value.KObservableObjectValue

/**
 * An [de.kotlinBerlin.kProperties.value.KObservableValue] implementation for [Any] objects
 * which value can not be changed.
 */
open class KObjectConstant<out T>(override val value: T) : KObservableObjectValue<T>()