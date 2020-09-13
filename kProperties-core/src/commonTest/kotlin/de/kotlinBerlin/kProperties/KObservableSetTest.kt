package de.kotlinBerlin.kProperties

import de.kotlinBerlin.kProperties.collection.KObservableSet
import de.kotlinBerlin.kProperties.collection.KSetListener
import de.kotlinBerlin.kProperties.collection.observableSet
import kotlin.test.*

class KObservableSetTest {

    private lateinit var observableSet: KObservableSet<Int>

    @BeforeTest
    fun prepare() {
        observableSet = observableSet()
        observableSet.add(1)
        observableSet.add(2)
    }

    @Test
    fun testAdd() {
        var tempListenerCalledFlag = false
        observableSet.addListener(object : KSetListener<Int> {
            override fun onAdd(aSet: KObservableSet<Int>, anAddedList: Collection<Int>) {
                tempListenerCalledFlag = true
                assertTrue { anAddedList.size == 1 }
                assertSame(anAddedList.first(), 3)
            }
        })
        observableSet.add(3)
        assertTrue { tempListenerCalledFlag }
    }

    @Test
    fun testAddAll() {
        var tempListenerCalledFlag = false
        observableSet.addListener(object : KSetListener<Int> {
            override fun onAdd(aSet: KObservableSet<Int>, anAddedList: Collection<Int>) {
                tempListenerCalledFlag = true
                assertTrue { anAddedList.size == 2 }
                assertTrue { anAddedList.containsAll(listOf(3, 4)) }
            }
        })
        observableSet.addAll(listOf(3, 4))
        assertTrue { tempListenerCalledFlag }
    }

    @Test
    fun testClear() {
        var tempListenerCalledFlag = false
        observableSet.addListener(object : KSetListener<Int> {
            override fun onRemove(aSet: KObservableSet<Int>, aRemovedList: Collection<Int>) {
                tempListenerCalledFlag = true
                assertTrue { aRemovedList.size == 2 }
                assertTrue { aRemovedList.containsAll(listOf(1, 2)) }
            }
        })
        observableSet.clear()
        assertTrue { tempListenerCalledFlag }
    }

    @Test
    fun testRemove() {
        var tempOnRemoveListenerCalledFlag = false
        observableSet.addListener(object : KSetListener<Int> {
            override fun onRemove(aSet: KObservableSet<Int>, aRemovedList: Collection<Int>) {
                tempOnRemoveListenerCalledFlag = true
                assertTrue { aRemovedList.size == 1 }
                assertTrue { aRemovedList.contains(1) }
            }
        })

        observableSet.remove(1)
        assertTrue { tempOnRemoveListenerCalledFlag }
    }

    @Test
    fun testRemoveByIterator() {
        var tempOnRemoveListenerCalledFlag = false
        observableSet.addListener(object : KSetListener<Int> {
            override fun onRemove(aSet: KObservableSet<Int>, aRemovedList: Collection<Int>) {
                tempOnRemoveListenerCalledFlag = true
                assertTrue { aRemovedList.size == 1 }
                assertTrue { aRemovedList.contains(1) }
            }
        })

        val tempIterator = observableSet.iterator()
        assertEquals(1, tempIterator.next())
        tempIterator.remove()
        assertEquals(2, tempIterator.next())
        assertEquals(1, observableSet.size)
        assertTrue { tempOnRemoveListenerCalledFlag }
    }

    @Test
    fun testRemoveAll() {
        var tempOnRemoveListenerCalledFlag = false
        observableSet.addListener(object : KSetListener<Int> {
            override fun onRemove(aSet: KObservableSet<Int>, aRemovedList: Collection<Int>) {
                tempOnRemoveListenerCalledFlag = true
                assertTrue { aRemovedList.size == 2 }
                assertTrue { aRemovedList.containsAll(listOf(1, 2)) }
            }
        })

        observableSet.removeAll(listOf(1, 2))
        assertTrue { tempOnRemoveListenerCalledFlag }
    }

    @Test
    fun testRetainAll() {
        var tempOnRemoveListenerCalledFlag = false
        observableSet.addListener(object : KSetListener<Int> {
            override fun onRemove(aSet: KObservableSet<Int>, aRemovedList: Collection<Int>) {
                tempOnRemoveListenerCalledFlag = true
                assertTrue { aRemovedList.size == 1 }
                assertTrue { aRemovedList.containsAll(listOf(1)) }
            }
        })

        observableSet.retainAll(listOf(2))
        assertTrue { tempOnRemoveListenerCalledFlag }
    }
}