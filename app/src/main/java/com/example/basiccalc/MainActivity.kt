package com.example.basiccalc

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val num1: EditText = findViewById(R.id.Num1)
        val num2: EditText = findViewById(R.id.Num2)
        val dropdown: AutoCompleteTextView = findViewById(R.id.drpdwn)
        val button: Button = findViewById(R.id.btn)
        val tvResult: TextView = findViewById(R.id.tvresult)
        val resultText: TextView = findViewById(R.id.result)

        val operations = arrayOf( "Addition", "Subtraction", "Multiplication", "Division", "Modulus" )

        val adapter =
            ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, operations)
        dropdown.setAdapter(adapter)

        button.setOnClickListener {
            val input1 = num1.text.toString()
            val input2 = num2.text.toString()
            val operation = dropdown.text.toString()

            if (input1.isEmpty() || input2.isEmpty() || operation.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val n1 = input1.toDoubleOrNull()
            val n2 = input2.toDoubleOrNull()

            if (n1 == null || n2 == null) {
                Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            var result: Double

            try {
                result = when (operation) {
                    "Addition" -> n1 + n2
                    "Subtraction" -> n1 - n2
                    "Multiplication" -> n1 * n2
                    "Division" -> {
                        if (n2 == 0.0) {
                            Toast.makeText(this, "Cannot divide by zero", Toast.LENGTH_SHORT).show()
                            return@setOnClickListener
                        }
                        n1 / n2
                    }
                    "Modulus" -> n1 % n2
                    else -> 0.0
                }

                tvResult.visibility = View.VISIBLE
                resultText.visibility = View.VISIBLE

                val roundedResult = (result * 1000).roundToInt() / 1000.0
                resultText.text = "Your result is $roundedResult"

            } catch (e: Exception) {
                Toast.makeText(this, "Error occurred", Toast.LENGTH_SHORT).show()
            } finally {

                num1.text.clear()
                num2.text.clear()
                dropdown.text.clear()
            }
        }
    }
}
