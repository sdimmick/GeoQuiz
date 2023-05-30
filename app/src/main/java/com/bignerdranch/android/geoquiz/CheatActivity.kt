package com.bignerdranch.android.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider

class CheatActivity : ComponentActivity() {
    companion object {
        private const val EXTRA_ANSWER_IS_TRUE = "com.bignerdranch.android.geoquiz.answer_is_true"
        const val EXTRA_ANSWER_SHOWN = "com.bignerdranch.android.geoquiz.answer_shown"
        const val KEY_ANSWER_SHOWN = "com.bignerdranch.android.geoquiz.answer_shown"

        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }

    private var answerIsTrue = false
    private lateinit var answerTextView: TextView
    private lateinit var showAnswerButton: Button
    private lateinit var apiVersionTextView: TextView

    private val cheatViewModel: CheatViewModel by lazy {
        ViewModelProvider(this)[CheatViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        val isCheater = savedInstanceState?.getBoolean(KEY_ANSWER_SHOWN, false) ?: false
        setAnswerShownResult(isCheater)

        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
        answerTextView = findViewById(R.id.answer_text_view)
        apiVersionTextView = findViewById(R.id.api_version)
        showAnswerButton = findViewById(R.id.show_answer_button)

        showAnswerButton.setOnClickListener {
            val answerText = when {
                answerIsTrue -> R.string.true_button
                else -> R.string.false_button
            }
            answerTextView.setText(answerText)
            setAnswerShownResult(true)
        }

        val apiVersionText = resources.getString(R.string.api_version, Build.VERSION.SDK_INT)
        apiVersionTextView.text = apiVersionText
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_ANSWER_SHOWN, cheatViewModel.isCheater)
    }

    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        cheatViewModel.isCheater = isAnswerShown

        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(Activity.RESULT_OK, data)
    }
}