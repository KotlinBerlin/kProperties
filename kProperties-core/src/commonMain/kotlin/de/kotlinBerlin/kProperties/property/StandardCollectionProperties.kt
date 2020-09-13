@file:Suppress("unused")

package de.kotlinBerlin.kProperties.property

import de.kotlinBerlin.kProperties.KInvalidationListener
import de.kotlinBerlin.kProperties.WeakKInvalidationListener
import de.kotlinBerlin.kProperties.collection.*
import de.kotlinBerlin.kProperties.value.*
import kotlin.properties.ReadOnlyProperty

/** An [KProperty] wrapping a [KObservableCollection] value. */
interface KCollectionProperty<E, C : KObservableCollection<E>?> : KProperty<C>, KObservableCollectionValue<E, C>

/** An [KProperty] wrapping a [KObservableMutableCollection] value. */
interface KMutableCollectionProperty<E, C : KObservableMutableCollection<E>?> : KCollectionProperty<E, C>,
    KObservableMutableCollectionValue<E, C>

/** An [KProperty] wrapping a [KObservableList] value. */
interface KListProperty<E, L : KObservableList<E>?> : KProperty<L>, KObservableListValue<E, L>

/** An [KProperty] wrapping a [KObservableMutableList] value. */
interface KMutableListProperty<E, L : KObservableMutableList<E>?> : KListProperty<E, L>,
    KObservableMutableListValue<E, L>

/** An [KProperty] wrapping a [KObservableMutableSet] value. */
interface KMutableSetProperty<E, S : KObservableMutableSet<E>?> : KProperty<S>, KObservableMutableSetValue<E, S>

/** An [KProperty] wrapping a [KObservableMap] value. */
interface KMapProperty<K, V, M : KObservableMap<K, V>?> : KProperty<M>, KObservableMapValue<K, V, M>

/** An [KProperty] wrapping a [KObservableMutableMap] value. */
interface KMutableMapProperty<K, V, M : KObservableMutableMap<K, V>?> : KMapProperty<K, V, M>,
    KObservableMutableMapValue<K, V, M>

private const val BOUND_ERROR_MESSAGE = "A bound value can not be set!"

/** An [KProperty] implementation for [KObservableMutableCollection] objects. */
open class BasicKMutableCollectionProperty<E, C : KObservableMutableCollection<E>?>(
    override val bean: Any?,
    override val name: String?,
    aValue: C
) : BasicKObservableMutableCollectionValue<E, C>(), KMutableCollectionProperty<E, C> {

    override var valid: Boolean = true
        protected set
    private var observable: KObservableValue<C>? = null

    private val listener: KInvalidationListener by lazy { KInvalidationListener { markInvalid() } }
    private val weakListener: WeakKInvalidationListener by lazy { WeakKInvalidationListener(listener) }

    private var _value: C = aValue

    override var value: C
        get() {
            val tempObservable = observable
            valid = true
            return if (tempObservable == null) _value else tempObservable.value
        }
        set(aValue) {
            if (isBound()) {
                throw IllegalStateException(BOUND_ERROR_MESSAGE)
            }
            if (aValue != value) {
                _value = aValue
                markInvalid()
            }
        }

    /**
     * May be overridden by subclasses to perform additional logic whenever the value of this
     * [KMutableCollectionProperty] gets invalid. It gets executed before any of the listeners are called.
     */
    protected open fun onInvalidating() {
        //Empty default implementation. Can be overridden to perform additional actions!
    }

    private fun markInvalid() {
        if (valid) {
            valid = false
            onInvalidating()
            fireValueChangedEvent()
        }
    }

    override fun bind(anObservable: KObservableValue<C>) {
        if (anObservable != this.observable) {
            unbind()
            observable = anObservable
            anObservable.addListener(weakListener)
            markInvalid()
        }
    }

    override fun unbind() {
        val tempObservable = observable
        if (tempObservable != null) {
            _value = tempObservable.value
            tempObservable.removeListener(weakListener)
            observable = null
        }
    }

    override fun isBound(): Boolean = observable != null

    override fun bindBidirectional(aProperty: KProperty<C>) {
        de.kotlinBerlin.kProperties.binding.bindBidirectional(this, aProperty)
    }

    override fun unbindBidirectional(aProperty: KProperty<C>) {
        de.kotlinBerlin.kProperties.binding.unbindBidirectional(this, aProperty)
    }
}

/** An [KProperty] implementation for [KObservableMutableList] objects. */
open class BasicKMutableListProperty<E, L : KObservableMutableList<E>?>(
    override val bean: Any?,
    override val name: String?,
    aValue: L
) : BasicKObservableMutableListValue<E, L>(), KMutableListProperty<E, L> {

    override var valid: Boolean = true
        protected set
    private var observable: KObservableValue<L>? = null

    private val listener: KInvalidationListener by lazy { KInvalidationListener { markInvalid() } }
    private val weakListener: WeakKInvalidationListener by lazy { WeakKInvalidationListener(listener) }

    private var _value: L = aValue

    override var value: L
        get() {
            val tempObservable = observable
            valid = true
            return if (tempObservable == null) _value else tempObservable.value
        }
        set(aValue) {
            if (isBound()) {
                throw IllegalStateException(BOUND_ERROR_MESSAGE)
            }
            if (aValue != value) {
                _value = aValue
                markInvalid()
            }
        }

    /**
     * May be overridden by subclasses to perform additional logic whenever the value of this
     * [KMutableListProperty] gets invalid. It gets executed before any of the listeners are called.
     */
    protected open fun onInvalidating() {
        //Empty default implementation. Can be overridden to perform additional actions!
    }

    private fun markInvalid() {
        if (valid) {
            valid = false
            onInvalidating()
            fireValueChangedEvent()
        }
    }

    override fun bind(anObservable: KObservableValue<L>) {
        if (anObservable != this.observable) {
            unbind()
            observable = anObservable
            anObservable.addListener(weakListener)
            markInvalid()
        }
    }

    override fun unbind() {
        val tempObservable = observable
        if (tempObservable != null) {
            _value = tempObservable.value
            tempObservable.removeListener(weakListener)
            observable = null
        }
    }

    override fun isBound(): Boolean = observable != null

    override fun bindBidirectional(aProperty: KProperty<L>) {
        de.kotlinBerlin.kProperties.binding.bindBidirectional(this, aProperty)
    }

    override fun unbindBidirectional(aProperty: KProperty<L>) {
        de.kotlinBerlin.kProperties.binding.unbindBidirectional(this, aProperty)
    }
}

/** An [KProperty] implementation for [KObservableMutableSet] objects. */
open class BasicKMutableSetProperty<E, S : KObservableMutableSet<E>?>(
    override val bean: Any?,
    override val name: String?,
    aValue: S
) : BasicKObservableMutableSetValue<E, S>(), KMutableSetProperty<E, S> {

    override var valid: Boolean = true
        protected set
    private var observable: KObservableValue<S>? = null

    private val listener: KInvalidationListener by lazy { KInvalidationListener { markInvalid() } }
    private val weakListener: WeakKInvalidationListener by lazy { WeakKInvalidationListener(listener) }

    private var _value: S = aValue

    override var value: S
        get() {
            val tempObservable = observable
            valid = true
            return if (tempObservable == null) _value else tempObservable.value
        }
        set(aValue) {
            if (isBound()) {
                throw IllegalStateException(BOUND_ERROR_MESSAGE)
            }
            if (aValue != value) {
                _value = aValue
                markInvalid()
            }
        }

    /**
     * May be overridden by subclasses to perform additional logic whenever the value of this
     * [KMutableSetProperty] gets invalid. It gets executed before any of the listeners are called.
     */
    protected open fun onInvalidating() {
        //Empty default implementation. Can be overridden to perform additional actions!
    }

    private fun markInvalid() {
        if (valid) {
            valid = false
            onInvalidating()
            fireValueChangedEvent()
        }
    }

    override fun bind(anObservable: KObservableValue<S>) {
        if (anObservable != this.observable) {
            unbind()
            observable = anObservable
            anObservable.addListener(weakListener)
            markInvalid()
        }
    }

    override fun unbind() {
        val tempObservable = observable
        if (tempObservable != null) {
            _value = tempObservable.value
            tempObservable.removeListener(weakListener)
            observable = null
        }
    }

    override fun isBound(): Boolean = observable != null

    override fun bindBidirectional(aProperty: KProperty<S>) {
        de.kotlinBerlin.kProperties.binding.bindBidirectional(this, aProperty)
    }

    override fun unbindBidirectional(aProperty: KProperty<S>) {
        de.kotlinBerlin.kProperties.binding.unbindBidirectional(this, aProperty)
    }
}

/** An [KProperty] implementation for [KObservableMutableMap] objects. */
open class BasicKMutableMapProperty<K, V, M : KObservableMutableMap<K, V>?>(
    override val bean: Any?,
    override val name: String?,
    aValue: M
) : BasicKObservableMutableMapValue<K, V, M>(), KMutableMapProperty<K, V, M> {

    override var valid: Boolean = true
        protected set
    private var observable: KObservableValue<M>? = null

    private val listener: KInvalidationListener by lazy { KInvalidationListener { markInvalid() } }
    private val weakListener: WeakKInvalidationListener by lazy { WeakKInvalidationListener(listener) }

    private var _value: M = aValue

    override var value: M
        get() {
            val tempObservable = observable
            valid = true
            return if (tempObservable == null) _value else tempObservable.value
        }
        set(aValue) {
            if (isBound()) {
                throw IllegalStateException(BOUND_ERROR_MESSAGE)
            }
            if (aValue != value) {
                _value = aValue
                markInvalid()
            }
        }

    /**
     * May be overridden by subclasses to perform additional logic whenever the value of this
     * [KMutableMapProperty] gets invalid. It gets executed before any of the listeners are called.
     */
    protected open fun onInvalidating() {
        //Empty default implementation. Can be overridden to perform additional actions!
    }

    private fun markInvalid() {
        if (valid) {
            valid = false
            onInvalidating()
            fireValueChangedEvent()
        }
    }

    override fun bind(anObservable: KObservableValue<M>) {
        if (anObservable != this.observable) {
            unbind()
            observable = anObservable
            anObservable.addListener(weakListener)
            markInvalid()
        }
    }

    override fun unbind() {
        val tempObservable = observable
        if (tempObservable != null) {
            _value = tempObservable.value
            tempObservable.removeListener(weakListener)
            observable = null
        }
    }

    override fun isBound(): Boolean = observable != null

    override fun bindBidirectional(aProperty: KProperty<M>) {
        de.kotlinBerlin.kProperties.binding.bindBidirectional(this, aProperty)
    }

    override fun unbindBidirectional(aProperty: KProperty<M>) {
        de.kotlinBerlin.kProperties.binding.unbindBidirectional(this, aProperty)
    }
}

/**
 *  A lazily created [KMutableCollectionProperty] instance, that automatically retrieves information about the [KProperty.bean]
 *  and the [KProperty.name] properties.
 */
inline fun <E, C : KObservableMutableCollection<E>?> lazyCollectionProperty(crossinline initializer: () -> C): ReadOnlyProperty<Any?, KMutableCollectionProperty<E, C>> =
    LazyPropertyImpl { anOwner, aProperty ->
        BasicKMutableCollectionProperty(anOwner, aProperty.name, initializer.invoke())
    }

/**
 *  A lazily created [KMutableListProperty] instance, that automatically retrieves information about the [KProperty.bean]
 *  and the [KProperty.name] properties.
 */
inline fun <E, L : KObservableMutableList<E>?> lazyListProperty(crossinline initializer: () -> L): ReadOnlyProperty<Any?, KMutableListProperty<E, L>> =
    LazyPropertyImpl { anOwner, aProperty ->
        BasicKMutableListProperty(anOwner, aProperty.name, initializer.invoke())
    }

/**
 *  A lazily created [KMutableSetProperty] instance, that automatically retrieves information about the [KProperty.bean]
 *  and the [KProperty.name] properties.
 */
inline fun <E, S : KObservableMutableSet<E>?> lazySetProperty(crossinline initializer: () -> S): ReadOnlyProperty<Any?, KMutableSetProperty<E, S>> =
    LazyPropertyImpl { anOwner, aProperty ->
        BasicKMutableSetProperty(anOwner, aProperty.name, initializer.invoke())
    }

/**
 *  A lazily created [KMutableMapProperty] instance, that automatically retrieves information about the [KProperty.bean]
 *  and the [KProperty.name] properties.
 */
inline fun <K, V, M : KObservableMutableMap<K, V>> lazyMapProperty(crossinline initializer: () -> M): ReadOnlyProperty<Any?, KMutableMapProperty<K, V, M>> =
    LazyPropertyImpl { anOwner, aProperty ->
        BasicKMutableMapProperty(anOwner, aProperty.name, initializer.invoke())
    }
