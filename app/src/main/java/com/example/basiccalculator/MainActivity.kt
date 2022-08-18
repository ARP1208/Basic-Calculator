package com.example.basiccalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    private var tvInput: TextView? = null

    //the below code is added for onDecimalPoint() functionality.
    // this block of code is written to prevent using more than one decimal point.
    var lastNumeric: Boolean = false
    var lastDotDecimal: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById(R.id.tvInputScreen)
    }

    fun onDigit(view: View){

        //tvInput?.append("1") // This line will show 1 on the layout screen whenever we click any button.
        tvInput?.append((view as Button).text)
        /*
        the below two lines are written after adding the DecimalDot fucntionality else it won't work.
         */
        lastNumeric = true
        lastDotDecimal = false

    }

    fun onClear(view: View){
        tvInput?.text = ""

    }

    fun onDecimalPoint(view: View){
        if(lastNumeric && !lastDotDecimal)
            tvInput?.append(".")
        lastNumeric = false
        lastDotDecimal = true
        // lastNumeric and lastDotDecimal are flags which are used to know if something is active or inactive so to speak.
    }

    fun onOperator(view: View){
        /*
        1. The "it" here that we are using is bold here is this CharSequence which is the value we receive from the
           lambda.
        2. So this here is a lambda that we have which is automatically created for us when we are using let{} and
           "let" is something you use for Nullables as we have them here.
        3. We can have Nullable chaining here.
         */
        tvInput?.text.let {
            if(lastNumeric && !isOperatorAdded(it.toString())){
                tvInput?.append((view as Button).text)

                //we will also make sire that "lastNumeric" and "lastDotDecimal" are set to "false"
                lastNumeric = false
                lastDotDecimal = false
            }
        }
    }

    fun onEqual(view: View){
        if(lastNumeric){
            var tvValue = tvInput?.text.toString()
            var prefix = ""
            // I made a mistake in line 74, the mistake was: var prefix = "-"

            try{

                if (tvValue.startsWith("-")){
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }

                if (tvValue.contains("-")){

                    val splitValue = tvValue.split("-")

                    var one = splitValue[0]
                    var two = splitValue[1]
                    /*var result = one.toDouble() - two.toDouble()
                    tvInput?.text = result.toString() */

                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }

                    tvInput?.text = removeZeroAfterDotDecimal((one.toDouble() - two.toDouble()).toString())
                }
                else if (tvValue.contains("+")){

                    val splitValue = tvValue.split("+")

                    var one = splitValue[0]
                    var two = splitValue[1]
                    /*var result = one.toDouble() + two.toDouble()
                    tvInput?.text = result.toString() */

                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }

                    tvInput?.text = removeZeroAfterDotDecimal((one.toDouble() + two.toDouble()).toString())
                }
                else if (tvValue.contains("/")){

                    val splitValue = tvValue.split("/")

                    var one = splitValue[0]
                    var two = splitValue[1]
                    /*var result = one.toDouble() / two.toDouble()
                    tvInput?.text = result.toString() */

                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }

                    tvInput?.text = removeZeroAfterDotDecimal((one.toDouble() / two.toDouble()).toString())
                }
                else if (tvValue.contains("*")){

                    val splitValue = tvValue.split("*")

                    var one = splitValue[0]
                    var two = splitValue[1]
                    /*var result = one.toDouble() * two.toDouble()
                    tvInput?.text = result.toString() */

                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }

                    tvInput?.text = removeZeroAfterDotDecimal((one.toDouble() * two.toDouble()).toString())
                }
            }
            catch (e: ArithmeticException){
                e.printStackTrace()
            }

        }
    }

    private fun removeZeroAfterDotDecimal(result: String):String{
        var value = result
        if(result.contains(".0"))
            value = result.substring(0, result.length-2)

        return value
    }

    private fun isOperatorAdded(value: String): Boolean{
        return if(value.startsWith("-")){
            false
        }
        else{
            value.contains("/")
                    || value.contains("*")
                    || value.contains("+")
                    || value.contains("-")
        }
    }
}