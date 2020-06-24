package ru.frozenpriest.calculator

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class CalculatorUnitTest {
    @Test
    fun rpn_isCorrect() {
        assertEquals("2 2 2 * +", Calculator().generateRPN("2+2*2"))
    }

    @Test
    fun calculateRPN_isCorrect() {
        assertEquals(6.0, Calculator().calculateRPN("2 2 2 * +"), 0.0000001)
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4.0, Calculator().calculate("2+2"), 0.0000001)
    }
}