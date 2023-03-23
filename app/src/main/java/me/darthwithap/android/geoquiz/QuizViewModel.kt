package me.darthwithap.android.geoquiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class QuizViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

  var hasCheated: Boolean
    get() =
      savedStateHandle[HAS_CHEATED] ?: false
    set(value) {
      savedStateHandle[HAS_CHEATED] = value
    }

  private val questionBank = listOf(
    Question(R.string.question_one, false),
    Question(R.string.question_two, true),
    Question(R.string.question_three, true),
    Question(R.string.question_four, false)
  )
  private var currentIndex: Int
    get() = savedStateHandle[CURRENT_INDEX_KEY] ?: 0
    set(value) {
      savedStateHandle[CURRENT_INDEX_KEY] = value
    }

  val currentQuestionTextResId: Int
    get() = questionBank[currentIndex].questionTextResId

  val currentAnswer: Boolean?
    get() = questionBank[currentIndex].answer ?: null

  fun previousQuestion() {
    if (currentIndex == 0) currentIndex = questionBank.size - 1
    else {
      currentIndex -= 1
    }
  }

  fun nextQuestion() {
    currentIndex = (currentIndex + 1) % questionBank.size
  }

  fun getAnswerStringResId(answer: Boolean) = when {
    hasCheated -> R.string.answer_cheater
    answer == currentAnswer -> R.string.answer_correct
    else -> R.string.answer_incorrect
  }

  companion object {
    private const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"
    private const val HAS_CHEATED = "HAS_CHEATED"
  }
}