package de.kotlinBerlin.kProperties.binding

import de.kotlinBerlin.kProperties.KInvalidationListener
import de.kotlinBerlin.kProperties.WeakKInvalidationListener
import de.kotlinBerlin.kProperties.collection.*
import de.kotlinBerlin.kProperties.value.*

internal open class BasicKBinding<out T>(private val func: () -> T) : KObjectBinding<T>() {
    override fun computeValue(): T {
        return func.invoke()
    }
}

internal open class BasicUnaryKBinding<T, out B>(
        private val observable: KObservableValue<T>, func: (KObservableValue<T>) -> B
) : BasicKBinding<B>({ func.invoke(observable) }) {

    private val listener = KInvalidationListener { invalidate() }
    private val weakListener = WeakKInvalidationListener(listener)

    init {
        observable.addListener(weakListener)
    }

    override fun dispose() {
        observable.removeListener(weakListener)
    }
}

internal open class ComplexBinaryKBinding<T, T1, out B>(
        private val observable1: KObservableValue<T>,
        private val observable2: KObservableValue<T1>,
        func: (KObservableValue<T>, KObservableValue<T1>) -> B
) : BasicKBinding<B>({ func.invoke(observable1, observable2) }) {

    private val listener = KInvalidationListener { invalidate() }
    private val weakListener = WeakKInvalidationListener(listener)

    init {
        observable1.addListener(weakListener)
        observable2.addListener(weakListener)
    }

    override fun dispose() {
        observable1.removeListener(weakListener)
        observable2.removeListener(weakListener)
    }
}

internal open class ComplexKBinding<T, out B>(
        private vararg val observables: KObservableValue<T>, func: (Array<out KObservableValue<T>>) -> B
) : BasicKBinding<B>({ func.invoke(observables) }) {

    private val listener = KInvalidationListener { invalidate() }
    private val weakListener = WeakKInvalidationListener(listener)

    init {
        for (observable in observables) {
            observable.addListener(weakListener)
        }
    }

    override fun dispose() {
        for (observable in observables) {
            observable.removeListener(weakListener)
        }
    }
}

internal open class BasicKMutableCollectionBinding<E, C : KObservableMutableCollection<E>?>(private val func: () -> C) :
        BasicKObservableMutableCollectionValue<E, C>(), KMutableCollectionBinding<E, C> {

    final override var valid: Boolean = false
        private set

    private var internalValue: C? = null

    @Suppress("UNCHECKED_CAST")
    override val value: C
        get() {
            if (!valid) {
                internalValue = func.invoke()
                valid = true
                value?.addListener(this)
            }
            return internalValue as C
        }

    protected open fun onInvalidating() {
        //Empty default implementation. Can be overridden to perform additional actions!
    }

    override fun invalidate() {
        if (valid) {
            value?.removeListener(this)
            valid = false
            onInvalidating()
            fireValueChangedEvent()
        }
    }

    override fun dispose() {
        //Nothing to do by default!
    }
}

internal open class BasicKMutableListBinding<E, L : KObservableMutableList<E>?>(private val func: () -> L) :
        BasicKObservableMutableListValue<E, L>(), KMutableListBinding<E, L> {

    final override var valid: Boolean = false
        private set

    private var internalValue: L? = null

    @Suppress("UNCHECKED_CAST")
    override val value: L
        get() {
            if (!valid) {
                internalValue = func.invoke()
                valid = true
                value?.addListener(this)
            }
            return internalValue as L
        }

    protected open fun onInvalidating() {
        //Empty default implementation. Can be overridden to perform additional actions!
    }

    override fun invalidate() {
        if (valid) {
            value?.removeListener(this)
            valid = false
            onInvalidating()
            fireValueChangedEvent()
        }
    }

    override fun dispose() {
        //Nothing to do by default!
    }
}

internal open class BasicKMutableSetBinding<E, S : KObservableMutableSet<E>?>(private val func: () -> S) :
        BasicKObservableMutableSetValue<E, S>(), KMutableSetBinding<E, S> {

    final override var valid: Boolean = false
        private set

    private var internalValue: S? = null

    @Suppress("UNCHECKED_CAST")
    override val value: S
        get() {
            if (!valid) {
                internalValue = func.invoke()
                valid = true
                value?.addListener(this)
            }
            return internalValue as S
        }

    protected open fun onInvalidating() {
        //Empty default implementation. Can be overridden to perform additional actions!
    }

    override fun invalidate() {
        if (valid) {
            value?.removeListener(this)
            valid = false
            onInvalidating()
            fireValueChangedEvent()
        }
    }

    override fun dispose() {
        //Nothing to do by default!
    }
}

internal open class BasicKMutableMapBinding<K, V, M : KObservableMutableMap<K, V>?>(private val func: () -> M) :
        BasicKObservableMutableMapValue<K, V, M>(), KMutableMapBinding<K, V, M> {

    final override var valid: Boolean = false
        private set

    private var internalValue: M? = null

    @Suppress("UNCHECKED_CAST")
    override val value: M
        get() {
            if (!valid) {
                internalValue = func.invoke()
                valid = true
                value?.addListener(this)
            }
            return internalValue as M
        }

    protected open fun onInvalidating() {
        //Empty default implementation. Can be overridden to perform additional actions!
    }

    override fun invalidate() {
        if (valid) {
            value?.removeListener(this)
            valid = false
            onInvalidating()
            fireValueChangedEvent()
        }
    }

    override fun dispose() {
        //Nothing to do by default!
    }
}
