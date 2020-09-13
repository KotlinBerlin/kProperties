@file:Suppress("DuplicatedCode")

package de.kotlinBerlin.kProperties.util

//PLUS

internal fun plus(n1: Number, n2: Number): Number {
    return when (n1) {
        is Double -> plus(n1, n2)
        is Float -> plus(n1, n2)
        is Long -> plus(n1, n2)
        is Int -> plus(n1, n2)
        is Short -> plus(n1, n2)
        is Byte -> plus(n1, n2)
        else -> plus(n1.toDouble(), n2)
    }
}

internal fun plus(n1: Double, n2: Number): Double {
    return when (n2) {
        is Double -> n1 + n2
        is Float -> n1 + n2
        is Long -> n1 + n2
        is Int -> n1 + n2
        is Short -> n1 + n2
        is Byte -> n1 + n2
        else -> n1 + n2.toDouble()
    }
}

internal fun plus(n1: Float, n2: Number): Number {
    return when (n2) {
        is Double -> n1 + n2
        is Float -> n1 + n2
        is Long -> n1 + n2
        is Int -> n1 + n2
        is Short -> n1 + n2
        is Byte -> n1 + n2
        else -> n1 + n2.toDouble()
    }
}

internal fun plus(n1: Long, n2: Number): Number {
    return when (n2) {
        is Double -> n1 + n2
        is Float -> n1 + n2
        is Long -> n1 + n2
        is Int -> n1 + n2
        is Short -> n1 + n2
        is Byte -> n1 + n2
        else -> n1 + n2.toDouble()
    }
}

internal fun plus(n1: Int, n2: Number): Number {
    return when (n2) {
        is Double -> n1 + n2
        is Float -> n1 + n2
        is Long -> n1 + n2
        is Int -> n1 + n2
        is Short -> n1 + n2
        is Byte -> n1 + n2
        else -> n1 + n2.toDouble()
    }
}

internal fun plus(n1: Short, n2: Number): Number {
    return when (n2) {
        is Double -> n1 + n2
        is Float -> n1 + n2
        is Long -> n1 + n2
        is Int -> n1 + n2
        is Short -> n1 + n2
        is Byte -> n1 + n2
        else -> n1 + n2.toDouble()
    }
}

internal fun plus(n1: Byte, n2: Number): Number {
    return when (n2) {
        is Double -> n1 + n2
        is Float -> n1 + n2
        is Long -> n1 + n2
        is Int -> n1 + n2
        is Short -> n1 + n2
        is Byte -> n1 + n2
        else -> n1 + n2.toDouble()
    }
}

// MINUS

internal fun minus(n1: Number, n2: Number): Number {
    return when (n1) {
        is Double -> minus(n1, n2)
        is Float -> minus(n1, n2)
        is Long -> minus(n1, n2)
        is Int -> minus(n1, n2)
        is Short -> minus(n1, n2)
        is Byte -> minus(n1, n2)
        else -> minus(n1.toDouble(), n2)
    }
}

internal fun minus(n1: Double, n2: Number): Double {
    return when (n2) {
        is Double -> n1 - n2
        is Float -> n1 - n2
        is Long -> n1 - n2
        is Int -> n1 - n2
        is Short -> n1 - n2
        is Byte -> n1 - n2
        else -> n1 - n2.toDouble()
    }
}

internal fun minus(n1: Float, n2: Number): Number {
    return when (n2) {
        is Double -> n1 - n2
        is Float -> n1 - n2
        is Long -> n1 - n2
        is Int -> n1 - n2
        is Short -> n1 - n2
        is Byte -> n1 - n2
        else -> n1 - n2.toDouble()
    }
}

internal fun minus(n1: Long, n2: Number): Number {
    return when (n2) {
        is Double -> n1 - n2
        is Float -> n1 - n2
        is Long -> n1 - n2
        is Int -> n1 - n2
        is Short -> n1 - n2
        is Byte -> n1 - n2
        else -> n1 - n2.toDouble()
    }
}

internal fun minus(n1: Int, n2: Number): Number {
    return when (n2) {
        is Double -> n1 - n2
        is Float -> n1 - n2
        is Long -> n1 - n2
        is Int -> n1 - n2
        is Short -> n1 - n2
        is Byte -> n1 - n2
        else -> n1 - n2.toDouble()
    }
}

internal fun minus(n1: Short, n2: Number): Number {
    return when (n2) {
        is Double -> n1 - n2
        is Float -> n1 - n2
        is Long -> n1 - n2
        is Int -> n1 - n2
        is Short -> n1 - n2
        is Byte -> n1 - n2
        else -> n1 - n2.toDouble()
    }
}

internal fun minus(n1: Byte, n2: Number): Number {
    return when (n2) {
        is Double -> n1 - n2
        is Float -> n1 - n2
        is Long -> n1 - n2
        is Int -> n1 - n2
        is Short -> n1 - n2
        is Byte -> n1 - n2
        else -> n1 - n2.toDouble()
    }
}

// TIMES

internal fun times(n1: Number, n2: Number): Number {
    return when (n1) {
        is Double -> times(n1, n2)
        is Float -> times(n1, n2)
        is Long -> times(n1, n2)
        is Int -> times(n1, n2)
        is Short -> times(n1, n2)
        is Byte -> times(n1, n2)
        else -> times(n1.toDouble(), n2)
    }
}

internal fun times(n1: Double, n2: Number): Double {
    return when (n2) {
        is Double -> n1 * n2
        is Float -> n1 * n2
        is Long -> n1 * n2
        is Int -> n1 * n2
        is Short -> n1 * n2
        is Byte -> n1 * n2
        else -> n1 * n2.toDouble()
    }
}

internal fun times(n1: Float, n2: Number): Number {
    return when (n2) {
        is Double -> n1 * n2
        is Float -> n1 * n2
        is Long -> n1 * n2
        is Int -> n1 * n2
        is Short -> n1 * n2
        is Byte -> n1 * n2
        else -> n1 * n2.toDouble()
    }
}

internal fun times(n1: Long, n2: Number): Number {
    return when (n2) {
        is Double -> n1 * n2
        is Float -> n1 * n2
        is Long -> n1 * n2
        is Int -> n1 * n2
        is Short -> n1 * n2
        is Byte -> n1 * n2
        else -> n1 * n2.toDouble()
    }
}

internal fun times(n1: Int, n2: Number): Number {
    return when (n2) {
        is Double -> n1 * n2
        is Float -> n1 * n2
        is Long -> n1 * n2
        is Int -> n1 * n2
        is Short -> n1 * n2
        is Byte -> n1 * n2
        else -> n1 * n2.toDouble()
    }
}

internal fun times(n1: Short, n2: Number): Number {
    return when (n2) {
        is Double -> n1 * n2
        is Float -> n1 * n2
        is Long -> n1 * n2
        is Int -> n1 * n2
        is Short -> n1 * n2
        is Byte -> n1 * n2
        else -> n1 * n2.toDouble()
    }
}

internal fun times(n1: Byte, n2: Number): Number {
    return when (n2) {
        is Double -> n1 * n2
        is Float -> n1 * n2
        is Long -> n1 * n2
        is Int -> n1 * n2
        is Short -> n1 * n2
        is Byte -> n1 * n2
        else -> n1 * n2.toDouble()
    }
}

// divide

internal fun divide(n1: Number, n2: Number): Number {
    return when (n1) {
        is Double -> divide(n1, n2)
        is Float -> divide(n1, n2)
        is Long -> divide(n1, n2)
        is Int -> divide(n1, n2)
        is Short -> divide(n1, n2)
        is Byte -> divide(n1, n2)
        else -> divide(n1.toDouble(), n2)
    }
}

internal fun divide(n1: Double, n2: Number): Double {
    return when (n2) {
        is Double -> n1 / n2
        is Float -> n1 / n2
        is Long -> n1 / n2
        is Int -> n1 / n2
        is Short -> n1 / n2
        is Byte -> n1 / n2
        else -> n1 / n2.toDouble()
    }
}

internal fun divide(n1: Float, n2: Number): Number {
    return when (n2) {
        is Double -> n1 / n2
        is Float -> n1 / n2
        is Long -> n1 / n2
        is Int -> n1 / n2
        is Short -> n1 / n2
        is Byte -> n1 / n2
        else -> n1 / n2.toDouble()
    }
}

internal fun divide(n1: Long, n2: Number): Number {
    return when (n2) {
        is Double -> n1 / n2
        is Float -> n1 / n2
        is Long -> n1 / n2
        is Int -> n1 / n2
        is Short -> n1 / n2
        is Byte -> n1 / n2
        else -> n1 / n2.toDouble()
    }
}

internal fun divide(n1: Int, n2: Number): Number {
    return when (n2) {
        is Double -> n1 / n2
        is Float -> n1 / n2
        is Long -> n1 / n2
        is Int -> n1 / n2
        is Short -> n1 / n2
        is Byte -> n1 / n2
        else -> n1 / n2.toDouble()
    }
}

internal fun divide(n1: Short, n2: Number): Number {
    return when (n2) {
        is Double -> n1 / n2
        is Float -> n1 / n2
        is Long -> n1 / n2
        is Int -> n1 / n2
        is Short -> n1 / n2
        is Byte -> n1 / n2
        else -> n1 / n2.toDouble()
    }
}

internal fun divide(n1: Byte, n2: Number): Number {
    return when (n2) {
        is Double -> n1 / n2
        is Float -> n1 / n2
        is Long -> n1 / n2
        is Int -> n1 / n2
        is Short -> n1 / n2
        is Byte -> n1 / n2
        else -> n1 / n2.toDouble()
    }
}