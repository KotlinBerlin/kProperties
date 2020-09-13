package de.kotlinBerlin.kProperties.value

/** An [KWritableValue] wraps a value and allows to observe the value for changes as well as modify the value. */
interface KWritableValue<T> : KObservableValue<T> {

    /** This property represents the value of this [KWritableValue] */
    override var value: T
}