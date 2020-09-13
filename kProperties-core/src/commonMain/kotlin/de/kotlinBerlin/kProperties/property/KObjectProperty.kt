package de.kotlinBerlin.kProperties.property

import de.kotlinBerlin.kProperties.KInvalidationListener
import de.kotlinBerlin.kProperties.WeakKInvalidationListener
import de.kotlinBerlin.kProperties.binding.bindBidirectional
import de.kotlinBerlin.kProperties.binding.unbindBidirectional
import de.kotlinBerlin.kProperties.value.KObservableObjectValue
import de.kotlinBerlin.kProperties.value.KObservableValue

/** An [KProperty] implementation for [Any] objects. */
open class KObjectProperty<T>(override val bean: Any?, override val name: String?, aValue: T) :
        KObservableObjectValue<T>(), KProperty<T> {

    override var valid: Boolean = true
        protected set
    private var observable: KObservableValue<T>? = null

    private val listener: KInvalidationListener by lazy { KInvalidationListener { markInvalid() } }
    private val weakListener: WeakKInvalidationListener by lazy { WeakKInvalidationListener(listener) }

    private var _value: T = aValue

    override var value: T
        get() {
            val tempObservable = observable
            valid = true
            return if (tempObservable == null) _value else tempObservable.value
        }
        set(aValue) {
            if (isBound()) {
                throw IllegalStateException("A bound value can not be set!")
            }
            if (aValue != value) {
                _value = aValue
                markInvalid()
            }
        }

    /**
     * May be overridden by subclasses to perform additional logic whenever the value of this
     * [KObjectProperty] gets invalid. It gets executed before any of the listeners are called.
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

    override fun bind(anObservable: KObservableValue<T>) {
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

    override fun bindBidirectional(aProperty: KProperty<T>): Unit =
            bindBidirectional(this, aProperty)

    override fun unbindBidirectional(aProperty: KProperty<T>): Unit =
            unbindBidirectional(this, aProperty)

    override fun toString(): String {
        val bean: Any? = bean
        val name: String? = name
        val result = StringBuilder("${this::class.simpleName} [")
        if (bean != null) {
            result.append("bean: ").append(bean).append(", ")
        }
        if (name != null && name != "") {
            result.append("name: ").append(name).append(", ")
        }
        if (isBound()) {
            result.append("bound, ")
        }
        if (valid) {
            result.append("value: ").append(value)
        } else {
            result.append("invalid")
        }
        result.append("]")
        return result.toString()
    }
}