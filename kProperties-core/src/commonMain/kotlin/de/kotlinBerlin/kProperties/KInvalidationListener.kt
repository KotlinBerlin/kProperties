package de.kotlinBerlin.kProperties

/**
 * An [KInvalidationListener] is notified whenever an
 * [KObservable] becomes invalid. It can be registered and
 * unregistered with [KObservable.addListener] respectively [KObservable.removeListener].
 * The same instance of [KInvalidationListener] can be registered to listen to multiple [KObservable] instances.
 *
 * @see KObservable
 */
fun interface KInvalidationListener {

    /**
     * This method is called if an [KObservable] becomes invalid.
     *
     * @param anObservable the [KObservable] that became invalid.
     */
    fun invalidated(anObservable: KObservable)
}