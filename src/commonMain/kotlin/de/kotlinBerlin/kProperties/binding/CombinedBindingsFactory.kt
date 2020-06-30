@file:Suppress("unused")

package de.kotlinBerlin.kProperties.binding

import de.kotlinBerlin.kProperties.constants.toObservableConst
import de.kotlinBerlin.kProperties.value.KObservableValue

/**
 * Creates a new [KBinding] which observes [this] as well as all of [anObservablesList] and combines their values
 * using [aMapper] function.
 */
fun <T> KObservableValue<T>.combineObservables(
        vararg anObservablesList: KObservableValue<T>,
        aMapper: T.(T) -> T
): KBinding<T> =
        ComplexKBinding(this, *anObservablesList, func = { tempObservables ->
            tempObservables.map { it.value }.reduce(aMapper)
        })

/** Creates a new [KBinding] which observes [this] and combines its value with [aValue] using [aMapper] function. */
fun <T, T1, R> KObservableValue<T>.combineValue(aValue: T1, aMapper: T.(T1) -> R): KBinding<R> =
        this.combine(aValue.toObservableConst(), aMapper)

/** Creates a new [KBinding] which observes [this] and [anObservable] and combines their values using [aMapper] function. */
fun <T, T1, R> KObservableValue<T>.combine(anObservable: KObservableValue<T1>, aMapper: T.(T1) -> R): KBinding<R> =
        ComplexBinaryKBinding(this, anObservable) { o1, o2 -> aMapper.invoke(o1.value, o2.value) }

/** Creates a new [KBooleanBinding] which observes [this] and combines its value with [aValue] using [aMapper] function. */
fun <T, T1, B : Boolean?> KObservableValue<T>.combineValueToBoolean(
        aValue: T1,
        aMapper: T.(T1) -> B
): KBooleanBinding<B> = this.combineToBoolean(aValue.toObservableConst(), aMapper)

/** Creates a new [KBooleanBinding] which observes [this] and [anObservable] and combines their values using [aMapper] function. */
fun <T, T1, B : Boolean?> KObservableValue<T>.combineToBoolean(
        anObservable: KObservableValue<T1>,
        aMapper: T.(T1) -> B
): KBooleanBinding<B> = object :
        ComplexBinaryKBinding<T, T1, B>(this, anObservable, func = { o1, o2 -> aMapper.invoke(o1.value, o2.value) }),
        KBooleanBinding<B> {}

/** Creates a new [KStringBinding] which observes [this] and combines its value with [aValue] using [aMapper] function. */
fun <T, T1, S : String?> KObservableValue<T>.combineValueToString(aValue: T1, aMapper: T.(T1) -> S): KStringBinding<S> =
        this.combineToString(aValue.toObservableConst(), aMapper)

/** Creates a new [KStringBinding] which observes [this] and [anObservable] and combines their values using [aMapper] function. */
fun <T, T1, S : String?> KObservableValue<T>.combineToString(
        anObservable: KObservableValue<T1>,
        aMapper: T.(T1) -> S
): KStringBinding<S> = object :
        ComplexBinaryKBinding<T, T1, S>(this, anObservable, func = { o1, o2 -> aMapper.invoke(o1.value, o2.value) }),
        KStringBinding<S> {}

/** Creates a new [KNumberBinding] which observes [this] and combines its value with [aValue] using [aMapper] function. */
fun <T, T1, N : Number?> KObservableValue<T>.combineValueToNumber(aValue: T1, aMapper: T.(T1) -> N): KNumberBinding<N> =
        this.combineToNumber(aValue.toObservableConst(), aMapper)

/** Creates a new [KNumberBinding] which observes [this] and [anObservable] and combines their values using [aMapper] function. */
fun <T, T1, N : Number?> KObservableValue<T>.combineToNumber(
        anObservable: KObservableValue<T1>,
        aMapper: T.(T1) -> N
): KNumberBinding<N> = object :
        ComplexBinaryKBinding<T, T1, N>(this, anObservable, func = { o1, o2 -> aMapper.invoke(o1.value, o2.value) }),
        KNumberBinding<N> {}

/** Creates a new [KDoubleBinding] which observes [this] and combines its value with [aValue] using [aMapper] function. */
fun <T, T1, D : Double?> KObservableValue<T>.combineValueToDouble(aValue: T1, aMapper: T.(T1) -> D): KDoubleBinding<D> =
        this.combineToDouble(aValue.toObservableConst(), aMapper)

/** Creates a new [KBooleanBinding] which observes [this] and [anObservable] and combines their values using [aMapper] function. */
fun <T, T1, D : Double?> KObservableValue<T>.combineToDouble(
        anObservable: KObservableValue<T1>,
        aMapper: T.(T1) -> D
): KDoubleBinding<D> = object :
        ComplexBinaryKBinding<T, T1, D>(this, anObservable, func = { o1, o2 -> aMapper.invoke(o1.value, o2.value) }),
        KDoubleBinding<D> {}

/** Creates a new [KFloatBinding] which observes [this] and combines its value with [aValue] using [aMapper] function. */
fun <T, T1, F : Float?> KObservableValue<T>.combineValueToFloat(aValue: T1, aMapper: T.(T1) -> F): KFloatBinding<F> =
        this.combineToFloat(aValue.toObservableConst(), aMapper)

/** Creates a new [KFloatBinding] which observes [this] and [anObservable] and combines their values using [aMapper] function. */
fun <T, T1, F : Float?> KObservableValue<T>.combineToFloat(
        anObservable: KObservableValue<T1>,
        aMapper: T.(T1) -> F
): KFloatBinding<F> = object :
        ComplexBinaryKBinding<T, T1, F>(this, anObservable, func = { o1, o2 -> aMapper.invoke(o1.value, o2.value) }),
        KFloatBinding<F> {}

/** Creates a new [KLongBinding] which observes [this] and combines its value with [aValue] using [aMapper] function. */
fun <T, T1, L : Long?> KObservableValue<T>.combineValueToLong(aValue: T1, aMapper: T.(T1) -> L): KLongBinding<L> =
        this.combineToLong(aValue.toObservableConst(), aMapper)

/** Creates a new [KLongBinding] which observes [this] and [anObservable] and combines their values using [aMapper] function. */
fun <T, T1, L : Long?> KObservableValue<T>.combineToLong(
        anObservable: KObservableValue<T1>,
        aMapper: T.(T1) -> L
): KLongBinding<L> = object :
        ComplexBinaryKBinding<T, T1, L>(this, anObservable, func = { o1, o2 -> aMapper.invoke(o1.value, o2.value) }),
        KLongBinding<L> {}

/** Creates a new [KIntBinding] which observes [this] and combines its value with [aValue] using [aMapper] function. */
fun <T, T1, I : Int?> KObservableValue<T>.combineValueToInt(aValue: T1, aMapper: T.(T1) -> I): KIntBinding<I> =
        this.combineToInt(aValue.toObservableConst(), aMapper)

/** Creates a new [KIntBinding] which observes [this] and [anObservable] and combines their values using [aMapper] function. */
fun <T, T1, I : Int?> KObservableValue<T>.combineToInt(
        anObservable: KObservableValue<T1>,
        aMapper: T.(T1) -> I
): KIntBinding<I> = object :
        ComplexBinaryKBinding<T, T1, I>(this, anObservable, func = { o1, o2 -> aMapper.invoke(o1.value, o2.value) }),
        KIntBinding<I> {}

/** Creates a new [KShortBinding] which observes [this] and combines its value with [aValue] using [aMapper] function. */
fun <T, T1, S : Short?> KObservableValue<T>.combineValueToShort(aValue: T1, aMapper: T.(T1) -> S): KShortBinding<S> =
        this.combineToShort(aValue.toObservableConst(), aMapper)

/** Creates a new [KShortBinding] which observes [this] and [anObservable] and combines their values using [aMapper] function. */
fun <T, T1, S : Short?> KObservableValue<T>.combineToShort(
        anObservable: KObservableValue<T1>,
        aMapper: T.(T1) -> S
): KShortBinding<S> = object :
        ComplexBinaryKBinding<T, T1, S>(this, anObservable, func = { o1, o2 -> aMapper.invoke(o1.value, o2.value) }),
        KShortBinding<S> {}

/** Creates a new [KByteBinding] which observes [this] and combines its value with [aValue] using [aMapper] function. */
fun <T, T1, B : Byte?> KObservableValue<T>.combineValuesToByte(aValue: T1, aMapper: T.(T1) -> B): KByteBinding<B> =
        this.combineToByte(aValue.toObservableConst(), aMapper)

/** Creates a new [KByteBinding] which observes [this] and [anObservable] and combines their values using [aMapper] function. */
fun <T, T1, B : Byte?> KObservableValue<T>.combineToByte(
        anObservable: KObservableValue<T1>,
        aMapper: T.(T1) -> B
): KByteBinding<B> = object :
        ComplexBinaryKBinding<T, T1, B>(this, anObservable, func = { o1, o2 -> aMapper.invoke(o1.value, o2.value) }),
        KByteBinding<B> {}