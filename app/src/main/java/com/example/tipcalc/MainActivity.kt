package com.example.tipcalc

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //initializing the views (?)
        val billText = findViewById<EditText>(R.id.etBillAmount)
        val tipPercentText = findViewById<EditText>(R.id.etTipPercent)
        val calcButton = findViewById<Button>(R.id.btnCalculate)
        val clearButton = findViewById<Button>(R.id.btnClear)

        clearButton.visibility = View.INVISIBLE

        calcButton.setOnClickListener(){
            val bill = billText.text.toString()
            val tipPercent = tipPercentText.text.toString()

            if(validateInput(bill, tipPercent)){

                //calculate tip amount
                val tipAmount = bill.toFloat() * (tipPercent.toFloat() / 100)
                //sets it to 2 digits decimal
                val tipAmount2Digits = String.format("%.2f", tipAmount).toFloat()

                hideKeyboard(tipPercentText)
                displayResult(tipAmount2Digits, tipPercent, bill)
                clearButton.visibility = View.VISIBLE
            }
        }

        clearButton.setOnClickListener(){
            clearText()
            clearButton.visibility = View.INVISIBLE
        }
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun validateInput(bill:String, tip:String):Boolean{
        return when{
            bill.isNullOrEmpty() -> {
                Toast.makeText(this, "Bill is empty", Toast.LENGTH_LONG).show()
                return false
            }
            tip.isNullOrEmpty() -> {
                Toast.makeText(this, "Tip is empty", Toast.LENGTH_LONG).show()
                return false
            }
            else -> {true}
        }
    }

    private fun displayResult(tipAmount:Float, percent:String, bill:String){
        val resultMessage = findViewById<TextView>(R.id.tvResultMessage)

        val tipResult = findViewById<TextView>(R.id.tvTipResult)

        tipResult.text = '$' + tipAmount.toString()
        resultMessage.text= "${percent}% tip on \$${bill} is"
    }

    private fun clearText(){
        val billText = findViewById<EditText>(R.id.etBillAmount)
        val tipPercentText = findViewById<EditText>(R.id.etTipPercent)
        val resultMessage = findViewById<TextView>(R.id.tvResultMessage)
        val tipResult = findViewById<TextView>(R.id.tvTipResult)

        billText.setText("")
        tipPercentText.setText("")
        tipResult.text = ""
        resultMessage.text= ""
    }
}