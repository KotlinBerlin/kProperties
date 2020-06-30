package de.kotlinBerlin.kProperties

import de.kotlinBerlin.kProperties.collection.*
import de.kotlinBerlin.kProperties.collection.KMapListener.Replacement
import kotlin.collections.MutableMap.MutableEntry
import kotlin.test.*

class KObservableMapTest {

    private lateinit var observableMap: KObservableMap<Int, String>

    @BeforeTest
    fun prepare() {
        observableMap = observableMap()
        observableMap[1] = "1"
        observableMap[2] = "2"
        observableMap[3] = "3"
    }

    @Test
    fun testClear() {
        var tempListenerCalledFlag = false
        var tempKeySetListenerCalledFlag = false
        var tempValueCollectionListenerCalledFlag = false
        observableMap.addListener(object : KMapListener<Int, String> {
            override fun onRemove(aMap: KObservableMap<Int, String>, aRemovedEntries: Collection<Pair<Int, String>>) {
                tempListenerCalledFlag = true
                assertEquals(3, aRemovedEntries.size)
                assertTrue { aRemovedEntries.any { it.first == 1 && it.second == "1" } }
                assertTrue { aRemovedEntries.any { it.first == 2 && it.second == "2" } }
                assertTrue { aRemovedEntries.any { it.first == 3 && it.second == "3" } }
            }
        })

        observableMap.keys.simpleObserveRemove { tempColl ->
            tempKeySetListenerCalledFlag = true
            assertEquals(3, tempColl.size)
            assertTrue { tempColl.any { it == 1 } }
            assertTrue { tempColl.any { it == 2 } }
            assertTrue { tempColl.any { it == 3 } }
        }

        observableMap.values.simpleObserveRemove { tempColl ->
            tempValueCollectionListenerCalledFlag = true
            assertEquals(3, tempColl.size)
            assertTrue { tempColl.any { it == "1" } }
            assertTrue { tempColl.any { it == "2" } }
            assertTrue { tempColl.any { it == "3" } }
        }

        observableMap.clear()
        assertTrue { tempListenerCalledFlag }
        assertTrue { tempKeySetListenerCalledFlag }
        assertTrue { tempValueCollectionListenerCalledFlag }
    }

    @Test
    fun testClearEntrySet() {
        var tempListenerCalledFlag = false
        observableMap.addListener(object : KMapListener<Int, String> {
            override fun onRemove(aMap: KObservableMap<Int, String>, aRemovedEntries: Collection<Pair<Int, String>>) {
                tempListenerCalledFlag = true
                assertEquals(3, aRemovedEntries.size)
                assertTrue { aRemovedEntries.any { it.first == 1 && it.second == "1" } }
                assertTrue { aRemovedEntries.any { it.first == 2 && it.second == "2" } }
                assertTrue { aRemovedEntries.any { it.first == 3 && it.second == "3" } }
            }
        })
        observableMap.entries.clear()
        assertTrue { tempListenerCalledFlag }
    }

    @Test
    fun testClearKeySet() {
        var tempListenerCalledFlag = false
        var tempSetListenerCalledFlag = false
        observableMap.addListener(object : KMapListener<Int, String> {
            override fun onRemove(aMap: KObservableMap<Int, String>, aRemovedEntries: Collection<Pair<Int, String>>) {
                tempListenerCalledFlag = true
                assertEquals(3, aRemovedEntries.size)
                assertTrue { aRemovedEntries.any { it.first == 1 && it.second == "1" } }
                assertTrue { aRemovedEntries.any { it.first == 2 && it.second == "2" } }
                assertTrue { aRemovedEntries.any { it.first == 3 && it.second == "3" } }
            }
        })
        val tempKeySet = observableMap.keys
        tempKeySet.addListener(object : KSetListener<Int> {
            override fun onRemove(aSet: KObservableSet<Int>, aRemovedList: Collection<Int>) {
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
        observableMap.addListener(object : KMapListener<Int, String> {
            override fun onRemove(aMap: KObservableMap<Int, String>, aRemovedEntries: Collection<Pair<Int, String>>) {
                tempListenerCalledFlag = true
                assertEquals(3, aRemovedEntries.size)
                assertTrue { aRemovedEntries.any { it.first == 1 && it.second == "1" } }
                assertTrue { aRemovedEntries.any { it.first == 2 && it.second == "2" } }
                assertTrue { aRemovedEntries.any { it.first == 3 && it.second == "3" } }
            }
        })
        val tempValueCollection = observableMap.values
        tempValueCollection.addListener(object : KCollectionListener<String> {
            override fun onRemove(aCollection: KObservableCollection<String>, aRemovedList: Collection<String>) {
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
        observableMap.addListener(object : KMapListener<Int, String> {
            override fun onAdd(aMap: KObservableMap<Int, String>, anAddedEntries: Collection<Pair<Int, String>>) {
                tempOnAddListenerCalledFlag = true
                assertEquals(1, anAddedEntries.size)
                assertEquals(4, anAddedEntries.first().first)
                assertEquals("4", anAddedEntries.first().second)
            }

            override fun onReplace(
                    aMap: KObservableMap<Int, String>,
                    aReplacedEntries: Collection<Replacement<Int, String>>
            ) {
                tempOnReplaceListenerCalledFlag = true
            }
        })

        observableMap.keys.simpleObserveAdd {
            tempKeySetOnAddListenerCalledFlag = true
            assertEquals(1, it.size)
            assertEquals(4, it.first())
        }

        observableMap.values.simpleObserveAdd {
            tempValuesCollOnAddListenerCalledFlag = true
            assertEquals(1, it.size)
            assertEquals("4", it.first())
        }

        observableMap[4] = "4"
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
        observableMap.addListener(object : KMapListener<Int, String> {
            override fun onAdd(aMap: KObservableMap<Int, String>, anAddedEntries: Collection<Pair<Int, String>>) {
                tempOnAddListenerCalledFlag = true
            }

            override fun onReplace(
                    aMap: KObservableMap<Int, String>,
                    aReplacedEntries: Collection<Replacement<Int, String>>
            ) {
                tempOnReplaceListenerCalledFlag = true
                assertEquals(1, aReplacedEntries.size)
                assertEquals(1, aReplacedEntries.first().key)
                assertEquals("1", aReplacedEntries.first().oldValue)
                assertEquals("1A", aReplacedEntries.first().newValue)
            }
        })

        observableMap.keys.simpleObserveAdd {
            fail("KeySet should not be called with on add")
        }
        observableMap.keys.simpleObserveRemove {
            fail("KeySet should not be called with on remove")
        }

        observableMap.values.simpleObserveRemove {
            assertFalse { tempValuesCollOnAddListenerCalledFlag }
            tempValuesCollOnRemoveListenerCalledFlag = true
            assertEquals(1, it.size)
            assertEquals("1", it.first())
        }

        observableMap.values.simpleObserveAdd {
            assertTrue { tempValuesCollOnRemoveListenerCalledFlag }
            tempValuesCollOnAddListenerCalledFlag = true
            assertEquals(1, it.size)
            assertEquals("1A", it.first())
        }

        observableMap[1] = "1A"
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
        observableMap.addListener(object : KMapListener<Int, String> {
            override fun onAdd(aMap: KObservableMap<Int, String>, anAddedEntries: Collection<Pair<Int, String>>) {
                tempOnAddListenerCalledFlag = true
                tempOnAddListenerCalledFlag = true
                assertEquals(1, anAddedEntries.size)
                assertEquals(4, anAddedEntries.first().first)
                assertEquals("4", anAddedEntries.first().second)
            }

            override fun onReplace(
                    aMap: KObservableMap<Int, String>,
                    aReplacedEntries: Collection<Replacement<Int, String>>
            ) {
                tempOnReplaceListenerCalledFlag = true
                assertEquals(1, aReplacedEntries.size)
                assertEquals(1, aReplacedEntries.first().key)
                assertEquals("1", aReplacedEntries.first().oldValue)
                assertEquals("1A", aReplacedEntries.first().newValue)
            }
        })

        observableMap.keys.simpleObserveRemove {
            fail("KeySet should not be called with on remove")
        }

        observableMap.keys.simpleObserveAdd {
            tempKeySetOnAddListenerCalledFlag = true
            assertEquals(1, it.size)
            assertEquals(4, it.first())
        }

        observableMap.values.simpleObserveRemove {
            tempValuesCollOnRemoveListenerCalledFlag = true
            assertFalse { tempValuesCollOnAdd4ListenerCalledFlag }
            assertFalse { tempValuesCollOnAdd1AListenerCalledFlag }
            assertEquals(1, it.size)
            assertEquals("1", it.first())
        }

        observableMap.values.simpleObserveAdd {
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

        observableMap.putAll(mapOf(4 to "4", 1 to "1A"))
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
        observableMap.addListener(object : KMapListener<Int, String> {
            override fun onRemove(aMap: KObservableMap<Int, String>, aRemovedEntries: Collection<Pair<Int, String>>) {
                tempOnRemoveListenerCalledFlag = true
                assertEquals(1, aRemovedEntries.size)
                assertEquals(1, aRemovedEntries.first().first)
                assertEquals("1", aRemovedEntries.first().second)
            }
        })

        observableMap.keys.simpleObserveRemove {
            tempKeySetOnRemoveListenerCalledFlag = true
            assertEquals(1, it.size)
            assertEquals(1, it.first())
        }

        observableMap.values.simpleObserveRemove {
            tempValueCollOnRemoveListenerCalledFlag = true
            assertEquals(1, it.size)
            assertEquals("1", it.first())
        }

        observableMap.remove(1)

        assertTrue { tempOnRemoveListenerCalledFlag }
        assertTrue { tempKeySetOnRemoveListenerCalledFlag }
        assertTrue { tempValueCollOnRemoveListenerCalledFlag }
    }

    @Test
    fun testRemoveFromKeySet() {
        var tempOnRemoveListenerCalledFlag = false
        var tempKeySetOnRemoveListenerCalledFlag = false
        var tempValueCollOnRemoveListenerCalledFlag = false
        observableMap.addListener(object : KMapListener<Int, String> {
            override fun onRemove(aMap: KObservableMap<Int, String>, aRemovedEntries: Collection<Pair<Int, String>>) {
                tempOnRemoveListenerCalledFlag = true
                assertEquals(1, aRemovedEntries.size)
                assertEquals(1, aRemovedEntries.first().first)
                assertEquals("1", aRemovedEntries.first().second)
            }
        })

        observableMap.keys.simpleObserveRemove {
            tempKeySetOnRemoveListenerCalledFlag = true
            assertEquals(1, it.size)
            assertEquals(1, it.first())
        }

        observableMap.values.simpleObserveRemove {
            tempValueCollOnRemoveListenerCalledFlag = true
            assertEquals(1, it.size)
            assertEquals("1", it.first())
        }

        observableMap.keys.remove(1)

        assertTrue { tempOnRemoveListenerCalledFlag }
        assertTrue { tempKeySetOnRemoveListenerCalledFlag }
        assertTrue { tempValueCollOnRemoveListenerCalledFlag }
    }

    @Test
    fun testRemoveFromValuesColl() {
        var tempOnRemoveListenerCalledFlag = false
        var tempKeySetOnRemoveListenerCalledFlag = false
        var tempValueCollOnRemoveListenerCalledFlag = false
        observableMap.addListener(object : KMapListener<Int, String> {
            override fun onRemove(aMap: KObservableMap<Int, String>, aRemovedEntries: Collection<Pair<Int, String>>) {
                tempOnRemoveListenerCalledFlag = true
                assertEquals(1, aRemovedEntries.size)
                assertEquals(1, aRemovedEntries.first().first)
                assertEquals("1", aRemovedEntries.first().second)
            }
        })

        observableMap.keys.simpleObserveRemove {
            tempKeySetOnRemoveListenerCalledFlag = true
            assertEquals(1, it.size)
            assertEquals(1, it.first())
        }

        observableMap.values.simpleObserveRemove {
            tempValueCollOnRemoveListenerCalledFlag = true
            assertEquals(1, it.size)
            assertEquals("1", it.first())
        }

        observableMap.values.remove("1")

        assertTrue { tempOnRemoveListenerCalledFlag }
        assertTrue { tempKeySetOnRemoveListenerCalledFlag }
        assertTrue { tempValueCollOnRemoveListenerCalledFlag }
    }

    @Test
    fun testRemoveFromEntriesSet() {
        var tempOnRemoveListenerCalledFlag = false
        var tempKeySetOnRemoveListenerCalledFlag = false
        var tempValueCollOnRemoveListenerCalledFlag = false
        observableMap.addListener(object : KMapListener<Int, String> {
            override fun onRemove(aMap: KObservableMap<Int, String>, aRemovedEntries: Collection<Pair<Int, String>>) {
                tempOnRemoveListenerCalledFlag = true
                assertEquals(1, aRemovedEntries.size)
                assertEquals(1, aRemovedEntries.first().first)
                assertEquals("1", aRemovedEntries.first().second)
            }
        })

        observableMap.keys.simpleObserveRemove {
            tempKeySetOnRemoveListenerCalledFlag = true
            assertEquals(1, it.size)
            assertEquals(1, it.first())
        }

        observableMap.values.simpleObserveRemove {
            tempValueCollOnRemoveListenerCalledFlag = true
            assertEquals(1, it.size)
            assertEquals("1", it.first())
        }

        observableMap.entries.remove(observableMap.entries.first())

        assertTrue { tempOnRemoveListenerCalledFlag }
        assertTrue { tempKeySetOnRemoveListenerCalledFlag }
        assertTrue { tempValueCollOnRemoveListenerCalledFlag }
    }

    @Test
    fun testKeySet() {
        try {
            observableMap.keys.add(1)
            fail("Should not be able to add a key to the KeySet of a KObservableMap!")
        } catch (e: UnsupportedOperationException) {
            //Ignore as expected
        }

        //Remove concurrent
        val tempIterator1 = observableMap.keys.iterator()
        observableMap.remove(1)
        try {
            tempIterator1.next()
            fail("Should throw ConcurrentModificationException!")
        } catch (e: ConcurrentModificationException) {
            //Ignore as expected
        }

        observableMap[1] = "1"

        //Add concurrent
        val tempIterator2 = observableMap.keys.iterator()
        observableMap[100] = "100"
        try {
            tempIterator2.next()
            fail("Should throw ConcurrentModificationException!")
        } catch (e: ConcurrentModificationException) {
            //Ignore as expected
        }

        //Replace concurrent
        val tempIterator3 = observableMap.keys.iterator()
        observableMap[1] = "2"
        tempIterator3.next()
    }

    @Test
    fun testRemoveFromKeySetIterator() {
        var tempOnRemoveListenerCalledFlag = false
        var tempKeySetOnRemoveListenerCalledFlag = false
        var tempValueCollOnRemoveListenerCalledFlag = false
        observableMap.addListener(object : KMapListener<Int, String> {
            override fun onRemove(aMap: KObservableMap<Int, String>, aRemovedEntries: Collection<Pair<Int, String>>) {
                tempOnRemoveListenerCalledFlag = true
                assertEquals(1, aRemovedEntries.size)
                assertEquals(1, aRemovedEntries.first().first)
                assertEquals("1", aRemovedEntries.first().second)
            }
        })

        observableMap.keys.simpleObserveRemove {
            tempKeySetOnRemoveListenerCalledFlag = true
            assertEquals(1, it.size)
            assertEquals(1, it.first())
        }

        observableMap.values.simpleObserveRemove {
            tempValueCollOnRemoveListenerCalledFlag = true
            assertEquals(1, it.size)
            assertEquals("1", it.first())
        }

        val tempIterator = observableMap.keys.iterator()
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
            observableMap.values.add("1")
            fail("Should not be able to add a key to the ValueCollection of a KObservableMap!")
        } catch (e: UnsupportedOperationException) {
            //Ignore as expected
        }

        //remove concurrent
        val tempIterator1 = observableMap.values.iterator()
        observableMap.remove(1)
        try {
            tempIterator1.next()
            fail("Should throw ConcurrentModificationException!")
        } catch (e: ConcurrentModificationException) {
            //Ignore as expected
        }

        observableMap[1] = "1"

        //add concurrent
        val tempIterator2 = observableMap.values.iterator()
        observableMap[100] = "100"
        try {
            tempIterator2.next()
            fail("Should throw ConcurrentModificationException!")
        } catch (e: ConcurrentModificationException) {
            //Ignore as expected
        }

        //replace concurrent
        val tempIterator3 = observableMap.values.iterator()
        observableMap[1] = "2"
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
        observableMap.addListener(object : KMapListener<Int, String> {
            override fun onRemove(aMap: KObservableMap<Int, String>, aRemovedEntries: Collection<Pair<Int, String>>) {
                tempOnRemoveListenerCalledFlag = true
                assertEquals(1, aRemovedEntries.size)
                assertEquals(1, aRemovedEntries.first().first)
                assertEquals("1", aRemovedEntries.first().second)
            }
        })

        observableMap.keys.simpleObserveRemove {
            tempKeySetOnRemoveListenerCalledFlag = true
            assertEquals(1, it.size)
            assertEquals(1, it.first())
        }

        observableMap.values.simpleObserveRemove {
            tempValueCollOnRemoveListenerCalledFlag = true
            assertEquals(1, it.size)
            assertEquals("1", it.first())
        }

        val tempIterator = observableMap.values.iterator()
        tempIterator.next()
        tempIterator.remove()
        tempIterator.next()

        assertTrue { tempOnRemoveListenerCalledFlag }
        assertTrue { tempKeySetOnRemoveListenerCalledFlag }
        assertTrue { tempValueCollOnRemoveListenerCalledFlag }
    }

    @Test
    fun testEntriesSet() {
        observableMap.entries.add(object : MutableEntry<Int, String> {
            override val key: Int get() = 2000
            override val value: String get() = "2000"
            override fun setValue(newValue: String): String = throw UnsupportedOperationException()
        })
        assertEquals("2000", observableMap[2000])

        //remove concurrent
        val tempIterator1 = observableMap.entries.iterator()
        observableMap.remove(1)
        try {
            tempIterator1.next()
            fail("Should throw ConcurrentModificationException!")
        } catch (e: ConcurrentModificationException) {
            //Ignore as expected
        }

        observableMap[1] = "1"

        //add concurrent
        val tempIterator2 = observableMap.entries.iterator()
        observableMap[100] = "100"
        try {
            tempIterator2.next()
            fail("Should throw ConcurrentModificationException!")
        } catch (e: ConcurrentModificationException) {
            //Ignore as expected
        }

        //replace concurrent
        val tempIterator3 = observableMap.entries.iterator()
        observableMap[1] = "2"
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
        observableMap.addListener(object : KMapListener<Int, String> {
            override fun onRemove(aMap: KObservableMap<Int, String>, aRemovedEntries: Collection<Pair<Int, String>>) {
                tempOnRemoveListenerCalledFlag = true
                assertEquals(1, aRemovedEntries.size)
                assertEquals(1, aRemovedEntries.first().first)
                assertEquals("1", aRemovedEntries.first().second)
            }
        })

        observableMap.keys.simpleObserveRemove {
            tempKeySetOnRemoveListenerCalledFlag = true
            assertEquals(1, it.size)
            assertEquals(1, it.first())
        }

        observableMap.values.simpleObserveRemove {
            tempValueCollOnRemoveListenerCalledFlag = true
            assertEquals(1, it.size)
            assertEquals("1", it.first())
        }

        val tempIterator = observableMap.entries.iterator()
        tempIterator.next()
        tempIterator.remove()
        tempIterator.next()

        assertTrue { tempOnRemoveListenerCalledFlag }
        assertTrue { tempKeySetOnRemoveListenerCalledFlag }
        assertTrue { tempValueCollOnRemoveListenerCalledFlag }
    }
}