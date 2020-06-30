package de.kotlinBerlin.kProperties

import de.kotlinBerlin.kProperties.binding.chooseBinding
import de.kotlinBerlin.kProperties.binding.ifBinding
import de.kotlinBerlin.kProperties.property.BasicKBooleanProperty
import de.kotlinBerlin.kProperties.property.BasicKIntProperty
import de.kotlinBerlin.kProperties.property.BasicKStringProperty
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ConditionBindingsTest {
    @Test
    fun testIfBinding() {
        val tempCondition = BasicKBooleanProperty(value = true)
        val tempThenValue = BasicKStringProperty<String?>(value = "Julian")
        val tempElseValue = BasicKStringProperty<String?>(value = null)
        val tempIf = ifBinding<String?>(tempCondition) {
            then { tempThenValue }
            otherwise { tempElseValue }
        }
        assertFalse { tempIf.valid }

        assertEquals("Julian", tempIf.value)
        assertTrue { tempIf.valid }

        tempCondition.value = false
        assertFalse { tempIf.valid }
        assertEquals(null, tempIf.value)
        assertTrue { tempIf.valid }

        tempCondition.value = true
        assertFalse { tempIf.valid }
        assertEquals("Julian", tempIf.value)
        assertTrue { tempIf.valid }

        tempThenValue.value = "Julian1"
        assertFalse { tempIf.valid }
        assertEquals("Julian1", tempIf.value)
        assertTrue { tempIf.valid }

        tempElseValue.value = "Anette1"
        assertTrue { tempIf.valid }
        assertEquals("Julian1", tempIf.value)
        assertTrue { tempIf.valid }

        tempCondition.value = false
        assertFalse { tempIf.valid }
        assertEquals("Anette1", tempIf.value)
        assertTrue { tempIf.valid }

        tempElseValue.value = "Anette"
        assertFalse { tempIf.valid }
        assertEquals("Anette", tempIf.value)
        assertTrue { tempIf.valid }

        tempThenValue.value = "Julian"
        assertTrue { tempIf.valid }
        assertEquals("Anette", tempIf.value)
        assertTrue { tempIf.valid }
    }

    @Test
    fun testIfThenElseBinding() {
        val tempCondition = BasicKBooleanProperty(value = true)
        val tempElseIfCondition = BasicKBooleanProperty(value = true)

        val tempThenValue = BasicKStringProperty(value = "Julian")
        val tempElseThenValue = BasicKStringProperty(value = "Anette")
        val tempElseElseValue = BasicKStringProperty(value = "Marcus")

        val tempIf = ifBinding<String>(tempCondition) {
            then { tempThenValue }
            elseIf(tempElseIfCondition) {
                then { tempElseThenValue }
                otherwise { tempElseElseValue }
            }
        }

        assertEquals("Julian", tempIf.value)
        tempCondition.value = false
        assertEquals("Anette", tempIf.value)
        tempElseIfCondition.value = false
        assertEquals("Marcus", tempIf.value)

        tempCondition.value = true
        assertEquals("Julian", tempIf.value)
        assertTrue { tempIf.valid }

        tempElseThenValue.value = "Anette1"
        assertTrue { tempIf.valid }
        assertEquals("Julian", tempIf.value)

        tempElseElseValue.value = "Marcus1"
        assertTrue { tempIf.valid }
        assertEquals("Julian", tempIf.value)

        tempElseIfCondition.value = true
        assertTrue { tempIf.valid }
        assertEquals("Julian", tempIf.value)

        tempCondition.value = false
        assertFalse { tempIf.valid }
        assertEquals("Anette1", tempIf.value)
        assertTrue { tempIf.valid }

        tempThenValue.value = "Julian1"
        assertTrue { tempIf.valid }
        assertEquals("Anette1", tempIf.value)
    }

    @Test
    fun testWhenBinding() {
        val tempWhenObject = BasicKIntProperty(value = 1)
        val tempResult1 = BasicKStringProperty(value = "1")
        val tempResult2 = BasicKStringProperty(value = "2")
        val tempResult3 = BasicKStringProperty(value = "3")
        val tempResultOlder = BasicKStringProperty(value = "Älter!")

        val tempWhen = chooseBinding<Int, String>(tempWhenObject) {
            case { this == 1 } then { tempResult1 }
            case { this == 2 } then { tempResult2 }
            condition({ this == 3 }, tempResult3)
            otherwise { tempResultOlder }
        }

        assertFalse { tempWhen.valid }
        assertEquals("1", tempWhen.value)
        assertTrue { tempWhen.valid }

        tempWhenObject.value = 2
        assertFalse { tempWhen.valid }
        assertEquals("2", tempWhen.value)
        assertTrue { tempWhen.valid }

        tempWhenObject.value = 3
        assertFalse { tempWhen.valid }
        assertEquals("3", tempWhen.value)
        assertTrue { tempWhen.valid }

        tempWhenObject.value = 4
        assertFalse { tempWhen.valid }
        assertEquals("Älter!", tempWhen.value)
        assertTrue { tempWhen.valid }

        tempResultOlder.value = "richtig alt!"
        assertFalse { tempWhen.valid }
        assertEquals("richtig alt!", tempWhen.value)
        assertTrue { tempWhen.valid }

        tempResult1.value = "1!"
        assertTrue { tempWhen.valid }
        assertEquals("richtig alt!", tempWhen.value)
        assertTrue { tempWhen.valid }

        tempWhenObject.value = 1
        assertFalse { tempWhen.valid }
        assertEquals("1!", tempWhen.value)
        assertTrue { tempWhen.valid }

        tempResultOlder.value = "Älter!"
        assertTrue { tempWhen.valid }
        assertEquals("1!", tempWhen.value)
        assertTrue { tempWhen.valid }

        tempResult2.value = "2!"
        assertTrue { tempWhen.valid }
        assertEquals("1!", tempWhen.value)
        assertTrue { tempWhen.valid }
    }
}