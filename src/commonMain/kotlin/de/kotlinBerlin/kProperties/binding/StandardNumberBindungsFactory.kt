@file:Suppress("unused")

package de.kotlinBerlin.kProperties.binding

import de.kotlinBerlin.kProperties.util.divide
import de.kotlinBerlin.kProperties.util.minus
import de.kotlinBerlin.kProperties.util.plus
import de.kotlinBerlin.kProperties.util.times
import de.kotlinBerlin.kProperties.value.*
import kotlin.math.abs
import kotlin.math.absoluteValue

//Convert

/**
 * Returns a [KNumberBinding]. If [this] already is an instance of [KNumberBinding] it is simply returned.
 * Otherwise a new [KNumberBinding] is created which observes this [KObservableValue] for changes.
 */
fun <N : Number?> KObservableValue<N>.toNumberBinding(): KNumberBinding<N> =
        if (this is KNumberBinding<N>) this else this.mapToNumber { this }

/**
 * Returns a [KDoubleBinding]. If [this] already is an instance of [KDoubleBinding] it is simply returned.
 * Otherwise a new [KDoubleBinding] is created which observes this [KObservableValue] for changes.
 */
fun <D : Double?> KObservableValue<D>.toDoubleBinding(): KDoubleBinding<D> =
        if (this is KDoubleBinding<D>) this else this.mapToDouble { this }

/**
 * Returns a [KFloatBinding]. If [this] already is an instance of [KFloatBinding] it is simply returned.
 * Otherwise a new [KFloatBinding] is created which observes this [KObservableValue] for changes.
 */
fun <F : Float?> KObservableValue<F>.toFloatBinding(): KFloatBinding<F> =
        if (this is KFloatBinding<F>) this else this.mapToFloat { this }

/**
 * Returns a [KLongBinding]. If [this] already is an instance of [KLongBinding] it is simply returned.
 * Otherwise a new [KLongBinding] is created which observes this [KObservableValue] for changes.
 */
fun <L : Long?> KObservableValue<L>.toLongBinding(): KLongBinding<L> =
        if (this is KLongBinding<L>) this else this.mapToLong { this }

/**
 * Returns a [KIntBinding]. If [this] already is an instance of [KIntBinding] it is simply returned.
 * Otherwise a new [KIntBinding] is created which observes this [KObservableValue] for changes.
 */
fun <I : Int?> KObservableValue<I>.toIntBinding(): KIntBinding<I> =
        if (this is KIntBinding<I>) this else this.mapToInt { this }

/**
 * Returns a [KShortBinding]. If [this] already is an instance of [KShortBinding] it is simply returned.
 * Otherwise a new [KShortBinding] is created which observes this [KObservableValue] for changes.
 */
fun <S : Short?> KObservableValue<S>.toShortBinding(): KShortBinding<S> =
        if (this is KShortBinding<S>) this else this.mapToShort { this }

/**
 * Returns a [KByteBinding]. If [this] already is an instance of [KByteBinding] it is simply returned.
 * Otherwise a new [KByteBinding] is created which observes this [KObservableValue] for changes.
 */
fun <B : Byte?> KObservableValue<B>.toByteBinding(): KByteBinding<B> =
        if (this is KByteBinding<B>) this else this.mapToByte { this }

/** Creates a new [KDoubleBinding] that observes [this] and converts its value to a [Double] */
fun <N : Number> KObservableNumberValue<N>.toDouble(): KDoubleBinding<Double> = this.mapToDouble(Number::toDouble)

/** Creates a new [KFloatBinding] that observes [this] and converts its value to a [Float] */
fun <N : Number> KObservableNumberValue<N>.toFloat(): KFloatBinding<Float> = this.mapToFloat(Number::toFloat)

/** Creates a new [KLongBinding] that observes [this] and converts its value to a [Long] */
fun <N : Number> KObservableNumberValue<N>.toLong(): KLongBinding<Long> = this.mapToLong(Number::toLong)

/** Creates a new [KIntBinding] that observes [this] and converts its value to a [Int] */
fun <N : Number> KObservableNumberValue<N>.toInt(): KIntBinding<Int> = this.mapToInt(Number::toInt)

/** Creates a new [KShortBinding] that observes [this] and converts its value to a [Short] */
fun <N : Number> KObservableNumberValue<N>.toShort(): KShortBinding<Short> = this.mapToShort(Number::toShort)

/** Creates a new [KByteBinding] that observes [this] and converts its value to a [Byte] */
fun <N : Number> KObservableNumberValue<N>.toByte(): KByteBinding<Byte> = this.mapToByte(Number::toByte)

//NUMBER

/** Creates a new [KNumberBinding] that observes [this] and [anObservableNumber] and represents the sum of their values. */
operator fun KObservableNumberValue<Number>.plus(anObservableNumber: KObservableNumberValue<Number>): KNumberBinding<Number> =
        this.combineToNumber(anObservableNumber, ::plus)

/** Creates a new [KNumberBinding] that observes [this] and represents the sum of its value and [aValue]. */
operator fun KObservableNumberValue<Number>.plus(aValue: Number): KNumberBinding<Number> =
        this.combineValueToNumber(aValue, ::plus)

/** Creates a new [KNumberBinding] that observes [this] and [anObservableNumber] and represents the subtraction of their values. */
operator fun KObservableNumberValue<Number>.minus(anObservableNumber: KObservableNumberValue<Number>): KNumberBinding<Number> =
        this.combineToNumber(anObservableNumber, ::minus)

/** Creates a new [KNumberBinding] that observes [this] and represents the subtraction of its value and [aValue]. */
operator fun KObservableNumberValue<Number>.minus(aValue: Number): KNumberBinding<Number> =
        this.combineValueToNumber(aValue, ::minus)

/** Creates a new [KNumberBinding] that observes [this] and [anObservableNumber] and represents the multiplication of their values. */
operator fun KObservableNumberValue<Number>.times(anObservableNumber: KObservableNumberValue<Number>): KNumberBinding<Number> =
        this.combineToNumber(anObservableNumber, ::times)

/** Creates a new [KNumberBinding] that observes [this] and represents the multiplication of its value and [aValue]. */
operator fun KObservableNumberValue<Number>.times(aValue: Number): KNumberBinding<Number> =
        this.combineValueToNumber(aValue, ::times)

/** Creates a new [KNumberBinding] that observes [this] and [anObservableNumber] and represents the division of their values. */
operator fun KObservableNumberValue<Number>.div(anObservableNumber: KObservableNumberValue<Number>): KNumberBinding<Number> =
        this.combineToNumber(anObservableNumber, ::divide)

/** Creates a new [KNumberBinding] that observes [this] and represents the division of its value and [aValue]. */
operator fun KObservableNumberValue<Number>.div(aValue: Number): KNumberBinding<Number> =
        this.combineValueToNumber(aValue, ::divide)

/** Creates a new [KNumberBinding] that observes [this] and represents its value + 1 */
operator fun KObservableNumberValue<Number>.inc(): KNumberBinding<Number> = this.combineValueToNumber(1, ::plus)

/** Creates a new [KNumberBinding] that observes [this] and represents its value - 1 */
operator fun KObservableNumberValue<Number>.dec(): KNumberBinding<Number> = this.combineValueToNumber(1, ::minus)

/** Creates a new [KNumberBinding] that observes [this] and represents its negated value */
operator fun KObservableNumberValue<Number>.unaryMinus(): KNumberBinding<Number> =
        chooseNumber(this) {
            thenMapValue(Double::unaryMinus)
            thenMapValue(Float::unaryMinus)
            thenMapValue(Long::unaryMinus)
            thenMapValue(Int::unaryMinus)
            thenMapValue(Short::unaryMinus)
            thenMapValue(Byte::unaryMinus)
            otherwise { this@unaryMinus.toDouble().map(Double::unaryMinus) }
        }

/** Creates a new [KNumberBinding] that observes [this] and represents its absolute value */
fun KObservableNumberValue<Number>.abs(): KNumberBinding<Number> =
        chooseNumber(this) {
            thenMapValue(Double::absoluteValue)
            thenMapValue(Float::absoluteValue)
            thenMapValue(Long::absoluteValue)
            thenMapValue(Int::absoluteValue)
            case<Short>() thenMapValue { if (this < 0) -1 * this else this }
            case<Byte>() thenMapValue { if (this < 0) -1 * this else this }
        }

//DOUBLE

/** Creates a new [KDoubleBinding] that observes [this] and [anObservableNumber] and represents the sum of their values. */
operator fun KObservableDoubleValue<Double>.plus(anObservableNumber: KObservableNumberValue<Number>): KDoubleBinding<Double> =
        this.combineToDouble(anObservableNumber, ::plus)

/** Creates a new [KDoubleBinding] that observes [this] and [anObservableNumber] and represents the subtraction of their values. */
operator fun KObservableDoubleValue<Double>.minus(anObservableNumber: KObservableNumberValue<Number>): KDoubleBinding<Double> =
        this.combineToDouble(anObservableNumber, ::minus)

/** Creates a new [KDoubleBinding] that observes [this] and [anObservableNumber] and represents the multiplication of their values. */
operator fun KObservableDoubleValue<Double>.times(anObservableNumber: KObservableNumberValue<Number>): KDoubleBinding<Double> =
        this.combineToDouble(anObservableNumber, ::times)

/** Creates a new [KDoubleBinding] that observes [this] and [anObservableNumber] and represents the division of their values. */
operator fun KObservableDoubleValue<Double>.div(anObservableNumber: KObservableNumberValue<Number>): KDoubleBinding<Double> =
        this.combineToDouble(anObservableNumber, ::divide)

/** Creates a new [KDoubleBinding] that observes [this] and represents its value + 1 */
operator fun KObservableDoubleValue<Double>.inc(): KDoubleBinding<Double> =
        this.mapToDouble(Double::inc)

/** Creates a new [KDoubleBinding] that observes [this] and represents its value - 1 */
operator fun KObservableDoubleValue<Double>.dec(): KDoubleBinding<Double> =
        this.mapToDouble(Double::dec)

/** Creates a new [KDoubleBinding] that observes [this] and represents its negated value */
operator fun KObservableDoubleValue<Double>.unaryMinus(): KDoubleBinding<Double> =
        this.mapToDouble(Double::unaryMinus)

/** Creates a new [KDoubleBinding] that observes [this] and represents its absolute value */
fun KObservableDoubleValue<Double>.abs(): KDoubleBinding<Double> =
        this.mapToDouble(::abs)

//FLOAT

/** Creates a new [KDoubleBinding] that observes [this] and [anObservableNumber] and represents the sum of their values. */
operator fun KObservableFloatValue<Float>.plus(anObservableNumber: KObservableDoubleValue<Double>): KDoubleBinding<Double> =
        this.combineToDouble(anObservableNumber, Float::plus)

/** Creates a new [KFloatBinding] that observes [this] and [anObservableNumber] and represents the sum of their values. */
operator fun KObservableFloatValue<Float>.plus(anObservableNumber: KObservableFloatValue<Float>): KFloatBinding<Float> =
        this.combineToFloat(anObservableNumber, Float::plus)

/** Creates a new [KFloatBinding] that observes [this] and [anObservableNumber] and represents the sum of their values. */
operator fun KObservableFloatValue<Float>.plus(anObservableNumber: KObservableLongValue<Long>): KFloatBinding<Float> =
        this.combineToFloat(anObservableNumber, Float::plus)

/** Creates a new [KFloatBinding] that observes [this] and [anObservableNumber] and represents the sum of their values. */
operator fun KObservableFloatValue<Float>.plus(anObservableNumber: KObservableIntValue<Int>): KFloatBinding<Float> =
        this.combineToFloat(anObservableNumber, Float::plus)

/** Creates a new [KFloatBinding] that observes [this] and [anObservableNumber] and represents the sum of their values. */
operator fun KObservableFloatValue<Float>.plus(anObservableNumber: KObservableShortValue<Short>): KFloatBinding<Float> =
        this.combineToFloat(anObservableNumber, Float::plus)

/** Creates a new [KFloatBinding] that observes [this] and [anObservableNumber] and represents the sum of their values. */
operator fun KObservableFloatValue<Float>.plus(anObservableNumber: KObservableByteValue<Byte>): KFloatBinding<Float> =
        this.combineToFloat(anObservableNumber, Float::plus)

/** Creates a new [KDoubleBinding] that observes [this] and [anObservableNumber] and represents the subtraction of their values. */
operator fun KObservableFloatValue<Float>.minus(anObservableNumber: KObservableDoubleValue<Double>): KDoubleBinding<Double> =
        this.combineToDouble(anObservableNumber, Float::minus)

/** Creates a new [KFloatBinding] that observes [this] and [anObservableNumber] and represents the subtraction of their values. */
operator fun KObservableFloatValue<Float>.minus(anObservableNumber: KObservableFloatValue<Float>): KFloatBinding<Float> =
        this.combineToFloat(anObservableNumber, Float::minus)

/** Creates a new [KFloatBinding] that observes [this] and [anObservableNumber] and represents the subtraction of their values. */
operator fun KObservableFloatValue<Float>.minus(anObservableNumber: KObservableLongValue<Long>): KFloatBinding<Float> =
        this.combineToFloat(anObservableNumber, Float::minus)

/** Creates a new [KFloatBinding] that observes [this] and [anObservableNumber] and represents the subtraction of their values. */
operator fun KObservableFloatValue<Float>.minus(anObservableNumber: KObservableIntValue<Int>): KFloatBinding<Float> =
        this.combineToFloat(anObservableNumber, Float::minus)

/** Creates a new [KFloatBinding] that observes [this] and [anObservableNumber] and represents the subtraction of their values. */
operator fun KObservableFloatValue<Float>.minus(anObservableNumber: KObservableShortValue<Short>): KFloatBinding<Float> =
        this.combineToFloat(anObservableNumber, Float::minus)

/** Creates a new [KFloatBinding] that observes [this] and [anObservableNumber] and represents the subtraction of their values. */
operator fun KObservableFloatValue<Float>.minus(anObservableNumber: KObservableByteValue<Byte>): KFloatBinding<Float> =
        this.combineToFloat(anObservableNumber, Float::minus)

/** Creates a new [KDoubleBinding] that observes [this] and [anObservableNumber] and represents the multiplication of their values. */
operator fun KObservableFloatValue<Float>.times(anObservableNumber: KObservableDoubleValue<Double>): KDoubleBinding<Double> =
        this.combineToDouble(anObservableNumber, Float::times)

/** Creates a new [KFloatBinding] that observes [this] and [anObservableNumber] and represents the multiplication of their values. */
operator fun KObservableFloatValue<Float>.times(anObservableNumber: KObservableFloatValue<Float>): KFloatBinding<Float> =
        this.combineToFloat(anObservableNumber, Float::times)

/** Creates a new [KFloatBinding] that observes [this] and [anObservableNumber] and represents the multiplication of their values. */
operator fun KObservableFloatValue<Float>.times(anObservableNumber: KObservableLongValue<Long>): KFloatBinding<Float> =
        this.combineToFloat(anObservableNumber, Float::times)

/** Creates a new [KFloatBinding] that observes [this] and [anObservableNumber] and represents the multiplication of their values. */
operator fun KObservableFloatValue<Float>.times(anObservableNumber: KObservableIntValue<Int>): KFloatBinding<Float> =
        this.combineToFloat(anObservableNumber, Float::times)

/** Creates a new [KFloatBinding] that observes [this] and [anObservableNumber] and represents the multiplication of their values. */
operator fun KObservableFloatValue<Float>.times(anObservableNumber: KObservableShortValue<Short>): KFloatBinding<Float> =
        this.combineToFloat(anObservableNumber, Float::times)

/** Creates a new [KFloatBinding] that observes [this] and [anObservableNumber] and represents the multiplication of their values. */
operator fun KObservableFloatValue<Float>.times(anObservableNumber: KObservableByteValue<Byte>): KFloatBinding<Float> =
        this.combineToFloat(anObservableNumber, Float::times)

/** Creates a new [KDoubleBinding] that observes [this] and [anObservableNumber] and represents the division of their values. */
operator fun KObservableFloatValue<Float>.div(anObservableNumber: KObservableDoubleValue<Double>): KDoubleBinding<Double> =
        this.combineToDouble(anObservableNumber, Float::div)

/** Creates a new [KFloatBinding] that observes [this] and [anObservableNumber] and represents the division of their values. */
operator fun KObservableFloatValue<Float>.div(anObservableNumber: KObservableFloatValue<Float>): KFloatBinding<Float> =
        this.combineToFloat(anObservableNumber, Float::div)

/** Creates a new [KFloatBinding] that observes [this] and [anObservableNumber] and represents the division of their values. */
operator fun KObservableFloatValue<Float>.div(anObservableNumber: KObservableLongValue<Long>): KFloatBinding<Float> =
        this.combineToFloat(anObservableNumber, Float::div)

/** Creates a new [KFloatBinding] that observes [this] and [anObservableNumber] and represents the division of their values. */
operator fun KObservableFloatValue<Float>.div(anObservableNumber: KObservableIntValue<Int>): KFloatBinding<Float> =
        this.combineToFloat(anObservableNumber, Float::div)

/** Creates a new [KFloatBinding] that observes [this] and [anObservableNumber] and represents the division of their values. */
operator fun KObservableFloatValue<Float>.div(anObservableNumber: KObservableShortValue<Short>): KFloatBinding<Float> =
        this.combineToFloat(anObservableNumber, Float::div)

/** Creates a new [KFloatBinding] that observes [this] and [anObservableNumber] and represents the division of their values. */
operator fun KObservableFloatValue<Float>.div(anObservableNumber: KObservableByteValue<Byte>): KFloatBinding<Float> =
        this.combineToFloat(anObservableNumber, Float::div)

/** Creates a new [KFloatBinding] that observes [this] and represents its value + 1 */
operator fun KObservableFloatValue<Float>.inc(): KFloatBinding<Float> = this.mapToFloat(Float::inc)

/** Creates a new [KFloatBinding] that observes [this] and represents its value - 1 */
operator fun KObservableFloatValue<Float>.dec(): KFloatBinding<Float> = this.mapToFloat(Float::dec)

/** Creates a new [KFloatBinding] that observes [this] and represents its negated value */
operator fun KObservableFloatValue<Float>.unaryMinus(): KFloatBinding<Float> = this.mapToFloat(Float::unaryMinus)

/** Creates a new [KFloatBinding] that observes [this] and represents its absolute value */
fun KObservableFloatValue<Float>.abs(): KFloatBinding<Float> = this.mapToFloat(::abs)

//LONG

/** Creates a new [KDoubleBinding] that observes [this] and [anObservableNumber] and represents the sum of their values. */
operator fun KObservableLongValue<Long>.plus(anObservableNumber: KObservableDoubleValue<Double>): KDoubleBinding<Double> =
        this.combineToDouble(anObservableNumber, Long::plus)

/** Creates a new [KFloatBinding] that observes [this] and [anObservableNumber] and represents the sum of their values. */
operator fun KObservableLongValue<Long>.plus(anObservableNumber: KObservableFloatValue<Float>): KFloatBinding<Float> =
        this.combineToFloat(anObservableNumber, Long::plus)

/** Creates a new [KLongBinding] that observes [this] and [anObservableNumber] and represents the sum of their values. */
operator fun KObservableLongValue<Long>.plus(anObservableNumber: KObservableLongValue<Long>): KLongBinding<Long> =
        this.combineToLong(anObservableNumber, Long::plus)

/** Creates a new [KLongBinding] that observes [this] and [anObservableNumber] and represents the sum of their values. */
operator fun KObservableLongValue<Long>.plus(anObservableNumber: KObservableIntValue<Int>): KLongBinding<Long> =
        this.combineToLong(anObservableNumber, Long::plus)

/** Creates a new [KLongBinding] that observes [this] and [anObservableNumber] and represents the sum of their values. */
operator fun KObservableLongValue<Long>.plus(anObservableNumber: KObservableShortValue<Short>): KLongBinding<Long> =
        this.combineToLong(anObservableNumber, Long::plus)

/** Creates a new [KLongBinding] that observes [this] and [anObservableNumber] and represents the sum of their values. */
operator fun KObservableLongValue<Long>.plus(anObservableNumber: KObservableByteValue<Byte>): KLongBinding<Long> =
        this.combineToLong(anObservableNumber, Long::plus)

/** Creates a new [KDoubleBinding] that observes [this] and [anObservableNumber] and represents the subtraction of their values. */
operator fun KObservableLongValue<Long>.minus(anObservableNumber: KObservableDoubleValue<Double>): KDoubleBinding<Double> =
        this.combineToDouble(anObservableNumber, Long::minus)

/** Creates a new [KFloatBinding] that observes [this] and [anObservableNumber] and represents the subtraction of their values. */
operator fun KObservableLongValue<Long>.minus(anObservableNumber: KObservableFloatValue<Float>): KFloatBinding<Float> =
        this.combineToFloat(anObservableNumber, Long::minus)

/** Creates a new [KLongBinding] that observes [this] and [anObservableNumber] and represents the subtraction of their values. */
operator fun KObservableLongValue<Long>.minus(anObservableNumber: KObservableLongValue<Long>): KLongBinding<Long> =
        this.combineToLong(anObservableNumber, Long::minus)

/** Creates a new [KLongBinding] that observes [this] and [anObservableNumber] and represents the subtraction of their values. */
operator fun KObservableLongValue<Long>.minus(anObservableNumber: KObservableIntValue<Int>): KLongBinding<Long> =
        this.combineToLong(anObservableNumber, Long::minus)

/** Creates a new [KLongBinding] that observes [this] and [anObservableNumber] and represents the subtraction of their values. */
operator fun KObservableLongValue<Long>.minus(anObservableNumber: KObservableShortValue<Short>): KLongBinding<Long> =
        this.combineToLong(anObservableNumber, Long::minus)

/** Creates a new [KLongBinding] that observes [this] and [anObservableNumber] and represents the subtraction of their values. */
operator fun KObservableLongValue<Long>.minus(anObservableNumber: KObservableByteValue<Byte>): KLongBinding<Long> =
        this.combineToLong(anObservableNumber, Long::minus)

/** Creates a new [KDoubleBinding] that observes [this] and [anObservableNumber] and represents the multiplication of their values. */
operator fun KObservableLongValue<Long>.times(anObservableNumber: KObservableDoubleValue<Double>): KDoubleBinding<Double> =
        this.combineToDouble(anObservableNumber, Long::times)

/** Creates a new [KFloatBinding] that observes [this] and [anObservableNumber] and represents the multiplication of their values. */
operator fun KObservableLongValue<Long>.times(anObservableNumber: KObservableFloatValue<Float>): KFloatBinding<Float> =
        this.combineToFloat(anObservableNumber, Long::times)

/** Creates a new [KLongBinding] that observes [this] and [anObservableNumber] and represents the multiplication of their values. */
operator fun KObservableLongValue<Long>.times(anObservableNumber: KObservableLongValue<Long>): KLongBinding<Long> =
        this.combineToLong(anObservableNumber, Long::times)

/** Creates a new [KLongBinding] that observes [this] and [anObservableNumber] and represents the multiplication of their values. */
operator fun KObservableLongValue<Long>.times(anObservableNumber: KObservableIntValue<Int>): KLongBinding<Long> =
        this.combineToLong(anObservableNumber, Long::times)

/** Creates a new [KLongBinding] that observes [this] and [anObservableNumber] and represents the multiplication of their values. */
operator fun KObservableLongValue<Long>.times(anObservableNumber: KObservableShortValue<Short>): KLongBinding<Long> =
        this.combineToLong(anObservableNumber, Long::times)

/** Creates a new [KLongBinding] that observes [this] and [anObservableNumber] and represents the multiplication of their values. */
operator fun KObservableLongValue<Long>.times(anObservableNumber: KObservableByteValue<Byte>): KLongBinding<Long> =
        this.combineToLong(anObservableNumber, Long::times)

/** Creates a new [KDoubleBinding] that observes [this] and [anObservableNumber] and represents the division of their values. */
operator fun KObservableLongValue<Long>.div(anObservableNumber: KObservableDoubleValue<Double>): KDoubleBinding<Double> =
        this.combineToDouble(anObservableNumber, Long::div)

/** Creates a new [KFloatBinding] that observes [this] and [anObservableNumber] and represents the division of their values. */
operator fun KObservableLongValue<Long>.div(anObservableNumber: KObservableFloatValue<Float>): KFloatBinding<Float> =
        this.combineToFloat(anObservableNumber, Long::div)

/** Creates a new [KLongBinding] that observes [this] and [anObservableNumber] and represents the division of their values. */
operator fun KObservableLongValue<Long>.div(anObservableNumber: KObservableLongValue<Long>): KLongBinding<Long> =
        this.combineToLong(anObservableNumber, Long::div)

/** Creates a new [KLongBinding] that observes [this] and [anObservableNumber] and represents the division of their values. */
operator fun KObservableLongValue<Long>.div(anObservableNumber: KObservableIntValue<Int>): KLongBinding<Long> =
        this.combineToLong(anObservableNumber, Long::div)

/** Creates a new [KLongBinding] that observes [this] and [anObservableNumber] and represents the division of their values. */
operator fun KObservableLongValue<Long>.div(anObservableNumber: KObservableShortValue<Short>): KLongBinding<Long> =
        this.combineToLong(anObservableNumber, Long::div)

/** Creates a new [KLongBinding] that observes [this] and [anObservableNumber] and represents the division of their values. */
operator fun KObservableLongValue<Long>.div(anObservableNumber: KObservableByteValue<Byte>): KLongBinding<Long> =
        this.combineToLong(anObservableNumber, Long::div)

/** Creates a new [KLongBinding] that observes [this] and represents its value + 1 */
operator fun KObservableLongValue<Long>.inc(): KLongBinding<Long> = this.mapToLong(Long::inc)

/** Creates a new [KLongBinding] that observes [this] and represents its value - 1 */
operator fun KObservableLongValue<Long>.dec(): KLongBinding<Long> = this.mapToLong(Long::dec)

/** Creates a new [KLongBinding] that observes [this] and represents its negated value */
operator fun KObservableLongValue<Long>.unaryMinus(): KLongBinding<Long> = this.mapToLong(Long::unaryMinus)

/** Creates a new [KLongBinding] that observes [this] and represents its absolute value */
fun KObservableLongValue<Long>.abs(): KLongBinding<Long> = this.mapToLong(::abs)

//INT

/** Creates a new [KDoubleBinding] that observes [this] and [anObservableNumber] and represents the sum of their values. */
operator fun KObservableIntValue<Int>.plus(anObservableNumber: KObservableDoubleValue<Double>): KDoubleBinding<Double> =
        this.combineToDouble(anObservableNumber, Int::plus)

/** Creates a new [KFloatBinding] that observes [this] and [anObservableNumber] and represents the sum of their values. */
operator fun KObservableIntValue<Int>.plus(anObservableNumber: KObservableFloatValue<Float>): KFloatBinding<Float> =
        this.combineToFloat(anObservableNumber, Int::plus)

/** Creates a new [KLongBinding] that observes [this] and [anObservableNumber] and represents the sum of their values. */
operator fun KObservableIntValue<Int>.plus(anObservableNumber: KObservableLongValue<Long>): KLongBinding<Long> =
        this.combineToLong(anObservableNumber, Int::plus)

/** Creates a new [KIntBinding] that observes [this] and [anObservableNumber] and represents the sum of their values. */
operator fun KObservableIntValue<Int>.plus(anObservableNumber: KObservableIntValue<Int>): KIntBinding<Int> =
        this.combineToInt(anObservableNumber, Int::plus)

/** Creates a new [KIntBinding] that observes [this] and [anObservableNumber] and represents the sum of their values. */
operator fun KObservableIntValue<Int>.plus(anObservableNumber: KObservableShortValue<Short>): KIntBinding<Int> =
        this.combineToInt(anObservableNumber, Int::plus)

/** Creates a new [KIntBinding] that observes [this] and [anObservableNumber] and represents the sum of their values. */
operator fun KObservableIntValue<Int>.plus(anObservableNumber: KObservableByteValue<Byte>): KIntBinding<Int> =
        this.combineToInt(anObservableNumber, Int::plus)

/** Creates a new [KDoubleBinding] that observes [this] and [anObservableNumber] and represents the subtraction of their values. */
operator fun KObservableIntValue<Int>.minus(anObservableNumber: KObservableDoubleValue<Double>): KDoubleBinding<Double> =
        this.combineToDouble(anObservableNumber, Int::minus)

/** Creates a new [KFloatBinding] that observes [this] and [anObservableNumber] and represents the subtraction of their values. */
operator fun KObservableIntValue<Int>.minus(anObservableNumber: KObservableFloatValue<Float>): KFloatBinding<Float> =
        this.combineToFloat(anObservableNumber, Int::minus)

/** Creates a new [KLongBinding] that observes [this] and [anObservableNumber] and represents the subtraction of their values. */
operator fun KObservableIntValue<Int>.minus(anObservableNumber: KObservableLongValue<Long>): KLongBinding<Long> =
        this.combineToLong(anObservableNumber, Int::minus)

/** Creates a new [KIntBinding] that observes [this] and [anObservableNumber] and represents the subtraction of their values. */
operator fun KObservableIntValue<Int>.minus(anObservableNumber: KObservableIntValue<Int>): KIntBinding<Int> =
        this.combineToInt(anObservableNumber, Int::minus)

/** Creates a new [KIntBinding] that observes [this] and [anObservableNumber] and represents the subtraction of their values. */
operator fun KObservableIntValue<Int>.minus(anObservableNumber: KObservableShortValue<Short>): KIntBinding<Int> =
        this.combineToInt(anObservableNumber, Int::minus)

/** Creates a new [KIntBinding] that observes [this] and [anObservableNumber] and represents the subtraction of their values. */
operator fun KObservableIntValue<Int>.minus(anObservableNumber: KObservableByteValue<Byte>): KIntBinding<Int> =
        this.combineToInt(anObservableNumber, Int::minus)

/** Creates a new [KDoubleBinding] that observes [this] and [anObservableNumber] and represents the multiplication of their values. */
operator fun KObservableIntValue<Int>.times(anObservableNumber: KObservableDoubleValue<Double>): KDoubleBinding<Double> =
        this.combineToDouble(anObservableNumber, Int::times)

/** Creates a new [KFloatBinding] that observes [this] and [anObservableNumber] and represents the multiplication of their values. */
operator fun KObservableIntValue<Int>.times(anObservableNumber: KObservableFloatValue<Float>): KFloatBinding<Float> =
        this.combineToFloat(anObservableNumber, Int::times)

/** Creates a new [KLongBinding] that observes [this] and [anObservableNumber] and represents the multiplication of their values. */
operator fun KObservableIntValue<Int>.times(anObservableNumber: KObservableLongValue<Long>): KLongBinding<Long> =
        this.combineToLong(anObservableNumber, Int::times)

/** Creates a new [KIntBinding] that observes [this] and [anObservableNumber] and represents the multiplication of their values. */
operator fun KObservableIntValue<Int>.times(anObservableNumber: KObservableIntValue<Int>): KIntBinding<Int> =
        this.combineToInt(anObservableNumber, Int::times)

/** Creates a new [KIntBinding] that observes [this] and [anObservableNumber] and represents the multiplication of their values. */
operator fun KObservableIntValue<Int>.times(anObservableNumber: KObservableShortValue<Short>): KIntBinding<Int> =
        this.combineToInt(anObservableNumber, Int::times)

/** Creates a new [KIntBinding] that observes [this] and [anObservableNumber] and represents the multiplication of their values. */
operator fun KObservableIntValue<Int>.times(anObservableNumber: KObservableByteValue<Byte>): KIntBinding<Int> =
        this.combineToInt(anObservableNumber, Int::times)

/** Creates a new [KDoubleBinding] that observes [this] and [anObservableNumber] and represents the division of their values. */
operator fun KObservableIntValue<Int>.div(anObservableNumber: KObservableDoubleValue<Double>): KDoubleBinding<Double> =
        this.combineToDouble(anObservableNumber, Int::div)

/** Creates a new [KFloatBinding] that observes [this] and [anObservableNumber] and represents the division of their values. */
operator fun KObservableIntValue<Int>.div(anObservableNumber: KObservableFloatValue<Float>): KFloatBinding<Float> =
        this.combineToFloat(anObservableNumber, Int::div)

/** Creates a new [KLongBinding] that observes [this] and [anObservableNumber] and represents the division of their values. */
operator fun KObservableIntValue<Int>.div(anObservableNumber: KObservableLongValue<Long>): KLongBinding<Long> =
        this.combineToLong(anObservableNumber, Int::div)

/** Creates a new [KIntBinding] that observes [this] and [anObservableNumber] and represents the division of their values. */
operator fun KObservableIntValue<Int>.div(anObservableNumber: KObservableIntValue<Int>): KIntBinding<Int> =
        this.combineToInt(anObservableNumber, Int::div)

/** Creates a new [KIntBinding] that observes [this] and [anObservableNumber] and represents the division of their values. */
operator fun KObservableIntValue<Int>.div(anObservableNumber: KObservableShortValue<Short>): KIntBinding<Int> =
        this.combineToInt(anObservableNumber, Int::div)

/** Creates a new [KIntBinding] that observes [this] and [anObservableNumber] and represents the division of their values. */
operator fun KObservableIntValue<Int>.div(anObservableNumber: KObservableByteValue<Byte>): KIntBinding<Int> =
        this.combineToInt(anObservableNumber, Int::div)

/** Creates a new [KIntBinding] that observes [this] and represents its value + 1 */
operator fun KObservableIntValue<Int>.inc(): KIntBinding<Int> = this.mapToInt(Int::inc)

/** Creates a new [KIntBinding] that observes [this] and represents its value - 1 */
operator fun KObservableIntValue<Int>.dec(): KIntBinding<Int> = this.mapToInt(Int::dec)

/** Creates a new [KIntBinding] that observes [this] and represents its negated value */
operator fun KObservableIntValue<Int>.unaryMinus(): KIntBinding<Int> = this.mapToInt(Int::unaryMinus)

/** Creates a new [KIntBinding] that observes [this] and represents its absolute value */
fun KObservableIntValue<Int>.abs(): KIntBinding<Int> = this.mapToInt(::abs)

//Short

/** Creates a new [KDoubleBinding] that observes [this] and [anObservableNumber] and represents the sum of their values. */
operator fun KObservableShortValue<Short>.plus(anObservableNumber: KObservableDoubleValue<Double>): KDoubleBinding<Double> =
        this.combineToDouble(anObservableNumber, Short::plus)

/** Creates a new [KFloatBinding] that observes [this] and [anObservableNumber] and represents the sum of their values. */
operator fun KObservableShortValue<Short>.plus(anObservableNumber: KObservableFloatValue<Float>): KFloatBinding<Float> =
        this.combineToFloat(anObservableNumber, Short::plus)

/** Creates a new [KLongBinding] that observes [this] and [anObservableNumber] and represents the sum of their values. */
operator fun KObservableShortValue<Short>.plus(anObservableNumber: KObservableLongValue<Long>): KLongBinding<Long> =
        this.combineToLong(anObservableNumber, Short::plus)

/** Creates a new [KIntBinding] that observes [this] and [anObservableNumber] and represents the sum of their values. */
operator fun KObservableShortValue<Short>.plus(anObservableNumber: KObservableIntValue<Int>): KIntBinding<Int> =
        this.combineToInt(anObservableNumber, Short::plus)

/** Creates a new [KIntBinding] that observes [this] and [anObservableNumber] and represents the sum of their values. */
operator fun KObservableShortValue<Short>.plus(anObservableNumber: KObservableShortValue<Short>): KIntBinding<Int> =
        this.combineToInt(anObservableNumber, Short::plus)

/** Creates a new [KIntBinding] that observes [this] and [anObservableNumber] and represents the sum of their values. */
operator fun KObservableShortValue<Short>.plus(anObservableNumber: KObservableByteValue<Byte>): KIntBinding<Int> =
        this.combineToInt(anObservableNumber, Short::plus)

/** Creates a new [KDoubleBinding] that observes [this] and [anObservableNumber] and represents the subtraction of their values. */
operator fun KObservableShortValue<Short>.minus(anObservableNumber: KObservableDoubleValue<Double>): KDoubleBinding<Double> =
        this.combineToDouble(anObservableNumber, Short::minus)

/** Creates a new [KFloatBinding] that observes [this] and [anObservableNumber] and represents the subtraction of their values. */
operator fun KObservableShortValue<Short>.minus(anObservableNumber: KObservableFloatValue<Float>): KFloatBinding<Float> =
        this.combineToFloat(anObservableNumber, Short::minus)

/** Creates a new [KLongBinding] that observes [this] and [anObservableNumber] and represents the subtraction of their values. */
operator fun KObservableShortValue<Short>.minus(anObservableNumber: KObservableLongValue<Long>): KLongBinding<Long> =
        this.combineToLong(anObservableNumber, Short::minus)

/** Creates a new [KIntBinding] that observes [this] and [anObservableNumber] and represents the subtraction of their values. */
operator fun KObservableShortValue<Short>.minus(anObservableNumber: KObservableIntValue<Int>): KIntBinding<Int> =
        this.combineToInt(anObservableNumber, Short::minus)

/** Creates a new [KIntBinding] that observes [this] and [anObservableNumber] and represents the subtraction of their values. */
operator fun KObservableShortValue<Short>.minus(anObservableNumber: KObservableShortValue<Short>): KIntBinding<Int> =
        this.combineToInt(anObservableNumber, Short::minus)

/** Creates a new [KIntBinding] that observes [this] and [anObservableNumber] and represents the subtraction of their values. */
operator fun KObservableShortValue<Short>.minus(anObservableNumber: KObservableByteValue<Byte>): KIntBinding<Int> =
        this.combineToInt(anObservableNumber, Short::minus)

/** Creates a new [KDoubleBinding] that observes [this] and [anObservableNumber] and represents the multiplication of their values. */
operator fun KObservableShortValue<Short>.times(anObservableNumber: KObservableDoubleValue<Double>): KDoubleBinding<Double> =
        this.combineToDouble(anObservableNumber, Short::times)

/** Creates a new [KFloatBinding] that observes [this] and [anObservableNumber] and represents the multiplication of their values. */
operator fun KObservableShortValue<Short>.times(anObservableNumber: KObservableFloatValue<Float>): KFloatBinding<Float> =
        this.combineToFloat(anObservableNumber, Short::times)

/** Creates a new [KLongBinding] that observes [this] and [anObservableNumber] and represents the multiplication of their values. */
operator fun KObservableShortValue<Short>.times(anObservableNumber: KObservableLongValue<Long>): KLongBinding<Long> =
        this.combineToLong(anObservableNumber, Short::times)

/** Creates a new [KIntBinding] that observes [this] and [anObservableNumber] and represents the multiplication of their values. */
operator fun KObservableShortValue<Short>.times(anObservableNumber: KObservableIntValue<Int>): KIntBinding<Int> =
        this.combineToInt(anObservableNumber, Short::times)

/** Creates a new [KIntBinding] that observes [this] and [anObservableNumber] and represents the multiplication of their values. */
operator fun KObservableShortValue<Short>.times(anObservableNumber: KObservableShortValue<Short>): KIntBinding<Int> =
        this.combineToInt(anObservableNumber, Short::times)

/** Creates a new [KIntBinding] that observes [this] and [anObservableNumber] and represents the multiplication of their values. */
operator fun KObservableShortValue<Short>.times(anObservableNumber: KObservableByteValue<Byte>): KIntBinding<Int> =
        this.combineToInt(anObservableNumber, Short::times)

/** Creates a new [KDoubleBinding] that observes [this] and [anObservableNumber] and represents the division of their values. */
operator fun KObservableShortValue<Short>.div(anObservableNumber: KObservableDoubleValue<Double>): KDoubleBinding<Double> =
        this.combineToDouble(anObservableNumber, Short::div)

/** Creates a new [KFloatBinding] that observes [this] and [anObservableNumber] and represents the division of their values. */
operator fun KObservableShortValue<Short>.div(anObservableNumber: KObservableFloatValue<Float>): KFloatBinding<Float> =
        this.combineToFloat(anObservableNumber, Short::div)

/** Creates a new [KLongBinding] that observes [this] and [anObservableNumber] and represents the division of their values. */
operator fun KObservableShortValue<Short>.div(anObservableNumber: KObservableLongValue<Long>): KLongBinding<Long> =
        this.combineToLong(anObservableNumber, Short::div)

/** Creates a new [KIntBinding] that observes [this] and [anObservableNumber] and represents the division of their values. */
operator fun KObservableShortValue<Short>.div(anObservableNumber: KObservableIntValue<Int>): KIntBinding<Int> =
        this.combineToInt(anObservableNumber, Short::div)

/** Creates a new [KIntBinding] that observes [this] and [anObservableNumber] and represents the division of their values. */
operator fun KObservableShortValue<Short>.div(anObservableNumber: KObservableShortValue<Short>): KIntBinding<Int> =
        this.combineToInt(anObservableNumber, Short::div)

/** Creates a new [KIntBinding] that observes [this] and [anObservableNumber] and represents the division of their values. */
operator fun KObservableShortValue<Short>.div(anObservableNumber: KObservableByteValue<Byte>): KIntBinding<Int> =
        this.combineToInt(anObservableNumber, Short::div)

/** Creates a new [KShortBinding] that observes [this] and represents its value + 1 */
operator fun KObservableShortValue<Short>.inc(): KShortBinding<Short> = this.mapToShort(Short::inc)

/** Creates a new [KShortBinding] that observes [this] and represents its value - 1 */
operator fun KObservableShortValue<Short>.dec(): KShortBinding<Short> = this.mapToShort(Short::dec)

/** Creates a new [KIntBinding] that observes [this] and represents its negated value */
operator fun KObservableShortValue<Short>.unaryMinus(): KIntBinding<Int> = this.mapToInt(Short::unaryMinus)

/** Creates a new [KShortBinding] that observes [this] and represents its absolute value */
fun KObservableShortValue<Short>.abs(): KShortBinding<Short> =
        this.mapToShort { if (value < 0) (-1 * value).toShort() else value }

//Short

/** Creates a new [KDoubleBinding] that observes [this] and [anObservableNumber] and represents the sum of their values. */
operator fun KObservableByteValue<Byte>.plus(anObservableNumber: KObservableDoubleValue<Double>): KDoubleBinding<Double> =
        this.combineToDouble(anObservableNumber, Byte::plus)

/** Creates a new [KFloatBinding] that observes [this] and [anObservableNumber] and represents the sum of their values. */
operator fun KObservableByteValue<Byte>.plus(anObservableNumber: KObservableFloatValue<Float>): KFloatBinding<Float> =
        this.combineToFloat(anObservableNumber, Byte::plus)

/** Creates a new [KLongBinding] that observes [this] and [anObservableNumber] and represents the sum of their values. */
operator fun KObservableByteValue<Byte>.plus(anObservableNumber: KObservableLongValue<Long>): KLongBinding<Long> =
        this.combineToLong(anObservableNumber, Byte::plus)

/** Creates a new [KIntBinding] that observes [this] and [anObservableNumber] and represents the sum of their values. */
operator fun KObservableByteValue<Byte>.plus(anObservableNumber: KObservableIntValue<Int>): KIntBinding<Int> =
        this.combineToInt(anObservableNumber, Byte::plus)

/** Creates a new [KIntBinding] that observes [this] and [anObservableNumber] and represents the sum of their values. */
operator fun KObservableByteValue<Byte>.plus(anObservableNumber: KObservableShortValue<Short>): KIntBinding<Int> =
        this.combineToInt(anObservableNumber, Byte::plus)

/** Creates a new [KIntBinding] that observes [this] and [anObservableNumber] and represents the sum of their values. */
operator fun KObservableByteValue<Byte>.plus(anObservableNumber: KObservableByteValue<Byte>): KIntBinding<Int> =
        this.combineToInt(anObservableNumber, Byte::plus)

/** Creates a new [KDoubleBinding] that observes [this] and [anObservableNumber] and represents the subtraction of their values. */
operator fun KObservableByteValue<Byte>.minus(anObservableNumber: KObservableDoubleValue<Double>): KDoubleBinding<Double> =
        this.combineToDouble(anObservableNumber, Byte::minus)

/** Creates a new [KFloatBinding] that observes [this] and [anObservableNumber] and represents the subtraction of their values. */
operator fun KObservableByteValue<Byte>.minus(anObservableNumber: KObservableFloatValue<Float>): KFloatBinding<Float> =
        this.combineToFloat(anObservableNumber, Byte::minus)

/** Creates a new [KLongBinding] that observes [this] and [anObservableNumber] and represents the subtraction of their values. */
operator fun KObservableByteValue<Byte>.minus(anObservableNumber: KObservableLongValue<Long>): KLongBinding<Long> =
        this.combineToLong(anObservableNumber, Byte::minus)

/** Creates a new [KIntBinding] that observes [this] and [anObservableNumber] and represents the subtraction of their values. */
operator fun KObservableByteValue<Byte>.minus(anObservableNumber: KObservableIntValue<Int>): KIntBinding<Int> =
        this.combineToInt(anObservableNumber, Byte::minus)

/** Creates a new [KIntBinding] that observes [this] and [anObservableNumber] and represents the subtraction of their values. */
operator fun KObservableByteValue<Byte>.minus(anObservableNumber: KObservableShortValue<Short>): KIntBinding<Int> =
        this.combineToInt(anObservableNumber, Byte::minus)

/** Creates a new [KIntBinding] that observes [this] and [anObservableNumber] and represents the subtraction of their values. */
operator fun KObservableByteValue<Byte>.minus(anObservableNumber: KObservableByteValue<Byte>): KIntBinding<Int> =
        this.combineToInt(anObservableNumber, Byte::minus)

/** Creates a new [KDoubleBinding] that observes [this] and [anObservableNumber] and represents the multiplication of their values. */
operator fun KObservableByteValue<Byte>.times(anObservableNumber: KObservableDoubleValue<Double>): KDoubleBinding<Double> =
        this.combineToDouble(anObservableNumber, Byte::times)

/** Creates a new [KFloatBinding] that observes [this] and [anObservableNumber] and represents the multiplication of their values. */
operator fun KObservableByteValue<Byte>.times(anObservableNumber: KObservableFloatValue<Float>): KFloatBinding<Float> =
        this.combineToFloat(anObservableNumber, Byte::times)

/** Creates a new [KLongBinding] that observes [this] and [anObservableNumber] and represents the multiplication of their values. */
operator fun KObservableByteValue<Byte>.times(anObservableNumber: KObservableLongValue<Long>): KLongBinding<Long> =
        this.combineToLong(anObservableNumber, Byte::times)

/** Creates a new [KIntBinding] that observes [this] and [anObservableNumber] and represents the multiplication of their values. */
operator fun KObservableByteValue<Byte>.times(anObservableNumber: KObservableIntValue<Int>): KIntBinding<Int> =
        this.combineToInt(anObservableNumber, Byte::times)

/** Creates a new [KIntBinding] that observes [this] and [anObservableNumber] and represents the multiplication of their values. */
operator fun KObservableByteValue<Byte>.times(anObservableNumber: KObservableShortValue<Short>): KIntBinding<Int> =
        this.combineToInt(anObservableNumber, Byte::times)

/** Creates a new [KIntBinding] that observes [this] and [anObservableNumber] and represents the multiplication of their values. */
operator fun KObservableByteValue<Byte>.times(anObservableNumber: KObservableByteValue<Byte>): KIntBinding<Int> =
        this.combineToInt(anObservableNumber, Byte::times)

/** Creates a new [KDoubleBinding] that observes [this] and [anObservableNumber] and represents the division of their values. */
operator fun KObservableByteValue<Byte>.div(anObservableNumber: KObservableDoubleValue<Double>): KDoubleBinding<Double> =
        this.combineToDouble(anObservableNumber, Byte::div)

/** Creates a new [KFloatBinding] that observes [this] and [anObservableNumber] and represents the division of their values. */
operator fun KObservableByteValue<Byte>.div(anObservableNumber: KObservableFloatValue<Float>): KFloatBinding<Float> =
        this.combineToFloat(anObservableNumber, Byte::div)

/** Creates a new [KLongBinding] that observes [this] and [anObservableNumber] and represents the division of their values. */
operator fun KObservableByteValue<Byte>.div(anObservableNumber: KObservableLongValue<Long>): KLongBinding<Long> =
        this.combineToLong(anObservableNumber, Byte::div)

/** Creates a new [KIntBinding] that observes [this] and [anObservableNumber] and represents the division of their values. */
operator fun KObservableByteValue<Byte>.div(anObservableNumber: KObservableIntValue<Int>): KIntBinding<Int> =
        this.combineToInt(anObservableNumber, Byte::div)

/** Creates a new [KIntBinding] that observes [this] and [anObservableNumber] and represents the division of their values. */
operator fun KObservableByteValue<Byte>.div(anObservableNumber: KObservableShortValue<Short>): KIntBinding<Int> =
        this.combineToInt(anObservableNumber, Byte::div)

/** Creates a new [KIntBinding] that observes [this] and [anObservableNumber] and represents the division of their values. */
operator fun KObservableByteValue<Byte>.div(anObservableNumber: KObservableByteValue<Byte>): KIntBinding<Int> =
        this.combineToInt(anObservableNumber, Byte::div)

/** Creates a new [KByteBinding] that observes [this] and represents its value + 1 */
operator fun KObservableByteValue<Byte>.inc(): KByteBinding<Byte> = this.mapToByte(Byte::inc)

/** Creates a new [KByteBinding] that observes [this] and represents its value - 1 */
operator fun KObservableByteValue<Byte>.dec(): KByteBinding<Byte> = this.mapToByte(Byte::dec)

/** Creates a new [KIntBinding] that observes [this] and represents its negated value */
operator fun KObservableByteValue<Byte>.unaryMinus(): KIntBinding<Int> = this.mapToInt(Byte::unaryMinus)

/** Creates a new [KByteBinding] that observes [this] and represents its absolute value */
fun KObservableByteValue<Byte>.abs(): KByteBinding<Byte> =
        this.mapToByte { if (value < 0) (-1 * value).toByte() else value }