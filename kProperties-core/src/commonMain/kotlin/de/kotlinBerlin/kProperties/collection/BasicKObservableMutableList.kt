package de.kotlinBerlin.kProperties.collection

import de.kotlinBerlin.kProperties.util.*
import kotlin.math.max

/** Basic implementation of the [KObservableMutableList] interface. */
class BasicKObservableMutableList<E>(private val wrapped: MutableList<E>) :
    KObservableMutableList<E>, MutableList<E> by wrapped {

    private val listeners by lazy { mutableListOf<Any>() }

    override fun addListener(aListener: KMutableListListener<E>) {
        listeners.add(aListener)
    }

    override fun addListener(aListener: KMutableCollectionListener<E>) {
        listeners.add(aListener)
    }

    override fun addListener(aListener: KCollectionListener<E>) {
        listeners.add(aListener)
    }

    override fun addListener(aListener: KListListener<E>) {
        listeners.add(aListener)
    }

    override fun removeListener(aListener: KMutableListListener<E>) {
        listeners.remove(aListener)
    }

    override fun removeListener(aListener: KMutableCollectionListener<E>) {
        listeners.remove(aListener)
    }

    override fun removeListener(aListener: KCollectionListener<E>) {
        listeners.remove(aListener)
    }

    override fun removeListener(aListener: KListListener<E>) {
        listeners.remove(aListener)
    }

    override fun add(element: E): Boolean {
        if (wrapped.add(element)) {
            onAdd(max(wrapped.lastIndex - 1, 0), immutableListOf(element))
            return true
        }
        return false
    }

    override fun addAll(elements: Collection<E>): Boolean {
        if (wrapped.addAll(elements)) {
            onAdd(max(wrapped.lastIndex - elements.size, 0), elements.copyToImmutableCollection())
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
        val tempIndex = wrapped.indexOf(element)

        val tempList = createImmutableCollection<ListPermutation<E>> {
            for (i in tempIndex + 1 until wrapped.size) {
                add(ListPermutation(wrapped[i], i, i - 1))
            }
        }

        if (wrapped.remove(element)) {
            onMove(tempList)
            onRemove(immutableListOf(element))
            return true
        }
        return false
    }

    override fun removeAll(elements: Collection<E>): Boolean {
        val tempMoves = HashMap<E, ListPermutation<E>>()
        val tempRemoved = createImmutableCollection<E> {
            val tempIterator = wrapped.asReversed().iterator()
            while (tempIterator.hasNext()) {
                val tempSubMoves = HashMap(tempMoves)
                val tempNext = tempIterator.next()
                collectOrUpdatePermutations(tempNext, tempSubMoves)
                if (elements.contains(tempNext)) {
                    tempIterator.remove()
                    add(tempNext)
                    tempMoves.putAll(tempSubMoves)
                    tempMoves.remove(tempNext)
                }
            }
        }

        val tempSortedMoves = tempMoves.values.toList().sortedBy { it.oldIndex }

        if (tempSortedMoves.isNotEmpty()) onMove(tempSortedMoves.toImmutableCollection())
        if (!tempRemoved.isEmpty()) onRemove(tempRemoved)
        return tempRemoved.isNotEmpty()
    }

    override fun retainAll(elements: Collection<E>): Boolean {
        val tempMoves = HashMap<E, ListPermutation<E>>()
        val tempRemoved = createImmutableCollection<E> {
            val tempIterator = wrapped.asReversed().iterator()
            while (tempIterator.hasNext()) {
                val tempNext = tempIterator.next()
                if (!elements.contains(tempNext)) {
                    collectOrUpdatePermutations(tempNext, tempMoves)
                    tempIterator.remove()
                    add(tempNext)
                    tempMoves.remove(tempNext)
                }
            }
        }

        val tempSortedMoves = tempMoves.values.toList().sortedBy { it.oldIndex }

        if (tempSortedMoves.isNotEmpty()) onMove(tempSortedMoves.toImmutableCollection())
        if (tempRemoved.isNotEmpty()) onRemove(tempRemoved)
        return tempRemoved.isNotEmpty()
    }

    override fun add(index: Int, element: E) {
        val tempList = createImmutableCollection<ListPermutation<E>> {
            for (i in index until wrapped.size) {
                add(ListPermutation(wrapped[i], i, i + 1))
            }
        }
        wrapped.add(index, element)

        if (tempList.isNotEmpty()) onMove(tempList)
        onAdd(index, immutableListOf(element))
    }

    override fun addAll(index: Int, elements: Collection<E>): Boolean {
        val tempList = createImmutableCollection<ListPermutation<E>> {
            for (i in index until wrapped.size) {
                add(ListPermutation(wrapped[i], i, i + elements.size))
            }
        }
        if (wrapped.addAll(index, elements)) {
            if (tempList.isNotEmpty()) onMove(tempList)
            onAdd(index, elements.copyToImmutableCollection())
            return true
        }
        return false
    }

    override fun removeAt(index: Int): E {
        val tempList = createImmutableCollection<ListPermutation<E>> {
            for (i in index + 1 until wrapped.size) {
                add(ListPermutation(wrapped[i], i, i - 1))
            }
        }
        val tempRemoved = wrapped.removeAt(index)

        if (tempList.isNotEmpty()) onMove(tempList)
        onRemove(immutableListOf(tempRemoved))

        return tempRemoved
    }

    override fun set(index: Int, element: E): E {
        val tempElement = wrapped.set(index, element)
        onReplace(immutableListOf(ListReplacement(tempElement, element, index)))
        return tempElement
    }

    private fun onAdd(aStartIndex: Int, anAddedList: Collection<E>) {
        performListeners(
            anAddedList,
            KListListener<E>::onAdd, KMutableListListener<E>::onAdd,
            KCollectionListener<E>::onAdd, KMutableCollectionListener<E>::onAdd,
            aStartIndex,
            listeners
        )
    }

    private fun onRemove(aRemovedList: Collection<E>) {
        performListeners(
            aRemovedList,
            KListListener<E>::onRemove, KMutableListListener<E>::onRemove,
            KCollectionListener<E>::onRemove, KMutableCollectionListener<E>::onRemove,
            listeners
        )
    }

    private fun onMove(aPermutationList: Collection<ListPermutation<E>>) {
        performListeners(
            aPermutationList, KListListener<E>::onMove, KMutableListListener<E>::onMove, listeners = listeners
        )
    }

    private fun onReplace(aReplacementList: Collection<ListReplacement<E>>) {
        performListeners(
            aReplacementList, KListListener<E>::onReplace, KMutableListListener<E>::onReplace, listeners = listeners
        )
    }

    private fun collectOrUpdatePermutations(
        tempNext: E,
        tempMoves: HashMap<E, ListPermutation<E>>
    ) {
        val tempIndex = wrapped.lastIndexOf(tempNext)
        for (i in tempIndex + 1 until wrapped.size) {
            val tempElement = wrapped[i]
            val tempPermutation = tempMoves[tempElement]
            if (tempPermutation != null) {
                tempMoves[tempElement] =
                    ListPermutation(tempElement, tempPermutation.oldIndex, tempPermutation.newIndex - 1)
            } else {
                tempMoves[tempElement] = ListPermutation(tempElement, i, i - 1)
            }
        }
    }

    override fun iterator(): MutableIterator<E> = listIterator()

    override fun listIterator(): MutableListIterator<E> = listIterator(0)

    override fun listIterator(index: Int): MutableListIterator<E> =
        KObservableMutableListIterator(index)

    private inner class KObservableMutableListIterator(anIndex: Int) : MutableListIterator<E>, KMutableListListener<E> {

        private var nextIndex = anIndex
        private var lastIndex = -1
        private var valid: Boolean = true
        private val privateChanges = mutableListOf<Any>()

        init {
            addListener(WeakKMutableListListener(this))
        }

        override fun onAdd(aMutableList: KObservableMutableList<E>, aStartIndex: Int, anAddedList: Collection<E>) {
            if (!privateChanges.remove(anAddedList)) invalidate()
        }

        override fun onRemove(aMutableList: KObservableMutableList<E>, aRemovedList: Collection<E>) {
            if (!privateChanges.remove(aRemovedList)) invalidate()
        }

        override fun onMove(aMutableList: KObservableMutableList<E>, aPermutationList: Collection<ListPermutation<E>>) {
            if (!privateChanges.remove(aPermutationList)) invalidate()
        }

        override fun onReplace(
            aMutableList: KObservableMutableList<E>,
            aReplacementList: Collection<ListReplacement<E>>
        ) {
            if (!privateChanges.remove(aReplacementList)) invalidate()
        }

        override fun hasNext(): Boolean = nextIndex != wrapped.size
        override fun hasPrevious(): Boolean = nextIndex != 0
        override fun nextIndex(): Int = nextIndex
        override fun previousIndex(): Int = nextIndex - 1

        override fun next(): E {
            checkValidity()
            val i: Int = nextIndex
            if (i >= wrapped.size) throw NoSuchElementException()
            nextIndex = i + 1
            lastIndex = i
            return wrapped[lastIndex]
        }

        override fun previous(): E {
            checkValidity()
            val i = nextIndex - 1
            if (i < 0) throw NoSuchElementException()
            nextIndex = i
            lastIndex = i
            return wrapped[lastIndex]
        }

        override fun remove() {
            check(lastIndex >= 0)
            checkValidity()
            try {
                internalRemove(lastIndex)
                nextIndex = lastIndex
                lastIndex = -1
            } catch (e: IndexOutOfBoundsException) {
                throw ConcurrentModificationException()
            }
        }

        override fun add(element: E) {
            checkValidity()

            try {
                val i = nextIndex
                internalAdd(i, element)
                nextIndex = i + 1
                lastIndex = -1
            } catch (e: IndexOutOfBoundsException) {
                throw ConcurrentModificationException()
            }
        }

        override fun set(element: E) {
            check(lastIndex >= 0)
            checkValidity()

            try {
                internalSet(lastIndex, element)
            } catch (e: IndexOutOfBoundsException) {
                throw ConcurrentModificationException()
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

        private fun internalAdd(anIndex: Int, anElement: E) {
            val tempPermutationChangeList =
                createImmutableCollection<ListPermutation<E>> {
                    for (i in anIndex until wrapped.size) {
                        add(
                            ListPermutation(
                                wrapped[i],
                                i,
                                i + 1
                            )
                        )
                    }
                }

            val tempAddChangeList = immutableListOf(anElement)
            wrapped.add(anIndex, anElement)

            try {
                privateChanges.add(tempPermutationChangeList)
                privateChanges.add(tempAddChangeList)
                if (tempPermutationChangeList.isNotEmpty()) onMove(tempPermutationChangeList)
                onAdd(anIndex, tempAddChangeList)
            } finally {
                privateChanges.remove(tempPermutationChangeList)
                privateChanges.remove(tempAddChangeList)
            }
        }

        private fun internalSet(anIndex: Int, anElement: E) {
            val tempElement = wrapped.set(anIndex, anElement)
            val tempChangeList = immutableListOf(
                ListReplacement(
                    tempElement,
                    anElement,
                    anIndex
                )
            )
            try {
                privateChanges.add(tempChangeList)
                onReplace(tempChangeList)
            } finally {
                privateChanges.remove(tempChangeList)
            }
        }

        private fun internalRemove(anIndex: Int) {
            val tempPermutationChangeList =
                createImmutableCollection<ListPermutation<E>> {
                    for (i in anIndex + 1 until wrapped.size) {
                        add(
                            ListPermutation(
                                wrapped[i],
                                i,
                                i - 1
                            )
                        )
                    }
                }

            val tempRemoved = wrapped.removeAt(anIndex)
            val tempRemovedChangeList = immutableListOf(tempRemoved)

            try {
                privateChanges.add(tempPermutationChangeList)
                privateChanges.add(tempRemovedChangeList)
                if (tempPermutationChangeList.isNotEmpty()) onMove(tempPermutationChangeList)
                onRemove(tempRemovedChangeList)
            } finally {
                privateChanges.remove(tempPermutationChangeList)
                privateChanges.remove(tempRemovedChangeList)
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
internal fun <E, T> KObservableMutableList<E>.performListeners(
    aParameter: T,
    aListAction: KListListener<E>.(KObservableList<E>, Int, T) -> Unit,
    aMutableListAction: KMutableListListener<E>.(KObservableMutableList<E>, Int, T) -> Unit,
    aColAction: (KCollectionListener<E>.(KObservableCollection<E>, T) -> Unit)? = null,
    aMutableColAction: (KMutableCollectionListener<E>.(KObservableMutableCollection<E>, T) -> Unit)? = null,
    aStartIndex: Int,
    listeners: Collection<Any>
) {
    listeners.forEach {
        when (it) {
            is KListListener<*> -> aListAction(it as KListListener<E>, this, aStartIndex, aParameter)
            is KMutableListListener<*> -> aMutableListAction(
                it as KMutableListListener<E>, this, aStartIndex,
                aParameter
            )
            is KCollectionListener<*> -> aColAction?.invoke(it as KCollectionListener<E>, this, aParameter)
            is KMutableCollectionListener<*> -> aMutableColAction?.invoke(
                it as KMutableCollectionListener<E>, this, aParameter
            )
        }
    }
}

@Suppress("UNCHECKED_CAST")
internal fun <E, T> KObservableMutableList<E>.performListeners(
    aParameter: T,
    aListAction: KListListener<E>.(KObservableList<E>, T) -> Unit,
    aMutableListAction: KMutableListListener<E>.(KObservableMutableList<E>, T) -> Unit,
    aColAction: (KCollectionListener<E>.(KObservableCollection<E>, T) -> Unit)? = null,
    aMutableColAction: (KMutableCollectionListener<E>.(KObservableMutableCollection<E>, T) -> Unit)? = null,
    listeners: Collection<Any>
) {
    listeners.forEach {
        when (it) {
            is KListListener<*> -> aListAction(it as KListListener<E>, this, aParameter)
            is KMutableListListener<*> -> aMutableListAction(it as KMutableListListener<E>, this, aParameter)
            is KCollectionListener<*> -> aColAction?.invoke(it as KCollectionListener<E>, this, aParameter)
            is KMutableCollectionListener<*> -> aMutableColAction?.invoke(
                it as KMutableCollectionListener<E>, this, aParameter
            )
        }
    }
}
