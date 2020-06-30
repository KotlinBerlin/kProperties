@file:Suppress("unused")

package de.kotlinBerlin.kProperties.util

/**
 * Converts this [Collection] to an immutable one not only relying on the interface immutability,
 * but also preventing a cast to a [MutableCollection].
 */
fun <E> Collection<E>.toImmutableCollection(): Collection<E> {
    return object : Collection<E> by this {}
}

/**
 * Converts this [List] to an immutable one not only relying on the interface immutability,
 * but also preventing a cast to a [MutableList].
 */
fun <E> List<E>.toImmutableList(): List<E> {
    return object : List<E> by this {}
}

/**
 * Converts this [Set] to an immutable one not only relying on the interface immutability,
 * but also preventing a cast to a [MutableSet].
 */
fun <E> Set<E>.toImmutableSet(): Set<E> {
    return object : Set<E> by this {}
}

/**
 * Converts this [Map] to an immutable one not only relying on the interface immutability,
 * but also preventing a cast to a [MutableMap].
 */
fun <K, V> Map<K, V>.toImmutableMap(): Map<K, V> {
    return object : Map<K, V> by this {}
}

/**
 * Copies this [Collection] to an immutable one not only relying on the interface immutability,
 * but also preventing a cast to a [MutableCollection].
 */
fun <E> Collection<E>.copyToImmutableCollection(): Collection<E> {
    return ArrayList(this).toImmutableCollection()
}

/**
 * Converts this [List] to an immutable one not only relying on the interface immutability,
 * but also preventing a cast to a [MutableList].
 */
fun <E> List<E>.copyToImmutableList(): List<E> {
    return ArrayList(this).toImmutableList()
}

/**
 * Converts this [Set] to an immutable one not only relying on the interface immutability,
 * but also preventing a cast to a [MutableSet].
 */
fun <E> Set<E>.copyToImmutableSet(): Set<E> {
    return HashSet(this).toImmutableSet()
}

/**
 * Converts this [Map] to an immutable one not only relying on the interface immutability,
 * but also preventing a cast to a [MutableMap].
 */
fun <K, V> Map<K, V>.copyToImmutableMap(): Map<K, V> {
    return HashMap(this).toImmutableMap()
}

/**
 * Builder for an [Collection] that is immutable not only relying on the interface immutability,
 * but also preventing a cast to a [MutableCollection].
 */
inline fun <E> createImmutableCollection(
        creator: () -> MutableCollection<E> = ::ArrayList,
        init: MutableCollection<E>.() -> Unit
): Collection<E> {
    val tempCollection = creator.invoke()
    tempCollection.init()
    return tempCollection.toImmutableCollection()
}

/**
 * Builder for an [List] that is immutable not only relying on the interface immutability,
 * but also preventing a cast to a [MutableList].
 */
inline fun <E> createImmutableList(init: MutableList<E>.() -> Unit): List<E> {
    val tempList = ArrayList<E>()
    tempList.init()
    return tempList.toImmutableList()
}

/**
 * Builder for an [Set] that is immutable not only relying on the interface immutability,
 * but also preventing a cast to a [MutableSet].
 */
inline fun <E> createImmutableSet(init: MutableSet<E>.() -> Unit): Set<E> {
    val tempSet = HashSet<E>()
    tempSet.init()
    return tempSet.toImmutableSet()
}

/**
 * Builder for an [Map] that is immutable not only relying on the interface immutability,
 * but also preventing a cast to a [MutableMap].
 */
inline fun <K, V> createImmutableMap(init: MutableMap<K, V>.() -> Unit): Map<K, V> {
    val tempMap = HashMap<K, V>()
    tempMap.init()
    return tempMap.toImmutableMap()
}

/**
 * Creates a [Collection] that contains all [elements] and is immutable not only relying on the
 * interface immutability, but also preventing a cast to a [MutableCollection].
 */
fun <E> immutableCollectionOf(vararg elements: E): Collection<E> {
    return createImmutableCollection(init = {
        addAll(elements)
    })
}

/**
 * Creates a [Collection] that contains all [elements] and is immutable not only relying on the
 * interface immutability, but also preventing a cast to a [MutableCollection].
 */
fun <E> immutableCollectionOf(elements: Iterable<E>): Collection<E> {
    return createImmutableCollection(init = {
        addAll(elements)
    })
}

/**
 * Creates a [List] that contains all [elements] and is immutable not only relying on the
 * interface immutability, but also preventing a cast to a [MutableList].
 */
fun <E> immutableListOf(vararg elements: E): List<E> {
    return createImmutableList {
        addAll(elements)
    }
}

/**
 * Creates a [List] that contains all [elements] and is immutable not only relying on the
 * interface immutability, but also preventing a cast to a [MutableList].
 */
fun <E> immutableListOf(elements: Iterable<E>): List<E> {
    return createImmutableList {
        addAll(elements)
    }
}

/**
 * Creates a [Set] that contains all [elements] and is immutable not only relying on the
 * interface immutability, but also preventing a cast to a [MutableSet].
 */
fun <E> immutableSetOf(vararg elements: E): Set<E> {
    return createImmutableSet {
        addAll(elements)
    }
}

/**
 * Creates a [Set] that contains all [elements] and is immutable not only relying on the
 * interface immutability, but also preventing a cast to a [MutableSet].
 */
fun <E> immutableSetOf(elements: Iterable<E>): Set<E> {
    return createImmutableSet {
        addAll(elements)
    }
}

/**
 * Creates a [Map] that contains all [elements] and is immutable not only relying on the
 * interface immutability, but also preventing a cast to a [MutableMap].
 */
fun <K, V> immutableMapOf(vararg elements: Pair<K, V>): Map<K, V> {
    return createImmutableMap {
        putAll(elements)
    }
}

/**
 * Creates a [Map] that contains all [elements] and is immutable not only relying on the
 * interface immutability, but also preventing a cast to a [MutableMap].
 */
fun <K, V> immutableMapOf(elements: Iterable<Pair<K, V>>): Map<K, V> {
    return createImmutableMap {
        putAll(elements)
    }
}