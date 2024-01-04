package com.example.mindsharpener

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout

import java.util.*
import kotlin.math.pow

class MainActivity : AppCompatActivity() {

    private lateinit var textView4: TextView
    private lateinit var textView5: TextView
    private lateinit var textView6: TextView
    private lateinit var textView8: TextView
    private lateinit var editTextText: EditText
    private lateinit var button: Button
    private lateinit var radioGroup: RadioGroup
    private var points = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val constraintLayout = ConstraintLayout(this)
        val layoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.MATCH_PARENT
        )
        constraintLayout.layoutParams = layoutParams
        setContentView(constraintLayout)

        // Inflate the XML layout
        layoutInflater.inflate(R.layout.activity_main, constraintLayout, true)

        val toolbar: Toolbar = findViewById(com.google.android.material.R.id.open_search_view_dummy_toolbar)
        setSupportActionBar(toolbar)

        // Initialize views from the inflated layout
        textView4 = findViewById(R.id.textview4)
        textView5 = findViewById(R.id.textview5)
        textView6 = findViewById(R.id.textview6)
        textView8 = findViewById(R.id.textview8)
        editTextText = findViewById(R.id.editText)
        button = findViewById(R.id.button)
        radioGroup = findViewById(R.id.radio_group)

        // Set a listener for the Check button
        button.setOnClickListener { checkAnswer() }

        // Set a listener for the RadioGroup to handle level selection
        radioGroup.setOnCheckedChangeListener { _, checkedId -> updateQuestion() }

        // Initial question update
        updateQuestion()
    }

    // Method to update the displayed question based on the selected level
    private fun updateQuestion() {
        val selectedLevelId = radioGroup.checkedRadioButtonId
        val selectedLevelRadioButton: RadioButton = findViewById(selectedLevelId)

        // Set the maximum digits based on the selected level
        val maxDigits: Int = when (selectedLevelRadioButton.text.toString()) {
            "i3" -> 1
            "i5" -> 2
            "i7" -> 3
            else -> 1
        }

        // Generate random numbers
        val random = Random()
        val firstNumber = random.nextInt(10.0.pow(maxDigits).toInt())
        val secondNumber = random.nextInt(10.0.pow(maxDigits).toInt())

        // Display the numbers in TextViews
        textView4.text = firstNumber.toString()
        textView6.text = secondNumber.toString()

        // Generate a random operator (0 to 3)
        val operator = random.nextInt(4)

        // Update the operator based on the random number
        textView5.text = when (operator) {
            0 -> "+"
            1 -> "-"
            2 -> "*"
            3 -> "/"
            else -> "+"
        }

        // Clear the EditText
        editTextText.text.clear()
    }

    // Method to check the answer and update points
    private fun checkAnswer() {
        // Get the correct answer based on the displayed question
        val firstNumber = textView4.text.toString().toInt()
        val secondNumber = textView6.text.toString().toInt()
        val operator = getOperator(textView5.text.toString())
        val correctAnswer = calculateAnswer(firstNumber, secondNumber, operator)

        // Get the user's answer from the EditText
        val userAnswerString = editTextText.text.toString()

        // Check if the user's answer is correct
        if (userAnswerString.isNotEmpty()) {
            val userAnswer = userAnswerString.toInt()
            if (userAnswer == correctAnswer) {
                // Increment points for correct answer
                points++
            } else {
                // Decrement points for wrong answer
                points--
            }
        }

        // Update the points TextView
        textView8.text = points.toString()

        // Update the question for the next round
        updateQuestion()
    }

    // Helper method to convert operator string to corresponding integer
    private fun getOperator(operatorString: CharSequence): Int {
        return when (operatorString.toString()) {
            "+" -> 0
            "-" -> 1
            "*" -> 2
            "/" -> 3
            else -> 0
        }
    }

    // Helper method to calculate the answer based on the operator
    private fun calculateAnswer(firstNumber: Int, secondNumber: Int, operator: Int): Int {
        return when (operator) {
            0 -> firstNumber + secondNumber
            1 -> firstNumber - secondNumber
            2 -> firstNumber * secondNumber
            3 -> if (secondNumber != 0) firstNumber / secondNumber else 0
            else -> 0
        }
    }
}
