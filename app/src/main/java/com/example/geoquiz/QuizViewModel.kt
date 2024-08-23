package com.example.geoquiz

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel


const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"
//const val IS_CHEATER_KEY = "IS_CHEATER_KEY"
//Gave up on using the IS_CHEATER_KEY variable since it had problems identifying when the user cheats.
//Creating a map allows for each question to have a variable to which decides if they cheated.
// This works better since it allows for the main activity to check if the player cheated the same way if they were checking if they were correct.

class QuizViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    private val cheatsMap: MutableMap<Int, Boolean>
        get() = savedStateHandle["CHEATS_MAP"] ?: mutableMapOf<Int, Boolean>().also {
            savedStateHandle["CHEATS_MAP"] = it
        }

    var currentIndex: Int
        get() = savedStateHandle[CURRENT_INDEX_KEY] ?: 0
        set(value) = savedStateHandle.set(CURRENT_INDEX_KEY, value)

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    val hasCheatedOnCurrentQuestion: Boolean
        get() = cheatsMap[currentIndex] ?: false

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun moveToPrev() {
        currentIndex = (currentIndex - 1) % questionBank.size
    }

    fun markCheat() {
        cheatsMap[currentIndex] = true
        savedStateHandle.set("CHEATS_MAP", cheatsMap)
    }
}