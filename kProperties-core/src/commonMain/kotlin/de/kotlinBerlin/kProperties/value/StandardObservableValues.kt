package de.kotlinBerlin.kProperties.value

/** An observable [Boolean] value. */
interface KObservableBooleanValue<out B : Boolean?> : KObservableValue<B>

/** An observable [String] value. */
interface KObservableStringValue<out S : String?> : KObservableValue<S>