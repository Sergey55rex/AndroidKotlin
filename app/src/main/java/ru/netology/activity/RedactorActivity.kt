package ru.netology.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_redactor.*
import ru.netology.R
import ru.netology.databinding.ActivityMainBinding
import ru.netology.databinding.ActivityNewPostBinding
import ru.netology.databinding.ActivityRedactorBinding

class RedactorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRedactorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val count = intent.getStringExtra("text")
        binding.edit.requestFocus();
        edit.setText(count)

        binding.ok.setOnClickListener {
            val intent = Intent()
            if (binding.edit.text.isNullOrBlank()) {
                setResult(Activity.RESULT_CANCELED, intent)
            } else {
                val content = binding.edit.text.toString()
                intent.putExtra(Intent.EXTRA_TEXT, content)
                setResult(Activity.RESULT_OK, intent)
            }
            finish()
        }
    }
}
