package ru.frozenpriest.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.math.pow

class MainActivity : AppCompatActivity() {
    private lateinit var textView:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        makeCurrentTextView()
    }

    fun clickDigit(view: View) {
        textView.append((view as Button).text)
    }


    fun calculate(view: View) {
        val valid = isValidInput(textView.text)
        if(!valid) return

        val input = opn(textView.text.toString()) ?: ""

        textView.append(" = ${calculate(input)}")


        makeCurrentTextView()
    }



    private fun opn(sIn: String): String? {
        val sbStack = StringBuilder("")
        val sbOut = StringBuilder("")
        var cIn: Char
        var cTmp: Char
        for (element in sIn) {
            cIn = element
            if (isOp(cIn)) {
                while (sbStack.isNotEmpty()) {
                    cTmp = sbStack.substring(sbStack.length - 1)[0]
                    if (isOp(cTmp) && opPriority(cIn) <= opPriority(cTmp)) {
                        sbOut.append(" ").append(cTmp).append(" ")
                        sbStack.setLength(sbStack.length - 1)
                    } else {
                        sbOut.append(" ")
                        break
                    }
                }
                sbOut.append(" ")
                sbStack.append(cIn)
            } else if ('(' == cIn) {
                sbStack.append(cIn)
            } else if (')' == cIn) {
                cTmp = sbStack.substring(sbStack.length - 1)[0]
                while ('(' != cTmp) {
                    sbOut.append(" ").append(cTmp)
                    sbStack.setLength(sbStack.length - 1)
                    cTmp = sbStack.substring(sbStack.length - 1)[0]
                }
                sbStack.setLength(sbStack.length - 1)
            } else {
                sbOut.append(cIn)
            }
        }

        while (sbStack.isNotEmpty()) {
            sbOut.append(" ").append(sbStack.substring(sbStack.length - 1))
            sbStack.setLength(sbStack.length - 1)
        }
        return sbOut.toString()
    }

    private fun isOp(c: Char): Boolean {
        when (c) {
            '-', '+', '*', '/', '^', '%' -> return true
        }
        return false
    }


    private fun opPriority(op: Char): Byte {
        when (op) {
            '^' -> return 3
            '*', '/', '%' -> return 2
        }
        return 1
    }

    @Throws(Exception::class)
    private fun calculate(sIn: String): Double {
        var dA: Double
        var dB: Double
        var sTmp: String
        val stack: Stack<Double> = Stack()
        val st = StringTokenizer(sIn)
        while (st.hasMoreTokens()) {
            try {
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
                        else -> throw Exception("Wrong operation $sTmp")
                    }
                    stack.push(dA)
                } else {
                    dA = sTmp.toDouble()
                    stack.push(dA)
                }
            } catch (e: Exception) {
                throw Exception("Illegal symbol")
            }
        }
        return stack.pop()
    }

    fun addOperand(view: View) {
        textView.append((view as Button).text)
    }

    private val regex = Regex("^[(]*([+\\-])?\\d+(([+\\-*/%^])[(]*\\d+[)]*)*\$")
    private fun isValidInput(text: CharSequence): Boolean {
        if(regex.matches(text)) {
            var counter = 0
            for (i in text.indices) {
                if (text[i] == '(')
                    counter++
                else if (text[i] == ')')
                    counter--
                if (counter < 0)
                    return false
            }
            return counter == 0
        }
        return false
    }

    fun clear(view: View) {
        textView.text = ""
    }

    private fun makeCurrentTextView() {
        textView = layoutInflater.inflate(R.layout.text_view_layout, history, false) as TextView
        history.addView(textView)
        textView.addTextChangedListener(afterTextChanged = {scrollView.fullScroll(ScrollView.FOCUS_DOWN)})
        scrollView.post { scrollView.fullScroll(ScrollView.FOCUS_DOWN)}
    }


}