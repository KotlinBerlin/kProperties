@file:Suppress("unused")

package de.kotlinBerlin.kProperties.binding

import de.kotlinBerlin.kProperties.value.KObservableValue

//Map

/** Creates a new [KBinding] that observes [this] and represents its value mapped with [aMapper] */
fun <T, R> KObservableValue<T>.map(aMapper: T.() -> R): KBinding<R> =
        BasicUnaryKBinding(this) { aMapper.invoke(it.value) }

/** Creates a new [KBooleanBinding] that observes [this] and represents its value mapped with [aMapper] */
fun <T, B : Boolean?> KObservableValue<T>.mapToBoolean(aMapper: T.() -> B): KBooleanBinding<B> =
        object : BasicUnaryKBinding<T, B>(this, { aMapper.invoke(it.value) }), KBooleanBinding<B> {}

/** Creates a new [KStringBinding] that observes [this] and represents its value mapped with [aMapper] */
fun <T, S : String?> KObservableValue<T>.mapToString(aMapper: T.() -> S): KStringBinding<S> =
        object : BasicUnaryKBinding<T, S>(this, { aMapper.invoke(it.value) }), KStringBinding<S> {}

/** Creates a new [KNumberBinding] that observes [this] and represents its value mapped with [aMapper] */
fun <T, N : Number?> KObservableValue<T>.mapToNumber(aMapper: T.() -> N): KNumberBinding<N> =
        object : BasicUnaryKBinding<T, N>(this, { aMapper.invoke(it.value) }), KNumberBinding<N> {}

/** Creates a new [KDoubleBinding] that observes [this] and represents its value mapped with [aMapper] */
fun <T, D : Double?> KObservableValue<T>.mapToDouble(aMapper: T.() -> D): KDoubleBinding<D> =
        object : BasicUnaryKBinding<T, D>(this, { aMapper.invoke(it.value) }), KDoubleBinding<D> {}

/** Creates a new [KFloatBinding] that observes [this] and represents its value mapped with [aMapper] */
fun <T, F : Float?> KObservableValue<T>.mapToFloat(aMapper: T.() -> F): KFloatBinding<F> =
        object : BasicUnaryKBinding<T, F>(this, { aMapper.invoke(it.value) }), KFloatBinding<F> {}

/** Creates a new [KLongBinding] that observes [this] and represents its value mapped with [aMapper] */
fun <T, L : Long?> KObservableValue<T>.mapToLong(aMapper: T.() -> L): KLongBinding<L> =
        object : BasicUnaryKBinding<T, L>(this, { aMapper.invoke(it.value) }), KLongBinding<L> {}

/** Creates a new [KIntBinding] that observes [this] and represents its value mapped with [aMapper] */
fun <T, I : Int?> KObservableValue<T>.mapToInt(aMapper: T.() -> I): KIntBinding<I> =
        object : BasicUnaryKBinding<T, I>(this, { aMapper.invoke(it.value) }), KIntBinding<I> {}

/** Creates a new [KShortBinding] that observes [this] and represents its value mapped with [aMapper] */
fun <T, S : Short?> KObservableValue<T>.mapToShort(aMapper: T.() -> S): KShortBinding<S> =
        object : BasicUnaryKBinding<T, S>(this, { aMapper.invoke(it.value) }), KShortBinding<S> {}

/** Creates a new [KByteBinding] that observes [this] and represents its value mapped with [aMapper] */
fun <T, B : Byte?> KObservableValue<T>.mapToByte(aMapper: T.() -> B): KByteBinding<B> =
        object : BasicUnaryKBinding<T, B>(this, { aMapper.invoke(it.value) }), KByteBinding<B> {}