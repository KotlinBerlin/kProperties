package de.kotlinBerlin.kProperties.property

import de.kotlinBerlin.kProperties.value.KObservableBooleanValue
import de.kotlinBerlin.kProperties.value.KObservableStringValue

/** An [KReadOnlyProperty] wrapping a [Boolean] value. */
interface KReadOnlyBooleanProperty<B : Boolean?> : KReadOnlyProperty<B>, KObservableBooleanValue<B>

/** An [KReadOnlyProperty] wrapping a [String] value. */
interface KReadOnlyStringProperty<S : String?> : KReadOnlyProperty<S>, KObservableStringValue<S>