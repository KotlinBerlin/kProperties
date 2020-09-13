package de.kotlinBerlin.kProperties.util

/**
 * An object wrapped by a [WeakReference] can be cleaned up by a garbage collector, event if the
 * [WeakReference] itself is sill strongly reachable and there are no other strong references on the
 * wrapped object.
 */
actual class WeakReference<T : Any> actual constructor(aWrapped: T) {

    private var weakRef: dynamic

    private var strongRefFallback: T?

    /** The weakly referenced object. If the garbage collector collected the object, this returns null. */
    actual val wrapped: T? get() = if (weakRef == null) strongRefFallback else weakRef.deref() as T?

    init {
        try {
            weakRef = js("new WeakRef(aWrapped)")
            strongRefFallback = null
        } catch (e: Throwable) {
            strongRefFallback = aWrapped
            weakRef = null
        }
    }
}