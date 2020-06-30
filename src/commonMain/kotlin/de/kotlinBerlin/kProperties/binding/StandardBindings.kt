@file:Suppress("unused")

package de.kotlinBerlin.kProperties.binding

import de.kotlinBerlin.kProperties.value.KObservableBooleanValue
import de.kotlinBerlin.kProperties.value.KObservableStringValue

/** An [KBinding] wrapping a [Boolean] value. */
interface KBooleanBinding<B : Boolean?> : KBinding<B>, KObservableBooleanValue<B>

/** An [KBinding] wrapping a [String] value. */
interface KStringBinding<S : String?> : KBinding<S>, KObservableStringValue<S>

/** Creates a [KBinding] that uses the given function to calculate its value. */
fun <T : Any?> createBinding(func: () -> T): KBinding<T> {
    return BasicKBinding(func)
}

/** Creates a [KBooleanBinding] that uses the given function to calculate its value. */
fun <B : Boolean?> createBooleanBinding(func: () -> B): KBooleanBinding<B> {
    return object : BasicKBinding<B>(func), KBooleanBinding<B> {}
}

/** Creates a [KStringBinding] that uses the given function to calculate its value. */
fun <S : String?> createStringBinding(func: () -> S): KStringBinding<S> {
    return object : BasicKBinding<S>(func), KStringBinding<S> {}
}