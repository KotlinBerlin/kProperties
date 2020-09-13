@file:Suppress("unused")

package de.kotlinBerlin.kProperties.property

import de.kotlinBerlin.kProperties.value.KObservableBooleanValue
import de.kotlinBerlin.kProperties.value.KObservableStringValue
import kotlin.properties.ReadOnlyProperty

/** An [KProperty] wrapping a [Boolean] value. */
interface KBooleanProperty<B : Boolean?> : KProperty<B>, KObservableBooleanValue<B>

/** An [KProperty] wrapping a [String] value. */
interface KStringProperty<S : String?> : KProperty<S>, KObservableStringValue<S>

/** An [KProperty] implementation for [Boolean] objects. */
open class BasicKBooleanProperty<B : Boolean?>(bean: Any? = null, name: String? = null, value: B) :
        KObjectProperty<B>(bean, name, value), KBooleanProperty<B>

/** An [KProperty] implementation for [String] objects. */
open class BasicKStringProperty<S : String?>(bean: Any? = null, name: String? = null, value: S) :
        KObjectProperty<S>(bean, name, value),
        KStringProperty<S>

// Lazy delegates

/**
 *  A lazily created [KProperty] instance, that automatically retrieves information about the [KProperty.bean]
 *  and the [KProperty.name] properties.
 */
inline fun <T : Any?> lazyObjectProperty(crossinline initializer: () -> T): ReadOnlyProperty<Any?, KProperty<T>> =
        LazyPropertyImpl { anOwner, aProperty ->
            KObjectProperty(anOwner, aProperty.name, initializer.invoke())
        }

/**
 *  A lazily created [KBooleanProperty] instance, that automatically retrieves information about the [KProperty.bean]
 *  and the [KProperty.name] properties.
 */
inline fun <B : Boolean?> lazyBooleanProperty(crossinline initializer: () -> B): ReadOnlyProperty<Any?, KBooleanProperty<B>> =
        LazyPropertyImpl { anOwner, aProperty ->
            BasicKBooleanProperty(anOwner, aProperty.name, initializer.invoke())
        }

/**
 *  A lazily created [KStringProperty] instance, that automatically retrieves information about the [KProperty.bean]
 *  and the [KProperty.name] properties.
 */
inline fun lazyStringProperty(crossinline initializer: () -> String = { "" }): ReadOnlyProperty<Any?, KStringProperty<String>> =
        LazyPropertyImpl { anOwner, aProperty ->
            BasicKStringProperty(anOwner, aProperty.name, initializer.invoke())
        }

internal val UNINITIALIZED_VALUE = object : Any() {}

/**
 * Delegate class for lazily created [KProperty] instances.
 */
class LazyPropertyImpl<out T>(initializer: (Any?, kotlin.reflect.KProperty<*>) -> T) :
        ReadOnlyProperty<Any?, T> {
    private var initializer: ((Any?, kotlin.reflect.KProperty<*>) -> T)? = initializer
    private var _value: Any? = UNINITIALIZED_VALUE

    override fun getValue(thisRef: Any?, property: kotlin.reflect.KProperty<*>): T {
        if (!isInitialized()) {
            _value = initializer!!(thisRef, property)
            initializer = null
        }
        @Suppress("UNCHECKED_CAST")
        return _value as T
    }

    private fun isInitialized(): Boolean = _value !== UNINITIALIZED_VALUE

    override fun toString(): String = if (isInitialized()) _value.toString() else "Lazy KProperty not initialized yet."
}