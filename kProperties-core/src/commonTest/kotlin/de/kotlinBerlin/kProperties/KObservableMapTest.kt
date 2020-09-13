package de.kotlinBerlin.kProperties

import de.kotlinBerlin.kProperties.collection.*
import kotlin.collections.MutableMap.MutableEntry
import kotlin.test.*

class KObservableMapTest {

    private lateinit var observableMutableMap: KObservableMutableMap<Int, String>

    @BeforeTest
    fun prepare() {
        observableMutableMap = observableMutableMap()
        observableMutableMap[1] = "1"
        observableMutableMap[2] = "2"
        observableMutableMap[3] = "3"
    }

    @Test
    fun testClear() {
        var tempListenerCalledFlag = false
        var tempKeySetListenerCalledFlag = false
        var tempValueCollectionListenerCalledFlag = false
        observableMutableMap.addListener(object : KMutableMapListener<Int, String> {
            override fun onRemove(
                aMutableMap: KObservableMutableMap<Int, String>,
                aRemovedEntries: Collection<Pair<Int, String>>
            ) {
                tempListenerCalledFlag = true
                assertEquals(3, aRemovedEntries.size)
                assertTrue { aRemovedEntries.any { it.first == 1 && it.second == "1" } }
                assertTrue { aRemovedEntries.any { it.first == 2 && it.second == "2" } }
                assertTrue { aRemovedEntries.any { it.first == 3 && it.second == "3" } }
            }
        })

        observableMutableMap.keys.simpleObserveMutableRemove { tempColl ->
            tempKeySetListenerCalledFlag = true
            assertEquals(3, tempColl.size)
            assertTrue { tempColl.any { it == 1 } }
            assertTrue { tempColl.any { it == 2 } }
            assertTrue { tempColl.any { it == 3 } }
        }

        observableMutableMap.values.simpleObserveMutableRemove { tempColl ->
            tempValueCollectionListenerCalledFlag = true
            assertEquals(3, tempColl.size)
            assertTrue { tempColl.any { it == "1" } }
            assertTrue { tempColl.any { it == "2" } }
            assertTrue { tempColl.any { it == "3" } }
        }

        observableMutableMap.clear()
        assertTrue { tempListenerCalledFlag }
        assertTrue { tempKeySetListenerCalledFlag }
        assertTrue { tempValueCollectionListenerCalledFlag }
    }

    @Test
    fun testClearEntrySet() {
        var tempListenerCalledFlag = false
        observableMutableMap.addListener(object : KMutableMapListener<Int, String> {
            override fun onRemove(
                aMutableMap: KObservableMutableMap<Int, String>,
                aRemovedEntries: Collection<Pair<Int, String>>
            ) {
                tempListenerCalledFlag = true
                assertEquals(3, aRemovedEntries.size)
                assertTrue { aRemovedEntries.any { it.first == 1 && it.second == "1" } }
                assertTrue { aRemovedEntries.any { it.first == 2 && it.second == "2" } }
                assertTrue { aRemovedEntries.any { it.first == 3 && it.second == "3" } }
            }
        })
        observableMutableMap.entries.clear()
        assertTrue { tempListenerCalledFlag }
    }

    @Test
    fun testClearKeySet() {
        var tempListenerCalledFlag = false
        var tempSetListenerCalledFlag = false
        observableMutableMap.addListener(object : KMutableMapListener<Int, String> {
            override fun onRemove(
                aMutableMap: KObservableMutableMap<Int, String>,
                aRemovedEntries: Collection<Pair<Int, String>>
            ) {
                tempListenerCalledFlag = true
                assertEquals(3, aRemovedEntries.size)
                assertTrue { aRemovedEntries.any { it.first == 1 && it.second == "1" } }
                assertTrue { aRemovedEntries.any { it.first == 2 && it.second == "2" } }
                assertTrue { aRemovedEntries.any { it.first == 3 && it.second == "3" } }
            }
        })
        val tempKeySet = observableMutableMap.keys
        tempKeySet.addListener(object : KMutableSetListener<Int> {
            override fun onRemove(aMutableSet: KObservableMutableSet<Int>, aRemovedList: Collection<Int>) {
                tempSetListenerCalledFlag = true
                assertEquals(3, aRemovedList.size)
                assertTrue { aRemovedList.any { it == 1 } }
                assertTrue { aRemovedList.any { it == 2 } }
                assertTrue { aRemovedList.any { it == 3 } }
            }
        })
        tempKeySet.clear()
        assertTrue { tempListenerCalledFlag }
        assertTrue { tempSetListenerCalledFlag }
    }

    @Test
    fun testClearValueCollection() {
        var tempListenerCalledFlag = false
        var tempSetListenerCalledFlag = false
        observableMutableMap.addListener(object : KMutableMapListener<Int, String> {
            override fun onRemove(
                aMutableMap: KObservableMutableMap<Int, String>,
                aRemovedEntries: Collection<Pair<Int, String>>
            ) {
                tempListenerCalledFlag = true
                assertEquals(3, aRemovedEntries.size)
                assertTrue { aRemovedEntries.any { it.first == 1 && it.second == "1" } }
                assertTrue { aRemovedEntries.any { it.first == 2 && it.second == "2" } }
                assertTrue { aRemovedEntries.any { it.first == 3 && it.second == "3" } }
            }
        })
        val tempValueCollection = observableMutableMap.values
        tempValueCollection.addListener(object : KMutableCollectionListener<String> {
            override fun onRemove(aMutableCollection: KObservableMutableCollection<String>, aRemovedList: Collection<String>) {
                tempSetListenerCalledFlag = true
                assertEquals(3, aRemovedList.size)
                assertTrue { aRemovedList.any { it == "1" } }
                assertTrue { aRemovedList.any { it == "2" } }
                assertTrue { aRemovedList.any { it == "3" } }
            }
        })
        tempValueCollection.clear()
        assertTrue { tempListenerCalledFlag }
        assertTrue { tempSetListenerCalledFlag }
    }

    @Test
    fun testPutNoReplace() {
        var tempOnAddListenerCalledFlag = false
        var tempKeySetOnAddListenerCalledFlag = false
        var tempValuesCollOnAddListenerCalledFlag = false
        var tempOnReplaceListenerCalledFlag = false
        observableMutableMap.addListener(object : KMutableMapListener<Int, String> {
            override fun onAdd(
                aMutableMap: KObservableMutableMap<Int, String>,
                anAddedEntries: Collection<Pair<Int, String>>
            ) {
                tempOnAddListenerCalledFlag = true
                assertEquals(1, anAddedEntries.size)
                assertEquals(4, anAddedEntries.first().first)
                assertEquals("4", anAddedEntries.first().second)
            }

            override fun onReplace(
                aMutableMap: KObservableMutableMap<Int, String>,
                aReplacedEntries: Collection<MapReplacement<Int, String>>
            ) {
                tempOnReplaceListenerCalledFlag = true
            }
        })

        observableMutableMap.keys.simpleObserveMutableAdd {
            tempKeySetOnAddListenerCalledFlag = true
            assertEquals(1, it.size)
            assertEquals(4, it.first())
        }

        observableMutableMap.values.simpleObserveMutableAdd {
            tempValuesCollOnAddListenerCalledFlag = true
            assertEquals(1, it.size)
            assertEquals("4", it.first())
        }

        observableMutableMap[4] = "4"
        assertTrue { tempOnAddListenerCalledFlag }
        assertTrue { tempKeySetOnAddListenerCalledFlag }
        assertTrue { tempValuesCollOnAddListenerCalledFlag }
        assertFalse { tempOnReplaceListenerCalledFlag }
    }

    @Test
    fun testPutWithReplace() {
        var tempOnAddListenerCalledFlag = false
        var tempValuesCollOnAddListenerCalledFlag = false
        var tempValuesCollOnRemoveListenerCalledFlag = false
        var tempOnReplaceListenerCalledFlag = false
        observableMutableMap.addListener(object : KMutableMapListener<Int, String> {
            override fun onAdd(
                aMutableMap: KObservableMutableMap<Int, String>,
                anAddedEntries: Collection<Pair<Int, String>>
            ) {
                tempOnAddListenerCalledFlag = true
            }

            override fun onReplace(
                aMutableMap: KObservableMutableMap<Int, String>,
                aReplacedEntries: Collection<MapReplacement<Int, String>>
            ) {
                tempOnReplaceListenerCalledFlag = true
                assertEquals(1, aReplacedEntries.size)
                assertEquals(1, aReplacedEntries.first().key)
                assertEquals("1", aReplacedEntries.first().oldValue)
                assertEquals("1A", aReplacedEntries.first().newValue)
            }
        })

        observableMutableMap.keys.simpleObserveMutableAdd {
            fail("KeySet should not be called with on add")
        }
        observableMutableMap.keys.simpleObserveMutableRemove {
            fail("KeySet should not be called with on remove")
        }

        observableMutableMap.values.simpleObserveMutableRemove {
            assertFalse { tempValuesCollOnAddListenerCalledFlag }
            tempValuesCollOnRemoveListenerCalledFlag = true
            assertEquals(1, it.size)
            assertEquals("1", it.first())
        }

        observableMutableMap.values.simpleObserveMutableAdd {
            assertTrue { tempValuesCollOnRemoveListenerCalledFlag }
            tempValuesCollOnAddListenerCalledFlag = true
            assertEquals(1, it.size)
            assertEquals("1A", it.first())
        }

        observableMutableMap[1] = "1A"
        assertFalse { tempOnAddListenerCalledFlag }
        assertTrue { tempOnReplaceListenerCalledFlag }
        assertTrue { tempValuesCollOnAddListenerCalledFlag }
        assertTrue { tempValuesCollOnRemoveListenerCalledFlag }
    }

    @Test
    fun testPutAll() {
        var tempOnAddListenerCalledFlag = false
        var tempKeySetOnAddListenerCalledFlag = false
        var tempValuesCollOnRemoveListenerCalledFlag = false
        var tempValuesCollOnAdd4ListenerCalledFlag = false
        var tempValuesCollOnAdd1AListenerCalledFlag = false
        var tempOnReplaceListenerCalledFlag = false
        observableMutableMap.addListener(object : KMutableMapListener<Int, String> {
            override fun onAdd(
                aMutableMap: KObservableMutableMap<Int, String>,
                anAddedEntries: Collection<Pair<Int, String>>
            ) {
                tempOnAddListenerCalledFlag = true
                tempOnAddListenerCalledFlag = true
                assertEquals(1, anAddedEntries.size)
                assertEquals(4, anAddedEntries.first().first)
                assertEquals("4", anAddedEntries.first().second)
            }

            override fun onReplace(
                aMutableMap: KObservableMutableMap<Int, String>,
                aReplacedEntries: Collection<MapReplacement<Int, String>>
            ) {
                tempOnReplaceListenerCalledFlag = true
                assertEquals(1, aReplacedEntries.size)
                assertEquals(1, aReplacedEntries.first().key)
                assertEquals("1", aReplacedEntries.first().oldValue)
                assertEquals("1A", aReplacedEntries.first().newValue)
            }
        })

        observableMutableMap.keys.simpleObserveMutableRemove {
            fail("KeySet should not be called with on remove")
        }

        observableMutableMap.keys.simpleObserveMutableAdd {
            tempKeySetOnAddListenerCalledFlag = true
            assertEquals(1, it.size)
            assertEquals(4, it.first())
        }

        observableMutableMap.values.simpleObserveMutableRemove {
            tempValuesCollOnRemoveListenerCalledFlag = true
            assertFalse { tempValuesCollOnAdd4ListenerCalledFlag }
            assertFalse { tempValuesCollOnAdd1AListenerCalledFlag }
            assertEquals(1, it.size)
            assertEquals("1", it.first())
        }

        observableMutableMap.values.simpleObserveMutableAdd {
            assertEquals(1, it.size)
            when {
                it.first() == "1A" -> {
                    tempValuesCollOnAdd1AListenerCalledFlag = true
                    assertFalse { tempValuesCollOnAdd4ListenerCalledFlag }
                }
                it.first() == "4" -> {
                    tempValuesCollOnAdd4ListenerCalledFlag = true
                    assertTrue { tempValuesCollOnAdd1AListenerCalledFlag }
                }
                else -> {
                    fail("ValuesColl add listener called with wrong value: ${it.first()}")
                }
            }
        }

        observableMutableMap.putAll(mapOf(4 to "4", 1 to "1A"))
        assertTrue { tempOnAddListenerCalledFlag }
        assertTrue { tempOnReplaceListenerCalledFlag }
        assertTrue { tempKeySetOnAddListenerCalledFlag }
        assertTrue { tempValuesCollOnAdd1AListenerCalledFlag }
        assertTrue { tempValuesCollOnAdd4ListenerCalledFlag }
        assertTrue { tempValuesCollOnRemoveListenerCalledFlag }
    }

    @Test
    fun testRemove() {
        var tempOnRemoveListenerCalledFlag = false
        var tempKeySetOnRemoveListenerCalledFlag = false
        var tempValueCollOnRemoveListenerCalledFlag = false
        observableMutableMap.addListener(object : KMutableMapListener<Int, String> {
            override fun onRemove(
                aMutableMap: KObservableMutableMap<Int, String>,
                aRemovedEntries: Collection<Pair<Int, String>>
            ) {
                tempOnRemoveListenerCalledFlag = true
                assertEquals(1, aRemovedEntries.size)
                assertEquals(1, aRemovedEntries.first().first)
                assertEquals("1", aRemovedEntries.first().second)
            }
        })

        observableMutableMap.keys.simpleObserveMutableRemove {
            tempKeySetOnRemoveListenerCalledFlag = true
            assertEquals(1, it.size)
            assertEquals(1, it.first())
        }

        observableMutableMap.values.simpleObserveMutableRemove {
            tempValueCollOnRemoveListenerCalledFlag = true
            assertEquals(1, it.size)
            assertEquals("1", it.first())
        }

        observableMutableMap.remove(1)

        assertTrue { tempOnRemoveListenerCalledFlag }
        assertTrue { tempKeySetOnRemoveListenerCalledFlag }
        assertTrue { tempValueCollOnRemoveListenerCalledFlag }
    }

    @Test
    fun testRemoveFromKeySet() {
        var tempOnRemoveListenerCalledFlag = false
        var tempKeySetOnRemoveListenerCalledFlag = false
        var tempValueCollOnRemoveListenerCalledFlag = false
        observableMutableMap.addListener(object : KMutableMapListener<Int, String> {
            override fun onRemove(
                aMutableMap: KObservableMutableMap<Int, String>,
                aRemovedEntries: Collection<Pair<Int, String>>
            ) {
                tempOnRemoveListenerCalledFlag = true
                assertEquals(1, aRemovedEntries.size)
                assertEquals(1, aRemovedEntries.first().first)
                assertEquals("1", aRemovedEntries.first().second)
            }
        })

        observableMutableMap.keys.simpleObserveMutableRemove {
            tempKeySetOnRemoveListenerCalledFlag = true
            assertEquals(1, it.size)
            assertEquals(1, it.first())
        }

        observableMutableMap.values.simpleObserveMutableRemove {
            tempValueCollOnRemoveListenerCalledFlag = true
            assertEquals(1, it.size)
            assertEquals("1", it.first())
        }

        observableMutableMap.keys.remove(1)

        assertTrue { tempOnRemoveListenerCalledFlag }
        assertTrue { tempKeySetOnRemoveListenerCalledFlag }
        assertTrue { tempValueCollOnRemoveListenerCalledFlag }
    }

    @Test
    fun testRemoveFromValuesColl() {
        var tempOnRemoveListenerCalledFlag = false
        var tempKeySetOnRemoveListenerCalledFlag = false
        var tempValueCollOnRemoveListenerCalledFlag = false
        observableMutableMap.addListener(object : KMutableMapListener<Int, String> {
            override fun onRemove(
                aMutableMap: KObservableMutableMap<Int, String>,
                aRemovedEntries: Collection<Pair<Int, String>>
            ) {
                tempOnRemoveListenerCalledFlag = true
                assertEquals(1, aRemovedEntries.size)
                assertEquals(1, aRemovedEntries.first().first)
                assertEquals("1", aRemovedEntries.first().second)
            }
        })

        observableMutableMap.keys.simpleObserveMutableRemove {
            tempKeySetOnRemoveListenerCalledFlag = true
            assertEquals(1, it.size)
            assertEquals(1, it.first())
        }

        observableMutableMap.values.simpleObserveMutableRemove {
            tempValueCollOnRemoveListenerCalledFlag = true
            assertEquals(1, it.size)
            assertEquals("1", it.first())
        }

        observableMutableMap.values.remove("1")

        assertTrue { tempOnRemoveListenerCalledFlag }
        assertTrue { tempKeySetOnRemoveListenerCalledFlag }
        assertTrue { tempValueCollOnRemoveListenerCalledFlag }
    }

    @Test
    fun testRemoveFromEntriesSet() {
        var tempOnRemoveListenerCalledFlag = false
        var tempKeySetOnRemoveListenerCalledFlag = false
        var tempValueCollOnRemoveListenerCalledFlag = false
        observableMutableMap.addListener(object : KMutableMapListener<Int, String> {
            override fun onRemove(
                aMutableMap: KObservableMutableMap<Int, String>,
                aRemovedEntries: Collection<Pair<Int, String>>
            ) {
                tempOnRemoveListenerCalledFlag = true
                assertEquals(1, aRemovedEntries.size)
                assertEquals(1, aRemovedEntries.first().first)
                assertEquals("1", aRemovedEntries.first().second)
            }
        })

        observableMutableMap.keys.simpleObserveMutableRemove {
            tempKeySetOnRemoveListenerCalledFlag = true
            assertEquals(1, it.size)
            assertEquals(1, it.first())
        }

        observableMutableMap.values.simpleObserveMutableRemove {
            tempValueCollOnRemoveListenerCalledFlag = true
            assertEquals(1, it.size)
            assertEquals("1", it.first())
        }

        observableMutableMap.entries.remove(observableMutableMap.entries.first())

        assertTrue { tempOnRemoveListenerCalledFlag }
        assertTrue { tempKeySetOnRemoveListenerCalledFlag }
        assertTrue { tempValueCollOnRemoveListenerCalledFlag }
    }

    @Test
    fun testKeySet() {
        try {
            observableMutableMap.keys.add(1)
            fail("Should not be able to add a key to the KeySet of a KObservableMap!")
        } catch (e: UnsupportedOperationException) {
            //Ignore as expected
        }

        //Remove concurrent
        val tempIterator1 = observableMutableMap.keys.iterator()
        observableMutableMap.remove(1)
        try {
            tempIterator1.next()
            fail("Should throw ConcurrentModificationException!")
        } catch (e: ConcurrentModificationException) {
            //Ignore as expected
        }

        observableMutableMap[1] = "1"

        //Add concurrent
        val tempIterator2 = observableMutableMap.keys.iterator()
        observableMutableMap[100] = "100"
        try {
            tempIterator2.next()
            fail("Should throw ConcurrentModificationException!")
        } catch (e: ConcurrentModificationException) {
            //Ignore as expected
        }

        //Replace concurrent
        val tempIterator3 = observableMutableMap.keys.iterator()
        observableMutableMap[1] = "2"
        tempIterator3.next()
    }

    @Test
    fun testRemoveFromKeySetIterator() {
        var tempOnRemoveListenerCalledFlag = false
        var tempKeySetOnRemoveListenerCalledFlag = false
        var tempValueCollOnRemoveListenerCalledFlag = false
        observableMutableMap.addListener(object : KMutableMapListener<Int, String> {
            override fun onRemove(
                aMutableMap: KObservableMutableMap<Int, String>,
                aRemovedEntries: Collection<Pair<Int, String>>
            ) {
                tempOnRemoveListenerCalledFlag = true
                assertEquals(1, aRemovedEntries.size)
                assertEquals(1, aRemovedEntries.first().first)
                assertEquals("1", aRemovedEntries.first().second)
            }
        })

        observableMutableMap.keys.simpleObserveMutableRemove {
            tempKeySetOnRemoveListenerCalledFlag = true
            assertEquals(1, it.size)
            assertEquals(1, it.first())
        }

        observableMutableMap.values.simpleObserveMutableRemove {
            tempValueCollOnRemoveListenerCalledFlag = true
            assertEquals(1, it.size)
            assertEquals("1", it.first())
        }

        val tempIterator = observableMutableMap.keys.iterator()
        tempIterator.next()
        tempIterator.remove()
        tempIterator.next()

        assertTrue { tempOnRemoveListenerCalledFlag }
        assertTrue { tempKeySetOnRemoveListenerCalledFlag }
        assertTrue { tempValueCollOnRemoveListenerCalledFlag }
    }

    @Test
    fun testValuesColl() {
        try {
            observableMutableMap.values.add("1")
            fail("Should not be able to add a key to the ValueCollection of a KObservableMap!")
        } catch (e: UnsupportedOperationException) {
            //Ignore as expected
        }

        //remove concurrent
        val tempIterator1 = observableMutableMap.values.iterator()
        observableMutableMap.remove(1)
        try {
            tempIterator1.next()
            fail("Should throw ConcurrentModificationException!")
        } catch (e: ConcurrentModificationException) {
            //Ignore as expected
        }

        observableMutableMap[1] = "1"

        //add concurrent
        val tempIterator2 = observableMutableMap.values.iterator()
        observableMutableMap[100] = "100"
        try {
            tempIterator2.next()
            fail("Should throw ConcurrentModificationException!")
        } catch (e: ConcurrentModificationException) {
            //Ignore as expected
        }

        //replace concurrent
        val tempIterator3 = observableMutableMap.values.iterator()
        observableMutableMap[1] = "2"
        try {
            tempIterator3.next()
            fail("Should throw ConcurrentModificationException!")
        } catch (e: ConcurrentModificationException) {
            //Ignore as expected
        }
    }

    @Test
    fun testRemoveFromValuesCollIterator() {
        var tempOnRemoveListenerCalledFlag = false
        var tempKeySetOnRemoveListenerCalledFlag = false
        var tempValueCollOnRemoveListenerCalledFlag = false
        observableMutableMap.addListener(object : KMutableMapListener<Int, String> {
            override fun onRemove(
                aMutableMap: KObservableMutableMap<Int, String>,
                aRemovedEntries: Collection<Pair<Int, String>>
            ) {
                tempOnRemoveListenerCalledFlag = true
                assertEquals(1, aRemovedEntries.size)
                assertEquals(1, aRemovedEntries.first().first)
                assertEquals("1", aRemovedEntries.first().second)
            }
        })

        observableMutableMap.keys.simpleObserveMutableRemove {
            tempKeySetOnRemoveListenerCalledFlag = true
            assertEquals(1, it.size)
            assertEquals(1, it.first())
        }

        observableMutableMap.values.simpleObserveMutableRemove {
            tempValueCollOnRemoveListenerCalledFlag = true
            assertEquals(1, it.size)
            assertEquals("1", it.first())
        }

        val tempIterator = observableMutableMap.values.iterator()
        tempIterator.next()
        tempIterator.remove()
        tempIterator.next()

        assertTrue { tempOnRemoveListenerCalledFlag }
        assertTrue { tempKeySetOnRemoveListenerCalledFlag }
        assertTrue { tempValueCollOnRemoveListenerCalledFlag }
    }

    @Test
    fun testEntriesSet() {
        observableMutableMap.entries.add(object : MutableEntry<Int, String> {
            override val key: Int get() = 2000
            override val value: String get() = "2000"
            override fun setValue(newValue: String): String = throw UnsupportedOperationException()
        })
        assertEquals("2000", observableMutableMap[2000])

        //remove concurrent
        val tempIterator1 = observableMutableMap.entries.iterator()
        observableMutableMap.remove(1)
        try {
            tempIterator1.next()
            fail("Should throw ConcurrentModificationException!")
        } catch (e: ConcurrentModificationException) {
            //Ignore as expected
        }

        observableMutableMap[1] = "1"

        //add concurrent
        val tempIterator2 = observableMutableMap.entries.iterator()
        observableMutableMap[100] = "100"
        try {
            tempIterator2.next()
            fail("Should throw ConcurrentModificationException!")
        } catch (e: ConcurrentModificationException) {
            //Ignore as expected
        }

        //replace concurrent
        val tempIterator3 = observableMutableMap.entries.iterator()
        observableMutableMap[1] = "2"
        try {
            tempIterator3.next()
            fail("Should throw ConcurrentModificationException!")
        } catch (e: ConcurrentModificationException) {
            //Ignore as expected
        }
    }

    @Test
    fun testRemoveFromEntriesIterator() {
        var tempOnRemoveListenerCalledFlag = false
        var tempKeySetOnRemoveListenerCalledFlag = false
        var tempValueCollOnRemoveListenerCalledFlag = false
        observableMutableMap.addListener(object : KMutableMapListener<Int, String> {
            override fun onRemove(
                aMutableMap: KObservableMutableMap<Int, String>,
                aRemovedEntries: Collection<Pair<Int, String>>
            ) {
                tempOnRemoveListenerCalledFlag = true
                assertEquals(1, aRemovedEntries.size)
                assertEquals(1, aRemovedEntries.first().first)
                assertEquals("1", aRemovedEntries.first().second)
            }
        })

        observableMutableMap.keys.simpleObserveMutableRemove {
            tempKeySetOnRemoveListenerCalledFlag = true
            assertEquals(1, it.size)
            assertEquals(1, it.first())
        }

        observableMutableMap.values.simpleObserveMutableRemove {
            tempValueCollOnRemoveListenerCalledFlag = true
            assertEquals(1, it.size)
            assertEquals("1", it.first())
        }

        val tempIterator = observableMutableMap.entries.iterator()
        tempIterator.next()
        tempIterator.remove()
        tempIterator.next()

        assertTrue { tempOnRemoveListenerCalledFlag }
        assertTrue { tempKeySetOnRemoveListenerCalledFlag }
        assertTrue { tempValueCollOnRemoveListenerCalledFlag }
    }
}
