@file:Suppress("unused")

package de.kotlinBerlin.kProperties.binding

import de.kotlinBerlin.kProperties.KInvalidationListener
import de.kotlinBerlin.kProperties.WeakKInvalidationListener
import de.kotlinBerlin.kProperties.constants.toObservableConst
import de.kotlinBerlin.kProperties.value.KObservableValue

/**
 * Creates a new [KBinding] that represents a if the else style binding. In [anInitBlock] you can add a value for the case that
 * [aCondition] with the [KIfBindingBuilder.then] methods. Additionally you can have to provide an else value for the case
 * that [aCondition] is false with the [KIfBindingBuilder.otherwise] methods. Alternatively you can use the
 * [KIfBindingBuilder.elseIf] method to create a nested condition that itself can must be configured by its own [KIfBindingBuilder].
 *
 * If you get many nested elseIf statements then its better to use the [chooseBinding] method, that creates only a single [KBinding] for all cases
 * whereas this will produce one additional [KBinding] instance per elseIf statement.
 *
 * @see chooseBinding
 */
fun <T : Any?> ifBinding(aCondition: KObservableValue<Boolean>, anInitBlock: KIfBindingBuilder<T>.() -> Unit): KBinding<T> {
    val tempBinding = KIfBindingBuilder<T>(aCondition)
    tempBinding.anInitBlock()
    return tempBinding.build()
}

/** A builder for a if then else style binding */
class KIfBindingBuilder<T : Any?> internal constructor(private val condition: KObservableValue<Boolean>) {

    private lateinit var thenObservable: KObservableValue<T>

    private lateinit var elseObservable: KObservableValue<T>

    /** Simply sets a value as the value that is returned if the condition is true. */
    fun then(aValue: T) {
        then { aValue.toObservableConst() }
    }

    /**
     * Sets an [KObservableValue] as the value that is returned if the condition is true. Its observed for changes
     * and will update the returned binding appropriately.
     */
    fun then(block: () -> KObservableValue<T>) {
        thenObservable = block.invoke()
    }

    /** Allows to nest another if condition as the value for the case where the condition is true. */
    fun then(aCondition: KObservableValue<Boolean>, anInitBlock: KIfBindingBuilder<T>.() -> Unit) {
        thenObservable = ifBinding(aCondition, anInitBlock)
    }

    /** Simply sets a value as the value that is returned if the condition is false. */
    fun otherwise(aValue: T) {
        otherwise { aValue.toObservableConst() }
    }

    /**
     * Sets an [KObservableValue] as the value that is returned if the condition is false. Its observed for changes
     * and will update the returned binding appropriately.
     */
    fun otherwise(block: () -> KObservableValue<T>) {
        elseObservable = block.invoke()
    }

    /** Allows to nest another if condition as the value for the case where the condition is false. */
    fun elseIf(aCondition: KObservableValue<Boolean>, anInitBlock: KIfBindingBuilder<T>.() -> Unit) {
        elseObservable = ifBinding(aCondition, anInitBlock)
    }

    internal fun build(): KBinding<T> {
        if (!::thenObservable.isInitialized) throw IllegalStateException("There must be a then value!")
        if (!::elseObservable.isInitialized) throw IllegalStateException("There must be an else value!")

        return object : KObjectBinding<T>() {

            private val conditionListener = KInvalidationListener { invalidate() }
            private val thenListener = KInvalidationListener { if (condition.value) invalidate() }
            private val elseListener = KInvalidationListener { if (!condition.value) invalidate() }

            init {
                condition.addListener(WeakKInvalidationListener(conditionListener))
                thenObservable.addListener(WeakKInvalidationListener(thenListener))
                elseObservable.addListener(WeakKInvalidationListener(elseListener))
            }

            override fun computeValue(): T {
                return if (condition.value) thenObservable.value else elseObservable.value
            }

            override fun dispose() {
                condition.removeListener(conditionListener)
                thenObservable.removeListener(thenListener)
                elseObservable.removeListener(elseListener)
            }
        }
    }
}

//Fast way, but produces an additional binding object each time. Should be replaced sometimes!

/** Same as [ifBinding] but always produces a [KBooleanBinding] as its result */
fun <B : Boolean?> booleanIfBinding(
        aCondition: KObservableValue<Boolean>,
        anInitBlock: KIfBindingBuilder<B>.() -> Unit
): KBooleanBinding<B> = ifBinding(aCondition, anInitBlock).toBooleanBinding()


/** Same as [ifBinding] but always produces a [KStringBinding] as its result */
fun <S : String?> stringIfBinding(
        aCondition: KObservableValue<Boolean>,
        anInitBlock: KIfBindingBuilder<S>.() -> Unit
): KStringBinding<S> = ifBinding(aCondition, anInitBlock).toStringBinding()


/** Same as [ifBinding] but always produces a [KNumberBinding] as its result */
fun <N : Number?> numberIfBinding(
        aCondition: KObservableValue<Boolean>,
        anInitBlock: KIfBindingBuilder<N>.() -> Unit
): KNumberBinding<N> = ifBinding(aCondition, anInitBlock).toNumberBinding()


/** Same as [ifBinding] but always produces a [KDoubleBinding] as its result */
fun <D : Double?> doubleIfBinding(
        aCondition: KObservableValue<Boolean>,
        anInitBlock: KIfBindingBuilder<D>.() -> Unit
): KDoubleBinding<D> {
    return ifBinding(aCondition, anInitBlock).toDoubleBinding()
}

/** Same as [ifBinding] but always produces a [KFloatBinding] as its result */
fun <F : Float?> floatIfBinding(
        aCondition: KObservableValue<Boolean>,
        anInitBlock: KIfBindingBuilder<F>.() -> Unit
): KFloatBinding<F> {
    return ifBinding(aCondition, anInitBlock).toFloatBinding()
}

/** Same as [ifBinding] but always produces a [KLongBinding] as its result */
fun <L : Long?> longIfBinding(
        aCondition: KObservableValue<Boolean>,
        anInitBlock: KIfBindingBuilder<L>.() -> Unit
): KLongBinding<L> {
    return ifBinding(aCondition, anInitBlock).toLongBinding()
}

/** Same as [ifBinding] but always produces a [KIntBinding] as its result */
fun <I : Int?> intIfBinding(
        aCondition: KObservableValue<Boolean>,
        anInitBlock: KIfBindingBuilder<I>.() -> Unit
): KIntBinding<I> {
    return ifBinding(aCondition, anInitBlock).toIntBinding()
}

/** Same as [ifBinding] but always produces a [KShortBinding] as its result */
fun <S : Short?> shortIfBinding(
        aCondition: KObservableValue<Boolean>,
        anInitBlock: KIfBindingBuilder<S>.() -> Unit
): KShortBinding<S> {
    return ifBinding(aCondition, anInitBlock).toShortBinding()
}

/** Same as [ifBinding] but always produces a [KByteBinding] as its result */
fun <B : Byte?> byteIfBinding(
        aCondition: KObservableValue<Boolean>,
        anInitBlock: KIfBindingBuilder<B>.() -> Unit
): KByteBinding<B> {
    return ifBinding(aCondition, anInitBlock).toByteBinding()
}