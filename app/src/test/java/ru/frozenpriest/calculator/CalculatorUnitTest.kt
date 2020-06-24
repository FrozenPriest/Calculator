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
    fun prepare_isCorrect() {
        assertEquals("0-2", Calculator().prepareExpression("-2"))
        assertEquals("0+2", Calculator().prepareExpression("+2"))
        assertEquals("0-2+(0-5)", Calculator().prepareExpression("-2+(-5)"))
        assertEquals("0-2+(0-5+7)", Calculator().prepareExpression("-2+(-5+7)"))
        assertEquals("0-2-(0-7)", Calculator().prepareExpression("-2-(-7)"))
        assertEquals("0-(0-2-2)", Calculator().prepareExpression("-(-2-2)"))

    }

    @Test
    fun generateRPN_isCorrect() {
        assertEquals("0 0 2 - 2 - -", Calculator().generateRPN("0-(0-2-2)"))
        assertEquals("2 2 2 * +", Calculator().generateRPN("2+2*2"))

    }

    @Test
    fun calculateRPN_isCorrect() {
        assertEquals(6.0, Calculator().calculateRPN("2 2 2 * +"), 0.0000001)
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4.0, Calculator().calculate("-(-2-2)"), 0.0000001)
        assertEquals(4.0, Calculator().calculate("2+2"), 0.0000001)
        assertEquals(5.0, Calculator().calculate("-2-(-7)"), 0.0000001)

    }
}