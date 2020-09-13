package de.kotlinBerlin.kProperties.util

/**
 * An object wrapped by a [WeakReference] can be cleaned up by a garbage collector, event if the
 * [WeakReference] itself is sill strongly reachable and there are no other strong references on the
 * wrapped object.
 */
expect class WeakReference<T : Any>(aWrapped: T) {

    /** The weakly referenced object. If the garbage collector collected the object, this returns null. */
    val wrapped: T?
}