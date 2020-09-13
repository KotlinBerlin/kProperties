package de.kotlinBerlin.kProperties

import de.kotlinBerlin.kProperties.collection.KListListener
import de.kotlinBerlin.kProperties.collection.KListListener.Permutation
import de.kotlinBerlin.kProperties.collection.KListListener.Replacement
import de.kotlinBerlin.kProperties.collection.KObservableList
import de.kotlinBerlin.kProperties.collection.observableList
import kotlin.test.*

class KObservableListTest {

    private lateinit var observableList: KObservableList<Int>

    @BeforeTest
    fun prepare() {
        observableList = observableList()
        observableList.add(1)
        observableList.add(2)
    }

    @Test
    fun testAdd() {
        var tempListenerCalledFlag = false
        observableList.addListener(object : KListListener<Int> {
            override fun onAdd(aList: KObservableList<Int>, anAddedList: Collection<Int>) {
                tempListenerCalledFlag = true
                assertTrue { anAddedList.size == 1 }
                assertSame(anAddedList.first(), 3)
            }
        })
        observableList.add(3)
        assertTrue { tempListenerCalledFlag }
    }

    @Test
    fun testIterator() {
        val tempIterator = observableList.iterator()
        tempIterator.next()
        observableList.add(5)
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
        observableList.addListener(object : KListListener<Int> {
            override fun onAdd(aList: KObservableList<Int>, anAddedList: Collection<Int>) {
                tempListenerCalledFlag = true
                assertTrue { anAddedList.size == 1 }
                assertSame(anAddedList.first(), 3)
            }
        })
        val tempIterator = observableList.listIterator()
        tempIterator.add(3)
        tempIterator.next()
        assertTrue { tempListenerCalledFlag }
    }

    @Test
    fun testAddAll() {
        var tempListenerCalledFlag = false
        observableList.addListener(object : KListListener<Int> {
            override fun onAdd(aList: KObservableList<Int>, anAddedList: Collection<Int>) {
                tempListenerCalledFlag = true
                assertTrue { anAddedList.size == 2 }
                assertTrue { anAddedList.containsAll(listOf(3, 4)) }
            }
        })
        observableList.addAll(listOf(3, 4))
        assertTrue { tempListenerCalledFlag }
    }

    @Test
    fun testClear() {
        var tempListenerCalledFlag = false
        observableList.addListener(object : KListListener<Int> {
            override fun onRemove(aList: KObservableList<Int>, aRemovedList: Collection<Int>) {
                tempListenerCalledFlag = true
                assertTrue { aRemovedList.size == 2 }
                assertTrue { aRemovedList.containsAll(listOf(1, 2)) }
            }
        })
        observableList.clear()
        assertTrue { tempListenerCalledFlag }
    }

    @Test
    fun testRemove() {
        var tempOnMoveListenerCalledFlag = false
        var tempOnRemoveListenerCalledFlag = false
        observableList.add(0, 0)
        observableList.addListener(object : KListListener<Int> {
            override fun onRemove(aList: KObservableList<Int>, aRemovedList: Collection<Int>) {
                tempOnRemoveListenerCalledFlag = true
                assertTrue { aRemovedList.size == 1 }
                assertTrue { aRemovedList.contains(0) }
            }

            override fun onMove(aList: KObservableList<Int>, aPermutationList: Collection<Permutation<Int>>) {
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

        observableList.remove(0)
        assertTrue { tempOnRemoveListenerCalledFlag }
        assertTrue { tempOnMoveListenerCalledFlag }
    }

    @Test
    fun testRemoveByIterator() {
        var tempOnMoveListenerCalledFlag = false
        var tempOnRemoveListenerCalledFlag = false
        observableList.add(0, 0)
        observableList.addListener(object : KListListener<Int> {
            override fun onRemove(aList: KObservableList<Int>, aRemovedList: Collection<Int>) {
                tempOnRemoveListenerCalledFlag = true
                assertTrue { aRemovedList.size == 1 }
                assertTrue { aRemovedList.contains(0) }
            }

            override fun onMove(aList: KObservableList<Int>, aPermutationList: Collection<Permutation<Int>>) {
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

        val tempIterator = observableList.iterator()
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
        observableList.add(0, 0)
        observableList.add(0)
        observableList.addListener(object : KListListener<Int> {
            override fun onRemove(aList: KObservableList<Int>, aRemovedList: Collection<Int>) {
                tempOnRemoveListenerCalledFlag = true
                assertTrue { aRemovedList.size == 3 }
                assertTrue { aRemovedList.containsAll(listOf(0, 1)) }
            }

            override fun onMove(aList: KObservableList<Int>, aPermutationList: Collection<Permutation<Int>>) {
                assertEquals(1, aPermutationList.size)
                aPermutationList.forEach {
                    tempOnMoveListenerCalledFlag = true
                    when (it.element) {
                        2 -> assertTrue { it.oldIndex == 2 && it.newIndex == 0 }
                    }
                }
            }
        })

        observableList.removeAll(listOf(0, 1))
        assertFalse { observableList.contains(0) }
        assertTrue { tempOnRemoveListenerCalledFlag }
        assertTrue { tempOnMoveListenerCalledFlag }
    }

    @Test
    fun testRetainAll() {
        var tempOnMoveListenerCalledFlag = false
        var tempOnRemoveListenerCalledFlag = false
        observableList.add(0, 0)
        observableList.add(0)
        observableList.addListener(object : KListListener<Int> {
            override fun onRemove(aList: KObservableList<Int>, aRemovedList: Collection<Int>) {
                tempOnRemoveListenerCalledFlag = true
                assertTrue { aRemovedList.size == 3 }
                assertTrue { aRemovedList.containsAll(listOf(0, 1)) }
            }

            override fun onMove(aList: KObservableList<Int>, aPermutationList: Collection<Permutation<Int>>) {
                assertEquals(1, aPermutationList.size)
                aPermutationList.forEach {
                    tempOnMoveListenerCalledFlag = true
                    when (it.element) {
                        2 -> assertTrue { it.oldIndex == 2 && it.newIndex == 0 }
                    }
                }
            }
        })

        observableList.retainAll(listOf(2))
        assertFalse { observableList.contains(0) }
        assertTrue { tempOnRemoveListenerCalledFlag }
        assertTrue { tempOnMoveListenerCalledFlag }
    }

    @Test
    fun testAddWithIndex() {
        var tempOnMoveListenerCalledFlag = false
        var tempOnAddListenerCalledFlag = false
        observableList.addListener(object : KListListener<Int> {
            override fun onAdd(aList: KObservableList<Int>, anAddedList: Collection<Int>) {
                tempOnAddListenerCalledFlag = true
                assertTrue { anAddedList.size == 1 }
                assertEquals(0, anAddedList.first())
            }

            override fun onMove(aList: KObservableList<Int>, aPermutationList: Collection<Permutation<Int>>) {
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

        observableList.add(0, 0)

        assertTrue { tempOnAddListenerCalledFlag }
        assertTrue { tempOnMoveListenerCalledFlag }
    }

    @Test
    fun testAddAllWithIndex() {
        var tempOnMoveListenerCalledFlag = false
        var tempOnAddListenerCalledFlag = false
        observableList.addListener(object : KListListener<Int> {
            override fun onAdd(aList: KObservableList<Int>, anAddedList: Collection<Int>) {
                tempOnAddListenerCalledFlag = true
                assertTrue { anAddedList.size == 2 }
                assertTrue { anAddedList.containsAll(arrayListOf(-1, 0)) }
            }

            override fun onMove(aList: KObservableList<Int>, aPermutationList: Collection<Permutation<Int>>) {
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

        observableList.addAll(0, arrayListOf(-1, 0))
        assertTrue { tempOnAddListenerCalledFlag }
        assertTrue { tempOnMoveListenerCalledFlag }
    }

    @Test
    fun testRemoveAt() {
        var tempOnMoveListenerCalledFlag = false
        var tempOnRemoveListenerCalledFlag = false
        observableList.add(0, 0)
        observableList.addListener(object : KListListener<Int> {
            override fun onRemove(aList: KObservableList<Int>, aRemovedList: Collection<Int>) {
                tempOnRemoveListenerCalledFlag = true
                assertTrue { aRemovedList.size == 1 }
                assertTrue { aRemovedList.contains(0) }
            }

            override fun onMove(aList: KObservableList<Int>, aPermutationList: Collection<Permutation<Int>>) {
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

        observableList.removeAt(0)
        assertTrue { tempOnRemoveListenerCalledFlag }
        assertTrue { tempOnMoveListenerCalledFlag }
    }

    @Test
    fun testSet() {
        var tempOnReplaceListenerCalledFlag = false
        observableList.addListener(object : KListListener<Int> {
            override fun onReplace(aList: KObservableList<Int>, aReplacementList: Collection<Replacement<Int>>) {
                tempOnReplaceListenerCalledFlag = true
                assertEquals(1, aReplacementList.size)
                val (oldElement, newElement, index) = aReplacementList.first()
                assertEquals(oldElement, 1)
                assertEquals(newElement, 5)
                assertEquals(index, 0)
            }
        })

        observableList[0] = 5

        assertTrue { tempOnReplaceListenerCalledFlag }
    }

    @Test
    fun testSetWithIterator() {
        var tempOnReplaceListenerCalledFlag = false
        observableList.addListener(object : KListListener<Int> {
            override fun onReplace(aList: KObservableList<Int>, aReplacementList: Collection<Replacement<Int>>) {
                tempOnReplaceListenerCalledFlag = true
                assertEquals(1, aReplacementList.size)
                val (oldElement, newElement, index) = aReplacementList.first()
                assertEquals(oldElement, 1)
                assertEquals(newElement, 5)
                assertEquals(index, 0)
            }
        })

        val tempIterator = observableList.listIterator()
        tempIterator.next()
        tempIterator.set(5)
        tempIterator.next()

        assertTrue { tempOnReplaceListenerCalledFlag }
    }
}