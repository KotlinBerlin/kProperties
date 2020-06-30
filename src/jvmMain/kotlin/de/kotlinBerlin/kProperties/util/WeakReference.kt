package de.kotlinBerlin.kProperties.util

import java.lang.ref.WeakReference

/**
 * An object wrapped by a [WeakReference] can be cleaned up by a garbage collector, event if the
 * [WeakReference] itself is sill strongly reachable and there are no other strong references on the
 * wrapped object.
 */
actual class WeakReference<T : Any> actual constructor(aWrapped: T) : WeakReference<T>(aWrapped) {

    /** The weakly referenced object. If the garbage collector collected the object, this returns null. */
    actual val wrapped: T? get() = super.get()
}