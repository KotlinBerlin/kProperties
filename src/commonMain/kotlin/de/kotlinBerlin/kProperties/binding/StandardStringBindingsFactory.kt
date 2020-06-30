@file:Suppress("unused")

package de.kotlinBerlin.kProperties.binding

import de.kotlinBerlin.kProperties.value.KObservableStringValue

/**
 * Returns an [KBooleanBinding] which observes both [this] and [anObservable]
 * and represents whether or not the values of those are equal,
 * optionally ignoring the case of the [String] instances.
 *
 * @see String.equals
 */
fun KObservableStringValue<String?>.isEqual(
        anObservable: KObservableStringValue<String?>,
        anIgnoreCaseFlag: Boolean = false
): KBooleanBinding<Boolean> =
        this.combineToBoolean(anObservable) { anOtherString -> this.equals(anOtherString, anIgnoreCaseFlag) }

/**
 * Returns an [KBooleanBinding] which observes [this]
 * and represents whether or not the value of [this] is equal to [aValue],
 * optionally ignoring the case of the [String] instances.
 *
 * @see String.equals
 */
fun KObservableStringValue<String?>.isEqual(
        aValue: String?,
        anIgnoreCaseFlag: Boolean = false
): KBooleanBinding<Boolean> = this.combineValueToBoolean(aValue) { anOtherString -> this.equals(anOtherString, anIgnoreCaseFlag) }

/**
 * Returns an [KStringBinding] which observes both [this] and [anObservable]
 * and represents the concatenated value of both of their values.
 *
 * @see String.plus
 */
operator fun KObservableStringValue<String>.plus(anObservable: KObservableStringValue<String>): KStringBinding<String> =
        this.combineToString(anObservable, String::plus)

/**
 * Returns an [KStringBinding] which observes [this] and represents the concatenated value of [this] and [aValue].
 *
 * @see String.plus
 */
operator fun KObservableStringValue<String>.plus(aValue: String): KStringBinding<String> =
        this.combineValueToString(aValue, String::plus)

/**
 * Returns an [KStringBinding] which observes [this] and represents whether or not the value is empty.
 *
 * @see String.isEmpty
 */
fun KObservableStringValue<String>.isEmpty(): KBooleanBinding<Boolean> = this.mapToBoolean(String::isEmpty)

/**
 * Returns an [KStringBinding] which observes [this] and represents whether or not the value is not empty.
 *
 * @see String.isNotEmpty
 */
fun KObservableStringValue<String>.isNotEmpty(): KBooleanBinding<Boolean> = this.mapToBoolean(String::isNotEmpty)

/**
 * Returns an [KStringBinding] which observes [this] and represents whether or not the value is blank.
 *
 * @see String.isBlank
 */
fun KObservableStringValue<String>.isBlank(): KBooleanBinding<Boolean> = this.mapToBoolean(String::isBlank)

/**
 * Returns an [KStringBinding] which observes [this] and represents whether or not the value is not blank.
 *
 * @see String.isNotBlank
 */
fun KObservableStringValue<String>.isNotBlank(): KBooleanBinding<Boolean> = this.mapToBoolean(String::isNotBlank)

/**
 * Returns an [KStringBinding] which observes [this] and represents whether or not the value is null or blank.
 *
 * @see String.isNullOrBlank
 */
fun KObservableStringValue<String>.isNullOrBlank(): KBooleanBinding<Boolean> = this.mapToBoolean(String::isNullOrBlank)

/**
 * Returns an [KStringBinding] which observes [this] and represents whether or not the value is null or empty.
 *
 * @see String.isNullOrEmpty
 */
fun KObservableStringValue<String>.isNullOrEmpty(): KBooleanBinding<Boolean> = this.mapToBoolean(String::isNullOrEmpty)

/**
 * Returns an [KIntBinding] which observes [this] and represents the length of its value.
 *
 * @see String.length
 */
fun KObservableStringValue<String>.length(): KIntBinding<Int> = this.mapToInt(String::length)