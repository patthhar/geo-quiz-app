package me.darthwithap.android.geoquiz

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import me.darthwithap.android.geoquiz.databinding.ActivityCheatBinding

class CheatActivity : AppCompatActivity() {

  private lateinit var binding: ActivityCheatBinding
  private var answer = false

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivityCheatBinding.inflate(layoutInflater)
    setContentView(binding.root)

    answer = intent.getBooleanExtra(EXTRA_ANSWER, false)

    binding.tvAnswer.setText(R.string.tex_cheat_warning)

    binding.buttonCheat.setOnClickListener {
      binding.tvAnswer.text = answer.toString()
      val data = Intent().apply {
        putExtra(HAS_CHEATED, true)
      }
      setResult(RESULT_OK, data)
    }
  }

  companion object {
    fun newIntent(packageContext: Context, answer: Boolean): Intent {
      return Intent(packageContext, CheatActivity::class.java).apply {
        putExtra(EXTRA_ANSWER, answer)
      }
    }

    private const val EXTRA_ANSWER = "EXTRA_ANSWER"
    private const val HAS_CHEATED = "HAS_CHEATED"
  }
}