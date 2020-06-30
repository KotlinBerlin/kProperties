package de.kotlinBerlin.kProperties.util

import kotlin.native.ref.WeakReference as NativeWeakRef

/**
 * An object wrapped by a [WeakReference] can be cleaned up by a garbage collector, event if the
 * [WeakReference] itself is sill strongly reachable and there are no other strong references on the
 * wrapped object.
 */
actual class WeakReference<T : Any> actual constructor(aWrapped: T) {

    private val weakRef = NativeWeakRef(aWrapped)

    /** The weakly referenced object. If the garbage collector collected the object, this returns null. */
    actual val wrapped: T? get() = weakRef.get()
}