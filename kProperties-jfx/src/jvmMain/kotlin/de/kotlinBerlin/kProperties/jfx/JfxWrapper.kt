@file:Suppress("unused")

package de.kotlinBerlin.kProperties.jfx

import de.kotlinBerlin.kProperties.KInvalidationListener
import de.kotlinBerlin.kProperties.binding.bindBidirectional
import de.kotlinBerlin.kProperties.binding.unbindBidirectional
import de.kotlinBerlin.kProperties.property.*
import de.kotlinBerlin.kProperties.value.KChangeListener
import de.kotlinBerlin.kProperties.value.KObservableValue
import javafx.beans.InvalidationListener
import javafx.beans.binding.ObjectBinding
import javafx.beans.property.Property
import javafx.beans.value.ChangeListener

fun <V> Property<V?>.toKProperty(): KProperty<V?> = JfxKProperty(this, null)
fun <V : Any> Property<V>.toSaveKProperty(defaultValue: V): KProperty<V> = JfxKProperty(this, defaultValue)

fun Property<Boolean?>.toKProperty(): KBooleanProperty<Boolean?> = JfxKBooleanProperty(this, null)
fun Property<Boolean>.toSaveKProperty(defaultValue: Boolean = false): KBooleanProperty<Boolean> =
    JfxKBooleanProperty(this, defaultValue)

fun Property<String?>.toKProperty(): KStringProperty<String?> = JfxKStringProperty(this, null)
fun Property<String>.toSaveKProperty(defaultValue: String = ""): KStringProperty<String> =
    JfxKStringProperty(this, defaultValue)

fun Property<Double?>.toKProperty(): KDoubleProperty<Double?> = JfxKDoubleProperty(this, null)
fun Property<Double>.toSaveKProperty(defaultValue: Double = 0.0): KDoubleProperty<Double> =
    JfxKDoubleProperty(this, defaultValue)

fun Property<Float?>.toKProperty(): KFloatProperty<Float?> = JfxKFloatProperty(this, null)
fun Property<Float>.toSaveKProperty(defaultValue: Float = 0.0F): KFloatProperty<Float> =
    JfxKFloatProperty(this, defaultValue)

fun Property<Int?>.toKProperty(): KIntProperty<Int?> = JfxKIntProperty(this, null)
fun Property<Int>.toSaveKProperty(defaultValue: Int = 0): KIntProperty<Int> =
    JfxKIntProperty(this, defaultValue)

fun Property<Short?>.toKProperty(): KShortProperty<Short?> = JfxKShortProperty(this, null)
fun Property<Short>.toSaveKProperty(defaultValue: Short = 0): KShortProperty<Short> =
    JfxKShortProperty(this, defaultValue)

fun Property<Byte?>.toKProperty(): KByteProperty<Byte?> = JfxKByteProperty(this, null)
fun Property<Byte>.toSaveKProperty(defaultValue: Byte = 0): KByteProperty<Byte> =
    JfxKByteProperty(this, defaultValue)

open class JfxKProperty<V>(private val jfxProp: Property<V>, private val defaultValue: V) : KProperty<V> {
    override val bean: Any? get() = jfxProp.bean
    override val name: String? get() = jfxProp.name

    override var value: V
        get() = jfxProp.value
        set(value) {
            jfxProp.value = value
        }

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
        jfxProp.addListener(invalidationListener)
        jfxProp.addListener(changeListener)
    }

    override fun addListener(aListener: KInvalidationListener) {
        kInvalidationListener.add(aListener)
    }

    override fun removeListener(aListener: KInvalidationListener) {
        kInvalidationListener.remove(aListener)
    }

    override val valid: Boolean get() = true
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

class JfxKBooleanProperty<B : Boolean?>(jfxProp: Property<B>, defaultValue: B) : JfxKProperty<B>(jfxProp, defaultValue),
    KBooleanProperty<B>

class JfxKStringProperty<S : String?>(jfxProp: Property<S>, defaultValue: S) : JfxKProperty<S>(jfxProp, defaultValue),
    KStringProperty<S>

class JfxKDoubleProperty<D : Double?>(jfxProp: Property<D>, defaultValue: D) : JfxKProperty<D>(jfxProp, defaultValue),
    KDoubleProperty<D>

class JfxKFloatProperty<F : Float?>(jfxProp: Property<F>, defaultValue: F) : JfxKProperty<F>(jfxProp, defaultValue),
    KFloatProperty<F>

class JfxKIntProperty<I : Int?>(jfxProp: Property<I>, defaultValue: I) : JfxKProperty<I>(jfxProp, defaultValue),
    KIntProperty<I>

class JfxKShortProperty<S : Short?>(jfxProp: Property<S>, defaultValue: S) : JfxKProperty<S>(jfxProp, defaultValue),
    KShortProperty<S>

class JfxKByteProperty<B : Byte?>(jfxProp: Property<B>, defaultValue: B) : JfxKProperty<B>(jfxProp, defaultValue),
    KByteProperty<B>

