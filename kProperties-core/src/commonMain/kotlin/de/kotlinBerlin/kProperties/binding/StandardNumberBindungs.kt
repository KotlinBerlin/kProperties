@file:Suppress("unused")

package de.kotlinBerlin.kProperties.binding

import de.kotlinBerlin.kProperties.value.*

/** A common interface of all [KBinding] implementations that wrap a [Number]. */
interface KNumberBinding<N : Number?> : KBinding<N>, KObservableNumberValue<N>

/** An [KBinding] wrapping a [Double] value. */
interface KDoubleBinding<D : Double?> : KNumberBinding<D>, KObservableDoubleValue<D>

/** An [KBinding] wrapping a [Float] value. */
interface KFloatBinding<F : Float?> : KNumberBinding<F>, KObservableFloatValue<F>

/** An [KBinding] wrapping a [Long] value. */
interface KLongBinding<L : Long?> : KNumberBinding<L>, KObservableLongValue<L>

/** An [KBinding] wrapping a [Int] value. */
interface KIntBinding<I : Int?> : KNumberBinding<I>, KObservableIntValue<I>

/** An [KBinding] wrapping a [Short] value. */
interface KShortBinding<S : Short?> : KNumberBinding<S>, KObservableShortValue<S>

/** An [KBinding] wrapping a [Byte] value. */
interface KByteBinding<B : Byte?> : KNumberBinding<B>, KObservableByteValue<B>

/** Creates a [KNumberBinding] that uses the given function to calculate its value. */
fun <N : Number?> createNumberBinding(func: () -> N): KNumberBinding<N> {
    return object : BasicKBinding<N>(func), KNumberBinding<N> {}
}

/** Creates a [KDoubleBinding] that uses the given function to calculate its value. */
fun <D : Double?> createDoubleBinding(func: () -> D): KDoubleBinding<D> {
    return object : BasicKBinding<D>(func), KDoubleBinding<D> {}
}

/** Creates a [KFloatBinding] that uses the given function to calculate its value. */
fun <F : Float?> createFloatBinding(func: () -> F): KFloatBinding<F> {
    return object : BasicKBinding<F>(func), KFloatBinding<F> {}
}

/** Creates a [KLongBinding] that uses the given function to calculate its value. */
fun <L : Long?> createLongBinding(func: () -> L): KLongBinding<L> {
    return object : BasicKBinding<L>(func), KLongBinding<L> {}
}

/** Creates a [KIntBinding] that uses the given function to calculate its value. */
fun <I : Int?> createIntBinding(func: () -> I): KIntBinding<I> {
    return object : BasicKBinding<I>(func), KIntBinding<I> {}
}

/** Creates a [KShortBinding] that uses the given function to calculate its value. */
fun <S : Short?> createShortBinding(func: () -> S): KShortBinding<S> {
    return object : BasicKBinding<S>(func), KShortBinding<S> {}
}

/** Creates a [KByteBinding] that uses the given function to calculate its value. */
fun <B : Byte?> createByteBinding(func: () -> B): KByteBinding<B> {
    return object : BasicKBinding<B>(func), KByteBinding<B> {}
}