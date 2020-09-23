package de.kotlinBerlin.kProperties

import de.kotlinBerlin.kProperties.binding.bindContent
import de.kotlinBerlin.kProperties.binding.bindContentBidirectional
import de.kotlinBerlin.kProperties.collection.observableMutableList
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ListBindingTest {

    @Test
    fun testContentBinding() {
        val t = mutableListOf<String>()
        val t1 = observableMutableList<Int>()

        bindContent(t, t1, Int::toString)

        t1.add(1)

        assertTrue { t.contains("1") }

        t1.add(2)

        assertTrue { t.contains("2") }

        t1.remove(1)

        assertFalse { t.contains("1") }
        assertTrue { t.first() == "2" }

        t1[0] = 5

        assertTrue { t.first() == "5" }
        assertFalse { t.contains("2") }
    }

    @Test
    fun testBidirectionalBinding() {
        println("TEST")
        val t = observableMutableList<String>()
        val t1 = observableMutableList<Long>()

        bindContentBidirectional(t, t1, Long::toString, String::toLong)

        println(t)
        println(t1)

        t.add("1")

        assertTrue { t1.contains(1) }

        t1.add(2)

        assertTrue { t.contains("2") }

        t.remove("2")


        assertFalse { t1.contains(2) }

        t1.remove(1)

        assertFalse { t.contains("1") }

        t.add("1")

        assertTrue { t1.first() == 1L }

        t.add(0, "2")

        assertTrue { t1.first() == 2L }

        t[0] = "3"

        assertTrue { t1.first() == 3L }
        println("TEST")
    }
}
