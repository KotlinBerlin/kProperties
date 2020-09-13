package de.kotlinBerlin.kProperties.jfx

import de.kotlinBerlin.kProperties.property.BasicKStringProperty
import javafx.beans.property.SimpleStringProperty
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class JfxBindingTests {

    @Test
    fun testSimpleWrapping() {
        val tempNullableJfxProp = SimpleStringProperty()
        val tempNullableKProp = tempNullableJfxProp.toKProperty()

        assertNull(tempNullableKProp.value)
        tempNullableJfxProp.value = "Test"
        assertEquals("Test", tempNullableKProp.value)
        tempNullableKProp.value = "Test2"
        assertEquals("Test2", tempNullableJfxProp.value)

        val tempJfxProp = SimpleStringProperty("Initial")
        val tempKProp = tempJfxProp.toSaveKProperty()

        assertEquals("Initial", tempKProp.value)
        tempJfxProp.value = "Test"
        assertEquals("Test", tempKProp.value)
        tempKProp.value = "Test2"
        assertEquals("Test2", tempJfxProp.value)
        tempJfxProp.value = null
        assertEquals("", tempJfxProp.value)
        assertEquals("", tempKProp.value)
    }

    @Test
    fun testBinding() {
        val tempNullableJfxProp = SimpleStringProperty("Hallo")
        val tempNullableKProp = tempNullableJfxProp.toKProperty()

        val tempBindableProp = BasicKStringProperty<String?>(value = null)

        tempNullableKProp.bind(tempBindableProp)

        assertNull(tempNullableJfxProp.value)
        assertNull(tempNullableKProp.value)

        tempBindableProp.value = "Test"

        assertEquals("Test", tempNullableJfxProp.value)
        assertEquals("Test", tempNullableKProp.value)

        tempNullableKProp.unbind()

        tempBindableProp.value = "Test2"

        assertEquals("Test", tempNullableJfxProp.value)
        assertEquals("Test", tempNullableKProp.value)
    }

    @Test
    fun testBidirectionalBinding() {
        val tempNullableJfxProp = SimpleStringProperty("Hallo")
        val tempNullableKProp = tempNullableJfxProp.toKProperty()

        val tempBindableProp = BasicKStringProperty<String?>(value = null)

        tempNullableKProp.bindBidirectional(tempBindableProp)

        assertNull(tempNullableJfxProp.value)
        assertNull(tempNullableKProp.value)

        tempBindableProp.value = "Test"

        assertEquals("Test", tempNullableJfxProp.value)
        assertEquals("Test", tempNullableKProp.value)

        tempNullableJfxProp.value = "JFX"

        assertEquals("JFX", tempBindableProp.value)
        assertEquals("JFX", tempNullableKProp.value)

        tempNullableKProp.value = "Test"

        assertEquals("Test", tempBindableProp.value)
        assertEquals("Test", tempNullableJfxProp.value)

        tempNullableKProp.unbindBidirectional(tempBindableProp)

        tempBindableProp.value = "Test2"

        assertEquals("Test", tempNullableJfxProp.value)
        assertEquals("Test", tempNullableKProp.value)
    }

    @Test
    fun testBoundJfxPropNotNull() {
        val tempJfxProperty = SimpleStringProperty()
        val tempSaveKProp = tempJfxProperty.toSaveKProperty()

        val tempJfxBindProp = SimpleStringProperty("Hallo")

        tempJfxProperty.bind(tempJfxBindProp)

        tempJfxBindProp.value = null
    }
}
