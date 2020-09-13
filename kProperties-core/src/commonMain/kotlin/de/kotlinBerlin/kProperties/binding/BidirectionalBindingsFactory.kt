package de.kotlinBerlin.kProperties.binding

import de.kotlinBerlin.kProperties.property.KProperty
import de.kotlinBerlin.kProperties.util.WeakReference
import de.kotlinBerlin.kProperties.value.KChangeListener
import de.kotlinBerlin.kProperties.value.KObservableValue

private class BidirectionalBinding<T, T1>(
        firstProp: KProperty<T>,
        secondProp: KProperty<T1>,
        private val toFirstMapper: (T1) -> T,
        private val toSecondMapper: (T) -> T1
) : KChangeListener<Any?> {

    private val firstPropertyRef = WeakReference(firstProp)

    private val secondPropertyRef = WeakReference(secondProp)

    private var updating: Boolean = false

    private val firstProp
        get() = firstPropertyRef.wrapped

    private val secondProp
        get() = secondPropertyRef.wrapped

    override fun onChange(anObservable: KObservableValue<Any?>, anOldValue: Any?, aNewValue: Any?) {
        val tempFirstProp = firstProp
        val tempSecondProp = secondProp

        if (tempFirstProp == null || tempSecondProp == null) {
            tempFirstProp?.removeListener(this)
            tempSecondProp?.removeListener(this)
            return
        }
        if (!updating) {
            try {
                updating = true
                if (tempFirstProp == anObservable) {
                    tempSecondProp.value = toSecondMapper.invoke(tempFirstProp.value)
                } else {
                    tempFirstProp.value = toFirstMapper.invoke(tempSecondProp.value)
                }
            } catch (e: Exception) {
                //Just ignore it and keep old value!
            } finally {
                updating = false
            }
        }
    }

    override fun hashCode(): Int = firstProp.hashCode() + secondProp.hashCode()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (firstProp == null || secondProp === null) return false
        if (other is BidirectionalBinding<*, *>) {
            if (other.firstProp == null || other.secondProp == null) return false
            if (firstProp === other.firstProp && secondProp === other.secondProp) return true
            return secondProp === other.firstProp && firstProp === other.secondProp
        }
        return false
    }
}

/**
 * Creates a bidirectional binding between the two [KProperty] instances.
 *
 * @see KProperty.bindBidirectional
 */
fun <T> bindBidirectional(firstProp: KProperty<T>, secondProp: KProperty<T>) {
    firstProp.value = secondProp.value
    val tempBinding = BidirectionalBinding(
            firstProp,
            secondProp,
            { it },
            { it })
    firstProp.addListener(tempBinding)
    secondProp.addListener(tempBinding)
}

/**
 * Removed a bidirectional binding between the two [KProperty] instances.
 *
 * @see KProperty.unbindBidirectional
 */
@Suppress("UNCHECKED_CAST")
fun <T, T1> unbindBidirectional(firstProp: KProperty<T>, secondProp: KProperty<T1>) {
    val tempBinding = BidirectionalBinding(
            firstProp,
            secondProp,
            { it as T },
            { it as T1 })
    firstProp.removeListener(tempBinding)
    secondProp.removeListener(tempBinding)
}