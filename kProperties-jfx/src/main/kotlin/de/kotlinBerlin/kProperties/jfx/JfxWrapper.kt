@file:Suppress("unused")

package de.kotlinBerlin.kProperties.jfx

import de.kotlinBerlin.kProperties.KInvalidationListener
import de.kotlinBerlin.kProperties.binding.bindBidirectional
import de.kotlinBerlin.kProperties.binding.unbindBidirectional
import de.kotlinBerlin.kProperties.property.*
import de.kotlinBerlin.kProperties.value.KChangeListener
import de.kotlinBerlin.kProperties.value.KObservableValue
import javafx.beans.InvalidationListener
import javafx.beans.WeakInvalidationListener
import javafx.beans.binding.ObjectBinding
import javafx.beans.property.Property
import javafx.beans.property.ReadOnlyProperty
import javafx.beans.value.ChangeListener
import javafx.beans.value.WeakChangeListener

//Generic

fun <V> ReadOnlyProperty<V?>.toKReadOnlyProperty(bean: Any? = null, name: String? = null): KReadOnlyProperty<V?> =
    JfxKReadOnlyProperty(this, true, bean, name)

fun <V : Any> ReadOnlyProperty<V>.toSaveKReadOnlyProperty(bean: Any? = null, name: String? = null):
        KReadOnlyProperty<V> = JfxKReadOnlyProperty(this, false, bean, name)

fun <V> Property<V?>.toKProperty(bean: Any? = null, name: String? = null): KProperty<V?> =
    JfxKProperty(this, null, bean, name)

fun <V : Any> Property<V>.toSaveKProperty(defaultValue: V, bean: Any? = null, name: String? = null): KProperty<V> =
    JfxKProperty(this, defaultValue, bean, name)

//Boolean

fun ReadOnlyProperty<Boolean?>.toKProperty(
    bean: Any? = null, name: String? = null
): KReadOnlyBooleanProperty<Boolean?> = JfxKReadOnlyBooleanProperty(this, true, bean, name)

fun ReadOnlyProperty<Boolean>.toSaveKProperty(
    bean: Any? = null, name: String? = null
): KReadOnlyBooleanProperty<Boolean> = JfxKReadOnlyBooleanProperty(this, false, bean, name)

fun Property<Boolean?>.toKProperty(bean: Any? = null, name: String? = null): KBooleanProperty<Boolean?> =
    JfxKBooleanProperty(this, null, bean, name)

fun Property<Boolean>.toSaveKProperty(
    defaultValue: Boolean = false, bean: Any? = null, name: String? = null
): KBooleanProperty<Boolean> = JfxKBooleanProperty(this, defaultValue, bean, name)

//String

fun ReadOnlyProperty<String?>.toKProperty(bean: Any? = null, name: String? = null): KReadOnlyStringProperty<String?> =
    JfxKReadOnlyStringProperty(this, true, bean, name)

fun ReadOnlyProperty<String>.toSaveKProperty(
    bean: Any? = null, name: String? = null
): KReadOnlyStringProperty<String> = JfxKReadOnlyStringProperty(this, false, bean, name)

fun Property<String?>.toKProperty(bean: Any? = null, name: String? = null): KStringProperty<String?> =
    JfxKStringProperty(this, null, bean, name)

fun Property<String>.toSaveKProperty(
    defaultValue: String = "", bean: Any? = null, name: String? = null
): KStringProperty<String> = JfxKStringProperty(this, defaultValue, bean, name)

//Double

fun ReadOnlyProperty<Double?>.toKProperty(bean: Any? = null, name: String? = null): KReadOnlyDoubleProperty<Double?> =
    JfxKReadOnlyDoubleProperty(this, true, bean, name)

fun ReadOnlyProperty<Double>.toSaveKProperty(
    bean: Any? = null, name: String? = null
): KReadOnlyDoubleProperty<Double> = JfxKReadOnlyDoubleProperty(this, false, bean, name)

fun Property<Double?>.toKProperty(bean: Any? = null, name: String? = null): KDoubleProperty<Double?> =
    JfxKDoubleProperty(this, null, bean, name)

fun Property<Double>.toSaveKProperty(
    defaultValue: Double = 0.0, bean: Any? = null, name: String? = null
): KDoubleProperty<Double> = JfxKDoubleProperty(this, defaultValue, bean, name)

//Float

fun Property<Float?>.toKProperty(bean: Any? = null, name: String? = null): KFloatProperty<Float?> =
    JfxKFloatProperty(this, null, bean, name)

fun Property<Float>.toSaveKProperty(
    defaultValue: Float = 0.0F, bean: Any? = null, name: String? = null
): KFloatProperty<Float> = JfxKFloatProperty(this, defaultValue, bean, name)

//Int

fun Property<Int?>.toKProperty(bean: Any? = null, name: String? = null): KIntProperty<Int?> =
    JfxKIntProperty(this, null, bean, name)

fun Property<Int>.toSaveKProperty(defaultValue: Int = 0, bean: Any? = null, name: String? = null): KIntProperty<Int> =
    JfxKIntProperty(this, defaultValue, bean, name)

//Short

fun Property<Short?>.toKProperty(bean: Any? = null, name: String? = null): KShortProperty<Short?> =
    JfxKShortProperty(this, null, bean, name)

fun Property<Short>.toSaveKProperty(
    defaultValue: Short = 0, bean: Any? = null, name: String? = null
): KShortProperty<Short> = JfxKShortProperty(this, defaultValue, bean, name)

//Byte

fun Property<Byte?>.toKProperty(bean: Any? = null, name: String? = null): KByteProperty<Byte?> =
    JfxKByteProperty(this, null, bean, name)

fun Property<Byte>.toSaveKProperty(
    defaultValue: Byte = 0, bean: Any? = null, name: String? = null
): KByteProperty<Byte> = JfxKByteProperty(this, defaultValue, bean, name)

private open class JfxKProperty<V>(
    private val jfxProp: Property<V>, private val defaultValue: V, private val _bean:
    Any?, private val _name: String?
) : KProperty<V> {
    override val bean: Any? get() = _bean ?: jfxProp.bean
    override val name: String? get() = _name ?: jfxProp.name

    override var value: V
        get() = jfxProp.value
        set(value) {
            jfxProp.value = value
        }

    override val valid: Boolean get() = true

    private val invalidationListener = InvalidationListener { kInvalidationListener.forEach { it.invalidated(this) } }

    private val changeListener = ChangeListener<V> { _, oldValue, newValue ->
        if (newValue == null && defaultValue != null) {
            try {
                jfxProp.value = defaultValue
            } catch (e: Exception) {
                if (isBound()) {
                    e.addSuppressed(
                        NullPointerException(
                            "JFX Property was set to null which is bound to a non nullable " +
                                    "KProperty. As the JFX Property is bound to another ObservableValue, its value can not be set to " +
                                    "the default value!"
                        )
                    )
                }
                throw e
            }
        } else {
            kChangeListener.forEach { it.onChange(this, oldValue, newValue) }
        }
    }

    private val kInvalidationListener = arrayListOf<KInvalidationListener>()

    private val kChangeListener = arrayListOf<KChangeListener<V>>()

    init {
        jfxProp.addListener(WeakInvalidationListener(invalidationListener))
        jfxProp.addListener(WeakChangeListener(changeListener))
    }

    override fun addListener(aListener: KInvalidationListener) {
        kInvalidationListener.add(aListener)
    }

    override fun removeListener(aListener: KInvalidationListener) {
        kInvalidationListener.remove(aListener)
    }

    override fun addListener(aListener: KChangeListener<V>) {
        kChangeListener.add(aListener)
    }

    override fun removeListener(aListener: KChangeListener<V>) {
        kChangeListener.remove(aListener)
    }

    override fun bind(anObservable: KObservableValue<V>) {
        val tempBinding = object : ObjectBinding<V>() {
            override fun computeValue(): V = anObservable.value
        }

        anObservable.addListener(KInvalidationListener { tempBinding.invalidate() })

        jfxProp.bind(tempBinding)
    }

    override fun unbind() = jfxProp.unbind()

    override fun isBound(): Boolean = jfxProp.isBound

    override fun bindBidirectional(aProperty: KProperty<V>): Unit = bindBidirectional(this, aProperty)

    override fun unbindBidirectional(aProperty: KProperty<V>): Unit = unbindBidirectional(this, aProperty)
}

private open class JfxKReadOnlyProperty<V>(
    private val jfxProp: ReadOnlyProperty<V>, private val allowNull: Boolean, private val _bean:
    Any?, private val _name: String?
) : KReadOnlyProperty<V> {
    override val bean: Any? get() = _bean ?: jfxProp.bean
    override val name: String? get() = _name ?: jfxProp.name

    override val value: V get() = jfxProp.value

    private val kInvalidationListener = arrayListOf<KInvalidationListener>()
    private val kChangeListener = arrayListOf<KChangeListener<V>>()

    private val invalidationListener = InvalidationListener { kInvalidationListener.forEach { it.invalidated(this) } }

    private val changeListener = ChangeListener<V> { _, oldValue, newValue ->
        if (newValue == null && !allowNull) {
            throw NullPointerException("Value of JfxProperty was set to null!")
        } else {
            kChangeListener.forEach { it.onChange(this, oldValue, newValue) }
        }
    }

    init {
        jfxProp.addListener(WeakInvalidationListener(invalidationListener))
        jfxProp.addListener(WeakChangeListener(changeListener))
    }

    override fun addListener(aListener: KInvalidationListener) {
        kInvalidationListener.add(aListener)
    }

    override fun removeListener(aListener: KInvalidationListener) {
        kInvalidationListener.remove(aListener)
    }

    override fun addListener(aListener: KChangeListener<V>) {
        kChangeListener.add(aListener)
    }

    override fun removeListener(aListener: KChangeListener<V>) {
        kChangeListener.remove(aListener)
    }
}

private class JfxKReadOnlyBooleanProperty<B : Boolean?>(
    jfxProp: ReadOnlyProperty<B>, allowNull: Boolean, _bean: Any?, _name: String?
) : JfxKReadOnlyProperty<B>(jfxProp, allowNull, _bean, _name), KReadOnlyBooleanProperty<B>

private class JfxKBooleanProperty<B : Boolean?>(jfxProp: Property<B>, defaultValue: B, _bean: Any?, _name: String?) :
    JfxKProperty<B>(jfxProp, defaultValue, _bean, _name), KBooleanProperty<B>

private class JfxKReadOnlyStringProperty<S : String?>(
    jfxProp: ReadOnlyProperty<S>, allowNull: Boolean, _bean: Any?, _name: String?
) : JfxKReadOnlyProperty<S>(jfxProp, allowNull, _bean, _name), KReadOnlyStringProperty<S>

private class JfxKStringProperty<S : String?>(jfxProp: Property<S>, defaultValue: S, _bean: Any?, _name: String?) :
    JfxKProperty<S>(jfxProp, defaultValue, _bean, _name), KStringProperty<S>

private class JfxKReadOnlyDoubleProperty<D : Double?>(
    jfxProp: ReadOnlyProperty<D>, allowNull: Boolean, _bean: Any?, _name: String?
) : JfxKReadOnlyProperty<D>(jfxProp, allowNull, _bean, _name), KReadOnlyDoubleProperty<D>

private class JfxKDoubleProperty<D : Double?>(jfxProp: Property<D>, defaultValue: D, _bean: Any?, _name: String?) :
    JfxKProperty<D>(jfxProp, defaultValue, _bean, _name), KDoubleProperty<D>

private class JfxKFloatProperty<F : Float?>(jfxProp: Property<F>, defaultValue: F, _bean: Any?, _name: String?) :
    JfxKProperty<F>(jfxProp, defaultValue, _bean, _name), KFloatProperty<F>

private class JfxKIntProperty<I : Int?>(jfxProp: Property<I>, defaultValue: I, _bean: Any?, _name: String?) :
    JfxKProperty<I>(jfxProp, defaultValue, _bean, _name), KIntProperty<I>

private class JfxKShortProperty<S : Short?>(jfxProp: Property<S>, defaultValue: S, _bean: Any?, _name: String?) :
    JfxKProperty<S>(jfxProp, defaultValue, _bean, _name), KShortProperty<S>

private class JfxKByteProperty<B : Byte?>(jfxProp: Property<B>, defaultValue: B, _bean: Any?, _name: String?) :
    JfxKProperty<B>(jfxProp, defaultValue, _bean, _name), KByteProperty<B>

