package de.kotlinBerlin.kProperties.property

import de.kotlinBerlin.kProperties.value.*

/** A common interface of all [KReadOnlyProperty] implementations that wrap a [Number]. */
interface KReadOnlyNumberProperty<N : Number?> : KReadOnlyProperty<N>, KObservableNumberValue<N>

/** An [KReadOnlyProperty] wrapping a [Double] value. */
interface KReadOnlyDoubleProperty<D : Double?> : KReadOnlyNumberProperty<D>, KObservableDoubleValue<D>

/** An [KReadOnlyProperty] wrapping a [Float] value. */
interface KReadOnlyFloatProperty<F : Float?> : KReadOnlyNumberProperty<F>, KObservableFloatValue<F>

/** An [KReadOnlyProperty] wrapping a [Long] value. */
interface KReadOnlyLongProperty<L : Long?> : KReadOnlyNumberProperty<L>, KObservableLongValue<L>

/** An [KReadOnlyProperty] wrapping a [Int] value. */
interface KReadOnlyIntProperty<I : Int?> : KReadOnlyNumberProperty<I>, KObservableIntValue<I>

/** An [KReadOnlyProperty] wrapping a [Short] value. */
interface KReadOnlyShortProperty<S : Short?> : KReadOnlyNumberProperty<S>, KObservableShortValue<S>

/** An [KReadOnlyProperty] wrapping a [Byte] value. */
interface KReadOnlyByteProperty<B : Byte?> : KReadOnlyNumberProperty<B>, KObservableByteValue<B>