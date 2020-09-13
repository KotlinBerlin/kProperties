@file:Suppress("unused")

package de.kotlinBerlin.kProperties.binding

import de.kotlinBerlin.kProperties.value.KObservableBooleanValue
import de.kotlinBerlin.kProperties.value.KObservableValue

/** Creates a new [KBooleanBinding] that observes [this] and all of [anObservableList] and represents all of its values combined with the && operator. */
fun KObservableBooleanValue<Boolean>.and(vararg anObservableList: KObservableValue<Boolean>): KBooleanBinding<Boolean> =
        (this.combineObservables(*anObservableList, aMapper = Boolean::and)).toBooleanBinding()

/** Creates a new [KBooleanBinding] that observes [this] and all of [anObservableList] and represents all of its values combined with the || operator. */
fun KObservableBooleanValue<Boolean>.or(vararg anObservableList: KObservableValue<Boolean>): KBooleanBinding<Boolean> =
        (this.combineObservables(*anObservableList, aMapper = Boolean::or)).toBooleanBinding()

/** Creates a new [KBooleanBinding] that observes [this] and represents its negated value. */
operator fun KObservableBooleanValue<Boolean>.not(): KBooleanBinding<Boolean> = this.mapToBoolean(Boolean::not)