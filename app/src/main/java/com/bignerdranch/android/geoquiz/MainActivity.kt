package com.bignerdranch.android.geoquiz

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var questionTextView: TextView

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
//        Question(R.string.question_oceans, true),
//        Question(R.string.question_mideast, false),
//        Question(R.string.question_africa, false),
//        Question(R.string.question_americas, true),
//        Question(R.string.question_asia, true),
    )

    private val answers = HashMap<Question, Boolean>()

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() called")
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        questionTextView = findViewById(R.id.question_text_view)

        trueButton.setOnClickListener {
            checkAnswer(true)
        }

        falseButton.setOnClickListener {
            checkAnswer(false)
        }

        nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }

        updateQuestion()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy()")
    }

    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
        updateUI()
    }

    private fun updateUI() {
        val question = questionBank[currentIndex]
        val buttonsEnabled = !answers.containsKey(question)

        // Toggle button enabled / disabled for previously answered questions
        trueButton.isEnabled = buttonsEnabled
        falseButton.isEnabled = buttonsEnabled

        // Display a toast if all questions have been answered
        if (answers.size == questionBank.size) {
            val correctAnswerCount = answers.count { (_, isCorrect) -> isCorrect }
            val score = ((correctAnswerCount.toFloat() / answers.size) * 100).toInt()
            val text = resources.getString(R.string.quiz_score, score)
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val question = questionBank[currentIndex]
        val correctAnswer = question.answer
        val isCorrect = userAnswer == correctAnswer

        val messageResId = if (isCorrect) R.string.correct_toast else R.string.incorrect_toast
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()

        answers[question] = isCorrect

        updateUI()
    }
}