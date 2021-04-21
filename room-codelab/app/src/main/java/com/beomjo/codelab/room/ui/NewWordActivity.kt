package com.beomjo.codelab.room.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import com.beomjo.codelab.room.databinding.ActivityNewWordBinding

class NewWordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewWordBinding
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewWordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setView()
    }

    private fun setView() {
        binding.apply {
            buttonSave.setOnClickListener {
                val replyIntent = Intent()
                if (TextUtils.isEmpty(editWord.text)) {
                    setResult(Activity.RESULT_CANCELED, replyIntent)
                } else {
                    val word = editWord.text.toString()
                    replyIntent.putExtra(EXTRA_REPLY, word)
                    setResult(Activity.RESULT_OK, replyIntent)
                }
                finish()
            }
        }
    }

    companion object {
        const val EXTRA_REPLY = "com.beomjo.codelab.room.wordlistsql.REPLY"
    }
}