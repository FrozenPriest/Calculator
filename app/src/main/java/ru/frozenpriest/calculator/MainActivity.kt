package ru.frozenpriest.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var textView:TextView

    private val calculator = Calculator()


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


        textView.append(" = ${calculator.calculate(textView.text.toString())}")


        makeCurrentTextView()
    }



//    private fun isOp(c: Char): Boolean {
//        when (c) {
//            '-', '+', '*', '/', '^', '%' -> return true
//        }
//        return false
//    }




    fun addOperand(view: View) {
        textView.append((view as Button).text)
    }

    private val regex = Regex("^[(]*([+\\-])?\\d+(([+\\-*/%^])[(]*[-]?\\d+[)]*)*\$")
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