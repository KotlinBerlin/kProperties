@file:Suppress("unused")

package de.kotlinBerlin.kProperties.constants

import de.kotlinBerlin.kProperties.value.*

/** An [KObservableValue] implementation for [Number] objects which value can not be changed. */
open class KNumberConstant<out N : Number?>(value: N) : KObjectConstant<N>(value), KObservableNumberValue<N>

/** An [KObservableValue] implementation for [Double] objects which value can not be changed. */
open class KDoubleConstant<out D : Double?>(value: D) : KNumberConstant<D>(value), KObservableDoubleValue<D>

/** An [KObservableValue] implementation for [Float] objects which value can not be changed. */
open class KFloatConstant<out F : Float?>(value: F) : KNumberConstant<F>(value), KObservableFloatValue<F>

/** An [KObservableValue] implementation for [Long] objects which value can not be changed. */
open class KLongConstant<out L : Long?>(value: L) : KNumberConstant<L>(value), KObservableLongValue<L>

/** An [KObservableValue] implementation for [Int] objects which value can not be changed. */
open class KIntConstant<out I : Int?>(value: I) : KNumberConstant<I>(value), KObservableIntValue<I>

/** An [KObservableValue] implementation for [Short] objects which value can not be changed. */
open class KShortConstant<out S : Short?>(value: S) : KNumberConstant<S>(value), KObservableShortValue<S>

/** An [KObservableValue] implementation for [Byte] objects which value can not be changed. */
open class KByteConstant<out B : Byte?>(value: B) : KNumberConstant<B>(value), KObservableByteValue<B>

/** Converts the [Number] instance to an [KNumberConstant] */
fun <N : Number?> N.toObservableConst(): KNumberConstant<N> = KNumberConstant(this)

/** Converts the [Double] instance to an [KDoubleConstant] */
fun <D : Double?> D.toObservableConst(): KDoubleConstant<D> = KDoubleConstant(this)

/** Converts the [Float] instance to an [KFloatConstant] */
fun <F : Float?> F.toObservableConst(): KFloatConstant<F> = KFloatConstant(this)

/** Converts the [Long] instance to an [KLongConstant] */
fun <L : Long?> L.toObservableConst(): KLongConstant<L> = KLongConstant(this)

/** Converts the [Int] instance to an [KIntConstant] */
fun <I : Int?> I.toObservableConst(): KIntConstant<I> = KIntConstant(this)

/** Converts the [Short] instance to an [KShortConstant] */
fun <S : Short?> S.toObservableConst(): KShortConstant<S> = KShortConstant(this)

/** Converts the [Byte] instance to an [KByteConstant] */
fun <B : Byte?> B.toObservableConst(): KByteConstant<B> = KByteConstant(this)
