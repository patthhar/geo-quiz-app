package me.darthwithap.android.geoquiz

import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import me.darthwithap.android.geoquiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding
  private val viewModel: QuizViewModel by viewModels()
  private val launcher = registerForActivityResult(
    ActivityResultContracts.StartActivityForResult()
  ) { result ->
    if (result.resultCode == RESULT_OK) {
      viewModel.hasCheated = result.data?.getBooleanExtra(HAS_CHEATED, false) ?: false
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivityMainBinding.inflate(layoutInflater)

    binding.buttonNext.setOnClickListener {
      nextQuestion()
    }

    binding.buttonPrevious.setOnClickListener {
      viewModel.previousQuestion()
      updateQuestion()
    }

    binding.buttonTrue.setOnClickListener {
      showToast(viewModel.getAnswerStringResId(true))
      nextQuestion()
    }

    binding.buttonFalse.setOnClickListener {
      showToast(viewModel.getAnswerStringResId(false))
      nextQuestion()
    }

    binding.tvQuestion.setText(viewModel.currentQuestionTextResId)

    binding.buttonCheat.setOnClickListener {
      val intent =
        viewModel.currentAnswer?.let { answer ->
          CheatActivity.newIntent(
            this@MainActivity,
            answer
          )
        }

      launcher.launch(intent)
    }

    setContentView(binding.root)
  }

  private fun showToast(answerString: Int) {
    Toast.makeText(this, getString(answerString), Toast.LENGTH_SHORT).show()
  }

  private fun updateQuestion() {
    binding.tvQuestion.setText(viewModel.currentQuestionTextResId)
    viewModel.hasCheated = false
  }

  private fun nextQuestion() {
    viewModel.nextQuestion()
    updateQuestion()
  }

  companion object {
    private const val HAS_CHEATED = "HAS_CHEATED"
  }
}