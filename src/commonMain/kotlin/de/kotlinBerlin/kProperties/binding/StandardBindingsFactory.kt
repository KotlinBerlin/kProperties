@file:Suppress("unused")

package de.kotlinBerlin.kProperties.binding

import de.kotlinBerlin.kProperties.value.KObservableValue

//Convert

/**
 * Returns a [KBooleanBinding]. If [this] already is an instance of [KBooleanBinding] it is simply returned.
 * Otherwise a new [KBooleanBinding] is created which observes this [KObservableValue] for changes.
 */
fun <B : Boolean?> KObservableValue<B>.toBooleanBinding(): KBooleanBinding<B> =
        if (this is KBooleanBinding<B>) this else this.mapToBoolean { this }

/**
 * Returns a [KStringBinding]. If [this] already is an instance of [KStringBinding] it is simply returned.
 * Otherwise a new [KStringBinding] is created which observes this [KObservableValue] for changes.
 */
fun <S : String?> KObservableValue<S>.toStringBinding(): KStringBinding<S> =
        if (this is KStringBinding<S>) this else this.mapToString { this }

//Equals

/**
 * Creates a [KBooleanBinding] that observes [this] and [anObservable] for changes and checks whether or not
 * their values are equal.
 */
fun KObservableValue<Any?>.isEqual(anObservable: KObservableValue<Any?>): KBooleanBinding<Boolean> =
        this.combineToBoolean(anObservable) { anOtherObject -> this == anOtherObject }

/**
 * Creates a [KBooleanBinding] that observes this [KObservableValue] for changes and checks whether or not
 * its value is equal to [aValue].
 */
fun KObservableValue<Any?>.isValueEqual(aValue: Any?): KBooleanBinding<Boolean> = this.mapToBoolean { this == aValue }

/**
 * Creates a [KBooleanBinding] that observes [this] and [anObservable] for changes and checks whether or not
 * their values are unequal.
 */
fun KObservableValue<Any?>.isNotEqual(anObservable: KObservableValue<Any?>): KBooleanBinding<Boolean> =
        this.combineToBoolean(anObservable) { anOtherObject -> this != anOtherObject }

/** Creates a [KBooleanBinding] that observes [this] for changes and check whether or not its value is unequal to [aValue]. */
fun KObservableValue<Any?>.isValueNotEqual(aValue: Any?): KBooleanBinding<Boolean> =
        this.mapToBoolean { this != aValue }

/** Creates a [KBooleanBinding] that observes [this] for changes and check whether or not its value is null. */
fun KObservableValue<Any?>.isNull(): KBooleanBinding<Boolean> = this.mapToBoolean { this == null }

/** Creates a [KBooleanBinding] that observes [this] for changes and check whether or not its value is not null. */
fun KObservableValue<Any?>.isNotNull(): KBooleanBinding<Boolean> = this.mapToBoolean { this != null }

//To String

/**
 * Create a [KStringBinding] that observes [this] and represents its value as a [String]
 *
 * @see Any.toString
 */
fun KObservableValue<Any?>.asString(): KStringBinding<String> = this.mapToString(Any?::toString)

//Compare

/**
 * Creates a [KBooleanBinding] that observes [this] and [anObservable] for changes and
 * check whether or not the value of [this] is smaller than the value of [anObservable].
 */
fun <T : Comparable<T>> KObservableValue<T>.isSmaller(anObservable: KObservableValue<T>): KBooleanBinding<Boolean> =
        this.combineToBoolean(anObservable) { anOtherObject -> this < anOtherObject }

/**
 * Creates a [KBooleanBinding] that observes [this] for changes and
 * check whether or not its value is smaller than [aValue].
 */
fun <T : Comparable<T>> KObservableValue<T>.isValueSmaller(aValue: T): KBooleanBinding<Boolean> =
        this.mapToBoolean { this < aValue }

/**
 * Creates a [KBooleanBinding] that observes [this] and [anObservable] for changes and
 * check whether or not the value of [this] is greater than the value of [anObservable].
 */
fun <T : Comparable<T>> KObservableValue<T>.isGreater(anObservable: KObservableValue<T>): KBooleanBinding<Boolean> =
        this.combineToBoolean(anObservable) { anOtherObject -> this > anOtherObject }

/**
 * Creates a [KBooleanBinding] that observes [this] for changes and
 * check whether or not its value is greater than [aValue].
 */
fun <T : Comparable<T>> KObservableValue<T>.isValueGreater(aValue: T): KBooleanBinding<Boolean> =
        this.mapToBoolean { this > aValue }

/**
 * Creates a [KBooleanBinding] that observes [this] and [anObservable] for changes and
 * check whether or not the value of [this] is smaller or equal to the value of [anObservable].
 */
fun <T : Comparable<T>> KObservableValue<T>.isSmallerOrEqual(anObservable: KObservableValue<T>): KBooleanBinding<Boolean> =
        this.combineToBoolean(anObservable) { anOtherObject -> this <= anOtherObject }

/**
 * Creates a [KBooleanBinding] that observes [this] for changes and
 * check whether or not its value is smaller or equal to [aValue].
 */
fun <T : Comparable<T>> KObservableValue<T>.isValueSmallerOrEqual(aValue: T): KBooleanBinding<Boolean> =
        this.mapToBoolean { this <= aValue }

/**
 * Creates a [KBooleanBinding] that observes [this] and [anObservable] for changes and
 * check whether or not the value of [this] is greater or equal to the value of [anObservable].
 */
fun <T : Comparable<T>> KObservableValue<T>.isGreaterOrEqual(anObservable: KObservableValue<T>): KBooleanBinding<Boolean> =
        this.combineToBoolean(anObservable) { anOtherObject -> this >= anOtherObject }

/**
 * Creates a [KBooleanBinding] that observes [this] for changes and
 * check whether or not its value is greater or equal to [aValue].
 */
fun <T : Comparable<T>> KObservableValue<T>.isGreaterOrEqual(aValue: T): KBooleanBinding<Boolean> =
        this.mapToBoolean { this >= aValue }