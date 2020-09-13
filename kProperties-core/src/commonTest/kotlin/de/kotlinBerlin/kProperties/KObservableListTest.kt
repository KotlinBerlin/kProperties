package de.kotlinBerlin.kProperties

import de.kotlinBerlin.kProperties.collection.*
import kotlin.test.*

class KObservableListTest {

    private lateinit var observableMutableList: KObservableMutableList<Int>

    @BeforeTest
    fun prepare() {
        observableMutableList = observableMutableList()
        observableMutableList.add(1)
        observableMutableList.add(2)
    }

    @Test
    fun testAdd() {
        var tempListenerCalledFlag = false
        observableMutableList.addListener(object : KMutableListListener<Int> {
            override fun onAdd(aMutableList: KObservableMutableList<Int>, anAddedList: Collection<Int>) {
                tempListenerCalledFlag = true
                assertTrue { anAddedList.size == 1 }
                assertSame(anAddedList.first(), 3)
            }
        })
        observableMutableList.add(3)
        assertTrue { tempListenerCalledFlag }
    }

    @Test
    fun testIterator() {
        val tempIterator = observableMutableList.iterator()
        tempIterator.next()
        observableMutableList.add(5)
        try {
            tempIterator.next()
            fail("Expected ConcurrentModificationException")
        } catch (e: ConcurrentModificationException) {
            //Ignore as expected
        }
    }

    @Test
    fun testAddByIterator() {
        var tempListenerCalledFlag = false
        observableMutableList.addListener(object : KMutableListListener<Int> {
            override fun onAdd(aMutableList: KObservableMutableList<Int>, anAddedList: Collection<Int>) {
                tempListenerCalledFlag = true
                assertTrue { anAddedList.size == 1 }
                assertSame(anAddedList.first(), 3)
            }
        })
        val tempIterator = observableMutableList.listIterator()
        tempIterator.add(3)
        tempIterator.next()
        assertTrue { tempListenerCalledFlag }
    }

    @Test
    fun testAddAll() {
        var tempListenerCalledFlag = false
        observableMutableList.addListener(object : KMutableListListener<Int> {
            override fun onAdd(aMutableList: KObservableMutableList<Int>, anAddedList: Collection<Int>) {
                tempListenerCalledFlag = true
                assertTrue { anAddedList.size == 2 }
                assertTrue { anAddedList.containsAll(listOf(3, 4)) }
            }
        })
        observableMutableList.addAll(listOf(3, 4))
        assertTrue { tempListenerCalledFlag }
    }

    @Test
    fun testClear() {
        var tempListenerCalledFlag = false
        observableMutableList.addListener(object : KMutableListListener<Int> {
            override fun onRemove(aMutableList: KObservableMutableList<Int>, aRemovedList: Collection<Int>) {
                tempListenerCalledFlag = true
                assertTrue { aRemovedList.size == 2 }
                assertTrue { aRemovedList.containsAll(listOf(1, 2)) }
            }
        })
        observableMutableList.clear()
        assertTrue { tempListenerCalledFlag }
    }

    @Test
    fun testRemove() {
        var tempOnMoveListenerCalledFlag = false
        var tempOnRemoveListenerCalledFlag = false
        observableMutableList.add(0, 0)
        observableMutableList.addListener(object : KMutableListListener<Int> {
            override fun onRemove(aMutableList: KObservableMutableList<Int>, aRemovedList: Collection<Int>) {
                tempOnRemoveListenerCalledFlag = true
                assertTrue { aRemovedList.size == 1 }
                assertTrue { aRemovedList.contains(0) }
            }

            override fun onMove(aMutableList: KObservableMutableList<Int>, aPermutationList: Collection<ListPermutation<Int>>) {
                assertEquals(2, aPermutationList.size)
                aPermutationList.forEach {
                    tempOnMoveListenerCalledFlag = true
                    when (it.element) {
                        1 -> assertTrue { it.oldIndex == 1 && it.newIndex == 0 }
                        2 -> assertTrue { it.oldIndex == 2 && it.newIndex == 1 }
                    }
                }
            }
        })

        observableMutableList.remove(0)
        assertTrue { tempOnRemoveListenerCalledFlag }
        assertTrue { tempOnMoveListenerCalledFlag }
    }

    @Test
    fun testRemoveByIterator() {
        var tempOnMoveListenerCalledFlag = false
        var tempOnRemoveListenerCalledFlag = false
        observableMutableList.add(0, 0)
        observableMutableList.addListener(object : KMutableListListener<Int> {
            override fun onRemove(aMutableList: KObservableMutableList<Int>, aRemovedList: Collection<Int>) {
                tempOnRemoveListenerCalledFlag = true
                assertTrue { aRemovedList.size == 1 }
                assertTrue { aRemovedList.contains(0) }
            }

            override fun onMove(aMutableList: KObservableMutableList<Int>, aPermutationList: Collection<ListPermutation<Int>>) {
                assertEquals(2, aPermutationList.size)
                aPermutationList.forEach {
                    tempOnMoveListenerCalledFlag = true
                    when (it.element) {
                        1 -> assertTrue { it.oldIndex == 1 && it.newIndex == 0 }
                        2 -> assertTrue { it.oldIndex == 2 && it.newIndex == 1 }
                    }
                }
            }
        })

        val tempIterator = observableMutableList.iterator()
        tempIterator.next()
        tempIterator.remove()
        tempIterator.next()
        assertTrue { tempOnRemoveListenerCalledFlag }
        assertTrue { tempOnMoveListenerCalledFlag }
    }

    @Test
    fun testRemoveAll() {
        var tempOnMoveListenerCalledFlag = false
        var tempOnRemoveListenerCalledFlag = false
        observableMutableList.add(0, 0)
        observableMutableList.add(0)
        observableMutableList.addListener(object : KMutableListListener<Int> {
            override fun onRemove(aMutableList: KObservableMutableList<Int>, aRemovedList: Collection<Int>) {
                tempOnRemoveListenerCalledFlag = true
                assertTrue { aRemovedList.size == 3 }
                assertTrue { aRemovedList.containsAll(listOf(0, 1)) }
            }

            override fun onMove(aMutableList: KObservableMutableList<Int>, aPermutationList: Collection<ListPermutation<Int>>) {
                assertEquals(1, aPermutationList.size)
                aPermutationList.forEach {
                    tempOnMoveListenerCalledFlag = true
                    when (it.element) {
                        2 -> assertTrue { it.oldIndex == 2 && it.newIndex == 0 }
                    }
                }
            }
        })

        observableMutableList.removeAll(listOf(0, 1))
        assertFalse { observableMutableList.contains(0) }
        assertTrue { tempOnRemoveListenerCalledFlag }
        assertTrue { tempOnMoveListenerCalledFlag }
    }

    @Test
    fun testRetainAll() {
        var tempOnMoveListenerCalledFlag = false
        var tempOnRemoveListenerCalledFlag = false
        observableMutableList.add(0, 0)
        observableMutableList.add(0)
        observableMutableList.addListener(object : KMutableListListener<Int> {
            override fun onRemove(aMutableList: KObservableMutableList<Int>, aRemovedList: Collection<Int>) {
                tempOnRemoveListenerCalledFlag = true
                assertTrue { aRemovedList.size == 3 }
                assertTrue { aRemovedList.containsAll(listOf(0, 1)) }
            }

            override fun onMove(aMutableList: KObservableMutableList<Int>, aPermutationList: Collection<ListPermutation<Int>>) {
                assertEquals(1, aPermutationList.size)
                aPermutationList.forEach {
                    tempOnMoveListenerCalledFlag = true
                    when (it.element) {
                        2 -> assertTrue { it.oldIndex == 2 && it.newIndex == 0 }
                    }
                }
            }
        })

        observableMutableList.retainAll(listOf(2))
        assertFalse { observableMutableList.contains(0) }
        assertTrue { tempOnRemoveListenerCalledFlag }
        assertTrue { tempOnMoveListenerCalledFlag }
    }

    @Test
    fun testAddWithIndex() {
        var tempOnMoveListenerCalledFlag = false
        var tempOnAddListenerCalledFlag = false
        observableMutableList.addListener(object : KMutableListListener<Int> {
            override fun onAdd(aMutableList: KObservableMutableList<Int>, anAddedList: Collection<Int>) {
                tempOnAddListenerCalledFlag = true
                assertTrue { anAddedList.size == 1 }
                assertEquals(0, anAddedList.first())
            }

            override fun onMove(aMutableList: KObservableMutableList<Int>, aPermutationList: Collection<ListPermutation<Int>>) {
                tempOnMoveListenerCalledFlag = true
                assertEquals(2, aPermutationList.size)
                aPermutationList.forEach {
                    when (it.element) {
                        1 -> assertTrue { it.oldIndex == 0 && it.newIndex == 1 }
                        2 -> assertTrue { it.oldIndex == 1 && it.newIndex == 2 }
                    }
                }
            }
        })

        observableMutableList.add(0, 0)

        assertTrue { tempOnAddListenerCalledFlag }
        assertTrue { tempOnMoveListenerCalledFlag }
    }

    @Test
    fun testAddAllWithIndex() {
        var tempOnMoveListenerCalledFlag = false
        var tempOnAddListenerCalledFlag = false
        observableMutableList.addListener(object : KMutableListListener<Int> {
            override fun onAdd(aMutableList: KObservableMutableList<Int>, anAddedList: Collection<Int>) {
                tempOnAddListenerCalledFlag = true
                assertTrue { anAddedList.size == 2 }
                assertTrue { anAddedList.containsAll(arrayListOf(-1, 0)) }
            }

            override fun onMove(aMutableList: KObservableMutableList<Int>, aPermutationList: Collection<ListPermutation<Int>>) {
                tempOnMoveListenerCalledFlag = true
                assertEquals(2, aPermutationList.size)
                aPermutationList.forEach {
                    when (it.element) {
                        1 -> assertTrue { it.oldIndex == 0 && it.newIndex == 2 }
                        2 -> assertTrue { it.oldIndex == 1 && it.newIndex == 3 }
                    }
                }
            }
        })

        observableMutableList.addAll(0, arrayListOf(-1, 0))
        assertTrue { tempOnAddListenerCalledFlag }
        assertTrue { tempOnMoveListenerCalledFlag }
    }

    @Test
    fun testRemoveAt() {
        var tempOnMoveListenerCalledFlag = false
        var tempOnRemoveListenerCalledFlag = false
        observableMutableList.add(0, 0)
        observableMutableList.addListener(object : KMutableListListener<Int> {
            override fun onRemove(aMutableList: KObservableMutableList<Int>, aRemovedList: Collection<Int>) {
                tempOnRemoveListenerCalledFlag = true
                assertTrue { aRemovedList.size == 1 }
                assertTrue { aRemovedList.contains(0) }
            }

            override fun onMove(aMutableList: KObservableMutableList<Int>, aPermutationList: Collection<ListPermutation<Int>>) {
                assertEquals(2, aPermutationList.size)
                aPermutationList.forEach {
                    tempOnMoveListenerCalledFlag = true
                    when (it.element) {
                        1 -> assertTrue { it.oldIndex == 1 && it.newIndex == 0 }
                        2 -> assertTrue { it.oldIndex == 2 && it.newIndex == 1 }
                    }
                }
            }
        })

        observableMutableList.removeAt(0)
        assertTrue { tempOnRemoveListenerCalledFlag }
        assertTrue { tempOnMoveListenerCalledFlag }
    }

    @Test
    fun testSet() {
        var tempOnReplaceListenerCalledFlag = false
        observableMutableList.addListener(object : KMutableListListener<Int> {
            override fun onReplace(aMutableList: KObservableMutableList<Int>, aReplacementList: Collection<ListReplacement<Int>>) {
                tempOnReplaceListenerCalledFlag = true
                assertEquals(1, aReplacementList.size)
                val (oldElement, newElement, index) = aReplacementList.first()
                assertEquals(oldElement, 1)
                assertEquals(newElement, 5)
                assertEquals(index, 0)
            }
        })

        observableMutableList[0] = 5

        assertTrue { tempOnReplaceListenerCalledFlag }
    }

    @Test
    fun testSetWithIterator() {
        var tempOnReplaceListenerCalledFlag = false
        observableMutableList.addListener(object : KMutableListListener<Int> {
            override fun onReplace(aMutableList: KObservableMutableList<Int>, aReplacementList: Collection<ListReplacement<Int>>) {
                tempOnReplaceListenerCalledFlag = true
                assertEquals(1, aReplacementList.size)
                val (oldElement, newElement, index) = aReplacementList.first()
                assertEquals(oldElement, 1)
                assertEquals(newElement, 5)
                assertEquals(index, 0)
            }
        })

        val tempIterator = observableMutableList.listIterator()
        tempIterator.next()
        tempIterator.set(5)
        tempIterator.next()

        assertTrue { tempOnReplaceListenerCalledFlag }
    }
}
