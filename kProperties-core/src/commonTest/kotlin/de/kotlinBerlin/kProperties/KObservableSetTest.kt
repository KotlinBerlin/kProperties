package de.kotlinBerlin.kProperties

import de.kotlinBerlin.kProperties.collection.KObservableMutableSet
import de.kotlinBerlin.kProperties.collection.KMutableSetListener
import de.kotlinBerlin.kProperties.collection.observableMutableSet
import kotlin.test.*

class KObservableSetTest {

    private lateinit var observableMutableSet: KObservableMutableSet<Int>

    @BeforeTest
    fun prepare() {
        observableMutableSet = observableMutableSet()
        observableMutableSet.add(1)
        observableMutableSet.add(2)
    }

    @Test
    fun testAdd() {
        var tempListenerCalledFlag = false
        observableMutableSet.addListener(object : KMutableSetListener<Int> {
            override fun onAdd(aMutableSet: KObservableMutableSet<Int>, anAddedList: Collection<Int>) {
                tempListenerCalledFlag = true
                assertTrue { anAddedList.size == 1 }
                assertSame(anAddedList.first(), 3)
            }
        })
        observableMutableSet.add(3)
        assertTrue { tempListenerCalledFlag }
    }

    @Test
    fun testAddAll() {
        var tempListenerCalledFlag = false
        observableMutableSet.addListener(object : KMutableSetListener<Int> {
            override fun onAdd(aMutableSet: KObservableMutableSet<Int>, anAddedList: Collection<Int>) {
                tempListenerCalledFlag = true
                assertTrue { anAddedList.size == 2 }
                assertTrue { anAddedList.containsAll(listOf(3, 4)) }
            }
        })
        observableMutableSet.addAll(listOf(3, 4))
        assertTrue { tempListenerCalledFlag }
    }

    @Test
    fun testClear() {
        var tempListenerCalledFlag = false
        observableMutableSet.addListener(object : KMutableSetListener<Int> {
            override fun onRemove(aMutableSet: KObservableMutableSet<Int>, aRemovedList: Collection<Int>) {
                tempListenerCalledFlag = true
                assertTrue { aRemovedList.size == 2 }
                assertTrue { aRemovedList.containsAll(listOf(1, 2)) }
            }
        })
        observableMutableSet.clear()
        assertTrue { tempListenerCalledFlag }
    }

    @Test
    fun testRemove() {
        var tempOnRemoveListenerCalledFlag = false
        observableMutableSet.addListener(object : KMutableSetListener<Int> {
            override fun onRemove(aMutableSet: KObservableMutableSet<Int>, aRemovedList: Collection<Int>) {
                tempOnRemoveListenerCalledFlag = true
                assertTrue { aRemovedList.size == 1 }
                assertTrue { aRemovedList.contains(1) }
            }
        })

        observableMutableSet.remove(1)
        assertTrue { tempOnRemoveListenerCalledFlag }
    }

    @Test
    fun testRemoveByIterator() {
        var tempOnRemoveListenerCalledFlag = false
        observableMutableSet.addListener(object : KMutableSetListener<Int> {
            override fun onRemove(aMutableSet: KObservableMutableSet<Int>, aRemovedList: Collection<Int>) {
                tempOnRemoveListenerCalledFlag = true
                assertTrue { aRemovedList.size == 1 }
                assertTrue { aRemovedList.contains(1) }
            }
        })

        val tempIterator = observableMutableSet.iterator()
        assertEquals(1, tempIterator.next())
        tempIterator.remove()
        assertEquals(2, tempIterator.next())
        assertEquals(1, observableMutableSet.size)
        assertTrue { tempOnRemoveListenerCalledFlag }
    }

    @Test
    fun testRemoveAll() {
        var tempOnRemoveListenerCalledFlag = false
        observableMutableSet.addListener(object : KMutableSetListener<Int> {
            override fun onRemove(aMutableSet: KObservableMutableSet<Int>, aRemovedList: Collection<Int>) {
                tempOnRemoveListenerCalledFlag = true
                assertTrue { aRemovedList.size == 2 }
                assertTrue { aRemovedList.containsAll(listOf(1, 2)) }
            }
        })

        observableMutableSet.removeAll(listOf(1, 2))
        assertTrue { tempOnRemoveListenerCalledFlag }
    }

    @Test
    fun testRetainAll() {
        var tempOnRemoveListenerCalledFlag = false
        observableMutableSet.addListener(object : KMutableSetListener<Int> {
            override fun onRemove(aMutableSet: KObservableMutableSet<Int>, aRemovedList: Collection<Int>) {
                tempOnRemoveListenerCalledFlag = true
                assertTrue { aRemovedList.size == 1 }
                assertTrue { aRemovedList.containsAll(listOf(1)) }
            }
        })

        observableMutableSet.retainAll(listOf(2))
        assertTrue { tempOnRemoveListenerCalledFlag }
    }
}
