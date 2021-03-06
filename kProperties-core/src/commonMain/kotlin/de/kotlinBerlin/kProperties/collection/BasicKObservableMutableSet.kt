package de.kotlinBerlin.kProperties.collection

import de.kotlinBerlin.kProperties.util.copyToImmutableCollection
import de.kotlinBerlin.kProperties.util.createImmutableCollection
import de.kotlinBerlin.kProperties.util.immutableCollectionOf
import de.kotlinBerlin.kProperties.util.immutableListOf

/** Basic implementation of the [KObservableMutableSet] interface. */
class BasicKObservableMutableSet<E>(private val wrapped: MutableSet<E>) :
    KObservableMutableSet<E>,
    MutableSet<E> by wrapped {

    private val listeners by lazy { mutableListOf<Any>() }

    override fun addListener(aListener: KMutableSetListener<E>) {
        listeners.add(aListener)
    }

    override fun addListener(aListener: KMutableCollectionListener<E>) {
        listeners.add(aListener)
    }

    override fun addListener(aListener: KCollectionListener<E>) {
        listeners.add(aListener)
    }

    override fun addListener(aListener: KSetListener<E>) {
        listeners.add(aListener)
    }

    override fun removeListener(aListener: KMutableSetListener<E>) {
        listeners.remove(aListener)
    }

    override fun removeListener(aListener: KMutableCollectionListener<E>) {
        listeners.remove(aListener)
    }

    override fun removeListener(aListener: KCollectionListener<E>) {
        listeners.remove(aListener)
    }

    override fun removeListener(aListener: KSetListener<E>) {
        listeners.remove(aListener)
    }

    override fun add(element: E): Boolean {
        if (wrapped.add(element)) {
            onAdd(immutableListOf(element))
            return true
        }
        return false
    }

    override fun addAll(elements: Collection<E>): Boolean {
        if (wrapped.addAll(elements)) {
            onAdd(elements.copyToImmutableCollection())
            return true
        }
        return false
    }

    override fun clear() {
        val tempRemoved = immutableCollectionOf(wrapped)
        wrapped.clear()
        if (tempRemoved.isNotEmpty()) onRemove(tempRemoved)
    }

    override fun remove(element: E): Boolean {
        if (wrapped.remove(element)) {
            onRemove(immutableListOf(element))
            return true
        }
        return false
    }

    override fun removeAll(elements: Collection<E>): Boolean {
        val tempRemoved = createImmutableCollection<E> {
            elements.forEach {
                if (wrapped.remove(it)) {
                    add(it)
                }
            }
        }

        if (tempRemoved.isNotEmpty()) onRemove(tempRemoved)
        return tempRemoved.isNotEmpty()
    }

    override fun retainAll(elements: Collection<E>): Boolean {
        val tempRemoved = createImmutableCollection<E> {
            val tempIterator = wrapped.iterator()
            while (tempIterator.hasNext()) {
                val tempNext = tempIterator.next()
                if (!elements.contains(tempNext)) {
                    tempIterator.remove()
                    add(tempNext)
                }
            }
        }

        if (tempRemoved.isNotEmpty()) onRemove(tempRemoved)
        return tempRemoved.isNotEmpty()
    }

    private fun onAdd(anAddedList: Collection<E>) {
        performListeners(
            anAddedList,
            KSetListener<E>::onAdd, KMutableSetListener<E>::onAdd,
            KCollectionListener<E>::onAdd, KMutableCollectionListener<E>::onAdd,
            listeners
        )
    }

    private fun onRemove(aRemovedList: Collection<E>) {
        performListeners(
            aRemovedList,
            KSetListener<E>::onRemove, KMutableSetListener<E>::onRemove,
            KCollectionListener<E>::onRemove, KMutableCollectionListener<E>::onRemove,
            listeners
        )
    }

    override fun iterator(): MutableIterator<E> = KObservableMutableSetIterator()

    private inner class KObservableMutableSetIterator : MutableIterator<E>, KMutableSetListener<E> {

        private val wrappedIterator = wrapped.iterator()
        private var valid: Boolean = true
        private val privateChanges = mutableListOf<Any>()
        private var currentElement: E? = null

        init {
            addListener(WeakKMutableSetListener(this))
        }

        override fun onAdd(aMutableSet: KObservableMutableSet<E>, anAddedList: Collection<E>) = invalidate()

        override fun onRemove(aMutableSet: KObservableMutableSet<E>, aRemovedList: Collection<E>) {
            if (!privateChanges.remove(aRemovedList)) invalidate()
        }

        override fun hasNext(): Boolean = wrappedIterator.hasNext()

        override fun next(): E {
            checkValidity()
            val tempNextElement = wrappedIterator.next()
            currentElement = tempNextElement
            return tempNextElement
        }

        @Suppress("UNCHECKED_CAST")
        override fun remove() {
            checkValidity()
            wrappedIterator.remove()

            val tempCurrentElement = currentElement as E
            val tempRemovedList = immutableListOf(tempCurrentElement)
            try {
                privateChanges.add(tempRemovedList)
                onRemove(tempRemovedList)
            } finally {
                privateChanges.remove(tempRemovedList)
            }
        }

        private fun invalidate() {
            valid = false
        }

        private fun checkValidity() {
            if (!valid) {
                throw ConcurrentModificationException("The KObservableCollection has been modified concurrently!")
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
internal fun <T, E> KObservableMutableSet<E>.performListeners(
    aParameter: T,
    aSetAction: KSetListener<E>.(KObservableSet<E>, T) -> Unit,
    aMutableSetAction: KMutableSetListener<E>.(KObservableMutableSet<E>, T) -> Unit,
    aColAction: KCollectionListener<E>.(KObservableCollection<E>, T) -> Unit,
    aMutableColAction: KMutableCollectionListener<E>.(KObservableMutableCollection<E>, T) -> Unit,
    listeners: Collection<Any>
) {
    listeners.forEach {
        when (it) {
            is KSetListener<*> -> aSetAction(it as KSetListener<E>, this, aParameter)
            is KMutableSetListener<*> -> aMutableSetAction(it as KMutableSetListener<E>, this, aParameter)
            is KCollectionListener<*> -> aColAction(it as KCollectionListener<E>, this, aParameter)
            is KMutableCollectionListener<*> -> aMutableColAction(
                it as KMutableCollectionListener<E>,
                this,
                aParameter
            )
        }
    }
}
