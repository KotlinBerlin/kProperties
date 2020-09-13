package de.kotlinBerlin.kProperties.value

/** A common interface of all [KObservableValue] implementations that wrap a [Number]. */
interface KObservableNumberValue<out N : Number?> : KObservableValue<N>

/** An observable [Double] value. */
interface KObservableDoubleValue<out D : Double?> : KObservableNumberValue<D>

/** An observable [Float] value. */
interface KObservableFloatValue<out F : Float?> : KObservableNumberValue<F>

/** An observable [Long] value. */
interface KObservableLongValue<out L : Long?> : KObservableNumberValue<L>

/** An observable [Int] value. */
interface KObservableIntValue<out I : Int?> : KObservableNumberValue<I>

/** An observable [Short] value. */
interface KObservableShortValue<out S : Short?> : KObservableNumberValue<S>

/** An observable [Byte] value. */
interface KObservableByteValue<out B : Byte?> : KObservableNumberValue<B>