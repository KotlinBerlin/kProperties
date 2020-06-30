@file:Suppress("unused")

package de.kotlinBerlin.kProperties.property

import de.kotlinBerlin.kProperties.KInvalidationListener
import de.kotlinBerlin.kProperties.WeakKInvalidationListener
import de.kotlinBerlin.kProperties.collection.KObservableCollection
import de.kotlinBerlin.kProperties.collection.KObservableList
import de.kotlinBerlin.kProperties.collection.KObservableMap
import de.kotlinBerlin.kProperties.collection.KObservableSet
import de.kotlinBerlin.kProperties.value.*
import kotlin.properties.ReadOnlyProperty

/** An [KProperty] wrapping a [KObservableCollection] value. */
interface KCollectionProperty<E, C : KObservableCollection<E>?> : KProperty<C>, KObservableCollectionValue<E, C>

/** An [KProperty] wrapping a [KObservableList] value. */
interface KListProperty<E, L : KObservableList<E>?> : KProperty<L>, KObservableListValue<E, L>

/** An [KProperty] wrapping a [KObservableSet] value. */
interface KSetProperty<E, S : KObservableSet<E>?> : KProperty<S>, KObservableSetValue<E, S>

/** An [KProperty] wrapping a [KObservableMap] value. */
interface KMapProperty<K, V, M : KObservableMap<K, V>?> : KProperty<M>, KObservableMapValue<K, V, M>

private const val BOUND_ERROR_MESSAGE = "A bound value can not be set!"

/** An [KProperty] implementation for [KObservableCollection] objects. */
open class BasicKCollectionProperty<E, C : KObservableCollection<E>?>(
        override val bean: Any?,
        override val name: String?,
        aValue: C
) : BasicKObservableCollectionValue<E, C>(), KCollectionProperty<E, C> {

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
     * [KCollectionProperty] gets invalid. It gets executed before any of the listeners are called.
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

/** An [KProperty] implementation for [KObservableList] objects. */
open class BasicKListProperty<E, L : KObservableList<E>?>(
        override val bean: Any?,
        override val name: String?,
        aValue: L
) : BasicKObservableListValue<E, L>(), KListProperty<E, L> {

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
     * [KListProperty] gets invalid. It gets executed before any of the listeners are called.
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

/** An [KProperty] implementation for [KObservableSet] objects. */
open class BasicKSetProperty<E, S : KObservableSet<E>?>(
        override val bean: Any?,
        override val name: String?,
        aValue: S
) : BasicKObservableSetValue<E, S>(), KSetProperty<E, S> {

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
     * [KSetProperty] gets invalid. It gets executed before any of the listeners are called.
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

/** An [KProperty] implementation for [KObservableMap] objects. */
open class BasicKMapProperty<K, V, M : KObservableMap<K, V>?>(
        override val bean: Any?,
        override val name: String?,
        aValue: M
) : BasicKObservableMapValue<K, V, M>(), KMapProperty<K, V, M> {

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
     * [KMapProperty] gets invalid. It gets executed before any of the listeners are called.
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
 *  A lazily created [KCollectionProperty] instance, that automatically retrieves information about the [KProperty.bean]
 *  and the [KProperty.name] properties.
 */
inline fun <E, C : KObservableCollection<E>?> lazyCollectionProperty(crossinline initializer: () -> C): ReadOnlyProperty<Any?, KCollectionProperty<E, C>> =
        LazyPropertyImpl { anOwner, aProperty ->
            BasicKCollectionProperty(anOwner, aProperty.name, initializer.invoke())
        }

/**
 *  A lazily created [KListProperty] instance, that automatically retrieves information about the [KProperty.bean]
 *  and the [KProperty.name] properties.
 */
inline fun <E, L : KObservableList<E>?> lazyListProperty(crossinline initializer: () -> L): ReadOnlyProperty<Any?, KListProperty<E, L>> =
        LazyPropertyImpl { anOwner, aProperty ->
            BasicKListProperty(anOwner, aProperty.name, initializer.invoke())
        }

/**
 *  A lazily created [KSetProperty] instance, that automatically retrieves information about the [KProperty.bean]
 *  and the [KProperty.name] properties.
 */
inline fun <E, S : KObservableSet<E>?> lazySetProperty(crossinline initializer: () -> S): ReadOnlyProperty<Any?, KSetProperty<E, S>> =
        LazyPropertyImpl { anOwner, aProperty ->
            BasicKSetProperty(anOwner, aProperty.name, initializer.invoke())
        }

/**
 *  A lazily created [KMapProperty] instance, that automatically retrieves information about the [KProperty.bean]
 *  and the [KProperty.name] properties.
 */
inline fun <K, V, M : KObservableMap<K, V>> lazyMapProperty(crossinline initializer: () -> M): ReadOnlyProperty<Any?, KMapProperty<K, V, M>> =
        LazyPropertyImpl { anOwner, aProperty ->
            BasicKMapProperty(anOwner, aProperty.name, initializer.invoke())
        }