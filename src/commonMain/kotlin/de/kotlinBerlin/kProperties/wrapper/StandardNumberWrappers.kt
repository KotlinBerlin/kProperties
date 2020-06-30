@file:Suppress("unused")

package de.kotlinBerlin.kProperties.wrapper

import de.kotlinBerlin.kProperties.property.*
import de.kotlinBerlin.kProperties.value.*

/**
 * Basic [KWrapper] implementation for [Number] objects
 */
open class BasicKNumberWrapper<N : Number?>(bean: Any? = null, name: String? = null, value: N) :
        BasicKNumberProperty<N>(bean, name, value), KWrapper<N> {
    override val readOnlyProperty: KReadOnlyNumberProperty<N> by lazy {
        object : KReadOnlyNumberProperty<N>, KObservableNumberValue<N> by this {
            override val bean: Any?
                get() = this@BasicKNumberWrapper.bean
            override val name: String?
                get() = this@BasicKNumberWrapper.name
        }
    }
}

/**
 * Basic [KWrapper] implementation for [Double] objects
 */
open class BasicKDoubleWrapper<D : Double?>(bean: Any? = null, name: String? = null, value: D) :
        BasicKDoubleProperty<D>(bean, name, value), KWrapper<D> {
    override val readOnlyProperty: KReadOnlyDoubleProperty<D> by lazy {
        object : KReadOnlyDoubleProperty<D>, KObservableDoubleValue<D> by this {
            override val bean: Any?
                get() = this@BasicKDoubleWrapper.bean
            override val name: String?
                get() = this@BasicKDoubleWrapper.name
        }
    }
}

/**
 * Basic [KWrapper] implementation for [Float] objects
 */
open class BasicKFloatWrapper<F : Float?>(bean: Any? = null, name: String? = null, value: F) :
        BasicKFloatProperty<F>(bean, name, value), KWrapper<F> {
    override val readOnlyProperty: KReadOnlyFloatProperty<F> by lazy {
        object : KReadOnlyFloatProperty<F>, KObservableFloatValue<F> by this {
            override val bean: Any?
                get() = this@BasicKFloatWrapper.bean
            override val name: String?
                get() = this@BasicKFloatWrapper.name
        }
    }
}

/**
 * Basic [KWrapper] implementation for [Long] objects
 */
open class BasicKLongWrapper<L : Long?>(bean: Any? = null, name: String? = null, value: L) :
        BasicKLongProperty<L>(bean, name, value), KWrapper<L> {
    override val readOnlyProperty: KReadOnlyLongProperty<L> by lazy {
        object : KReadOnlyLongProperty<L>, KObservableLongValue<L> by this {
            override val bean: Any?
                get() = this@BasicKLongWrapper.bean
            override val name: String?
                get() = this@BasicKLongWrapper.name
        }
    }
}

/**
 * Basic [KWrapper] implementation for [Int] objects
 */
open class BasicKIntWrapper<I : Int?>(bean: Any? = null, name: String? = null, value: I) :
        BasicKIntProperty<I>(bean, name, value), KWrapper<I> {
    override val readOnlyProperty: KReadOnlyIntProperty<I> by lazy {
        object : KReadOnlyIntProperty<I>, KObservableIntValue<I> by this {
            override val bean: Any?
                get() = this@BasicKIntWrapper.bean
            override val name: String?
                get() = this@BasicKIntWrapper.name
        }
    }
}

/**
 * Basic [KWrapper] implementation for [Short] objects
 */
open class BasicKShortWrapper<S : Short?>(bean: Any? = null, name: String? = null, value: S) :
        BasicKShortProperty<S>(bean, name, value), KWrapper<S> {
    override val readOnlyProperty: KReadOnlyShortProperty<S> by lazy {
        object : KReadOnlyShortProperty<S>, KObservableShortValue<S> by this {
            override val bean: Any?
                get() = this@BasicKShortWrapper.bean
            override val name: String?
                get() = this@BasicKShortWrapper.name
        }
    }
}

/**
 * Basic [KWrapper] implementation for [Byte] objects
 */
open class BasicKByteWrapper<B : Byte?>(bean: Any? = null, name: String? = null, value: B) :
        BasicKByteProperty<B>(bean, name, value), KWrapper<B> {
    override val readOnlyProperty: KReadOnlyByteProperty<B> by lazy {
        object : KReadOnlyByteProperty<B>, KObservableByteValue<B> by this {
            override val bean: Any?
                get() = this@BasicKByteWrapper.bean
            override val name: String?
                get() = this@BasicKByteWrapper.name
        }
    }
}