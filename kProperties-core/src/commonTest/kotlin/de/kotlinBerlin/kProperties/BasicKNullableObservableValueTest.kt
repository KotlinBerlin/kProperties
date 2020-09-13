package de.kotlinBerlin.kProperties

import de.kotlinBerlin.kProperties.binding.isEqual
import de.kotlinBerlin.kProperties.binding.not
import de.kotlinBerlin.kProperties.property.BasicKStringProperty
import de.kotlinBerlin.kProperties.value.KObservableObjectValue
import kotlin.test.*

class BasicKNullableObservableValueTest {

    @Test
    fun testBidirectionalBinding() {
        val tempProperty = BasicKStringProperty(value = "Test")
        val tempProperty1 = BasicKStringProperty(value = "")

        tempProperty.bindBidirectional(tempProperty1)

        assertTrue { tempProperty.value == tempProperty1.value }

        tempProperty.value = "Hallo"

        assertTrue { tempProperty.value == tempProperty1.value }

        tempProperty1.value = "Hallo1"

        assertTrue { tempProperty.value == tempProperty1.value }

        tempProperty.unbindBidirectional(tempProperty1)

        tempProperty.value = "Hey"

        assertFalse { tempProperty.value == tempProperty1.value }
    }

    @Test
    fun testBoundProperties() {
        val tempProperty = BasicKStringProperty(value = "Test")
        val tempProperty1 = BasicKStringProperty(value = "")

        assertTrue { tempProperty.valid }
        assertTrue { tempProperty1.valid }

        tempProperty1.bind(tempProperty)

        assertTrue { tempProperty.valid }
        assertFalse { tempProperty1.valid }

        assertEquals(tempProperty.value, tempProperty1.value)

        tempProperty.value = "Test1"

        assertFalse { tempProperty.valid }
        assertFalse { tempProperty1.valid }

        assertEquals(tempProperty.value, tempProperty1.value)

        assertTrue { tempProperty.valid }
        assertTrue { tempProperty1.valid }

        tempProperty1.unbind()

        assertTrue { tempProperty.valid }
        assertTrue { tempProperty1.valid }

        assertEquals(tempProperty.value, tempProperty1.value)

        assertTrue { tempProperty.valid }
        assertTrue { tempProperty1.valid }

        tempProperty.value = "Test"

        assertFalse { tempProperty.valid }
        assertTrue { tempProperty1.valid }

        assertNotEquals(tempProperty.value, tempProperty1.value)

        assertTrue { tempProperty.valid }
        assertTrue { tempProperty1.valid }

        tempProperty1.bind(tempProperty)

        assertEquals(tempProperty.value, tempProperty1.value)

        assertTrue { tempProperty.valid }
        assertTrue { tempProperty1.valid }

        tempProperty.value = "Test2"

        tempProperty1.unbind()

        assertTrue { tempProperty.valid }
        assertFalse { tempProperty1.valid }
    }

    @Test
    fun testEqualsBinding() {
        val tempA1 = A(1)
        val tempA2 = A(2)

        val tempBinding = tempA1.isEqual(tempA2)
        val tempNotBinding = !tempBinding

        assertFalse(tempBinding.value, "Should be false here")
        assertTrue(tempNotBinding.value, "Should be true here")

        tempA1.test2()

        assertTrue(tempBinding.value, "Should be true here")
        assertFalse(tempNotBinding.value, "Should be false here")
    }

    @Test
    fun testAddSingleInvalidationListener() {
        var t = false
        val a = A(1)
        a.addListener { _ -> t = true }
        a.test()
        if (!t) {
            fail("listener was not called!")
        }
    }

    @Test
    fun testAddSingleChangeListenerWithoutChange() {
        var t = false
        val a = A(1)
        a.addListener { _, _, _ -> t = true }
        a.test()
        if (t) {
            fail("listener was called without value change!")
        }
    }

    @Test
    fun testAddSingleChangeListenerWithChange() {
        var t = false
        val a = A(1)
        a.addListener { _, _, _ -> t = true }
        a.test2()
        if (!t) {
            fail("listener was not called!")
        }
    }

    private class A(override var value: Int) : KObservableObjectValue<Int>() {
        fun test() {
            fireValueChangedEvent()
        }

        fun test2() {
            value = 2
            fireValueChangedEvent()
        }
    }
}