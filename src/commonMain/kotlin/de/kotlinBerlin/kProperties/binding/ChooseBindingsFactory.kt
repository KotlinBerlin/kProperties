@file:Suppress("unused")

package de.kotlinBerlin.kProperties.binding

import de.kotlinBerlin.kProperties.KInvalidationListener
import de.kotlinBerlin.kProperties.WeakKInvalidationListener
import de.kotlinBerlin.kProperties.constants.toObservableConst
import de.kotlinBerlin.kProperties.value.KObservableValue

/**
 * Creates a new [KBinding] that represents a when style binding. In [anInitBlock] you can add a value for the different cases that
 * should be handled. To add these you can use either of the following methods:
 * - [KChooseBindingBuilder.condition]
 * - [KChooseBindingBuilder.case]
 * - [KChooseBindingBuilder.thenMap]
 * - [KChooseBindingBuilder.thenMapValue]
 *
 * Additionally you can have to provide an else value for the case
 * that neither of the defined cases is true with the [KChooseBindingBuilder.otherwise] methods.
 *
 * @see ifBinding
 */
fun <W : Any, T : Any?> chooseBinding(
        aWhenObject: KObservableValue<W>,
        anInitBlock: KChooseBindingBuilder<W, T>.() -> Unit
): KBinding<T> {
    val tempBinding = KChooseBindingBuilder<W, T>(aWhenObject)
    tempBinding.anInitBlock()
    return tempBinding.build()
}

/** A builder for the result part of a case */
class KChooseBindingConditionPartBuilder<W : Any, T : Any?, C : Any?>(
        private val chooseBinding: KChooseBindingBuilder<W, T>,
        private val condition: W.() -> Boolean
) {
    /** Simply sets a value as the value that is returned if the condition for this case is true. */
    infix fun then(aValue: T) {
        then { aValue.toObservableConst() }
    }

    /**
     * Sets an [KObservableValue] as the value that is returned if the condition for this case is true. Its observed for changes
     * and will update the returned binding appropriately.
     */
    infix fun then(block: () -> KObservableValue<T>) {
        chooseBinding.condition(condition, block.invoke())
    }

    /**
     * In addition to the [then] methods provides the [KObservableValue] that was passed in as the aWhenObject to the [chooseBinding] method to be used
     * to calculate the result value.
     */
    infix fun thenMap(block: KObservableValue<C>.() -> KObservableValue<T>) {
        @Suppress("UNCHECKED_CAST")
        chooseBinding.condition(
                @Suppress("UNCHECKED_CAST")
                condition, block.invoke(chooseBinding.whenObject as KObservableValue<C>))
    }

    /**
     * In addition to the [then] methods provides the value of the [KObservableValue] that was passed in as the aWhenObject to the [chooseBinding] method to be used
     * to calculate the result value.
     */
    infix fun thenMapValue(block: C.() -> T) {
        @Suppress("UNCHECKED_CAST")
        chooseBinding.condition(
                @Suppress("UNCHECKED_CAST")
                condition, (chooseBinding.whenObject as KObservableValue<C>).map(block))
    }
}

/** A builder for a when style binding */
class KChooseBindingBuilder<W : Any, T : Any?> internal constructor(
        /** The object that all conditions depend on. */
        val whenObject: KObservableValue<W>
) {

    private val whenBranches: MutableList<Pair<W.() -> Boolean, KObservableValue<T>>>

    private lateinit var elseObservable: KObservableValue<T>

    /** creates a new case for the when binding and defines the condition for it. The result value has to be defined by the returned [KChooseBindingConditionPartBuilder] */
    fun case(aCondition: W.() -> Boolean): KChooseBindingConditionPartBuilder<W, T, W> = KChooseBindingConditionPartBuilder(this, aCondition)

    /** shortcut for creating a new case that as a condition makes an is check. The result value has to be defined by the returned [KChooseBindingConditionPartBuilder] */
    inline fun <reified C : Any> case(): KChooseBindingConditionPartBuilder<W, T, C> =
            KChooseBindingConditionPartBuilder(this) { C::class.isInstance(this) }

    /** Shortcut for a chained call to [case] and [KChooseBindingConditionPartBuilder.thenMap] */
    inline fun <reified C : Any> thenMap(block: KObservableValue<C>.() -> KObservableValue<T>) {
        @Suppress("UNCHECKED_CAST")
        condition({ C::class.isInstance(this) }, block.invoke(whenObject as KObservableValue<C>))
    }

    /** Shortcut for a chained call to [case] and [KChooseBindingConditionPartBuilder.thenMapValue] */
    inline fun <reified C : Any> thenMapValue(noinline block: C.() -> T) {
        @Suppress("UNCHECKED_CAST")
        condition({ C::class.isInstance(this) }, (whenObject as KObservableValue<C>).map(block))
    }

    /** Adds a case with [aCondition] and [aValue] to the when binding */
    fun condition(aCondition: W.() -> Boolean, aValue: KObservableValue<T>) {
        whenBranches.add(Pair(aCondition, aValue))
    }

    /** Simply sets a value as the value that is returned if neither of the defined cases is true */
    fun otherwise(aValue: T) {
        otherwise { aValue.toObservableConst() }
    }

    /**
     * Sets an [KObservableValue] as the value that is returned if neither of the defined cases is true.
     * Its observed for changes and will update the returned binding appropriately.
     */
    fun otherwise(block: () -> KObservableValue<T>) {
        elseObservable = block.invoke()
    }

    internal fun build(): KBinding<T> {
        if (!::elseObservable.isInitialized) throw IllegalStateException("There must be an else value!")

        return object : KObjectBinding<T>() {

            private var currentBranch: (W.() -> Boolean)? = null

            private val whenObjectListener = KInvalidationListener { invalidate() }
            private val whenBranchesListeners: MutableList<Pair<KObservableValue<*>, KInvalidationListener>> =
                    ArrayList()
            private val elseListener = KInvalidationListener { if (currentBranch === null) invalidate() }

            init {
                whenObject.addListener(WeakKInvalidationListener(whenObjectListener))
                elseObservable.addListener(elseListener)
                for (i in 0 until whenBranches.size) {
                    whenBranches[i].apply {
                        val tempResultListener = KInvalidationListener {
                            if (currentBranch === first) {
                                invalidate()
                            }
                        }

                        whenBranchesListeners.add(second to tempResultListener)
                        second.addListener(tempResultListener)
                    }
                }
            }

            override fun onInvalidating() {
                currentBranch = null
            }

            override fun computeValue(): T {
                whenBranches.forEach {
                    if (it.first.invoke(whenObject.value)) {
                        currentBranch = it.first
                        return it.second.value
                    }
                }
                return elseObservable.value
            }

            override fun dispose() {
                whenObject.removeListener(whenObjectListener)
                elseObservable.removeListener(elseListener)
                whenBranchesListeners.forEach {
                    it.first.removeListener(it.second)
                }
                currentBranch = null
            }
        }
    }

    init {
        this.whenBranches = ArrayList()
    }
}

//Fast way, but produces an additional binding object each time. Should be replaced sometimes!

/** Same as [chooseBinding] but always produces a [KBooleanBinding] as its result */
fun <W : Any, B : Boolean?> chooseBoolean(
        aWhenObject: KObservableValue<W>,
        anInitBlock: KChooseBindingBuilder<W, B>.() -> Unit
): KBooleanBinding<B> = chooseBinding(aWhenObject, anInitBlock).toBooleanBinding()

/** Same as [chooseBinding] but always produces a [KStringBinding] as its result */
fun <W : Any, S : String?> chooseString(
        aWhenObject: KObservableValue<W>,
        anInitBlock: KChooseBindingBuilder<W, S>.() -> Unit
): KStringBinding<S> = chooseBinding(aWhenObject, anInitBlock).toStringBinding()

/** Same as [chooseBinding] but always produces a [KNumberBinding] as its result */
fun <W : Any, N : Number?> chooseNumber(
        aWhenObject: KObservableValue<W>,
        anInitBlock: KChooseBindingBuilder<W, N>.() -> Unit
): KNumberBinding<N> = chooseBinding(aWhenObject, anInitBlock).toNumberBinding()

/** Same as [chooseBinding] but always produces a [KDoubleBinding] as its result */
fun <W : Any, D : Double?> chooseDouble(
        aWhenObject: KObservableValue<W>,
        anInitBlock: KChooseBindingBuilder<W, D>.() -> Unit
): KDoubleBinding<D> = chooseBinding(aWhenObject, anInitBlock).toDoubleBinding()

/** Same as [chooseBinding] but always produces a [KFloatBinding] as its result */
fun <W : Any, F : Float?> chooseFloat(
        aWhenObject: KObservableValue<W>,
        anInitBlock: KChooseBindingBuilder<W, F>.() -> Unit
): KFloatBinding<F> = chooseBinding(aWhenObject, anInitBlock).toFloatBinding()

/** Same as [chooseBinding] but always produces a [KLongBinding] as its result */
fun <W : Any, L : Long?> chooseLong(
        aWhenObject: KObservableValue<W>,
        anInitBlock: KChooseBindingBuilder<W, L>.() -> Unit
): KLongBinding<L> = chooseBinding(aWhenObject, anInitBlock).toLongBinding()

/** Same as [chooseBinding] but always produces a [KIntBinding] as its result */
fun <W : Any, I : Int?> chooseInt(
        aWhenObject: KObservableValue<W>,
        anInitBlock: KChooseBindingBuilder<W, I>.() -> Unit
): KIntBinding<I> = chooseBinding(aWhenObject, anInitBlock).toIntBinding()

/** Same as [chooseBinding] but always produces a [KShortBinding] as its result */
fun <W : Any, S : Short?> chooseShort(
        aWhenObject: KObservableValue<W>,
        anInitBlock: KChooseBindingBuilder<W, S>.() -> Unit
): KShortBinding<S> = chooseBinding(aWhenObject, anInitBlock).toShortBinding()

/** Same as [chooseBinding] but always produces a [KByteBinding] as its result */
fun <W : Any, B : Byte?> chooseByte(
        aWhenObject: KObservableValue<W>,
        anInitBlock: KChooseBindingBuilder<W, B>.() -> Unit
): KByteBinding<B> = chooseBinding(aWhenObject, anInitBlock).toByteBinding()