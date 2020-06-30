@file:Suppress("unused")

package de.kotlinBerlin.kProperties.property

import de.kotlinBerlin.kProperties.value.*
import kotlin.properties.ReadOnlyProperty

/** A common interface of all [KProperty] implementations that wrap a [Number]. */
interface KNumberProperty<N : Number?> : KProperty<N>, KObservableNumberValue<N>

/** An [KProperty] wrapping a [Double] value. */
interface KDoubleProperty<D : Double?> : KNumberProperty<D>, KObservableDoubleValue<D>

/** An [KProperty] wrapping a [Float] value. */
interface KFloatProperty<F : Float?> : KNumberProperty<F>, KObservableFloatValue<F>

/** An [KProperty] wrapping a [Long] value. */
interface KLongProperty<L : Long?> : KNumberProperty<L>, KObservableLongValue<L>

/** An [KProperty] wrapping a [Int] value. */
interface KIntProperty<I : Int?> : KNumberProperty<I>, KObservableIntValue<I>

/** An [KProperty] wrapping a [Short] value. */
interface KShortProperty<S : Short?> : KNumberProperty<S>, KObservableShortValue<S>

/** An [KProperty] wrapping a [Byte] value. */
interface KByteProperty<B : Byte?> : KNumberProperty<B>, KObservableByteValue<B>

/** An [KProperty] implementation for [Number] objects. */
open class BasicKNumberProperty<N : Number?>(bean: Any? = null, name: String? = null, value: N) :
        KObjectProperty<N>(bean, name, value), KNumberProperty<N>

/** An [KProperty] implementation for [Double] objects. */
open class BasicKDoubleProperty<D : Double?>(bean: Any? = null, name: String? = null, value: D) :
        BasicKNumberProperty<D>(bean, name, value), KDoubleProperty<D>

/** An [KProperty] implementation for [Float] objects. */
open class BasicKFloatProperty<F : Float?>(bean: Any? = null, name: String? = null, value: F) :
        BasicKNumberProperty<F>(bean, name, value), KFloatProperty<F>

/** An [KProperty] implementation for [Long] objects. */
open class BasicKLongProperty<L : Long?>(bean: Any? = null, name: String? = null, value: L) :
        BasicKNumberProperty<L>(bean, name, value), KLongProperty<L>

/** An [KProperty] implementation for [Int] objects. */
open class BasicKIntProperty<I : Int?>(bean: Any? = null, name: String? = null, value: I) :
        BasicKNumberProperty<I>(bean, name, value), KIntProperty<I>

/** An [KProperty] implementation for [Short] objects. */
open class BasicKShortProperty<S : Short?>(bean: Any? = null, name: String? = null, value: S) :
        BasicKNumberProperty<S>(bean, name, value), KShortProperty<S>

/** An [KProperty] implementation for [Byte] objects. */
open class BasicKByteProperty<B : Byte?>(bean: Any? = null, name: String? = null, value: B) :
        BasicKNumberProperty<B>(bean, name, value), KByteProperty<B>

// Lazy delegates

/**
 *  A lazily created [KNumberProperty] instance, that automatically retrieves information about the [KProperty.bean]
 *  and the [KProperty.name] properties.
 */
inline fun <N : Number?> lazyNumberProperty(crossinline initializer: () -> N): ReadOnlyProperty<Any?, KNumberProperty<N>> =
        LazyPropertyImpl { anOwner, aProperty ->
            BasicKNumberProperty(anOwner, aProperty.name, initializer.invoke())
        }

/**
 *  A lazily created [KDoubleProperty] instance, that automatically retrieves information about the [KProperty.bean]
 *  and the [KProperty.name] properties.
 */
inline fun <D : Double?> lazyDoubleProperty(crossinline initializer: () -> D): ReadOnlyProperty<Any?, KDoubleProperty<D>> =
        LazyPropertyImpl { anOwner, aProperty ->
            BasicKDoubleProperty(anOwner, aProperty.name, initializer.invoke())
        }

/**
 *  A lazily created [KFloatProperty] instance, that automatically retrieves information about the [KProperty.bean]
 *  and the [KProperty.name] properties.
 */
inline fun <F : Float?> lazyFloatProperty(crossinline initializer: () -> F): ReadOnlyProperty<Any?, KFloatProperty<F>> =
        LazyPropertyImpl { anOwner, aProperty ->
            BasicKFloatProperty(anOwner, aProperty.name, initializer.invoke())
        }

/**
 *  A lazily created [KIntProperty] instance, that automatically retrieves information about the [KProperty.bean]
 *  and the [KProperty.name] properties.
 */
inline fun <I : Int?> lazyIntProperty(crossinline initializer: () -> I): ReadOnlyProperty<Any?, KIntProperty<I>> =
        LazyPropertyImpl { anOwner, aProperty ->
            BasicKIntProperty(anOwner, aProperty.name, initializer.invoke())
        }

/**
 *  A lazily created [KShortProperty] instance, that automatically retrieves information about the [KProperty.bean]
 *  and the [KProperty.name] properties.
 */
inline fun <S : Short?> lazyShortProperty(crossinline initializer: () -> S): ReadOnlyProperty<Any?, KShortProperty<S>> =
        LazyPropertyImpl { anOwner, aProperty ->
            BasicKShortProperty(anOwner, aProperty.name, initializer.invoke())
        }

/**
 *  A lazily created [KByteProperty] instance, that automatically retrieves information about the [KProperty.bean]
 *  and the [KProperty.name] properties.
 */
inline fun <B : Byte?> lazyByteProperty(crossinline initializer: () -> B): ReadOnlyProperty<Any?, KByteProperty<B>> =
        LazyPropertyImpl { anOwner, aProperty ->
            BasicKByteProperty(anOwner, aProperty.name, initializer.invoke())
        }