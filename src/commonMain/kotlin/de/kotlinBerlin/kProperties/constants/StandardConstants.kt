@file:Suppress("unused")

package de.kotlinBerlin.kProperties.constants

import de.kotlinBerlin.kProperties.value.KObservableBooleanValue
import de.kotlinBerlin.kProperties.value.KObservableStringValue

/**
 * An [de.kotlinBerlin.kProperties.value.KObservableValue] implementation for [Boolean] objects
 * which value can not be changed.
 */
open class KBooleanConstant<out B : Boolean?>(value: B) : KObjectConstant<B>(value), KObservableBooleanValue<B>

/**
 * An [de.kotlinBerlin.kProperties.value.KObservableValue] implementation for [String] objects
 * which value can not be changed.
 */
open class KStringConstant<out S : String?>(value: S) : KObjectConstant<S>(value), KObservableStringValue<S>

/** Converts the [Byte] instance to an [KObjectConstant] */
fun <T : Any?> T.toObservableConst(): KObjectConstant<T> = KObjectConstant(this)

/** Converts the [Byte] instance to an [KBooleanConstant] */
fun <B : Boolean?> B.toObservableConst(): KBooleanConstant<B> = KBooleanConstant(this)

/** Converts the [Byte] instance to an [KStringConstant] */
fun <S : String?> S.toObservableConst(): KStringConstant<S> = KStringConstant(this)