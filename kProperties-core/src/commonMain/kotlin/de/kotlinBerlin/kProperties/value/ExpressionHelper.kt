package de.kotlinBerlin.kProperties.value

import de.kotlinBerlin.kProperties.KInvalidationListener

internal interface KExpressionHelper<T> {

    fun addListener(aListener: KChangeListener<T>): KExpressionHelper<T>
    fun removeListener(aListener: KChangeListener<T>): KExpressionHelper<T>?

    fun addListener(aListener: KInvalidationListener): KExpressionHelper<T>

    fun removeListener(aListener: KInvalidationListener): KExpressionHelper<T>?

    fun fireValueChangedEvent()
}

internal open class KSingleInvalidation<T>(
        private val observable: KObservableValue<T>, private val listener: KInvalidationListener
) : KExpressionHelper<T> {

    override fun addListener(aListener: KChangeListener<T>): KExpressionHelper<T> {
        return KGeneric(observable, listener, aListener)
    }

    override fun removeListener(aListener: KChangeListener<T>): KExpressionHelper<T>? {
        return this
    }

    override fun addListener(aListener: KInvalidationListener): KExpressionHelper<T> {
        return KGeneric(observable, listener, aListener)
    }

    override fun removeListener(aListener: KInvalidationListener): KExpressionHelper<T>? {
        return if (listener == aListener) null else this
    }

    override fun fireValueChangedEvent() {
        listener.invalidated(observable)
    }
}

private class KSingleChange<T>(
        private val observable: KObservableValue<T>, private val listener: KChangeListener<T>
) : KExpressionHelper<T> {

    private var currentValue: T = observable.value

    override fun removeListener(aListener: KInvalidationListener): KExpressionHelper<T> {
        return this
    }

    override fun removeListener(aListener: KChangeListener<T>): KExpressionHelper<T>? {
        return if (aListener == listener) null else this
    }

    override fun addListener(aListener: KInvalidationListener): KExpressionHelper<T> {
        return KGeneric(observable, aListener, listener)
    }

    override fun addListener(aListener: KChangeListener<T>): KExpressionHelper<T> {
        return KGeneric(observable, listener, aListener)
    }

    override fun fireValueChangedEvent() {
        val oldValue = currentValue
        currentValue = observable.value
        val changed = currentValue !== oldValue
        if (changed) {
            listener.onChange(observable, oldValue, currentValue)
        }
    }
}

internal open class KGeneric<T>(private val observable: KObservableValue<T>) : KExpressionHelper<T> {

    private val changeListener: MutableList<KChangeListener<T>> = ArrayList()
    private val invalidationListeners: MutableList<KInvalidationListener> = ArrayList()
    private var currentValue: T = observable.value

    constructor(
            anObservable: KObservableValue<T>, aFirstListener: KInvalidationListener, aSecondListener: KInvalidationListener
    ) : this(anObservable) {
        invalidationListeners.add(aFirstListener)
        invalidationListeners.add(aSecondListener)
    }

    constructor(
            anObservable: KObservableValue<T>, aFirstListener: KChangeListener<T>, aSecondListener: KChangeListener<T>
    ) : this(anObservable) {
        changeListener.add(aFirstListener)
        changeListener.add(aSecondListener)
    }

    constructor(
            anObservable: KObservableValue<T>,
            anInvalidationListener: KInvalidationListener,
            aChangeListener: KChangeListener<T>
    ) : this(anObservable) {
        invalidationListeners.add(anInvalidationListener)
        changeListener.add(aChangeListener)
    }

    override fun addListener(aListener: KInvalidationListener): KGeneric<T> {
        invalidationListeners.add(aListener)
        return this
    }

    override fun removeListener(aListener: KInvalidationListener): KExpressionHelper<T>? {
        invalidationListeners.remove(aListener)
        return thisOrSingle()
    }

    override fun addListener(aListener: KChangeListener<T>): KExpressionHelper<T> {
        changeListener.add(aListener)
        return this
    }

    override fun removeListener(aListener: KChangeListener<T>): KExpressionHelper<T>? {
        changeListener.remove(aListener)
        return thisOrSingle()
    }

    private fun thisOrSingle(): KExpressionHelper<T>? {
        if (invalidationListeners.isEmpty() && changeListener.size == 1) {
            return KSingleChange(observable, changeListener[0])
        } else if (invalidationListeners.size == 1 && changeListener.isEmpty()) {
            return KSingleInvalidation(
                    observable, invalidationListeners[0]
            )
        }

        return this
    }

    override fun fireValueChangedEvent() {
        for (invalidationListener in ArrayList(invalidationListeners)) {
            invalidationListener.invalidated(observable)
        }
        if (changeListener.isEmpty()) return

        val oldValue = currentValue
        currentValue = observable.value
        val changed = currentValue !== oldValue

        if (!changed) return

        for (changeListener in ArrayList(changeListener)) {
            changeListener.onChange(observable, oldValue, currentValue)
        }
    }
}

internal fun <T> addListener(
        aHelper: KExpressionHelper<T>?, anObservable: KObservableValue<T>, aListener: KInvalidationListener
): KExpressionHelper<T> {
    anObservable.value // validate the observable
    return aHelper?.addListener(aListener) ?: KSingleInvalidation(
            anObservable, aListener
    )
}

internal fun <T> addListener(
        aHelper: KExpressionHelper<T>?, anObservable: KObservableValue<T>, aListener: KChangeListener<T>
): KExpressionHelper<T> {
    anObservable.value // validate the observable
    return aHelper?.addListener(aListener) ?: KSingleChange(
            anObservable, aListener
    )
}