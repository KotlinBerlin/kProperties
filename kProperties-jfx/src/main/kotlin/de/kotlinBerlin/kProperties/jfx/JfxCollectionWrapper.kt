@file:Suppress("NOTHING_TO_INLINE", "unused")

package de.kotlinBerlin.kProperties.jfx

import de.kotlinBerlin.kProperties.collection.*
import javafx.collections.*

fun <V> ObservableList<V>.toKObservableMutableList(): KObservableMutableList<V> = JfxKObservableMutableList(this)
inline fun <V> ObservableList<V>.toKObservableList(): KObservableList<V> = toKObservableMutableList().toImmutable()

fun <V> ObservableSet<V>.toKObservableMutableSet(): KObservableMutableSet<V> = JfxKObservableMutableSet(this)
inline fun <V> ObservableSet<V>.toKObservableSet(): KObservableSet<V> = toKObservableMutableSet().toImmutable()

fun <K, V> ObservableMap<K, V>.toKObservableMutableMap(): KObservableMutableMap<K, V> = JfxKObservableMutableMap(this)
fun <K, V> ObservableMap<K, V>.toKObservableMap(): KObservableMap<K, V> = toKObservableMutableMap().toImmutable()

private class JfxKObservableMutableList<V>(private val jfxList: ObservableList<V>) : KObservableMutableList<V>,
    MutableList<V> by jfxList {

    private val collectionListener = arrayListOf<KCollectionListener<V>>()
    private val mutableCollectionListener = arrayListOf<KMutableCollectionListener<V>>()
    private val listListener = arrayListOf<KListListener<V>>()
    private val mutableListListener = arrayListOf<KMutableListListener<V>>()

    private val listChangeListener = ListChangeListener<V> {
        while (it.next()) {
            when {
                it.wasPermutated() -> handlePermutation(it.from, it.to)
                it.wasReplaced() -> handleReplacement(it.addedSubList, it.removed)
                it.wasAdded() -> handleAdd(it.from, it.addedSubList)
                it.wasRemoved() -> handleRemove(it.removed)
            }
        }
    }

    private fun handlePermutation(from: Int, to: Int) {
        val tempPermutation = ListPermutation(elementAt(to), from, to)
        listListener.forEach { it.onMove(this, listOf(tempPermutation)) }
        mutableListListener.forEach { it.onMove(this, listOf(tempPermutation)) }
    }

    private fun handleReplacement(addedSubList: List<V>, removed: List<V>) {
        val tempReplacements = addedSubList.mapIndexed { index, element ->
            ListReplacement(removed[index], element, indexOf(element))
        }
        listListener.forEach { it.onReplace(this, tempReplacements) }
        mutableListListener.forEach { it.onReplace(this, tempReplacements) }
    }

    private fun handleRemove(removed: List<V>) {
        collectionListener.forEach { it.onRemove(this, removed) }
        mutableCollectionListener.forEach { it.onRemove(this, removed) }
        listListener.forEach { it.onRemove(this, removed) }
        mutableListListener.forEach { it.onRemove(this, removed) }
    }

    private fun handleAdd(aStartIndex: Int, added: List<V>) {
        collectionListener.forEach { it.onAdd(this, added) }
        mutableCollectionListener.forEach { it.onAdd(this, added) }
        listListener.forEach { it.onAdd(this, aStartIndex, added) }
        mutableListListener.forEach { it.onAdd(this, aStartIndex, added) }
    }

    init {
        jfxList.addListener(WeakListChangeListener(listChangeListener))
    }

    override fun addListener(aListener: KListListener<V>) {
        listListener.add(aListener)
    }

    override fun addListener(aListener: KMutableListListener<V>) {
        mutableListListener.add(aListener)
    }

    override fun addListener(aListener: KCollectionListener<V>) {
        collectionListener.add(aListener)
    }

    override fun addListener(aListener: KMutableCollectionListener<V>) {
        mutableCollectionListener.add(aListener)
    }

    override fun removeListener(aListener: KCollectionListener<V>) {
        collectionListener.remove(aListener)
    }

    override fun removeListener(aListener: KListListener<V>) {
        listListener.remove(aListener)
    }

    override fun removeListener(aListener: KMutableListListener<V>) {
        mutableListListener.remove(aListener)
    }

    override fun removeListener(aListener: KMutableCollectionListener<V>) {
        mutableCollectionListener.remove(aListener)
    }
}

private class JfxKObservableMutableSet<V>(private val jfxSet: ObservableSet<V>) : KObservableMutableSet<V>,
    MutableSet<V> by jfxSet {

    private val collectionListener = arrayListOf<KCollectionListener<V>>()
    private val mutableCollectionListener = arrayListOf<KMutableCollectionListener<V>>()
    private val setListener = arrayListOf<KSetListener<V>>()
    private val mutableSetListener = arrayListOf<KMutableSetListener<V>>()

    private val setChangeListener = SetChangeListener<V> {
        if (it.wasAdded()) {
            handleAdd(it.elementAdded)
        }
        if (it.wasRemoved()) {
            handleRemove(it.elementRemoved)
        }
    }

    init {
        jfxSet.addListener(WeakSetChangeListener(setChangeListener))
    }

    private fun handleRemove(removed: V) {
        val tempRemovedList = listOf(removed)
        collectionListener.forEach { it.onRemove(this, tempRemovedList) }
        mutableCollectionListener.forEach { it.onRemove(this, tempRemovedList) }
        setListener.forEach { it.onRemove(this, tempRemovedList) }
        mutableSetListener.forEach { it.onRemove(this, tempRemovedList) }
    }

    private fun handleAdd(added: V) {
        val tempAddedList = listOf(added)
        collectionListener.forEach { it.onAdd(this, tempAddedList) }
        mutableCollectionListener.forEach { it.onAdd(this, tempAddedList) }
        setListener.forEach { it.onAdd(this, tempAddedList) }
        mutableSetListener.forEach { it.onAdd(this, tempAddedList) }
    }

    override fun addListener(aListener: KSetListener<V>) {
        setListener.add(aListener)
    }

    override fun addListener(aListener: KMutableSetListener<V>) {
        mutableSetListener.add(aListener)
    }

    override fun addListener(aListener: KCollectionListener<V>) {
        collectionListener.add(aListener)
    }

    override fun addListener(aListener: KMutableCollectionListener<V>) {
        mutableCollectionListener.add(aListener)
    }

    override fun removeListener(aListener: KCollectionListener<V>) {
        collectionListener.remove(aListener)
    }

    override fun removeListener(aListener: KSetListener<V>) {
        setListener.remove(aListener)
    }

    override fun removeListener(aListener: KMutableSetListener<V>) {
        mutableSetListener.remove(aListener)
    }

    override fun removeListener(aListener: KMutableCollectionListener<V>) {
        mutableCollectionListener.remove(aListener)
    }

}

private class JfxKObservableMutableMap<K, V>(private val jfxMap: ObservableMap<K, V>) : KObservableMutableMap<K, V>,
    MutableMap<K, V> by jfxMap {

    override val keys: KObservableMutableSet<K> by lazy { jfxMap.keys.toObservableMutableSet() }
    override val values: KObservableMutableCollection<V> = throw UnsupportedOperationException(
        "values are currently not supported in jfx interop"
    )

    private val mapListeners = arrayListOf<KMapListener<K, V>>()
    private val mutableMapListeners = arrayListOf<KMutableMapListener<K, V>>()

    private val mapChangeListener = MapChangeListener<K, V> {
        when {
            it.wasAdded() && it.wasRemoved() -> {
                mapListeners.forEach { listener ->
                    listener.onReplace(this, listOf(MapReplacement(it.key, it.valueRemoved, it.valueAdded)))
                }
            }
            it.wasAdded() -> {
                mapListeners.forEach { listener -> listener.onAdd(this, listOf(it.key to it.valueAdded)) }
                mutableMapListeners.forEach { listener -> listener.onAdd(this, listOf(it.key to it.valueAdded)) }
            }
            it.wasRemoved() -> {
                mapListeners.forEach { listener -> listener.onRemove(this, listOf(it.key to it.valueRemoved)) }
                mutableMapListeners.forEach { listener -> listener.onRemove(this, listOf(it.key to it.valueRemoved)) }
            }
        }
    }

    init {
        jfxMap.addListener(WeakMapChangeListener(mapChangeListener))
    }

    override fun addListener(aListener: KMapListener<K, V>) {
        mapListeners.add(aListener)
    }

    override fun addListener(aListener: KMutableMapListener<K, V>) {
        mutableMapListeners.add(aListener)
    }

    override fun removeListener(aListener: KMapListener<K, V>) {
        mapListeners.remove(aListener)
    }

    override fun removeListener(aListener: KMutableMapListener<K, V>) {
        mutableMapListeners.remove(aListener)
    }
}
