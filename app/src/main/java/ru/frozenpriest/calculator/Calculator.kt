package ru.frozenpriest.calculator

import java.util.*
import kotlin.math.pow

class Calculator {

    fun calculate(expression: String) : Double {
        val input = generateRPN(prepareExpression(expression))
        return calculateRPN(input)
    }

    fun prepareExpression(rpn : String) : String {
        var result = ""

        for(i in rpn.indices) {
            if(isOp(rpn[i]) && (i == 0 || rpn[i-1] == '('))
                result +='0'
            result+=rpn[i]
        }
        return result
    }

    fun generateRPN(input: String) : String {
        val stack = Stack<Char>()
        var result = ""

        for (currentChar in input) {
            val priority = getPriority(currentChar)
            when {
                priority == 0 -> {
                    result+=currentChar
                }
                priority == 1 -> {

                    stack.push(currentChar)
                }
                priority > 1 -> {
                    result += ' '
                    while (!stack.empty()) {
                        if(getPriority(stack.peek()) >= priority) {
                            result+=stack.pop()
                        } else {
                            break
                        }
                    }
                    if(result.last() != ' ') result += ' '
                    stack.push(currentChar)
                }
                priority == -1 -> {
                    result += ' '
                    while (getPriority(stack.peek()) != 1) {
                        result+=stack.pop()
                    }
                    stack.pop()
                }
                else -> {
                    throw IllegalArgumentException("Wrong input string")
                }
            }
        }

        while (!stack.empty()) {
            result+=(" " + stack.pop())
        }

        return result
    }

    fun calculateRPN(rpn: String) : Double {
        var dA: Double
        var dB: Double
        var sTmp: String
        val stack: Stack<Double> = Stack()
        val st = StringTokenizer(rpn)
        while (st.hasMoreTokens()) {
            sTmp = st.nextToken().trim()
            if (1 == sTmp.length && isOp(sTmp[0])) {

                dB = stack.pop()
                dA = stack.pop()
                when (sTmp[0]) {
                    '+' -> dA += dB
                    '-' -> dA -= dB
                    '/' -> dA /= dB
                    '*' -> dA *= dB
                    '%' -> dA %= dB
                    '^' -> dA = dA.pow(dB)
                    else -> throw IllegalArgumentException("Wrong operation $sTmp")
                }
                stack.push(dA)
            } else {
                dA = sTmp.toDouble()
                stack.push(dA)
            }

        }
        return stack.pop()
    }

    private fun isOp(c: Char): Boolean {
        when (c) {
            '-', '+', '*', '/', '^', '%' -> return true
        }
        return false
    }

    private fun getPriority(op: Char): Int {
        when (op) {
            '^' -> return 4
            '*', '/', '%' -> return 3
            '+', '-' -> return 2
            '(' -> return 1
            ')' -> return -1
        }
        return 0
    }
}