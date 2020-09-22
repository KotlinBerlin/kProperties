@file:Suppress("unused", "DuplicatedCode")

package de.kotlinBerlin.kProperties.binding

import de.kotlinBerlin.kProperties.collection.*
import de.kotlinBerlin.kProperties.util.WeakReference
import kotlin.jvm.JvmName

/**
 * Creates a bidirectional binding between the two [KObservableMutableList] instances.
 */
fun <E> bindContent(list1: MutableList<E>, list2: KObservableList<E>): Unit =
    bindContent(list1, list2) { it }

/**
 * Creates a bidirectional binding between the two [KObservableMutableList] instances.
 */
fun <E, E1> bindContent(
    list1: MutableList<E>, list2: KObservableList<E1>,
    toFirstMapper: (E1) -> E
) {
    if (list1 === list2) {
        throw IllegalArgumentException("Can not bind list to itself.")
    }
    list1.clear()
    list1.addAll(list2.map(toFirstMapper))
    ListContentBinding(list1, list2, toFirstMapper)
}

/**
 * Creates a bidirectional binding between the two [KObservableMutableList] instances.
 */
fun <E> bindContentBidirectional(list1: KObservableMutableList<E>, list2: KObservableMutableList<E>): Unit =
    bindContentBidirectional(list1, list2, { it }, { it })

/**
 * Creates a bidirectional binding between the two [KObservableMutableList] instances.
 */
fun <E, E1> bindContentBidirectional(
    list1: KObservableMutableList<E>, list2: KObservableMutableList<E1>,
    toFirstMapper: (E1) -> E, toSecondMapper: (E) -> E1
) {
    if (list1 === list2) {
        throw IllegalArgumentException("Can not bind list to itself.")
    }
    list1.clear()
    list1.addAll(list2.map(toFirstMapper))
    BidirectionalListContentBinding(list1, list2, toFirstMapper, toSecondMapper)
}

private class BidirectionalListContentBinding<E, E1>(
    list1: KObservableMutableList<E>, list2: KObservableMutableList<E1>,
    private val toFirstMapper: (E1) -> E, private val toSecondMapper: (E) -> E1
) {
    private val propertyRef1: WeakReference<KObservableMutableList<E>> = WeakReference(list1)
    private val propertyRef2: WeakReference<KObservableMutableList<E1>> = WeakReference(list2)
    private var updating = false

    private val listener1: KMutableListListener<E> = object : KMutableListListener<E> {
        override fun onAdd(aMutableList: KObservableMutableList<E>, aStartIndex: Int, anAddedList: Collection<E>) {
            doUpdate { destination: KObservableMutableList<E1> ->
                val map = anAddedList.map(toSecondMapper)
                destination.addAll(aStartIndex, map)
            }
        }

        override fun onRemove(aMutableList: KObservableMutableList<E>, aRemovedList: Collection<E>) {
            doUpdate { destination: KObservableMutableList<E1> ->
                aRemovedList.map(toSecondMapper).forEach(destination::remove)
            }
        }

        override fun onReplace(
            aMutableList: KObservableMutableList<E>,
            aReplacementList: Collection<ListReplacement<E>>
        ) {
            doUpdate { destination: KObservableMutableList<E1> ->
                aReplacementList.forEach {
                    destination[it.index] = toSecondMapper(it.newElement)
                }
            }
        }
    }

    val listener2: KMutableListListener<E1> = object : KMutableListListener<E1> {
        override fun onAdd(aMutableList: KObservableMutableList<E1>, aStartIndex: Int, anAddedList: Collection<E1>) {
            doUpdate { destination: KObservableMutableList<E> ->
                destination.addAll(aStartIndex, anAddedList.map(toFirstMapper))
            }
        }

        override fun onRemove(aMutableList: KObservableMutableList<E1>, aRemovedList: Collection<E1>) {
            doUpdate { destination: KObservableMutableList<E> ->
                aRemovedList.map(toFirstMapper).forEach(destination::remove)
            }
        }

        override fun onReplace(
            aMutableList: KObservableMutableList<E1>,
            aReplacementList: Collection<ListReplacement<E1>>
        ) {
            doUpdate { destination: KObservableMutableList<E> ->
                aReplacementList.forEach {
                    destination[it.index] = toFirstMapper(it.newElement)
                }
            }
        }
    }

    init {
        list1.addListener(listener1)
        list2.addListener(listener2)
    }

    @JvmName("doUpdate1")
    private inline fun doUpdate(block: (KObservableMutableList<E>) -> Unit) {
        if (!updating) {
            checkGarbageCollected()?.let { (list1, _) ->
                try {
                    updating = true
                    block(list1)
                } finally {
                    updating = false
                }
            }
        }
    }

    @JvmName("doUpdate2")
    private inline fun doUpdate(block: (KObservableMutableList<E1>) -> Unit) {
        if (!updating) {
            checkGarbageCollected()?.let { (_, list2) ->
                try {
                    updating = true
                    block(list2)
                } finally {
                    updating = false
                }
            }
        }
    }

    private fun checkGarbageCollected(): Pair<KObservableMutableList<E>, KObservableMutableList<E1>>? {
        val list1: KObservableMutableList<E>? = propertyRef1.wrapped
        val list2: KObservableMutableList<E1>? = propertyRef2.wrapped
        if (list1 == null || list2 == null) {
            list1?.removeListener(listener1)
            list2?.removeListener(listener2)
            return null
        }
        return Pair(list1, list2)
    }

    override fun hashCode(): Int {
        val list1: KObservableMutableList<E>? = propertyRef1.wrapped
        val list2: KObservableMutableList<E1>? = propertyRef2.wrapped
        val hc1 = list1?.hashCode() ?: 0
        val hc2 = list2?.hashCode() ?: 0
        return hc1 * hc2
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        val propertyA1: Any? = propertyRef1.wrapped
        val propertyA2: Any? = propertyRef2.wrapped
        if (propertyA1 == null || propertyA2 == null) {
            return false
        }
        if (other is BidirectionalListContentBinding<*, *>) {
            val propertyB1: Any? = other.propertyRef1.wrapped
            val propertyB2: Any? = other.propertyRef2.wrapped
            if (propertyB1 == null || propertyB2 == null) {
                return false
            }
            if (propertyA1 === propertyB1 && propertyA2 === propertyB2) {
                return true
            }
            if (propertyA1 === propertyB2 && propertyA2 === propertyB1) {
                return true
            }
        }
        return false
    }
}

private class ListContentBinding<E, E1>(
    list1: MutableList<E>, list2: KObservableList<E1>,
    private val toFirstMapper: (E1) -> E
) : KListListener<E1> {
    private val propertyRef1: WeakReference<MutableList<E>> = WeakReference(list1)
    private val propertyRef2: WeakReference<KObservableList<E1>> = WeakReference(list2)
    private var updating = false

    init {
        list2.addListener(this)
    }

    override fun onAdd(aList: KObservableList<E1>, aStartIndex: Int, anAddedList: Collection<E1>) {
        doUpdate { destination ->
            destination.addAll(aStartIndex, anAddedList.map(toFirstMapper))
        }
    }

    override fun onRemove(aList: KObservableList<E1>, aRemovedList: Collection<E1>) {
        doUpdate { destination ->
            aRemovedList.map(toFirstMapper).forEach(destination::remove)
        }
    }

    override fun onReplace(
        aList: KObservableList<E1>,
        aReplacementList: Collection<ListReplacement<E1>>
    ) {
        doUpdate { destination ->
            aReplacementList.forEach {
                destination[it.index] = toFirstMapper(it.newElement)
            }
        }
    }

    private inline fun doUpdate(block: (MutableList<E>) -> Unit) {
        if (!updating) {
            checkGarbageCollected()?.let {
                try {
                    updating = true
                    block(it)
                } finally {
                    updating = false
                }
            }
        }
    }

    private fun checkGarbageCollected(): MutableList<E>? {
        val list1: MutableList<E>? = propertyRef1.wrapped
        val list2: KObservableList<E1>? = propertyRef2.wrapped
        if (list1 == null || list2 == null) {
            list2?.removeListener(this)
            return null
        }
        return list1
    }

    override fun hashCode(): Int {
        val list1: MutableList<E>? = propertyRef1.wrapped
        val list2: KObservableList<E1>? = propertyRef2.wrapped
        val hc1 = list1?.hashCode() ?: 0
        val hc2 = list2?.hashCode() ?: 0
        return hc1 * hc2
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        val propertyA1: Any? = propertyRef1.wrapped
        val propertyA2: Any? = propertyRef2.wrapped
        if (propertyA1 == null || propertyA2 == null) {
            return false
        }
        if (other is ListContentBinding<*, *>) {
            val propertyB1: Any? = other.propertyRef1.wrapped
            val propertyB2: Any? = other.propertyRef2.wrapped
            if (propertyB1 == null || propertyB2 == null) {
                return false
            }
            if (propertyA1 === propertyB1 && propertyA2 === propertyB2) {
                return true
            }
            if (propertyA1 === propertyB2 && propertyA2 === propertyB1) {
                return true
            }
        }
        return false
    }
}
