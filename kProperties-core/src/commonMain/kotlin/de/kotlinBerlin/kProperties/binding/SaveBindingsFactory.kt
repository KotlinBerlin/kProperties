@file:Suppress("unused")

package de.kotlinBerlin.kProperties.binding

import de.kotlinBerlin.kProperties.value.KObservableValue

/** Creates a new [KBinding] that observes [this] and represents its value or, if that is null, the [aDefaultValue] */
fun <T> KObservableValue<T?>.toSave(aDefaultValue: T): KBinding<T> = this.map { this ?: aDefaultValue }

/** Creates a new [KBooleanBinding] that observes [this] and represents its value or, if that is null, the [aDefaultValue] */
fun KObservableValue<Boolean?>.toSaveBoolean(aDefaultValue: Boolean): KBooleanBinding<Boolean> =
        this.mapToBoolean { this ?: aDefaultValue }

/** Creates a new [KStringBinding] that observes [this] and represents its value or, if that is null, the [aDefaultValue] */
fun KObservableValue<String?>.toSaveString(aDefaultValue: String): KStringBinding<String> =
        this.mapToString { this ?: aDefaultValue }

/** Creates a new [KNumberBinding] that observes [this] and represents its value or, if that is null, the [aDefaultValue] */
fun KObservableValue<Number?>.toSaveNumber(aDefaultValue: Number): KNumberBinding<Number> =
        this.mapToNumber { this ?: aDefaultValue }

/** Creates a new [KDoubleBinding] that observes [this] and represents its value or, if that is null, the [aDefaultValue] */
fun KObservableValue<Double?>.toSaveDouble(aDefaultValue: Double): KDoubleBinding<Double> =
        this.mapToDouble { this ?: aDefaultValue }

/** Creates a new [KFloatBinding] that observes [this] and represents its value or, if that is null, the [aDefaultValue] */
fun KObservableValue<Float?>.toSaveFloat(aDefaultValue: Float): KFloatBinding<Float> =
        this.mapToFloat { this ?: aDefaultValue }

/** Creates a new [KLongBinding] that observes [this] and represents its value or, if that is null, the [aDefaultValue] */
fun KObservableValue<Long?>.toSaveLong(aDefaultValue: Long): KLongBinding<Long> =
        this.mapToLong { this ?: aDefaultValue }

/** Creates a new [KIntBinding] that observes [this] and represents its value or, if that is null, the [aDefaultValue] */
fun KObservableValue<Int?>.toSaveInt(aDefaultValue: Int): KIntBinding<Int> = this.mapToInt { this ?: aDefaultValue }

/** Creates a new [KShortBinding] that observes [this] and represents its value or, if that is null, the [aDefaultValue] */
fun KObservableValue<Short?>.toSaveShort(aDefaultValue: Short): KShortBinding<Short> =
        this.mapToShort { this ?: aDefaultValue }

/** Creates a new [KByteBinding] that observes [this] and represents its value or, if that is null, the [aDefaultValue] */
fun KObservableValue<Byte?>.toSaveByte(aDefaultValue: Byte): KByteBinding<Byte> =
        this.mapToByte { this ?: aDefaultValue }
