package de.kotlinBerlin.kProperties.collection

import de.kotlinBerlin.kProperties.collection.KMapListener.Replacement
import de.kotlinBerlin.kProperties.util.createImmutableCollection
import de.kotlinBerlin.kProperties.util.immutableCollectionOf
import de.kotlinBerlin.kProperties.util.immutableListOf
import de.kotlinBerlin.kProperties.util.toImmutableCollection
import kotlin.collections.Map.Entry
import kotlin.collections.MutableMap.MutableEntry

/** Basic implementation of the [KObservableMap] interface. */
class BasicKObservableMap<K, V>(private val wrapped: MutableMap<K, V>) :
        KObservableMap<K, V>, MutableMap<K, V> by wrapped {

    override val entries: MutableSet<MutableEntry<K, V>> by lazy { EntrySet() }
    override val keys: KObservableSet<K> by lazy { KeySet() }
    override val values: KObservableCollection<V> by lazy { ValueCollection() }

    private val listeners by lazy { mutableListOf<KMapListener<K, V>>() }

    override fun addListener(aListener: KMapListener<K, V>) {
        listeners.add(aListener)
    }

    override fun removeListener(aListener: KMapListener<K, V>) {
        listeners.remove(aListener)
    }

    override fun clear() {
        val tempRemoved = immutableCollectionOf(wrapped.entries.map { Pair(it.key, it.value) })
        wrapped.clear()
        onRemove(tempRemoved)
    }

    @Suppress("UNCHECKED_CAST")
    override fun put(key: K, value: V): V? {
        val tempReplacementFlag = wrapped.containsKey(key)
        val tempReplacedValue = wrapped.put(key, value)

        if (tempReplacementFlag) onReplace(
                immutableCollectionOf(
                        Replacement(
                                key,
                                tempReplacedValue as V,
                                value
                        )
                )
        ) else onAdd(immutableCollectionOf(Pair(key, value)))

        return tempReplacedValue
    }

    @Suppress("UNCHECKED_CAST")
    override fun putAll(from: Map<out K, V>) {
        val tempReplacements = mutableListOf<Replacement<K, V>>()

        val tempAdded = createImmutableCollection<Pair<K, V>> {
            from.forEach {
                val tempReplacementFlag = wrapped.containsKey(it.key)
                val tempReplacedValue = wrapped.put(it.key, it.value)

                if (tempReplacementFlag) tempReplacements.add(
                        Replacement(
                                it.key,
                                tempReplacedValue as V,
                                it.value
                        )
                ) else add(it.key to it.value)
            }
        }

        if (tempReplacements.isNotEmpty()) onReplace(tempReplacements.toImmutableCollection())
        if (tempAdded.isNotEmpty()) onAdd(tempAdded)
    }

    @Suppress("UNCHECKED_CAST")
    override fun remove(key: K): V? {
        if (wrapped.containsKey(key)) {
            val tempRemoved = wrapped.remove(key) as V
            onRemove(immutableCollectionOf(key to tempRemoved))
        }
        return null
    }

    private fun onAdd(anAddedEntries: Collection<Pair<K, V>>) {
        listeners.forEach { it.onAdd(this, anAddedEntries) }
    }

    private fun onRemove(aRemovedEntries: Collection<Pair<K, V>>) {
        listeners.forEach { it.onRemove(this, aRemovedEntries) }
    }

    private fun onReplace(aReplacedEntries: Collection<Replacement<K, V>>) {
        listeners.forEach { it.onReplace(this, aReplacedEntries) }
    }

    private inner class EntrySet : MutableSet<MutableEntry<K, V>> {

        override val size: Int get() = this@BasicKObservableMap.size

        override fun add(element: MutableEntry<K, V>): Boolean {
            put(element.key, element.value)
            return true
        }

        override fun addAll(elements: Collection<MutableEntry<K, V>>): Boolean {
            this@BasicKObservableMap.putAll(mapOf(*elements.map { it.key to it.value }.toTypedArray()))
            return true
        }

        override fun clear() = this@BasicKObservableMap.clear()

        override fun iterator(): MutableIterator<MutableEntry<K, V>> = KObservableMapEntryIterator()

        override fun remove(element: MutableEntry<K, V>): Boolean {
            if (contains(element)) {
                this@BasicKObservableMap.remove(element.key)
                return true
            }
            return false
        }

        override fun removeAll(elements: Collection<MutableEntry<K, V>>): Boolean = elements.map(::remove).any { it }

        override fun retainAll(elements: Collection<MutableEntry<K, V>>): Boolean =
                wrapped.entries.filter { wrappedElement -> elements.none { wrappedElement.key == it.key && wrappedElement.value == it.value } }
                        .map(::remove).any { it }

        override fun contains(element: MutableEntry<K, V>): Boolean =
                containsKey(element.key) && get(element.key) == element.value

        override fun containsAll(elements: Collection<MutableEntry<K, V>>): Boolean = elements.all(::contains)

        override fun isEmpty(): Boolean = this@BasicKObservableMap.isEmpty()
    }

    private inner class KeySet : MutableSet<K>, KObservableSet<K>, KMapListener<K, V> {

        private val keySetListeners by lazy { mutableListOf<Any>() }
        override val size: Int get() = this@BasicKObservableMap.size

        init {
            addListener(WeakKMapListener(this))
        }

        override fun onAdd(aMap: KObservableMap<K, V>, anAddedEntries: Collection<Pair<K, V>>) {
            onAdd(anAddedEntries.map { it.first }.toImmutableCollection())
        }

        override fun onRemove(aMap: KObservableMap<K, V>, aRemovedEntries: Collection<Pair<K, V>>) {
            onRemove(aRemovedEntries.map { it.first }.toImmutableCollection())
        }

        override fun addListener(aListener: KSetListener<K>) {
            keySetListeners.add(aListener)
        }

        override fun addListener(aListener: KCollectionListener<K>) {
            keySetListeners.add(aListener)
        }

        override fun removeListener(aListener: KSetListener<K>) {
            keySetListeners.remove(aListener)
        }

        override fun removeListener(aListener: KCollectionListener<K>) {
            keySetListeners.remove(aListener)
        }

        override fun add(element: K): Boolean = throw UnsupportedOperationException()

        override fun addAll(elements: Collection<K>): Boolean = throw UnsupportedOperationException()

        override fun clear() = this@BasicKObservableMap.clear()

        override fun iterator(): MutableIterator<K> = KObservableMapKeyIterator()

        override fun remove(element: K): Boolean {
            if (containsKey(element)) {
                this@BasicKObservableMap.remove(element)
                return true
            }
            return false
        }

        override fun removeAll(elements: Collection<K>): Boolean = elements.map(::remove).any { it }

        override fun retainAll(elements: Collection<K>): Boolean =
                wrapped.keys.filterNot { elements.contains(it) }.map(::remove).any { it }

        override fun contains(element: K): Boolean = containsKey(element)

        override fun containsAll(elements: Collection<K>): Boolean = elements.all(::contains)

        override fun isEmpty(): Boolean = this@BasicKObservableMap.isEmpty()

        private fun onAdd(anAddedList: Collection<K>) {
            perform(anAddedList, KSetListener<K>::onAdd, KCollectionListener<K>::onAdd)
        }

        private fun onRemove(aRemovedList: Collection<K>) {
            perform(aRemovedList, KSetListener<K>::onRemove, KCollectionListener<K>::onRemove)
        }

        @Suppress("UNCHECKED_CAST")
        private fun <T> perform(
                aParameter: T,
                aListAction: KSetListener<K>.(KObservableSet<K>, T) -> Unit,
                aColAction: KCollectionListener<K>.(KObservableCollection<K>, T) -> Unit
        ) {
            keySetListeners.filter { it is KSetListener<*> || it is KCollectionListener<*> }
                    .forEach {
                        when (it) {
                            is KSetListener<*> -> aListAction.invoke(it as KSetListener<K>, this, aParameter)
                            is KCollectionListener<*> -> aColAction.invoke(
                                    it as KCollectionListener<K>, this, aParameter
                            )
                        }
                    }
        }
    }

    private inner class ValueCollection : MutableCollection<V>, KObservableCollection<V>, KMapListener<K, V> {

        private val valueCollectionListeners by lazy { arrayListOf<KCollectionListener<V>>() }

        init {
            addListener(WeakKMapListener(this))
        }

        override fun onAdd(aMap: KObservableMap<K, V>, anAddedEntries: Collection<Pair<K, V>>) {
            val tempAddedList = anAddedEntries.map { it.second }.toImmutableCollection()
            valueCollectionListeners.forEach { it.onAdd(this, tempAddedList) }
        }

        override fun onRemove(aMap: KObservableMap<K, V>, aRemovedEntries: Collection<Pair<K, V>>) {
            val tempRemovedList = aRemovedEntries.map { it.second }.toImmutableCollection()
            valueCollectionListeners.forEach { it.onRemove(this, tempRemovedList) }
        }

        override fun onReplace(aMap: KObservableMap<K, V>, aReplacedEntries: Collection<Replacement<K, V>>) {
            val tempRemovedValues = aReplacedEntries.map { it.oldValue }.toImmutableCollection()
            val tempAddedValues = aReplacedEntries.map { it.newValue }.toImmutableCollection()
            valueCollectionListeners.forEach { it.onRemove(this, tempRemovedValues) }
            valueCollectionListeners.forEach { it.onAdd(this, tempAddedValues) }
        }

        override fun addListener(aListener: KCollectionListener<V>) {
            valueCollectionListeners.add(aListener)
        }

        override fun removeListener(aListener: KCollectionListener<V>) {
            valueCollectionListeners.remove(aListener)
        }

        override val size: Int get() = this@BasicKObservableMap.size

        override fun add(element: V): Boolean = throw UnsupportedOperationException()

        override fun addAll(elements: Collection<V>): Boolean = throw UnsupportedOperationException()

        override fun clear() = this@BasicKObservableMap.clear()

        override fun iterator(): MutableIterator<V> = KObservableMapValueIterator()

        override fun remove(element: V): Boolean {
            val tempIterator = iterator()
            while (tempIterator.hasNext()) {
                val tempNext = tempIterator.next()
                if (tempNext == element) {
                    tempIterator.remove()
                    return true
                }
            }
            return false
        }

        override fun removeAll(elements: Collection<V>): Boolean = elements.map(::remove).any { it }

        override fun retainAll(elements: Collection<V>): Boolean =
                wrapped.values.filterNot { elements.contains(it) }.map(::remove).any { it }

        override fun contains(element: V): Boolean = containsValue(element)

        override fun containsAll(elements: Collection<V>): Boolean = elements.all(::contains)

        override fun isEmpty(): Boolean = this@BasicKObservableMap.isEmpty()
    }

    private abstract inner class KObservableMapIterator<T> : MutableIterator<T>, KMapListener<K, V> {

        private val wrappedIterator = wrapped.entries.iterator()
        private var valid: Boolean = true
        private val privateChanges = mutableListOf<Any>()
        private var currentElement: Entry<K, V>? = null

        init {
            @Suppress("LeakingThis")
            addListener(WeakKMapListener(this))
        }

        protected open fun invalidateOnReplace() = true

        override fun onAdd(aMap: KObservableMap<K, V>, anAddedEntries: Collection<Pair<K, V>>) = invalidate()

        override fun onRemove(aMap: KObservableMap<K, V>, aRemovedEntries: Collection<Pair<K, V>>) {
            if (!privateChanges.contains(aRemovedEntries)) invalidate()
        }

        override fun onReplace(aMap: KObservableMap<K, V>, aReplacedEntries: Collection<Replacement<K, V>>) {
            if (invalidateOnReplace()) invalidate()
        }

        abstract override fun next(): T

        override fun hasNext(): Boolean = wrappedIterator.hasNext()

        protected fun nextEntry(): MutableEntry<K, V> {
            checkValidity()
            val tempNextElement = wrappedIterator.next()
            currentElement = tempNextElement
            return tempNextElement
        }

        @Suppress("UNCHECKED_CAST")
        override fun remove() {
            checkValidity()

            val tempKey = currentElement?.key
            val tempValue = currentElement?.value

            wrappedIterator.remove()

            val tempRemovedList = immutableListOf(tempKey as K to tempValue as V)
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

    private inner class KObservableMapEntryIterator : KObservableMapIterator<MutableEntry<K, V>>() {
        override fun next(): MutableEntry<K, V> = nextEntry()
    }

    private inner class KObservableMapKeyIterator : KObservableMapIterator<K>() {
        override fun next(): K = nextEntry().key
        override fun invalidateOnReplace(): Boolean = false
    }

    private inner class KObservableMapValueIterator : KObservableMapIterator<V>() {
        override fun next(): V = nextEntry().value
    }
}