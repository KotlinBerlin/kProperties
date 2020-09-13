package de.kotlinBerlin.kProperties.property

import de.kotlinBerlin.kProperties.value.KObservableValue
import de.kotlinBerlin.kProperties.value.KWritableValue

/**
 * In addition to an [KReadOnlyProperty] a [KProperty] also implements the [KWritableValue] interface and
 * so its value can be modified.
 *
 * Additionally it offers support to bind it to another [KProperty] instance either uni- or bidirectional with the
 * [bind] and [bindBidirectional] methods. To remove a binding there are the [unbind] and the [unbindBidirectional]
 * methods.
 *
 * The value of a [KProperty] that is bound unidirectional to another [KProperty] may not be changed manually.
 *
 * @see [KObservableValue]
 * @see [KWritableValue]
 */
interface KProperty<T> : KWritableValue<T>, KReadOnlyProperty<T> {

    /**
     * The valid state represents whether or not the current value of the [KProperty] instance
     * is valid or should be recomputed.
     */
    val valid: Boolean

    /**
     * Binds this [KProperty] to the value of [anObservable]. The value of this [KProperty] may not be changed
     * manually as long as it is bound to another [KProperty].
     *
     * If this [KProperty] is already bound the this binding will be removed first.
     */
    fun bind(anObservable: KObservableValue<T>)

    /**
     * Removes the current binding to another [KProperty].
     * If this [KProperty] is not bound then this method is a no-op.
     */
    fun unbind()

    /**
     * Returns whether  or not this [KProperty] is currently bound unidirectional to another [KProperty].
     */
    fun isBound(): Boolean

    /**
     * Creates a bidirectional binding between this [KProperty] and [aProperty]. In contrast to the [bind] method
     * the value of this [KProperty] may be changed manually even if it is bound bidirectionally to another [KProperty].
     *
     * One [KProperty] may be bound bidirectionally to more than one other [KProperty].
     */
    fun bindBidirectional(aProperty: KProperty<T>)

    /**
     * Removes the bidirectional binding between this [KProperty] and [aProperty].
     */
    fun unbindBidirectional(aProperty: KProperty<T>)
}