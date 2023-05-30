package com.bignerdranch.android.geoquiz

import android.util.Log
import androidx.lifecycle.ViewModel

class QuizViewModel : ViewModel() {

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true),
    )

    var currentIndex = 0

    private val cheatedQuestions = HashMap<Int, Boolean>()

    fun isCheater() = cheatedQuestions[currentIndex] ?: false
    fun setCheater(cheater: Boolean) {
        cheatedQuestions[currentIndex] = cheater
    }

    val cheatCount: Int
        get() = cheatedQuestions.count { (_, didCheat) -> didCheat }

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    companion object {
        private val TAG = "QuizViewModel"
    }

    init {
        Log.d(TAG, "ViewModel instance created")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel instance about to be destroyed")
    }
}